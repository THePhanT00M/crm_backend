package site.shcrm.shcrm_backend.Service;

import site.shcrm.shcrm_backend.DTO.JoinDTO;
import site.shcrm.shcrm_backend.Entity.UserEntity;
import site.shcrm.shcrm_backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class JoinService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    public void joinProcess(JoinDTO joinDTO) {


        //db에 이미 동일한 username을 가진 회원이 존재하는지?
        boolean isUser = userRepository.existsByUsername(joinDTO.getUsername());
        if (isUser) {
            return;
        }


        UserEntity data = new UserEntity();

        data.setUsername(joinDTO.getUsername());
        data.setPassword(bCryptPasswordEncoder.encode(joinDTO.getPassword()));
        data.setEmail(joinDTO.getEmail());
        data.setDepartment(joinDTO.getDepartment());
        if (joinDTO.getRole()==1){
            data.setRole("ROLE_admin");
        }
        else if (joinDTO.getRole()==0){
            data.setRole("ROLE_user");
        }


        userRepository.save(data);
    }
}
