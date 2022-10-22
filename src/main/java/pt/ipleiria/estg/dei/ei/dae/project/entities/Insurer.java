package pt.ipleiria.estg.dei.ei.dae.project.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;

@Entity
@NamedQueries({
        @NamedQuery(
                name = "getAllInsurers",
                query = "SELECT u FROM Insurer u ORDER BY u.name" // JPQL
        )
})
@Table(name = "Insurers")
public class Insurer implements Serializable {
    @Id
    int id;

    @NotNull
    String name;

    @NotNull
    @OneToMany(mappedBy = "insurer")
    List<InsurerExpert> insurer_experts;

    @ManyToMany(fetch = FetchType.EAGER,mappedBy = "insurers")
    List<RepairShop> repairShops;


    public Insurer() {
        insurer_experts = new LinkedList<>();
    }

    public Insurer(int id, String name) {
        this.id = id;
        this.name = name;
        insurer_experts = new LinkedList<>();
        repairShops = new LinkedList<>();
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

    public List<InsurerExpert> getInsurer_experts() {
        return insurer_experts;
    }

    public void setInsurer_experts(List<InsurerExpert> insurer_experts) {
        this.insurer_experts = new LinkedList<>(insurer_experts);
    }

    public void populateTable() {
        try {
            URL url = new URL("https://634f1183df22c2af7b4a4b38.mockapi.io/Insurers");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestProperty("Accept", "application/json");
            con.setDoOutput(true);
            con.setDoInput(true);
            con.connect();
            System.out.println(con.getResponseCode());
            System.out.println(con.getResponseMessage());

            BufferedReader responseReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String responseLine;
            while ((responseLine = responseReader.readLine()) != null) {
                System.out.println(responseLine);
            }
            responseReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
