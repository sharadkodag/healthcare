package com.skhealthcare.modules.adminview.tabs;

import com.skhealthcare.entity.Appointment;
import com.skhealthcare.entity.Department;
import com.skhealthcare.entity.Staff;
import com.skhealthcare.modules.adminview.AdminPresenter;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.HeaderRow;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@UIScope
@SpringComponent
public class StaffDetails extends VerticalLayout {

    @Autowired
    AdminPresenter adminPresenter;
    HorizontalLayout staffButtonLayout;
    Binder<Staff> staffBinder;
    Button logoutButton;

    @PostConstruct
    public void init(){
        staffButtonLayout = new HorizontalLayout();
        staffBinder = new Binder<>();
        logoutButton = new Button("Logout");

        setMargin(false);
        setPadding(false);
        setSizeFull();

        add(staffButtonLayout, addStaffDetails());

    }

    public VerticalLayout addStaffDetails(){

        VerticalLayout verticalLayout1 = new VerticalLayout();
        Grid<Staff> staffGrid = new Grid<>();
        staffGrid.setItems(adminPresenter.getAllStaff());
        Grid.Column<Staff> firstName = staffGrid.addColumn(Staff::getFirstName).setHeader("First name");
        Grid.Column<Staff> lastName = staffGrid.addColumn(Staff::getLastName).setHeader("Last Name");
        Grid.Column<Staff> education = staffGrid.addColumn(Staff::getEducation).setHeader("Education");
        Grid.Column<Staff> gender = staffGrid.addColumn(Staff::getGender).setHeader("Gender");
        Grid.Column<Staff> mobileNumber = staffGrid.addColumn(Staff::getMobileNumber).setHeader("Mobile Number");
        Grid.Column<Staff> bloodGroup = staffGrid.addColumn(Staff::getBloodGroup).setHeader("Blood Group");
        Grid.Column<Staff> department = staffGrid.addColumn(e -> e.getDepartment().getDeptName()).setHeader("Department");
        Grid.Column<Staff> designation1 = staffGrid.addColumn(e -> e.getDesignation()).setHeader("Designation");

        TextField firstNameField = new TextField();
        TextField lastNameField = new TextField();
        TextField educationField = new TextField();
        TextField genderField= new TextField();
        TextField mobileField = new TextField();
        TextField bloodGroupField = new TextField();
        TextField departmentField = new TextField();
        TextField designationField = new TextField();

        firstNameField.setPlaceholder("First Name");
        lastNameField.setPlaceholder("Last Name");
        educationField.setPlaceholder("Education");
        genderField.setPlaceholder("Gender");
        mobileField.setPlaceholder("Mobile Number");
        bloodGroupField.setPlaceholder("Blood Group");
        departmentField.setPlaceholder("Department");
        designationField.setPlaceholder("Department");

        firstNameField.setValueChangeMode(ValueChangeMode.LAZY);
        lastNameField.setValueChangeMode(ValueChangeMode.LAZY);
        educationField.setValueChangeMode(ValueChangeMode.LAZY);
        bloodGroupField.setValueChangeMode(ValueChangeMode.LAZY);
        genderField.setValueChangeMode(ValueChangeMode.LAZY);
        mobileField.setValueChangeMode(ValueChangeMode.LAZY);
        departmentField.setValueChangeMode(ValueChangeMode.LAZY);
        designationField.setValueChangeMode(ValueChangeMode.LAZY);

        HeaderRow headerRow = staffGrid.appendHeaderRow();
        headerRow.getCell(firstName).setComponent(firstNameField);
        headerRow.getCell(lastName).setComponent(lastNameField);
        headerRow.getCell(education).setComponent(educationField);
        headerRow.getCell(gender).setComponent(genderField);
        headerRow.getCell(mobileNumber).setComponent(mobileField);
        headerRow.getCell(bloodGroup).setComponent(bloodGroupField);
        headerRow.getCell(department).setComponent(departmentField);
        headerRow.getCell(designation1).setComponent(designationField);

        verticalLayout1.add(staffGrid);
        verticalLayout1.setSizeFull();

        firstNameField.addValueChangeListener(event -> getStaffFilter(staffGrid,firstNameField,lastNameField,educationField,
                genderField,mobileField,bloodGroupField,departmentField,designationField));
        lastNameField.addValueChangeListener(event -> getStaffFilter(staffGrid,firstNameField,lastNameField,educationField,
                genderField,mobileField,bloodGroupField,departmentField,designationField));
        genderField.addValueChangeListener(event -> getStaffFilter(staffGrid,firstNameField,lastNameField,educationField,
                genderField,mobileField,bloodGroupField,departmentField,designationField));
        bloodGroupField.addValueChangeListener(event -> getStaffFilter(staffGrid,firstNameField,lastNameField,educationField,
                genderField,mobileField,bloodGroupField,departmentField,designationField));
        departmentField.addValueChangeListener(event -> getStaffFilter(staffGrid,firstNameField,lastNameField,educationField,
                genderField,mobileField,bloodGroupField,departmentField,designationField));
        educationField.addValueChangeListener(event -> getStaffFilter(staffGrid,firstNameField,lastNameField,educationField,
                genderField,mobileField,bloodGroupField,departmentField,designationField));
        mobileField.addValueChangeListener(event -> getStaffFilter(staffGrid,firstNameField,lastNameField,educationField,
                genderField,mobileField,bloodGroupField,departmentField,designationField));
        designationField.addValueChangeListener(event -> getStaffFilter(staffGrid,firstNameField,lastNameField,educationField,
                genderField,mobileField,bloodGroupField,departmentField,designationField));

        Button addButton = new Button("Add");
        Button editButton = new Button("Edit");
        Button deleteButton = new Button("Delete");
        Dialog dialog = new Dialog();
        FormLayout formLayout = new FormLayout();
        IntegerField id = new IntegerField("Staff Id");
        TextField fName = new TextField("First Name");
        TextField lName = new TextField("Last Name");
        DatePicker dob = new DatePicker("Date of Birth");
        ComboBox<String> edu = new ComboBox<>("Education");
        RadioButtonGroup<String> genderF= new RadioButtonGroup<>();
        TextField mob = new TextField("Mobile Number");
        ComboBox<String> bg = new ComboBox<>("Blood Group");
        ComboBox<Department> dept = new ComboBox<>("Department");
        ComboBox<String> designation = new ComboBox<>("Designation");
        ComboBox<String> maritalStatus = new ComboBox<>("Marital Status");
        DatePicker joining = new DatePicker("Joining Date");
        Checkbox isWorking = new Checkbox("Is working");
        DatePicker leavingDate = new DatePicker("Leaving date");
        TextField userName = new TextField("Username");
        TextField password = new TextField("Password");
        Button save = new Button("Save");
        Button cancel = new Button("Cancel");

        genderF.setLabel("Gender");


        id.setEnabled(false);
        genderF.setItems("Male", "Female");
        edu.setItems("B.Pharmacy", "D.Pharmacy", "M.B.B.S", "B.H.M.S", "B.A.M.S", "HSC", "SSC",
                "B.Tech", "B.Sc", "B.Com", "B.A", "M.D");
        bg.setItems("A +ve", "A -ve", "B +ve", "B -ve", "AB +ve", "AB -ve", "O +ve", "O -ve");
        dept.setItems(adminPresenter.getAllDepartment());
        dept.setItemLabelGenerator(Department::getDeptName);
        designation.setItems("Doctor", "Nurse", "Receptionist","Helper", "Pharmacist", "Admin");
        maritalStatus.setItems("Single", "Married", "Divorced");
        addButton.getStyle().set("color", "white").set("background-color","green");
        editButton.getStyle().set("color", "white").set("background-color","blue");
        deleteButton.getStyle().set("color", "white").set("background-color","red");
        save.getStyle().set("color", "white").set("background-color","green");
        cancel.getStyle().set("color","white").set("background-color","red");
        dialog.setWidth(40, Unit.PERCENTAGE);

        staffBinder.forField(id).bind(Staff::getId,Staff::setId);
        staffBinder.forField(fName).withValidator(s -> !s.equals(""),"Enter first name").bind(Staff::getFirstName, Staff::setFirstName);
        staffBinder.forField(lName).withValidator(s -> !s.equals(""),"Enter last name").bind(Staff::getLastName, Staff::setLastName);
        staffBinder.forField(dob).withValidator(s -> dob.getValue()!=null,"Enter date of birth").bind(Staff::getDateOfBirth, Staff::setDateOfBirth);
        staffBinder.forField(edu).withValidator(Objects::nonNull,"Enter education").bind(Staff::getEducation,Staff::setEducation);
        staffBinder.forField(bg).withValidator(Objects::nonNull,"Select blood group").bind(Staff::getBloodGroup,Staff::setBloodGroup);
        staffBinder.forField(genderF).withValidator(Objects::nonNull,"select gender").bind(Staff::getGender,Staff::setGender);
        staffBinder.forField(mob).withValidator(s -> !s.equals(""),"Enter mobile number").bind(Staff::getMobileNumber,Staff::setMobileNumber);
        staffBinder.forField(dept).withValidator(Objects::nonNull,"Select department").bind(Staff::getDepartment, Staff::setDepartment);
        staffBinder.forField(designation).withValidator(Objects::nonNull,"Select designationField").bind(Staff::getDesignation, Staff::setDesignation);
        staffBinder.forField(maritalStatus).withValidator(Objects::nonNull,"Select marital status").bind(Staff::getMaritalStatus, Staff::setMaritalStatus);
        staffBinder.forField(joining).withValidator(Objects::nonNull,"Select joining date").bind(Staff::getJoiningDate, Staff::setJoiningDate);
        staffBinder.forField(isWorking).withValidator(Objects::nonNull,"Select").bind(Staff::getIsWorking,Staff::setIsWorking);
        staffBinder.forField(leavingDate).bind(Staff::getLeavingDate,Staff::setLeavingDate);
        staffBinder.forField(userName).withValidator(s -> !s.equals("") && !adminPresenter.checkUsername(userName.getValue()),"username not available")
                .bind(Staff::getUserName, Staff::setUserName);
        staffBinder.forField(password).withValidator(s -> s.length()>=6,"Enter password greater than 6 character")
                .bind(Staff::getPassword, Staff::setPassword);

        staffButtonLayout.add(addButton,editButton,deleteButton);
        formLayout.add(id, fName, lName, dob, edu, genderF, bg, mob, dept, designation, maritalStatus, joining, isWorking, leavingDate, userName, password, save, cancel);
        formLayout.setColspan(save,2);
        formLayout.setColspan(cancel,2);
        dialog.add(formLayout);

        addButton.addClickListener(event -> {
            adminPresenter.addStaff(staffBinder, dialog, save, cancel);
        });
        editButton.addClickListener(event -> {
            Set<Staff> staffSet = staffGrid.getSelectedItems();
            Staff next = new Staff();
            if(staffSet.size()>0) {
                next = staffSet.iterator().next();
            }
            staffBinder.setBean(next);
            adminPresenter.editStaff(staffGrid, staffBinder, id.getValue(), dialog, save, cancel);
        });
        deleteButton.addClickListener(event -> {
            adminPresenter.deleteStaff(staffGrid);
        });

        return verticalLayout1;
    }

    public void getStaffFilter(Grid<Staff> staffGrid, TextField firstnameField, TextField lastNameField, TextField educationField, TextField genderField,
                               TextField mobileField, TextField bloodGroupField, TextField departmentField, TextField designationField){

        ListDataProvider<Staff> dataProvider = (ListDataProvider)staffGrid.getDataProvider();
        dataProvider.setFilter(e -> {

            Boolean b1 = e.getFirstName().toLowerCase().contains(firstnameField.getValue().toLowerCase());
            Boolean b2 = e.getLastName().toLowerCase().contains(lastNameField.getValue().toLowerCase());
            Boolean b3 = e.getEducation().toLowerCase().contains(educationField.getValue().toLowerCase());
            Boolean b4 = e.getGender().toLowerCase().contains(genderField.getValue().toLowerCase());
            Boolean b5 = e.getMobileNumber().toLowerCase().contains(mobileField.getValue().toLowerCase());
            Boolean b6 = e.getBloodGroup().toLowerCase().contains(bloodGroupField.getValue().toLowerCase());
            Boolean b7 = e.getDepartment().getDeptName().toLowerCase().contains(departmentField.getValue().toLowerCase());
            Boolean b8 = e.getDesignation().toLowerCase().contains(designationField.getValue().toLowerCase());

            return b1 && b2 && b3 && b4 && b6 && b7 && b5 && b8;
        });
    }

}
