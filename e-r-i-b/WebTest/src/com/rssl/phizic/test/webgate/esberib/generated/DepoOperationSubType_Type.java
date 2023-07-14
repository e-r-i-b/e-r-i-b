/**
 * DepoOperationSubType_Type.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.test.webgate.esberib.generated;

public class DepoOperationSubType_Type implements java.io.Serializable {
    private java.lang.String _value_;
    private static java.util.HashMap _table_ = new java.util.HashMap();

    // Constructor
    protected DepoOperationSubType_Type(java.lang.String value) {
        _value_ = value;
        _table_.put(_value_,this);
    }

    public static final java.lang.String _INTERNAL_TRANSFER = "INTERNAL_TRANSFER";
    public static final java.lang.String _LIST_TRANSFER = "LIST_TRANSFER";
    public static final java.lang.String _EXTERNAL_TRANSFER = "EXTERNAL_TRANSFER";
    public static final java.lang.String _INTERNAL_RECEPTION = "INTERNAL_RECEPTION";
    public static final java.lang.String _LIST_RECEPTION = "LIST_RECEPTION";
    public static final java.lang.String _EXTERNAL_RECEPTION = "EXTERNAL_RECEPTION";
    public static final java.lang.String _INTERNAL_ACCOUNT_TRANSFER = "INTERNAL_ACCOUNT_TRANSFER";
    public static final DepoOperationSubType_Type INTERNAL_TRANSFER = new DepoOperationSubType_Type(_INTERNAL_TRANSFER);
    public static final DepoOperationSubType_Type LIST_TRANSFER = new DepoOperationSubType_Type(_LIST_TRANSFER);
    public static final DepoOperationSubType_Type EXTERNAL_TRANSFER = new DepoOperationSubType_Type(_EXTERNAL_TRANSFER);
    public static final DepoOperationSubType_Type INTERNAL_RECEPTION = new DepoOperationSubType_Type(_INTERNAL_RECEPTION);
    public static final DepoOperationSubType_Type LIST_RECEPTION = new DepoOperationSubType_Type(_LIST_RECEPTION);
    public static final DepoOperationSubType_Type EXTERNAL_RECEPTION = new DepoOperationSubType_Type(_EXTERNAL_RECEPTION);
    public static final DepoOperationSubType_Type INTERNAL_ACCOUNT_TRANSFER = new DepoOperationSubType_Type(_INTERNAL_ACCOUNT_TRANSFER);
    public java.lang.String getValue() { return _value_;}
    public static DepoOperationSubType_Type fromValue(java.lang.String value)
          throws java.lang.IllegalArgumentException {
        DepoOperationSubType_Type enumeration = (DepoOperationSubType_Type)
            _table_.get(value);
        if (enumeration==null) throw new java.lang.IllegalArgumentException();
        return enumeration;
    }
    public static DepoOperationSubType_Type fromString(java.lang.String value)
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
        new org.apache.axis.description.TypeDesc(DepoOperationSubType_Type.class);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "DepoOperationSubType_Type"));
    }
    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

}
