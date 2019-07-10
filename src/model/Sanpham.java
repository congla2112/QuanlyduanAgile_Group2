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
public class Sanpham {

    String masp, tensp, donvi, hangsp, chuthich;
    double GiaNhap, GiaBan;
    int soluongnhapkho,tonkho;

    public Sanpham(String masp, String tensp, String donvi, String hangsp, String chuthich, double GiaNhap, double GiaBan, int soluongnhapkho, int tonkho) {
        this.masp = masp;
        this.tensp = tensp;
        this.donvi = donvi;
        this.hangsp = hangsp;
        this.chuthich = chuthich;
        this.GiaNhap = GiaNhap;
        this.GiaBan = GiaBan;
        this.soluongnhapkho = soluongnhapkho;
        this.tonkho = tonkho;
    }

    public Sanpham() {
    }

    public String getMasp() {
        return masp;
    }

    public String getTensp() {
        return tensp;
    }

    public String getDonvi() {
        return donvi;
    }

    public String getHangsp() {
        return hangsp;
    }

    public String getChuthich() {
        return chuthich;
    }

    public double getGiaNhap() {
        return GiaNhap;
    }

    public double getGiaBan() {
        return GiaBan;
    }

    public int getSoluongnhapkho() {
        return soluongnhapkho;
    }

    public int getTonkho() {
        return tonkho;
    }

    public void setMasp(String masp) {
        this.masp = masp;
    }

    public void setTensp(String tensp) {
        this.tensp = tensp;
    }

    public void setDonvi(String donvi) {
        this.donvi = donvi;
    }

    public void setHangsp(String hangsp) {
        this.hangsp = hangsp;
    }

    public void setChuthich(String chuthich) {
        this.chuthich = chuthich;
    }

    public void setGiaNhap(double GiaNhap) {
        this.GiaNhap = GiaNhap;
    }

    public void setGiaBan(double GiaBan) {
        this.GiaBan = GiaBan;
    }

    public void setSoluongnhapkho(int soluongnhapkho) {
        this.soluongnhapkho = soluongnhapkho;
    }

    public void setTonkho(int tonkho) {
        this.tonkho = tonkho;
    }

   
}
