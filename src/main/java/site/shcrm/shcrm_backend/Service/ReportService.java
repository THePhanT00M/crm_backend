package site.shcrm.shcrm_backend.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import site.shcrm.shcrm_backend.DTO.ReportDTO;
import site.shcrm.shcrm_backend.Entity.ReportEntity;
import site.shcrm.shcrm_backend.repository.ReportRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportService {
    private final ReportRepository reportRepository;
    public void save(ReportDTO reportDTO) {
        ReportEntity reportEntity = ReportEntity.toSaveEntity(reportDTO);
        reportRepository.save(reportEntity);

    }

    public List<ReportDTO> findAll() {
        List<ReportEntity> reportEntityList = reportRepository.findAll();
        List<ReportDTO> reportDTOList = new ArrayList<>();
        for (ReportEntity reportEntity: reportEntityList){
            reportDTOList.add(ReportDTO.toreportDTO(reportEntity));
        }
        return reportDTOList;
    }
}
