/**
 * RequestType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.rsa.integration.ws.control.generated;

public class RequestType implements java.io.Serializable {
    private java.lang.String _value_;
    private static java.util.HashMap _table_ = new java.util.HashMap();

    // Constructor
    protected RequestType(java.lang.String value) {
        _value_ = value;
        _table_.put(_value_,this);
    }

    public static final java.lang.String _ANALYZE = "ANALYZE";
    public static final java.lang.String _AUTHENTICATE = "AUTHENTICATE";
    public static final java.lang.String _CHALLENGE = "CHALLENGE";
    public static final java.lang.String _CREATEUSER = "CREATEUSER";
    public static final java.lang.String _NOTIFY = "NOTIFY";
    public static final java.lang.String _QUERY = "QUERY";
    public static final java.lang.String _QUERYAUTHSTATUS = "QUERYAUTHSTATUS";
    public static final java.lang.String _UPDATEUSER = "UPDATEUSER";
    public static final RequestType ANALYZE = new RequestType(_ANALYZE);
    public static final RequestType AUTHENTICATE = new RequestType(_AUTHENTICATE);
    public static final RequestType CHALLENGE = new RequestType(_CHALLENGE);
    public static final RequestType CREATEUSER = new RequestType(_CREATEUSER);
    public static final RequestType NOTIFY = new RequestType(_NOTIFY);
    public static final RequestType QUERY = new RequestType(_QUERY);
    public static final RequestType QUERYAUTHSTATUS = new RequestType(_QUERYAUTHSTATUS);
    public static final RequestType UPDATEUSER = new RequestType(_UPDATEUSER);
    public java.lang.String getValue() { return _value_;}
    public static RequestType fromValue(java.lang.String value)
          throws java.lang.IllegalArgumentException {
        RequestType enumeration = (RequestType)
            _table_.get(value);
        if (enumeration==null) throw new java.lang.IllegalArgumentException();
        return enumeration;
    }
    public static RequestType fromString(java.lang.String value)
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
        new org.apache.axis.description.TypeDesc(RequestType.class);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "RequestType"));
    }
    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

}
