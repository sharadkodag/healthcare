package com.skhealthcare.serviceimpl;

import com.skhealthcare.entity.Patient;
import com.skhealthcare.repository.PatientRepository;
import com.skhealthcare.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientServiceImpl implements PatientService {

    @Autowired
    PatientRepository patientRepository;

    @Override
    public List<Patient> getAllPatient() {
        return patientRepository.findAll();
    }

    @Override
    public void addPatient(Patient patient) {
        patientRepository.save(patient);
    }

    @Override
    public void deletePatient(Patient patient) {
        patientRepository.delete(patient);
    }

    @Override
    public void updatePatient(Patient patient) {
        patientRepository.save(patient);
    }

    @Override
    public Patient getPatientById(Integer id) {
        return patientRepository.findById(id).orElse(null);
    }


}
