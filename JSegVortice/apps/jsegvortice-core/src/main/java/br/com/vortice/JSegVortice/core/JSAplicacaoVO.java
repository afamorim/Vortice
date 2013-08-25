package br.com.vortice.JSegVortice.core;

import java.util.List;

public class JSAplicacaoVO extends JSValueObject {

	private Integer				codigo;
	private String				nome;
	private List<JSUsuarioVO>	usuarios;
	
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
	public List<JSUsuarioVO> getUsuarios() {
		return usuarios;
	}
	public void setUsuarios(List<JSUsuarioVO> usuarios) {
		this.usuarios = usuarios;
	}
}
