package site.shcrm.shcrm_backend.Service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import site.shcrm.shcrm_backend.DTO.CategoryDTO;
import site.shcrm.shcrm_backend.Entity.CategoryEntity;
import site.shcrm.shcrm_backend.repository.CategoryRepository;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    // 카테고리 생성
    public CategoryDTO createCategory(CategoryDTO categoryDTO) {
        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setCategoryName(categoryDTO.getCategoryName());
        categoryEntity.setDescription(categoryDTO.getDescription());
        // 자동으로 createdAt 및 updatedAt 필드가 설정됨
        CategoryEntity savedEntity = categoryRepository.save(categoryEntity);
        return convertToDTO(savedEntity);
    }

    // 카테고리 업데이트
    public CategoryDTO updateCategory(CategoryDTO categoryDTO) {
        Optional<CategoryEntity> optionalCategory = categoryRepository.findById(categoryDTO.getCategoryId());

        if (optionalCategory.isPresent()) {
            CategoryEntity categoryEntity = optionalCategory.get();
            categoryEntity.setCategoryName(categoryDTO.getCategoryName());
            categoryEntity.setDescription(categoryDTO.getDescription());
            categoryEntity.setUpdatedAt(LocalDateTime.now()); // 업데이트 시각 갱신
            CategoryEntity updatedEntity = categoryRepository.save(categoryEntity);
            return convertToDTO(updatedEntity);
        } else {
            throw new EntityNotFoundException("Category not found with ID: " + categoryDTO.getCategoryId());
        }
    }

    // 카테고리 삭제
    public void deleteCategory(int categoryId) {
        if (categoryRepository.existsById(categoryId)) {
            categoryRepository.deleteById(categoryId);
        } else {
            throw new EntityNotFoundException("Category not found with ID: " + categoryId);
        }
    }

    // DTO로 변환
    private CategoryDTO convertToDTO(CategoryEntity entity) {
        CategoryDTO dto = new CategoryDTO();
        dto.setCategoryId(entity.getCategoryId());
        dto.setCategoryName(entity.getCategoryName());
        dto.setDescription(entity.getDescription());
        return dto;
    }
}
