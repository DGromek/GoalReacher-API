package pl.politechnika.goalreacher.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import pl.politechnika.goalreacher.utils.JWTUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class JWTAuthenticationFilter extends BasicAuthenticationFilter {
    public JWTAuthenticationFilter(AuthenticationManager authManager) {
        super(authManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req,
                                    HttpServletResponse res,
                                    FilterChain chain) throws IOException, ServletException {
        String token = req.getHeader("Authorization");

        if (token == null || !token.startsWith("Bearer ")) {
            chain.doFilter(req, res);
            return;
        }

        DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC512(JWTUtils.getSecret().getBytes()))
                .build()
                .verify(token.replace("Bearer ", ""));
        String email = decodedJWT.getSubject();

        if (email != null) {
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(email, null, null);
            String refreshedToken = JWTUtils.getToken(email);

            res.addHeader("Access-Control-Expose-Headers", "Refreshed-token");
            res.addHeader("Refreshed-token", refreshedToken);
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }

        chain.doFilter(req, res);
    }
}
