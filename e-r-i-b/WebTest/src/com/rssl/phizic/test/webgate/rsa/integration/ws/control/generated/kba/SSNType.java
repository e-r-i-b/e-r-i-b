/**
 * SSNType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.kba;

public class SSNType implements java.io.Serializable {
    private java.lang.String _value_;
    private static java.util.HashMap _table_ = new java.util.HashMap();

    // Constructor
    protected SSNType(java.lang.String value) {
        _value_ = value;
        _table_.put(_value_,this);
    }

    public static final java.lang.String _SSN4 = "SSN4";
    public static final java.lang.String _SSN9 = "SSN9";
    public static final java.lang.String _SSN5 = "SSN5";
    public static final java.lang.String _OTHER = "OTHER";
    public static final java.lang.String _NOSSN = "NOSSN";
    public static final SSNType SSN4 = new SSNType(_SSN4);
    public static final SSNType SSN9 = new SSNType(_SSN9);
    public static final SSNType SSN5 = new SSNType(_SSN5);
    public static final SSNType OTHER = new SSNType(_OTHER);
    public static final SSNType NOSSN = new SSNType(_NOSSN);
    public java.lang.String getValue() { return _value_;}
    public static SSNType fromValue(java.lang.String value)
          throws java.lang.IllegalArgumentException {
        SSNType enumeration = (SSNType)
            _table_.get(value);
        if (enumeration==null) throw new java.lang.IllegalArgumentException();
        return enumeration;
    }
    public static SSNType fromString(java.lang.String value)
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
        new org.apache.axis.description.TypeDesc(SSNType.class);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.kba.csd.rsa.com", "SSNType"));
    }
    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

}
