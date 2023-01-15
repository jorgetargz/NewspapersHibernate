package domain.services.impl;

import dao.ArticlesDao;
import domain.modelo.Article;
import domain.services.ServicesArticles;
import io.vavr.control.Either;
import jakarta.inject.Inject;

import java.util.List;

public class ServicesArticlesImpl implements ServicesArticles {

    private final ArticlesDao articlesDao;

    @Inject
    public ServicesArticlesImpl(ArticlesDao articlesDao) {
        this.articlesDao = articlesDao;
    }

    @Override
    public Either<String, List<Article>> scGetAll() {
        return articlesDao.getAll();
    }

    @Override
    public Either<String, Article> scSave(Article article) {
        return articlesDao.save(article);
    }

    @Override
    public Either<String, Article> scUpdate(Article article) {
        return articlesDao.update(article);
    }

    @Override
    public Either<String, Boolean> scDelete(Article article) {
        return articlesDao.delete(article);
    }
}
