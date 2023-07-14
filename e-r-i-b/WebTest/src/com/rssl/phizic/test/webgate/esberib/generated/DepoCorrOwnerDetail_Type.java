/**
 * DepoCorrOwnerDetail_Type.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.test.webgate.esberib.generated;


/**
 * Детальная информации о владельце счета (ДЕПО).
 */
public class DepoCorrOwnerDetail_Type  implements java.io.Serializable {
    /* Имя физического лица */
    private com.rssl.phizic.test.webgate.esberib.generated.PersonName_Type personName;

    /* Удостоверение личности */
    private com.rssl.phizic.test.webgate.esberib.generated.IdentityCard_Type identityCard;

    public DepoCorrOwnerDetail_Type() {
    }

    public DepoCorrOwnerDetail_Type(
           com.rssl.phizic.test.webgate.esberib.generated.PersonName_Type personName,
           com.rssl.phizic.test.webgate.esberib.generated.IdentityCard_Type identityCard) {
           this.personName = personName;
           this.identityCard = identityCard;
    }


    /**
     * Gets the personName value for this DepoCorrOwnerDetail_Type.
     * 
     * @return personName   * Имя физического лица
     */
    public com.rssl.phizic.test.webgate.esberib.generated.PersonName_Type getPersonName() {
        return personName;
    }


    /**
     * Sets the personName value for this DepoCorrOwnerDetail_Type.
     * 
     * @param personName   * Имя физического лица
     */
    public void setPersonName(com.rssl.phizic.test.webgate.esberib.generated.PersonName_Type personName) {
        this.personName = personName;
    }


    /**
     * Gets the identityCard value for this DepoCorrOwnerDetail_Type.
     * 
     * @return identityCard   * Удостоверение личности
     */
    public com.rssl.phizic.test.webgate.esberib.generated.IdentityCard_Type getIdentityCard() {
        return identityCard;
    }


    /**
     * Sets the identityCard value for this DepoCorrOwnerDetail_Type.
     * 
     * @param identityCard   * Удостоверение личности
     */
    public void setIdentityCard(com.rssl.phizic.test.webgate.esberib.generated.IdentityCard_Type identityCard) {
        this.identityCard = identityCard;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof DepoCorrOwnerDetail_Type)) return false;
        DepoCorrOwnerDetail_Type other = (DepoCorrOwnerDetail_Type) obj;
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
        new org.apache.axis.description.TypeDesc(DepoCorrOwnerDetail_Type.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "DepoCorrOwnerDetail_Type"));
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
