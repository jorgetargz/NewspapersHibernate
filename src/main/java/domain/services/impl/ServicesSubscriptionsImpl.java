package domain.services.impl;

import dao.SubscriptionsDao;
import domain.modelo.Reader;
import domain.modelo.Subscribe;
import domain.services.ServicesSubscriptions;
import io.vavr.control.Either;
import jakarta.inject.Inject;

import java.util.List;

public class ServicesSubscriptionsImpl implements ServicesSubscriptions {

    private final SubscriptionsDao subscriptionsDao;

    @Inject
    public ServicesSubscriptionsImpl(SubscriptionsDao subscriptionsDao) {
        this.subscriptionsDao = subscriptionsDao;
    }

    @Override
    public Either<String, List<Subscribe>> scGetAllActiveByReader(Reader reader) {
        return subscriptionsDao.getAll(reader);
    }
}

