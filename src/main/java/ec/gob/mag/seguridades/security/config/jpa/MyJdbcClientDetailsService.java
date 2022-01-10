package ec.gob.mag.seguridades.security.config.jpa;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.common.exceptions.InvalidClientException;
import org.springframework.security.oauth2.common.util.DefaultJdbcListFactory;
import org.springframework.security.oauth2.common.util.JdbcListFactory;
import org.springframework.security.oauth2.provider.ClientAlreadyExistsException;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationService;
import org.springframework.security.oauth2.provider.NoSuchClientException;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;

/**
 * Basic, JDBC implementation of the client details service.
 */
@SuppressWarnings("deprecation")
public class MyJdbcClientDetailsService implements ClientDetailsService, ClientRegistrationService {

	private static final Log logger = LogFactory.getLog(MyJdbcClientDetailsService.class);

	private JsonMapper mapper = createJsonMapper();

	private RowMapper<ClientDetails> rowMapper = new ClientDetailsRowMapper();

	private String deleteClientDetailsSql = Querys.DEFAULT_DELETE_STATEMENT;

	private String findClientDetailsSql = Querys.DEFAULT_FIND_STATEMENT;

	private String updateClientDetailsSql = Querys.DEFAULT_UPDATE_STATEMENT;

	private String updateClientSecretSql = Querys.DEFAULT_UPDATE_SECRET_STATEMENT;

	private String insertClientDetailsSql = Querys.DEFAULT_INSERT_STATEMENT;

	private String selectClientDetailsSql = Querys.DEFAULT_SELECT_STATEMENT;

	private PasswordEncoder passwordEncoder = NoOpPasswordEncoder.getInstance();

	private final JdbcTemplate jdbcTemplate;

	private JdbcListFactory listFactory;

	public MyJdbcClientDetailsService(DataSource dataSource) {
		Assert.notNull(dataSource, "DataSource required");
		this.jdbcTemplate = new JdbcTemplate(dataSource);
		this.listFactory = new DefaultJdbcListFactory(new NamedParameterJdbcTemplate(jdbcTemplate));
	}

	/**
	 * @param passwordEncoder the password encoder to set
	 */
	public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}

	public ClientDetails loadClientByClientId(String clientId) throws InvalidClientException {
		ClientDetails details;
		try {
			details = jdbcTemplate.queryForObject(selectClientDetailsSql, new ClientDetailsRowMapper(), clientId);
		} catch (EmptyResultDataAccessException e) {
			throw new NoSuchClientException("No client with requested id: " + clientId);
		}

		return details;
	}

	public void addClientDetails(ClientDetails clientDetails) throws ClientAlreadyExistsException {
		try {
			jdbcTemplate.update(insertClientDetailsSql, getFields(clientDetails));
		} catch (DuplicateKeyException e) {
			throw new ClientAlreadyExistsException("Client already exists: " + clientDetails.getClientId(), e);
		}
	}

	public void updateClientDetails(ClientDetails clientDetails) throws NoSuchClientException {
		int count = jdbcTemplate.update(updateClientDetailsSql, getFieldsForUpdate(clientDetails));
		if (count != 1) {
			throw new NoSuchClientException("No client found with id = " + clientDetails.getClientId());
		}
	}

	public void updateClientSecret(String clientId, String secret) throws NoSuchClientException {
		int count = jdbcTemplate.update(updateClientSecretSql, passwordEncoder.encode(secret), clientId);
		if (count != 1) {
			throw new NoSuchClientException("No client found with id = " + clientId);
		}
	}

	public void removeClientDetails(String clientId) throws NoSuchClientException {
		int count = jdbcTemplate.update(deleteClientDetailsSql, clientId);
		if (count != 1) {
			throw new NoSuchClientException("No client found with id = " + clientId);
		}
	}

	public List<ClientDetails> listClientDetails() {
		return listFactory.getList(findClientDetailsSql, Collections.<String, Object>emptyMap(), rowMapper);
	}

	private Object[] getFields(ClientDetails clientDetails) {
		Object[] fieldsForUpdate = getFieldsForUpdate(clientDetails);
		Object[] fields = new Object[fieldsForUpdate.length + 1];
		System.arraycopy(fieldsForUpdate, 0, fields, 1, fieldsForUpdate.length);
		fields[0] = clientDetails.getClientSecret() != null ? passwordEncoder.encode(clientDetails.getClientSecret())
				: null;
		return fields;
	}

	private Object[] getFieldsForUpdate(ClientDetails clientDetails) {
		String json = null;
		try {
			json = mapper.write(clientDetails.getAdditionalInformation());
		} catch (Exception e) {
			logger.warn("Could not serialize additional information: " + clientDetails, e);
		}
		return new Object[] {
				clientDetails.getResourceIds() != null
						? StringUtils.collectionToCommaDelimitedString(clientDetails.getResourceIds())
						: null,
				clientDetails.getScope() != null
						? StringUtils.collectionToCommaDelimitedString(clientDetails.getScope())
						: null,
				clientDetails.getAuthorizedGrantTypes() != null
						? StringUtils.collectionToCommaDelimitedString(clientDetails.getAuthorizedGrantTypes())
						: null,
				clientDetails.getRegisteredRedirectUri() != null
						? StringUtils.collectionToCommaDelimitedString(clientDetails.getRegisteredRedirectUri())
						: null,
				clientDetails.getAuthorities() != null
						? StringUtils.collectionToCommaDelimitedString(clientDetails.getAuthorities())
						: null,
				clientDetails.getAccessTokenValiditySeconds(), clientDetails.getRefreshTokenValiditySeconds(), json,
				getAutoApproveScopes(clientDetails), clientDetails.getClientId() };
	}

	private String getAutoApproveScopes(ClientDetails clientDetails) {
		if (clientDetails.isAutoApprove("true")) {
			return "true"; // all scopes autoapproved
		}
		Set<String> scopes = new HashSet<String>();
		for (String scope : clientDetails.getScope()) {
			if (clientDetails.isAutoApprove(scope)) {
				scopes.add(scope);
			}
		}
		return StringUtils.collectionToCommaDelimitedString(scopes);
	}

	public void setSelectClientDetailsSql(String selectClientDetailsSql) {
		this.selectClientDetailsSql = selectClientDetailsSql;
	}

	public void setDeleteClientDetailsSql(String deleteClientDetailsSql) {
		this.deleteClientDetailsSql = deleteClientDetailsSql;
	}

	public void setUpdateClientDetailsSql(String updateClientDetailsSql) {
		this.updateClientDetailsSql = updateClientDetailsSql;
	}

	public void setUpdateClientSecretSql(String updateClientSecretSql) {
		this.updateClientSecretSql = updateClientSecretSql;
	}

	public void setInsertClientDetailsSql(String insertClientDetailsSql) {
		this.insertClientDetailsSql = insertClientDetailsSql;
	}

	public void setFindClientDetailsSql(String findClientDetailsSql) {
		this.findClientDetailsSql = findClientDetailsSql;
	}

	/**
	 * @param listFactory the list factory to set
	 */
	public void setListFactory(JdbcListFactory listFactory) {
		this.listFactory = listFactory;
	}

	/**
	 * @param rowMapper the rowMapper to set
	 */
	public void setRowMapper(RowMapper<ClientDetails> rowMapper) {
		this.rowMapper = rowMapper;
	}

	/**
	 * Row mapper for ClientDetails.
	 * 
	 * @author Dave Syer
	 * 
	 */
	private static class ClientDetailsRowMapper implements RowMapper<ClientDetails> {
		private JsonMapper mapper = createJsonMapper();

		public ClientDetails mapRow(ResultSet rs, int rowNum) throws SQLException {
			BaseClientDetails details = new BaseClientDetails(rs.getString(1), rs.getString(3), rs.getString(4),
					rs.getString(5), rs.getString(7), rs.getString(6));
			details.setClientSecret(rs.getString(2));
			if (rs.getObject(8) != null) {
				details.setAccessTokenValiditySeconds(rs.getInt(8));
			}
			if (rs.getObject(9) != null) {
				details.setRefreshTokenValiditySeconds(rs.getInt(9));
			}
			String json = rs.getString(10);
			if (json != null) {
				try {
					@SuppressWarnings("unchecked")
					Map<String, Object> additionalInformation = mapper.read(json, Map.class);
					details.setAdditionalInformation(additionalInformation);
				} catch (Exception e) {
					logger.warn("Could not decode JSON for additional information: " + details, e);
				}
			}
			String scopes = rs.getString(11);
			if (scopes != null) {
				details.setAutoApproveScopes(StringUtils.commaDelimitedListToSet(scopes));
			}
			return details;
		}
	}

	interface JsonMapper {
		String write(Object input) throws Exception;

		<T> T read(String input, Class<T> type) throws Exception;
	}

	private static JsonMapper createJsonMapper() {
		if (ClassUtils.isPresent("org.codehaus.jackson.map.ObjectMapper", null)) {
			return new JacksonMapper();
		} else if (ClassUtils.isPresent("com.fasterxml.jackson.databind.ObjectMapper", null)) {
			return new Jackson2Mapper();
		}
		return new NotSupportedJsonMapper();
	}

	private static class JacksonMapper implements JsonMapper {
		private org.codehaus.jackson.map.ObjectMapper mapper = new org.codehaus.jackson.map.ObjectMapper();

		@Override
		public String write(Object input) throws Exception {
			return mapper.writeValueAsString(input);
		}

		@Override
		public <T> T read(String input, Class<T> type) throws Exception {
			return mapper.readValue(input, type);
		}
	}

	private static class Jackson2Mapper implements JsonMapper {
		private com.fasterxml.jackson.databind.ObjectMapper mapper = new com.fasterxml.jackson.databind.ObjectMapper();

		@Override
		public String write(Object input) throws Exception {
			return mapper.writeValueAsString(input);
		}

		@Override
		public <T> T read(String input, Class<T> type) throws Exception {
			return mapper.readValue(input, type);
		}
	}

	private static class NotSupportedJsonMapper implements JsonMapper {
		@Override
		public String write(Object input) throws Exception {
			throw new UnsupportedOperationException(
					"Neither Jackson 1 nor 2 is available so JSON conversion cannot be done");
		}

		@Override
		public <T> T read(String input, Class<T> type) throws Exception {
			throw new UnsupportedOperationException(
					"Neither Jackson 1 nor 2 is available so JSON conversion cannot be done");
		}
	}

}
