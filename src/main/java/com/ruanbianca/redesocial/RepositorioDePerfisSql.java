package com.ruanbianca.redesocial;
//oi, 
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Optional;
import java.sql.Connection;
import java.util.UUID;


public class RepositorioDePerfisSql implements IRepositorioDePerfis {

    private Connection conexao;

    public RepositorioDePerfisSql() throws SQLException{

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/rubi";
            String usuario = "root";
            String senha = "123456";
            conexao = DriverManager.getConnection(url, usuario, senha);

        } catch (ClassNotFoundException | SQLException e) {
            throw new SQLException("Erro durante instanciação do Repositório de SQL!\nPor favor tente novamente com outro tipo de persistência.");
        }
    }


    public ArrayList<Perfil> getPerfis() throws SQLException{

        ResultSet resultado = selectFromTabela("Perfil");

        ArrayList<Perfil> perfis = new ArrayList<>();
        Perfil perfil;

        while (resultado.next()) {

            perfil = new Perfil(UUID.fromString(
                    resultado.getString("id")),
                    resultado.getString("username"),
                    resultado.getString("nome"),
                    resultado.getString("email"),
                    resultado.getString("biografia"));
            perfis.add(perfil);
        }
        return perfis;
    }

    
    public Optional<Perfil> consultar(UUID id, String username, String email) throws SQLException{

        try{
            ResultSet resultado = selectFromTabela("Perfil");

            while (resultado.next()) {

                if ((Optional.ofNullable(id).isPresent() &&
                        id.equals(UUID.fromString(resultado.getString("id")))) ||
                        (Optional.ofNullable(username).isPresent() &&
                                username.equals(resultado.getString("username")))
                        ||
                        (Optional.ofNullable(email).isPresent() &&
                                email.equals(resultado.getString("email")))) {
                    Perfil perfil = new Perfil(UUID.fromString(resultado.getString("id")),
                            resultado.getString("username"),
                            resultado.getString("nome"),
                            resultado.getString("email"),
                            resultado.getString("biografia"));
                    return Optional.ofNullable(perfil);
                }
            }
            return Optional.empty();

        } catch (SQLException e) {
            throw new SQLException(
                    "SQL não está funcionando no momento, por favor tente novamente com outro tipo de persistência...");
        }
    }


    public void incluir(Perfil perfil) throws NullObjectAsArgumentException, UserAlreadyExistsException, SQLException {

        Optional.ofNullable(perfil).orElseThrow(NullObjectAsArgumentException::new);
        
        if (usuarioJaExite(perfil.getId(), perfil.getUsername(), perfil.getEmail())) 
            throw new UserAlreadyExistsException();
        
        try{

            String insert_sql = "INSERT INTO Perfil(id, username, nome, email, biografia) VALUES (?,?,?,?,?)";
            PreparedStatement insert = conexao.prepareStatement(insert_sql);
            insert.setString(1, perfil.getId().toString());
            insert.setString(2, perfil.getUsername());
            insert.setString(3, perfil.getNome());
            insert.setString(4, perfil.getEmail());
            insert.setString(5, perfil.getBiografia());
            insert.executeUpdate();

      } catch (SQLException e) {
            throw new SQLException(
                    "SQL não está funcionando no momento, por favor tente novamente com outro tipo de persistência...");
        }
    }


    ResultSet selectFromTabela(String nomeTabela) throws SQLException {
        
        try{
            ResultSet resultado = null;
            Statement statement = conexao.createStatement();

            String querySelect = String.format("SELECT * FROM %s", nomeTabela);
            resultado = statement.executeQuery(querySelect);
            return resultado;

        } catch (SQLException e) {
            throw new SQLException(
                    "SQL não está funcionando no momento, por favor tente novamente com outro tipo de persistência...");
        }
    }


    public void removerPerfil(String username) throws UserNotFoundException, SQLException{

        String delete_sql = "DELETE FROM Perfil WHERE username = ?";
        
        try{
            PreparedStatement delete = conexao.prepareStatement(delete_sql);
            delete.setString(1, username);
            int afetado = delete.executeUpdate();
            if (afetado == 0) 
                throw new UserNotFoundException();
            
        } catch (SQLException e) {
            throw new SQLException(
                    "SQL não está funcionando no momento, por favor tente novamente com outro tipo de persistência...");
        }
    }


    public boolean usuarioJaExite(UUID id, String username, String email) throws SQLException{

        return consultar(id, username, email).isPresent();
    }
    

    //Fazer validação do lado de fora?
    public void atualizarPerfil(String username, String novoAtributo, String nomeAtributo) throws UserNotFoundException, SQLException{
        
        try{
        if(!usuarioJaExite(null, username, nomeAtributo))
            throw new UserNotFoundException();

        String update_sql = String.format("UPDATE Perfil SET %s = ? WHERE username = ?",nomeAtributo);

        PreparedStatement update = conexao.prepareStatement(update_sql);
        update.setString(1, novoAtributo);
        update.setString(2,username);
        
        int afetado = update.executeUpdate();

        if (afetado == 0) 
            throw new UserNotFoundException();
            
        } catch (SQLException e) {
            throw new SQLException(
                    "SQL não está funcionando no momento, por favor tente novamente com outro tipo de persistência...");
        }
    }
}