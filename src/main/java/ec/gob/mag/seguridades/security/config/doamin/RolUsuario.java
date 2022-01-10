package ec.gob.mag.seguridades.security.config.doamin;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import ec.gob.mag.seguridades.domain.UsuarioPersona;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
@Table(name = "tbl_rol_usuario", schema = "sc_seguridad_sicpas")

public class RolUsuario implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "rol_usu_id", unique = true)
	@JsonProperty("rolUsuId")
	private Long id;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "rol_id")
	@JsonProperty("rol")
	@JsonInclude(Include.NON_NULL)
	private Rol rol;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "usup_id")
	@JsonProperty("usuarioPersona")
	@JsonInclude(Include.NON_NULL)
	@JsonBackReference(value = "usuarioPersona-rolUsuario")
	private UsuarioPersona usuarioPersona;

}
