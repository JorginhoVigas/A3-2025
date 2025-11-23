package modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataBaseDAO {
    
    
   private static final String URL = "jdbc:mysql://localhost:3306/apex_bd";
   private static final String USER = "root";
   private static final String SENHA = "";
   
   public Connection conn;
   
   //construtor que carrega o driver
   public DataBaseDAO() throws ClassNotFoundException{
       Class.forName("com.mysql.jdbc.Driver");
   }
   
   //Método para conectar ao banco de dados
   public void conectar() throws SQLException{
       if(conn == null || conn.isClosed()){
           conn = DriverManager.getConnection(URL, USER, SENHA);
       }
   }
   
   //Método para desconectar do banco de dados
   public void desconectar(){
       if(conn != null){
           try{
               conn.close();
           }catch (SQLException e){
              System.out.println("Erro ao fechar a conexão"+e);
           }
       }
   }
   
   //Método para obter a conexão (caso precise usá-la fora da classe)
   public Connection getConnection(){
      return conn;
   }
    
}
