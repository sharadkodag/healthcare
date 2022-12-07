package com.skhealthcare.modules.doctorview;

import com.skhealthcare.entity.Appointment;
import com.skhealthcare.entity.Department;
import com.skhealthcare.entity.Staff;
import com.skhealthcare.entity.Patient;
import com.skhealthcare.mvputil.BasePresenter;
import com.skhealthcare.service.AppointmentService;
import com.skhealthcare.service.DepartmentService;
import com.skhealthcare.service.StaffService;
import com.skhealthcare.service.PatientService;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@SpringComponent
@UIScope
public class DoctorLoginPresenter extends BasePresenter<DoctorLoginView> {

    @Autowired
    PatientService patientService;

    @Autowired
    StaffService staffService;

    @Autowired
    DepartmentService departmentService;

    @Autowired
    AppointmentService appointmentService;

    public List<Patient> getAllPatient(){
        return patientService.getAllPatient();
    }

    public List<Staff> getAllDoctor(){
        return staffService.getAllDoctors();
    }

    public List<Department> getAllDepartment(){
        return departmentService.getAllDepartment();
    }

    public void addPatient(Patient patient){
        patientService.addPatient(patient);
    }

    public void deletePatient(Patient patient){
        patientService.deletePatient(patient);
    }

    public Patient getPatientById(Integer id){
        return patientService.getPatientById(id);
    }

    public List<Appointment> getAllAppointment(){
        return appointmentService.getAllAppointment();
    }
    public Appointment getAppointmentById(Integer id){
        return appointmentService.getAppointmentById(id);
    }



}
