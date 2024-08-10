package site.shcrm.shcrm_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import site.shcrm.shcrm_backend.Entity.ReportEntity;
import site.shcrm.shcrm_backend.Entity.UserEntity;


public interface ReportRepository extends JpaRepository <ReportEntity,Long>{

}
