/*
 * Created on 12/03/2005
 */
package com.vortice.core.persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.vortice.core.exception.AmbienteException;
import com.vortice.core.exception.AplicacaoException;
import com.vortice.core.view.MensagensErroIf;

/**
 * @author Antonio Fernando
 */
public class PostGreSqlDAO extends DataAccessObjectAB{
	
    public static String  FOREIGN_KEY_VIOLACAO = "violates foreign key constraint";

    public static String FOREIGN_KEY_UNIQUE = "violates unique constraint";
    
//	private ConexaoIf conexao;
	
	private Logger LOG = Logger.getLogger(PostGreSqlDAO.class);
    
	public Integer findValorAtual(String sequence) throws AmbienteException {
	  	Connection conn = null;
	    PreparedStatement pStm = null;
	    ResultSet rSet = null;
	    try {
	    	conn = getTipoConexao().getConexao();
	    	pStm = conn.prepareStatement("SELECT LAST_VALUE AS sequence FROM " + sequence);
	    	rSet = pStm.executeQuery();
	    	if(!rSet.next()) throw new AmbienteException("Nao foi possível obter o próximo valor da sequence '" + sequence + "'.");
	    	return new Integer(rSet.getInt("sequence"));
	    } catch(SQLException e) {
	    	throw new AmbienteException("Nao foi possivel inserir.", e);
	    } finally {
	    	super.fechar(conn, pStm, rSet);
	    }
	}
  
	public Integer getSequence(String sequence) throws AmbienteException{
		String sql = "SELECT nextval('" + sequence + "') as sequence";
	  	Connection conn = null;
	  	PreparedStatement stmt = null;
	  	ResultSet rs = null;
	  	try{
	  		conn = getTipoConexao().getConexao();
	  		stmt = conn.prepareStatement(sql);
	  		rs = stmt.executeQuery();
	  		rs.next();
	  		int seq = rs.getInt("sequence");
	  		return new Integer(seq);
	  	}catch(SQLException sqlEx){
	  		throw new AmbienteException(sqlEx);
	  	}finally{
	  		super.fechar(conn, stmt, rs);
	  	}
	}
    
	protected void tratarExcessaoUnique(SQLException e) throws AplicacaoException {
		LOG.debug("e.getSQLState() " + e.getSQLState());
        if (e.getSQLState().equals("23505")){
            throw new AplicacaoException(MensagensErroIf.REGISTRO_DUPLICADO); 
        }
    }
	
    protected void tratarExcessaoRemove(SQLException e) throws AplicacaoException,AmbienteException {
    	e.printStackTrace();
    	if (e.getSQLState().equals("23503")){
    		System.out.println("e.getSQLState() " + e.getSQLState());
            throw new AplicacaoException(MensagensErroIf.DELETE_REGISTRO_LIGADO); 
        }
        throw new AmbienteException(e);
    }

//	public void setTipoConexao(ConexaoIf conexao) {
//		this.conexao = conexao;
//	}
//	
//	public ConexaoIf getTipoConexao() {
//		return this.conexao;
//	}
//
//	public Connection getConexao() throws AmbienteException{
//		return this.conexao.getConexao();
//	}
//	
//	public void releaseConn(Connection aConn){
//		conexao.releaseConn(aConn);
//	}
}