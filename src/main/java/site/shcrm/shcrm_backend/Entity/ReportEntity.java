package site.shcrm.shcrm_backend.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import site.shcrm.shcrm_backend.DTO.ReportDTO;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Entity
@Table(name = "report")
public class ReportEntity extends TimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reportNo;

    @Column
    private String username;

    @Column(length = 20, nullable = false)
    private String reportWriter;

    @Column
    private String reportTitle;

    @Column(length = 500)
    private String reportContents;

    @OneToMany(mappedBy = "reportEntity", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<ReportFileEntity> reportFileEntityList = new ArrayList<>();

    @OneToMany(mappedBy = "reportEntity", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<CommentEntity> commentEntityList = new ArrayList<>();

    public static ReportEntity toUpdateEntity(ReportDTO reportDTO) {
        ReportEntity reportEntity = new ReportEntity();
        reportEntity.setReportNo(reportDTO.getReportNo());
        reportEntity.setUsername(reportDTO.getUsername()); // Ensure username is also updated if needed
        reportEntity.setReportWriter(reportDTO.getReportWriter());
        reportEntity.setReportTitle(reportDTO.getReportTitle());
        reportEntity.setReportContents(reportDTO.getReportContents());
        return reportEntity;
    }

    public static ReportEntity toSaveFileEntity(ReportDTO reportDTO) {
        ReportEntity reportEntity = new ReportEntity();
        reportEntity.setUsername(reportDTO.getUsername());
        reportEntity.setReportWriter(reportDTO.getReportWriter());
        reportEntity.setReportTitle(reportDTO.getReportTitle());
        reportEntity.setReportContents(reportDTO.getReportContents());
        return reportEntity;
    }
}
