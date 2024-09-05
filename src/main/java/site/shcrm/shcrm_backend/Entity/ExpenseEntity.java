package site.shcrm.shcrm_backend.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.security.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;

@Setter
@Getter
@Entity
@Table(name = "expenses")
public class ExpenseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int expenseId;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal amount;

    @Column(nullable = false, length = 255)
    private String merchantName;

    @Column(length = 255)
    private String address;

    @Column(nullable = false)
    private Date expenseDate; // 수정된 부분

    @ManyToOne
    @JoinColumn(name = "attachmentId")
    private AttachmentEntity attachment;

    @ManyToOne
    @JoinColumn(name = "policyId")
    private PolicyEntity policyId;

    @ManyToOne
    @JoinColumn(name = "employeeId", nullable = false)
    private MembersEntity employeeId;

    @ManyToOne
    @JoinColumn(name = "categoryId")
    private CategoryEntity categoryId;

    @ManyToOne
    @JoinColumn(name = "reportId")
    private ReportsEntity reportId;

    @Column(nullable = false, length = 1)
    private String reimbursement;

    @Column(nullable = false, length = 1, columnDefinition = "CHAR(1) DEFAULT 'N'")
    private Character isDeleted; // 수정된 부분

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
