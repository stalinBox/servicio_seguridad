package ec.gob.mag.seguridades.security.config.doamin;

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
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import org.hibernate.annotations.CreationTimestamp;

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
@Table(name = "tbl_roles", schema = "sc_seguridad_sicpas")

public class Rol implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "rol_id", unique = true)
	@JsonProperty("rolId")
	private Long id;

	@Column(name = "apli_id")
	@JsonProperty("apliId")
	@JsonInclude(Include.NON_NULL)
	private Long apliId;

	@Column(name = "rol_nombre")
	@JsonProperty("rolNombre")
	@JsonInclude(Include.NON_NULL)
	private String rolNombre;

	@Column(name = "rol_estado")
	@JsonProperty("rolEstado")
	@JsonInclude(Include.NON_NULL)
	private Integer rolEstado;

	@Column(name = "rol_descripcion")
	@JsonProperty("rolDescripcion")
	@JsonInclude(Include.NON_NULL)
	private String rolDescripcion;

	@Column(name = "rol_reg_usu")
	@JsonProperty("rolRegUsu")
	@JsonInclude(Include.NON_NULL)
	private Long rolRegUsu;

	@CreationTimestamp
	@Column(name = "rol_reg_fecha", updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	@JsonProperty("rolRegFecha")
	@JsonInclude(Include.NON_NULL)
	private Date rolRegFecha;

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "rol", cascade = { CascadeType.REMOVE })
	@JsonProperty("permisosRol")
	@JsonManagedReference(value = "rol-permisoRol")
	private List<PermisoRol> permisosRol;
	
	//nuevo campo
	@Column(name = "tpef_id")
	@JsonProperty("tpefId")
	@JsonInclude(Include.NON_NULL)
	private Long tpefId;

	@PrePersist
	public void prePersist() {

		this.rolEstado = 11;
	}

}
//ok
