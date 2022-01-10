package ec.gob.mag.seguridades;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@SpringBootApplication
public class ServicioSeguridades extends SpringBootServletInitializer implements CommandLineRunner {

	private static Class<ServicioSeguridades> applicationClass = ServicioSeguridades.class;

	@RequestMapping("/validateUser")
	public Principal user(Principal user) {
		return user;
	}

	@RequestMapping("/unauthenticated")
	public String unauthenticated() {
		return "redirect:/?error=true";
	}

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	public static void main(String[] args) {
		SpringApplication.run(ServicioSeguridades.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(applicationClass);
	}

	@Override
	public void run(String... args) throws Exception {
		String password = "6i77P@__";

		for (int i = 0; i < 4; i++) {
			String passwordBcrypt = passwordEncoder.encode(password);
			System.out.println(passwordBcrypt);
		}

	}

}
