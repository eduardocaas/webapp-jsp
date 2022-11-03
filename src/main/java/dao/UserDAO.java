package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import connection.SingleConnection;
import model.ModelLogin;

public class UserDAO {

	private Connection connection;
	
	public UserDAO() {
		connection = SingleConnection.getConnection();
	}
	
	public ModelLogin userRegistration(ModelLogin modelLogin) throws Exception {
		
		String sql = "INSERT INTO users(login, senha, nome, email) VALUES (?, ?, ?, ?);";
		PreparedStatement statement = connection.prepareStatement(sql);
		
		statement.setString(1, modelLogin.getLogin());
		statement.setString(2, modelLogin.getSenha());
		statement.setString(3, modelLogin.getNome());
		statement.setString(4, modelLogin.getEmail());
		
		statement.execute();
		connection.commit();
		
		return this.searchUser(modelLogin.getLogin()); // já consulta após gravar
		
			
	}
	
	public ModelLogin searchUser(String login) throws Exception { // retorna um ModelLogin
		
		ModelLogin modelLogin = new ModelLogin();
		
		String sql = "SELECT * FROM users WHERE upper(login) = upper('"+ login +"')";
		
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
	
}
