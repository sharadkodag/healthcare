package com.skhealthcare.modules.mainpage;

import com.skhealthcare.mvputil.BaseView;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Footer;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;

@Route("")
@SpringComponent
@UIScope
public class MainPageView extends BaseView<MainPagePresenter> {

    Label label;
    HorizontalLayout h1;

    public MainPageView(){
        removeAll();
        setPadding(false);
        setAlignItems(Alignment.CENTER);
        H1 heading = new H1("SK Healthcare");
        heading.getStyle().set("background-color", "green").set("padding-left","7%").set("padding-top","1%")
                .set("font-weight", "bold").set("color", "white").set("font-size","xx-larger").set("margin-top","0px")
                .set("box-shadow","25% 25% black");
        add(heading);
        heading.setWidth(93,Unit.PERCENTAGE);
        setSizeFull();
        setPadding(false);
        getStyle().set("background-image", "url('images/Untitled.png')").set("background-repeat", "no-repeat")
                .set("background-position", "center").set("background-size","cover").set("background-attachment","fixed");
        addComponents();
    }

    public void addComponents(){

//        label = new Label("Login as");
//        label.getStyle().set("background-color","coral").set("border-radius","10%").set("margin-left","40%")
//                .set("color","white").set("font-size","200%").set("margin-top","5%").set("padding","0% 3%").set("font-weight","bold");
//        add(label);

        Label label1 = new Label("Health is wealth");
        label1.getStyle().set("background-image","linear-gradient(to right,rgba(255,255,255,0),rgba(255,255,255,1), rgba(255,255,255,0))").set("color", "green").set("padding-left", "45%")
                .set("font-size","200%").set("font-weight","bold");
        label1.setWidth(55, Unit.PERCENTAGE);
        add(label1);

        Button adminButton = new Button("Admin");
        adminButton.getStyle().set("transition","width 2s");
        adminButton.getStyle().set("background-color","white").set("font-size","120%");
        Button receptionistButton = new Button("Receptionist");
        receptionistButton.getStyle().set("background-color","white").set("font-size","120%");
        Button doctorButton = new Button("Doctor");
        doctorButton.getStyle().set("background-color","white").set("font-size","120%");
        Button pharmacistButton = new Button("Pharmacist");
        pharmacistButton.getStyle().set("background-color","white").set("font-size","120%");

        Icon adminIcon = VaadinIcon.USER_STAR.create();
        adminIcon.setSize("10em");
        adminIcon.setColor("blue");
        Icon pharmacistIcon = VaadinIcon.USER.create();
        pharmacistIcon.setSize("10em");
        pharmacistIcon.setColor("blue");
        Icon receptionistIcon = VaadinIcon.USER_CHECK.create();
        receptionistIcon.setSize("10em");
        receptionistIcon.setColor("blue");
        Icon doctorIcon = VaadinIcon.USER_HEART.create();
        doctorIcon.setSize("10em");
        doctorIcon.setColor("blue");

        h1 = new HorizontalLayout();
        h1.setWidth(93, Unit.PERCENTAGE);
        h1.setAlignItems(Alignment.CENTER);
        h1.getStyle().set("margin-top","5%");
        VerticalLayout v1 =new VerticalLayout();
        v1.setAlignItems(Alignment.CENTER);
        v1.add(adminIcon,adminButton);
        VerticalLayout v2 =new VerticalLayout();
        v2.setAlignItems(Alignment.CENTER);
        v2.add(doctorIcon,doctorButton);
        VerticalLayout v3 =new VerticalLayout();
        v3.setAlignItems(Alignment.CENTER);
        v3.add(receptionistIcon,receptionistButton);
        VerticalLayout v4 =new VerticalLayout();
        v4.setAlignItems(Alignment.CENTER);
        v4.add(pharmacistIcon,pharmacistButton);
        h1.add(v1,v2,v3,v4);
        h1.setSizeFull();
        h1.getStyle().set("margin-bottom","20%");
        add(h1);

        v3.addClickListener(event -> {
            loginLayout(receptionistButton.getText());
        });
        v2.addClickListener(event -> {
            loginLayout(doctorButton.getText());
        });
        v4.addClickListener(event -> {
            loginLayout(pharmacistButton.getText());
        });
        v1.addClickListener(event -> {
            loginLayout(adminButton.getText());
        });

        Label label = new Label("Copyright @Direction Software");
        label.setWidth(55, Unit.PERCENTAGE);
        label.getStyle().set("background-color", "black").set("padding-left","45%")
                .set("font-weight", "bold").set("color","white");
        add(label);
//        Footer footer = new Footer();
//        footer.add(label);
//        add(footer);
    }

    public void loginLayout(String text){

        Dialog dialog = new Dialog();
        dialog.open();

        Button back = new Button("Back");
        back.setIcon(VaadinIcon.ARROW_BACKWARD.create());
        back.getStyle().set("background-color","red").set("color","white");
        HorizontalLayout h1 = new HorizontalLayout();
        h1.add(back);

        dialog.add(h1);


        Label label1 = new Label(text + " login");
        label1.getStyle().set("background-color","white").set("border-radius","10%")
                .set("color","black").set("font-size","200%").set("margin-top","5%").set("padding","0% 3%").set("font-weight","bold");
        dialog.add(label1);

        FormLayout formLayout = new FormLayout();
        TextField username = new TextField("Username");
        PasswordField passwordField = new PasswordField("Password");
        Button login = new Button("Login");
        login.getStyle().set("background-color","green").set("color","white");

        formLayout.setColspan(username,2);
        formLayout.setColspan(passwordField,2);
        formLayout.setColspan(username,2);
        formLayout.add(username,passwordField,login);
        dialog.add(formLayout);

        back.addClickListener(event -> {
            dialog.close();
        });


    }



}
