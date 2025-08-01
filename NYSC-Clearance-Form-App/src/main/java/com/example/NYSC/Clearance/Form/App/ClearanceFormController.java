package com.example.NYSC.Clearance.Form.App;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class ClearanceFormController {

    private final ClearanceFormService service;

    // Directory to save uploaded signature files (configured in application.properties)
    @Value("${file.upload-dir}")
    private String uploadDir;

    //This Is To Show The Main Form
    @GetMapping("/")
    public String showForm(Model model) {
        model.addAttribute("form", new ClearanceForm());
        return "form"; // Load form.html
    }

    //This Will Handle My Form Submission
    @PostMapping("/submit")
    public String submitForm(@ModelAttribute ClearanceForm form,
                             @RequestParam("supervisorSignature") MultipartFile supervisorSig,
                             @RequestParam("hodSignature") MultipartFile hodSig) throws IOException {

        // THis Is To Save Supervisor Signature
        if (!supervisorSig.isEmpty()) {
            String filename = System.currentTimeMillis() + "_" + supervisorSig.getOriginalFilename();
            File dest = new File(uploadDir, filename);
            supervisorSig.transferTo(dest);
            form.setSupervisorSignaturePath(filename);
        }

        // This Is To Save HOD Signature
        if (!hodSig.isEmpty()) {
            String filename = System.currentTimeMillis() + "_" + hodSig.getOriginalFilename();
            File dest = new File(uploadDir, filename);
            hodSig.transferTo(dest);
            form.setHodSignaturePath(filename);
        }

        //This is To Save form data to database
        form.setCreatedAt(LocalDate.now());
        service.saveForm(form);

        return "redirect:/success"; // Redirect to success.html
    }

    // Success Page
    @GetMapping("/success")
    public String successPage() {
        return "success";
    }

    //Admin View All Records
    @GetMapping("/records")
    public String viewRecords(Model model) {
        List<ClearanceForm> forms = service.getAllForms();
        model.addAttribute("forms", forms);
        return "records"; // Load records.html
    }

    // Admin Search Records
    @GetMapping("/records/search")
    public String searchRecords(@RequestParam("keyword") String keyword, Model model) {
        List<ClearanceForm> results = service.searchForms(keyword);
        model.addAttribute("forms", results);
        return "records"; // Show filtered results in same records.html
    }
}
