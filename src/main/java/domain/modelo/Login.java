package domain.modelo;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString

@Entity
@Table(name = "login")

public class Login {

    @Id
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "mail")
    private String email;

    @OneToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    @JoinColumn(name = "id_reader", nullable = false)
    @ToString.Exclude
    private Reader reader;

    @Column(name = "role")
    private String role;

    public Login(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public Login() {

    }

    public Login(String username, String password, String email, Reader reader) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.reader = reader;
    }
}
