package site.shcrm.shcrm_backend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import site.shcrm.shcrm_backend.DTO.ReportDTO;
import site.shcrm.shcrm_backend.Service.CommentService;
import site.shcrm.shcrm_backend.Service.ReportService;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/report")
public class ReportController {

    private final ReportService reportService;
    private final CommentService commentService;

    @PostMapping("/save")
    public ResponseEntity<ReportDTO> createReport(
            @RequestParam("username") String username,
            @RequestParam("reportWriter") String reportWriter,
            @RequestParam("reportTitle") String reportTitle,
            @RequestParam("reportContents") String reportContents,
            @RequestParam("reportFiles") MultipartFile[] reportFiles) {

        ReportDTO reportDTO = new ReportDTO();
        reportDTO.setUsername(username);
        reportDTO.setReportWriter(reportWriter);
        reportDTO.setReportTitle(reportTitle);
        reportDTO.setReportContents(reportContents);

        try {
            ReportDTO savedReportDTO = reportService.save(reportDTO, reportFiles);
            return ResponseEntity.ok(savedReportDTO);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/list")
    public ResponseEntity<List<ReportDTO>> findAll() {
        List<ReportDTO> reportDTOList = reportService.findAll();
        return ResponseEntity.ok(reportDTOList);
    }

    @GetMapping("/list/{no}")
    public ResponseEntity<ReportDTO> findById(@PathVariable Long no) {
        try {
            ReportDTO reportDTO = reportService.findById(no);

            return ResponseEntity.ok(reportDTO);
        } catch (RuntimeException e) {
            // 예외가 발생하면 404 상태 코드와 함께 응답합니다
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PutMapping("/update/{no}")
    public ResponseEntity<String> updateReport(
            @PathVariable long no,
            @RequestParam("username") String username,
            @RequestParam("reportWriter") String reportWriter,
            @RequestParam("reportTitle") String reportTitle,
            @RequestParam("reportContents") String reportContents,
            @RequestParam(value = "files", required = false) MultipartFile[] files) {

        ReportDTO reportDTO = new ReportDTO();
        reportDTO.setReportNo(no);
        reportDTO.setUsername(username);
        reportDTO.setReportWriter(reportWriter);
        reportDTO.setReportTitle(reportTitle);
        reportDTO.setReportContents(reportContents);

        try {
            reportService.update(no, reportDTO, files);
            return ResponseEntity.ok("Report updated successfully.");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("File processing error.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/delete/{no}")
    public ResponseEntity<String> delete(@PathVariable long no) {
        boolean isDeleted = reportService.delete(no);
        if (isDeleted) {
            return ResponseEntity.ok("Report and related files and comments deleted successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Report not found.");
        }
    }

    private final String uploadDir = "C:/springboot_img";

    @GetMapping("/download/{filename:.+}")
    public ResponseEntity<InputStreamResource> downloadFile(@PathVariable String filename) {
        File file = null;
        try {
            // 파일 이름 디코딩 (URL 인코딩된 파일 이름 처리)
            String decodedFilename = URLDecoder.decode(filename, StandardCharsets.UTF_8);

            // 파일 경로 설정 (경로 안전성 강화)
            file = new File(uploadDir, decodedFilename);

            // 파일 경로 출력
            System.out.println("File path: " + file.getAbsolutePath());

            // 파일이 존재하지 않을 경우 404 상태 반환
            if (!file.exists() || !file.isFile()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }

            // 파일을 InputStreamResource로 반환
            try (FileInputStream fileInputStream = new FileInputStream(file)) {
                InputStreamResource resource = new InputStreamResource(fileInputStream);
                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + URLEncoder.encode(filename, StandardCharsets.UTF_8.toString()) + "\"")
                        .contentType(MediaType.APPLICATION_OCTET_STREAM)
                        .body(resource);
            }
        } catch (IOException e) {
            // 파일 읽기 오류 발생 시 500 상태 반환
            e.printStackTrace(); // 오류 로그 추가
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/files/view")
    public ResponseEntity<InputStreamResource> viewFile(@RequestParam("filename") String filename) {
        try {
            File file = new File(uploadDir + File.separator + filename);
            if (!file.exists()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }

            // 파일을 InputStreamResource로 반환
            InputStreamResource resource = new InputStreamResource(new FileInputStream(file));

            // MIME 타입 결정
            String mimeType = Files.probeContentType(file.toPath());
            if (mimeType == null) {
                mimeType = MediaType.APPLICATION_OCTET_STREAM_VALUE; // 기본 MIME 타입
            }

            // 응답 설정
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_TYPE, mimeType)
                    .body(resource);
        } catch (IOException e) {
            // 파일 읽기 오류 발생 시 500 상태 반환
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}