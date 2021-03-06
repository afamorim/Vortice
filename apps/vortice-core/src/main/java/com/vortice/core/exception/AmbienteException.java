/*
 * Created on 15/10/2004
 */
package com.vortice.core.exception;

/**
 * Classe de Exception que irá tratar as Exceptions de Ambiente.
 * @author Fernando Amorim
 */
public class AmbienteException extends RuntimeException{
	
	/**
	 * Constutor que ir passar uma mensagem e a exception que foi lançada.
	 * @param aMensagemDeErro
	 * @param aExcessaoOriginal
	 */
	public AmbienteException(String aMensagemDeErro, Exception aExcessaoOriginal){
		super(aMensagemDeErro,aExcessaoOriginal);
	}

	/**
	 * Construtor que irá passar a exception que foi lançada.
	 * @param aExcessaoOriginal
	 */
	public AmbienteException(Exception aExcessaoOriginal){
		super(aExcessaoOriginal);
	}

    /**
     * Construtor que irá passar uma messagem.
     *
     * @param aMensagemDeErro
     */
    public AmbienteException(String aMensagemDeErro) {
      super(aMensagemDeErro);
    }

    public String toString() {
      StringBuffer retorno = new StringBuffer();
      if (getCause() != null) {
        StackTraceElement[] erros = getCause().getStackTrace();
        for (int i = 0; i < erros.length; i++) {
          retorno.append(erros[i].toString());
        }
      }
      return getMessage() + retorno.toString();
    }
}