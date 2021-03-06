package com.vortice.core.persistencia.conexao;

public class FabricaConexao {
	
	private ConexaoIf		conexao;
	private	String 			tipoConexao;
	
	private final String	SPRING	=	"SPRING";
	private final String	JDBC	= 	"JDBC";

	public ConexaoIf getConexao() {
		if (tipoConexao.equals(JDBC)){
			conexao = new ConexaoJdbc();
		}
		
		return conexao;
	}

	public void setConexao(ConexaoIf conexao) {
		this.conexao = conexao;
	}

	public void setTipoConexao(String tipoConexao) {
		this.tipoConexao = tipoConexao;
	}
}