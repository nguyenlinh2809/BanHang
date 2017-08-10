package com.example.linh0nguyen.banhang.model;

/**
 * Created by Linh(^0^)Nguyen on 7/14/2017.
 */

public class GioHang {
    private int idSp;
    private String tenSp;
    private String hinhSp;
    private long giaSp;
    private int soLuong;

    public GioHang(int idSp, String tenSp, String hinhSp, long giaSp, int soLuong) {
        this.idSp = idSp;
        this.tenSp = tenSp;
        this.hinhSp = hinhSp;
        this.giaSp = giaSp;
        this.soLuong = soLuong;
    }

    public int getIdSp() {
        return idSp;
    }

    public void setIdSp(int idSp) {
        this.idSp = idSp;
    }

    public String getTenSp() {
        return tenSp;
    }

    public void setTenSp(String tenSp) {
        this.tenSp = tenSp;
    }

    public String getHinhSp() {
        return hinhSp;
    }

    public void setHinhSp(String hinhSp) {
        this.hinhSp = hinhSp;
    }

    public long getGiaSp() {
        return giaSp;
    }

    public void setGiaSp(long giaSp) {
        this.giaSp = giaSp;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }
    public long tongTien(){
        return giaSp*soLuong;
    }
}
