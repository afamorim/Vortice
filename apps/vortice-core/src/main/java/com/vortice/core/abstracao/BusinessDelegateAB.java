package com.vortice.core.abstracao;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.Hashtable;

import javax.ejb.EJBHome;
import javax.ejb.EJBObject;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.rmi.PortableRemoteObject;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.vortice.core.exception.AmbienteException;

public class BusinessDelegateAB {
private String xmlPath;
	
	private InputStream is;
	
	private String host;
	
	private Logger LOG = Logger.getLogger(BusinessDelegateAB.class);
	
	public EJBObject getEJBSessionRemote(String aAplicacao, String aNome) throws AmbienteException {
		try {

            InitialContext context = (InitialContext) getInitialContext();
            LOG.debug("Olha o que vai procurar>> " + aAplicacao + aNome);
            EJBHome lEJBHome = (EJBHome) PortableRemoteObject.narrow(context.lookup(aAplicacao + aNome),EJBHome.class);

            Method lMethod = null;

            lMethod = lEJBHome.getEJBMetaData().getHomeInterfaceClass().getMethod("create", null);

            return (EJBObject)lMethod.invoke(lEJBHome,null);
	    } catch (Exception ex) {
	    	throw new AmbienteException("Erro ao fazer look-up do ejb: "+aAplicacao + aNome,ex);
	    }
	}// Fim do getSessionRemote()
	
	private Context getInitialContext() throws NamingException {
	    Hashtable environment = new Hashtable();

	    environment.put(Context.INITIAL_CONTEXT_FACTORY, "org.jnp.interfaces.NamingContextFactory");
	    environment.put(Context.URL_PKG_PREFIXES, "org.jboss.naming:org.jnp.interfaces");
	    environment.put(Context.PROVIDER_URL, "jnp://" + host + ":1099");
	    return new InitialContext(environment);
	}
	
	public String getTipoDelegate() throws AmbienteException{
		try{
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			if (xmlPath != null && !xmlPath.equals("")){
				Document doc = db.parse(xmlPath);
				Element elem = doc.getDocumentElement();
				//NodeList nl = elem.getElementsByTagName("seguranca");
				//Element tagSeguranca = (Element) nl.item(0);
				NodeList children = elem.getElementsByTagName("sessionBean");
				if (children == null) return null;
				Element child = (Element)children.item(0);
		        if( child == null ) return null;
		        return child.getFirstChild().getNodeValue();
			} else if (is != null){
				Document doc = db.parse(is);
				Element elem = doc.getDocumentElement();
				//NodeList nl = elem.getElementsByTagName("seguranca");
				//Element tagSeguranca = (Element) nl.item(0);
				NodeList children = elem.getElementsByTagName("sessionBean");
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

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
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
}
