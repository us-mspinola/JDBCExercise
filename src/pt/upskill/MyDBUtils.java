package pt.upskill;

import java.sql.*;
import java.util.StringTokenizer;

public class MyDBUtils {

    public enum db_type {DB_MYSQL, DB_SQLSERVER, DB_SQLITE}

    private static String get_connection_string (db_type type, String server, String port, String db, String user, String pwd){
        switch (type){
            case DB_MYSQL: return  "jdbc:mysql://"+ server +":"+ port+"/"+db;
        }
        return null;
    }

    /**
     *
     * @param type
     * @param server
     * @param port
     * @param db
     * @param user
     * @param pwd
     * @return
     * @throws SQLException
     */
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
     * @author Maria Spínola
     * @param conn
     * @param sqlCmd
     * @return
     * @throws SQLException
     */
    private static ResultSet exec_query(Connection conn,String sqlCmd) throws SQLException
    {
        Statement statement = conn.createStatement();
        ResultSet rs = statement.executeQuery(sqlCmd);
        return rs;
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


    /**
     *
     * @param conn
     * @param table
     * @param where
     * @return
     * @throws SQLException
     */

    public static boolean exist(Connection conn, String table, String where) throws SQLException
    {

        String cmdSQL= get_select_command("count(*)", table, where);
        ResultSet rs = exec_query(conn,cmdSQL);

        return rs.next() && rs.getInt(1) != 0;

    }

    /**
     * Crie o método público Object lookup que devolve o valor do campo field existente na tabela
     * ou o valor de default, caso não exista o registo.
     * @param conn
     * @param field
     * @param table
     * @param where_cond
     * @param default_value
     * @return
     *
     * Exemplo int cod_cor = 194;
     * String color_description = lookup(conn, "colorName", "TColor", "id_cor=" + cod_cor, "NO COLOR FOUND")
     */
    public static Object lookup(Connection conn, String field, String table, String where_cond,String default_value) throws SQLException
    {
        String cmdSQL = get_select_command(field, table, where_cond);
        ResultSet rs = exec_query(conn, cmdSQL);

        if (rs.next())
            return rs.getObject(1);

        return default_value;
    }

    /**
     *
     * @param conn
     * @param field
     * @param table
     * @param where_cond
     * @param group_by
     * @param having
     * @param default_value
     * @return
     * @throws SQLException
     */

    public static Object lookup(Connection conn, String field, String table,
                                String where_cond, String group_by, String having, String default_value) throws SQLException
    {
        String cmdSQL = get_select_command(field, table, where_cond,group_by,having, field);
        ResultSet rs = exec_query(conn, cmdSQL);

        if (rs.next())
            return rs.getObject(1);

        return default_value;
    }

    /**
     * @author:
     * @param conn
     * @param sqlCmd
     * @return
     * @throws SQLException
     */
    public static ListIdDesc<Integer, String> get_list_id_desc(Connection conn, String sqlCmd) throws SQLException
    {
        ListIdDesc<Integer, String> list= new ListIdDesc();
        IdDesc<Integer, String> idDesc;

        ResultSet rs = exec_query(conn, sqlCmd);
        while (rs.next()){
            idDesc = new IdDesc(rs.getInt(1),rs.getString(2));
            list.add(idDesc);
        }
        return list;

    }


}
