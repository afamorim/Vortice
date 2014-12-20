package com.vortice.core.util.converter;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;

import com.vortice.core.util.TypeConverter;

public class DecimalConverter implements Converter {

    protected DecimalFormat formatter;
    
    public Object getAsObject(FacesContext pFacesContext, UIComponent pUIComponent, String value) throws ConverterException {
        try{
            if (TypeConverter.isClear(value)){
               return null; 
            }else{
                formatter = 
                    new DecimalFormat("#,##0.00",new DecimalFormatSymbols(pFacesContext.getApplication().getDefaultLocale()));
                return new Float(formatter.parse(value.toString()).doubleValue());
            }
        }catch(ParseException pe){
            String msg = "\""+pUIComponent.getId()+"\": Formato inválido.";
            FacesMessage errMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR,msg,msg);
            throw new ConverterException(errMsg);
        }
    }

    public String getAsString(FacesContext pFacesContext, UIComponent pUIComponent, Object value) throws ConverterException {
        Number numero = (Number)value;
        if (TypeConverter.isClear(numero)){
           return ""; 
        }else{
            formatter = 
                new DecimalFormat("#,##0.00",new DecimalFormatSymbols(pFacesContext.getApplication().getDefaultLocale()));
            return formatter.format(numero.doubleValue());
        }
    }
    
}
