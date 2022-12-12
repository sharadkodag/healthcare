package com.skhealthcare.modules.doctorview.tabs;

import com.skhealthcare.entity.Appointment;
import com.skhealthcare.entity.Department;
import com.skhealthcare.entity.Patient;
import com.skhealthcare.entity.Staff;
import com.skhealthcare.modules.doctorview.DoctorLoginPresenter;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.HeaderRow;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

@UIScope
@SpringComponent
public class AppointmentsTab extends VerticalLayout {
    @Autowired
    DoctorLoginPresenter doctorLoginPresenter;
    VerticalLayout appointmentGridLayout;
    List<Patient> allPatient;
    ComboBox<Staff> doctorFilter;
    ComboBox<Department> departmentFilter;
    Grid<Appointment> appointmentGrid;
    List<Appointment> appointmentList;
    Binder<Appointment> appointmentBinder;
    Button logoutButton;

    @PostConstruct
    public void init(){
        allPatient = new ArrayList<>();
        doctorFilter = new ComboBox<>();
        departmentFilter = new ComboBox<>();
        appointmentGridLayout = new VerticalLayout();
        appointmentGrid = new Grid<>();
        logoutButton = new Button("Logout");

        setMargin(false);
        setPadding(false);
        setSizeFull();

        add(addAppointmentGridLayout());
    }

    public VerticalLayout addAppointmentGridLayout(){

        appointmentList = doctorLoginPresenter.getAllAppointmentForDoctor();
        if(allPatient !=null) {
            appointmentGrid.setItems(appointmentList);
        }
        Grid.Column<Appointment> idColumn = appointmentGrid.addColumn(Appointment::getId).setHeader("Id");
        Grid.Column<Appointment> nameColumn = appointmentGrid.addColumn(Appointment::getName).setHeader("Name");
        Grid.Column<Appointment> addressColumn = appointmentGrid.addColumn(Appointment::getAddress).setHeader("Address");
        Grid.Column<Appointment> ageColumn = appointmentGrid.addColumn(Appointment::getAge).setHeader("Age");
        Grid.Column<Appointment> descriptionColumn = appointmentGrid.addColumn(Appointment::getDescription).setHeader("Description");
        Grid.Column<Appointment> timeColumn = appointmentGrid.addColumn(Appointment::getTime).setHeader("Time");

        HeaderRow headerRow = appointmentGrid.appendHeaderRow();

        appointmentGrid.addSelectionListener(event -> {
            Set<Appointment> allSelectedItems = event.getAllSelectedItems();
            Iterator<Appointment> iterator = allSelectedItems.iterator();
            appointmentBinder.readBean(iterator.next());
        });

        TextField idFilter = new TextField();
        TextField nameFilter = new TextField();
        TextField addressFilter = new TextField();
        TextField ageFilter = new TextField();
        TextField descriptionFilter = new TextField();
        TextField timeFilter = new TextField();

        idFilter.setPlaceholder("Id");
        nameFilter.setPlaceholder("Name");
        addressFilter.setPlaceholder("Address");
        ageFilter.setPlaceholder("Age");
        descriptionFilter.setPlaceholder("Description");
        timeFilter.setPlaceholder("Time");

        idFilter.setValueChangeMode(ValueChangeMode.LAZY);
        nameFilter.setValueChangeMode(ValueChangeMode.LAZY);
        addressFilter.setValueChangeMode(ValueChangeMode.LAZY);
        ageFilter.setValueChangeMode(ValueChangeMode.LAZY);
        descriptionFilter.setValueChangeMode(ValueChangeMode.LAZY);
        timeFilter.setValueChangeMode(ValueChangeMode.LAZY);

        idFilter.addValueChangeListener(e -> getAppointmentFilter(idFilter, ageFilter, descriptionFilter,
                nameFilter, addressFilter, timeFilter));
        ageFilter.addValueChangeListener(e -> getAppointmentFilter(idFilter, ageFilter, descriptionFilter,
                nameFilter, addressFilter, timeFilter));
        descriptionFilter.addValueChangeListener(e -> getAppointmentFilter(idFilter, ageFilter, descriptionFilter,
                nameFilter, addressFilter, timeFilter));
        nameFilter.addValueChangeListener(e -> getAppointmentFilter(idFilter, ageFilter, descriptionFilter,
                nameFilter, addressFilter, timeFilter));
        addressFilter.addValueChangeListener(e -> getAppointmentFilter(idFilter, ageFilter, descriptionFilter,
                nameFilter, addressFilter, timeFilter));
        timeFilter.addValueChangeListener(e -> getAppointmentFilter(idFilter, ageFilter, descriptionFilter,
                nameFilter, addressFilter, timeFilter));

        headerRow.getCell(idColumn).setComponent(idFilter);
        headerRow.getCell(timeColumn).setComponent(timeFilter);
        headerRow.getCell(ageColumn).setComponent(ageFilter);
        headerRow.getCell(descriptionColumn).setComponent(descriptionFilter);
        headerRow.getCell(nameColumn).setComponent(nameFilter);
        headerRow.getCell(addressColumn).setComponent(addressFilter);

        appointmentGridLayout.add(appointmentGrid);

        return appointmentGridLayout;
    }

    private void getAppointmentFilter(TextField idFilter, TextField ageFilter, TextField descriptionFilter,
                                      TextField nameFilter, TextField addressFilter, TextField timeFilter) {

        ListDataProvider<Appointment> dataProvider = (ListDataProvider<Appointment>) appointmentGrid.getDataProvider();
        dataProvider.setFilter(e ->{
            boolean b6 = String.valueOf(e.getId()).contains(idFilter.getValue());
            boolean b5 = e.getAge().contains(ageFilter.getValue());
            boolean b2 = e.getDescription().toLowerCase().contains(descriptionFilter.getValue().toLowerCase());
            boolean b1 = e.getName().toLowerCase().contains(nameFilter.getValue().toLowerCase());
            boolean b7 = e.getAddress().toLowerCase().contains(addressFilter.getValue().toLowerCase());
            boolean b8 = e.getTime().contains(timeFilter.getValue());
            return b1 && b2 && b6 && b5 && b7 && b8;
        });

    }

}
