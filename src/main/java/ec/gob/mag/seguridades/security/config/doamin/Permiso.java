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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Table(name = "tbl_permisos", schema = "sc_seguridad_sicpas")

public class Permiso implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "perm_id", unique = true)
	@JsonProperty("perm_id")
	private Long id;

	@Column(name = "apli_id")
	@JsonProperty("apliId")
	@JsonInclude(Include.NON_NULL)
	private Long apliId;

	@Column(name = "per_descripcion")
	@JsonProperty("perDescripcion")
	@JsonInclude(Include.NON_NULL)
	private String perDescripcion;

	@Column(name = "per_nombre")
	@JsonProperty("perNombre")
	@JsonInclude(Include.NON_NULL)
	private String perNombre;

	@Column(name = "per_ruta")
	@JsonProperty("perRuta")
	@JsonInclude(Include.NON_NULL)
	private String perRuta;

	@Column(name = "per_tipo")
	@JsonProperty("perTipo")
	@JsonInclude(Include.NON_NULL)
	private String perTipo;

	@Column(name = "metodo")
	@JsonProperty("metodo")
	@JsonInclude(Include.NON_NULL)
	private String metodo;

	@CreationTimestamp
	@Column(name = "per_reg_fecha", updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	@JsonProperty("perRegFecha")
	@JsonInclude(Include.NON_NULL)
	private Date perRegFecha;

	@JsonIgnore
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "permiso", cascade = { CascadeType.REMOVE })
	@JsonProperty("permisosRol")
	private List<PermisoRol> premisosRol;

}
//ok
