/**
 * AutopayStatus_Type.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizicgate.esberibgate.ws.generated;

public class AutopayStatus_Type implements java.io.Serializable {
    private java.lang.String _value_;
    private static java.util.HashMap _table_ = new java.util.HashMap();

    // Constructor
    protected AutopayStatus_Type(java.lang.String value) {
        _value_ = value;
        _table_.put(_value_,this);
    }

    public static final java.lang.String _New = "New";
    public static final java.lang.String _OnRegistration = "OnRegistration";
    public static final java.lang.String _WaitingForActivation = "WaitingForActivation";
    public static final java.lang.String _Confirmed = "Confirmed";
    public static final java.lang.String _Active = "Active";
    public static final java.lang.String _WaitForAccept = "WaitForAccept";
    public static final java.lang.String _ErrorRegistration = "ErrorRegistration";
    public static final java.lang.String _Paused = "Paused";
    public static final java.lang.String _Closed = "Closed";
    public static final AutopayStatus_Type New = new AutopayStatus_Type(_New);
    public static final AutopayStatus_Type OnRegistration = new AutopayStatus_Type(_OnRegistration);
    public static final AutopayStatus_Type WaitingForActivation = new AutopayStatus_Type(_WaitingForActivation);
    public static final AutopayStatus_Type Confirmed = new AutopayStatus_Type(_Confirmed);
    public static final AutopayStatus_Type Active = new AutopayStatus_Type(_Active);
    public static final AutopayStatus_Type WaitForAccept = new AutopayStatus_Type(_WaitForAccept);
    public static final AutopayStatus_Type ErrorRegistration = new AutopayStatus_Type(_ErrorRegistration);
    public static final AutopayStatus_Type Paused = new AutopayStatus_Type(_Paused);
    public static final AutopayStatus_Type Closed = new AutopayStatus_Type(_Closed);
    public java.lang.String getValue() { return _value_;}
    public static AutopayStatus_Type fromValue(java.lang.String value)
          throws java.lang.IllegalArgumentException {
        AutopayStatus_Type enumeration = (AutopayStatus_Type)
            _table_.get(value);
        if (enumeration==null) throw new java.lang.IllegalArgumentException();
        return enumeration;
    }
    public static AutopayStatus_Type fromString(java.lang.String value)
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
        new org.apache.axis.description.TypeDesc(AutopayStatus_Type.class);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "AutopayStatus_Type"));
    }
    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

}
