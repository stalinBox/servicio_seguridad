package ec.gob.mag.seguridades.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ec.gob.mag.seguridades.security.config.doamin.PermisoRol;

@Repository("permisoRolRepository")
public interface PermisoRolRepository extends CrudRepository<PermisoRol, Long> {

	Optional<PermisoRol> findById(Long id);
}
