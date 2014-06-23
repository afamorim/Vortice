package com.vortice.web.view;

import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.vortice.core.exception.AmbienteException;
import com.vortice.core.exception.AplicacaoException;

public class BasePageBean implements Serializable {
	
	private static final long 		serialVersionUID = 811762826605968812L;
	
	private final String			sucesso = "sucesso";
	private final String			falha = "falha";
	private InputStream 			is;
	private String 					xmlPath;
	private transient static Logger	LOG = Logger.getLogger(BasePageBean.class);
	private String 					sort;
	private boolean					ascending;
	
	public Application getApplication(){
		return getFacesContext().getApplication();
	}
	
	public FacesContext getFacesContext(){
		return FacesContext.getCurrentInstance();
	}
	
	public HttpSession getHttpSession(){
		return (HttpSession)getFacesContext().getExternalContext().getSession(true);
	}
	
	public String getSessionBean() throws AmbienteException{
		try{
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			if (xmlPath != null && !xmlPath.equals("")){
				Document doc = db.parse(xmlPath);
				Element elem = doc.getDocumentElement();
				NodeList children = elem.getElementsByTagName("facade");
				if (children == null) return null;
				Element child = (Element)children.item(0);
		        if( child == null ) return null;
		        return child.getFirstChild().getNodeValue();
			} else if (is != null){
				Document doc = db.parse(is);
				Element elem = doc.getDocumentElement();
				NodeList children = elem.getElementsByTagName("facade");
				if (children == null) return null;
				Element child = (Element)children.item(0);
		        if( child == null ) return null;
		        return child.getFirstChild().getNodeValue();
			} else {
				return null;
			}
		}catch(ParserConfigurationException pcEx){
			throw new AmbienteException(pcEx);
		}catch(IOException ioEx){
			throw new AmbienteException(ioEx);
		}catch(SAXException saxEx){
			throw new AmbienteException(saxEx);
		}
	}
	
	public void addMessage(Severity severity,String id,String title,String mensagem){
		FacesMessage msg = new FacesMessage(severity, title, mensagem);  
		FacesContext.getCurrentInstance().addMessage(id, msg);
		
		Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
		flash.setKeepMessages(true);
		flash.setRedirect(true);
	}
	
	public void addMessageSucesso(String id,String title,String mensagem){
		addMessage(FacesMessage.SEVERITY_INFO, id, title, mensagem);
    }
    
    public void addMessageSucesso(String id,String mensagem){
        addMessage(FacesMessage.SEVERITY_INFO,id,"INFO",mensagem);
    }
    
    public void addMessageSucesso(String mensagem){
        addMessageSucesso(null,mensagem);
    }
	
	public String tratarExcecao(Exception e){
		FacesMessage msgs = null;
		LOG.debug("FacesContext.getCurrentInstance() " + FacesContext.getCurrentInstance());
		if (e instanceof AplicacaoException) {
			AplicacaoException aplEx = (AplicacaoException) e;
			if (e.getMessage().equals(MensagensErroIf.REGISTRO_DUPLICADO)){
				String erroMsg = "Já existe um registro com essas informações na base de dados.";
				msgs = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", erroMsg);
			}else if (e.getMessage().equals(MensagensErroIf.DELETE_REGISTRO_LIGADO)){
				String erroMsg = "Não foi possivel remover este registro, pois ainda existem registros ligados a este.";
				msgs = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", erroMsg);
			}else{
				msgs = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", aplEx.getMessage());
			}
			LOG.error("Erro de Aplicação ", aplEx);
		}else if (e instanceof AmbienteException){
			AmbienteException ambEx = (AmbienteException) e;
			String erroMsg = "Ocorreu um erro inesperado, comunique o administrador do sistema.";
			msgs = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", erroMsg);
			LOG.error("Erro de Ambiente ", ambEx);
		}else{
			String erroMsg = "Ocorreu um erro inesperado, comunique o administrador do sistema.";
			msgs = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", erroMsg);
			LOG.error("Erro de Generico ", e);
		}
		FacesContext.getCurrentInstance().addMessage(null, msgs);
        return falha;
	}
	
	protected HttpServletRequest getRequest(){
		return (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
	}
	
	protected HttpSession getSession(){
		return getRequest().getSession();
	}
    
	protected HttpServletResponse getResponse(){
		return (HttpServletResponse)FacesContext.getCurrentInstance().getExternalContext().getResponse();
	}
	
    protected void registrarMensagem(String msg){
        FacesMessage msgs = new FacesMessage(msg);
        getFacesContext().addMessage(null, msgs);
    }
    
    public List getSelect(Collection collection, String value, String texto) throws Exception{
		try{
			String valueMethod = "get" + value.substring(0, 1).toUpperCase() + value.substring(1, value.length());
			String textoMethod = "get" + texto.substring(0, 1).toUpperCase() + texto.substring(1, texto.length());
			ArrayList list = new ArrayList();
			if (collection != null && collection.size() > 0){
				Iterator iterator = collection.iterator();
				if (iterator != null){
					while(iterator.hasNext()){
						Object obj = iterator.next();
						String v = "";
						try{
							v = obj.getClass().getMethod(valueMethod, null).invoke(obj, null).toString();
						}catch(NullPointerException nullEx){
							nullEx.printStackTrace();
						}
						String t = "";
						try{
							t = obj.getClass().getMethod(textoMethod, null).invoke(obj, null).toString();
						}catch(NullPointerException nullEx){
							nullEx.printStackTrace();
						}
						//System.out.println("V " + v + " T " + t);
						SelectItem selectItem = new SelectItem(v, t, null);
						list.add(selectItem); 
					}
				}
			}
			return list;
		}catch(Exception e){
			LOG.error("BasePageBean getSelect() Error:", e);
			return new ArrayList();
		}
	}
	
	public Collection extractSelect(List listSelect, String objName, String value, String texto) throws Exception{
		try{
//			String valueMethod = "set" + value.substring(0, 1).toUpperCase() + value.substring(1, value.length());
			//String textoMethod = "set" + texto.substring(0, 1).toUpperCase() + texto.substring(1, texto.length());
			Collection collObj = new ArrayList();
			Class classObj = Class.forName(objName);
			PropertyDescriptor propertyInfo = null;
			if (listSelect != null && listSelect.size() > 0){
				Iterator iterator = listSelect.iterator();
				Class inteiro[] = new Class[1];
	            inteiro[0] = Integer.class;
	            Class string[] = new Class[1];
	            string[0] = String.class;
	            //partypes[1] = String.;
				while(iterator.hasNext()){
					Object obj = classObj.newInstance();
					SelectItem selectItem = (SelectItem)iterator.next();
					if (!selectItem.getValue().toString().trim().equals("")){
						propertyInfo= PropertyUtils.getPropertyDescriptor(obj, value);
		                if (propertyInfo==null)
		                    throw new AmbienteException("Nome da Propriedade ( "+ value +" ) nao encontrada.");
		                if (propertyInfo.getWriteMethod() == null)
		                    throw new AmbienteException("Nao existe o metodo set para a Propriedade ( " + value + " ).");
		                propertyInfo.getWriteMethod().invoke(obj, new Object[]{new Integer(selectItem.getValue().toString())});
		                
		                propertyInfo= PropertyUtils.getPropertyDescriptor(obj, texto);
		                if (propertyInfo==null)
		                    throw new AmbienteException("Nome da Propriedade ( "+ texto +" ) nao encontrada.");
		                if (propertyInfo.getWriteMethod() == null)
		                    throw new AmbienteException("Nao existe o metodo set para a Propriedade ( " + selectItem.getLabel() + " ).");
		                propertyInfo.getWriteMethod().invoke(obj, new Object[]{new String(texto)});
		                
						//obj.getClass().getMethod(valueMethod, inteiro).invoke(obj, new Object[]{selectItem.getValue()});
						//obj.getClass().getMethod(textoMethod, string).invoke(obj, new Object[]{selectItem.getLabel()});
						collObj.add(obj);
					}
				}
			}
			return collObj;
		}catch(Exception e){
			LOG.error("BasePageBean extractSelect() Erro:", e);
			return new ArrayList();
		}
	}
	
	public void sort(String sortColumn){
		if (sortColumn == null){
        	throw new IllegalArgumentException("Argument sortColumn must not be null.");
		}

		if (sort.equals(sortColumn)){
			//current sort equals new sortColumn -> reverse sort order
			ascending = !ascending;
		}else{
			//sort new column in default direction
			sort = sortColumn;
			ascending = isDefaultAscending(sort);
		}
		sort(sort, ascending);
	}
	
	protected boolean isDefaultAscending(String sortColumn){
		return false;
	}
	
	protected void sort(final String column, final boolean ascending){
		
	}

	public InputStream getIs() {
		return is;
	}

	public void setIs(InputStream is) {
		this.is = is;
	}

	public String getXmlPath() {
		return xmlPath;
	}

	public void setXmlPath(String xmlPath) {
		this.xmlPath = xmlPath;
	}

	public String getFalha() {
		return falha;
	}

	public String getSucesso() {
		return sucesso;
	}
	
	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public boolean isAscending() {
		return ascending;
	}

	public void setAscending(boolean ascending) {
		this.ascending = ascending;
	}
}