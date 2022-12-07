package com.skhealthcare.service;

import com.skhealthcare.entity.Appointment;
import org.springframework.stereotype.Service;

import java.util.List;

public interface AppointmentService {

    public List<Appointment> getAllAppointment();

    public void addAppointment(Appointment appointment);

    public void deleteAppointment(Appointment appointment);

    public Appointment getAppointmentById(Integer id);

}
