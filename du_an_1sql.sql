USE [master]
GO
/****** Object:  Database [QLBH1]    Script Date: 4/13/2019 7:08:14 AM ******/
CREATE DATABASE [QLBH1]
 CONTAINMENT = NONE
 ON  PRIMARY 
( NAME = N'QLBH', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL11.SQLEXPRESS\MSSQL\DATA\QLBH.mdf' , SIZE = 5120KB , MAXSIZE = UNLIMITED, FILEGROWTH = 1024KB )
 LOG ON 
( NAME = N'QLBH_log', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL11.SQLEXPRESS\MSSQL\DATA\QLBH_log.ldf' , SIZE = 2048KB , MAXSIZE = 2048GB , FILEGROWTH = 10%)
GO
ALTER DATABASE [QLBH] SET COMPATIBILITY_LEVEL = 110
GO
IF (1 = FULLTEXTSERVICEPROPERTY('IsFullTextInstalled'))
begin
EXEC [QLBH].[dbo].[sp_fulltext_database] @action = 'enable'
end
GO
ALTER DATABASE [QLBH] SET ANSI_NULL_DEFAULT OFF 
GO
ALTER DATABASE [QLBH] SET ANSI_NULLS OFF 
GO
ALTER DATABASE [QLBH] SET ANSI_PADDING OFF 
GO
ALTER DATABASE [QLBH] SET ANSI_WARNINGS OFF 
GO
ALTER DATABASE [QLBH] SET ARITHABORT OFF 
GO
ALTER DATABASE [QLBH] SET AUTO_CLOSE OFF 
GO
ALTER DATABASE [QLBH] SET AUTO_CREATE_STATISTICS ON 
GO
ALTER DATABASE [QLBH] SET AUTO_SHRINK OFF 
GO
ALTER DATABASE [QLBH] SET AUTO_UPDATE_STATISTICS ON 
GO
ALTER DATABASE [QLBH] SET CURSOR_CLOSE_ON_COMMIT OFF 
GO
ALTER DATABASE [QLBH] SET CURSOR_DEFAULT  GLOBAL 
GO
ALTER DATABASE [QLBH] SET CONCAT_NULL_YIELDS_NULL OFF 
GO
ALTER DATABASE [QLBH] SET NUMERIC_ROUNDABORT OFF 
GO
ALTER DATABASE [QLBH] SET QUOTED_IDENTIFIER OFF 
GO
ALTER DATABASE [QLBH] SET RECURSIVE_TRIGGERS OFF 
GO
ALTER DATABASE [QLBH] SET  DISABLE_BROKER 
GO
ALTER DATABASE [QLBH] SET AUTO_UPDATE_STATISTICS_ASYNC OFF 
GO
ALTER DATABASE [QLBH] SET DATE_CORRELATION_OPTIMIZATION OFF 
GO
ALTER DATABASE [QLBH] SET TRUSTWORTHY OFF 
GO
ALTER DATABASE [QLBH] SET ALLOW_SNAPSHOT_ISOLATION OFF 
GO
ALTER DATABASE [QLBH] SET PARAMETERIZATION SIMPLE 
GO
ALTER DATABASE [QLBH] SET READ_COMMITTED_SNAPSHOT OFF 
GO
ALTER DATABASE [QLBH] SET HONOR_BROKER_PRIORITY OFF 
GO
ALTER DATABASE [QLBH] SET RECOVERY SIMPLE 
GO
ALTER DATABASE [QLBH] SET  MULTI_USER 
GO
ALTER DATABASE [QLBH] SET PAGE_VERIFY CHECKSUM  
GO
ALTER DATABASE [QLBH] SET DB_CHAINING OFF 
GO
ALTER DATABASE [QLBH] SET FILESTREAM( NON_TRANSACTED_ACCESS = OFF ) 
GO
ALTER DATABASE [QLBH] SET TARGET_RECOVERY_TIME = 0 SECONDS 
GO
USE [QLBH]
GO
/****** Object:  StoredProcedure [dbo].[dh_tusinh]    Script Date: 4/13/2019 7:08:14 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
create proc [dbo].[dh_tusinh]
as
begin
declare @ma_next nvarchar(50)
declare @max int
select @max=COUNT(madh) + 1 from Donhang where MaDh like 'AA'
set @ma_next ='AA' + RIGHT('O' + CAST(@max as varchar(10)),10)
while(exists(select madh from Donhang where madh=@ma_next))
begin
	set @max=@max+1
	set @ma_next='AA' + RIGHT('O' + CAST(@max as varchar(18)),18)
end
select @ma_next
end

GO
/****** Object:  Table [dbo].[Donhang]    Script Date: 4/13/2019 7:08:14 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Donhang](
	[MaDh] [nvarchar](50) NOT NULL,
	[MatheKH] [nvarchar](50) NOT NULL,
	[Ngaymua] [nvarchar](50) NULL,
	[Trangthai] [nvarchar](50) NULL,
	[Manv] [nvarchar](50) NULL,
 CONSTRAINT [PK_Donhang] PRIMARY KEY CLUSTERED 
(
	[MaDh] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
/****** Object:  Table [dbo].[DonHangChiTiet]    Script Date: 4/13/2019 7:08:14 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[DonHangChiTiet](
	[MaDHCT] [int] IDENTITY(1,1) NOT NULL,
	[Masp] [nvarchar](50) NULL,
	[Soluong] [int] NOT NULL,
	[MaDH] [nvarchar](50) NULL,
 CONSTRAINT [PK_DH_ChiTiet] PRIMARY KEY CLUSTERED 
(
	[MaDHCT] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
/****** Object:  Table [dbo].[Khachhang]    Script Date: 4/13/2019 7:08:14 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Khachhang](
	[MatheKH] [nvarchar](50) NOT NULL,
	[Hovaten] [nvarchar](50) NULL,
	[Diachi] [nvarchar](50) NULL,
	[Dienthoai] [nvarchar](50) NULL,
	[Email] [nvarchar](70) NULL,
	[CMTND] [nvarchar](50) NULL,
	[Gioitinh] [bit] NULL,
 CONSTRAINT [PK_Khachhang] PRIMARY KEY CLUSTERED 
(
	[MatheKH] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
/****** Object:  Table [dbo].[Nhanvien]    Script Date: 4/13/2019 7:08:14 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Nhanvien](
	[MaNV] [nvarchar](50) NOT NULL,
	[Tennv] [nvarchar](50) NULL,
	[Sodienthoai] [nvarchar](50) NULL,
	[DiaChi] [nvarchar](50) NULL,
	[Gioitinh] [bit] NULL,
	[Email] [nvarchar](50) NULL,
	[UserName] [nvarchar](50) NULL,
	[PassWord] [nvarchar](50) NULL,
	[MaNhomQuyen] [nvarchar](50) NULL,
 CONSTRAINT [PK_Nhanvien] PRIMARY KEY CLUSTERED 
(
	[MaNV] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
/****** Object:  Table [dbo].[NhapKho]    Script Date: 4/13/2019 7:08:14 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[NhapKho](
	[maPhieu] [nvarchar](50) NOT NULL,
	[hangCungCap] [nvarchar](50) NULL,
	[ngayNhap] [nvarchar](50) NULL,
	[tongTien] [float] NULL,
	[daTra] [float] NULL,
	[conNo] [float] NULL,
	[MaNV] [nvarchar](50) NULL,
	[ghiChu] [nvarchar](50) NULL,
 CONSTRAINT [PK_NhapKho] PRIMARY KEY CLUSTERED 
(
	[maPhieu] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
/****** Object:  Table [dbo].[NhomQuyen]    Script Date: 4/13/2019 7:08:14 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[NhomQuyen](
	[MaNhomQuyen] [nvarchar](50) NOT NULL,
	[TenNhomQuyen] [nvarchar](50) NULL,
 CONSTRAINT [PK_NhomQuyen] PRIMARY KEY CLUSTERED 
(
	[MaNhomQuyen] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
/****** Object:  Table [dbo].[SanPham]    Script Date: 4/13/2019 7:08:14 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[SanPham](
	[Masp] [nvarchar](50) NOT NULL,
	[Tensp] [nvarchar](50) NULL,
	[donvi] [nvarchar](50) NULL,
	[gianhap] [float] NULL,
	[giaban] [float] NULL,
	[hangsp] [nvarchar](50) NULL,
	[soluongnhapkho] [int] NULL,
	[chuthich] [nvarchar](50) NULL,
 CONSTRAINT [PK_SanPham2] PRIMARY KEY CLUSTERED 
(
	[Masp] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
/****** Object:  Table [dbo].[SanPhamBackup]    Script Date: 4/13/2019 7:08:14 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[SanPhamBackup](
	[Masp] [nvarchar](50) NOT NULL,
	[TenSp] [nvarchar](50) NULL,
	[Soluong] [int] NULL,
	[Dongia] [float] NULL,
	[Loai] [nvarchar](50) NULL,
 CONSTRAINT [PK_SanPham] PRIMARY KEY CLUSTERED 
(
	[Masp] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
INSERT [dbo].[Donhang] ([MaDh], [MatheKH], [Ngaymua], [Trangthai], [Manv]) VALUES (N'DH000', N'KH001', N'3/12/2018', N'Đã Trả Tiền', N'nv1')
INSERT [dbo].[Donhang] ([MaDh], [MatheKH], [Ngaymua], [Trangthai], [Manv]) VALUES (N'DH001', N'TUDO', N'4/13/2019', N'Đã Trả Tiền', N'nv1')
INSERT [dbo].[Donhang] ([MaDh], [MatheKH], [Ngaymua], [Trangthai], [Manv]) VALUES (N'DH002', N'TUDO', N'4/13/2019', N'Đã Trả Tiền', N'nv1')
SET IDENTITY_INSERT [dbo].[DonHangChiTiet] ON 

INSERT [dbo].[DonHangChiTiet] ([MaDHCT], [Masp], [Soluong], [MaDH]) VALUES (1092, N'sp1', 33, N'DH001')
INSERT [dbo].[DonHangChiTiet] ([MaDHCT], [Masp], [Soluong], [MaDH]) VALUES (1093, N'Sp3', 9, N'DH001')
INSERT [dbo].[DonHangChiTiet] ([MaDHCT], [Masp], [Soluong], [MaDH]) VALUES (1094, N'Sp3', 22, N'DH002')
INSERT [dbo].[DonHangChiTiet] ([MaDHCT], [Masp], [Soluong], [MaDH]) VALUES (1095, N'Sp3', 22, N'DH002')
INSERT [dbo].[DonHangChiTiet] ([MaDHCT], [Masp], [Soluong], [MaDH]) VALUES (1096, N'Sp3', 22, N'DH002')
SET IDENTITY_INSERT [dbo].[DonHangChiTiet] OFF
INSERT [dbo].[Khachhang] ([MatheKH], [Hovaten], [Diachi], [Dienthoai], [Email], [CMTND], [Gioitinh]) VALUES (N'KH001', N'Nguyễn Quang Thanh', N'Hàm Nghi-Hà Nội', N'0251441552', N'thanhnt@gmail.com', N'265654686', 1)
INSERT [dbo].[Khachhang] ([MatheKH], [Hovaten], [Diachi], [Dienthoai], [Email], [CMTND], [Gioitinh]) VALUES (N'KH005', N'Nguyễn Văn Hồng Duy', N'Mĩ Đình -Hà Nội', N'0971442155', N'duyvnhdtamctd@gmail.com', N'975752727', 1)
INSERT [dbo].[Khachhang] ([MatheKH], [Hovaten], [Diachi], [Dienthoai], [Email], [CMTND], [Gioitinh]) VALUES (N'KH008', N'Phạm Quí Nghiệp', N'Hoàn Kiếm-Hà Nội', N'0564645645', N'nghieppq@gmail.com', N'172727555', 0)
INSERT [dbo].[Khachhang] ([MatheKH], [Hovaten], [Diachi], [Dienthoai], [Email], [CMTND], [Gioitinh]) VALUES (N'KH009', N'Quynh Thị Hà Sâm', N'Quốc Oai-Hà Nội', N'5656565669', N'samqth@gmail.com', N'999999999', 0)
INSERT [dbo].[Khachhang] ([MatheKH], [Hovaten], [Diachi], [Dienthoai], [Email], [CMTND], [Gioitinh]) VALUES (N'KH012', N'Đào Xuân Sơn', N'Tây Hồ', N'0646454658', N'sondxx@gmail.com', N'666666666', 0)
INSERT [dbo].[Khachhang] ([MatheKH], [Hovaten], [Diachi], [Dienthoai], [Email], [CMTND], [Gioitinh]) VALUES (N'KH013', N'Dư Chánh Tín', N'Hỏa Lò-Hà Nội', N'0574685455', N'tindc@gmail.com', N'375373977', 1)
INSERT [dbo].[Khachhang] ([MatheKH], [Hovaten], [Diachi], [Dienthoai], [Email], [CMTND], [Gioitinh]) VALUES (N'KH015', N'Văn Đăng Thương', N'Tràng Tiên', N'0567345454', N'thuongvdd@gmail.com', N'512727888', 0)
INSERT [dbo].[Khachhang] ([MatheKH], [Hovaten], [Diachi], [Dienthoai], [Email], [CMTND], [Gioitinh]) VALUES (N'KH016', N'Vũ Kiều Hoài', N'Hai Bà Trưng', N'1111111111', N'hoaivk@gmail.com', N'172782855', 1)
INSERT [dbo].[Khachhang] ([MatheKH], [Hovaten], [Diachi], [Dienthoai], [Email], [CMTND], [Gioitinh]) VALUES (N'KH018', N'Đào Đức Mạnh', N'Nam Cao', N'0348484884', N'manhddd@gmail.com', N'174275279', 1)
INSERT [dbo].[Khachhang] ([MatheKH], [Hovaten], [Diachi], [Dienthoai], [Email], [CMTND], [Gioitinh]) VALUES (N'KH019', N'Huỳnh Trọng Khiêm', N'Hàng Bông', N'0244848489', N'khiemht@gmail.com', N'275297782', 1)
INSERT [dbo].[Khachhang] ([MatheKH], [Hovaten], [Diachi], [Dienthoai], [Email], [CMTND], [Gioitinh]) VALUES (N'TUDO', N'Trống', N'Trống', N'Trống', N'Trống', N'Trống', 1)
INSERT [dbo].[Nhanvien] ([MaNV], [Tennv], [Sodienthoai], [DiaChi], [Gioitinh], [Email], [UserName], [PassWord], [MaNhomQuyen]) VALUES (N'Nv1', N'Nguyễn Khắc Thanh', N'0955414221', N'Tây Hồ-Hà Nội', 1, N'thanhnk@gmail.com', N'thanh', N'fcea920f7412b5da7be0cf42b8c93759', N'admin')
INSERT [dbo].[Nhanvien] ([MaNV], [Tennv], [Sodienthoai], [DiaChi], [Gioitinh], [Email], [UserName], [PassWord], [MaNhomQuyen]) VALUES (N'Nv2', N'Nguyễn Khắc Thanh', N'0955414221', N'Tây Hồ-Hà Nội', 1, N'thanhnk@gmail.com', N'thanh2', N'123456', N'staff')
INSERT [dbo].[Nhanvien] ([MaNV], [Tennv], [Sodienthoai], [DiaChi], [Gioitinh], [Email], [UserName], [PassWord], [MaNhomQuyen]) VALUES (N'Nv20', N'minh', N'1111111111', N'ha noi', 1, N'tt@gmail.com', N'minh', N'25d55ad283aa400af464c76d713c07ad', N'admin')
INSERT [dbo].[Nhanvien] ([MaNV], [Tennv], [Sodienthoai], [DiaChi], [Gioitinh], [Email], [UserName], [PassWord], [MaNhomQuyen]) VALUES (N'Nv3', N'Vũ Đức Trung', N'0144555122', N'Mỹ Đình-Hà Nội', 1, N'trungvd@gmail.com', N'trung', N'123456', N'admin')
INSERT [dbo].[Nhanvien] ([MaNV], [Tennv], [Sodienthoai], [DiaChi], [Gioitinh], [Email], [UserName], [PassWord], [MaNhomQuyen]) VALUES (N'Nv4', N'Nguyễn Quý Hoàng', N'0521425211', N'Cầu Giấy-Hà Nội', 1, N'hoangnq@gmail.com', N'hoang', N'123456', N'admin')
INSERT [dbo].[Nhanvien] ([MaNV], [Tennv], [Sodienthoai], [DiaChi], [Gioitinh], [Email], [UserName], [PassWord], [MaNhomQuyen]) VALUES (N'Nv5', N'Nguyễn Tuấn Anh', N'0112525112', N'Cầu Giấy-Hà Nội', 1, N'Anhnt@gmail.com', N'anh', N'123456', N'admin')
INSERT [dbo].[Nhanvien] ([MaNV], [Tennv], [Sodienthoai], [DiaChi], [Gioitinh], [Email], [UserName], [PassWord], [MaNhomQuyen]) VALUES (N'Nv8', N'Nguyễn Tiến Luật', N'0164554112', N'Hà Đông-Hà Nội', 1, N'TL@fpt.vn', N'luat', N'123456', N'admin')
INSERT [dbo].[Nhanvien] ([MaNV], [Tennv], [Sodienthoai], [DiaChi], [Gioitinh], [Email], [UserName], [PassWord], [MaNhomQuyen]) VALUES (N'Nv9', N'Trương Trọng Hiếu', N'0971447638', N'Vĩnh Tường - Vĩnh Phúc', 1, N'hieuttph06748@fpt.edu.vn', N'hieu', N'123456', N'admin')
INSERT [dbo].[Nhanvien] ([MaNV], [Tennv], [Sodienthoai], [DiaChi], [Gioitinh], [Email], [UserName], [PassWord], [MaNhomQuyen]) VALUES (N'Nv98', N'gg', N'0234234234', N'hn', 1, N'gg@gg.gg', N'gg', N'12', N'staff')
INSERT [dbo].[Nhanvien] ([MaNV], [Tennv], [Sodienthoai], [DiaChi], [Gioitinh], [Email], [UserName], [PassWord], [MaNhomQuyen]) VALUES (N'Nv99', N'sh', N'0122565656', N'Hanoi', 1, N'sh@sh.sh', N'sh', N'12', N'admin')
INSERT [dbo].[NhapKho] ([maPhieu], [hangCungCap], [ngayNhap], [tongTien], [daTra], [conNo], [MaNV], [ghiChu]) VALUES (N'h', N'Vinamilk-CT', N'12/4/2019', 50, 30, 20, N'Nv1', N'')
INSERT [dbo].[NhapKho] ([maPhieu], [hangCungCap], [ngayNhap], [tongTien], [daTra], [conNo], [MaNV], [ghiChu]) VALUES (N'MP1', N'Vinamilk-CT', N'12/4/2019', 22000000, 20000000, 2000000, N'Nv5', N'd')
INSERT [dbo].[NhapKho] ([maPhieu], [hangCungCap], [ngayNhap], [tongTien], [daTra], [conNo], [MaNV], [ghiChu]) VALUES (N'Mp2', N'Vinamilk-CT', N'12/4/2019', 56000000, 52500000, 3500000, N'Nv2', N's')
INSERT [dbo].[NhapKho] ([maPhieu], [hangCungCap], [ngayNhap], [tongTien], [daTra], [conNo], [MaNV], [ghiChu]) VALUES (N'MP3', N'Vinamilk-CT', N'12/4/2019', 50, 11, 39, N'Nv1', N'')
INSERT [dbo].[NhomQuyen] ([MaNhomQuyen], [TenNhomQuyen]) VALUES (N'Admin', N'Quản Lý')
INSERT [dbo].[NhomQuyen] ([MaNhomQuyen], [TenNhomQuyen]) VALUES (N'Staff', N'Nhân viên')
INSERT [dbo].[SanPham] ([Masp], [Tensp], [donvi], [gianhap], [giaban], [hangsp], [soluongnhapkho], [chuthich]) VALUES (N'sp1', N'Sữa tươi', N'Hộp', 14000, 15000, N'Vinamilk-CN', 40, N'')
INSERT [dbo].[SanPham] ([Masp], [Tensp], [donvi], [gianhap], [giaban], [hangsp], [soluongnhapkho], [chuthich]) VALUES (N'sp10', N'Sữa Dilac-Alpha', N'Hộp', 249000, 250000, N'Vinamilk-CN', 115, N'')
INSERT [dbo].[SanPham] ([Masp], [Tensp], [donvi], [gianhap], [giaban], [hangsp], [soluongnhapkho], [chuthich]) VALUES (N'sp11', N'Sữa dinh dưỡng', N'Hộp', 16000, 17000, N'Vinamilk-CT', 35, NULL)
INSERT [dbo].[SanPham] ([Masp], [Tensp], [donvi], [gianhap], [giaban], [hangsp], [soluongnhapkho], [chuthich]) VALUES (N'sp12', N'Sữa chua có đường', N'Hộp', 3000, 4000, N'Vinamilk-CT', 40, NULL)
INSERT [dbo].[SanPham] ([Masp], [Tensp], [donvi], [gianhap], [giaban], [hangsp], [soluongnhapkho], [chuthich]) VALUES (N'sp13', N'Sữa chua nha đam', N'Hộp', 4000, 5000, N'Vinamilk-CT', 30, N'')
INSERT [dbo].[SanPham] ([Masp], [Tensp], [donvi], [gianhap], [giaban], [hangsp], [soluongnhapkho], [chuthich]) VALUES (N'sp14', N'Sữa chua nếp cẩm ', N'Hộp', 5000, 6000, N'Vinamilk-CN', 18, N'')
INSERT [dbo].[SanPham] ([Masp], [Tensp], [donvi], [gianhap], [giaban], [hangsp], [soluongnhapkho], [chuthich]) VALUES (N'sp15', N'Sữa đậu nành giàu đạm có đường', N'Hộp', 6000, 7000, N'Vinamilk-CT', 22, N'')
INSERT [dbo].[SanPham] ([Masp], [Tensp], [donvi], [gianhap], [giaban], [hangsp], [soluongnhapkho], [chuthich]) VALUES (N'sp16', N'Sữa dinh dưỡng ít đường', N'Chai', 9000, 11000, N'Vinamilk-CT', 27, NULL)
INSERT [dbo].[SanPham] ([Masp], [Tensp], [donvi], [gianhap], [giaban], [hangsp], [soluongnhapkho], [chuthich]) VALUES (N'sp17', N'Sữa Hạt Óc Chó', N'Thùng', 170000, 175000, N'Vinamilk-CN', 35, NULL)
INSERT [dbo].[SanPham] ([Masp], [Tensp], [donvi], [gianhap], [giaban], [hangsp], [soluongnhapkho], [chuthich]) VALUES (N'Sp2', N'Sữa dinh dưỡng', N'Chai', 16000, 17000, N'Vinamilk-CN', 45, N'nhập về tháng4')
INSERT [dbo].[SanPham] ([Masp], [Tensp], [donvi], [gianhap], [giaban], [hangsp], [soluongnhapkho], [chuthich]) VALUES (N'Sp3', N'Sữa tươi có đường', N'Thùng', 199000, 200000, N'Vinamilk-CT', 120, N'nhập về tháng4')
INSERT [dbo].[SanPham] ([Masp], [Tensp], [donvi], [gianhap], [giaban], [hangsp], [soluongnhapkho], [chuthich]) VALUES (N'sp4', N'Sữa dinh dưỡng không đường', N'Hộp', 6500, 7500, N'Vinamilk-CN', 36, N'')
INSERT [dbo].[SanPham] ([Masp], [Tensp], [donvi], [gianhap], [giaban], [hangsp], [soluongnhapkho], [chuthich]) VALUES (N'sp5', N'Sữa nguyên chất tiệt trùng', N'Chai', 9000, 10000, N'Vinamilk-CT', 80, NULL)
INSERT [dbo].[SanPham] ([Masp], [Tensp], [donvi], [gianhap], [giaban], [hangsp], [soluongnhapkho], [chuthich]) VALUES (N'sp6', N'Sữa dâu', N'Hộp', 7000, 8000, N'Vinamilk-CN', 15, NULL)
INSERT [dbo].[SanPham] ([Masp], [Tensp], [donvi], [gianhap], [giaban], [hangsp], [soluongnhapkho], [chuthich]) VALUES (N'sp7', N'Sữa sô cô la', N'Hộp', 8000, 9000, N'Vinamilk-CN', 50, NULL)
INSERT [dbo].[SanPham] ([Masp], [Tensp], [donvi], [gianhap], [giaban], [hangsp], [soluongnhapkho], [chuthich]) VALUES (N'sp8', N'Sữa Me', N'Thùng', 169000, 170000, N'Vinamilk-CT', 70, NULL)
INSERT [dbo].[SanPham] ([Masp], [Tensp], [donvi], [gianhap], [giaban], [hangsp], [soluongnhapkho], [chuthich]) VALUES (N'sp9', N'Sữa Chuối', N'Hộp', 6000, 7000, N'Vinamilk-CN', 60, N'')
INSERT [dbo].[SanPhamBackup] ([Masp], [TenSp], [Soluong], [Dongia], [Loai]) VALUES (N'Sp1', N'Sữa tươi', 2000, 15000, N'Hộp')
INSERT [dbo].[SanPhamBackup] ([Masp], [TenSp], [Soluong], [Dongia], [Loai]) VALUES (N'Sp10', N'Sữa Dilac-Alpha', 1000, 250000, N'Hộp')
INSERT [dbo].[SanPhamBackup] ([Masp], [TenSp], [Soluong], [Dongia], [Loai]) VALUES (N'Sp11', N'Sữa dinh dưỡng', 2500, 17000, N'Hộp')
INSERT [dbo].[SanPhamBackup] ([Masp], [TenSp], [Soluong], [Dongia], [Loai]) VALUES (N'Sp12', N'Sữa chua có đường', 2000, 4000, N'Hộp')
INSERT [dbo].[SanPhamBackup] ([Masp], [TenSp], [Soluong], [Dongia], [Loai]) VALUES (N'Sp13', N'Sữa chua nha đam', 2500, 5000, N'Hộp')
INSERT [dbo].[SanPhamBackup] ([Masp], [TenSp], [Soluong], [Dongia], [Loai]) VALUES (N'Sp14', N'Sữa chua nếp cẩm ', 1700, 6000, N'Hộp')
INSERT [dbo].[SanPhamBackup] ([Masp], [TenSp], [Soluong], [Dongia], [Loai]) VALUES (N'Sp15', N'Sữa đậu nành giàu đạm có đường', 2200, 7000, N'Hộp')
INSERT [dbo].[SanPhamBackup] ([Masp], [TenSp], [Soluong], [Dongia], [Loai]) VALUES (N'Sp2', N'Sữa dinh dưỡng', 2500, 17000, N'Chai')
INSERT [dbo].[SanPhamBackup] ([Masp], [TenSp], [Soluong], [Dongia], [Loai]) VALUES (N'Sp3', N'Sữa tươi có đường', 3000, 200000, N'Thùng')
INSERT [dbo].[SanPhamBackup] ([Masp], [TenSp], [Soluong], [Dongia], [Loai]) VALUES (N'Sp4', N'Sữa dinh dưỡng không đường', 3100, 7500, N'Hộp')
INSERT [dbo].[SanPhamBackup] ([Masp], [TenSp], [Soluong], [Dongia], [Loai]) VALUES (N'Sp5', N'Sữa nguyên chất tiệt trùng', 8000, 10000, N'Chai')
INSERT [dbo].[SanPhamBackup] ([Masp], [TenSp], [Soluong], [Dongia], [Loai]) VALUES (N'Sp6', N'Sữa dâu', 1500, 8000, N'Hộp')
INSERT [dbo].[SanPhamBackup] ([Masp], [TenSp], [Soluong], [Dongia], [Loai]) VALUES (N'Sp7', N'Sữa sô cô la', 5000, 9000, N'Hộp')
INSERT [dbo].[SanPhamBackup] ([Masp], [TenSp], [Soluong], [Dongia], [Loai]) VALUES (N'Sp8', N'Sữa Me', 7000, 170000, N'Thùng')
INSERT [dbo].[SanPhamBackup] ([Masp], [TenSp], [Soluong], [Dongia], [Loai]) VALUES (N'Sp9', N'Sữa Chuối', 1500, 7000, N'Hộp')
ALTER TABLE [dbo].[Donhang]  WITH CHECK ADD  CONSTRAINT [FK_Donhang_Khachhang] FOREIGN KEY([MatheKH])
REFERENCES [dbo].[Khachhang] ([MatheKH])
GO
ALTER TABLE [dbo].[Donhang] CHECK CONSTRAINT [FK_Donhang_Khachhang]
GO
ALTER TABLE [dbo].[Donhang]  WITH CHECK ADD  CONSTRAINT [FK_Donhang_Nhanvien] FOREIGN KEY([Manv])
REFERENCES [dbo].[Nhanvien] ([MaNV])
GO
ALTER TABLE [dbo].[Donhang] CHECK CONSTRAINT [FK_Donhang_Nhanvien]
GO
ALTER TABLE [dbo].[DonHangChiTiet]  WITH CHECK ADD  CONSTRAINT [FK_DonHangChiTiet_Donhang] FOREIGN KEY([MaDH])
REFERENCES [dbo].[Donhang] ([MaDh])
GO
ALTER TABLE [dbo].[DonHangChiTiet] CHECK CONSTRAINT [FK_DonHangChiTiet_Donhang]
GO
ALTER TABLE [dbo].[DonHangChiTiet]  WITH CHECK ADD  CONSTRAINT [FK_DonHangChiTiet_SanPham2] FOREIGN KEY([Masp])
REFERENCES [dbo].[SanPham] ([Masp])
GO
ALTER TABLE [dbo].[DonHangChiTiet] CHECK CONSTRAINT [FK_DonHangChiTiet_SanPham2]
GO
ALTER TABLE [dbo].[Nhanvien]  WITH CHECK ADD  CONSTRAINT [FK_Nhanvien_NhomQuyen] FOREIGN KEY([MaNhomQuyen])
REFERENCES [dbo].[NhomQuyen] ([MaNhomQuyen])
GO
ALTER TABLE [dbo].[Nhanvien] CHECK CONSTRAINT [FK_Nhanvien_NhomQuyen]
GO
ALTER TABLE [dbo].[NhapKho]  WITH CHECK ADD  CONSTRAINT [FK_NhapKho_Nhanvien] FOREIGN KEY([MaNV])
REFERENCES [dbo].[Nhanvien] ([MaNV])
GO
ALTER TABLE [dbo].[NhapKho] CHECK CONSTRAINT [FK_NhapKho_Nhanvien]
GO
USE [master]
GO
ALTER DATABASE [QLBH] SET  READ_WRITE 
GO
