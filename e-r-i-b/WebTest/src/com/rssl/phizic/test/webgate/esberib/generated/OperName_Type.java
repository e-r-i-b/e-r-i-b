/**
 * OperName_Type.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.test.webgate.esberib.generated;

public class OperName_Type implements java.io.Serializable {
    private java.lang.String _value_;
    private static java.util.HashMap _table_ = new java.util.HashMap();

    // Constructor
    protected OperName_Type(java.lang.String value) {
        _value_ = value;
        _table_.put(_value_,this);
    }

    public static final java.lang.String _TCC = "TCC";
    public static final java.lang.String _TCD = "TCD";
    public static final java.lang.String _TDC = "TDC";
    public static final java.lang.String _TDD = "TDD";
    public static final java.lang.String _TCP = "TCP";
    public static final java.lang.String _SDP = "SDP";
    public static final java.lang.String _CreateForm190ForLoanRequest = "CreateForm190ForLoanRequest";
    public static final java.lang.String _TDDC = "TDDC";
    public static final java.lang.String _TDCC = "TDCC";
    public static final java.lang.String _TDI = "TDI";
    public static final java.lang.String _TID = "TID";
    public static final java.lang.String _TDIO = "TDIO";
    public static final java.lang.String _TCIO = "TCIO";
    public static final java.lang.String _TCI = "TCI";
    public static final java.lang.String _TIC = "TIC";
    public static final java.lang.String _TDDO = "TDDO";
    public static final java.lang.String _TCDO = "TCDO";
    public static final java.lang.String _SrvChangeAccountInfo = "SrvChangeAccountInfo";
    public static final OperName_Type TCC = new OperName_Type(_TCC);
    public static final OperName_Type TCD = new OperName_Type(_TCD);
    public static final OperName_Type TDC = new OperName_Type(_TDC);
    public static final OperName_Type TDD = new OperName_Type(_TDD);
    public static final OperName_Type TCP = new OperName_Type(_TCP);
    public static final OperName_Type SDP = new OperName_Type(_SDP);
    public static final OperName_Type CreateForm190ForLoanRequest = new OperName_Type(_CreateForm190ForLoanRequest);
    public static final OperName_Type TDDC = new OperName_Type(_TDDC);
    public static final OperName_Type TDCC = new OperName_Type(_TDCC);
    public static final OperName_Type TDI = new OperName_Type(_TDI);
    public static final OperName_Type TID = new OperName_Type(_TID);
    public static final OperName_Type TDIO = new OperName_Type(_TDIO);
    public static final OperName_Type TCIO = new OperName_Type(_TCIO);
    public static final OperName_Type TCI = new OperName_Type(_TCI);
    public static final OperName_Type TIC = new OperName_Type(_TIC);
    public static final OperName_Type TDDO = new OperName_Type(_TDDO);
    public static final OperName_Type TCDO = new OperName_Type(_TCDO);
    public static final OperName_Type SrvChangeAccountInfo = new OperName_Type(_SrvChangeAccountInfo);
    public java.lang.String getValue() { return _value_;}
    public static OperName_Type fromValue(java.lang.String value)
          throws java.lang.IllegalArgumentException {
        OperName_Type enumeration = (OperName_Type)
            _table_.get(value);
        if (enumeration==null) throw new java.lang.IllegalArgumentException();
        return enumeration;
    }
    public static OperName_Type fromString(java.lang.String value)
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
        new org.apache.axis.description.TypeDesc(OperName_Type.class);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "OperName_Type"));
    }
    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

}
