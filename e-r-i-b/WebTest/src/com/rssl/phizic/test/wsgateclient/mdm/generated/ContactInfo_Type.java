/**
 * ContactInfo_Type.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.test.wsgateclient.mdm.generated;

public class ContactInfo_Type  implements java.io.Serializable {
    /* Адрес электронной почты */
    private java.lang.String emailAddr;

    /* Тип доставки сообщений */
    private com.rssl.phizic.test.wsgateclient.mdm.generated.MessageDeliveryType_Type messageDeliveryType;

    /* Блок нетипизированных контактных данных */
    private com.rssl.phizic.test.wsgateclient.mdm.generated.ContactData_Type[] contactData;

    /* Полный адрес */
    private com.rssl.phizic.test.wsgateclient.mdm.generated.FullAddress_Type[] postAddr;

    public ContactInfo_Type() {
    }

    public ContactInfo_Type(
           java.lang.String emailAddr,
           com.rssl.phizic.test.wsgateclient.mdm.generated.MessageDeliveryType_Type messageDeliveryType,
           com.rssl.phizic.test.wsgateclient.mdm.generated.ContactData_Type[] contactData,
           com.rssl.phizic.test.wsgateclient.mdm.generated.FullAddress_Type[] postAddr) {
           this.emailAddr = emailAddr;
           this.messageDeliveryType = messageDeliveryType;
           this.contactData = contactData;
           this.postAddr = postAddr;
    }


    /**
     * Gets the emailAddr value for this ContactInfo_Type.
     * 
     * @return emailAddr   * Адрес электронной почты
     */
    public java.lang.String getEmailAddr() {
        return emailAddr;
    }


    /**
     * Sets the emailAddr value for this ContactInfo_Type.
     * 
     * @param emailAddr   * Адрес электронной почты
     */
    public void setEmailAddr(java.lang.String emailAddr) {
        this.emailAddr = emailAddr;
    }


    /**
     * Gets the messageDeliveryType value for this ContactInfo_Type.
     * 
     * @return messageDeliveryType   * Тип доставки сообщений
     */
    public com.rssl.phizic.test.wsgateclient.mdm.generated.MessageDeliveryType_Type getMessageDeliveryType() {
        return messageDeliveryType;
    }


    /**
     * Sets the messageDeliveryType value for this ContactInfo_Type.
     * 
     * @param messageDeliveryType   * Тип доставки сообщений
     */
    public void setMessageDeliveryType(com.rssl.phizic.test.wsgateclient.mdm.generated.MessageDeliveryType_Type messageDeliveryType) {
        this.messageDeliveryType = messageDeliveryType;
    }


    /**
     * Gets the contactData value for this ContactInfo_Type.
     * 
     * @return contactData   * Блок нетипизированных контактных данных
     */
    public com.rssl.phizic.test.wsgateclient.mdm.generated.ContactData_Type[] getContactData() {
        return contactData;
    }


    /**
     * Sets the contactData value for this ContactInfo_Type.
     * 
     * @param contactData   * Блок нетипизированных контактных данных
     */
    public void setContactData(com.rssl.phizic.test.wsgateclient.mdm.generated.ContactData_Type[] contactData) {
        this.contactData = contactData;
    }

    public com.rssl.phizic.test.wsgateclient.mdm.generated.ContactData_Type getContactData(int i) {
        return this.contactData[i];
    }

    public void setContactData(int i, com.rssl.phizic.test.wsgateclient.mdm.generated.ContactData_Type _value) {
        this.contactData[i] = _value;
    }


    /**
     * Gets the postAddr value for this ContactInfo_Type.
     * 
     * @return postAddr   * Полный адрес
     */
    public com.rssl.phizic.test.wsgateclient.mdm.generated.FullAddress_Type[] getPostAddr() {
        return postAddr;
    }


    /**
     * Sets the postAddr value for this ContactInfo_Type.
     * 
     * @param postAddr   * Полный адрес
     */
    public void setPostAddr(com.rssl.phizic.test.wsgateclient.mdm.generated.FullAddress_Type[] postAddr) {
        this.postAddr = postAddr;
    }

    public com.rssl.phizic.test.wsgateclient.mdm.generated.FullAddress_Type getPostAddr(int i) {
        return this.postAddr[i];
    }

    public void setPostAddr(int i, com.rssl.phizic.test.wsgateclient.mdm.generated.FullAddress_Type _value) {
        this.postAddr[i] = _value;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ContactInfo_Type)) return false;
        ContactInfo_Type other = (ContactInfo_Type) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.emailAddr==null && other.getEmailAddr()==null) || 
             (this.emailAddr!=null &&
              this.emailAddr.equals(other.getEmailAddr()))) &&
            ((this.messageDeliveryType==null && other.getMessageDeliveryType()==null) || 
             (this.messageDeliveryType!=null &&
              this.messageDeliveryType.equals(other.getMessageDeliveryType()))) &&
            ((this.contactData==null && other.getContactData()==null) || 
             (this.contactData!=null &&
              java.util.Arrays.equals(this.contactData, other.getContactData()))) &&
            ((this.postAddr==null && other.getPostAddr()==null) || 
             (this.postAddr!=null &&
              java.util.Arrays.equals(this.postAddr, other.getPostAddr())));
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
        if (getEmailAddr() != null) {
            _hashCode += getEmailAddr().hashCode();
        }
        if (getMessageDeliveryType() != null) {
            _hashCode += getMessageDeliveryType().hashCode();
        }
        if (getContactData() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getContactData());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getContactData(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getPostAddr() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getPostAddr());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getPostAddr(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ContactInfo_Type.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://sbrf.srb.ru/erib/mdm", "ContactInfo_Type"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("emailAddr");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.srb.ru/erib/mdm", "EmailAddr"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("messageDeliveryType");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.srb.ru/erib/mdm", "MessageDeliveryType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.srb.ru/erib/mdm", "MessageDeliveryType_Type"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("contactData");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.srb.ru/erib/mdm", "ContactData"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.srb.ru/erib/mdm", "ContactData"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("postAddr");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.srb.ru/erib/mdm", "PostAddr"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.srb.ru/erib/mdm", "FullAddress_Type"));
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
