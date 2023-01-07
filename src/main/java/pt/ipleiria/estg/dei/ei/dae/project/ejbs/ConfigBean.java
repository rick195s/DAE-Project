package pt.ipleiria.estg.dei.ei.dae.project.ejbs;

import com.github.javafaker.Faker;
import pt.ipleiria.estg.dei.ei.dae.project.Supervisor;
import pt.ipleiria.estg.dei.ei.dae.project.entities.Client;
import pt.ipleiria.estg.dei.ei.dae.project.entities.User;
import pt.ipleiria.estg.dei.ei.dae.project.exceptions.OccurrenceSmallDescriptionException;
import pt.ipleiria.estg.dei.ei.dae.project.gateways.PolicyGateway;
import pt.ipleiria.estg.dei.ei.dae.project.gateways.RepairShopGateway;
import pt.ipleiria.estg.dei.ei.dae.project.pojos.*;
import pt.ipleiria.estg.dei.ei.dae.project.security.enums.Role;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import java.text.SimpleDateFormat;
import java.util.*;

@Startup
@Singleton
public class ConfigBean {
    @EJB
    ClientBean clientBean;

    @EJB
    UserBean userBean;

    @EJB
    OccurrenceBean occurrenceBean;

    @Inject
    Supervisor supervisor;

    private final Faker faker = new Faker(new Locale("en"));

    @PostConstruct
    public void populateDB() {
        System.out.println("Hello Java EE!");

        createClients();
        createAdmins();
        createRepairShopExperts();
        createInsurerExperts();
        // populateMockAPI();

       createOccurrences();

    }

    private void createClients() {
        for (int i = 0; i < 20; i++) {
            clientBean.create(faker.name().fullName(), faker.internet().emailAddress(), "123", String.valueOf(Role.CLIENT), ((int) faker.number().randomNumber(9, true)));
        }
    }

    private void createAdmins() {
        for (int i = 0; i < 6; i++) {
            userBean.create(faker.name().fullName(), faker.internet().emailAddress(), "123",String.valueOf(Role.ADMINISTRATOR));
        }
    }

    private void createRepairShopExperts() {
        for (int i = 0; i < 6; i++) {
            userBean.create(faker.name().fullName(), faker.internet().emailAddress(), "123",String.valueOf(Role.REPAIR_SHOP_EXPERT));
        }
    }

    private void createInsurerExperts() {
        for (int i = 0; i < 6; i++) {
            userBean.create(faker.name().fullName(), faker.internet().emailAddress(), "123",String.valueOf(Role.INSURER_EXPERT));
        }
    }

    private void createOccurrences() {
        for (int i = 0; i < 20; i++) {
            try {
                occurrenceBean.create(supervisor.getPolicies().get(0).getId(), faker.lorem().sentence(10), i+1);
            } catch (OccurrenceSmallDescriptionException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void populateMockAPI() {
        populatePoliciesInAPI();
    }

    private void populatePoliciesInAPI() {
        Calendar calendar = Calendar.getInstance(
                TimeZone.getTimeZone("UTC"));

        calendar.set(2021, Calendar.JULY, 1);
        Calendar calendar2 = Calendar.getInstance(
                TimeZone.getTimeZone("UTC"));
        calendar2.set(2021, Calendar.DECEMBER, 2);

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


        PolicyGateway gateway = new PolicyGateway();
        for (Policy policy : supervisor.getPolicies()) {
            gateway.postToMockAPI(policy);
        }
    }
}
