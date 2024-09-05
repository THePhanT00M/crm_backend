package site.shcrm.shcrm_backend.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.security.Timestamp;

@Setter
@Getter
@Entity
@Table(name = "report_history")
public class ReportHistoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int historyId;

    @ManyToOne
    @JoinColumn(name = "reportId", nullable = false)
    private ReportsEntity reportId;

    @ManyToOne
    @JoinColumn(name = "employeeId", nullable = false)
    private MembersEntity employeeId;

    @Column(nullable = false, insertable = false,
            columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp modifiedAt;

    @Column(columnDefinition = "TEXT")
    private String changeSummary;

    @Column(columnDefinition = "TEXT")
    private String previousContent;

    @Column(columnDefinition = "TEXT")
    private String newContent;

    @Column(length = 50)
    private String statusBefore;

    @Column(length = 50)
    private String statusAfter;

    @ManyToOne
    @JoinColumn(name = "previousPolicyId")
    private PolicyEntity previousPolicyId;

    @ManyToOne
    @JoinColumn(name = "newPolicyId")
    private PolicyEntity newPolicyId;

    @ManyToOne
    @JoinColumn(name = "previousCategoryId")
    private CategoryEntity previousCategoryId;

    @ManyToOne
    @JoinColumn(name = "newCategoryId")
    private CategoryEntity newCategoryId;
}
