package com.example.thithu.Model;

import com.google.gson.annotations.SerializedName;

public class SinhVienModel {
    @SerializedName("_id")
    private String id ;
    @SerializedName("name")
    private String name ;
    @SerializedName("age")
    private int tuoi ;
    @SerializedName("diemTb")
    private double diemtb ;
    @SerializedName("status")
    private int status ;
    @SerializedName("Image")
     private String img ;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTuoi() {
        return tuoi;
    }

    public void setTuoi(int tuoi) {
        this.tuoi = tuoi;
    }

    public double getDiemtb() {
        return diemtb;
    }

    public void setDiemtb(double diemtb) {
        this.diemtb = diemtb;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public SinhVienModel() {
    }

    public SinhVienModel(String id, String name, int tuoi, double diemtb, int status, String img) {
        this.id = id;
        this.name = name;
        this.tuoi = tuoi;
        this.diemtb = diemtb;
        this.status = status;
        this.img = img;
    }
    public String Status(){
        return status == 1?"Dang Hoc" :"Dahoc xong" ;
    }

    public SinhVienModel(String name, int tuoi, double diemtb, int status, String img) {
        this.name = name;
        this.tuoi = tuoi;
        this.diemtb = diemtb;
        this.status = status;
        this.img = img;
    }
}
