/**
 * MDMPaymentTemplUpdateRq_TypeTemplateInfo.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.test.webgate.esberib.generated;

public class MDMPaymentTemplUpdateRq_TypeTemplateInfo  implements java.io.Serializable {
    /* Наименование шаблона */
    private java.lang.String nickname;

    /* Уникальный в рамках  данной системы идентификатор шаблона */
    private java.lang.String recXferId;

    /* Тип шаблона */
    private java.lang.String category;

    public MDMPaymentTemplUpdateRq_TypeTemplateInfo() {
    }

    public MDMPaymentTemplUpdateRq_TypeTemplateInfo(
           java.lang.String nickname,
           java.lang.String recXferId,
           java.lang.String category) {
           this.nickname = nickname;
           this.recXferId = recXferId;
           this.category = category;
    }


    /**
     * Gets the nickname value for this MDMPaymentTemplUpdateRq_TypeTemplateInfo.
     * 
     * @return nickname   * Наименование шаблона
     */
    public java.lang.String getNickname() {
        return nickname;
    }


    /**
     * Sets the nickname value for this MDMPaymentTemplUpdateRq_TypeTemplateInfo.
     * 
     * @param nickname   * Наименование шаблона
     */
    public void setNickname(java.lang.String nickname) {
        this.nickname = nickname;
    }


    /**
     * Gets the recXferId value for this MDMPaymentTemplUpdateRq_TypeTemplateInfo.
     * 
     * @return recXferId   * Уникальный в рамках  данной системы идентификатор шаблона
     */
    public java.lang.String getRecXferId() {
        return recXferId;
    }


    /**
     * Sets the recXferId value for this MDMPaymentTemplUpdateRq_TypeTemplateInfo.
     * 
     * @param recXferId   * Уникальный в рамках  данной системы идентификатор шаблона
     */
    public void setRecXferId(java.lang.String recXferId) {
        this.recXferId = recXferId;
    }


    /**
     * Gets the category value for this MDMPaymentTemplUpdateRq_TypeTemplateInfo.
     * 
     * @return category   * Тип шаблона
     */
    public java.lang.String getCategory() {
        return category;
    }


    /**
     * Sets the category value for this MDMPaymentTemplUpdateRq_TypeTemplateInfo.
     * 
     * @param category   * Тип шаблона
     */
    public void setCategory(java.lang.String category) {
        this.category = category;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof MDMPaymentTemplUpdateRq_TypeTemplateInfo)) return false;
        MDMPaymentTemplUpdateRq_TypeTemplateInfo other = (MDMPaymentTemplUpdateRq_TypeTemplateInfo) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.nickname==null && other.getNickname()==null) || 
             (this.nickname!=null &&
              this.nickname.equals(other.getNickname()))) &&
            ((this.recXferId==null && other.getRecXferId()==null) || 
             (this.recXferId!=null &&
              this.recXferId.equals(other.getRecXferId()))) &&
            ((this.category==null && other.getCategory()==null) || 
             (this.category!=null &&
              this.category.equals(other.getCategory())));
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
        if (getNickname() != null) {
            _hashCode += getNickname().hashCode();
        }
        if (getRecXferId() != null) {
            _hashCode += getRecXferId().hashCode();
        }
        if (getCategory() != null) {
            _hashCode += getCategory().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(MDMPaymentTemplUpdateRq_TypeTemplateInfo.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", ">MDMPaymentTemplUpdateRq_Type>TemplateInfo"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("nickname");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Nickname"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("recXferId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "RecXferId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("category");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Category"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
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
