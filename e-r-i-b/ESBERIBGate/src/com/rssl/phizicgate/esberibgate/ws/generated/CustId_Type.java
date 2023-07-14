/**
 * CustId_Type.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizicgate.esberibgate.ws.generated;


/**
 * Идентификация потребителя <CustId>
 */
public class CustId_Type  implements java.io.Serializable {
    /* Название ПУ, присвоившего <CustPermId>. */
    private com.rssl.phizicgate.esberibgate.ws.generated.SPName_Type SPName;

    /* Постоянный идентификатор потребителя. Используется как ключ
     * БД для уникальной идентификации потребителя. Не может быть изменен
     * потребителем. */
    private java.lang.String custPermId;

    public CustId_Type() {
    }

    public CustId_Type(
           com.rssl.phizicgate.esberibgate.ws.generated.SPName_Type SPName,
           java.lang.String custPermId) {
           this.SPName = SPName;
           this.custPermId = custPermId;
    }


    /**
     * Gets the SPName value for this CustId_Type.
     * 
     * @return SPName   * Название ПУ, присвоившего <CustPermId>.
     */
    public com.rssl.phizicgate.esberibgate.ws.generated.SPName_Type getSPName() {
        return SPName;
    }


    /**
     * Sets the SPName value for this CustId_Type.
     * 
     * @param SPName   * Название ПУ, присвоившего <CustPermId>.
     */
    public void setSPName(com.rssl.phizicgate.esberibgate.ws.generated.SPName_Type SPName) {
        this.SPName = SPName;
    }


    /**
     * Gets the custPermId value for this CustId_Type.
     * 
     * @return custPermId   * Постоянный идентификатор потребителя. Используется как ключ
     * БД для уникальной идентификации потребителя. Не может быть изменен
     * потребителем.
     */
    public java.lang.String getCustPermId() {
        return custPermId;
    }


    /**
     * Sets the custPermId value for this CustId_Type.
     * 
     * @param custPermId   * Постоянный идентификатор потребителя. Используется как ключ
     * БД для уникальной идентификации потребителя. Не может быть изменен
     * потребителем.
     */
    public void setCustPermId(java.lang.String custPermId) {
        this.custPermId = custPermId;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof CustId_Type)) return false;
        CustId_Type other = (CustId_Type) obj;
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
            ((this.custPermId==null && other.getCustPermId()==null) || 
             (this.custPermId!=null &&
              this.custPermId.equals(other.getCustPermId())));
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
        if (getCustPermId() != null) {
            _hashCode += getCustPermId().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(CustId_Type.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "CustId_Type"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("SPName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "SPName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "SPName_Type"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("custPermId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "CustPermId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "CustPermId_Type"));
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
