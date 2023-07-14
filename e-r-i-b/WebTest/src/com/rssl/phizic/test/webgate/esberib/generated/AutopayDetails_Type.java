/**
 * AutopayDetails_Type.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.test.webgate.esberib.generated;


/**
 * Тип для передачи Параметров подписки при подготовке или исполнении
 */
public class AutopayDetails_Type  implements java.io.Serializable {
    /* Тип подписки */
    private com.rssl.phizic.test.webgate.esberib.generated.AutopayType_Type[] autoPayType;

    /* Лимит (допустимое пороговое значение или список допустимых
     * пороговых значений) для пороговых автоплатежей */
    private java.lang.String limit;

    public AutopayDetails_Type() {
    }

    public AutopayDetails_Type(
           com.rssl.phizic.test.webgate.esberib.generated.AutopayType_Type[] autoPayType,
           java.lang.String limit) {
           this.autoPayType = autoPayType;
           this.limit = limit;
    }


    /**
     * Gets the autoPayType value for this AutopayDetails_Type.
     * 
     * @return autoPayType   * Тип подписки
     */
    public com.rssl.phizic.test.webgate.esberib.generated.AutopayType_Type[] getAutoPayType() {
        return autoPayType;
    }


    /**
     * Sets the autoPayType value for this AutopayDetails_Type.
     * 
     * @param autoPayType   * Тип подписки
     */
    public void setAutoPayType(com.rssl.phizic.test.webgate.esberib.generated.AutopayType_Type[] autoPayType) {
        this.autoPayType = autoPayType;
    }

    public com.rssl.phizic.test.webgate.esberib.generated.AutopayType_Type getAutoPayType(int i) {
        return this.autoPayType[i];
    }

    public void setAutoPayType(int i, com.rssl.phizic.test.webgate.esberib.generated.AutopayType_Type _value) {
        this.autoPayType[i] = _value;
    }


    /**
     * Gets the limit value for this AutopayDetails_Type.
     * 
     * @return limit   * Лимит (допустимое пороговое значение или список допустимых
     * пороговых значений) для пороговых автоплатежей
     */
    public java.lang.String getLimit() {
        return limit;
    }


    /**
     * Sets the limit value for this AutopayDetails_Type.
     * 
     * @param limit   * Лимит (допустимое пороговое значение или список допустимых
     * пороговых значений) для пороговых автоплатежей
     */
    public void setLimit(java.lang.String limit) {
        this.limit = limit;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof AutopayDetails_Type)) return false;
        AutopayDetails_Type other = (AutopayDetails_Type) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.autoPayType==null && other.getAutoPayType()==null) || 
             (this.autoPayType!=null &&
              java.util.Arrays.equals(this.autoPayType, other.getAutoPayType()))) &&
            ((this.limit==null && other.getLimit()==null) || 
             (this.limit!=null &&
              this.limit.equals(other.getLimit())));
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
        if (getAutoPayType() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getAutoPayType());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getAutoPayType(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getLimit() != null) {
            _hashCode += getLimit().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(AutopayDetails_Type.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "AutopayDetails_Type"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("autoPayType");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "AutoPayType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "AutopayType_Type"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("limit");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Limit"));
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
