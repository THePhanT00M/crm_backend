package site.shcrm.shcrm_backend.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import site.shcrm.shcrm_backend.DTO.CommentDTO;

@Entity
@Getter
@Setter
@Table(name = "comment_table")
public class CommentEntity extends TimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentNo;

    @Column(length = 20, nullable = false)
    private String commentWriter;

    @Column(nullable = false)
    private String commentContents;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "report_no")
    private ReportEntity reportEntity;

    public static CommentEntity toSaveEntity(CommentDTO commentDTO, ReportEntity reportEntity) {
        CommentEntity commentEntity = new CommentEntity();
        commentEntity.setCommentWriter(commentDTO.getCommentWriter());
        commentEntity.setCommentContents(commentDTO.getCommentContents());
        commentEntity.setReportEntity(reportEntity);
        return commentEntity;
    }
}