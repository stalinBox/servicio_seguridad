package ec.gob.mag.seguridades.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
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
@Table(name = "usuario", schema = "sc_seguridad")
public class Usuario implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "usu_id", unique = true)
	@JsonProperty("usuId")
	private Long id;

	@Column(name = "peti_id")
	@JsonProperty("petiId")
	@JsonInclude(Include.NON_NULL)
	private Long petiId;

	@CreationTimestamp
	@Column(name = "usu_expira", updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	@JsonProperty("usuExpira")
	@JsonInclude(Include.NON_NULL)
	private Date usuExpira;

	@Column(name = "usu_estado")
	@JsonProperty("usuEstado")
	@JsonInclude(Include.NON_NULL)
	private Long usuEstado;

	@Column(name = "usu_eliminado")
	@JsonProperty("usuEliminado")
	@JsonInclude(Include.NON_NULL)
	private Boolean usuEliminado;

	@Column(name = "usu_reg_usu")
	@JsonProperty("usuRegUsu")
	@JsonInclude(Include.NON_NULL)
	private Long usuRegUsu;

	@Column(name = "usu_reg_fecha", updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	@JsonProperty("usuRegFecha")
	@JsonInclude(Include.NON_NULL)
	private Date usuRegFecha;

	@Column(name = "usu_act_usu")
	@JsonProperty("usuActUsu")
	@JsonInclude(Include.NON_NULL)
	private Long usuActUsu;

	@Column(name = "usu_act_fecha", updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	@JsonProperty("usuActFecha")
	@JsonInclude(Include.NON_NULL)
	private Date usuActFecha;

	@Column(name = "usu_fecha_activacion", updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	@JsonProperty("usuFechaActivacion")
	@JsonInclude(Include.NON_NULL)
	private Date usuFechaActivacion;

	@Column(name = "usu_acepta_term")
	@JsonProperty("usuAceptaTerm")
	@JsonInclude(Include.NON_NULL)
	private Boolean usuAceptaTerm;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "usup_id")
	@JsonProperty("usupId")
	@JsonInclude(Include.NON_NULL)
	private UsuarioPersona usuariopersona;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "usuario")
	@JsonProperty("usuariosperfil")
	@JsonInclude(Include.NON_NULL)
	private List<UsuarioPerfil> usuariosperfil;

}
