package ec.gob.mag.seguridades.repository;

import java.util.List;

//import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ec.gob.mag.seguridades.security.config.doamin.OauthClientDetails;

@Repository("oauthClientDetailsRepository")
public interface OauthClientDetailsRepository extends CrudRepository<OauthClientDetails, Long> {

	List<OauthClientDetails> findByClientIdAndAplicacion_Id(Long clientId, Long apliId);

}
