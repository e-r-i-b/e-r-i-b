/**
 * BankAcctStatus_Type.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.test.webgate.esberib.generated;


/**
 * Статус счета
 */
public class BankAcctStatus_Type  implements java.io.Serializable {
    /* Код статуса */
    private java.lang.String bankAcctStatusCode;

    /* Описание статуса */
    private java.lang.String statusDesc;

    public BankAcctStatus_Type() {
    }

    public BankAcctStatus_Type(
           java.lang.String bankAcctStatusCode,
           java.lang.String statusDesc) {
           this.bankAcctStatusCode = bankAcctStatusCode;
           this.statusDesc = statusDesc;
    }


    /**
     * Gets the bankAcctStatusCode value for this BankAcctStatus_Type.
     * 
     * @return bankAcctStatusCode   * Код статуса
     */
    public java.lang.String getBankAcctStatusCode() {
        return bankAcctStatusCode;
    }


    /**
     * Sets the bankAcctStatusCode value for this BankAcctStatus_Type.
     * 
     * @param bankAcctStatusCode   * Код статуса
     */
    public void setBankAcctStatusCode(java.lang.String bankAcctStatusCode) {
        this.bankAcctStatusCode = bankAcctStatusCode;
    }


    /**
     * Gets the statusDesc value for this BankAcctStatus_Type.
     * 
     * @return statusDesc   * Описание статуса
     */
    public java.lang.String getStatusDesc() {
        return statusDesc;
    }


    /**
     * Sets the statusDesc value for this BankAcctStatus_Type.
     * 
     * @param statusDesc   * Описание статуса
     */
    public void setStatusDesc(java.lang.String statusDesc) {
        this.statusDesc = statusDesc;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof BankAcctStatus_Type)) return false;
        BankAcctStatus_Type other = (BankAcctStatus_Type) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.bankAcctStatusCode==null && other.getBankAcctStatusCode()==null) || 
             (this.bankAcctStatusCode!=null &&
              this.bankAcctStatusCode.equals(other.getBankAcctStatusCode()))) &&
            ((this.statusDesc==null && other.getStatusDesc()==null) || 
             (this.statusDesc!=null &&
              this.statusDesc.equals(other.getStatusDesc())));
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
        if (getBankAcctStatusCode() != null) {
            _hashCode += getBankAcctStatusCode().hashCode();
        }
        if (getStatusDesc() != null) {
            _hashCode += getStatusDesc().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(BankAcctStatus_Type.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "BankAcctStatus_Type"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("bankAcctStatusCode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "BankAcctStatusCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "BankAcctStatusCode_Type"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("statusDesc");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "StatusDesc"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "StatusDesc_Type"));
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
