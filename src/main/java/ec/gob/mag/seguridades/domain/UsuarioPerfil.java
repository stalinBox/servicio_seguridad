package ec.gob.mag.seguridades.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

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

@Entity
@Table(name = "usuario_perfil", schema = "sc_seguridad")
public class UsuarioPerfil implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "upef_id", unique = true)
	@JsonProperty("upefId")
	private Long id;

	@Column(name = "pef_id")
	@JsonProperty("pefId")
	@JsonInclude(Include.NON_NULL)
	private Long pefId;

	@Column(name = "apli_id")
	@JsonProperty("apliId")
	@JsonInclude(Include.NON_NULL)
	private Long apliId;

	@Column(name = "upef_por_defecto")
	@JsonProperty("upefPorDefecto")
	@JsonInclude(Include.NON_NULL)
	private Long upefPorDefecto;

	@Column(name = "upef_estado")
	@JsonProperty("upefEstado")
	@JsonInclude(Include.NON_NULL)
	private Long upefEstado;

	@Column(name = "upef_eliminado")
	@JsonProperty("upefEliminado")
	@JsonInclude(Include.NON_NULL)
	private Boolean upefEliminado;

	@Column(name = "upef_reg_usu")
	@JsonProperty("upefRegUsu")
	@JsonInclude(Include.NON_NULL)
	private Long upefRegUsu;

	@CreationTimestamp
	@Column(name = "upef_reg_fecha", updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	@JsonProperty("upefRegFecha")
	@JsonInclude(Include.NON_NULL)
	private Date upefRegFecha;

	@Column(name = "upef_act_usu")
	@JsonProperty("upefActUsu")
	@JsonInclude(Include.NON_NULL)
	private Long upefActUsu;

	@CreationTimestamp
	@Column(name = "upef_act_fecha", updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	@JsonProperty("upefActFecha")
	@JsonInclude(Include.NON_NULL)
	private Date upefActFecha;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "usu_id")
	@JsonProperty("usuId")
	@JsonInclude(Include.NON_NULL)
	private Usuario usuario;

}
