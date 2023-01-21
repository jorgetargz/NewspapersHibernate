package dao.impl;

import common.Constantes;
import dao.LoginDao;
import dao.utils.JPAUtil;
import domain.modelo.Login;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceException;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class LoginDaoImpl implements LoginDao {

    private final JPAUtil jpaUtil;
    private EntityManager em;

    @Inject
    public LoginDaoImpl(JPAUtil jpaUtil) {
        this.jpaUtil = jpaUtil;
    }

    @Override
    public Either<Integer, Login> get(String username) {
        Either<Integer, Login> result;
        em = jpaUtil.getEntityManager();
        try {
            result = Either.right(em.find(Login.class, username));
            if (result.get() == null) {
                result = Either.left(Constantes.DB_NOT_FOUND_CODE);
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
    public Either<Integer, Login> save(Login login) {
        Either<Integer, Login> result;
        em = jpaUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(login);
            em.getTransaction().commit();
            result = Either.right(login);
        } catch (PersistenceException e) {
            result = Either.left(Constantes.DB_ERROR_CODE);
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
    public Either<Integer, Login> update(Login login) {
        Either<Integer, Login> result;
        em = jpaUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(login);
            em.getTransaction().commit();
            result = Either.right(login);
        } catch (PersistenceException e) {
            result = Either.left(Constantes.DB_ERROR_CODE);
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
    public Either<Integer, Boolean> delete(Login login) {
        Either<Integer, Boolean> result;
        em = jpaUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.createNamedQuery("HQL_DELETE_READARTICLE_BY_READER")
                    .setParameter("reader", login.getReader())
                    .executeUpdate();
            em.createNamedQuery("HQL_DELETE_ALL_SUBSCRIBES_BY_READER")
                    .setParameter("reader", login.getReader())
                    .executeUpdate();
            em.remove(em.merge(login));
            em.getTransaction().commit();
            result = Either.right(true);
        } catch (PersistenceException e) {
            result = Either.left(Constantes.DB_ERROR_CODE);
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