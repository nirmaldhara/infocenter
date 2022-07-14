package infocenterreceipt.dao;

import infocenterreceipt.db.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SettingsDAO {

    public static Map<String,String> getBillStructure() {

        Map<String,String> billStructure=new HashMap<>();


        try (Connection con = Database.getConnection();) {
            ResultSet rs = null;
            PreparedStatement ps = con.prepareStatement("select * from bill_structure");
            rs = ps.executeQuery();
            while (rs != null && rs.next()) {

                    billStructure.put(rs.getString("fieldname"),rs.getString("fieldvalue"));
            }
        } catch (Exception e) {

        }

        return billStructure;
    }

    public static boolean saveBillStructure(Map<String,String> structure) {

        PreparedStatement ps = null;

        try (Connection con = Database.getConnection();) {

            for (Map.Entry<String,String> entry : structure.entrySet()) {
                ps = con.prepareStatement("INSERT OR REPLACE INTO bill_structure (fieldname,fieldvalue) VALUES(?,?)");
                ps.setString(1, entry.getKey());
                ps.setString(2, entry.getValue());
                ps.executeUpdate();
            }

        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null,
                    ex);
        }
        return true;

    }

    public static String getServiceCharges() {

        String serviceCharge = "0.0";
        try (Connection con = Database.getConnection();) {
            ResultSet rs = null;
            PreparedStatement ps = con.prepareStatement("select * from settings where fieldname='service_charge'");
            rs = ps.executeQuery();
            if (rs != null && rs.next()) {
                serviceCharge=rs.getString("value");
            }
        } catch (Exception e) {

        }

        return serviceCharge;
    }

    public static boolean saveSettings(String fieldname, String value) {

        PreparedStatement ps = null;

        try (Connection con = Database.getConnection();) {

            ps = con.prepareStatement("INSERT OR REPLACE INTO settings (fieldname,value) VALUES(?,?)");
            ps.setString(1, fieldname);
            ps.setString(2, value);
            ps.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null,
                    ex);
        }
        return true;
    }
}
