package site.shcrm.shcrm_backend.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import site.shcrm.shcrm_backend.DTO.CustomUserDetails;
import site.shcrm.shcrm_backend.Entity.UserEntity;
import site.shcrm.shcrm_backend.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private static final Logger logger = LoggerFactory.getLogger(CustomUserDetailsService.class);

    @Autowired
    private UserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
        // 로깅: id 값 확인
        logger.info("Attempting to load user with id: {}", id);

        // String 타입의 id로 사용자 검색
        UserEntity userData = userRepository.findById(Integer.parseInt(id));

        // 로깅: 검색된 userData 값 확인
        if (userData != null) {
            logger.info("User found: {}", userData);
            return new CustomUserDetails(userData);
        } else {
            logger.warn("User not found with id: {}", id);
            throw new UsernameNotFoundException("User not found with id: " + id);
        }
    }
}
