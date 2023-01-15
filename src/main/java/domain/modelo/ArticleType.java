package domain.modelo;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@ToString
@RequiredArgsConstructor

@Entity
@Table(name = "articletype")

@NamedQuery(name = "HQL_GET_ALL_ARTICLETYPES", query = "SELECT a FROM ArticleType a")
public class ArticleType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "description")
    private String description;

    public ArticleType(Integer id, String description) {
        this.id = id;
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        ArticleType that = (ArticleType) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public String toString() {
        return description;
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}