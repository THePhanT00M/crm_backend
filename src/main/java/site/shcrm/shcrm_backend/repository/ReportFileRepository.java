package site.shcrm.shcrm_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.shcrm.shcrm_backend.Entity.ReportEntity;
import site.shcrm.shcrm_backend.Entity.ReportFileEntity;

import java.util.List;

public interface ReportFileRepository extends JpaRepository<ReportFileEntity,Long> {
    List<ReportFileEntity> findByReportEntity(ReportEntity reportEntity);
}
