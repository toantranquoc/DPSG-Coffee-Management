/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BanHangUI;

import Data.ConnectDB;
import MainUI.ManagerForm;
import Models.Ban;
import Models.DsOrder;
import Models.HoaDon;
import Models.ThucDon;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author pc
 */
public class JpGoiMon extends javax.swing.JPanel {

    /**
     * Creates new form JpGoiMon
     */
    String TenBan;
    int MaBan;
    int MaHD, tienmon = 0, tongtien = 0;
    HoaDon hd;
    NumberFormat chuyentien = new DecimalFormat("#,###,###");
    ArrayList<DsOrder> order;
    public static JpGoiMon goimon;
    SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm a");
    public ConnectDB cn = new ConnectDB();

    public JpGoiMon(String trangthai, String tenban, int maban) {

        initComponents();
        goimon = this;
        MaBan = maban;
        TenBan = tenban;
        jpDsMon.setVisible(false);
        jpThongTinThanhToan.setVisible(false);
        jScrollPane1.setVisible(false);

        hd = cn.GetHDbyMaBan(maban);
        if (hd != null) {
            order = cn.GetDsOrder(hd.GetMaHD());
            fillDsMon(0);
            MaHD = hd.GetMaHD();
            lblgioden.setText(df.format(hd.GetGioDen()));
        }
        lblTenBan.setText(tenban);
        lbltrangthai.setText(trangthai);

        if (lbltrangthai.getText().equals("Trống")) {
            btnDatban.setText("Đặt chỗ");
            btnChuyenban.setVisible(false);
            btnGopBan.setVisible(true);
            return;

        }
        if (lbltrangthai.getText().equals("Đã đặt trước")) {
            btnDatban.setText("Hủy đặt");
            btnChuyenban.setVisible(false);
            btnGopBan.setVisible(false);
            return;
        }
        if (lbltrangthai.getText().equals("Đang phục vụ")) {
            btnDatban.setVisible(false);
            btnChuyenban.setVisible(true);
            btnGopBan.setVisible(false);
            JpThucDon thucdon = new JpThucDon();
            thucdon.tenban = TenBan;
            thucdon.maban = maban;
            jpthucdon.removeAll();
            jpthucdon.setLayout(new BorderLayout());
            jpthucdon.add(thucdon);
            jpthucdon.updateUI();
        }
        StringBuilder builder = new StringBuilder(lbltrangthai.getText());
        String tt = builder.substring(0, 4);
        if (tt.equals("Ghép")) {
            btnDatban.setText("Hủy đặt");
            btnChuyenban.setVisible(false);
            btnGopBan.setVisible(false);
            btnGoimon.setVisible(false);
            return;
        }
        String me = builder.substring(0, 3);
        if (me.equals("Gộp")) {
            btnDatban.setText("Hủy đặt");
            btnChuyenban.setVisible(false);
            btnGopBan.setVisible(true);
            btnGoimon.setVisible(true);
        }
    }

    public void fillDsMon(int mahd) {
        if (mahd != 0) {
            order = cn.GetDsOrder(mahd);
            hd = cn.GetHDbyMaBan(MaBan);
            tienmon = 0;
        }
        mahd = MaHD;
        if (order != null) {
            jpDsMon.setVisible(true);
            jpThongTinThanhToan.setVisible(true);
            jScrollPane1.setVisible(true);
            btnChuyenban.setVisible(true);
            btnGopBan.setVisible(false);
            btnGoimon.setText("Thanh toán");

            JPanel[] pn = new JPanel[order.size()];
            jpDsMon.removeAll();
            jpDsMon.setLayout(new BoxLayout(jpDsMon, BoxLayout.Y_AXIS));
            for (int i = 0; i < order.size(); i++) {
                tienmon += order.get(i).GetGia() * order.get(i).GetSoLuong();
                pn[i] = new JPanel();
                pn[i].setName(order.get(i).GetMaMon());
                pn[i].setCursor(new Cursor(Cursor.HAND_CURSOR));
                pn[i].setBorder(BorderFactory.createLineBorder(Color.darkGray, 1));
                pn[i].setBackground(Color.decode("#b3ff99"));
                pn[i].setLayout(new GridLayout());
                pn[i].setPreferredSize(new Dimension(270, 50));
                pn[i].setMaximumSize(new Dimension(270, 50));
                pn[i].setMinimumSize(new Dimension(270, 50));
                pn[i].add(new JLabel(order.get(i).GetTenMon(), JLabel.CENTER)).setForeground(Color.decode("#001a66"));
                pn[i].add(new JLabel(String.valueOf(chuyentien.format(order.get(i).GetGia())) + " VNĐ", JLabel.CENTER)).setForeground(Color.decode("#ff0000"));
                pn[i].add(new JLabel(String.valueOf(order.get(i).GetSoLuong()) + " " + order.get(i).GetDVT(), JLabel.RIGHT)).setForeground(Color.decode("#008000"));
                Icon ic = new ImageIcon(getClass().getResource("/Images/huymon.png"));
                JLabel lbl = new JLabel("", ic, JLabel.CENTER);
                lbl.setForeground(Color.decode("#b3ff99"));
                lbl.setName(order.get(i).GetMaMon());
                pn[i].add(lbl).addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        int qs;
                        ArrayList<ThucDon> td = cn.GetThucDonByMa(e.getComponent().getName());

                        qs = JOptionPane.showConfirmDialog(null, "Hủy món: " + td.get(0).GetTenMon() + " ?", "Hủy món", JOptionPane.YES_NO_OPTION);
                        if (qs == JOptionPane.YES_OPTION) {
                            int xoa = cn.DeleteMon(e.getComponent().getName(), MaHD, MaBan);
                            if (xoa == 1) {
                                fillDsMon(MaHD);
                            }
                            if (xoa == 2) {
                                fillDsMon(MaHD);
                                HuyHD();
                            }
                        }
                    }
                });
                pn[i].addMouseListener(new MouseAdapter() {
                    @Override
                    public void mousePressed(MouseEvent e) {
                        DlSoLuong sl = new DlSoLuong(MainUI.ManagerForm.main, true, e.getComponent().getName(), TenBan, MaBan);
                        sl.setVisible(true);
                    }
                });
                jpDsMon.add(pn[i]);
                jpDsMon.updateUI();

            }

            fillThongTin();
        }
    }

    public void fillThongTin() {
        hd = cn.GetHDbyMaBan(MaBan);
        MaHD = hd.GetMaHD();
        int ck = cn.CheckDsMon(MaHD, MaBan);
        btnGoimon.setVisible(true);
        if (ck == 0) {
            HuyHD();
            btnGoimon.setVisible(false);
        }

        if (hd.GetGiamGia() > 100) {
            lblgiamgia.setText(chuyentien.format(hd.GetGiamGia()) + " VNĐ");
            if (tienmon - hd.GetGiamGia() <= 0) {
                tongtien = 0;
                lbltongtien.setText("0 VNĐ");
            } else {
                tongtien = tienmon - hd.GetGiamGia();
                lbltongtien.setText(String.valueOf(chuyentien.format(tongtien)) + " VNĐ");
            }
        }
        if (hd.GetGiamGia() <= 100) {
            tongtien = tienmon - (tienmon * hd.GetGiamGia() / 100);
            lbltongtien.setText(String.valueOf(chuyentien.format(tongtien)) + " VNĐ");
            lblgiamgia.setText(String.valueOf(hd.GetGiamGia()) + " %");
        }
        lbltienmon.setText(String.valueOf(chuyentien.format(tienmon)) + " VNĐ");
    }

    public void HuyHD() {

        JButton btnhuy = new JButton("Hủy bàn");
        btnhuy.setPreferredSize(new Dimension(100, 40));
        btnhuy.setBounds(100, 50, 100, 40);
        jpDsMon.setLayout(null);
        btnGoimon.setVisible(false);
        jpThongTinThanhToan.setVisible(false);
        btnChuyenban.setVisible(false);
        btnGopBan.setVisible(true);
        btnhuy.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                Ban b = new Ban();
                b.SetTrangThai("Trống");
                b.SetMaBan(MaBan);
                cn.UpDateTrangThaiBan(b);

                JpBanhang.banhang.fillBan();
                JpGoiMon.goimon.removeAll();
                //JpBanhang.banhang.fillLb();

                HoaDon hd = new HoaDon();
                hd.SetMaHD(MaHD);
                cn.HuyHD(hd);
            }
        });
        jpDsMon.add(btnhuy);
        jpDsMon.updateUI();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jpThongTinBan = new javax.swing.JPanel();
        lblTenBan = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        lblgioden = new javax.swing.JLabel();
        lbltrangthai = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        btnDatban = new javax.swing.JButton();
        btnGoimon = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        jpThongTinThanhToan = new javax.swing.JPanel();
        lbltongtien = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        lblgiamgia = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        lbltienmon = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jpDsMon = new javax.swing.JPanel();
        btnChuyenban = new javax.swing.JButton();
        btnGopBan = new javax.swing.JButton();
        jpthucdon = new javax.swing.JPanel();

        setPreferredSize(new java.awt.Dimension(724, 521));

        jpThongTinBan.setBackground(Color.decode("#e6e6e6"));
        jpThongTinBan.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jpThongTinBan.setAutoscrolls(true);

        lblTenBan.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        lblTenBan.setForeground(new java.awt.Color(102, 51, 0));
        lblTenBan.setText("Bàn 1");

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(51, 51, 0));
        jLabel2.setText("Giờ đến:");

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(102, 0, 0));
        jLabel3.setText("Trạng thái");

        lblgioden.setText(".....");

        lbltrangthai.setText("....");

        jPanel1.setBackground(Color.decode("#e6e6e6"));

        btnDatban.setFont(new java.awt.Font("Tahoma", 1, 9)); // NOI18N
        btnDatban.setForeground(new java.awt.Color(102, 51, 0));
        btnDatban.setText("Đặt bàn");
        btnDatban.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnDatban.setPreferredSize(new java.awt.Dimension(100, 40));
        btnDatban.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDatbanActionPerformed(evt);
            }
        });
        jPanel1.add(btnDatban);

        btnGoimon.setFont(new java.awt.Font("Tahoma", 1, 9)); // NOI18N
        btnGoimon.setForeground(new java.awt.Color(102, 51, 0));
        btnGoimon.setText("Gọi món");
        btnGoimon.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnGoimon.setPreferredSize(new java.awt.Dimension(100, 40));
        btnGoimon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGoimonActionPerformed(evt);
            }
        });
        jPanel1.add(btnGoimon);

        jSeparator1.setBackground(Color.decode("#e6e6e6"));
        jSeparator1.setForeground(new java.awt.Color(21, 75, 158));

        jpThongTinThanhToan.setBackground(Color.decode("#e6e6e6"));
        jpThongTinThanhToan.setAutoscrolls(true);
        jpThongTinThanhToan.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jpThongTinThanhToan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jpThongTinThanhToanMousePressed(evt);
            }
        });

        lbltongtien.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lbltongtien.setForeground(new java.awt.Color(255, 0, 0));
        lbltongtien.setText("0");

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(51, 0, 51));
        jLabel8.setText("Giảm giá:");

        lblgiamgia.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        lblgiamgia.setForeground(new java.awt.Color(51, 0, 51));
        lblgiamgia.setText("0");

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(51, 0, 51));
        jLabel7.setText("Tiền món:");

        lbltienmon.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        lbltienmon.setText("0");

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 0, 0));
        jLabel9.setText("THÀNH TIỀN:");

        javax.swing.GroupLayout jpThongTinThanhToanLayout = new javax.swing.GroupLayout(jpThongTinThanhToan);
        jpThongTinThanhToan.setLayout(jpThongTinThanhToanLayout);
        jpThongTinThanhToanLayout.setHorizontalGroup(
            jpThongTinThanhToanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpThongTinThanhToanLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpThongTinThanhToanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpThongTinThanhToanLayout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addGroup(jpThongTinThanhToanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jpThongTinThanhToanLayout.createSequentialGroup()
                                .addComponent(jLabel8)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(lblgiamgia, javax.swing.GroupLayout.DEFAULT_SIZE, 25, Short.MAX_VALUE))
                            .addGroup(jpThongTinThanhToanLayout.createSequentialGroup()
                                .addComponent(jLabel7)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lbltienmon, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGap(23, 23, 23))
                    .addGroup(jpThongTinThanhToanLayout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lbltongtien, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(44, 44, 44))
        );
        jpThongTinThanhToanLayout.setVerticalGroup(
            jpThongTinThanhToanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpThongTinThanhToanLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpThongTinThanhToanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(lbltienmon))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jpThongTinThanhToanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(lblgiamgia))
                .addGap(18, 18, 18)
                .addGroup(jpThongTinThanhToanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(lbltongtien))
                .addContainerGap())
        );

        jScrollPane1.setBackground(Color.decode("#e6e6e6"));
        jScrollPane1.setBorder(null);
        jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        jpDsMon.setBackground(Color.decode("#e6e6e6"));
        jpDsMon.setMaximumSize(new java.awt.Dimension(32767, 400));
        jpDsMon.setOpaque(false);

        javax.swing.GroupLayout jpDsMonLayout = new javax.swing.GroupLayout(jpDsMon);
        jpDsMon.setLayout(jpDsMonLayout);
        jpDsMonLayout.setHorizontalGroup(
            jpDsMonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 297, Short.MAX_VALUE)
        );
        jpDsMonLayout.setVerticalGroup(
            jpDsMonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 265, Short.MAX_VALUE)
        );

        jScrollPane1.setViewportView(jpDsMon);

        btnChuyenban.setFont(new java.awt.Font("Tahoma", 1, 9)); // NOI18N
        btnChuyenban.setForeground(new java.awt.Color(102, 51, 0));
        btnChuyenban.setText("Chuyển bàn");
        btnChuyenban.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnChuyenban.setPreferredSize(new java.awt.Dimension(100, 40));
        btnChuyenban.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnChuyenbanActionPerformed(evt);
            }
        });

        btnGopBan.setFont(new java.awt.Font("Tahoma", 1, 9)); // NOI18N
        btnGopBan.setForeground(new java.awt.Color(102, 51, 0));
        btnGopBan.setText("Gộp bàn");
        btnGopBan.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnGopBan.setPreferredSize(new java.awt.Dimension(100, 40));
        btnGopBan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGopBanActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jpThongTinBanLayout = new javax.swing.GroupLayout(jpThongTinBan);
        jpThongTinBan.setLayout(jpThongTinBanLayout);
        jpThongTinBanLayout.setHorizontalGroup(
            jpThongTinBanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpThongTinBanLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jpThongTinBanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jpThongTinBanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpThongTinBanLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(lblgioden, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(lbltrangthai, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(33, 33, 33))
            .addGroup(jpThongTinBanLayout.createSequentialGroup()
                .addGap(53, 53, 53)
                .addComponent(jpThongTinThanhToan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jpThongTinBanLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(jpThongTinBanLayout.createSequentialGroup()
                .addGroup(jpThongTinBanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpThongTinBanLayout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 224, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jpThongTinBanLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 263, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jpThongTinBanLayout.createSequentialGroup()
                        .addGap(91, 91, 91)
                        .addComponent(lblTenBan, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jpThongTinBanLayout.createSequentialGroup()
                        .addGap(38, 38, 38)
                        .addComponent(btnChuyenban, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnGopBan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jpThongTinBanLayout.setVerticalGroup(
            jpThongTinBanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpThongTinBanLayout.createSequentialGroup()
                .addContainerGap(24, Short.MAX_VALUE)
                .addComponent(lblTenBan)
                .addGap(26, 26, 26)
                .addGroup(jpThongTinBanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(lblgioden))
                .addGap(18, 18, 18)
                .addGroup(jpThongTinBanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(lbltrangthai))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(23, 23, 23)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 8, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(3, 3, 3)
                .addComponent(jpThongTinThanhToan, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpThongTinBanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnGopBan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnChuyenban, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout jpthucdonLayout = new javax.swing.GroupLayout(jpthucdon);
        jpthucdon.setLayout(jpthucdonLayout);
        jpthucdonLayout.setHorizontalGroup(
            jpthucdonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 469, Short.MAX_VALUE)
        );
        jpthucdonLayout.setVerticalGroup(
            jpthucdonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jpThongTinBan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jpthucdon, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jpThongTinBan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jpthucdon, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnDatbanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDatbanActionPerformed
        StringBuilder builder = new StringBuilder(lbltrangthai.getText());
        String tt = builder.substring(0, 4);
        String me = builder.substring(0, 3);
        if (tt.equals("Ghép")) {
            lbltrangthai.setText("Trống");
            btnDatban.setText("Đặt chỗ");
            btnChuyenban.setVisible(false);
            btnGopBan.setVisible(false);
            String TrangThai = "Trống";
            Ban b = new Ban(MaBan, TenBan, TrangThai);
            boolean Update = cn.UpdateBan(b);
            goimon.removeAll();
            goimon.updateUI();
            JpBanhang.banhang.fillBan();
            JpBanhang.banhang.updateUI();
        } else {
            if (me.equals("Gộp")) {
                lbltrangthai.setText("Trống");
                btnDatban.setText("Đặt chỗ");
                btnChuyenban.setVisible(false);
                btnGopBan.setVisible(false);
                String TrangThai = "Trống";
                Ban b = new Ban(MaBan, TenBan, TrangThai);
                boolean Update = cn.UpdateBan(b);
                if (cn.UpdateBanCon(String.valueOf(MaBan))) {
                   System.out.println("Hủy bàn thành công!");
                }
                goimon.removeAll();
                goimon.updateUI();
                JpBanhang.banhang.fillBan();
                JpBanhang.banhang.updateUI();
            } else {
                if (lbltrangthai.getText().equals("Đã đặt trước")) {
                    lbltrangthai.setText("Trống");
                    btnDatban.setText("Đặt chỗ");
                    btnChuyenban.setVisible(false);
                    btnGopBan.setVisible(false);
                    String TrangThai = "Trống";
                    Ban b = new Ban(MaBan, TenBan, TrangThai);
                    boolean Update = cn.UpdateBan(b);
                    //goimon.removeAll();
                    goimon.updateUI();
                    JpBanhang.banhang.fillBan();
                    JpBanhang.banhang.updateUI();
                } else {
                    lbltrangthai.setText("Đã đặt trước");
                    btnDatban.setText("Hủy đặt");
                    String TrangThai = "Đã đặt trước";
                    Ban b = new Ban(MaBan, TenBan, TrangThai);
                    boolean Update = cn.UpdateBan(b);
                    //goimon.removeAll();
                    goimon.updateUI();
                    JpBanhang.banhang.fillBan();
                    JpBanhang.banhang.updateUI();
                    //            Run.QlCafe.reloadPanel(1, MaBan);
                }
            }
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_btnDatbanActionPerformed

    private void btnGoimonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGoimonActionPerformed
        if (btnGoimon.getText().equals("Hủy bàn")) {

            JpThucDon.thucdon.removeAll();
            JpThucDon.thucdon.updateUI();
            jpThongTinThanhToan.setVisible(false);
            btnChuyenban.setVisible(false);
            btnGopBan.setVisible(false);
            lblgioden.setText("......");
            lbltrangthai.setText("Trống");
            String TrangThai = "Trống";
            Ban b = new Ban(MaBan, lblTenBan.getText(), TrangThai);
            if (cn.UpdateBan(b)) {
                JpBanhang.banhang.fillBan();
                btnGoimon.setText("Gọi món");
                btnDatban.setVisible(true);
                btnDatban.setText("Đặt bàn");
                return;
            }

        }
        if (btnGoimon.getText().equals("Thanh toán")) {
            DlThanhToan thanhtoan = new DlThanhToan(ManagerForm.main, true, tongtien, TenBan, MaBan, MaHD);//tongtien trang thai ban ten ban
            thanhtoan.setVisible(true);
            btnChuyenban.setVisible(true);
            btnGopBan.setVisible(false);
            return;
        }
        if (btnGoimon.getText().equals("Gọi món")) {
            jpthucdon.setVisible(true);
            Date date = new Date();
            SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm a");
            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            lblgioden.setText(df.format(date));
            lbltrangthai.setText("Đang phục vụ");
            btnDatban.setVisible(false);
            btnChuyenban.setVisible(true);
            btnGopBan.setVisible(true);
            btnGoimon.setText("Hủy bàn");

            JpThucDon thucdon;
            thucdon = new JpThucDon();
            thucdon.maban = MaBan;
            thucdon.tenban = TenBan;

            thucdon.gioden = sf.format(date);
            jpthucdon.removeAll();
            jpthucdon.setLayout(new BorderLayout());
            jpthucdon.add(thucdon);
            jpthucdon.revalidate();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_btnGoimonActionPerformed

    private void jpThongTinThanhToanMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jpThongTinThanhToanMousePressed
        DlGiamGia giam = new DlGiamGia(ManagerForm.main, true, MaHD, TenBan, tienmon);
        giam.setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_jpThongTinThanhToanMousePressed

    private void btnChuyenbanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnChuyenbanActionPerformed
        // TODO add your handling code here:
        DlChuyenBan chuyenban = new DlChuyenBan(ManagerForm.main, true, MaBan, MaHD, TenBan);
        chuyenban.setVisible(true);
    }//GEN-LAST:event_btnChuyenbanActionPerformed

    private void btnGopBanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGopBanActionPerformed
        // TODO add your handling code here:
        DlGopBan gopban = new DlGopBan(ManagerForm.main, true, MaBan, MaHD, TenBan);
        gopban.setVisible(true);
    }//GEN-LAST:event_btnGopBanActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnChuyenban;
    private javax.swing.JButton btnDatban;
    private javax.swing.JButton btnGoimon;
    private javax.swing.JButton btnGopBan;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JPanel jpDsMon;
    private javax.swing.JPanel jpThongTinBan;
    private javax.swing.JPanel jpThongTinThanhToan;
    private javax.swing.JPanel jpthucdon;
    private javax.swing.JLabel lblTenBan;
    private javax.swing.JLabel lblgiamgia;
    private javax.swing.JLabel lblgioden;
    private javax.swing.JLabel lbltienmon;
    private javax.swing.JLabel lbltongtien;
    private javax.swing.JLabel lbltrangthai;
    // End of variables declaration//GEN-END:variables

}
