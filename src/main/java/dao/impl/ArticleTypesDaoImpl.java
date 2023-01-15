package dao.impl;

import dao.ArticleTypesDao;
import dao.utils.JPAUtil;
import domain.modelo.ArticleType;
import domain.modelo.Readarticle;
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
    public Either<String, List<ArticleType>> getAll() {
        Either<String, List<ArticleType>> result;
        em = jpaUtil.getEntityManager();
        try {
            result = Either.right(em.createNamedQuery("HQL_GET_ALL_ARTICLETYPES", ArticleType.class)
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
}