package com.ftn.security;



import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;

public class CheckTokenFilter extends UsernamePasswordAuthenticationFilter {
	
	@Autowired
	private TokenUtils tokenUtils;

	public CheckTokenFilter() {
		
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
	    
		String token = httpRequest.getHeader("token");
		System.out.println("ovo je token " + token);
	
		if(token != null) {

			token = token.substring(1,token.length()-1).toString();
			
			List<SimpleGrantedAuthority> authorities = null;
			try {
				authorities =  (List<SimpleGrantedAuthority>) tokenUtils.getUserSecurity(token).getAuthorities();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			
			}
			String username = null;
			
			try {
				username = tokenUtils.getUserSecurity(token).getUsername();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			
			UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(username,  null, authorities);
			
			auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpRequest));
			SecurityContextHolder.getContext().setAuthentication(auth);
		} else {
			System.out.println("Token je null");
		}
		
		chain.doFilter(request, response);
	}
	
}
