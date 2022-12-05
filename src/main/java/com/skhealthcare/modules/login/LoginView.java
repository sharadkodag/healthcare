package com.skhealthcare.modules.login;

import com.skhealthcare.entity.Department;
import com.skhealthcare.entity.Doctor;
import com.skhealthcare.entity.Patient;
import com.skhealthcare.mvputil.BaseView;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.HeaderRow;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

@Route("receptionist")
@UIScope
@SpringComponent
public class LoginView extends BaseView<LoginPresenter> {

    @Autowired
    LoginPresenter loginPresenter;

    @PostConstruct
    public void init(){
        removeAll();
        setPadding(false);
        setAlignItems(Alignment.CENTER);
        H1 heading = new H1("SK Healthcare");
        heading.getStyle().set("background-color", "green").set("padding-left","7%").set("padding-top","1%")
                .set("font-weight", "bold").set("color", "white").set("font-size","xx-larger").set("margin-top","0px");
        add(heading);
        heading.setWidth(93,Unit.PERCENTAGE);
        setSizeFull();
        setPadding(false);
        getStyle().set("b   ackground-image", "url('images/Untitled.png')").set("background-repeat", "no-repeat")
                .set("background-position", "center").set("background-size","cover");

        addLayout();
    }

    public void addLayout(){

        Label label = new Label("Receptionist Portal");
        label.getStyle().set("background-color", "blue").set("padding-left","40%")
                .set("color","white").set("font-weight", "bold").set("font-size", "xx-large");
        label.setWidth(60, Unit.PERCENTAGE);
        add(label);

        receptionistTab();

    }

    public void receptionistTab(){
        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.setSizeFull();
        verticalLayout.getStyle().set("background-color","white");

        Tabs tabs = new Tabs();
        tabs.setWidthFull();
        tabs.getStyle().set("background-color", "white");
        Tab tab = new Tab("Patient Details");
        tab.getStyle().set("background-color","white");
        Tab tab1 = new Tab("Doctor Details");
        tab.getStyle().set("background-color","white");
        tabs.add(tab, tab1);
        verticalLayout.add(tabs);
        add(verticalLayout);

        Label label = new Label("Copyright @Direction Software");
        label.setWidth(55, Unit.PERCENTAGE);
        label.getStyle().set("background-color", "black").set("padding-left","45%")
                .set("font-weight", "bold").set("color", "white");
        add(label);

        VerticalLayout verticalLayout1 = new VerticalLayout();
        verticalLayout1.add(addPatientFields());

        verticalLayout.add(verticalLayout1);
        tabs.addSelectedChangeListener(event -> {
            if(event.getSelectedTab().equals(tab)){
                verticalLayout1.removeAll();
                verticalLayout1.add(addPatientFields());
            }else if (event.getSelectedTab().equals(tab1)){
                verticalLayout1.removeAll();
            }
        });

    }

    public VerticalLayout addPatientFields(){

        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.setSizeFull();
        verticalLayout.setAlignItems(Alignment.CENTER);

        IntegerField idField = new IntegerField("Id");
        idField.setEnabled(false);
        TextField addressField = new TextField("Address");
        addressField.setEnabled(false);
        IntegerField ageField = new IntegerField("Age");
        ageField.setEnabled(false);
        TextField nameField = new TextField("Name");
        nameField.setEnabled(false);
        ComboBox<Doctor> doctorComboBox = new ComboBox<>();
        doctorComboBox.setEnabled(false);
        doctorComboBox.setLabel("Doctor");
        doctorComboBox.setItemLabelGenerator(d -> {
            return d.getFirstName() + " " + d.getLastName() + " (" + d.getDepartment() + ")";
        });
        TextField mobileField = new TextField("Address");
        mobileField.setEnabled(false);

        Binder<Patient> binder = new Binder<>();
        binder.setBean(new Patient());
        binder.forField(idField).bind(Patient::getId, Patient::setAge);
        binder.forField(addressField).bind(Patient::getAddress, Patient::setAddress);
        binder.forField(ageField).bind(Patient::getAge, Patient::setAge);
        binder.forField(nameField).bind(Patient::getFullName, Patient::setFullName);
        binder.forField(doctorComboBox).bind(Patient::getDoctor, Patient::setDoctor);
        binder.forField(mobileField).bind(Patient::getMobileNumber, Patient::setMobileNumber);

        Grid<Patient> patientGrid = new Grid<>();
        if(loginPresenter.getAllPatient()!=null) {
            patientGrid.setItems(loginPresenter.getAllPatient());
        }
        Grid.Column<Patient> nameColumn = patientGrid.addColumn(Patient::getFullName).setHeader("Name");
        Grid.Column<Patient> addressColumn = patientGrid.addColumn(Patient::getAddress).setHeader("Address");
        Grid.Column<Patient> doctorNameColumn = patientGrid.addColumn(Patient::getDoctor).setHeader("Doctor Name");
        Grid.Column<Patient> departmentColumn = patientGrid.addColumn(p -> {
            Doctor doctor = p.getDoctor();
            return doctor.getDepartment();
        }).setHeader("Department");
        HeaderRow headerRow = patientGrid.appendHeaderRow();

        TextField nameFilter = new TextField();
        nameFilter.setPlaceholder("Name");
        TextField addressFilter = new TextField();
        addressFilter.setPlaceholder("Address");
        ComboBox<Doctor> doctorFilter = new ComboBox<>();
        if (loginPresenter.getAllDoctor()!=null) {
            doctorFilter.setItems(loginPresenter.getAllDoctor());
        }
        doctorFilter.setPlaceholder("Doctor");
        ComboBox<Department> departmentFilter = new ComboBox<>();
        departmentFilter.setPlaceholder("Department");
        if (loginPresenter.getAllDepartment()!=null) {
            departmentFilter.setItems(loginPresenter.getAllDepartment());
        }

        headerRow.getCell(nameColumn).setComponent(nameFilter);
        headerRow.getCell(addressColumn).setComponent(addressFilter);
        headerRow.getCell(doctorNameColumn).setComponent(doctorFilter);
        headerRow.getCell(departmentColumn).setComponent(departmentFilter);

        Button addButton = new Button("Add");
        Button editButton = new Button("Edit");
        Button deleteButton = new Button("Delete");

        addButton.getStyle().set("color", "white").set("background-color","green");
        editButton.getStyle().set("color", "white").set("background-color","blue");
        deleteButton.getStyle().set("color", "white").set("background-color","red");

        Button save = new Button("Save");
        save.setVisible(false);
        save.getStyle().set("color", "white").set("background-color","green");
        Button cancel = new Button("Cancel");
        cancel.setVisible(false);
        cancel.getStyle().set("color","white").set("background-color","red");

        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.add(addButton,editButton,deleteButton);

        HorizontalLayout horizontalLayout1 = new HorizontalLayout();
        VerticalLayout verticalLayout1 = new VerticalLayout();
        verticalLayout1.setSizeFull();
        VerticalLayout verticalLayout2 = new VerticalLayout();
        verticalLayout2.setSizeFull();
        verticalLayout1.add(patientGrid);
        FormLayout formLayout = new FormLayout();
        formLayout.add(idField, nameField, addressField,ageField, mobileField,doctorComboBox);
        verticalLayout2.add(formLayout);
        horizontalLayout1.add(verticalLayout1, verticalLayout2);
        verticalLayout.add(horizontalLayout, horizontalLayout1);
        horizontalLayout.setWidthFull();
        horizontalLayout1.setSizeFull();

        addButton.addClickListener(event -> {
            binder.removeBean();
            save.setVisible(true);
            cancel.setVisible(true);
            formLayout.add(save,cancel);
            idField.setEnabled(true);
            nameField.setEnabled(true);
            ageField.setEnabled(true);
            addressField.setEnabled(true);
            mobileField.setEnabled(true);
            doctorComboBox.setEnabled(true);
        });

        editButton.addClickListener(event -> {
            save.setVisible(true);
            cancel.setVisible(true);
            formLayout.add(save,cancel);
            idField.setEnabled(true);
            nameField.setEnabled(true);
            ageField.setEnabled(true);
            addressField.setEnabled(true);
            mobileField.setEnabled(true);
            doctorComboBox.setEnabled(true);
        });

        save.addClickListener(event -> {
            save.setVisible(false);
            cancel.setVisible(false);
            formLayout.remove(save,cancel);
            idField.setEnabled(false);
            nameField.setEnabled(false);
            ageField.setEnabled(false);
            addressField.setEnabled(false);
            mobileField.setEnabled(false);
            doctorComboBox.setEnabled(false);
        });
        cancel.addClickListener(event -> {
            save.setVisible(false);
            cancel.setVisible(false);
            formLayout.remove(save,cancel);
            idField.setEnabled(false);
            nameField.setEnabled(false);
            ageField.setEnabled(false);
            addressField.setEnabled(false);
            mobileField.setEnabled(false);
            doctorComboBox.setEnabled(false);
        });

        return verticalLayout;
    }





}
