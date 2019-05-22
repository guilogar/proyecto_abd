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

public class VideoClub
{
    private ProcessBuilder pb;

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

    public Set<Categoria> listarCategorias()
    {
        Set<Producto> listaCategorias = new Set<Categoria>();

        Map<String, String> env = this.pb.environment();
        String host = env.get("HOST_DB");
        String dbname = env.get("DB_NAME");
        String dbuser = env.get("DB_USER");
        String dbpass = env.get("DB_PASS");

        Connection conexion = DriverManager.getConnection("jdbc:mysql://" + host + "/" + dbname, dbuser, dbpass);

        String query = "select * from categories ";
        PreparedStatement sentencia = conexion.prepareStatement(query);
        ResultSet rs = sentencia.executeQuery();

        while(rs.next())
        {
            int id = (int) rs.getObject("id");
            Categoria c = new Categoria(id, this.pb);
            listaCategorias.add(p);
        }

        return listaProductos;
    }

    public static void main(String[] args)
    {
        this.pb = new ProcessBuilder();
        Map<String, String> env = this.pb.environment();
        env.put("HOST_DB", "HOST_DB");
        env.put("DB_NAME", "DB_NAME");
        env.put("DB_USER", "DB_USER");
        env.put("DB_PASS", "DB_PASS");

    }
}