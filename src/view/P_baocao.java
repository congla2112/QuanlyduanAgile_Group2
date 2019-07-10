/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import controller.DBUtils;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import model.Khachhang;
import model.Nhanvien;
import model.Sanpham;
import model.khbaocao;
import model.spbaocao;

/**
 *
 * @author DELL
 */
public class P_baocao extends javax.swing.JPanel {

    /**
     * Creates new form P_baocao
     */
    String manv, tennv, sodienthoai, diachi, email, username, pass, rule;
    boolean gioitinh;
    String masp, tensp, donvi, hangsp, chuthich;
    float gianhap, giaban;
    int tonkho, soluongnhap;
    String makh, tenkh, diachi1, sodienthoai1, email1, cmt;
    boolean gioitinh1;
    String masp1, tensp1;
    int sumsp;
    String makh1, tenkh1;
    int sumkh;
    List<Nhanvien> listnv = new ArrayList<>();
    DefaultTableModel model = new DefaultTableModel();
    DefaultTableModel model1 = new DefaultTableModel();
    DefaultTableModel model2 = new DefaultTableModel();
    List<Sanpham> listsp = new ArrayList<>();
    List<Sanpham> listsp1 = new ArrayList<>();
    List<Khachhang> listkh = new ArrayList<>();
    List<spbaocao> listspb = new ArrayList<>();
    List<khbaocao> listkhb = new ArrayList<>();

    public P_baocao() {
        initComponents();
        listnv();
        listsp();
        listkh();
        init();
        cot();
    }

    public void cot() {
        model.addColumn("Mã Sản Phẩm");
        model.addColumn("Tên Sản Phẩm");
        model.addColumn("Loại Sản Phẩm");
        model.addColumn("Giá Nhập");
        model.addColumn("Giá Bán");
        model.addColumn("Hãng Sản Xuất");
        model.addColumn("Tồn Kho");
        model.addColumn("Chú Thích");
        model1.addColumn("Mã Sản Phẩm");
        model1.addColumn("Tên Sản Phẩm");
        model1.addColumn("Số lượng bán ra");
        model2.addColumn("Mã Thẻ Khách Hàng");
        model2.addColumn("Tên Khách Hàng");
        model2.addColumn("Tổng Đơn Hàng");
    }

    public void listspb() {
        model1.setRowCount(0);
        try {
            Connection connection = DBUtils.getConnection();
            Statement statement = connection.createStatement();
            String sql = "select top 3 DonHangChiTiet.Masp,Tensp,sum(soluong)\n"
                    + "from SanPham inner join DonHangChiTiet on SanPham.Masp=DonHangChiTiet.Masp\n"
                    + "group by DonHangChiTiet.Masp,Tensp \n"
                    + "order by SUM(soluong) desc";
            ResultSet rs = statement.executeQuery(sql);
            listspb.clear();
            while (rs.next()) {
                masp1 = rs.getString(1);
                tensp1 = rs.getString(2);
                sumsp = rs.getInt(3);

                listspb.add(new spbaocao(tensp1, masp1, sumsp));
                Vector row = new Vector();
                row.add(masp1);
                row.add(tensp1);
                row.add(sumsp);

                model1.addRow(row);
            }

        } catch (SQLException e1) {
            System.out.println("Loadlist: " + e1);
        }

    }

    public void listkhb() {
        model2.setRowCount(0);
        try {
            Connection connection = DBUtils.getConnection();
            Statement statement = connection.createStatement();
            String sql = "Select top 4 donhang.MatheKH,Hovaten,COUNT(MaDh)\n"
                    + "from Donhang inner join Khachhang on Khachhang.MatheKH=Donhang.MatheKH\n"
                    + "group by donhang.MatheKH,Hovaten\n"
                    + "order by COUNT(MaDh) desc";
            ResultSet rs = statement.executeQuery(sql);
            listkhb.clear();
            while (rs.next()) {
                makh1 = rs.getString(1);
                tenkh1 = rs.getString(2);
                sumkh = rs.getInt(3);

                listspb.add(new spbaocao(makh1, tenkh1, sumkh));
                Vector row = new Vector();
                row.add(makh1);
                row.add(tenkh1);
                row.add(sumkh);

                model2.addRow(row);
            }

        } catch (SQLException e1) {
            System.out.println("Loadlist: " + e1);
        }
    }

    public void listnv() {
        try {
            Connection connection = DBUtils.getConnection();
            Statement statement = connection.createStatement();
            String sql = "select * from nhanvien ";
            ResultSet rs = statement.executeQuery(sql);
            listnv.clear();
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
                listnv.add(new Nhanvien(manv, tennv, sodienthoai, diachi, gioitinh, email, username, pass, rule));
            }
        } catch (Exception e) {
        }
    }

    public void listsp() {
        try {
            Connection connection = DBUtils.getConnection();
            Statement statement = connection.createStatement();
            String sql = " select SanPham.Masp,Tensp,donvi,gianhap,giaban,hangsp,soluongnhapkho,chuthich,soluongnhapkho-sum (soluong) as tonkho\n"
                    + "from SanPham full join DonHangChiTiet on SanPham.Masp=DonHangChiTiet.Masp\n"
                    + "group by SanPham.Masp,Tensp,donvi,gianhap,giaban,hangsp,soluongnhapkho,chuthich\n"
                    + "order by soluongnhapkho-sum(soluong)";
            ResultSet rs = statement.executeQuery(sql);
            listsp.clear();
            while (rs.next()) {
                masp = rs.getString(1);
                tensp = rs.getString(2);
                donvi = rs.getString(3);
                gianhap = rs.getFloat(4);
                giaban = rs.getFloat(5);
                hangsp = rs.getString(6);
                soluongnhap = rs.getInt(7);
                chuthich = rs.getString(8);
                tonkho = rs.getInt(9);

                listsp.add(new Sanpham(masp, tensp, donvi, hangsp, chuthich, gianhap, giaban, soluongnhap, tonkho));
                if (tonkho == 0) {
                    tonkho = soluongnhap;
                }
            }
        } catch (Exception e) {
        }
    }

    public void listkh() {
        try {
            Connection connection = DBUtils.getConnection();
            Statement statement = connection.createStatement();
            String sql = "select * from khachhang";
            ResultSet rs = statement.executeQuery(sql);
            listkh.clear();
            while (rs.next()) {
                makh = rs.getString(1);
                tenkh = rs.getString(2);
                diachi1 = rs.getString(3);
                sodienthoai1 = rs.getString(4);
                email1 = rs.getString(5);
                cmt = rs.getString(6);
                gioitinh1 = rs.getBoolean(7);

                listkh.add(new Khachhang(makh, tenkh, diachi1, sodienthoai1, email1, cmt, gioitinh1));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi");
        }
    }

    public void listsp1() {
        model.setRowCount(0);
        try {
            Connection connection = DBUtils.getConnection();
            Statement statement = connection.createStatement();
            String sql = " select SanPham.Masp,Tensp,donvi,gianhap,giaban,hangsp,soluongnhapkho,chuthich,soluongnhapkho-sum (soluong) as tonkho\n"
                    + "from SanPham full join DonHangChiTiet on SanPham.Masp=DonHangChiTiet.Masp\n"
                    + "group by SanPham.Masp,Tensp,donvi,gianhap,giaban,hangsp,soluongnhapkho,chuthich\n"
                    + "order by soluongnhapkho-sum(soluong)";
            ResultSet rs = statement.executeQuery(sql);
            listsp1.clear();
            while (rs.next()) {
                masp = rs.getString(1);
                tensp = rs.getString(2);
                donvi = rs.getString(3);
                gianhap = rs.getFloat(4);
                giaban = rs.getFloat(5);
                hangsp = rs.getString(6);
                soluongnhap = rs.getInt(7);
                chuthich = rs.getString(8);
                tonkho = rs.getInt(9);
                listsp.add(new Sanpham(masp, tensp, donvi, hangsp, chuthich, gianhap, giaban, soluongnhap, tonkho));
                Vector row = new Vector();
                row.add(masp);
                row.add(tensp);
                row.add(donvi);
                row.add(String.format("%.0f", gianhap));
                row.add(String.format("%.0f", giaban));
                row.add(hangsp);
                if (tonkho == 0) {
                    tonkho = soluongnhap;
                }
                row.add(tonkho);
                row.add(chuthich);
                if (tonkho < 5) {
                    model.addRow(row);
                }
            }

        } catch (SQLException e1) {
            System.out.println("Loadlist: " + e1);
        }
    }

    public void init() {
        int a = listnv.size();
        int c = listkh.size();
        int b = 0;
        for (int i = 0; i < listsp.size(); i++) {

            b = b + listsp.get(0).getSoluongnhapkho();

        }
        lbl_nv.setText("" + a);
        lbl_ton.setText("" + b);
        lbl_kh.setText("" + c);

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
        tbl_table = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        lbl_nv = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        lbl_ton = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        lbl_kh = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 204));
        setMaximumSize(new java.awt.Dimension(915, 540));
        setMinimumSize(new java.awt.Dimension(915, 540));
        setPreferredSize(new java.awt.Dimension(915, 540));

        jPanel1.setBackground(new java.awt.Color(0, 153, 51));
        jPanel1.setMaximumSize(new java.awt.Dimension(915, 35));
        jPanel1.setMinimumSize(new java.awt.Dimension(915, 35));
        jPanel1.setPreferredSize(new java.awt.Dimension(915, 35));

        jLabel1.setFont(new java.awt.Font("Arial", 1, 16)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("BÁO CÁO");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(397, 397, 397)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE)
        );

        tbl_table.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(tbl_table);

        jPanel2.setBackground(new java.awt.Color(255, 204, 51));

        jButton1.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jButton1.setText("Top 3 sản phẩm bán chạy nhất");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jButton2.setText("Sản phẩm sắp hết hàng");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jButton3.setText("Top 3 khách hàng thân quen");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(0, 153, 51));
        jLabel8.setText("CHỨC NĂNG BÁO CÁO");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(20, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel8)
                .addGap(43, 43, 43))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel8)
                .addGap(18, 18, 18)
                .addComponent(jButton1)
                .addGap(36, 36, 36)
                .addComponent(jButton2)
                .addGap(35, 35, 35)
                .addComponent(jButton3)
                .addContainerGap(161, Short.MAX_VALUE))
        );

        jPanel3.setBackground(new java.awt.Color(0, 153, 51));
        jPanel3.setMaximumSize(new java.awt.Dimension(250, 50));
        jPanel3.setMinimumSize(new java.awt.Dimension(250, 50));
        jPanel3.setPreferredSize(new java.awt.Dimension(250, 50));

        jLabel3.setFont(new java.awt.Font("Arial", 1, 15)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Tổng số nhân viên :");

        lbl_nv.setFont(new java.awt.Font("Arial", 1, 15)); // NOI18N
        lbl_nv.setForeground(new java.awt.Color(255, 255, 255));
        lbl_nv.setText("00");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbl_nv, javax.swing.GroupLayout.DEFAULT_SIZE, 82, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lbl_nv, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
        );

        jPanel4.setBackground(new java.awt.Color(0, 153, 51));
        jPanel4.setMaximumSize(new java.awt.Dimension(250, 50));
        jPanel4.setMinimumSize(new java.awt.Dimension(250, 50));
        jPanel4.setPreferredSize(new java.awt.Dimension(250, 50));

        jLabel4.setFont(new java.awt.Font("Arial", 1, 15)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Tổng số SP tồn kho:");

        lbl_ton.setFont(new java.awt.Font("Arial", 1, 15)); // NOI18N
        lbl_ton.setForeground(new java.awt.Color(255, 255, 255));
        lbl_ton.setText("00");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbl_ton, javax.swing.GroupLayout.DEFAULT_SIZE, 93, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
            .addComponent(lbl_ton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel6.setBackground(new java.awt.Color(0, 153, 51));
        jPanel6.setMaximumSize(new java.awt.Dimension(250, 50));
        jPanel6.setMinimumSize(new java.awt.Dimension(250, 50));
        jPanel6.setPreferredSize(new java.awt.Dimension(250, 50));

        jLabel2.setFont(new java.awt.Font("Arial", 1, 15)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Tổng số thành viên CH:");

        lbl_kh.setFont(new java.awt.Font("Arial", 1, 15)); // NOI18N
        lbl_kh.setForeground(new java.awt.Color(255, 255, 255));
        lbl_kh.setText("00");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbl_kh, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
            .addComponent(lbl_kh, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 57, Short.MAX_VALUE)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1))
                .addGap(44, 44, 44)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, 234, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(36, 36, 36))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 48, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(30, 30, 30))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

        tbl_table.setModel(model1);
        listspb();// TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed

        tbl_table.setModel(model);
        listsp1();       // TODO add your handling code here:
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed

        tbl_table.setModel(model2);
        listkhb(); // TODO add your handling code here:
    }//GEN-LAST:event_jButton3ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lbl_kh;
    private javax.swing.JLabel lbl_nv;
    private javax.swing.JLabel lbl_ton;
    private javax.swing.JTable tbl_table;
    // End of variables declaration//GEN-END:variables
}
