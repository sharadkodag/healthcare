package com.skhealthcare.modules.login;

import com.skhealthcare.entity.Department;
import com.skhealthcare.entity.Staff;
import com.skhealthcare.entity.Patient;
import com.skhealthcare.mvputil.BasePresenter;
import com.skhealthcare.service.DepartmentService;
import com.skhealthcare.service.StaffService;
import com.skhealthcare.service.PatientService;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@SpringComponent
@UIScope
public class LoginPresenter extends BasePresenter<LoginView> {

    @Autowired
    PatientService patientService;

    @Autowired
    StaffService staffService;

    @Autowired
    DepartmentService departmentService;

    public List<Patient> getAllPatient(){
        return patientService.getAllPatient();
    }

    public List<Staff> getAllDoctor(){
        return staffService.getAllDoctors("Doctor");
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
