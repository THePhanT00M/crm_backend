package site.shcrm.shcrm_backend.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import site.shcrm.shcrm_backend.DTO.ReportsDTO;

import java.security.Timestamp;
import java.time.LocalDateTime;

@Setter
@Getter
@Entity
@Table(name = "reports")
public class ReportsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int reportId;

    @Column(nullable = false, length = 255)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(nullable = false, length = 50)
    private String status;

    @ManyToOne
    @JoinColumn(name = "policyId")
    private PolicyEntity policyId;

    @ManyToOne
    @JoinColumn(name = "employeeId", nullable = false)
    private MembersEntity employeeId;

    @ManyToOne
    @JoinColumn(name = "categoryId")
    private CategoryEntity categoryId;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    // 엔티티가 영속화되기 전에 실행됨
    @PrePersist
    protected void onCreate() {
        LocalDateTime now = LocalDateTime.now();
        this.createdAt = now; // 엔티티가 처음 저장될 때 현재 시간으로 설정
        this.updatedAt = now; // 엔티티가 처음 저장될 때 현재 시간으로 설정
    }

    // 엔티티가 업데이트되기 전에 실행됨
    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now(); // 엔티티가 업데이트될 때 현재 시간으로 갱신
    }
}