package com.soolo.soolochat.config;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.soolo.soolochat.security.jwt.JwtUtil;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity // 스프링 Security 지원을 가능하게 함
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(securedEnabled = true)
public class WebSecurityConfig {
	private static final String[] AUTH_WHITELIST = {
		"/api/user/**",
		"/member/authenticate",
		"/swagger-ui.html",
		"/swagger-ui/**",
		"/v3/api-docs/**",
		"/swagger-resources/**",
		"/api-docs",
		"/ws-stomp/**", // 웹소켓 경로 추가
	};
	private final JwtUtil jwtUtil;
	@Bean
	public WebSecurityCustomizer webSecurityCustomizer() {
		return (web) -> web.ignoring()
			.requestMatchers(PathRequest.toStaticResources().atCommonLocations());
	}
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		// CSRF 설정
		http.csrf()
			.ignoringAntMatchers("/ws-stomp/**") // 웹소켓 경로에 대한 CSRF 비활성화
			.ignoringAntMatchers("/ws-stomp/**")
			.disable();
		http.cors();
		// 기본 설정인 Session 방식은 사용하지 않고 JWT 방식을 사용하기 위한 설정
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		http.authorizeHttpRequests(authorize -> authorize
				.shouldFilterAllDispatcherTypes(false)
				.antMatchers(AUTH_WHITELIST)
				.permitAll()
				.antMatchers("/chat/**").permitAll()
				.antMatchers("/test").permitAll()
				.anyRequest()
				.authenticated());// 그외의 요청들은 모두 인가 받아야 한다.

		//SockJS를 위해
		http.headers().frameOptions().sameOrigin();
		return http.build();
	}

	// cors 에러를 위한 체크
	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration config = new CorsConfiguration();

		config.addAllowedOrigin("http://localhost:3000");
		config.addAllowedOrigin("http://localhost:8080");
		config.addAllowedOrigin("http://localhost:8081");
		config.addAllowedOrigin("http://3.34.14.45:8080");
		config.addAllowedOrigin("http://im-soolo.shop");
		config.addAllowedOrigin("https://im-soolo.shop");
		config.addAllowedOrigin("http://www.im-soolo.shop");
		config.addAllowedOrigin("https://www.im-soolo.shop");
		config.addAllowedOrigin("https://soolo-fe.vercel.app");
		config.addExposedHeader(JwtUtil.AUTHORIZATION_HEADER);
		config.addExposedHeader(JwtUtil.ACCESS_KEY);
		config.addExposedHeader(JwtUtil.REFRESH_KEY);
		config.addAllowedMethod("*");
		config.addAllowedHeader("*");
		config.setAllowCredentials(true);
		config.validateAllowCredentials();

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", config);

		return source;
	}

	// 비밀번호 암호화
	@Bean // 비밀번호 암호화 기능 등록
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
