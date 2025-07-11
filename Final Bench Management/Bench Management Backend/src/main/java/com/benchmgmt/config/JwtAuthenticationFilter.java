package com.benchmgmt.config;

import com.benchmgmt.repository.AdminRepository;
import com.benchmgmt.repository.TrainerRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.util.List;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final AdminRepository adminRepo;
    private final TrainerRepository trainerRepo;

    public JwtAuthenticationFilter(JwtService jwtService,
                                   AdminRepository adminRepo,
                                   TrainerRepository trainerRepo) {
        this.jwtService = jwtService;
        this.adminRepo = adminRepo;
        this.trainerRepo = trainerRepo;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req,
                                    HttpServletResponse res,
                                    FilterChain chain) throws java.io.IOException, jakarta.servlet.ServletException {
        String authHeader = req.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            try {
                var claims = jwtService.parseClaims(token);
                String email = claims.getSubject();
                String role = claims.get("role", String.class);
                Object user = role.equals("ADMIN")
                        ? adminRepo.findByEmail(email).orElse(null)
                        : trainerRepo.findByEmail(email).orElse(null);
                if (user != null) {
                    UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                            user,
                            null,
                            List.of(new SimpleGrantedAuthority("ROLE_" + role))
                    );
                    auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(req));
                    SecurityContextHolder.getContext().setAuthentication(auth);
                }
            } catch (Exception ignored) { }
        }
        chain.doFilter(req, res);
    }
}
