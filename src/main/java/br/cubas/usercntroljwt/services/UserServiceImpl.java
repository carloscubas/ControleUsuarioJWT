package br.cubas.usercntroljwt.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.cubas.usercntroljwt.dto.Role;
import br.cubas.usercntroljwt.dto.User;
import br.cubas.usercntroljwt.repository.RoleRepository;
import br.cubas.usercntroljwt.repository.UserRepository;


@Service
public class UserServiceImpl implements UserService {
	
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private RoleRepository roleRepository;
    
   // @Autowired
   // private RoleDao roleRepository;
    
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public void save(User user) {
    	
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        
        Role role = roleRepository.findByRole("ROLE_BASIC");
        user.getRoles().add(role);
        userRepository.save(user);
        
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}