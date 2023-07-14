/**
 * SummaKindCodeASAP_Type.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizicgate.esberibgate.ws.generated;

public class SummaKindCodeASAP_Type implements java.io.Serializable {
    private java.lang.String _value_;
    private static java.util.HashMap _table_ = new java.util.HashMap();

    // Constructor
    protected SummaKindCodeASAP_Type(java.lang.String value) {
        _value_ = value;
        _table_.put(_value_,this);
    }

    public static final java.lang.String _FIXED_SUMMA_IN_RECIP_CURR = "FIXED_SUMMA_IN_RECIP_CURR";
    public static final java.lang.String _BY_BILLING = "BY_BILLING";
    public static final java.lang.String _PERCENT_BY_ANY_RECEIPT = "PERCENT_BY_ANY_RECEIPT";
    public static final java.lang.String _PERCENT_BY_DEBIT = "PERCENT_BY_DEBIT";
    public static final java.lang.String _RUR_SUMMA = "RUR_SUMMA";
    public static final java.lang.String _FIXED_SUMMA = "FIXED_SUMMA";
    public static final java.lang.String _BY_BILLING_BASKET = "BY_BILLING_BASKET";
    public static final SummaKindCodeASAP_Type FIXED_SUMMA_IN_RECIP_CURR = new SummaKindCodeASAP_Type(_FIXED_SUMMA_IN_RECIP_CURR);
    public static final SummaKindCodeASAP_Type BY_BILLING = new SummaKindCodeASAP_Type(_BY_BILLING);
    public static final SummaKindCodeASAP_Type PERCENT_BY_ANY_RECEIPT = new SummaKindCodeASAP_Type(_PERCENT_BY_ANY_RECEIPT);
    public static final SummaKindCodeASAP_Type PERCENT_BY_DEBIT = new SummaKindCodeASAP_Type(_PERCENT_BY_DEBIT);
    public static final SummaKindCodeASAP_Type RUR_SUMMA = new SummaKindCodeASAP_Type(_RUR_SUMMA);
    public static final SummaKindCodeASAP_Type FIXED_SUMMA = new SummaKindCodeASAP_Type(_FIXED_SUMMA);
    public static final SummaKindCodeASAP_Type BY_BILLING_BASKET = new SummaKindCodeASAP_Type(_BY_BILLING_BASKET);
    public java.lang.String getValue() { return _value_;}
    public static SummaKindCodeASAP_Type fromValue(java.lang.String value)
          throws java.lang.IllegalArgumentException {
        SummaKindCodeASAP_Type enumeration = (SummaKindCodeASAP_Type)
            _table_.get(value);
        if (enumeration==null) throw new java.lang.IllegalArgumentException();
        return enumeration;
    }
    public static SummaKindCodeASAP_Type fromString(java.lang.String value)
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
        new org.apache.axis.description.TypeDesc(SummaKindCodeASAP_Type.class);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "SummaKindCodeASAP_Type"));
    }
    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

}
