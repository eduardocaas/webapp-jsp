package model;

import java.io.Serializable;

public class ModelLogin implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	private String nome;
	private String email;
	private String login;
	private String senha;
	private String perfil;
	private String sexo;
	private String fotouser;
	private String fotouser_extensao;
	private boolean useradmin;
	
	
	public boolean isNew() {
		
		if (this.id == null) {
			return true; // Inserir novo
		} 
		else if (this.id != null && this.id > 0){
			return false; // Atualizar
		}
		
		return id == null;
		
	}
	
	public String getFoto() {
		return fotouser;
	}
	
	public void setFoto(String foto) {
		this.fotouser = foto;
	}
	
	public String getFotoextensao() {
		return fotouser_extensao;
	}
	
	public void setFotoextensao(String fotoextensao) {
		this.fotouser_extensao = fotoextensao;
	}
	
	public String getSexo() {
		return sexo;
	}
	
	public void setSexo(String sexo) {
		this.sexo = sexo;
	}
	
	public String getPerfil() {
		return perfil;
	}
	
	public void setPerfil(String perfil) {
		this.perfil = perfil;
	}
	
	public void setUseradmin(boolean useradmin) {
		this.useradmin = useradmin;
	}
	
	public boolean getUseradmin() {
		return useradmin;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	
	
	
	
}
