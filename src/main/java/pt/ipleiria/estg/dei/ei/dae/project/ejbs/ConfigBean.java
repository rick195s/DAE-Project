package pt.ipleiria.estg.dei.ei.dae.project.ejbs;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;

@Startup
@Singleton
public class ConfigBean {
    @EJB
    CourseBean courseBean;

    @PostConstruct
    public void populateDB() {
        System.out.println("Hello Java EE!");

        courseBean.create(1, "Engenharia Informática");

    }
}
