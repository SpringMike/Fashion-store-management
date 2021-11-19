ALTER TABLE dbo.Material ALTER COLUMN valueMaterial NVARCHAR(50) NOT NULL
ALTER TABLE dbo.Color ALTER COLUMN valueColor NVARCHAR(50) NOT NULL
----
ALTER TABLE dbo.Products ADD statusDelete BIT DEFAULT 1
-- xoá
ALTER TABLE dbo.Products DROP CONSTRAINT FK__Products__idSupp__44FF419A
ALTER TABLE dbo.Products DROP COLUMN idSupplier


INSERT INTO dbo.Products
(
    idList,
    nameProduct,
    description,
    status,
    statusDelete
)
VALUES
(   6,      -- idList - int
    N'Giày Vải',    -- nameProduct - nvarchar(255)
    NULL,   -- description - nvarchar(255)
    1,   -- status - bit
    DEFAULT -- statusDelete - bit
    )
UPDATE dbo.Products SET statusDelete = 0 WHERE idProduct = 15
SELECT * FROM dbo.Account
SELECT * FROM dbo.Products
SELECT * FROM dbo.[User]
INSERT INTO dbo.Products
(
    idList,
    nameProduct,
    description,
    status
)
VALUES
(   1,    -- idList - int
    N'Done',  -- nameProduct - nvarchar(255)
    NULL, -- description - nvarchar(255)
    NULL  -- status - bit
    )
SELECT * FROM dbo.Products JOIN dbo.List ON List.idList = Products.idList WHERE status = 1