package domain.modelo;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import java.util.Objects;

@Getter
@Setter
@ToString
@RequiredArgsConstructor

@Entity
@Table(name = "readarticle")

@NamedQuery(name = "HQL_GET_ALL_READARTICLES", query = "SELECT r FROM Readarticle r")

@NamedQuery(name = "HQL_DELETE_READARTICLE_BY_READER",
        query = "delete from Readarticle ra where ra.readerById = :reader")

public class Readarticle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "rating", nullable = false)
    private Integer rating;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_article", referencedColumnName = "id", insertable = false, updatable = false)
    @ToString.Exclude
    private Article articleById;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_reader", referencedColumnName = "id", insertable = false, updatable = false)
    @ToString.Exclude
    private Reader readerById;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Readarticle that = (Readarticle) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}