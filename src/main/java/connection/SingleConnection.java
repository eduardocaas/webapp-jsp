package connection;

import java.sql.Connection;
import java.sql.DriverManager;

public class SingleConnection {
	
	private static String url = "jdbc:postgresql://localhost:5432/mydb-jsp?autoReconnect=true"; // reconecta automaticamente se cair
	private static String user = "postgres";
	private static String password = "admin";
	private static Connection connection = null;
	
	
	public static Connection getConnection() { // verificar conexão
		return connection;
	}
	
	
	static { // se a classe for chamada diretamente, será executado esse método
		connect();
	}
	
	public SingleConnection() { // sempre que singleconnection for instanciado, será executado esse método
		connect();
	}
	
	private static void connect() {
		
		try {
			if(connection == null) { // conexão só é feita uma vez
				
				Class.forName("org.postgresql.Driver"); // classe de conexão do postgresql
				connection = DriverManager.getConnection(url, user, password); 
				connection.setAutoCommit(false); // não salvar as ações no banco automaticamente sem nosso comando
			
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
