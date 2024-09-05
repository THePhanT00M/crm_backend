package site.shcrm.shcrm_backend.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import site.shcrm.shcrm_backend.DTO.ExpenseDTO;
import site.shcrm.shcrm_backend.Entity.CategoryEntity;
import site.shcrm.shcrm_backend.Entity.ExpenseEntity;
import site.shcrm.shcrm_backend.Entity.MembersEntity;
import site.shcrm.shcrm_backend.Entity.PolicyEntity;
import site.shcrm.shcrm_backend.repository.CategoryRepository;
import site.shcrm.shcrm_backend.repository.ExpenseRepository;
import site.shcrm.shcrm_backend.repository.MembersRepository;
import site.shcrm.shcrm_backend.repository.PolicyRepository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final PolicyRepository policyRepository;
    private final CategoryRepository categoryRepository;
    private final MembersRepository membersRepository;

    @Transactional
    public int saveExpense(ExpenseDTO expenseDTO) {
        try {
            // DTO를 엔티티로 변환
            ExpenseEntity expenseEntity = new ExpenseEntity();
            expenseEntity.setAmount(expenseDTO.getAmount());
            expenseEntity.setMerchantName(expenseDTO.getMerchantName());
            expenseEntity.setAddress(expenseDTO.getAddress());
            expenseEntity.setExpenseDate(expenseDTO.getExpenseDate());
            expenseEntity.setReimbursement(expenseDTO.getReimbursement());
            expenseEntity.setIsDeleted('N'); // 초기에는 삭제되지 않음으로 설정

            // CategoryEntity와 PolicyEntity를 조회하여 설정
            if (expenseDTO.getCategoryId() != null) {
                CategoryEntity category = categoryRepository.findById(expenseDTO.getCategoryId())
                        .orElseThrow(() -> new IllegalArgumentException("Invalid category_id: " + expenseDTO.getCategoryId()));
                expenseEntity.setCategoryId(category);
            }

            if (expenseDTO.getPolicyId() != null) {
                PolicyEntity policy = policyRepository.findById(expenseDTO.getPolicyId())
                        .orElseThrow(() -> new IllegalArgumentException("Invalid policy_id: " + expenseDTO.getPolicyId()));
                expenseEntity.setPolicyId(policy);
            }

            if (expenseDTO.getEmployeeId() == null || expenseDTO.getEmployeeId() <= 0) {
                throw new IllegalArgumentException("Employee ID must be provided and greater than 0");
            } else {
                MembersEntity members = membersRepository.findByEmployeeId(expenseDTO.getEmployeeId())
                        .orElseThrow(() -> new IllegalArgumentException("Invalid employee_id: " + expenseDTO.getEmployeeId()));
                expenseEntity.setEmployeeId(members);
            }

            ExpenseEntity savedEntity = expenseRepository.save(expenseEntity);
            return savedEntity.getExpenseId();
        } catch (Exception e) {
            e.printStackTrace(); // 예외를 로그로 남기거나 추가적인 처리가 필요할 수 있음
            throw e; // 예외를 던져서 트랜잭션 롤백을 발생시킵니다
        }
    }


    @Transactional
    public boolean markExpenseAsDeleted(int expenseId, int employeeId) {
        try {
            MembersEntity membersEntity = membersRepository.findByEmployeeId(employeeId)
                    .orElseThrow(() -> new IllegalArgumentException("Invalid employee_id: " + employeeId));
            Optional<ExpenseEntity> optionalExpense = expenseRepository.findByExpenseIdAndEmployeeId(expenseId, membersEntity);

            if (optionalExpense.isPresent()) {
                ExpenseEntity expense = optionalExpense.get();
                expense.setIsDeleted('Y'); // is_deleted 값을 "Y"로 설정
                expenseRepository.save(expense);
                return true;
            } else {
                return false; // 해당 expense_id가 존재하지 않거나 해당 employee가 소유하지 않음
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false; // 삭제 실패
        }
    }

    @Transactional
    public boolean updateExpense(ExpenseDTO expenseDTO) {
        try {
            // DTO에서 매개변수 추출
            int expenseId = expenseDTO.getExpenseId();
            int employeeId = expenseDTO.getEmployeeId();
            BigDecimal amount = expenseDTO.getAmount();
            String merchantName = expenseDTO.getMerchantName();
            String address = expenseDTO.getAddress();
            Date expenseDate = expenseDTO.getExpenseDate();
            Integer categoryId = expenseDTO.getCategoryId();
            Integer policyId = expenseDTO.getPolicyId();
            String reimbursement = expenseDTO.getReimbursement();

            // 직원 엔티티 조회
            MembersEntity membersEntity = membersRepository.findByEmployeeId(employeeId)
                    .orElseThrow(() -> new IllegalArgumentException("Invalid employee_id: " + employeeId));

            // 경비 엔티티 조회
            Optional<ExpenseEntity> optionalExpense = expenseRepository.findByExpenseIdAndEmployeeId(expenseId, membersEntity);

            if (optionalExpense.isPresent()) {
                ExpenseEntity expense = optionalExpense.get();

                // 삭제 여부 확인
                if (expense.getIsDeleted() == 'N') {
                    // DTO에서 가져온 값으로 업데이트
                    expense.setAmount(amount);
                    expense.setMerchantName(merchantName);
                    expense.setAddress(address);
                    expense.setExpenseDate(expenseDate);
                    expense.setReimbursement(reimbursement);

                    // 카테고리 엔티티 설정
                    if (categoryId != null) {
                        CategoryEntity category = categoryRepository.findById(categoryId)
                                .orElseThrow(() -> new IllegalArgumentException("Invalid category_id: " + categoryId));
                        expense.setCategoryId(category);
                    }

                    // 정책 엔티티 설정
                    if (policyId != null) {
                        PolicyEntity policy = policyRepository.findById(policyId)
                                .orElseThrow(() -> new IllegalArgumentException("Invalid policy_id: " + policyId));
                        expense.setPolicyId(policy);
                    }

                    // 업데이트된 엔티티 저장
                    expenseRepository.save(expense);
                    return true;
                } else {
                    return false; // 이미 삭제된 데이터
                }
            } else {
                return false; // 경비 데이터가 존재하지 않거나 직원 소속이 아님
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false; // 업데이트 실패
        }
    }



    public List<ExpenseEntity> getAllActiveExpenses(ExpenseDTO expenseDTO) {
        int employeeId = expenseDTO.getEmployeeId();
        MembersEntity membersEntity = membersRepository.findByEmployeeId(employeeId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid employee_id: " + employeeId));
        return expenseRepository.findByIsDeletedAndEmployeeId('N', membersEntity);
    }

    public ExpenseEntity getExpenseById(ExpenseDTO expenseDTO) {
        int expenseId = expenseDTO.getExpenseId();
        int employeeId = expenseDTO.getEmployeeId();
        MembersEntity membersEntity = membersRepository.findByEmployeeId(employeeId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid employee_id: " + employeeId));
        // expense_id와 employee_id로 엔티티를 조회하고, is_deleted가 "N"인 경우에만 반환
        Optional<ExpenseEntity> optionalExpense = expenseRepository.findByExpenseIdAndEmployeeId(expenseId, membersEntity);
        return optionalExpense.filter(expense -> expense.getIsDeleted() == 'N').orElse(null);
    }
}