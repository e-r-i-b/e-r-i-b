/**
 * ContactInfo_Type.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizicgate.esberibgate.ws.generated;


/**
 * Контактная информация
 */
public class ContactInfo_Type  implements java.io.Serializable {
    /* Адрес электронной почты */
    private java.lang.String emailAddr;

    /* Тип доставки сообщений */
    private com.rssl.phizicgate.esberibgate.ws.generated.MessageDeliveryType_Type messageDeliveryType;

    /* Тип отчета */
    private java.lang.String reportOrderType;

    /* Блок нетипизированных контактных данных */
    private com.rssl.phizicgate.esberibgate.ws.generated.ContactData_Type[] contactData;

    /* Полный адрес */
    private com.rssl.phizicgate.esberibgate.ws.generated.FullAddress_Type[] postAddr;

    /* Формат отчета */
    private com.rssl.phizicgate.esberibgate.ws.generated.ReportDeliveryType_Type reportDeliveryType;

    /* Язык отчета */
    private com.rssl.phizicgate.esberibgate.ws.generated.ReportLangType_Type reportLangType;

    /* Способ доставки отчета */
    private com.rssl.phizicgate.esberibgate.ws.generated.ReportType_Type reportType;

    /* Полный адрес */
    private com.rssl.phizicgate.esberibgate.ws.generated.FullAddress_IssueCard_Type[] fullAddress;

    /* Телефон */
    private com.rssl.phizicgate.esberibgate.ws.generated.PhoneNum_Type[] phoneNum;

    public ContactInfo_Type() {
    }

    public ContactInfo_Type(
           java.lang.String emailAddr,
           com.rssl.phizicgate.esberibgate.ws.generated.MessageDeliveryType_Type messageDeliveryType,
           java.lang.String reportOrderType,
           com.rssl.phizicgate.esberibgate.ws.generated.ContactData_Type[] contactData,
           com.rssl.phizicgate.esberibgate.ws.generated.FullAddress_Type[] postAddr,
           com.rssl.phizicgate.esberibgate.ws.generated.ReportDeliveryType_Type reportDeliveryType,
           com.rssl.phizicgate.esberibgate.ws.generated.ReportLangType_Type reportLangType,
           com.rssl.phizicgate.esberibgate.ws.generated.ReportType_Type reportType,
           com.rssl.phizicgate.esberibgate.ws.generated.FullAddress_IssueCard_Type[] fullAddress,
           com.rssl.phizicgate.esberibgate.ws.generated.PhoneNum_Type[] phoneNum) {
           this.emailAddr = emailAddr;
           this.messageDeliveryType = messageDeliveryType;
           this.reportOrderType = reportOrderType;
           this.contactData = contactData;
           this.postAddr = postAddr;
           this.reportDeliveryType = reportDeliveryType;
           this.reportLangType = reportLangType;
           this.reportType = reportType;
           this.fullAddress = fullAddress;
           this.phoneNum = phoneNum;
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
    public com.rssl.phizicgate.esberibgate.ws.generated.MessageDeliveryType_Type getMessageDeliveryType() {
        return messageDeliveryType;
    }


    /**
     * Sets the messageDeliveryType value for this ContactInfo_Type.
     * 
     * @param messageDeliveryType   * Тип доставки сообщений
     */
    public void setMessageDeliveryType(com.rssl.phizicgate.esberibgate.ws.generated.MessageDeliveryType_Type messageDeliveryType) {
        this.messageDeliveryType = messageDeliveryType;
    }


    /**
     * Gets the reportOrderType value for this ContactInfo_Type.
     * 
     * @return reportOrderType   * Тип отчета
     */
    public java.lang.String getReportOrderType() {
        return reportOrderType;
    }


    /**
     * Sets the reportOrderType value for this ContactInfo_Type.
     * 
     * @param reportOrderType   * Тип отчета
     */
    public void setReportOrderType(java.lang.String reportOrderType) {
        this.reportOrderType = reportOrderType;
    }


    /**
     * Gets the contactData value for this ContactInfo_Type.
     * 
     * @return contactData   * Блок нетипизированных контактных данных
     */
    public com.rssl.phizicgate.esberibgate.ws.generated.ContactData_Type[] getContactData() {
        return contactData;
    }


    /**
     * Sets the contactData value for this ContactInfo_Type.
     * 
     * @param contactData   * Блок нетипизированных контактных данных
     */
    public void setContactData(com.rssl.phizicgate.esberibgate.ws.generated.ContactData_Type[] contactData) {
        this.contactData = contactData;
    }

    public com.rssl.phizicgate.esberibgate.ws.generated.ContactData_Type getContactData(int i) {
        return this.contactData[i];
    }

    public void setContactData(int i, com.rssl.phizicgate.esberibgate.ws.generated.ContactData_Type _value) {
        this.contactData[i] = _value;
    }


    /**
     * Gets the postAddr value for this ContactInfo_Type.
     * 
     * @return postAddr   * Полный адрес
     */
    public com.rssl.phizicgate.esberibgate.ws.generated.FullAddress_Type[] getPostAddr() {
        return postAddr;
    }


    /**
     * Sets the postAddr value for this ContactInfo_Type.
     * 
     * @param postAddr   * Полный адрес
     */
    public void setPostAddr(com.rssl.phizicgate.esberibgate.ws.generated.FullAddress_Type[] postAddr) {
        this.postAddr = postAddr;
    }

    public com.rssl.phizicgate.esberibgate.ws.generated.FullAddress_Type getPostAddr(int i) {
        return this.postAddr[i];
    }

    public void setPostAddr(int i, com.rssl.phizicgate.esberibgate.ws.generated.FullAddress_Type _value) {
        this.postAddr[i] = _value;
    }


    /**
     * Gets the reportDeliveryType value for this ContactInfo_Type.
     * 
     * @return reportDeliveryType   * Формат отчета
     */
    public com.rssl.phizicgate.esberibgate.ws.generated.ReportDeliveryType_Type getReportDeliveryType() {
        return reportDeliveryType;
    }


    /**
     * Sets the reportDeliveryType value for this ContactInfo_Type.
     * 
     * @param reportDeliveryType   * Формат отчета
     */
    public void setReportDeliveryType(com.rssl.phizicgate.esberibgate.ws.generated.ReportDeliveryType_Type reportDeliveryType) {
        this.reportDeliveryType = reportDeliveryType;
    }


    /**
     * Gets the reportLangType value for this ContactInfo_Type.
     * 
     * @return reportLangType   * Язык отчета
     */
    public com.rssl.phizicgate.esberibgate.ws.generated.ReportLangType_Type getReportLangType() {
        return reportLangType;
    }


    /**
     * Sets the reportLangType value for this ContactInfo_Type.
     * 
     * @param reportLangType   * Язык отчета
     */
    public void setReportLangType(com.rssl.phizicgate.esberibgate.ws.generated.ReportLangType_Type reportLangType) {
        this.reportLangType = reportLangType;
    }


    /**
     * Gets the reportType value for this ContactInfo_Type.
     * 
     * @return reportType   * Способ доставки отчета
     */
    public com.rssl.phizicgate.esberibgate.ws.generated.ReportType_Type getReportType() {
        return reportType;
    }


    /**
     * Sets the reportType value for this ContactInfo_Type.
     * 
     * @param reportType   * Способ доставки отчета
     */
    public void setReportType(com.rssl.phizicgate.esberibgate.ws.generated.ReportType_Type reportType) {
        this.reportType = reportType;
    }


    /**
     * Gets the fullAddress value for this ContactInfo_Type.
     * 
     * @return fullAddress   * Полный адрес
     */
    public com.rssl.phizicgate.esberibgate.ws.generated.FullAddress_IssueCard_Type[] getFullAddress() {
        return fullAddress;
    }


    /**
     * Sets the fullAddress value for this ContactInfo_Type.
     * 
     * @param fullAddress   * Полный адрес
     */
    public void setFullAddress(com.rssl.phizicgate.esberibgate.ws.generated.FullAddress_IssueCard_Type[] fullAddress) {
        this.fullAddress = fullAddress;
    }

    public com.rssl.phizicgate.esberibgate.ws.generated.FullAddress_IssueCard_Type getFullAddress(int i) {
        return this.fullAddress[i];
    }

    public void setFullAddress(int i, com.rssl.phizicgate.esberibgate.ws.generated.FullAddress_IssueCard_Type _value) {
        this.fullAddress[i] = _value;
    }


    /**
     * Gets the phoneNum value for this ContactInfo_Type.
     * 
     * @return phoneNum   * Телефон
     */
    public com.rssl.phizicgate.esberibgate.ws.generated.PhoneNum_Type[] getPhoneNum() {
        return phoneNum;
    }


    /**
     * Sets the phoneNum value for this ContactInfo_Type.
     * 
     * @param phoneNum   * Телефон
     */
    public void setPhoneNum(com.rssl.phizicgate.esberibgate.ws.generated.PhoneNum_Type[] phoneNum) {
        this.phoneNum = phoneNum;
    }

    public com.rssl.phizicgate.esberibgate.ws.generated.PhoneNum_Type getPhoneNum(int i) {
        return this.phoneNum[i];
    }

    public void setPhoneNum(int i, com.rssl.phizicgate.esberibgate.ws.generated.PhoneNum_Type _value) {
        this.phoneNum[i] = _value;
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
            ((this.reportOrderType==null && other.getReportOrderType()==null) || 
             (this.reportOrderType!=null &&
              this.reportOrderType.equals(other.getReportOrderType()))) &&
            ((this.contactData==null && other.getContactData()==null) || 
             (this.contactData!=null &&
              java.util.Arrays.equals(this.contactData, other.getContactData()))) &&
            ((this.postAddr==null && other.getPostAddr()==null) || 
             (this.postAddr!=null &&
              java.util.Arrays.equals(this.postAddr, other.getPostAddr()))) &&
            ((this.reportDeliveryType==null && other.getReportDeliveryType()==null) || 
             (this.reportDeliveryType!=null &&
              this.reportDeliveryType.equals(other.getReportDeliveryType()))) &&
            ((this.reportLangType==null && other.getReportLangType()==null) || 
             (this.reportLangType!=null &&
              this.reportLangType.equals(other.getReportLangType()))) &&
            ((this.reportType==null && other.getReportType()==null) || 
             (this.reportType!=null &&
              this.reportType.equals(other.getReportType()))) &&
            ((this.fullAddress==null && other.getFullAddress()==null) || 
             (this.fullAddress!=null &&
              java.util.Arrays.equals(this.fullAddress, other.getFullAddress()))) &&
            ((this.phoneNum==null && other.getPhoneNum()==null) || 
             (this.phoneNum!=null &&
              java.util.Arrays.equals(this.phoneNum, other.getPhoneNum())));
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
        if (getReportOrderType() != null) {
            _hashCode += getReportOrderType().hashCode();
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
        if (getReportDeliveryType() != null) {
            _hashCode += getReportDeliveryType().hashCode();
        }
        if (getReportLangType() != null) {
            _hashCode += getReportLangType().hashCode();
        }
        if (getReportType() != null) {
            _hashCode += getReportType().hashCode();
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
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ContactInfo_Type.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "ContactInfo_Type"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("emailAddr");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "EmailAddr"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("messageDeliveryType");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "MessageDeliveryType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "MessageDeliveryType_Type"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("reportOrderType");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "ReportOrderType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "String"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("contactData");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "ContactData"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "ContactData"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("postAddr");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "PostAddr"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "FullAddress_Type"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("reportDeliveryType");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "ReportDeliveryType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "ReportDeliveryType_Type"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("reportLangType");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "ReportLangType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "ReportLangType_Type"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("reportType");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "ReportType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "ReportType_Type"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("fullAddress");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "FullAddress"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "FullAddress_IssueCard_Type"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("phoneNum");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "PhoneNum"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "PhoneNum_Type"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        elemField.setMaxOccursUnbounded(true);
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
