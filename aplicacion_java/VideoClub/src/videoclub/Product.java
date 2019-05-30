/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package videoclub;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Set;

/**
 *
 * @author oem
 */
public class Product
{
    private Database db;
    private long id;
    
    public Product(Database db, long product_id)
    {
        this.db = db;
        this.id = product_id;
    }
    
    public boolean equals(Product p)
    {
        return this.id == p.getID();
    }
    
    public Long getID()
    {
        return this.id;
    }
    
    public HashMap<String, Object> getAttributes() throws SQLException
    {
        HashMap<String, Object> atributos = new HashMap<String, Object>();
        this.db.createConnection();
        
        String table     = " products ";
        String[] columns = { "id" };
        Long[] values    = { this.id };
        String[] conditions = {};
        ResultSet rs = this.db.searchInTableByValue(table, columns, values, conditions);
        
        if(rs.next())
        {
            String code        = (String)  rs.getObject("code");
            String name        = (String)  rs.getObject("name");
            String description = (String)  rs.getObject("description");
            Date date          = (Date)    rs.getObject("date");
            Double price       = (Double)  rs.getObject("price");
            Integer stock      = (Integer) rs.getObject("stock");
            
            atributos.put("id"         , this.id);
            atributos.put("code"       , code);
            atributos.put("name"       , name);
            atributos.put("description", description);
            atributos.put("date"       , date);
            atributos.put("price"      , price);
            atributos.put("stock"      , stock);
        }
        
        this.db.destroyConnection();
        
        return atributos;
    }
    
    public HashMap<String, Object> updateProduct(String[] columns, Object[] values) throws SQLException
    {
        HashMap<String, Object> atributos = getAttributes();
        this.db.createConnection();
        
        Set<String> k        = atributos.keySet();
        Collection<Object> v = (Collection<Object>) atributos.values();
        
        String[] columnsConditions = (String[]) k.toArray(new String[0]);
        Object[] valuesConditions  = (Object[]) v.toArray();
        
        String table = " products ";
        this.db.updateInTable(table, columns, values, columnsConditions, valuesConditions, true);
        
        atributos = getAttributes();
        
        this.db.destroyConnection();
        return atributos;
    }
    
    public boolean destroyProduct() throws SQLException
    {
        HashMap<String, Object> atributos = getAttributes();
        this.db.createConnection();
        
        this.db.deleteInTable(
            " products_categories ", new String[] { "product_id" },
            new Object[] { this.id }, true
        );
        
        this.db.deleteInTable(
            " orders_products ", new String[] { "product_id" },
            new Object[] { this.id }, true
        );
        
        Set<String> k        = atributos.keySet();
        Collection<Object> v = (Collection<Object>) atributos.values();
        
        String[] columnsConditions = (String[]) k.toArray(new String[0]);
        Object[] valuesConditions  = (Object[]) v.toArray();
        
        String table = " products ";
        
        int cambios = this.db.deleteInTable(table, columnsConditions, valuesConditions, true);
        
        this.db.destroyConnection();
        return (cambios > 0);
    }
    
    public static Product crearProducto(Database db, String[] columns, Object[] values) throws SQLException
    {
        Product p = null;
        
        db.createConnection();
        
        String table = " products ";
        
        int exito = db.insertIntoTable(table, columns, values);
        
        if(exito > 0)
        {
            ResultSet rs = db.searchInTableByValue(table, columns, values, true);
            if(rs.next())
            {
                Long id = (Long) rs.getObject("id");
                p = new Product(db, id);
            }
        }
        
        db.destroyConnection();
        
        return p;
    }
}