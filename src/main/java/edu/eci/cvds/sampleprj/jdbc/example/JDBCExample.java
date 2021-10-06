/*
* Copyright (C) 2015 hcadavid
*
* This program is free software: you can redistribute it and/or modify
* it under the terms of the GNU General Public License as published by
* the Free Software Foundation, either version 3 of the License, or
* (at your option) any later version.
*
* This program is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
* GNU General Public License for more details.
*
* You should have received a copy of the GNU General Public License
* along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/
package edu.eci.cvds.sampleprj.jdbc.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.mysql.jdbc.ResultSetMetaData;

/**
*
* @author hcadavid
*/
public class JDBCExample {
   
   public static void main(String args[]){
       try {
           String url="jdbc:mysql://desarrollo.is.escuelaing.edu.co:3306/bdprueba";
           String driver="com.mysql.jdbc.Driver";
           String user="bdprueba";
           String pwd="prueba2019";
                       
           Class.forName(driver);
           Connection con=DriverManager.getConnection(url,user,pwd);
           con.setAutoCommit(false);
                
           
           System.out.println("Valor total pedido 1:"+valorTotalPedido(con, 1));
           
           List<String> prodsPedido=nombresProductosPedido(con, 1);
           
           
           System.out.println("Productos del pedido 1:");
           System.out.println("-----------------------");
           for (String nomprod:prodsPedido){
               System.out.println(nomprod);
           }
           System.out.println("-----------------------");
           
           
           int suCodigoECI=2167892;
           //registrarNuevoProducto(con, suCodigoECI, "CRISTIAN", 99999999);            
           //con.commit();         
           con.close();
                                  
       } catch (ClassNotFoundException | SQLException ex) {
           Logger.getLogger(JDBCExample.class.getName()).log(Level.SEVERE, null, ex);
       }
       
       
   }
   
   /**
    * Agregar un nuevo producto con los parámetros dados
    * @param con la conexión JDBC
    * @param codigo
    * @param nombre
    * @param precio
    * @throws SQLException 
    */
   public static void registrarNuevoProducto(Connection con, int codigo, String nombre,int precio) throws SQLException{
        String insertProduct = "INSERT INTO ORD_PRODUCTOS VALUES(?,?,?)";
        //Crear preparedStatement
        PreparedStatement addProduct = con.prepareStatement(insertProduct);
        //Asignar parámetros
        addProduct.setInt(1, codigo);
        addProduct.setString(2, nombre);
        addProduct.setInt(3, precio);
        //usar 'execute'
        addProduct.executeUpdate();
        con.commit();
   }
   
   /**
    * Consultar los nombres de los productos asociados a un pedido
    * @param con la conexión JDBC
    * @param codigoPedido el código del pedido
    * @return 
    */
   public static List<String> nombresProductosPedido(Connection con, int codigoPedido){
       List<String> np=new LinkedList<>();
       String selectString = "SELECT ORD_PRODUCTOS.nombre FROM ORD_PRODUCTOS INNER JOIN ORD_DETALLE_PEDIDO INNER JOIN ORD_PEDIDOS WHERE ORD_PEDIDOS.codigo=? ";
       try{
        //Crear prepared statement
        PreparedStatement selectQuery = con.prepareStatement(selectString);
        //asignar parámetros
        selectQuery.setInt(1, codigoPedido);
        //usar executeQuery
        ResultSet consulta = selectQuery.executeQuery();
        //Sacar resultados del ResultSet
        ResultSetMetaData resultado = (ResultSetMetaData) consulta.getMetaData();
        //Llenar la lista y retornarla
        while (consulta.next()){
            np.add(consulta.getString("ORD_PRODUCTOS.nombre"));
        }
       }catch(Exception e){
            System.out.println(e.getMessage());
       }
       return np;
   }

   
   /**
    * Calcular el costo total de un pedido
    * @param con
    * @param codigoPedido código del pedido cuyo total se calculará
    * @return el costo total del pedido (suma de: cantidades*precios)
    */
   public static int valorTotalPedido(Connection con, int codigoPedido){
        String precio = "";
        int cantidad = 0;
        int valorTotal = 0;
        //Crear prepared statement
        String ConsultaValorPedido = "SELECT SUM(ORD_PRODUCTOS.precio*ORD_DETALLE_PEDIDO.cantidad) AS SUMA FROM ORD_PEDIDOS INNER JOIN ORD_DETALLE_PEDIDO INNER JOIN ORD_PRODUCTOS WHERE ORD_PEDIDOS.codigo = ?";
       try{
        //Crear prepared statement
        PreparedStatement selectQuery = con.prepareStatement(ConsultaValorPedido);
        //asignar parámetros
        selectQuery.setInt(1,codigoPedido);
        System.out.println("Error aqui");
        //usar executeQuery
        ResultSet consulta = selectQuery.executeQuery();
        //Sacar resultado del ResultSet
        precio = consulta.getString("SUMA");
        System.out.println(precio + "" + cantidad+"" +"" +valorTotal);
        cantidad = consulta.getInt("d.cantidad");
       }catch(Exception e){
            System.out.println(e.getMessage() + e.getCause());
       }
       
       return valorTotal;
   }
   

   
   
   
}