package com.skhealthcare.modules.homeview;

import com.skhealthcare.modules.receprionistview.ReceptionistView;
import com.skhealthcare.mvputil.BaseView;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

@Route(value = "homepage", layout = Template.class)
@UIScope
@SpringComponent
public class HomepageView extends BaseView<HomepagePresenter> {

    @Autowired
    HomepagePresenter homepagePresenter;

    VerticalLayout verticalLayout;
    HorizontalLayout horizontalLayout;
    HorizontalLayout horizontalLayout1;
    Button adminButton;
    Button receptionistButton;
    Button pharmacistLoginButton;
    Button doctorLoginButton;
    Button login;

    @Override
    public void init(){

        verticalLayout = new VerticalLayout();
        horizontalLayout = new HorizontalLayout();
        horizontalLayout1 = new HorizontalLayout();
        adminButton = new Button("Admin");
        receptionistButton = new Button("Receptionist");
        pharmacistLoginButton = new Button("Pharmacist");
        doctorLoginButton = new Button("Doctor");

        addButtonLayout();
    }

    public void addButtonLayout(){
        adminButton.getStyle().set("font-size","150%").set("color","white").set("background-color","blue")
                .set("height","100%").set("width","20%");
        receptionistButton.getStyle().set("font-size","150%").set("color","white").set("background-color","blue")
                .set("height","100%").set("width","20%");
        pharmacistLoginButton.getStyle().set("font-size","150%").set("color","white").set("background-color","blue")
                .set("height","100%").set("width","20%");
        doctorLoginButton.getStyle().set("font-size","150%").set("color","white").set("background-color","blue")
                .set("height","100%").set("width","20%");

        adminButton.setIcon(VaadinIcon.USER_STAR.create());
        receptionistButton.setIcon(VaadinIcon.USER_CHECK.create());
        doctorLoginButton.setIcon(VaadinIcon.USER_HEART.create());
        pharmacistLoginButton.setIcon(VaadinIcon.USER_CLOCK.create());

        adminButton.addClickListener(event -> onButtonCLick(adminButton.getText()));
        receptionistButton.addClickListener(event -> onButtonCLick(receptionistButton.getText()));
        doctorLoginButton.addClickListener(event -> onButtonCLick(doctorLoginButton.getText()));
        pharmacistLoginButton.addClickListener(event -> onButtonCLick(pharmacistLoginButton.getText()));

        verticalLayout.setSizeFull();
        verticalLayout.setAlignItems(Alignment.CENTER);
        verticalLayout.getStyle().set("margin-top","10%");
        horizontalLayout.setWidth(70, Unit.PERCENTAGE);
        horizontalLayout1.setWidth(70, Unit.PERCENTAGE);

        verticalLayout.add(adminButton, receptionistButton, doctorLoginButton, pharmacistLoginButton);
        add(verticalLayout);
    }

    public void onButtonCLick(String text){

        Dialog dialog = new Dialog();
        HorizontalLayout h1 = new HorizontalLayout();
        Button back = new Button("Back");
        Label label1 = new Label(text);
        FormLayout formLayout = new FormLayout();
        TextField username = new TextField("Username");
        PasswordField passwordField = new PasswordField("Password");
        login = new Button("Login");

        dialog.open();

        back.setIcon(VaadinIcon.ARROW_BACKWARD.create());
        back.getStyle().set("background-color","red").set("color","white");
        label1.getStyle().set("background-color","white").set("border-radius","10%")
                .set("color","black").set("font-size","200%").set("margin-top","5%").set("padding","0% 3%").set("font-weight","bold");
        login.getStyle().set("background-color","green").set("color","white");
        formLayout.setColspan(username,2);
        formLayout.setColspan(passwordField,2);
        formLayout.setColspan(username,2);
        formLayout.add(username,passwordField,login);

        login.addClickListener(event -> {
            dialog.close();
            homepagePresenter.checkUserAndLogin(text, username.getValue(), passwordField.getValue(), login);
        });
        back.addClickListener(event -> {
            dialog.close();
        });

        h1.add(back);
        dialog.add(h1);
        dialog.add(label1);
        dialog.add(formLayout);

    }
}
