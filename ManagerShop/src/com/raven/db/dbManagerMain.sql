CREATE DATABASE dbManagerShop
GO

USE dbManagerShop
GO

CREATE TABLE List
(
	idList INT IDENTITY(1,1) PRIMARY KEY,
	nameList NVARCHAR(255) NOT NULL
)
GO

CREATE TABLE Size
(
	idSize INT IDENTITY(1,1) PRIMARY KEY,
	valueSize VARCHAR(5) NOT NULL,

)
GO

CREATE TABLE Color
(
	idColor INT IDENTITY(1,1) PRIMARY KEY,
	valueColor VARCHAR(5) NOT NULL,

)
GO

CREATE TABLE Material
(
	idMaterial INT IDENTITY(1,1) PRIMARY KEY,
	valueMaterial VARCHAR(5) NOT NULL,
)
GO

CREATE TABLE [User]
(
	idUser INT IDENTITY(1,1) PRIMARY KEY,
	[name] NVARCHAR(255) NOT NULL,
	birthday DATE,
	gender BIT,
	phoneNumber VARCHAR(15),
	address NVARCHAR(255),
	salary MONEY,
	role BIT,
	[status] bit,
)
GO

CREATE TABLE Customer
(
	idCustomer INT IDENTITY(1,1) PRIMARY KEY,
	[name] NVARCHAR(255) NOT NULL,
	phoneNumber VARCHAR(15) NOT NULL,
	gender BIT,
	[address] NVARCHAR(255) NOT NULL,
)
GO

CREATE TABLE Account
(
	idAccount INT IDENTITY(1,1) PRIMARY KEY,
	idUser INT NOT NULL,
	username NVARCHAR(255) NOT NULL,
	password NVARCHAR(255) NOT NULL,
	FOREIGN KEY(idUser) REFERENCES dbo.[User](idUser)
)
GO

CREATE TABLE Voucher
(
	idVoucher INT IDENTITY(1,1) PRIMARY KEY,
	valueVoucher VARCHAR(255),
	[value] DOUBLE PRECISION,
	dateStart DATE,
	dateEnd DATE,
	quatity INT,
)
GO


CREATE TABLE Invoice
(
	idInvoice INT IDENTITY(1,1) PRIMARY KEY,
	idCustomer INT NOT NULL,
	idEmpolyee INT NOT NULL,
	idVoucher INT,
	dateCreateInvoice DATE,
	statusInvoice BIT,
	statusPay BIT,
	FOREIGN KEY(idCustomer) REFERENCES dbo.Customer(idCustomer),
	FOREIGN KEY(idEmpolyee) REFERENCES dbo.[User](idUser),
	FOREIGN KEY(idVoucher) REFERENCES dbo.Voucher(idVoucher)

)
GO



CREATE TABLE detailsProduct
(
	idPrDeltails INT IDENTITY(1,1) PRIMARY KEY, 
	idProduct INT,
	idSize INT,
	idColor INT,
	idMaterial INT,
	price MONEY,
	sku VARCHAR(255),
	quatity INT,
	[status] bit,
	FOREIGN KEY(idSize) REFERENCES dbo.Size(idSize),
	FOREIGN KEY(idColor) REFERENCES dbo.Color(idColor),
	FOREIGN KEY(idMaterial) REFERENCES dbo.Material(idMaterial)
)
GO

CREATE TABLE Supplier
(
	idSupplier INT IDENTITY(1,1) PRIMARY KEY,
	nameMaterial NVARCHAR(255) NOT NULL,
	phoneNumber VARCHAR(15) NOT NULL,
	address NVARCHAR(255)
)
GO

CREATE TABLE detailsInvoice
(
	idInvoice INT,
	idPrDeltails INT, 
	detailsInvoice INT,
	quatity INT, 
	status BIT,
	-- nhà cung cấp
	idSupplier INT,
	PRIMARY KEY(idInvoice, idPrDeltails),
	FOREIGN KEY(idInvoice) REFERENCES dbo.Invoice(idInvoice),
	FOREIGN KEY(idPrDeltails) REFERENCES dbo.detailsProduct(idPrDeltails),
	FOREIGN KEY(idSupplier) REFERENCES dbo.Supplier(idSupplier),
	--FOREIGN KEY(detailsInvoice) REFERENCES dbo.detailsInvoice()
)
GO

CREATE TABLE Products
(
	idProduct INT IDENTITY(1,1) PRIMARY KEY,
	idSupplier INT NOT NULL,
	idList INT NOT NULL,
	nameProduct NVARCHAR(255) NOT NULL,
	description NVARCHAR(255),
	[status] bit,
	FOREIGN KEY (idSupplier) REFERENCES dbo.Supplier(idSupplier),
	FOREIGN KEY (idList) REFERENCES dbo.List(idList)
)
GO

CREATE TABLE ImageProducts
(
	idImage INT IDENTITY(1,1) PRIMARY KEY,
	idPrDeltails INT,
	valueImage NVARCHAR(255) NOT NULL,
	FOREIGN KEY(idPrDeltails) REFERENCES dbo.detailsProduct(idPrDeltails)
)
GO












