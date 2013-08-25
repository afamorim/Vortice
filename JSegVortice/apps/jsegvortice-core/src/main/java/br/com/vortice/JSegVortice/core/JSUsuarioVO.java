package br.com.vortice.JSegVortice.core;

import java.util.List;

public class JSUsuarioVO extends JSValueObject {

	private Integer				codigo;
	private String				nome;
	private String				login;
	private String				senha;
	private List<JSPerfilVO> 	perfis;
	private List<JSFuncaoVO>	funcoes;
	
	public Integer getCodigo() {
		return codigo;
	}
	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
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
	public List<JSPerfilVO> getPerfis() {
		return perfis;
	}
	public void setPerfis(List<JSPerfilVO> perfis) {
		this.perfis = perfis;
	}
	public List<JSFuncaoVO> getFuncoes() {
		return funcoes;
	}
	public void setFuncoes(List<JSFuncaoVO> funcoes) {
		this.funcoes = funcoes;
	}
	
}