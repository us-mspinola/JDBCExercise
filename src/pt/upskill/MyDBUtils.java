package pt.upskill;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class MyDBUtils {

    public enum db_type {DB_MYSQL, DB_SQLSERVER, DB_SQLITE};

    private static String get_connection_string (db_type type, String server, String port, String db, String user, String pwd){
        switch (type){
            case DB_MYSQL: return  "jdbc:mysql://"+ server +":"+ port+"/"+db;
        }
        return null;
    }

    public static Connection get_connection(db_type type, String server, String port, String db, String user, String pwd){
        String connString = get_connection_string(type,server,port,db,user,pwd);

        try {
            Connection conn = DriverManager.getConnection(connString,user,pwd);
            return conn;
        } catch (SQLException e) {
            System.out.println(e.toString());
            return null;
        }
    }
    public static int exec_sql (Connection conn,String sqlCmd){
        try {
            Statement statement = conn.createStatement();
            int n_regs = statement.executeUpdate(sqlCmd);
            return n_regs;

        } catch (SQLException e) {
            System.out.println(e.toString());

        }
        return -1;
    }
}
