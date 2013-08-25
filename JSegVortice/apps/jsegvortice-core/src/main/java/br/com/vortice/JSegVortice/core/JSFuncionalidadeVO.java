package br.com.vortice.JSegVortice.core;

import java.util.List;

public class JSFuncionalidadeVO extends JSValueObject {

	private Integer				codigo;
	private String				nome;
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
	public List<JSFuncaoVO> getFuncoes() {
		return funcoes;
	}
	public void setFuncoes(List<JSFuncaoVO> funcoes) {
		this.funcoes = funcoes;
	}
}
