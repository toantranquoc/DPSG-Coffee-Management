Create table tbAccount(
	id int not null identity(1,1),
	username varchar(50) not null,
	password varchar(50) not null,
	typeaccount int not null
)


CREATE TABLE ban (
  MaBan int NOT NULL IDENTITY(42, 1),
  TenBan nvarchar(55) NOT NULL,
  TrangThai nvarchar(50) NOT NULL
) 


INSERT INTO ban (MaBan, TenBan, TrangThai) VALUES
(1, N'Bàn 1', N'Đã đặt trước'),
(2, N'Bàn 2', N'Đang phục vụ'),
(3, N'Bàn 3', N'Trống'),
(4, N'Bàn 4', N'Đã đặt trước'),
(5, N'Bàn 5', N'Trống'),
(6, N'Bàn 6', N'Trống'),
(7, N'Bàn 7', N'Trống'),
(8, N'Bàn 8', N'Đang phục vụ'),
(9, N'Bàn 9', N'Trống'),
(10, N'Bàn 10', N'Đang phục vụ'),
(11, N'Bàn 11', N'Đã đặt trước'),
(13, N'Bàn 12', N'Trống'),
(14, N'Bàn 13', N'Đang phục vụ'),
(15, N'Bàn 14', N'Trống'),
(16, N'Bàn 15', N'Trống'),
(17, N'Bàn 16', N'Đang phục vụ'),
(18, N'Bàn 17', N'Trống'),
(19, N'Bàn 18', N'Trống'),
(25, N'Bàn 19', N'Đã đặt trước'),
(31, N'Bàn 20', N'Trống')

-- --------------------------------------------------------

--
-- Table CTHD
--

CREATE TABLE chitiethd (
  MaChiTietHD int NOT NULL IDENTITY(383, 1) ,
  MaHoaDon int NOT NULL,
  MaMon int NOT NULL,
  SoLuong int NOT NULL,
  Gia int NOT NULL
) 

--
-- Dumping data for table chitiethd
--

INSERT INTO chitiethd (MaChiTietHD, MaHoaDon, MaMon, SoLuong, Gia) VALUES
(293, 159, 9, 5, 50000),
(294, 160, 32, 10, 60000),
(295, 161, 40, 12, 20000),
(296, 162, 16, 2, 25000),
(297, 163, 21, 5, 500000),
(298, 164, 15, 1, 25000),
(299, 165, 7, 1, 25000),
(300, 166, 37, 1, 25000),
(302, 168, 7, 1, 25000),
(304, 170, 21, 12, 500000),
(306, 172, 10, 3, 40000),
(307, 173, 21, 2, 500000),
(308, 174, 33, 2, 20000),
(310, 176, 27, 1, 35000),
(311, 177, 14, 1, 20000),
(313, 179, 11, 2, 69000),
(314, 180, 29, 2, 15000),
(315, 181, 20, 1, 15000),
(316, 182, 44, 2, 25000),
(317, 183, 23, 2, 35000),
(318, 184, 17, 1, 20000),
(319, 184, 45, 1, 25000),
(320, 184, 34, 3, 25000),
(321, 184, 35, 2, 25000),
(322, 184, 30, 2, 15000),
(323, 184, 8, 2, 25000),
(324, 185, 26, 1, 30000),
(325, 186, 26, 1, 30000),
(326, 187, 17, 1, 20000),
(327, 188, 34, 1, 25000),
(328, 189, 35, 2, 25000),
(329, 187, 9, 1, 50000),
(330, 187, 36, 3, 25000),
(331, 187, 37, 2, 25000),
(332, 190, 17, 1, 20000),
(333, 191, 20, 1, 15000),
(334, 192, 11, 1, 69000),
(335, 193, 41, 1, 20000),
(342, 195, 15, 1, 15000),
(343, 195, 44, 1, 25000),
(347, 196, 32, 2, 60000),
(348, 197, 32, 2, 60000),
(349, 198, 32, 1, 20000),
(350, 192, 26, 1, 30000),
(351, 192, 27, 1, 35000),
(352, 192, 22, 1, 30000),
(353, 192, 59, 1, 55000),
(354, 192, 57, 1, 15000),
(355, 192, 58, 1, 15000),
(356, 192, 32, 1, 60000),
(357, 192, 33, 1, 20000),
(358, 192, 31, 1, 30000),
(359, 192, 30, 1, 15000),
(360, 192, 28, 1, 250000),
(361, 192, 29, 1, 15000),
(362, 192, 36, 1, 25000),
(363, 192, 9, 1, 50000),
(364, 193, 40, 1, 20000),
(365, 193, 45, 1, 25000),
(366, 193, 16, 1, 25000),
(370, 200, 32, 1, 60000),
(373, 201, 59, 2, 55000),
(374, 201, 40, 2, 20000),
(375, 202, 32, 2, 60000),
(376, 203, 13, 1, 20000),
(377, 204, 21, 2, 200000),
(378, 204, 18, 1, 25000),
(379, 204, 15, 1, 25000),
(380, 204, 40, 1, 20000),
(381, 205, 13, 1, 20000),
(382, 206, 21, 1, 200000);

-- --------------------------------------------------------

--
-- Table Hoa don
--

CREATE TABLE hoadon (
  MaHoaDon int NOT NULL IDENTITY(207, 1) ,
  GiamGia int DEFAULT NULL,
  MaBan int NOT NULL,
  GioDen datetime NOT NULL,
  TongTien int DEFAULT NULL,
  TrangThai int NOT NULL
)



INSERT INTO hoadon (MaHoaDon, GiamGia, MaBan, GioDen, TongTien, TrangThai) VALUES
(159, NULL, 5, '2016-02-17 19:44:48', 250000, 1),
(160, NULL, 17, '2016-01-17 19:45:04', 600000, 1),
(161, NULL, 13, '2016-01-17 19:45:13', 240000, 1),
(162, NULL, 6, '2016-03-17 19:45:20', 50000, 1),
(163, NULL, 9, '2016-03-17 19:45:33', 2500000, 1),
(164, NULL, 1, '2016-03-17 19:50:24', 25000, 1),
(165, NULL, 9, '2016-03-17 19:50:28', 25000, 1),
(166, NULL, 13, '2016-03-17 19:50:33', 25000, 1),
(168, NULL, 7, '2016-02-17 19:50:42', 25000, 1),
(170, NULL, 1, '2016-03-17 20:14:16', 6000000, 1),
(172, NULL, 9, '2016-03-17 20:14:37', 102000, 1),
(173, NULL, 8, '2016-03-17 20:14:47', 1000000, 1),
(174, NULL, 16, '2016-03-17 20:14:59', 40000, 1),
(176, NULL, 1, '2016-03-17 20:15:37', 35000, 1),
(177, 20000, 14, '2016-03-17 23:47:38', 0, 1),
(179, 35, 5, '2016-03-17 23:52:59', 89700, 1),
(180, NULL, 17, '2016-03-17 23:53:14', 30000, 1),
(181, 5, 7, '2016-03-17 23:53:50', 14250, 1),
(182, 30, 2, '2016-03-17 23:54:01', 35000, 1),
(183, 5, 9, '2016-03-18 00:11:27', 66500, 1),
(184, 10, 14, '2015-12-18 00:11:57', 225000, 1),
(185, NULL, 14, '2015-09-18 00:15:15', 30000, 1),
(186, NULL, 17, '2015-07-18 00:15:20', 30000, 1),
(187, 20, 2, '2016-02-18 00:15:25', 156000, 1),
(188, NULL, 8, '2016-01-18 00:15:31', 25000, 1),
(189, NULL, 25, '2016-03-18 00:15:42', 50000, 1),
(190, NULL, 3, '2016-03-18 09:17:29', 20000, 1),
(191, 10, 8, '2016-03-18 09:28:01', 13500, 1),
(192, NULL, 17, '2016-03-18 09:28:05', NULL, 0),
(193, 20000, 6, '2016-03-18 09:28:09', 70000, 1),
(195, 10, 2, '2016-03-18 09:34:47', 36000, 1),
(196, NULL, 18, '2016-03-30 02:37:43', 120000, 1),
(197, 10, 19, '2016-03-30 03:18:39', 108000, 1),
(198, NULL, 16, '2016-03-30 09:25:48', 20000, 1),
(200, 20, 15, '2016-04-01 17:25:54', 48000, 1),
(201, 10000, 9, '2016-04-01 17:27:43', 140000, 1),
(202, NULL, 8, '2016-04-06 11:17:57', NULL, 0),
(203, NULL, 2, '2016-04-06 11:18:04', NULL, 0),
(204, NULL, 10, '2016-04-06 11:18:12', NULL, 0),
(205, NULL, 14, '2016-04-06 11:18:37', NULL, 0),
(206, NULL, 18, '2016-04-06 11:23:10', 200000, 1);

-- --------------------------------------------------------

--
-- Table Nhom mon
--

CREATE TABLE nhommon (
  MaLoai int NOT NULL IDENTITY(16, 1),
  TenLoai nvarchar(55)NOT NULL,
  MauSac nvarchar(50) NOT NULL
) 


INSERT INTO nhommon (MaLoai, TenLoai, MauSac) VALUES
(1, N'Cà phê', '#ac3939'),
(2, N'Nước đóng chai', '#66b3ff'),
(3, N'Nước-Lon', '#9900ff'),
(4, N'Lipton-Trà', '#ffcc00'),
(5, N'Sinh tố', '#40ff00'),
(6, N'Thứ khác', '#e68a00'),
(14, N'Đồ ăn nhanh', '#009966');


--
-- Table Thuc Don
--

CREATE TABLE thucdon (
  MaMon int NOT NULL IDENTITY(66, 1),
  TenMon nvarchar(55) NOT NULL ,
  MaLoai int NOT NULL,
  DonGia int NOT NULL,
  DVT nvarchar(55) NOT NULL
) 

--
-- Insert Data to Table Thuc Don
--

INSERT INTO thucdon (MaMon, TenMon, MaLoai, DonGia, DVT) VALUES
(7, N'Nâu đá', 1, 25000, N'Ly'),
(8, N'Nâu nóng', 1, 25000, N'Ly'),
(9, N'Cafe Sữa', 1, 50000, N'Ly'),
(10, N'Lọc đá vắt chanh', 2, 40000, N'Chậu'),
(11, N'Nâu lắc', 1, 69000, N'Ly'),
(12, N'Trà Xanh ', 2, 25000, N'Chai'),
(13, N'Trà C2', 2, 20000, N'Chai'),
(14, N'Chanh muối', 2, 20000, N'Chai'),
(15, N'Coca Cola', 3, 25000, N'Lon'),
(16, N'RedBull', 3, 25000, N'Lon'),
(17, N'Pepsi', 3, 20000, N'Lon'),
(18, N'Trà Gừng', 4, 25000, N'Ly'),
(19, N'Trà Dilmah', 4, 25000, N'Ly'),
(20, N'Trà chanh', 4, 15000, N'Ly'),
(21, N'Trà My', 4, 200000, N'Bát'),
(22, N'Sinh tố Xoài', 5, 30000, N'Ly'),
(23, N'Sinh tố bơ', 5, 35000, N'Ly'),
(24, N'Sinh tố Dưa Hấu', 5, 30000, N'Ly'),
(25, N'Sinh tố Mãng Cầu', 5, 35000, N'Ly'),
(26, N'Sinh tố chanh leo', 5, 30000, N'Ly'),
(27, N'Sinh tố dưa chuột', 5, 35000, N'Ly'),
(28, N'kẹo cao su', 6, 1000, N'cái'),
(29, N'Hướng Dương', 6, 15000, N'Đĩa'),
(30, N'Khoai chiên', 6, 15000, N'Miếng'),
(31, N'Vina', 6, 30000, N'Bao'),
(32, N'555', 6, 60000, N'Bao'),
(33, N'Thăng Long', 6, 20000, N'Bao'),
(34, N'Cao cao nóng', 1, 25000, N'Ly'),
(35, N'Ca cao nguội', 1, 25000, N'Ly'),
(36, N'Đen đá', 1, 25000, N'Ly'),
(37, N'Đen nóng ', 1, 25000, N'Ly'),
(38, N'Bia Ken', 3, 25000, N'Lon'),
(40, N'Bia Sài Gòn', 3, 20000, N'Lon'),
(41, N'Bia Hà Nội', 3, 20000, N'Lon'),
(44, N'Bia Kenn', 3, 25000, N'Lon'),
(45, N'Bia Ken', 3, 25000, N'Lon'),
(57, N'Mỳ tôm', 14, 15000, N'Bát'),
(58, N'Bánh mỳ pate', 14, 15000, N'Cái'),
(59, N'Mực nướng', 14, 55000, N'Con')



--
-- Indexes for table `ban`
--
ALTER TABLE ban
  ADD PRIMARY KEY (MaBan);

--
-- Indexes for table `chitiethd`
--
ALTER TABLE chitiethd
  ADD PRIMARY KEY (MaChiTietHD)


--
-- Indexes for table `hoadon`
--
ALTER TABLE hoadon
  ADD PRIMARY KEY (MaHoaDon)
  --ADD KEY `MaBan` (`MaBan`);

--
-- Indexes for table `nhommon`
--
ALTER TABLE nhommon
  ADD PRIMARY KEY (MaLoai)

--
-- Indexes for table `taikhoan`

-- Indexes for table `thucdon`
--
ALTER TABLE thucdon
  ADD PRIMARY KEY (MaMon)

----
---- AUTO_INCREMENT for dumped tables
----

----
---- AUTO_INCREMENT for table `ban`
----
--ALTER TABLE ban
--  MODIFY MaBan int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=42;
----
---- AUTO_INCREMENT for table `chitiethd`
----
--ALTER TABLE `chitiethd`
--  MODIFY `MaChiTietHD` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=383;
----
---- AUTO_INCREMENT for table `hoadon`
----
--ALTER TABLE `hoadon`
--  MODIFY `MaHoaDon` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=207;
----
---- AUTO_INCREMENT for table `nhommon`
----
--ALTER TABLE `nhommon`
--  MODIFY `MaLoai` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;
----
---- AUTO_INCREMENT for table `taikhoan`
----
--ALTER TABLE `taikhoan`
--  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;
----
---- AUTO_INCREMENT for table `thucdon`
----
--ALTER TABLE `thucdon`
--  MODIFY `MaMon` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=66;
----
---- Constraints for dumped tables
----

--
-- Constraints for table `chitiethd`
--
ALTER TABLE chitiethd
  ADD CONSTRAINT chitiethd_ibfk_1 FOREIGN KEY (MaHoaDon) REFERENCES hoadon (MaHoaDon)
Alter table chitiethd
  ADD CONSTRAINT chitiethd_ibfk_2 FOREIGN KEY (MaMon) REFERENCES thucdon (MaMon)

--
-- Constraints for table `hoadon`
--
ALTER TABLE hoadon
  ADD CONSTRAINT hoadon_ibfk_1 FOREIGN KEY (MaBan) REFERENCES ban (MaBan)

--
-- Constraints for table `thucdon`
--
ALTER TABLE thucdon
  ADD CONSTRAINT thucdon_ibfk_1 FOREIGN KEY (MaLoai) REFERENCES nhommon (MaLoai)