package pt.upskill;

import java.sql.*;

public class MyDBUtils {

    public enum db_type {DB_MYSQL, DB_SQLSERVER, DB_SQLITE};

    private static String get_connection_string (db_type type, String server, String port, String db, String user, String pwd){
        switch (type){
            case DB_MYSQL: return  "jdbc:mysql://"+ server +":"+ port+"/"+db;
        }
        return null;
    }

    public static Connection get_connection(db_type type, String server, String port, String db, String user, String pwd) throws SQLException

    {
        String connString = get_connection_string(type,server,port,db,user,pwd);

        Connection conn = DriverManager.getConnection(connString,user,pwd);
        return conn;
    }

    /**
     * executa na BD o comando (não query) armazenado na string sqlCmd, que associada à conexão enviada como parâmetro.
     * @param conn
     * @param sqlCmd
     * @return Devolve o número de registos afetado pela execução do comando ou -1, em caso de erro.
     */
    public static int exec_sql (Connection conn,String sqlCmd) throws SQLException
    {

            Statement statement = conn.createStatement();
            int n_regs = statement.executeUpdate(sqlCmd);
            return n_regs;

    }


    /**
     * Crie os métodos públicos que devolvem a string contendo o comando SELECT corretamente construído a partir dos valores
     * enviados a cada função. Apenas o parâmetro fields tem de ter conteúdo não nulo e não vazio:
     * o
     * get_select_command(field)
     * o
     * get_select_command(fields, tables, where_cond)
     * o
     * get_select_command(fields, tables, where_cond, order_by)
     * o
     * get_select_command(fields, tables, where_cond, group_by, having, order_by)
     */


    public static String get_select_command(String field){
        return "SELECT " + field;
    }

    /**
     * get_select_command("id_pessoa, nome AS fullName", "pessoa", "sexo='m'", "apelido, nome")
     * @param fields
     * @param tables
     * @param where_cond
     * @return
     */
    public static String get_select_command(String fields, String tables, String where_cond)
    {
       return "SELECT "+ fields+ " FROM " + tables + " WHERE " + where_cond;

    }

    public static String get_select_command(String fields, String tables, String where_cond, String order_by)
    {
        return (get_select_command(fields,tables, where_cond) + " ORDER BY " + order_by);
    }

    public static String get_select_command(String fields, String tables, String where_cond,
                                            String group_by, String having, String order_by)
    {
        return (get_select_command(fields,tables, where_cond) + " GROUP BY "
                + group_by + " HAVING " + having + " ORDER BY " + order_by);
    }


    /** Crie o método público Boolean exist(conn, table, where) que verifica se existe algum registo nas condições enviadas à função.
     *
     */

    public static boolean  exist(Connection conn, String table, String where) throws SQLException
    {

        Statement statement = conn.createStatement();
        String cmdSQL= get_select_command("count(*)", table, where);
        ResultSet rs = statement.executeQuery(cmdSQL);

        return (rs.next() && rs.getInt(1) !=0) ? true: false;

    }

}
