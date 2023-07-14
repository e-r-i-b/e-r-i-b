/**
 * CardAuthorization_Type.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizicgate.esberibgate.ws.generated;


/**
 * Тип информация об авторизации карты
 */
public class CardAuthorization_Type  implements java.io.Serializable {
    /* Код авторизации, если транзакция прошла успешно */
    private long authorizationCode;

    /* Дата и время транзакции */
    private java.lang.String authorizationDtTm;

    public CardAuthorization_Type() {
    }

    public CardAuthorization_Type(
           long authorizationCode,
           java.lang.String authorizationDtTm) {
           this.authorizationCode = authorizationCode;
           this.authorizationDtTm = authorizationDtTm;
    }


    /**
     * Gets the authorizationCode value for this CardAuthorization_Type.
     * 
     * @return authorizationCode   * Код авторизации, если транзакция прошла успешно
     */
    public long getAuthorizationCode() {
        return authorizationCode;
    }


    /**
     * Sets the authorizationCode value for this CardAuthorization_Type.
     * 
     * @param authorizationCode   * Код авторизации, если транзакция прошла успешно
     */
    public void setAuthorizationCode(long authorizationCode) {
        this.authorizationCode = authorizationCode;
    }


    /**
     * Gets the authorizationDtTm value for this CardAuthorization_Type.
     * 
     * @return authorizationDtTm   * Дата и время транзакции
     */
    public java.lang.String getAuthorizationDtTm() {
        return authorizationDtTm;
    }


    /**
     * Sets the authorizationDtTm value for this CardAuthorization_Type.
     * 
     * @param authorizationDtTm   * Дата и время транзакции
     */
    public void setAuthorizationDtTm(java.lang.String authorizationDtTm) {
        this.authorizationDtTm = authorizationDtTm;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof CardAuthorization_Type)) return false;
        CardAuthorization_Type other = (CardAuthorization_Type) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            this.authorizationCode == other.getAuthorizationCode() &&
            ((this.authorizationDtTm==null && other.getAuthorizationDtTm()==null) || 
             (this.authorizationDtTm!=null &&
              this.authorizationDtTm.equals(other.getAuthorizationDtTm())));
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
        _hashCode += new Long(getAuthorizationCode()).hashCode();
        if (getAuthorizationDtTm() != null) {
            _hashCode += getAuthorizationDtTm().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(CardAuthorization_Type.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "CardAuthorization_Type"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("authorizationCode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "AuthorizationCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("authorizationDtTm");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "AuthorizationDtTm"));
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
