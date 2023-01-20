package dao.impl;

import common.Constantes;
import dao.NewspapersDao;
import dao.utils.JPAUtil;
import domain.modelo.Newspaper;
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
    public Either<Integer, List<Newspaper>> getAll() {
        Either<Integer, List<Newspaper>> result;
        em = jpaUtil.getEntityManager();
        try {
            result = Either.right(em.createNamedQuery("HQL_GET_ALL_NEWSPAPERS", Newspaper.class)
                    .getResultList());

        } catch (PersistenceException e) {
            result = Either.left(Constantes.DB_ERROR_CODE);
            log.error(e.getMessage(), e);
        } finally {
            if (em.isOpen()) {
                em.close();
            }
        }
        return result;
    }

    @Override
    public Either<Integer, Newspaper> get(int id) {
        Either<Integer, Newspaper> result;
        em = jpaUtil.getEntityManager();
        try {
            result = Either.right(em.find(Newspaper.class, id));
            result.get().getArticles().size();
            if (result.get() == null) {
                result = Either.left(Constantes.DB_NOT_FOUND_CODE);
                log.error("No newspapers found with id: " + id);
            }
        } catch (PersistenceException e) {
            result = Either.left(Constantes.DB_ERROR_CODE);
            log.error(e.getMessage(), e);
        } finally {
            if (em.isOpen()) {
                em.close();
            }
        }
        return result;
    }

    @Override
    public Either<Integer, Newspaper> save(Newspaper newspaper) {
        Either<Integer, Newspaper> result;
        em = jpaUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(newspaper);
            em.getTransaction().commit();
            result = Either.right(newspaper);
        } catch (PersistenceException e) {
            em.getTransaction().rollback();
            result = Either.left(Constantes.DB_ERROR_CODE);
            log.error(e.getMessage(), e);
        } finally {
            if (em.isOpen()) {
                em.close();
            }
        }
        return result;
    }

    @Override
    public Either<Integer, Newspaper> update(Newspaper newspaper) {
        Either<Integer, Newspaper> result;
        em = jpaUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(newspaper);
            em.getTransaction().commit();
            result = Either.right(newspaper);
        } catch (PersistenceException e) {
            em.getTransaction().rollback();
            result = Either.left(Constantes.DB_ERROR_CODE);
            log.error(e.getMessage(), e);
        } finally {
            if (em.isOpen()) {
                em.close();
            }
        }
        return result;
    }

    @Override
    public Either<Integer, Boolean> delete(Newspaper newspaper) {
        Either<Integer, Boolean> result;
        em = jpaUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.remove(em.merge(newspaper));
            em.getTransaction().commit();
            result = Either.right(true);
        } catch (PersistenceException e) {
            em.getTransaction().rollback();
            if (e.getCause() instanceof ConstraintViolationException) {
                result = Either.left(Constantes.DB_CONSTRAINT_VIOLATION_CODE);
            } else {
                result = Either.left(Constantes.DB_ERROR_CODE);
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
