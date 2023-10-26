package pt.upskill;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class Main {


    private static final String DB_SERVER = "localhost";
    private static final String DB_PORT = "3306";
    private static final String DB_NAME = "demo_jdbc";
    private static final String DB_USER = "big";
    private static final String DB_PWD = "brother";


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

        Connection connection = MyDBUtils.get_connection(MyDBUtils.db_type.DB_MYSQL,DB_SERVER,DB_PORT,DB_NAME,DB_USER,DB_PWD);
        OpMainMenu opMenu;
        while ( (opMenu = MyUtils.getMenuOption(mainMenu))!=OpMainMenu.OP_SAIR)
            if (opMenu!= null) {
                switch (opMenu) {
                    case OP_CRIAR_BD:
                        criarTabelas(connection);
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
                        popularIdDesc("cores.txt");
                        break;
                    case OP_POPULAR_CURSOS:
                        popularIdDesc("cursos.txt");

                        break;
                    case OP_SAIR:
                        System.out.println(opMenu);
                }
            }

    }


    private static void popularIdDesc(String fileName)
    {
        String path= "dados//"+fileName;

        ListIdDesc <Integer, String> listIdDescs= new ListIdDesc();

        IdDesc.loadIdDescFromFile (listIdDescs,path);

        HashMap<Integer,String> list = listIdDescs.getKvMap();

        // Iterate over the HashMap
        for (Map.Entry<Integer, String> entry : list.entrySet()) {

            System.out.println("Key: " + entry.getKey() + ", Value: " + entry.getValue());
        }





    }


    private static void popularTabela(String fileName)
    {
        String path= "dados//"+fileName;

        ArrayList<IdDesc> listIdDescs = new ArrayList<>();

        IdDesc.loadIdDesc (listIdDescs,path);

        for (IdDesc idDesc : listIdDescs)
            System.out.println(idDesc);


    }

    private static void criarTabelas(Connection connection){

        MyDBUtils.exec_sql(connection,"create table curso(id_curso int primary key, " +
                "curso varchar(50) not null unique)");

        MyDBUtils.exec_sql(connection,"create table cor(id_cor int primary key, " +
                "cor varchar(50) not null unique)");

        StringBuilder sb = new StringBuilder();
        sb.append("create table aluno(nr_aluno int primary key auto_increment,");
        sb.append("nome varchar(50) not null,");
        sb.append("data_criacao date,");
        sb.append("id_cor int not null,");
        sb.append("id_curso int not null,");
        sb.append("foreign key(id_cor) references cor(id_cor),");
        sb.append("foreign key(id_curso) references curso(id_curso))");
        MyDBUtils.exec_sql(connection,sb.toString());

    }
    private static void testDB() {
        String connString = "jdbc:mysql://localhost:3306/demo_jdbc";
        String username = "big";
        String password = "brother";

        try (Connection conn = DriverManager.getConnection(connString, username, password);
             Statement statement = conn.createStatement();) {

            String cmdSQL = "SELECT * FROM pessoa ORDER BY nome";
            ResultSet rs = statement.executeQuery(cmdSQL);

            while (rs.next()) {
                System.out.printf("%-10s \t %2d \t %c\n", rs.getString("nome"),
                        rs.getInt("idade"),
                        rs.getString("cod_dep").charAt(0));
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
    }

}
