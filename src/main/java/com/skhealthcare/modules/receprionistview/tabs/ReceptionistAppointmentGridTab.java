package com.skhealthcare.modules.receprionistview.tabs;

import com.skhealthcare.entity.Appointment;
import com.skhealthcare.entity.Department;
import com.skhealthcare.entity.Staff;
import com.skhealthcare.modules.receprionistview.ReceptionistPresenter;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
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
public class ReceptionistAppointmentGridTab extends VerticalLayout {

    @Autowired
    ReceptionistPresenter receptionistPresenter;
    HorizontalLayout appointmentButtonLayout;;
    VerticalLayout appointmentGridLayout;
    List<com.skhealthcare.entity.Patient> allPatient;
    Grid<Appointment> appointmentGrid;
    List<Appointment> appointmentList;
    Binder<Appointment> appointmentBinder;
    VerticalLayout verticalLayout;
    Dialog dialog;

    @PostConstruct
    public void init(){
        appointmentGridLayout = new VerticalLayout();
        appointmentButtonLayout = new HorizontalLayout();
        allPatient = new ArrayList<>();
        appointmentGrid = new Grid<>();
        appointmentList = new ArrayList<>();
        appointmentBinder = new Binder<>();;
        verticalLayout = new VerticalLayout();
        dialog = new Dialog();

        setMargin(false);
        setPadding(false);
        setSizeFull();

        dialog.setWidth(30, Unit.PERCENTAGE);

        addAppointmentGridLayout();
        addAppointmentFieldLayout();
        add(verticalLayout);

    }

    public void addAppointmentGridLayout(){

        appointmentList = receptionistPresenter.getAllAppointment();
        if(allPatient !=null) {
            appointmentGrid.setItems(appointmentList);
        }
        Grid.Column<Appointment> idColumn = appointmentGrid.addColumn(Appointment::getId).setHeader("Id");
        Grid.Column<Appointment> nameColumn = appointmentGrid.addColumn(Appointment::getName).setHeader("Name");
        Grid.Column<Appointment> doctorNameColumn = appointmentGrid.addColumn(p ->{
            return p.getDoctor().getFirstName() + " " + p.getDoctor().getLastName();
        }).setHeader("Doctor Name");
        Grid.Column<Appointment> departmentColumn = appointmentGrid.addColumn(p -> {
            return p.getDoctor().getDepartment().getDeptName();
        }).setHeader("Department");
        Grid.Column<Appointment> addressColumn = appointmentGrid.addColumn(Appointment::getAddress).setHeader("Address");
        Grid.Column<Appointment> ageColumn = appointmentGrid.addColumn(Appointment::getAge).setHeader("Age");
        Grid.Column<Appointment> descriptionColumn = appointmentGrid.addColumn(Appointment::getDescription).setHeader("Description");
        Grid.Column<Appointment> timeColumn = appointmentGrid.addColumn(Appointment::getTime).setHeader("Time");

        HeaderRow headerRow = appointmentGrid.appendHeaderRow();

        appointmentGrid.addSelectionListener(event -> {
            Set<Appointment> allSelectedItems = event.getAllSelectedItems();
            Iterator<Appointment> iterator = allSelectedItems.iterator();
            if(allSelectedItems.size()>0) {
                appointmentBinder.readBean(iterator.next());
            }
        });

        TextField idFilter = new TextField();
        TextField nameFilter = new TextField();
        ComboBox<Staff> doctorFilter = new ComboBox<>();
        ComboBox<Department> departmentFilter = new ComboBox<>();
        TextField addressFilter = new TextField();
        TextField ageFilter = new TextField();
        TextField descriptionFilter = new TextField();
        TextField timeFilter = new TextField();

        idFilter.setPlaceholder("Id");
        nameFilter.setPlaceholder("Name");
        addressFilter.setPlaceholder("Address");
        ageFilter.setPlaceholder("Age");
        descriptionFilter.setPlaceholder("Description");
        doctorFilter.setPlaceholder("Doctor");
        departmentFilter.setPlaceholder("Department");
        timeFilter.setPlaceholder("Time");

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

        idFilter.setValueChangeMode(ValueChangeMode.LAZY);
        nameFilter.setValueChangeMode(ValueChangeMode.LAZY);
        addressFilter.setValueChangeMode(ValueChangeMode.LAZY);
        ageFilter.setValueChangeMode(ValueChangeMode.LAZY);
        descriptionFilter.setValueChangeMode(ValueChangeMode.LAZY);
        timeFilter.setValueChangeMode(ValueChangeMode.LAZY);

        idFilter.addValueChangeListener(e -> getAppointmentFilter(idFilter, ageFilter, descriptionFilter,
                nameFilter, addressFilter, doctorFilter, departmentFilter, timeFilter));
        ageFilter.addValueChangeListener(e -> getAppointmentFilter(idFilter, ageFilter, descriptionFilter,
                nameFilter, addressFilter, doctorFilter, departmentFilter, timeFilter));
        descriptionFilter.addValueChangeListener(e -> getAppointmentFilter(idFilter, ageFilter, descriptionFilter,
                nameFilter, addressFilter, doctorFilter, departmentFilter, timeFilter));
        nameFilter.addValueChangeListener(e -> getAppointmentFilter(idFilter, ageFilter, descriptionFilter,
                nameFilter, addressFilter, doctorFilter, departmentFilter, timeFilter));
        addressFilter.addValueChangeListener(e -> getAppointmentFilter(idFilter, ageFilter, descriptionFilter,
                nameFilter, addressFilter, doctorFilter, departmentFilter, timeFilter));
        doctorFilter.addValueChangeListener(e -> getAppointmentFilter(idFilter, ageFilter, descriptionFilter,
                nameFilter, addressFilter, doctorFilter, departmentFilter, timeFilter));
        departmentFilter.addValueChangeListener(e -> getAppointmentFilter(idFilter, ageFilter, descriptionFilter,
                nameFilter, addressFilter, doctorFilter, departmentFilter, timeFilter));
        timeFilter.addValueChangeListener(e -> getAppointmentFilter(idFilter, ageFilter, descriptionFilter,
                nameFilter, addressFilter, doctorFilter, departmentFilter, timeFilter));

        headerRow.getCell(idColumn).setComponent(idFilter);
        headerRow.getCell(timeColumn).setComponent(timeFilter);
        headerRow.getCell(ageColumn).setComponent(ageFilter);
        headerRow.getCell(descriptionColumn).setComponent(descriptionFilter);
        headerRow.getCell(nameColumn).setComponent(nameFilter);
        headerRow.getCell(addressColumn).setComponent(addressFilter);
        headerRow.getCell(doctorNameColumn).setComponent(doctorFilter);
        headerRow.getCell(departmentColumn).setComponent(departmentFilter);

        appointmentGridLayout.add(appointmentGrid);
    }

    private void getAppointmentFilter(TextField idFilter, TextField ageFilter, TextField descriptionFilter,
                                      TextField nameFilter, TextField addressFilter, ComboBox<Staff> doctorFilter,
                                      ComboBox<Department> departmentFilter, TextField timeFilter) {

        ListDataProvider<Appointment> dataProvider = (ListDataProvider<Appointment>) appointmentGrid.getDataProvider();
        dataProvider.setFilter(e ->{
            boolean b6 = String.valueOf(e.getId()).contains(idFilter.getValue());
            boolean b5 = e.getAge().contains(ageFilter.getValue());
            boolean b2 = e.getDescription().toLowerCase().contains(descriptionFilter.getValue().toLowerCase());
            boolean b1 = e.getName().toLowerCase().contains(nameFilter.getValue().toLowerCase());
            boolean b7 = e.getAddress().toLowerCase().contains(addressFilter.getValue().toLowerCase());
            boolean b4 = e.getDoctor().getFirstName().equals(doctorFilter.getValue()==null?"":doctorFilter.getValue().getFirstName());
            boolean b8 = e.getTime().contains(timeFilter.getValue());
            boolean b3 = e.getDoctor().getDepartment().getDeptName().equals(departmentFilter.getValue()==null?"":departmentFilter.getValue().getDeptName());

            if(departmentFilter.getValue()== null|| doctorFilter.getValue()== null ){
                if(departmentFilter.getValue()== null && doctorFilter.getValue()== null ){
                    return b1 && b2 && b5 && b6 && b7 && b8;
                }else if(doctorFilter.getValue()== null){
                    return b1 && b2 && b3 && b6 && b5  && b7 && b8;
                }  else {
                    return b1 && b2 && b4 && b5 && b6 && b7 && b8;
                }
            }
            return b1 && b2 && b3 && b4 && b6 && b5 && b7 && b8;
        });

    }

    public void addAppointmentFieldLayout(){

        IntegerField idField = new IntegerField("Id");
        TextField timeField = new TextField("Time");
        TextField addressField = new TextField("Address");
        TextField ageField = new TextField("Age");
        TextField nameField = new TextField("Name");
        ComboBox<Staff> doctorComboBox = new ComboBox<>();
        ComboBox<Department> departmentComboBox = new ComboBox<>();
        TextField description = new TextField("Description");
        FormLayout formLayout = new FormLayout();
        Button cancelButton = new Button("Cancel");
        Button saveButton = new Button("Save");
        Button addButton = new Button("Add");
        Button editButton = new Button("Edit");
        Button deleteButton = new Button("delete");
        Appointment bean = new Appointment();
        idField.setEnabled(false);

        saveButton.getStyle().set("color", "white").set("background-color","green");
        cancelButton.getStyle().set("color","white").set("background-color","red");
        addButton.getStyle().set("color", "white").set("background-color","green");
        editButton.getStyle().set("color", "white").set("background-color","blue");
        deleteButton.getStyle().set("color", "white").set("background-color","red");

        doctorComboBox.setLabel("Doctor");
        formLayout.setColspan(saveButton,2);
        formLayout.setColspan(cancelButton,2);


        departmentComboBox.setItems(receptionistPresenter.getAllDepartment());
        doctorComboBox.setItems(receptionistPresenter.getAllDoctor());
        doctorComboBox.setItemLabelGenerator(d -> {
            return d.getFirstName() + " " + d.getLastName() + " (" + d.getDepartment().getDeptName() + ")";
        });
        departmentComboBox.setItemLabelGenerator(Department::getDeptName);

        appointmentBinder = new Binder<>();
        appointmentBinder.setBean(new Appointment());
        appointmentBinder.forField(idField).bind(Appointment::getId, Appointment::setId);
        appointmentBinder.forField(addressField).withValidator(s->!s.equals(""),"Please enter address")
                .bind(Appointment::getAddress, Appointment::setAddress);
        appointmentBinder.forField(ageField).withValidator(s-> !s.equals(""),"Please enter age")
                .bind(Appointment::getAge, Appointment::setAge);
        appointmentBinder.forField(nameField).withValidator(s-> !s.equals(""),"Please enter name")
                .bind(Appointment::getName, Appointment::setName);
        appointmentBinder.forField(doctorComboBox).withValidator(Objects::nonNull,"Please select")
                .bind(Appointment::getDoctor, Appointment::setDoctor);
        appointmentBinder.forField(description)
                .bind(Appointment::getDescription, Appointment::setDescription);
        appointmentBinder.forField(timeField).withValidator(s-> !s.equals(""),"Please select")
                .bind(Appointment::getTime,Appointment::setTime);

        addButton.addClickListener(event -> {
            appointmentBinder.removeBean();
            appointmentBinder.readBean(new Appointment());
            dialog.open();
        });
        editButton.addClickListener(event -> {
            Set<Appointment> selectedItems = appointmentGrid.getSelectedItems();
            if(!selectedItems.isEmpty()){
                dialog.open();
            }else {
                Notification notification = Notification.show("Please select Appointment", 3000, Notification.Position.TOP_CENTER);
                notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
            }
        });
        saveButton.addClickListener(event -> {
            appointmentBinder.validate();
            if(appointmentBinder.isValid()){
                try {
                    appointmentBinder.writeBean(bean);
                } catch (ValidationException e) {
                    throw new RuntimeException(e);
                }
                if(!idField.isEmpty()){
                    Appointment appointmentById = receptionistPresenter.getAppointmentById(idField.getValue());
                    if(appointmentById == null){
                        appointmentById = new Appointment();
                    }
                    try {
                        appointmentBinder.writeBean(appointmentById);
                        receptionistPresenter.addAppointment(appointmentById);
                        for(Appointment appointment : appointmentList){
                            if(Objects.equals(appointment.getId(), appointmentById.getId())){
                                appointmentList.set(appointmentList.indexOf(appointment),appointmentById);
                            }
                        }
                        Notification.show("Appointment changed", 2000, Notification.Position.TOP_CENTER);
                    } catch (ValidationException e) {
                        throw new RuntimeException(e);
                    }
                }else {
                    receptionistPresenter.addAppointment(bean);
                    appointmentList.add(bean);
                    Notification.show("Appointment saved", 2000, Notification.Position.TOP_CENTER);
                }
                appointmentGrid.getDataProvider().refreshAll();
                dialog.close();
            }
        });
        cancelButton.addClickListener(event -> {
            appointmentBinder.removeBean();
            dialog.close();

        });
        deleteButton.addClickListener(event -> {
            Set<Appointment> selectedItems = appointmentGrid.getSelectedItems();
            if(!selectedItems.isEmpty()){
                Iterator<Appointment> iterator = selectedItems.iterator();
                Appointment appointment = iterator.next();
                receptionistPresenter.deleteAppointment(appointment);
                appointmentList.remove(appointment);
                Notification notification = Notification.show("Appointment deleted successfully", 3000, Notification.Position.TOP_CENTER);
                notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
                appointmentGrid.getDataProvider().refreshAll();
            }
            else {
                Notification notification = Notification.show("Please select appointment", 3000, Notification.Position.TOP_CENTER);
                notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
            }
        });

        appointmentButtonLayout.add(addButton, editButton,deleteButton);
        formLayout.add(idField, nameField, addressField,ageField, description, timeField,doctorComboBox ,saveButton,cancelButton);
        dialog.add(formLayout);
        verticalLayout.add(appointmentButtonLayout,appointmentGrid);
    }
}
