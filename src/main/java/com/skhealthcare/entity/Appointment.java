package com.skhealthcare.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Appointment {

    @Id
    @SequenceGenerator(name = "no", sequenceName = "no", initialValue = 1, allocationSize = 1)
    @GeneratedValue(generator = "no", strategy = GenerationType.SEQUENCE)
    private Integer id;
    private String name;
    private String address;
    private String age;
    private String description;
    @OneToOne
    private Staff doctor;
    private String time;

}
