package com.example.NYSC.Clearance.Form.App;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClearanceFormService {

    private final ClearanceFormRepo repository;

    public ClearanceForm saveForm(ClearanceForm form) {
        return repository.save(form);
    }

    public List<ClearanceForm> getAllForms() {
        return repository.findAll();
    }
    public List<ClearanceForm> searchForms(String keyword) {
        return repository.findByFullNameContainingIgnoreCaseOrEmailContainingIgnoreCase(keyword, keyword);
    }

}

