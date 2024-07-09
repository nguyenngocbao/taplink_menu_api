package taplink.network.menu.api.configs;


import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import taplink.network.menu.api.commons.utils.JwtUtils;
import taplink.network.menu.api.dtos.response.ApiErrorResponse;
import taplink.network.menu.api.exceptions.AccessDeniedException;
import taplink.network.menu.api.models.User;
import taplink.network.menu.api.repositories.UserRepository;
import taplink.network.menu.api.security.OtpAuthenticationToken;

import java.io.IOException;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final ObjectMapper objectMapper;
    private final UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            String authHeader = request.getHeader("Authorization");

            String token = null;
            String username = null;
            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                token = authHeader.substring(7);
                username = JwtUtils.extractUsername(token);
            }

            if (token == null) {
                filterChain.doFilter(request, response);
                return;
            }

            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                Optional<User> userOpt = userRepository.findByUsernameOrEmail(username, username);
                if (userOpt.isEmpty()) {
                    throw new UsernameNotFoundException("User not found with username or email: " + username);
                }
                User user = userOpt.get();
                if (JwtUtils.validateToken(token, user)) {
                    OtpAuthenticationToken otpAuthenticationToken = new OtpAuthenticationToken(user.getUsername(), null, user.getAuthorities());
                    otpAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(otpAuthenticationToken);
                }
            }

            filterChain.doFilter(request, response);
        } catch (AccessDeniedException e) {
            ApiErrorResponse errorResponse = new ApiErrorResponse(HttpServletResponse.SC_UNAUTHORIZED, e.getMessage());
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write(toJson(errorResponse));
        }
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return request.getServletPath().startsWith("/api/v1/auth");
    }

    private String toJson(ApiErrorResponse response) {
        try {
            return objectMapper.writeValueAsString(response);
        } catch (Exception e) {
            return "";
        }
    }
}
