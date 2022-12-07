package com.skhealthcare.modules.homeview;

import com.skhealthcare.entity.Staff;
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
        if(staff!=null) {
            login.getUI().ifPresent(e -> e.navigate(ReceptionistView.class));
            Notification validUser = Notification.show("Successfully Login", 3000, Notification.Position.TOP_CENTER);
            validUser.addThemeVariants(NotificationVariant.LUMO_ERROR);
        }else {
            Notification invalidUser = Notification.show("Invalid User", 3000, Notification.Position.TOP_CENTER);
            invalidUser.addThemeVariants(NotificationVariant.LUMO_ERROR);
        }
    }

}
