package com.skhealthcare.modules.doctorlogin;

import com.skhealthcare.modules.homepage.Template;
import com.skhealthcare.mvputil.BaseView;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.UIScope;

@UIScope
@Route(value = "demo", layout = Template.class)
public class Demo extends BaseView {



    public Demo() {
//        add(new Label("Hello"));
    }

}
