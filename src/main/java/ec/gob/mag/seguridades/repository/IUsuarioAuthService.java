package ec.gob.mag.seguridades.repository;

import java.util.Optional;

import ec.gob.mag.seguridades.domain.UsuarioPersona;

public interface IUsuarioAuthService {
	Optional<UsuarioPersona> findByUsernameAndIdApli(String username, Long idApli);

	String getValueParameter(String name);

	String getClientId();
}
