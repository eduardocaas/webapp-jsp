package servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.UserDAO;
import model.ModelLogin;

/**
 * Servlet implementation class ServletUsuario
 */
@WebServlet("/ServletUsuario")
public class ServletUsuario extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	private UserDAO userDAO = new UserDAO();
       
    public ServletUsuario() {
    	
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
		
		request.setCharacterEncoding("UTF-8");
		String id = request.getParameter("id");
		String nome = request.getParameter("nome");
		String login = request.getParameter("login");
		String email = request.getParameter("email");
		String senha = request.getParameter("senha");
		
		ModelLogin modelLogin = new ModelLogin();
		modelLogin.setId(id != null && !id.isEmpty() ? Long.parseLong(id) : null); // recebe uma string, se não for nulo ou vazio, converte pra long, senão, recebe nulo 
		modelLogin.setNome(nome);
		modelLogin.setEmail(email);
		modelLogin.setLogin(login);
		modelLogin.setSenha(senha);
		
		modelLogin = userDAO.userRegistration(modelLogin);
		
		request.setAttribute("msg", "Operação realizada com sucesso!");
		request.setAttribute("modelLogin", modelLogin); // manter os dados na tela ao enviar formulario, definido também value padrão no jsp usuario
		RequestDispatcher redirect = request.getRequestDispatcher("principal/usuario.jsp");
		redirect.forward(request, response);
			
		} catch (Exception e) {
			e.printStackTrace();
			RequestDispatcher redirect = request.getRequestDispatcher("/error.jsp");
			request.setAttribute("msg" , e.getMessage());
			redirect.forward(request, response);
		}
		
		
		
	}

}
