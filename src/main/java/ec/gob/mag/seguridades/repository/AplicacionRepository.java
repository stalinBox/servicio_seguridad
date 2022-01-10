package ec.gob.mag.seguridades.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ec.gob.mag.seguridades.domain.Aplicacion;

@Repository("aplicacionRepository")
public interface AplicacionRepository extends CrudRepository<Aplicacion, Long> {

	Optional<Aplicacion> findById(Long id);

	Optional<Aplicacion> findByIdAndOauthClientDetails_ClientId(Long id, String clientId);

}
