package site.shcrm.shcrm_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import site.shcrm.shcrm_backend.Entity.CommentEntity;
import site.shcrm.shcrm_backend.Entity.ReportEntity;

import java.util.List;


public interface CommentRepository extends JpaRepository<CommentEntity, Long > {
    List<CommentEntity> findByReportEntityOrderByCommentNoDesc(ReportEntity reportEntity);
}
