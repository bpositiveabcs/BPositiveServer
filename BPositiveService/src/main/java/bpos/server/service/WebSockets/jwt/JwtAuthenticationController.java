package bpos.server.service.WebSockets.jwt;//package bpos.server.service.WebSockets;
//
//import bpos.server.service.WebSockets.JwtRequest;
//import bpos.server.service.WebSockets.JwtResponse;
//import bpos.server.service.WebSockets.JwtTokenUtil;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.web.bind.annotation.*;
//
//
//@RestController
//@CrossOrigin
//public class JwtAuthenticationController {
//
//    @Autowired
//    private AuthenticationManager authenticationManager;
//
//    @Autowired
//    private JwtTokenUtil jwtTokenUtil;
//
//    @Autowired
//    private UserDetailsService userDetailsService;
//
//    @PostMapping("/authenticate")
//    public JwtResponse createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {
//        authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());
//
//        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
//        final String token = jwtTokenUtil.generateToken(userDetails);
//
//        return new JwtResponse(token);
//    }
//
//    private void authenticate(String username, String password) throws Exception {
//        try {
//            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
//        } catch (UsernameNotFoundException e) {
//            throw new Exception("INVALID_CREDENTIALS", e);
//        }
//    }
//}
