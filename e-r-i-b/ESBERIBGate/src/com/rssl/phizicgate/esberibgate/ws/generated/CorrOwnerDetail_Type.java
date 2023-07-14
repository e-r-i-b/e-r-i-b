/**
 * CorrOwnerDetail_Type.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizicgate.esberibgate.ws.generated;


/**
 * Детальная информации о владельце счета
 */
public class CorrOwnerDetail_Type  implements java.io.Serializable {
    private com.rssl.phizicgate.esberibgate.ws.generated.PersonName_Type personName;

    private com.rssl.phizicgate.esberibgate.ws.generated.IdentityCard_Type identityCard;

    public CorrOwnerDetail_Type() {
    }

    public CorrOwnerDetail_Type(
           com.rssl.phizicgate.esberibgate.ws.generated.PersonName_Type personName,
           com.rssl.phizicgate.esberibgate.ws.generated.IdentityCard_Type identityCard) {
           this.personName = personName;
           this.identityCard = identityCard;
    }


    /**
     * Gets the personName value for this CorrOwnerDetail_Type.
     * 
     * @return personName
     */
    public com.rssl.phizicgate.esberibgate.ws.generated.PersonName_Type getPersonName() {
        return personName;
    }


    /**
     * Sets the personName value for this CorrOwnerDetail_Type.
     * 
     * @param personName
     */
    public void setPersonName(com.rssl.phizicgate.esberibgate.ws.generated.PersonName_Type personName) {
        this.personName = personName;
    }


    /**
     * Gets the identityCard value for this CorrOwnerDetail_Type.
     * 
     * @return identityCard
     */
    public com.rssl.phizicgate.esberibgate.ws.generated.IdentityCard_Type getIdentityCard() {
        return identityCard;
    }


    /**
     * Sets the identityCard value for this CorrOwnerDetail_Type.
     * 
     * @param identityCard
     */
    public void setIdentityCard(com.rssl.phizicgate.esberibgate.ws.generated.IdentityCard_Type identityCard) {
        this.identityCard = identityCard;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof CorrOwnerDetail_Type)) return false;
        CorrOwnerDetail_Type other = (CorrOwnerDetail_Type) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.personName==null && other.getPersonName()==null) || 
             (this.personName!=null &&
              this.personName.equals(other.getPersonName()))) &&
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
        if (getPersonName() != null) {
            _hashCode += getPersonName().hashCode();
        }
        if (getIdentityCard() != null) {
            _hashCode += getIdentityCard().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(CorrOwnerDetail_Type.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "CorrOwnerDetail_Type"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("personName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "PersonName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "PersonName_Type"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
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
