package com.skhealthcare.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Department {

    @Id
    private int id;
    private String deptName;
    @OneToMany(mappedBy = "department")
    List<Doctor> doctorList;
    @OneToMany(mappedBy = "dept")
    List<Staff> staffList;

}
