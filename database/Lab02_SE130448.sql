USE [master]
GO
/****** Object:  Database [Lab02_HanaShop]    Script Date: 3/2/2020 5:34:52 PM ******/
CREATE DATABASE [Lab02_HanaShop]
 CONTAINMENT = NONE
 ON  PRIMARY 
( NAME = N'Lab02_HanaShop', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL15.SQLEXPRESS\MSSQL\DATA\Lab02_HanaShop.mdf' , SIZE = 8192KB , MAXSIZE = UNLIMITED, FILEGROWTH = 65536KB )
 LOG ON 
( NAME = N'Lab02_HanaShop_log', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL15.SQLEXPRESS\MSSQL\DATA\Lab02_HanaShop_log.ldf' , SIZE = 8192KB , MAXSIZE = 2048GB , FILEGROWTH = 65536KB )
 WITH CATALOG_COLLATION = DATABASE_DEFAULT
GO
ALTER DATABASE [Lab02_HanaShop] SET COMPATIBILITY_LEVEL = 150
GO
IF (1 = FULLTEXTSERVICEPROPERTY('IsFullTextInstalled'))
begin
EXEC [Lab02_HanaShop].[dbo].[sp_fulltext_database] @action = 'enable'
end
GO
ALTER DATABASE [Lab02_HanaShop] SET ANSI_NULL_DEFAULT OFF 
GO
ALTER DATABASE [Lab02_HanaShop] SET ANSI_NULLS OFF 
GO
ALTER DATABASE [Lab02_HanaShop] SET ANSI_PADDING OFF 
GO
ALTER DATABASE [Lab02_HanaShop] SET ANSI_WARNINGS OFF 
GO
ALTER DATABASE [Lab02_HanaShop] SET ARITHABORT OFF 
GO
ALTER DATABASE [Lab02_HanaShop] SET AUTO_CLOSE OFF 
GO
ALTER DATABASE [Lab02_HanaShop] SET AUTO_SHRINK OFF 
GO
ALTER DATABASE [Lab02_HanaShop] SET AUTO_UPDATE_STATISTICS ON 
GO
ALTER DATABASE [Lab02_HanaShop] SET CURSOR_CLOSE_ON_COMMIT OFF 
GO
ALTER DATABASE [Lab02_HanaShop] SET CURSOR_DEFAULT  GLOBAL 
GO
ALTER DATABASE [Lab02_HanaShop] SET CONCAT_NULL_YIELDS_NULL OFF 
GO
ALTER DATABASE [Lab02_HanaShop] SET NUMERIC_ROUNDABORT OFF 
GO
ALTER DATABASE [Lab02_HanaShop] SET QUOTED_IDENTIFIER OFF 
GO
ALTER DATABASE [Lab02_HanaShop] SET RECURSIVE_TRIGGERS OFF 
GO
ALTER DATABASE [Lab02_HanaShop] SET  DISABLE_BROKER 
GO
ALTER DATABASE [Lab02_HanaShop] SET AUTO_UPDATE_STATISTICS_ASYNC OFF 
GO
ALTER DATABASE [Lab02_HanaShop] SET DATE_CORRELATION_OPTIMIZATION OFF 
GO
ALTER DATABASE [Lab02_HanaShop] SET TRUSTWORTHY OFF 
GO
ALTER DATABASE [Lab02_HanaShop] SET ALLOW_SNAPSHOT_ISOLATION OFF 
GO
ALTER DATABASE [Lab02_HanaShop] SET PARAMETERIZATION SIMPLE 
GO
ALTER DATABASE [Lab02_HanaShop] SET READ_COMMITTED_SNAPSHOT OFF 
GO
ALTER DATABASE [Lab02_HanaShop] SET HONOR_BROKER_PRIORITY OFF 
GO
ALTER DATABASE [Lab02_HanaShop] SET RECOVERY SIMPLE 
GO
ALTER DATABASE [Lab02_HanaShop] SET  MULTI_USER 
GO
ALTER DATABASE [Lab02_HanaShop] SET PAGE_VERIFY CHECKSUM  
GO
ALTER DATABASE [Lab02_HanaShop] SET DB_CHAINING OFF 
GO
ALTER DATABASE [Lab02_HanaShop] SET FILESTREAM( NON_TRANSACTED_ACCESS = OFF ) 
GO
ALTER DATABASE [Lab02_HanaShop] SET TARGET_RECOVERY_TIME = 60 SECONDS 
GO
ALTER DATABASE [Lab02_HanaShop] SET DELAYED_DURABILITY = DISABLED 
GO
ALTER DATABASE [Lab02_HanaShop] SET QUERY_STORE = OFF
GO
USE [Lab02_HanaShop]
GO
/****** Object:  Table [dbo].[tblBills]    Script Date: 3/2/2020 5:34:52 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[tblBills](
	[IdBill] [nvarchar](300) NOT NULL,
	[UserID] [nvarchar](200) NOT NULL,
	[SubTotal] [float] NOT NULL,
	[Tax] [float] NOT NULL,
	[Total] [float] NOT NULL,
	[Date] [datetime] NOT NULL,
	[StatusBillCode] [int] NOT NULL,
 CONSTRAINT [PK_tblBills] PRIMARY KEY CLUSTERED 
(
	[IdBill] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[tblBillStatuses]    Script Date: 3/2/2020 5:34:52 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[tblBillStatuses](
	[StatusBillCode] [int] NOT NULL,
	[Status] [nvarchar](max) NOT NULL,
 CONSTRAINT [PK_tblBillStatuses] PRIMARY KEY CLUSTERED 
(
	[StatusBillCode] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[tblCategories]    Script Date: 3/2/2020 5:34:52 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[tblCategories](
	[CategoryID] [varchar](50) NOT NULL,
	[CategoryName] [nvarchar](max) NOT NULL,
	[KindID] [nvarchar](50) NOT NULL,
 CONSTRAINT [PK_tblCategories] PRIMARY KEY CLUSTERED 
(
	[CategoryID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[tblFoods]    Script Date: 3/2/2020 5:34:52 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[tblFoods](
	[IdFood] [varchar](50) NOT NULL,
	[ImgFood] [nvarchar](max) NOT NULL,
	[NameFood] [nvarchar](max) NOT NULL,
	[Description] [nvarchar](max) NOT NULL,
	[Quantity] [int] NOT NULL,
	[Price] [float] NOT NULL,
	[CategoryID] [varchar](50) NOT NULL,
	[CreateDate] [date] NOT NULL,
	[UpdateDate] [date] NULL,
	[UpdateUser] [nvarchar](max) NULL,
	[StatusCode] [int] NOT NULL,
 CONSTRAINT [PK_tblFoods] PRIMARY KEY CLUSTERED 
(
	[IdFood] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[tblFoodsInBill]    Script Date: 3/2/2020 5:34:52 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[tblFoodsInBill](
	[IdBill] [nvarchar](300) NOT NULL,
	[IdFood] [varchar](50) NOT NULL,
	[NameFood] [nvarchar](max) NULL,
	[Quantity] [int] NOT NULL,
	[StatusFoodID] [int] NOT NULL,
	[Price] [float] NOT NULL,
	[Total] [float] NOT NULL,
 CONSTRAINT [PK_tblFoodsInBill] PRIMARY KEY CLUSTERED 
(
	[IdBill] ASC,
	[IdFood] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[tblKinds]    Script Date: 3/2/2020 5:34:52 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[tblKinds](
	[KindID] [nvarchar](50) NOT NULL,
	[KindFood] [nvarchar](max) NOT NULL,
 CONSTRAINT [PK_tblKinds] PRIMARY KEY CLUSTERED 
(
	[KindID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[tblRoles]    Script Date: 3/2/2020 5:34:52 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[tblRoles](
	[RoleID] [nvarchar](50) NOT NULL,
	[RoleDetail] [nvarchar](max) NOT NULL,
 CONSTRAINT [PK_tblRoles] PRIMARY KEY CLUSTERED 
(
	[RoleID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[tblStatusFood]    Script Date: 3/2/2020 5:34:52 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[tblStatusFood](
	[StatusCode] [int] NOT NULL,
	[Status] [nvarchar](max) NOT NULL,
 CONSTRAINT [PK_tblStatusFood] PRIMARY KEY CLUSTERED 
(
	[StatusCode] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[tblStatusFoodInBill]    Script Date: 3/2/2020 5:34:52 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[tblStatusFoodInBill](
	[StatusFoodID] [int] NOT NULL,
	[Status] [nvarchar](max) NOT NULL,
 CONSTRAINT [PK_tblStatusFoodInBill] PRIMARY KEY CLUSTERED 
(
	[StatusFoodID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[tblUsers]    Script Date: 3/2/2020 5:34:52 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[tblUsers](
	[UserID] [nvarchar](200) NOT NULL,
	[Email] [nvarchar](100) NOT NULL,
	[Avatar] [nvarchar](max) NOT NULL,
	[Name] [nvarchar](max) NOT NULL,
	[Password] [nvarchar](max) NOT NULL,
	[RoleID] [nvarchar](50) NOT NULL,
	[DateCreate] [date] NULL,
	[Description] [nvarchar](max) NULL,
	[IdFoodRecentlyView] [nvarchar](max) NULL,
 CONSTRAINT [PK_tblUsers] PRIMARY KEY CLUSTERED 
(
	[UserID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
ALTER TABLE [dbo].[tblBills]  WITH CHECK ADD  CONSTRAINT [FK_tblBills_tblBillStatuses] FOREIGN KEY([StatusBillCode])
REFERENCES [dbo].[tblBillStatuses] ([StatusBillCode])
GO
ALTER TABLE [dbo].[tblBills] CHECK CONSTRAINT [FK_tblBills_tblBillStatuses]
GO
ALTER TABLE [dbo].[tblBills]  WITH CHECK ADD  CONSTRAINT [FK_tblBills_tblUsers] FOREIGN KEY([UserID])
REFERENCES [dbo].[tblUsers] ([UserID])
GO
ALTER TABLE [dbo].[tblBills] CHECK CONSTRAINT [FK_tblBills_tblUsers]
GO
ALTER TABLE [dbo].[tblCategories]  WITH CHECK ADD  CONSTRAINT [FK_tblCategories_tblKinds] FOREIGN KEY([KindID])
REFERENCES [dbo].[tblKinds] ([KindID])
GO
ALTER TABLE [dbo].[tblCategories] CHECK CONSTRAINT [FK_tblCategories_tblKinds]
GO
ALTER TABLE [dbo].[tblFoods]  WITH CHECK ADD  CONSTRAINT [FK_tblFoods_tblCategories] FOREIGN KEY([CategoryID])
REFERENCES [dbo].[tblCategories] ([CategoryID])
GO
ALTER TABLE [dbo].[tblFoods] CHECK CONSTRAINT [FK_tblFoods_tblCategories]
GO
ALTER TABLE [dbo].[tblFoods]  WITH CHECK ADD  CONSTRAINT [FK_tblFoods_tblStatusFood] FOREIGN KEY([StatusCode])
REFERENCES [dbo].[tblStatusFood] ([StatusCode])
GO
ALTER TABLE [dbo].[tblFoods] CHECK CONSTRAINT [FK_tblFoods_tblStatusFood]
GO
ALTER TABLE [dbo].[tblFoodsInBill]  WITH CHECK ADD  CONSTRAINT [FK_tblFoodsInBill_tblBills] FOREIGN KEY([IdBill])
REFERENCES [dbo].[tblBills] ([IdBill])
GO
ALTER TABLE [dbo].[tblFoodsInBill] CHECK CONSTRAINT [FK_tblFoodsInBill_tblBills]
GO
ALTER TABLE [dbo].[tblFoodsInBill]  WITH CHECK ADD  CONSTRAINT [FK_tblFoodsInBill_tblFoods] FOREIGN KEY([IdFood])
REFERENCES [dbo].[tblFoods] ([IdFood])
GO
ALTER TABLE [dbo].[tblFoodsInBill] CHECK CONSTRAINT [FK_tblFoodsInBill_tblFoods]
GO
ALTER TABLE [dbo].[tblFoodsInBill]  WITH CHECK ADD  CONSTRAINT [FK_tblFoodsInBill_tblStatusFoodInBill] FOREIGN KEY([StatusFoodID])
REFERENCES [dbo].[tblStatusFoodInBill] ([StatusFoodID])
GO
ALTER TABLE [dbo].[tblFoodsInBill] CHECK CONSTRAINT [FK_tblFoodsInBill_tblStatusFoodInBill]
GO
ALTER TABLE [dbo].[tblUsers]  WITH CHECK ADD  CONSTRAINT [FK_tblUsers_tblRoles] FOREIGN KEY([RoleID])
REFERENCES [dbo].[tblRoles] ([RoleID])
GO
ALTER TABLE [dbo].[tblUsers] CHECK CONSTRAINT [FK_tblUsers_tblRoles]
GO
USE [master]
GO
ALTER DATABASE [Lab02_HanaShop] SET  READ_WRITE 
GO
