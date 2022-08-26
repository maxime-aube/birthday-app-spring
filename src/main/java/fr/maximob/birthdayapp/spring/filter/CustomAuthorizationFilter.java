package fr.maximob.birthdayapp.spring.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import fr.maximob.birthdayapp.spring.security.MyUserPrincipal;
import fr.maximob.birthdayapp.spring.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CustomAuthorizationFilter extends OncePerRequestFilter {

    @Autowired
    private UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        if (request.getServletPath().equals("/login")) {
            filterChain.doFilter(request, response); // On ne filtre pas /login

        } else {
            // On filtre une requête afin de récupérer le token pour identifier l’émetteur.
            // On récupère la ligne "Authorizationé du header et on vérifie si elle respecte bien la convention avec Bearer
            String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                String token = authHeader.substring("Bearer ".length(), authHeader.length());
                // Une fois le token récupéré, on le décrypte pour retrouver le username
                Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
                JWTVerifier jwtVerifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT = jwtVerifier.verify(token);
                String username = decodedJWT.getSubject();
                // Grâce au username, on peut identifier l’utilisateur qui à fait la requête
                MyUserPrincipal myUserPrincipal = (MyUserPrincipal) userService.loadUserByUsername(username);
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(myUserPrincipal, null, myUserPrincipal.getAuthorities());
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                // permet de modifier le contextHolder avec l'utilisateur principal pour qu'il puisse être manipulé dans le controller
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                filterChain.doFilter(request, response);
            }
        }
    }
}
