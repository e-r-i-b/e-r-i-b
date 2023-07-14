/**
 * StatusType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizicgate.uec.generated;


/**
 * Информация о выполнении запроса
 */
public class StatusType  implements java.io.Serializable {
    private com.rssl.phizicgate.uec.generated.StatusCodeType statusCode;

    /* Текст ошибки, готовый для отображения пользователю.
     *                         Заполняется в зависимости от кода ошибки в
     * соответствии с таблицей, представленной ниже.
     *                         Не приходит только в том случае если StatusCode
     * равен 0. */
    private java.lang.String statusDesc;

    public StatusType() {
    }

    public StatusType(
           com.rssl.phizicgate.uec.generated.StatusCodeType statusCode,
           java.lang.String statusDesc) {
           this.statusCode = statusCode;
           this.statusDesc = statusDesc;
    }


    /**
     * Gets the statusCode value for this StatusType.
     * 
     * @return statusCode
     */
    public com.rssl.phizicgate.uec.generated.StatusCodeType getStatusCode() {
        return statusCode;
    }


    /**
     * Sets the statusCode value for this StatusType.
     * 
     * @param statusCode
     */
    public void setStatusCode(com.rssl.phizicgate.uec.generated.StatusCodeType statusCode) {
        this.statusCode = statusCode;
    }


    /**
     * Gets the statusDesc value for this StatusType.
     * 
     * @return statusDesc   * Текст ошибки, готовый для отображения пользователю.
     *                         Заполняется в зависимости от кода ошибки в
     * соответствии с таблицей, представленной ниже.
     *                         Не приходит только в том случае если StatusCode
     * равен 0.
     */
    public java.lang.String getStatusDesc() {
        return statusDesc;
    }


    /**
     * Sets the statusDesc value for this StatusType.
     * 
     * @param statusDesc   * Текст ошибки, готовый для отображения пользователю.
     *                         Заполняется в зависимости от кода ошибки в
     * соответствии с таблицей, представленной ниже.
     *                         Не приходит только в том случае если StatusCode
     * равен 0.
     */
    public void setStatusDesc(java.lang.String statusDesc) {
        this.statusDesc = statusDesc;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof StatusType)) return false;
        StatusType other = (StatusType) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.statusCode==null && other.getStatusCode()==null) || 
             (this.statusCode!=null &&
              this.statusCode.equals(other.getStatusCode()))) &&
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
        if (getStatusCode() != null) {
            _hashCode += getStatusCode().hashCode();
        }
        if (getStatusDesc() != null) {
            _hashCode += getStatusDesc().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(StatusType.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/e-invoicing/uec/1", "StatusType"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("statusCode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/e-invoicing/uec/1", "StatusCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/e-invoicing/uec/1", "StatusCodeType"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("statusDesc");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/e-invoicing/uec/1", "StatusDesc"));
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
