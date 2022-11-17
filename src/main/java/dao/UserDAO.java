package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import connection.SingleConnection;
import model.ModelLogin;

public class UserDAO {

	private Connection connection;
	
	public UserDAO() {
		connection = SingleConnection.getConnection();
	}
	
	// userLogado -> consultas trazem apenas usuários cadastrados pelo usuário logado, inserções levam o id do atual usuário logado, (user_cadastro_id)
	
	public ModelLogin userRegistration(ModelLogin modelLogin, Long userLogado) throws Exception {
		
		if(modelLogin.isNew()) { // Grava um objeto novo
		
		String sql = "INSERT INTO users(login, senha, nome, email, user_cadastro_id, perfil) VALUES (?, ?, ?, ?, ?, ?);";
		PreparedStatement statement = connection.prepareStatement(sql);
		
		statement.setString(1, modelLogin.getLogin());
		statement.setString(2, modelLogin.getSenha());
		statement.setString(3, modelLogin.getNome());
		statement.setString(4, modelLogin.getEmail());
		statement.setLong(5, userLogado);
		statement.setString(6, modelLogin.getPerfil());
		
		statement.execute();
		connection.commit();
		
		} else { // Update
			
			String sql = "UPDATE users SET login=?, senha=?, nome=?, email=?, perfil=? WHERE id = " + modelLogin.getId() + ";";
			PreparedStatement statement = connection.prepareStatement(sql);
			
			statement.setString(1, modelLogin.getLogin());
			statement.setString(2, modelLogin.getSenha());
			statement.setString(3, modelLogin.getNome());
			statement.setString(4, modelLogin.getEmail());
			statement.setString(5, modelLogin.getPerfil());
			
			statement.executeUpdate();			
			connection.commit();
			
		}
		
		return this.searchUser(modelLogin.getLogin(), userLogado); // já consulta após gravar
			
	}
	
	public List<ModelLogin> returnUserList(Long userLogado) throws Exception { // listar usuário, ao acessar pag jsp
			
			List<ModelLogin> modelLogins = new ArrayList<ModelLogin>();
			
			String sql = "SELECT * FROM users WHERE useradmin = FALSE AND user_cadastro_id = " + userLogado + " ORDER BY id";
			PreparedStatement statement = connection.prepareStatement(sql);
			ResultSet result = statement.executeQuery();
			
			while (result.next()) {
				
				ModelLogin modelLogin = new ModelLogin();
				modelLogin.setId(result.getLong("id"));
				modelLogin.setLogin(result.getString("login"));
				// modelLogin.setSenha(result.getString("senha"));
				modelLogin.setNome(result.getString("nome"));
				modelLogin.setEmail(result.getString("email"));
				
				modelLogins.add(modelLogin);
				
			}
			
			return modelLogins;
			
		}
	
	public List<ModelLogin> searchUserList(String name, Long userLogado) throws Exception { // modal
		
		List<ModelLogin> modelLogins = new ArrayList<ModelLogin>();
		
		String sql = "SELECT * FROM users WHERE UPPER(nome) LIKE UPPER(?) AND useradmin = FALSE AND user_cadastro_id = ? ORDER BY nome";
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setString(1, "%" + name + "%");
		statement.setLong(2, userLogado);
		ResultSet result = statement.executeQuery();
		
		while (result.next()) {
			
			ModelLogin modelLogin = new ModelLogin();
			modelLogin.setId(result.getLong("id"));
			modelLogin.setLogin(result.getString("login"));
			// modelLogin.setSenha(result.getString("senha"));
			modelLogin.setNome(result.getString("nome"));
			modelLogin.setEmail(result.getString("email"));
			
			modelLogins.add(modelLogin);
			
		}
		
		return modelLogins;
		
	}
	
	public ModelLogin searchUser(String login, Long userLogado) throws Exception { // retorna um ModelLogin
		
		ModelLogin modelLogin = new ModelLogin();
		
		String sql = "SELECT * FROM users WHERE UPPER(login) = UPPER('"+ login +"') AND useradmin = FALSE AND user_cadastro_id = " + userLogado;
		
		PreparedStatement statement = connection.prepareStatement(sql);
		ResultSet result = statement.executeQuery();
		
		while (result.next()) {
			modelLogin.setId(result.getLong("id"));
			modelLogin.setLogin(result.getString("login"));
			modelLogin.setSenha(result.getString("senha"));
			modelLogin.setNome(result.getString("nome"));
			modelLogin.setEmail(result.getString("email"));
		}
		
		return modelLogin;
		
	}
	
	public ModelLogin searchUser(String login) throws Exception { // retorna um ModelLogin, consultado somente por login para verificar usuario logado
			
			ModelLogin modelLogin = new ModelLogin();
			
			String sql = "SELECT * FROM users WHERE UPPER(login) = UPPER('"+ login +"') AND useradmin = FALSE";
			
			PreparedStatement statement = connection.prepareStatement(sql);
			ResultSet result = statement.executeQuery();
			
			while (result.next()) {
				modelLogin.setId(result.getLong("id"));
				modelLogin.setLogin(result.getString("login"));
				modelLogin.setSenha(result.getString("senha"));
				modelLogin.setNome(result.getString("nome"));
				modelLogin.setEmail(result.getString("email"));
			}
			
			return modelLogin;
			
		}
	
	public ModelLogin searchUserLogged(String login) throws Exception { // retorna um ModelLogin, consultado somente por login para verificar usuario logado, 
		
		ModelLogin modelLogin = new ModelLogin();
		
		String sql = "SELECT * FROM users WHERE UPPER(login) = UPPER('"+ login +"')";
		
		PreparedStatement statement = connection.prepareStatement(sql);
		ResultSet result = statement.executeQuery();
		
		while (result.next()) { // cada result é uma coluna
			modelLogin.setId(result.getLong("id"));
			modelLogin.setLogin(result.getString("login"));
			modelLogin.setSenha(result.getString("senha"));
			modelLogin.setNome(result.getString("nome"));
			modelLogin.setEmail(result.getString("email"));
			modelLogin.setUseradmin(result.getBoolean("useradmin"));
			modelLogin.setPerfil(result.getString("perfil"));
		}
		
		return modelLogin;
		
	}
	
	public ModelLogin searchUserID(String id, Long userLogado) throws Exception { // retorna um ModelLogin
			
			ModelLogin modelLogin = new ModelLogin();
			
			String sql = "SELECT * FROM users WHERE id = ? AND useradmin = FALSE and user_cadastro_id = ?";
			
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setLong(1, Long.parseLong(id));
			statement.setLong(2, userLogado);
			ResultSet result = statement.executeQuery();
			
			while (result.next()) {
				modelLogin.setId(result.getLong("id"));
				modelLogin.setLogin(result.getString("login"));
				modelLogin.setSenha(result.getString("senha"));
				modelLogin.setNome(result.getString("nome"));
				modelLogin.setEmail(result.getString("email"));
			}
			
			return modelLogin;
			
		}
	
	public boolean loginValidate(String login) throws Exception{
		
		String sql = "SELECT COUNT(1) > 0 AS ext FROM users WHERE UPPER(login) = UPPER('"+ login + "')"; // se count login for maior que 0, retorna TRUE	
		
		PreparedStatement statement = connection.prepareStatement(sql);
		ResultSet result = statement.executeQuery();
		
		result.next(); // entra nos resultados
		return result.getBoolean("ext");
		
	}
	
	public void deleteUser(String idUser) throws Exception {
		
		String sql = "DELETE FROM users WHERE id = ? AND useradmin = FALSE;";
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setLong(1, Long.parseLong(idUser));
		statement.executeUpdate();
		connection.commit();
		
	}
	
}
