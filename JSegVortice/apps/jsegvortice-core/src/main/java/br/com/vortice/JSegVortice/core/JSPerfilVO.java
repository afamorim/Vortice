package br.com.vortice.JSegVortice.core;

import java.util.List;


public class JSPerfilVO extends JSValueObject{

	private Integer					codigo;
	private String					nome;
	private List<JSFuncionalidadeVO> funcionalidades;
	
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
	public List<JSFuncionalidadeVO> getFuncionalidades() {
		return funcionalidades;
	}
	public void setFuncionalidades(List<JSFuncionalidadeVO> funcionalidades) {
		this.funcionalidades = funcionalidades;
	}
}