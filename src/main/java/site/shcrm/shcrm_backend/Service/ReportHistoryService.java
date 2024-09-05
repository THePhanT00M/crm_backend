package site.shcrm.shcrm_backend.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import site.shcrm.shcrm_backend.DTO.ReportHistoryDTO;
import site.shcrm.shcrm_backend.Entity.ReportHistoryEntity;
import site.shcrm.shcrm_backend.repository.*;

@Service
public class ReportHistoryService {

    @Autowired
    private ReportHistoryRepository reportHistoryRepository;

    @Autowired
    private ReportRepository reportRepository;

    @Autowired
    private MembersRepository membersRepository;

    @Autowired
    private PolicyRepository policyRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    public boolean save(ReportHistoryDTO dto) {
        try {
            // ReportHistoryEntity 인스턴스 생성 및 DTO 데이터 매핑
            ReportHistoryEntity reportHistoryEntity = new ReportHistoryEntity();

            // 외래키 필드 설정
            reportHistoryEntity.setReportId(reportRepository.findById(dto.getReportId()).orElse(null));
            reportHistoryEntity.setEmployeeId(membersRepository.findById(dto.getEmployeeId()).orElse(null));
            reportHistoryEntity.setPreviousPolicyId(policyRepository.findById(dto.getPreviousPolicyId()).orElse(null));
            reportHistoryEntity.setNewPolicyId(policyRepository.findById(dto.getNewPolicyId()).orElse(null));
            reportHistoryEntity.setPreviousCategoryId(categoryRepository.findById(dto.getPreviousCategoryId()).orElse(null));
            reportHistoryEntity.setNewCategoryId(categoryRepository.findById(dto.getNewCategoryId()).orElse(null));

            // 기타 필드 설정
            reportHistoryEntity.setChangeSummary(dto.getChangeSummary());
            reportHistoryEntity.setPreviousContent(dto.getPreviousContent());
            reportHistoryEntity.setNewContent(dto.getNewContent());
            reportHistoryEntity.setStatusBefore(dto.getStatusBefore());
            reportHistoryEntity.setStatusAfter(dto.getStatusBefore());

            // 엔티티 저장
            reportHistoryRepository.save(reportHistoryEntity);
            return true;  // 저장 성공
        } catch (Exception e) {
            return false; // 저장 실패
        }
    }
}
