/**
 * PlasticInfoType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizicgate.esberibgate.ws.generated;


/**
 * Информация о клиенте, которая печатается на карте
 */
public class PlasticInfoType  implements java.io.Serializable {
    /* Обращение к клиенту */
    private java.lang.String personTitle;

    /* Фамилия клиента для записи на пластиковую карту (только латинские
     * буквы до 32 символов) */
    private java.lang.String lastName;

    /* Имя клиента для записи на пластиковую карту (только латинские
     * буквы до 32 символов) */
    private java.lang.String firstName;

    /* Название компании */
    private java.lang.String companyName;

    public PlasticInfoType() {
    }

    public PlasticInfoType(
           java.lang.String personTitle,
           java.lang.String lastName,
           java.lang.String firstName,
           java.lang.String companyName) {
           this.personTitle = personTitle;
           this.lastName = lastName;
           this.firstName = firstName;
           this.companyName = companyName;
    }


    /**
     * Gets the personTitle value for this PlasticInfoType.
     * 
     * @return personTitle   * Обращение к клиенту
     */
    public java.lang.String getPersonTitle() {
        return personTitle;
    }


    /**
     * Sets the personTitle value for this PlasticInfoType.
     * 
     * @param personTitle   * Обращение к клиенту
     */
    public void setPersonTitle(java.lang.String personTitle) {
        this.personTitle = personTitle;
    }


    /**
     * Gets the lastName value for this PlasticInfoType.
     * 
     * @return lastName   * Фамилия клиента для записи на пластиковую карту (только латинские
     * буквы до 32 символов)
     */
    public java.lang.String getLastName() {
        return lastName;
    }


    /**
     * Sets the lastName value for this PlasticInfoType.
     * 
     * @param lastName   * Фамилия клиента для записи на пластиковую карту (только латинские
     * буквы до 32 символов)
     */
    public void setLastName(java.lang.String lastName) {
        this.lastName = lastName;
    }


    /**
     * Gets the firstName value for this PlasticInfoType.
     * 
     * @return firstName   * Имя клиента для записи на пластиковую карту (только латинские
     * буквы до 32 символов)
     */
    public java.lang.String getFirstName() {
        return firstName;
    }


    /**
     * Sets the firstName value for this PlasticInfoType.
     * 
     * @param firstName   * Имя клиента для записи на пластиковую карту (только латинские
     * буквы до 32 символов)
     */
    public void setFirstName(java.lang.String firstName) {
        this.firstName = firstName;
    }


    /**
     * Gets the companyName value for this PlasticInfoType.
     * 
     * @return companyName   * Название компании
     */
    public java.lang.String getCompanyName() {
        return companyName;
    }


    /**
     * Sets the companyName value for this PlasticInfoType.
     * 
     * @param companyName   * Название компании
     */
    public void setCompanyName(java.lang.String companyName) {
        this.companyName = companyName;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof PlasticInfoType)) return false;
        PlasticInfoType other = (PlasticInfoType) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.personTitle==null && other.getPersonTitle()==null) || 
             (this.personTitle!=null &&
              this.personTitle.equals(other.getPersonTitle()))) &&
            ((this.lastName==null && other.getLastName()==null) || 
             (this.lastName!=null &&
              this.lastName.equals(other.getLastName()))) &&
            ((this.firstName==null && other.getFirstName()==null) || 
             (this.firstName!=null &&
              this.firstName.equals(other.getFirstName()))) &&
            ((this.companyName==null && other.getCompanyName()==null) || 
             (this.companyName!=null &&
              this.companyName.equals(other.getCompanyName())));
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
        if (getPersonTitle() != null) {
            _hashCode += getPersonTitle().hashCode();
        }
        if (getLastName() != null) {
            _hashCode += getLastName().hashCode();
        }
        if (getFirstName() != null) {
            _hashCode += getFirstName().hashCode();
        }
        if (getCompanyName() != null) {
            _hashCode += getCompanyName().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(PlasticInfoType.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "PlasticInfoType"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("personTitle");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "PersonTitle"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("lastName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "LastName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "LastName_Type"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("firstName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "FirstName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "FirstName_Type"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("companyName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "CompanyName"));
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
