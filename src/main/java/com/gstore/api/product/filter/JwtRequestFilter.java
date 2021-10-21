package com.gstore.api.product.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.gstore.api.product.controller.CustomerExceptionHandler;
import com.gstore.api.product.util.JwtUtil;

import io.jsonwebtoken.SignatureException;



@Component
public class JwtRequestFilter extends OncePerRequestFilter  {
	
	
	
	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private JwtUtil jwtUtils;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException, SignatureException {
		try {
			String jwt = jwtUtils.getJwt(request);
			String username = jwtUtils.getUsername(jwt);

			if (null != username && SecurityContextHolder.getContext().getAuthentication() == null) {
				UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
				if (jwtUtils.validateToken(jwt, userDetails)) {
					UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
							userDetails, null, userDetails.getAuthorities());
					usernamePasswordAuthenticationToken
							.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
					SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
				}

			}
			
		 }
			catch(Exception ex)
			{
				CustomerExceptionHandler cus = new CustomerExceptionHandler();
				cus.handleRequestException1(ex);
			}

		//filterChain.doFilter(request, response);
			
		
		
	} 

}
