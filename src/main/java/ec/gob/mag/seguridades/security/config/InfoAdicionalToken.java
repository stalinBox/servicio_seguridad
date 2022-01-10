package ec.gob.mag.seguridades.security.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.stereotype.Component;

import ec.gob.mag.seguridades.domain.Perfil;
import ec.gob.mag.seguridades.domain.Usuario;
import ec.gob.mag.seguridades.domain.UsuarioPerfil;
import ec.gob.mag.seguridades.domain.UsuarioPersona;
import ec.gob.mag.seguridades.domain.dto.PerfilTipoAuth;
import ec.gob.mag.seguridades.domain.dto.PermisoAuth;
import ec.gob.mag.seguridades.domain.dto.RecursoAuth;
import ec.gob.mag.seguridades.domain.dto.UsuariosAuth;
import ec.gob.mag.seguridades.repository.UsuarioPerfilRepository;
import ec.gob.mag.seguridades.repository.UsuarioRepository;
import ec.gob.mag.seguridades.security.config.doamin.RolUsuario;
import ec.gob.mag.seguridades.services.PerfilService;
import ec.gob.mag.seguridades.services.UsuarioAuthService;

@Component
public class InfoAdicionalToken implements TokenEnhancer {

	@Autowired
	@Qualifier("usuarioAuthService")
	private UsuarioAuthService usuarioAuthService;

	@Autowired
	@Qualifier("usuarioRepository")
	private UsuarioRepository usuarioRepository;

	@Autowired
	@Qualifier("perfilAuthService")
	private PerfilService perfilAuthService;

	@Autowired
	@Qualifier("usuarioPerfilAuthRepository")
	private UsuarioPerfilRepository usuarioPerfilRepository;

	@Override
	public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {

		Map<String, Object> info = new HashMap<>();
		Long apliId = Long.parseLong(usuarioAuthService.getValueParameter("idapli"));
		Optional<UsuarioPersona> usuarioPersona = usuarioAuthService.findByUsernameAndIdApli(authentication.getName(),
				apliId);
		List<RolUsuario> rolesUsuario = usuarioPersona.get().getRolesUsuario();

		List<Usuario> usuario = usuarioRepository
				.findDistinctByUsuariopersona_id_AndUsuariosperfil_apliId(usuarioPersona.get().getId(), apliId);
		System.out.println("TAMAÑO DE USUARIO " + usuario.size());
		usuario.forEach(mpr -> {
			System.out.println("ID DE USUARIOS: " + mpr.getId());
		});

		List<PermisoAuth> roles = findRoles(rolesUsuario);
		List<RecursoAuth> recursos = findRecursos(rolesUsuario);
		List<UsuariosAuth> usuarios = findUsuarios(usuario, apliId);

		info.put("per_id", usuarioPersona.get().getPerId());
		info.put("apliId", apliId);
		info.put("roles", roles);
		info.put("recursos", recursos);
		info.put("usuarios", usuarios);
		((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(info);
		return accessToken;
	}

	public List<UsuariosAuth> findUsuarios(List<Usuario> usuarios, Long apliId) {
		List<UsuariosAuth> usuariosAuth = new ArrayList<UsuariosAuth>();
		usuarios.forEach(mprUsuario -> {
			List<PerfilTipoAuth> perfilTipoAuth = new ArrayList<PerfilTipoAuth>();
			UsuariosAuth uAuth = new UsuariosAuth();
			List<UsuarioPerfil> usuarioPerfil = usuarioPerfilRepository.findByApliIdAndUsuario_Id(apliId,
					mprUsuario.getId());

			System.out.println("TAMAÑO DE USUARIOPERFIL: " + usuarioPerfil.size());

			if (!usuarioPerfil.isEmpty()) {
				usuarioPerfil.forEach(mprUsuarioPerfil -> {
					PerfilTipoAuth uAuthPerfil = new PerfilTipoAuth();
					Optional<Perfil> perfil = perfilAuthService.findByIdAndApliId(mprUsuarioPerfil.getPefId(), apliId);
					uAuthPerfil.setTpefNombre(perfil.get().getPerfilTipo().getTpefNombre());
					perfilTipoAuth.add(uAuthPerfil);
				});
				uAuth.setUsuId(mprUsuario.getId());
				uAuth.setUsupId(mprUsuario.getUsuariopersona().getId());
				uAuth.setPerfilTipo(perfilTipoAuth);
				usuariosAuth.add(uAuth);
			}
		});
		return usuariosAuth;
	}

	public List<RecursoAuth> findRecursos(List<RolUsuario> rolesUsuario) {
		List<RecursoAuth> recursos = new ArrayList<RecursoAuth>();
		rolesUsuario.stream().forEachOrdered(rolUsuario -> {
			List<RecursoAuth> recursosI = new ArrayList<RecursoAuth>();
			if (rolUsuario.getRol().getPermisosRol() != null && rolUsuario.getRol().getPermisosRol().size() > 0)
				recursosI = rolUsuario.getRol().getPermisosRol().stream().map(pr -> {
					if (pr.getPermiso() != null && pr.getPermiso().getPerTipo().equals("A"))
						return new RecursoAuth(pr.getPermiso().getMetodo(), pr.getPermiso().getPerRuta());
					return new RecursoAuth("", "");
				}).filter(pr -> !pr.getPath().equals("")).collect(Collectors.toList());
			recursos.addAll(recursosI);
		});
		return recursos;
	}

	private List<PermisoAuth> findRoles(List<RolUsuario> rolesUsuario) {
		List<PermisoAuth> permisosr = rolesUsuario.stream().map(rolUsuario -> {
			PermisoAuth permiso = new PermisoAuth();
			permiso.setNombreRol(rolUsuario.getRol().getRolNombre());
			permiso.setIdRol(rolUsuario.getRol().getId());
			permiso.setTpefId(rolUsuario.getRol().getTpefId());
			List<String> permisos = new ArrayList<>();

			if (rolUsuario.getRol().getPermisosRol() != null && rolUsuario.getRol().getPermisosRol().size() > 0)
				permisos = rolUsuario.getRol().getPermisosRol().stream().map(pr -> {
					if (pr.getPermiso() != null && pr.getPermiso().getPerTipo() != null
							&& pr.getPermiso().getPerTipo().equals("A"))
						return pr.getPermiso().getPerRuta();
					return "";
				}).filter(pr -> !pr.equals("")).collect(Collectors.toList());
			permiso.setPermisos(permisos);
			return permiso;
		}).filter(permiso -> permiso.getPermisos().size() > 0).distinct().collect(Collectors.toList());
		if (permisosr.size() == 0 || permisosr == null)
			permisosr = new ArrayList<>();
		return permisosr;
	}

}
