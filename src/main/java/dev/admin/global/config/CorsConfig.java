package dev.admin.global.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {

	private static final String SERVER_URL = "https://admin.tokkit.site";
	private static final String USER_SERVER_URL = "https://www.tokkit.site";
	private static final String API_SERVER_URL = "https://api.tokkit.site";
	private static final String FRONT_LOCALHOST_URL = "http://localhost:3000";
	private static final String SERVER_LOCALHOST_URL = "http://localhost:8080";

	@Bean
	public CorsFilter corsFilter() {
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

		CorsConfiguration config = new CorsConfiguration();
		config.setAllowCredentials(true);
		config.addAllowedOrigin(SERVER_URL);
		config.addAllowedOrigin(USER_SERVER_URL);
		config.addAllowedOrigin(FRONT_LOCALHOST_URL);
		config.addAllowedOrigin(SERVER_LOCALHOST_URL);
		config.addAllowedOrigin(API_SERVER_URL);
		config.addAllowedHeader("*");
		config.addAllowedMethod("*");
		config.setExposedHeaders(List.of(
			"accessToken",
			"Authorization",
			"Authorization-refresh"
		));
		config.addExposedHeader("Set-Cookie");

		source.registerCorsConfiguration("/**", config);
		return new CorsFilter(source);
	}
}

