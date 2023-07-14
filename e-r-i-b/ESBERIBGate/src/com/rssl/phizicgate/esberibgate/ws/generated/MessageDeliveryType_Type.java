/**
 * MessageDeliveryType_Type.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizicgate.esberibgate.ws.generated;

public class MessageDeliveryType_Type implements java.io.Serializable {
    private java.lang.String _value_;
    private static java.util.HashMap _table_ = new java.util.HashMap();

    // Constructor
    protected MessageDeliveryType_Type(java.lang.String value) {
        _value_ = value;
        _table_.put(_value_,this);
    }

    public static final java.lang.String _E = "E";
    public static final java.lang.String _N = "N";
    public static final java.lang.String _S = "S";
    public static final java.lang.String _P = "P";
    public static final java.lang.String _I = "I";
    public static final MessageDeliveryType_Type E = new MessageDeliveryType_Type(_E);
    public static final MessageDeliveryType_Type N = new MessageDeliveryType_Type(_N);
    public static final MessageDeliveryType_Type S = new MessageDeliveryType_Type(_S);
    public static final MessageDeliveryType_Type P = new MessageDeliveryType_Type(_P);
    public static final MessageDeliveryType_Type I = new MessageDeliveryType_Type(_I);
    public java.lang.String getValue() { return _value_;}
    public static MessageDeliveryType_Type fromValue(java.lang.String value)
          throws java.lang.IllegalArgumentException {
        MessageDeliveryType_Type enumeration = (MessageDeliveryType_Type)
            _table_.get(value);
        if (enumeration==null) throw new java.lang.IllegalArgumentException();
        return enumeration;
    }
    public static MessageDeliveryType_Type fromString(java.lang.String value)
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
        new org.apache.axis.description.TypeDesc(MessageDeliveryType_Type.class);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "MessageDeliveryType_Type"));
    }
    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

}
