package site.shcrm.shcrm_backend.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import site.shcrm.shcrm_backend.Entity.MembersEntity;
import site.shcrm.shcrm_backend.JWT.MembersDetails;
import site.shcrm.shcrm_backend.repository.MembersRepository;

@Service
public class MembersService implements UserDetailsService {

    private final MembersRepository membersRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public MembersService(MembersRepository membersRepository, BCryptPasswordEncoder passwordEncoder) {
        this.membersRepository = membersRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            Integer employeeId = Integer.parseInt(username);
            MembersEntity membersEntity = membersRepository.findByEmployeeId(employeeId)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found with employeeId: " + employeeId));
            return new MembersDetails(membersEntity);
        } catch (NumberFormatException e) {
            throw new UsernameNotFoundException("Invalid employeeId format: " + username, e);
        }
    }
}
