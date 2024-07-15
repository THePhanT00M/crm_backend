package site.shcrm.shcrm_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.shcrm.shcrm_backend.Entity.ReportFileEntity;

public interface ReportFileRepository extends JpaRepository<ReportFileEntity,Long> {
}
