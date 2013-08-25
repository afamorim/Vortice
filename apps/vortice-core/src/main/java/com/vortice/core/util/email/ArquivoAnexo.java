package com.vortice.core.util.email;

import java.io.File;

public class ArquivoAnexo {
	
	private String 	nomeArquivo;
	private File	arquivo;
	
	public File getArquivo() {
		return arquivo;
	}
	
	public void setArquivo(File arquivo) {
		this.arquivo = arquivo;
	}

	public String getNomeArquivo() {
		return nomeArquivo;
	}

	public void setNomeArquivo(String nomeArquivo) {
		this.nomeArquivo = nomeArquivo;
	}
	
}