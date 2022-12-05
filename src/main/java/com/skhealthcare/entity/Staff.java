package com.skhealthcare.entity;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Staff {

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
    private Department dept;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Staff staff = (Staff) o;
        return id != null && Objects.equals(id, staff.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
