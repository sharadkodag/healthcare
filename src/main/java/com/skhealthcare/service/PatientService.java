package com.skhealthcare.service;

import com.skhealthcare.entity.Patient;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

public interface PatientService {

    public List<Patient> getAllPatient();
    public void addPatient(Patient patient);

}
