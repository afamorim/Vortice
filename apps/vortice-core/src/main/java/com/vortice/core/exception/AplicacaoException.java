/*
 * Created on 15/10/2004
 *
 */
package com.vortice.core.exception;

import javax.ejb.ApplicationException;

/**
 * Classe responsavel por tratar as exceptions da Aplicacao.
 *
 * @author Fernando Amorim
 */
@ApplicationException(rollback=true)
public class AplicacaoException extends Exception {

	private Integer codigo;
	
    /**
     * Constutor que irá passar uma mensagem e a exception que foi lançada.
     *
     * @param aMensagemDeErro
     * @param aExcessaoOriginal
     */
    public AplicacaoException(String aMensagemDeErro, Exception aExcessaoOriginal) {
        super(aMensagemDeErro, aExcessaoOriginal);
    }
    
    /**
     * Construtor que irá passar a mensagem a execption lançada alem do codigo do erro.
     * @param aMensagemDeErro
     * @param codigo
     * @param aExcessaoOriginal
     */
    public AplicacaoException(String aMensagemDeErro, Integer codigo, Exception aExcessaoOriginal) {
        super(aMensagemDeErro, aExcessaoOriginal);
        this.codigo = codigo;
    }

    /**
     * Construtor que irá passar a exception que foi lançada.
     *
     * @param aExcessaoOriginal
     */
    public AplicacaoException(Exception aExcessaoOriginal) {
        super(aExcessaoOriginal);
    }

    /**
     * Construtor que irá passar uma messagem.
     *
     * @param aMensagemDeErro
     */
    public AplicacaoException(String aMensagemDeErro) {
        super(aMensagemDeErro);
    }
    
    /**
     * Construtor que irá passar uma messagem e codigo do erro.
     *
     * @param aMensagemDeErro
     */
    public AplicacaoException(String aMensagemDeErro, Integer codigo) {
        super(aMensagemDeErro);
        this.codigo = codigo;
    }

    public String toString() {
        StringBuffer retorno = new StringBuffer();
        if (getCause() != null) {
            StackTraceElement[] erros = getCause().getStackTrace();
            for (int i = 0; i < erros.length; i++) {
                retorno.append(erros[i].toString());
            }
        }
        return getCause() + retorno.toString();
    }

	public Integer getCodigo() {
		return codigo;
	}
}