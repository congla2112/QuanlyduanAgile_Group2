/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import controller.DBUtils;
import controller.h_Utils;
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
import model.Sanpham;

/**
 *
 * @author trung
 */
public class P_sanpham extends javax.swing.JPanel {
   int index = -1;
    int current = 0;
    int mucTonKho = 20;
    List<Sanpham> list = new ArrayList<>();
    DefaultTableModel model = new DefaultTableModel();

    String masp, tensp, donvi, hangsp, chuthich;
    float gianhap, giaban;
    int tonkho,soluongnhap;
    /**
     * Creates new form NewJPanel
     */
    public P_sanpham() {
        initComponents();
        
        setdata();
    }
     public void setdata() {
        try {
            model.addColumn("Mã Sản Phẩm");
            model.addColumn("Tên Sản Phẩm");
            model.addColumn("Loại Sản Phẩm");
            model.addColumn("Giá Nhập");
            model.addColumn("Giá Bán");
            model.addColumn("Hãng Sản Xuất");
            model.addColumn("Tồn Kho");
            model.addColumn("Chú Thích");
  
            tlb_sanpham.setModel(model);
            load_list();
            tlb_sanpham.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
                @Override
                public void valueChanged(ListSelectionEvent lse) {
                    index = tlb_sanpham.getSelectedRow();
                    if (index >= 0) {
                        txt_masp.setText(tlb_sanpham.getValueAt(index, 0).toString());
                        txt_tensp.setText(tlb_sanpham.getValueAt(index, 1).toString());

                        if (tlb_sanpham.getValueAt(index, 2).toString().equalsIgnoreCase("Thùng")) {
                            cb_donvi.setSelectedIndex(0);
                        } else if (tlb_sanpham.getValueAt(index, 2).toString().equalsIgnoreCase("Hộp")) {
                            cb_donvi.setSelectedIndex(1);
                        } else {
                            cb_donvi.setSelectedIndex(2);
                        }

                        txt_gianhap.setText(tlb_sanpham.getValueAt(index, 3).toString());
                        txt_giaban.setText(tlb_sanpham.getValueAt(index, 4).toString());

                        if (tlb_sanpham.getValueAt(index, 5).toString().equalsIgnoreCase("Vinamilk-CN")) {
                            cb_hang.setSelectedIndex(0);
                        } else {
                            cb_hang.setSelectedIndex(1);
                        }

                       
                        if(tlb_sanpham.getValueAt(index, 7)!=null){
                            txt_chuthich.setText(tlb_sanpham.getValueAt(index, 7).toString());
                        } else {
                            txt_chuthich.setText("");
                        }                     

                        tlb_sanpham.scrollRectToVisible(tlb_sanpham.getCellRect(index, 0, true));

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
            String sql = " select SanPham.Masp,Tensp,donvi,gianhap,giaban,hangsp,soluongnhapkho,chuthich,soluongnhapkho-sum (soluong) as tonkho\n" +
"from SanPham full join DonHangChiTiet on SanPham.Masp=DonHangChiTiet.Masp\n" +
"group by SanPham.Masp,Tensp,donvi,gianhap,giaban,hangsp,soluongnhapkho,chuthich\n" +
"order by soluongnhapkho-sum(soluong)";
            ResultSet rs = statement.executeQuery(sql);
            list.clear();
            while (rs.next()) {
                masp = rs.getString(1);
                tensp = rs.getString(2);
                donvi = rs.getString(3);
                gianhap = rs.getFloat(4);
                giaban= rs.getFloat(5);
                hangsp = rs.getString(6);
                soluongnhap = rs.getInt(7);
                chuthich = rs.getString(8);
                tonkho=rs.getInt(9);
                list.add(new Sanpham(masp, tensp, donvi, hangsp, chuthich, gianhap, giaban, soluongnhap, tonkho));
                Vector row = new Vector();
                row.add(masp);
                row.add(tensp);
                row.add(donvi);
                row.add(String.format("%.0f", gianhap));
                row.add(String.format("%.0f", giaban));
                row.add(hangsp);
                if(tonkho==0){
                    tonkho=soluongnhap;
                }  
                row.add(tonkho);
                row.add(chuthich);
                model.addRow(row);
            }

        } catch (SQLException e1) {
            System.out.println("Loadlist: " + e1);
        }
    }
 public boolean txt() {
        try {
            if (txt_masp.getText().equals("") || txt_tensp.getText().equals("")
                    || txt_gianhap.getText().equals("") || txt_giaban.getText().equals("")
                  ||txt_soluongnhap.getText().equals("")  ) {
                JOptionPane.showMessageDialog(this, "Không được để trống trường dữ liệu");
                return false;
            }
            masp = txt_masp.getText().trim();
            tensp = txt_tensp.getText().trim();
            if(!masp.matches("\\d+")){
                JOptionPane.showMessageDialog(this, "Mã sản phẩm phải nhập số");
                return false;
            }
            
            gianhap = Float.valueOf(txt_gianhap.getText().trim());
            giaban = Float.valueOf(txt_giaban.getText().trim());    
            if(giaban<gianhap){
                JOptionPane.showMessageDialog(this, "Giá Bán phải lớn hơn giá Nhập");
                return  false;
            }
            if(gianhap<0){
                JOptionPane.showMessageDialog(this, "Giá Nhập không phải số âm");
                return  false;
            }
            
            if(giaban<0){
                JOptionPane.showMessageDialog(this, "Giá Bán không phải số âm");
                return  false;
            }
            
            
            if (!txt_soluongnhap.getText().matches("\\d+")) {
                JOptionPane.showMessageDialog(this, "Số lượng nhập phải là số");
                return false;
            }
             chuthich = txt_chuthich.getText().trim();
             
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi dữ liệu! Mời nhập lại");
            return false;
        }
        return true;
    }
 public boolean txtsua() {
        try {
            if (txt_masp.getText().equals("") || txt_tensp.getText().equals("")
                    || txt_gianhap.getText().equals("") || txt_giaban.getText().equals("")
                  ||txt_soluongnhap.getText().equals("")  ) {
                JOptionPane.showMessageDialog(this, "Không được để trống trường dữ liệu");
                return false;
            }
            masp = txt_masp.getText().trim();
            tensp = txt_tensp.getText().trim();
            
            
            gianhap = Float.valueOf(txt_gianhap.getText().trim());
            giaban = Float.valueOf(txt_giaban.getText().trim());    
            if(giaban<gianhap){
                JOptionPane.showMessageDialog(this, "Giá Bán phải lớn hơn giá Nhập");
                return  false;
            }
            if(gianhap<0){
                JOptionPane.showMessageDialog(this, "Giá Nhập không phải số âm");
                return  false;
            }
            
            if(giaban<0){
                JOptionPane.showMessageDialog(this, "Giá Bán không phải số âm");
                return  false;
            }
            
            
            if (!txt_soluongnhap.getText().matches("\\d+")) {
                JOptionPane.showMessageDialog(this, "Số lượng nhập phải là số");
                return false;
            }
             chuthich = txt_chuthich.getText().trim();
             
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi dữ liệu! Mời nhập lại");
            return false;
        }
        return true;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        txt_soluongnhap = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tlb_sanpham = new javax.swing.JTable();
        txt_masp = new javax.swing.JTextField();
        txt_tensp = new javax.swing.JTextField();
        txt_gianhap = new javax.swing.JTextField();
        cb_donvi = new javax.swing.JComboBox<String>();
        jLabel7 = new javax.swing.JLabel();
        txt_giaban = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        cb_hang = new javax.swing.JComboBox<String>();
        jLabel3 = new javax.swing.JLabel();
        txt_chuthich = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        btn_sua = new javax.swing.JButton();
        btn_xoa = new javax.swing.JButton();
        btn_tim = new javax.swing.JButton();
        btn_lammoi = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();

        setBackground(new java.awt.Color(255, 255, 204));
        setMaximumSize(new java.awt.Dimension(915, 40));
        setMinimumSize(new java.awt.Dimension(915, 540));
        setPreferredSize(new java.awt.Dimension(915, 540));

        txt_soluongnhap.setText("0");

        jLabel6.setText("Hãng sản xuất:");

        tlb_sanpham.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(tlb_sanpham);

        cb_donvi.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Thùng", "Hộp", "Chai" }));

        jLabel7.setText("Giá bán:");

        jLabel2.setText("Mã sản phẩm:");

        jLabel8.setText("Số lượng nhập:");

        cb_hang.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Vinamilk-CN", "Vinamilk-CT" }));

        jLabel3.setText("Tên sản phẩm:");

        jLabel4.setText("Đơn vị:");

        jLabel9.setText("Chú thích: ");

        jLabel5.setText("Giá nhập:");

        jPanel1.setBackground(new java.awt.Color(0, 153, 51));
        jPanel1.setMaximumSize(new java.awt.Dimension(915, 35));
        jPanel1.setMinimumSize(new java.awt.Dimension(915, 35));
        jPanel1.setPreferredSize(new java.awt.Dimension(915, 35));

        jLabel1.setFont(new java.awt.Font("Arial", 1, 16)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("SẢN PHẨM");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(402, 402, 402)
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

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/Add-Folder-icon.png"))); // NOI18N
        jButton1.setText("Thêm Sản Phẩm");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        btn_sua.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/edit-gnote-icon.png"))); // NOI18N
        btn_sua.setText("Sửa Sản Phẩm");
        btn_sua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_suaActionPerformed(evt);
            }
        });

        btn_xoa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/Actions-dialog-close-icon.png"))); // NOI18N
        btn_xoa.setText("Xóa Sản Phẩm");
        btn_xoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_xoaActionPerformed(evt);
            }
        });

        btn_tim.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/Search-icon.png"))); // NOI18N
        btn_tim.setText("Tìm Kiếm");
        btn_tim.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_timActionPerformed(evt);
            }
        });

        btn_lammoi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/new-icon.png"))); // NOI18N
        btn_lammoi.setText("Làm Mới");
        btn_lammoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_lammoiActionPerformed(evt);
            }
        });

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/system-software-update-icon.png"))); // NOI18N
        jButton2.setText("Cập nhật");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
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
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(47, 47, 47)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(cb_donvi, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txt_tensp, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txt_masp, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txt_gianhap, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(35, 35, 35)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel7)
                    .addComponent(jLabel6)
                    .addComponent(jLabel8)
                    .addComponent(jLabel9))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txt_giaban)
                    .addComponent(cb_hang, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txt_soluongnhap)
                    .addComponent(txt_chuthich, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 60, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btn_lammoi, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btn_sua, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btn_xoa, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btn_tim, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(55, 55, 55))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_masp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_giaban, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7)
                    .addComponent(jLabel2)
                    .addComponent(jButton1))
                .addGap(9, 9, 9)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_tensp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel6)
                    .addComponent(cb_hang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_sua))
                .addGap(11, 11, 11)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jLabel8)
                    .addComponent(txt_soluongnhap, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cb_donvi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_xoa))
                .addGap(13, 13, 13)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txt_gianhap, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_chuthich, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9)
                    .addComponent(btn_tim))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btn_lammoi)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 18, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        if (txt()) {

            try {

               

                Connection connection = DBUtils.getConnection();
                String sql = "insert into SanPham values(?,?,?,?,?,?,?,?)";
                PreparedStatement pst1 = connection.prepareStatement(sql);
                pst1.setString(1, "SP"+txt_masp.getText());
                pst1.setString(2, txt_tensp.getText());
                pst1.setString(3, cb_donvi.getSelectedItem().toString());
                pst1.setString(4, txt_gianhap.getText());
                pst1.setString(5, txt_giaban.getText());
                pst1.setString(6, cb_hang.getSelectedItem().toString());
                pst1.setString(7, txt_soluongnhap.getText());
                pst1.setString(8, txt_chuthich.getText());
                pst1.executeUpdate();

                JOptionPane.showMessageDialog(this, "Lưu thành công!");

                load_list();

            } catch(SQLException e5){
                JOptionPane.showMessageDialog(this,"Mã sản phẩm đã có vui lòng thử mã sản phẩm khác");
            }
                    

            // TODO add your handling code here:
        }  
    }//GEN-LAST:event_jButton1ActionPerformed

    private void btn_suaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_suaActionPerformed
        // TODO add your handling code here:
        if (index != -1 && txtsua()) {
            for (int i = 0; i < list.size(); i++) {
                if (txt_masp.getText().equalsIgnoreCase(list.get(i).getMasp())) {
                    try {

                        Connection connection = DBUtils.getConnection();
                        String sql = "update sanpham set tensp=?,donvi=?, gianhap=?, giaban=?, hangsp=?, soluongnhapkho=?, chuthich=?"
                        + " where masp=?";
                        PreparedStatement pst1 = connection.prepareStatement(sql);
                        pst1.setString(1, txt_tensp.getText());
                        pst1.setString(2,cb_donvi.getSelectedItem().toString());
                        pst1.setString(3, txt_gianhap.getText());
                        pst1.setString(4, txt_giaban.getText());
                        pst1.setString(5, cb_hang.getSelectedItem().toString());
                        pst1.setInt(6, (Integer.parseInt(txt_soluongnhap.getText())+list.get(i).getSoluongnhapkho()));
                        pst1.setString(7, txt_chuthich.getText());
                        pst1.setString(8, txt_masp.getText());

                        pst1.executeUpdate();

                        JOptionPane.showMessageDialog(this, "Lưu thành công!");

                        load_list();
                        
                    } catch (Exception e4) {
                        System.out.println("Lỗi" + e4);
                    }
                }
            }
        }
    }//GEN-LAST:event_btn_suaActionPerformed

    private void btn_xoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_xoaActionPerformed
        // TODO add your handling code here:
        if (index != -1) {
            int ret = JOptionPane.showConfirmDialog(this, "Xác nhận xóa?", "Confirm", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

            if (ret == JOptionPane.YES_OPTION) {
                try {
                    Connection connection = DBUtils.getConnection();
                    Statement statement = connection.createStatement();
                    String sql2 = "Delete From SanPham where Masp = N'" + list.get(index).getMasp() + "'";
                    statement.executeUpdate(sql2);
                    load_list();
                    JOptionPane.showMessageDialog(this, "Xóa thành công!");
                    tlb_sanpham.setRowSelectionInterval(0, 0);
                } catch (SQLException e1) {
                    JOptionPane.showMessageDialog(this, "Sản phẩm đã bán ra!! không thể xóa!!!");
                }
            }
        }
    }//GEN-LAST:event_btn_xoaActionPerformed

    private void btn_timActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_timActionPerformed
        // TODO add your handling code here:
        boolean find_check = false;
        try {
            String find_id = JOptionPane.showInputDialog(this, "Nhập mã hoặc tên sản phẩm cần tìm");
            while (find_id.equals("")) {
                JOptionPane.showMessageDialog(this, "Không để trống !");
                find_id = JOptionPane.showInputDialog(this, "Nhập mã hoặc tên sản phẩm cần tìm");
            }
            for (int i = 0; i < list.size(); i++) {
                if (find_id.equalsIgnoreCase(list.get(i).getMasp())) {

                    find_check = true;
                    tlb_sanpham.setRowSelectionInterval(i, i);
                }else if(find_id.equalsIgnoreCase(list.get(i).getTensp())){
                    tlb_sanpham.setRowSelectionInterval(i, i);
                    find_check=true;
                }
            }

            if (!find_check) {
                JOptionPane.showMessageDialog(this, "Không có sản phẩm này!");
            }

        } catch (NullPointerException e2) {
        } 
    }//GEN-LAST:event_btn_timActionPerformed

    private void btn_lammoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_lammoiActionPerformed
        // TODO add your handling code here:
        txt_masp.setText("");
        txt_masp.setEditable(true);
        txt_tensp.setText("");
        txt_gianhap.setText("");
        txt_giaban.setText("");
        txt_soluongnhap.setText("");
        txt_chuthich.setText("");  
    }//GEN-LAST:event_btn_lammoiActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
load_list();    // TODO add your handling code here:
    }//GEN-LAST:event_jButton2ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_lammoi;
    private javax.swing.JButton btn_sua;
    private javax.swing.JButton btn_tim;
    private javax.swing.JButton btn_xoa;
    private javax.swing.JComboBox<String> cb_donvi;
    private javax.swing.JComboBox<String> cb_hang;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
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
    private javax.swing.JTable tlb_sanpham;
    private javax.swing.JTextField txt_chuthich;
    private javax.swing.JTextField txt_giaban;
    private javax.swing.JTextField txt_gianhap;
    private javax.swing.JTextField txt_masp;
    private javax.swing.JTextField txt_soluongnhap;
    private javax.swing.JTextField txt_tensp;
    // End of variables declaration//GEN-END:variables
}
