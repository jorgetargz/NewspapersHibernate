package domain.services.impl;

import dao.ArticleTypesDao;
import domain.modelo.ArticleType;
import domain.services.ServicesArticleTypes;
import io.vavr.control.Either;
import jakarta.inject.Inject;

import java.util.List;

public class ServicesArticleTypesImpl implements ServicesArticleTypes {

    private final ArticleTypesDao daoArticleTypes;

    @Inject
    public ServicesArticleTypesImpl(ArticleTypesDao daoArticleTypes) {
        this.daoArticleTypes = daoArticleTypes;
    }

    @Override
    public Either<Integer, List<ArticleType>> scGetAll() {
        return daoArticleTypes.getAll();
    }
}
