package ec.gob.mag.seguridades.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Id;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import ec.gob.mag.seguridades.security.config.doamin.OauthClientDetails;
import ec.gob.mag.seguridades.security.config.doamin.Rol;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

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

@Entity
@Table(name = "aplicacion", schema = "sc_seguridad")

public class Aplicacion implements Serializable {
	/**
	 * 
	 */

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "apli_id", unique = true)
	@JsonProperty("apli_id")
	private Long id;

	@Column(name = "apli_nombre", length = 100)
	@JsonProperty("apliNombre")
	@JsonInclude(Include.NON_NULL)
	private String apliNombre;

	@Column(name = "apli_sigla", length = 32)
	@JsonProperty("apliSigla")
	@JsonInclude(Include.NON_NULL)
	private String apliSigla;

	@Column(name = "apli_url", length = 512)
	@JsonProperty("apli_url")
	@JsonInclude(Include.NON_NULL)
	private String apliUrl;

	@Column(name = "apli_descripcion", length = 1024)
	@JsonProperty("apliDescripcion")
	@JsonInclude(Include.NON_NULL)
	private String apliDescripcion;

	@Column(name = "apli_estado")
	@JsonProperty("apliEstado")
	@JsonInclude(Include.NON_NULL)
	private Integer apliEstado;

	@Column(name = "apli_eliminado")
	@JsonProperty("apliEliminado")
	@JsonInclude(Include.NON_NULL)
	private Boolean apliEliminado;

	@Column(name = "apli_reg_usu", updatable = false)
	@JsonProperty("apliRegUsu")
	@JsonInclude(Include.NON_NULL)
	private Long apliRegUsu;

	@CreationTimestamp
	@Column(name = "apli_reg_fecha", updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	@JsonProperty("apliRegFecha")
	@JsonInclude(Include.NON_NULL)
	private Date apliRegFecha;

	@Column(name = "apli_act_usu")
	@JsonProperty("apliActUsu")
	@JsonInclude(Include.NON_NULL)
	private Long apliActUsu;

	@UpdateTimestamp
	@Column(name = "apli_act_fecha")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonProperty("apliActFecha")
	@JsonInclude(Include.NON_NULL)
	private Date apliActFecha;

	@Column(name = "apli_req_activacion")
	@JsonProperty("apliReqActivacion")
	@JsonInclude(Include.NON_NULL)
	private Boolean apliReqActivacion;

	@OneToOne(fetch = FetchType.EAGER, mappedBy = "aplicacion", orphanRemoval = true, cascade = { CascadeType.PERSIST,
			CascadeType.MERGE, CascadeType.REFRESH })
	@JsonProperty("oauthClientDetails")
	@JsonManagedReference(value = "OauthClient-Aplicacion")
	private OauthClientDetails oauthClientDetails;

	@Transient
	@JsonInclude(Include.NON_NULL)
	List<Rol> roles;

	@PrePersist
	public void prePersist() {
		this.apliReqActivacion = true;
		this.apliEstado = 11;
	}

}
//ok
