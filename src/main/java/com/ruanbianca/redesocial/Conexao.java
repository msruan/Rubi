package com.ruanbianca.redesocial;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {
    public static void main(String[] args) {
        Connection conexao = null;  
        //Statement statement = null;

        try {
            // Carregando o driver JDBC
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Configurando a URL do banco de dados
            String url = "jdbc:mysql://localhost:3306/rubi";
            String usuario = "root";
            String senha = "123456";

            // Estabelecendo a conexão
            conexao = DriverManager.getConnection(url, usuario, senha);
           
            // Faça o que precisa ser feito com a conexão aqui

            System.out.println("Conexão bem-sucedida!");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (conexao != null && !conexao.isClosed()) {
                    conexao.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}

// public class Conexao {

//     public static Connection getConnection(){
//         Connection conexao = null;

//         try {
//             String driver = "com.mysql.jdbc.Driver";
//             Class.forName(driver);

//             String url = "jdbc:mysql://localhost:3306/rubi";
//             String username = "root";
//             String password = "123456";

//             conexao = DriverManager.getConnection(url,username,password);

//             System.out.println("Conexõa bem-sucedida :D");

//             //faz os babados aqui
//             return conexao;

//         }catch(ClassNotFoundException e){
//             System.out.println("Driver do banco de dados não encontrado :P");
//             return null;

//         }catch(SQLException e){
//             System.out.println("Erro ao acessar o banco: " + e.getMessage());
//             return null;

//         }finally { 
//             try {
//                 if(conexao != null && !conexao.isClosed()){
//                     conexao.close();
//                 }
//             } catch(SQLException e){
//                 System.out.println("Erro ao fechar a conexão...");
//             }
//         }
        
//     }

//     public static void main(String[] args) {
//         getConnection();
//     }
// }