/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

/**
 *
 * @author pc
 */
public class invoice {
    private String tenmon, soluong, gia, thanhtien, maban, manv, mahd, tongtien, time;

    public invoice(String tenMon, String soLuong, String Gia, String thanhTien , String maBan, String maNv, String maHd, String tongTien, String Time) { //
        this.tenmon = tenMon;
        this.soluong = soLuong;
        this.gia = Gia;
        this.thanhtien = thanhTien;
        this.maban = maBan;
        this.manv = maNv;
        this.mahd = maHd;
        this.tongtien = tongTien;
        this.time = Time;
    }

    public String getMaban() {
        return maban;
    }

    public String getManv() {
        return manv;
    }

    public String getMahd() {
        return mahd;
    }

    public String getTongtien() {
        return tongtien;
    }
    public String getTime(){
        return time;
    }


    public String getTenmon() {
        return tenmon;
    }



    public String getSoluong() {
        return soluong;
    }



    public String getGia() {
        return gia;
    }



    public String getThanhtien() {
        return thanhtien;
    }

    
}
