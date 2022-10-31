package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import connection.SingleConnection;
import model.ModelLogin;

public class LoginDAO {

	private Connection connection;
	
	public LoginDAO() {
		connection = SingleConnection.getConnection();
	}
	
	public boolean validateAuth(ModelLogin modelLogin) throws Exception{
		
		String sql = "SELECT * FROM users WHERE login = ? AND senha = ?";
		
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setString(1, modelLogin.getLogin());
		statement.setString(2, modelLogin.getSenha());
		
		ResultSet result = statement.executeQuery();
		
		if (result.next()) { // se existir
			return true; // autenticado
		}
		
		return false; // não autenticado
		
	}
	
}
