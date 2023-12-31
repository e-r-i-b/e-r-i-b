/**
 * AccountOwnershipType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated;

public class AccountOwnershipType implements java.io.Serializable {
    private java.lang.String _value_;
    private static java.util.HashMap _table_ = new java.util.HashMap();

    // Constructor
    protected AccountOwnershipType(java.lang.String value) {
        _value_ = value;
        _table_.put(_value_,this);
    }

    public static final java.lang.String _INDIVIDUAL = "INDIVIDUAL";
    public static final java.lang.String _JOINT = "JOINT";
    public static final java.lang.String _TRUST = "TRUST";
    public static final java.lang.String _CUSTODIAL = "CUSTODIAL";
    public static final java.lang.String _BUSINESS = "BUSINESS";
    public static final AccountOwnershipType INDIVIDUAL = new AccountOwnershipType(_INDIVIDUAL);
    public static final AccountOwnershipType JOINT = new AccountOwnershipType(_JOINT);
    public static final AccountOwnershipType TRUST = new AccountOwnershipType(_TRUST);
    public static final AccountOwnershipType CUSTODIAL = new AccountOwnershipType(_CUSTODIAL);
    public static final AccountOwnershipType BUSINESS = new AccountOwnershipType(_BUSINESS);
    public java.lang.String getValue() { return _value_;}
    public static AccountOwnershipType fromValue(java.lang.String value)
          throws java.lang.IllegalArgumentException {
        AccountOwnershipType enumeration = (AccountOwnershipType)
            _table_.get(value);
        if (enumeration==null) throw new java.lang.IllegalArgumentException();
        return enumeration;
    }
    public static AccountOwnershipType fromString(java.lang.String value)
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
        new org.apache.axis.description.TypeDesc(AccountOwnershipType.class);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "AccountOwnershipType"));
    }
    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

}
