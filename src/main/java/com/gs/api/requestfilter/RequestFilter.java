
/**
 * 
 * @author norirman
 * @desc -
 * © 2024 snoriman@gmail.com
 * Zurich POC for insurance open api
 */
package com.gs.api.requestfilter;

import java.io.IOException;
import java.util.Enumeration;
import java.util.Map;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import com.gs.api.security.JwtTokenUtil;
import com.gs.api.service.AuthenticationService;
import io.jsonwebtoken.ExpiredJwtException;

@Component
public class RequestFilter extends OncePerRequestFilter {

	private static Logger logger = LogManager.getLogger (RequestFilter.class);
	
	@Autowired
	private AuthenticationService jwtUserDetailsService;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws ServletException, IOException {

		final String requestTokenHeader = request.getHeader("Authorization");
		requestLogger(request) ;
   
		String username = null;
		String jwtToken = null;

		logger.info("Token Header :"+requestTokenHeader);
		if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
			jwtToken = requestTokenHeader.substring(7);
			try {
				username = jwtTokenUtil.getUsernameFromToken(jwtToken);
			} catch (IllegalArgumentException e) {
				logger.info("Unable to get JWT Token");
			} catch (ExpiredJwtException e) {
				logger.info("JWT Token has expired");
			}
		} else {
			logger.warn("JWT Token does not begin with Bearer String");
		}

		//validate token
		if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

			UserDetails userDetails = this.jwtUserDetailsService.loadUserByUsername(username);


			if (jwtTokenUtil.validateToken(jwtToken, userDetails)) {

				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
						userDetails, null, userDetails.getAuthorities());
				usernamePasswordAuthenticationToken
						.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
			}
		}
		chain.doFilter(request, response);
	}
	
	private void requestLogger(HttpServletRequest request) {
		  // Create a StringBuilder to hold the request information
        StringBuilder requestInfo = new StringBuilder();
       
        // Append request method and URL
        requestInfo.append("Request Method: ").append(request.getMethod()).append("\n");
        requestInfo.append("Request URL: ").append(request.getRequestURL()).append("\n");

        // Append request parameters
        requestInfo.append("Request Parameters:\n");
        Map<String, String[]> parameterMap = request.getParameterMap();
        for (Map.Entry<String, String[]> entry : parameterMap.entrySet()) {
            requestInfo.append(entry.getKey()).append("=");
            String[] values = entry.getValue();
            for (String value : values) {
                requestInfo.append(value).append(",");
            }
            requestInfo.setLength(requestInfo.length() - 1); // Remove trailing comma
            requestInfo.append("\n");
        }

        // Append request headers
        requestInfo.append("Request Headers:\n");
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            Enumeration<String> headers = request.getHeaders(headerName);
            while (headers.hasMoreElements()) {
                String headerValue = headers.nextElement();
                requestInfo.append(headerName).append(": ").append(headerValue).append("\n");
            }
        }
        
        // Print the request information
        logger.info(requestInfo.toString());

	}

}
