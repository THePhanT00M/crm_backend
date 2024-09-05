package site.shcrm.shcrm_backend.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.security.Timestamp;
import java.time.LocalDateTime;


@Entity
@Table(name = "attachments")
@Getter
@Setter
public class AttachmentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int attachmentId;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private RelatedType relatedType; // Enum 필드

    @Column(nullable = false)
    private int relatedId;

    @Column(nullable = false, length = 255)
    private String originalName;

    @Column(nullable = false, length = 255)
    private String fileUrl;

    @Column(nullable = false, updatable = false)
    private LocalDateTime uploadedAt;

    @PrePersist
    protected void onCreate() {
        this.uploadedAt = LocalDateTime.now();
    }

    public enum RelatedType {
        REPORT, EXPENSE
    }
}
