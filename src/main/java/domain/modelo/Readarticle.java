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

@NamedQuery(name = "HQL_DELETE_READARTICLE_BY_READER",
        query = "delete from Readarticle ra " +
                "where ra.readerById = :reader")

@NamedQuery(name = "HQL_UPDATE_READARTICLE_BY_ARTICLE_ID_AND_READER_ID",
        query = "update Readarticle ra " +
                "set ra.rating = :rating " +
                "where ra.articleById = :article " +
                "and ra.readerById = :reader")

@NamedQuery(name = "HQL_GET_AVERAGE_RATING_BY_READER",
        query = "select avg(ra.rating) as AVG_RATING , ra.articleById.idNewspaper.nameNewspaper as Newspaper " +
                "from Readarticle ra " +
                "where ra.readerById.id = :idReader " +
                "group by ra.articleById.idNewspaper.nameNewspaper")

public class Readarticle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "rating", nullable = false)
    private Integer rating;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_article", referencedColumnName = "id", nullable = false)
    @ToString.Exclude
    private Article articleById;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_reader", referencedColumnName = "id", nullable = false)
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