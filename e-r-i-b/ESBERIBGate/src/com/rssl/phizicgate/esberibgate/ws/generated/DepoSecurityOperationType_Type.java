/**
 * DepoSecurityOperationType_Type.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizicgate.esberibgate.ws.generated;

public class DepoSecurityOperationType_Type implements java.io.Serializable {
    private java.lang.String _value_;
    private static java.util.HashMap _table_ = new java.util.HashMap();

    // Constructor
    protected DepoSecurityOperationType_Type(java.lang.String value) {
        _value_ = value;
        _table_.put(_value_,this);
    }

    public static final java.lang.String _value1 = "1";
    public static final java.lang.String _value2 = "2";
    public static final java.lang.String _value3 = "4";
    public static final java.lang.String _value4 = "8";
    public static final java.lang.String _value5 = "16";
    public static final DepoSecurityOperationType_Type value1 = new DepoSecurityOperationType_Type(_value1);
    public static final DepoSecurityOperationType_Type value2 = new DepoSecurityOperationType_Type(_value2);
    public static final DepoSecurityOperationType_Type value3 = new DepoSecurityOperationType_Type(_value3);
    public static final DepoSecurityOperationType_Type value4 = new DepoSecurityOperationType_Type(_value4);
    public static final DepoSecurityOperationType_Type value5 = new DepoSecurityOperationType_Type(_value5);
    public java.lang.String getValue() { return _value_;}
    public static DepoSecurityOperationType_Type fromValue(java.lang.String value)
          throws java.lang.IllegalArgumentException {
        DepoSecurityOperationType_Type enumeration = (DepoSecurityOperationType_Type)
            _table_.get(value);
        if (enumeration==null) throw new java.lang.IllegalArgumentException();
        return enumeration;
    }
    public static DepoSecurityOperationType_Type fromString(java.lang.String value)
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
        new org.apache.axis.description.TypeDesc(DepoSecurityOperationType_Type.class);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "DepoSecurityOperationType_Type"));
    }
    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

}
