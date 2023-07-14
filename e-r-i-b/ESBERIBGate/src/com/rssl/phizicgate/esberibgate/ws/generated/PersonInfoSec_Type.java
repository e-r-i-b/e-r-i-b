/**
 * PersonInfoSec_Type.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizicgate.esberibgate.ws.generated;


/**
 * Информация о физическом лице формат МДО
 */
public class PersonInfoSec_Type  implements java.io.Serializable {
    private com.rssl.phizicgate.esberibgate.ws.generated.PersonName_Type personName;

    /* Контактная информация */
    private com.rssl.phizicgate.esberibgate.ws.generated.ContactInfoSec_Type contactInfo;

    /* Налоговый идентификатор */
    private com.rssl.phizicgate.esberibgate.ws.generated.TINInfo_Type TINInfo;

    /* Дата рождения */
    private java.lang.String birthDt;

    /* Пол */
    private java.lang.String gender;

    /* Перечень ДУЛ */
    private com.rssl.phizicgate.esberibgate.ws.generated.IdentityCard_Type[] identityCards;

    /* Место рождения */
    private java.lang.String birthPlace;

    /* Признак резидента */
    private java.lang.Boolean resident;

    /* Гражданство */
    private java.lang.String citizenship;

    /* Идентификатор клиента */
    private java.lang.String clientId;

    public PersonInfoSec_Type() {
    }

    public PersonInfoSec_Type(
           com.rssl.phizicgate.esberibgate.ws.generated.PersonName_Type personName,
           com.rssl.phizicgate.esberibgate.ws.generated.ContactInfoSec_Type contactInfo,
           com.rssl.phizicgate.esberibgate.ws.generated.TINInfo_Type TINInfo,
           java.lang.String birthDt,
           java.lang.String gender,
           com.rssl.phizicgate.esberibgate.ws.generated.IdentityCard_Type[] identityCards,
           java.lang.String birthPlace,
           java.lang.Boolean resident,
           java.lang.String citizenship,
           java.lang.String clientId) {
           this.personName = personName;
           this.contactInfo = contactInfo;
           this.TINInfo = TINInfo;
           this.birthDt = birthDt;
           this.gender = gender;
           this.identityCards = identityCards;
           this.birthPlace = birthPlace;
           this.resident = resident;
           this.citizenship = citizenship;
           this.clientId = clientId;
    }


    /**
     * Gets the personName value for this PersonInfoSec_Type.
     * 
     * @return personName
     */
    public com.rssl.phizicgate.esberibgate.ws.generated.PersonName_Type getPersonName() {
        return personName;
    }


    /**
     * Sets the personName value for this PersonInfoSec_Type.
     * 
     * @param personName
     */
    public void setPersonName(com.rssl.phizicgate.esberibgate.ws.generated.PersonName_Type personName) {
        this.personName = personName;
    }


    /**
     * Gets the contactInfo value for this PersonInfoSec_Type.
     * 
     * @return contactInfo   * Контактная информация
     */
    public com.rssl.phizicgate.esberibgate.ws.generated.ContactInfoSec_Type getContactInfo() {
        return contactInfo;
    }


    /**
     * Sets the contactInfo value for this PersonInfoSec_Type.
     * 
     * @param contactInfo   * Контактная информация
     */
    public void setContactInfo(com.rssl.phizicgate.esberibgate.ws.generated.ContactInfoSec_Type contactInfo) {
        this.contactInfo = contactInfo;
    }


    /**
     * Gets the TINInfo value for this PersonInfoSec_Type.
     * 
     * @return TINInfo   * Налоговый идентификатор
     */
    public com.rssl.phizicgate.esberibgate.ws.generated.TINInfo_Type getTINInfo() {
        return TINInfo;
    }


    /**
     * Sets the TINInfo value for this PersonInfoSec_Type.
     * 
     * @param TINInfo   * Налоговый идентификатор
     */
    public void setTINInfo(com.rssl.phizicgate.esberibgate.ws.generated.TINInfo_Type TINInfo) {
        this.TINInfo = TINInfo;
    }


    /**
     * Gets the birthDt value for this PersonInfoSec_Type.
     * 
     * @return birthDt   * Дата рождения
     */
    public java.lang.String getBirthDt() {
        return birthDt;
    }


    /**
     * Sets the birthDt value for this PersonInfoSec_Type.
     * 
     * @param birthDt   * Дата рождения
     */
    public void setBirthDt(java.lang.String birthDt) {
        this.birthDt = birthDt;
    }


    /**
     * Gets the gender value for this PersonInfoSec_Type.
     * 
     * @return gender   * Пол
     */
    public java.lang.String getGender() {
        return gender;
    }


    /**
     * Sets the gender value for this PersonInfoSec_Type.
     * 
     * @param gender   * Пол
     */
    public void setGender(java.lang.String gender) {
        this.gender = gender;
    }


    /**
     * Gets the identityCards value for this PersonInfoSec_Type.
     * 
     * @return identityCards   * Перечень ДУЛ
     */
    public com.rssl.phizicgate.esberibgate.ws.generated.IdentityCard_Type[] getIdentityCards() {
        return identityCards;
    }


    /**
     * Sets the identityCards value for this PersonInfoSec_Type.
     * 
     * @param identityCards   * Перечень ДУЛ
     */
    public void setIdentityCards(com.rssl.phizicgate.esberibgate.ws.generated.IdentityCard_Type[] identityCards) {
        this.identityCards = identityCards;
    }


    /**
     * Gets the birthPlace value for this PersonInfoSec_Type.
     * 
     * @return birthPlace   * Место рождения
     */
    public java.lang.String getBirthPlace() {
        return birthPlace;
    }


    /**
     * Sets the birthPlace value for this PersonInfoSec_Type.
     * 
     * @param birthPlace   * Место рождения
     */
    public void setBirthPlace(java.lang.String birthPlace) {
        this.birthPlace = birthPlace;
    }


    /**
     * Gets the resident value for this PersonInfoSec_Type.
     * 
     * @return resident   * Признак резидента
     */
    public java.lang.Boolean getResident() {
        return resident;
    }


    /**
     * Sets the resident value for this PersonInfoSec_Type.
     * 
     * @param resident   * Признак резидента
     */
    public void setResident(java.lang.Boolean resident) {
        this.resident = resident;
    }


    /**
     * Gets the citizenship value for this PersonInfoSec_Type.
     * 
     * @return citizenship   * Гражданство
     */
    public java.lang.String getCitizenship() {
        return citizenship;
    }


    /**
     * Sets the citizenship value for this PersonInfoSec_Type.
     * 
     * @param citizenship   * Гражданство
     */
    public void setCitizenship(java.lang.String citizenship) {
        this.citizenship = citizenship;
    }


    /**
     * Gets the clientId value for this PersonInfoSec_Type.
     * 
     * @return clientId   * Идентификатор клиента
     */
    public java.lang.String getClientId() {
        return clientId;
    }


    /**
     * Sets the clientId value for this PersonInfoSec_Type.
     * 
     * @param clientId   * Идентификатор клиента
     */
    public void setClientId(java.lang.String clientId) {
        this.clientId = clientId;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof PersonInfoSec_Type)) return false;
        PersonInfoSec_Type other = (PersonInfoSec_Type) obj;
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
            ((this.contactInfo==null && other.getContactInfo()==null) || 
             (this.contactInfo!=null &&
              this.contactInfo.equals(other.getContactInfo()))) &&
            ((this.TINInfo==null && other.getTINInfo()==null) || 
             (this.TINInfo!=null &&
              this.TINInfo.equals(other.getTINInfo()))) &&
            ((this.birthDt==null && other.getBirthDt()==null) || 
             (this.birthDt!=null &&
              this.birthDt.equals(other.getBirthDt()))) &&
            ((this.gender==null && other.getGender()==null) || 
             (this.gender!=null &&
              this.gender.equals(other.getGender()))) &&
            ((this.identityCards==null && other.getIdentityCards()==null) || 
             (this.identityCards!=null &&
              java.util.Arrays.equals(this.identityCards, other.getIdentityCards()))) &&
            ((this.birthPlace==null && other.getBirthPlace()==null) || 
             (this.birthPlace!=null &&
              this.birthPlace.equals(other.getBirthPlace()))) &&
            ((this.resident==null && other.getResident()==null) || 
             (this.resident!=null &&
              this.resident.equals(other.getResident()))) &&
            ((this.citizenship==null && other.getCitizenship()==null) || 
             (this.citizenship!=null &&
              this.citizenship.equals(other.getCitizenship()))) &&
            ((this.clientId==null && other.getClientId()==null) || 
             (this.clientId!=null &&
              this.clientId.equals(other.getClientId())));
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
        if (getContactInfo() != null) {
            _hashCode += getContactInfo().hashCode();
        }
        if (getTINInfo() != null) {
            _hashCode += getTINInfo().hashCode();
        }
        if (getBirthDt() != null) {
            _hashCode += getBirthDt().hashCode();
        }
        if (getGender() != null) {
            _hashCode += getGender().hashCode();
        }
        if (getIdentityCards() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getIdentityCards());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getIdentityCards(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getBirthPlace() != null) {
            _hashCode += getBirthPlace().hashCode();
        }
        if (getResident() != null) {
            _hashCode += getResident().hashCode();
        }
        if (getCitizenship() != null) {
            _hashCode += getCitizenship().hashCode();
        }
        if (getClientId() != null) {
            _hashCode += getClientId().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(PersonInfoSec_Type.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "PersonInfoSec_Type"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("personName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "PersonName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "PersonName_Type"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("contactInfo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "ContactInfo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "ContactInfoSec_Type"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("TINInfo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "TINInfo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "TINInfo_Type"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("birthDt");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "BirthDt"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("gender");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Gender"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Gender_Type"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("identityCards");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "IdentityCards"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "IdentityCard"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("birthPlace");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "BirthPlace"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "C"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("resident");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Resident"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("citizenship");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Citizenship"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "C"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("clientId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "ClientId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "C"));
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
