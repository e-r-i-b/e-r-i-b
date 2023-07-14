/**
 * InfoInqRs_TypeInfo.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.test.webgate.esberib.generated;

public class InfoInqRs_TypeInfo  implements java.io.Serializable {
    /* Статус при взаимодействии с внешним провайдером услуг */
    private com.rssl.phizic.test.webgate.esberib.generated.Status_Type extSPStatus;

    /* Выписка клиента по лицевому счету ПФР */
    private java.lang.String pfrInfo;

    public InfoInqRs_TypeInfo() {
    }

    public InfoInqRs_TypeInfo(
           com.rssl.phizic.test.webgate.esberib.generated.Status_Type extSPStatus,
           java.lang.String pfrInfo) {
           this.extSPStatus = extSPStatus;
           this.pfrInfo = pfrInfo;
    }


    /**
     * Gets the extSPStatus value for this InfoInqRs_TypeInfo.
     * 
     * @return extSPStatus   * Статус при взаимодействии с внешним провайдером услуг
     */
    public com.rssl.phizic.test.webgate.esberib.generated.Status_Type getExtSPStatus() {
        return extSPStatus;
    }


    /**
     * Sets the extSPStatus value for this InfoInqRs_TypeInfo.
     * 
     * @param extSPStatus   * Статус при взаимодействии с внешним провайдером услуг
     */
    public void setExtSPStatus(com.rssl.phizic.test.webgate.esberib.generated.Status_Type extSPStatus) {
        this.extSPStatus = extSPStatus;
    }


    /**
     * Gets the pfrInfo value for this InfoInqRs_TypeInfo.
     * 
     * @return pfrInfo   * Выписка клиента по лицевому счету ПФР
     */
    public java.lang.String getPfrInfo() {
        return pfrInfo;
    }


    /**
     * Sets the pfrInfo value for this InfoInqRs_TypeInfo.
     * 
     * @param pfrInfo   * Выписка клиента по лицевому счету ПФР
     */
    public void setPfrInfo(java.lang.String pfrInfo) {
        this.pfrInfo = pfrInfo;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof InfoInqRs_TypeInfo)) return false;
        InfoInqRs_TypeInfo other = (InfoInqRs_TypeInfo) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.extSPStatus==null && other.getExtSPStatus()==null) || 
             (this.extSPStatus!=null &&
              this.extSPStatus.equals(other.getExtSPStatus()))) &&
            ((this.pfrInfo==null && other.getPfrInfo()==null) || 
             (this.pfrInfo!=null &&
              this.pfrInfo.equals(other.getPfrInfo())));
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
        if (getExtSPStatus() != null) {
            _hashCode += getExtSPStatus().hashCode();
        }
        if (getPfrInfo() != null) {
            _hashCode += getPfrInfo().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(InfoInqRs_TypeInfo.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", ">InfoInqRs_Type>Info"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("extSPStatus");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "ExtSPStatus"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Status_Type"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("pfrInfo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "PfrInfo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
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
