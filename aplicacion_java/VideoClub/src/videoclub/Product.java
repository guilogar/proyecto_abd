/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package videoclub;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

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
    
    public HashMap<String, Object> getAttributes() throws SQLException
    {
        HashMap<String, Object> atributos = new HashMap<String, Object>();
        this.db.createConnection();
        
        String table     = "products";
        String[] columns = { "id" };
        Long[] values    = { this.id };
        String[] conditions = {};
        ResultSet rs = this.db.searchInTableByValue(table, columns, values, conditions);
        
        if(rs.next())
        {
            String code = (String) rs.getObject("code");
            String name = (String) rs.getObject("name");
            String description = (String) rs.getObject("description");
            Date date = (Date) rs.getObject("date");
            Double price = (Double) rs.getObject("price");
            Integer stock = (Integer) rs.getObject("stock");
            
            atributos.put("id"         , this.id);
            atributos.put("code"       , code);
            atributos.put("name"       , name);
            atributos.put("description", name);
            atributos.put("date"       , date);
            atributos.put("price"      , price);
            atributos.put("stock"      , stock);
        }
        
        this.db.destroyConnection();
        
        return atributos;
    }
}