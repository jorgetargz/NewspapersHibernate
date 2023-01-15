package dao.impl;

import dao.ReadersDao;
import dao.utils.JPAUtil;
import domain.modelo.ArticleType;
import domain.modelo.Reader;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceException;
import lombok.extern.log4j.Log4j2;

import java.util.List;

@Log4j2
public class ReadersDaoImpl implements ReadersDao {

    private final JPAUtil jpaUtil;
    private EntityManager em;

    @Inject
    public ReadersDaoImpl(JPAUtil jpaUtil) {
        this.jpaUtil = jpaUtil;
    }

    @Override
    public List<Reader> getAll() {
        List<Reader> list = null;
        em = jpaUtil.getEntityManager();
        try {
            list = em.createNamedQuery("HQL_GET_ALL_READERS", Reader.class)
                    .getResultList();

        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
        } finally {
            if (em.isOpen()) {
                em.close();
            }
        }
        return list;
    }

    @Override
    public List<Reader> getAll(ArticleType articleType) {
        List<Reader> list = null;
        em = jpaUtil.getEntityManager();
        try {
            list = em.createNamedQuery("HQL_GET_ALL_READERS_BY_ARTICLE_TYPE", Reader.class)
                    .setParameter("type", articleType)
                    .getResultList();

        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
        } finally {
            if (em.isOpen()) {
                em.close();
            }
        }
        return list;
    }

    @Override
    public Reader get(int id) {
        Reader reader = null;
        em = jpaUtil.getEntityManager();
        try {
            reader = em.find(Reader.class, id);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
        } finally {
            if (em.isOpen()) {
                em.close();
            }
        }
        return reader;
    }

    @Override
    public Reader save(Reader reader) {
        em = jpaUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(reader);
            em.getTransaction().commit();
        } catch (PersistenceException e) {
            em.getTransaction().rollback();
            log.error(e.getMessage(), e);
        } finally {
            if (em.isOpen()) {
                em.close();
            }
        }
        return reader;
    }

    @Override
    public Reader update(Reader reader) {
        em = jpaUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(reader);
            em.getTransaction().commit();
        } catch (PersistenceException e) {
            em.getTransaction().rollback();
            log.error(e.getMessage(), e);
        } finally {
            if (em.isOpen()) {
                em.close();
            }
        }
        return reader;
    }

    @Override
    public void delete(Reader reader) {
        em = jpaUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.remove(em.merge(reader));
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
