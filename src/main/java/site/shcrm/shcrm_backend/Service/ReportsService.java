package site.shcrm.shcrm_backend.Service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import site.shcrm.shcrm_backend.DTO.ReportsDTO;
import site.shcrm.shcrm_backend.Entity.ReportsEntity;
import site.shcrm.shcrm_backend.repository.CategoryRepository;
import site.shcrm.shcrm_backend.repository.MembersRepository;
import site.shcrm.shcrm_backend.repository.PolicyRepository;
import site.shcrm.shcrm_backend.repository.ReportRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReportsService {

    private final ReportRepository reportsRepository;
    private final MembersRepository membersRepository; // Author ID 조회를 위해 추가
    private final PolicyRepository policyRepository; // Policy ID 조회를 위해 추가
    private final CategoryRepository categoryRepository; // Category ID 조회를 위해 추가

    public ReportsDTO findById(Integer id) {
        Optional<ReportsEntity> reportsEntityOptional = reportsRepository.findById(id);
        if (reportsEntityOptional.isPresent()) {
            ReportsEntity reportsEntity = reportsEntityOptional.get();
            return convertToDTO(reportsEntity);
        } else {
            throw new EntityNotFoundException("Report not found with id: " + id);
        }
    }

    public List<ReportsDTO> findAll() {
        List<ReportsEntity> reportsEntities = reportsRepository.findAll();
        return reportsEntities.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public boolean saveReport(ReportsDTO reportsDTO) {
        ReportsEntity reportsEntity = new ReportsEntity();
        reportsEntity.setTitle(reportsDTO.getTitle());
        reportsEntity.setContent(reportsDTO.getContent());
        reportsEntity.setStatus(reportsDTO.getStatus());

        // AuthorEntity, PolicyEntity, CategoryEntity 설정
        if (reportsDTO.getEmployeeId() != null) {
            reportsEntity.setEmployeeId(membersRepository.findById(reportsDTO.getEmployeeId())
                    .orElseThrow(() -> new IllegalArgumentException("Invalid employee_id: " + reportsDTO.getEmployeeId())));
        } else {
            throw new IllegalArgumentException("Employee ID cannot be null");
        }

        if (reportsDTO.getPolicyId() != null) {
            reportsEntity.setPolicyId(policyRepository.findById(reportsDTO.getPolicyId())
                    .orElseThrow(() -> new IllegalArgumentException("Invalid policy_id: " + reportsDTO.getPolicyId())));
        }
        if (reportsDTO.getCategoryId() != null) {
            reportsEntity.setCategoryId(categoryRepository.findById(reportsDTO.getCategoryId())
                    .orElseThrow(() -> new IllegalArgumentException("Invalid category_id: " + reportsDTO.getCategoryId())));
        }

        try {
            reportsRepository.save(reportsEntity);
            return true; // 저장 성공
        } catch (Exception e) {
            e.printStackTrace();
            return false; // 저장 실패
        }
    }

    public boolean updateReport(Integer id, ReportsDTO reportsDTO) {
        Optional<ReportsEntity> existingReportOpt = reportsRepository.findById(id);
        if (existingReportOpt.isPresent()) {
            ReportsEntity reportsEntity = existingReportOpt.get();
            reportsEntity.setTitle(reportsDTO.getTitle());
            reportsEntity.setContent(reportsDTO.getContent());
            reportsEntity.setStatus(reportsDTO.getStatus());
            // Update other fields as needed
            reportsRepository.save(reportsEntity);
            return true;
        } else {
            return false;
        }
    }

    private ReportsDTO convertToDTO(ReportsEntity reportsEntity) {
        ReportsDTO reportsDTO = new ReportsDTO();
        reportsDTO.setReportId(reportsEntity.getReportId());
        reportsDTO.setTitle(reportsEntity.getTitle());
        reportsDTO.setContent(reportsEntity.getContent());
        reportsDTO.setStatus(reportsEntity.getStatus());
        reportsDTO.setPolicyId(reportsEntity.getPolicyId() != null ? reportsEntity.getPolicyId().getId() : null);
        reportsDTO.setCategoryId(reportsEntity.getCategoryId() != null ? reportsEntity.getCategoryId().getId() : null);
        reportsDTO.setEmployeeId(reportsEntity.getEmployeeId() != null ? reportsEntity.getEmployeeId().getEmployeeId() : null); // Assuming getEmployeeId() returns Integer

        return reportsDTO;
    }
}