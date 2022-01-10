package ec.gob.mag.seguridades.security.config;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;

import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.oauth2.common.DefaultExpiringOAuth2RefreshToken;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.DefaultOAuth2RefreshToken;
import org.springframework.security.oauth2.common.ExpiringOAuth2RefreshToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.security.oauth2.common.util.JsonParser;
import org.springframework.security.oauth2.common.util.JsonParserFactory;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.stereotype.Component;

@Component
public class CustomTokenConverter extends JwtAccessTokenConverter {
	private JsonParser objectMapper = JsonParserFactory.create();
	DefaultTokenServices defaultTokenServices = new DefaultTokenServices();

	private int minimumAccessTokenValiditySeconds = 10;
	private int maximumAccessTokenValiditySeconds = minimumAccessTokenValiditySeconds * 2;
//	private int minimumRefreshTokenValiditySeconds = 2592000;
//	private int maximumRefreshTokenValiditySeconds = minimumRefreshTokenValiditySeconds * 2;

	protected int getAccessTokenValiditySeconds(OAuth2Request clientAuth) {
		return new Random(System.currentTimeMillis()).nextInt(maximumAccessTokenValiditySeconds)
				+ minimumAccessTokenValiditySeconds;
	}

	@Override
	public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {

		DefaultOAuth2AccessToken result = new DefaultOAuth2AccessToken(accessToken);
		Map<String, Object> info = new LinkedHashMap<String, Object>(accessToken.getAdditionalInformation());
		String tokenId = result.getValue();
		if (!info.containsKey(TOKEN_ID)) {
			info.put(TOKEN_ID, tokenId);
		} else {
			tokenId = (String) info.get(TOKEN_ID);
		}
		result.setAdditionalInformation(info);
		result.setValue(encode(result, authentication));
		OAuth2RefreshToken refreshToken = result.getRefreshToken();
		if (refreshToken != null) {
			DefaultOAuth2AccessToken encodedRefreshToken = new DefaultOAuth2AccessToken(accessToken);
			encodedRefreshToken.setValue(refreshToken.getValue());
			// Refresh tokens do not expire unless explicitly of the right type

			int validitySeconds = getAccessTokenValiditySeconds(authentication.getOAuth2Request());
			encodedRefreshToken.setExpiration(new Date(System.currentTimeMillis() + (validitySeconds * 1000L)));
			try {
				Map<String, Object> claims = objectMapper
						.parseMap(JwtHelper.decode(refreshToken.getValue()).getClaims());
				if (claims.containsKey(TOKEN_ID)) {
					encodedRefreshToken.setValue(claims.get(TOKEN_ID).toString());
				}
			} catch (IllegalArgumentException e) {
			}
			Map<String, Object> refreshTokenInfo = new LinkedHashMap<String, Object>(
					accessToken.getAdditionalInformation());
			refreshTokenInfo.put(TOKEN_ID, encodedRefreshToken.getValue());
			refreshTokenInfo.put(ACCESS_TOKEN_ID, tokenId);
			encodedRefreshToken.setAdditionalInformation(refreshTokenInfo);

			DefaultOAuth2RefreshToken token = new DefaultOAuth2RefreshToken(
					encode(encodedRefreshToken, authentication));
			if (refreshToken instanceof ExpiringOAuth2RefreshToken) {
				Date expiration = ((ExpiringOAuth2RefreshToken) refreshToken).getExpiration();
				encodedRefreshToken.setExpiration(expiration);
				token = new DefaultExpiringOAuth2RefreshToken(encode(encodedRefreshToken, authentication), expiration);
			}
			result.setRefreshToken(token);
		}
		result.setAdditionalInformation(new LinkedHashMap<String, Object>());
		return result;
	}
}