package lk.ijse.gdse.serenitymentalhealthcenter.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;
import lk.ijse.gdse.serenitymentalhealthcenter.bo.BOFactory;
import lk.ijse.gdse.serenitymentalhealthcenter.bo.custom.LoginBO;
import lk.ijse.gdse.serenitymentalhealthcenter.bo.custom.SignInBO;
import lk.ijse.gdse.serenitymentalhealthcenter.dto.UserDto;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    private static final double DEFAULT_WIDTH = 1380;
    private static final double DEFAULT_HEIGHT = 775;
    public AnchorPane mainAnchor;
    public ImageView titelImage;
    public ImageView secondImage;
    public TextField txtUserName;
    public PasswordField txtPassword;
    public Button btnLogin;
    public ComboBox<String> cmbRole;
    public Text lblSignIn;

    LoginBO loginBO = (LoginBO) BOFactory.getInstance().getBO(BOFactory.BOType.LOGIN);
    SignInBO signInBO = (SignInBO) BOFactory.getInstance().getBO(BOFactory.BOType.SIGNIN);
    public void loginOnAction(ActionEvent actionEvent) {
        try {
            checkCredential(new ArrayList<>());
        } catch (SQLException | IOException | ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, "OOPS! something went wrong").show();
        }
    }

    public void onClickLable(MouseEvent mouseEvent) throws IOException {
        navigateToSingIn("/view/SignIn.fxml");
    }

    private void checkCredential(List<UserDto> userDtos) throws SQLException, IOException, ClassNotFoundException {
        try {
            String userId = txtUserName.getText().trim();
            String password = txtPassword.getText().trim();
            String selectedRole = cmbRole.getValue();

            userDtos = loginBO.loadUserData();

            if (userId.isEmpty() || password.isEmpty() || selectedRole == null) {
                new Alert(Alert.AlertType.INFORMATION, "Please fill all fields including role").show();
                return;
            }

            boolean isUserFound = false;

            for (UserDto userDto : userDtos) {
                if (userDto.getUserName().equals(userId)) {
                    isUserFound = true;

                    if (!userDto.getRole().equalsIgnoreCase(selectedRole)) {
                        new Alert(Alert.AlertType.ERROR, "Incorrect role selected!").show();
                        return;
                    }

                    if (userDto.getPassword().equals(password)) {
                        navigateToTheDashboard(userDto.getRole());
                        return;
                    } else {
                        new Alert(Alert.AlertType.ERROR, "Password is incorrect!").show();
                        return;
                    }
                }
            }

            if (!isUserFound) {
                new Alert(Alert.AlertType.ERROR, "Username not found!").show();
            }

        } finally {
            txtUserName.clear();
            txtPassword.clear();
        }
    }
    private void navigateToTheDashboard(String role) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/HomePage.fxml"));
        Parent root = loader.load();

        HomePageController homePageController = loader.getController();
        homePageController.setRole(role);

        Stage stage = (Stage) mainAnchor.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);

        if ("admin".equalsIgnoreCase(role) || "receptionist".equalsIgnoreCase(role)) {
            stage.setWidth(DEFAULT_WIDTH);
            stage.setHeight(DEFAULT_HEIGHT);

            Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
            stage.setX((screenBounds.getWidth() - DEFAULT_WIDTH) / 2);
            stage.setY((screenBounds.getHeight() - DEFAULT_HEIGHT) / 2);
        }

        stage.show();
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            loadUserRole();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void navigateToSingIn(String fxlmpath) throws IOException {
        mainAnchor.getChildren().clear();
        AnchorPane load = FXMLLoader.load(getClass().getResource(fxlmpath));
        mainAnchor.getChildren().add(load);
    }
    public void navigateTo(String fxlmpath) {
        try {
            mainAnchor.getChildren().clear();
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxlmpath));
            Parent load = loader.load();

            Stage currentStage = (Stage) mainAnchor.getScene().getWindow();

            currentStage.setWidth(DEFAULT_WIDTH);
            currentStage.setHeight(DEFAULT_HEIGHT);

            Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
            currentStage.setX((screenBounds.getWidth() - DEFAULT_WIDTH) / 2);
            currentStage.setY((screenBounds.getHeight() - DEFAULT_HEIGHT) / 2);

            Scene newScene = new Scene(load, DEFAULT_WIDTH, DEFAULT_HEIGHT);
            currentStage.setScene(newScene);
            currentStage.setResizable(false);
            currentStage.show();
        }catch (IOException e){
            new Alert(Alert.AlertType.ERROR,"Fail UI").show();
        }
    }
    private void loadUserRole() throws SQLException, ClassNotFoundException {
        List<String> userRole = signInBO.getAllRoles();
        ObservableList<String> observableList = FXCollections.observableArrayList();
        observableList.addAll(userRole);
        cmbRole.setItems(observableList);
    }
}
