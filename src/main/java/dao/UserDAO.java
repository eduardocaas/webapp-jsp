package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import connection.SingleConnection;
import model.ModelLogin;

public class UserDAO {

	private Connection connection;
	
	public UserDAO() {
		connection = SingleConnection.getConnection();
	}
	
	public ModelLogin userRegistration(ModelLogin modelLogin) throws Exception {
		
		if(modelLogin.isNew()) { // Grava um objeto novo
		
		String sql = "INSERT INTO users(login, senha, nome, email) VALUES (?, ?, ?, ?);";
		PreparedStatement statement = connection.prepareStatement(sql);
		
		statement.setString(1, modelLogin.getLogin());
		statement.setString(2, modelLogin.getSenha());
		statement.setString(3, modelLogin.getNome());
		statement.setString(4, modelLogin.getEmail());
		
		statement.execute();
		connection.commit();
		
		} else { // Update
			
			String sql = "UPDATE users SET login=?, senha=?, nome=?, email=? WHERE id = " + modelLogin.getId() + ";";
			PreparedStatement statement = connection.prepareStatement(sql);
			
			statement.setString(1, modelLogin.getLogin());
			statement.setString(2, modelLogin.getSenha());
			statement.setString(3, modelLogin.getNome());
			statement.setString(4, modelLogin.getEmail());
			
			statement.executeUpdate();
			
			connection.commit();
			
		}
		
		return this.searchUser(modelLogin.getLogin()); // já consulta após gravar
			
	}
	
	public ModelLogin searchUser(String login) throws Exception { // retorna um ModelLogin
		
		ModelLogin modelLogin = new ModelLogin();
		
		String sql = "SELECT * FROM users WHERE UPPER(login) = UPPER('"+ login +"')";
		
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
	
	public boolean loginValidate(String login) throws Exception{
		
		String sql = "SELECT COUNT(1) > 0 AS ext FROM users WHERE UPPER(login) = UPPER('"+ login + "')"; // se count login for maior que 0, retorna TRUE	
		
		PreparedStatement statement = connection.prepareStatement(sql);
		ResultSet result = statement.executeQuery();
		
		result.next(); // entra nos resultados
		return result.getBoolean("ext");
		
	}
	
}
