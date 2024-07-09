package site.shcrm.shcrm_backend.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import site.shcrm.shcrm_backend.DTO.ReportDTO;
import site.shcrm.shcrm_backend.Entity.ReportEntity;
import site.shcrm.shcrm_backend.repository.ReportRepository;

@Service
@RequiredArgsConstructor
public class ReportService {
    private final ReportRepository reportRepository;
    public void save(ReportDTO reportDTO) {
        ReportEntity reportEntity = ReportEntity.toSaveEntity(reportDTO);
        reportRepository.save(reportEntity);

    }
}
