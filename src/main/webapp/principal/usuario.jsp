<%@page import="model.ModelLogin"%>
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
														<form class="form-material" enctype="multipart/form-data" action="<%= request.getContextPath() %>/ServletUsuario" method="post" id="formUser"> <!-- enctype, para uploads, apenas post  -->
														
															<input type="hidden" name="act" id="act" value=""></input>
														
                                                            <div class="form-group form-default form-static-label">
                                                                <input type="text" name="id" id="id" class="form-control" placeholder="ID" value="${modelLogin.id}" readonly="readonly">
                                                                <span class="form-bar"></span>
                                                                <label class="float-label">ID</label>
                                                            </div>
                                                            
                                                            <div class="form-group form-default input-group mb-4">
                                                            	<div class="input-group-prepend">
                                                            		<c:if test="${modelLogin.fotouser != '' && modelLogin.fotouser != null}"> <!-- carrega foto na tela, apos inserção ou busca -->
                                                            			<a href="<%= request.getContextPath() %>/ServletUsuario?act=downloadUserImage&id=${modelLogin.id}">
                                                            				<img alt="Imagem Usuario" id="imgBase64" src="${modelLogin.fotouser}" width="70px">
                                                            			</a>
                                                            		</c:if>
                                                            		<c:if test="${modelLogin.fotouser == '' || modelLogin.fotouser == null}"> <!-- se não tiver foto, usuario novo -->
                                                            			<img alt="Imagem Usuario" id="imgBase64" src="<%= request.getContextPath() %>/assets/images/faq_man.png" width="70px"> <!-- https://cdn.pixabay.com/photo/2016/08/08/09/17/avatar-1577909_1280.png -->
                                                            		</c:if>
                                                            	</div>
                                                            	<input type="file" id=imgFile name="imgFile" accept="image/*" onchange="viewImg('imgBase64' , 'imgFile');" class="form-control-file" style="margin-top: 15px; margin-left: 5px;"> <!-- aceita só imagens, de qualquer formato, invoca função js para aparecer imagem ao lado -->
                                                            </div>
                                                            
                                                            <div class="form-group form-info">
                                                                <input type="text" name="nome" id="nome" class="form-control" required="required" value="${modelLogin.nome}">
                                                                <span class="form-bar"></span>
                                                                <label class="float-label">Nome</label>
                                                            </div>
                                                            
                                                            <div class="form-group form-info">
                                                                <input type="text" name="email" id="email" class="form-control" required="required" autocomplete="off" value="${modelLogin.email}">
                                                                <span class="form-bar"></span>
                                                                <label class="float-label">E-mail</label>
                                                            </div>
                                                            
                                                            <div class="form-group form-default form-static-label">
	                                                            <select class="form-control" aria-label="Default select example" name="perfil">
																  <option disabled="disabled">Selecione o Perfil</option>
																  <option value="ADMIN" <% 
																  
																  ModelLogin modelLogin = (ModelLogin) request.getAttribute("modelLogin");
																 
																  if (modelLogin != null && modelLogin.getPerfil().equals("ADMIN")) {
																	  out.print(" ");
																	  out.print("selected=\"selected\" "); // 
																	  out.print(" ");																	  
																  }%> >Admin</option>  <!-- enum -->
																  <option value="AUXILIAR" <%
																  
																  modelLogin = (ModelLogin) request.getAttribute("modelLogin");
																  
																  if (modelLogin != null && modelLogin.getPerfil().equals("AUXILIAR")) {
																	  out.print(" ");
																	  out.print("selected=\"selected\" ");
																	  out.print(" ");																	  
																  }%> >Auxiliar</option>
																  <option value="OPERADOR" <% 
																  
																  modelLogin = (ModelLogin) request.getAttribute("modelLogin");
																  
																  if (modelLogin != null && modelLogin.getPerfil().equals("OPERADOR")) {
																	  out.print(" ");
																	  out.print("selected=\"selected\" ");
																	  out.print(" ");																	  
																  }%> >Operador</option>
																</select>
																<span class="form-bar"></span>
                                                                <label class="float-label">Perfil</label>
                                                            </div>
                                                            
                                                            <div class="form-group form-default form-static-label">
                                                            	<input type="radio" name="sexo" value="MASCULINO" checked="checked" <%
                                                            	
																modelLogin = (ModelLogin) request.getAttribute("modelLogin"); 
                                                            	
                                                            	if (modelLogin != null && modelLogin.getSexo().equals("MASCULINO")) {
																	  out.print(" ");
																	  out.print("checked=\"checked\" ");
																	  out.print(" ");																	  
																  }
                                                            	
                                                            	%>> Masculino</>
                                                            	<input type="radio" name="sexo" value="FEMININO" <%
                                                            	
                                                            	modelLogin = (ModelLogin) request.getAttribute("modelLogin"); 
                                                            	
                                                            	if (modelLogin != null && modelLogin.getSexo().equals("FEMININO")) {
																	  out.print(" ");
																	  out.print("checked=\"checked\" ");
																	  out.print(" ");																	  
																  }                                                           	
                                                            	%>> Feminino</>
                                                            
                                                            </div>
                                                            
                                                            <div class="form-group form-info">
                                                                <input onblur="searchCep();" type="text" name="cep" id="cep" class="form-control" required="required" autocomplete="off" value="${modelLogin.cep}"> <!-- onblur, quando sai do foco -->
                                                                <span class="form-bar"></span>
                                                                <label class="float-label">CEP</label>
                                                            </div>
                                                            
                                                            <div class="form-group form-info">
                                                                <input type="text" name="logradouro" id="logradouro" class="form-control" required="required" autocomplete="off" value="${modelLogin.logradouro}">
                                                                <span class="form-bar"></span>
                                                                <label class="float-label">Logradouro</label>
                                                            </div>
                                                            
                                                            <div class="form-group form-info">
                                                                <input type="text" name="bairro" id="bairro" class="form-control" required="required" autocomplete="off" value="${modelLogin.bairro}">
                                                                <span class="form-bar"></span>
                                                                <label class="float-label">Bairro</label>
                                                            </div>
                                                            
                                                            <div class="form-group form-info">
                                                                <input type="text" name="localidade" id="localidade" class="form-control" required="required" autocomplete="off" value="${modelLogin.localidade}">
                                                                <span class="form-bar"></span>
                                                                <label class="float-label">Cidade</label>
                                                            </div>
                                                            
                                                            <div class="form-group form-info">
                                                                <input type="text" name="uf" id="uf" class="form-control" required="required" autocomplete="off" value="${modelLogin.uf}">
                                                                <span class="form-bar"></span>
                                                                <label class="float-label">UF</label>
                                                            </div>
                                                            
                                                            <div class="form-group form-info">
                                                                <input type="text" name="numero" id="numero" class="form-control" required="required" autocomplete="off" value="${modelLogin.numero}">
                                                                <span class="form-bar"></span>
                                                                <label class="float-label">Número</label>
                                                            </div>
                                                            
                                                            <div class="form-group form-info">
                                                                <input type="text" name="login" id="login" class="form-control" required="required" autocomplete="off" value="${modelLogin.login}">
                                                                <span class="form-bar"></span>
                                                                <label class="float-label">Login</label>
                                                            </div>
                                                            
                                                            <div class="form-group form-info">
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
	
	function searchCep() {
		var cep = $("#cep").val();
		
		$.getJSON("https://viacep.com.br/ws/"+ cep +"/json/?callback=?" , function (dados) { // url do json
			
			if (!("erro" in dados)) {
                //Atualiza os campos com os valores da consulta.
                $("#logradouro").val(dados.logradouro);
                $("#bairro").val(dados.bairro);
                $("#localidade").val(dados.localidade);
                $("#uf").val(dados.uf);

            }
			
		});
	}
	
	function viewUser(id){
		
		let urlAction = document.getElementById('formUser').action;
		window.location.href = urlAction + '?act=viewModalUser&id=' + id; // executa get, redireciona a servlet, com parametros
		
	}
	
	function viewImg(imgBase64, imgFile) {
		
		var preview = document.getElementById('imgBase64'); // campo img html
		var imgFile = document.getElementById('imgFile').files[0]; // caso retorne mais de um arquivo pega somente o primeiro
		var reader = new FileReader();
		
		reader.onloadend = function (){
			
			preview.src = reader.result; // pega o arquivo e joga pro campo de img no html
			
		};
		
		if (imgFile) { // se tiver foto sendo carregada
			
			reader.readAsDataURL(imgFile); // preview da imagem
			
		} else {
			preview.src = ''; // limpa tela, caso não tiver imagem
		}
		
	}
	
	

</script>
    
</body>
</html>
