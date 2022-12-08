package com.skhealthcare.repository;

import com.skhealthcare.entity.Patient;
import com.skhealthcare.entity.Staff;
import com.vaadin.flow.server.VaadinSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PatientRepository extends JpaRepository<Patient,Integer> {

    public List<Patient> findAllByDoctor(Staff doctor);
}
