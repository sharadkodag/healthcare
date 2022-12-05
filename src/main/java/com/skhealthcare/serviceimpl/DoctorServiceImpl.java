package com.skhealthcare.serviceimpl;

import com.skhealthcare.entity.Doctor;
import com.skhealthcare.repository.DoctorRepository;
import com.skhealthcare.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DoctorServiceImpl implements DoctorService {

    @Autowired
    DoctorRepository doctorRepository;

    @Override
    public List<Doctor> getAllDoctors() {
        return doctorRepository.findAll();
    }

}
