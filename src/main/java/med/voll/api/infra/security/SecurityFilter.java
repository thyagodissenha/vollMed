package med.voll.api.infra.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import med.voll.api.domain.usuario.UsuarioRepository;

@Component
public class SecurityFilter extends OncePerRequestFilter{
	@Autowired
	private TokenService tokenService;
	@Autowired
	UsuarioRepository repository;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		String tokenJWT = recuperarToken(request);
		if(tokenJWT != null) {
			String subject = tokenService.getSubject(tokenJWT);
			UserDetails usuario = repository.findByLogin(subject);
			
			UsernamePasswordAuthenticationToken autentication = new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());
			SecurityContextHolder.getContext().setAuthentication(autentication);
		}
		
		filterChain.doFilter(request, response);		
	}

	private String recuperarToken(HttpServletRequest request) {
		String autorizationHeader = request.getHeader("Authorization");
		if(autorizationHeader != null)
			return autorizationHeader.replace("Bearer ", "");
		return null;
	}
}
