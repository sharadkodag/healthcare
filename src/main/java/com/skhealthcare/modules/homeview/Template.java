package com.skhealthcare.modules.homeview;

import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.RouterLayout;
import com.vaadin.flow.server.VaadinSession;

import javax.annotation.PostConstruct;
import java.awt.*;

public class Template extends VerticalLayout implements RouterLayout {

    Button logout = new Button("Logout");

    @PostConstruct
    public void init(){
        addHeading();
    }

    public void addHeading(){
        setPadding(false);
        setAlignItems(Alignment.CENTER);
        setSizeFull();

        H1 heading = new H1("SK Healthcare");
        HorizontalLayout horizontalLayout = new HorizontalLayout();

        Label label1 = new Label("Health is wealth");

        logout.setVisible(false);
        horizontalLayout.setWidthFull();
        heading.setWidth(98, Unit.PERCENTAGE);
        heading.getStyle().set("background-color", "green").set("padding-left","2%").set("padding-top","1%")
                .set("font-weight", "bold").set("color", "white").set("font-size","xx-larger").set("margin-top","0px");
        getStyle().set("background-image", "url('images/Untitled.png')").set("background-repeat", "no-repeat")
                .set("background-position", "center").set("background-size","cover").set("background-attachment","fixed");
        label1.getStyle().set("background-image","linear-gradient(to right,rgba(255,255,255,0),rgba(255,255,255,1), rgba(255,255,255,0))").set("color", "green").set("padding-left", "45%")
                .set("font-size","200%").set("font-weight","bold");
        label1.setWidth(50, Unit.PERCENTAGE);
        logout.getStyle().set("color", "white").set("background-color", "red");

        logout.addClickListener(event -> {
            VaadinSession.getCurrent().close();
            logout.getUI().ifPresent(e -> e.navigate(HomepageView.class));
        });

        horizontalLayout.add(label1, logout);
        add(heading);
        add(horizontalLayout);

    }

}
