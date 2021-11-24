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













