package dao.impl;

import common.Constantes;
import dao.ReadArticleDao;
import dao.utils.JPAUtil;
import domain.modelo.Readarticle;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceException;
import jakarta.persistence.Tuple;
import lombok.extern.log4j.Log4j2;
import org.hibernate.exception.ConstraintViolationException;

import java.util.Map;
import java.util.stream.Collectors;

@Log4j2
public class ReadArticleDaoImpl implements ReadArticleDao {

    private final JPAUtil jpaUtil;
    private EntityManager em;

    @Inject
    public ReadArticleDaoImpl(JPAUtil jpaUtil) {
        this.jpaUtil = jpaUtil;
    }

    @Override
    public Either<Integer, Readarticle> save(Readarticle readarticle) {
        Either<Integer, Readarticle> result;
        em = jpaUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(readarticle);
            em.getTransaction().commit();
            result = Either.right(readarticle);
        } catch (PersistenceException e) {
            if (e.getCause() instanceof ConstraintViolationException) {
                result = Either.left(Constantes.DB_CONSTRAINT_VIOLATION_CODE);
            } else {
                result = Either.left(Constantes.DB_ERROR_CODE);
            }
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
    public Either<Integer, Readarticle> update(Readarticle readarticle) {
        Either<Integer, Readarticle> result;
        em = jpaUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            if (em.createNamedQuery("HQL_UPDATE_READARTICLE_BY_ARTICLE_ID_AND_READER_ID")
                    .setParameter("rating", readarticle.getRating())
                    .setParameter("article", readarticle.getArticleById())
                    .setParameter("reader", readarticle.getReaderById())
                    .executeUpdate() == 0) {
                result = Either.left(Constantes.DB_NOT_FOUND_CODE);
            } else {
                result = Either.right(readarticle);
            }
            em.getTransaction().commit();
        } catch (PersistenceException e) {
            em.getTransaction().rollback();
            log.error(e.getMessage(), e);
            result = Either.left(Constantes.DB_ERROR_CODE);
        } finally {
            if (em.isOpen()) {
                em.close();
            }
        }
        return result;
    }

    @Override
    public Either<Integer, Map<Double, String>> getAvgRating(int idReader) {
        Either<Integer, Map<Double, String>> result;
        em = jpaUtil.getEntityManager();
        try {
            result = Either.right(em.createNamedQuery("HQL_GET_AVERAGE_RATING_BY_READER", Tuple.class)
                    .setParameter("idReader", idReader)
                    .getResultStream()
                    .collect(
                            Collectors.toMap(
                                    tuple -> ((Number) tuple.get(0)).doubleValue(),
                                    tuple -> ((String) tuple.get(1))
                            )
                    ));
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