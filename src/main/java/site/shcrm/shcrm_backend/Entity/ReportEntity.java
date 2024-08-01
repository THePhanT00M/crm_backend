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
public class ReportEntity extends TimeEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long no;

    @Column
    private String username;

    @Column(length = 20, nullable = false)
    private String reportWriter;

    @Column
    private String reportTitle;

    @Column(length = 500)
    private String reportContents;

    @Column
    private int reportHits;

    @Column
    private int fileAttached;

    @OneToMany(mappedBy = "reportEntity", cascade = CascadeType.REMOVE, orphanRemoval = true,fetch = FetchType.LAZY)
    private List<ReportFileEntity> reportFileEntityList = new ArrayList<>();


    public static ReportEntity toSaveEntity(ReportDTO reportDTO){
        ReportEntity reportEntity = new ReportEntity();
        reportEntity.setReportWriter(reportDTO.getReportWriter());
        reportEntity.setReportTitle(reportDTO.getReportTitle());
        reportEntity.setReportContents(reportDTO.getReportContents());
        reportEntity.setReportHits(0);
        reportEntity.setFileAttached(0);
        return reportEntity;
    }

    public static ReportEntity toUpdateEntity(ReportDTO reportDTO) {
        ReportEntity reportEntity = new ReportEntity();
        reportEntity.setNo(reportDTO.getNo());
        reportEntity.setReportWriter(reportDTO.getReportWriter());
        reportEntity.setReportTitle(reportDTO.getReportTitle());
        reportEntity.setReportContents(reportDTO.getReportContents());
        reportEntity.setReportHits(reportDTO.getReportHits());
        return reportEntity;
    }

    public static ReportEntity toSaveFileEntity(ReportDTO reportDTO) {
        ReportEntity reportEntity = new ReportEntity();
        reportEntity.setReportWriter(reportDTO.getReportWriter());
        reportEntity.setReportTitle(reportDTO.getReportTitle());
        reportEntity.setReportContents(reportDTO.getReportContents());
        reportEntity.setReportHits(0);
        reportEntity.setFileAttached(1);
        return reportEntity;
    }
}
