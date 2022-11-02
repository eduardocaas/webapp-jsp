package servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.ModelLogin;

/**
 * Servlet implementation class ServletUsuario
 */
@WebServlet("/ServletUsuario")
public class ServletUsuario extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
       
    public ServletUsuario() {
    	
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String id = request.getParameter("id");
		String nome = request.getParameter("nome");
		String login = request.getParameter("login");
		String email = request.getParameter("email");
		String senha = request.getParameter("senha");
		
		ModelLogin modelLogin = new ModelLogin();
		modelLogin.setId(id != null && !id.isEmpty() ? Long.parseLong(id) : null); // recebe uma string, se não for nulo ou vazio, converte pra long, senão, recebe nulo 
		modelLogin.setNome(nome);
		modelLogin.setEmail(email);
		modelLogin.setSenha(senha);
		
		RequestDispatcher redirect = request.getRequestDispatcher("principal/usuario.jsp");
		redirect.forward(request, response);
		
	}

}
