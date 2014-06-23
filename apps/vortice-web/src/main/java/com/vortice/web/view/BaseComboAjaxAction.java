package com.vortice.web.view;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.vortice.core.bean.BeanUtil;
import com.vortice.core.bean.PropertyVO;
import com.vortice.core.exception.AmbienteException;
import com.vortice.core.exception.AplicacaoException;

public abstract class BaseComboAjaxAction extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/xml");
	    response.setHeader("Cache-Control", "no-cache");
		
        try{
			Collection lista = getCollection(request, response);
			StringBuffer xml = null;
			if (lista != null && lista.size() > 0){
                
			    Iterator iterator = lista.iterator();
                PropertyVO propVO = null;
                
                xml = new StringBuffer(); 
                xml.append("<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?> \n");
                xml.append("<componentes>");
                
                while(iterator.hasNext()){
					Object valueObject = iterator.next();
                    
					xml.append("<componente>");
                    
                    propVO = BeanUtil.executePropertyGet(getValueField(), valueObject);
                    xml.append("<codigo>")
                        .append(propVO.getValue())
                        .append("</codigo>");
                    
                    propVO = BeanUtil.executePropertyGet(getTextField(), valueObject);
                    xml.append("<nome>")
                        .append(propVO.getValue())
                        .append("</nome>");
                    
                    xml.append("</componente>");
				}
				
                xml.append("</componentes>");
                
                response.getWriter().write(xml.toString().trim());
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
        
        response.flushBuffer();
	}
    
    /**
     * Retorna a cole��o que o combo ser� preenchido.
     * @param request
     * @param response
     * @return uma cole��o 
     */
    protected abstract Collection getCollection(HttpServletRequest request, HttpServletResponse response)
        throws AmbienteException,AplicacaoException;
    
    /**
     * Retorna o nome da propriedade do VO que representa o Value do Combo
     * @return 
     */
    protected abstract String getValueField();
    
    /**
     * Retorna o nome da propriedade do VO que representa o Texto do Combo
     * @return 
     */
    protected abstract String getTextField();
}
