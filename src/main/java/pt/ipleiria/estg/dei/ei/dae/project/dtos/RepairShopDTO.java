package pt.ipleiria.estg.dei.ei.dae.project.dtos;

import pt.ipleiria.estg.dei.ei.dae.project.pojos.RepairShop;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

public class RepairShopDTO implements Serializable {

    int id;
    String name;
    String email;
    long phone;

    public RepairShopDTO(int id, String name, String email, long phone) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
    }

    public RepairShopDTO() {
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

    public static RepairShopDTO from(RepairShop repairShop) {
        return new RepairShopDTO(
                repairShop.getId(),
                repairShop.getName(),
                repairShop.getEmail(),
                repairShop.getPhone()
        );
    }

    public static List<RepairShopDTO> from(List<RepairShop> repairShops) {
        return repairShops.stream().map(RepairShopDTO::from).collect(Collectors.toList());
    }
}

