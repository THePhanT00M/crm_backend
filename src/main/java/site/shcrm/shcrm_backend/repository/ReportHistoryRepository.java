package site.shcrm.shcrm_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import site.shcrm.shcrm_backend.Entity.ReportHistoryEntity;

@Repository
public interface ReportHistoryRepository extends JpaRepository<ReportHistoryEntity,Integer> {
}
