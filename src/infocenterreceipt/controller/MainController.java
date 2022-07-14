/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package infocenterreceipt.controller;

import infocenterreceipt.dao.LoginDAO;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author ndhara
 */
public class MainController extends Application implements Initializable {

    @FXML
    Button loginMenu;
    @FXML
    Button newBillMenu;
    
    @FXML
    Button newEnotes;
    
    @FXML
    Button ebillReport;

    @FXML
    Button settings;
    @FXML
    Button logoutMenu;
    @FXML
    ImageView mainLogo;
    @FXML
    Label lblAddress;

    @FXML
    private void logout() {
        LoginDAO dao = new LoginDAO();
        dao.removeSession();
    }

    @FXML
    private void openEBill(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/infocenterreceipt/fxml/ElectricBill.fxml"));
        Parent root1 = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.DECORATED);
        stage.setFullScreen(false);
        stage.setTitle("Electric Bill");
        stage.setScene(new Scene(root1));
        stage.show();
    }

    @FXML
    private void openEBillReport(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/infocenterreceipt/fxml/EbillReport.fxml"));
        Parent root1 = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.DECORATED);
        stage.setFullScreen(false);
        stage.setTitle("Electric Bill Report");
        stage.setScene(new Scene(root1));
        stage.show();
    }

    @FXML
    private void openSignUp(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/infocenterreceipt/fxml/signup.fxml"));
        VBox root1 = (VBox) fxmlLoader.load();
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.DECORATED);
        stage.setFullScreen(false);
        stage.setTitle("Sign Up");
        stage.setScene(new Scene(root1));
        stage.show();
    }




    @FXML
    private void openSettings(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/infocenterreceipt/fxml/settings.fxml"));
        VBox root1 = (VBox) fxmlLoader.load();

        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.DECORATED);
        stage.setFullScreen(false);
        stage.setTitle("Settings");
        stage.setScene(new Scene(root1));
        stage.show();
    }
    @FXML
    private void openLogin(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/infocenterreceipt/fxml/Login.fxml"));
        VBox root1 = (VBox) fxmlLoader.load();
        LoginController lController = fxmlLoader.getController();
        lController.eanbleButtons(newBillMenu, ebillReport, newEnotes,settings);
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.DECORATED);
        stage.setFullScreen(false);
        stage.setTitle("Login");
        stage.setScene(new Scene(root1));
        stage.show();
    }
    
    @FXML
    private void openEnotes(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/infocenterreceipt/fxml/enotes.fxml"));
        VBox root1 = (VBox) fxmlLoader.load();
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.DECORATED);
        stage.setFullScreen(false);
        stage.setTitle("Sign Up");
        stage.setScene(new Scene(root1));
        stage.show();
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        javafx.geometry.Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
        Double width=primaryScreenBounds.getWidth();
        Double height=primaryScreenBounds.getHeight();
        mainLogo.setLayoutX(width/2);
        mainLogo.setLayoutY((height/2)-200);
        lblAddress.setLayoutX((width/2)+50);
        lblAddress.setLayoutY(height/2);
        newBillMenu.setDisable(true);
        newEnotes.setDisable(true);
        ebillReport.setDisable(true);
        settings.setDisable(true);

    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
