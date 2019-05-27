/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Data;

import Models.account;
import com.sun.scenario.effect.impl.prism.ps.PPSDrawable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import javax.naming.spi.DirStateFactory;
import MainUI.LoginForm;
import Models.Ban;
import Models.ChiTietHD;
import Models.DsOrder;
import Models.HoaDon;
import Models.Loai;
import Models.ThucDon;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;
import javax.swing.JOptionPane;

/**
 *
 * @author pc
 */
public class ConnectDB {

    private Connection cn;

    public ConnectDB() {
        try {
            String url = "jdbc:sqlserver://localhost:1433;"
                    + "databaseName=DPSG;"
                    + "integratedSecurity=true";
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            cn = DriverManager.getConnection(url);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Kết nối thất bại!");
        }
    }

    public ArrayList<account> getListaccount() {
        ArrayList<account> list = new ArrayList<>();
        String sql = "select * from tbAccount";
        try {
            PreparedStatement ps = cn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                account a = new account();
                a.setId(rs.getInt("id"));
                a.setUsername(rs.getString("username"));
                a.setPassword(rs.getString("password"));
                a.setLoaiTk(rs.getInt("typeaccount"));
                list.add(a);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public ArrayList<account> SearchTaiKhoan(String ten) {
        ArrayList<account> arrAccount = null;
        String sql;
        sql = "SELECT * FROM tbAccount WHERE username LIKE '%" + ten + "%'";
        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            arrAccount = new ArrayList<account>();
            while (rs.next()) {
                account a = new account(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4));
                arrAccount.add(a);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "lỗi !");
        }
        return arrAccount;
    }

    public boolean isAccount(String user, String pass) {
        //boolean result = true;
        String sql = "select * from tbAccount where username=? and password =?";
        try {
            PreparedStatement ps = cn.prepareStatement(sql);
            ps.setString(1, user);
            ps.setString(2, pass);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                LoginForm.taikhoan.setId(rs.getInt("id"));
                LoginForm.taikhoan.setUsername(rs.getString("username"));
                LoginForm.taikhoan.setPassword(rs.getString("password"));
                LoginForm.taikhoan.setLoaiTk(rs.getInt("typeaccount"));
                return true;
            } else {
                return false;
            }

        } catch (Exception e) {
            return false;

        }
    }

    public boolean isAddaNewAccount(String user, String pass) {
        String sql = "insert into tbAccount(username, password)" + "values(?,?)";
        try {
            PreparedStatement ps = cn.prepareStatement(sql);
            ps.setString(1, user);
            ps.setString(2, pass);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean isExistAccount(String username) {
        String sql = "select * from tbAccount where username=?";
        try {
            PreparedStatement ps = cn.prepareStatement(sql);
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (Exception e) {
        }
        return false;
    }

    public boolean isExistMon(String username) {
        String sql = "select * from thucdon where TenMon=?";
        try {
            PreparedStatement ps = cn.prepareStatement(sql);
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (Exception e) {
        }
        return false;
    }

    public boolean isExistTalbe(String tablename) {
        String sql = "select * from ban where TenBan=?";
        try {
            PreparedStatement ps = cn.prepareStatement(sql);
            ps.setString(1, tablename);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (Exception e) {
        }
        return false;
    }

    public boolean isExistLoai(String tenloai) {
        String sql = "select * from nhommon where TenLoai=?";
        try {
            PreparedStatement ps = cn.prepareStatement(sql);
            ps.setString(1, tenloai);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (Exception e) {
        }
        return false;
    }

    public ArrayList<Loai> GetLoai() {
        ArrayList<Loai> arrloai = null;
        String sql = "Select * From nhommon";
        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            arrloai = new ArrayList<Loai>();
            while (rs.next()) {
                Loai sp = new Loai(rs.getString(1), rs.getString(2), rs.getString(3));
                arrloai.add(sp);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Đã xảy ra lỗi !");
        }
        return arrloai;
    }

    public ArrayList<Ban> GetBan(int maban) {
        ArrayList<Ban> arrBan = null;
        String sql;
        if (maban == 0) {
            sql = "Select * From ban";
        } else {
            sql = "Select * From ban Where MaBan = '" + maban + "'";
        }
        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            arrBan = new ArrayList<Ban>();
            while (rs.next()) {
                Ban sp = new Ban(rs.getInt(1), rs.getString(2), rs.getString(3));
                arrBan.add(sp);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Đã xảy ra lỗi !");
        }
        return arrBan;
    }

    public boolean UpdateBan(Ban b) {
        int update = 0;
        String sql = "UPDATE ban SET TenBan = N'" + b.GetTenBan() + "', TrangThai = N'" + b.GetTrangThai() + "' WHERE MaBan = '" + b.GetMaBan() + "'";
        try {
            Statement st = cn.createStatement();
            update = st.executeUpdate(sql);
            return true;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Update bàn không thành công !");
            return false;
        }
    }

    public String GetMaLoai(String TenLoai) {
        String maloai = null;
        String sql = "Select MaLoai From nhommon Where TenLoai = '" + TenLoai + "'";
        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                maloai = rs.getString(1);
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Không lấy được mã loại !");
        }
        return maloai;
    }

    public ArrayList<ThucDon> GetThucDonByMa(String ma) {
        ArrayList<ThucDon> arrThucDon = null;
        String sql;

        sql = "Select * From thucdon Where MaMon = '" + ma + "'";
        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            arrThucDon = new ArrayList<ThucDon>();
            while (rs.next()) {
                ThucDon td = new ThucDon(rs.getString(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getString(5));
                arrThucDon.add(td);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Không lấy được danh sách thực đơn !");
        }
        return arrThucDon;
    }

    public ArrayList<DsOrder> GetDsOrder(int ma) {
        ArrayList<DsOrder> arrDs = null;
        String sql;
        sql = "Select ct.MaMon, TenMon, DVT, SoLuong, Gia, MaHoaDon From chitiethd AS ct INNER JOIN thucdon AS td ON ct.MaMon = td.MaMon Where ct.MaHoaDon = '" + ma + "'";
        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            arrDs = new ArrayList<DsOrder>();
            while (rs.next()) {
                DsOrder order = new DsOrder(rs.getString(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getInt(5), rs.getInt(6));
                arrDs.add(order);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Không lấy được danh sách Order !");
        }
        return arrDs;
    }

    public ChiTietHD GetDsChiTiet(String ma, int maban) {
        ChiTietHD arrDs = null;
        String sql;

        sql = "Select SoLuong, Gia, MaChiTietHD From chitiethd AS ct INNER JOIN hoadon AS hd ON ct.MaHoaDon = hd.MaHoaDon Where MaMon = '" + ma + "' AND MaBan = '" + maban + "' AND hd.TrangThai = 0";
        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                arrDs = new ChiTietHD();
                arrDs.SetSoLuong(rs.getInt(1));
                arrDs.SetGia(rs.getInt(2));
                arrDs.SetMaChiTietHD(rs.getInt(3));
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Không lấy được danh sách Order !");
        }
        return arrDs;
    }

    public int UpdateChiTiet(ChiTietHD ct) {
        int update = 0;
        String sql = "UPDATE chitiethd SET SoLuong = '" + ct.GetSoLuong() + "', Gia = '" + ct.GetGia() + "' WHERE MaChiTietHD = '" + ct.GetMaChiTietHD() + "'";
        try {
            Statement st = cn.createStatement();
            update = st.executeUpdate(sql);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Update chi tiết không thành công !");
        }
        return update;
    }

    public int HuyHD(HoaDon hd) {
        int update = 0;
        String sql = "Delete From hoadon WHERE MaHoaDon = '" + hd.GetMaHD() + "'";
        try {
            Statement st = cn.createStatement();
            update = st.executeUpdate(sql);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Thanh toán không thành công !");
        }
        return update;
    }

    public int ThanhToan(HoaDon hd) {
        int update = 0;
        String sql = "UPDATE hoadon SET TongTien = '" + hd.GetTongTien() + "', TrangThai = 1 WHERE MaHoaDon = '" + hd.GetMaHD() + "'";
        try {
            Statement st = cn.createStatement();
            update = st.executeUpdate(sql);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Thanh toán không thành công !");
        }
        return update;
    }

    public int UpDateTrangThaiBan(Ban b) {
        int update = 0;
        String sql = "UPDATE ban SET TrangThai = N'" + b.GetTrangThai() + "' WHERE MaBan = '" + b.GetMaBan() + "'";
        try {
            Statement st = cn.createStatement();
            update = st.executeUpdate(sql);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Update trạng thái bàn không thành công !");
        }
        return update;
    }

    public ArrayList<ThucDon> GetThucDon(String ma) {
        ArrayList<ThucDon> arrThucDon = null;
        String sql;
        if (ma == null) {
            sql = "Select * From thucdon";
        } else {
            sql = "Select * From thucdon Where MaLoai = '" + ma + "'";
        }
        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            arrThucDon = new ArrayList<ThucDon>();
            while (rs.next()) {
                ThucDon td = new ThucDon(rs.getString(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getString(5));
                arrThucDon.add(td);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Không lấy được danh sách thực đơn !");
        }
        return arrThucDon;
    }

    public int InsertHoaDon(HoaDon hd, String gio) {
        int insert = 0;
        String sql = "Insert into hoadon (MaBan, GioDen, TrangThai) values ('" + hd.GetMaBan() + "', '" + gio + "', '" + hd.GetTrangThai() + "')";
        try {
            Statement st = cn.createStatement();
            insert = st.executeUpdate(sql);
        } catch (SQLException ex) {
        }
        return insert;
    }

    public int DeleteMon(String mamon, int mahd, int maban) {
        int check = 0;
        try {
            String sql;
            sql = "Delete From chitiethd Where MaMon = '" + mamon + "' AND MaHoaDon = '" + mahd + "'";
            Statement st = cn.createStatement();
            st.executeUpdate(sql);
            check = 1;
            if (CheckDsMon(mahd, maban) == 0) {
                check = 2;
            }
        } catch (SQLException ex) {

        }
        return check;
    }

    public int CheckDsMon(int mahd, int maban) {
        String sql;
        int dem = 0;
        sql = "Select * From hoadon AS hd INNER JOIN chitiethd AS ct ON ct.MaHoaDon = hd.MaHoaDon Where MaBan = '" + maban + "' AND ct.MaHoaDon = '" + mahd + "' AND TrangThai = 0";
        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                dem++;
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Không lấy được danh sách hóa đơn !");
        }
        return dem;
    }

    public HoaDon GetHDbyMaBan(int ma) {
        String sql;
        HoaDon arrhd = null;
        sql = "Select * From hoadon Where MaBan = '" + ma + "' AND TrangThai = 0";
        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                arrhd = new HoaDon(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getTimestamp(4), rs.getInt(5), rs.getInt(6));
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Không lấy được danh sách hóa đơn !");
        }
        return arrhd;
    }

    public int GetMaHD(int ma) {
        String sql;
        int mahd = 0;
        sql = "Select MaHoaDon From hoadon Where MaBan = '" + ma + "' AND TrangThai = 0";
        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                mahd = rs.getInt(1);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Không lấy được danh sách thực đơn !");
        }
        return mahd;
    }

    public int UpdateHD(HoaDon hd) {
        int update = 0;
        String sql = "UPDATE hoadon SET GiamGia = '" + hd.GetGiamGia() + "' WHERE MaHoaDon = '" + hd.GetMaHD() + "'";
        try {
            Statement st = cn.createStatement();
            update = st.executeUpdate(sql);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Giảm giá không thành công !");
        }
        return update;
    }

    public boolean UpdateHDBan(String mahd, String maban) {
        int update = 0;
        String sql = "UPDATE hoadon SET MaBan = '" + maban + "' WHERE MaHoaDon = '" + mahd + "'";
        try {
            Statement st = cn.createStatement();
            update = st.executeUpdate(sql);
            return true;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Chuyển bàn không thành công !");
            return false;
        }
    }

    public int InsertChiTietHD(ChiTietHD cthd) {
        int insert = 0;
        String sql = "Insert into chitiethd (MaHoaDon, MaMon, SoLuong, Gia) values ('" + cthd.GetMaHD() + "', '" + cthd.GetMaMon() + "', '" + cthd.GetSoLuong() + "', '" + cthd.GetGia() + "')";
        try {
            Statement st = cn.createStatement();
            insert = st.executeUpdate(sql);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Thêm chi tiết hóa đơn không thành công !" + ex.toString());
        }
        return insert;
    }

    public int LVTK(account tk) {
        int lvtk = 0;
        String sql;
        sql = "Select lv From taikhoan Where username = '" + tk.getUsername() + "' AND password='" + tk.getPassword() + "'";
        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                lvtk = rs.getInt(1);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Đã xảy ra lỗi !");
        }
        return lvtk;
    }

    public boolean InsertBan(Ban b) {
        int insert = 0;
        String sql = "Insert into ban (TenBan, TrangThai) values ('" + b.GetTenBan() + "', N'" + b.GetTrangThai() + "')";
        try {
            Statement st = cn.createStatement();
            insert = st.executeUpdate(sql);
            return true;
        } catch (SQLException ex) {
            return false;
        }

    }

    public boolean DeleteBan(ArrayList<Integer> listMaBan) {
        boolean check = false;
        try {
            String sql;
            for (int ma : listMaBan) {
                sql = "Delete From ban Where MaBan = '" + ma + "'";
                Statement st = cn.createStatement();
                st.executeUpdate(sql);
            }
            check = true;
        } catch (SQLException ex) {

        }
        return check;
    }

    public Vector GetNhomMon() {
        Vector arrloai = null;
        String sql = "Select * From nhommon";
        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            arrloai = new Vector();
            Loai all = new Loai(null, "Tất cả các món", "");
            arrloai.add(all);
            while (rs.next()) {
                Loai sp = new Loai(rs.getString(1), rs.getString(2), rs.getString(3));
                arrloai.add(sp);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Đã xảy ra lỗi !");
        }
        return arrloai;
    }

    public Vector GetBanTrong() {
        Vector arrayBan = null;
        String sql = "select * from ban where TrangThai = N'Trống'";
        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            arrayBan = new Vector();
            while (rs.next()) {
                Ban ban = new Ban(rs.getInt(1), rs.getString(2), rs.getString(3));
                arrayBan.add(ban);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Đã xảy ra lỗi !");
        }
        return arrayBan;
    }
    public Vector GetBanDangPhucVu() {
        Vector arrayBan = null;
        String sql = "select * from ban where TrangThai = N'Đang phục vụ'";
        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            arrayBan = new Vector();
            while (rs.next()) {
                Ban ban = new Ban(rs.getInt(1), rs.getString(2), rs.getString(3));
                arrayBan.add(ban);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Đã xảy ra lỗi !");
        }
        return arrayBan;
    }

    public boolean InsertThucDon(ThucDon td) {
        int insert = 0;
        String sql = "Insert into thucdon (TenMon, MaLoai, DonGia, DVT) values (N'" + td.GetTenMon() + "', '" + td.GetMaLoai() + "', '" + td.GetDonGia() + "', N'" + td.GetDVT() + "')";
        try {
            Statement st = cn.createStatement();
            insert = st.executeUpdate(sql);
            return true;
        } catch (SQLException ex) {
            return false;
        }

    }

    public boolean DeleteThucDon(ArrayList<String> listMamon) {
        boolean check = false;
        try {
            String sql;
            for (String ma : listMamon) {
                sql = "Delete From thucdon Where MaMon = '" + ma + "'";
                Statement st = cn.createStatement();
                st.executeUpdate(sql);
            }
            check = true;
        } catch (SQLException ex) {

        }
        return check;
    }

    public boolean UpdateThucDon(ThucDon td) {
        int update = 0;
        String sql = "UPDATE thucdon SET TenMon = N'" + td.GetTenMon() + "', MaLoai = '" + td.GetMaLoai() + "', DonGia = '" + td.GetDonGia() + "', DVT = N'" + td.GetDVT() + "' WHERE MaMon = '" + td.GetMaMon() + "'";
        try {
            Statement st = cn.createStatement();
            update = st.executeUpdate(sql);
            return true;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Update món không thành công !");
            return false;
        }
    }

    public int InsertLoai(Loai b) {
        int insert = 0;
        String sql = "Insert into nhommon (TenLoai, MauSac) values ('" + b.GetTenLoai() + "', '" + b.GetMauSac() + "')";
        try {
            Statement st = cn.createStatement();
            insert = st.executeUpdate(sql);
        } catch (SQLException ex) {
        }
        return insert;
    }

    public boolean DeleteNhom(ArrayList<String> lismanhom) {
        boolean check = false;
        try {
            String sql;
            for (String ma : lismanhom) {
                sql = "Delete From nhommon Where MaLoai = '" + ma + "'";
                Statement st = cn.createStatement();
                st.executeUpdate(sql);
            }
            check = true;
        } catch (SQLException ex) {

        }
        return check;
    }

    public Loai GetLoaiByMa(String manhom) {
        Loai loai = null;
        String sql = "Select * From nhommon Where MaLoai = '" + manhom + "'";
        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            if (rs.next()) {
                loai = new Loai(rs.getString(1), rs.getString(2), rs.getString(3));
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Đã xảy ra lỗi !");
        }
        return loai;
    }

    public int UpdateLoai(Loai b) {
        int update = 0;
        String sql = "UPDATE nhommon SET TenLoai = N'" + b.GetTenLoai() + "', MauSac = '" + b.GetMauSac() + "' WHERE MaLoai = '" + b.GetMaLoai() + "'";
        try {
            Statement st = cn.createStatement();
            update = st.executeUpdate(sql);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Update Loại không thành công !");
        }
        return update;
    }

    public ArrayList<ThucDon> SearchMon(String ten) {
        ArrayList<ThucDon> arrtd = null;
        String sql;
        sql = "SELECT * FROM thucdon WHERE TenMon LIKE '" + ten + "%'";
        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            arrtd = new ArrayList<ThucDon>();
            while (rs.next()) {
                ThucDon td = new ThucDon(rs.getString(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getString(5));
                arrtd.add(td);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "lỗi !");
        }
        return arrtd;
    }

    public ArrayList<Loai> SearchLoai(String ten) {
        ArrayList<Loai> arrtd = null;
        String sql;
        sql = "SELECT * FROM nhommon WHERE TenLoai LIKE '" + ten + "%'";
        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            arrtd = new ArrayList<Loai>();
            while (rs.next()) {
                Loai td = new Loai(rs.getString(1), rs.getString(2), rs.getString(3));
                arrtd.add(td);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "lỗi !");
        }
        return arrtd;
    }

    public ArrayList<Ban> SearchBan(String ten) {
        ArrayList<Ban> arrtd = null;
        String sql;
        sql = "SELECT * FROM ban WHERE TenBan LIKE '%" + ten + "%'";
        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            arrtd = new ArrayList<Ban>();
            while (rs.next()) {
                Ban td = new Ban(rs.getInt(1), rs.getString(2), rs.getString(3));
                arrtd.add(td);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "lỗi !");
        }
        return arrtd;
    }

    public ArrayList<account> GetTaiKhoan() {
        ArrayList<account> arrtd = null;
        String sql;
        sql = "SELECT * FROM tbAccount WHERE typeaccount != 1";
        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            arrtd = new ArrayList<account>();
            while (rs.next()) {
                account td = new account(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4));
                arrtd.add(td);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "lỗi !");
        }
        return arrtd;
    }

    public boolean DeleteTaiKhoan(ArrayList<Integer> listMaTK) {
        boolean check = false;
        try {
            String sql;
            for (int ma : listMaTK) {
                sql = "Delete From tbAccount Where id = '" + ma + "'";
                Statement st = cn.createStatement();
                st.executeUpdate(sql);
            }
            check = true;
        } catch (SQLException ex) {

        }
        return check;
    }

    public boolean InserTK(account b) {
        int insert = 0;
        String sql = "Insert into tbAccount (username, password, typeaccount) values ('" + b.getUsername() + "', '" + b.getPassword() + "', '" + b.getLoaiTk() + "')";
        try {
            Statement st = cn.createStatement();
            insert = st.executeUpdate(sql);
            return true;

        } catch (SQLException ex) {
            return false;
        }

    }

    public account GetTaiKhoan(int id) {
        account td = null;
        String sql;
        sql = "SELECT * FROM tbAccount WHERE id = '" + id + "'";
        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                td = new account(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4));
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "lỗi !");
        }
        return td;
    }

    public account GetTaiKhoan(String name, String pass) {
        account td = null;
        String sql;
        sql = "SELECT * FROM tbAccount Where username = '" + name + "' AND password='" + pass + "'";
        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                td = new account(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4));
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "lỗi !");
        }
        return td;
    }

    public boolean UpdateTK(account b) {
        int update = 0;
        String sql = "UPDATE tbAccount SET username = '" + b.getUsername() + "', password = '" + b.getPassword() + "' , typeaccount = '" + b.getLoaiTk() + "' WHERE id = '" + b.getId() + "'";
        try {
            Statement st = cn.createStatement();
            update = st.executeUpdate(sql);
            return true;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Update tài khoản không thành công !");
            return false;
        }
    }

    public ArrayList<HoaDon> GetDSHD() {
        ArrayList<HoaDon> arrDs = null;
        String sql;
        sql = "Select * From hoadon Where TrangThai = 1";
        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            arrDs = new ArrayList<HoaDon>();
            while (rs.next()) {
                HoaDon order = new HoaDon(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getTimestamp(4), rs.getInt(5), rs.getInt(6));
                arrDs.add(order);
            }
        } catch (SQLException ex) {
        }
        return arrDs;
    }

    public ArrayList<ThucDon> GetDsMonBan() {
        ArrayList<ThucDon> arrDs = null;
        String sql;
        sql = "Select TenMon, MaMon, DVT From thucdon WHERE MaMon in (Select MaMon From chitiethd) AND hoadon.TrangThai = 1";
        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            arrDs = new ArrayList<ThucDon>();
            while (rs.next()) {
                ThucDon order = new ThucDon();
                order.SetTenMon(rs.getString(1));
                order.SetMaMon(rs.getString(2));
                order.SetDVT(rs.getString(3));
                arrDs.add(order);
            }
        } catch (SQLException ex) {
        }
        return arrDs;
    }

    public ArrayList<ThucDon> GetChiTietMonByMa() {
        ArrayList<ThucDon> arrDs = null;
        String sql;
        sql = "SELECT TenMon, MaMon, DVT FROM thucdon where MaMon in (Select MaMon From chitiethd)";
        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            arrDs = new ArrayList<ThucDon>();
            while (rs.next()) {
                ThucDon order = new ThucDon();
                order.SetTenMon(rs.getString(1));
                order.SetMaMon(rs.getString(2));
                order.SetDVT(rs.getString(3));
                arrDs.add(order);
            }
        } catch (SQLException ex) {
        }
        return arrDs;
    }

    public ArrayList<DsOrder> GetGiaSoLuong(String ma) {
        ArrayList<DsOrder> arrDs = null;
        String sql;
        sql = "Select Gia, SoLuong, TenMon, DVT From chitiethd AS ct INNER JOIN hoadon AS hd ON ct.MaHoaDon = hd.MaHoaDon INNER JOIN thucdon AS td ON td.MaMon = ct.MaMon Where hd.TrangThai = 1 AND ct.MaMon = '" + ma + "'";
        try {

            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            arrDs = new ArrayList<DsOrder>();
            while (rs.next()) {

                DsOrder order = new DsOrder();
                order.SetGia(rs.getInt(1));
                order.SetSoLuong(rs.getInt(2));
                order.SetTenMon(rs.getString(3));
                order.SetDVT(rs.getString(4));
                arrDs.add(order);
            }
        } catch (SQLException ex) {
        }
        return arrDs;
    }

    public ArrayList<DsOrder> GetHdByDate(String d1, String d2, String m) {
        ArrayList<DsOrder> arrDs = null;
        String sql;
        if (d1.equals(d2)) {
            sql = "Select Gia, SoLuong, TenMon, DVT From chitiethd AS ct INNER JOIN hoadon AS hd ON ct.MaHoaDon = hd.MaHoaDon INNER JOIN thucdon AS td ON td.MaMon = ct.MaMon Where hd.TrangThai = 1 AND hd.GioDen >= '" + d1 + "' AND ct.MaMon ='" + m + "'";
        } else {
            sql = "Select Gia, SoLuong, TenMon, DVT From chitiethd AS ct INNER JOIN hoadon AS hd ON ct.MaHoaDon = hd.MaHoaDon INNER JOIN thucdon AS td ON td.MaMon = ct.MaMon Where hd.TrangThai = 1 AND hd.GioDen BETWEEN '" + d1 + "' AND '" + d2 + "' AND ct.MaMon ='" + m + "'";
        }
        try {

            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            arrDs = new ArrayList<DsOrder>();
            while (rs.next()) {
                DsOrder order = new DsOrder();
                order.SetGia(rs.getInt(1));
                order.SetSoLuong(rs.getInt(2));
                order.SetTenMon(rs.getString(3));
                order.SetDVT(rs.getString(4));
                arrDs.add(order);
            }
        } catch (SQLException ex) {
        }
        return arrDs;
    }

    public ArrayList<DsOrder> GetCtHDByDate(int ma, String d1, String d2) {
        ArrayList<DsOrder> arrDs = null;
        String sql;
        if (d1.equals(d2)) {
            sql = "Select ct.MaMon, TenMon, DVT, SoLuong, Gia, ct.MaHoaDon From chitiethd AS ct INNER JOIN thucdon AS td ON ct.MaMon = td.MaMon INNER JOIN hoadon AS hd ON hd.MaHoaDon = ct.MaHoaDon Where ct.MaHoaDon = '" + ma + "' AND hd.GioDen >= '" + d1 + "'";
        } else {
            sql = "Select ct.MaMon, TenMon, DVT, SoLuong, Gia, ct.MaHoaDon From chitiethd AS ct INNER JOIN thucdon AS td ON ct.MaMon = td.MaMon INNER JOIN hoadon AS hd ON hd.MaHoaDon = ct.MaHoaDon Where ct.MaHoaDon = '" + ma + "' AND hd.GioDen BETWEEN '" + d1 + "' AND '" + d2 + "'";
        }
        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            arrDs = new ArrayList<DsOrder>();
            while (rs.next()) {
                DsOrder order = new DsOrder(rs.getString(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getInt(5), rs.getInt(6));
                arrDs.add(order);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Không lấy được danh sách chi tiết hoa đơn !" + ex.toString());
        }
        return arrDs;
    }

    public static void main(String[] args) {
        ConnectDB cdb = new ConnectDB();

    }

}
