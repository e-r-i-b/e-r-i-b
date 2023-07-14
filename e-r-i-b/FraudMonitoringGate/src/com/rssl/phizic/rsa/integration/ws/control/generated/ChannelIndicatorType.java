/**
 * ChannelIndicatorType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.rsa.integration.ws.control.generated;

public class ChannelIndicatorType implements java.io.Serializable {
    private java.lang.String _value_;
    private static java.util.HashMap _table_ = new java.util.HashMap();

    // Constructor
    protected ChannelIndicatorType(java.lang.String value) {
        _value_ = value;
        _table_.put(_value_,this);
    }

    public static final java.lang.String _WEB = "WEB";
    public static final java.lang.String _IVR = "IVR";
    public static final java.lang.String _CALL_CENTER = "CALL_CENTER";
    public static final java.lang.String _BRANCH = "BRANCH";
    public static final java.lang.String _ATM = "ATM";
    public static final java.lang.String _MOBILE = "MOBILE";
    public static final java.lang.String _OTHER = "OTHER";
    public static final ChannelIndicatorType WEB = new ChannelIndicatorType(_WEB);
    public static final ChannelIndicatorType IVR = new ChannelIndicatorType(_IVR);
    public static final ChannelIndicatorType CALL_CENTER = new ChannelIndicatorType(_CALL_CENTER);
    public static final ChannelIndicatorType BRANCH = new ChannelIndicatorType(_BRANCH);
    public static final ChannelIndicatorType ATM = new ChannelIndicatorType(_ATM);
    public static final ChannelIndicatorType MOBILE = new ChannelIndicatorType(_MOBILE);
    public static final ChannelIndicatorType OTHER = new ChannelIndicatorType(_OTHER);
    public java.lang.String getValue() { return _value_;}
    public static ChannelIndicatorType fromValue(java.lang.String value)
          throws java.lang.IllegalArgumentException {
        ChannelIndicatorType enumeration = (ChannelIndicatorType)
            _table_.get(value);
        if (enumeration==null) throw new java.lang.IllegalArgumentException();
        return enumeration;
    }
    public static ChannelIndicatorType fromString(java.lang.String value)
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
        new org.apache.axis.description.TypeDesc(ChannelIndicatorType.class);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "ChannelIndicatorType"));
    }
    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

}
