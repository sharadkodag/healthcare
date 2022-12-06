package com.skhealthcare.entity;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Patient {

    @Id
    @SequenceGenerator(name = "sr", sequenceName = "sr", initialValue = 1, allocationSize = 1)
    @GeneratedValue(generator = "sr",strategy = GenerationType.SEQUENCE)
    private Integer id;
    private String fullName;
    private String address;
    private Integer age;
    private String mobileNumber;
    @ManyToOne
    private Doctor doctor;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Patient patient = (Patient) o;
        return id != null && Objects.equals(id, patient.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}
