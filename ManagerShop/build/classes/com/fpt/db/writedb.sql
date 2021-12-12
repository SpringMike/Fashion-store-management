ALTER TABLE dbo.Material ALTER COLUMN valueMaterial NVARCHAR(50) NOT NULL
ALTER TABLE dbo.Color ALTER COLUMN valueColor NVARCHAR(50) NOT NULL
----
ALTER TABLE dbo.Products ADD statusDelete BIT DEFAULT 1
-- xoá
ALTER TABLE dbo.Products DROP CONSTRAINT FK__Products__idSupp__44FF419A
ALTER TABLE dbo.Products DROP COLUMN idSupplier
ALTER TABLE dbo.List ADD status BIT DEFAULT 1
-----------------------------
-- Product delete 
IF OBJECT_ID('PRDelete') IS NOT NULL
DROP PROC PRDelete
GO

CREATE PROC PRDelete
@idPrDetails INT
AS
BEGIN TRY
	BEGIN TRAN
	DELETE dbo.ImageProducts
	WHERE idPrDeltails IN (SELECT idPrDeltails FROM dbo.ImageProducts WHERE @idPrDetails = idPrDeltails)
	DELETE dbo.detailsProduct
	WHERE idPrDeltails IN (SELECT idPrDeltails FROM dbo.detailsProduct WHERE @idPrDetails = idPrDeltails)
	COMMIT TRAN
END TRY
BEGIN CATCH
	ROLLBACK TRANSACTION
END CATCH
----------------------
--- new DB 24/11/2021

ALTER TABLE dbo.Invoice DROP CONSTRAINT FK__Invoice__idCusto__34C8D9D1
ALTER TABLE dbo.Invoice DROP COLUMN idCustomer
ALTER TABLE dbo.Invoice DROP CONSTRAINT FK__Invoice__idEmpol__35BCFE0A
ALTER TABLE dbo.Invoice DROP COLUMN idEmpolyee
ALTER TABLE dbo.Invoice DROP CONSTRAINT FK__Invoice__idVouch__36B12243
ALTER TABLE dbo.Invoice DROP COLUMN idVoucher
EXEC sys.sp_rename 'Invoice', 'InvoiceImportPr'
ALTER TABLE dbo.InvoiceImportPr ADD idAdmin INT
ALTER TABLE dbo.InvoiceImportPr ADD FOREIGN KEY (idAdmin) REFERENCES dbo.[User](idUser)
-----------------------------------------------
ALTER TABLE dbo.detailsInvoice DROP CONSTRAINT PK__detailsI__5C4F675008E94484
ALTER TABLE dbo.detailsInvoice DROP COLUMN detailsInvoice
ALTER TABLE dbo.detailsInvoice ADD detailsInvoice INT IDENTITY(1,1) PRIMARY KEY
EXEC sys.sp_rename 'detailsInvoice', 'detailsInvoiceImportPr'

ALTER TABLE dbo.detailsInvoiceImportPr DROP CONSTRAINT FK__detailsIn__idSup__4222D4EF
ALTER TABLE dbo.detailsInvoiceImportPr DROP COLUMN idSupplier
ALTER TABLE dbo.InvoiceImportPr ADD idSupplier INT
ALTER TABLE dbo.InvoiceImportPr ADD FOREIGN KEY (idSupplier) REFERENCES dbo.Supplier(idSupplier)
ALTER TABLE dbo.InvoiceImportPr ADD description NVARCHAR(255)
ALTER TABLE dbo.detailsInvoiceImportPr ADD priceImport MONEY
ALTER TABLE dbo.InvoiceSell ADD totalMoney MONEY

------------------------------------------------------------------------------------------------------

CREATE TABLE InvoiceSell
(
	idInvoiceSell INT IDENTITY(1,1) PRIMARY KEY,
	idCustomer INT,
	idHumanSell INT,
	idVoucher INT,
	dateCreateInvoice DATE,
	description NVARCHAR(255),
	statusPay BIT,
    statusInvoice BIT
    FOREIGN KEY(idCustomer) REFERENCES dbo.Customer(idCustomer),
	FOREIGN KEY(idHumanSell) REFERENCES dbo.[User](idUser),
	FOREIGN KEY(idVoucher) REFERENCES dbo.Voucher(idVoucher),
)
GO

CREATE TABLE detailsInvoiceSELL
(
	idDetailsInvoiceSELL INT IDENTITY (1,1) PRIMARY KEY,
	idInvoiceSell INT,
	idPrDetails INT,
	quatity INT,
	price MONEY,
	FOREIGN KEY(idInvoiceSell) REFERENCES dbo.InvoiceSell(idInvoiceSell),
	FOREIGN KEY(idPrDetails) REFERENCES dbo.detailsProduct(idPrDeltails)
)
GO
----------------------------------------------------- END --------------------------------------------
--26/11/2021
ALTER TABLE dbo.InvoiceSell ADD totalMoney MONEY
-----------------------------------------------
--27/11/2021
CREATE TABLE InvoiceReturn
(
	idInvoiceReturn INT IDENTITY(1,1) PRIMARY KEY,
	idInvoiceSell INT,
	idCustomer INT, 
	description NVARCHAR(255),
	totalReturn MONEY,
	FOREIGN KEY (idInvoiceSell) REFERENCES dbo.InvoiceSell(idInvoiceSell),
	FOREIGN KEY (idCustomer) REFERENCES dbo.Customer(idCustomer),
)
GO
ALTER TABLE dbo.InvoiceReturn ADD idUser INT
ALTER TABLE dbo.InvoiceReturn ADD FOREIGN KEY (idUser) REFERENCES dbo.[User](idUser)

SELECT * FROM dbo.InvoiceReturn

CREATE TABLE DetailInvoiceReturn
(
	idDetailInvoiceReturn INT IDENTITY(1,1) PRIMARY KEY,
	idInvoiceReturn INT,
	idPrDetails INT,
	quatity INT,
	price MONEY,
	FOREIGN KEY (idDetailInvoiceReturn) REFERENCES dbo.InvoiceReturn(idInvoiceReturn),
	FOREIGN KEY (idPrDetails) REFERENCES dbo.detailsProduct(idPrDeltails)
)
GO

ALTER TABLE dbo.InvoiceReturn ADD idUser INT
ALTER TABLE dbo.InvoiceReturn ADD FOREIGN KEY(idUser) REFERENCES dbo.[User](idUser)

ALTER TABLE dbo.InvoiceReturn ADD dateCreateInvoice DATE
----------------------------------------------------------------------------------------
--------------------------------------------------
IF OBJECT_ID('sp_statistical') IS NOT NULL
DROP PROC sp_statistical;
GO
CREATE PROC sp_statistical
(@year INT, @month int)
AS
	BEGIN
	SELECT Products.idProduct  idProduct,nameProduct nameProduct, Sum(detailsInvoiceSELL.quatity) quantitySell  FROM dbo.detailsProduct 
	JOIN dbo.detailsInvoiceSELL ON detailsInvoiceSELL.idPrDetails = detailsProduct.idPrDeltails
	JOIN dbo.InvoiceSell ON InvoiceSell.idInvoiceSell = detailsInvoiceSELL.idInvoiceSell
	JOIN dbo.Products ON Products.idProduct = detailsProduct.idProduct
	WHERE YEAR(dateCreateInvoice) = @year AND MONTH(dateCreateInvoice) = @month
	GROUP BY Products.idProduct, nameProduct
	ORDER BY quantitySell DESC
END;

EXEC dbo.sp_statistical @year = 2021, -- int
                        @month = 10 -- int
---------------------------
IF OBJECT_ID('sp_revenue') IS NOT NULL
DROP PROC sp_revenue
GO
CREATE PROC sp_revenue
(@year int)
AS
BEGIN
	SELECT MONTH(InvoiceSell.dateCreateInvoice) MonthDate , SUM(detailsInvoiceSELL.quatity) quantity,
	CAST(SUM(detailsInvoiceSELL.price * detailsInvoiceSELL.quatity) AS INT)
	 totalSell, 
	 CAST(SUM(totalReturn) AS INT )
	  totalReturn, 
	  CAST(SUM(detailsInvoiceSELL.price * detailsInvoiceSELL.quatity) - SUM(totalReturn) AS INT)
	revenue
	FROM dbo.detailsInvoiceSELL  
	JOIN dbo.InvoiceSell ON InvoiceSell.idInvoiceSell = detailsInvoiceSELL.idInvoiceSell
	LEFT JOIN dbo.InvoiceReturn ON InvoiceReturn.idInvoiceSell = InvoiceSell.idInvoiceSell
	WHERE YEAR(InvoiceSell.dateCreateInvoice) = 2021
	GROUP BY MONTH(InvoiceSell.dateCreateInvoice)
END
EXEC dbo.sp_revenue @year = 2021 -- int

-- thầy view
	SELECT MONTH(InvoiceSell.dateCreateInvoice) MonthDate , SUM(detailsInvoiceSELL.quatity) quantity,
	SUM(detailsInvoiceSELL.price * detailsInvoiceSELL.quatity) totalSell, SUM(totalReturn) totalReturn, 
	SUM(detailsInvoiceSELL.price * detailsInvoiceSELL.quatity) - SUM(totalReturn) revenue
	FROM dbo.detailsInvoiceSELL  
	JOIN dbo.InvoiceSell ON InvoiceSell.idInvoiceSell = detailsInvoiceSELL.idInvoiceSell
	LEFT JOIN dbo.InvoiceReturn ON InvoiceReturn.idInvoiceSell = InvoiceSell.idInvoiceSell
	JOIN dbo.detailsProduct ON detailsProduct.idPrDeltails = detailsInvoiceSELL.idPrDetails
	JOIN dbo.detailsInvoiceImportPr ON detailsInvoiceImportPr.idPrDeltails = detailsProduct.idPrDeltails
	JOIN dbo.InvoiceImportPr ON InvoiceImportPr.idInvoice = detailsInvoiceImportPr.idInvoice
	WHERE YEAR(InvoiceSell.dateCreateInvoice) = 2021
	GROUP BY MONTH(InvoiceSell.dateCreateInvoice)
-------------------------------
--2/12/2021
ALTER TABLE dbo.InvoiceImportPr ALTER COLUMN dateCreateInvoice DATETIME
ALTER TABLE dbo.InvoiceReturn ALTER COLUMN dateCreateInvoice DATETIME
ALTER TABLE dbo.InvoiceSell ALTER COLUMN dateCreateInvoice DATETIME
-------------------------------
--PRoc5/11/2021
IF OBJECT_ID('sp_Quantity') IS NOT NULL
DROP PROC sp_Quantity
GO
CREATE PROC sp_Quantity
AS
BEGIN
    SELECT name , iIF(gender = 0, N'Nữ', 'Nam') gender , phoneNumber, SUM(quatity) SumBuy FROM dbo.Customer JOIN dbo.InvoiceSell ON InvoiceSell.idCustomer = Customer.idCustomer
JOIN dbo.detailsInvoiceSELL ON detailsInvoiceSELL.idInvoiceSell = InvoiceSell.idInvoiceSell
GROUP BY name,
         gender,
         phoneNumber
END
----------- db 5/12/021
CREATE TABLE InvoiceChangeProducts
(
	idInvoiceChangeProducts INT IDENTITY(1,1) PRIMARY KEY,
	idCustomer INT,
	idInvoiceSell INT,
	dateCreateInvoice DATETIME,
	idDetailsNew int,
	idDetailsOld INT,
	idUser INT,
	description NVARCHAR(255),
	FOREIGN KEY(idCustomer) REFERENCES dbo.Customer(idCustomer),

	FOREIGN KEY(idInvoiceSell) REFERENCES dbo.InvoiceSell(idInvoiceSell),
		-- xoa khoa ngoai
	FOREIGN KEY(idDetailsOld) REFERENCES dbo.detailsInvoiceSELL(idDetailsInvoiceSELL),
		-- xoa khoa ngoai
	FOREIGN KEY(idDetailsNew) REFERENCES dbo.Products(idProduct),
	FOREIGN KEY(idUser) REFERENCES dbo.[User](idUser)
)
GO
------------------db9/12/2021
ALTER TABLE dbo.InvoiceSell ADD moneyCustom MONEY
ALTER TABLE dbo.InvoiceSell ADD moneyReturn MONEY
--------------------------------

--db9/12/2021

CREATE TABLE DetailsInvoiceChange
(
	id INT IDENTITY(1,1) PRIMARY KEY,
	idInvoiceChangeProducts INT,
	idDetailsPr INT,
	quantity INT,
	FOREIGN KEY(idInvoiceChangeProducts) REFERENCES dbo.InvoiceChangeProducts(idInvoiceChangeProducts),
	FOREIGN KEY(idDetailsPr) REFERENCES dbo.detailsProduct (idPrDeltails)
)
GO
CREATE TABLE DetailsChangeProducts
(
	id INT IDENTITY(1,1) PRIMARY KEY,
	idDetailsPr INT,
	idDetailsInvoiceChange INT,
	quantity INT,
	FOREIGN KEY(idDetailsInvoiceChange) REFERENCES dbo.DetailsInvoiceChange(id),
	FOREIGN KEY(idDetailsPr) REFERENCES dbo.detailsProduct (idPrDeltails)
)
GO

------------------------------
CREATE TABLE DetailsInvoiceChange
(
	id INT IDENTITY(1,1) PRIMARY KEY,
	idInvoiceChangeProducts INT,
	idDetailsPr INT,
	quantity INT,
	FOREIGN KEY(idInvoiceChangeProducts) REFERENCES dbo.InvoiceChangeProducts(idInvoiceChangeProducts),
	FOREIGN KEY(idDetailsPr) REFERENCES dbo.detailsProduct (idPrDeltails)
)
GO
CREATE TABLE DetailsChangeProducts
(
	id INT IDENTITY(1,1) PRIMARY KEY,
	idDetailsPr INT,
	idDetailsInvoiceChange INT,
	quantity INT,
	FOREIGN KEY(idDetailsInvoiceChange) REFERENCES dbo.DetailsInvoiceChange(id),
	FOREIGN KEY(idDetailsPr) REFERENCES dbo.detailsProduct (idPrDeltails)
)
GO
ALTER TABLE InvoiceChangeProducts
DROP COLUMN idDetailsNew;
ALTER TABLE dbo.InvoiceChangeProducts DROP CONSTRAINT FK__InvoiceCh__idDet__671F4F74

ALTER TABLE InvoiceChangeProducts
DROP COLUMN idDetailsOld;

SELECT * FROM dbo.InvoiceChangeProducts




--------------------------------------------

 select * from detailsProduct


INSERT INTO dbo.InvoiceSell
>>>>>>> origin/Minhvn
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
    'Hà Nội', -- address - nvarchar(255)
    3000000, -- salary - money
    1, -- role - bit
    1, -- status - bit
    'ducit2509@gmail.com'  -- email - varchar(255)
    )
	SELECT * FROM dbo.[User]
	INSERT INTO dbo.Account
	(
	    idUser,
	    username,
	    password
	)
	VALUES
	(   29,   -- idUser - int
	    N'admin', -- username - nvarchar(255)
	    N'123'  -- password - nvarchar(255)
	    )