package site.shcrm.shcrm_backend.repository;

import site.shcrm.shcrm_backend.Entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {

    boolean existsById(String id);

    UserEntity findById(String id);
}
