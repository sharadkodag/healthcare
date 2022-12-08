package com.skhealthcare.modules.adminview;

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
import com.sun.jna.platform.win32.WinCrypt;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;

import java.awt.*;
import java.util.List;
import java.util.Set;

@UIScope
@SpringComponent
public class AdminPresenter extends BasePresenter<AdminView> {

    @Autowired
    PatientService patientService;

    @Autowired
    StaffService staffService;

    @Autowired
    DepartmentService departmentService;

    @Autowired
    AppointmentService appointmentService;

    public void beforeEnter(BeforeEnterEvent observer){
        Staff staff = (Staff) VaadinSession.getCurrent().getAttribute("Admin");
        if(staff!=null){
            if(staff.getDesignation().equals("Admin")){
                observer.forwardTo(AdminView.class);
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
    public Boolean checkUsername(String username){
        return staffService.checkUsername(username);
    }

    public void addStaff(Binder<Staff> staffBinder, Dialog dialog, Button save, Button cancel){
        staffBinder.removeBean();
        dialog.open();
        save.addClickListener(event -> {
            staffBinder.validate();
            System.out.println("inside save");
            if(staffBinder.isValid()){
                System.out.println("inside valid");
                Staff staff = new Staff();
                try {
                    staffBinder.writeBean(staff);
                } catch (ValidationException e) {
                    throw new RuntimeException(e);
                }
                staffService.addStaff(staff);
                Notification show = Notification.show("Staff added successfully.", 3000, Notification.Position.TOP_CENTER);
                show.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
                dialog.close();
            }
        });
        cancel.addClickListener(e -> dialog.close());
    }

    public void editStaff(Grid<Staff> staffGrid, Binder<Staff> staffBinder, Integer id, Dialog dialog, Button save, Button cancel){
        Set<Staff> selectedItems = staffGrid.getSelectedItems();
        if (!selectedItems.isEmpty()) {
            Staff staff = selectedItems.iterator().next();
            dialog.open();
            save.addClickListener(event -> {
                staffBinder.validate();
                if(staffBinder.isValid()){
                    Staff staffById = staffService.getStaffById(staff.getId());
                    try {
                        staffBinder.writeBean(staffById);
                    } catch (ValidationException e) {
                        throw new RuntimeException(e);
                    }
                    staffService.addStaff(staffById);
                    Notification show = Notification.show("Staff edited successfully", 2000, Notification.Position.TOP_CENTER);
                    show.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
                }
            });
            cancel.addClickListener(e -> dialog.close());
        }else {
            Notification show = Notification.show("Please select staff", 3000, Notification.Position.TOP_CENTER);
            show.addThemeVariants(NotificationVariant.LUMO_ERROR);
        }
    }

    public void deleteStaff(Grid<Staff> staffGrid){
        Set<Staff> selectedItems = staffGrid.getSelectedItems();
        if(!selectedItems.isEmpty()){
            Staff staff = selectedItems.iterator().next();
            staffService.deleteStaff(staffService.getStaffById(staff.getId()));
            Notification show = Notification.show("Staff deleted successfully", 3000, Notification.Position.TOP_CENTER);
            show.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
        }else {
            Notification show = Notification.show("Please select staff", 3000, Notification.Position.TOP_CENTER);
            show.addThemeVariants(NotificationVariant.LUMO_ERROR);
        }
    }

    public Department getDepartmentByName(String deptName){
        return departmentService.getDepartmentByName(deptName);
    }

}
