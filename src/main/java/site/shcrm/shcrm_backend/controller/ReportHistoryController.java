package site.shcrm.shcrm_backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import site.shcrm.shcrm_backend.DTO.ReportHistoryDTO;
import site.shcrm.shcrm_backend.Entity.ReportHistoryEntity;
import site.shcrm.shcrm_backend.Service.ReportHistoryService;

@RestController
@RequestMapping("/report-history")
public class ReportHistoryController {


    @Autowired
    private ReportHistoryService reportHistoryService;

    @PostMapping("/save")
    public ResponseEntity<String> saveReportHistory(@RequestBody ReportHistoryDTO reportHistoryDTO) {
        try {
            // Service를 사용해 ReportHistoryEntity 저장
            boolean savedEntity = reportHistoryService.save(reportHistoryDTO);

            // 저장 성공 시 성공 메시지 반환
            return ResponseEntity.ok("Report history saved successfully");
        } catch (Exception e) {
            // 저장 실패 시 500 코드와 함께 실패 메시지 반환
            return ResponseEntity.status(500).body("Failed to save report history");
        }
    }
}
