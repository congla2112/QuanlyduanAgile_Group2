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
public class DonHang {
   String madh;
   String mathekh;
   String ngaymua;
   String trangthai;
   String manhanvien;

    public String getMadh() {
        return madh;
    }

    public void setMadh(String madh) {
        this.madh = madh;
    }

    public String getMathekh() {
        return mathekh;
    }

    public void setMathekh(String mathekh) {
        this.mathekh = mathekh;
    }

    public String getNgaymua() {
        return ngaymua;
    }

    public void setNgaymua(String ngaymua) {
        this.ngaymua = ngaymua;
    }

    public String getTrangthai() {
        return trangthai;
    }

    public void setTrangthai(String trangthai) {
        this.trangthai = trangthai;
    }

    public String getManhanvien() {
        return manhanvien;
    }

    public void setManhanvien(String manhanvien) {
        this.manhanvien = manhanvien;
    }

    public DonHang(String madh, String mathekh, String ngaymua, String trangthai, String manhanvien) {
        this.madh = madh;
        this.mathekh = mathekh;
        this.ngaymua = ngaymua;
        this.trangthai = trangthai;
        this.manhanvien = manhanvien;
    }
   
   public DonHang(){}
    
}
