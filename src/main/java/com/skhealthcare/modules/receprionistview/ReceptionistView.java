package com.skhealthcare.modules.receprionistview;

import com.skhealthcare.entity.Appointment;
import com.skhealthcare.entity.Department;
import com.skhealthcare.entity.Staff;
import com.skhealthcare.entity.Patient;
import com.skhealthcare.modules.adminview.tabs.AppointmentGridTab;
import com.skhealthcare.modules.doctorview.tabs.PatientTab;
import com.skhealthcare.modules.homeview.HomepageView;
import com.skhealthcare.modules.homeview.Template;
import com.skhealthcare.modules.receprionistview.tabs.DoctorDetails;
import com.skhealthcare.mvputil.BaseView;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.HeaderRow;
import com.vaadin.flow.component.html.Label;
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
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

import javax.annotation.PostConstruct;
import java.util.*;

@UIScope
@SpringComponent
@Route(value = "receptionist", layout = Template.class)
public class ReceptionistView extends BaseView<ReceptionistPresenter> {

//    @Autowired
//    ReceptionistPresenter receptionistPresenter;
    @Autowired
    AppointmentGridTab appointmentGridTab;
    @Autowired
    PatientTab patientTab;
    @Autowired
    DoctorDetails doctorDetail;
    VerticalLayout tabsLayout;
    HorizontalLayout patientButtonLayout;
    HorizontalLayout appointmentButtonLayout;
    HorizontalLayout gridAndFieldsLayout;

    VerticalLayout patientGridLayout;
//    VerticalLayout appointmentGridLayout;
    VerticalLayout patientFieldsLayout;
    VerticalLayout appointmentFieldLayout;
    Tabs tabs;
    Tab patientDetails;
    Tab doctorDetails;
    Tab appointmentDetails;
    Tab addAppointment;
    Button addButton;
    Button editButton;
    Button deleteButton;
//    Binder<Patient> patientBinder;
//    Grid<Patient> patientGrid;
    Button cancelButton;
    Button saveButton;
//    List<Patient> allPatient;
//    TextField nameFilter;
//    TextField addressFilter;
//    ComboBox<Staff> doctorFilter;
//    ComboBox<Department> departmentFilter;
//    Grid<Appointment> appointmentGrid;
//    List<Appointment> appointmentList;
//    Binder<Appointment> appointmentBinder;
    Button logoutButton;

    @Override
    public void beforeEnter(BeforeEnterEvent observer){
        getPresenter().beforeEnter(observer);
    }

    @Override
    public void init(){
        tabsLayout = new VerticalLayout();
        tabs = new Tabs();
        patientDetails = new Tab();
        appointmentDetails = new Tab();
        addAppointment = new Tab();
        gridAndFieldsLayout = new HorizontalLayout();
        patientGridLayout = new VerticalLayout();
        patientFieldsLayout = new VerticalLayout();
//        appointmentGridLayout = new VerticalLayout();
        appointmentFieldLayout = new VerticalLayout();
        patientButtonLayout = new HorizontalLayout();
        appointmentButtonLayout = new HorizontalLayout();
        addButton = new Button("Add");
        editButton = new Button("Edit");
        deleteButton = new Button("Delete");
        doctorDetails = new Tab();
//        patientBinder = new Binder<>();
        cancelButton = new Button("Cancel");
        saveButton = new Button("Save");
//        allPatient = new ArrayList<>();
//        patientGrid = new Grid<>();
//        nameFilter = new TextField();
//        addressFilter = new TextField();
//        doctorFilter = new ComboBox<>();
//        departmentFilter = new ComboBox<>();
//        appointmentGrid = new Grid<>();
//        appointmentList = new ArrayList<>();
//        appointmentBinder = new Binder<>();
        logoutButton = new Button("Logout");

        setMargin(false);
        setPadding(false);
        setSizeFull();

        appointmentFieldLayout.setSizeFull();
        appointmentFieldLayout.getStyle().set("background-color","white");
        appointmentButtonLayout.setVisible(false);
        logoutButton.getStyle().set("background-color", "red").set("color","white");

        addLayout();
        addMenuBarLayout();
    }

    public void addLayout(){
        Label label = new Label("Receptionist Portal");
        VerticalLayout verticalLayout = new VerticalLayout();

        label.getStyle().set("background-color", "blue").set("padding-left","45%")
                .set("color","white").set("font-weight", "bold").set("font-size", "xx-large");
        label.setWidth(55, Unit.PERCENTAGE);
        verticalLayout.setAlignItems(Alignment.END);
        logoutButton.getStyle().set("background-color","red").set("color","white");

        logoutButton.addClickListener(event -> {

            logoutButton.getUI().ifPresent(evt -> evt.navigate(HomepageView.class) );
            Notification.show("Successfully logout");
            VaadinSession.getCurrent().close();

        });

        verticalLayout.add(logoutButton);
        add(verticalLayout);
        add(label);
    }

    public void addMenuBarLayout(){
        patientDetails.setLabel("Patient Details");
        doctorDetails.setLabel("Doctor Details");
        addAppointment.setLabel("Add Appointment");
        appointmentDetails.setLabel("Appointment Details");

        tabsLayout.setSizeFull();
        tabsLayout.getStyle().set("background-color", "white");
        gridAndFieldsLayout.setSizeFull();
        gridAndFieldsLayout.getStyle().set("background-color", "white");
        patientGridLayout.getStyle().set("width","60%");
        patientFieldsLayout.getStyle().set("width","40%");
        addButton.getStyle().set("color", "white").set("background-color","green");
        editButton.getStyle().set("color", "white").set("background-color","blue");
        deleteButton.getStyle().set("color", "white").set("background-color","red");
        saveButton.getStyle().set("color", "white").set("background-color","green");
        cancelButton.getStyle().set("color","white").set("background-color","red");

        tabsLayout.add(tabs, patientButtonLayout,appointmentButtonLayout,gridAndFieldsLayout);
        tabs.add(patientDetails,doctorDetails,appointmentDetails);
        patientButtonLayout.add(addButton, editButton, deleteButton);
        gridAndFieldsLayout.add(patientGridLayout, patientFieldsLayout);
        add(tabsLayout);

        tabsLayout.setSizeFull();

        tabs.addSelectedChangeListener(event -> {
            if(event.getSelectedTab().equals(patientDetails)){
                gridAndFieldsLayout.removeAll();
//                patientButtonLayout.setVisible(true);
                appointmentButtonLayout.setVisible(false);
//                gridAndFieldsLayout.add(patientGridLayout, patientFieldsLayout);
                gridAndFieldsLayout.add(patientTab);
            }else if(event.getSelectedTab().equals(doctorDetails)){
                gridAndFieldsLayout.removeAll();
                patientButtonLayout.setVisible(false);
                appointmentButtonLayout.setVisible(false);
//                gridAndFieldsLayout.add(addDoctorDetails());
                gridAndFieldsLayout.add(doctorDetail);
            }else if (event.getSelectedTab().equals(appointmentDetails)){
                gridAndFieldsLayout.removeAll();
                patientButtonLayout.setVisible(false);
//                appointmentButtonLayout.setVisible(true);
//                gridAndFieldsLayout.add(appointmentGridLayout);
                gridAndFieldsLayout.add(appointmentGridTab);
            }
//            else if(event.getSelectedTab().equals(addAppointment)){
//                gridAndFieldsLayout.removeAll();
//                patientButtonLayout.setVisible(false);
//                appointmentButtonLayout.setVisible(false);
//                gridAndFieldsLayout.add(appointmentFieldLayout);
//            }
        });

//        addGridLayout();
//        addFieldLayout();
//        addAppointmentGridLayout();
//        addAppointmentFieldLayout();
    }

//    public void addGridLayout(){
//
//        allPatient = receptionistPresenter.getAllPatient();
//        if(allPatient !=null) {
//            patientGrid.setItems(allPatient);
//        }
//        Grid.Column<Patient> nameColumn = patientGrid.addColumn(Patient::getFullName).setHeader("Name");
//        Grid.Column<Patient> addressColumn = patientGrid.addColumn(Patient::getAddress).setHeader("Address");
//        Grid.Column<Patient> doctorNameColumn = patientGrid.addColumn(p ->{
//            return p.getDoctor().getFirstName() + " " + p.getDoctor().getLastName();
//        }).setHeader("Doctor Name");
//        Grid.Column<Patient> departmentColumn = patientGrid.addColumn(p -> {
//            return p.getDoctor().getDepartment().getDeptName();
//        }).setHeader("Department");
//
//        HeaderRow headerRow = patientGrid.appendHeaderRow();
//
//        patientGrid.addSelectionListener(event -> {
//            Set<Patient> allSelectedItems = event.getAllSelectedItems();
//            Iterator<Patient> iterator = allSelectedItems.iterator();
//            patientBinder.readBean(iterator.next());
//        });
//
//        nameFilter.setPlaceholder("Name");
//        addressFilter.setPlaceholder("Address");
//        doctorFilter.setPlaceholder("Doctor");
//        departmentFilter.setPlaceholder("Department");
//
//        doctorFilter.setItemLabelGenerator(d ->{
//            return d.getFirstName() + " " + d.getLastName();
//        });
//
//        if (receptionistPresenter.getAllDoctor()!=null) {
//            doctorFilter.setItems(receptionistPresenter.getAllDoctor());
//        }
//        if (receptionistPresenter.getAllDepartment()!=null) {
//            departmentFilter.setItems(receptionistPresenter.getAllDepartment());
//        }
//
//        departmentFilter.setItemLabelGenerator(Department::getDeptName);
//
//        nameFilter.setValueChangeMode(ValueChangeMode.LAZY);
//        addressFilter.setValueChangeMode(ValueChangeMode.LAZY);
//
//        nameFilter.addValueChangeListener(e -> getPatientFilter());
//        addressFilter.addValueChangeListener(e -> getPatientFilter());
//        doctorFilter.addValueChangeListener(e -> getPatientFilter());
//        departmentFilter.addValueChangeListener(e -> getPatientFilter());
//
//        headerRow.getCell(nameColumn).setComponent(nameFilter);
//        headerRow.getCell(addressColumn).setComponent(addressFilter);
//        headerRow.getCell(doctorNameColumn).setComponent(doctorFilter);
//        headerRow.getCell(departmentColumn).setComponent(departmentFilter);
//
//        patientGridLayout.add(patientGrid);
//    }
//
//    private void getPatientFilter() {
//
//        ListDataProvider<Patient> dataProvider = (ListDataProvider<Patient>) patientGrid.getDataProvider();
//        dataProvider.setFilter(e ->{
//            System.out.println("in data provider");
//            boolean b1 = e.getFullName().toLowerCase().contains(nameFilter.getValue().toLowerCase());
//            boolean b2 = e.getAddress().toLowerCase().contains(addressFilter.getValue().toLowerCase());
//            boolean b3 = e.getDoctor().getDepartment().getDeptName().equals((departmentFilter.getValue()==null) ? null : departmentFilter.getValue().getDeptName());
//            boolean b4 = e.getDoctor().getFirstName().equals(doctorFilter.getValue()==null ? null : doctorFilter.getValue().getFirstName())
//                    || e.getDoctor().getLastName().equals(doctorFilter.getValue() == null ? null : doctorFilter.getValue().getLastName());
//
//            if(departmentFilter.getValue()== null || doctorFilter.getValue()==null){
//                if(departmentFilter.getValue()== null && doctorFilter.getValue()==null){
//                    System.out.println("in empty");
//                    return b1 && b2;
//                }else if(doctorFilter.getValue()==null){
//                    return b1 && b2 && b3;
//                }else {
//                    return b1 && b2 && b4;
//                }
//            }
//            return b1 && b2 && b3 && b4;
//        });
//
//    }
//
//    public void addFieldLayout(){
//
//        IntegerField idField = new IntegerField("Id");
//        TextField addressField = new TextField("Address");
//        IntegerField ageField = new IntegerField("Age");
//        TextField nameField = new TextField("Name");
//        ComboBox<Staff> doctorComboBox = new ComboBox<>();
//        TextField mobileField = new TextField("Mobile Number");
//        FormLayout formLayout = new FormLayout();
//        cancelButton = new Button("Cancel");
//        saveButton = new Button("Save");
//
//        saveButton.setVisible(false);
//        cancelButton.setVisible(false);
//
//        saveButton.getStyle().set("color", "white").set("background-color","green");
//        cancelButton.getStyle().set("color","white").set("background-color","red");
//
//        doctorComboBox.setLabel("Doctor");
//        idField.setEnabled(false);
//        addressField.setEnabled(false);
//        ageField.setEnabled(false);
//        nameField.setEnabled(false);
//        doctorComboBox.setEnabled(false);
//        mobileField.setEnabled(false);
//
//        doctorComboBox.setItems(receptionistPresenter.getAllDoctor());
//        doctorComboBox.setItemLabelGenerator(d -> {
//            return d.getFirstName() + " " + d.getLastName() + " (" + d.getDepartment().getDeptName() + ")";
//        });
//
//        patientBinder = new Binder<>();
//        patientBinder.setBean(new Patient());
//        patientBinder.forField(idField).bind(Patient::getId, Patient::setAge);
//        patientBinder.forField(addressField).withValidator(s->!s.equals(""),"Please enter address")
//                .bind(Patient::getAddress, Patient::setAddress);
//        patientBinder.forField(ageField).withValidator(Objects::nonNull,"Please enter age")
//                .bind(Patient::getAge, Patient::setAge);
//        patientBinder.forField(nameField).withValidator(s->!s.equals(""),"Please enter name")
//                .bind(Patient::getFullName, Patient::setFullName);
//        patientBinder.forField(doctorComboBox).withValidator(Objects::nonNull,"Please select")
//                .bind(Patient::getDoctor, Patient::setDoctor);
//        patientBinder.forField(mobileField).withValidator(s-> s.length() == 10,"Please enter valid number")
//                .bind(Patient::getMobileNumber, Patient::setMobileNumber);
//
//        addButton.addClickListener(event -> {
//            patientBinder.removeBean();
//
//            patientBinder.readBean(new Patient());
//            saveButton.setVisible(true);
//            cancelButton.setVisible(true);
//            formLayout.add(saveButton,cancelButton);
//            nameField.setEnabled(true);
//            ageField.setEnabled(true);
//            addressField.setEnabled(true);
//            mobileField.setEnabled(true);
//            doctorComboBox.setEnabled(true);
//        });
//        editButton.addClickListener(event -> {
//            Set<Patient> selectedItems = patientGrid.getSelectedItems();
//            if(!selectedItems.isEmpty()){
//                saveButton.setVisible(true);
//                cancelButton.setVisible(true);
//                formLayout.add(saveButton,cancelButton);
//                nameField.setEnabled(true);
//                ageField.setEnabled(true);
//                addressField.setEnabled(true);
//                mobileField.setEnabled(true);
//                doctorComboBox.setEnabled(true);
//            }else {
//                Notification notification = Notification.show("Please select patient", 3000, Notification.Position.TOP_CENTER);
//                notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
//            }
//        });
//        saveButton.addClickListener(event -> {
//            patientBinder.validate();
//            if(patientBinder.isValid()){
//                Patient bean = new Patient();
//                try {
//                    patientBinder.writeBean(bean);
//                } catch (ValidationException e) {
//                    throw new RuntimeException(e);
//                }
//                if(idField.getValue()!=null){
//                    Patient patientById = receptionistPresenter.getPatientById(idField.getValue());
//                    try {
//                        patientBinder.writeBean(patientById);
//                        receptionistPresenter.addPatient(patientById);
//                        for(Patient patient1 : allPatient){
//                            if(Objects.equals(patient1.getId(), patientById.getId())){
//                                allPatient.set(allPatient.indexOf(patient1),patientById);
//                            }
//                        }
//                    } catch (ValidationException e) {
//                        throw new RuntimeException(e);
//                    }
//                }else {
//                    receptionistPresenter.addPatient(bean);
//                    allPatient.add(bean);
//                }
//                patientGrid.getDataProvider().refreshAll();
//                Notification.show("Patient Added", 2000, Notification.Position.TOP_CENTER);
//
//                saveButton.setVisible(false);
//                cancelButton.setVisible(false);
//                formLayout.remove(saveButton,cancelButton);
//                nameField.setEnabled(false);
//                ageField.setEnabled(false);
//                addressField.setEnabled(false);
//                mobileField.setEnabled(false);
//                doctorComboBox.setEnabled(false);
//            }
//        });
//        cancelButton.addClickListener(event -> {
//            saveButton.setVisible(false);
//            cancelButton.setVisible(false);
//            formLayout.remove(saveButton,cancelButton);
//            nameField.setEnabled(false);
//            ageField.setEnabled(false);
//            addressField.setEnabled(false);
//            mobileField.setEnabled(false);
//            doctorComboBox.setEnabled(false);
//        });
//        deleteButton.addClickListener(event -> {
//            Set<Patient> selectedItems = patientGrid.getSelectedItems();
//            if(!selectedItems.isEmpty()){
//                Iterator<Patient> iterator = selectedItems.iterator();
//                Patient patient = iterator.next();
//                receptionistPresenter.deletePatient(patient);
//                allPatient.remove(patient);
//                Notification notification = Notification.show("Patient deleted successfully", 3000, Notification.Position.TOP_CENTER);
//                notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
//                patientGrid.getDataProvider().refreshAll();
//            }
//            else {
//                Notification notification = Notification.show("Please select patient", 3000, Notification.Position.TOP_CENTER);
//                notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
//            }
//        });
//
//        formLayout.add(idField, nameField, addressField,ageField, mobileField,doctorComboBox );
//        patientFieldsLayout.add(formLayout);
//    }
//
//    public VerticalLayout addDoctorDetails(){
//
//        VerticalLayout verticalLayout1 = new VerticalLayout();
//        Grid<Staff> doctorGrid = new Grid<>();
//        doctorGrid.setItems(receptionistPresenter.getAllDoctor());
//        Grid.Column<Staff> firstName = doctorGrid.addColumn(Staff::getFirstName).setHeader("First name");
//        Grid.Column<Staff> lastName = doctorGrid.addColumn(Staff::getLastName).setHeader("Last Name");
//        Grid.Column<Staff> education = doctorGrid.addColumn(Staff::getEducation).setHeader("Education");
//        Grid.Column<Staff> gender = doctorGrid.addColumn(Staff::getGender).setHeader("Gender");
//        Grid.Column<Staff> mobileNumber = doctorGrid.addColumn(Staff::getMobileNumber).setHeader("Mobile Number");
//        Grid.Column<Staff> bloodGroup = doctorGrid.addColumn(Staff::getBloodGroup).setHeader("Blood Group");
//        Grid.Column<Staff> department = doctorGrid.addColumn(e -> e.getDepartment().getDeptName()).setHeader("Department");
//
//        TextField firstnameField = new TextField();
//        TextField lastNameField = new TextField();
//        TextField educationField = new TextField();
//        TextField genderField= new TextField();
//        TextField mobileField = new TextField();
//        TextField bloodGroupField = new TextField();
//        TextField departmentField = new TextField();
//
//        firstnameField.setPlaceholder("First Name");
//        lastNameField.setPlaceholder("Last Name");
//        educationField.setPlaceholder("Education");
//        genderField.setPlaceholder("Gender");
//        mobileField.setPlaceholder("Mobile Number");
//        bloodGroupField.setPlaceholder("Blood Group");
//        departmentField.setPlaceholder("Department");
//
//        firstnameField.setValueChangeMode(ValueChangeMode.LAZY);
//        lastNameField.setValueChangeMode(ValueChangeMode.LAZY);
//        educationField.setValueChangeMode(ValueChangeMode.LAZY);
//        bloodGroupField.setValueChangeMode(ValueChangeMode.LAZY);
//        genderField.setValueChangeMode(ValueChangeMode.LAZY);
//        mobileField.setValueChangeMode(ValueChangeMode.LAZY);
//        departmentField.setValueChangeMode(ValueChangeMode.LAZY);
//
//        HeaderRow headerRow = doctorGrid.appendHeaderRow();
//        headerRow.getCell(firstName).setComponent(firstnameField);
//        headerRow.getCell(lastName).setComponent(lastNameField);
//        headerRow.getCell(education).setComponent(educationField);
//        headerRow.getCell(gender).setComponent(genderField);
//        headerRow.getCell(mobileNumber).setComponent(mobileField);
//        headerRow.getCell(bloodGroup).setComponent(bloodGroupField);
//        headerRow.getCell(department).setComponent(departmentField);
//
//        verticalLayout1.add(doctorGrid);
//        verticalLayout1.setSizeFull();
//
//        firstnameField.addValueChangeListener(event -> getDoctorFilter(doctorGrid,firstnameField,lastNameField,educationField,
//                genderField,mobileField,bloodGroupField,departmentField));
//        lastNameField.addValueChangeListener(event -> getDoctorFilter(doctorGrid,firstnameField,lastNameField,educationField,
//                genderField,mobileField,bloodGroupField,departmentField));
//        genderField.addValueChangeListener(event -> getDoctorFilter(doctorGrid,firstnameField,lastNameField,educationField,
//                genderField,mobileField,bloodGroupField,departmentField));
//        bloodGroupField.addValueChangeListener(event -> getDoctorFilter(doctorGrid,firstnameField,lastNameField,educationField,
//                genderField,mobileField,bloodGroupField,departmentField));
//        departmentField.addValueChangeListener(event -> getDoctorFilter(doctorGrid,firstnameField,lastNameField,educationField,
//                genderField,mobileField,bloodGroupField,departmentField));
//        educationField.addValueChangeListener(event -> getDoctorFilter(doctorGrid,firstnameField,lastNameField,educationField,
//                genderField,mobileField,bloodGroupField,departmentField));
//        mobileField.addValueChangeListener(event -> getDoctorFilter(doctorGrid,firstnameField,lastNameField,educationField,
//                genderField,mobileField,bloodGroupField,departmentField));
//
//        return verticalLayout1;
//    }
//
//    public void getDoctorFilter(Grid<Staff> doctorGrid, TextField firstnameField, TextField lastNameField, TextField educationField, TextField genderField,
//                                TextField mobileField, TextField bloodGroupField, TextField departmentField){
//
//        ListDataProvider<Staff> dataProvider = (ListDataProvider)doctorGrid.getDataProvider();
//        dataProvider.setFilter(e -> {
//
//            Boolean b1 = e.getFirstName().toLowerCase().contains(firstnameField.getValue().toLowerCase());
//            Boolean b2 = e.getLastName().toLowerCase().contains(lastNameField.getValue().toLowerCase());
//            Boolean b3 = e.getEducation().toLowerCase().contains(educationField.getValue().toLowerCase());
//            Boolean b4 = e.getGender().toLowerCase().contains(genderField.getValue().toLowerCase());
//            Boolean b5 = e.getMobileNumber().toLowerCase().contains(mobileField.getValue().toLowerCase());
//            Boolean b6 = e.getBloodGroup().toLowerCase().contains(bloodGroupField.getValue().toLowerCase());
//            Boolean b7 = e.getDepartment().getDeptName().toLowerCase().contains(departmentField.getValue().toLowerCase());
//
//            return b1 && b2 && b3 && b4 && b6 && b7 && b5;
//        });
//    }
//
//    public VerticalLayout addAppointmentGridLayout(){
//
//        appointmentList = receptionistPresenter.getAllAppointment();
//        if(allPatient !=null) {
//            appointmentGrid.setItems(appointmentList);
//        }
//        Grid.Column<Appointment> idColumn = appointmentGrid.addColumn(Appointment::getId).setHeader("Id");
//        Grid.Column<Appointment> nameColumn = appointmentGrid.addColumn(Appointment::getName).setHeader("Name");
//        Grid.Column<Appointment> doctorNameColumn = appointmentGrid.addColumn(p ->{
//            return p.getDoctor().getFirstName() + " " + p.getDoctor().getLastName();
//        }).setHeader("Doctor Name");
//        Grid.Column<Appointment> departmentColumn = appointmentGrid.addColumn(p -> {
//            return p.getDoctor().getDepartment().getDeptName();
//        }).setHeader("Department");
//        Grid.Column<Appointment> addressColumn = appointmentGrid.addColumn(Appointment::getAddress).setHeader("Address");
//        Grid.Column<Appointment> ageColumn = appointmentGrid.addColumn(Appointment::getAge).setHeader("Age");
//        Grid.Column<Appointment> descriptionColumn = appointmentGrid.addColumn(Appointment::getDescription).setHeader("Description");
//        Grid.Column<Appointment> timeColumn = appointmentGrid.addColumn(Appointment::getTime).setHeader("Time");
//
//
//        HeaderRow headerRow = appointmentGrid.appendHeaderRow();
//
//        appointmentGrid.addSelectionListener(event -> {
//            Set<Appointment> allSelectedItems = event.getAllSelectedItems();
//            Iterator<Appointment> iterator = allSelectedItems.iterator();
//            appointmentBinder.readBean(iterator.next());
//        });
//
//        TextField idFilter = new TextField();
//        TextField nameFilter = new TextField();
//        ComboBox<Staff> doctorFilter = new ComboBox<>();
//        ComboBox<Department> departmentFilter = new ComboBox<>();
//        TextField addressFilter = new TextField();
//        TextField ageFilter = new TextField();
//        TextField descriptionFilter = new TextField();
//        TextField timeFilter = new TextField();
//
//        idFilter.setPlaceholder("Id");
//        nameFilter.setPlaceholder("Name");
//        addressFilter.setPlaceholder("Address");
//        ageFilter.setPlaceholder("Age");
//        descriptionFilter.setPlaceholder("Description");
//        doctorFilter.setPlaceholder("Doctor");
//        departmentFilter.setPlaceholder("Department");
//        timeFilter.setPlaceholder("Time (between 10 to 17)");
//
//        doctorFilter.setItemLabelGenerator(d ->{
//            return d.getFirstName() + " " + d.getLastName();
//        });
//
//        if (receptionistPresenter.getAllDoctor()!=null) {
//            doctorFilter.setItems(receptionistPresenter.getAllDoctor());
//        }
//        if (receptionistPresenter.getAllDepartment()!=null) {
//            departmentFilter.setItems(receptionistPresenter.getAllDepartment());
//        }
//
//        departmentFilter.setItemLabelGenerator(Department::getDeptName);
//
//        idFilter.setValueChangeMode(ValueChangeMode.LAZY);
//        nameFilter.setValueChangeMode(ValueChangeMode.LAZY);
//        addressFilter.setValueChangeMode(ValueChangeMode.LAZY);
//        ageFilter.setValueChangeMode(ValueChangeMode.LAZY);
//        descriptionFilter.setValueChangeMode(ValueChangeMode.LAZY);
//        timeFilter.setValueChangeMode(ValueChangeMode.LAZY);
//
//        idFilter.addValueChangeListener(e -> getAppointmentFilter(idFilter, ageFilter, descriptionFilter,
//            nameFilter, addressFilter, doctorFilter, departmentFilter, timeFilter));
//        ageFilter.addValueChangeListener(e -> getAppointmentFilter(idFilter, ageFilter, descriptionFilter,
//                nameFilter, addressFilter, doctorFilter, departmentFilter, timeFilter));
//        descriptionFilter.addValueChangeListener(e -> getAppointmentFilter(idFilter, ageFilter, descriptionFilter,
//                nameFilter, addressFilter, doctorFilter, departmentFilter, timeFilter));
//        nameFilter.addValueChangeListener(e -> getAppointmentFilter(idFilter, ageFilter, descriptionFilter,
//                nameFilter, addressFilter, doctorFilter, departmentFilter, timeFilter));
//        addressFilter.addValueChangeListener(e -> getAppointmentFilter(idFilter, ageFilter, descriptionFilter,
//                nameFilter, addressFilter, doctorFilter, departmentFilter, timeFilter));
//        doctorFilter.addValueChangeListener(e -> getAppointmentFilter(idFilter, ageFilter, descriptionFilter,
//                nameFilter, addressFilter, doctorFilter, departmentFilter, timeFilter));
//        departmentFilter.addValueChangeListener(e -> getAppointmentFilter(idFilter, ageFilter, descriptionFilter,
//                nameFilter, addressFilter, doctorFilter, departmentFilter, timeFilter));
//        timeFilter.addValueChangeListener(e -> getAppointmentFilter(idFilter, ageFilter, descriptionFilter,
//                nameFilter, addressFilter, doctorFilter, departmentFilter, timeFilter));
//
//        headerRow.getCell(idColumn).setComponent(idFilter);
//        headerRow.getCell(timeColumn).setComponent(timeFilter);
//        headerRow.getCell(ageColumn).setComponent(ageFilter);
//        headerRow.getCell(descriptionColumn).setComponent(descriptionFilter);
//        headerRow.getCell(nameColumn).setComponent(nameFilter);
//        headerRow.getCell(addressColumn).setComponent(addressFilter);
//        headerRow.getCell(doctorNameColumn).setComponent(doctorFilter);
//        headerRow.getCell(departmentColumn).setComponent(departmentFilter);
//
//        appointmentGridLayout.add(appointmentGrid);
//
//        return appointmentGridLayout;
//
//    }
//
//    private void getAppointmentFilter(TextField idFilter, TextField ageFilter, TextField descriptionFilter,
//                                      TextField nameFilter, TextField addressFilter, ComboBox<Staff> doctorFilter,
//                                      ComboBox<Department> departmentFilter, TextField timeFilter) {
//
//        ListDataProvider<Appointment> dataProvider = (ListDataProvider<Appointment>) appointmentGrid.getDataProvider();
//        dataProvider.setFilter(e ->{
//            boolean b6 = String.valueOf(e.getId()).contains(idFilter.getValue());
//            boolean b5 = e.getAge().contains(ageFilter.getValue());
//            boolean b2 = e.getDescription().toLowerCase().contains(descriptionFilter.getValue().toLowerCase());
//            boolean b1 = e.getName().toLowerCase().contains(nameFilter.getValue().toLowerCase());
//            boolean b7 = e.getAddress().toLowerCase().contains(addressFilter.getValue().toLowerCase());
//            boolean b4 = e.getDoctor().getFirstName().equals(doctorFilter.getValue()==null?"":doctorFilter.getValue().getFirstName());
//            boolean b8 = e.getTime().contains(timeFilter.getValue());
//            boolean b3 = e.getDoctor().getDepartment().getDeptName().equals(departmentFilter.getValue()==null?"":departmentFilter.getValue().getDeptName());
//
//            if(departmentFilter.getValue()== null|| doctorFilter.getValue()== null ){
//                if(departmentFilter.getValue()== null && doctorFilter.getValue()== null ){
//                    return b1 && b2 && b5 && b6 && b7 && b8;
//                }else if(doctorFilter.getValue()== null){
//                    return b1 && b2 && b3 && b6 && b5  && b7 && b8;
//                }  else {
//                    return b1 && b2 && b4 && b5 && b6 && b7 && b8;
//                }
//            }
//            return b1 && b2 && b3 && b4 && b6 && b5 && b7 && b8;
//
//
//        });
//
//    }
//
//    public void addAppointmentFieldLayout(){
//
//        IntegerField idField = new IntegerField("Id");
//        TextField timeField = new TextField("Time (between 10 to 17)");
//        TextField addressField = new TextField("Address");
//        TextField ageField = new TextField("Age");
//        TextField nameField = new TextField("Name");
//        ComboBox<Staff> doctorComboBox = new ComboBox<>();
//        ComboBox<Department> departmentComboBox = new ComboBox<>();
//        TextField description = new TextField("Description");
//        FormLayout formLayout = new FormLayout();
//        Button cancelButton = new Button("Cancel");
//        Button saveButton = new Button("Save");
//        Button addButton = new Button("Add");
//        Button editButton = new Button("Edit");
//        Button deleteButton = new Button("delete");
//
////        saveButton.setVisible(false);
//        cancelButton.setVisible(false);
//        idField.setEnabled(false);
//
//        saveButton.getStyle().set("color", "white").set("background-color","green");
//        cancelButton.getStyle().set("color","white").set("background-color","red");
//        addButton.getStyle().set("color", "white").set("background-color","green");
//        editButton.getStyle().set("color", "white").set("background-color","blue");
//        deleteButton.getStyle().set("color", "white").set("background-color","red");
//        formLayout.setWidth(40, Unit.PERCENTAGE);
//
//        doctorComboBox.setLabel("Doctor");
////        idField.setEnabled(false);
////        timeField.setEnabled(false);
////        description.setEnabled(false);
////        addressField.setEnabled(false);
////        ageField.setEnabled(false);
////        nameField.setEnabled(false);
////        doctorComboBox.setEnabled(false);
////        departmentComboBox.setEnabled(false);
//        formLayout.setColspan(saveButton,2);
//        formLayout.setColspan(cancelButton,2);
//
//
//        departmentComboBox.setItems(receptionistPresenter.getAllDepartment());
//        doctorComboBox.setItems(receptionistPresenter.getAllDoctor());
//        doctorComboBox.setItemLabelGenerator(d -> {
//            return d.getFirstName() + " " + d.getLastName() + " (" + d.getDepartment().getDeptName() + ")";
//        });
//        departmentComboBox.setItemLabelGenerator(Department::getDeptName);
//
//        appointmentBinder = new Binder<>();
//        appointmentBinder.setBean(new Appointment());
//        appointmentBinder.forField(idField).bind(Appointment::getId, Appointment::setId);
//        appointmentBinder.forField(addressField).withValidator(s->!s.equals(""),"Please enter address")
//                .bind(Appointment::getAddress, Appointment::setAddress);
//        appointmentBinder.forField(ageField).withValidator(Objects::nonNull,"Please enter age")
//                .bind(Appointment::getAge, Appointment::setAge);
//        appointmentBinder.forField(nameField).withValidator(s->!s.equals(""),"Please enter name")
//                .bind(Appointment::getName, Appointment::setName);
//        appointmentBinder.forField(doctorComboBox).withValidator(Objects::nonNull,"Please select")
//                .bind(Appointment::getDoctor, Appointment::setDoctor);
//        appointmentBinder.forField(description)
//                .bind(Appointment::getDescription, Appointment::setDescription);
//        appointmentBinder.forField(timeField).withValidator(e -> e.length()<=2, "Please enter valid time")
//                        .bind(Appointment::getTime,Appointment::setTime);
//
//        addButton.addClickListener(event -> {
//            appointmentBinder.removeBean();
//            addAppointment.setSelected(true);
//            appointmentBinder.readBean(new Appointment());
////            saveButton.setVisible(true);
////            cancelButton.setVisible(true);
////            formLayout.add(saveButton,cancelButton);
////            timeField.setEnabled(true);
////            description.setEnabled(true);
////            addressField.setEnabled(true);
////            ageField.setEnabled(true);
////            nameField.setEnabled(true);
////            doctorComboBox.setEnabled(true);
////            departmentComboBox.setEnabled(true);
//        });
//        editButton.addClickListener(event -> {
//            Set<Appointment> selectedItems = appointmentGrid.getSelectedItems();
//            if(!selectedItems.isEmpty()){
////                saveButton.setVisible(true);
////                cancelButton.setVisible(true);
////                formLayout.add(saveButton,cancelButton);
////                timeField.setEnabled(true);
////                description.setEnabled(true);
////                addressField.setEnabled(true);
////                ageField.setEnabled(true);
////                nameField.setEnabled(true);
////                doctorComboBox.setEnabled(true);
////                departmentComboBox.setEnabled(true);
////                appointmentBinder.readBean();
//                tabs.setSelectedTab(addAppointment);
//            }else {
//                Notification notification = Notification.show("Please select Appointment", 3000, Notification.Position.TOP_CENTER);
//                notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
//            }
//        });
//        saveButton.addClickListener(event -> {
//            appointmentBinder.validate();
//            if(appointmentBinder.isValid()){
//                Appointment bean = new Appointment();
//                try {
//                    appointmentBinder.writeBean(bean);
//                } catch (ValidationException e) {
//                    throw new RuntimeException(e);
//                }
//                if(!idField.isEmpty()){
//                    Appointment appointmentById = receptionistPresenter.getAppointmentById(idField.getValue());
//                    try {
//                        appointmentBinder.writeBean(appointmentById);
//                        receptionistPresenter.addAppointment(appointmentById);
//                        for(Appointment appointment : appointmentList){
//                            if(Objects.equals(appointment.getId(), appointmentById.getId())){
//                                appointmentList.set(appointmentList.indexOf(appointment),appointmentById);
//                            }
//                        }
//                        Notification.show("Appointment changed", 2000, Notification.Position.TOP_CENTER);
//                    } catch (ValidationException e) {
//                        throw new RuntimeException(e);
//                    }
//                }else {
//                    receptionistPresenter.addAppointment(bean);
//                    appointmentList.add(bean);
//                    Notification.show("Appointment saved", 2000, Notification.Position.TOP_CENTER);
//                }
//                appointmentGrid.getDataProvider().refreshAll();
//
////                saveButton.setVisible(false);
////                cancelButton.setVisible(false);
////                formLayout.remove(saveButton,cancelButton);
////                timeField.setEnabled(false);
////                description.setEnabled(false);
////                addressField.setEnabled(false);
////                ageField.setEnabled(false);
////                nameField.setEnabled(false);
////                doctorComboBox.setEnabled(false);
////                departmentComboBox.setEnabled(false);
//            }
//        });
//        cancelButton.addClickListener(event -> {
//            formLayout.remove(saveButton,cancelButton);
////            saveButton.setVisible(false);
////            cancelButton.setVisible(false);
//
////            timeField.setEnabled(false);
////            description.setEnabled(false);
////            addressField.setEnabled(false);
////            ageField.setEnabled(false);
////            nameField.setEnabled(false);
////            doctorComboBox.setEnabled(false);
////            departmentComboBox.setEnabled(false);
//        });
//        deleteButton.addClickListener(event -> {
//            Set<Appointment> selectedItems = appointmentGrid.getSelectedItems();
//            if(!selectedItems.isEmpty()){
//                Iterator<Appointment> iterator = selectedItems.iterator();
//                Appointment appointment = iterator.next();
//                receptionistPresenter.deleteAppointment(appointment);
//                appointmentList.remove(appointment);
//                Notification notification = Notification.show("Appointment deleted successfully", 3000, Notification.Position.TOP_CENTER);
//                notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
//                appointmentGrid.getDataProvider().refreshAll();
//            }
//            else {
//                Notification notification = Notification.show("Please select appointment", 3000, Notification.Position.TOP_CENTER);
//                notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
//            }
//        });
//
//        appointmentButtonLayout.add(editButton,deleteButton);
//        formLayout.add(idField, nameField, addressField,ageField, description, timeField,doctorComboBox ,saveButton);
//        appointmentFieldLayout.add(formLayout);
//    }

}
