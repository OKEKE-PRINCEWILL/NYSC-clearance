package com.example.NYSC.Clearance.Form.App;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class ClearanceFormController {

    private final ClearanceFormService service;

    @Value("${file.upload-dir}")
    private String uploadDir;

    @GetMapping("/")
    public String showForm(Model model) {
        // Fixed: Use "clearanceForm" to match the HTML template
        model.addAttribute("clearanceForm", new ClearanceForm());
        return "form";
    }

    @PostMapping("/submit")
    public String submitForm(@ModelAttribute ClearanceForm clearanceForm,
                             @RequestParam("supervisorSignature") MultipartFile supervisorSig,
                             @RequestParam("hodSignature") MultipartFile hodSig) throws IOException {

        // Create upload directory if it doesn't exist
        Files.createDirectories(Paths.get(uploadDir));

        // Save Supervisor Signature
        if (!supervisorSig.isEmpty()) {
            String filename = System.currentTimeMillis() + "_" + supervisorSig.getOriginalFilename();
            File dest = new File(uploadDir, filename);
            supervisorSig.transferTo(dest);
            clearanceForm.setSupervisorSignaturePath(filename);
        }

        // Save HOD Signature
        if (!hodSig.isEmpty()) {
            String filename = System.currentTimeMillis() + "_" + hodSig.getOriginalFilename();
            File dest = new File(uploadDir, filename);
            hodSig.transferTo(dest);
            clearanceForm.setHodSignaturePath(filename);
        }

        // Save form data to database
        clearanceForm.setCreatedAt(LocalDate.now());
        service.saveForm(clearanceForm);

        return "redirect:/success";
    }

    @GetMapping("/success")
    public String successPage() {
        return "success";
    }

    @GetMapping("/records")
    public String viewRecords(Model model) {
        List<ClearanceForm> forms = service.getAllForms();
        model.addAttribute("forms", forms);
        return "records";
    }

    @GetMapping("/records/search")
    public String searchRecords(@RequestParam("keyword") String keyword, Model model) {
        List<ClearanceForm> results = service.searchForms(keyword);
        model.addAttribute("forms", results);
        return "records";
    }
}