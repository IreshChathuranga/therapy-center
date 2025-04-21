package lk.ijse.gdse.serenitymentalhealthcenter;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import lk.ijse.gdse.serenitymentalhealthcenter.config.FactoryConfiguration;
import lk.ijse.gdse.serenitymentalhealthcenter.entity.Patient;
import lk.ijse.gdse.serenitymentalhealthcenter.entity.Therapist;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.io.IOException;

public class AppInitializer extends Application {
    public static void main(String[] args) throws IOException {
        Session session = FactoryConfiguration.getInstance().getSession();
        launch();
    }

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/Login.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Therapy Center");
        stage.setScene(scene);

        Image image = new Image(getClass().getResourceAsStream("/image/istockphoto-498715206-612x612.jpg"));
        stage.getIcons().add(image);

        stage.show();
    }
}