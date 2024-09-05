package site.shcrm.shcrm_backend.Service;

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

import javax.naming.AuthenticationException;

@Service
public class MembersService implements UserDetailsService {

    private final MembersRepository membersRepository;
    private final PasswordEncoder passwordEncoder;

    public MembersService(MembersRepository membersRepository, PasswordEncoder passwordEncoder) {
        this.membersRepository = membersRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        MembersEntity membersEntity = membersRepository.findByEmployeeId(Integer.parseInt(username))
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return new MembersDetails(membersEntity, (BCryptPasswordEncoder) passwordEncoder);
    }

    public Authentication authenticate(String employeeId, String rawPassword) throws AuthenticationException {
        UserDetails userDetails = loadUserByUsername(employeeId);
        if (!passwordEncoder.matches(rawPassword, userDetails.getPassword())) {
            throw new BadCredentialsException("Invalid credentials");
        }

        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }
}