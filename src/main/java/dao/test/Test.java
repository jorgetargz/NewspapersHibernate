package dao.test;

import dao.impl.ReadArticleDaoImpl;
import dao.impl.ReadersDaoImpl;
import dao.utils.JPAUtil;
import domain.modelo.Article;
import domain.modelo.Readarticle;
import domain.modelo.Reader;

public class Test {

    public static void main(String[] args) {
        System.out.println("Testing c");
        ReadersDaoImpl readersDao = new ReadersDaoImpl(new JPAUtil());
        ReadArticleDaoImpl readArticleDao = new ReadArticleDaoImpl(new JPAUtil());

//            System.out.println(dao.getAll());
//            System.out.println(dao.getAll(new ArticleType(1, "test")));

        Article article = new Article();
        article.setId(23);

        Reader reader = new Reader();
        reader.setId(63);

        Readarticle readarticle = new Readarticle();
        readarticle.setRating(8);
        readarticle.setReaderById(reader);
        readarticle.setArticleById(article);

        System.out.println(readArticleDao.save(readarticle));
    }

}
