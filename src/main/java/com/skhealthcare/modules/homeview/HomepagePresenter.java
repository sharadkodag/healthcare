package com.skhealthcare.modules.homeview;

import com.skhealthcare.entity.Staff;
import com.skhealthcare.modules.adminview.AdminView;
import com.skhealthcare.modules.doctorview.DoctorLoginView;
import com.skhealthcare.modules.receprionistview.ReceptionistView;
import com.skhealthcare.mvputil.BasePresenter;
import com.skhealthcare.service.StaffService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;

@SpringComponent
@UIScope
public class HomepagePresenter extends BasePresenter<HomepageView> {

    @Autowired
    StaffService service;
    public void checkUserAndLogin(String text, String username, String password, Button login){
        Staff staff = service.checkStaff(text, username, password);
        Notification validUser = Notification.show("Successfully Login", 3000, Notification.Position.TOP_CENTER);
        validUser.addThemeVariants(NotificationVariant.LUMO_SUCCESS);

        if(staff!=null) {
            if(text.equals("Doctor")){
                login.getUI().ifPresent(e -> e.navigate(DoctorLoginView.class));
                validUser.setOpened(true);
            } else if (text.equals("Admin")) {
                login.getUI().ifPresent(e -> e.navigate(AdminView.class));
                validUser.setOpened(true);
            } else if (text.equals("Receptionist")) {
                login.getUI().ifPresent(e -> e.navigate(ReceptionistView.class));
                validUser.setOpened(true);
            } else {
                Notification.show("Invalid User. Contact Admin");
            }


        }else {
            Notification invalidUser = Notification.show("Invalid User", 3000, Notification.Position.TOP_CENTER);
            invalidUser.addThemeVariants(NotificationVariant.LUMO_ERROR);
        }
    }

}
