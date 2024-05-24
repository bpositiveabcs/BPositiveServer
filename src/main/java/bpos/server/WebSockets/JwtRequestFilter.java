package bpos.server.WebSockets;//package bpos.server.service.WebSockets;
//
//import bpos.server.service.WebSockets.JwtTokenUtil;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
//import org.springframework.stereotype.Component;
//import org.springframework.web.filter.OncePerRequestFilter;
//import io.jsonwebtoken.ExpiredJwtException;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import java.io.IOException;
//
//
//public class JwtRequestFilter extends OncePerRequestFilter {
//
//
//    private UserDetailsService jwtUserDetailsService;
//
//
//    private JwtTokenUtil jwtTokenUtil;
//
//    public JwtRequestFilter(UserDetailsService jwtUserDetailsService, JwtTokenUtil jwtTokenUtil) {
//        this.jwtUserDetailsService = jwtUserDetailsService;
//        this.jwtTokenUtil = jwtTokenUtil;
//    }
//
//    private static final Logger logger = LoggerFactory.getLogger(JwtRequestFilter.class);
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
//            throws ServletException, IOException {
//
//        final String requestTokenHeader = request.getHeader("Authorization");
//
//        String username = null;
//        String jwtToken = null;
//
//        // JWT Token is in the form "Bearer token". Remove Bearer word and get only the Token
//        if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
//            jwtToken = requestTokenHeader.substring(7);
//            try {
//                username = jwtTokenUtil.getUsernameFromToken(jwtToken);
//            } catch (IllegalArgumentException e) {
//                logger.error("Unable to get JWT Token", e);
//            } catch (ExpiredJwtException e) {
//                logger.warn("JWT Token has expired", e);
//            }
//        } else {
//            logger.warn("JWT Token does not begin with Bearer String");
//        }
//
//
//        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
//            UserDetails userDetails = this.jwtUserDetailsService.loadUserByUsername(username);
//
//            // if token is valid configure Spring Security to manually set authentication
//            if (jwtTokenUtil.validateToken(jwtToken, userDetails)) {
//                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
//                        userDetails, null, userDetails.getAuthorities());
//                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//                // After setting the Authentication in the context, we specify that the current user is authenticated.
//                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
//            }
//        }
//        chain.doFilter(request, response);
//    }
//}
