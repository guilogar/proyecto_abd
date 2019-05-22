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
import java.util.ArrayList;
import java.util.HashMap;

public class VideoClub
{
/*
    public Set<Producto> listarProductos()
    {
        Set<Producto> listaProductos = new Set<Producto>();

        Map<String, String> env = this.pb.environment();
        String host = env.get("HOST_DB");
        String dbname = env.get("DB_NAME");
        String dbuser = env.get("DB_USER");
        String dbpass = env.get("DB_PASS");

        Connection conexion = DriverManager.getConnection("jdbc:mysql://" + host + "/" + dbname, dbuser, dbpass);

        String query = "select * from products ";
        PreparedStatement sentencia = conexion.prepareStatement(query);
        ResultSet rs = sentencia.executeQuery();

        while(rs.next())
        {
            int id = (int) rs.getObject("id");
            String code = (String) rs.getObject("code");
            Producto p = new Producto(id, code, this.pb);
            listaProductos.add(p);
        }

        return listaProductos;
    }
*/
    public static ArrayList<Category> listarCategorias(Database db) throws SQLException
    {
        ArrayList<Category> lista = new ArrayList<Category>();
        
        if(db.createConnection())
        {
            ResultSet rs = db.selectByTable("categories");

            while(rs.next())
            {
                long id = (long) rs.getObject("id");
                Category c = new Category(db, id);
                lista.add(c);
                
                ArrayList<Product> productos = c.listarProductos();
                
                for(Product p: productos)
                {
                    HashMap<String, Object> ap = p.getAttributes();
                    System.out.println("Producto => " + ap.get("name"));
                }
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
        
        Database db = new Database(pb);
        
        listarCategorias(db);
    }
}