package ec.gob.mag.seguridades.repository;

import java.util.List;
import java.util.Optional;

//import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ec.gob.mag.seguridades.security.config.doamin.Rol;

@Repository("rolRepository")
public interface RolRepository extends CrudRepository<Rol, Long> {

	List<Rol> findByApliId(Long apliId);

	Optional<Rol> findById(Long id);

}
