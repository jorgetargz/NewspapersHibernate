package dao.impl;

import dao.ArticleTypesDao;
import dao.utils.JPAUtil;
import domain.modelo.ArticleType;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceException;
import lombok.extern.log4j.Log4j2;

import java.util.List;

@Log4j2
public class ArticleTypesDaoImpl  implements ArticleTypesDao {

    private final JPAUtil jpaUtil;
    private EntityManager em;

    @Inject
    public ArticleTypesDaoImpl(JPAUtil jpaUtil) {
        this.jpaUtil = jpaUtil;
    }

    @Override
    public Either<Integer, List<ArticleType>> getAll() {
        Either<Integer, List<ArticleType>> result;
        em = jpaUtil.getEntityManager();
        try {
            result = Either.right(em.createNamedQuery("HQL_GET_ALL_ARTICLETYPES", ArticleType.class)
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