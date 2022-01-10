package ec.gob.mag.seguridades.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import ec.gob.mag.seguridades.domain.UsuarioPersona;
import ec.gob.mag.seguridades.exception.EnumCodeExceptions;
import ec.gob.mag.seguridades.exception.EnumTypeExceptions;
import ec.gob.mag.seguridades.exception.MyNotFoundException;
import ec.gob.mag.seguridades.repository.AplicacionRepository;
import ec.gob.mag.seguridades.repository.IUsuarioAuthService;
import ec.gob.mag.seguridades.repository.UsuarioPerfilRepository;
import ec.gob.mag.seguridades.repository.UsuarioPersonaRepository;
import ec.gob.mag.seguridades.security.config.doamin.RolUsuario;
import ec.gob.mag.seguridades.util.MyExceptionUtility;
import ec.gob.mag.seguridades.domain.Aplicacion;

import java.io.UnsupportedEncodingException;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Service("usuarioAuthService")
public class UsuarioAuthService implements IUsuarioAuthService, UserDetailsService {
	@Autowired
	@Qualifier("usuarioPersonaRepository")
	private UsuarioPersonaRepository usuarioPersonaRepository;

	@Autowired
	@Qualifier("aplicacionRepository")
	private AplicacionRepository aplicacionRepository;

	@Autowired
	private MessageSource messageSource;

	@Autowired
	@Qualifier("usuarioPerfilAuthRepository")
	private UsuarioPerfilRepository usuarioPerfilRepository;

	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Long apliId = Long.parseLong(getValueParameter("idapli"));
		String clientId = this.getClientId();

		if (clientId == null || clientId.equals("")) {
			String msg = MyExceptionUtility.buildExceptionJsonString("error.credential_required", username,
					this.getClass(), "loadUserByUsername", EnumTypeExceptions.INFO,
					EnumCodeExceptions.ERROR_AUTENTICATION, messageSource);
			throw new MyNotFoundException(msg);
		}

		Aplicacion aplicacion = aplicacionRepository.findByIdAndOauthClientDetails_ClientId(apliId, clientId).get();
		if (aplicacion == null) {
			String msg = MyExceptionUtility.buildExceptionJsonString("error.credential_not_equals", username,
					this.getClass(), "loadUserByUsername", EnumTypeExceptions.INFO,
					EnumCodeExceptions.ERROR_AUTENTICATION, messageSource);
			throw new MyNotFoundException(msg);
		}

		Optional<UsuarioPersona> usuarioPersona = this.findByUsernameAndIdApli(username, apliId);
		if (!usuarioPersona.isPresent()) {
			String msg = MyExceptionUtility.buildExceptionJsonString("_error.validation_users", username,
					this.getClass(), "loadUserByUsername", EnumTypeExceptions.INFO,
					EnumCodeExceptions.ERROR_AUTENTICATION, messageSource);
			throw new MyNotFoundException(msg);
		}

		Boolean status = (usuarioPersona.get().getUsupEstado() == 11 ? true : false);

		List<GrantedAuthority> authorities = usuarioPersona.get().getRolesUsuario().stream().map(rol -> {
			return new SimpleGrantedAuthority(rol.getRol().getRolNombre());
		}).collect(Collectors.toList());

		return new User(username, usuarioPersona.get().getUsupClave(), status, true, true, true, authorities);
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<UsuarioPersona> findByUsernameAndIdApli(String username, Long idApli) {
		Optional<UsuarioPersona> up = usuarioPersonaRepository
				.findByUsupLoginAndUsupEliminadoAndUsuarios_Usuariosperfil_apliId(username, false, idApli);
		List<RolUsuario> roles = up.get().getRolesUsuario().stream().filter(rol -> {
			Boolean rt = false;
			rt = idApli.equals(rol.getRol().getApliId());
			return rt;
		}).collect(Collectors.toList());
		up.get().setRolesUsuario(roles);
		return up;
	}

	@Override
	@Transactional(readOnly = true)
	public String getValueParameter(String name) {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
				.getRequest();
		return request.getParameter(name);
	}

	@Override
	@Transactional(readOnly = true)
	public String getClientId() {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
				.getRequest();
		String auth = request.getHeader("Authorization");
		auth = auth.replace("=", "").replace("Basic ", "");
		byte[] decodedBytes = null;
		try {
			decodedBytes = Base64.getUrlDecoder().decode(auth.getBytes("utf-8"));

		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		auth = new String(decodedBytes);
		return auth.split(":")[0];
	}

}
