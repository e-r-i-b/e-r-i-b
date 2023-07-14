/**
 * ResolutionTypeList.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.rsa.integration.ws.notification.generated;

public class ResolutionTypeList implements java.io.Serializable {
    private java.lang.String _value_;
    private static java.util.HashMap _table_ = new java.util.HashMap();

    // Constructor
    protected ResolutionTypeList(java.lang.String value) {
        _value_ = value;
        _table_.put(_value_,this);
    }

    public static final java.lang.String _CONFIRMED_FRAUD = "CONFIRMED_FRAUD";
    public static final java.lang.String _SUSPECTED_FRAUD = "SUSPECTED_FRAUD";
    public static final java.lang.String _CONFIRMED_GENUINE = "CONFIRMED_GENUINE";
    public static final java.lang.String _ASSUMED_GENUINE = "ASSUMED_GENUINE";
    public static final java.lang.String _UNKNOWN = "UNKNOWN";
    public static final java.lang.String _ANY = "ANY";
    public static final ResolutionTypeList CONFIRMED_FRAUD = new ResolutionTypeList(_CONFIRMED_FRAUD);
    public static final ResolutionTypeList SUSPECTED_FRAUD = new ResolutionTypeList(_SUSPECTED_FRAUD);
    public static final ResolutionTypeList CONFIRMED_GENUINE = new ResolutionTypeList(_CONFIRMED_GENUINE);
    public static final ResolutionTypeList ASSUMED_GENUINE = new ResolutionTypeList(_ASSUMED_GENUINE);
    public static final ResolutionTypeList UNKNOWN = new ResolutionTypeList(_UNKNOWN);
    public static final ResolutionTypeList ANY = new ResolutionTypeList(_ANY);
    public java.lang.String getValue() { return _value_;}
    public static ResolutionTypeList fromValue(java.lang.String value)
          throws java.lang.IllegalArgumentException {
        ResolutionTypeList enumeration = (ResolutionTypeList)
            _table_.get(value);
        if (enumeration==null) throw new java.lang.IllegalArgumentException();
        return enumeration;
    }
    public static ResolutionTypeList fromString(java.lang.String value)
          throws java.lang.IllegalArgumentException {
        return fromValue(value);
    }
    public boolean equals(java.lang.Object obj) {return (obj == this);}
    public int hashCode() { return toString().hashCode();}
    public java.lang.String toString() { return _value_;}
    public java.lang.Object readResolve() throws java.io.ObjectStreamException { return fromValue(_value_);}
    public static org.apache.axis.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new org.apache.axis.encoding.ser.EnumSerializer(
            _javaType, _xmlType);
    }
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new org.apache.axis.encoding.ser.EnumDeserializer(
            _javaType, _xmlType);
    }
    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ResolutionTypeList.class);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.rsa.msk.ru/ActivityEngine/", "ResolutionTypeList"));
    }
    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

}
