/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package infocenterreceipt.controller;

import infocenterreceipt.dao.EBillDAO;
import infocenterreceipt.model.EBillDetails;
import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author ndhara
 */
public class EbillReportController implements Initializable {

    @FXML
    DatePicker dateFrom;
    @FXML
    DatePicker dateTo;

    @FXML
    TableView tblBill;

    @FXML
    private TableColumn<EBillDetails, String> clmnTransactionID;
    @FXML
    private TableColumn<EBillDetails, String> clmnPaymentDate;
    @FXML
    private TableColumn<EBillDetails, String> clmnConsumerId;
    @FXML
    private TableColumn<EBillDetails, String> clmnName;
    @FXML
    private TableColumn<EBillDetails, String> clmnBillPaidFor;
    @FXML
    private TableColumn<EBillDetails, String> clmnReceived;
    @FXML
    private TableColumn<EBillDetails, String> clmnServiceCharge;

    @FXML
    Label lblTotalService;
    @FXML
    Label lblTotalBill;

    private ObservableList<List<EBillDetails>> data = null;

    @FXML
    private void showDataToTable() {
        LocalDate fromDate = dateFrom.getValue();
        
         String fmonth="";
        String fday="";
        
        
        
        
        if(fromDate.getMonthValue()<10)
            fmonth="0"+fromDate.getMonthValue();
        else
            fmonth=""+fromDate.getMonthValue();
        
        if(fromDate.getDayOfMonth()<10)
            fday="0"+(fromDate.getDayOfMonth());
        else
            fday=""+(fromDate.getDayOfMonth());
        
        String dateF =  fromDate.getYear()+ "-"+fmonth + "-" + fday;
        LocalDate toDate = dateTo.getValue();
        
        String tmonth="";
        String tday="";
        
        
        
        
        if(toDate.getMonthValue()<10)
            tmonth="0"+toDate.getMonthValue();
        else
            tmonth=""+toDate.getMonthValue();
        
        if(toDate.getDayOfMonth()<10)
            tday="0"+(toDate.getDayOfMonth()+1);
        else
            tday=""+(toDate.getDayOfMonth()+1);
        String dateT =  toDate.getYear()+ "-" + tmonth+"-"+(tday);
        List al = new ArrayList();
        al.addAll(EBillDAO.getBillDataByDate(dateF, dateT));
        data = FXCollections.observableArrayList(al);
        tblBill.getItems().setAll(data);

        try {
            Map m = EBillDAO.getTotal();
            lblTotalBill.setText(m.get("tbill").toString());
            lblTotalService.setText(m.get("tservice").toString());

        } catch (SQLException ex) {
            Logger.getLogger(EbillReportController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        clmnTransactionID.setCellValueFactory(new PropertyValueFactory<EBillDetails, String>("transactionID"));
        clmnPaymentDate.setCellValueFactory(new PropertyValueFactory<EBillDetails, String>("paymentDate"));
        clmnConsumerId.setCellValueFactory(new PropertyValueFactory<EBillDetails, String>("consumerId"));
        clmnName.setCellValueFactory(new PropertyValueFactory<EBillDetails, String>("customerName"));
        clmnBillPaidFor.setCellValueFactory(new PropertyValueFactory<EBillDetails, String>("billPaidFor"));
        clmnReceived.setCellValueFactory(new PropertyValueFactory<EBillDetails, String>("receivedAmount"));
        clmnServiceCharge.setCellValueFactory(new PropertyValueFactory<EBillDetails, String>("serviceCharge"));

        Calendar cal = Calendar.getInstance();
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DATE);
        int year = cal.get(Calendar.YEAR);

        cal.add(Calendar.MONTH, -1);

        int month1 = cal.get(Calendar.MONTH);
        int day1 = cal.get(Calendar.DATE);
        int year1 = cal.get(Calendar.YEAR);

        dateFrom.setValue(LocalDate.of(year1, month1 + 1, day1));
        dateTo.setValue(LocalDate.of(year, month + 1, day));
        System.out.println("hi--------");
        showDataToTable();

    }

}
