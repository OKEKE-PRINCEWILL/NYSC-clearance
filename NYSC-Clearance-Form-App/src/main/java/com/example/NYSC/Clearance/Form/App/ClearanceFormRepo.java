package com.example.NYSC.Clearance.Form.App;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClearanceFormRepo extends JpaRepository<ClearanceForm, Long> {

    // Fixed method to match actual entity fields
    List<ClearanceForm> findByCorpsNameContainingIgnoreCaseOrStateCodeContainingIgnoreCase(String corpsName, String stateCode);

    // Alternative using custom query for more flexible search
    @Query("SELECT c FROM ClearanceForm c WHERE " +
            "LOWER(c.corpsName) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(c.stateCode) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(c.department) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<ClearanceForm> searchByKeyword(@Param("keyword") String keyword);
}