/**
 * ActionCode.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated;

public class ActionCode implements java.io.Serializable {
    private java.lang.String _value_;
    private static java.util.HashMap _table_ = new java.util.HashMap();

    // Constructor
    protected ActionCode(java.lang.String value) {
        _value_ = value;
        _table_.put(_value_,this);
    }

    public static final java.lang.String _NONE = "NONE";
    public static final java.lang.String _ALLOW = "ALLOW";
    public static final java.lang.String _REVIEW = "REVIEW";
    public static final java.lang.String _DELAY_AND_REVIEW = "DELAY_AND_REVIEW";
    public static final java.lang.String _STOP_AND_REVIEW = "STOP_AND_REVIEW";
    public static final java.lang.String _ELEVATE_SECURITY = "ELEVATE_SECURITY";
    public static final java.lang.String _REDIRECT_CHALLENGE = "REDIRECT_CHALLENGE";
    public static final java.lang.String _REDIRECT_COLLECT = "REDIRECT_COLLECT";
    public static final java.lang.String _CHALLENGE = "CHALLENGE";
    public static final java.lang.String _COLLECT = "COLLECT";
    public static final java.lang.String _DENY = "DENY";
    public static final java.lang.String _BLOCK = "BLOCK";
    public static final java.lang.String _LOCKED = "LOCKED";
    public static final ActionCode NONE = new ActionCode(_NONE);
    public static final ActionCode ALLOW = new ActionCode(_ALLOW);
    public static final ActionCode REVIEW = new ActionCode(_REVIEW);
    public static final ActionCode DELAY_AND_REVIEW = new ActionCode(_DELAY_AND_REVIEW);
    public static final ActionCode STOP_AND_REVIEW = new ActionCode(_STOP_AND_REVIEW);
    public static final ActionCode ELEVATE_SECURITY = new ActionCode(_ELEVATE_SECURITY);
    public static final ActionCode REDIRECT_CHALLENGE = new ActionCode(_REDIRECT_CHALLENGE);
    public static final ActionCode REDIRECT_COLLECT = new ActionCode(_REDIRECT_COLLECT);
    public static final ActionCode CHALLENGE = new ActionCode(_CHALLENGE);
    public static final ActionCode COLLECT = new ActionCode(_COLLECT);
    public static final ActionCode DENY = new ActionCode(_DENY);
    public static final ActionCode BLOCK = new ActionCode(_BLOCK);
    public static final ActionCode LOCKED = new ActionCode(_LOCKED);
    public java.lang.String getValue() { return _value_;}
    public static ActionCode fromValue(java.lang.String value)
          throws java.lang.IllegalArgumentException {
        ActionCode enumeration = (ActionCode)
            _table_.get(value);
        if (enumeration==null) throw new java.lang.IllegalArgumentException();
        return enumeration;
    }
    public static ActionCode fromString(java.lang.String value)
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
        new org.apache.axis.description.TypeDesc(ActionCode.class);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "ActionCode"));
    }
    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

}
