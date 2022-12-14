package com.skhealthcare.modules.receprionistview;

import com.skhealthcare.entity.Appointment;
import com.skhealthcare.entity.Department;
import com.skhealthcare.entity.Staff;
import com.skhealthcare.entity.Patient;
import com.skhealthcare.modules.homeview.HomepageView;
import com.skhealthcare.mvputil.BasePresenter;
import com.skhealthcare.service.AppointmentService;
import com.skhealthcare.service.DepartmentService;
import com.skhealthcare.service.StaffService;
import com.skhealthcare.service.PatientService;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.server.VaadinSession;
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
    StaffService staffService;

    @Autowired
    DepartmentService departmentService;

    @Autowired
    AppointmentService appointmentService;

    public void beforeEnter(BeforeEnterEvent observer){
        Staff staff = (Staff) VaadinSession.getCurrent().getAttribute("Receptionist");
        if(staff!=null){
            if(staff.getDesignation().equals("Receptionist")){
                observer.forwardTo(ReceptionistView.class);
            }
        }else {
            observer.forwardTo(HomepageView.class);
        }
    }

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

    public Patient getPatientById(Integer id){
        return patientService.getPatientById(id);
    }

    public List<Appointment> getAllAppointment(){
        return appointmentService.getAllAppointment();
    }

    public void addAppointment(Appointment appointment){
        appointmentService.addAppointment(appointment);
    }

    public void deleteAppointment(Appointment appointment){
        appointmentService.deleteAppointment(appointment);
    }

    public Appointment getAppointmentById(Integer id){

        return appointmentService.getAppointmentById(id);
    }


}
