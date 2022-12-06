package com.skhealthcare.modules.login;

import com.skhealthcare.entity.Department;
import com.skhealthcare.entity.Doctor;
import com.skhealthcare.entity.Patient;
import com.skhealthcare.modules.mainpage.MainPageView;
import com.skhealthcare.mvputil.BaseView;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.HeaderRow;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
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
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Route("receptionistview")
@UIScope
@SpringComponent
public class LoginView extends BaseView<LoginPresenter> {

    @Autowired
    LoginPresenter loginPresenter;

    VerticalLayout verticalLayout;
    Binder<Patient> binder;
    Tabs doctorTabs;
    Tabs receptionistTab;
    Patient patient;
    Doctor doctor;
    List<Patient> allPatient;

    Grid<Patient> patientGrid;


    @PostConstruct
    public void init(){
        addHeading();
        verticalLayout = new VerticalLayout();
        verticalLayout.setSizeFull();
        verticalLayout.getStyle().set("background-color","white");
        addUserDetails();
        addLayout();
        receptionistTab();
        addFooter();
    }

    public void addHeading(){
        setPadding(false);
        setAlignItems(Alignment.CENTER);
        H1 heading = new H1("SK Healthcare");
        heading.getStyle().set("background-color", "green").set("padding-left","7%").set("padding-top","1%")
                .set("font-weight", "bold").set("color", "white").set("font-size","xx-larger").set("margin-top","0px");
        add(heading);
        heading.setWidth(93,Unit.PERCENTAGE);
        setSizeFull();
        setPadding(false);
        getStyle().set("background-image", "url('images/Untitled.png')").set("background-repeat", "no-repeat")
                .set("background-position", "center").set("background-size","cover");

    }

    public void addUserDetails(){
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        Icon icon = VaadinIcon.USER_CARD.create();

        icon.addClickListener(event -> {
            Dialog dialog = new Dialog();
            TextField nameField = new TextField("Name");
            TextField addressField = new TextField("Address");
            Button logout = new Button("Logout");
            VerticalLayout verticalLayout1 = new VerticalLayout();

            dialog.open();
            nameField.setEnabled(false);
            addressField.setEnabled(false);

            logout.addClickListener(e -> {
                logout.getUI().ifPresent(u -> u.navigate(MainPageView.class));
            });

            verticalLayout1.add(nameField, addressField, logout);
            dialog.add(verticalLayout1);
        });

        horizontalLayout.getStyle().set("padding-left","95%");

        horizontalLayout.add(icon);
        add(horizontalLayout);
    }

    public void addLayout(){

        Label label = new Label("Receptionist Portal");
        label.getStyle().set("background-color", "blue").set("padding-left","40%")
                .set("color","white").set("font-weight", "bold").set("font-size", "xx-large");
        label.setWidth(60, Unit.PERCENTAGE);
        add(label);

    }

    public void addFooter(){
        Label footer = new Label("Copyright @Direction Software");
        footer.setWidth(55, Unit.PERCENTAGE);
        footer.getStyle().set("background-color", "black").set("padding-left","45%")
                .set("font-weight", "bold").set("color", "white");
        add(footer);
    }

    public void receptionistTab(){
        verticalLayout.removeAll();

        receptionistTab = new Tabs();
        Tab tab = new Tab("Patient Details");
        Tab tab1 = new Tab("Doctor Details");
        receptionistTab.add(tab, tab1);
        verticalLayout.add(receptionistTab);

        VerticalLayout verticalLayout1 = new VerticalLayout();
        verticalLayout1.add(addPatientFields());

        verticalLayout.add(verticalLayout1);
        receptionistTab.addSelectedChangeListener(event -> {
            if(event.getSelectedTab().equals(tab)){
                verticalLayout1.removeAll();
                verticalLayout1.add(addPatientFields());
            }else if (event.getSelectedTab().equals(tab1)){
                verticalLayout1.removeAll();
                verticalLayout1.add(addDoctorDetails());
            }
        });

        add(verticalLayout);

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
        doctorComboBox.setItems(loginPresenter.getAllDoctor());
        doctorComboBox.setItemLabelGenerator(d -> {
            return d.getFirstName() + " " + d.getLastName() + " (" + d.getDepartment().getDeptName() + ")";
        });
        TextField mobileField = new TextField("Mobile Number");
        mobileField.setEnabled(false);

        binder = new Binder<>();
        binder.setBean(new Patient());
        binder.forField(idField).bind(Patient::getId, Patient::setAge);
        binder.forField(addressField).withValidator(s->!s.equals(""),"Please enter address")
                .bind(Patient::getAddress, Patient::setAddress);
        binder.forField(ageField).withValidator(Objects::nonNull,"Please enter age")
                .bind(Patient::getAge, Patient::setAge);
        binder.forField(nameField).withValidator(s->!s.equals(""),"Please enter name")
                .bind(Patient::getFullName, Patient::setFullName);
        binder.forField(doctorComboBox).withValidator(Objects::nonNull,"Please select")
                .bind(Patient::getDoctor, Patient::setDoctor);
        binder.forField(mobileField).withValidator(s-> s.length() == 10,"Please enter valid number")
                .bind(Patient::getMobileNumber, Patient::setMobileNumber);

        patientGrid = new Grid<>();

        allPatient = loginPresenter.getAllPatient();
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
            binder.readBean(iterator.next());
        });

        TextField nameFilter = new TextField();
        nameFilter.setPlaceholder("Name");
        TextField addressFilter = new TextField();
        addressFilter.setPlaceholder("Address");
        ComboBox<Doctor> doctorFilter = new ComboBox<>();
        if (loginPresenter.getAllDoctor()!=null) {
            doctorFilter.setItems(loginPresenter.getAllDoctor());
        }
        doctorFilter.setPlaceholder("Doctor");
        doctorFilter.setItemLabelGenerator(d ->{
            return d.getFirstName() + " " + d.getLastName();
        });
        ComboBox<Department> departmentFilter = new ComboBox<>();
        departmentFilter.setPlaceholder("Department");
        if (loginPresenter.getAllDepartment()!=null) {
            departmentFilter.setItems(loginPresenter.getAllDepartment());
        }
        departmentFilter.setItemLabelGenerator(l ->{
            return l.getDeptName();
        });

        nameFilter.setValueChangeMode(ValueChangeMode.LAZY);
        addressFilter.setValueChangeMode(ValueChangeMode.LAZY);
//        doctorFilter.setValueChangeMode(ValueChangeMode.LAZY);
//        departmentFilter.setValueChangeMode(ValueChangeMode.LAZY);

        nameFilter.addValueChangeListener(e -> getPatientFilter(patientGrid, nameFilter, addressFilter, departmentFilter, doctorFilter));
        addressFilter.addValueChangeListener(e -> getPatientFilter(patientGrid, nameFilter, addressFilter, departmentFilter, doctorFilter));
        doctorFilter.addValueChangeListener(e -> getPatientFilter(patientGrid, nameFilter, addressFilter, departmentFilter, doctorFilter));
        departmentFilter.addValueChangeListener(e -> getPatientFilter(patientGrid, nameFilter, addressFilter, departmentFilter, doctorFilter));

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
        verticalLayout1.setHeightFull();
        verticalLayout1.setWidth(65, Unit.PERCENTAGE);
        VerticalLayout verticalLayout2 = new VerticalLayout();
        verticalLayout2.setHeightFull();
        verticalLayout2.setWidth(35, Unit.PERCENTAGE);
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
            binder.readBean(new Patient());
            save.setVisible(true);
            cancel.setVisible(true);
            formLayout.add(save,cancel);
            nameField.setEnabled(true);
            ageField.setEnabled(true);
            addressField.setEnabled(true);
            mobileField.setEnabled(true);
            doctorComboBox.setEnabled(true);
        });

        editButton.addClickListener(event -> {
            Set<Patient> selectedItems = patientGrid.getSelectedItems();
            if(!selectedItems.isEmpty()){

                save.setVisible(true);
                cancel.setVisible(true);
                formLayout.add(save,cancel);
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

        save.addClickListener(event -> {

            binder.validate();

            if(binder.isValid()){
                Patient bean = new Patient();
                try {
                    binder.writeBean(bean);
                } catch (ValidationException e) {
                    throw new RuntimeException(e);
                }
                if(idField.getValue()!=null){
                    Patient patientById = loginPresenter.getPatientById(idField.getValue());
                    try {
                        binder.writeBean(patientById);
                        loginPresenter.addPatient(patientById);
                        for(Patient patient1 : allPatient){
                            if(patient1.getId()== patientById.getId()){
                                allPatient.set(allPatient.indexOf(patient1),patientById);
                            }
                        }
                    } catch (ValidationException e) {
                        throw new RuntimeException(e);
                    }
                }else {
                    loginPresenter.addPatient(bean);
                    allPatient.add(bean);
                }
                patientGrid.getDataProvider().refreshAll();
                Notification.show("Patient Added", 2000, Notification.Position.TOP_CENTER);

                save.setVisible(false);
                cancel.setVisible(false);
                formLayout.remove(save,cancel);
                nameField.setEnabled(false);
                ageField.setEnabled(false);
                addressField.setEnabled(false);
                mobileField.setEnabled(false);
                doctorComboBox.setEnabled(false);
            }
        });
        cancel.addClickListener(event -> {
            save.setVisible(false);
            cancel.setVisible(false);
            formLayout.remove(save,cancel);
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
                patient = iterator.next();
                loginPresenter.deletePatient(patient);
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
        return verticalLayout;
    }

    private void getPatientFilter(Grid<Patient> patientGrid, TextField nameFilter, TextField addressFilter,
                                  ComboBox<Department> departmentFilter, ComboBox<Doctor> doctorFilter) {

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

    public VerticalLayout addDoctorDetails(){

        VerticalLayout verticalLayout1 = new VerticalLayout();
        Grid<Doctor> doctorGrid = new Grid<>();
        doctorGrid.setItems(loginPresenter.getAllDoctor());
        Grid.Column<Doctor> firstName = doctorGrid.addColumn(Doctor::getFirstName).setHeader("First name");
        Grid.Column<Doctor> lastName = doctorGrid.addColumn(Doctor::getLastName).setHeader("Last Name");
        Grid.Column<Doctor> education = doctorGrid.addColumn(Doctor::getEducation).setHeader("Education");
        Grid.Column<Doctor> gender = doctorGrid.addColumn(Doctor::getGender).setHeader("Gender");
        Grid.Column<Doctor> mobileNumber = doctorGrid.addColumn(Doctor::getMobileNumber).setHeader("Mobile Number");
        Grid.Column<Doctor> bloodGroup = doctorGrid.addColumn(Doctor::getBloodGroup).setHeader("Blood Group");
        Grid.Column<Doctor> department = doctorGrid.addColumn(e -> e.getDepartment().getDeptName()).setHeader("Department");

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

    public void getDoctorFilter(Grid<Doctor> doctorGrid, TextField firstnameField, TextField lastNameField, TextField educationField, TextField genderField,
                                TextField mobileField, TextField bloodGroupField, TextField departmentField){

        ListDataProvider<Doctor> dataProvider = (ListDataProvider)doctorGrid.getDataProvider();
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
