package ec.gob.mag.seguridades.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ec.gob.mag.seguridades.security.config.doamin.Permiso;

@Repository("permisoRepository")
public interface PermisoRepository extends CrudRepository<Permiso, Long> {

	List<Permiso> findByApliId(Long apliId);

	Optional<Permiso> findById(Long id);

}
