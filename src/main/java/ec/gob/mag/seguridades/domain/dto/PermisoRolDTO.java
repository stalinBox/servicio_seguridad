package ec.gob.mag.seguridades.domain.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(of = "id")
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class PermisoRolDTO {

	@JsonProperty("permisoRolId")
	private Long id;

	@JsonProperty("permiso")
	@JsonInclude(Include.NON_NULL)
	private PermisoDTO permiso;

	@JsonProperty("rol")
	@JsonInclude(Include.NON_NULL)
	private RolDTO rol;

}
//ok
