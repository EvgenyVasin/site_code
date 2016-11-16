package ru.jskills.entities;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Created by safin.v on 22.10.2016.
 */
@Entity
@Table(name="users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long userId;

    @Column(name = "username", unique = true, length = 64, nullable = false)
    private String username;

    @Column(name = "password", length = 256, nullable = false)
    private String password;

    @Column(name = "e_mail", length = 256, nullable = false)
    private String mail;

    @Column(name = "first_name", length = 128)
    private String firstName;

    @Column(name = "last_name", length = 128)
    private String lastName;

    @Column(name = "enabled", length = 1, nullable = false)
    private boolean enabled;

    @Column(name = "img_link", length = 256)
    private String imgLink;

    @Column(name = "img_content_type", length = 256)
    private String imgContentType;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private UserRole userRole;


    @Column(name = "date_registration")
    private Date dateRegistration;

    @Column(name = "last_date")
    private Date lastDate;
    /**
     * @return the userId
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * @param userId the userId to set
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the e-mail
     */
    public String getMail() {
        return mail;
    }
    /**
     * @param mail the e-mail to set
     */
    public void setMail(String mail) {
        this.mail = mail;
    }

    /**
     * @return the firstName
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * @param firstName the firstName to set
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * @return the lastName
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * @param lastName the lastName to set
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    /**
     * @return the userRole
     */
    public UserRole getUserRole() {
        return userRole;
    }

    /**
     * @param userRole the userRoles to set
     */
    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }

    public Date getDateRegistration() {
        return dateRegistration;
    }

    public void setDateRegistration(Date dateRegistration) {
        this.dateRegistration = dateRegistration;
    }

    public Date getLastDate() {
        return lastDate;
    }

    public void setLastDate(Date lastDate) {
        this.lastDate = lastDate;
    }

    public String getImgLink() {
        return imgLink;
    }

    public void setImgLink(String imgLink) {
        this.imgLink = imgLink;
    }

    public String getImgContentType() {
        return imgContentType;
    }

    public void setImgContentType(String imgContentType) {
        this.imgContentType = imgContentType;
    }

    /* (non-Javadoc)
             * @see java.lang.Object#toString()
             */
    @Override
    public String toString() {
        return "User [userId=" + userId + ", username=" + username
                + ", password=" + password + ", firstName=" + firstName
                + ", lastName=" + lastName
                + ", userRoles=" + userRole
                + ", enabled=" + enabled +"]";
    }


}
