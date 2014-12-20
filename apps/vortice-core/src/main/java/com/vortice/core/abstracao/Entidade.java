package com.vortice.core.abstracao;

import java.io.Serializable;

@SuppressWarnings("serial")
public interface Entidade<ID> extends Serializable{

	public abstract ID getCodigo();

	public abstract void setCodigo(ID codigo);
}
