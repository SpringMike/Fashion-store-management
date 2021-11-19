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

UPDATE dbo.List SET status = 0 WHERE idList NOT IN (SELECT idList FROM dbo.Products) AND List.idList = ?