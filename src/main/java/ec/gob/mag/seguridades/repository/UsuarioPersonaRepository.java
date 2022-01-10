package ec.gob.mag.seguridades.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ec.gob.mag.seguridades.domain.UsuarioPersona;

@Repository("usuarioPersonaRepository")
public interface UsuarioPersonaRepository extends CrudRepository<UsuarioPersona, Long> {

	Optional<UsuarioPersona> findById(Long id);

	Optional<UsuarioPersona> findByUsupLoginAndUsupClave(String usupLogin, String usupClave);

	Optional<UsuarioPersona> findByUsupLogin(String usupLogin);

	Optional<UsuarioPersona> findByUsupLoginAndUsupEliminadoAndUsuarios_Usuariosperfil_apliId(String usupLogin,
			Boolean usupEliminado, Long apliId);

}
