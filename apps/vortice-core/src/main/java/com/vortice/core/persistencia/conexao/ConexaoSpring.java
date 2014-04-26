package com.vortice.core.persistencia.conexao;

import java.sql.Connection;

import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.datasource.DataSourceUtils;

import com.vortice.core.exception.AmbienteException;

public class ConexaoSpring extends JdbcDaoSupport implements ConexaoIf {
	
	public Connection getConexao()  throws AmbienteException {
        Connection conn = null;
        try{
//        	DataSource dataSource = getDataSource();
//        	conn = dataSource.getConnection();
        	
        	//GARANTE QUE UTILIZARÁ O DATASOURCE GERENCIADO PELO SPRING, PARA GARANTIR A TRANSAÇÃO
            conn = DataSourceUtils.getConnection(getDataSource());
            return conn;
        }catch(Exception e) {
            e.printStackTrace();
            throw new AmbienteException("Nao foi possivel estabelecer uma conexão com o banco de dados. Contate o seu administrador.", e);
        }
    }

	public String getDs() {
		return null;
	}

	public void setDs(String ds) {
	}

	public void releaseConn(Connection aConn){
		DataSourceUtils.releaseConnection(aConn, getDataSource());
	}
}