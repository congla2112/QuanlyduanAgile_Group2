/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import controller.DBUtils;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import model.Nhanvien;

/**
 *
 * @author DELL
 */
public class P_nhanvien extends javax.swing.JPanel {

    /**
     * Creates new form P_nhanvien
     */
    
    int index = -1;
    List<Nhanvien> list = new ArrayList<>();
    DefaultTableModel model = new DefaultTableModel();

    String manv, tennv, sodienthoai, diachi, email, username, pass, rule;
    boolean gioitinh;
    public static String getMD5(String md5){
        try {
            MessageDigest  md= MessageDigest.getInstance("MD5");
          
            byte [] array=md.digest(md5.getBytes());
            StringBuffer sb= new StringBuffer();
            for (int i=0;i<array.length; i++){
                sb.append(Integer.toHexString((array[i] &0xFF) | 0x100).substring(1, 3));
            }
            return sb.toString();
            
        } catch (Exception e) { e.printStackTrace();
    }
        return null;
        
    }
    
    public void setdata() {
        try {
            model.addColumn("Mã NV");
            model.addColumn("Họ tên");
            model.addColumn("Số ĐT");
            model.addColumn("Địa chỉ");
            model.addColumn("Giới tính");
            model.addColumn("Email");
            model.addColumn("Username");
            model.addColumn("Password");
            model.addColumn("Quyền");
            tbl_nhanvien.setModel(model);
            load_list();
            tbl_nhanvien.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
                @Override
                public void valueChanged(ListSelectionEvent lse) {
                    index = tbl_nhanvien.getSelectedRow();
                    if (index >= 0) {
                        txt_manv.setText(tbl_nhanvien.getValueAt(index, 0).toString());
                        txt_tennv.setText(tbl_nhanvien.getValueAt(index, 1).toString());
                        txt_sodt.setText(tbl_nhanvien.getValueAt(index, 2).toString());
                        txt_diachi.setText(tbl_nhanvien.getValueAt(index, 3).toString());

                        if (tbl_nhanvien.getValueAt(index, 4).toString().equals("Nam")) {
                            ck_nam.setSelected(true);
                            ck_nu.setSelected(false);
                        } else {
                            ck_nam.setSelected(false);
                            ck_nu.setSelected(true);
                        }
                        txt_email.setText(tbl_nhanvien.getValueAt(index, 5).toString());
                        txt_user.setText(tbl_nhanvien.getValueAt(index, 6).toString());
                        txt_matkhau.setText(tbl_nhanvien.getValueAt(index, 7).toString());
                        if (tbl_nhanvien.getValueAt(index, 8).toString().equals("admin")) {
                            cbb_quyen.setSelectedIndex(0);
                        } else {
                            cbb_quyen.setSelectedIndex(1);
                        }
                        tbl_nhanvien.scrollRectToVisible(tbl_nhanvien.getCellRect(index, 0, true));

                    }
                }
            });
        } catch (NullPointerException e2) {
            System.out.println(e2);

        }
        tbl_nhanvien.setRowSelectionInterval(0, 0);
    }
    
    public void load_list() {
        model.setRowCount(0);
        try {
            Connection connection = DBUtils.getConnection();
            Statement statement = connection.createStatement();
            String sql = "select * from nhanvien ";
            ResultSet rs = statement.executeQuery(sql);
            list.clear();
            while (rs.next()) {
                manv = rs.getString(1);
                tennv = rs.getString(2);
                sodienthoai = rs.getString(3);
                diachi = rs.getString(4);
                gioitinh = rs.getBoolean(5);
                email = rs.getString(6);
                username = rs.getString(7);
                pass = rs.getString(8);
                rule = rs.getString(9);
                list.add(new Nhanvien(manv, tennv, sodienthoai, diachi, gioitinh, email, username, pass, rule));
                Vector row = new Vector();
                row.add(manv);
                row.add(tennv);
                row.add(sodienthoai);
                row.add(diachi);

                if (gioitinh) {
                    row.add("Nam");
                } else {
                    row.add("Nữ");
                }
                row.add(email);
                row.add(username);
                row.add(pass);
                row.add(rule);
                model.addRow(row);
            }

        } catch (SQLException e1) {
            System.out.println("Loadlist: " + e1);
        }
    }
     public boolean txt() {
        try {
            if (txt_manv.getText().equals("")) {
                JOptionPane.showMessageDialog(this, "Mã nhân viên không được để trống");
                return false;
            }
            if (txt_tennv.getText().equals("")) {
                JOptionPane.showMessageDialog(this, "Tên nhân viên không được để trống");
                return false;
            }
            if (txt_user.getText().equals("")) {
                JOptionPane.showMessageDialog(this, "Tên đăng nhập không được để trống");
                return false;
            }
            if (txt_matkhau.getText().equals("")) {
                JOptionPane.showMessageDialog(this, "Mật khẩu  không được để trống");
                return false;
            }
            if (cbb_quyen.getSelectedItem().equals("")) {
                JOptionPane.showMessageDialog(this, "Quyền không được để trống");
                return false;
            }
            if (txt_sodt.getText().equals("")) {
                JOptionPane.showMessageDialog(this, "Số điện thoại không được để trống");
                return false;
            }
            if (txt_diachi.getText().equals("")) {
                JOptionPane.showMessageDialog(this, "Địa chỉ không được để trống");
                return false;
            }
            if (txt_email.getText().equals("")) {
                JOptionPane.showMessageDialog(this, "Email không được để trống");
                return false;
            }
            if (txt_sodt.getText().equals("")) {
                JOptionPane.showMessageDialog(this, "Số điện thoại không được để trống");
                return false;
            }
            if (!txt_sodt.getText().matches("\\d+") || txt_sodt.getText().length() != 10) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập số điện thoại là số và có 10 chữ số");
                return false;
            }
            if (!txt_email.getText().matches("\\w+@+\\w+.+\\w")) {
                JOptionPane.showMessageDialog(this, "Email phải đúng định dạng!VD:abc@abc.xyz");
                return false;
            }
            if(txt_matkhau.getText().length()<6){
                JOptionPane.showMessageDialog(this, "Mật khẩu phải có 6 kí tự trở lên");
                return false;
            }
            if (!ck_nam.isSelected() && !ck_nu.isSelected()) {
                JOptionPane.showMessageDialog(this, "Chưa chọn giới tính");
                return false;
            }
            if (ck_nam.isSelected()) {
                gioitinh = true;
            } else {
                gioitinh = false;
            }
            for (int i = 0; i < list.size(); i++) {

                if (txt_user.getText().equals(list.get(i).getUsername())) {
                    JOptionPane.showMessageDialog(this, "Username đã có vui lòng thử username khác");
                    return false;
                }
            }
            for (int i = 0; i < list.size(); i++) {
                    if (txt_manv.getText().equalsIgnoreCase(list.get(i).getManv())) {
                        JOptionPane.showMessageDialog(this, "Mã nhân viên đã có vui lòng thử mã nhân viên khác");
                    return false;
                    }
                }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi" + e);
            return false;
        }
        return true;
    }

    public boolean txtup() {
        try {
            if (txt_diachi.getText().equals("") || txt_email.getText().equals("")
                    || txt_manv.getText().equals("") || txt_matkhau.getText().equals("")
                    || txt_sodt.getText().equals("") || txt_tennv.getText().equals("")
                    || txt_user.getText().equals("")) {
                JOptionPane.showMessageDialog(this, "Không được để trống trường dữ liệu");
                return false;
            }
            if (!txt_sodt.getText().matches("\\d+") || txt_sodt.getText().length() != 10) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập số điện thoại là số và có 10 chữ số");
                return false;
            }
            if (!txt_email.getText().matches("\\w+@+\\w+.+\\w")) {
                JOptionPane.showMessageDialog(this, "Email phải đúng định dạng!VD:abc@abc.xyz");
                return false;
            }
             if(txt_matkhau.getText().length()<6){
                JOptionPane.showMessageDialog(this, "Mật khẩu phải có 6 kí tự trở lên");
                return false;
            }

            if (!ck_nam.isSelected() && !ck_nu.isSelected()) {
                JOptionPane.showMessageDialog(this, "Chưa chọn giới tính");
                return false;
            }
            if (ck_nam.isSelected()) {
                gioitinh = true;
            } else {
                gioitinh = false;
            }
            
            for (int i = 0; i < list.size(); i++) {

                if (txt_user.getText().equals(list.get(i).getUsername())&&!txt_user.getText().equals(list.get(tbl_nhanvien.getSelectedRow()).getUsername())) {
                    JOptionPane.showMessageDialog(this, "Username đã có vui lòng thử username khác");
                    return false;
                }
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Không được trùng ID");
            return false;
        }
        return true;
    }
    
    
    public P_nhanvien() {
        initComponents();
        setdata();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbl_nhanvien = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txt_manv = new javax.swing.JTextField();
        cbb_quyen = new javax.swing.JComboBox();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        txt_sodt = new javax.swing.JTextField();
        txt_diachi = new javax.swing.JTextField();
        txt_email = new javax.swing.JTextField();
        ck_nam = new javax.swing.JRadioButton();
        ck_nu = new javax.swing.JRadioButton();
        btn_them = new javax.swing.JButton();
        btn_sua = new javax.swing.JButton();
        btn_xoa = new javax.swing.JButton();
        btn_lammoi = new javax.swing.JButton();
        btn_tim = new javax.swing.JButton();
        txt_tennv = new javax.swing.JTextField();
        txt_user = new javax.swing.JTextField();
        btn_excel = new javax.swing.JButton();
        txt_matkhau = new javax.swing.JPasswordField();

        setBackground(new java.awt.Color(255, 255, 204));
        setMaximumSize(new java.awt.Dimension(915, 540));
        setMinimumSize(new java.awt.Dimension(915, 540));

        jPanel1.setBackground(new java.awt.Color(0, 153, 51));
        jPanel1.setMaximumSize(new java.awt.Dimension(915, 35));
        jPanel1.setMinimumSize(new java.awt.Dimension(915, 35));
        jPanel1.setPreferredSize(new java.awt.Dimension(915, 35));

        jLabel1.setFont(new java.awt.Font("Arial", 1, 16)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("QUẢN LÝ NHÂN VIÊN");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(373, 373, 373)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE)
        );

        tbl_nhanvien.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(tbl_nhanvien);

        jLabel2.setText("Mã Nhân Viên:");

        jLabel3.setText("Tên Nhân Viên:");

        jLabel4.setText("Tên Đăng Nhập:");

        jLabel5.setText("Mật Khẩu:");

        jLabel6.setText("Quyền:");

        cbb_quyen.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "admin", "staff" }));

        jLabel7.setText("Số Điện Thoại:");

        jLabel8.setText("Địa Chỉ:");

        jLabel9.setText("Email:");

        jLabel10.setText("Giới Tính:");

        ck_nam.setText("Nam");
        ck_nam.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ck_namActionPerformed(evt);
            }
        });

        ck_nu.setText("Nữ");
        ck_nu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ck_nuActionPerformed(evt);
            }
        });

        btn_them.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/Add-Folder-icon.png"))); // NOI18N
        btn_them.setText("Thêm Tài khoản");
        btn_them.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_themActionPerformed(evt);
            }
        });

        btn_sua.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/system-software-update-icon.png"))); // NOI18N
        btn_sua.setText("Sửa Tài Khoản");
        btn_sua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_suaActionPerformed(evt);
            }
        });

        btn_xoa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/Actions-dialog-close-icon.png"))); // NOI18N
        btn_xoa.setText("Xóa Tài Khoản");
        btn_xoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_xoaActionPerformed(evt);
            }
        });

        btn_lammoi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/new-icon.png"))); // NOI18N
        btn_lammoi.setText("Làm Mới");
        btn_lammoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_lammoiActionPerformed(evt);
            }
        });

        btn_tim.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/Search-icon.png"))); // NOI18N
        btn_tim.setText("Tìm Kiếm");
        btn_tim.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_timActionPerformed(evt);
            }
        });

        btn_excel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/Print-icon.png"))); // NOI18N
        btn_excel.setText("In");
        btn_excel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_excelActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(59, 59, 59)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel6)
                    .addComponent(jLabel5)
                    .addComponent(jLabel4)
                    .addComponent(jLabel3)
                    .addComponent(jLabel2))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(cbb_quyen, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_manv, javax.swing.GroupLayout.DEFAULT_SIZE, 159, Short.MAX_VALUE)
                    .addComponent(txt_tennv, javax.swing.GroupLayout.DEFAULT_SIZE, 159, Short.MAX_VALUE)
                    .addComponent(txt_user, javax.swing.GroupLayout.DEFAULT_SIZE, 159, Short.MAX_VALUE)
                    .addComponent(txt_matkhau))
                .addGap(56, 56, 56)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel7)
                    .addComponent(jLabel8)
                    .addComponent(jLabel9)
                    .addComponent(jLabel10))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(ck_nam)
                            .addGap(18, 18, 18)
                            .addComponent(ck_nu))
                        .addComponent(txt_sodt)
                        .addComponent(txt_diachi)
                        .addComponent(txt_email, javax.swing.GroupLayout.DEFAULT_SIZE, 169, Short.MAX_VALUE))
                    .addComponent(btn_excel))
                .addGap(75, 75, 75)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btn_them, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btn_sua, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btn_xoa, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btn_lammoi, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btn_tim, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(77, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txt_manv, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7)
                    .addComponent(txt_sodt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_them))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel8)
                    .addComponent(txt_diachi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_sua)
                    .addComponent(txt_tennv, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jLabel9)
                    .addComponent(txt_email, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_xoa)
                    .addComponent(txt_user, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(17, 17, 17)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jLabel10)
                    .addComponent(ck_nam)
                    .addComponent(ck_nu)
                    .addComponent(btn_lammoi)
                    .addComponent(txt_matkhau, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(cbb_quyen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_tim)
                    .addComponent(btn_excel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 29, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void ck_namActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ck_namActionPerformed
        // TODO add your handling code here:
        ck_nu.setSelected(false);
    }//GEN-LAST:event_ck_namActionPerformed

    private void ck_nuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ck_nuActionPerformed
        // TODO add your handling code here:
        ck_nam.setSelected(false);
    }//GEN-LAST:event_ck_nuActionPerformed

    private void btn_themActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_themActionPerformed
        // TODO add your handling code here:
        if (txt()) {
            try {

                Connection connection = DBUtils.getConnection();
                String sql = "insert into nhanvien values(?,?,?,?,?,?,?,?,?)";
                PreparedStatement pst = connection.prepareStatement(sql);
                pst.setString(1, txt_manv.getText());
                pst.setString(2, txt_tennv.getText());
                pst.setString(3, txt_sodt.getText());
                pst.setString(4, txt_diachi.getText());
                pst.setBoolean(5, gioitinh);
                pst.setString(6, txt_email.getText());
                pst.setString(7, txt_user.getText());
                pst.setString(8, getMD5(new String (txt_matkhau.getPassword())));
                pst.setString(9, cbb_quyen.getSelectedItem().toString());
                pst.executeUpdate();

                JOptionPane.showMessageDialog(this, "Lưu thành công!");

                
                load_list();

            }
            catch (Exception e4) {
                System.out.println("Lỗi" + e4);
            }

        }
    }//GEN-LAST:event_btn_themActionPerformed

    private void btn_lammoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_lammoiActionPerformed
        // TODO add your handling code here:
        txt_diachi.setText("");
        txt_email.setText("");
        txt_manv.setText("");
        txt_matkhau.setText("");
        txt_sodt.setText("");
        txt_tennv.setText("");
        txt_user.setText("");
        ck_nam.setSelected(false);
        ck_nu.setSelected(false);
    }//GEN-LAST:event_btn_lammoiActionPerformed

    private void btn_timActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_timActionPerformed
        // TODO add your handling code here:
        boolean find_check = false;
        try {
            String find_id = JOptionPane.showInputDialog(this, "Nhập mã nhân viên cần tìm");
            while (find_id.equals("")) {
                JOptionPane.showMessageDialog(this, "Không để trống mã!");
                find_id = JOptionPane.showInputDialog(this, "Nhập mã nhân viên cần tìm");
            }
            for (int i = 0; i < list.size(); i++) {
                if (find_id.equalsIgnoreCase(list.get(i).getManv())) {

                    find_check = true;
                    tbl_nhanvien.setRowSelectionInterval(i, i);
                }
            }

            if (!find_check) {
                JOptionPane.showMessageDialog(this, "Không có nhân viên này!");
            }

        } catch (NullPointerException e2) {
        }
    }//GEN-LAST:event_btn_timActionPerformed

    private void btn_suaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_suaActionPerformed
        // TODO add your handling code here:
        if (index != -1 && txtup()) {
            try {

                Connection connection = DBUtils.getConnection();
                String sql = "update nhanvien set tennv=?,sodienthoai=?,diachi=?,gioitinh=?"
                + ",email=?,username=?,password=?,manhomquyen=? where manv=?";
                PreparedStatement pst = connection.prepareStatement(sql);
                pst.setString(1, txt_tennv.getText());
                pst.setString(2, txt_sodt.getText());
                pst.setString(3, txt_diachi.getText());
                pst.setBoolean(4, gioitinh);
                pst.setString(5, txt_email.getText());
                pst.setString(6, txt_user.getText());
                pst.setString(7, getMD5(new String (txt_matkhau.getPassword())));
                pst.setString(8, cbb_quyen.getSelectedItem().toString());
                pst.setString(9, txt_manv.getText());
                pst.executeUpdate();

                JOptionPane.showMessageDialog(this, "Sửa thành công!");

                load_list();

            } catch (Exception e4) {
                System.out.println("Lỗi" + e4);
            }

        }
    }//GEN-LAST:event_btn_suaActionPerformed

    private void btn_xoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_xoaActionPerformed
        // TODO add your handling code here:
        if (index != -1) {
            int ret = JOptionPane.showConfirmDialog(this, "Xác nhận xóa?",
                "Confirm", JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);

            if (ret == JOptionPane.YES_OPTION) {
                try {
                    Connection connection = DBUtils.getConnection();
                    Statement statement = connection.createStatement();
                    String sql2 = "Delete From nhanvien where Manv = N'" + list.get(index).getManv() + "'";
                    statement.executeUpdate(sql2);
                    load_list();
                    JOptionPane.showMessageDialog(this, "Xóa thành công!");
                    tbl_nhanvien.setRowSelectionInterval(0, 0);
                } catch (SQLException e1) {
                    JOptionPane.showMessageDialog(this, "Nhân viên này không thể xóa");
                }
            }
        }
    }//GEN-LAST:event_btn_xoaActionPerformed
// xuat file excel
    public void exportExcel(JTable table) {
        JFileChooser chooser = new JFileChooser();
        int i = chooser.showSaveDialog(chooser);
        if (i == JFileChooser.APPROVE_OPTION) {
            File file = chooser.getSelectedFile();
            try {
                FileWriter out = new FileWriter(file + ".xls");
                BufferedWriter bwrite = new BufferedWriter(out);
                DefaultTableModel defaultTableModel = (DefaultTableModel) table.getModel();
                // ten Cot
                for (int j = 0; j < table.getColumnCount(); j++) {
                    bwrite.write(defaultTableModel.getColumnName(j) + "\t");
                }
                bwrite.write("\n");
                // Lay du lieu dong
                for (int j = 0; j < table.getRowCount(); j++) {
                    for (int k = 0; k < table.getColumnCount(); k++) {
                        bwrite.write(defaultTableModel.getValueAt(j, k) + "\t");
                    }
                    bwrite.write("\n");
                }
                bwrite.close();
                JOptionPane.showMessageDialog(null, "Lưu file thành công!");
            } catch (Exception e2) {
                JOptionPane.showMessageDialog(null, "Lỗi khi lưu file!");
            }
        }
    }
    private void btn_excelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_excelActionPerformed
        // TODO add your handling code here:
        exportExcel(tbl_nhanvien);
    }//GEN-LAST:event_btn_excelActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_excel;
    private javax.swing.JButton btn_lammoi;
    private javax.swing.JButton btn_sua;
    private javax.swing.JButton btn_them;
    private javax.swing.JButton btn_tim;
    private javax.swing.JButton btn_xoa;
    private javax.swing.JComboBox cbb_quyen;
    private javax.swing.JRadioButton ck_nam;
    private javax.swing.JRadioButton ck_nu;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tbl_nhanvien;
    private javax.swing.JTextField txt_diachi;
    private javax.swing.JTextField txt_email;
    private javax.swing.JTextField txt_manv;
    private javax.swing.JPasswordField txt_matkhau;
    private javax.swing.JTextField txt_sodt;
    private javax.swing.JTextField txt_tennv;
    private javax.swing.JTextField txt_user;
    // End of variables declaration//GEN-END:variables
}
