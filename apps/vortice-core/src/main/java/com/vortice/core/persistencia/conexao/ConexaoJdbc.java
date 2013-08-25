package com.vortice.core.persistencia.conexao;

import java.sql.Connection;

import javax.naming.InitialContext;
import javax.rmi.PortableRemoteObject;
import javax.sql.DataSource;

import com.vortice.core.exception.AmbienteException;

public class ConexaoJdbc implements ConexaoIf {
	
	private String 					ds;
	private static InitialContext	ic;
	private DataSource 				dataSource;
	
	public Connection getConexao() throws AmbienteException  {
		Object obj = null ;
		Connection conn = null;
        try{
            obj = getInitialContext().lookup(ds);
            if (dataSource == null) {
                dataSource = (DataSource) PortableRemoteObject.narrow(obj,javax.sql.DataSource.class);
            }
            conn = dataSource.getConnection();
            return conn;
        }catch(Exception e) {
            e.printStackTrace();
            throw new AmbienteException("Nao foi possivel estabelecer uma conexão com o banco de dados. Contate o seu administrador.", e);
        }
		
	}
	
	private InitialContext getInitialContext() throws Exception {
        if (ic == null) {
            return new InitialContext();
        }
        return ic;
    }

	public void setDs(String ds) {
		this.ds = ds;
	}

	public String getDs() {
		return ds;
	}

	public void releaseConn(Connection conn) {
	}
}
