package site.shcrm.shcrm_backend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import site.shcrm.shcrm_backend.DTO.ReportDTO;
import site.shcrm.shcrm_backend.Service.ReportService;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/report")
public class ReportController {

    private final ReportService reportService;

    @PostMapping("/save")
    public ResponseEntity<String> save(@RequestBody ReportDTO reportDTO) throws IOException {
        ReportDTO save = reportService.save(reportDTO);
        return ResponseEntity.ok("save");
    }
    @GetMapping("/list")
    public ResponseEntity<List<ReportDTO>> findALL(Model model){
        List<ReportDTO> reportDTOList = reportService.findAll();
        return ResponseEntity.ok(reportDTOList);
    }

    @PostMapping("/detail/{no}")
    public ResponseEntity<ReportDTO> findById(@PathVariable Long no){
        reportService.updateHits(no);
        ReportDTO reportDTO = reportService.findById(no);
        if (reportDTO == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(reportDTO);
    }
    @GetMapping("/update/{no}")
    public String updateForm(@PathVariable long no,Model model){
        ReportDTO reportDTO = reportService.findById(no);
        model.addAttribute("reportUpdate",reportDTO);
        return "ReportUpdate";
    }
    @PostMapping("/update")
    public String update(@ModelAttribute ReportDTO reportDTO, Model model){
        ReportDTO report = reportService.update(reportDTO);
        model.addAttribute("report",report);
        return "ReportDetail";
    }

    @GetMapping("/delete/{no}")
    public String delete(@PathVariable long no){
        reportService.delete(no);
        return "redirect:/report/home";
    }
}
