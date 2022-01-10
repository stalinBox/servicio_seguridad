package ec.gob.mag.seguridades.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "perfil_tipo", schema = "sc_seguridad")
public class PerfilTipo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "tpef_id")
	@JsonProperty("tpefId")
	private Long id;

	@Column(name = "tpef_nombre")
	@JsonProperty("tpefNombre")
	@JsonInclude(Include.NON_NULL)
	private String tpefNombre;

	@Column(name = "tpef_descripcion")
	@JsonProperty("tpefDescripcion")
	@JsonInclude(Include.NON_NULL)
	private String tpefDescripcion;

	@Column(name = "tpef_estado")
	@JsonProperty("tpefEstado")
	@JsonInclude(Include.NON_NULL)
	private Integer tpefEstado;

	@Column(name = "tpef_eliminado")
	@JsonProperty("tpefEliminado")
	@JsonInclude(Include.NON_NULL)
	private Boolean tpefEliminado;

	@Column(name = "tpef_reg_usu")
	@JsonProperty("tpefRegUsu")
	@JsonInclude(Include.NON_NULL)
	private Integer tpefRegUsu;

	@CreationTimestamp
	@Column(name = "tpef_reg_fecha", updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	@JsonProperty("tpefRegFecha")
	@JsonInclude(Include.NON_NULL)
	private Date tpefRegFecha;

	@Column(name = "tpef_act_usu")
	@JsonProperty("tpefActUsu")
	@JsonInclude(Include.NON_NULL)
	private Integer tpefActUsu;

	@CreationTimestamp
	@Column(name = "tpef_act_fecha", updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	@JsonProperty("tpefActFecha")
	@JsonInclude(Include.NON_NULL)
	private Date tpefActFecha;
}
