package ec.gob.mag.seguridades.domain.dto;

import java.util.Date;

import java.util.List;

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

public class RolDTO {

	@JsonProperty("rolId")
	private Long id;

	@JsonProperty("apliId")
	@JsonInclude(Include.NON_NULL)
	private Long apliId;

	@JsonProperty("rolNombre")
	@JsonInclude(Include.NON_NULL)
	private String rolNombre;

	@JsonProperty("rolDescripcion")
	@JsonInclude(Include.NON_NULL)
	private String rolDescripcion;

	@JsonProperty("rolEstado")
	@JsonInclude(Include.NON_NULL)
	private Integer rolEstado;

	@JsonProperty("rolRegUsu")
	@JsonInclude(Include.NON_NULL)
	private Long rolRegUsu;

	@JsonProperty("rolRegFecha")
	@JsonInclude(Include.NON_NULL)
	private Date rolRegFecha;

	@JsonProperty("permisosRol")
	@JsonInclude(Include.NON_NULL)
	private List<PermisoRolDTO> permisosRol;

}
//ok
