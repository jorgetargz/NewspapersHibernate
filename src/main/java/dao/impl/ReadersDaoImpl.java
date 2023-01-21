package dao.impl;

import common.Constantes;
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
    public Either<Integer, List<Reader>> getAll(ArticleType articleType) {
        Either<Integer, List<Reader>> result;
        em = jpaUtil.getEntityManager();
        try {
            result = Either.right(em.createNamedQuery("HQL_GET_ALL_READERS_BY_ARTICLE_TYPE", Reader.class)
                    .setParameter("type", articleType)
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
    public Either<Integer, List<Reader>> getAll(Newspaper newspaper) {
        Either<Integer, List<Reader>> result;
        em = jpaUtil.getEntityManager();
        try {
            result = Either.right(em.createNamedQuery("HQL_GET_ALL_READERS_BY_NEWSPAPER_SUBSCRIPTION_ACTIVE", Reader.class)
                    .setParameter("idNewspaper", newspaper.getId())
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

}
