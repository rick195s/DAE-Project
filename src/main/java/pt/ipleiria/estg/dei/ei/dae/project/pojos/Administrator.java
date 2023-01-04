package pt.ipleiria.estg.dei.ei.dae.project.pojos;

import pt.ipleiria.estg.dei.ei.dae.project.entities.User;

public class Administrator extends User {
    public Administrator(String username, String password, String name, String email) {
        super(username, password, name, email);
    }

    public Administrator() {
    }
}
