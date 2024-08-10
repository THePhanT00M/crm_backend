package site.shcrm.shcrm_backend.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import site.shcrm.shcrm_backend.DTO.CommentDTO;
import site.shcrm.shcrm_backend.Entity.CommentEntity;
import site.shcrm.shcrm_backend.Entity.ReportEntity;
import site.shcrm.shcrm_backend.repository.CommentRepository;
import site.shcrm.shcrm_backend.repository.ReportRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final ReportRepository reportRepository;

    // 댓글 저장
    public Long save(CommentDTO commentDTO) {
        return reportRepository.findById(commentDTO.getReportNo())
                .map(reportEntity -> {
                    CommentEntity commentEntity = CommentEntity.toSaveEntity(commentDTO, reportEntity);
                    return commentRepository.save(commentEntity).getCommentNo();
                })
                .orElseThrow(() -> new IllegalArgumentException("Report not found with id: " + commentDTO.getReportNo()));
    }

    // 댓글 조회
    public List<CommentDTO> findAll(Long reportNo) {
        return reportRepository.findById(reportNo)
                .map(reportEntity -> commentRepository.findByReportEntityOrderByCommentNoDesc(reportEntity)
                        .stream()
                        .map(commentEntity -> CommentDTO.toCommentDTO(commentEntity, reportNo))
                        .collect(Collectors.toList()))
                .orElseThrow(() -> new IllegalArgumentException("Report not found with id: " + reportNo));
    }

    // 댓글 수정
    @Transactional
    public void update(Long commentNo, CommentDTO commentDTO) {
        CommentEntity commentEntity = commentRepository.findById(commentNo)
                .orElseThrow(() -> new IllegalArgumentException("Comment not found with id: " + commentNo));

        commentEntity.setCommentWriter(commentDTO.getCommentWriter());
        commentEntity.setCommentContents(commentDTO.getCommentContents());
        // 추가로 수정할 필드가 있으면 여기에 추가
    }

    // 댓글 삭제
    @Transactional
    public void delete(Long commentId) {
        if (commentRepository.existsById(commentId)) {
            commentRepository.deleteById(commentId);
        } else {
            throw new IllegalArgumentException("Comment not found with id: " + commentId);
        }
    }
}
