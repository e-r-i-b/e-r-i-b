/**
 * State_Type.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizicgate.esberibgate.ws.generated;

public class State_Type implements java.io.Serializable {
    private java.lang.String _value_;
    private static java.util.HashMap _table_ = new java.util.HashMap();

    // Constructor
    protected State_Type(java.lang.String value) {
        _value_ = value;
        _table_.put(_value_,this);
    }

    public static final java.lang.String _NO_CONTRACT = "NO_CONTRACT";
    public static final java.lang.String _ACTIVE_CONTRACT = "ACTIVE_CONTRACT";
    public static final java.lang.String _CLOSED_CONTRACT = "CLOSED_CONTRACT";
    public static final java.lang.String _NOT_SIGNED = "NOT_SIGNED";
    public static final State_Type NO_CONTRACT = new State_Type(_NO_CONTRACT);
    public static final State_Type ACTIVE_CONTRACT = new State_Type(_ACTIVE_CONTRACT);
    public static final State_Type CLOSED_CONTRACT = new State_Type(_CLOSED_CONTRACT);
    public static final State_Type NOT_SIGNED = new State_Type(_NOT_SIGNED);
    public java.lang.String getValue() { return _value_;}
    public static State_Type fromValue(java.lang.String value)
          throws java.lang.IllegalArgumentException {
        State_Type enumeration = (State_Type)
            _table_.get(value);
        if (enumeration==null) throw new java.lang.IllegalArgumentException();
        return enumeration;
    }
    public static State_Type fromString(java.lang.String value)
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
        new org.apache.axis.description.TypeDesc(State_Type.class);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "State_Type"));
    }
    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

}
