package site.shcrm.shcrm_backend.repository;

import site.shcrm.shcrm_backend.Entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {

    boolean existsByid(int id);


    UserEntity findById(int id);
}
