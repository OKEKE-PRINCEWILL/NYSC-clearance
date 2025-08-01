package com.example.NYSC.Clearance.Form.App;

import org.springframework.data.convert.ReadingConverter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClearanceFormRepo extends JpaRepository<ClearanceForm, Long> {
    List<ClearanceForm> findByFullNameContainingIgnoreCaseOrEmailContainingIgnoreCase(String name);
}
