package pt.ipleiria.estg.dei.ei.dae.project.pojos;

import pt.ipleiria.estg.dei.ei.dae.project.entities.Occurrence;
import pt.ipleiria.estg.dei.ei.dae.project.entities.RepairShopExpert;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

public class RepairShop implements Serializable {
    int id;

    String name;

    String email;

    long phone;

    List<RepairShopExpert> repairShopExperts;

    List<Occurrence> occurrences;

    List<Insurer> insurers;

    public RepairShop(int id, String name, String email, long phone) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        insurers= new LinkedList<>();
        repairShopExperts = new LinkedList<>();
        occurrences = new LinkedList<>();
    }

    public RepairShop(String name, String email, long phone) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        insurers= new LinkedList<>();
        repairShopExperts = new LinkedList<>();
        occurrences = new LinkedList<>();
    }

    public RepairShop() {
        insurers= new LinkedList<>();
        repairShopExperts = new LinkedList<>();
        occurrences = new LinkedList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public long getPhone() {
        return phone;
    }

    public void setPhone(long phone) {
        this.phone = phone;
    }

    public List<Insurer> getInsurers() {
        return insurers;
    }

    public void addInsurer(List<Insurer> insurers) {
        this.insurers = insurers;
    }
}
