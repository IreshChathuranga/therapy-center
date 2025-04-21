module org.example.serenitymentalhealthcenter {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires javafx.base;
    requires lombok;
    requires java.sql;

    requires org.hibernate.orm.core;
    requires jakarta.persistence;
    requires java.naming;

    opens lk.ijse.gdse.serenitymentalhealthcenter.entity to org.hibernate.orm.core;
    opens lk.ijse.gdse.serenitymentalhealthcenter.config to jakarta.persistence;

    opens lk.ijse.gdse.serenitymentalhealthcenter.dto.tm to javafx.base;
    opens lk.ijse.gdse.serenitymentalhealthcenter.controller to javafx.fxml;
    exports lk.ijse.gdse.serenitymentalhealthcenter;
    exports lk.ijse.gdse.serenitymentalhealthcenter.controller;
}