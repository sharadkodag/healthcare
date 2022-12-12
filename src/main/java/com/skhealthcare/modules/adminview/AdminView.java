package com.skhealthcare.modules.adminview;

import com.skhealthcare.entity.Appointment;
import com.skhealthcare.entity.Department;
import com.skhealthcare.entity.Staff;
import com.skhealthcare.entity.Patient;
import com.skhealthcare.modules.adminview.tabs.AppointmentGridTab;
import com.skhealthcare.modules.adminview.tabs.StaffDetails;
import com.skhealthcare.modules.homeview.HomepageView;
import com.skhealthcare.modules.homeview.Template;
import com.skhealthcare.mvputil.BaseView;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.HeaderRow;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

@UIScope
@SpringComponent
@Route(value = "admin", layout = Template.class)
public class AdminView extends BaseView<AdminPresenter> {
    @Autowired
    com.skhealthcare.modules.adminview.tabs.Patient patient;
    @Autowired
    StaffDetails staffDetail;
    @Autowired
    AppointmentGridTab appointmentGridTab;
    VerticalLayout tabsLayout;
    HorizontalLayout gridAndFieldsLayout;
    Tabs tabs;
    Tab patientDetails;
    Tab staffDetails;
    Tab appointmentDetails;
    Button logoutButton;


    @Override
    public void beforeEnter(BeforeEnterEvent observer){
        getPresenter().beforeEnter(observer);
    }

    @Override
    public void init(){
        tabsLayout = new VerticalLayout();
        tabs = new Tabs();
        staffDetails = new Tab();
        patientDetails = new Tab();
        appointmentDetails = new Tab();
        gridAndFieldsLayout = new HorizontalLayout();;
        logoutButton = new Button("Logout");

        setMargin(false);
        setPadding(false);
        setSizeFull();

        addLayout();
        addMenuBarLayout();
    }

    public void addLayout(){
        Label label = new Label("Admin Portal");
        VerticalLayout verticalLayout = new VerticalLayout();

        label.getStyle().set("background-color", "blue").set("padding-left","45%")
                .set("color","white").set("font-weight", "bold").set("font-size", "xx-large");
        label.setWidth(55, Unit.PERCENTAGE);
        logoutButton.getStyle().set("background-color","red").set("color","white");
        verticalLayout.setAlignItems(Alignment.END);

        logoutButton.addClickListener(event -> {

            logoutButton.getUI().ifPresent(evt -> evt.navigate(HomepageView.class) );
            Notification.show("Successfully logout");
            VaadinSession.getCurrent().close();

        });

        verticalLayout.add(logoutButton);
        add(verticalLayout, label);
    }

    public void addMenuBarLayout() {
        patientDetails.setLabel("Patient Details");
        staffDetails.setLabel("Staff details");
        appointmentDetails.setLabel("Appointment Details");

        tabsLayout.setSizeFull();
        tabsLayout.getStyle().set("background-color", "white");
        gridAndFieldsLayout.setSizeFull();
        gridAndFieldsLayout.getStyle().set("background-color", "white");
        tabsLayout.setSizeFull();

        tabs.addSelectedChangeListener(event -> {
            if (event.getSelectedTab().equals(patientDetails)) {
                gridAndFieldsLayout.removeAll();
                gridAndFieldsLayout.add(patient);
            } else if (event.getSelectedTab().equals(staffDetails)) {
                gridAndFieldsLayout.removeAll();
                gridAndFieldsLayout.add(staffDetail);
            } else if (event.getSelectedTab().equals(appointmentDetails)) {
                gridAndFieldsLayout.removeAll();
                gridAndFieldsLayout.add(appointmentGridTab);
            }
        });

        tabsLayout.add(tabs, gridAndFieldsLayout);
        tabs.add(patientDetails, staffDetails, appointmentDetails);
        gridAndFieldsLayout.add(patient);
        add(tabsLayout);

    }

}
