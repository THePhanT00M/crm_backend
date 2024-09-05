package site.shcrm.shcrm_backend.Service;

import site.shcrm.shcrm_backend.DTO.JoinDTO;
import site.shcrm.shcrm_backend.Entity.MembersEntity;
import site.shcrm.shcrm_backend.repository.MembersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class JoinService {

    @Autowired
    private MembersRepository membersRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    public void joinProcess(JoinDTO joinDTO) {

        MembersEntity data = new MembersEntity();

        data.setEmployeeId(joinDTO.getEmployeeId());
        data.setFirstName(joinDTO.getFirstName());
        data.setLastName(joinDTO.getLastName());
        data.setPasswordHash(bCryptPasswordEncoder.encode(joinDTO.getPasswordHash()));
        data.setEmail(joinDTO.getEmail());
        data.setHireDate(joinDTO.getHireDate());
        data.setPhoneNumber(joinDTO.getPhoneNumber());
        data.setJobTitle(joinDTO.getJobTitle());


//        if (joinDTO.getRole()==1){
//            data.setRole("ROLE_admin");
//        }
//        else if (joinDTO.getRole()==0){
//            data.setRole("ROLE_user");
//        }


        membersRepository.save(data);
    }
}
