<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>


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
                                                            
                                                            <button type="button" class="btn waves-effect waves-light btn-primary btn-outline-primary" onclick="clearForm()"><i class="icofont icofont-user-alt-3"></i>Novo</button>
                                                            <button class="btn waves-effect waves-light btn-success btn-outline-success"><i class="icofont icofont-check-circled"></i>Salvar</button> <!-- por padrão é type submit -->
                											<button type="button" class="btn waves-effect waves-light btn-danger btn-outline-danger" onclick="deleteForm()"><i class="icofont icofont-eye-alt"></i>Excluir</button> <!-- type button não envia formulário-->
               
                                                        </form>
													</div>
												</div>
											</div>
										</div>

										<span>${msg}</span>

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
    
<script type="text/javascript">

	function clearForm() {
			
		let elementos = document.getElementById("formUser").elements; // Retorna os elementos html dentro do form
		
		for (p = 0; p < elementos.length; p++){
			
			elementos[p].value = '';  // limpar formulario, atribui valor vazio aos campos
			
		}
		
	}
	
	function deleteForm(){
		
		if(confirm('Deseja realmente excluir os dados?'));{
		
			document.getElementById("formUser").method = 'get'; // mudando para get
			document.getElementById("act").value = 'delete';
			document.getElementById("formUser").submit();
		
		}
	}
	
	
	

</script>
    
</body>
</html>
