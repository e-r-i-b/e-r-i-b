/**
 * StatusCodeType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizicgate.uec.generated;

public class StatusCodeType implements java.io.Serializable {
    private long _value_;
    private static java.util.HashMap _table_ = new java.util.HashMap();

    // Constructor
    protected StatusCodeType(long value) {
        _value_ = value;
        _table_.put(new java.lang.Long(_value_),this);
    }

    public static final long _value1 = 0L;
    public static final long _value2 = 40004L;
    public static final long _value3 = 40005L;
    public static final long _value4 = 40011L;
    public static final long _value5 = 40014L;
    public static final long _value6 = 40017L;
    public static final StatusCodeType value1 = new StatusCodeType(_value1);
    public static final StatusCodeType value2 = new StatusCodeType(_value2);
    public static final StatusCodeType value3 = new StatusCodeType(_value3);
    public static final StatusCodeType value4 = new StatusCodeType(_value4);
    public static final StatusCodeType value5 = new StatusCodeType(_value5);
    public static final StatusCodeType value6 = new StatusCodeType(_value6);
    public long getValue() { return _value_;}
    public static StatusCodeType fromValue(long value)
          throws java.lang.IllegalArgumentException {
        StatusCodeType enumeration = (StatusCodeType)
            _table_.get(new java.lang.Long(value));
        if (enumeration==null) throw new java.lang.IllegalArgumentException();
        return enumeration;
    }
    public static StatusCodeType fromString(java.lang.String value)
          throws java.lang.IllegalArgumentException {
        try {
            return fromValue(java.lang.Long.parseLong(value));
        } catch (Exception e) {
            throw new java.lang.IllegalArgumentException();
        }
    }
    public boolean equals(java.lang.Object obj) {return (obj == this);}
    public int hashCode() { return toString().hashCode();}
    public java.lang.String toString() { return java.lang.String.valueOf(_value_);}
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
        new org.apache.axis.description.TypeDesc(StatusCodeType.class);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/e-invoicing/uec/1", "StatusCodeType"));
    }
    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

}
