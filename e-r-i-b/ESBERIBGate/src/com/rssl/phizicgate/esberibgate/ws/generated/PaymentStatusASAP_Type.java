/**
 * PaymentStatusASAP_Type.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizicgate.esberibgate.ws.generated;

public class PaymentStatusASAP_Type implements java.io.Serializable {
    private java.lang.String _value_;
    private static java.util.HashMap _table_ = new java.util.HashMap();

    // Constructor
    protected PaymentStatusASAP_Type(java.lang.String value) {
        _value_ = value;
        _table_.put(_value_,this);
    }

    public static final java.lang.String _New = "New";
    public static final java.lang.String _Canceled = "Canceled";
    public static final java.lang.String _Done = "Done";
    public static final java.lang.String _Reg_Debt = "Reg_Debt";
    public static final java.lang.String _Wait_Approve = "Wait_Approve";
    public static final java.lang.String _In_Exec = "In_Exec";
    public static final PaymentStatusASAP_Type New = new PaymentStatusASAP_Type(_New);
    public static final PaymentStatusASAP_Type Canceled = new PaymentStatusASAP_Type(_Canceled);
    public static final PaymentStatusASAP_Type Done = new PaymentStatusASAP_Type(_Done);
    public static final PaymentStatusASAP_Type Reg_Debt = new PaymentStatusASAP_Type(_Reg_Debt);
    public static final PaymentStatusASAP_Type Wait_Approve = new PaymentStatusASAP_Type(_Wait_Approve);
    public static final PaymentStatusASAP_Type In_Exec = new PaymentStatusASAP_Type(_In_Exec);
    public java.lang.String getValue() { return _value_;}
    public static PaymentStatusASAP_Type fromValue(java.lang.String value)
          throws java.lang.IllegalArgumentException {
        PaymentStatusASAP_Type enumeration = (PaymentStatusASAP_Type)
            _table_.get(value);
        if (enumeration==null) throw new java.lang.IllegalArgumentException();
        return enumeration;
    }
    public static PaymentStatusASAP_Type fromString(java.lang.String value)
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
        new org.apache.axis.description.TypeDesc(PaymentStatusASAP_Type.class);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "PaymentStatusASAP_Type"));
    }
    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

}
