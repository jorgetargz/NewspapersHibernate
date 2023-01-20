package domain.modelo;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.Hibernate;

import java.util.Objects;

@Getter
@Setter
@RequiredArgsConstructor

@Entity
@Table(name = "articletype")

@NamedQuery(name = "HQL_GET_ALL_ARTICLETYPES", query = "FROM ArticleType")

@NamedQuery(name = "HQL_GET_MOST_READ_ARTICLE_TYPE",
        query = "select ra.articleById.type " +
                "from Readarticle ra " +
                "group by ra.articleById.type " +
                "order by count(ra.articleById.type) desc")

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