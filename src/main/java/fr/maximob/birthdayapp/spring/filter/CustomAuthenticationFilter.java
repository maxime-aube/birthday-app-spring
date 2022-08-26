package fr.maximob.birthdayapp.spring.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import fr.maximob.birthdayapp.spring.security.MyUserPrincipal;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private AuthenticationManager authenticationManager;

    public CustomAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
//        System.out.println("username = " + username);
//        System.out.println("password = " + password);
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, password);
        return authenticationManager.authenticate(authToken);
    }

    @Override
    protected void successfulAuthentication(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain,
            Authentication authResult
    ) throws IOException, ServletException {

        // Récupération du user authentifié
        MyUserPrincipal myUserPrincipal = (MyUserPrincipal) authResult.getPrincipal();

        // Utilisation d’un algorithme pour créer le token
        Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());

        // Création du token depuis le username avec une date d’expiration (ici 1h)
        String access_token = JWT.create()
                .withSubject(myUserPrincipal.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + 60 * 60 * 1000))
                .withIssuer(request.getRequestURL().toString())
                .sign(algorithm);

        // On place le token dans le header de la réponse à l'authentification
        response.setHeader("token", access_token);
    }
}
