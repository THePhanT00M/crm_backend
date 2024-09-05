package site.shcrm.shcrm_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import site.shcrm.shcrm_backend.Entity.MembersEntity;
import site.shcrm.shcrm_backend.Entity.ReportsEntity;

@Repository
public interface ReportRepository extends JpaRepository<ReportsEntity, Integer> {
    // MembersEntity 객체를 이용한 메소드 정의
    boolean existsByEmployeeId(MembersEntity employeeId);
}
