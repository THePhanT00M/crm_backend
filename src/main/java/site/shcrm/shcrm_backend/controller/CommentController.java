package site.shcrm.shcrm_backend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import site.shcrm.shcrm_backend.DTO.CommentDTO;
import site.shcrm.shcrm_backend.Service.CommentService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/comment")
public class CommentController {

    private final CommentService commentService;

    // 댓글 저장
    @PostMapping("/save/{reportno}")
    public ResponseEntity<String> save(@PathVariable Long reportno,
                                       @RequestParam String commentWriter,
                                       @RequestParam String commentContents) {
        try {
            CommentDTO commentDTO = CommentDTO.builder()
                    .reportNo(reportno)
                    .commentWriter(commentWriter)
                    .commentContents(commentContents)
                    .build();
            Long saveResult = commentService.save(commentDTO);
            if (saveResult != null) {
                return ResponseEntity.status(HttpStatus.CREATED).body("댓글이 성공적으로 저장되었습니다.");
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("작성 실패");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 오류");
        }
    }

    // 댓글 수정
    @PutMapping("/update/{commentno}")
    public ResponseEntity<String> update(@PathVariable Long commentno,
                                         @RequestParam String commentWriter,
                                         @RequestParam String commentContents) {
        try {
            // CommentDTO 객체를 생성하여 필요한 필드를 설정
            CommentDTO commentDTO = CommentDTO.builder()
                    .commentWriter(commentWriter)
                    .commentContents(commentContents)
                    .build();

            commentService.update(commentno, commentDTO);
            return ResponseEntity.ok("댓글이 성공적으로 수정되었습니다.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 오류");
        }
    }

    // 댓글 삭제
    @DeleteMapping("/delete/{commentno}")
    public ResponseEntity<String> delete(@PathVariable Long commentno) {
        try {
            commentService.delete(commentno);
            return ResponseEntity.ok("댓글이 성공적으로 삭제되었습니다.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 오류");
        }
    }
}