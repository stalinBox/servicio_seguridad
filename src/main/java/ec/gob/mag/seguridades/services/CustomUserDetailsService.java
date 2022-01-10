package ec.gob.mag.seguridades.services;

import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import ec.gob.mag.seguridades.domain.Usuario;
import ec.gob.mag.seguridades.repository.UsuarioRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService{
	
	@Autowired
	@Qualifier("usuarioRepository")
	private UsuarioRepository usuarioRepository; 
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
	{   
		Usuario usuario = usuarioRepository.findByUsuariopersona_UsupLoginAndUsuEstadoAndUsuEliminadoAndUsuariopersona_UsupEstadoAndUsuariopersona_UsupEliminado(username, new Long(11), new Boolean(false),new Integer(11), new Boolean(false)).get(0);
		return new org.springframework.security.core.userdetails.User(usuario.getUsuariopersona().getUsupLogin(),usuario.getUsuariopersona().getUsupClave(),new ArrayList<>()); 
	}

}
