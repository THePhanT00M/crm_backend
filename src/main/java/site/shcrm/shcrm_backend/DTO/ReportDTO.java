package site.shcrm.shcrm_backend.DTO;

import lombok.*;
import site.shcrm.shcrm_backend.Entity.ReportEntity;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ReportDTO {

    private Long no;

    private String id;
    private String reportWriter;
    private String reportTitle;
    private String reportContents;
    private int reportHits;
    private LocalDateTime reportCreatedTime;
    private LocalDateTime reportUpdatedTime;

    public static ReportDTO toreportDTO(ReportEntity reportEntity) {
        ReportDTO reportDTO = new ReportDTO();
        reportDTO.setId(reportEntity.getId());
        reportDTO.setNo(reportEntity.getNo());
        reportDTO.setReportWriter(reportEntity.getReportWriter());
        reportDTO.setReportTitle(reportEntity.getReportTitle());
        reportDTO.setReportContents(reportEntity.getReportContents());
        reportDTO.setReportUpdatedTime(reportEntity.getReportUpdatedTime());
        reportDTO.setReportCreatedTime(reportEntity.getReportCreatedTime());
        reportDTO.setReportHits(reportEntity.getReportHits());
        return reportDTO;
    }
}
