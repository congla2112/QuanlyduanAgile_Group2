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
public class khbaocao {
   String makh,tenkh;
   int sumdh;

    public khbaocao(String makh, String tenkh, int sumdh) {
        this.makh = makh;
        this.tenkh = tenkh;
        this.sumdh = sumdh;
    }

    public khbaocao() {
    }

    public String getMakh() {
        return makh;
    }

    public String getTenkh() {
        return tenkh;
    }

    public int getSumdh() {
        return sumdh;
    }

    public void setMakh(String makh) {
        this.makh = makh;
    }

    public void setTenkh(String tenkh) {
        this.tenkh = tenkh;
    }

    public void setSumdh(int sumdh) {
        this.sumdh = sumdh;
    }
    
}
