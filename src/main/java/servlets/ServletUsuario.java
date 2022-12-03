package servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.apache.commons.io.IOUtils;
import org.apache.tomcat.util.codec.binary.Base64;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;

import com.fasterxml.jackson.databind.ObjectMapper;

import dao.UserDAO;
import model.ModelLogin;

@MultipartConfig // para arquivos ex: foto de usuario
@WebServlet(urlPatterns = {"/ServletUsuario"}) /* Mapeamento de URL que vem da tela */
public class ServletUsuario extends /*HttpServlet*/ ServletGenericUtil { // httpservlet herdado de servletgenericutil
	
	private static final long serialVersionUID = 1L;
	
	private UserDAO userDAO = new UserDAO();
       
    public ServletUsuario() {
    	
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException { // get - deletar e consultar (boa prática)
		
		try {
		
			String act = request.getParameter("act"); // input hidden - usuario.jsp,   na url onde estiver act, recebe os parametros atribuidos, ex: act=ListUser
			
			if (act != null && !act.isEmpty() && act.equalsIgnoreCase("delete")) { // delete padrão
				
				String idUser = request.getParameter("id");
				userDAO.deleteUser(idUser);
				request.setAttribute("msg", "Excluído com sucesso!");
				
				List<ModelLogin> modelLoginList = userDAO.returnUserList(super.getUserLogado(request)); // recarregar tabela novamente
				request.setAttribute("modelLoginList", modelLoginList); 
				request.setAttribute("totalPages", userDAO.totalPage(this.getUserLogado(request))); // seta atributo de total de páginas antes de redirecionamento
				request.getRequestDispatcher("principal/usuario.jsp").forward(request, response);
				
				
				
			} 
			
			else if (act != null && !act.isEmpty() && act.equalsIgnoreCase("deleteAjax")) { // caso for usado metódo ajax para deletar
				
				String idUser = request.getParameter("id");
				userDAO.deleteUser(idUser);
				response.getWriter().write("Excluido com sucesso!"); // resposta com ajax, cai na funcão sucess 
				
			}
			
			else if (act != null && !act.isEmpty() && act.equalsIgnoreCase("searchUserAjax")) { // metódo de busca ajax (modal)
				
				String nameUser = request.getParameter("nameModal");
				
				List<ModelLogin> userDataJSON = userDAO.searchUserList(nameUser, super.getUserLogado(request));
				
				ObjectMapper mapper = new ObjectMapper();
				String json = mapper.writeValueAsString(userDataJSON);
				
				response.getWriter().write(json); // resposta com ajax, cai na funcão sucess 
				
			}
			
			else if (act != null && !act.isEmpty() && act.equalsIgnoreCase("viewModalUser")) {
				String id = request.getParameter("id");
				
				ModelLogin modelLogin = userDAO.searchUserID(id, super.getUserLogado(request));
				
				List<ModelLogin> modelLoginList = userDAO.returnUserList(super.getUserLogado(request)); // recarregar tabela novamente
				request.setAttribute("modelLoginList", modelLoginList); 
				
				request.setAttribute("modelLogin", modelLogin);
				request.setAttribute("totalPages", userDAO.totalPage(this.getUserLogado(request))); //
				request.getRequestDispatcher("principal/usuario.jsp").forward(request, response);
				
				
								
			}
			
			else if (act != null && !act.isEmpty() && act.equalsIgnoreCase("listUser")) { // mostra lista de usuários ao entrar no usuario.jsp
				
				List<ModelLogin> modelLoginList = userDAO.returnUserList(super.getUserLogado(request));
				
				request.setAttribute("msg", "Usuários carregados");
				request.setAttribute("modelLoginList", modelLoginList);  // parametro a ser usado dentro do jsp, apenas modelLogin é do form
				request.setAttribute("totalPages", userDAO.totalPage(this.getUserLogado(request))); 
				request.getRequestDispatcher("principal/usuario.jsp").forward(request, response); // carrega os usuários e envia para tela
				
			}
			
			else if (act != null && !act.isEmpty() && act.equalsIgnoreCase("downloadUserImage")) { // download da imagem do usuario na tela
				
				String idUser = request.getParameter("id");
				ModelLogin modelLogin = userDAO.searchUserID(idUser, super.getUserLogado(request));
				
				if (modelLogin.getFotouser() != null && !modelLogin.getFotouser().isEmpty()) {
					
					response.setHeader("Content-Disposition", "attachment;filename=arquivo." + modelLogin.getFotouser_extensao()); // resposta, content-disposition -> navegador identificar download
					response.getOutputStream().write(new Base64().decodeBase64(modelLogin.getFotouser().split("\\,")[1])); // bytes da foto, decodifica, split -> pega apenas após 'base64,'
					
				}
				
				
			}
			
			else if (act != null && !act.isEmpty() && act.equalsIgnoreCase("pagination")) { 
				
				Integer offset = Integer.parseInt(request.getParameter("page")); // parametro definido na url no jsp, offset -> qntd de páginas vezes 5
				
				List<ModelLogin> modelLogins = userDAO.returnUserListPAGINATED(this.getUserLogado(request), offset);
				
				request.setAttribute("modelLoginList", modelLogins); 
				request.setAttribute("totalPages", userDAO.totalPage(this.getUserLogado(request))); 
				request.getRequestDispatcher("principal/usuario.jsp").forward(request, response);
				
			}
			
			else {
				
				List<ModelLogin> modelLoginList = userDAO.returnUserList(super.getUserLogado(request)); // recarregar tabela novamente
				request.setAttribute("modelLoginList", modelLoginList); 
				request.setAttribute("totalPages", userDAO.totalPage(this.getUserLogado(request))); 
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
		
		String id = request.getParameter("id"); // name no html - input
		String nome = request.getParameter("nome");
		String login = request.getParameter("login");
		String email = request.getParameter("email");
		String senha = request.getParameter("senha");
		String perfil = request.getParameter("perfil");
		String sexo = request.getParameter("sexo");
		String cep = request.getParameter("cep");
		String logradouro = request.getParameter("logradouro");
		String bairro = request.getParameter("bairro");
		String localidade = request.getParameter("localidade");
		String uf = request.getParameter("uf");
		String numero = request.getParameter("numero");
		
		ModelLogin modelLogin = new ModelLogin();
		
		modelLogin.setId(id != null && !id.isEmpty() ? Long.parseLong(id) : null); // recebe uma string, se não for nulo ou vazio, converte pra long, senão, recebe nulo 
		modelLogin.setNome(nome);
		modelLogin.setEmail(email);
		modelLogin.setLogin(login);
		modelLogin.setSenha(senha);
		modelLogin.setPerfil(perfil);
		modelLogin.setSexo(sexo);
		modelLogin.setCep(cep);
		modelLogin.setLogradouro(logradouro);
		modelLogin.setBairro(bairro);
		modelLogin.setLocalidade(localidade);
		modelLogin.setUf(uf);
		modelLogin.setNumero(numero);
		
		if (ServletFileUpload.isMultipartContent(request)) { // intercepta arquivos
			
			
			
			Part part = request.getPart("imgFile"); // pega foto da tela, pelo name
			
			if(part.getSize() > 0) { // evitar que ao não informar nenhum arquivo, ocorra a concatenação e o envio incompleto de file64 -> extensão
			
				byte[] imgFileByte = IOUtils.toByteArray(part.getInputStream()); // converte imagem convertida para byte, IOUtils Apache Commons
				String imgFileBase64 = "data:image/" + part.getContentType().split("\\/")[1] + ";base64," + new Base64().encodeBase64String(imgFileByte); // converte imagem para base64 recebendo um byte , padrão para html ler imagem, Apache Codec Base 64
				// exemplo de arquivo gerado: data:image/png;base64,ivBORw0RKgoAAAAnUhEg
				
				modelLogin.setFotouser(imgFileBase64); // foto base64
				modelLogin.setFotouser_extensao(part.getContentType().split("\\/")[1]); // extensão da foto, split pegar apenas extensao da imagem, identificando pela barra
			}
		}
		
		if (userDAO.loginValidate(modelLogin.getLogin()) && modelLogin.getId() == null) { // se já existe o login, e estou tentando gravar um novo usuário (não conflitar com update)
			
			msg = "Login já existente, informe outro login!";
			
		} else {
			
			if (modelLogin.isNew()) {
				msg = "Gravado com sucesso!";
			} else {
				msg = "Atualizado com sucesso!";
			}
			
			modelLogin = userDAO.userRegistration(modelLogin, super.getUserLogado(request));
		}
		
		
		
		List<ModelLogin> modelLoginList = userDAO.returnUserList(super.getUserLogado(request)); // recarregar tabela novamente
		request.setAttribute("modelLoginList", modelLoginList);
		
		request.setAttribute("msg", msg);
		request.setAttribute("modelLogin", modelLogin); // manter os dados na tela ao enviar formulario, definido também value padrão no jsp usuario
		request.setAttribute("totalPages", userDAO.totalPage(this.getUserLogado(request)));
		request.getRequestDispatcher("principal/usuario.jsp").forward(request, response);
			
		} catch (Exception e) {
			e.printStackTrace();
			RequestDispatcher redirect = request.getRequestDispatcher("/error.jsp");
			request.setAttribute("msg" , e.getMessage());
			redirect.forward(request, response);
		}
		
		
		
	}

}
