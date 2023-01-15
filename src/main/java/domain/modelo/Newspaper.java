package domain.modelo;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@ToString
@RequiredArgsConstructor

@Entity
@Table(name = "newspaper")

@NamedQuery(name = "HQL_GET_ALL_NEWSPAPERS",
        query = "from Newspaper")

public class Newspaper {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name_newspaper")
    private String nameNewspaper;

    @Column(name = "release_date")
    private LocalDate releaseDate;

    @OneToMany(mappedBy = "idNewspaper", fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    private Set<Article> articles = new LinkedHashSet<>();

    public Newspaper(String name, LocalDate releaseDate) {
        this.nameNewspaper = name;
        this.releaseDate = releaseDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Newspaper newspaper = (Newspaper) o;
        return id != null && Objects.equals(id, newspaper.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return nameNewspaper;
    }
}
