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
public class ChiTietDonHang {
    String madh_ct, mathekh_CT,ngaymua_CT,trangthai_CT,manv;
    String masp,tensp;
    float giaban;
    String donvi;
    int soluong;
    int stt;

    public String getMadh_ct() {
        return madh_ct;
    }

    public void setMadh_ct(String madh_ct) {
        this.madh_ct = madh_ct;
    }

    public String getMathekh_CT() {
        return mathekh_CT;
    }

    public void setMathekh_CT(String mathekh_CT) {
        this.mathekh_CT = mathekh_CT;
    }

    public String getNgaymua_CT() {
        return ngaymua_CT;
    }

    public void setNgaymua_CT(String ngaymua_CT) {
        this.ngaymua_CT = ngaymua_CT;
    }

    public String getTrangthai_CT() {
        return trangthai_CT;
    }

    public void setTrangthai_CT(String trangthai_CT) {
        this.trangthai_CT = trangthai_CT;
    }

    public String getManv() {
        return manv;
    }

    public void setManv(String manv) {
        this.manv = manv;
    }

    public String getMasp() {
        return masp;
    }

    public void setMasp(String masp) {
        this.masp = masp;
    }

    public String getTensp() {
        return tensp;
    }

    public void setTensp(String tensp) {
        this.tensp = tensp;
    }

    public float getGiaban() {
        return giaban;
    }

    public void setGiaban(float giaban) {
        this.giaban = giaban;
    }

    public String getDonvi() {
        return donvi;
    }

    public void setDonvi(String donvi) {
        this.donvi = donvi;
    }

    public int getSoluong() {
        return soluong;
    }

    public void setSoluong(int soluong) {
        this.soluong = soluong;
    }

    public int getStt() {
        return stt;
    }

    public void setStt(int stt) {
        this.stt = stt;
    }

    public ChiTietDonHang(String madh_ct, String mathekh_CT, String ngaymua_CT, String trangthai_CT, String manv, String masp, String tensp, float giaban, String donvi, int soluong, int stt) {
        this.madh_ct = madh_ct;
        this.mathekh_CT = mathekh_CT;
        this.ngaymua_CT = ngaymua_CT;
        this.trangthai_CT = trangthai_CT;
        this.manv = manv;
        this.masp = masp;
        this.tensp = tensp;
        this.giaban = giaban;
        this.donvi = donvi;
        this.soluong = soluong;
        this.stt = stt;
    }

    
    public ChiTietDonHang(){}
    }
