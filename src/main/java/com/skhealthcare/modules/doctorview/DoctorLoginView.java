package com.skhealthcare.modules.doctorview;

import com.skhealthcare.entity.Appointment;
import com.skhealthcare.entity.Department;
import com.skhealthcare.entity.Staff;
import com.skhealthcare.entity.Patient;
import com.skhealthcare.modules.doctorview.tabs.AppointmentsTab;
import com.skhealthcare.modules.doctorview.tabs.PatientTab;
import com.skhealthcare.modules.homeview.HomepageView;
import com.skhealthcare.modules.homeview.Template;
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
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.*;

@UIScope
@SpringComponent
@Route(value = "doctor", layout = Template.class)
public class DoctorLoginView extends BaseView<DoctorLoginPresenter> {
    @Autowired
    PatientTab patientTab;
    @Autowired
    AppointmentsTab appointmentsTab;
    VerticalLayout tabsLayout;
    HorizontalLayout gridAndFieldsLayout;
    Tabs tabs;
    Tab patientDetails;
    Tab appointments;
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
        gridAndFieldsLayout = new HorizontalLayout();
        appointments = new Tab();
        logoutButton = new Button("Logout");

        logoutButton.getStyle().set("background-color", "red").set("color","white");
        setMargin(false);
        setPadding(false);
        setSizeFull();

        gridAndFieldsLayout.add(patientTab);
        addLayout();
        addMenuBarLayout();
    }

    public void addLayout(){
        Label label = new Label("Doctor's Portal");
        VerticalLayout verticalLayout = new VerticalLayout();

        label.getStyle().set("background-color", "blue").set("padding-left","45%")
                .set("color","white").set("font-weight", "bold").set("font-size", "xx-large");
        label.setWidth(55, Unit.PERCENTAGE);
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
        appointments.setLabel("Appointment");

        tabsLayout.setSizeFull();
        tabsLayout.getStyle().set("background-color", "white");
        gridAndFieldsLayout.setSizeFull();
        gridAndFieldsLayout.getStyle().set("background-color", "white");

        tabsLayout.add(tabs, gridAndFieldsLayout);
        tabs.add(patientDetails);
        tabs.add(appointments);
        ;
        add(tabsLayout);

        tabsLayout.setSizeFull();

        tabs.addSelectedChangeListener(event -> {
            if (event.getSelectedTab().equals(patientDetails)) {
                gridAndFieldsLayout.removeAll();
                gridAndFieldsLayout.add(patientTab);
            } else if (event.getSelectedTab().equals(appointments)) {
                gridAndFieldsLayout.removeAll();
                gridAndFieldsLayout.add(appointmentsTab);
            }
        });
    }
}
