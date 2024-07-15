package site.shcrm.shcrm_backend.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "report_file_table")
public class ReportFileEntity extends TimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long no;

    @Column
    private String originalFileName;

    @Column
    private String storedFileName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "report_no")
    private ReportEntity reportEntity;

    public static ReportFileEntity toReportFileEntity(ReportEntity reportEntity, String originalFileName, String storedFileName) {
        ReportFileEntity reportFileEntity = new ReportFileEntity();
        reportFileEntity.setOriginalFileName(originalFileName);
        reportFileEntity.setStoredFileName(storedFileName);
        reportFileEntity.setReportEntity(reportEntity);
        return reportFileEntity;
    }
}
