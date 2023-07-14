/**
 * GetResolutionResponseType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.rsa.integration.ws.notification.generated;


/**
 * Ответ на получение резолюции события
 */
public class GetResolutionResponseType  implements java.io.Serializable {
    /* Статус запроса */
    private com.rssl.phizic.rsa.integration.ws.notification.generated.ReturnTypeList _return;

    /* Текущая резолюция */
    private com.rssl.phizic.rsa.integration.ws.notification.generated.ResolutionTypeList resolution;

    public GetResolutionResponseType() {
    }

    public GetResolutionResponseType(
           com.rssl.phizic.rsa.integration.ws.notification.generated.ReturnTypeList _return,
           com.rssl.phizic.rsa.integration.ws.notification.generated.ResolutionTypeList resolution) {
           this._return = _return;
           this.resolution = resolution;
    }


    /**
     * Gets the _return value for this GetResolutionResponseType.
     * 
     * @return _return   * Статус запроса
     */
    public com.rssl.phizic.rsa.integration.ws.notification.generated.ReturnTypeList get_return() {
        return _return;
    }


    /**
     * Sets the _return value for this GetResolutionResponseType.
     * 
     * @param _return   * Статус запроса
     */
    public void set_return(com.rssl.phizic.rsa.integration.ws.notification.generated.ReturnTypeList _return) {
        this._return = _return;
    }


    /**
     * Gets the resolution value for this GetResolutionResponseType.
     * 
     * @return resolution   * Текущая резолюция
     */
    public com.rssl.phizic.rsa.integration.ws.notification.generated.ResolutionTypeList getResolution() {
        return resolution;
    }


    /**
     * Sets the resolution value for this GetResolutionResponseType.
     * 
     * @param resolution   * Текущая резолюция
     */
    public void setResolution(com.rssl.phizic.rsa.integration.ws.notification.generated.ResolutionTypeList resolution) {
        this.resolution = resolution;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof GetResolutionResponseType)) return false;
        GetResolutionResponseType other = (GetResolutionResponseType) obj;
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
              this._return.equals(other.get_return()))) &&
            ((this.resolution==null && other.getResolution()==null) || 
             (this.resolution!=null &&
              this.resolution.equals(other.getResolution())));
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
        if (getResolution() != null) {
            _hashCode += getResolution().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(GetResolutionResponseType.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.rsa.msk.ru/ActivityEngine/", "getResolutionResponseType"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("_return");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.rsa.msk.ru/ActivityEngine/", "return"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.rsa.msk.ru/ActivityEngine/", "ReturnTypeList"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("resolution");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.rsa.msk.ru/ActivityEngine/", "resolution"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.rsa.msk.ru/ActivityEngine/", "ResolutionTypeList"));
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
