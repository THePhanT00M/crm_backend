package site.shcrm.shcrm_backend.DTO;

import lombok.*;
import site.shcrm.shcrm_backend.Entity.ReportEntity;
import site.shcrm.shcrm_backend.Entity.ReportFileEntity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ReportDTO {

    private Long reportNo;
    private String username;
    private String reportWriter;
    private String reportTitle;
    private String reportContents;
    private LocalDateTime reportCreatedTime;
    private LocalDateTime reportUpdatedTime;
    List<CommentDTO> comments;

    private List<ReportFileDTO> reportFileDTOList = new ArrayList<>(); // 수정된 필드

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ReportFileDTO {
        private Long reportFileNo;
        private String originalFileName;
        private String storedFileName;
        private String fileUrl;
    }

    // 새로운 메서드 추가
    public static ReportDTO toreportfileDTO(ReportEntity reportEntity, String baseUrl) {
        ReportDTO reportDTO = new ReportDTO();
        reportDTO.setReportNo(reportEntity.getReportNo());
        reportDTO.setUsername(reportEntity.getUsername());
        reportDTO.setReportWriter(reportEntity.getReportWriter());
        reportDTO.setReportTitle(reportEntity.getReportTitle());
        reportDTO.setReportContents(reportEntity.getReportContents());
        reportDTO.setReportCreatedTime(reportEntity.getCreatedTime());
        reportDTO.setReportUpdatedTime(reportEntity.getUpdatedTime());

        List<ReportFileDTO> fileDTOList = new ArrayList<>();
        if (reportEntity.getReportFileEntityList() != null) {
            for (ReportFileEntity reportFileEntity : reportEntity.getReportFileEntityList()) {
                ReportFileDTO fileDTO = new ReportFileDTO();
                fileDTO.setReportFileNo(reportFileEntity.getReportFileNo());
                fileDTO.setOriginalFileName(reportFileEntity.getOriginalFileName());
                fileDTO.setStoredFileName(reportFileEntity.getStoredFileName());
                fileDTO.setFileUrl(baseUrl + "/files/download/" + reportFileEntity.getStoredFileName());
                fileDTOList.add(fileDTO);
            }
        }

        reportDTO.setReportFileDTOList(fileDTOList);
        return reportDTO;
    }
}