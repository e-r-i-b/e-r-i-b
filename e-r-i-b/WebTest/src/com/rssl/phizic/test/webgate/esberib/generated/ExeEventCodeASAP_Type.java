/**
 * ExeEventCodeASAP_Type.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.test.webgate.esberib.generated;

public class ExeEventCodeASAP_Type implements java.io.Serializable {
    private java.lang.String _value_;
    private static java.util.HashMap _table_ = new java.util.HashMap();

    // Constructor
    protected ExeEventCodeASAP_Type(java.lang.String value) {
        _value_ = value;
        _table_.put(_value_,this);
    }

    public static final java.lang.String _ONCE_IN_WEEK = "ONCE_IN_WEEK";
    public static final java.lang.String _ONCE_IN_MONTH = "ONCE_IN_MONTH";
    public static final java.lang.String _ONCE_IN_QUARTER = "ONCE_IN_QUARTER";
    public static final java.lang.String _ON_REMAIND = "ON_REMAIND";
    public static final java.lang.String _ONCE_IN_YEAR = "ONCE_IN_YEAR";
    public static final java.lang.String _BY_ANY_RECEIPT = "BY_ANY_RECEIPT";
    public static final java.lang.String _BY_DEBIT = "BY_DEBIT";
    public static final ExeEventCodeASAP_Type ONCE_IN_WEEK = new ExeEventCodeASAP_Type(_ONCE_IN_WEEK);
    public static final ExeEventCodeASAP_Type ONCE_IN_MONTH = new ExeEventCodeASAP_Type(_ONCE_IN_MONTH);
    public static final ExeEventCodeASAP_Type ONCE_IN_QUARTER = new ExeEventCodeASAP_Type(_ONCE_IN_QUARTER);
    public static final ExeEventCodeASAP_Type ON_REMAIND = new ExeEventCodeASAP_Type(_ON_REMAIND);
    public static final ExeEventCodeASAP_Type ONCE_IN_YEAR = new ExeEventCodeASAP_Type(_ONCE_IN_YEAR);
    public static final ExeEventCodeASAP_Type BY_ANY_RECEIPT = new ExeEventCodeASAP_Type(_BY_ANY_RECEIPT);
    public static final ExeEventCodeASAP_Type BY_DEBIT = new ExeEventCodeASAP_Type(_BY_DEBIT);
    public java.lang.String getValue() { return _value_;}
    public static ExeEventCodeASAP_Type fromValue(java.lang.String value)
          throws java.lang.IllegalArgumentException {
        ExeEventCodeASAP_Type enumeration = (ExeEventCodeASAP_Type)
            _table_.get(value);
        if (enumeration==null) throw new java.lang.IllegalArgumentException();
        return enumeration;
    }
    public static ExeEventCodeASAP_Type fromString(java.lang.String value)
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
        new org.apache.axis.description.TypeDesc(ExeEventCodeASAP_Type.class);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "ExeEventCodeASAP_Type"));
    }
    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

}
