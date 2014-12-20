package com.vortice.core.util;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

import org.apache.commons.beanutils.ConversionException;
import org.apache.commons.beanutils.Converter;


/**
 * Esta classe é chamda através do método BeanUtils.copyPropertes para converter 
 * Float.  
 * 
 * @author amadeu
 */
public class FloatConverter implements Converter {

    protected final DecimalFormat formatter;
    
    public FloatConverter(Locale locale){
        super();
        formatter  = new DecimalFormat("#,##0.00",new DecimalFormatSymbols(locale));
    }
    
    
    /**
     * Converte uma String para um Float 
     *
     * @param type the class type to output
     * @param value the object to convert
     * @return object the converted object (Date or String)
     */
    public Object convert(Class type, Object value) {
        // for a null value, return null
        if (value == null) {
            return null;
        } else {
            try {
                if (value instanceof String) {
                        if (value.toString().trim().length()==0) {
                            return null;
                        }
                        return new Float(formatter.parse(value.toString()).doubleValue());
                }
            } catch (Exception pe) {
                pe.printStackTrace();
            }
        }

        throw new ConversionException("Nãoo pode converter "
                                      + value.getClass().getName() + " para "
                                      + type.getName() + "!");
    }
}
