<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="ISO-8859-1">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-Zenh87qX5JnK2Jl0vWa8Ck2rdkQ2Bzep5IDxbcnCeuOxjzrPF/et3URy9Bv1WTRi" crossorigin="anonymous">
	
	<title>Java JSP</title>
	
<style type="text/css">

form{
	position: absolute;
	top: 40%;
	left: 33%;
	right: 33%;	
}

h1{
	position: absolute;
	top: 30%;
	left: 47%;
}

.msg{
	top: 70%;
	left: 33%;
	font-size: 15px;
	color: #664d03;
    background-color: #fff3cd;
    border-color: #ffecb5;
}

</style>	
	
</head>
<body>
	<h1>Login</h1>
		
	<form action="ServletLogin" method="post" class="row g-3 needs-validation" novalidate>
	<input type="hidden" value="<%= request.getParameter("url") %>" name="url">
	
		<div class="mb-3">
			<label class="form-label">Usuário</label>
			<input name="login" type="text" class="form-control" required>
			<div class="invalid-feedback">
      			Preencha o campo usuário!
    		</div>
		</div>
		
		<div class="mb-3">			
			<label class="form-label">Senha</label>
			<input name="senha" type="password" class="form-control" required>
			<div class="invalid-feedback">
      			Preencha o campo senha!
    		</div>
		</div>
		
		<input type="submit" value="Logar" class="btn btn-primary">
		
		<h5 class="msg">${msg}</h5>		
	
	</form>
		
	
	
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-OERcA2EqjJCMA+/3y+gxIOqMEjwtxJY7qPCqsdltbNJuaOe923+mo//f6V8Qbsw3" crossorigin="anonymous"></script>

<script type="text/javascript">

	(() => {
		  'use strict'
	
		  // Fetch all the forms we want to apply custom Bootstrap validation styles to
		  const forms = document.querySelectorAll('.needs-validation')
	
		  // Loop over them and prevent submission
		  Array.from(forms).forEach(form => {
		    form.addEventListener('submit', event => {
		      if (!form.checkValidity()) {
		        event.preventDefault()
		        event.stopPropagation()
		      }
	
		      form.classList.add('was-validated')
		    }, false)
		  })
		})()

</script>

</body>
</html>