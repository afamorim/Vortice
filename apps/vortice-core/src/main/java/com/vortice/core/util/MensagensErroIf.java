package com.vortice.core.util;


/**
 * As mensagens devem ser colocadas no ApplicationResources.
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: </p>
 * @author Antonio Amadeu
 * @version 1.0
 */

public interface MensagensErroIf {

	String VAZIO = "error.validar.VAZIO";
	String NUMERO_INTEIRO = "error.validar.NUMERO_INTEIRO";
	String NUMERO_FLOAT = "error.validar.NUMERO_FLOAT";
	String NUMERICO = "error.validar.NUMERICO";
	String TAMANHO_MAX = "error.validar.TAMANHO_MAX";
	String NUMERO_POSITIVO = "error.validar.NUMERO_POSITIVO";
	String LETRA_OBRIGATORIA = "error.validar.LETRA_OBRIGATORIA";
	String MAIOR_QUE_ZERO = "error.validar.MAIOR_QUE_ZERO";

	String CHAVE_DUPLICADA = "error.aplicacao.CHAVE_DUPLICADA";
	String CRIACAO_ENTIDADE = "error.aplicacao.CRIACAO_ENTIDADE";
	String INESPERADO = "error.ambiente.INESPERADO";
	String DATA_INVALIDA = "error.validar.DATA";
	String DATA_FUTURA_INVALIDA = "error.validar.DATA_FUTURA_INVALIDA";
	String REGISTRO_NAO_ENCONTRADO = "error.aplicacao.REGISTRO_NAO_ENCONTRADO";
	String CAMPO_INVALIDO = "error.aplicacao.CAMPO_INVALIDO";
	String REGISTRO_EM_USO = "error.aplicacao.REGISTRO_EM_USO";
	String REGISTRO_DUPLICADO = "error.aplicacao.REGISTRO_LIGADO";

	String DATA_DEVE_SER_MAIOR =  "error.aplicacao.DATA_DEVE_SER_MAIOR";
	String DATA_DEVE_SER_MENOR = "error.aplicacao.DATA_DEVE_SER_MENOR";
	String DATA_DEVE_SER_MAIOR_IGUAL ="error.aplicacao.DATA_DEVE_SER_MAIOR_IGUAL";
	String DATA_DEVE_SER_MENOR_IGUAL ="error.aplicacao.DATA_DEVE_SER_MENOR_IGUAL";
	String DATA_DEVE_SER_IGUAL = "error.aplicacao.DATA_DEVE_SER_IGUAL";
  
	String USUARIO_INVALIDO = "error.aplicacao.USUARIO_INVALIDO";
	String PARTE_EXISTENTE = "error.aplicacao.PARTE_EXISTENTE";
	
	String DELETE_REGISTRO_LIGADO = "Este registro possui registros dependentes ao mesmo e n\u00E3o poder\u00E1 ser removido enquanto os mesmos n\u00E3o forem removidos.";
}