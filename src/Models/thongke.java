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
public class thongke {
    private String mahd, tienmon, giamgia, thanhtien, diemban,cacmon, tongtienthu, tonghoadon, tongtienban, tonggiamgia;

    public thongke(String maHD, String tienMon, String giamGia, String thanhTien, String diemBan, String cacMon, String tongTien, String tongHoadon, String tongTienban,String tongGiamgia) {
        this.mahd = maHD;
        this.tienmon = tienMon;
        this.giamgia = giamGia;
        this.thanhtien = thanhTien;
        this.diemban = diemBan;
        this.cacmon = cacMon;
        this.tongtienthu = tongTien;
        this.tonghoadon = tongHoadon;
        this.tongtienban = tongTienban;
        this.tonggiamgia  = tongGiamgia;
    }

    public String getTonghoadon() {
        return tonghoadon;
    }

    public String getTongtienban() {
        return tongtienban;
    }

    public String getTonggiamgia() {
        return tonggiamgia;
    }

    public String getTongtienthu() {
        return tongtienthu;
    }

    public String getMahd() {
        return mahd;
    }


    public String getTienmon() {
        return tienmon;
    }

    public String getGiamgia() {
        return giamgia;
    }

    public String getThanhtien() {
        return thanhtien;
    }

    public String getDiemban() {
        return diemban;
    }

    public String getCacmon() {
        return cacmon;
    }
    
}
