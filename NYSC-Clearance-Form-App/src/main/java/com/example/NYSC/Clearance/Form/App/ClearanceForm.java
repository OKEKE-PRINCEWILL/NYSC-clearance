package com.example.NYSC.Clearance.Form.App;


import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "clearance_forms")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClearanceForm {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Corps Member Section
    private String corpsName;
    private String stateCode;
    private String department;

    // Supervisor Section
    private int daysAbsent;
    @Column(length = 1000)
    private String conductRemark;
    private String supervisorName;
    private String supervisorSignaturePath;
    private LocalDate supervisorDate;

    // HOD Section
    @Column(length = 1000)
    private String hodRemark;
    private String hodName;
    private String hodSignaturePath;
    private LocalDate hodDate;

    private LocalDate createdAt;
}
