package servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.LoginDAO;
import dao.UserDAO;
import model.ModelLogin;

/* O chamado controller são as servlets */
@WebServlet(urlPatterns = {"/principal/ServletLogin" , "/ServletLogin"}) /* Mapeamento de URL que vem da tela */
public class ServletLogin extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	private LoginDAO loginDAO = new LoginDAO();
	private UserDAO userDAO = new UserDAO();
       
    public ServletLogin() {
       
    }

    /* Recebe os dados da URL em paramêtros*/
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException { // get envia pela url
		
		String action = request.getParameter("action"); // método na url, definido no jsp
		if(action != null && !action.isEmpty() && action.equalsIgnoreCase("logout")) {
			
			request.getSession().invalidate(); // invalida a sessão (logout)
			
			RequestDispatcher redirect = request.getRequestDispatcher("index.jsp");
			redirect.forward(request, response);
			
		} else { // se não for logout, continua o fluxo
			
			doPost(request, response); // tentar acessar por url
			
		}
		
		
	}
	
	/* Recebe os dados enviados por um formulário  | tudo que vem da tela request | para enviar a resposta request */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
		String login = request.getParameter("login");
		String senha = request.getParameter("senha");
		String url = request.getParameter("url");
		
		try {
		
			if(login != null && !login.isEmpty() && senha != null && !senha.isEmpty()) {
				
				ModelLogin modelLogin = new ModelLogin();
				modelLogin.setLogin(login);
				modelLogin.setSenha(senha);
				
				if(loginDAO.validateAuth(modelLogin)){ // simulando login
					
					modelLogin = userDAO.searchUserLogged(login);
					
					request.getSession().setAttribute("usuario", modelLogin.getLogin()); // usuario logado na sessão (só salva o login)
					request.getSession().setAttribute("usuarioImg", modelLogin.getFotouser()); // foto do usuario, ex: para mostrar no navbar
					request.getSession().setAttribute("isAdmin", modelLogin.getUseradmin()); // para controle de acesso de admins (ex: jsp)
					request.getSession().setAttribute("perfil", modelLogin.getPerfil());
					
					if (url == null || url.equals("null")) {
						url = "principal/principal.jsp";
					}
					
					RequestDispatcher redirect = request.getRequestDispatcher(url); 
					redirect.forward(request, response);
					
				}
				else {
					RequestDispatcher redirect = request.getRequestDispatcher("/index.jsp"); // retorna a tela index, caso caia no else
					request.setAttribute("msg" , "Login ou senha incorretos!");
					redirect.forward(request, response);
				}
				
			}
			else {
				RequestDispatcher redirect = request.getRequestDispatcher("index.jsp"); // retorna a tela index, caso caia no else
				request.setAttribute("msg" , "Informe o login e senha corretamente!");
				redirect.forward(request, response);
			}
		
		} catch (Exception e) {
			e.printStackTrace();
			RequestDispatcher redirect = request.getRequestDispatcher("/error.jsp");
			request.setAttribute("msg" , e.getMessage());
			redirect.forward(request, response);
		}
		
	}

}
