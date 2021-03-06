/*
 * Created on 02/03/2005
 */
package com.vortice.core.persistencia;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;

import com.vortice.core.bean.BeanUtil;
import com.vortice.core.bean.PropertyVO;
import com.vortice.core.exception.AmbienteException;
import com.vortice.core.persistencia.conexao.ConexaoIf;
import com.vortice.core.persistencia.conexao.ConexaoSpring;

public abstract class DataAccessObjectAB {
   
	private ConexaoIf conexao;
	
	public void setTipoConexao(ConexaoIf conexao) {
		this.conexao = conexao;
	}
	
	public ConexaoIf getTipoConexao() {
		return this.conexao;
	}

	public Connection getConexao() throws AmbienteException{
		return this.conexao.getConexao();
	}
	
	public void releaseConn(Connection aConn){
		conexao.releaseConn(aConn);
	}
	
    /**
     * Metodo para fechar conexao, statement e resultSet
     * @param connection
     * @param statement
     * @param rs
     * @throws java.lang.Exception
    */
    protected void fechar(Connection conn, Statement stmt, ResultSet rs) throws AmbienteException {
    	try{
        	if (rs != null){
        		rs.close();
        	}
        	if (stmt != null){
        		stmt.close();
        	}
        	if (conn != null){
        		if (conexao instanceof ConexaoSpring){
        			ConexaoSpring conexaoSpring = (ConexaoSpring)conexao;
        			conexaoSpring.releaseConn(conn);
        		}else{
	        		if (!conn.isClosed())
	        			conn.close();
        		}
        	}
        }catch(Exception e){
            throw new AmbienteException("Erro ao tentar fechar conexao, statement ou resultSet. Contate o seu administrador.", e);
        }
    }
    
    protected Object createVO(ResultSet rs,Class tipo,String[] propertes) throws AmbienteException{
      try {
            Object objeto = null;
    
//            Object valor = null;
//            String propertComposta = null;
//            PropertyDescriptor propertyInfo = null;
//            Object objectTemp = null;
//            Object objectCurrent = null;
//            String propertSimples = null;
            if (rs.next()){
                objeto = tipo.newInstance();
                for (int i = 0; i < propertes.length; i++) {
                    PropertyVO propertyVO = BeanUtil.getInstanceNestedProperty(propertes[i], objeto);
                    setPropertyValue(propertyVO.getType(), rs, i, objeto,propertes[i]);
                }
            }
            
            return objeto;
        
        } catch (Exception e) {
            e.printStackTrace();
            throw new AmbienteException(e);
        }
    }
    
    private ArrayList createArraylist(ResultSet rs,Class tipo,String[] propertes) throws AmbienteException{
        ArrayList lista = new ArrayList();
        try{
             Object vo = null;
//             String propertComposta = null;
//             PropertyDescriptor propertyInfo = null;
//             Object objectTemp = null;
//             Object objectCurrent = null;
//             String propertSimples = null;
             while (rs.next()){
                     vo = tipo.newInstance();
                     for (int i = 0; i < propertes.length; i++) {
                         
                         PropertyVO propertyVO = BeanUtil.getInstanceNestedProperty(propertes[i], vo);
                         setPropertyValue(propertyVO.getType(), rs, i, vo,propertes[i]);
                         
                     }
                     lista.add(vo);        
                 }
             
                 return lista;
        }catch (Exception e) {
            e.printStackTrace();
            throw new AmbienteException(e);
        }
    }
    
    protected Object createObject(ResultSet aResultSet, Class tipo, String[] properties) throws AmbienteException{
    	try {
    		Object vo = null;
    		if (aResultSet.next()){
    			vo = tipo.newInstance();
                for (int i = 0; i < properties.length; i++) 
                {
                    PropertyVO propertyVO = BeanUtil.getInstanceNestedProperty(properties[i], vo);
                    setPropertyValue(propertyVO.getType(), aResultSet, i, vo,properties[i]);
                }
                return vo;
    		}else return null;
		} catch (Exception e) {
			throw new AmbienteException(e);
		}
    }
    
    protected Collection createCollection(ResultSet rs,Class tipo,String[] propertes) throws AmbienteException{
            return createArraylist(rs, tipo, propertes);
    }
    
    protected List createList(ResultSet rs,Class tipo,String[] propertes) throws AmbienteException{
    	return createArraylist(rs, tipo, propertes);
    }
    
    private void setPropertyValue(Class type, 
                                  ResultSet rs,
                                  int indRs, 
                                  Object vo,
                                  String name) 
        throws SQLException,InvocationTargetException,IllegalAccessException,NoSuchMethodException{
            if (type.equals(Integer.class)){
                int valor = rs.getInt(indRs+1);
                if (!rs.wasNull())
                    PropertyUtils.setNestedProperty(vo,name,new Integer(valor));
            }else if (type.equals(String.class)){
                PropertyUtils.setNestedProperty(vo,name,rs.getString(indRs+1));
            }else if (type.equals(Long.class)){
                long valor = rs.getLong(indRs+1);
                if (!rs.wasNull())
                    PropertyUtils.setNestedProperty(vo,name,new Long(valor));
            }else if (type.equals(Double.class)){
                double valor = rs.getDouble(indRs+1);
                if (!rs.wasNull())
                    PropertyUtils.setNestedProperty(vo,name,new Double(valor));
            }else if (type.equals(Float.class)){
                float valor = rs.getFloat(indRs+1);
                if (!rs.wasNull())
                    PropertyUtils.setNestedProperty(vo,name,new Float(valor));
            }else if (type.equals(java.util.Date.class)){
                if (rs.getDate(indRs+1)!=null)
                    PropertyUtils.setNestedProperty(vo,name,new Date(rs.getDate(indRs+1).getTime()));
            }else if (type.equals(Timestamp.class)){
                if (rs.getTimestamp(indRs+1)!=null)
                    PropertyUtils.setNestedProperty(vo,name,rs.getTimestamp(indRs+1));
            }else if (type.equals(byte[].class)){
                if (rs.getBytes(indRs+1)!=null)
                    PropertyUtils.setNestedProperty(vo,name,rs.getBytes(indRs+1));    
            }else if (type.equals(Boolean.class)){
            	String strValor = rs.getString(indRs+1);
            	try{
	                int valor = new Integer(strValor);
	                if (!rs.wasNull()){
	                    if (valor==0)
	                        PropertyUtils.setNestedProperty(vo,name,Boolean.FALSE);
	                    else
	                        PropertyUtils.setNestedProperty(vo,name,Boolean.TRUE);
	                }
            	}catch(NumberFormatException e){
            		Boolean valor = rs.getBoolean(indRs+1);
            		if (!rs.wasNull()){
	                    if (!valor.booleanValue())
	                        PropertyUtils.setNestedProperty(vo,name,Boolean.FALSE);
	                    else
	                        PropertyUtils.setNestedProperty(vo,name,Boolean.TRUE);
	                }
            	}
            }
    }
    
    protected void setParameterValueStatement(PreparedStatement pStmt,Object objeto,String[] propertes) throws AmbienteException{
    	try {
    		PropertyVO propertyVO = null; 
			String propertyName = null;
			for (int i = 0; i < propertes.length; i++) {
				propertyName = propertes[i];
				propertyVO = BeanUtil.executePropertyGet(propertyName, objeto);
				setParameterValue(pStmt, i, propertyVO);    
            }
		} catch (Exception e) {
		    e.printStackTrace();
		    throw new AmbienteException(e); 
		}
   }
    
   protected void setParameterValueStatement(PreparedStatement pStmt,Object objeto,ArrayList propertes)
       throws AmbienteException{
         try {
    
            PropertyVO propertyVO = null; 
            String propertyName = null;
            for (int i = 0; i < propertes.size(); i++) {
                propertyName = (String)propertes.get(i);
                propertyVO = BeanUtil.executePropertyGet(propertyName, objeto);
                //System.out.println("propertyVO.toString()->"+ propertyVO.toString());
                setParameterValue(pStmt, i, propertyVO);    
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new AmbienteException(e); 
        }
}
    
    protected void setParameterValueStatementFilter(PreparedStatement pStmt,Object objeto,String[] propertes)
        throws AmbienteException{
         try {
    
            PropertyVO propertyVO = null; 
            String propertyName = null;
            int indStmt = 0;
            for (int i = 0; i < propertes.length; i++) {
                propertyName = propertes[i];
                propertyVO = BeanUtil.executePropertyGet(propertyName, objeto);
                indStmt = i*2;
                setParameterValue(pStmt, indStmt, propertyVO);    
                setParameterValue(pStmt, indStmt+1, propertyVO);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new AmbienteException(e); 
        }
}
   
    protected void setParameterValueStatementFilter(PreparedStatement pStmt,Object objeto,ArrayList propertes)
      throws AmbienteException{
         try {
            PropertyVO propertyVO = null; 
            String propertyName = null;
            int indStmt = 0;
            for (int i = 0; i < propertes.size(); i++) {
                propertyName = (String) propertes.get(i);
                propertyVO = BeanUtil.executePropertyGet(propertyName, objeto);
                indStmt = i*2;
                setParameterValue(pStmt, indStmt, propertyVO);    
                setParameterValue(pStmt, indStmt+1, propertyVO);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new AmbienteException(e); 
        }
    }
    
    private void setParameterValue(PreparedStatement pStmt,
                                   int indPStmt,
                                   PropertyVO propertyVO) throws SQLException
	{
        int indice = indPStmt+1;
        if (propertyVO.getType().equals(Integer.class)){
            if (propertyVO.getValue() == null)
                pStmt.setNull(indice,Types.INTEGER);
            else
                pStmt.setInt(indice,Integer.parseInt(propertyVO.getValue().toString()));
        }else if (propertyVO.getType().equals(Long.class)){
            if (propertyVO.getValue() == null)
                pStmt.setNull(indice,Types.BIGINT);
            else
                pStmt.setLong(indice,Long.parseLong(propertyVO.getValue().toString()));
        }else if (propertyVO.getType().equals(Date.class)){
            if (propertyVO.getValue() == null)
                pStmt.setNull(indice,Types.DATE);
            else
                pStmt.setDate(indice,new java.sql.Date(((Date)propertyVO.getValue()).getTime()));
        }else if (propertyVO.getType().equals(Timestamp.class)){
            if (propertyVO.getValue() == null)
                pStmt.setNull(indice,Types.TIMESTAMP);
            else
                pStmt.setTimestamp(indice,((Timestamp)propertyVO.getValue()));
        
        }else if (propertyVO.getType().equals(String.class)){
            if (propertyVO.getValue() == null)
                pStmt.setNull(indice,Types.VARCHAR);
            else
                pStmt.setString(indice,propertyVO.getValue().toString());
        }else if (propertyVO.getType().equals(Float.class)){
            if (propertyVO.getValue() == null)
                pStmt.setNull(indice,Types.FLOAT);
            else
                pStmt.setFloat(indice,((Float)propertyVO.getValue()).floatValue());
        }else if (propertyVO.getType().equals(Double.class)){
            if (propertyVO.getValue() == null)
                pStmt.setNull(indice,Types.DOUBLE);
            else
                pStmt.setDouble(indice,((Double)propertyVO.getValue()).doubleValue());
        }else if (propertyVO.getType().equals(Boolean.class)){
                if (propertyVO.getValue() == null)
                    pStmt.setNull(indice,Types.BOOLEAN);
                else{
                	if (propertyVO.getValue() instanceof Integer) {
                      if (((Boolean)propertyVO.getValue()).booleanValue()){ 
                    	  pStmt.setInt(indice,1);
                      }else{
                    	  pStmt.setInt(indice,0);
                      }
					}else{
						pStmt.setBoolean(indice,(Boolean)propertyVO.getValue());
					}
                }
        }else if (propertyVO.getType().equals(Character.class)){
            if (propertyVO.getValue() == null)
                pStmt.setNull(indice,Types.CHAR);
            else
                pStmt.setString(indice,propertyVO.getValue().toString());
        }else if (propertyVO.getType().equals(byte[].class)){
            if (propertyVO.getValue() == null)
                pStmt.setNull(indice,Types.BLOB);
            else
                pStmt.setBytes(indice,(byte[])propertyVO.getValue());
        }
    } 
}