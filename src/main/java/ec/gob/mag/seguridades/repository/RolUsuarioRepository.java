package ec.gob.mag.seguridades.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ec.gob.mag.seguridades.security.config.doamin.RolUsuario;

@Repository("rolUsuarioRepository")
public interface RolUsuarioRepository extends CrudRepository<RolUsuario, Long> {

	Optional<RolUsuario> findById(Long id);

}
