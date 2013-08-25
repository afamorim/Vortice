package br.com.vortice.JSegVortice.core;

public class JSFuncaoVO extends JSValueObject {

	private Integer		codigo;
	private String		nome;
	private String		acao;
	private JSTelaVO	tela;
	
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
	public String getAcao() {
		return acao;
	}
	public void setAcao(String acao) {
		this.acao = acao;
	}
	public JSTelaVO getTela() {
		return tela;
	}
	public void setTela(JSTelaVO tela) {
		this.tela = tela;
	}
}