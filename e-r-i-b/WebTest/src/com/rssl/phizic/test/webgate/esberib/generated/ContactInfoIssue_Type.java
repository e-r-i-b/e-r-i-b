/**
 * ContactInfoIssue_Type.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.test.webgate.esberib.generated;


/**
 * Контактная информация
 */
public class ContactInfoIssue_Type  implements java.io.Serializable {
    /* Телефонные номера */
    private java.lang.String[] phoneNum;

    /* Адрес электронной почты */
    private java.lang.String emailAddr;

    /* Адрес Web сайта */
    private java.lang.String URL;

    /* Список почтовых адресов */
    private com.rssl.phizic.test.webgate.esberib.generated.FullAddress_Type[] fullAddress;

    /* Способ отправки отчетов по счету карты Тип доставки сообщений.
     * Возможные значения:
     * E - e-mail
     * N - no e-mail\mail
     * S - sbrf e-mail
     * P – mail
     * I - e-mail/mail */
    private com.rssl.phizic.test.webgate.esberib.generated.MessageDeliveryType_Type messageDeliveryType;

    public ContactInfoIssue_Type() {
    }

    public ContactInfoIssue_Type(
           java.lang.String[] phoneNum,
           java.lang.String emailAddr,
           java.lang.String URL,
           com.rssl.phizic.test.webgate.esberib.generated.FullAddress_Type[] fullAddress,
           com.rssl.phizic.test.webgate.esberib.generated.MessageDeliveryType_Type messageDeliveryType) {
           this.phoneNum = phoneNum;
           this.emailAddr = emailAddr;
           this.URL = URL;
           this.fullAddress = fullAddress;
           this.messageDeliveryType = messageDeliveryType;
    }


    /**
     * Gets the phoneNum value for this ContactInfoIssue_Type.
     * 
     * @return phoneNum   * Телефонные номера
     */
    public java.lang.String[] getPhoneNum() {
        return phoneNum;
    }


    /**
     * Sets the phoneNum value for this ContactInfoIssue_Type.
     * 
     * @param phoneNum   * Телефонные номера
     */
    public void setPhoneNum(java.lang.String[] phoneNum) {
        this.phoneNum = phoneNum;
    }

    public java.lang.String getPhoneNum(int i) {
        return this.phoneNum[i];
    }

    public void setPhoneNum(int i, java.lang.String _value) {
        this.phoneNum[i] = _value;
    }


    /**
     * Gets the emailAddr value for this ContactInfoIssue_Type.
     * 
     * @return emailAddr   * Адрес электронной почты
     */
    public java.lang.String getEmailAddr() {
        return emailAddr;
    }


    /**
     * Sets the emailAddr value for this ContactInfoIssue_Type.
     * 
     * @param emailAddr   * Адрес электронной почты
     */
    public void setEmailAddr(java.lang.String emailAddr) {
        this.emailAddr = emailAddr;
    }


    /**
     * Gets the URL value for this ContactInfoIssue_Type.
     * 
     * @return URL   * Адрес Web сайта
     */
    public java.lang.String getURL() {
        return URL;
    }


    /**
     * Sets the URL value for this ContactInfoIssue_Type.
     * 
     * @param URL   * Адрес Web сайта
     */
    public void setURL(java.lang.String URL) {
        this.URL = URL;
    }


    /**
     * Gets the fullAddress value for this ContactInfoIssue_Type.
     * 
     * @return fullAddress   * Список почтовых адресов
     */
    public com.rssl.phizic.test.webgate.esberib.generated.FullAddress_Type[] getFullAddress() {
        return fullAddress;
    }


    /**
     * Sets the fullAddress value for this ContactInfoIssue_Type.
     * 
     * @param fullAddress   * Список почтовых адресов
     */
    public void setFullAddress(com.rssl.phizic.test.webgate.esberib.generated.FullAddress_Type[] fullAddress) {
        this.fullAddress = fullAddress;
    }

    public com.rssl.phizic.test.webgate.esberib.generated.FullAddress_Type getFullAddress(int i) {
        return this.fullAddress[i];
    }

    public void setFullAddress(int i, com.rssl.phizic.test.webgate.esberib.generated.FullAddress_Type _value) {
        this.fullAddress[i] = _value;
    }


    /**
     * Gets the messageDeliveryType value for this ContactInfoIssue_Type.
     * 
     * @return messageDeliveryType   * Способ отправки отчетов по счету карты Тип доставки сообщений.
     * Возможные значения:
     * E - e-mail
     * N - no e-mail\mail
     * S - sbrf e-mail
     * P – mail
     * I - e-mail/mail
     */
    public com.rssl.phizic.test.webgate.esberib.generated.MessageDeliveryType_Type getMessageDeliveryType() {
        return messageDeliveryType;
    }


    /**
     * Sets the messageDeliveryType value for this ContactInfoIssue_Type.
     * 
     * @param messageDeliveryType   * Способ отправки отчетов по счету карты Тип доставки сообщений.
     * Возможные значения:
     * E - e-mail
     * N - no e-mail\mail
     * S - sbrf e-mail
     * P – mail
     * I - e-mail/mail
     */
    public void setMessageDeliveryType(com.rssl.phizic.test.webgate.esberib.generated.MessageDeliveryType_Type messageDeliveryType) {
        this.messageDeliveryType = messageDeliveryType;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ContactInfoIssue_Type)) return false;
        ContactInfoIssue_Type other = (ContactInfoIssue_Type) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.phoneNum==null && other.getPhoneNum()==null) || 
             (this.phoneNum!=null &&
              java.util.Arrays.equals(this.phoneNum, other.getPhoneNum()))) &&
            ((this.emailAddr==null && other.getEmailAddr()==null) || 
             (this.emailAddr!=null &&
              this.emailAddr.equals(other.getEmailAddr()))) &&
            ((this.URL==null && other.getURL()==null) || 
             (this.URL!=null &&
              this.URL.equals(other.getURL()))) &&
            ((this.fullAddress==null && other.getFullAddress()==null) || 
             (this.fullAddress!=null &&
              java.util.Arrays.equals(this.fullAddress, other.getFullAddress()))) &&
            ((this.messageDeliveryType==null && other.getMessageDeliveryType()==null) || 
             (this.messageDeliveryType!=null &&
              this.messageDeliveryType.equals(other.getMessageDeliveryType())));
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
        if (getPhoneNum() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getPhoneNum());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getPhoneNum(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getEmailAddr() != null) {
            _hashCode += getEmailAddr().hashCode();
        }
        if (getURL() != null) {
            _hashCode += getURL().hashCode();
        }
        if (getFullAddress() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getFullAddress());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getFullAddress(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getMessageDeliveryType() != null) {
            _hashCode += getMessageDeliveryType().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ContactInfoIssue_Type.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "ContactInfoIssue_Type"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("phoneNum");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "PhoneNum"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "PhoneNumber_Type"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("emailAddr");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "EmailAddr"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "NC"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("URL");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "URL"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "NC"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("fullAddress");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "FullAddress"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "FullAddress_Type"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("messageDeliveryType");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "MessageDeliveryType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "MessageDeliveryType_Type"));
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
