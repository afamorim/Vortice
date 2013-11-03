package com.vortice.core.util.converter;

import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;

import com.vortice.core.util.TypeConverter;

public class DateConverter implements Converter {

	private SimpleDateFormat simpleDateFormat;

	public Object getAsObject(FacesContext aFacesContext, UIComponent aUIComponent, String value) throws ConverterException {
		try {
			if (TypeConverter.isClear(value)){
	               return null; 
	            }else{
	            	simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy", new DateFormatSymbols(aFacesContext.getApplication().getDefaultLocale()));
	            	return simpleDateFormat.parse(value.toString());
	            }
		} catch (Exception e) {
			String msg = "\""+aUIComponent.getId()+"\": Formato inválido.";
            FacesMessage errMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR,msg,msg);
            throw new ConverterException(errMsg);
		}
	}

	public String getAsString(FacesContext aFacesContext, UIComponent aUIComponent, Object aObject) throws ConverterException {
		if (aObject == null){
			return "";
		}else{
			simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy", new DateFormatSymbols(aFacesContext.getApplication().getDefaultLocale()));
			return simpleDateFormat.format((Date)aObject);
		}
	}
}