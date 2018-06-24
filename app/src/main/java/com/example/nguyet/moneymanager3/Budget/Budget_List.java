package com.example.nguyet.moneymanager3.Budget;

/**
 * Created by Nguyet on 10/26/2017.
 */

public class Budget_List {
    private String DanhMuc;
    private int Tien;
    private String Ngay;
    private Integer iconimage;


    public Integer getIconimage() {
        return iconimage;
    }

    public void setIconimage(Integer iconimage) {
        this.iconimage = iconimage;
    }

    public Budget_List(String danhMuc, int tien, String ngay, Integer iconimage){
        setDanhMuc(danhMuc);
        setTien(tien);
        setNgay(ngay);
        setIconimage(iconimage);
    }


    public String getDanhMuc() {
        return DanhMuc;
    }

    public void setDanhMuc(String danhMuc) {
        DanhMuc = danhMuc;
    }

    public int getTien() {
        return Tien;
    }

    public void setTien(int tien) {
        Tien = tien;
    }

    public String getNgay() {
        return Ngay;
    }

    public void setNgay(String ngay) {
        Ngay = ngay;
    }

}

