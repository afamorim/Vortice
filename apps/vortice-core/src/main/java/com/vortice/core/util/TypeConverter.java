package com.vortice.core.util;

public class TypeConverter {

    public static boolean isClear(String valor){
        return (valor==null || valor.trim().length()==0);
    }

    public static boolean isClear(Number valor){
        return (valor==null || valor.doubleValue()==0);
    }     

}
