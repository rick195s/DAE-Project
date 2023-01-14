package pt.ipleiria.estg.dei.ei.dae.project;

import com.github.javafaker.Faker;
import pt.ipleiria.estg.dei.ei.dae.project.ejbs.ClientBean;
import pt.ipleiria.estg.dei.ei.dae.project.entities.Client;
import pt.ipleiria.estg.dei.ei.dae.project.gateways.*;
import pt.ipleiria.estg.dei.ei.dae.project.pojos.*;

import javax.ejb.EJB;
import javax.enterprise.context.ApplicationScoped;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@ApplicationScoped
public class Supervisor {
    @EJB
    ClientBean clientBean;

    private final Faker faker = new Faker(new Locale("en"));

    private List<Policy> policies = new ArrayList<>();
    private List<RepairShop> repairShops = new ArrayList<>();
    private List<Insurer> insurers = new ArrayList<>();
    private List<PolicyTypeDetail> policyTypeDetails = new ArrayList<>();
    private List<PolicyObject> policyObjects = new ArrayList<>();

    public Supervisor() {
        populatePolicyTypeDetails();
        populatePolicyObejcts();
    }

    public List<Policy> getPolicies(int NIF) {
        refreshPoliciesViaAPI(NIF);
        return policies;
    }

    public List<Policy> getPolicies() {
        return getPolicies(0);
    }


    private void refreshPoliciesViaAPI(int NIF) {
        PolicyGateway gateway = new PolicyGateway();
        policies = gateway.getFromMockAPI(0, NIF);

        for (Policy policy : policies) {
            Client client = clientBean.findByNIFNIPC(policy.getClientNIFNIPC());
            if (client != null) {
                policy.setClient(client);
            }
        }
    }

    public List<RepairShop> getRepairShops() {
        refreshRepairShopsViaAPI();
        return repairShops;
    }

    public List<RepairShop> getRepairShops(int insurerId) {
        List<RepairShop> repairShopsOfInsurer = new ArrayList<>();

        InsurerGateway insurerGateway = new InsurerGateway();
        List<Integer> repairShopsIds = new ArrayList<>(insurerGateway.getInsurersRepairShopsFromMockAPI(insurerId));

        for (RepairShop repairShop : getRepairShops()) {
            if (repairShopsIds.contains(repairShop.getId())) {
                repairShopsOfInsurer.add(repairShop);
            }
        }
        return repairShopsOfInsurer;
    }

    public List<PolicyObject> getPolicyObjects() {
       populatePolicyObejcts();
        return policyObjects;
    }

    public PolicyObject getPolicyObject(String name) {
        for(PolicyObject policyObject :policyObjects){
            if(policyObject.getName().equals(name)){
               return policyObject;
            }
        }
        return null;
    }

    private void refreshRepairShopsViaAPI() {
        RepairShopGateway repairShopGateway = new RepairShopGateway();
        repairShops = repairShopGateway.getFromMockAPI();
    }

    public List<Insurer> getInsurers() {
        refreshInsurersViaAPI();
        return insurers;
    }

    private void populatePolicyTypeDetails() {
        PolicyTypeDetailsGateway gateway = new PolicyTypeDetailsGateway();
        policyTypeDetails = gateway.getFromMockAPI();
    }

    private void populatePolicyObejcts() {
        PolicyObjectGateway gateway = new PolicyObjectGateway();
        policyObjects = gateway.getFromMockAPI();
    }

    private void refreshInsurersViaAPI() {
        InsurerGateway gateway = new InsurerGateway();
        insurers = gateway.getFromMockAPI();
    }
    public List<PolicyTypeDetail> getPolicyTypeDetails() {
        populatePolicyTypeDetails();
        return policyTypeDetails;
    }


    public PolicyTypeDetail getPolicyTypeDetail(int id) {
        for(PolicyTypeDetail policyObject :policyTypeDetails){
            if(policyObject.getId()==id){
                return policyObject;
            }
        }
        return null;
    }

    public List<Integer> getPoliciesIds(int insurerId) {
        PolicyGateway gateway = new PolicyGateway();
        policies = gateway.getFromMockAPI(insurerId, 0);
        return policies.stream()
                .map(Policy::getId)
                .collect(Collectors.toList());
    }
}
