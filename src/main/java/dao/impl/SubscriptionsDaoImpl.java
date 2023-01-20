package dao.impl;

import dao.SubscriptionsDao;
import dao.utils.JPAUtil;
import domain.modelo.Reader;
import domain.modelo.Subscribe;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceException;
import lombok.extern.log4j.Log4j2;

import java.util.List;

@Log4j2
public class SubscriptionsDaoImpl implements SubscriptionsDao {

    private final JPAUtil jpaUtil;
    private EntityManager em;

    @Inject
    public SubscriptionsDaoImpl(JPAUtil jpaUtil) {
        this.jpaUtil = jpaUtil;
    }

    @Override
    public Either<Integer, List<Subscribe>> getAll(Reader reader) {
        Either<Integer, List<Subscribe>> result;
        em = jpaUtil.getEntityManager();
        try {
            result = Either.right(em.createNamedQuery("HQL_GET_ALL_ACTIVE_SUBSCRIBES_BY_READER", Subscribe.class)
                    .setParameter("reader", reader)
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
}
