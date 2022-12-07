package pt.ipleiria.estg.dei.ei.dae.project.pojos;

import pt.ipleiria.estg.dei.ei.dae.project.entities.Occurrence;

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

    String phone;

    List<RepairShopExpert> repairShopExperts;

    List<Occurrence> occurrences;

    List<Insurer> insurers;

    public RepairShop(int id, String name, String email, String phone) {
        this.id = id;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}