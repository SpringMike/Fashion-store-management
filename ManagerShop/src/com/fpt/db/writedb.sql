ALTER TABLE dbo.Material ALTER COLUMN valueMaterial NVARCHAR(50) NOT NULL
ALTER TABLE dbo.Color ALTER COLUMN valueColor NVARCHAR(50) NOT NULL
----
ALTER TABLE dbo.Products ADD statusDelete BIT DEFAULT 1
-- xoá
ALTER TABLE dbo.Products DROP CONSTRAINT FK__Products__idSupp__44FF419A
ALTER TABLE dbo.Products DROP COLUMN idSupplier
ALTER TABLE dbo.List ADD status BIT DEFAULT 1
-----------------------------
SELECT * FROM dbo.List
SELECT * FROM dbo.Products

SELECT * FROM dbo.Products JOIN dbo.List ON List.idList = Products.idList WHERE statusDelete = 1 AND List.status = 1


SELECT * FROM dbo.Size
SELECT * FROM dbo.Material
SELECT * FROM dbo.Color


SELECT * FROM dbo.detailsProduct
SELECT * FROM dbo.ImageProducts


DELETE dbo.detailsProduct WHERE idPrDeltails
-- vũ ngáo.....(code siêu bẩn)

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

EXEC dbo.PRDelete @idPrDetails = 7 -- int


