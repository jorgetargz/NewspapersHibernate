package dao.impl;

import dao.LoginDao;
import domain.modelo.Login;
import dao.utils.JPAUtil;
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
    public Login get(String username) {
        Login login = null;
        em = jpaUtil.getEntityManager();
        try {
            login = em.find(Login.class, username);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
        } finally {
            if (em.isOpen()) {
                em.close();
            }
        }
        return login;
    }

    @Override
    public Login save(Login login) {
        em = jpaUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(login);
            em.getTransaction().commit();
        } catch (PersistenceException e) {
            login = null;
            em.getTransaction().rollback();
            log.error(e.getMessage(), e);
        } finally {
            if (em.isOpen()) {
                em.close();
            }
        }
        return login;
    }

    @Override
    public void delete(Login login) {
        em = jpaUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.remove(em.merge(login));
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