/**
 * OTPRestriction_Type.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.test.webgate.esberib.generated;


/**
 * Тип для блока "Группа ограничений для одноразовых паролей (OTP)"
 */
public class OTPRestriction_Type  implements java.io.Serializable {
    /* Признак возможности печати одноразовых паролей по карте
     * true - установить возможность 
     * false - запретить возможность */
    private boolean OTPGet;

    /* Признак возможности использования ранее выданных одноразовых
     * паролей.
     * true – выданные ранее одноразовые пароли не блокируются
     * false - - выданные ранее одноразовые парли блокируются
     * Обязателен при OPTGet=false. 
     * Для сервиса GFL следующий смысл "Признак наличия по карте ранее выданных
     * одноразовых паролей" 
     * true – пароли есть
     * false – паролей нет */
    private java.lang.Boolean OTPUse;

    public OTPRestriction_Type() {
    }

    public OTPRestriction_Type(
           boolean OTPGet,
           java.lang.Boolean OTPUse) {
           this.OTPGet = OTPGet;
           this.OTPUse = OTPUse;
    }


    /**
     * Gets the OTPGet value for this OTPRestriction_Type.
     * 
     * @return OTPGet   * Признак возможности печати одноразовых паролей по карте
     * true - установить возможность 
     * false - запретить возможность
     */
    public boolean isOTPGet() {
        return OTPGet;
    }


    /**
     * Sets the OTPGet value for this OTPRestriction_Type.
     * 
     * @param OTPGet   * Признак возможности печати одноразовых паролей по карте
     * true - установить возможность 
     * false - запретить возможность
     */
    public void setOTPGet(boolean OTPGet) {
        this.OTPGet = OTPGet;
    }


    /**
     * Gets the OTPUse value for this OTPRestriction_Type.
     * 
     * @return OTPUse   * Признак возможности использования ранее выданных одноразовых
     * паролей.
     * true – выданные ранее одноразовые пароли не блокируются
     * false - - выданные ранее одноразовые парли блокируются
     * Обязателен при OPTGet=false. 
     * Для сервиса GFL следующий смысл "Признак наличия по карте ранее выданных
     * одноразовых паролей" 
     * true – пароли есть
     * false – паролей нет
     */
    public java.lang.Boolean getOTPUse() {
        return OTPUse;
    }


    /**
     * Sets the OTPUse value for this OTPRestriction_Type.
     * 
     * @param OTPUse   * Признак возможности использования ранее выданных одноразовых
     * паролей.
     * true – выданные ранее одноразовые пароли не блокируются
     * false - - выданные ранее одноразовые парли блокируются
     * Обязателен при OPTGet=false. 
     * Для сервиса GFL следующий смысл "Признак наличия по карте ранее выданных
     * одноразовых паролей" 
     * true – пароли есть
     * false – паролей нет
     */
    public void setOTPUse(java.lang.Boolean OTPUse) {
        this.OTPUse = OTPUse;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof OTPRestriction_Type)) return false;
        OTPRestriction_Type other = (OTPRestriction_Type) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            this.OTPGet == other.isOTPGet() &&
            ((this.OTPUse==null && other.getOTPUse()==null) || 
             (this.OTPUse!=null &&
              this.OTPUse.equals(other.getOTPUse())));
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
        _hashCode += (isOTPGet() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        if (getOTPUse() != null) {
            _hashCode += getOTPUse().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(OTPRestriction_Type.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "OTPRestriction_Type"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("OTPGet");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "OTPGet"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("OTPUse");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "OTPUse"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
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
