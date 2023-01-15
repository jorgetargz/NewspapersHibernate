package dao.impl;

import dao.ReadArticleDao;
import dao.utils.JPAUtil;
import domain.modelo.Readarticle;
import domain.modelo.Reader;
import io.vavr.control.Either;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceException;
import lombok.extern.log4j.Log4j2;

import java.util.List;

@Log4j2
public class ReadArticleDaoImpl implements ReadArticleDao {

    private final JPAUtil jpaUtil;
    private EntityManager em;

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
    public Readarticle save(Readarticle readarticle) {
        em = jpaUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(readarticle);
            em.getTransaction().commit();
        } catch (PersistenceException e) {
            em.getTransaction().rollback();
            log.error(e.getMessage(), e);
        } finally {
            if (em.isOpen()) {
                em.close();
            }
        }
        return readarticle;
    }

    @Override
    public Readarticle update(Readarticle readarticle) {
        em = jpaUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(readarticle);
            em.getTransaction().commit();
        } catch (PersistenceException e) {
            em.getTransaction().rollback();
            log.error(e.getMessage(), e);
        } finally {
            if (em.isOpen()) {
                em.close();
            }
        }
        return readarticle;
    }

    @Override
    public Readarticle delete(Readarticle readarticle) {
        em = jpaUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.remove(em.merge(readarticle));
            em.getTransaction().commit();
        } catch (PersistenceException e) {
            em.getTransaction().rollback();
            log.error(e.getMessage(), e);
        } finally {
            if (em.isOpen()) {
                em.close();
            }
        }
        return readarticle;
    }

    @Override
    public void deleteAll(Reader reader) {
        em = jpaUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.createNamedQuery("HQL_DELETE_READARTICLE_BY_READER")
                    .setParameter("reader", reader)
                    .executeUpdate();
            em.getTransaction().commit();
        } catch (PersistenceException e) {
            em.getTransaction().rollback();
            log.error(e.getMessage(), e);
        } finally {
            if (em.isOpen()) {
                em.close();
            }
        }
    }
}