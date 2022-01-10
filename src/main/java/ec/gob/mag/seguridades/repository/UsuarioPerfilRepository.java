package ec.gob.mag.seguridades.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ec.gob.mag.seguridades.domain.UsuarioPerfil;

@Repository("usuarioPerfilAuthRepository")
public interface UsuarioPerfilRepository extends CrudRepository<UsuarioPerfil, Long> {
	List<UsuarioPerfil> findByApliIdAndUsuario_Id(Long apliId, Long usupId);
}
