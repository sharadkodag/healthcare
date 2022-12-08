package com.skhealthcare.serviceimpl;

import com.skhealthcare.entity.Appointment;
import com.skhealthcare.entity.Staff;
import com.skhealthcare.repository.AppointmentRepository;
import com.skhealthcare.service.AppointmentService;
import com.vaadin.flow.server.VaadinSession;
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

    @Override
    public void addAppointment(Appointment appointment) {
        appointmentRepository.save(appointment);
    }

    @Override
    public void deleteAppointment(Appointment appointment) {
        appointmentRepository.delete(appointment);
    }

    @Override
    public Appointment getAppointmentById(Integer id) {
        return appointmentRepository.findById(id).orElse(null);
    }

    @Override
    public List<Appointment> getAllAppointmentForDoctor() {
        Staff doctor = (Staff) VaadinSession.getCurrent().getAttribute("Doctor");
        return appointmentRepository.findAllByDoctor(doctor);
    }
}
