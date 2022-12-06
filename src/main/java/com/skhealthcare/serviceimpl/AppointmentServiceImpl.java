package com.skhealthcare.serviceimpl;

import com.skhealthcare.entity.Appointment;
import com.skhealthcare.repository.AppointmentRepository;
import com.skhealthcare.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppointmentServiceImpl implements AppointmentService {

    @Autowired
    AppointmentRepository appointmentRepository;

    @Override
    public List<Appointment> getAllAppointment() {
        return appointmentRepository.findAll();
    }
}
