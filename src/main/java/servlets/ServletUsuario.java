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


@WebServlet("/ServletUsuario")
public class ServletUsuario extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	private UserDAO userDAO = new UserDAO();
       
    public ServletUsuario() {
    	
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException { // get - deletar e consultar (boa prática)
		
		try {
		
			String act = request.getParameter("act"); // input hidden - usuario.jsp
			
			if (act != null && !act.isEmpty() && act.equalsIgnoreCase("delete")) { // delete padrão
				
				String idUser = request.getParameter("id");
				userDAO.deleteUser(idUser);
				request.setAttribute("msg", "Excluído com sucesso!");
				
				request.getRequestDispatcher("principal/usuario.jsp").forward(request, response);
				
			} 
			
			else if (act != null && !act.isEmpty() && act.equalsIgnoreCase("deleteAjax")) { // caso for usado metódo ajax para deletar
				
				String idUser = request.getParameter("id");
				userDAO.deleteUser(idUser);
				response.getWriter().write("Excluido com sucesso!"); // resposta com ajax, cai na funcão sucess 
				
			}
			
			else if (act != null && !act.isEmpty() && act.equalsIgnoreCase("searchUserAjax")) { // metódo de busca ajax (modal)
				
				String nameUser = request.getParameter("nameModal");
				
				// response.getWriter().write("Excluido com sucesso!"); // resposta com ajax, cai na funcão sucess 
				
			}
			
			else {
				
				request.getRequestDispatcher("principal/usuario.jsp").forward(request, response);
				
			}
			
			
			
			} catch (Exception e) {
				e.printStackTrace();
				RequestDispatcher redirect = request.getRequestDispatcher("/error.jsp");
				request.setAttribute("msg" , e.getMessage());
				redirect.forward(request, response);
				
			}
		
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException { // post - gravar e atualizar (boa prática)
		
		try {
			
		String msg = "Operação realizada com sucesso!";
		
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
		
		if (userDAO.loginValidate(modelLogin.getLogin()) && modelLogin.getId() == null) { // se já existe o login, e estou tentando gravar um novo usuário (não conflitar com update)
			
			msg = "Login já existente, informe outro login!";
			
		} else {
			
			if (modelLogin.isNew()) {
				msg = "Gravado com sucesso!";
			} else {
				msg = "Atualizado com sucesso!";
			}
			
			modelLogin = userDAO.userRegistration(modelLogin);
		}
		
		
		
		request.setAttribute("msg", msg);
		request.setAttribute("modelLogin", modelLogin); // manter os dados na tela ao enviar formulario, definido também value padrão no jsp usuario
		request.getRequestDispatcher("principal/usuario.jsp").forward(request, response);
			
		} catch (Exception e) {
			e.printStackTrace();
			RequestDispatcher redirect = request.getRequestDispatcher("/error.jsp");
			request.setAttribute("msg" , e.getMessage());
			redirect.forward(request, response);
		}
		
		
		
	}

}
