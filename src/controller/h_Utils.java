
package controller;

import java.awt.CardLayout;
import java.awt.Font;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.JPanel;
import javax.swing.JTable;
import model.Nhanvien;
import view.*;

/* @author Hoang */


public class h_Utils {
    
    public void setTable(JTable table){
        table.setRowHeight(25);
        table.getTableHeader().setReorderingAllowed(false);
        table.getTableHeader().setFont(new Font("SansSerif", Font.TRUETYPE_FONT, 16));
        table.scrollRectToVisible(table.getCellRect(table.getSelectedRow(), 0, true));
    }
    
    
    public void setPanel(JPanel panel, CardLayout cLayout){
        panel.setLayout(cLayout);
        panel.add("p_home", new P_home());
        panel.add("p_donhang", new P_donhang());
        panel.add("p_khachhang", new P_khachhang());
        panel.add("p_nhapkho", new P_nhapkho());
        panel.add("p_sanpham", new P_sanpham());
        panel.add("p_baocao", new P_baocao());
        panel.add("p_thongke", new P_thongke());
        panel.add("p_nhanvien", new P_nhanvien());
        panel.add("p_chamcong", new P_chamcong());
        
    }
    
    public boolean init_dbConfig(){
        
        try {
            FileInputStream fis = new FileInputStream(new File("dbSaveConfig_hg.dat").getAbsolutePath());
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            int nRead;
            byte[] data = new byte[16384];
            while ((nRead = fis.read(data, 0, data.length)) != -1) buffer.write(data, 0, nRead);
            
            set_dbConfig(getDecrypted(buffer.toByteArray()));
            fis.close();
            buffer.close();
            
            Connection con = DBUtils.getConnection();
            ResultSet rs = con.createStatement().executeQuery("select * from Nhanvien");
            
        } catch (FileNotFoundException e1) {
            new view.Config_db().setVisible(true);
            return false;
            
        } catch (IOException | NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException 
                | IllegalBlockSizeException | BadPaddingException e2) {
            System.out.println("Error2: " + e2);
        } catch (NullPointerException | SQLException e4){
            return false;
        }
        return true;
    }
    
    
    private static Nhanvien nhanvien;

    public static Nhanvien getNhanvien() {
        return nhanvien;
    }

    public static void setNhanvien(Nhanvien nhanvien) {
        h_Utils.nhanvien = nhanvien;
    }

    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    

    public byte[] getEncrypted(String original) throws NoSuchAlgorithmException, NoSuchPaddingException,
            InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
        cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec("@McQfTjWnZq4t7w!".getBytes(), "AES"));
        return cipher.doFinal(original.getBytes());
    }
    
    public String getDecrypted(byte[] byteEncrypted) throws NoSuchAlgorithmException, NoSuchPaddingException,
            InvalidKeyException, IllegalBlockSizeException, BadPaddingException{
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
        cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec("@McQfTjWnZq4t7w!".getBytes(), "AES"));
        byte[] byteDecrypted = cipher.doFinal(byteEncrypted);
        return new String(byteDecrypted);
    }

    public void set_dbConfig(String dbConfig){
        DBUtils.setDbName(dbConfig.substring(0, dbConfig.indexOf(",")));
        DBUtils.setDbUser(dbConfig.substring(dbConfig.indexOf(",")+1, dbConfig.lastIndexOf(",")));
        DBUtils.setDbPass(dbConfig.substring(dbConfig.lastIndexOf(",")+1, dbConfig.length()));
    }
    
        
    
        
        
        
        
        
        
        
        
        
        
    
}
