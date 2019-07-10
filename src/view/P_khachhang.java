/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import controller.DBUtils;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import model.Khachhang;

/**
 *
 * @author DELL
 */
public class P_khachhang extends javax.swing.JPanel {

    /**
     * Creates new form p_khachhang
     */
    int index = -1;
    List<Khachhang> list = new ArrayList<>();
    DefaultTableModel model = new DefaultTableModel();
    String makh, tenkh, diachi, sodienthoai, email, cmt;
    boolean gioitinh;

    public void setdata() {
        try {
            model.addColumn("Mã Thẻ Khách Hàng");
            model.addColumn("Tên Khách Hàng");
            model.addColumn("Địa Chỉ");
            model.addColumn("Điện Thoại");
            model.addColumn("Email");
            model.addColumn("Số CMTND");
            model.addColumn("Giới Tính");
           
            txt_mathe.setEditable(false);
            tbl_khachhang.setModel(model);
            load_list();
            tbl_khachhang.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
                @Override
                public void valueChanged(ListSelectionEvent lse) {
                    index = tbl_khachhang.getSelectedRow();
                    if (index >= 0) {
                        txt_mathe.setText(tbl_khachhang.getValueAt(index, 0).toString());
                        txt_tenkh.setText(tbl_khachhang.getValueAt(index, 1).toString());
                        txt_diachikh.setText(tbl_khachhang.getValueAt(index, 2).toString());
                        txt_sodt.setText(tbl_khachhang.getValueAt(index, 3).toString());
                        txt_email.setText(tbl_khachhang.getValueAt(index, 4).toString());
                        txt_cmtnd.setText(tbl_khachhang.getValueAt(index, 5).toString());

                        if (tbl_khachhang.getValueAt(index, 6).toString().equals("Nam")) {
                            ck_nam.setSelected(true);
                            ck_nu.setSelected(false);
                        } else {
                            ck_nam.setSelected(false);
                            ck_nu.setSelected(true);
                        }                  

                        tbl_khachhang.scrollRectToVisible(tbl_khachhang.getCellRect(index, 0, true));

                    }
                }
            });
        } catch (NullPointerException e2) {
            System.out.println(e2);

        }
//        tlb_sanpham.setRowSelectionInterval(0, 0);
    }
    
    public void load_list() {
        model.setRowCount(0);
        try {
            Connection connection = DBUtils.getConnection();
            Statement statement = connection.createStatement();
            String sql = "select * from khachhang";
            ResultSet rs = statement.executeQuery(sql);
            list.clear();
            while (rs.next()) {
                makh = rs.getString(1);
                tenkh = rs.getString(2);
                diachi = rs.getString(3);
                sodienthoai = rs.getString(4);
                email = rs.getString(5);
                cmt = rs.getString(6);
                gioitinh = rs.getBoolean(7);

                list.add(new Khachhang(makh, tenkh, diachi, sodienthoai, email, cmt, gioitinh));
                Vector row = new Vector();
                row.add(makh);
                row.add(tenkh);
                row.add(diachi);
                row.add(sodienthoai);
                row.add(email);
                row.add(cmt);
                if (gioitinh) {
                    row.add("Nam");
                } else {
                    row.add("Nữ");
                }
                model.addRow(row);
            }

        } catch (SQLException e1) {
            System.out.println("Loadlist: " + e1);
        }
    }
    
     public boolean txt() {
        try {
            
            if (txt_mathe.getText().equals("")) {
                JOptionPane.showMessageDialog(this, "Mã khách hàng không được để trống");
                return false;
            }
            if (txt_tenkh.getText().equals("")) {
                JOptionPane.showMessageDialog(this, "Tên khách hàng không được để trống");
                return false;
            }
            if (txt_diachikh.getText().equals("")) {
                JOptionPane.showMessageDialog(this, "Địa chỉ khách hàng không được để trống");
                return false;
            }
            if (txt_sodt.getText().equals("")) {
                JOptionPane.showMessageDialog(this, "Số điện thoại khách hàng không được để trống");
                return false;
            }
            if (txt_email.getText().equals("")) {
                JOptionPane.showMessageDialog(this, "Email khách hàng không được để trống");
                return false;
            }
            if (txt_cmtnd.getText().equals("")) {
                JOptionPane.showMessageDialog(this, "Số CMTND khách hàng không được để trống");
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
            if (!txt_cmtnd.getText().matches("\\d+") || txt_cmtnd.getText().length() != 9) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập số CMTND là số và có 9 chữ số");
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
           
           

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi" + e);
            return false;
        }
        return true;
    }

    public P_khachhang() {
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
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txt_mathe = new javax.swing.JTextField();
        txt_tenkh = new javax.swing.JTextField();
        txt_diachikh = new javax.swing.JTextField();
        txt_sodt = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbl_khachhang = new javax.swing.JTable();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txt_email = new javax.swing.JTextField();
        txt_cmtnd = new javax.swing.JTextField();
        ck_nam = new javax.swing.JRadioButton();
        ck_nu = new javax.swing.JRadioButton();
        btn_lm = new javax.swing.JButton();
        btn_them = new javax.swing.JButton();
        btn_xoa = new javax.swing.JButton();
        btn_sua = new javax.swing.JButton();
        btn_tim = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();

        setBackground(new java.awt.Color(255, 255, 204));
        setMaximumSize(new java.awt.Dimension(915, 540));
        setMinimumSize(new java.awt.Dimension(915, 540));

        jPanel1.setBackground(new java.awt.Color(0, 153, 51));
        jPanel1.setMaximumSize(new java.awt.Dimension(915, 35));
        jPanel1.setMinimumSize(new java.awt.Dimension(915, 35));
        jPanel1.setPreferredSize(new java.awt.Dimension(915, 35));

        jLabel1.setFont(new java.awt.Font("Arial", 1, 16)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("QUẢN LÝ KHÁCH HÀNG");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(357, 357, 357)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel2.setText("Mã Thẻ Khách Hàng:");

        jLabel3.setText("Tên Khách Hàng:");

        jLabel4.setText("Địa Chỉ:");

        jLabel5.setText("Số Điện Thoại:");

        txt_mathe.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_matheActionPerformed(evt);
            }
        });

        tbl_khachhang.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(tbl_khachhang);

        jLabel6.setText("Email:");

        jLabel7.setText("CMTND:");

        jLabel8.setText("Giới Tính:");

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

        btn_lm.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/new-icon.png"))); // NOI18N
        btn_lm.setText("Làm Mới");
        btn_lm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_lmActionPerformed(evt);
            }
        });

        btn_them.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/Add-Folder-icon.png"))); // NOI18N
        btn_them.setText("Thêm Tài Khoản");
        btn_them.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_themActionPerformed(evt);
            }
        });

        btn_xoa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/Actions-dialog-close-icon.png"))); // NOI18N
        btn_xoa.setText("Xóa Khách Hàng");
        btn_xoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_xoaActionPerformed(evt);
            }
        });

        btn_sua.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/system-software-update-icon.png"))); // NOI18N
        btn_sua.setText("Sửa Khách Hàng");
        btn_sua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_suaActionPerformed(evt);
            }
        });

        btn_tim.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/Search-icon.png"))); // NOI18N
        btn_tim.setText("Tìm Khách hàng");
        btn_tim.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_timActionPerformed(evt);
            }
        });

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/Print-icon.png"))); // NOI18N
        jButton1.setText("In");

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
                .addGap(68, 68, 68)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txt_tenkh)
                    .addComponent(txt_mathe)
                    .addComponent(txt_diachikh)
                    .addComponent(txt_sodt, javax.swing.GroupLayout.DEFAULT_SIZE, 139, Short.MAX_VALUE))
                .addGap(64, 64, 64)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel6)
                    .addComponent(jLabel7)
                    .addComponent(jLabel8))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(ck_nam)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(ck_nu))
                        .addComponent(txt_cmtnd)
                        .addComponent(txt_email, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jButton1))
                .addGap(69, 69, 69)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btn_sua, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btn_them, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btn_lm, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btn_xoa, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btn_tim, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(102, 102, 102))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel2)
                        .addComponent(txt_mathe, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel6)
                        .addComponent(txt_email, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btn_lm))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(41, 41, 41)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(txt_cmtnd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btn_them)
                            .addComponent(txt_tenkh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(ck_nam)
                            .addComponent(ck_nu)
                            .addComponent(btn_xoa)
                            .addComponent(txt_diachikh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txt_sodt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_sua)
                    .addComponent(jButton1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btn_tim)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 259, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btn_lmActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_lmActionPerformed
        // TODO add your handling code here
        txt_mathe.setEditable(true);
        txt_sodt.setText("");
        txt_email.setText("");
        txt_mathe.setText("");
        txt_diachikh.setText("");
        txt_tenkh.setText("");
        txt_cmtnd.setText("");
        ck_nam.setSelected(false);
        ck_nu.setSelected(false);
    }//GEN-LAST:event_btn_lmActionPerformed

    private void btn_themActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_themActionPerformed
        // TODO add your handling code here:
        if (txt()) {

            try {

                for (int i = 0; i < list.size(); i++) {
                    if (txt_mathe.getText().equalsIgnoreCase(list.get(i).getMakh())) {
                        JOptionPane.showMessageDialog(this, "Mã khách hàng đã được đăng ký mời nhập mã khách hàng khác");

                    }
                }

                Connection connection = DBUtils.getConnection();
                String sql = "insert into khachhang values(?,?,?,?,?,?,?)";
                PreparedStatement pst = connection.prepareStatement(sql);
                pst.setString(1, txt_mathe.getText());
                pst.setString(2, txt_tenkh.getText());
                pst.setString(3, txt_diachikh.getText());
                pst.setString(4, txt_sodt.getText());
                pst.setString(5, txt_email.getText());
                pst.setString(6, txt_cmtnd.getText());
                pst.setBoolean(7, gioitinh);
                pst.executeUpdate();

                JOptionPane.showMessageDialog(this, "Tạo tài khoản thành công!");

                load_list();

            } catch (Exception e4) {
                System.out.println("Lỗi của thêm" + e4);
            }

            // TODO add your handling code here:
        }
    }//GEN-LAST:event_btn_themActionPerformed

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
                    String sql2 = "Delete From khachhang where Mathekh = N'" + list.get(index).getMakh() + "'";
                    statement.executeUpdate(sql2);
                    load_list();
                    JOptionPane.showMessageDialog(this, "Xóa thành công!");
                    tbl_khachhang.setRowSelectionInterval(0, 0);
                } catch (SQLException e1) {
                    JOptionPane.showMessageDialog(this, "Không thể xóa khách hàng này");
                }
            }
        }
    }//GEN-LAST:event_btn_xoaActionPerformed

    private void btn_suaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_suaActionPerformed
        // TODO add your handling code here:
        boolean a = false;// TODO add your handling code here:
        if (index != -1 && txt()) {
            for (int i = 0; i < list.size(); i++) {
                if (txt_mathe.getText().equalsIgnoreCase(list.get(i).getMakh())) {
                    try {

                        Connection connection = DBUtils.getConnection();
                        String sql = "update khachhang set hovaten=?, diachi=?, dienthoai=?, email=?, cmtnd=?, gioitinh=? "
                                + " where mathekh=?";
                        PreparedStatement pst = connection.prepareStatement(sql);
                        pst.setString(1, txt_tenkh.getText());
                        pst.setString(2, txt_diachikh.getText());
                        pst.setString(3, txt_sodt.getText());
                        
                        pst.setString(4, txt_email.getText());
                        pst.setString(5, txt_cmtnd.getText());
                        pst.setBoolean(6, gioitinh);
                        pst.setString(7, txt_mathe.getText());
                        pst.executeUpdate();

                        JOptionPane.showMessageDialog(this, "Sửa thành công!");

                        load_list();
                        a = true;
                    } catch (Exception e4) {
                        System.out.println("Lỗi của sửa" + e4);
                    }
                }
            }
        }
    }//GEN-LAST:event_btn_suaActionPerformed

    private void btn_timActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_timActionPerformed
        // TODO add your handling code here:
        boolean find_check = false;
        try {
            String find_id = JOptionPane.showInputDialog(this, "Nhập mã khách hàng cần tìm");
            while (find_id.equals("")) {
                JOptionPane.showMessageDialog(this, "Không để trống mã!");
                find_id = JOptionPane.showInputDialog(this, "Nhập mã khách hàng cần tìm");
            }
            for (int i = 0; i < list.size(); i++) {
                if (find_id.equalsIgnoreCase(list.get(i).getMakh())) {

                    find_check = true;
                    tbl_khachhang.setRowSelectionInterval(i, i);
                }
            }

            if (!find_check) {
                JOptionPane.showMessageDialog(this, "Không có khách hàng này!");
            }

        } catch (NullPointerException e2) {
        }
    }//GEN-LAST:event_btn_timActionPerformed

    private void ck_namActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ck_namActionPerformed
        // TODO add your handling code here:
        ck_nu.setSelected(false);
    }//GEN-LAST:event_ck_namActionPerformed

    private void ck_nuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ck_nuActionPerformed
        // TODO add your handling code here:
        ck_nam.setSelected(false);
    }//GEN-LAST:event_ck_nuActionPerformed

    private void txt_matheActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_matheActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_matheActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_lm;
    private javax.swing.JButton btn_sua;
    private javax.swing.JButton btn_them;
    private javax.swing.JButton btn_tim;
    private javax.swing.JButton btn_xoa;
    private javax.swing.JRadioButton ck_nam;
    private javax.swing.JRadioButton ck_nu;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tbl_khachhang;
    private javax.swing.JTextField txt_cmtnd;
    private javax.swing.JTextField txt_diachikh;
    private javax.swing.JTextField txt_email;
    private javax.swing.JTextField txt_mathe;
    private javax.swing.JTextField txt_sodt;
    private javax.swing.JTextField txt_tenkh;
    // End of variables declaration//GEN-END:variables
}
