package site.shcrm.shcrm_backend.DTO;

import lombok.Builder;
import lombok.Value;
import site.shcrm.shcrm_backend.Entity.CommentEntity;

import java.time.LocalDateTime;

@Value
@Builder
public class CommentDTO {
    Long commentNo;
    String commentWriter;
    String commentContents;
    Long reportNo;
    LocalDateTime commentCreatedTime;

    public static CommentDTO toCommentDTO(CommentEntity commentEntity, Long reportNo) {
        return CommentDTO.builder()
                .commentNo(commentEntity.getCommentNo())
                .commentWriter(commentEntity.getCommentWriter())
                .commentContents(commentEntity.getCommentContents())
                .commentCreatedTime(commentEntity.getCreatedTime())
                .reportNo(reportNo)
                .build();
    }
}
