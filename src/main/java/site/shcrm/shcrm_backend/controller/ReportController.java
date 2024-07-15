package site.shcrm.shcrm_backend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import site.shcrm.shcrm_backend.DTO.ReportDTO;
import site.shcrm.shcrm_backend.Service.ReportService;

import java.io.IOException;
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
    public String save(@ModelAttribute ReportDTO reportDTO) throws IOException {
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
    @GetMapping("/reportdetail/{no}")
    public String findById(@PathVariable Long no,Model model){
        reportService.updateHits(no);
        ReportDTO reportDTO = reportService.findById(no);
        model.addAttribute("report",reportDTO);
        return "ReportDetail";
    }
    @GetMapping("/reportupdate/{no}")
    public String updateForm(@PathVariable long no,Model model){
        ReportDTO reportDTO = reportService.findById(no);
        model.addAttribute("reportUpdate",reportDTO);
        return "ReportUpdate";
    }
    @PostMapping("/reportupdate")
    public String update(@ModelAttribute ReportDTO reportDTO, Model model){
        ReportDTO report = reportService.update(reportDTO);
        model.addAttribute("report",report);
        return "ReportDetail";
    }

    @GetMapping("/reportdelete/{no}")
    public String delete(@PathVariable long no){
        reportService.delete(no);
        return "redirect:/reporthome";
    }
}
