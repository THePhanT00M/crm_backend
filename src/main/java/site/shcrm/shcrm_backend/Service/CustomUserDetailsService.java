package site.shcrm.shcrm_backend.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import site.shcrm.shcrm_backend.DTO.CustomUserDetails;
import site.shcrm.shcrm_backend.Entity.UserEntity;
import site.shcrm.shcrm_backend.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserEntity userData = userRepository.findById(username);

        if (userData != null) {

            return new CustomUserDetails(userData);
        }

        return null;
    }
}
