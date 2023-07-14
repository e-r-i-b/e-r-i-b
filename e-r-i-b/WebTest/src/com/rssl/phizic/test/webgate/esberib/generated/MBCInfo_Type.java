/**
 * MBCInfo_Type.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.test.webgate.esberib.generated;


/**
 * Информация об услуге мобильный банк
 */
public class MBCInfo_Type  implements java.io.Serializable {
    /* Подключена услуга или нет */
    private boolean status;

    /* Тип пакета (0 - полный, 1 - экономический) */
    private java.lang.Long contractType;

    /* Информация об операторе и моб.телефоне, к которому подключена
     * услуга < Мобильный банк > */
    private com.rssl.phizic.test.webgate.esberib.generated.PhoneNum_Type phoneNum;

    public MBCInfo_Type() {
    }

    public MBCInfo_Type(
           boolean status,
           java.lang.Long contractType,
           com.rssl.phizic.test.webgate.esberib.generated.PhoneNum_Type phoneNum) {
           this.status = status;
           this.contractType = contractType;
           this.phoneNum = phoneNum;
    }


    /**
     * Gets the status value for this MBCInfo_Type.
     * 
     * @return status   * Подключена услуга или нет
     */
    public boolean isStatus() {
        return status;
    }


    /**
     * Sets the status value for this MBCInfo_Type.
     * 
     * @param status   * Подключена услуга или нет
     */
    public void setStatus(boolean status) {
        this.status = status;
    }


    /**
     * Gets the contractType value for this MBCInfo_Type.
     * 
     * @return contractType   * Тип пакета (0 - полный, 1 - экономический)
     */
    public java.lang.Long getContractType() {
        return contractType;
    }


    /**
     * Sets the contractType value for this MBCInfo_Type.
     * 
     * @param contractType   * Тип пакета (0 - полный, 1 - экономический)
     */
    public void setContractType(java.lang.Long contractType) {
        this.contractType = contractType;
    }


    /**
     * Gets the phoneNum value for this MBCInfo_Type.
     * 
     * @return phoneNum   * Информация об операторе и моб.телефоне, к которому подключена
     * услуга < Мобильный банк >
     */
    public com.rssl.phizic.test.webgate.esberib.generated.PhoneNum_Type getPhoneNum() {
        return phoneNum;
    }


    /**
     * Sets the phoneNum value for this MBCInfo_Type.
     * 
     * @param phoneNum   * Информация об операторе и моб.телефоне, к которому подключена
     * услуга < Мобильный банк >
     */
    public void setPhoneNum(com.rssl.phizic.test.webgate.esberib.generated.PhoneNum_Type phoneNum) {
        this.phoneNum = phoneNum;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof MBCInfo_Type)) return false;
        MBCInfo_Type other = (MBCInfo_Type) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            this.status == other.isStatus() &&
            ((this.contractType==null && other.getContractType()==null) || 
             (this.contractType!=null &&
              this.contractType.equals(other.getContractType()))) &&
            ((this.phoneNum==null && other.getPhoneNum()==null) || 
             (this.phoneNum!=null &&
              this.phoneNum.equals(other.getPhoneNum())));
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
        _hashCode += (isStatus() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        if (getContractType() != null) {
            _hashCode += getContractType().hashCode();
        }
        if (getPhoneNum() != null) {
            _hashCode += getPhoneNum().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(MBCInfo_Type.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "MBCInfo_Type"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("status");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Status"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("contractType");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "ContractType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("phoneNum");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "PhoneNum"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "PhoneNum_Type"));
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
