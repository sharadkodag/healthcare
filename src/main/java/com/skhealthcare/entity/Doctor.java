package com.skhealthcare.entity;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Doctor {

    @Id
    @SequenceGenerator(name = "sr", sequenceName = "sr", initialValue = 101, allocationSize = 1)
    @GeneratedValue(generator = "sr",strategy = GenerationType.SEQUENCE)
    @EqualsAndHashCode.Include
    private Integer id;
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private String bloodGroup;
    private String gender;
    private String education;
    private String designation;
    private String mobileNumber;
    private String maritalStatus;
    private LocalDate joiningDate;
    private Boolean isWorking;
    private LocalDate leavingDate;
    private String username;
    private String password;
    @ManyToOne
    private Department department;
    @OneToMany(mappedBy = "doctor")
    List<Patient> patientsList;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Doctor doctor = (Doctor) o;
        return id != null && Objects.equals(id, doctor.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}
