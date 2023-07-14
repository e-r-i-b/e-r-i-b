/**
 * TermType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.rsa.integration.ws.control.generated;

public class TermType implements java.io.Serializable {
    private java.lang.String _value_;
    private static java.util.HashMap _table_ = new java.util.HashMap();

    // Constructor
    protected TermType(java.lang.String value) {
        _value_ = value;
        _table_.put(_value_,this);
    }

    public static final java.lang.String _GOOD_FOR_DAY = "GOOD_FOR_DAY";
    public static final java.lang.String _GOOD_UNTIL_CANCELLED = "GOOD_UNTIL_CANCELLED";
    public static final java.lang.String _IMMEDIATE_OR_CANCEL = "IMMEDIATE_OR_CANCEL";
    public static final java.lang.String _FILL_OR_KILL = "FILL_OR_KILL";
    public static final TermType GOOD_FOR_DAY = new TermType(_GOOD_FOR_DAY);
    public static final TermType GOOD_UNTIL_CANCELLED = new TermType(_GOOD_UNTIL_CANCELLED);
    public static final TermType IMMEDIATE_OR_CANCEL = new TermType(_IMMEDIATE_OR_CANCEL);
    public static final TermType FILL_OR_KILL = new TermType(_FILL_OR_KILL);
    public java.lang.String getValue() { return _value_;}
    public static TermType fromValue(java.lang.String value)
          throws java.lang.IllegalArgumentException {
        TermType enumeration = (TermType)
            _table_.get(value);
        if (enumeration==null) throw new java.lang.IllegalArgumentException();
        return enumeration;
    }
    public static TermType fromString(java.lang.String value)
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
        new org.apache.axis.description.TypeDesc(TermType.class);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "TermType"));
    }
    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

}
