package site.shcrm.shcrm_backend.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import site.shcrm.shcrm_backend.DTO.ReportDTO;
import site.shcrm.shcrm_backend.Entity.ReportEntity;
import site.shcrm.shcrm_backend.Entity.ReportFileEntity;
import site.shcrm.shcrm_backend.repository.ReportFileRepository;
import site.shcrm.shcrm_backend.repository.ReportRepository;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReportService {
    private final ReportRepository reportRepository;
    private final ReportFileRepository reportFileRepository;

    public ReportDTO save(ReportDTO reportDTO) throws IOException {
        if (reportDTO.getReportFile().isEmpty()) {
            ReportEntity reportEntity = ReportEntity.toSaveEntity(reportDTO);
            reportRepository.save(reportEntity);
        } else {
            ReportEntity reportEntity = ReportEntity.toSaveFileEntity(reportDTO);
            Long saveNo = reportRepository.save(reportEntity).getNo();
            ReportEntity report = reportRepository.findById(saveNo).get();
            for (MultipartFile reportFile : reportDTO.getReportFile()) {
                String originalFilename = reportFile.getOriginalFilename();
                String storedFileName = System.currentTimeMillis() + "_" + originalFilename;
                String savePath = "C:/springboot_img/" + storedFileName;
                reportFile.transferTo(new File(savePath));
                ReportFileEntity reportFileEntity = ReportFileEntity.toReportFileEntity(report, originalFilename, storedFileName);
                reportFileRepository.save(reportFileEntity);
            }

        }
        return reportDTO;
    }

    @Transactional
    public List<ReportDTO> findAll() {
        List<ReportEntity> reportEntityList = reportRepository.findAll();
        List<ReportDTO> reportDTOList = new ArrayList<>();
        for (ReportEntity reportEntity : reportEntityList) {
            reportDTOList.add(ReportDTO.toreportDTO(reportEntity));
        }
        return reportDTOList;
    }

    @Transactional
    public void updateHits(Long no) {
        reportRepository.updateHits(no);
    }

    @Transactional
    public ReportDTO findById(Long no) {
        Optional<ReportEntity> optionalReportEntity = reportRepository.findById(no);
        if (optionalReportEntity.isPresent()) {
            ReportEntity reportEntity = optionalReportEntity.get();
            ReportDTO reportDTO = ReportDTO.toreportDTO(reportEntity);
            return reportDTO;
        } else {
            return null;
        }
    }

    public ReportDTO update(ReportDTO reportDTO) {
        ReportEntity reportEntity = ReportEntity.toUpdateEntity(reportDTO);
        reportRepository.save(reportEntity);
        return findById(reportDTO.getNo());
    }

    public void delete(long no) {
        reportRepository.deleteById(no);
    }
}
