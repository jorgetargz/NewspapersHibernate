package dao.impl;

import dao.ArticlesDao;
import dao.utils.JPAUtil;
import domain.modelo.Article;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

import java.util.List;

public class ArticlesDaoImpl implements ArticlesDao {

    private final JPAUtil jpaUtil;
    private EntityManager em;

    @Inject
    public ArticlesDaoImpl(JPAUtil jpaUtil) {
        this.jpaUtil = jpaUtil;
    }

    @Override
    public Either<Integer, List<Article>> getAll() {
        Either<Integer, List<Article>> result;
        em = jpaUtil.getEntityManager();
        try {
            result = Either.right(em.createNamedQuery("HQL_GET_ALL_ARTICLES", Article.class)
                    .getResultList());
        } catch (Exception e) {
            result = Either.left(-1);
        } finally {
            if (em.isOpen()) {
                em.close();
            }
        }
        return result;
    }

    @Override
    public Either<Integer, Article> save(Article article) {
        Either<Integer, Article> result;
        em = jpaUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(em.merge(article));
            em.getTransaction().commit();
            result = Either.right(article);
        } catch (Exception e) {
            result = Either.left(-1);
        } finally {
            if (em.isOpen()) {
                em.close();
            }
        }
        return result;
    }

    @Override
    public Either<Integer, Article> update(Article article) {
        Either<Integer, Article> result;
        em = jpaUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(article);
            em.getTransaction().commit();
            result = Either.right(article);
        } catch (Exception e) {
            result = Either.left(-1);
        } finally {
            if (em.isOpen()) {
                em.close();
            }
        }
        return result;
    }

    @Override
    public Either<Integer, Boolean> delete(Article article) {
        Either<Integer, Boolean> result;
        em = jpaUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.remove(em.merge(article));
            em.getTransaction().commit();
            result = Either.right(true);
        } catch (Exception e) {
            result = Either.left(-1);
        } finally {
            if (em.isOpen()) {
                em.close();
            }
        }
        return result;
    }





}
