package site.shcrm.shcrm_backend.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.security.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Setter
@Getter
@Entity
@Table(name = "policies")
public class PolicyEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int policyId;

    @Column(nullable = false, length = 255)
    private String policyName;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private LocalDate effectiveDate;

    private LocalDate expirationDate;

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

    public int getId() {
        return policyId;
    }
}