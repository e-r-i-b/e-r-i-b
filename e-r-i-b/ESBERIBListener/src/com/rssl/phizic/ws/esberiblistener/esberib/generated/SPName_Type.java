/**
 * SPName_Type.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.ws.esberiblistener.esberib.generated;

public class SPName_Type implements java.io.Serializable {
    private java.lang.String _value_;
    private static java.util.HashMap _table_ = new java.util.HashMap();

    // Constructor
    protected SPName_Type(java.lang.String value) {
        _value_ = value;
        _table_.put(_value_,this);
    }

    public static final java.lang.String _value1 = "BP_ES";
    public static final java.lang.String _value2 = "BP_VSP";
    public static final java.lang.String _value3 = "BP_ERIB";
    public static final java.lang.String _value4 = "BP_COD";
    public static final java.lang.String _value5 = "BP_ESK";
    public static final java.lang.String _value6 = "BP_IASK";
    public static final java.lang.String _value7 = "BP_WAY";
    public static final java.lang.String _value8 = "BP_DEPO";
    public static final java.lang.String _value9 = "urn:sbrfsystems:99-autopay";
    public static final SPName_Type value1 = new SPName_Type(_value1);
    public static final SPName_Type value2 = new SPName_Type(_value2);
    public static final SPName_Type value3 = new SPName_Type(_value3);
    public static final SPName_Type value4 = new SPName_Type(_value4);
    public static final SPName_Type value5 = new SPName_Type(_value5);
    public static final SPName_Type value6 = new SPName_Type(_value6);
    public static final SPName_Type value7 = new SPName_Type(_value7);
    public static final SPName_Type value8 = new SPName_Type(_value8);
    public static final SPName_Type value9 = new SPName_Type(_value9);
    public java.lang.String getValue() { return _value_;}
    public static SPName_Type fromValue(java.lang.String value)
          throws java.lang.IllegalArgumentException {
        SPName_Type enumeration = (SPName_Type)
            _table_.get(value);
        if (enumeration==null) throw new java.lang.IllegalArgumentException();
        return enumeration;
    }
    public static SPName_Type fromString(java.lang.String value)
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
        new org.apache.axis.description.TypeDesc(SPName_Type.class);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "SPName_Type"));
    }
    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

}
