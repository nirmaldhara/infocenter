/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package infocenterreceipt.controller;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import infocenterreceipt.dao.SettingsDAO;
import infocenterreceipt.enums.Message;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 *
 * @author ndhara
 */
public class SettingsController implements Initializable {

    String serviceChargeAmount=null;
    Map<String,String> billStructure= new HashMap<>();
    @FXML
    private ComboBox<String> delimeter;
    @FXML
    private TextField serviceCharge;
    @FXML
    private Label settingsSaveMessage;

    ////////////////

    @FXML
    private TextField transactionId;
    @FXML
    private TextField date;
    @FXML
    private TextField paymentGateway;
    @FXML
    private TextField billingOffice;
    @FXML
    private TextField consumerId;
    @FXML
    private TextField name;
    @FXML
    private TextField invoiceNo;
    @FXML
    private TextField billPaidFor;
    @FXML
    private TextField paymentMode;
    @FXML
    private TextField received;

    @FXML
    private void saveSettings(ActionEvent event) throws IOException {

    	SettingsDAO.saveSettings("service_charge",serviceCharge.getText());
        settingsSaveMessage.setText(Message.valueOf("SETTING_SAVE_SUCCESS").get());


        billStructure.put("transactionId",transactionId.getText());
        billStructure.put("date",date.getText());
        billStructure.put("paymentGateway",paymentGateway.getText());
        billStructure.put("billingOffice",billingOffice.getText());
        billStructure.put("consumerId",consumerId.getText());
        billStructure.put("name",name.getText());
        billStructure.put("invoiceNo",invoiceNo.getText());
        billStructure.put("billPaidFor",billPaidFor.getText());
        billStructure.put("paymentMode",paymentMode.getText());
        billStructure.put("received",received.getText());
        billStructure.put("delimeter", delimeter.getValue());
        SettingsDAO.saveBillStructure(billStructure);

    }


    @Override
    public void initialize(URL url, ResourceBundle rb) {
    	delimeter.setItems(FXCollections.observableArrayList("New Line","Same Line"));
    
    	
      serviceChargeAmount= SettingsDAO.getServiceCharges();
      serviceCharge.setText(serviceChargeAmount);
      Map<String,String> billStructure= SettingsDAO.getBillStructure();

      transactionId.setText(billStructure.get("transactionId"));
      date.setText(billStructure.get("date"));
      paymentGateway.setText(billStructure.get("paymentGateway"));
      billingOffice.setText(billStructure.get("billingOffice"));
      consumerId.setText(billStructure.get("consumerId"));
      name.setText(billStructure.get("name"));
      invoiceNo.setText(billStructure.get("invoiceNo"));
      billPaidFor.setText(billStructure.get("billPaidFor"));
      paymentMode.setText(billStructure.get("paymentMode"));
      received.setText(billStructure.get("received"));
  	delimeter.setValue(billStructure.get("delimeter"));




    }


}
