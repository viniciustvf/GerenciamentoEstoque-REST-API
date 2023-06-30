package com.trier.gerenciamentoestoque.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.trier.gerenciamentoestoque.config.jwt.JwtUtil;
import com.trier.gerenciamentoestoque.config.jwt.LoginDTO;

@RestController
@RequestMapping(value = "/auth")
public class JwtResource {

	@Autowired
	private JwtUtil jwtUtil;

	@Autowired
	private AuthenticationManager auth;

	@PostMapping("/token")
	public String authenticateAndGetToken(@RequestBody LoginDTO loginDto) { 
		Authentication authentication = auth
				.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword()));
		if (authentication.isAuthenticated()) {
			return jwtUtil.generateToken(loginDto.getEmail());
		} else {
			throw new UsernameNotFoundException("Usuário inválido"); 
		}
	}
}
