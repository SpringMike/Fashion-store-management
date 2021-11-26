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
ALTER TABLE dbo.detailsInvoice DROP CONSTRAINT PK__detailsI__5C4F67505F5F8AD9
ALTER TABLE dbo.detailsInvoice DROP COLUMN detailsInvoice
ALTER TABLE dbo.detailsInvoice ADD detailsInvoice INT IDENTITY(1,1) PRIMARY KEY
EXEC sys.sp_rename 'detailsInvoice', 'detailsInvoiceImportPr'

ALTER TABLE dbo.detailsInvoiceImportPr DROP CONSTRAINT FK__detailsIn__idSup__4222D4EF
ALTER TABLE dbo.detailsInvoiceImportPr DROP COLUMN idSupplier
ALTER TABLE dbo.InvoiceImportPr ADD idSupplier INT
ALTER TABLE dbo.InvoiceImportPr ADD FOREIGN KEY (idSupplier) REFERENCES dbo.Supplier(idSupplier)
ALTER TABLE dbo.InvoiceImportPr ADD description NVARCHAR(255)
ALTER TABLE dbo.detailsInvoiceImportPr ADD priceImport MONEY

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

SELECT * FROM dbo.InvoiceImportPr
SELECT * FROM dbo.detailsInvoiceImportPr

SELECT * FROM dbo.InvoiceSell

SELECT * FROM dbo.detailsInvoiceSELL

select D.*,P.nameProduct,S.valueSize,C.valueColor,M.valueMaterial,nameList,quatity from detailsProduct D
                 INNER JOIN Size S on D.idSize = S.idSize INNER JOIN Material M on M.idMaterial = D.idMaterial
                 INNER JOIN Color C on C.idColor = D.idColor
                 INNER JOIN Products P on P.idProduct = D.idProduct
                 INNER JOIN List L  on L.idList = P.idList
                 where D.status = 1 and D.quatity > 0

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
WHERE detailsInvoiceSELL.idInvoiceSell = 1

SELECT idInvoiceSell, (SUM(detailsInvoiceSELL.quatity * price) - (SUM(detailsInvoiceSELL.quatity * price) * (valueVoucher / 100)))
AS N'Total'
FROM dbo.detailsInvoiceSELL JOIN dbo.Voucher ON Voucher.quatity = detailsInvoiceSELL.quatity
GROUP BY idInvoiceSell, valueVoucher
HAVING idInvoiceSell = 4













