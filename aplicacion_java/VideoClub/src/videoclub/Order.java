/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package videoclub;

import java.sql.Connection;
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
public class Order
{
    private Database db;
    private long id;
    
    public Order(Database db, long id) throws SQLException
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
        
        String table     = " orders ";
        String[] columns = { "id" };
        Long[] values    = { this.id };
        String[] conditions = {};
        ResultSet rs = this.db.searchInTableByValue(table, columns, values, conditions);
        
        if(rs.next())
        {
            Long order_id     = (Long) rs.getObject("id");
            Integer status_id = (Integer) rs.getObject("status_id");
            Long client_id    = (Long) rs.getObject("client_id");
            Long worker_id    = (Long) rs.getObject("worker_id");
            String code       = (String) rs.getObject("code");
            Date created_at   = (Date) rs.getObject("created_at");
            Date start_at     = (Date) rs.getObject("start_at");
            Date end_at       = (Date) rs.getObject("end_at");
            Date delivered_at = (Date) rs.getObject("delivered_at");
            Double discount   = (Double) rs.getDouble("discount");
            if(rs.wasNull()) discount = null;
            
            atributos.put("id"          , this.id);
            atributos.put("status_id"   , status_id);
            atributos.put("client_id"   , client_id);
            atributos.put("worker_id"   , worker_id);
            atributos.put("code"        , code);
            atributos.put("created_at"  , created_at);
            atributos.put("start_at"    , start_at);
            atributos.put("end_at"      , end_at);
            atributos.put("delivered_at", delivered_at);
            atributos.put("discount"    , discount);
        }
        
        this.db.destroyConnection();
        
        return atributos;
    }
    
    public boolean destroyOrder() throws SQLException
    {
        HashMap<String, Object> atributos = getAttributes();
        this.db.createConnection();
        
        this.db.deleteInTable(
            " orders_products ", new String[] { "order_id" },
            new Object[] { this.id }, true
        );
        
        Set<String> k        = atributos.keySet();
        Collection<Object> v = (Collection<Object>) atributos.values();
        
        String[] columnsConditions = (String[]) k.toArray(new String[0]);
        Object[] valuesConditions  = (Object[]) v.toArray();
        
        String table = " orders ";
        
        int cambios = this.db.deleteInTable(table, columnsConditions, valuesConditions, true);
        
        this.db.destroyConnection();
        return (cambios > 0);
    }
    
    public static Order crearOrder(Database db, String[] columns, Object[] values, HashMap<Product, Long> productos) throws SQLException
    {
        Order o = null;
        
        db.createConnection();
        
        String table = " orders ";
        
        int exito = db.insertIntoTable(table, columns, values);
        
        if(exito > 0)
        {
            ResultSet rs = db.searchInTableByValue(table, columns, values, true);
            
            if(rs.next())
            {
                Long id = (Long) rs.getObject("id");
                o = new Order(db, id);
                
                Set<Product> k = productos.keySet();
                for (Product p : k)
                {
                    long qty = productos.get(p);
                    double price = qty * (Double) p.getAttributes().get("price");                    
                    columns = new String[] { "order_id", "product_id", "qty", "price" };
                    values  = new Object[] { o.getID(), p.getID(), qty, price};
                    
                    db.createConnection();
                    db.insertIntoTable(" orders_products ", columns, values);
                }
            }
        }
        
        db.destroyConnection();
        
        return o;
    }
}
