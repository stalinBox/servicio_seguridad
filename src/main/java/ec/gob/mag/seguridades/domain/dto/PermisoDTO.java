package ec.gob.mag.seguridades.domain.dto;

import java.util.Date;

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

public class PermisoDTO {

	@JsonProperty("perm_id")
	private Long id;

	@JsonProperty("apliId")
	@JsonInclude(Include.NON_NULL)
	private Long apliId;

	@JsonProperty("perDescripcion")
	@JsonInclude(Include.NON_NULL)
	private String perDescripcion;

	@JsonProperty("perNombre")
	@JsonInclude(Include.NON_NULL)
	private String perNombre;

	@JsonProperty("perRuta")
	@JsonInclude(Include.NON_NULL)
	private String perRuta;

	@JsonProperty("perTipo")
	@JsonInclude(Include.NON_NULL)
	private String perTipo;

	@JsonProperty("metodo")
	@JsonInclude(Include.NON_NULL)
	private String metodo;

	@JsonProperty("perRegFecha")
	@JsonInclude(Include.NON_NULL)
	private Date perRegFecha;

}
