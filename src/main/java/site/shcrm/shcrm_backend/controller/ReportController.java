package site.shcrm.shcrm_backend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import site.shcrm.shcrm_backend.DTO.ReportDTO;
import site.shcrm.shcrm_backend.Service.ReportService;
import site.shcrm.shcrm_backend.repository.ReportRepository;

@Controller
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;
    @GetMapping("/reporthome")
    public String ReportHome(){
        return "ReportHome";
    }

    @GetMapping("/reportsave")
    public String ReportSave(){
        return "ReportSave";
    }
    @PostMapping("/reportsave")
    public String save(@ModelAttribute ReportDTO reportDTO){
        System.out.println("boardDTO = " + reportDTO);
        reportService.save(reportDTO);

        return "ReportHome";
    }

}
