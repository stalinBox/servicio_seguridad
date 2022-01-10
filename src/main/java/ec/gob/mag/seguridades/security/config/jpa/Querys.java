package ec.gob.mag.seguridades.security.config.jpa;

/**
 * Basic, JDBC implementation of the client details service.
 */
public class Querys {

	public static final String TABLE = " sc_seguridad_sicpas.tbl_oauth_client_details ";

	public static final String CLIENT_FIELDS_FOR_UPDATE = "resource_ids, scope, "
			+ "authorized_grant_types, web_server_redirect_uri, authorities, access_token_validity, "
			+ "refresh_token_validity, additional_information, autoapprove";

	public static final String CLIENT_FIELDS = "client_secret, " + CLIENT_FIELDS_FOR_UPDATE;

	public static final String BASE_FIND_STATEMENT = "select client_id, " + CLIENT_FIELDS + " from" + TABLE;

	public static final String DEFAULT_FIND_STATEMENT = BASE_FIND_STATEMENT + " order by client_id";

	public static final String DEFAULT_SELECT_STATEMENT = BASE_FIND_STATEMENT + " where client_id = ?";

	public static final String DEFAULT_INSERT_STATEMENT = "insert into" + TABLE + "(" + CLIENT_FIELDS
			+ ", client_id) values (?,?,?,?,?,?,?,?,?,?,?)";

	public static final String DEFAULT_UPDATE_STATEMENT = "update" + TABLE + "set "
			+ CLIENT_FIELDS_FOR_UPDATE.replaceAll(", ", "=?, ") + "=? where client_id = ?";

	public static final String DEFAULT_UPDATE_SECRET_STATEMENT = "update" + TABLE
			+ "set client_secret = ? where client_id = ?";

	public static final String DEFAULT_DELETE_STATEMENT = "delete from " + TABLE + " where client_id = ?";
}
