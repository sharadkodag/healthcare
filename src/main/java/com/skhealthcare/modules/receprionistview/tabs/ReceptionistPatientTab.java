package com.skhealthcare.modules.receprionistview.tabs;

import com.skhealthcare.entity.Department;
import com.skhealthcare.entity.Patient;
import com.skhealthcare.entity.Staff;
import com.skhealthcare.modules.receprionistview.ReceptionistPresenter;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.HeaderRow;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.*;

@UIScope
@SpringComponent
public class ReceptionistPatientTab extends VerticalLayout {

    @Autowired
    ReceptionistPresenter receptionistPresenter;
    VerticalLayout patientGridLayout;
    VerticalLayout patientFieldsLayout;
    Button addButton;
    Button editButton;
    Button deleteButton;
    Binder<Patient> patientBinder;
    Grid<Patient> patientGrid;
    Button cancelButton;
    Button saveButton;
    List<Patient> allPatient;
    TextField nameFilter;
    TextField addressFilter;
    ComboBox<Staff> doctorFilter;
    ComboBox<Department> departmentFilter;
    HorizontalLayout horizontalLayout;
    HorizontalLayout patientButtonLayout;

    @PostConstruct
    public void init(){
        patientGridLayout = new VerticalLayout();
        patientFieldsLayout = new VerticalLayout();
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
        doctorFilter = new ComboBox<>();
        departmentFilter = new ComboBox<>();
        horizontalLayout = new HorizontalLayout();
        patientButtonLayout = new HorizontalLayout();

        horizontalLayout.setWidthFull();
        patientGridLayout.getStyle().set("width","60%");
        patientFieldsLayout.getStyle().set("width","40%");
        addButton.getStyle().set("color", "white").set("background-color","green");
        editButton.getStyle().set("color", "white").set("background-color","blue");
        deleteButton.getStyle().set("color", "white").set("background-color","red");
        saveButton.getStyle().set("color", "white").set("background-color","green");
        cancelButton.getStyle().set("color","white").set("background-color","red");

        addPatientGridLayout();
        addPatientFieldLayout();

        setMargin(false);
        setPadding(false);
        setSizeFull();

        patientButtonLayout.add(addButton, editButton, deleteButton);
        horizontalLayout.add(patientGridLayout, patientFieldsLayout);
        add(patientButtonLayout, horizontalLayout);
    }

    public void addPatientGridLayout(){

        allPatient = receptionistPresenter.getAllPatient();
        if(allPatient !=null) {
            patientGrid.setItems(allPatient);
        }
        Grid.Column<Patient> nameColumn = patientGrid.addColumn(Patient::getFullName).setHeader("Name");
        Grid.Column<Patient> addressColumn = patientGrid.addColumn(Patient::getAddress).setHeader("Address");
        Grid.Column<Patient> doctorNameColumn = patientGrid.addColumn(p ->{
            return p.getDoctor().getFirstName() + " " + p.getDoctor().getLastName();
        }).setHeader("Doctor Name");
        Grid.Column<Patient> departmentColumn = patientGrid.addColumn(p -> {
            return p.getDoctor().getDepartment().getDeptName();
        }).setHeader("Department");

        HeaderRow headerRow = patientGrid.appendHeaderRow();

        patientGrid.addSelectionListener(event -> {
            Set<Patient> allSelectedItems = event.getAllSelectedItems();
            Iterator<Patient> iterator = allSelectedItems.iterator();
            patientBinder.readBean(iterator.next());
        });

        nameFilter.setPlaceholder("Name");
        addressFilter.setPlaceholder("Address");
        doctorFilter.setPlaceholder("Doctor");
        departmentFilter.setPlaceholder("Department");

        doctorFilter.setItemLabelGenerator(d ->{
            return d.getFirstName() + " " + d.getLastName();
        });

        if (receptionistPresenter.getAllDoctor()!=null) {
            doctorFilter.setItems(receptionistPresenter.getAllDoctor());
        }
        if (receptionistPresenter.getAllDepartment()!=null) {
            departmentFilter.setItems(receptionistPresenter.getAllDepartment());
        }

        departmentFilter.setItemLabelGenerator(Department::getDeptName);

        nameFilter.setValueChangeMode(ValueChangeMode.LAZY);
        addressFilter.setValueChangeMode(ValueChangeMode.LAZY);

        nameFilter.addValueChangeListener(e -> getPatientFilter());
        addressFilter.addValueChangeListener(e -> getPatientFilter());
        doctorFilter.addValueChangeListener(e -> getPatientFilter());
        departmentFilter.addValueChangeListener(e -> getPatientFilter());

        headerRow.getCell(nameColumn).setComponent(nameFilter);
        headerRow.getCell(addressColumn).setComponent(addressFilter);
        headerRow.getCell(doctorNameColumn).setComponent(doctorFilter);
        headerRow.getCell(departmentColumn).setComponent(departmentFilter);

        patientGridLayout.add(patientGrid);
    }

    private void getPatientFilter() {

        ListDataProvider<Patient> dataProvider = (ListDataProvider<Patient>) patientGrid.getDataProvider();
        dataProvider.setFilter(e ->{
            boolean b1 = e.getFullName().toLowerCase().contains(nameFilter.getValue().toLowerCase());
            boolean b2 = e.getAddress().toLowerCase().contains(addressFilter.getValue().toLowerCase());
            boolean b3 = e.getDoctor().getDepartment().getDeptName().equals((departmentFilter.getValue()==null) ? null : departmentFilter.getValue().getDeptName());
            boolean b4 = e.getDoctor().getFirstName().equals(doctorFilter.getValue()==null ? null : doctorFilter.getValue().getFirstName())
                    || e.getDoctor().getLastName().equals(doctorFilter.getValue() == null ? null : doctorFilter.getValue().getLastName());

            if(departmentFilter.getValue()== null || doctorFilter.getValue()==null){
                if(departmentFilter.getValue()== null && doctorFilter.getValue()==null){
                    return b1 && b2;
                }else if(doctorFilter.getValue()==null){
                    return b1 && b2 && b3;
                }else {
                    return b1 && b2 && b4;
                }
            }
            return b1 && b2 && b3 && b4;
        });
    }

    public void addPatientFieldLayout(){

        IntegerField idField = new IntegerField("Id");
        TextField addressField = new TextField("Address");
        IntegerField ageField = new IntegerField("Age");
        TextField nameField = new TextField("Name");
        ComboBox<Staff> doctorComboBox = new ComboBox<>();
        TextField mobileField = new TextField("Mobile Number");
        FormLayout formLayout = new FormLayout();
        cancelButton = new Button("Cancel");
        saveButton = new Button("Save");

        saveButton.setVisible(false);
        cancelButton.setVisible(false);

        saveButton.getStyle().set("color", "white").set("background-color","green");
        cancelButton.getStyle().set("color","white").set("background-color","red");

        doctorComboBox.setLabel("Doctor");
        idField.setEnabled(false);
        addressField.setEnabled(false);
        ageField.setEnabled(false);
        nameField.setEnabled(false);
        doctorComboBox.setEnabled(false);
        mobileField.setEnabled(false);

        doctorComboBox.setItems(receptionistPresenter.getAllDoctor());
        doctorComboBox.setItemLabelGenerator(d -> {
            return d.getFirstName() + " " + d.getLastName() + " (" + d.getDepartment().getDeptName() + ")";
        });

        patientBinder = new Binder<>();
        patientBinder.setBean(new Patient());
        patientBinder.forField(idField).bind(Patient::getId, Patient::setAge);
        patientBinder.forField(addressField).withValidator(s->!s.equals(""),"Please enter address")
                .bind(Patient::getAddress, Patient::setAddress);
        patientBinder.forField(ageField).withValidator(Objects::nonNull,"Please enter age")
                .bind(Patient::getAge, Patient::setAge);
        patientBinder.forField(nameField).withValidator(s->!s.equals(""),"Please enter name")
                .bind(Patient::getFullName, Patient::setFullName);
        patientBinder.forField(doctorComboBox).withValidator(Objects::nonNull,"Please select")
                .bind(Patient::getDoctor, Patient::setDoctor);
        patientBinder.forField(mobileField).withValidator(s-> s.length() == 10,"Please enter valid number")
                .bind(Patient::getMobileNumber, Patient::setMobileNumber);

        addButton.addClickListener(event -> {
            patientBinder.removeBean();
            patientBinder.readBean(new Patient());
            saveButton.setVisible(true);
            cancelButton.setVisible(true);
            formLayout.add(saveButton,cancelButton);
            nameField.setEnabled(true);
            ageField.setEnabled(true);
            addressField.setEnabled(true);
            mobileField.setEnabled(true);
            doctorComboBox.setEnabled(true);
        });
        editButton.addClickListener(event -> {
            Set<Patient> selectedItems = patientGrid.getSelectedItems();
            if(!selectedItems.isEmpty()){
                saveButton.setVisible(true);
                cancelButton.setVisible(true);
                formLayout.add(saveButton,cancelButton);
                nameField.setEnabled(true);
                ageField.setEnabled(true);
                addressField.setEnabled(true);
                mobileField.setEnabled(true);
                doctorComboBox.setEnabled(true);
            }else {
                Notification notification = Notification.show("Please select patient", 3000, Notification.Position.TOP_CENTER);
                notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
            }
        });
        saveButton.addClickListener(event -> {
            patientBinder.validate();
            if(patientBinder.isValid()){
                Patient bean = new Patient();
                try {
                    patientBinder.writeBean(bean);
                } catch (ValidationException e) {
                    throw new RuntimeException(e);
                }
                if(idField.getValue()!=null){
                    Patient patientById = receptionistPresenter.getPatientById(idField.getValue());
                    try {
                        patientBinder.writeBean(patientById);
                        receptionistPresenter.addPatient(patientById);
                        for(Patient patient1 : allPatient){
                            if(Objects.equals(patient1.getId(), patientById.getId())){
                                allPatient.set(allPatient.indexOf(patient1),patientById);
                            }
                        }
                        Notification.show("Patient Updated", 2000, Notification.Position.TOP_CENTER);
                    } catch (ValidationException e) {
                        throw new RuntimeException(e);
                    }
                }else {
                    receptionistPresenter.addPatient(bean);
                    allPatient.add(bean);
                    Notification.show("Patient Added", 2000, Notification.Position.TOP_CENTER);
                }
                patientGrid.getDataProvider().refreshAll();

                saveButton.setVisible(false);
                cancelButton.setVisible(false);
                formLayout.remove(saveButton,cancelButton);
                nameField.setEnabled(false);
                ageField.setEnabled(false);
                addressField.setEnabled(false);
                mobileField.setEnabled(false);
                doctorComboBox.setEnabled(false);
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
            doctorComboBox.setEnabled(false);
        });
        deleteButton.addClickListener(event -> {
            Set<Patient> selectedItems = patientGrid.getSelectedItems();
            if(!selectedItems.isEmpty()){
                Iterator<Patient> iterator = selectedItems.iterator();
                Patient patient = iterator.next();
                receptionistPresenter.deletePatient(patient);
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

        formLayout.add(idField, nameField, addressField,ageField, mobileField,doctorComboBox );
        patientFieldsLayout.add(formLayout);
    }

}
