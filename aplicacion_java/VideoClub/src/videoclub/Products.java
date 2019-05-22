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

public class Products
{
    private int id;
    private String code;
    private ProcessBuilder pb;

    public Products(int id, String code, ProcessBuilder pb)
    {
        this.id = id;
        this.code = code;
        this.pb = pb;
    }

    public void updateAttribute(String[] columnas, String[] valores)
    {
        if(columnas.length > 0 && valores.length > 0 && columnas.length == valores.length)
        {
            Map<String, String> env = this.pb.environment();
            String host = env.get("HOST_DB");
            String dbname = env.get("DB_NAME");
            String dbuser = env.get("DB_USER");
            String dbpass = env.get("DB_PASS");

            Connection conexion = DriverManager.getConnection("jdbc:mysql://" + host + "/" + dbname, dbuser, dbpass);

            String query = "update products set ";
            for (int i = 0; i < columnas.length; i++)
            {
                query += columnas[i] + " = ? ,"; // + valores[i];
            }
            query.substring(0, query.length() - 1);
            query += " where id = ? and code = ? ";

            PreparedStatement sentencia = conexion.prepareStatement(query);
            int v = 0;
            for (v = 0; v < valores.length; v++)
            {
                sentencia.setString(v+1, valores[v]);
            }
            sentencia.setString(++v, (String) this.id);
            sentencia.setString(++v, (String) this.code);

            ResultSet rs = sentencia.executeUpdate();

            ResultSet rs = this.getAttributes();
            this.id = (int) rs.getObject("id");
            this.code = (String) rs.getObject("code");
        } else
        {
            throw new Exception("Error. Lista de columnas y/o valores incorrecta.");
        }
    }

    public ResultSet getAttributes()
    {
        Map<String, String> env = this.pb.environment();
        String host = env.get("HOST_DB");
        String dbname = env.get("DB_NAME");
        String dbuser = env.get("DB_USER");
        String dbpass = env.get("DB_PASS");

        Connection conexion = DriverManager.getConnection("jdbc:mysql://" + host + "/" + dbname, dbuser, dbpass);

        String query = "select * from products where id = ? and code = ? ";
        PreparedStatement sentencia = conexion.prepareStatement(query);
        sentencia.setString(1, this.id);
        sentencia.setString(2, this.code);
        ResultSet rs = sentencia.executeQuery();

        return rs;
    }

    public void deleteProduct()
    {
        Map<String, String> env = this.pb.environment();
        String host = env.get("HOST_DB");
        String dbname = env.get("DB_NAME");
        String dbuser = env.get("DB_USER");
        String dbpass = env.get("DB_PASS");

        Connection conexion = DriverManager.getConnection("jdbc:mysql://" + host + "/" + dbname, dbuser, dbpass);

        String query = "delete from products where id = ? and code = ? ";
        PreparedStatement sentencia = conexion.prepareStatement(query);
        sentencia.setString(1, this.id);
        sentencia.setString(2, this.code);
        ResultSet rs = sentencia.executeUpdate();
    }
}