package com.vortice.core.view;

import org.apache.log4j.Logger;

public class Formatador {
	
	private static final Logger LOG = Logger.getLogger(Formatador.class);
	
	public static String formatCEP(Integer cep){
		if (cep != null){
			String strCep =cep.toString();
			if (strCep.length() == 8){
				strCep = strCep.substring(0, 2) + "." + strCep.substring(2, 5) + "-" + strCep.substring(5, 8);
				return strCep;
			}
		}
		return "";
	}
	
	public static Integer	parseCEP(String strCep){
		if (strCep != null && !"".equals(strCep))
		{
			if (strCep.length() == 10){
				try {
					Integer cep = new Integer((strCep.replaceAll("\\.", "")).replaceAll("-", ""));
					return cep;
				} catch (Exception e) {
					LOG.error("ERRO NA HORA RETORNAR O CEP DE " + strCep, e);
				}
			}
		}
		return null;
	}
	
	public static String formatTelefone(Long telefone){
		if (telefone != null){
			String strTelefone = telefone.toString();
			if (strTelefone.length() == 10){
				strTelefone = "(" + strTelefone.substring(0, 2) + ") " + strTelefone.substring(2, 6) + "-" + strTelefone.substring(6, 10);
				return strTelefone;
			}
		}
		return "";
	}
	
	public static Long parseTelefone(String strTelefone){
		if (strTelefone != null && !"".equals(strTelefone))
		{
			if (strTelefone.length() == 14){
				try{
					Long telefone = new Long((strTelefone.replaceAll(" ", "")).replaceAll("\\(", "").replaceAll("\\)", "").replaceAll("-", ""));
					return telefone;
				}catch(Exception e){
					LOG.error("ERRO NA HORA DE RETORNAR O TELEFONE", e);
				}
			}
		}
		return null;
	}
	
	public static Long parseCPF(String strCpf){
		if (strCpf != null && !"".equals(strCpf))
		{
			if (strCpf.length() == 14){
				try{
					Long cpf = new Long((strCpf.replaceAll("\\.", "")).replaceAll("-", ""));
					return cpf;
				}catch(Exception e){
					LOG.error("ERRO NA HORA DE RETORNAR O TELEFONE", e);
				}
			}
		}
		return null;
	}
	
	public static String formatCpf(Long cpf){
		if (cpf != null){
			String strCpf = cpf.toString();
			LOG.debug(strCpf + " >> strCpf.length() " + strCpf.length());
			if (strCpf.length() == 11){
				strCpf =  strCpf.substring(0, 3) + "." + strCpf.substring(3, 6) + "." + strCpf.substring(6, 9) + "-" + strCpf.substring(9, 11);
				return strCpf;
			}
		}
		return "";
	}
}