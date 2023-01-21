package domain.modelo;

import jakarta.persistence.*;
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
@Table(name = "article")

@NamedQuery(name = "HQL_GET_ALL_ARTICLES",
        query = "from Article")

@NamedQuery(name = "HQL_DELETE_ALL_ARTICLES_BY_NEWSPAPER",
        query = "delete from Article a " +
                "where a.idNewspaper.id = :idNewspaper")

@NamedQuery(name = "HQL_GET_NUMBER_OF_ARTICLE_TYPE_BY_NEWSPAPER",
        query = "select a.type, count(a.type) " +
                "from Article a " +
                "where a.idNewspaper.id = :idNewspaper " +
                "group by a.type")

// It would be great to have the Newspaper object in the Subscribe object,
// but as I was required not to have it I decided to use a sub-query to get the
// newspaper ids from the subscribe table. This would be the query if I had the
// newspaper object in the Subscribe object:
// @NamedQuery(name = "HQL_GET_ALL_ARTICLES_BY_READER",
//         query = "select s.newspaperById.articles " +
//                 "from Subscribe s " +
//                 "where s.idReader = :idReader and s.cancellationDate is null")
@NamedQuery(name = "HQL_GET_ALL_ARTICLES_BY_READER",
        query = "select a " +
                "from Article a " +
                "where a.idNewspaper.id in " +
                "(select s.idNewspaper " +
                "from Subscribe s " +
                "where s.idReader = :idReader and s.cancellationDate is null)")

public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "name_article")
    private String nameArticle;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "id_type")
    private ArticleType type;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "id_newspaper")
    @ToString.Exclude
    private Newspaper idNewspaper;


    public Article(String nameArticle, ArticleType type) {
        this.nameArticle = nameArticle;
        this.type = type;
    }

    public Article(String name, ArticleType articletype, Newspaper newspaper) {
        this.nameArticle = name;
        this.type = articletype;
        this.idNewspaper = newspaper;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Article article = (Article) o;
        return id != null && Objects.equals(id, article.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}