package ec.gob.mag.seguridades.security.config.doamin;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.JoinColumn;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import ec.gob.mag.seguridades.domain.Aplicacion;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(of = "clientId")
@EqualsAndHashCode(of = "clientId")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "tbl_oauth_client_details", schema = "sc_seguridad_sicpas")

public class OauthClientDetails implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "client_id")
	@JsonProperty("clientId")
	@NotNull
	private String clientId;

	@Column(name = "client_secret")
	@JsonProperty(access = Access.WRITE_ONLY, value = "clientSecret")
	@NotNull
	private String clientSecret;

	@Column(name = "scope")
	@JsonProperty(access = Access.READ_ONLY, value = "scope")
	private String scope;

	@Column(name = "authorized_grant_types")
	@JsonProperty(access = Access.READ_ONLY, value = "authorizedGrantTypes")
	@JsonIgnore
	private String authorizedGrantTypes;

	@Column(name = "access_token_validity")
	@JsonProperty("accessTokenValidity")
	@NotNull
	private Integer accessTokenValidity;

	@Column(name = "refresh_token_validity")
	@JsonInclude(Include.NON_NULL)
	@JsonProperty(access = Access.READ_ONLY, value = "refreshTokenValidity")
	private Integer refreshTokenValidity;

	@Column(name = "autoapprove")
	@JsonProperty(access = Access.READ_ONLY, value = "autoapprove")
	@JsonIgnore
	private String autoapprove;

	@OneToOne()
	@JoinColumn(name = "apli_id")
	@JsonProperty("aplicacion")
	@JsonBackReference(value = "OauthClient-Aplicacion")
	private Aplicacion aplicacion;

	@PrePersist
	public void prePersist() {
		this.scope = "secret";
		this.autoapprove = "true";
		this.authorizedGrantTypes = "password,authorization_code,refresh_token";
		if (this.accessTokenValidity == 0 || this.accessTokenValidity == null) {
			this.accessTokenValidity = 36000;
		}
		this.refreshTokenValidity = this.accessTokenValidity + 300;
	}

	@PreUpdate
	public void PreUpdate() {
		this.scope = "secret";
		this.autoapprove = "true";
		this.authorizedGrantTypes = "password,authorization_code,refresh_token";
		if (this.accessTokenValidity == 0 || this.accessTokenValidity == null) {
			this.accessTokenValidity = 36000;
		}
		this.refreshTokenValidity = this.accessTokenValidity + 300;
	}

}
//ok
