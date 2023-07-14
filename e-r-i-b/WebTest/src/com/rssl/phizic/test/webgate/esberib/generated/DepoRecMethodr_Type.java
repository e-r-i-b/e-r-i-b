/**
 * DepoRecMethodr_Type.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.test.webgate.esberib.generated;


/**
 * Метод обработки информации (ДЕПО)
 */
public class DepoRecMethodr_Type  implements java.io.Serializable {
    /* Номер рублевого счета */
    private java.math.BigInteger method;

    /* Вид пластиковой карты. Обязательно, если карт счет. */
    private java.lang.String agreementDate;

    /* Номер рублевого счета */
    private java.lang.String agreementNumber;

    public DepoRecMethodr_Type() {
    }

    public DepoRecMethodr_Type(
           java.math.BigInteger method,
           java.lang.String agreementDate,
           java.lang.String agreementNumber) {
           this.method = method;
           this.agreementDate = agreementDate;
           this.agreementNumber = agreementNumber;
    }


    /**
     * Gets the method value for this DepoRecMethodr_Type.
     * 
     * @return method   * Номер рублевого счета
     */
    public java.math.BigInteger getMethod() {
        return method;
    }


    /**
     * Sets the method value for this DepoRecMethodr_Type.
     * 
     * @param method   * Номер рублевого счета
     */
    public void setMethod(java.math.BigInteger method) {
        this.method = method;
    }


    /**
     * Gets the agreementDate value for this DepoRecMethodr_Type.
     * 
     * @return agreementDate   * Вид пластиковой карты. Обязательно, если карт счет.
     */
    public java.lang.String getAgreementDate() {
        return agreementDate;
    }


    /**
     * Sets the agreementDate value for this DepoRecMethodr_Type.
     * 
     * @param agreementDate   * Вид пластиковой карты. Обязательно, если карт счет.
     */
    public void setAgreementDate(java.lang.String agreementDate) {
        this.agreementDate = agreementDate;
    }


    /**
     * Gets the agreementNumber value for this DepoRecMethodr_Type.
     * 
     * @return agreementNumber   * Номер рублевого счета
     */
    public java.lang.String getAgreementNumber() {
        return agreementNumber;
    }


    /**
     * Sets the agreementNumber value for this DepoRecMethodr_Type.
     * 
     * @param agreementNumber   * Номер рублевого счета
     */
    public void setAgreementNumber(java.lang.String agreementNumber) {
        this.agreementNumber = agreementNumber;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof DepoRecMethodr_Type)) return false;
        DepoRecMethodr_Type other = (DepoRecMethodr_Type) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.method==null && other.getMethod()==null) || 
             (this.method!=null &&
              this.method.equals(other.getMethod()))) &&
            ((this.agreementDate==null && other.getAgreementDate()==null) || 
             (this.agreementDate!=null &&
              this.agreementDate.equals(other.getAgreementDate()))) &&
            ((this.agreementNumber==null && other.getAgreementNumber()==null) || 
             (this.agreementNumber!=null &&
              this.agreementNumber.equals(other.getAgreementNumber())));
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
        if (getMethod() != null) {
            _hashCode += getMethod().hashCode();
        }
        if (getAgreementDate() != null) {
            _hashCode += getAgreementDate().hashCode();
        }
        if (getAgreementNumber() != null) {
            _hashCode += getAgreementNumber().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(DepoRecMethodr_Type.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "DepoRecMethodr_Type"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("method");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Method"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("agreementDate");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "AgreementDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("agreementNumber");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "AgreementNumber"));
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
