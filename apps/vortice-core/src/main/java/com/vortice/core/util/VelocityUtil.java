package com.vortice.core.util;

import java.io.Writer;
import java.util.Properties;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

import com.vortice.core.exception.AmbienteException;

public class VelocityUtil {
	
	private VelocityEngine velocityEngine;
	
	private Template template;
	
	private VelocityContext velocityContext;

	public VelocityUtil() throws AmbienteException{
		velocityEngine = new VelocityEngine();
		velocityContext = new VelocityContext();
		try{
			velocityEngine.init();
		}catch(Exception e){
			throw new AmbienteException(e);
		}
	}
	
	public VelocityUtil(Properties properties) throws AmbienteException{
		velocityEngine = new VelocityEngine();
		velocityContext = new VelocityContext();
		try{
			velocityEngine.init(properties);
		}catch(Exception e){
			throw new AmbienteException(e);
		}
	}
	
	/*
	 * Metodo responsavel setar o caminho do template.
	 */
	public void setTemplate(String pathTemplate) throws AmbienteException{
		try{
			template = velocityEngine.getTemplate(pathTemplate);
		}catch(Exception e){
			System.out.println(e);
			throw new AmbienteException(e);
		}
	}
	
	/*
	 * Metodo responsavel por colocar o obejcto no contexto
	 */
	public void setObjContext(Object object, String nomeObj){
		this.velocityContext.put(nomeObj, object);
	}
	
	/*
	 * Metodo responsavel fazer merge de um objeto writer com o template
	 */
	public void mergeTemplate(Writer writer) throws AmbienteException{
		try{
			template.merge(velocityContext, writer);
		}catch(Exception e){
			throw new AmbienteException(e);
		}
	}
}