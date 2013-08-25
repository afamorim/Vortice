/*
 * Created on 13/03/2005
 */
package com.vortice.core.abstracao;

import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

import com.vortice.core.exception.AmbienteException;
import com.vortice.core.exception.AplicacaoException;
/**
 * @author Antonio Amadeu
 */
public class BaseSessionBean implements SessionBean{
	
	/**
	 * Contexto da sessão.
	 */
	protected SessionContext sessionContext;

	/**
	* Padrão de um EJB SessionBean.
	*/
	public void ejbCreate() throws AmbienteException{
	}

	/**
	* Padrão de um EJB SessionBean.
	*/
	public void ejbActivate() {}

  	/**
   	* Padrão de um EJB SessionBean.
   	*/
  	public void ejbPassivate() {}

  	/**
   	* Padrão de um EJB SessionBean.
   	*/
	public void ejbRemove() {}

  	/**
  	* Padrão de um EJB SessionBean.
  	*/
  	public void setSessionContext(SessionContext sessionContext) {
  		this.sessionContext = sessionContext;
  	}
  	
    /**
     * Método para tratar excessão de insert-update-delete
     * 
     * @param excessao
     * @return
     */
  	protected void tratarExcessaoFacade(Exception excessao) throws AplicacaoException,AmbienteException{
        sessionContext.setRollbackOnly();
        if (excessao instanceof AplicacaoException){
            throw (AplicacaoException)excessao;
        }else if (excessao instanceof AmbienteException){
            throw (AmbienteException)excessao;
        }else{
            throw new AmbienteException("Erro não tratado:", excessao);
        }
    }
}