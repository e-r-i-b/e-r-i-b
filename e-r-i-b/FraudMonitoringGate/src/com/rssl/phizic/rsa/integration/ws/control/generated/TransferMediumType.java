/**
 * TransferMediumType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.rsa.integration.ws.control.generated;

public class TransferMediumType implements java.io.Serializable {
    private java.lang.String _value_;
    private static java.util.HashMap _table_ = new java.util.HashMap();

    // Constructor
    protected TransferMediumType(java.lang.String value) {
        _value_ = value;
        _table_.put(_value_,this);
    }

    public static final java.lang.String _INTERNAL = "INTERNAL";
    public static final java.lang.String _BILLPAY_MAIL = "BILLPAY_MAIL";
    public static final java.lang.String _BILLPAY_ELEC = "BILLPAY_ELEC";
    public static final java.lang.String _BALANCE_TRANSFER = "BALANCE_TRANSFER";
    public static final java.lang.String _ACH = "ACH";
    public static final java.lang.String _WIRE = "WIRE";
    public static final java.lang.String _INTL_WIRE = "INTL_WIRE";
    public static final java.lang.String _CHECK = "CHECK";
    public static final TransferMediumType INTERNAL = new TransferMediumType(_INTERNAL);
    public static final TransferMediumType BILLPAY_MAIL = new TransferMediumType(_BILLPAY_MAIL);
    public static final TransferMediumType BILLPAY_ELEC = new TransferMediumType(_BILLPAY_ELEC);
    public static final TransferMediumType BALANCE_TRANSFER = new TransferMediumType(_BALANCE_TRANSFER);
    public static final TransferMediumType ACH = new TransferMediumType(_ACH);
    public static final TransferMediumType WIRE = new TransferMediumType(_WIRE);
    public static final TransferMediumType INTL_WIRE = new TransferMediumType(_INTL_WIRE);
    public static final TransferMediumType CHECK = new TransferMediumType(_CHECK);
    public java.lang.String getValue() { return _value_;}
    public static TransferMediumType fromValue(java.lang.String value)
          throws java.lang.IllegalArgumentException {
        TransferMediumType enumeration = (TransferMediumType)
            _table_.get(value);
        if (enumeration==null) throw new java.lang.IllegalArgumentException();
        return enumeration;
    }
    public static TransferMediumType fromString(java.lang.String value)
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
        new org.apache.axis.description.TypeDesc(TransferMediumType.class);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "TransferMediumType"));
    }
    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

}
