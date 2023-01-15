package dao.impl;

import dao.ReadArticleDao;
import dao.utils.JPAUtil;
import domain.modelo.Readarticle;
import domain.modelo.Reader;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceException;
import lombok.extern.log4j.Log4j2;
import org.hibernate.exception.ConstraintViolationException;

import java.util.List;

@Log4j2
public class ReadArticleDaoImpl implements ReadArticleDao {

    private final JPAUtil jpaUtil;
    private EntityManager em;

    @Inject
    public ReadArticleDaoImpl(JPAUtil jpaUtil) {
        this.jpaUtil = jpaUtil;
    }

    @Override
    public Either<String, List<Readarticle>> getAll() {
        Either<String, List<Readarticle>> result = null;
        em = jpaUtil.getEntityManager();
        try {
            result = Either.right(em.createNamedQuery("HQL_GET_ALL_READARTICLES", Readarticle.class)
                    .getResultList());

        } catch (PersistenceException e) {
            result = Either.left(e.getMessage());
            log.error(e.getMessage(), e);
        } finally {
            if (em.isOpen()) {
                em.close();
            }
        }
        return result;
    }

    @Override
    public Either<String, List<Readarticle>> getAll(Reader reader) {
        Either<String, List<Readarticle>> result = null;
        em = jpaUtil.getEntityManager();
        try {
            result = Either.right(em.createNamedQuery("HQL_GET_ALL_READARTICLES_BY_READER", Readarticle.class)
                    .setParameter("reader", reader)
                    .getResultList());

        } catch (PersistenceException e) {
            result = Either.left(e.getMessage());
            log.error(e.getMessage(), e);
        } finally {
            if (em.isOpen()) {
                em.close();
            }
        }
        return result;
    }

    @Override
    public Either<String, Readarticle> save(Readarticle readarticle) {
        Either<String, Readarticle> result = null;
        em = jpaUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(readarticle);
            em.getTransaction().commit();
            result = Either.right(readarticle);
        } catch (PersistenceException e) {
            em.getTransaction().rollback();
            log.error(e.getMessage(), e);
            if (e.getCause() instanceof ConstraintViolationException) {
                result = Either.left("Article already scored");
            } else {
                result = Either.left(e.getMessage());
            }
        } finally {
            if (em.isOpen()) {
                em.close();
            }
        }
        return result;
    }

    @Override
    public Either<String, Readarticle> update(Readarticle readarticle) {
        Either<String, Readarticle> result = null;
        em = jpaUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.createNamedQuery("HQL_UPDATE_READARTICLE_BY_ARTICLE_ID_AND_READER_ID")
                    .setParameter("rating", readarticle.getRating())
                    .setParameter("article", readarticle.getArticleById())
                    .setParameter("reader", readarticle.getReaderById())
                    .executeUpdate();
            em.getTransaction().commit();
            result = Either.right(readarticle);
        } catch (PersistenceException e) {
            em.getTransaction().rollback();
            log.error(e.getMessage(), e);
            result = Either.left(e.getMessage());
        } finally {
            if (em.isOpen()) {
                em.close();
            }
        }
        return result;
    }

    @Override
    public Either<String, Boolean> delete(Readarticle readarticle) {
        Either<String, Boolean> result = null;
        em = jpaUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.remove(em.merge(readarticle));
            em.getTransaction().commit();
            result = Either.right(true);
        } catch (PersistenceException e) {
            em.getTransaction().rollback();
            log.error(e.getMessage(), e);
            result = Either.left(e.getMessage());
        } finally {
            if (em.isOpen()) {
                em.close();
            }
        }
        return result;
    }

    @Override
    public Either<String, Boolean> deleteAll(Reader reader) {
        Either<String, Boolean> result = null;
        em = jpaUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.createNamedQuery("HQL_DELETE_READARTICLE_BY_READER")
                    .setParameter("reader", reader)
                    .executeUpdate();
            em.getTransaction().commit();
            result = Either.right(true);
        } catch (PersistenceException e) {
            em.getTransaction().rollback();
            log.error(e.getMessage(), e);
            result = Either.left(e.getMessage());
        } finally {
            if (em.isOpen()) {
                em.close();
            }
        }
        return result;
    }
}