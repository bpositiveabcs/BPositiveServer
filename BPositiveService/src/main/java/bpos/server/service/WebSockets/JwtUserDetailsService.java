//package bpos.server.service.WebSockets;
//
//import bpos.common.model.Person;
//import bpos.server.repository.Interfaces.PersonRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//import java.util.ArrayList;
//
//
//public class JwtUserDetailsService implements UserDetailsService {
//
//
//    private PersonRepository personRepository;
//
//    public JwtUserDetailsService(PersonRepository personRepository) {
//        this.personRepository = personRepository;
//    }
//
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        Person user = personRepository.findByUsername(username);
//        if (user == null) {
//            throw new UsernameNotFoundException("User not found with username: " + username);
//        }
//        return new org.springframework.security.core.userdetails.User(user.getPersonLogInfo().getUsername(), user.getPersonLogInfo().getPassword(),
//                new ArrayList<>());
//    }
//}
