// import java.sql.Connection;
// import java.sql.DriverManager;
// import java.sql.PreparedStatement;
// import java.sql.ResultSet;
// import java.sql.SQLException;
// import java.sql.Statement;
// import java.util.ArrayList;
// import java.util.Optional;
// import java.util.UUID;
// import java.util.stream.Stream;

// public class RepositorioDePerfisMySQL implements IRepositorioDePerfis {
    
//     private Connection conexao;

//     public RepositorioDePerfisMySQL() {

//         try {
//             Class.forName("com.mysql.cj.jdbc.Driver");
//             String url = "jdbc:mysql://localhost:3306/rubi";
//             String usuario = "root";
//             String senha = "123456";
//             conexao = DriverManager.getConnection(url, usuario, senha);
//         } catch (ClassNotFoundException | SQLException e){
//             e.printStackTrace();
//         }
//     }

//     public void incluir(Perfil perfil) throws NullObjectAsArgumentException, UserAlreadyExistsException{
//         Optional.ofNullable(perfil).orElseThrow(NullObjectAsArgumentException::new);
       
//         try {
//             String insert_sql = "INSERT INTO perfil (id, username, nome, email, biografia) VALUES (?,?,?,?,?)";
//             PreparedStatement insert = conexao.prepareStatement(insert_sql);
//             insert.setString(1,perfil.getId().toString());
//             insert.setString(2,perfil.getUsername());
//             insert.setString(3,perfil.getNome());
//             insert.setString(4,perfil.getEmail());
//             insert.setString(5,perfil.getBiografia());
//             insert.executeUpdate();
//         } catch (SQLException e){
//             e.printStackTrace();
//         }
//     }

//     // boolean usuarioJaExite(UUID id, String username, String email){
//     //     try {
//     //         String select_sql = "SELECT *"
//     //     }
//     // }

//     ResultSet selectFromTabela(String nomeTabela) throws SQLException{
        
//         try{
//             ResultSet resultado = null; 
//             Statement statement = conexao.createStatement();

//             String querySelect = String.format("SELECT * FROM %s", nomeTabela);
//             resultado = statement.executeQuery(querySelect);
//             return resultado;
//         }catch(SQLException e){

//         }
//     }
 
//     // public Optional<Perfil> consultarPerfilPorTodosOsAtributos(UUID id, String username, String email);

//     // public Optional<Perfil> consultarPerfilPorId(UUID id);

//     // public Optional<Perfil> consultarPerfilPorUsername(String username);

//     // public Optional<Perfil> consultarPerfilPorEmail(String email);

//     // public ArrayList<Perfil> getPerfis();

//     // public void removerPerfil(String username);

//     // public void resgatarPerfis();

//     // public void salvarPerfis();

//     //  public String getCaminhoDoBancoDeDados(String entidade) throws BadChoiceOfEntityForDB;
// }