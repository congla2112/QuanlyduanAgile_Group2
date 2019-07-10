/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author My PC
 */
public class Nhanvien {
    String manv,tennv,sodienthoai,diachi,email,username,pass,role;

    
    boolean gioitinh;

   public Nhanvien(String manv, String tennv, String sodienthoai, String diachi, boolean gioitinh, String email, String username, String pass, String role) {
        this.manv = manv;
        this.tennv = tennv;
        this.sodienthoai = sodienthoai;
        this.diachi = diachi;
        this.gioitinh = gioitinh;
        this.email = email;
        this.username = username;
        this.pass = pass;
        this.role = role;
    }

    public Nhanvien() {
    }

    public String getManv() {
        return manv;
    }

    public String getTennv() {
        return tennv;
    }

    public String getSodienthoai() {
        return sodienthoai;
    }

    public String getDiachi() {
        return diachi;
    }

    public boolean getGioitinh() {
        return gioitinh;
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    public String getPass() {
        return pass;
    }

    public String getRole() {
        return role;
    }

    public void setManv(String manv) {
        this.manv = manv;
    }

    public void setTennv(String tennv) {
        this.tennv = tennv;
    }

    public void setSodienthoai(String sodienthoai) {
        this.sodienthoai = sodienthoai;
    }

    public void setDiachi(String diachi) {
        this.diachi = diachi;
    }

    public void setGioitinh(boolean gioitinh) {
        this.gioitinh = gioitinh;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public void setRole(String role) {
        this.role = role;
    }
   
    
}
