package site.shcrm.shcrm_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import site.shcrm.shcrm_backend.Entity.AttachmentEntity;


@Repository
public interface AttachmentRepository extends JpaRepository<AttachmentEntity,Integer> {
}
