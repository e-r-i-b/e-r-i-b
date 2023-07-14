/**
 * Contract_Type.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.test.webgate.esberib.generated;


/**
 * Информация по счетовому контракту в WAY4
 */
public class Contract_Type  implements java.io.Serializable {
    /* Номер счетового контракта */
    private java.lang.String contractNumber;

    /* Дата открытия счетового контракта YYYY-MM-DD */
    private java.lang.String openDate;

    /* Код продукта счетового контракта 
     * CODEWAY4SHORT справочника CARD_MDM */
    private java.lang.String productCode;

    public Contract_Type() {
    }

    public Contract_Type(
           java.lang.String contractNumber,
           java.lang.String openDate,
           java.lang.String productCode) {
           this.contractNumber = contractNumber;
           this.openDate = openDate;
           this.productCode = productCode;
    }


    /**
     * Gets the contractNumber value for this Contract_Type.
     * 
     * @return contractNumber   * Номер счетового контракта
     */
    public java.lang.String getContractNumber() {
        return contractNumber;
    }


    /**
     * Sets the contractNumber value for this Contract_Type.
     * 
     * @param contractNumber   * Номер счетового контракта
     */
    public void setContractNumber(java.lang.String contractNumber) {
        this.contractNumber = contractNumber;
    }


    /**
     * Gets the openDate value for this Contract_Type.
     * 
     * @return openDate   * Дата открытия счетового контракта YYYY-MM-DD
     */
    public java.lang.String getOpenDate() {
        return openDate;
    }


    /**
     * Sets the openDate value for this Contract_Type.
     * 
     * @param openDate   * Дата открытия счетового контракта YYYY-MM-DD
     */
    public void setOpenDate(java.lang.String openDate) {
        this.openDate = openDate;
    }


    /**
     * Gets the productCode value for this Contract_Type.
     * 
     * @return productCode   * Код продукта счетового контракта 
     * CODEWAY4SHORT справочника CARD_MDM
     */
    public java.lang.String getProductCode() {
        return productCode;
    }


    /**
     * Sets the productCode value for this Contract_Type.
     * 
     * @param productCode   * Код продукта счетового контракта 
     * CODEWAY4SHORT справочника CARD_MDM
     */
    public void setProductCode(java.lang.String productCode) {
        this.productCode = productCode;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Contract_Type)) return false;
        Contract_Type other = (Contract_Type) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.contractNumber==null && other.getContractNumber()==null) || 
             (this.contractNumber!=null &&
              this.contractNumber.equals(other.getContractNumber()))) &&
            ((this.openDate==null && other.getOpenDate()==null) || 
             (this.openDate!=null &&
              this.openDate.equals(other.getOpenDate()))) &&
            ((this.productCode==null && other.getProductCode()==null) || 
             (this.productCode!=null &&
              this.productCode.equals(other.getProductCode())));
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
        if (getContractNumber() != null) {
            _hashCode += getContractNumber().hashCode();
        }
        if (getOpenDate() != null) {
            _hashCode += getOpenDate().hashCode();
        }
        if (getProductCode() != null) {
            _hashCode += getProductCode().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Contract_Type.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Contract_Type"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("contractNumber");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "ContractNumber"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("openDate");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "OpenDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("productCode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "ProductCode"));
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
