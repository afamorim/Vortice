package com.vortice.core.persistencia;

import java.sql.Connection;

import com.vortice.core.exception.AmbienteException;
import com.vortice.core.persistencia.conexao.ConexaoIf;

public class MsSqlDAO extends DataAccessObjectAB {
	
	private ConexaoIf conexao;
	
	public void setConexao(ConexaoIf conexao) {
		this.conexao = conexao;
	}
	
	public Connection getConexao() throws AmbienteException{
		return this.conexao.getConexao();
	}
}
