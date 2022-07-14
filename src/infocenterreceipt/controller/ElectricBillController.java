/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package infocenterreceipt.controller;

import com.sun.javafx.print.Units;
import static com.sun.javafx.print.Units.MM;

import infocenterreceipt.dao.EBillDAO;
import infocenterreceipt.dao.SettingsDAO;
import infocenterreceipt.enums.Message;
import infocenterreceipt.util.Util;
import infocenterreceipt.model.EBillDetails;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.print.JobSettings;
import javafx.print.PageLayout;
import javafx.print.PageOrientation;
import javafx.print.Paper;
import javafx.print.Printer;
import javafx.print.PrinterJob;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

/**
 * FXML Controller class
 *
 * @author ndhara
 */
public class ElectricBillController implements Initializable {
    
    EBillDetails eBillDetails;
    @FXML
    TextArea billDetails;
    
    @FXML
    TextField transactionID;
    @FXML
    TextField paymentDate;
    @FXML
    TextField paymentGateway;
    @FXML
    TextField billingOffice;
    @FXML
    TextField consumerId;
    @FXML
    TextField customerName;
    @FXML
    TextField invoiceNumber;
    @FXML
    TextField billPaidFor;
    @FXML
    TextField paymentMode;
    @FXML
    TextField receivedAmount;
    @FXML
    TextField servicecharge;
    
    @FXML
    TextField txtSearch;
    
    @FXML
    TextField txtGivenAmount;
    
    @FXML
    Label msgServiceCharge;
    
    @FXML
    Label printstatus;
    
    @FXML
    Label lblTotalDue;
    @FXML
    Label lblReturnAmount;
    @FXML
    Label lblTakeMoreAmount;
    
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
    private TableColumn<EBillDetails, String> clmnBillPaidDate;
    
    @FXML
    Button savePrint;
    @FXML
    Button printBill;
    
    private ObservableList<List<EBillDetails>> data = null;

    Map<String,String> billStructure=new HashMap<String,String>();


    
    public void print() throws MalformedURLException {
        printstatus.setText("print : printing...");
        Paper photo = null;
        try {
            WebView webview = new WebView();
            final WebEngine webengine = webview.getEngine();
            Printer printer = Printer.getDefaultPrinter();
            Constructor<Paper> c = Paper.class.getDeclaredConstructor(String.class,
                    double.class, double.class, Units.class);
            c.setAccessible(true);
            try {
                photo = c.newInstance("10x15", 82, 297, MM);
            } catch (InstantiationException ex) {
                Logger.getLogger(ElectricBillController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalAccessException ex) {
                Logger.getLogger(ElectricBillController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalArgumentException ex) {
                Logger.getLogger(ElectricBillController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InvocationTargetException ex) {
                Logger.getLogger(ElectricBillController.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            PageLayout pageLayout = printer.createPageLayout(photo,
                    PageOrientation.PORTRAIT, Printer.MarginType.HARDWARE_MINIMUM);
            PrinterJob job = PrinterJob.createPrinterJob();
            
            job.getJobSettings().setPageLayout(pageLayout);
            
            JobSettings js = job.getJobSettings();
            
            Double w = js.getPageLayout().getLeftMargin();
            Double h = js.getPageLayout().getRightMargin();
            
            System.out.println("=------------" + w + "   " + h);
            if (job != null) {
                //System.out.println(job.getJobSettings().getPageLayout());
                
                File myFile = new File("bill.html");
                URL myUrl = myFile.toURI().toURL();
                webengine.load(myUrl.toString());
                
                webengine.print(job);
                
               
                job.endJob();
                
                printstatus.setText("print : Done");
            }
        } catch (NoSuchMethodException ex) {
            Logger.getLogger(ElectricBillController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SecurityException ex) {
            Logger.getLogger(ElectricBillController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private boolean createBillFile() throws IOException {
        boolean flag = false;
        StringBuilder htmlBuilder = new StringBuilder();
        if (eBillDetails != null) {
            htmlBuilder.append("<html>");
            htmlBuilder.append("<head><title>Info Center</title></head>");
            htmlBuilder.append("<body width=100%>");
            htmlBuilder.append("<table>");
            htmlBuilder.append("<tr><td nowrap colspan=2 width=100% valign=middle><center><img src='logo-png-bill.png' width=40/> <font size=4>Info Center</font></center></td></tr>");
            htmlBuilder.append("<tr><td nowrap colspan=2 width=100%><font size=2>Demari, tamluk, mob- 9036494984</font></td></tr>");
            htmlBuilder.append("<tr><td nowrap colspan=2 width=100%>~~~~~~~~~~~~~~~~~~~~~~~~~~~</td></tr>");
            if(null!=billStructure.get("transactionId") && !billStructure.get("transactionId").equals("")) {
                htmlBuilder.append("<tr><td nowrap><font size=2 color=\"#888888\">"+billStructure.get("transactionId")+"</font></td></tr><tr><td nowrap> <font size=3 color=\"#202021\">" + eBillDetails.getTransactionID() + "</font></td></tr>");
                htmlBuilder.append("<tr><td></tr>");
            }
            if(null!=billStructure.get("date") && !billStructure.get("date").equals("")) {
                htmlBuilder.append("<tr><td nowrap><font size=2 color=\"#888888\">"+billStructure.get("date")+"</font></td></tr><tr><td nowrap> <font size=3 color=\"#202021\">" + eBillDetails.getPaymentDate() + "</font></td></tr>");
                htmlBuilder.append("<tr><td></tr>");
            }
            if(null!=billStructure.get("consumerId") &&!billStructure.get("consumerId").equals("")) {
                htmlBuilder.append("<tr><td nowrap><font size=2 color=\"#888888\">"+billStructure.get("consumerId")+"</font></td></tr><tr><td nowrap> <font size=3 color=\"#202021\">" + eBillDetails.getConsumerId() + "</font></td></tr>");
                htmlBuilder.append("<tr><td></tr>");
            }
            if(null!=billStructure.get("name") && !billStructure.get("name").equals("")) {
                htmlBuilder.append("<tr><td nowrap><font size=2 color=\"#888888\">"+billStructure.get("name")+"</font></td></tr><tr><td nowrap> <font size=3 color=\"#202021\">" + eBillDetails.getCustomerName() + "</font></td></tr>");
                htmlBuilder.append("<tr><td></tr>");
            }
            if(null!=billStructure.get("invoiceNo")&& !billStructure.get("invoiceNo").equals("")) {
                htmlBuilder.append("<tr><td nowrap><font size=2 color=\"#888888\">"+billStructure.get("invoiceNo")+"</font></td></tr><tr><td nowrap> <font size=3 color=\"#202021\">" + eBillDetails.getInvoiceNumber() + "</font></td></tr>");
                htmlBuilder.append("<tr><td></tr>");
            }
            if(null!= billStructure.get("paymentMode")&& !billStructure.get("paymentMode").equals("")) {
                htmlBuilder.append("<tr><td nowrap><font size=2 color=\"#888888\">"+billStructure.get("paymentMode")+"</font></td></tr><tr><td nowrap> <font size=3 color=\"#202021\">" + eBillDetails.getPaymentMode() + "</font></td></tr>");
                htmlBuilder.append("<tr><td></tr>");
            }
            if(null!= billStructure.get("billPaidFor") && !billStructure.get("billPaidFor").equals("")) {
                htmlBuilder.append("<tr><td nowrap><font size=2 color=\"#888888\">"+billStructure.get("billPaidFor")+"</font></td></tr><tr><td nowrap> <font size=3 color=\"#202021\">" + eBillDetails.getBillPaidFor() + "</font></td></tr>");
                htmlBuilder.append("<tr><td></tr>");
            }
            if(null!= billStructure.get("billingOffice") && !billStructure.get("billingOffice").equals("")) {
                htmlBuilder.append("<tr><td nowrap><font size=2 color=\"#888888\">Billing Office</font></td></tr><tr><td nowrap> <font size=3 color=\"#202021\">"+billStructure.get("billingOffice")+"</font></td></tr>");
                htmlBuilder.append("<tr><td></tr>");
            }
            if(null!=billStructure.get("received")&& !billStructure.get("received").equals("")) {
                htmlBuilder.append("<tr><td nowrap><font size=2 color=\"#888888\">"+billStructure.get("received")+"</font></td></tr><tr><td nowrap> <img src='inr.png' width=20 style=\"vertical-align:middle\"/><font size=3 color=\"#202021\"> " + eBillDetails.getReceivedAmount() + "</font></td></tr>");
            }
            htmlBuilder.append("<tr><td nowrap><center><img src='paid.png' width=70/></center></td></tr>");
            htmlBuilder.append("</table>");
            htmlBuilder.append("</body>");
            htmlBuilder.append("</html>");
            try (FileOutputStream oS = new FileOutputStream(new File("bill.html"))) {
                oS.write(htmlBuilder.toString().getBytes());
                flag = true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Util.showErrorMessage(Message.NO_DATA.get(), Message.CAN_NOT_PRINT.get());
        }
        return flag;
        
    }
    
    private void displayData() {
        transactionID.setText(eBillDetails.getTransactionID());
        paymentDate.setText(eBillDetails.getPaymentDate());
        paymentGateway.setText(eBillDetails.getPaymentGateway());
        billingOffice.setText(eBillDetails.getBillingOffice());
        consumerId.setText(eBillDetails.getConsumerId());
        customerName.setText(eBillDetails.getCustomerName());
        invoiceNumber.setText(eBillDetails.getInvoiceNumber());
        billPaidFor.setText(eBillDetails.getBillPaidFor());
        paymentMode.setText(eBillDetails.getPaymentMode());
        receivedAmount.setText(eBillDetails.getReceivedAmount());
        
    }
    
    private void showDataToTable(List<EBillDetails> list) {
        List al = new ArrayList();
        // EBillDAO.getBillData();
        al.addAll(list);
        data = FXCollections.observableArrayList(al);
        tblBill.getItems().setAll(data);
        
    }


    @FXML
    private void dataPate(KeyEvent event) throws IOException {
        String lines[] = billDetails.getText().split("\\r?\\n");
        parse(lines);
    }
    
    
    private void parseNextLine(String []lines){
    	int i=0;
    	 for(String l:lines)
         {   try {


             for ( String key : billStructure.keySet() ) {
                 String value=null;
                 if(!l.contains(billStructure.get(key)))
                     continue;
                  value= l.substring(0, billStructure.get(key).trim().length());
                 System.out.println("-----------------"+value);
                 eBillDetails.setBillingOffice(billStructure.get("billingOffice"));
             if(value.equals(""))
              continue;
                 if (value.equalsIgnoreCase(billStructure.get("transactionId").trim())) {
                     eBillDetails.setTransactionID(lines[i+1]);
                 } else if (value.equalsIgnoreCase(billStructure.get("date").trim())) {
                     eBillDetails.setPaymentDate(lines[i+1]);
                 }
            else if(value.equalsIgnoreCase(billStructure.get("paymentGateway").trim())) {
                eBillDetails.setPaymentGateway(lines[i+1]);
            }

          else if(value.equalsIgnoreCase(billStructure.get("consumerId").trim())) {
                 eBillDetails.setConsumerId(lines[i+1]);
            }
            else if(value.equalsIgnoreCase(billStructure.get("name").trim())) {
               eBillDetails.setCustomerName(lines[i+1]);
            }
            else if(value.equalsIgnoreCase(billStructure.get("invoiceNo").trim())) {
                 eBillDetails.setInvoiceNumber(lines[i+1]);
             }
            else if(value.equalsIgnoreCase(billStructure.get("billPaidFor").trim())) {
               eBillDetails.setBillPaidFor(lines[i+1]);
            }
            else if(value.trim().equalsIgnoreCase(billStructure.get("paymentMode").trim())) {
                eBillDetails.setPaymentMode(lines[i+1]);
             }
            else if(value.trim().equalsIgnoreCase(billStructure.get("received").trim())) {

                 eBillDetails.setReceivedAmount(lines[i+1]);
            }
             }
         }
         catch (Exception e){
             e.printStackTrace();
         }
         i++;
         }

         displayData();
         calculateTotalAndDue();
         calculateReturnAmount();

         savePrint.setDisable(false);
     }
    
    
    private void parseSameLine(String []lines){
    	 for(String l:lines)
         {   try {


             for ( String key : billStructure.keySet() ) {
                 String value=null;
                 if(!l.contains(billStructure.get(key)))
                     continue;
                  value= l.substring(0, billStructure.get(key).trim().length());
                 System.out.println("-----------------"+value);
                 eBillDetails.setBillingOffice(billStructure.get("billingOffice"));
             if(value.equals(""))
              continue;
                 if (value.equalsIgnoreCase(billStructure.get("transactionId").trim())) {
                     eBillDetails.setTransactionID(l.substring(billStructure.get("transactionId").trim().length() + 1));
                 } else if (value.equalsIgnoreCase(billStructure.get("date").trim())) {
                     eBillDetails.setPaymentDate(l.substring(billStructure.get("date").trim().length() + 1));
                 }
            else if(value.equalsIgnoreCase(billStructure.get("paymentGateway").trim())) {
                eBillDetails.setPaymentGateway(l.substring(billStructure.get("paymentGateway").trim().length()+1));
            }

          else if(value.equalsIgnoreCase(billStructure.get("consumerId").trim())) {
                 eBillDetails.setConsumerId(l.substring(billStructure.get("consumerId").trim().length()+1));
            }
            else if(value.equalsIgnoreCase(billStructure.get("name").trim())) {
               eBillDetails.setCustomerName(l.substring(billStructure.get("name").trim().length()+1));
            }
            else if(value.equalsIgnoreCase(billStructure.get("invoiceNo").trim())) {
                 eBillDetails.setInvoiceNumber(l.substring(billStructure.get("invoiceNo").trim().length()+1));
             }
            else if(value.equalsIgnoreCase(billStructure.get("billPaidFor").trim())) {
               eBillDetails.setBillPaidFor(l.substring(billStructure.get("billPaidFor").trim().length()+1));
            }
            else if(value.trim().equalsIgnoreCase(billStructure.get("paymentMode").trim())) {
                eBillDetails.setPaymentMode(l.substring(billStructure.get("paymentMode").trim().length()+1));
             }
            else if(value.trim().equalsIgnoreCase(billStructure.get("received").trim())) {

                 eBillDetails.setReceivedAmount(l.substring(billStructure.get("received").trim().length()+1));
            }
             }
         }
         catch (Exception e){
             e.printStackTrace();
         }
         }

         displayData();
         calculateTotalAndDue();
         calculateReturnAmount();

         savePrint.setDisable(false);
     
    }

    private void parse(String []lines){

    	
    	

        if (lines.length >5) {
            eBillDetails = new EBillDetails();
            
            
            if(billStructure.get("delimeter").equalsIgnoreCase("New Line"))
            	parseNextLine(lines);
            else
            	parseSameLine(lines);
            
           
    }
        else {
            Util.showErrorMessage(Message.DATA_PARSING_FAIL.get(), Message.NOT_VALID_DATA.get());
            savePrint.setDisable(true);
        }
    }
    @FXML
    private void saveAndPrint(ActionEvent event) throws IOException {
        
        if (createBillFile()) {
            if (servicecharge.getText().equals("")) {
                msgServiceCharge.setText("Enter service charge");
            } else {
                eBillDetails.setServiceCharge(servicecharge.getText());
                EBillDAO.addBill(eBillDetails, 1);
                showDataToTable(EBillDAO.getBillData());
                print();
                billDetails.setText("");
            }
        }
        
    }
    
    private void calculateReturnAmount() {
        try {
            Float amount = Float.parseFloat(txtGivenAmount.getText().equals("")?"0.0":txtGivenAmount.getText()) - Float.parseFloat(lblTotalDue.getText().equals("")?"0.0":lblTotalDue.getText());
            if (amount > 0) {
                lblReturnAmount.setText("" + Math.round(amount));
                lblTakeMoreAmount.setText("0.0");
            } else if (amount < 0) {
                amount = -1 * amount;
                lblReturnAmount.setText("0.0");
                lblTakeMoreAmount.setText("" + Math.round(amount));
                
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @FXML
    private void calculateReturnAmount(KeyEvent event) throws IOException {
        calculateReturnAmount();
    }
    
    private void calculateTotalAndDue() {
        try {
            Float abount = Float.parseFloat(receivedAmount.getText() !=null ? receivedAmount.getText():"0.0") + Float.parseFloat(servicecharge.getText().toString());
            lblTotalDue.setText("" + Math.round(abount));
            calculateReturnAmount();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @FXML
    private void calculateTotalAndDue(KeyEvent event) throws IOException {
        calculateTotalAndDue();
    }
    
    private void search(String key) {
        showDataToTable(EBillDAO.getBillData(key));
    }
    
    @FXML
    private void searchBillDetails(KeyEvent event) throws IOException {
        search(txtSearch.getText());
    }
    
    @FXML
    private void printBill(ActionEvent event) throws IOException {
        
        eBillDetails = (EBillDetails) tblBill.getSelectionModel().getSelectedItem();
        if (createBillFile()) {
            print();
        }
        
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

        clmnTransactionID.setCellValueFactory(new PropertyValueFactory<EBillDetails, String>("transactionID"));
        clmnPaymentDate.setCellValueFactory(new PropertyValueFactory<EBillDetails, String>("paymentDate"));
        clmnConsumerId.setCellValueFactory(new PropertyValueFactory<EBillDetails, String>("consumerId"));
        clmnName.setCellValueFactory(new PropertyValueFactory<EBillDetails, String>("customerName"));
        clmnBillPaidFor.setCellValueFactory(new PropertyValueFactory<EBillDetails, String>("billPaidFor"));
        clmnReceived.setCellValueFactory(new PropertyValueFactory<EBillDetails, String>("receivedAmount"));
        clmnServiceCharge.setCellValueFactory(new PropertyValueFactory<EBillDetails, String>("serviceCharge"));
        clmnBillPaidDate.setCellValueFactory(new PropertyValueFactory<EBillDetails, String>("created"));
        
        msgServiceCharge.setText("");
        printBill.setDisable(true);
        showDataToTable(EBillDAO.getBillData());
        savePrint.setDisable(true);

        String serviceChargeAmount= SettingsDAO.getServiceCharges();

        servicecharge.setText(serviceChargeAmount);
        
        tblBill.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                printBill.setDisable(false);
            }
        });

        billStructure=SettingsDAO.getBillStructure();

    }
    
}
