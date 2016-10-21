package ru.jskills.domain;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.Size;

/**
 * Created by safin.v on 19.10.2016.
 */
@Entity
@Table(name = "USER")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IDUSER")
    private int idUser;

    @NotEmpty
    @Size(min = 5, max = 20)
    @Column(name = "USERNAME")
    private String username;

    @NotEmpty
    @Size(min=5, max=20)
    @Column(name="PASSWORD")
    private String password;

    @Column(name="IDROLE")
    private int idRole;

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getidRole() {
        return idRole;
    }

    public void setidRole(int idRole) {
        this.idRole= idRole;
    }

    @Override
    public String toString() {
        return "User{" +
                "idUser=" + idUser +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", idrole=" + idRole +
                '}';
    }
}