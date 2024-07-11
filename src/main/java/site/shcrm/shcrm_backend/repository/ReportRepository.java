package site.shcrm.shcrm_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import site.shcrm.shcrm_backend.Entity.ReportEntity;


public interface ReportRepository extends JpaRepository <ReportEntity,Long>{
    @Modifying
    @Query(value = "update ReportEntity r set r.reportHits=r.reportHits+1 where r.no=:no")
    void updateHits(@Param("no") Long no);
}
