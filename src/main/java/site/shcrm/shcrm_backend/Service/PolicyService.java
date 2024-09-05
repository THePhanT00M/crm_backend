package site.shcrm.shcrm_backend.Service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import site.shcrm.shcrm_backend.DTO.PolicyDTO;
import site.shcrm.shcrm_backend.Entity.PolicyEntity;
import site.shcrm.shcrm_backend.repository.PolicyRepository;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PolicyService {

    private final PolicyRepository policyRepository;

    // 정책 생성
    public PolicyDTO createPolicy(PolicyDTO policyDTO) {
        PolicyEntity policyEntity = new PolicyEntity();
        policyEntity.setPolicyName(policyDTO.getPolicyName());
        policyEntity.setDescription(policyDTO.getDescription());
        policyEntity.setEffectiveDate(policyDTO.getEffectiveDate());
        policyEntity.setExpirationDate(policyDTO.getExpirationDate());
        // 자동으로 createdAt 및 updatedAt 필드가 설정됨
        PolicyEntity savedEntity = policyRepository.save(policyEntity);
        return convertToDTO(savedEntity);
    }

    // 정책 업데이트
    public PolicyDTO updatePolicy(int policyId, PolicyDTO policyDTO) {
        Optional<PolicyEntity> optionalPolicy = policyRepository.findById(policyId);

        if (optionalPolicy.isPresent()) {
            PolicyEntity policyEntity = optionalPolicy.get();
            policyEntity.setPolicyName(policyDTO.getPolicyName());
            policyEntity.setDescription(policyDTO.getDescription());
            policyEntity.setEffectiveDate(policyDTO.getEffectiveDate());
            policyEntity.setExpirationDate(policyDTO.getExpirationDate());
            policyEntity.setUpdatedAt(LocalDateTime.now()); // 업데이트 시각 갱신
            PolicyEntity updatedEntity = policyRepository.save(policyEntity);
            return convertToDTO(updatedEntity);
        } else {
            throw new EntityNotFoundException("Policy not found with ID: " + policyId);
        }
    }

    // 정책 삭제
    public void deletePolicy(int policyId) {
        if (policyRepository.existsById(policyId)) {
            policyRepository.deleteById(policyId);
        } else {
            throw new EntityNotFoundException("Policy not found with ID: " + policyId);
        }
    }

    // DTO로 변환
    private PolicyDTO convertToDTO(PolicyEntity entity) {
        PolicyDTO dto = new PolicyDTO();
        dto.setPolicyId(entity.getPolicyId());
        dto.setPolicyName(entity.getPolicyName());
        dto.setDescription(entity.getDescription());
        dto.setEffectiveDate(entity.getEffectiveDate());
        dto.setExpirationDate(entity.getExpirationDate());
        return dto;
    }
}
