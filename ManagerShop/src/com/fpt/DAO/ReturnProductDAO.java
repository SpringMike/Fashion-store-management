/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fpt.DAO;

import com.fpt.entity.DetailInvoiceReturn;
import com.fpt.entity.DetailInvoiceSell;
import com.fpt.entity.InvoiceRetuns;
import com.fpt.entity.InvoiceSell;
import com.fpt.entity.ProductItem;
import com.fpt.helper.jdbcHelper;
import com.fpt.utils.XDate;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Đặng Đình Vũ
 */
public class ReturnProductDAO extends ShopDAO<InvoiceRetuns, Integer> {

    @Override
    public void insert(InvoiceRetuns e) {
        String sql = "INSERT Into dbo.InvoiceReturn(idInvoiceSell,idCustomer, description,totalReturn, idUser, dateCreateInvoice) VALUES(?,?,?,?,?,?)";
        jdbcHelper.update(sql, e.getIdInvoiceSell(), e.getIdCustomer(), e.getDescription(), e.getTotalReturn(), e.getIdUser(), e.getDateCreateInvoiceReturn());
    }

    @Override
    public void update(InvoiceRetuns e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(Integer k) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<InvoiceRetuns> selectAll() {
        String sql = "SELECT * FROM dbo.InvoiceReturn JOIN dbo.Customer ON Customer.idCustomer = InvoiceReturn.idCustomer";
        return selectBySql(sql);
    }

    @Override
    public InvoiceRetuns selectById(Integer k) {
        String sql = "SELECT * FROM dbo.InvoiceReturn JOIN dbo.Customer ON Customer.idCustomer = InvoiceReturn.idCustomer \n"
                + "where idInvoiceReturn = ?";
        List<InvoiceRetuns> list = selectBySql(sql, k);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    @Override
    protected List<InvoiceRetuns> selectBySql(String sql, Object... args) {
        List<InvoiceRetuns> list = new ArrayList<>();
        try {
            ResultSet rs = jdbcHelper.query(sql, args);
            while (rs.next()) {
                InvoiceRetuns p = new InvoiceRetuns();
                p.setIdInvoiceRetuns(rs.getInt("idInvoiceReturn"));
                p.setIdInvoiceSell(rs.getInt("idInvoiceSell"));
                p.setDateCreateInvoiceReturn(rs.getDate("dateCreateInvoice"));
                p.setIdCustomer(rs.getInt("idCustomer"));
                p.setTotalReturn(rs.getDouble("totalReturn"));
                p.setDescription(rs.getString("description"));
                p.setNameCustomer(rs.getString("name"));

                list.add(p);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    protected List<ProductItem> selectBySql1(String sql, Object... args) {
        List<ProductItem> list = new ArrayList<>();
        try {
            ResultSet rs = jdbcHelper.query(sql, args);
            while (rs.next()) {
                ProductItem p = new ProductItem();
                p.setIdInvoiceSell(rs.getInt("idInvoiceSell"));
                p.setId(rs.getInt("idPrDetails"));
                p.setPrice(rs.getFloat("price"));
                p.setQuantity(rs.getInt("quatity"));
                p.setSize(rs.getString("valueSize"));
                p.setColor(rs.getString("valueColor"));
                p.setMaterial(rs.getString("valueMaterial"));
                p.setProductName(rs.getString("nameProduct"));
                p.setNameCustomer(rs.getString("name"));
                p.setIdCustomer(rs.getInt("idCustomer"));
                p.setDateCreateInvoice(rs.getDate("dateCreateInvoice"));
                list.add(p);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<ProductItem> selectByIdInvoiceReturn(int id) {
        String sql = "SELECT InvoiceSell.idInvoiceSell, idPrDetails, nameProduct, detailsInvoiceSELL.quatity, valueSize, valueColor, valueMaterial, detailsInvoiceSELL.price, name, Customer.idCustomer, dateCreateInvoice  FROM dbo.detailsInvoiceSELL\n"
                + "                        JOIN dbo.InvoiceSell ON InvoiceSell.idInvoiceSell = detailsInvoiceSELL.idInvoiceSell\n"
                + "                               JOIN dbo.Customer ON Customer.idCustomer = InvoiceSell.idCustomer\n"
                + "                         JOIN dbo.detailsProduct ON detailsProduct.idPrDeltails = detailsInvoiceSELL.idPrDetails\n"
                + "                           JOIN dbo.Products ON Products.idProduct = detailsProduct.idProduct JOIN dbo.Size ON Size.idSize = detailsProduct.idSize\n"
                + "                              JOIN dbo.Color ON Color.idColor = detailsProduct.idColor JOIN dbo.Material ON Material.idMaterial = detailsProduct.idMaterial\n"
                + "                               WHERE detailsInvoiceSELL.idInvoiceSell = ? AND detailsInvoiceSELL.quatity > 0";

        return selectBySql1(sql, id);
    }

    public void sellProductItem(Integer quantity, Integer id) {
        String sql = "UPDATE dbo.detailsInvoiceSELL\n"
                + "SET quatity -= ? \n"
                + "WHERE idInvoiceSell = ?;";
        jdbcHelper.update(sql, quantity, id);
    }

    public int totalPage(String Stringdate) {
        ResultSet rs;
        if (!Stringdate.isEmpty()) {
            java.util.Date date = XDate.toDate(Stringdate, "dd-MM-yyyy");
            String sql = " select count(*) as soLuong from InvoiceReturn where dateCreateInvoice = ?";
            try {
                rs = jdbcHelper.query(sql, date);
                while (rs.next()) {
                    return rs.getInt("soLuong");
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        String sql = "select count(*) as soLuong from InvoiceReturn";
        try {
            rs = jdbcHelper.query(sql);
            while (rs.next()) {
                return rs.getInt("soLuong");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public List<InvoiceRetuns> pagingPage(int page, int pageSize, String Stringdate) {
        if (!Stringdate.isEmpty()) {
            java.util.Date date = XDate.toDate(Stringdate, "dd-MM-yyyy");
            String sql = "SELECT * FROM dbo.InvoiceReturn JOIN dbo.Customer ON Customer.idCustomer = InvoiceReturn.idCustomer \n"
                    + " where dateCreateInvoice =?\n"
                    + "order by idInvoiceReturn OFFSET ? ROWS FETCH FIRST ? ROWS ONLY";
            return selectBySql(sql, date, (page - 1) * pageSize, pageSize);
        }
        String sql = "SELECT * FROM dbo.InvoiceReturn JOIN dbo.Customer ON Customer.idCustomer = InvoiceReturn.idCustomer \n"
                + "order by idInvoiceReturn OFFSET ? ROWS FETCH FIRST ? ROWS ONLY";
        return selectBySql(sql, (page - 1) * pageSize, pageSize);
    }
}
