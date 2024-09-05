package site.shcrm.shcrm_backend.repository;

import site.shcrm.shcrm_backend.Entity.MembersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface MembersRepository extends JpaRepository<MembersEntity, Integer> {
    Optional<MembersEntity> findByEmployeeId(Integer employeeId);
}
