package com.example.restapipractice.base.security.entryPoint;

import static org.springframework.http.MediaType.*;

import java.io.IOException;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.example.restapipractice.base.rsData.RsData;
import com.example.restapipractice.util.Ut;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class ApiAuthenticationEntryPoint implements AuthenticationEntryPoint {
	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
		AuthenticationException authException) throws IOException, ServletException {
		RsData rs = RsData.of("F-AccessDenied", "인증실패", null);

		response.setCharacterEncoding("UTF-8");
		response.setContentType(APPLICATION_JSON_VALUE);
		response.setStatus(403);
		response.getWriter().append(Ut.json.toStr(rs));
	}
}
