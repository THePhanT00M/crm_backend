package site.shcrm.shcrm_backend.DTO;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;
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

    private Long no;

    private String username;
    private String reportWriter;
    private String reportTitle;
    private String reportContents;
    private int reportHits;
    private LocalDateTime reportCreatedTime;
    private LocalDateTime reportUpdatedTime;

    private List<MultipartFile> reportFile; // save.html -> Controller 파일 담는 용도
    private List<String> originalFileName; // 원본 파일 이름
    private List<String> storedFileName; // 서버 저장용 파일 이름
    private int fileAttached; // 파일 첨부 여부(첨부 1, 미첨부 0)

    public static ReportDTO toreportDTO(ReportEntity reportEntity) {
        ReportDTO reportDTO = new ReportDTO();
        reportDTO.setUsername(reportEntity.getUsername());
        reportDTO.setNo(reportEntity.getNo());
        reportDTO.setReportWriter(reportEntity.getReportWriter());
        reportDTO.setReportTitle(reportEntity.getReportTitle());
        reportDTO.setReportContents(reportEntity.getReportContents());
        reportDTO.setReportUpdatedTime(reportEntity.getUpdatedTime());
        reportDTO.setReportCreatedTime(reportEntity.getCreatedTime());
        reportDTO.setReportHits(reportEntity.getReportHits());
        if (reportEntity.getFileAttached() == 0) {
            reportDTO.setFileAttached(0); // 0
        } else {
            List<String> originalFileNameList = new ArrayList<>();
            List<String> storedFileNameList = new ArrayList<>();
            reportDTO.setFileAttached(1); // 1
            for(ReportFileEntity reportFileEntity: reportEntity.getReportFileEntityList()) {
               originalFileNameList.add(reportFileEntity.getOriginalFileName());
               storedFileNameList.add(reportFileEntity.getStoredFileName());
            }
            reportDTO.setOriginalFileName(originalFileNameList);
            reportDTO.setStoredFileName(storedFileNameList);
        }
        return reportDTO;
    }
}
