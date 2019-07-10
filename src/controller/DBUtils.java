    
package controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/* @author Hoang */

public class DBUtils {
    
    private static Connection connection;
    
    private DBUtils() {};
    
    private static String dbName;
    private static String dbUser;
    private static String dbPass;

    public static void setDbName(String dbName) {
        DBUtils.dbName = dbName;
    }

    public static void setDbUser(String dbUser) {
        DBUtils.dbUser = dbUser;
    }

    public static void setDbPass(String dbPass) {
        DBUtils.dbPass = dbPass;
    }

    
    public static synchronized Connection getConnection() throws SQLException{
        
        if(connection!=null){
            return connection;
        }else{
            String url = "jdbc:sqlserver://localhost:1433;instance=SQLEXPRESS;databaseName="+dbName;
            try{
                connection = DriverManager.getConnection(url,dbUser,dbPass);
                return connection;    
            }catch(SQLException e1){
                return null;
            }
        }
    }
    
        
    public static synchronized void closeConnection() throws SQLException{
        
        if(connection!=null){
            try{
                connection.close();
            }catch(SQLException e1){
                System.out.println("Error: " + e1);
            }finally{
                connection=null;
            }
        }
    }
        
    
    
    
    
    
    
}
