package com.skhealthcare.repository;

import com.skhealthcare.entity.Appointment;
import com.skhealthcare.entity.Staff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Integer> {

    public List<Appointment> findAllByDoctor(Staff staff);

}
