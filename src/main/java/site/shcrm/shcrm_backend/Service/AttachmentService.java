package site.shcrm.shcrm_backend.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import site.shcrm.shcrm_backend.DTO.AttachmentDTO;
import site.shcrm.shcrm_backend.Entity.AttachmentEntity;
import site.shcrm.shcrm_backend.repository.AttachmentRepository;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.util.*;

@Service
@RequiredArgsConstructor
public class AttachmentService {

    private final AttachmentRepository attachmentRepository;

    private static final String UPLOADED_FOLDER = "C:" + File.separator + "springboot_data" + File.separator;

    public List<AttachmentDTO> uploadFiles(MultipartFile[] files, int relatedId, AttachmentDTO.RelatedType relatedType) throws IOException {
        List<AttachmentDTO> attachmentDTOs = new ArrayList<>();

        // 업로드 디렉토리가 존재하는지 확인하고, 없으면 생성
        File uploadDir = new File(UPLOADED_FOLDER);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }

        for (MultipartFile file : files) {
            String originalFilename = file.getOriginalFilename();
            if (originalFilename == null) {
                // 파일 이름이 없는 경우 예외 처리
                throw new IllegalArgumentException("File name cannot be null");
            }
            String uniqueFilename = generateUniqueFileName(originalFilename);

            Path path = Paths.get(UPLOADED_FOLDER + uniqueFilename);
            Files.write(path, file.getBytes());

            AttachmentEntity attachmentEntity = new AttachmentEntity();
            attachmentEntity.setRelatedId(relatedId);
            attachmentEntity.setRelatedType(AttachmentEntity.RelatedType.valueOf(relatedType.name()));
            attachmentEntity.setOriginalName(originalFilename);
            attachmentEntity.setFileUrl(path.toString());
            AttachmentEntity savedEntity = attachmentRepository.save(attachmentEntity);

            attachmentDTOs.add(convertToDTO(savedEntity));
        }

        return attachmentDTOs;
    }

    private String generateUniqueFileName(String originalFilename) {
        String extension = "";
        int index = originalFilename.lastIndexOf('.');
        if (index > 0) {
            extension = originalFilename.substring(index);
        }
        return UUID.randomUUID().toString() + extension;
    }

    public boolean deleteFile(AttachmentDTO attachmentDTO) {
        int attachmentId = attachmentDTO.getAttachmentId();
        Optional<AttachmentEntity> attachmentEntityOpt = attachmentRepository.findById(attachmentId);

        if (attachmentEntityOpt.isPresent()) {
            AttachmentEntity attachmentEntity = attachmentEntityOpt.get();
            File file = new File(attachmentEntity.getFileUrl());

            if (file.exists() && file.delete()) {
                attachmentRepository.deleteById(attachmentId);
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    private AttachmentDTO convertToDTO(AttachmentEntity entity) {
        AttachmentDTO dto = new AttachmentDTO();
        dto.setAttachmentId(entity.getAttachmentId());
        dto.setRelatedId(entity.getRelatedId());
        dto.setRelatedType(AttachmentDTO.RelatedType.valueOf(entity.getRelatedType().name()));
        dto.setOriginalName(entity.getOriginalName());
        dto.setFileUrl(entity.getFileUrl());
        return dto;
    }
}
