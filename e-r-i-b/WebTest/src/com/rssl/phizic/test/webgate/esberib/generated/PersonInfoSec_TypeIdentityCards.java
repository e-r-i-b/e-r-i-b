/**
 * PersonInfoSec_TypeIdentityCards.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.test.webgate.esberib.generated;

public class PersonInfoSec_TypeIdentityCards  implements java.io.Serializable {
    private com.rssl.phizic.test.webgate.esberib.generated.IdentityCard_Type identityCard;

    public PersonInfoSec_TypeIdentityCards() {
    }

    public PersonInfoSec_TypeIdentityCards(
           com.rssl.phizic.test.webgate.esberib.generated.IdentityCard_Type identityCard) {
           this.identityCard = identityCard;
    }


    /**
     * Gets the identityCard value for this PersonInfoSec_TypeIdentityCards.
     * 
     * @return identityCard
     */
    public com.rssl.phizic.test.webgate.esberib.generated.IdentityCard_Type getIdentityCard() {
        return identityCard;
    }


    /**
     * Sets the identityCard value for this PersonInfoSec_TypeIdentityCards.
     * 
     * @param identityCard
     */
    public void setIdentityCard(com.rssl.phizic.test.webgate.esberib.generated.IdentityCard_Type identityCard) {
        this.identityCard = identityCard;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof PersonInfoSec_TypeIdentityCards)) return false;
        PersonInfoSec_TypeIdentityCards other = (PersonInfoSec_TypeIdentityCards) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.identityCard==null && other.getIdentityCard()==null) || 
             (this.identityCard!=null &&
              this.identityCard.equals(other.getIdentityCard())));
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
        if (getIdentityCard() != null) {
            _hashCode += getIdentityCard().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(PersonInfoSec_TypeIdentityCards.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", ">PersonInfoSec_Type>IdentityCards"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("identityCard");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "IdentityCard"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "IdentityCard_Type"));
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
