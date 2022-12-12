package com.skhealthcare.modules.receprionistview.tabs;

import com.skhealthcare.entity.Department;
import com.skhealthcare.entity.Staff;
import com.skhealthcare.modules.receprionistview.ReceptionistPresenter;
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
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.Objects;
import java.util.Set;

@UIScope
@SpringComponent
public class DoctorDetails extends VerticalLayout {

    @Autowired
    ReceptionistPresenter receptionistPresenter;
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

        add(addDoctorDetails());
    }

    public VerticalLayout addDoctorDetails(){

        VerticalLayout verticalLayout1 = new VerticalLayout();
        Grid<Staff> doctorGrid = new Grid<>();
        doctorGrid.setItems(receptionistPresenter.getAllDoctor());
        Grid.Column<Staff> firstName = doctorGrid.addColumn(Staff::getFirstName).setHeader("First name");
        Grid.Column<Staff> lastName = doctorGrid.addColumn(Staff::getLastName).setHeader("Last Name");
        Grid.Column<Staff> education = doctorGrid.addColumn(Staff::getEducation).setHeader("Education");
        Grid.Column<Staff> gender = doctorGrid.addColumn(Staff::getGender).setHeader("Gender");
        Grid.Column<Staff> mobileNumber = doctorGrid.addColumn(Staff::getMobileNumber).setHeader("Mobile Number");
        Grid.Column<Staff> bloodGroup = doctorGrid.addColumn(Staff::getBloodGroup).setHeader("Blood Group");
        Grid.Column<Staff> department = doctorGrid.addColumn(e -> e.getDepartment().getDeptName()).setHeader("Department");

        TextField firstnameField = new TextField();
        TextField lastNameField = new TextField();
        TextField educationField = new TextField();
        TextField genderField= new TextField();
        TextField mobileField = new TextField();
        TextField bloodGroupField = new TextField();
        TextField departmentField = new TextField();

        firstnameField.setPlaceholder("First Name");
        lastNameField.setPlaceholder("Last Name");
        educationField.setPlaceholder("Education");
        genderField.setPlaceholder("Gender");
        mobileField.setPlaceholder("Mobile Number");
        bloodGroupField.setPlaceholder("Blood Group");
        departmentField.setPlaceholder("Department");

        firstnameField.setValueChangeMode(ValueChangeMode.LAZY);
        lastNameField.setValueChangeMode(ValueChangeMode.LAZY);
        educationField.setValueChangeMode(ValueChangeMode.LAZY);
        bloodGroupField.setValueChangeMode(ValueChangeMode.LAZY);
        genderField.setValueChangeMode(ValueChangeMode.LAZY);
        mobileField.setValueChangeMode(ValueChangeMode.LAZY);
        departmentField.setValueChangeMode(ValueChangeMode.LAZY);

        HeaderRow headerRow = doctorGrid.appendHeaderRow();
        headerRow.getCell(firstName).setComponent(firstnameField);
        headerRow.getCell(lastName).setComponent(lastNameField);
        headerRow.getCell(education).setComponent(educationField);
        headerRow.getCell(gender).setComponent(genderField);
        headerRow.getCell(mobileNumber).setComponent(mobileField);
        headerRow.getCell(bloodGroup).setComponent(bloodGroupField);
        headerRow.getCell(department).setComponent(departmentField);

        verticalLayout1.add(doctorGrid);
        verticalLayout1.setSizeFull();

        firstnameField.addValueChangeListener(event -> getDoctorFilter(doctorGrid,firstnameField,lastNameField,educationField,
                genderField,mobileField,bloodGroupField,departmentField));
        lastNameField.addValueChangeListener(event -> getDoctorFilter(doctorGrid,firstnameField,lastNameField,educationField,
                genderField,mobileField,bloodGroupField,departmentField));
        genderField.addValueChangeListener(event -> getDoctorFilter(doctorGrid,firstnameField,lastNameField,educationField,
                genderField,mobileField,bloodGroupField,departmentField));
        bloodGroupField.addValueChangeListener(event -> getDoctorFilter(doctorGrid,firstnameField,lastNameField,educationField,
                genderField,mobileField,bloodGroupField,departmentField));
        departmentField.addValueChangeListener(event -> getDoctorFilter(doctorGrid,firstnameField,lastNameField,educationField,
                genderField,mobileField,bloodGroupField,departmentField));
        educationField.addValueChangeListener(event -> getDoctorFilter(doctorGrid,firstnameField,lastNameField,educationField,
                genderField,mobileField,bloodGroupField,departmentField));
        mobileField.addValueChangeListener(event -> getDoctorFilter(doctorGrid,firstnameField,lastNameField,educationField,
                genderField,mobileField,bloodGroupField,departmentField));

        return verticalLayout1;
    }

    public void getDoctorFilter(Grid<Staff> doctorGrid, TextField firstnameField, TextField lastNameField, TextField educationField, TextField genderField,
                                TextField mobileField, TextField bloodGroupField, TextField departmentField){

        ListDataProvider<Staff> dataProvider = (ListDataProvider)doctorGrid.getDataProvider();
        dataProvider.setFilter(e -> {

            Boolean b1 = e.getFirstName().toLowerCase().contains(firstnameField.getValue().toLowerCase());
            Boolean b2 = e.getLastName().toLowerCase().contains(lastNameField.getValue().toLowerCase());
            Boolean b3 = e.getEducation().toLowerCase().contains(educationField.getValue().toLowerCase());
            Boolean b4 = e.getGender().toLowerCase().contains(genderField.getValue().toLowerCase());
            Boolean b5 = e.getMobileNumber().toLowerCase().contains(mobileField.getValue().toLowerCase());
            Boolean b6 = e.getBloodGroup().toLowerCase().contains(bloodGroupField.getValue().toLowerCase());
            Boolean b7 = e.getDepartment().getDeptName().toLowerCase().contains(departmentField.getValue().toLowerCase());

            return b1 && b2 && b3 && b4 && b6 && b7 && b5;
        });
    }

}
