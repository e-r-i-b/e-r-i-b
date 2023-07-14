/**
 * Status_Type.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.ws.esberiblistener.esberib.generated;


/**
 * Статус ответа <Status>. Агрегат <Status> используется для возврата
 * статуса сообщения.
 */
public class Status_Type  implements java.io.Serializable {
    /* Статусный код возврата. */
    private long statusCode;

    /* Описание статуса. */
    private java.lang.String statusDesc;

    /* Тип ошибки. */
    private java.lang.String statusType;

    /* Описание статуса системы источника */
    private java.lang.String serverStatusDesc;

    public Status_Type() {
    }

    public Status_Type(
           long statusCode,
           java.lang.String statusDesc,
           java.lang.String statusType,
           java.lang.String serverStatusDesc) {
           this.statusCode = statusCode;
           this.statusDesc = statusDesc;
           this.statusType = statusType;
           this.serverStatusDesc = serverStatusDesc;
    }


    /**
     * Gets the statusCode value for this Status_Type.
     * 
     * @return statusCode   * Статусный код возврата.
     */
    public long getStatusCode() {
        return statusCode;
    }


    /**
     * Sets the statusCode value for this Status_Type.
     * 
     * @param statusCode   * Статусный код возврата.
     */
    public void setStatusCode(long statusCode) {
        this.statusCode = statusCode;
    }


    /**
     * Gets the statusDesc value for this Status_Type.
     * 
     * @return statusDesc   * Описание статуса.
     */
    public java.lang.String getStatusDesc() {
        return statusDesc;
    }


    /**
     * Sets the statusDesc value for this Status_Type.
     * 
     * @param statusDesc   * Описание статуса.
     */
    public void setStatusDesc(java.lang.String statusDesc) {
        this.statusDesc = statusDesc;
    }


    /**
     * Gets the statusType value for this Status_Type.
     * 
     * @return statusType   * Тип ошибки.
     */
    public java.lang.String getStatusType() {
        return statusType;
    }


    /**
     * Sets the statusType value for this Status_Type.
     * 
     * @param statusType   * Тип ошибки.
     */
    public void setStatusType(java.lang.String statusType) {
        this.statusType = statusType;
    }


    /**
     * Gets the serverStatusDesc value for this Status_Type.
     * 
     * @return serverStatusDesc   * Описание статуса системы источника
     */
    public java.lang.String getServerStatusDesc() {
        return serverStatusDesc;
    }


    /**
     * Sets the serverStatusDesc value for this Status_Type.
     * 
     * @param serverStatusDesc   * Описание статуса системы источника
     */
    public void setServerStatusDesc(java.lang.String serverStatusDesc) {
        this.serverStatusDesc = serverStatusDesc;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Status_Type)) return false;
        Status_Type other = (Status_Type) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            this.statusCode == other.getStatusCode() &&
            ((this.statusDesc==null && other.getStatusDesc()==null) || 
             (this.statusDesc!=null &&
              this.statusDesc.equals(other.getStatusDesc()))) &&
            ((this.statusType==null && other.getStatusType()==null) || 
             (this.statusType!=null &&
              this.statusType.equals(other.getStatusType()))) &&
            ((this.serverStatusDesc==null && other.getServerStatusDesc()==null) || 
             (this.serverStatusDesc!=null &&
              this.serverStatusDesc.equals(other.getServerStatusDesc())));
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
        _hashCode += new Long(getStatusCode()).hashCode();
        if (getStatusDesc() != null) {
            _hashCode += getStatusDesc().hashCode();
        }
        if (getStatusType() != null) {
            _hashCode += getStatusType().hashCode();
        }
        if (getServerStatusDesc() != null) {
            _hashCode += getServerStatusDesc().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Status_Type.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Status_Type"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("statusCode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "StatusCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "StatusCode_Type"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("statusDesc");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "StatusDesc"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "StatusDesc_Type"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("statusType");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "StatusType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "StatusType_Type"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("serverStatusDesc");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "ServerStatusDesc"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "ServerStatusDesc_Type"));
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
