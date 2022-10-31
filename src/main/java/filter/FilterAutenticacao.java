package filter;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import connection.SingleConnection;

//intercepta todas as requisições que vierem do projeto ou mapeamento

@WebFilter(urlPatterns = {"/principal/*"}) // tudo que vier do principal será interceptado para fazer validaçao
public class FilterAutenticacao extends HttpFilter implements Filter {
       

	private static Connection connection;
	
	public FilterAutenticacao() {
       
    }

	public void destroy() { // encerra os processos quando o servidor é parado, ex: mataria os processos de conexao com o db 
		try {
			connection.close(); // fecha a conexão
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	
	// intercepta as requisições e as respostas no sistema, tudo que fizer no sistema passa aqui, ex: validação de autenticação, 
							// dar commmit e rollback de transações no banco, validar e redirecionar páginas
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		
		try {
			HttpServletRequest req = (HttpServletRequest) request;
			HttpSession session = req.getSession();
			
			String usuarioLogado = (String) session.getAttribute("usuario");
			
			String urlAuth = req.getServletPath(); // url que está sendo acessada
			
			
			// Validar se está logado, se não, redireciona a tela de login
			if(usuarioLogado == null && !urlAuth.equalsIgnoreCase("/principal/ServletLogin")) { // se for nulo ou vazio, ou não tiver o servletlogin
				
				RequestDispatcher redirect = request.getRequestDispatcher("/index.jsp?url=" + urlAuth); // ele vai para o login, e deixa a url que ele estava tentando acessar antes
				request.setAttribute("msg", "Realize seu login");
				redirect.forward(request, response);
				return; // para a execução e redireciona para o login
			}
			else {
				chain.doFilter(request, response); // acima é feito as validações, chain deixa o processo do software continuar
			}
			
			connection.commit(); // deu tudo certo, salva as alterações no banco de dados
		
		} catch (Exception e) {
			e.printStackTrace();
			RequestDispatcher redirect = request.getRequestDispatcher("/error.jsp");
			request.setAttribute("msg" , e.getMessage());
			redirect.forward(request, response);
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	}

	// inicia os processos ou recursos quando o servidor sobe o sistema, ex: inicia a conexão com o db
	public void init(FilterConfig fConfig) throws ServletException { 		
		connection = SingleConnection.getConnection(); // inicia a conexão
	}
}
