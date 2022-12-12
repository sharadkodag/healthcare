package com.skhealthcare.modules.adminview.tabs;

import com.skhealthcare.entity.Appointment;
import com.skhealthcare.entity.Department;
import com.skhealthcare.entity.Staff;
import com.skhealthcare.modules.adminview.AdminPresenter;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.HeaderRow;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.util.*;

@UIScope
@SpringComponent
@Route("demo")
public class Patient extends VerticalLayout {

    @Autowired
    AdminPresenter adminPresenter;
    HorizontalLayout patientButtonLayout;
    VerticalLayout patientGridLayout;
    VerticalLayout patientFieldsLayout;
    Button addButton;
    Button editButton;
    Button deleteButton;
    Binder<com.skhealthcare.entity.Patient> patientBinder;
    Grid<com.skhealthcare.entity.Patient> patientGrid;
    Button cancelButton;
    Button saveButton;
    List<com.skhealthcare.entity.Patient> allPatient;
    TextField nameFilter;
    TextField addressFilter;
    ComboBox<Staff> staffFilter;
    ComboBox<Department> departmentFilter;
    HorizontalLayout horizontalLayout = new HorizontalLayout();

    public Patient(){

        setSizeFull();
        patientGridLayout = new VerticalLayout();
        patientFieldsLayout = new VerticalLayout();
        patientButtonLayout = new HorizontalLayout();
        addButton = new Button("Add");
        editButton = new Button("Edit");
        deleteButton = new Button("Delete");
        patientBinder = new Binder<>();
        cancelButton = new Button("Cancel");
        saveButton = new Button("Save");
        allPatient = new ArrayList<>();
        patientGrid = new Grid<>();
        nameFilter = new TextField();
        addressFilter = new TextField();
        staffFilter = new ComboBox<>();
        departmentFilter = new ComboBox<>();

        patientButtonLayout.add(addButton, editButton, deleteButton);

    }

    @PostConstruct
    public void init(){
        addPatientFieldLayout();
        addPatientGridLayout();

        addButton.getStyle().set("color", "white").set("background-color","green");
        editButton.getStyle().set("color", "white").set("background-color","blue");
        deleteButton.getStyle().set("color", "white").set("background-color","red");
        patientGridLayout.getStyle().set("width","60%");
        patientFieldsLayout.getStyle().set("width","40%");

        horizontalLayout.setWidthFull();
        horizontalLayout.add(patientGridLayout, patientFieldsLayout);
        add(patientButtonLayout, horizontalLayout);
    }

    public void addPatientGridLayout(){

        allPatient = adminPresenter.getAllPatient();
        if(allPatient !=null) {
            patientGrid.setItems(allPatient);
        }
        Grid.Column<com.skhealthcare.entity.Patient> nameColumn = patientGrid.addColumn(com.skhealthcare.entity.Patient::getFullName).setHeader("Name");
        Grid.Column<com.skhealthcare.entity.Patient> addressColumn = patientGrid.addColumn(com.skhealthcare.entity.Patient::getAddress).setHeader("Address");
        Grid.Column<com.skhealthcare.entity.Patient> doctorNameColumn = patientGrid.addColumn(p ->{
            return p.getDoctor().getFirstName() + " " + p.getDoctor().getLastName();
        }).setHeader("Doctor Name");
        Grid.Column<com.skhealthcare.entity.Patient> departmentColumn = patientGrid.addColumn(p -> {
            return p.getDoctor().getDepartment().getDeptName();
        }).setHeader("Department");

        HeaderRow headerRow = patientGrid.appendHeaderRow();

        patientGrid.addSelectionListener(event -> {
            Set<com.skhealthcare.entity.Patient> allSelectedItems = event.getAllSelectedItems();
            Iterator<com.skhealthcare.entity.Patient> iterator = allSelectedItems.iterator();
            if(allSelectedItems.size()>0) {
                patientBinder.readBean(iterator.next());
            }
        });

        nameFilter.setPlaceholder("Name");
        addressFilter.setPlaceholder("Address");
        staffFilter.setPlaceholder("Doctor");
        departmentFilter.setPlaceholder("Department");

        staffFilter.setItemLabelGenerator(d ->{
            return d.getFirstName() + " " + d.getLastName();
        });

        if (adminPresenter.getAllDoctor()!=null) {
            staffFilter.setItems(adminPresenter.getAllDoctor());
        }
        if (adminPresenter.getAllDepartment()!=null) {
            departmentFilter.setItems(adminPresenter.getAllDepartment());
        }

        departmentFilter.setItemLabelGenerator(Department::getDeptName);

        nameFilter.setValueChangeMode(ValueChangeMode.LAZY);
        addressFilter.setValueChangeMode(ValueChangeMode.LAZY);

        nameFilter.addValueChangeListener(e -> getPatientFilter());
        addressFilter.addValueChangeListener(e -> getPatientFilter());
        staffFilter.addValueChangeListener(e -> getPatientFilter());
        departmentFilter.addValueChangeListener(e -> getPatientFilter());

        headerRow.getCell(nameColumn).setComponent(nameFilter);
        headerRow.getCell(addressColumn).setComponent(addressFilter);
        headerRow.getCell(doctorNameColumn).setComponent(staffFilter);
        headerRow.getCell(departmentColumn).setComponent(departmentFilter);

        patientGridLayout.add(patientGrid);
    }

    private void getPatientFilter() {

        ListDataProvider<com.skhealthcare.entity.Patient> dataProvider = (ListDataProvider<com.skhealthcare.entity.Patient>) patientGrid.getDataProvider();
        dataProvider.setFilter(e ->{
            boolean b1 = e.getFullName().toLowerCase().contains(nameFilter.getValue().toLowerCase());
            boolean b2 = e.getAddress().toLowerCase().contains(addressFilter.getValue().toLowerCase());
            boolean b3 = e.getDoctor().getDepartment().getDeptName().equals((departmentFilter.getValue()==null) ? null : departmentFilter.getValue().getDeptName());
            boolean b4 = e.getDoctor().getFirstName().equals(staffFilter.getValue()==null ? null : staffFilter.getValue().getFirstName())
                    || e.getDoctor().getLastName().equals(staffFilter.getValue() == null ? null : staffFilter.getValue().getLastName());

            if(departmentFilter.getValue()== null || staffFilter.getValue()==null){
                if(departmentFilter.getValue()== null && staffFilter.getValue()==null){
                    return b1 && b2;
                }else if(staffFilter.getValue()==null){
                    return b1 && b2 && b3;
                }else {
                    return b1 && b2 && b4;
                }
            }
            return b1 && b2 && b3 && b4;
        });

    }

    @Transactional
    public void addPatientFieldLayout(){

        IntegerField idField = new IntegerField("Id");
        TextField addressField = new TextField("Address");
        IntegerField ageField = new IntegerField("Age");
        TextField nameField = new TextField("Name");
        ComboBox<Staff> staffComboBox = new ComboBox<>();
        TextField mobileField = new TextField("Mobile Number");
        FormLayout formLayout = new FormLayout();
        cancelButton = new Button("Cancel");
        saveButton = new Button("Save");

        saveButton.setVisible(false);
        cancelButton.setVisible(false);

        saveButton.getStyle().set("color", "white").set("background-color","green");
        cancelButton.getStyle().set("color","white").set("background-color","red");

        staffComboBox.setLabel("Doctor");
        idField.setEnabled(false);
        addressField.setEnabled(false);
        ageField.setEnabled(false);
        nameField.setEnabled(false);
        staffComboBox.setEnabled(false);
        mobileField.setEnabled(false);

        staffComboBox.setItems(adminPresenter.getAllDoctor());
        staffComboBox.setItemLabelGenerator(d -> {
            return d.getFirstName() + " " + d.getLastName() + " (" + d.getDepartment().getDeptName() + ")";
        });

        patientBinder = new Binder<>();
        patientBinder.setBean(new com.skhealthcare.entity.Patient());
        patientBinder.forField(idField).bind(com.skhealthcare.entity.Patient::getId, com.skhealthcare.entity.Patient::setAge);
        patientBinder.forField(addressField).withValidator(s->!s.equals(""),"Please enter address")
                .bind(com.skhealthcare.entity.Patient::getAddress, com.skhealthcare.entity.Patient::setAddress);
        patientBinder.forField(ageField).withValidator(Objects::nonNull,"Please enter age")
                .bind(com.skhealthcare.entity.Patient::getAge, com.skhealthcare.entity.Patient::setAge);
        patientBinder.forField(nameField).withValidator(s->!s.equals(""),"Please enter name")
                .bind(com.skhealthcare.entity.Patient::getFullName, com.skhealthcare.entity.Patient::setFullName);
        patientBinder.forField(staffComboBox).withValidator(Objects::nonNull,"Please select")
                .bind(com.skhealthcare.entity.Patient::getDoctor, com.skhealthcare.entity.Patient::setDoctor);
        patientBinder.forField(mobileField).withValidator(s-> s.length() == 10,"Please enter valid number")
                .bind(com.skhealthcare.entity.Patient::getMobileNumber, com.skhealthcare.entity.Patient::setMobileNumber);

        addButton.addClickListener(event -> {
            patientBinder.removeBean();
            patientBinder.readBean(new com.skhealthcare.entity.Patient());
            saveButton.setVisible(true);
            cancelButton.setVisible(true);
            formLayout.add(saveButton,cancelButton);
            nameField.setEnabled(true);
            ageField.setEnabled(true);
            addressField.setEnabled(true);
            mobileField.setEnabled(true);
            staffComboBox.setEnabled(true);
        });
        editButton.addClickListener(event -> {
            Set<com.skhealthcare.entity.Patient> selectedItems = patientGrid.getSelectedItems();
            if(!selectedItems.isEmpty()){
                saveButton.setVisible(true);
                cancelButton.setVisible(true);
                formLayout.add(saveButton,cancelButton);
                nameField.setEnabled(true);
                ageField.setEnabled(true);
                addressField.setEnabled(true);
                mobileField.setEnabled(true);
                staffComboBox.setEnabled(true);
            }else {
                Notification notification = Notification.show("Please select patient", 3000, Notification.Position.TOP_CENTER);
                notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
            }
        });
        saveButton.addClickListener(event -> {
            patientBinder.validate();
            if(patientBinder.isValid()){
                com.skhealthcare.entity.Patient bean = new com.skhealthcare.entity.Patient();
                try {
                    patientBinder.writeBean(bean);
                } catch (ValidationException e) {
                    throw new RuntimeException(e);
                }
                if(idField.getValue()!=null){
                    com.skhealthcare.entity.Patient patientById = adminPresenter.getPatientById(idField.getValue());
                    try {
                        patientBinder.writeBean(patientById);
//                        adminPresenter.addPatient(patientById);
                        for(com.skhealthcare.entity.Patient patient1 : allPatient){
                            if(Objects.equals(patient1.getId(), patientById.getId())){
                                allPatient.set(allPatient.indexOf(patient1),patientById);
                            }
                        }
                    } catch (ValidationException e) {
                        throw new RuntimeException(e);
                    }
                }else {
                    adminPresenter.addPatient(bean);
                    allPatient.add(bean);
                }
                patientGrid.getDataProvider().refreshAll();
                Notification.show("Patient Added", 2000, Notification.Position.TOP_CENTER);

                saveButton.setVisible(false);
                cancelButton.setVisible(false);
                formLayout.remove(saveButton,cancelButton);
                nameField.setEnabled(false);
                ageField.setEnabled(false);
                addressField.setEnabled(false);
                mobileField.setEnabled(false);
                staffComboBox.setEnabled(false);
            }
        });
        cancelButton.addClickListener(event -> {
            saveButton.setVisible(false);
            cancelButton.setVisible(false);
            formLayout.remove(saveButton,cancelButton);
            nameField.setEnabled(false);
            ageField.setEnabled(false);
            addressField.setEnabled(false);
            mobileField.setEnabled(false);
            staffComboBox.setEnabled(false);
        });
        deleteButton.addClickListener(event -> {
            Set<com.skhealthcare.entity.Patient> selectedItems = patientGrid.getSelectedItems();
            if(!selectedItems.isEmpty()){
                Iterator<com.skhealthcare.entity.Patient> iterator = selectedItems.iterator();
                com.skhealthcare.entity.Patient patient = iterator.next();
                adminPresenter.deletePatient(patient);
                allPatient.remove(patient);
                Notification notification = Notification.show("Patient deleted successfully", 3000, Notification.Position.TOP_CENTER);
                notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
                patientGrid.getDataProvider().refreshAll();
            }
            else {
                Notification notification = Notification.show("Please select patient", 3000, Notification.Position.TOP_CENTER);
                notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
            }
        });

        formLayout.add(idField, nameField, addressField,ageField, mobileField,staffComboBox );
        patientFieldsLayout.add(formLayout);

    }

}
