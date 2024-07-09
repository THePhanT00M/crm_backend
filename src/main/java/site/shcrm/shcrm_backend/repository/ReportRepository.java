package site.shcrm.shcrm_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.shcrm.shcrm_backend.Entity.ReportEntity;


public interface ReportRepository extends JpaRepository <ReportEntity,Long>{

}
