/**
 * ClientRs_Type.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.test.ermb.smslog.generated;


/**
 * Информация о клиенте. Приходит, если по номеру телефона найден
 * профиль клиента
 */
public class ClientRs_Type  implements java.io.Serializable {
    private java.lang.String lastname;

    private java.lang.String firstname;

    private java.lang.String middlename;

    private java.util.Date birthday;

    private com.rssl.phizic.test.ermb.smslog.generated.IdentityCard_Type identityCard;

    private java.lang.String tb;

    public ClientRs_Type() {
    }

    public ClientRs_Type(
           java.lang.String lastname,
           java.lang.String firstname,
           java.lang.String middlename,
           java.util.Date birthday,
           com.rssl.phizic.test.ermb.smslog.generated.IdentityCard_Type identityCard,
           java.lang.String tb) {
           this.lastname = lastname;
           this.firstname = firstname;
           this.middlename = middlename;
           this.birthday = birthday;
           this.identityCard = identityCard;
           this.tb = tb;
    }


    /**
     * Gets the lastname value for this ClientRs_Type.
     * 
     * @return lastname
     */
    public java.lang.String getLastname() {
        return lastname;
    }


    /**
     * Sets the lastname value for this ClientRs_Type.
     * 
     * @param lastname
     */
    public void setLastname(java.lang.String lastname) {
        this.lastname = lastname;
    }


    /**
     * Gets the firstname value for this ClientRs_Type.
     * 
     * @return firstname
     */
    public java.lang.String getFirstname() {
        return firstname;
    }


    /**
     * Sets the firstname value for this ClientRs_Type.
     * 
     * @param firstname
     */
    public void setFirstname(java.lang.String firstname) {
        this.firstname = firstname;
    }


    /**
     * Gets the middlename value for this ClientRs_Type.
     * 
     * @return middlename
     */
    public java.lang.String getMiddlename() {
        return middlename;
    }


    /**
     * Sets the middlename value for this ClientRs_Type.
     * 
     * @param middlename
     */
    public void setMiddlename(java.lang.String middlename) {
        this.middlename = middlename;
    }


    /**
     * Gets the birthday value for this ClientRs_Type.
     * 
     * @return birthday
     */
    public java.util.Date getBirthday() {
        return birthday;
    }


    /**
     * Sets the birthday value for this ClientRs_Type.
     * 
     * @param birthday
     */
    public void setBirthday(java.util.Date birthday) {
        this.birthday = birthday;
    }


    /**
     * Gets the identityCard value for this ClientRs_Type.
     * 
     * @return identityCard
     */
    public com.rssl.phizic.test.ermb.smslog.generated.IdentityCard_Type getIdentityCard() {
        return identityCard;
    }


    /**
     * Sets the identityCard value for this ClientRs_Type.
     * 
     * @param identityCard
     */
    public void setIdentityCard(com.rssl.phizic.test.ermb.smslog.generated.IdentityCard_Type identityCard) {
        this.identityCard = identityCard;
    }


    /**
     * Gets the tb value for this ClientRs_Type.
     * 
     * @return tb
     */
    public java.lang.String getTb() {
        return tb;
    }


    /**
     * Sets the tb value for this ClientRs_Type.
     * 
     * @param tb
     */
    public void setTb(java.lang.String tb) {
        this.tb = tb;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ClientRs_Type)) return false;
        ClientRs_Type other = (ClientRs_Type) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.lastname==null && other.getLastname()==null) || 
             (this.lastname!=null &&
              this.lastname.equals(other.getLastname()))) &&
            ((this.firstname==null && other.getFirstname()==null) || 
             (this.firstname!=null &&
              this.firstname.equals(other.getFirstname()))) &&
            ((this.middlename==null && other.getMiddlename()==null) || 
             (this.middlename!=null &&
              this.middlename.equals(other.getMiddlename()))) &&
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
        if (getLastname() != null) {
            _hashCode += getLastname().hashCode();
        }
        if (getFirstname() != null) {
            _hashCode += getFirstname().hashCode();
        }
        if (getMiddlename() != null) {
            _hashCode += getMiddlename().hashCode();
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
        new org.apache.axis.description.TypeDesc(ClientRs_Type.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/ermb/auxiliary/sms-log", "clientRs_Type"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("lastname");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/ermb/auxiliary/sms-log", "lastname"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("firstname");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/ermb/auxiliary/sms-log", "firstname"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("middlename");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/ermb/auxiliary/sms-log", "middlename"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("birthday");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/ermb/auxiliary/sms-log", "birthday"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "date"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("identityCard");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/ermb/auxiliary/sms-log", "identityCard"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/ermb/auxiliary/sms-log", "identityCard_Type"));
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
