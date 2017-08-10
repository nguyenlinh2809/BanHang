package com.example.linh0nguyen.banhang.model;

/**
 * Created by Linh(^0^)Nguyen on 7/3/2017.
 */

public class LoaiSanPham {
    private int id;
    private String tenLoaiSp;
    private String hinhLoaiSp;

    public LoaiSanPham(int id, String tenLoaiSp, String hinhLoaiSp) {
        this.id = id;
        this.tenLoaiSp = tenLoaiSp;
        this.hinhLoaiSp = hinhLoaiSp;
    }

    public LoaiSanPham(String tenLoaiSp, String hinhLoaiSp) {
        this.tenLoaiSp = tenLoaiSp;
        this.hinhLoaiSp = hinhLoaiSp;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTenLoaiSp() {
        return tenLoaiSp;
    }

    public void setTenLoaiSp(String tenLoaiSp) {
        this.tenLoaiSp = tenLoaiSp;
    }

    public String getHinhLoaiSp() {
        return hinhLoaiSp;
    }

    public void setHinhLoaiSp(String hinhLoaiSp) {
        this.hinhLoaiSp = hinhLoaiSp;
    }
}
