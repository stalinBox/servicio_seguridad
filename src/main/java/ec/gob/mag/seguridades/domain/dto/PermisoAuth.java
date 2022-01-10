package ec.gob.mag.seguridades.domain.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PermisoAuth {
	private Long idRol;
	private String nombreRol;
	private Long tpefId;
	private List<String> permisos;

}