package com.example.linh0nguyen.banhang.model;

import java.io.Serializable;

/**
 * Created by Linh(^0^)Nguyen on 7/4/2017.
 */

public class SanPham implements Serializable{
    private int id;
    private String tenSp;
    private int giaSp;
    private String hinhSp;
    private String moTa;
    private int idLoaiSp;

    public SanPham(int id, String tenSp, int giaSp, String hinhSp, String moTa, int idLoaiSp) {
        this.id = id;
        this.tenSp = tenSp;
        this.giaSp = giaSp;
        this.hinhSp = hinhSp;
        this.moTa = moTa;
        this.idLoaiSp = idLoaiSp;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTenSp() {
        return tenSp;
    }

    public void setTenSp(String tenSp) {
        this.tenSp = tenSp;
    }

    public int getGiaSp() {
        return giaSp;
    }

    public void setGiaSp(int giaSp) {
        this.giaSp = giaSp;
    }

    public String getHinhSp() {
        return hinhSp;
    }

    public void setHinhSp(String hinhSp) {
        this.hinhSp = hinhSp;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public int getIdLoaiSp() {
        return idLoaiSp;
    }

    public void setIdLoaiSp(int idLoaiSp) {
        this.idLoaiSp = idLoaiSp;
    }
}
