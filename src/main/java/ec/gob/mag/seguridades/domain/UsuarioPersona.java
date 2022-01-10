package ec.gob.mag.seguridades.domain;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import ec.gob.mag.seguridades.security.config.doamin.RolUsuario;
import ec.gob.mag.seguridades.util.Util;

import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

//============== LOMBOK =============
@Getter
@Setter
@ToString(of = "id")
//@EqualsAndHashCode(of="id")
@NoArgsConstructor
@AllArgsConstructor
@Builder
//========== JPA ======================
@Entity
@Table(name = "usuario_persona", schema = "sc_seguridad", uniqueConstraints = @UniqueConstraint(columnNames = {
		"usup_login", "per_id" }))
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "ord", scope = UsuarioPersona.class)
public class UsuarioPersona implements java.io.Serializable {
	private static final long serialVersionUID = -8497901268125504227L;
	@Id
	@Column(name = "usup_id", unique = true)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonProperty("usupId")
	private Long id;

	@Column(name = "per_id")
	@JsonProperty("perId")
	@JsonInclude(Include.NON_NULL)
	private Long perId;

	@Column(name = "usup_per_id_sigap")
	@JsonProperty("usupPerIdSigap")
	@JsonInclude(Include.NON_NULL)
	private Long usupPerIdSigap;

	@Column(name = "usup_login", unique = true)
	@JsonProperty("usupLogin")
	@JsonInclude(Include.NON_NULL)
	private String usupLogin;

	@JsonIgnoreProperties
	@Column(name = "usup_clave_sicpas")
	@JsonProperty("usupClave")
	@JsonInclude(Include.NON_NULL)
	private String usupClave;

	@Column(name = "usup_ad_correo")
	@JsonProperty("usupAdCorreo")
	@JsonInclude(Include.NON_NULL)
	private String usupAdCorreo;

	@Column(name = "usup_estado")
	@JsonProperty("usupEstado")
	@JsonInclude(Include.NON_NULL)
	private Integer usupEstado;

	@Column(name = "usup_eliminado")
	@JsonProperty("usupEliminado")
	@JsonInclude(Include.NON_NULL)
	private Boolean usupEliminado;

	@Column(name = "usup_reg_usu")
	@JsonProperty("usupRegUsu")
	@JsonInclude(Include.NON_NULL)
	private Integer usupRegUsu;

	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "usup_reg_fecha")
	@JsonProperty("usupRegFecha")
	@JsonInclude(Include.NON_NULL)
	private Date usupRegFecha;

	@Column(name = "usup_act_usu")
	@JsonProperty("usupActUsu")
	@JsonInclude(Include.NON_NULL)
	private Integer usupActUsu;

	@UpdateTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "usup_act_fecha")
	@JsonProperty("usupActFecha")
	@JsonInclude(Include.NON_NULL)
	private Date usupActFecha;

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "usuarioPersona")
	@JsonProperty("rolesUsuario")
	@JsonInclude(Include.NON_NULL)
	@JsonManagedReference(value = "usuarioPersona-rolUsuario")
	private List<RolUsuario> rolesUsuario;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "usuariopersona")
	@JsonProperty("usuarios")
	@JsonInclude(Include.NON_NULL)
	private List<Usuario> usuarios;

	@Transient
	@JsonIgnore
	private transient UsuarioPersona savedState;

	void saveState(UsuarioPersona savedState) {
		this.savedState = savedState;
	}

	@PrePersist
	public void prePersist() {
		this.usupRegFecha = Util.dateNow();
		this.usupEstado = 11;
		this.usupEliminado = false;
	}

	@PreUpdate
	public void preUpdate() {
		this.usupActFecha = Util.dateNow();
	}

}
