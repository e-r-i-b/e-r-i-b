/**
 * PaymentDetails_Type.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.test.webgate.esberib.generated;


/**
 * Платежные реквизиты
 */
public class PaymentDetails_Type  implements java.io.Serializable {
    /* БИК */
    private java.lang.String BIC;

    /* Номер кор. счета */
    private java.lang.String corrAcctNum;

    /* Номер счета */
    private java.lang.String acctNum;

    /* Номер счета для МФР */
    private java.lang.String interfilialAcctNum;

    public PaymentDetails_Type() {
    }

    public PaymentDetails_Type(
           java.lang.String BIC,
           java.lang.String corrAcctNum,
           java.lang.String acctNum,
           java.lang.String interfilialAcctNum) {
           this.BIC = BIC;
           this.corrAcctNum = corrAcctNum;
           this.acctNum = acctNum;
           this.interfilialAcctNum = interfilialAcctNum;
    }


    /**
     * Gets the BIC value for this PaymentDetails_Type.
     * 
     * @return BIC   * БИК
     */
    public java.lang.String getBIC() {
        return BIC;
    }


    /**
     * Sets the BIC value for this PaymentDetails_Type.
     * 
     * @param BIC   * БИК
     */
    public void setBIC(java.lang.String BIC) {
        this.BIC = BIC;
    }


    /**
     * Gets the corrAcctNum value for this PaymentDetails_Type.
     * 
     * @return corrAcctNum   * Номер кор. счета
     */
    public java.lang.String getCorrAcctNum() {
        return corrAcctNum;
    }


    /**
     * Sets the corrAcctNum value for this PaymentDetails_Type.
     * 
     * @param corrAcctNum   * Номер кор. счета
     */
    public void setCorrAcctNum(java.lang.String corrAcctNum) {
        this.corrAcctNum = corrAcctNum;
    }


    /**
     * Gets the acctNum value for this PaymentDetails_Type.
     * 
     * @return acctNum   * Номер счета
     */
    public java.lang.String getAcctNum() {
        return acctNum;
    }


    /**
     * Sets the acctNum value for this PaymentDetails_Type.
     * 
     * @param acctNum   * Номер счета
     */
    public void setAcctNum(java.lang.String acctNum) {
        this.acctNum = acctNum;
    }


    /**
     * Gets the interfilialAcctNum value for this PaymentDetails_Type.
     * 
     * @return interfilialAcctNum   * Номер счета для МФР
     */
    public java.lang.String getInterfilialAcctNum() {
        return interfilialAcctNum;
    }


    /**
     * Sets the interfilialAcctNum value for this PaymentDetails_Type.
     * 
     * @param interfilialAcctNum   * Номер счета для МФР
     */
    public void setInterfilialAcctNum(java.lang.String interfilialAcctNum) {
        this.interfilialAcctNum = interfilialAcctNum;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof PaymentDetails_Type)) return false;
        PaymentDetails_Type other = (PaymentDetails_Type) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.BIC==null && other.getBIC()==null) || 
             (this.BIC!=null &&
              this.BIC.equals(other.getBIC()))) &&
            ((this.corrAcctNum==null && other.getCorrAcctNum()==null) || 
             (this.corrAcctNum!=null &&
              this.corrAcctNum.equals(other.getCorrAcctNum()))) &&
            ((this.acctNum==null && other.getAcctNum()==null) || 
             (this.acctNum!=null &&
              this.acctNum.equals(other.getAcctNum()))) &&
            ((this.interfilialAcctNum==null && other.getInterfilialAcctNum()==null) || 
             (this.interfilialAcctNum!=null &&
              this.interfilialAcctNum.equals(other.getInterfilialAcctNum())));
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
        if (getBIC() != null) {
            _hashCode += getBIC().hashCode();
        }
        if (getCorrAcctNum() != null) {
            _hashCode += getCorrAcctNum().hashCode();
        }
        if (getAcctNum() != null) {
            _hashCode += getAcctNum().hashCode();
        }
        if (getInterfilialAcctNum() != null) {
            _hashCode += getInterfilialAcctNum().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(PaymentDetails_Type.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "PaymentDetails_Type"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("BIC");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "BIC"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "C"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("corrAcctNum");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "CorrAcctNum"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "NC"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("acctNum");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "AcctNum"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "NC"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("interfilialAcctNum");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "InterfilialAcctNum"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "String"));
        elemField.setMinOccurs(0);
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
