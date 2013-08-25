package com.vortice.core.util;

import org.apache.commons.beanutils.ConversionException;
import org.apache.commons.beanutils.Converter;


/**
 * This class is converts a java.util.Date to a String
 * and a String to a java.util.Date. It is used by
 * BeanUtils when copying properties.  Registered
 * for use in BaseManager.
 * 
 * <p>
 * <a href="DateConverter.java.html"><i>View Source</i></a>
 * </p>
 * 
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a>
 * @version $Revision: 1.1 $ $Date: 2008/05/04 01:00:34 $
 */
public class DateConverter implements Converter {
     
    public DateConverter(){
        super();
    }
    
    
    /**
     * Convert a String to a Date and a Date to a String
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
                        return DateUtil.getInstance().convertStringToDate(value.toString());
                }
            } catch (Exception pe) {
                pe.printStackTrace();
            }
        }

        throw new ConversionException("Não pode converter "
                                      + value.getClass().getName() + " para "
                                      + type.getName() + "!");
    }
}
