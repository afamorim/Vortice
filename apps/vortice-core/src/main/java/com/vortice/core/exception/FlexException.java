package com.vortice.core.exception;

public class FlexException extends Exception {

	private String descricao;
	
	public FlexException(String aDescricao, Exception aException){
		super(aDescricao, aException);
		descricao = aDescricao;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
}
