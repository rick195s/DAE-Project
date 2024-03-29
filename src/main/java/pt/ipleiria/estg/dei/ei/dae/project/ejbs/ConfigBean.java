package pt.ipleiria.estg.dei.ei.dae.project.ejbs;

import com.github.javafaker.Faker;
import pt.ipleiria.estg.dei.ei.dae.project.Supervisor;
import pt.ipleiria.estg.dei.ei.dae.project.entities.Client;
import pt.ipleiria.estg.dei.ei.dae.project.exceptions.OccurrenceSmallDescriptionException;
import pt.ipleiria.estg.dei.ei.dae.project.exceptions.UserDontHavePolicyException;
import pt.ipleiria.estg.dei.ei.dae.project.gateways.PolicyGateway;
import pt.ipleiria.estg.dei.ei.dae.project.pojos.*;
import pt.ipleiria.estg.dei.ei.dae.project.security.enums.Role;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import javax.mail.MessagingException;
import java.text.SimpleDateFormat;
import java.util.*;

@Startup
@Singleton
public class ConfigBean {
    @EJB
    ClientBean clientBean;

    @EJB
    PolicyBean policyBean;

    @EJB
    RepairShopBean repairShopBean;

    @EJB
    UserBean userBean;

    @EJB
    RepairShopExpertBean repairShopExpertBean;

    @EJB
    InsurerExpertBean insurerExpertBean;

    @EJB
    InsurerBean insurerBean;

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

        try {
            createOccurrences();
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }

    }

    private void createClients() {
        for (int i = 0; i < 20; i++) {
            clientBean.create(faker.name().fullName(), faker.internet().emailAddress(), "123", ((int) faker.number().randomNumber(9, true)));
        }
        clientBean.create("Tiago Santos", "tiago@mail.pt", "123", 333333333);
    }

    private void createAdmins() {
        for (int i = 0; i < 6; i++) {
            userBean.create(faker.name().fullName(), faker.internet().emailAddress(), "123",String.valueOf(Role.ADMINISTRATOR));
        }
        userBean.create("manel", "manel@gmail.com", "123",String.valueOf(Role.ADMINISTRATOR));
    }

    private void createRepairShopExperts() {
        List<RepairShop> repairShops = repairShopBean.getAllRepairShops();
        int max = Math.min(repairShops.size(), 20);
        for (int i = 0; i < max; i++) {
            repairShopExpertBean.create(faker.name().fullName(), faker.internet().emailAddress(), "123", repairShops.get(i).getId());
        }
    }

    private void createInsurerExperts() {
        List<Insurer> insurers = insurerBean.getAllInsurers();
        int max = Math.min(insurers.size(), 20);
        for (int i = 0; i < max; i++) {
            insurerExpertBean.create(faker.name().fullName(), faker.internet().emailAddress(), "123", 1/* insurers.get(i).getId()*/);
        }

        insurerExpertBean.create("Insurer Expert Example", "insurer.expert@mail.pt", "123", 1/* insurers.get(i).getId()*/);
    }

    private void createOccurrences() throws MessagingException {
        List<Policy> policies = policyBean.getAllPolicies();
        int max = Math.min(policies.size(), 20);
        Client client =  clientBean.findByNIFNIPC(333333333);

        int occurrenceId = 1;
        for (int i = 0; i < max; i++) {
            int id = client != null ? client.getId() : i + 1;

            try {
                 occurrenceId = occurrenceBean.create(policies.get(i).getId(), faker.lorem().sentence(10), id).getId();
            } catch (OccurrenceSmallDescriptionException | UserDontHavePolicyException e) {
                throw new RuntimeException(e);
            }
        }

        occurrenceBean.setOccurrenceRepairShop(occurrenceId, repairShopBean.getAllRepairShops().get(0).getId());
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
