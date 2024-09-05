package site.shcrm.shcrm_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import site.shcrm.shcrm_backend.Entity.ExpenseEntity;
import site.shcrm.shcrm_backend.Entity.MembersEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface ExpenseRepository extends JpaRepository<ExpenseEntity, Integer> {
    List<ExpenseEntity> findByIsDeleted(String is_deleted);

    Optional<ExpenseEntity> findByExpenseIdAndEmployeeId(int expenseId, MembersEntity employeeId);

    List<ExpenseEntity> findByIsDeletedAndEmployeeId(Character isDeleted, MembersEntity employee);
}

