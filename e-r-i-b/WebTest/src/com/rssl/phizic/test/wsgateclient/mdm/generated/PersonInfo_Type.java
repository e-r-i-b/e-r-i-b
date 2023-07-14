/**
 * PersonInfo_Type.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.test.wsgateclient.mdm.generated;


/**
 * Информация о физическом лице <PersonInfo>
 */
public class PersonInfo_Type  implements java.io.Serializable {
    /* Дата рождения */
    private java.lang.String birthday;

    /* Место рождения */
    private java.lang.String birthPlace;

    /* ИНН */
    private java.lang.String taxId;

    /* Гражданство */
    private java.lang.String citizenship;

    /* Гражданство */
    private java.lang.String additionalInfo;

    /* Роль клиента (используется в кредитном договоре) */
    private java.lang.Long personRole;

    /* Пол. Значения:Male – мужской Female – женский Unknown – неизвестен */
    private java.lang.String gender;

    /* Резидент или нет */
    private java.lang.Boolean resident;

    /* ФИО клиента */
    private com.rssl.phizic.test.wsgateclient.mdm.generated.PersonName_Type personName;

    /* Удостоверение личности. */
    private com.rssl.phizic.test.wsgateclient.mdm.generated.IdentityCard_Type identityCard;

    /* Контактная информация */
    private com.rssl.phizic.test.wsgateclient.mdm.generated.ContactInfo_Type contactInfo;

    public PersonInfo_Type() {
    }

    public PersonInfo_Type(
           java.lang.String birthday,
           java.lang.String birthPlace,
           java.lang.String taxId,
           java.lang.String citizenship,
           java.lang.String additionalInfo,
           java.lang.Long personRole,
           java.lang.String gender,
           java.lang.Boolean resident,
           com.rssl.phizic.test.wsgateclient.mdm.generated.PersonName_Type personName,
           com.rssl.phizic.test.wsgateclient.mdm.generated.IdentityCard_Type identityCard,
           com.rssl.phizic.test.wsgateclient.mdm.generated.ContactInfo_Type contactInfo) {
           this.birthday = birthday;
           this.birthPlace = birthPlace;
           this.taxId = taxId;
           this.citizenship = citizenship;
           this.additionalInfo = additionalInfo;
           this.personRole = personRole;
           this.gender = gender;
           this.resident = resident;
           this.personName = personName;
           this.identityCard = identityCard;
           this.contactInfo = contactInfo;
    }


    /**
     * Gets the birthday value for this PersonInfo_Type.
     * 
     * @return birthday   * Дата рождения
     */
    public java.lang.String getBirthday() {
        return birthday;
    }


    /**
     * Sets the birthday value for this PersonInfo_Type.
     * 
     * @param birthday   * Дата рождения
     */
    public void setBirthday(java.lang.String birthday) {
        this.birthday = birthday;
    }


    /**
     * Gets the birthPlace value for this PersonInfo_Type.
     * 
     * @return birthPlace   * Место рождения
     */
    public java.lang.String getBirthPlace() {
        return birthPlace;
    }


    /**
     * Sets the birthPlace value for this PersonInfo_Type.
     * 
     * @param birthPlace   * Место рождения
     */
    public void setBirthPlace(java.lang.String birthPlace) {
        this.birthPlace = birthPlace;
    }


    /**
     * Gets the taxId value for this PersonInfo_Type.
     * 
     * @return taxId   * ИНН
     */
    public java.lang.String getTaxId() {
        return taxId;
    }


    /**
     * Sets the taxId value for this PersonInfo_Type.
     * 
     * @param taxId   * ИНН
     */
    public void setTaxId(java.lang.String taxId) {
        this.taxId = taxId;
    }


    /**
     * Gets the citizenship value for this PersonInfo_Type.
     * 
     * @return citizenship   * Гражданство
     */
    public java.lang.String getCitizenship() {
        return citizenship;
    }


    /**
     * Sets the citizenship value for this PersonInfo_Type.
     * 
     * @param citizenship   * Гражданство
     */
    public void setCitizenship(java.lang.String citizenship) {
        this.citizenship = citizenship;
    }


    /**
     * Gets the additionalInfo value for this PersonInfo_Type.
     * 
     * @return additionalInfo   * Гражданство
     */
    public java.lang.String getAdditionalInfo() {
        return additionalInfo;
    }


    /**
     * Sets the additionalInfo value for this PersonInfo_Type.
     * 
     * @param additionalInfo   * Гражданство
     */
    public void setAdditionalInfo(java.lang.String additionalInfo) {
        this.additionalInfo = additionalInfo;
    }


    /**
     * Gets the personRole value for this PersonInfo_Type.
     * 
     * @return personRole   * Роль клиента (используется в кредитном договоре)
     */
    public java.lang.Long getPersonRole() {
        return personRole;
    }


    /**
     * Sets the personRole value for this PersonInfo_Type.
     * 
     * @param personRole   * Роль клиента (используется в кредитном договоре)
     */
    public void setPersonRole(java.lang.Long personRole) {
        this.personRole = personRole;
    }


    /**
     * Gets the gender value for this PersonInfo_Type.
     * 
     * @return gender   * Пол. Значения:Male – мужской Female – женский Unknown – неизвестен
     */
    public java.lang.String getGender() {
        return gender;
    }


    /**
     * Sets the gender value for this PersonInfo_Type.
     * 
     * @param gender   * Пол. Значения:Male – мужской Female – женский Unknown – неизвестен
     */
    public void setGender(java.lang.String gender) {
        this.gender = gender;
    }


    /**
     * Gets the resident value for this PersonInfo_Type.
     * 
     * @return resident   * Резидент или нет
     */
    public java.lang.Boolean getResident() {
        return resident;
    }


    /**
     * Sets the resident value for this PersonInfo_Type.
     * 
     * @param resident   * Резидент или нет
     */
    public void setResident(java.lang.Boolean resident) {
        this.resident = resident;
    }


    /**
     * Gets the personName value for this PersonInfo_Type.
     * 
     * @return personName   * ФИО клиента
     */
    public com.rssl.phizic.test.wsgateclient.mdm.generated.PersonName_Type getPersonName() {
        return personName;
    }


    /**
     * Sets the personName value for this PersonInfo_Type.
     * 
     * @param personName   * ФИО клиента
     */
    public void setPersonName(com.rssl.phizic.test.wsgateclient.mdm.generated.PersonName_Type personName) {
        this.personName = personName;
    }


    /**
     * Gets the identityCard value for this PersonInfo_Type.
     * 
     * @return identityCard   * Удостоверение личности.
     */
    public com.rssl.phizic.test.wsgateclient.mdm.generated.IdentityCard_Type getIdentityCard() {
        return identityCard;
    }


    /**
     * Sets the identityCard value for this PersonInfo_Type.
     * 
     * @param identityCard   * Удостоверение личности.
     */
    public void setIdentityCard(com.rssl.phizic.test.wsgateclient.mdm.generated.IdentityCard_Type identityCard) {
        this.identityCard = identityCard;
    }


    /**
     * Gets the contactInfo value for this PersonInfo_Type.
     * 
     * @return contactInfo   * Контактная информация
     */
    public com.rssl.phizic.test.wsgateclient.mdm.generated.ContactInfo_Type getContactInfo() {
        return contactInfo;
    }


    /**
     * Sets the contactInfo value for this PersonInfo_Type.
     * 
     * @param contactInfo   * Контактная информация
     */
    public void setContactInfo(com.rssl.phizic.test.wsgateclient.mdm.generated.ContactInfo_Type contactInfo) {
        this.contactInfo = contactInfo;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof PersonInfo_Type)) return false;
        PersonInfo_Type other = (PersonInfo_Type) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.birthday==null && other.getBirthday()==null) || 
             (this.birthday!=null &&
              this.birthday.equals(other.getBirthday()))) &&
            ((this.birthPlace==null && other.getBirthPlace()==null) || 
             (this.birthPlace!=null &&
              this.birthPlace.equals(other.getBirthPlace()))) &&
            ((this.taxId==null && other.getTaxId()==null) || 
             (this.taxId!=null &&
              this.taxId.equals(other.getTaxId()))) &&
            ((this.citizenship==null && other.getCitizenship()==null) || 
             (this.citizenship!=null &&
              this.citizenship.equals(other.getCitizenship()))) &&
            ((this.additionalInfo==null && other.getAdditionalInfo()==null) || 
             (this.additionalInfo!=null &&
              this.additionalInfo.equals(other.getAdditionalInfo()))) &&
            ((this.personRole==null && other.getPersonRole()==null) || 
             (this.personRole!=null &&
              this.personRole.equals(other.getPersonRole()))) &&
            ((this.gender==null && other.getGender()==null) || 
             (this.gender!=null &&
              this.gender.equals(other.getGender()))) &&
            ((this.resident==null && other.getResident()==null) || 
             (this.resident!=null &&
              this.resident.equals(other.getResident()))) &&
            ((this.personName==null && other.getPersonName()==null) || 
             (this.personName!=null &&
              this.personName.equals(other.getPersonName()))) &&
            ((this.identityCard==null && other.getIdentityCard()==null) || 
             (this.identityCard!=null &&
              this.identityCard.equals(other.getIdentityCard()))) &&
            ((this.contactInfo==null && other.getContactInfo()==null) || 
             (this.contactInfo!=null &&
              this.contactInfo.equals(other.getContactInfo())));
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
        if (getBirthday() != null) {
            _hashCode += getBirthday().hashCode();
        }
        if (getBirthPlace() != null) {
            _hashCode += getBirthPlace().hashCode();
        }
        if (getTaxId() != null) {
            _hashCode += getTaxId().hashCode();
        }
        if (getCitizenship() != null) {
            _hashCode += getCitizenship().hashCode();
        }
        if (getAdditionalInfo() != null) {
            _hashCode += getAdditionalInfo().hashCode();
        }
        if (getPersonRole() != null) {
            _hashCode += getPersonRole().hashCode();
        }
        if (getGender() != null) {
            _hashCode += getGender().hashCode();
        }
        if (getResident() != null) {
            _hashCode += getResident().hashCode();
        }
        if (getPersonName() != null) {
            _hashCode += getPersonName().hashCode();
        }
        if (getIdentityCard() != null) {
            _hashCode += getIdentityCard().hashCode();
        }
        if (getContactInfo() != null) {
            _hashCode += getContactInfo().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(PersonInfo_Type.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://sbrf.srb.ru/erib/mdm", "PersonInfo_Type"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("birthday");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.srb.ru/erib/mdm", "Birthday"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.srb.ru/erib/mdm", "Date"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("birthPlace");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.srb.ru/erib/mdm", "BirthPlace"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("taxId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.srb.ru/erib/mdm", "TaxId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.srb.ru/erib/mdm", "String"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("citizenship");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.srb.ru/erib/mdm", "Citizenship"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("additionalInfo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.srb.ru/erib/mdm", "AdditionalInfo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("personRole");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.srb.ru/erib/mdm", "PersonRole"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.srb.ru/erib/mdm", "Long"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("gender");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.srb.ru/erib/mdm", "Gender"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.srb.ru/erib/mdm", "Gender_Type"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("resident");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.srb.ru/erib/mdm", "Resident"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("personName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.srb.ru/erib/mdm", "PersonName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.srb.ru/erib/mdm", "PersonName_Type"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("identityCard");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.srb.ru/erib/mdm", "IdentityCard"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.srb.ru/erib/mdm", "IdentityCard_Type"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("contactInfo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.srb.ru/erib/mdm", "ContactInfo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.srb.ru/erib/mdm", "ContactInfo_Type"));
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
