/*
 * Created on 13/01/2005
 */
package com.vortice.core.abstracao;

import java.io.Serializable;

import javax.faces.model.SelectItem;

/**
 * @author Antonio Fernando
 */
public abstract class SelectItemVO extends SelectItem implements Serializable {
	
	private boolean apagar;

	public boolean getApagar() {
		return apagar;
	}

	public void setApagar(boolean apagar) {
		this.apagar = apagar;
	}
	
}
