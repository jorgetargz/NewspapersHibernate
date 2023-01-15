package dao.impl;

import dao.NewspapersDao;
import domain.modelo.Newspaper;
import dao.utils.JPAUtil;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceException;
import lombok.extern.log4j.Log4j2;
import org.hibernate.exception.ConstraintViolationException;

import java.util.List;

@Log4j2
public class NewspaperDaoImpl implements NewspapersDao {

    private final JPAUtil jpaUtil;
    private EntityManager em;

    @Inject
    public NewspaperDaoImpl(JPAUtil jpaUtil) {
        this.jpaUtil = jpaUtil;
    }

    @Override
    public Either<String, List<Newspaper>> getAll() {
        Either<String, List<Newspaper>> result = null;
        em = jpaUtil.getEntityManager();
        try {
            result = Either.right(em.createNamedQuery("HQL_GET_ALL_NEWSPAPERS", Newspaper.class)
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
    public Either<String, Newspaper> get(int id) {
        Either<String, Newspaper> result = null;
        em = jpaUtil.getEntityManager();
        try {
            result = Either.right(em.find(Newspaper.class, id));
            result.get().getArticles().size();
            if (result.get() == null) {
                result = Either.left("No newspaper found with id: " + id);
                log.error("No newspapers found with id: " + id);
            }
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
    public Either<String, Newspaper> save(Newspaper newspaper) {
        Either<String, Newspaper> result = null;
        em = jpaUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(newspaper);
            em.getTransaction().commit();
            result = Either.right(newspaper);
        } catch (PersistenceException e) {
            em.getTransaction().rollback();
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
    public Either<String, Newspaper> update(Newspaper newspaper) {
        Either<String, Newspaper> result = null;
        em = jpaUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(newspaper);
            em.getTransaction().commit();
            result = Either.right(newspaper);
        } catch (PersistenceException e) {
            em.getTransaction().rollback();
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
    public Either<String, Newspaper> delete(Newspaper newspaper) {
        Either<String, Newspaper> result = null;
        em = jpaUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.remove(em.merge(newspaper));
            em.getTransaction().commit();
            result = Either.right(newspaper);
        } catch (PersistenceException e) {
            em.getTransaction().rollback();
            if (e.getCause() instanceof ConstraintViolationException) {
                result = Either.left("Newspaper has relations, can't be deleted");
            } else {
                result = Either.left(e.getMessage());
            }
            log.error(e.getMessage(), e);
        } finally {
            if (em.isOpen()) {
                em.close();
            }
        }
        return result;
    }
}
