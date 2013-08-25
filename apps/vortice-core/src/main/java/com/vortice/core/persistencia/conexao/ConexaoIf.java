package com.vortice.core.persistencia.conexao;

import java.sql.Connection;

import com.vortice.core.exception.AmbienteException;

public interface ConexaoIf {
	
	public Connection getConexao() throws AmbienteException;
	
	public void setDs(String ds);
	
	public String getDs();
	
	public void releaseConn(Connection aConn);
}
