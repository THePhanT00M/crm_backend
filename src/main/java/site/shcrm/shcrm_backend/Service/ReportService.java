package site.shcrm.shcrm_backend.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import site.shcrm.shcrm_backend.DTO.ReportDTO;
import site.shcrm.shcrm_backend.Entity.ReportEntity;
import site.shcrm.shcrm_backend.repository.ReportRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    @Transactional
    public void updateHits(Long no) {
        reportRepository.updateHits(no);
    }

    public ReportDTO findById(Long no) {
        Optional<ReportEntity> optionalReportEntity = reportRepository.findById(no);
        if(optionalReportEntity.isPresent()){
            ReportEntity reportEntity = optionalReportEntity.get();
            ReportDTO reportDTO = ReportDTO.toreportDTO(reportEntity);
            return reportDTO;
        } else {
            return null;
        }
    }
}
