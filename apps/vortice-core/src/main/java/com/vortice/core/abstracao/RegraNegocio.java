package com.vortice.core.abstracao;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.vortice.core.bean.BeanUtil;
import com.vortice.core.bean.PropertyVO;
import com.vortice.core.exception.AmbienteException;
import com.vortice.core.exception.AplicacaoException;
import com.vortice.core.persistencia.FabricaDAOIf;

public abstract class RegraNegocio {
	
	private String xmlPath;
	
	private InputStream is;
	
	protected FabricaDAOIf fabricaDAO;

	
	public RegraNegocio(FabricaDAOIf fabricaDAONova){
		this.fabricaDAO = fabricaDAONova;
	}
	
	public RegraNegocio(String aXmlPath){
		this.xmlPath = aXmlPath;
	}
	
	public RegraNegocio(InputStream aIs){
		this.is = aIs;
	}
	
	public RegraNegocio(){}

	public FabricaDAOIf getFabricaDAO() {
		return fabricaDAO;
	}
	
	public void setXmlPath(String aXmlPath){
		this.xmlPath = aXmlPath;
	}

	public String getXmlPath() {
		return xmlPath;
	}
	
	public String getTipoBanco() throws AmbienteException{
		try{
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			if (xmlPath != null && !xmlPath.equals("")){
				Document doc = db.parse(xmlPath);
				Element elem = doc.getDocumentElement();
				NodeList children = elem.getElementsByTagName("banco");
				if (children == null) return null;
				Element child = (Element)children.item(0);
		        if( child == null ) return null;
		        return child.getFirstChild().getNodeValue();
			} else if (is != null){
				Document doc = db.parse(is);
				Element elem = doc.getDocumentElement();
				NodeList children = elem.getElementsByTagName("banco");
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
	
	protected void validateEmpty(Object objeto, PropertyVO[] aParametros) throws AplicacaoException, AmbienteException{
		if (aParametros != null && aParametros.length > 0){
			for (PropertyVO property : aParametros){
				PropertyVO retorno = BeanUtil.executePropertyGet(property.getName(), objeto);
				if (retorno.getValue() == null){
					throw new AplicacaoException("O campo " + property.getLabel() + ", não pode ser vazio.");
				}else if (retorno.getValue() instanceof String){
					if (retorno.getValue().toString().trim().equals("")){
						throw new AplicacaoException("O campo " + property.getLabel() + ", não pode ser vazio.");
					}
				}
			}
		}
	}

	public InputStream getIs() {
		return is;
	}

	public void setIs(InputStream is) {
		this.is = is;
	}
}	