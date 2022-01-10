package ec.gob.mag.seguridades.services;

import java.io.Serializable;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import ec.gob.mag.seguridades.domain.Perfil;
import ec.gob.mag.seguridades.exception.EnumCodeExceptions;
import ec.gob.mag.seguridades.exception.EnumTypeExceptions;
import ec.gob.mag.seguridades.exception.MyNotFoundException;
import ec.gob.mag.seguridades.repository.PerfilRepository;
import ec.gob.mag.seguridades.util.MyExceptionUtility;

@Service("perfilAuthService")
public class PerfilService implements Serializable {

	private static final long serialVersionUID = 1L;

	@Autowired
	@Qualifier("perfilAuthRepository")
	private PerfilRepository perfilAuthRepository;

	@Autowired
	private MessageSource messageSource;

	public Optional<Perfil> findByIdAndApliId(Long id, Long idApli) {
		System.out.println("ID DE LA APLICACION----- " + idApli);
		System.out.println("ID DE PERFIL----- " + id);
		Optional<Perfil> perfil = perfilAuthRepository.findById(id);
		if (!perfil.isPresent()) {
			String msg = MyExceptionUtility.buildExceptionJsonString("error.entity_not_exist.message", id.toString(),
					this.getClass(), "findById", EnumTypeExceptions.INFO, EnumCodeExceptions.DATA_NOT_FOUND_DB,
					messageSource);
			throw new MyNotFoundException(msg);
		}
		return perfil;
	}
}
