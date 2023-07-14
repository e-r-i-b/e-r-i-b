/**
 * UpdateActivityResponseType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.test.webgate.rsa.integration.ws.notification.generated;


/**
 * Результат обновления резолюции события
 */
public class UpdateActivityResponseType  implements java.io.Serializable {
    /* Статус запроса */
    private com.rssl.phizic.test.webgate.rsa.integration.ws.notification.generated.ReturnTypeList _return;

    public UpdateActivityResponseType() {
    }

    public UpdateActivityResponseType(
           com.rssl.phizic.test.webgate.rsa.integration.ws.notification.generated.ReturnTypeList _return) {
           this._return = _return;
    }


    /**
     * Gets the _return value for this UpdateActivityResponseType.
     * 
     * @return _return   * Статус запроса
     */
    public com.rssl.phizic.test.webgate.rsa.integration.ws.notification.generated.ReturnTypeList get_return() {
        return _return;
    }


    /**
     * Sets the _return value for this UpdateActivityResponseType.
     * 
     * @param _return   * Статус запроса
     */
    public void set_return(com.rssl.phizic.test.webgate.rsa.integration.ws.notification.generated.ReturnTypeList _return) {
        this._return = _return;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof UpdateActivityResponseType)) return false;
        UpdateActivityResponseType other = (UpdateActivityResponseType) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this._return==null && other.get_return()==null) || 
             (this._return!=null &&
              this._return.equals(other.get_return())));
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
        if (get_return() != null) {
            _hashCode += get_return().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(UpdateActivityResponseType.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.rsa.msk.ru/ActivityEngine/", "updateActivityResponseType"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("_return");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.rsa.msk.ru/ActivityEngine/", "return"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.rsa.msk.ru/ActivityEngine/", "ReturnTypeList"));
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
