/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author DELL
 */
public class Nhapkho {
    String maphieu;
    String hangcungcap;
    String ngaynhap;
    float tongtien;
    float datra;
    float conno;
    String manv1;
    String ghichu;

    public String getMaphieu() {
        return maphieu;
    }

    public void setMaphieu(String maphieu) {
        this.maphieu = maphieu;
    }

    public String getHangcungcap() {
        return hangcungcap;
    }

    public void setHangcungcap(String hangcungcap) {
        this.hangcungcap = hangcungcap;
    }

    public String getNgaynhap() {
        return ngaynhap;
    }

    public void setNgaynhap(String ngaynhap) {
        this.ngaynhap = ngaynhap;
    }

    public float getTongtien() {
        return tongtien;
    }

    public void setTongtien(float tongtien) {
        this.tongtien = tongtien;
    }

    public float getDatra() {
        return datra;
    }

    public void setDatra(float datra) {
        this.datra = datra;
    }

    public float getConno() {
        return conno;
    }

    public void setConno(float conno) {
        this.conno = conno;
    }

    public String getManv1() {
        return manv1;
    }

    public void setManv1(String manv1) {
        this.manv1 = manv1;
    }

    public String getGhichu() {
        return ghichu;
    }

    public void setGhichu(String ghichu) {
        this.ghichu = ghichu;
    }

    public Nhapkho(String maphieu, String hangcungcap, String ngaynhap, float tongtien, float datra, float conno, String manv1, String ghichu) {
        this.maphieu = maphieu;
        this.hangcungcap = hangcungcap;
        this.ngaynhap = ngaynhap;
        this.tongtien = tongtien;
        this.datra = datra;
        this.conno = conno;
        this.manv1 = manv1;
        this.ghichu = ghichu;
    }
    public String getNo(){
        float avg = Math.round((tongtien+datra)*10) ;
        avg /= 10;
        String avg2 = String.valueOf(avg);
        avg2.substring(0, 3);
        return avg2;
    }
    
    

    
   
    
    public Nhapkho(){
        
    }
    
    
}
