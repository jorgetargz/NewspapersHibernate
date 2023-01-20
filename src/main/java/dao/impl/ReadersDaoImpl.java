package dao.impl;

import dao.ReadersDao;
import dao.utils.JPAUtil;
import domain.modelo.ArticleType;
import domain.modelo.Newspaper;
import domain.modelo.Reader;
import io.vavr.control.Either;
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
    public Either<Integer, List<Reader>> getAll() {
        Either<Integer, List<Reader>> result;
        em = jpaUtil.getEntityManager();
        try {
            result = Either.right(em.createNamedQuery("HQL_GET_ALL_READERS", Reader.class)
                    .getResultList());

        } catch (PersistenceException e) {
            result = Either.left(-1);
            log.error(e.getMessage(), e);
        } finally {
            if (em.isOpen()) {
                em.close();
            }
        }
        return result;
    }

    @Override
    public Either<Integer, List<Reader>> getAll(ArticleType articleType) {
        Either<Integer, List<Reader>> result;
        em = jpaUtil.getEntityManager();
        try {
            result = Either.right(em.createNamedQuery("HQL_GET_ALL_READERS_BY_ARTICLE_TYPE", Reader.class)
                    .setParameter("type", articleType)
                    .getResultList());

        } catch (PersistenceException e) {
            result = Either.left(-1);
            log.error(e.getMessage(), e);
        } finally {
            if (em.isOpen()) {
                em.close();
            }
        }
        return result;
    }

    @Override
    public Either<Integer, List<Reader>> getAll(Newspaper newspaper) {
        Either<Integer, List<Reader>> result;
        em = jpaUtil.getEntityManager();
        try {
            result = Either.right(em.createNamedQuery("HQL_GET_ALL_READERS_BY_NEWSPAPER", Reader.class)
                    .setParameter("idNewspaper", newspaper.getId())
                    .getResultList());

        } catch (PersistenceException e) {
            result = Either.left(-1);
            log.error(e.getMessage(), e);
        } finally {
            if (em.isOpen()) {
                em.close();
            }
        }
        return result;
    }

    @Override
    public Either<Integer, Reader> get(int id) {
        Either<Integer, Reader> result;
        em = jpaUtil.getEntityManager();
        try {
            result = Either.right(em.find(Reader.class, id));
        } catch (PersistenceException e) {
            result = Either.left(-1);
            log.error(e.getMessage(), e);
        } finally {
            if (em.isOpen()) {
                em.close();
            }
        }
        return result;
    }

    @Override
    public Either<Integer, Reader> save(Reader reader) {
        Either<Integer, Reader> result;
        em = jpaUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(reader);
            em.getTransaction().commit();
            result = Either.right(reader);
        } catch (PersistenceException e) {
            result = Either.left(-1);
            em.getTransaction().rollback();
            log.error(e.getMessage(), e);
        } finally {
            if (em.isOpen()) {
                em.close();
            }
        }
        return result;
    }

    @Override
    public Either<Integer, Reader> update(Reader reader) {
        Either<Integer, Reader> result;
        em = jpaUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(reader);
            em.getTransaction().commit();
            result = Either.right(reader);
        } catch (PersistenceException e) {
            result = Either.left(-1);
            em.getTransaction().rollback();
            log.error(e.getMessage(), e);
        } finally {
            if (em.isOpen()) {
                em.close();
            }
        }
        return result;
    }

    @Override
    public Either<Integer, Boolean> delete(Reader reader) {
        Either<Integer, Boolean> result;
        em = jpaUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.remove(em.merge(reader));
            em.getTransaction().commit();
            result = Either.right(true);
        } catch (PersistenceException e) {
            result = Either.left(-1);
            em.getTransaction().rollback();
            log.error(e.getMessage(), e);
        } finally {
            if (em.isOpen()) {
                em.close();
            }
        }
        return result;
    }
}
