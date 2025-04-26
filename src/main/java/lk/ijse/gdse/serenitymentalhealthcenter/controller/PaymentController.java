package lk.ijse.gdse.serenitymentalhealthcenter.controller;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;

import java.net.URL;
import java.util.ResourceBundle;

public class PaymentController implements Initializable {
    public TextField txtAmount;
    public TextField txtDate;
    public Button btnUpdate;
    public Button btnDelete;
    public TableView tblPayment;
    public TableColumn paymentId;
    public TableColumn paymentAmount;
    public TableColumn paymentDate;
    public TableColumn sessionId;
    public TextField txtSessionId;
    public Rectangle Theraphist;
    public Label lblPaymentId;
    public Button btnRefresh;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        
    }

    public void updateOnAction(ActionEvent actionEvent) {
    }

    public void deleteOnAction(ActionEvent actionEvent) {
    }

    public void onClickTable(MouseEvent mouseEvent) {
    }

    public void refreshOnAction(ActionEvent actionEvent) {
    }
}
