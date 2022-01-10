package ec.gob.mag.seguridades.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
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
@Table(name = "perfil", schema = "sc_seguridad")
public class Perfil implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "pef_id")
	@JsonProperty("pefId")
	private Long id;

	@Column(name = "apli_id")
	@JsonProperty("apliId")
	@JsonInclude(Include.NON_NULL)
	private Long apliId;

	@Column(name = "pef_estado")
	@JsonProperty("pefEstado")
	@JsonInclude(Include.NON_NULL)
	private Integer pefEstado;

	@Column(name = "pef_eliminado")
	@JsonProperty("pefEliminado")
	@JsonInclude(Include.NON_NULL)
	private Boolean pefEliminado;

	@Column(name = "pef_reg_usu")
	@JsonProperty("pefRegUsu")
	@JsonInclude(Include.NON_NULL)
	private Integer pefRegUsu;

	@CreationTimestamp
	@Column(name = "pef_reg_fecha", updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	@JsonProperty("pefRegFecha")
	@JsonInclude(Include.NON_NULL)
	private Date pefRegFecha;

	@Column(name = "pef_act_usu")
	@JsonProperty("pefActUsu")
	@JsonInclude(Include.NON_NULL)
	private Integer pefActUsu;

	@CreationTimestamp
	@Column(name = "pef_act_fecha", updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	@JsonProperty("pefActFecha")
	@JsonInclude(Include.NON_NULL)
	private Date pefActFecha;

	/**
	 * RELACIONES JPA
	 */
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "tpef_id", referencedColumnName = "tpef_id")
	@JsonProperty("perfilTipo")
	@JsonInclude(Include.NON_NULL)
	private PerfilTipo perfilTipo;
}
