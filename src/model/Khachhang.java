/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author trung
 */
public class Khachhang {
    String makh, tenkh, diachi, sodienthoai, email, cmt;
    boolean gioitinh;

    public Khachhang() {
    }

    public Khachhang(String makh, String tenkh, String diachi, String sodienthoai, String email, String cmt, boolean gioitinh) {
        this.makh = makh;
        this.tenkh = tenkh;
        this.diachi = diachi;
        this.sodienthoai = sodienthoai;
        this.email = email;
        this.cmt = cmt;
        this.gioitinh = gioitinh;
    }

    public String getMakh() {
        return makh;
    }

    public void setMakh(String makh) {
        this.makh = makh;
    }

    public String getTenkh() {
        return tenkh;
    }

    public void setTenkh(String tenkh) {
        this.tenkh = tenkh;
    }

    public String getDiachi() {
        return diachi;
    }

    public void setDiachi(String diachi) {
        this.diachi = diachi;
    }

    public String getSodienthoai() {
        return sodienthoai;
    }

    public void setSodienthoai(String sodienthoai) {
        this.sodienthoai = sodienthoai;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCmt() {
        return cmt;
    }

    public void setCmt(String cmt) {
        this.cmt = cmt;
    }

    public boolean isGioitinh() {
        return gioitinh;
    }

    public void setGioitinh(boolean gioitinh) {
        this.gioitinh = gioitinh;
    }

    

 
}
