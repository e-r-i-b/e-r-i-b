/**
 * TariffClassifier_Type.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizicgate.esberibgate.ws.generated;


/**
 * Классификаторы тарифного плана
 */
public class TariffClassifier_Type  implements java.io.Serializable {
    /* Код классификатора  тарифного   плана */
    private java.lang.String code;

    /* Значение классификатора  тарифного   плана */
    private java.lang.String value;

    /* Признак  отнесения  классификатора  к контракту (счетовому
     * карточному) Используется для  определения   классификаторов для счетовой
     * и   карточной   аппликации  для  WAYU.  Возможные   значения  Card
     * - для  карточного  классификатора  Account-для  счетовoго  классификатора */
    private java.lang.String vid;

    public TariffClassifier_Type() {
    }

    public TariffClassifier_Type(
           java.lang.String code,
           java.lang.String value,
           java.lang.String vid) {
           this.code = code;
           this.value = value;
           this.vid = vid;
    }


    /**
     * Gets the code value for this TariffClassifier_Type.
     * 
     * @return code   * Код классификатора  тарифного   плана
     */
    public java.lang.String getCode() {
        return code;
    }


    /**
     * Sets the code value for this TariffClassifier_Type.
     * 
     * @param code   * Код классификатора  тарифного   плана
     */
    public void setCode(java.lang.String code) {
        this.code = code;
    }


    /**
     * Gets the value value for this TariffClassifier_Type.
     * 
     * @return value   * Значение классификатора  тарифного   плана
     */
    public java.lang.String getValue() {
        return value;
    }


    /**
     * Sets the value value for this TariffClassifier_Type.
     * 
     * @param value   * Значение классификатора  тарифного   плана
     */
    public void setValue(java.lang.String value) {
        this.value = value;
    }


    /**
     * Gets the vid value for this TariffClassifier_Type.
     * 
     * @return vid   * Признак  отнесения  классификатора  к контракту (счетовому
     * карточному) Используется для  определения   классификаторов для счетовой
     * и   карточной   аппликации  для  WAYU.  Возможные   значения  Card
     * - для  карточного  классификатора  Account-для  счетовoго  классификатора
     */
    public java.lang.String getVid() {
        return vid;
    }


    /**
     * Sets the vid value for this TariffClassifier_Type.
     * 
     * @param vid   * Признак  отнесения  классификатора  к контракту (счетовому
     * карточному) Используется для  определения   классификаторов для счетовой
     * и   карточной   аппликации  для  WAYU.  Возможные   значения  Card
     * - для  карточного  классификатора  Account-для  счетовoго  классификатора
     */
    public void setVid(java.lang.String vid) {
        this.vid = vid;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof TariffClassifier_Type)) return false;
        TariffClassifier_Type other = (TariffClassifier_Type) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.code==null && other.getCode()==null) || 
             (this.code!=null &&
              this.code.equals(other.getCode()))) &&
            ((this.value==null && other.getValue()==null) || 
             (this.value!=null &&
              this.value.equals(other.getValue()))) &&
            ((this.vid==null && other.getVid()==null) || 
             (this.vid!=null &&
              this.vid.equals(other.getVid())));
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
        if (getCode() != null) {
            _hashCode += getCode().hashCode();
        }
        if (getValue() != null) {
            _hashCode += getValue().hashCode();
        }
        if (getVid() != null) {
            _hashCode += getVid().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(TariffClassifier_Type.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "TariffClassifier_Type"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("code");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Code"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "C"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("value");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Value"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("vid");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Vid"));
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
