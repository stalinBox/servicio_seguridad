package ec.gob.mag.seguridades.repository;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ec.gob.mag.seguridades.domain.Usuario;

@Repository("usuarioRepository")
public interface UsuarioRepository extends CrudRepository<Usuario, Long> {

	List<Usuario> findDistinctByUsuariopersona_id_AndUsuariosperfil_apliId(Long usuId, Long apliId);
	
	List<Usuario> findByUsuariopersona_UsupLoginAndUsuEstadoAndUsuEliminadoAndUsuariopersona_UsupEstadoAndUsuariopersona_UsupEliminado(String login, Long estado, Boolean eliminado, Integer estado1, Boolean eliminado1);
}
