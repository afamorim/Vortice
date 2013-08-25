package com.vortice.core.bean;

import com.vortice.core.abstracao.ValueObject;

public class PropertyVO extends ValueObject
{
    private Class	type;
    private Object	value;
    private String	name;
    private String	label;

    public PropertyVO() {}
    
    public PropertyVO(String aName, String aLabel) {
    	name = aName;
    	label = aLabel;
    }
    
    public PropertyVO(Class type, Object value, String name) {
        this.type = type;
        this.value = value;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Class getType() {
        return type;
    }

    public void setType(Class type) {
        this.type = type;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }
    
    public String toString(){
        return name+"->"+value;
    }

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}
}