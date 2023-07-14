/**
 * AcctType_Type.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.test.webgate.esberib.generated;

public class AcctType_Type implements java.io.Serializable {
    private java.lang.String _value_;
    private static java.util.HashMap _table_ = new java.util.HashMap();

    // Constructor
    protected AcctType_Type(java.lang.String value) {
        _value_ = value;
        _table_.put(_value_,this);
    }

    public static final java.lang.String _Deposit = "Deposit";
    public static final java.lang.String _IMA = "IMA";
    public static final java.lang.String _Card = "Card";
    public static final java.lang.String _Credit = "Credit";
    public static final java.lang.String _LongOrd = "LongOrd";
    public static final java.lang.String _DepoAcc = "DepoAcc";
    public static final java.lang.String _CardWay = "CardWay";
    public static final java.lang.String _Securities = "Securities";
    public static final AcctType_Type Deposit = new AcctType_Type(_Deposit);
    public static final AcctType_Type IMA = new AcctType_Type(_IMA);
    public static final AcctType_Type Card = new AcctType_Type(_Card);
    public static final AcctType_Type Credit = new AcctType_Type(_Credit);
    public static final AcctType_Type LongOrd = new AcctType_Type(_LongOrd);
    public static final AcctType_Type DepoAcc = new AcctType_Type(_DepoAcc);
    public static final AcctType_Type CardWay = new AcctType_Type(_CardWay);
    public static final AcctType_Type Securities = new AcctType_Type(_Securities);
    public java.lang.String getValue() { return _value_;}
    public static AcctType_Type fromValue(java.lang.String value)
          throws java.lang.IllegalArgumentException {
        AcctType_Type enumeration = (AcctType_Type)
            _table_.get(value);
        if (enumeration==null) throw new java.lang.IllegalArgumentException();
        return enumeration;
    }
    public static AcctType_Type fromString(java.lang.String value)
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
        new org.apache.axis.description.TypeDesc(AcctType_Type.class);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "AcctType_Type"));
    }
    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

}
