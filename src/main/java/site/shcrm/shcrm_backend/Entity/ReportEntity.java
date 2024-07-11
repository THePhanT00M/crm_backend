package site.shcrm.shcrm_backend.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import site.shcrm.shcrm_backend.DTO.ReportDTO;

import java.time.LocalDateTime;

@Setter
@Getter
@Entity
@Table(name = "report")
public class ReportEntity extends TimeEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long no;

    @Column
    private String id;

    @Column(length = 20, nullable = false)
    private String reportWriter;

    @Column
    private String reportTitle;

    @Column(length = 50)
    private String reportContents;
    @Column
    private int reportHits;


    public static ReportEntity toSaveEntity(ReportDTO reportDTO){
        ReportEntity reportEntity = new ReportEntity();
        reportEntity.setReportWriter(reportDTO.getReportWriter());
        reportEntity.setReportTitle(reportDTO.getReportTitle());
        reportEntity.setReportContents(reportDTO.getReportContents());
        reportEntity.setReportHits(0);
        return reportEntity;
    }
}
