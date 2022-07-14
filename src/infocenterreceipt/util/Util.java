/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package infocenterreceipt.util;

import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

/**
 *
 * @author ndhara
 */
public class Util {
    
    public static boolean isLogedIn(){
        boolean flag=false;
        
        return flag;
    }
    public static void showErrorMessage(String header, String message){
        Alert dialog = new Alert(Alert.AlertType.ERROR);
            dialog.setHeaderText(header);
            dialog.setContentText(message);
            dialog.setResizable(true);
            dialog.getDialogPane().setPrefSize(400, 200);
            dialog.showAndWait();
    }
    
    public static boolean isblank(TextField text){
        if(text.getText().equals(""))
        {
            return true;
        }
        return false;
    }
}
