package com.vortice.core.util.converter;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;

import com.vortice.core.util.TypeConverter;

public class LongConverter implements Converter {

	public Object getAsObject(FacesContext pFacesContext, UIComponent pUIComponent, String value) throws ConverterException {
		System.out.println("LongConverter::AsObject " + value);
		try{
            if (TypeConverter.isClear(value)){
               return null; 
            }else{
                return new Long(value);
            }
        }catch(Exception pe){
            String msg = "\""+pUIComponent.getId()+"\": Formato invalido.";
            FacesMessage errMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR,msg,msg);
            throw new ConverterException(errMsg);
        }
	}

	public String getAsString(FacesContext pFacesContext, UIComponent pUIComponent, Object value) throws ConverterException {
		System.out.println("LongConverter::AsString " + value);
		if (value instanceof String){
			return (String)value;
		}
        Number numero = (Number)value;
        if (TypeConverter.isClear(numero)){
           return ""; 
        }else{
            return numero.toString();
        }
    }

}
