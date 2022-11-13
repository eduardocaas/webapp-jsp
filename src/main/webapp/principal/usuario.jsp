<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<!DOCTYPE html>
<html lang="en">

<jsp:include page="head.jsp"></jsp:include>

<body>

  <jsp:include page="theme-loader.jsp"></jsp:include> <!-- pre-loader -->
  
  <div id="pcoded" class="pcoded">
      <div class="pcoded-overlay-box"></div>
      <div class="pcoded-container navbar-wrapper">
          
          <jsp:include page="navbar.jsp"></jsp:include>

          <div class="pcoded-main-container">
              <div class="pcoded-wrapper">
                  
                  <jsp:include page="menunavbar.jsp"></jsp:include>
                  
                  <div class="pcoded-content">
            
                      <jsp:include page="page-header.jsp"></jsp:include>
                      
                        <div class="pcoded-inner-content">
                            <!-- Main-body start -->
                            <div class="main-body">
                                <div class="page-wrapper">
                                    <!-- Page-body start -->
									<div class="page-body">

										<div class="row">
											<div class="col-sm-12">
												<!-- Basic Form Inputs card start -->
												<div class="card">
													<div class="card-block">
														<h4 class="sub-title">Cadastro de usuário</h4>
														<form class="form-material" action="<%= request.getContextPath() %>/ServletUsuario" method="post" id="formUser">
														
															<input type="hidden" name="act" id="act" value=""></input>
														
                                                            <div class="form-group form-default form-static-label">
                                                                <input type="text" name="id" id="id" class="form-control" placeholder="ID" value="${modelLogin.id}" readonly="readonly">
                                                                <span class="form-bar"></span>
                                                                <label class="float-label">ID</label>
                                                            </div>
                                                            <div class="form-group form-default">
                                                                <input type="text" name="nome" id="nome" class="form-control" required="required" value="${modelLogin.nome}">
                                                                <span class="form-bar"></span>
                                                                <label class="float-label">Nome</label>
                                                            </div>
                                                            <div class="form-group form-default">
                                                                <input type="text" name="email" id="email" class="form-control" required="required" autocomplete="off" value="${modelLogin.email}">
                                                                <span class="form-bar"></span>
                                                                <label class="float-label">E-mail</label>
                                                            </div>
                                                            <div class="form-group form-default">
                                                                <input type="text" name="login" id="login" class="form-control" required="required" autocomplete="off" value="${modelLogin.login}">
                                                                <span class="form-bar"></span>
                                                                <label class="float-label">Login</label>
                                                            </div>
                                                            <div class="form-group form-default">
                                                                <input type="password" name="senha" id="senha" class="form-control" required="required" autocomplete="off" value="${modelLogin.senha}">
                                                                <span class="form-bar"></span>
                                                                <label class="float-label">Senha</label>
                                                            </div>
                                                            
                                                            <button type="button" class="btn btn-outline-primary" onclick="clearForm();"><i class="icofont icofont-user-alt-3"></i>Novo</button>
                                                            <button class="btn btn-outline-success"><i class="icofont icofont-check-circled"></i>Salvar</button> <!-- por padrão é type submit -->
                											<button type="button" class="btn btn-outline-secondary" onclick="deleteFormAjax();"><i class="icofont icofont-warning-alt"></i>Excluir</button> <!-- type button não envia formulário-->
															<button type="button" class="btn btn-outline-info" data-toggle="modal" data-target="#ModalUser"><i class="icofont icofont-info-square"></i>Pesquisar</button> <!-- botão modal -->
															
														</form>
													</div>
												</div>
											</div>
										</div>

										<span id="msg">${msg}</span>
										<div style="height: 400px; overflow: scroll;"> <!-- scroll automático, muitos registros -->
											<table class="table" id="usersTable">
												<thead>
													<tr>
														<th scope="col">ID</th>
														<th scope="col">Nome</th>
														<th scope="col">Ver</th>
													</tr>
												</thead>
												<tbody>
													<c:forEach items="${modelLoginList}" var="ml">
														<tr>
															<td><c:out value="${ml.id}"></c:out></td>
															<td><c:out value="${ml.nome}"></c:out></td>
															<td><a class="btn btn-primary" href="<%= request.getContextPath() %>/ServletUsuario?act=viewModalUser&id=${ml.id}">Ver</a></td>
														</tr>
													</c:forEach>
												</tbody>
											</table>
										</div>
									</div>
									<!-- Page-body end -->
                                </div>
                                <div id="styleSelector"> </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    
<jsp:include page="script.jsp"></jsp:include> <!-- javascript -->
    
<!-- Modal -->
<div class="modal fade" id="ModalUser" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="exampleModalLabel">Usuários cadastrados</h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <div class="modal-body">

					<div class="input-group mb-3">
						<input type="text" class="form-control" placeholder="Digite o nome" aria-label="nome" id="nameModal" aria-describedby="basic-addon2">
						<div class="input-group-append">
							<button class="btn btn-primary" type="button" onclick="searchUser()">Buscar</button>
						</div>
					</div>
					
					<div style="height: 400px; overflow: scroll;"> <!-- scroll automático, muitos registros -->
					  <table class="table" id="modalTable">
					    <thead>
					      <tr>
					        <th scope="col">ID</th>
					        <th scope="col">Nome</th>
					        <th scope="col">Ver</th>
					      </tr>
					    </thead>
					    <tbody>
					   
					    </tbody>
					  </table>
					</div>
				  </div>
      <div class="modal-footer">
      	<span id="modalResults"></span>
        <button type="button" class="btn btn-secondary" data-dismiss="modal">Fechar</button>
      </div>
    </div>
  </div>
</div>    
    
    
<script type="text/javascript">

	function clearForm() {
			
		let elementos = document.getElementById("formUser").elements; // Retorna os elementos html dentro do form
		
		for (p = 0; p < elementos.length; p++){
			
			elementos[p].value = '';  // limpar formulario, atribui valor vazio aos campos
			
		}
		
	}
	
	function deleteFormAjax() {
		
		if(confirm('Deseja realmente excluir os dados?')){
			
			let urlAction = document.getElementById('formUser').action; // action do formulario, para usar servlet usuario
			let idUser = document.getElementById('id').value; // id usuario no formulario
			
			$.ajax({
				
				method: "get", // cai no get no servlet
				url: urlAction,
				data: "id=" + idUser + '&act=deleteAjax',
				success: function (response) {
					
					clearForm();
					document.getElementById('msg').textContent = response;
					
				}
				
			}).fail(function(xhr, status, errorThrown ) {// caso de erro, xhr traz detalhes do erro, status do erro, errorThrown exceção de erro
															
				alert('Erro ao deletar usuário por id: ' + xhr.responseText);
				
			});

		}
		
	}
	
	function deleteForm() {
		
		if(confirm('Deseja realmente excluir os dados?')){
		
			document.getElementById("formUser").method = 'get'; // mudando para get
			document.getElementById("act").value = 'delete';
			document.getElementById("formUser").submit();
		
		}
	}
	
	function searchUser() {
		
		let nameModal = document.getElementById('nameModal').value;
		let urlAction = document.getElementById('formUser').action;
		
		if (nameModal != null && nameModal != '' && nameModal.trim() != ''){ // validando se tem valor para fazer busca no banco

			$.ajax({
				
				method: "get", // cai no get no servlet
				url: urlAction,
				data: "nameModal=" + nameModal + '&act=searchUserAjax',
				success: function (response) {
					
					let json = JSON.parse(response); // converte pra JSON
				
					$('#modalTable > tbody > tr').remove(); // remove todas as linhas antes da consulta, para limpar a tabela
					
					for(let i = 0; i < json.length; i++){
						
						$('#modalTable > tbody').append('<tr> <td>' + json[i].id + '</td> <td>' + json[i].nome + '</td> <td><button onclick="viewUser(' + json[i].id + ');" type="button" class="btn btn-info">Ver</button></td> </tr>');									
						
					}
					
					document.getElementById('modalResults').textContent = 'Total de registros: ' + json.length;
					
				}
				
			}).fail(function(xhr, status, errorThrown ) {// caso de erro, xhr traz detalhes do erro, status do erro, errorThrown exceção de erro
															
				alert('Erro ao buscar usuário por nome: ' + xhr.responseText);
				
			});
			
		}
		
	}
	
	function viewUser(id){
		
		let urlAction = document.getElementById('formUser').action;
		window.location.href = urlAction + '?act=viewModalUser&id=' + id; // executa get, redireciona a servlet, com parametros
		
	}
	
	
	

</script>
    
</body>
</html>
