/**
 * ClientRq_Type.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.business.ermb.auxiliary.smslog.generated;


/**
 * Информация о клиенте. Присутствует, если по номеру телефона найден
 * профиль клиента
 */
public class ClientRq_Type  implements java.io.Serializable {
    private com.rssl.phizic.business.ermb.auxiliary.smslog.generated.Name_Type name;

    private java.util.Date birthday;

    private com.rssl.phizic.business.ermb.auxiliary.smslog.generated.IdentityCard_Type identityCard;

    private java.lang.String tb;

    public ClientRq_Type() {
    }

    public ClientRq_Type(
           com.rssl.phizic.business.ermb.auxiliary.smslog.generated.Name_Type name,
           java.util.Date birthday,
           com.rssl.phizic.business.ermb.auxiliary.smslog.generated.IdentityCard_Type identityCard,
           java.lang.String tb) {
           this.name = name;
           this.birthday = birthday;
           this.identityCard = identityCard;
           this.tb = tb;
    }


    /**
     * Gets the name value for this ClientRq_Type.
     * 
     * @return name
     */
    public com.rssl.phizic.business.ermb.auxiliary.smslog.generated.Name_Type getName() {
        return name;
    }


    /**
     * Sets the name value for this ClientRq_Type.
     * 
     * @param name
     */
    public void setName(com.rssl.phizic.business.ermb.auxiliary.smslog.generated.Name_Type name) {
        this.name = name;
    }


    /**
     * Gets the birthday value for this ClientRq_Type.
     * 
     * @return birthday
     */
    public java.util.Date getBirthday() {
        return birthday;
    }


    /**
     * Sets the birthday value for this ClientRq_Type.
     * 
     * @param birthday
     */
    public void setBirthday(java.util.Date birthday) {
        this.birthday = birthday;
    }


    /**
     * Gets the identityCard value for this ClientRq_Type.
     * 
     * @return identityCard
     */
    public com.rssl.phizic.business.ermb.auxiliary.smslog.generated.IdentityCard_Type getIdentityCard() {
        return identityCard;
    }


    /**
     * Sets the identityCard value for this ClientRq_Type.
     * 
     * @param identityCard
     */
    public void setIdentityCard(com.rssl.phizic.business.ermb.auxiliary.smslog.generated.IdentityCard_Type identityCard) {
        this.identityCard = identityCard;
    }


    /**
     * Gets the tb value for this ClientRq_Type.
     * 
     * @return tb
     */
    public java.lang.String getTb() {
        return tb;
    }


    /**
     * Sets the tb value for this ClientRq_Type.
     * 
     * @param tb
     */
    public void setTb(java.lang.String tb) {
        this.tb = tb;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ClientRq_Type)) return false;
        ClientRq_Type other = (ClientRq_Type) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.name==null && other.getName()==null) || 
             (this.name!=null &&
              this.name.equals(other.getName()))) &&
            ((this.birthday==null && other.getBirthday()==null) || 
             (this.birthday!=null &&
              this.birthday.equals(other.getBirthday()))) &&
            ((this.identityCard==null && other.getIdentityCard()==null) || 
             (this.identityCard!=null &&
              this.identityCard.equals(other.getIdentityCard()))) &&
            ((this.tb==null && other.getTb()==null) || 
             (this.tb!=null &&
              this.tb.equals(other.getTb())));
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
        if (getName() != null) {
            _hashCode += getName().hashCode();
        }
        if (getBirthday() != null) {
            _hashCode += getBirthday().hashCode();
        }
        if (getIdentityCard() != null) {
            _hashCode += getIdentityCard().hashCode();
        }
        if (getTb() != null) {
            _hashCode += getTb().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ClientRq_Type.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/ermb/auxiliary/sms-log", "clientRq_Type"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("name");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/ermb/auxiliary/sms-log", "name"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/ermb/auxiliary/sms-log", "name_Type"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("birthday");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/ermb/auxiliary/sms-log", "birthday"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "date"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("identityCard");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/ermb/auxiliary/sms-log", "identityCard"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/ermb/auxiliary/sms-log", "identityCard_Type"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tb");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/ermb/auxiliary/sms-log", "tb"));
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
