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
public class UsuariosAuth {
	private Long usuId;
	private Long usupId;
	private List<PerfilTipoAuth> perfilTipo;
}