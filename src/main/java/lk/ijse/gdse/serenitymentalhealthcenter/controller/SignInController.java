package lk.ijse.gdse.serenitymentalhealthcenter.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import lk.ijse.gdse.serenitymentalhealthcenter.bo.BOFactory;
import lk.ijse.gdse.serenitymentalhealthcenter.bo.custom.PatientBO;
import lk.ijse.gdse.serenitymentalhealthcenter.bo.custom.SignInBO;
import lk.ijse.gdse.serenitymentalhealthcenter.dto.UserDto;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class SignInController implements Initializable {
    public AnchorPane mainAnchor;
    public TextField txtName;
    public TextField txtNumber;
    public TextField txtAddress;
    public TextField txtUserName;
    public PasswordField txtPassword;
    public Button btnSignup;
    public Button btnLogin;
    public TextField txtRole;
    public Text lblLogIn;

    SignInBO signInBO = (SignInBO) BOFactory.getInstance().getBO(BOFactory.BOType.SIGNIN);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
    public void adminOnKeyReleased(KeyEvent keyEvent) {
    }

    public void numberOnKeyReleased(KeyEvent keyEvent) {
    }

    public void addressOnKeyReleased(KeyEvent keyEvent) {
    }

    public void uerNameOnKeyReleased(KeyEvent keyEvent) {
    }

    public void passwordOnKeyReleased(KeyEvent keyEvent) {
    }

    public void signinOnAction(ActionEvent actionEvent) throws IOException, SQLException, ClassNotFoundException {
        String name=txtName.getText();
        String role=txtRole.getText();
        String number= txtNumber.getText();
        String address=txtAddress.getText();
        String userName=txtUserName.getText();
        String userPassword=txtPassword.getText();

        UserDto userDto= new UserDto(
                name,
                role,
                number,
                address,
                userName,
                userPassword
        );

        boolean isSaved=signInBO.saveUser(userDto);
        if(isSaved){
            txtName.setText("");
            txtRole.setText("");
            txtNumber.setText("");
            txtAddress.setText("");
            txtUserName.setText("");
            txtPassword.setText("");
        }
        navigateTo("/view/LogIn.fxml");
    }

    public void roalOnKeyReleased(KeyEvent keyEvent) {
    }

    public void onClickLog(MouseEvent mouseEvent) throws IOException {
        navigateTo("/view/LogIn.fxml");
    }
    public void navigateTo(String fxmlpath) throws IOException {
        mainAnchor.getChildren().clear();
        AnchorPane load = FXMLLoader.load(getClass().getResource(fxmlpath));
        mainAnchor.getChildren().add(load);
    }
}
