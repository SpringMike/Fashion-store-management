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
--29/11/2021
CREATE TABLE SaveMoney
(
	id INT IDENTITY(1,1) PRIMARY KEY,
	dateCreate DATE,
	moneyReturn MONEY,
	moneySell MONEY,
	moneyImport MONEY,
)
GO


SELECT * FROM dbo.InvoiceImportPr
SELECT * FROM dbo.detailsInvoiceImportPr

SELECT * FROM dbo.InvoiceSell

SELECT * FROM dbo.detailsInvoiceSELL

select D.*,P.nameProduct,S.valueSize,C.valueColor,M.valueMaterial,nameList,quatity from detailsProduct D
                 INNER JOIN Size S on D.idSize = S.idSize INNER JOIN Material M on M.idMaterial = D.idMaterial
                 INNER JOIN Color C on C.idColor = D.idColor
                 INNER JOIN Products P on P.idProduct = D.idProduct
                 INNER JOIN List L  on L.idList = P.idList
                 where D.status = 1 and D.quatity > 0 AND P.nameProduct =?

				 select I.*,name,S.nameMaterial from InvoiceImportPr I join [User] U on U.idUser = I.idAdmin
                join Supplier S on S.idSupplier = I.idSupplier
SELECT I.*, idInvoiceSell, Customer.name, [User].name FROM dbo.InvoiceSell I JOIN dbo.[User] ON [User].idUser = I.idHumanSell
JOIN dbo.Customer ON Customer.idCustomer = I.idCustomer


SELECT * FROM dbo.detailsInvoiceSELL
INSERT INTO dbo.detailsInvoiceImportPr
(
    idInvoice,
    idPrDeltails,
    quatity,
    status,
    priceImport
)
VALUES
(   0,    -- idInvoice - int
    0,    -- idPrDeltails - int
    NULL, -- quatity - int
    NULL, -- status - bit
    NULL  -- priceImport - money
    )


select D.detailsInvoice, P.nameProduct,S.valueSize,C.valueColor,M.valueMaterial,D.quatity,D.priceImport from detailsInvoiceImportPr D
                join detailsProduct De on De.idPrDeltails = D.idPrDeltails
                join Products P on De.idProduct = P.idProduct
                join Size S on S.idSize = De.idSize
                join Color C on C.idColor = De.idColor
                join Material M on M.idMaterial = De.idMaterial
                where D.idInvoice = 1

SELECT idDetailsInvoiceSELL, nameProduct, name, valueSize, valueColor, valueMaterial, detailsInvoiceSELL.quatity, detailsInvoiceSELL.price  FROM dbo.detailsInvoiceSELL 
JOIN dbo.InvoiceSell ON InvoiceSell.idInvoiceSell = detailsInvoiceSELL.idInvoiceSell
JOIN dbo.Customer ON Customer.idCustomer = InvoiceSell.idCustomer
JOIN dbo.detailsProduct ON detailsProduct.idPrDeltails = detailsInvoiceSELL.idPrDetails
JOIN dbo.Products ON Products.idProduct = detailsProduct.idProduct JOIN dbo.Size ON Size.idSize = detailsProduct.idSize
JOIN dbo.Color ON Color.idColor = detailsProduct.idColor JOIN dbo.Material ON Material.idMaterial = detailsProduct.idMaterial
WHERE detailsInvoiceSELL.idInvoiceSell =9

SELECT * FROM dbo.InvoiceSell
SELECT idInvoiceSell, SUM(detailsInvoiceSELL.quatity * price)
AS N'Total'
FROM dbo.detailsInvoiceSELL
GROUP BY idInvoiceSell
HAVING idInvoiceSell = 4

SELECT *FROM dbo.[User]

UPDATE dbo.[User] SET name = ?, birthday = ?, gender = ?, phoneNumber = ?, address = ?,
email = ? WHERE idUser = ?

SELECT * FROM dbo.InvoiceSell JOIN dbo.Voucher ON Voucher.idVoucher = InvoiceSell.idVoucher

SELECT * FROM dbo.InvoiceSell JOIN dbo.[User] ON [User].idUser = InvoiceSell.idHumanSell JOIN dbo.Customer ON Customer.idCustomer = InvoiceSell.idCustomer
WHERE dateCreateInvoice 
SELECT * FROM dbo.InvoiceSell

UPDATE dbo.Account SET password = ? WHERE idUser = ?
SELECT * FROM dbo.Voucher


INSERT dbo.InvoiceReturn(idInvoiceSell,idCustomer, description,totalReturn)
VALUES(?,?,?,?)
SELECT * FROM dbo.DetailInvoiceReturn
INSERT INTO dbo.DetailInvoiceReturn
(idInvoiceReturn,idPrDetails,quatity,price)
VALUES
((SELECT TOP 1 idInvoiceReturn FROM dbo.InvoiceReturn ORDER BY idInvoiceReturn DESC),?,?,?)
--INSERT dbo.DetailInvoiceReturn( idInvoiceReturn, idPrDetails,quatity, price)VALUES(?,?,?,?)
SELECT * FROM dbo.DetailInvoiceReturn
SELECT idDetailInvoiceReturn, nameProduct, name, valueSize, valueColor, valueMaterial, DetailInvoiceReturn.quatity, detailsProduct.price * DetailInvoiceReturn.quatity AS N'price' FROM dbo.DetailInvoiceReturn JOIN dbo.InvoiceReturn ON InvoiceReturn.idInvoiceReturn = DetailInvoiceReturn.idDetailInvoiceReturn
JOIN dbo.Customer ON Customer.idCustomer = InvoiceReturn.idCustomer
JOIN dbo.detailsProduct ON detailsProduct.idPrDeltails = DetailInvoiceReturn.idPrDetails
JOIN dbo.Products ON Products.idProduct = detailsProduct.idProduct
JOIN dbo.Size ON Size.idSize = detailsProduct.idSize JOIN dbo.Color ON Color.idColor = detailsProduct.idColor
JOIN dbo.Material ON Material.idMaterial = detailsProduct.idMaterial 
WHERE dbo.DetailInvoiceReturn.idInvoiceReturn = 7


SELECT InvoiceSell.idInvoiceSell, idPrDetails, nameProduct, detailsInvoiceSELL.quatity, valueSize, valueColor, valueMaterial, detailsInvoiceSELL.price, name, Customer.idCustomer, dateCreateInvoice  FROM dbo.detailsInvoiceSELL
JOIN dbo.InvoiceSell ON InvoiceSell.idInvoiceSell = detailsInvoiceSELL.idInvoiceSell
JOIN dbo.Customer ON Customer.idCustomer = InvoiceSell.idCustomer
JOIN dbo.detailsProduct ON detailsProduct.idPrDeltails = detailsInvoiceSELL.idPrDetails
JOIN dbo.Products ON Products.idProduct = detailsProduct.idProduct JOIN dbo.Size ON Size.idSize = detailsProduct.idSize
JOIN dbo.Color ON Color.idColor = detailsProduct.idColor JOIN dbo.Material ON Material.idMaterial = detailsProduct.idMaterial
WHERE detailsInvoiceSELL.idInvoiceSell = 9 AND detailsInvoiceSELL.quatity > 0 AND InvoiceSell.idInvoiceSell NOT IN (SELECT idInvoiceSell FROM dbo.InvoiceReturn)

SELECT * FROM dbo.InvoiceReturn JOIN dbo.Customer ON Customer.idCustomer = InvoiceReturn.idCustomer WHERE dateCreateInvoice = ?

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
	SUM(detailsInvoiceSELL.price * detailsInvoiceSELL.quatity) totalSell, SUM(totalReturn) totalReturn, 
	SUM(detailsInvoiceSELL.price * detailsInvoiceSELL.quatity) - SUM(totalReturn) revenue
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
	WHERE YEAR(InvoiceSell.dateCreateInvoice) = @year
	GROUP BY MONTH(InvoiceSell.dateCreateInvoice)
-------------------------------
IF OBJECT_ID('sp_demo') IS NOT NULL
DROP PROC sp_demo
GO
CREATE PROC sp_demo
(@year int)
AS
BEGIN
    SELECT SUM(priceImport * quatity) TienNhap FROM dbo.detailsInvoiceImportPr JOIN dbo.InvoiceImportPr ON InvoiceImportPr.idInvoice = detailsInvoiceImportPr.idInvoice
	WHERE YEAR(dateCreateInvoice) = 2021
	GROUP BY MONTH(dateCreateInvoice)
END

EXEC dbo.sp_demo @year = 0 -- int


IF OBJECT_ID('sp_Return') IS NOT NULL
DROP FUNCTION sp_Return
GO
CREATE FUNCTION sp_Return
(@year int)
RETURNS @Bang TABLE (tienNhap FLOAT)
as
BEGIN
  INSERT INTO @Bang
      SELECT SUM(priceImport * quatity) TienNhap FROM dbo.detailsInvoiceImportPr JOIN dbo.InvoiceImportPr ON InvoiceImportPr.idInvoice = detailsInvoiceImportPr.idInvoice
	WHERE YEAR(dateCreateInvoice) = @year
	GROUP BY MONTH(dateCreateInvoice)
  RETURN
END

SELECT * FROM dbo.sp_Return(2021)

INSERT INTO dbo.SaveMoney
(dateCreate,moneyReturn,moneySell,moneyImport)
VALUES(?,?,?)

ALTER TABLE dbo.DetailInvoiceReturn DROP CONSTRAINT FK__DetailInv__idDet__6FE99F9F
ALTER TABLE dbo.DetailInvoiceReturn ADD FOREIGN KEY (idInvoiceReturn) REFERENCES dbo.InvoiceReturn(idInvoiceReturn)
DROP TABLE dbo.SaveMoney


SELECT * FROM Account
SELECT * FROM [user]

UPDATE dbo.[User] SET status = 1 WHERE idUser = 15









