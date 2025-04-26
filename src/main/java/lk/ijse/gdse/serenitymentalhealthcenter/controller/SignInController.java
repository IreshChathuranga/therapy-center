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
import java.util.regex.Pattern;

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
    private void addError(TextField textField) {
        textField.setStyle("-fx-border-color: red;  -fx-border-radius: 40; -fx-background-color: #74ffd5; -fx-background-radius: 40 ");
    }
    private void removeError(TextField textField) {
        textField.setStyle("-fx-border-color:  #74ffd5;  -fx-border-radius: 40;-fx-background-color: #74ffd5; -fx-background-radius: 40");
    }
    public void adminOnKeyReleased(KeyEvent keyEvent) {
        Pattern userPattern = Pattern.compile("^[A-Za-z ]+$");
        if (!userPattern.matcher(txtName.getText()).matches()) {
            addError(txtName);
            btnSignup.setDisable(true);
        }else{
            removeError(txtName);
            btnSignup.setDisable(false);
        }
    }

    public void numberOnKeyReleased(KeyEvent keyEvent) {
        Pattern userPattern = Pattern.compile("^\\d{10}$");
        if (!userPattern.matcher(txtNumber.getText()).matches()) {
            addError(txtNumber);
            btnSignup.setDisable(true);
        }else{
            removeError(txtNumber);
            btnSignup.setDisable(false);
        }
    }

    public void addressOnKeyReleased(KeyEvent keyEvent) {
        Pattern userPattern = Pattern.compile("^[A-Za-z0-9\\s,.\\-\\/]{5,100}$");
        if (!userPattern.matcher(txtAddress.getText()).matches()) {
            addError(txtAddress);
            btnSignup.setDisable(true);
        }else{
            removeError(txtAddress);
            btnSignup.setDisable(false);
        }
    }

    public void uerNameOnKeyReleased(KeyEvent keyEvent) {
        Pattern userPattern = Pattern.compile("^[A-Za-z][A-Za-z0-9._]{2,15}(?<![_.])$");
        if (!userPattern.matcher(txtUserName.getText()).matches()) {
            addError(txtUserName);
            btnSignup.setDisable(true);
        }else{
            removeError(txtUserName);
            btnSignup.setDisable(false);
        }
    }

    public void passwordOnKeyReleased(KeyEvent keyEvent) {
        Pattern userPattern = Pattern.compile("^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@#$%^&+=!])[A-Za-z\\d@#$%^&+=!]{8,20}$");
        if (!userPattern.matcher(txtPassword.getText()).matches()) {
            addError(txtPassword);
            btnSignup.setDisable(true);
        }else{
            removeError(txtPassword);
            btnSignup.setDisable(false);
        }
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
