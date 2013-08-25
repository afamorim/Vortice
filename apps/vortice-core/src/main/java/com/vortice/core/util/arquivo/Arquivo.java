package com.vortice.core.util.arquivo;

import java.io.Serializable;

public class Arquivo implements Serializable{
	
	public Arquivo(){}
	
	private String	nome;
	private byte[]	bytes;

	public byte[] getBytes() {
		return bytes;
	}
	
	public void setBytes(byte[] bytes) {
		this.bytes = bytes;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
}