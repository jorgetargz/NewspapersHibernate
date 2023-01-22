module NewspaperFX {

    requires javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;
    requires MaterialFX;

    requires lombok;
    requires org.apache.logging.log4j;
    requires io.vavr;

    requires java.sql;
    requires com.zaxxer.hikari;
    requires org.hibernate.orm.core;
    requires jakarta.cdi;
    requires jakarta.inject;
    requires jakarta.persistence;
    requires jakarta.transaction;
    requires jakarta.validation;

    exports gui.main;
    exports gui.main.common;
    exports gui.screens.main;
    exports gui.screens.common;
    exports gui.screens.login;
    exports gui.screens.welcome;
    exports gui.screens.newspapers_list;
    exports gui.screens.newspapers_add;
    exports gui.screens.newspapers_update;
    exports gui.screens.newspapers_delete;
    exports gui.screens.readers_list;
    exports gui.screens.readers_update;
    exports gui.screens.readers_add;
    exports gui.screens.readers_delete;
    exports gui.screens.articles_list;
    exports gui.screens.articles_add;
    exports gui.screens.articles_update;
    exports gui.screens.articles_delete;

    exports dao;
    exports common;
    exports dao.impl;
    exports domain.services;
    exports domain.services.impl;
    exports domain.modelo;

    opens domain.modelo;
    opens gui.main;
    opens gui.screens.common;
    opens gui.screens.login;
    opens gui.screens.main;
    opens gui.screens.welcome;
    opens gui.screens.newspapers_list;
    opens gui.screens.newspapers_add;
    opens gui.screens.newspapers_update;
    opens gui.screens.newspapers_delete;
    opens gui.screens.readers_list;
    opens gui.screens.readers_update;
    opens gui.screens.readers_add;
    opens gui.screens.readers_delete;
    opens gui.screens.articles_list;
    opens gui.screens.articles_add;
    opens gui.screens.articles_update;
    opens gui.screens.articles_delete;

    opens css;
    opens fxml;
    opens media;
    opens common;
    opens dao.utils;
}
