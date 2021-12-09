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
drop table [User]
CREATE TABLE [User]
(
	idUser INT IDENTITY(1,1) PRIMARY KEY,
	[name] NVARCHAR(255) NOT NULL,
	birthday DATE,
	gender BIT,
	phoneNumber VARCHAR(15),
	email varchar(255),
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
select * from dbo.[User]
SELECT * FROM dbo.Account WHERE username like 

select * from Products
s


SELECT * FROM dbo.Account
SELECT * FROM dbo.[User]

INSERT INTO dbo.[User]
(
    name,
    birthday,
    gender,
    phoneNumber,
    address,
    salary,
    role,
    status,
    email
)
VALUES
(   N'Nguyễn Văn Đức',  -- name - nvarchar(255)
    '2002-09-25', -- birthday - date
    1, -- gender - bit
    '0332429178', -- phoneNumber - varchar(15)
    N'Hà Nội', -- address - nvarchar(255)
    9000000, -- salary - money
    0, -- role - bit
    0, -- status - bit
    'ducnvph14435@gmail.com'  -- email - varchar(255)
    )

	INSERT INTO dbo.Account
	(
	    idUser,
	    username,
	    password
	)
	VALUES
	(   1,   -- idUser - int
	    N'ducnv2509', -- username - nvarchar(255)
	    N'nguyenvanduc'  -- password - nvarchar(255)
	    )

		UPDATE dbo.Account
		SET  password = ?
		FROM dbo.Account JOIN dbo.[User]
		ON [User].idUser = Account.idUser
		WHERE username = ?
SELECT * FROM dbo.Account JOIN dbo.[User] ON [User].idUser = Account.idUser
UPDATE dbo.[User]
SET email = 'ducit2509@gmail.com'
WHERE idUser = 1

INSERT INTO dbo.[User]
(
    name,
    birthday,
    gender,
    phoneNumber,
    address,
    salary,
    role,
    status,
    email
)
VALUES
(   N'',  -- name - nvarchar(255)
    NULL, -- birthday - date
    NULL, -- gender - bit
    NULL, -- phoneNumber - varchar(15)
    NULL, -- address - nvarchar(255)
    NULL, -- salary - money
    NULL, -- role - bit
    NULL, -- status - bit
    NULL  -- email - varchar(255)
    )
	INSERT INTO dbo.Account
	(
	    idUser,
	    username,
	    password
	)
	VALUES
	(   0,   -- idUser - int
	    N'', -- username - nvarchar(255)
	    N''  -- password - nvarchar(255)
	    )

		SELECT * FROM dbo.Customer

		SELECT * FROM dbo.[User]
		SELECT * FROM Account

		INSERT INTO dbo.[User]
		(
		    name,
		    birthday,
		    gender,
		    phoneNumber,
		    address,
		    salary,
		    role,
		    status,
		    email
		)
		VALUES
		(   N'',  -- name - nvarchar(255)
		    NULL, -- birthday - date
		    NULL, -- gender - bit
		    NULL, -- phoneNumber - varchar(15)
		    NULL, -- address - nvarchar(255)
		    NULL, -- salary - money
		    NULL, -- role - bit
		    NULL, -- status - bit
		    NULL  -- email - varchar(255)
		    )

			SELECT * FROM dbo.Customer

			UPDATE dbo.[User] SET status = 0 WHERE idUser = 9

			INSERT INTO dbo.[User]
			(
			    name,
			    birthday,
			    gender,
			    phoneNumber,
			    address,
			    salary,
			    role,
			    status,
			    email
			)
			VALUES
			(   N'Truong',  -- name - nvarchar(255)
			    '2002-09-09', -- birthday - date
			    1, -- gender - bit
			    '01231231231', -- phoneNumber - varchar(15)
			    'Ha Noi', -- address - nvarchar(255)
			    190091, -- salary - money
			    1, -- role - bit
			    1, -- status - bit
			    'duc@gmail.com'  -- email - varchar(255)
			    )
INSERT INTO dbo.Supplier
(
    nameMaterial,
    phoneNumber,
    address
)
VALUES
(   N'Nhà May Hải Dương', -- nameMaterial - nvarchar(255)
    '0332429178',  -- phoneNumber - varchar(15)
    N'Hải Dương' -- address - nvarchar(255)
    )

	SELECT * FROM dbo.[User]
	SELECT * FROM dbo.Account