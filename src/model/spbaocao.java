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
public class spbaocao {
    String tensp,masp;
    int sumsp;

    public spbaocao(String tensp, String masp, int sumsp) {
        this.tensp = tensp;
        this.masp = masp;
        this.sumsp = sumsp;
    }

    public spbaocao() {
    }

    public String getTensp() {
        return tensp;
    }

    public String getMasp() {
        return masp;
    }

    public int getSumsp() {
        return sumsp;
    }

    public void setTensp(String tensp) {
        this.tensp = tensp;
    }

    public void setMasp(String masp) {
        this.masp = masp;
    }

    public void setSumsp(int sumsp) {
        this.sumsp = sumsp;
    }
    
}
