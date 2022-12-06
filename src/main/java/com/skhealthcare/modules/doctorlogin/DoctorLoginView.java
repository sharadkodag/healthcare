package com.skhealthcare.modules.doctorlogin;

import com.skhealthcare.modules.login.LoginView;
import com.skhealthcare.mvputil.BaseView;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

@UIScope
@SpringComponent
@Route("doctorLogin")
public class DoctorLoginView extends BaseView<DoctorLoginPresenter> {

    @Autowired
    LoginView loginView;

    @PostConstruct
    public void init(){
        loginView.addHeading();
        addLayout();
        addDoctorLoginTabs();
        loginView.addFooter();
    }

    public void addLayout(){

        Label label = new Label("Doctor's Portal");
        label.getStyle().set("background-color", "blue").set("padding-left","40%")
                .set("color","white").set("font-weight", "bold").set("font-size", "xx-large");
        label.setWidth(60, Unit.PERCENTAGE);
        add(label);

    }

    public void addDoctorLoginTabs() {

        VerticalLayout verticalLayout = loginView.addPatientFields();

        verticalLayout.removeAll();

        Tabs doctorTabs = new Tabs();
        Tab tab = new Tab("Appointments");
        Tab tab1 = new Tab("Patient Details");

        doctorTabs.add(tab, tab1);

        VerticalLayout verticalLayout1 = new VerticalLayout();

        doctorTabs.addSelectedChangeListener(event -> {
            if(event.getSelectedTab().equals(tab)){
                verticalLayout1.removeAll();
                verticalLayout1.add();
            }else if(event.getSelectedTab().equals(tab1)){
                verticalLayout1.removeAll();
                verticalLayout1.add(verticalLayout);
            }
        });

        verticalLayout.add(doctorTabs,verticalLayout1);

        add(verticalLayout);

    }


}
