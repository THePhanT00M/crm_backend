package site.shcrm.shcrm_backend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import site.shcrm.shcrm_backend.DTO.ReportDTO;
import site.shcrm.shcrm_backend.Service.ReportService;
import site.shcrm.shcrm_backend.repository.ReportRepository;

import java.util.List;

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
        System.out.println("reportDTO = " + reportDTO);
        reportService.save(reportDTO);

        return "ReportHome";
    }
    @GetMapping("/reportlist")
    public String findALL(Model model){
        List<ReportDTO> reportDTOList = reportService.findAll();
        model.addAttribute("reportlist",reportDTOList);
        return "ReportList";
    }
}
