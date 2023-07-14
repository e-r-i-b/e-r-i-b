/**
 * ContactData_Type.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.test.wsgateclient.mdm.generated;

public class ContactData_Type  implements java.io.Serializable {
    /* Тип контактной информации */
    private java.lang.String contactType;

    /* Значение контактной информации */
    private java.lang.String contactNum;

    /* Наименование мобильного оператора */
    private java.lang.String phoneOperName;

    public ContactData_Type() {
    }

    public ContactData_Type(
           java.lang.String contactType,
           java.lang.String contactNum,
           java.lang.String phoneOperName) {
           this.contactType = contactType;
           this.contactNum = contactNum;
           this.phoneOperName = phoneOperName;
    }


    /**
     * Gets the contactType value for this ContactData_Type.
     * 
     * @return contactType   * Тип контактной информации
     */
    public java.lang.String getContactType() {
        return contactType;
    }


    /**
     * Sets the contactType value for this ContactData_Type.
     * 
     * @param contactType   * Тип контактной информации
     */
    public void setContactType(java.lang.String contactType) {
        this.contactType = contactType;
    }


    /**
     * Gets the contactNum value for this ContactData_Type.
     * 
     * @return contactNum   * Значение контактной информации
     */
    public java.lang.String getContactNum() {
        return contactNum;
    }


    /**
     * Sets the contactNum value for this ContactData_Type.
     * 
     * @param contactNum   * Значение контактной информации
     */
    public void setContactNum(java.lang.String contactNum) {
        this.contactNum = contactNum;
    }


    /**
     * Gets the phoneOperName value for this ContactData_Type.
     * 
     * @return phoneOperName   * Наименование мобильного оператора
     */
    public java.lang.String getPhoneOperName() {
        return phoneOperName;
    }


    /**
     * Sets the phoneOperName value for this ContactData_Type.
     * 
     * @param phoneOperName   * Наименование мобильного оператора
     */
    public void setPhoneOperName(java.lang.String phoneOperName) {
        this.phoneOperName = phoneOperName;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ContactData_Type)) return false;
        ContactData_Type other = (ContactData_Type) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.contactType==null && other.getContactType()==null) || 
             (this.contactType!=null &&
              this.contactType.equals(other.getContactType()))) &&
            ((this.contactNum==null && other.getContactNum()==null) || 
             (this.contactNum!=null &&
              this.contactNum.equals(other.getContactNum()))) &&
            ((this.phoneOperName==null && other.getPhoneOperName()==null) || 
             (this.phoneOperName!=null &&
              this.phoneOperName.equals(other.getPhoneOperName())));
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
        if (getContactType() != null) {
            _hashCode += getContactType().hashCode();
        }
        if (getContactNum() != null) {
            _hashCode += getContactNum().hashCode();
        }
        if (getPhoneOperName() != null) {
            _hashCode += getPhoneOperName().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ContactData_Type.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://sbrf.srb.ru/erib/mdm", "ContactData_Type"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("contactType");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.srb.ru/erib/mdm", "ContactType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("contactNum");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.srb.ru/erib/mdm", "ContactNum"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("phoneOperName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.srb.ru/erib/mdm", "PhoneOperName"));
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
