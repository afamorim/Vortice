package br.com.vortice.JSegVortice.core;

import java.util.List;

public class JSTelaVO extends JSValueObject {

	private Integer				codigo;
	private String				nome;
	private String				URL;
	private Boolean				restrita;
	private JSAplicacaoVO		aplicacao;
	private List<JSFuncaoVO> 	funcoes;
	
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
	public String getURL() {
		return URL;
	}
	public void setURL(String uRL) {
		URL = uRL;
	}
	public Boolean getRestrita() {
		return restrita;
	}
	public void setRestrita(Boolean restrita) {
		this.restrita = restrita;
	}
	public List<JSFuncaoVO> getFuncoes() {
		return funcoes;
	}
	public void setFuncoes(List<JSFuncaoVO> funcoes) {
		this.funcoes = funcoes;
	}
	public JSAplicacaoVO getAplicacao() {
		return aplicacao;
	}
	public void setAplicacao(JSAplicacaoVO aplicacao) {
		this.aplicacao = aplicacao;
	}
	
}