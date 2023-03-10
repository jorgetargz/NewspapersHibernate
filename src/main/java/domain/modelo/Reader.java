package domain.modelo;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import java.time.LocalDate;
import java.util.Objects;

@Getter
@Setter
@ToString
@RequiredArgsConstructor

@Entity
@Table(name = "reader")

@NamedQuery(name = "HQL_GET_ALL_READERS",
        query = "select r from Reader r " +
                "where r.login.role = 'READER'")

@NamedQuery(name = "HQL_GET_ALL_READERS_BY_ARTICLE_TYPE",
        query = "select a.readerById " +
                "from Readarticle a " +
                "where a.articleById.type = :type")

@NamedQuery(name = "HQL_GET_ALL_READERS_BY_NEWSPAPER_SUBSCRIPTION_ACTIVE",
        query = "select s.readerByIdReader " +
                "from Subscribe s " +
                "where s.idNewspaper = :idNewspaper " +
                "and s.cancellationDate is null")

public class Reader {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name_reader")
    private String name;

    @Column(name = "birth_reader")
    private LocalDate dateOfBirth;

    @OneToOne(mappedBy = "reader", cascade = {CascadeType.REMOVE, CascadeType.MERGE, CascadeType.PERSIST})
    private Login login;


    public Reader(String nameInput, LocalDate birthdayInput, Login loginInput) {
        this.name = nameInput;
        this.dateOfBirth = birthdayInput;
        this.login = loginInput;
    }

    public Reader(int id, String nameInput, LocalDate birthdayInput) {
        this.id = id;
        this.name = nameInput;
        this.dateOfBirth = birthdayInput;
    }

    //USED IN HIBERNATE TEST ONLY
    public Reader(String nameInput, LocalDate birthdayInput) {
        this.name = nameInput;
        this.dateOfBirth = birthdayInput;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Reader reader = (Reader) o;
        return id != null && Objects.equals(id, reader.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
