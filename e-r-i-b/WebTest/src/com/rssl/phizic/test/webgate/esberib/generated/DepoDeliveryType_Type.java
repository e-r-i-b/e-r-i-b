/**
 * DepoDeliveryType_Type.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.test.webgate.esberib.generated;

public class DepoDeliveryType_Type implements java.io.Serializable {
    private java.lang.String _value_;
    private static java.util.HashMap _table_ = new java.util.HashMap();

    // Constructor
    protected DepoDeliveryType_Type(java.lang.String value) {
        _value_ = value;
        _table_.put(_value_,this);
    }

    public static final java.lang.String _value1 = "1";
    public static final java.lang.String _value2 = "2";
    public static final java.lang.String _value3 = "3";
    public static final DepoDeliveryType_Type value1 = new DepoDeliveryType_Type(_value1);
    public static final DepoDeliveryType_Type value2 = new DepoDeliveryType_Type(_value2);
    public static final DepoDeliveryType_Type value3 = new DepoDeliveryType_Type(_value3);
    public java.lang.String getValue() { return _value_;}
    public static DepoDeliveryType_Type fromValue(java.lang.String value)
          throws java.lang.IllegalArgumentException {
        DepoDeliveryType_Type enumeration = (DepoDeliveryType_Type)
            _table_.get(value);
        if (enumeration==null) throw new java.lang.IllegalArgumentException();
        return enumeration;
    }
    public static DepoDeliveryType_Type fromString(java.lang.String value)
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
        new org.apache.axis.description.TypeDesc(DepoDeliveryType_Type.class);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "DepoDeliveryType_Type"));
    }
    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

}
