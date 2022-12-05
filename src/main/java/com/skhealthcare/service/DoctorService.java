package com.skhealthcare.service;

import com.skhealthcare.entity.Doctor;
import org.springframework.stereotype.Service;

import java.util.List;

public interface DoctorService {

    public List<Doctor> getAllDoctors();

}
