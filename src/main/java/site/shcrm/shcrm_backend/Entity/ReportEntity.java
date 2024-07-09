package site.shcrm.shcrm_backend.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import site.shcrm.shcrm_backend.DTO.ReportDTO;

import java.time.LocalDateTime;

@Setter
@Getter
@Entity
@Table(name = "report")
public class ReportEntity extends TimeEntity{

    @Id
    private Long no;

    @Column
    private String id;

    @Column(length = 20, nullable = false)
    private String boardWriter;

    @Column
    private String boardTitle;

    @Column(length = 50)
    private String boardContents;
    @Column
    private int boardHits;
    @Column
    private LocalDateTime boardCreatedTime;
    @Column
    private LocalDateTime boardUpdatedTime;

    public static ReportEntity toSaveEntity(ReportDTO reportDTO){
        ReportEntity reportEntity = new ReportEntity();
        reportEntity.setBoardWriter(reportDTO.getBoardWriter());
        reportEntity.setBoardTitle(reportDTO.getBoardWriter());
        reportEntity.setBoardContents(reportEntity.getBoardContents());
        reportEntity.setBoardHits(0);
        return reportEntity;
    }
}
