package com.skhealthcare.modules.receprionist;

import com.skhealthcare.entity.Department;
import com.skhealthcare.entity.Doctor;
import com.skhealthcare.entity.Patient;
import com.skhealthcare.mvputil.BasePresenter;
import com.skhealthcare.service.DepartmentService;
import com.skhealthcare.service.DoctorService;
import com.skhealthcare.service.PatientService;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@UIScope
@SpringComponent
public class ReceptionistPresenter extends BasePresenter<ReceptionistView> {

    @Autowired
    PatientService patientService;

    @Autowired
    DoctorService doctorService;

    @Autowired
    DepartmentService departmentService;

    public List<Patient> getAllPatient(){
        return patientService.getAllPatient();
    }

    public List<Doctor> getAllDoctor(){
        return doctorService.getAllDoctors();
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

    public void updatePatient(Patient patient){
        patientService.updatePatient(patient);
    }

    public Patient getPatientById(Integer id){
        return patientService.getPatientById(id);
    }


}
