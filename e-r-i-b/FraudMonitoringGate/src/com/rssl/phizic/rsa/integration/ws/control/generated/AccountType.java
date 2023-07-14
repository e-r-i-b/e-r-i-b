/**
 * AccountType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.rsa.integration.ws.control.generated;

public class AccountType implements java.io.Serializable {
    private java.lang.String _value_;
    private static java.util.HashMap _table_ = new java.util.HashMap();

    // Constructor
    protected AccountType(java.lang.String value) {
        _value_ = value;
        _table_.put(_value_,this);
    }

    public static final java.lang.String _CREDIT_CARD = "CREDIT_CARD";
    public static final java.lang.String _DEBIT_CARD = "DEBIT_CARD";
    public static final java.lang.String _CHECKING = "CHECKING";
    public static final java.lang.String _CHECKING_WITH_OVERDRAFT = "CHECKING_WITH_OVERDRAFT";
    public static final java.lang.String _BROKERAGE = "BROKERAGE";
    public static final java.lang.String _SAVINGS = "SAVINGS";
    public static final java.lang.String _CD = "CD";
    public static final java.lang.String _LINE_OF_CREDIT = "LINE_OF_CREDIT";
    public static final java.lang.String _RETIREMENT = "RETIREMENT";
    public static final java.lang.String _MORTGAGE = "MORTGAGE";
    public static final java.lang.String _USER_DEFINED = "USER_DEFINED";
    public static final AccountType CREDIT_CARD = new AccountType(_CREDIT_CARD);
    public static final AccountType DEBIT_CARD = new AccountType(_DEBIT_CARD);
    public static final AccountType CHECKING = new AccountType(_CHECKING);
    public static final AccountType CHECKING_WITH_OVERDRAFT = new AccountType(_CHECKING_WITH_OVERDRAFT);
    public static final AccountType BROKERAGE = new AccountType(_BROKERAGE);
    public static final AccountType SAVINGS = new AccountType(_SAVINGS);
    public static final AccountType CD = new AccountType(_CD);
    public static final AccountType LINE_OF_CREDIT = new AccountType(_LINE_OF_CREDIT);
    public static final AccountType RETIREMENT = new AccountType(_RETIREMENT);
    public static final AccountType MORTGAGE = new AccountType(_MORTGAGE);
    public static final AccountType USER_DEFINED = new AccountType(_USER_DEFINED);
    public java.lang.String getValue() { return _value_;}
    public static AccountType fromValue(java.lang.String value)
          throws java.lang.IllegalArgumentException {
        AccountType enumeration = (AccountType)
            _table_.get(value);
        if (enumeration==null) throw new java.lang.IllegalArgumentException();
        return enumeration;
    }
    public static AccountType fromString(java.lang.String value)
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
        new org.apache.axis.description.TypeDesc(AccountType.class);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "AccountType"));
    }
    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

}
