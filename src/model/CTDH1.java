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
public class CTDH1 {
    String madh_ct;
    String masp,tensp;
    float giaban;
    String donvi;
    int soluong;
    int madhct;

    public String getMadh_ct() {
        return madh_ct;
    }

    public void setMadh_ct(String madh_ct) {
        this.madh_ct = madh_ct;
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

    public int getMadhct() {
        return madhct;
    }

    public void setMadhct(int madhct) {
        this.madhct = madhct;
    }

    public CTDH1(String madh_ct, String masp, String tensp, float giaban, String donvi, int soluong, int madhct) {
        this.madh_ct = madh_ct;
        this.masp = masp;
        this.tensp = tensp;
        this.giaban = giaban;
        this.donvi = donvi;
        this.soluong = soluong;
        this.madhct = madhct;
    }

    
    
    public CTDH1(){}
    
}
