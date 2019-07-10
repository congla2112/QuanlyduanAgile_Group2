/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import controller.DBUtils;
import controller.h_Utils;
import java.awt.CardLayout;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import model.ChiTietDonHang;
import model.DonHang;

/**
 *
 * @author DELL
 */
public class P_donhang extends javax.swing.JPanel {

    /**
     * Creates new form P_donhang
     */
    int index = -1;
    List<DonHang> list = new ArrayList<>();
    DefaultTableModel model = new DefaultTableModel();
    String madh, mathekh, ngaymua, trangthai, manhanvien;

    List<ChiTietDonHang> list_ct = new ArrayList<>();
    DefaultTableModel model_ct = new DefaultTableModel();
    DefaultTableModel model_searchSP = new DefaultTableModel();

    String madh_ct, mathekh_CT, ngaymua_CT, trangthai_CT, manv;
    String masp, tensp;
    float giaban;
    String donvi;
    int soluong;

    int stt;

    boolean searchSP = false;

    public void setdata_ct() {
        try {
            model_ct.addColumn("Mã Đơn Hàng");
            model_ct.addColumn("Mã Thẻ KH");
            model_ct.addColumn("Ngày Mua");
            model_ct.addColumn("Trạng Thái");
            model_ct.addColumn("Mã Nhân Viên");
            model_ct.addColumn("Mã Sản Phẩm");
            model_ct.addColumn("Tên Sp");
            model_ct.addColumn("Giá Bán");
            model_ct.addColumn("Đơn Vị");
            model_ct.addColumn("Số Lượng");

            model_ct.addColumn("Số tt");

            model_searchSP.addColumn("Mã Sản Phẩm");
            model_searchSP.addColumn("Tên Sản Phẩm");
            model_searchSP.addColumn("Giá bán");
            model_searchSP.addColumn("Đơn Vị");
            tbl_donhangchitiet.setModel(model_ct);
            txt_madh.setEditable(false);
            txt_ngaymua.setEditable(false);
            txt_masanpham.setEditable(false);
            txt_tensanpham.setEditable(false);
            txt_stt.setEditable(false);
            txt_giaban.setEditable(false);
            load_list_ct();
            tbl_donhangchitiet.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
                @Override
                public void valueChanged(ListSelectionEvent lse) {
                    index = tbl_donhangchitiet.getSelectedRow();
                    if (!searchSP && index >= 0) {
                        txt_madh.setText(tbl_donhangchitiet.getValueAt(index, 0).toString());
                        txt_mathekh.setText(tbl_donhangchitiet.getValueAt(index, 1).toString());
                        txt_ngaymua.setText(tbl_donhangchitiet.getValueAt(index, 2).toString());

                        if (tbl_donhangchitiet.getValueAt(index, 3).toString().equalsIgnoreCase("Đã Trả Tiền")) {
                            cbb_trangthai.setSelectedIndex(0);
                        } else {
                            cbb_trangthai.setSelectedIndex(1);
                        }

                        txt_manhanvien.setText(tbl_donhangchitiet.getValueAt(index, 4).toString());
                        txt_masanpham.setText(tbl_donhangchitiet.getValueAt(index, 5).toString());
                        txt_tensanpham.setText(tbl_donhangchitiet.getValueAt(index, 6).toString());
                        txt_giaban.setText(tbl_donhangchitiet.getValueAt(index, 7).toString());

                        if (tbl_donhangchitiet.getValueAt(index, 8).toString().equalsIgnoreCase("Hộp")) {
                            cbb_donvi.setSelectedIndex(0);
                        } else if (tbl_donhangchitiet.getValueAt(index, 8).toString().equalsIgnoreCase("Thùng")) {
                            cbb_donvi.setSelectedIndex(1);
                        } else {
                            cbb_donvi.setSelectedIndex(2);
                        }
                        txt_soluong.setText(tbl_donhangchitiet.getValueAt(index, 9).toString());

                        txt_stt.setText(tbl_donhangchitiet.getValueAt(index, 10).toString());
                        tbl_donhangchitiet.scrollRectToVisible(tbl_donhangchitiet.getCellRect(index, 0, true));
                    }
                    if (searchSP) {
                        txt_masanpham.setText(tbl_donhangchitiet.getValueAt(index, 0).toString());
                        txt_tensanpham.setText(tbl_donhangchitiet.getValueAt(index, 1).toString());
                        txt_giaban.setText(tbl_donhangchitiet.getValueAt(index, 2).toString());
                        if (tbl_donhangchitiet.getValueAt(index, 3).toString().equalsIgnoreCase("Hộp")) {
                            cbb_donvi.setSelectedIndex(0);
                        } else if (tbl_donhangchitiet.getValueAt(index, 3).toString().equalsIgnoreCase("Thùng")) {
                            cbb_donvi.setSelectedIndex(1);
                        } else {
                            cbb_donvi.setSelectedIndex(2);
                        }
                        index = -1;
                    }

                }
            });
        } catch (NullPointerException e2) {
            System.out.println(e2);

        }
//        tlb_sanpham.setRowSelectionInterval(0, 0);
    }

    public void load_list_ct() {
        model_ct.setRowCount(0);
        try {
            Connection connection = DBUtils.getConnection();
            Statement statement = connection.createStatement();
            String sql = "select donhangchitiet.madh, mathekh, ngaymua, trangthai, manv, donhangchitiet.masp, sanpham.tensp, sanpham.giaban, sanpham.donvi, soluong, madhct from donhang"
                    + " inner join donhangchitiet on donhang.madh=donhangchitiet.madh"
                    + " inner join sanpham on sanpham.masp=donhangchitiet.masp"
                    + " order by ngaymua desc";
            ResultSet rs = statement.executeQuery(sql);
            list_ct.clear();
            while (rs.next()) {
                madh_ct = rs.getString(1);
                mathekh_CT = rs.getString(2);
                ngaymua_CT = rs.getString(3);
                trangthai_CT = rs.getString(4);
                manv = rs.getString(5);
                masp = rs.getString(6);
                tensp = rs.getString(7);
                giaban = rs.getFloat(8);
                donvi = rs.getString(9);
                soluong = rs.getInt(10);
                stt = rs.getInt(11);

                list_ct.add(new ChiTietDonHang(madh_ct, mathekh_CT, ngaymua_CT, trangthai_CT, manv, masp, tensp, giaban, donvi, soluong, stt));
                Vector row = new Vector();
                row.add(madh_ct);
                row.add(mathekh_CT);
                row.add(ngaymua_CT);
                row.add(trangthai_CT);
                row.add(manv);
                row.add(masp);
                row.add(tensp);
                row.add(String.format("%.0f", giaban));
                row.add(donvi);
                row.add(soluong);
                row.add(stt);

                model_ct.addRow(row);
            }

        } catch (SQLException e1) {
            System.out.println("Loadlist: loi cua loadlist " + e1);
        }
    }

    public boolean txt() {
        try {
            if (txt_masanpham.getText().equals("")) {
                JOptionPane.showMessageDialog(this, "Không được để trống mã sản phẩm");
                return false;
            }
            if (txt_soluong.getText().equals("")) {
                JOptionPane.showMessageDialog(this, "Không được để trống số lượng");
                return false;
            }

            if (!txt_soluong.getText().matches("\\d+")) {
                JOptionPane.showMessageDialog(this, "Bạn phải nhập số ở số lượng");
                return false;
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi" + e);
            return false;
        }
        return true;
    }

    public void load_list() {
        model.setRowCount(0);
        try {
            Connection connection = DBUtils.getConnection();
            Statement statement = connection.createStatement();
            String sql = "select * from donhang"
                    + " order by ngaymua desc";
            ResultSet rs = statement.executeQuery(sql);
            list.clear();
            while (rs.next()) {
                madh = rs.getString(1);
                mathekh = rs.getString(2);
                ngaymua = rs.getString(3);
                trangthai = rs.getString(4);
                manhanvien = rs.getString(5);

                list.add(new DonHang(madh, mathekh, ngaymua, trangthai, manhanvien));
                Vector row = new Vector();
                row.add(madh);
                row.add(mathekh);
                row.add(ngaymua);
                row.add(trangthai);
                row.add(manhanvien);

                model.addRow(row);
            }

        } catch (SQLException e1) {
            System.out.println("Loadlist: " + e1);
        }
    }

    public P_donhang() {
        initComponents();
        setdata_ct();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLayeredPane1 = new javax.swing.JLayeredPane();
        h_Utils1 = new controller.h_Utils();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        txt_madh = new javax.swing.JTextField();
        txt_mathekh = new javax.swing.JTextField();
        txt_ngaymua = new javax.swing.JTextField();
        cbb_trangthai = new javax.swing.JComboBox();
        txt_manhanvien = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        txt_masanpham = new javax.swing.JTextField();
        btn_timsanpham = new javax.swing.JButton();
        txt_tensanpham = new javax.swing.JTextField();
        txt_giaban = new javax.swing.JTextField();
        cbb_donvi = new javax.swing.JComboBox();
        txt_soluong = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbl_donhangchitiet = new javax.swing.JTable();
        btn_themsanpham = new javax.swing.JButton();
        btn_timdonhang = new javax.swing.JButton();
        btn_xoadonhang = new javax.swing.JButton();
        btn_lammoisp = new javax.swing.JButton();
        btn_lammoidh = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jLabel12 = new javax.swing.JLabel();
        txt_stt = new javax.swing.JTextField();
        jButton2 = new javax.swing.JButton();

        javax.swing.GroupLayout jLayeredPane1Layout = new javax.swing.GroupLayout(jLayeredPane1);
        jLayeredPane1.setLayout(jLayeredPane1Layout);
        jLayeredPane1Layout.setHorizontalGroup(
            jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jLayeredPane1Layout.setVerticalGroup(
            jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        setBackground(new java.awt.Color(255, 255, 204));
        setMaximumSize(new java.awt.Dimension(915, 540));
        setMinimumSize(new java.awt.Dimension(915, 540));
        setPreferredSize(new java.awt.Dimension(915, 540));

        jPanel1.setBackground(new java.awt.Color(0, 153, 51));
        jPanel1.setMaximumSize(new java.awt.Dimension(915, 35));
        jPanel1.setMinimumSize(new java.awt.Dimension(915, 35));
        jPanel1.setPreferredSize(new java.awt.Dimension(915, 35));

        jLabel2.setFont(new java.awt.Font("Arial", 1, 16)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("XUẤT BÁN LẺ");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(373, 373, 373)
                .addComponent(jLabel2)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE)
        );

        jLabel1.setText("Mã Đơn Hàng:");

        jLabel3.setText("Mã Thẻ KH:");

        jLabel4.setText("Ngày Mua:");

        jLabel5.setText("Trạng Thái:");

        jLabel11.setText("Mã Nhân Viên:");

        cbb_trangthai.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Đã Trả Tiền", "Chưa Trả Tiền", " " }));

        jLabel6.setText("Mã Sản Phẩm:");

        jLabel7.setText("Tên Sản Phẩm:");

        jLabel8.setText("Giá Bán:");

        jLabel9.setText("Đơn Vị:");

        jLabel10.setText("Số Lượng:");

        btn_timsanpham.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/Search-icon.png"))); // NOI18N
        btn_timsanpham.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_timsanphamActionPerformed(evt);
            }
        });

        cbb_donvi.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Hộp", "Thùng", "Chai" }));

        tbl_donhangchitiet.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane2.setViewportView(tbl_donhangchitiet);

        btn_themsanpham.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/Add-Folder-icon.png"))); // NOI18N
        btn_themsanpham.setText("Thêm Sản Phẩm");
        btn_themsanpham.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_themsanphamActionPerformed(evt);
            }
        });

        btn_timdonhang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/Search-icon.png"))); // NOI18N
        btn_timdonhang.setText("Tìm Đơn Hàng");
        btn_timdonhang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_timdonhangActionPerformed(evt);
            }
        });

        btn_xoadonhang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/Actions-dialog-close-icon.png"))); // NOI18N
        btn_xoadonhang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_xoadonhangActionPerformed(evt);
            }
        });

        btn_lammoisp.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/New-icon (1).png"))); // NOI18N
        btn_lammoisp.setText("Làm Mới SP");
        btn_lammoisp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_lammoispActionPerformed(evt);
            }
        });

        btn_lammoidh.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/new-icon.png"))); // NOI18N
        btn_lammoidh.setText("Làm Mới ĐH");
        btn_lammoidh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_lammoidhActionPerformed(evt);
            }
        });

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/system-software-update-icon.png"))); // NOI18N
        jButton1.setText("Sửa Đơn Hàng");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel12.setText("STT:");

        jButton2.setText("Xóa SP");
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
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(63, 63, 63)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txt_mathekh)
                            .addComponent(txt_ngaymua)
                            .addComponent(cbb_trangthai, 0, 175, Short.MAX_VALUE)
                            .addComponent(txt_madh)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel11)
                        .addGap(18, 18, 18)
                        .addComponent(txt_manhanvien)))
                .addGap(51, 51, 51)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel6)
                    .addComponent(jLabel7)
                    .addComponent(jLabel8)
                    .addComponent(jLabel9)
                    .addComponent(jLabel10)
                    .addComponent(jLabel12))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(txt_tensanpham)
                        .addComponent(txt_giaban)
                        .addComponent(txt_soluong)
                        .addComponent(cbb_donvi, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(txt_masanpham, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(btn_timsanpham, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addComponent(txt_stt, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(62, 62, 62)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btn_lammoidh, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btn_lammoisp, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btn_timdonhang, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btn_themsanpham, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btn_xoadonhang)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap(74, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txt_madh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_themsanpham)
                    .addComponent(jLabel12)
                    .addComponent(txt_stt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txt_mathekh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_timdonhang)
                    .addComponent(jLabel6)
                    .addComponent(txt_masanpham, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_timsanpham))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel4)
                        .addComponent(txt_ngaymua, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btn_xoadonhang)
                        .addComponent(jLabel7)
                        .addComponent(txt_tensanpham, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(cbb_trangthai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_lammoisp)
                    .addComponent(txt_giaban, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_manhanvien, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11)
                    .addComponent(btn_lammoidh)
                    .addComponent(jLabel9)
                    .addComponent(cbb_donvi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jLabel10)
                    .addComponent(txt_soluong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 39, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btn_timsanphamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_timsanphamActionPerformed
        // TODO add your handling code here:
        txt_soluong.setText("");
        try {
            String find_id = JOptionPane.showInputDialog(this, "Nhập mã sản phẩm cần tìm");
            boolean find_check = false;
            while (find_id.equals("")) {
                JOptionPane.showMessageDialog(this, "Không để trống mã!");
                find_id = JOptionPane.showInputDialog(this, "Nhập mã sản phẩm cần tìm");
            }

            try {
                model_searchSP.setRowCount(0);
                Connection connection = DBUtils.getConnection();
                String sql = "select masp, tensp, giaban, donvi from sanpham where Masp = ?";
                PreparedStatement pst = connection.prepareStatement(sql);
                pst.setString(1, find_id);
                ResultSet rs = pst.executeQuery();
                while (rs.next()) {
                    Vector row = new Vector();
                    find_check = true;
                    row.add(rs.getString(1));
                    row.add(rs.getString(2));
                    row.add(rs.getString(3));
                    row.add(rs.getString(4));
                    model_searchSP.addRow(row);
                }

                tbl_donhangchitiet.setModel(model_searchSP);
                searchSP = true;

                if (!find_check) {
                    JOptionPane.showMessageDialog(this, "Không có sản phẩm này!");
                }

            } catch (SQLException ex) {
                System.out.println(ex);
            }
        } catch (NullPointerException e) {
        }
    }//GEN-LAST:event_btn_timsanphamActionPerformed

    private void btn_lammoispActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_lammoispActionPerformed
        // TODO add your handling code here:
        txt_madh.setEditable(false);

        txt_ngaymua.setEditable(false);

        txt_masanpham.setEditable(false);
        txt_tensanpham.setEditable(false);
        txt_giaban.setEditable(false);
        txt_masanpham.setText("");
        txt_tensanpham.setText("");
        txt_giaban.setText("");
        txt_soluong.setText("");
    }//GEN-LAST:event_btn_lammoispActionPerformed

    private void btn_lammoidhActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_lammoidhActionPerformed
        // TODO add your handling code here:
        new h_Utils().set_dbConfig("QLBH,sa,123");
        try {

            Connection connection = DBUtils.getConnection();
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("select top 1 MaDh from Donhang order by MaDh desc");
            rs.next();
            String MaPhieu = "" + (Integer.parseInt(rs.getString(1).substring(rs.getString(1).length() - 3)) + 1);
            while (MaPhieu.length() < 3) {
                MaPhieu = "0" + MaPhieu;
            }
            
            txt_madh.setText("DH" + MaPhieu);
            txt_mathekh.setText("TUDO");
            Calendar cal = Calendar.getInstance();
            txt_ngaymua.setText( (cal.get(Calendar.MONTH) + 1) + "/" + cal.get(Calendar.DAY_OF_MONTH) + "/" + cal.get(Calendar.YEAR));
            txt_manhanvien.setText("nv1");
            txt_masanpham.setText("");
            txt_tensanpham.setText("");
            txt_giaban.setText("");
            txt_soluong.setText("");

            String sql = "insert into donhang values(?,?,?,?,?)";
            PreparedStatement pst1 = connection.prepareStatement(sql);
            pst1.setString(1, txt_madh.getText());
            pst1.setString(2, txt_mathekh.getText());
            pst1.setString(3, txt_ngaymua.getText());
            pst1.setString(4, cbb_trangthai.getSelectedItem().toString());
            pst1.setString(5, txt_manhanvien.getText());

            pst1.executeUpdate();

            JOptionPane.showMessageDialog(this, "Tạo Đơn Hàng Thành Công! Mời Nhập Mặt Hàng");
            load_list_ct();

        } catch (Exception e) {
        }

    }//GEN-LAST:event_btn_lammoidhActionPerformed

    private void btn_themsanphamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_themsanphamActionPerformed
        // TODO add your handling code here:
        if (txt()) {
            try {

                Connection connection = DBUtils.getConnection();

                String sql_dh = "update donhang set mathekh=?, ngaymua=?, trangthai=?, manv=?"
                        + " where madh=?";
                PreparedStatement pst1_dh = connection.prepareStatement(sql_dh);
                pst1_dh.setString(1, txt_mathekh.getText());
                pst1_dh.setString(2, txt_ngaymua.getText());
                pst1_dh.setString(3, cbb_trangthai.getSelectedItem().toString());
                pst1_dh.setString(4, txt_manhanvien.getText());
                pst1_dh.setString(5, txt_madh.getText());

                pst1_dh.executeUpdate();

                String sql = "insert into donhangchitiet values(?,?,?)";
                PreparedStatement pst1 = connection.prepareStatement(sql);
                pst1.setString(1, txt_masanpham.getText());
                pst1.setString(2, txt_soluong.getText());
                pst1.setString(3, txt_madh.getText());

                pst1.executeUpdate();

                JOptionPane.showMessageDialog(this, "Lưu thành công!");

                load_list_ct();
                searchSP = false;
                tbl_donhangchitiet.setModel(model_ct);
            } catch (Exception e4) {
                System.out.println("Lỗi cua thêm" + e4);
            }
        }
    }//GEN-LAST:event_btn_themsanphamActionPerformed

    private void btn_timdonhangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_timdonhangActionPerformed
        // TODO add your handling code here:

        boolean find_check = false;
        try {
            String find_id = JOptionPane.showInputDialog(this, "Nhập mã đon hàng muốn tìm kiếm");
            while (find_id.equals("")) {
                JOptionPane.showMessageDialog(this, "Không để trống mã đơn hàng!");
                find_id = JOptionPane.showInputDialog(this, "Nhập mã đơn hang muốn tìm kiếm");
            }
            for (int i = 0; i < list_ct.size(); i++) {
                if (find_id.equalsIgnoreCase(list_ct.get(i).getMadh_ct())) {

                    find_check = true;
                    tbl_donhangchitiet.setRowSelectionInterval(i, i);
                }
            }

            if (!find_check) {
                JOptionPane.showMessageDialog(this, "Mã đơn nhập không có trong dữ liệu!");
            }

        } catch (NullPointerException e2) {
        }
    }//GEN-LAST:event_btn_timdonhangActionPerformed

    private void btn_xoadonhangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_xoadonhangActionPerformed
        // TODO add your handling code here:
        if (index != -1) {
            int ret = JOptionPane.showConfirmDialog(this, "Xác nhận xóa đơn hàng này?",
                    "Confirm", JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE);
            String tt = tbl_donhangchitiet.getValueAt(index, 3).toString();
            if (ret == JOptionPane.YES_OPTION && tt.equalsIgnoreCase("Chưa trả tiền")) {
                try {
                    String madh = tbl_donhangchitiet.getValueAt(index, 0).toString();
                    Connection connection = DBUtils.getConnection();
                    Statement statement = connection.createStatement();
                    String sql2 = "Delete from DonHangChiTiet where MaDh='" + madh + "' Delete From Donhang where MaDh='" + madh + "'";
                    statement.executeUpdate(sql2);
                    load_list_ct();
                    JOptionPane.showMessageDialog(this, "Xóa thành công!");
                    tbl_donhangchitiet.setRowSelectionInterval(0, 0);
                } catch (SQLException e1) {
                    JOptionPane.showMessageDialog(this, "Lỗi");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Không thể xóa đơn hàng này");
            }
        }

    }//GEN-LAST:event_btn_xoadonhangActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:

        try {

            Connection connection = DBUtils.getConnection();

            String sql_dh = "update donhang set mathekh=?, ngaymua=?, trangthai=?, manv=?"
                    + " where madh=?";
            PreparedStatement pst1_dh = connection.prepareStatement(sql_dh);
            pst1_dh.setString(1, txt_mathekh.getText());
            pst1_dh.setString(2, txt_ngaymua.getText());
            pst1_dh.setString(3, cbb_trangthai.getSelectedItem().toString());
            pst1_dh.setString(4, txt_manhanvien.getText());
            pst1_dh.setString(5, txt_madh.getText());

            pst1_dh.executeUpdate();
            if (txt()) {
                String sql = "update donhangchitiet set masp=?, soluong=?"
                        + " where madhct=?";
                PreparedStatement pst1 = connection.prepareStatement(sql);
                pst1.setString(1, txt_masanpham.getText());
                pst1.setString(2, txt_soluong.getText());
                pst1.setString(3, txt_stt.getText());

                pst1.executeUpdate();

                JOptionPane.showMessageDialog(this, "Lưu thành công!");

                load_list_ct();
                searchSP = false;
                tbl_donhangchitiet.setModel(model_ct);
            }
        } catch (Exception e4) {
            JOptionPane.showMessageDialog(this, "Khách hàng chưa đăng ký tài khoản!");
        }


    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        if (index != -1) {
            int ret = JOptionPane.showConfirmDialog(this, "Xác nhận xóa sản phẩm này?",
                    "Confirm", JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE);
            String tt = tbl_donhangchitiet.getValueAt(index, 3).toString();
            if (ret == JOptionPane.YES_OPTION && tt.equalsIgnoreCase("Chưa trả tiền")) {
                try {
                    String madh = tbl_donhangchitiet.getValueAt(index, 0).toString();
                    Connection connection = DBUtils.getConnection();
                    Statement statement = connection.createStatement();
                    String sql2 = "Delete From donhangchitiet where Madhct = N'" + list_ct.get(index).getStt() + "'";
                    statement.executeUpdate(sql2);
                    load_list_ct();
                    JOptionPane.showMessageDialog(this, "Xóa sản phẩm thành công!");
                    tbl_donhangchitiet.setRowSelectionInterval(0, 0);
                } catch (SQLException e1) {
                    JOptionPane.showMessageDialog(this, "Lỗi");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Không thể xóa sản phẩm này");
            }
        }
    }//GEN-LAST:event_jButton2ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_lammoidh;
    private javax.swing.JButton btn_lammoisp;
    private javax.swing.JButton btn_themsanpham;
    private javax.swing.JButton btn_timdonhang;
    private javax.swing.JButton btn_timsanpham;
    private javax.swing.JButton btn_xoadonhang;
    private javax.swing.JComboBox cbb_donvi;
    private javax.swing.JComboBox cbb_trangthai;
    private controller.h_Utils h_Utils1;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLayeredPane jLayeredPane1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable tbl_donhangchitiet;
    private javax.swing.JTextField txt_giaban;
    private javax.swing.JTextField txt_madh;
    private javax.swing.JTextField txt_manhanvien;
    private javax.swing.JTextField txt_masanpham;
    private javax.swing.JTextField txt_mathekh;
    private javax.swing.JTextField txt_ngaymua;
    private javax.swing.JTextField txt_soluong;
    private javax.swing.JTextField txt_stt;
    private javax.swing.JTextField txt_tensanpham;
    // End of variables declaration//GEN-END:variables

}
