/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package videoclub;

/**
 *
 * @author oem
 */
import java.util.Map;
import java.sql.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;

public class VideoClub
{
    public static ArrayList<Category> listarCategorias(Database db) throws SQLException
    {
        ArrayList<Category> lista = new ArrayList<>();
        
        if(db.createConnection())
        {
            ResultSet rs = db.selectByTable("categories");

            while(rs.next())
            {
                long id = (long) rs.getObject("id");
                Category c = new Category(db, id);
                lista.add(c);
                
                /*
                ArrayList<Product> productos = c.listarProductos();
                
                for(Product p: productos)
                {
                    HashMap<String, Object> ap = p.getAttributes();
                }
                */
            }
        } else
        {
            System.out.println("Error inesperado");
        }
        
        return lista;
    }

    public static void main(String[] args) throws SQLException
    {
        ProcessBuilder pb = new ProcessBuilder();
        Map<String, String> env = pb.environment();
        env.put("DB_HOST", "localhost");
        env.put("DB_NAME", "videoclub");
        env.put("DB_USER", "usuario");
        env.put("DB_PASS", "usuario");
        
        try
        {
            Database db = new Database(pb);
            //listarCategorias(db);
            
            long id = 1;
            String[] columns = new String[] { "id", "category_id", "name" };
            Object[] values  = new Object[] { id, null, "Videojuegos"};

            Category c = Category.crearCategoria(db, columns, values);

            HashMap<String, Object> ac = c.getAttributes();
            System.out.println("Categoria => " + ac.get("name"));
            
            values[2] = "Videojuegos para alquilar";
            c.updateCategory(columns, values);
            
            Instant t = Instant.now(); long miliseconds = t.toEpochMilli();
            Date date          = new Date(miliseconds);

            columns = new String[] { "id", "code", "name", "description", "price", "stock", "date" };
            values  = new Object[] { id, "1", "Nico nico niii", "Baka baka hentai!", 25.5, 6, date };
            //Product p = Product.crearProducto(db, columns, values);
            Product p = c.crearProducto(columns, values);

            HashMap<String, Object> ap = p.getAttributes();
            System.out.println("Producto => " + ap.get("name"));
            
            columns = new String[] { "id", "status_id", "client_id", "worker_id", "code", "created_at", "start_at", "end_at", "delivered_at", "discount" };
            values  = new Object[] {  id, 1, 1, 1, "5", date, date, date, date, null };
            
            HashMap<Product, Long> productos = new HashMap<> ();
            productos.put(p, 2L);
            productos.put(p, 6L);
            
            // insert into statuses(id, name) values(1, 'estado');
            // insert into clients(id, user_id, address) values(1, NULL, 'debajo de un puente');
            // insert into workers(id, name, surname, dni, phone, email, iban, ss) values(1, 'nombre', 'surname', 'dni', 'phone', 'email', 'iban', 'ss');
            Order o = Order.crearOrder(db, columns, values, productos);
            
            c.destroyCategory();
            p.destroyProduct();
            o.destroyOrder();
        } catch(Exception ex)
        {
            System.err.println(ex.getMessage());
            throw ex;
        }
    }
}