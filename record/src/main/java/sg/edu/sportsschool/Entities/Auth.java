package sg.edu.sportsschool.Entities;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "auth")
@JsonIgnoreProperties({"privateKey", "publicKey"})
public class Auth {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(columnDefinition = "TEXT", name = "private_key")
    private String privateKey;

    @Column(columnDefinition = "TEXT", name = "public_key")
    private String publicKey;

    @Column(name = "created_date")
    private Date createdDate;

    public Auth() {}

    public Auth(String privateKey, String publicKey) {
        this.privateKey = privateKey;
        this.publicKey = publicKey;
        this.createdDate = new Date();
    }

    public Integer getId() {
        return id;
    }

    public String getPrivateKey() {
        return privateKey;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public Date getCreatedDate() {
        return createdDate;
    }
}
