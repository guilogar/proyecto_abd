/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package videoclub;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

/**
 *
 * @author oem
 */
public class Category
{
    private Database db;
    private long id;
    
    public Category(Database db, long id) throws SQLException
    {
        this.db = db;
        this.id = id;
    }
    
    public HashMap<String, Object> getAttributes() throws SQLException
    {
        HashMap<String, Object> atributos = new HashMap<String, Object>();
        this.db.createConnection();
        
        String table     = "categories";
        String[] columns = { "id" };
        Long[] values    = { this.id };
        String[] conditions = {};
        ResultSet rs = this.db.searchInTableByValue(table, columns, values, conditions);
        
        if(rs.next())
        {
            Long category_id = (Long) rs.getObject("category_id");
            String name = (String) rs.getObject("name");
            
            atributos.put("id", this.id);
            atributos.put("category_id", category_id);
            atributos.put("name", name);
        }
        
        this.db.destroyConnection();
        
        return atributos;
    }
    
    public ArrayList<Product> listarProductos() throws SQLException
    {
        ArrayList<Product> productos = new ArrayList<Product>();
        this.db.createConnection();
        
        String table     = " products_categories pc left join products p on pc.product_id = p.id " ;
        String[] columns = { "category_id" };
        Long[] values    = { this.id };
        String[] conditions = {};
        ResultSet rs = this.db.searchInTableByValue(table, columns, values, conditions);
        
        while(rs.next())
        {
            long product_id = (long) rs.getObject("product_id");
            Product p = new Product(this.db, product_id);
            productos.add(p);
        }
        
        this.db.destroyConnection();
        
        return productos;
    }
}
