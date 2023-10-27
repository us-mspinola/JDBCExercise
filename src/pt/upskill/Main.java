package pt.upskill;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;


public class Main {


    private static final String DB_SERVER = "localhost";
    private static final String DB_PORT = "3306";
    private static final String DB_NAME = "demo_jdbc";
    private static final String DB_USER = "big";
    private static final String DB_PWD = "brother";

    private static final String PATH_NAME="dados//";


    enum OpMainMenu {
        OP_ALUNOS('1'),OP_CURSOS('2'), OP_CORES('3'),
        OP_CRIAR_BD('7'), OP_POPULAR_CURSOS('8'), OP_POPULAR_CORES('9'),
        OP_SAIR('0');

        private char menu_option;
        private OpMainMenu(char value)
        {
            menu_option=value;
        }

        public char getOption() {
            return menu_option;
        }
    }

    private static String mainMenu[] = {
            "1 - Gerir Alunos",
            "2 - Gerir Cursos",
            "3 - Gerir Cores // (não implementar, é semelhante aos Cursos)",
            "7 - Criar BD e tabelas",
            "8 - Adicionar Cursos (a partir de um ficheiro de dados)",
            "9 - Adicionar Cores (a partir de um ficheiro de dados)",
            "",
            "0 - Sair"
    };


    public static void main(String[] args) {

        try {

        Connection connection = MyDBUtils.get_connection(MyDBUtils.db_type.DB_MYSQL,DB_SERVER,DB_PORT,DB_NAME,DB_USER,DB_PWD);

        // doMainMenu(connection);

          doTest(connection);

        connection.close();

        } catch (SQLException e) {
            System.out.println(e.toString());
        }

    }

    private static void doTest(Connection connection ) throws SQLException
    {
        MyDBUtils.get_select_command("now");
        System.out.println(MyDBUtils.exist(connection,"cor", "cor='Azul'"));


        int codCor= 10;
        System.out.println(MyDBUtils.lookup(connection, "cor", "cor", "id_cor=" + codCor, "NO COLOR FOUND"));
    }


   private static void doMainMenu(Connection connection )
   {

       OpMainMenu opMenu;

       while ( (opMenu = MyUtils.getMenuOption(mainMenu))!=OpMainMenu.OP_SAIR)
           if (opMenu!= null) {
               switch (opMenu) {
                   case OP_CRIAR_BD:
                       createDBTables(connection);
                       break;
                   case OP_ALUNOS:
                       System.out.println(opMenu);
                       break;
                   case OP_CURSOS:
                       System.out.println(opMenu);
                       break;
                   case OP_CORES:
                       System.out.println(opMenu);
                       break;
                   case OP_POPULAR_CORES:
                       populateIdDescTableFromFile(connection, "cor", PATH_NAME, "cores.txt");
                       break;
                   case OP_POPULAR_CURSOS:
                       populateIdDescTableFromFile(connection, "curso", PATH_NAME,"cursos.txt");

                       break;
                   case OP_SAIR:
                       System.out.println(opMenu);
               }
           }

   }





    private static void populateIdDescTableFromFile(Connection connection, String tableName, String pathName, String fileName )
    {
        String path= pathName + fileName;

        ListIdDesc <Integer, String> listIdDescs= new ListIdDesc();

        MyUtils.loadIdDescFromFile (listIdDescs,path);

        HashMap<Integer,String> list = listIdDescs.getKvMap();

        // Iterate over the HashMap
        for (Map.Entry<Integer, String> entry : list.entrySet()) {
            String sql = "insert into " + tableName + " values ("+ entry.getKey() + ",'" + entry.getValue() + "');";
            try {
                MyDBUtils.exec_sql(connection,sql);
            } catch (SQLException e) {
                System.out.println("exec_sql:" + sql + " Error: " + e.getMessage());
            }
        }
    }


    /**
     *
     * @param connection
     * @todo: take proper care of exception
     */
    private static void createDBTables(Connection connection)
    {

        String sql = "";
        try {

            sql= "create table curso(id_curso int primary key, curso varchar(50) not null unique)";
            MyDBUtils.exec_sql(connection, sql);


            sql= "create table cor(id_cor int primary key, cor varchar(50) not null unique)";
            MyDBUtils.exec_sql(connection, sql);

            StringBuilder sb = new StringBuilder();
            sb.append("create table aluno(nr_aluno int primary key auto_increment,");
            sb.append("nome varchar(50) not null,");
            sb.append("data_criacao date,");
            sb.append("id_cor int not null,");
            sb.append("id_curso int not null,");
            sb.append("foreign key(id_cor) references cor(id_cor),");
            sb.append("foreign key(id_curso) references curso(id_curso))");
            MyDBUtils.exec_sql(connection, sb.toString());
        }
        catch (SQLException e) {

            System.out.println("exec_sql:" + sql + " Error: " + e.getMessage());
        }
    }


}
