/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package videoclub;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
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
    
    public Long getID()
    {
        return this.id;
    }
    
    public HashMap<String, Object> getAttributes() throws SQLException
    {
        HashMap<String, Object> atributos = new HashMap<String, Object>();
        this.db.createConnection();
        
        String table     = " categories ";
        String[] columns = { "id" };
        Long[] values    = { this.id };
        String[] conditions = {};
        ResultSet rs = this.db.searchInTableByValue(table, columns, values, conditions);
        
        if(rs.next())
        {
            Long category_id = (Long) rs.getObject("category_id");
            String name = (String) rs.getObject("name");
            
            atributos.put("id",          this.id);
            atributos.put("category_id", category_id);
            atributos.put("name",        name);
        }
        
        this.db.destroyConnection();
        
        return atributos;
    }
    
    public HashMap<String, Object> updateCategory(String[] columns, Object[] values) throws SQLException
    {
        HashMap<String, Object> atributos = getAttributes();
        this.db.createConnection();
        
        Set<String> k        = atributos.keySet();
        Collection<Object> v = (Collection<Object>) atributos.values();
        
        String[] columnsConditions = (String[]) k.toArray(new String[0]);
        Object[] valuesConditions  = (Object[]) v.toArray();
        
        String table = " categories ";
        this.db.updateInTable(table, columns, values, columnsConditions, valuesConditions, true);
        
        atributos = getAttributes();
        
        this.db.destroyConnection();
        return atributos;
    }
    
    public boolean destroyCategory() throws SQLException
    {
        HashMap<String, Object> atributos = getAttributes();
        this.db.createConnection();
        
        this.db.deleteInTable(
            " products_categories ", new String[] { "category_id" },
            new Object[] { this.id }, true
        );
        
        Set<String> k        = atributos.keySet();
        Collection<Object> v = (Collection<Object>) atributos.values();
        
        String[] columnsConditions = (String[]) k.toArray(new String[0]);
        Object[] valuesConditions  = (Object[]) v.toArray();
        
        String table = " categories ";
        
        int cambios = this.db.deleteInTable(table, columnsConditions, valuesConditions, true);
        
        this.db.destroyConnection();
        return (cambios > 0);
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
    
    public Product crearProducto(String[] columns, Object[] values) throws SQLException
    {
        int cambios = 0;
        
        Product p = Product.crearProducto(this.db, columns, values);
        
        this.db.createConnection();
        
        String table = " products_categories ";
        
        columns = new String[2]; columns[0] = "category_id"; columns[1] = "product_id";
        values  = new Object[2]; values[0]  = this.id; values[1] = p.getID();
        
        cambios = db.insertIntoTable(table, columns, values);
        
        this.db.destroyConnection();
        
        if(cambios > 0)
            return p;
        else
            return null;
    }
    
    public static Category crearCategoria(Database db, String[] columns, Object[] values) throws SQLException
    {
        Category c = null;
        
        db.createConnection();
        
        String table = " categories ";
        
        int exito = db.insertIntoTable(table, columns, values);
        
        
        if(exito > 0)
        {
            ResultSet rs = db.searchInTableByValue(table, columns, values, true);
            if(rs.next())
            {
                Long id = (Long) rs.getObject("id");
                c = new Category(db, id);
            }
        }
        
        db.destroyConnection();
        
        return c;
    }
}
