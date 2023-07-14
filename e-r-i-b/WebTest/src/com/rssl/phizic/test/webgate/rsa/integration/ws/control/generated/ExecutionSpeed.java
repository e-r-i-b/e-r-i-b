/**
 * ExecutionSpeed.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated;

public class ExecutionSpeed implements java.io.Serializable {
    private java.lang.String _value_;
    private static java.util.HashMap _table_ = new java.util.HashMap();

    // Constructor
    protected ExecutionSpeed(java.lang.String value) {
        _value_ = value;
        _table_.put(_value_,this);
    }

    public static final java.lang.String _SEVERAL_DAYS = "SEVERAL_DAYS";
    public static final java.lang.String _OVER_NIGHT = "OVER_NIGHT";
    public static final java.lang.String _FEW_HOURS = "FEW_HOURS";
    public static final java.lang.String _REAL_TIME = "REAL_TIME";
    public static final ExecutionSpeed SEVERAL_DAYS = new ExecutionSpeed(_SEVERAL_DAYS);
    public static final ExecutionSpeed OVER_NIGHT = new ExecutionSpeed(_OVER_NIGHT);
    public static final ExecutionSpeed FEW_HOURS = new ExecutionSpeed(_FEW_HOURS);
    public static final ExecutionSpeed REAL_TIME = new ExecutionSpeed(_REAL_TIME);
    public java.lang.String getValue() { return _value_;}
    public static ExecutionSpeed fromValue(java.lang.String value)
          throws java.lang.IllegalArgumentException {
        ExecutionSpeed enumeration = (ExecutionSpeed)
            _table_.get(value);
        if (enumeration==null) throw new java.lang.IllegalArgumentException();
        return enumeration;
    }
    public static ExecutionSpeed fromString(java.lang.String value)
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
        new org.apache.axis.description.TypeDesc(ExecutionSpeed.class);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "ExecutionSpeed"));
    }
    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

}
