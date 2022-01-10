package ec.gob.mag.seguridades.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ec.gob.mag.seguridades.domain.Perfil;

@Repository("perfilAuthRepository")
public interface PerfilRepository extends CrudRepository<Perfil, Long> {
	Optional<Perfil> findById(Long pefId);
}
