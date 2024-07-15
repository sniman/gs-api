
/**
 * 
 * @author norirman
 * @desc -
 * © 2024 snoriman@gmail.com
 * Zurich POC for insurance open api
 */
package com.gs.api.controller;

import java.text.SimpleDateFormat;
import java.util.Objects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.gs.api.db.entity.AuthUser;
import com.gs.api.db.repositories.AuthUserRepository;
import com.gs.api.security.JwtTokenUtil;
import com.gs.api.util.CustomError;
import com.gs.api.vo.request.AuthenticationRequest;
import com.gs.api.vo.request.RequestTokenDetail;
import com.gs.api.vo.response.Response;
import com.gs.api.vo.response.ResponseTokenDetail;

import io.swagger.annotations.Api;

@RestController
@Api(value = "JWT ", description = "Generate jwt ")
@CrossOrigin
@RequestMapping("/api/v1")
public class JwtAuthenticationController {
	private static Logger logger = LogManager.getLogger (JwtAuthenticationController.class);

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private UserDetailsService jwtInMemoryUserDetailsService;
	
	@Autowired
	private AuthUserRepository userRepository;

	@RequestMapping(value = "/authenticate", method = RequestMethod.POST)
	public ResponseEntity<?> generateAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest)
			throws Exception {
		

		AuthUser usr= userRepository.findByUsername(authenticationRequest.getUsername());
		if(usr==null) {
			usr=new AuthUser();
			usr.setUsername("");
			usr.setPassword("");
		}
		logger.info("authenticating for {}"+usr.toString());
		authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());
  
		
		final UserDetails userDetails = jwtInMemoryUserDetailsService
				.loadUserByUsername(authenticationRequest.getUsername());

		final String token = jwtTokenUtil.generateToken(userDetails);
		
		//saved created token in db
		logger.info("Token Generated :{}",token);
		logger.info("Token Issued date :{}",jwtTokenUtil.getIssuedAtDateFromToken(token));
		logger.info("Token Expired date: {}",jwtTokenUtil.getExpirationDateFromToken(token));
		
		
		usr.setToken(token);
		usr.setIssueDate(jwtTokenUtil.getIssuedAtDateFromToken(token));
		usr.setExpiredDate(jwtTokenUtil.getExpirationDateFromToken(token));
		usr.setCreateDate(jwtTokenUtil.getIssuedAtDateFromToken(token));
		usr.setUpdateDate(jwtTokenUtil.getIssuedAtDateFromToken(token));
		userRepository.save(usr);
		

		return ResponseEntity.ok(new Response(usr));
	}

	private void authenticate(String username, String password) throws Exception {
		Objects.requireNonNull(username);
		Objects.requireNonNull(password);
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (DisabledException e) {
			throw new Exception("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			throw new Exception("INVALID_CREDENTIALS", e);
		}
	}
	
	@RequestMapping(value = "/token", method = RequestMethod.POST)
	public ResponseEntity<?> getTokenDetail(@RequestBody RequestTokenDetail request) {
		
		logger.info("Retriving token :{}",request.getToken());
		AuthUser usr= userRepository.findByToken(request.getToken());
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd hh:mm:ss");  

		if (usr == null) {
			//logger.error("User with token {} not found.", request.getToken().toString());
			return new ResponseEntity(new CustomError("User with token " + request.getToken() + " not found"), HttpStatus.NOT_FOUND);
		}
		ResponseTokenDetail response = new ResponseTokenDetail();
		String strexpiredDate = formatter.format(usr.getExpiredDate());  
		String strissuedDate = formatter.format(usr.getIssueDate());  
		response.setToken(usr.getToken());
		response.setExpiredDate(strexpiredDate);
		response.setIssueDate(strissuedDate);
		response.setUsername(usr.getUsername());
		
		    
		return new ResponseEntity<ResponseTokenDetail>(response, HttpStatus.OK);
	}
}
