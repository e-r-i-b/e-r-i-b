/**
 * CustStatus_Type.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.test.webgate.esberib.generated;


/**
 * Состояние потребителя
 */
public class CustStatus_Type  implements java.io.Serializable {
    /* Идентификатор системы (может использоваться опционально) */
    private com.rssl.phizic.test.webgate.esberib.generated.SPName_Type SPName;

    /* Код статуса потребителя в системе */
    private long custStatusCode;

    /* Описание статуса потребителя */
    private java.lang.String statusDesc;

    public CustStatus_Type() {
    }

    public CustStatus_Type(
           com.rssl.phizic.test.webgate.esberib.generated.SPName_Type SPName,
           long custStatusCode,
           java.lang.String statusDesc) {
           this.SPName = SPName;
           this.custStatusCode = custStatusCode;
           this.statusDesc = statusDesc;
    }


    /**
     * Gets the SPName value for this CustStatus_Type.
     * 
     * @return SPName   * Идентификатор системы (может использоваться опционально)
     */
    public com.rssl.phizic.test.webgate.esberib.generated.SPName_Type getSPName() {
        return SPName;
    }


    /**
     * Sets the SPName value for this CustStatus_Type.
     * 
     * @param SPName   * Идентификатор системы (может использоваться опционально)
     */
    public void setSPName(com.rssl.phizic.test.webgate.esberib.generated.SPName_Type SPName) {
        this.SPName = SPName;
    }


    /**
     * Gets the custStatusCode value for this CustStatus_Type.
     * 
     * @return custStatusCode   * Код статуса потребителя в системе
     */
    public long getCustStatusCode() {
        return custStatusCode;
    }


    /**
     * Sets the custStatusCode value for this CustStatus_Type.
     * 
     * @param custStatusCode   * Код статуса потребителя в системе
     */
    public void setCustStatusCode(long custStatusCode) {
        this.custStatusCode = custStatusCode;
    }


    /**
     * Gets the statusDesc value for this CustStatus_Type.
     * 
     * @return statusDesc   * Описание статуса потребителя
     */
    public java.lang.String getStatusDesc() {
        return statusDesc;
    }


    /**
     * Sets the statusDesc value for this CustStatus_Type.
     * 
     * @param statusDesc   * Описание статуса потребителя
     */
    public void setStatusDesc(java.lang.String statusDesc) {
        this.statusDesc = statusDesc;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof CustStatus_Type)) return false;
        CustStatus_Type other = (CustStatus_Type) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.SPName==null && other.getSPName()==null) || 
             (this.SPName!=null &&
              this.SPName.equals(other.getSPName()))) &&
            this.custStatusCode == other.getCustStatusCode() &&
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
        if (getSPName() != null) {
            _hashCode += getSPName().hashCode();
        }
        _hashCode += new Long(getCustStatusCode()).hashCode();
        if (getStatusDesc() != null) {
            _hashCode += getStatusDesc().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(CustStatus_Type.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "CustStatus_Type"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("SPName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "SPName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "SPName_Type"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("custStatusCode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "CustStatusCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Long"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("statusDesc");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "StatusDesc"));
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
