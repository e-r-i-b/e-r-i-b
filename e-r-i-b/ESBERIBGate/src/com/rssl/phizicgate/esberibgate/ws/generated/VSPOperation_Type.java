/**
 * VSPOperation_Type.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizicgate.esberibgate.ws.generated;


/**
 * Реквизиты операции в ВСП
 */
public class VSPOperation_Type  implements java.io.Serializable {
    /* ФИО клиента */
    private java.lang.String clientName;

    /* Номер счета */
    private java.lang.String clientAccount;

    /* Признак приходной или расходной операции */
    private boolean isDebit;

    /* Сумма операции в валюте  счета */
    private java.math.BigDecimal amount;

    /* Дата совершения операции */
    private java.lang.String payDate;

    /* Код авторизации */
    private long authorizationCode;

    public VSPOperation_Type() {
    }

    public VSPOperation_Type(
           java.lang.String clientName,
           java.lang.String clientAccount,
           boolean isDebit,
           java.math.BigDecimal amount,
           java.lang.String payDate,
           long authorizationCode) {
           this.clientName = clientName;
           this.clientAccount = clientAccount;
           this.isDebit = isDebit;
           this.amount = amount;
           this.payDate = payDate;
           this.authorizationCode = authorizationCode;
    }


    /**
     * Gets the clientName value for this VSPOperation_Type.
     * 
     * @return clientName   * ФИО клиента
     */
    public java.lang.String getClientName() {
        return clientName;
    }


    /**
     * Sets the clientName value for this VSPOperation_Type.
     * 
     * @param clientName   * ФИО клиента
     */
    public void setClientName(java.lang.String clientName) {
        this.clientName = clientName;
    }


    /**
     * Gets the clientAccount value for this VSPOperation_Type.
     * 
     * @return clientAccount   * Номер счета
     */
    public java.lang.String getClientAccount() {
        return clientAccount;
    }


    /**
     * Sets the clientAccount value for this VSPOperation_Type.
     * 
     * @param clientAccount   * Номер счета
     */
    public void setClientAccount(java.lang.String clientAccount) {
        this.clientAccount = clientAccount;
    }


    /**
     * Gets the isDebit value for this VSPOperation_Type.
     * 
     * @return isDebit   * Признак приходной или расходной операции
     */
    public boolean isIsDebit() {
        return isDebit;
    }


    /**
     * Sets the isDebit value for this VSPOperation_Type.
     * 
     * @param isDebit   * Признак приходной или расходной операции
     */
    public void setIsDebit(boolean isDebit) {
        this.isDebit = isDebit;
    }


    /**
     * Gets the amount value for this VSPOperation_Type.
     * 
     * @return amount   * Сумма операции в валюте  счета
     */
    public java.math.BigDecimal getAmount() {
        return amount;
    }


    /**
     * Sets the amount value for this VSPOperation_Type.
     * 
     * @param amount   * Сумма операции в валюте  счета
     */
    public void setAmount(java.math.BigDecimal amount) {
        this.amount = amount;
    }


    /**
     * Gets the payDate value for this VSPOperation_Type.
     * 
     * @return payDate   * Дата совершения операции
     */
    public java.lang.String getPayDate() {
        return payDate;
    }


    /**
     * Sets the payDate value for this VSPOperation_Type.
     * 
     * @param payDate   * Дата совершения операции
     */
    public void setPayDate(java.lang.String payDate) {
        this.payDate = payDate;
    }


    /**
     * Gets the authorizationCode value for this VSPOperation_Type.
     * 
     * @return authorizationCode   * Код авторизации
     */
    public long getAuthorizationCode() {
        return authorizationCode;
    }


    /**
     * Sets the authorizationCode value for this VSPOperation_Type.
     * 
     * @param authorizationCode   * Код авторизации
     */
    public void setAuthorizationCode(long authorizationCode) {
        this.authorizationCode = authorizationCode;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof VSPOperation_Type)) return false;
        VSPOperation_Type other = (VSPOperation_Type) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.clientName==null && other.getClientName()==null) || 
             (this.clientName!=null &&
              this.clientName.equals(other.getClientName()))) &&
            ((this.clientAccount==null && other.getClientAccount()==null) || 
             (this.clientAccount!=null &&
              this.clientAccount.equals(other.getClientAccount()))) &&
            this.isDebit == other.isIsDebit() &&
            ((this.amount==null && other.getAmount()==null) || 
             (this.amount!=null &&
              this.amount.equals(other.getAmount()))) &&
            ((this.payDate==null && other.getPayDate()==null) || 
             (this.payDate!=null &&
              this.payDate.equals(other.getPayDate()))) &&
            this.authorizationCode == other.getAuthorizationCode();
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = 1;
        if (getClientName() != null) {
            _hashCode += getClientName().hashCode();
        }
        if (getClientAccount() != null) {
            _hashCode += getClientAccount().hashCode();
        }
        _hashCode += (isIsDebit() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        if (getAmount() != null) {
            _hashCode += getAmount().hashCode();
        }
        if (getPayDate() != null) {
            _hashCode += getPayDate().hashCode();
        }
        _hashCode += new Long(getAuthorizationCode()).hashCode();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(VSPOperation_Type.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "VSPOperation_Type"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("clientName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "ClientName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "C"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("clientAccount");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "ClientAccount"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "String"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("isDebit");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "IsDebit"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("amount");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Amount"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("payDate");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "PayDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("authorizationCode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "AuthorizationCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
    }

    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

    /**
     * Get Custom Serializer
     */
    public static org.apache.axis.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanSerializer(
            _javaType, _xmlType, typeDesc);
    }

    /**
     * Get Custom Deserializer
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanDeserializer(
            _javaType, _xmlType, typeDesc);
    }

}
