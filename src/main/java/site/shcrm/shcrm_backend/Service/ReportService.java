package site.shcrm.shcrm_backend.Service;

import jakarta.transaction.Transactional;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import site.shcrm.shcrm_backend.DTO.CommentDTO;
import site.shcrm.shcrm_backend.DTO.ReportDTO;
import site.shcrm.shcrm_backend.Entity.CommentEntity;
import site.shcrm.shcrm_backend.Entity.ReportEntity;
import site.shcrm.shcrm_backend.Entity.ReportFileEntity;
import site.shcrm.shcrm_backend.repository.CommentRepository;
import site.shcrm.shcrm_backend.repository.ReportFileRepository;
import site.shcrm.shcrm_backend.repository.ReportRepository;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ReportService {

    @Getter
    @Value("${file.upload-dir}")
    private String uploadDir;

    @Value("${file.base-url}")
    private String baseUrl;

    private final ReportRepository reportRepository;
    private final ReportFileRepository reportFileRepository;
    private final CommentRepository commentRepository; // 댓글 레포지토리 추가
    private final CommentService commentService;

    @Autowired
    public ReportService(ReportRepository reportRepository, ReportFileRepository reportFileRepository, CommentRepository commentRepository, CommentService commentService) {
        this.reportRepository = reportRepository;
        this.reportFileRepository = reportFileRepository;
        this.commentRepository = commentRepository; // 댓글 레포지토리 주입
        this.commentService = commentService;
    }

    @Transactional
    public ReportDTO save(ReportDTO reportDTO, MultipartFile[] reportFiles) throws IOException {
        ReportEntity reportEntity = ReportEntity.toSaveFileEntity(reportDTO);
        Long saveNo = reportRepository.save(reportEntity).getReportNo();
        ReportEntity report = reportRepository.findById(saveNo)
                .orElseThrow(() -> new RuntimeException("Report not found"));

        for (MultipartFile reportFile : reportFiles) {
            if (!reportFile.isEmpty()) {
                String originalFilename = reportFile.getOriginalFilename();
                String storedFileName = System.currentTimeMillis() + "_" + originalFilename;
                String savePath = uploadDir + File.separator + storedFileName;

                // 디렉토리 생성
                File directory = new File(uploadDir);
                if (!directory.exists()) {
                    directory.mkdirs();
                }

                // 파일 저장
                File destinationFile = new File(savePath);
                reportFile.transferTo(destinationFile);

                // ReportFileEntity 저장
                ReportFileEntity reportFileEntity = ReportFileEntity.toReportFileEntity(report, originalFilename, storedFileName);
                reportFileRepository.save(reportFileEntity);
            }
        }
        return ReportDTO.toreportfileDTO(reportEntity, baseUrl);
    }

    @Transactional
    public List<ReportDTO> findAll() {
        List<ReportEntity> reportEntityList = reportRepository.findAll();
        List<ReportDTO> reportDTOList = new ArrayList<>();
        for (ReportEntity reportEntity : reportEntityList) {
            reportDTOList.add(ReportDTO.toreportfileDTO(reportEntity, baseUrl));
        }
        return reportDTOList;
    }

    @Transactional
    public ReportDTO findById(Long no) {
        // ID로 ReportEntity 조회
        ReportEntity reportEntity = reportRepository.findById(no)
                .orElseThrow(() -> new RuntimeException("Report not found with ID: " + no));

        // 댓글 목록을 포함하여 ReportDTO를 반환
        List<CommentDTO> comments = commentService.findAll(no);
        ReportDTO reportDTO = ReportDTO.toreportfileDTO(reportEntity, baseUrl);
        reportDTO.setComments(comments); // 댓글 추가

        return reportDTO;
    }

    @Transactional
    public void update(Long reportNo, ReportDTO reportDTO, MultipartFile[] newFiles) throws IOException {
        // 기존 엔티티 조회
        ReportEntity existingReportEntity = reportRepository.findById(reportNo)
                .orElseThrow(() -> new RuntimeException("Report not found with ID: " + reportNo));

        // DTO의 값으로 엔티티 업데이트
        existingReportEntity.setReportWriter(reportDTO.getReportWriter());
        existingReportEntity.setReportTitle(reportDTO.getReportTitle());
        existingReportEntity.setReportContents(reportDTO.getReportContents());

        // 기존 파일 삭제
        List<ReportFileEntity> existingFiles = reportFileRepository.findByReportEntity(existingReportEntity);
        for (ReportFileEntity fileEntity : existingFiles) {
            // 파일 시스템에서 삭제
            File file = new File(uploadDir + File.separator + fileEntity.getStoredFileName());
            if (file.exists()) {
                file.delete();
            }
            // 데이터베이스에서 파일 정보 삭제
            reportFileRepository.delete(fileEntity);
        }

        // 새로운 파일 저장
        for (MultipartFile file : newFiles) {
            if (!file.isEmpty()) {
                String originalFilename = file.getOriginalFilename();
                String storedFileName = System.currentTimeMillis() + "_" + originalFilename;
                String savePath = uploadDir + File.separator + storedFileName;

                // 디렉토리 생성
                File directory = new File(uploadDir);
                if (!directory.exists()) {
                    directory.mkdirs();
                }

                // 파일 저장
                File destinationFile = new File(savePath);
                file.transferTo(destinationFile);

                // ReportFileEntity 저장
                ReportFileEntity reportFileEntity = ReportFileEntity.toReportFileEntity(existingReportEntity, originalFilename, storedFileName);
                reportFileRepository.save(reportFileEntity);
            }
        }

        // 엔티티 저장 (변경된 엔티티를 저장)
        reportRepository.save(existingReportEntity);
    }

    @Transactional
    public boolean delete(Long no) {
        if (reportRepository.existsById(no)) {
            // 게시글 엔티티 조회
            ReportEntity reportEntity = reportRepository.findById(no)
                    .orElseThrow(() -> new RuntimeException("Report not found"));

            // 게시글에 관련된 모든 파일 삭제
            List<ReportFileEntity> filesToDelete = reportFileRepository.findByReportEntity(reportEntity);
            for (ReportFileEntity fileEntity : filesToDelete) {
                // 파일 삭제
                File file = new File(uploadDir + File.separator + fileEntity.getStoredFileName());
                if (file.exists()) {
                    file.delete();
                }

                // 파일 엔티티 삭제
                reportFileRepository.delete(fileEntity);
            }

            // 게시글에 관련된 모든 댓글 삭제
            List<CommentEntity> comments = commentRepository.findByReportEntityOrderByCommentNoDesc(reportEntity);
            for (CommentEntity comment : comments) {
                commentRepository.delete(comment);
            }

            // 게시글 삭제
            reportRepository.deleteById(no);
            return true;
        } else {
            return false;
        }
    }
}