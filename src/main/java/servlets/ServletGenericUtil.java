package servlets;

import java.io.Serializable;
import java.sql.Connection;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import connection.SingleConnection;
import dao.UserDAO;

public class ServletGenericUtil extends HttpServlet implements Serializable{ // classe de utilidades

	private static final long serialVersionUID = 1L;
	
	// private Connection connection; conexão não necessária pois pega do userDAO
	
	private UserDAO userDAO = new UserDAO();
	
	/*public ServletGenericUtil() { // estabelece conexão
		connection = SingleConnection.getConnection();
	} */
	
	
	
	public Long getUserLogado(HttpServletRequest request) throws Exception { // descobrir qual usuario está logado, para cadastro de novo usuario (passando parametro user_cadastro_id no banco)
		
		HttpSession session = request.getSession();
		
		String usuarioLogado = (String) session.getAttribute("usuario");
		
		return userDAO.searchUserLogged(usuarioLogado).getId(); // consultando pelo login e pegando o id
		
	}
	
}
