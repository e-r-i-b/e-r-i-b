/**
 * OfferType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.business.etsm.offer.service.generated;

public class OfferType  implements java.io.Serializable {
    /* Статус оферты (ACTIVE - действующая, DELETED - после того,
     * как клиент подтвердил оферту или отказался от нее) */
    private java.lang.String offerStatus;

    private java.util.Calendar offerDate;

    /* Идентификатор заявки в ETSM */
    private java.lang.String applicationNumber;

    /* Cчет зачислени/Номер карты */
    private java.lang.String accauntNumber;

    /* Тип счета зачисления(Карта - 1/Вклад - 0) */
    private java.lang.String typeOfIssue;

    /* Категория клиента:
     *                         «A» - «Сорудник банка»
     *                         «A1» - «Сотрудник дочерней организации банка»
     * «B» - Сотрудники аккредитованных организаций-участники зарплатных
     * проектов
     *                         «B1» - Участники зарплатных проектов (не сотрудники
     * аккредитованных компаний)
     *                         «С» - Сотрудники аккредитованных организаций
     * – не участники зарплатных проектов
     *                         «С1» - Участники зарплатных проектов(не сотрудники
     * аккредитованныху омпаний)
     *                         «D» - Прочие клиенты
     *                         «D1» - Прочие клиенты
     *                         «D2» - Прочие клиенты
     *                         «E» - Клиенты, получающие пенсию в Сбербанке
     * Росссии
     *                         «N» - Клиент с неподтвержденной занятостью */
    private java.lang.String clientCategory;

    private com.rssl.phizic.business.etsm.offer.service.generated.AlternativeType[] alternative;

    /* Код типа продукта */
    private java.lang.String productTypeCode;

    /* Код продукта */
    private java.lang.String productCode;

    /* Код суб продукта */
    private java.lang.String subProductCode;

    /* Валюта кредита */
    private com.rssl.phizic.business.etsm.offer.service.generated.OfferTypeCurrency currency;

    /* Подразделение для оформления заявки в формате: тер. банк (2
     * цифры), ОСБ (4 цифры), ВСП (5 цифр) */
    private java.lang.String department;

    /* Счетчик количества отображений оферты */
    private java.lang.Long visibilityCounter;

    public OfferType() {
    }

    public OfferType(
           java.lang.String offerStatus,
           java.util.Calendar offerDate,
           java.lang.String applicationNumber,
           java.lang.String accauntNumber,
           java.lang.String typeOfIssue,
           java.lang.String clientCategory,
           com.rssl.phizic.business.etsm.offer.service.generated.AlternativeType[] alternative,
           java.lang.String productTypeCode,
           java.lang.String productCode,
           java.lang.String subProductCode,
           com.rssl.phizic.business.etsm.offer.service.generated.OfferTypeCurrency currency,
           java.lang.String department,
           java.lang.Long visibilityCounter) {
           this.offerStatus = offerStatus;
           this.offerDate = offerDate;
           this.applicationNumber = applicationNumber;
           this.accauntNumber = accauntNumber;
           this.typeOfIssue = typeOfIssue;
           this.clientCategory = clientCategory;
           this.alternative = alternative;
           this.productTypeCode = productTypeCode;
           this.productCode = productCode;
           this.subProductCode = subProductCode;
           this.currency = currency;
           this.department = department;
           this.visibilityCounter = visibilityCounter;
    }


    /**
     * Gets the offerStatus value for this OfferType.
     * 
     * @return offerStatus   * Статус оферты (ACTIVE - действующая, DELETED - после того,
     * как клиент подтвердил оферту или отказался от нее)
     */
    public java.lang.String getOfferStatus() {
        return offerStatus;
    }


    /**
     * Sets the offerStatus value for this OfferType.
     * 
     * @param offerStatus   * Статус оферты (ACTIVE - действующая, DELETED - после того,
     * как клиент подтвердил оферту или отказался от нее)
     */
    public void setOfferStatus(java.lang.String offerStatus) {
        this.offerStatus = offerStatus;
    }


    /**
     * Gets the offerDate value for this OfferType.
     * 
     * @return offerDate
     */
    public java.util.Calendar getOfferDate() {
        return offerDate;
    }


    /**
     * Sets the offerDate value for this OfferType.
     * 
     * @param offerDate
     */
    public void setOfferDate(java.util.Calendar offerDate) {
        this.offerDate = offerDate;
    }


    /**
     * Gets the applicationNumber value for this OfferType.
     * 
     * @return applicationNumber   * Идентификатор заявки в ETSM
     */
    public java.lang.String getApplicationNumber() {
        return applicationNumber;
    }


    /**
     * Sets the applicationNumber value for this OfferType.
     * 
     * @param applicationNumber   * Идентификатор заявки в ETSM
     */
    public void setApplicationNumber(java.lang.String applicationNumber) {
        this.applicationNumber = applicationNumber;
    }


    /**
     * Gets the accauntNumber value for this OfferType.
     * 
     * @return accauntNumber   * Cчет зачислени/Номер карты
     */
    public java.lang.String getAccauntNumber() {
        return accauntNumber;
    }


    /**
     * Sets the accauntNumber value for this OfferType.
     * 
     * @param accauntNumber   * Cчет зачислени/Номер карты
     */
    public void setAccauntNumber(java.lang.String accauntNumber) {
        this.accauntNumber = accauntNumber;
    }


    /**
     * Gets the typeOfIssue value for this OfferType.
     * 
     * @return typeOfIssue   * Тип счета зачисления(Карта - 1/Вклад - 0)
     */
    public java.lang.String getTypeOfIssue() {
        return typeOfIssue;
    }


    /**
     * Sets the typeOfIssue value for this OfferType.
     * 
     * @param typeOfIssue   * Тип счета зачисления(Карта - 1/Вклад - 0)
     */
    public void setTypeOfIssue(java.lang.String typeOfIssue) {
        this.typeOfIssue = typeOfIssue;
    }


    /**
     * Gets the clientCategory value for this OfferType.
     * 
     * @return clientCategory   * Категория клиента:
     *                         «A» - «Сорудник банка»
     *                         «A1» - «Сотрудник дочерней организации банка»
     * «B» - Сотрудники аккредитованных организаций-участники зарплатных
     * проектов
     *                         «B1» - Участники зарплатных проектов (не сотрудники
     * аккредитованных компаний)
     *                         «С» - Сотрудники аккредитованных организаций
     * – не участники зарплатных проектов
     *                         «С1» - Участники зарплатных проектов(не сотрудники
     * аккредитованныху омпаний)
     *                         «D» - Прочие клиенты
     *                         «D1» - Прочие клиенты
     *                         «D2» - Прочие клиенты
     *                         «E» - Клиенты, получающие пенсию в Сбербанке
     * Росссии
     *                         «N» - Клиент с неподтвержденной занятостью
     */
    public java.lang.String getClientCategory() {
        return clientCategory;
    }


    /**
     * Sets the clientCategory value for this OfferType.
     * 
     * @param clientCategory   * Категория клиента:
     *                         «A» - «Сорудник банка»
     *                         «A1» - «Сотрудник дочерней организации банка»
     * «B» - Сотрудники аккредитованных организаций-участники зарплатных
     * проектов
     *                         «B1» - Участники зарплатных проектов (не сотрудники
     * аккредитованных компаний)
     *                         «С» - Сотрудники аккредитованных организаций
     * – не участники зарплатных проектов
     *                         «С1» - Участники зарплатных проектов(не сотрудники
     * аккредитованныху омпаний)
     *                         «D» - Прочие клиенты
     *                         «D1» - Прочие клиенты
     *                         «D2» - Прочие клиенты
     *                         «E» - Клиенты, получающие пенсию в Сбербанке
     * Росссии
     *                         «N» - Клиент с неподтвержденной занятостью
     */
    public void setClientCategory(java.lang.String clientCategory) {
        this.clientCategory = clientCategory;
    }


    /**
     * Gets the alternative value for this OfferType.
     * 
     * @return alternative
     */
    public com.rssl.phizic.business.etsm.offer.service.generated.AlternativeType[] getAlternative() {
        return alternative;
    }


    /**
     * Sets the alternative value for this OfferType.
     * 
     * @param alternative
     */
    public void setAlternative(com.rssl.phizic.business.etsm.offer.service.generated.AlternativeType[] alternative) {
        this.alternative = alternative;
    }

    public com.rssl.phizic.business.etsm.offer.service.generated.AlternativeType getAlternative(int i) {
        return this.alternative[i];
    }

    public void setAlternative(int i, com.rssl.phizic.business.etsm.offer.service.generated.AlternativeType _value) {
        this.alternative[i] = _value;
    }


    /**
     * Gets the productTypeCode value for this OfferType.
     * 
     * @return productTypeCode   * Код типа продукта
     */
    public java.lang.String getProductTypeCode() {
        return productTypeCode;
    }


    /**
     * Sets the productTypeCode value for this OfferType.
     * 
     * @param productTypeCode   * Код типа продукта
     */
    public void setProductTypeCode(java.lang.String productTypeCode) {
        this.productTypeCode = productTypeCode;
    }


    /**
     * Gets the productCode value for this OfferType.
     * 
     * @return productCode   * Код продукта
     */
    public java.lang.String getProductCode() {
        return productCode;
    }


    /**
     * Sets the productCode value for this OfferType.
     * 
     * @param productCode   * Код продукта
     */
    public void setProductCode(java.lang.String productCode) {
        this.productCode = productCode;
    }


    /**
     * Gets the subProductCode value for this OfferType.
     * 
     * @return subProductCode   * Код суб продукта
     */
    public java.lang.String getSubProductCode() {
        return subProductCode;
    }


    /**
     * Sets the subProductCode value for this OfferType.
     * 
     * @param subProductCode   * Код суб продукта
     */
    public void setSubProductCode(java.lang.String subProductCode) {
        this.subProductCode = subProductCode;
    }


    /**
     * Gets the currency value for this OfferType.
     * 
     * @return currency   * Валюта кредита
     */
    public com.rssl.phizic.business.etsm.offer.service.generated.OfferTypeCurrency getCurrency() {
        return currency;
    }


    /**
     * Sets the currency value for this OfferType.
     * 
     * @param currency   * Валюта кредита
     */
    public void setCurrency(com.rssl.phizic.business.etsm.offer.service.generated.OfferTypeCurrency currency) {
        this.currency = currency;
    }


    /**
     * Gets the department value for this OfferType.
     * 
     * @return department   * Подразделение для оформления заявки в формате: тер. банк (2
     * цифры), ОСБ (4 цифры), ВСП (5 цифр)
     */
    public java.lang.String getDepartment() {
        return department;
    }


    /**
     * Sets the department value for this OfferType.
     * 
     * @param department   * Подразделение для оформления заявки в формате: тер. банк (2
     * цифры), ОСБ (4 цифры), ВСП (5 цифр)
     */
    public void setDepartment(java.lang.String department) {
        this.department = department;
    }


    /**
     * Gets the visibilityCounter value for this OfferType.
     * 
     * @return visibilityCounter   * Счетчик количества отображений оферты
     */
    public java.lang.Long getVisibilityCounter() {
        return visibilityCounter;
    }


    /**
     * Sets the visibilityCounter value for this OfferType.
     * 
     * @param visibilityCounter   * Счетчик количества отображений оферты
     */
    public void setVisibilityCounter(java.lang.Long visibilityCounter) {
        this.visibilityCounter = visibilityCounter;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof OfferType)) return false;
        OfferType other = (OfferType) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.offerStatus==null && other.getOfferStatus()==null) || 
             (this.offerStatus!=null &&
              this.offerStatus.equals(other.getOfferStatus()))) &&
            ((this.offerDate==null && other.getOfferDate()==null) || 
             (this.offerDate!=null &&
              this.offerDate.equals(other.getOfferDate()))) &&
            ((this.applicationNumber==null && other.getApplicationNumber()==null) || 
             (this.applicationNumber!=null &&
              this.applicationNumber.equals(other.getApplicationNumber()))) &&
            ((this.accauntNumber==null && other.getAccauntNumber()==null) || 
             (this.accauntNumber!=null &&
              this.accauntNumber.equals(other.getAccauntNumber()))) &&
            ((this.typeOfIssue==null && other.getTypeOfIssue()==null) || 
             (this.typeOfIssue!=null &&
              this.typeOfIssue.equals(other.getTypeOfIssue()))) &&
            ((this.clientCategory==null && other.getClientCategory()==null) || 
             (this.clientCategory!=null &&
              this.clientCategory.equals(other.getClientCategory()))) &&
            ((this.alternative==null && other.getAlternative()==null) || 
             (this.alternative!=null &&
              java.util.Arrays.equals(this.alternative, other.getAlternative()))) &&
            ((this.productTypeCode==null && other.getProductTypeCode()==null) || 
             (this.productTypeCode!=null &&
              this.productTypeCode.equals(other.getProductTypeCode()))) &&
            ((this.productCode==null && other.getProductCode()==null) || 
             (this.productCode!=null &&
              this.productCode.equals(other.getProductCode()))) &&
            ((this.subProductCode==null && other.getSubProductCode()==null) || 
             (this.subProductCode!=null &&
              this.subProductCode.equals(other.getSubProductCode()))) &&
            ((this.currency==null && other.getCurrency()==null) || 
             (this.currency!=null &&
              this.currency.equals(other.getCurrency()))) &&
            ((this.department==null && other.getDepartment()==null) || 
             (this.department!=null &&
              this.department.equals(other.getDepartment()))) &&
            ((this.visibilityCounter==null && other.getVisibilityCounter()==null) || 
             (this.visibilityCounter!=null &&
              this.visibilityCounter.equals(other.getVisibilityCounter())));
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
        if (getOfferStatus() != null) {
            _hashCode += getOfferStatus().hashCode();
        }
        if (getOfferDate() != null) {
            _hashCode += getOfferDate().hashCode();
        }
        if (getApplicationNumber() != null) {
            _hashCode += getApplicationNumber().hashCode();
        }
        if (getAccauntNumber() != null) {
            _hashCode += getAccauntNumber().hashCode();
        }
        if (getTypeOfIssue() != null) {
            _hashCode += getTypeOfIssue().hashCode();
        }
        if (getClientCategory() != null) {
            _hashCode += getClientCategory().hashCode();
        }
        if (getAlternative() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getAlternative());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getAlternative(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getProductTypeCode() != null) {
            _hashCode += getProductTypeCode().hashCode();
        }
        if (getProductCode() != null) {
            _hashCode += getProductCode().hashCode();
        }
        if (getSubProductCode() != null) {
            _hashCode += getSubProductCode().hashCode();
        }
        if (getCurrency() != null) {
            _hashCode += getCurrency().hashCode();
        }
        if (getDepartment() != null) {
            _hashCode += getDepartment().hashCode();
        }
        if (getVisibilityCounter() != null) {
            _hashCode += getVisibilityCounter().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(OfferType.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://generated.listener.etsm.phizic.rssl.com", "OfferType"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("offerStatus");
        elemField.setXmlName(new javax.xml.namespace.QName("http://generated.listener.etsm.phizic.rssl.com", "OfferStatus"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("offerDate");
        elemField.setXmlName(new javax.xml.namespace.QName("http://generated.listener.etsm.phizic.rssl.com", "OfferDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("applicationNumber");
        elemField.setXmlName(new javax.xml.namespace.QName("http://generated.listener.etsm.phizic.rssl.com", "ApplicationNumber"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("accauntNumber");
        elemField.setXmlName(new javax.xml.namespace.QName("http://generated.listener.etsm.phizic.rssl.com", "AccauntNumber"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("typeOfIssue");
        elemField.setXmlName(new javax.xml.namespace.QName("http://generated.listener.etsm.phizic.rssl.com", "TypeOfIssue"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("clientCategory");
        elemField.setXmlName(new javax.xml.namespace.QName("http://generated.listener.etsm.phizic.rssl.com", "ClientCategory"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("alternative");
        elemField.setXmlName(new javax.xml.namespace.QName("http://generated.listener.etsm.phizic.rssl.com", "Alternative"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://generated.listener.etsm.phizic.rssl.com", "AlternativeType"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("productTypeCode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://generated.listener.etsm.phizic.rssl.com", "ProductTypeCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("productCode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://generated.listener.etsm.phizic.rssl.com", "ProductCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("subProductCode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://generated.listener.etsm.phizic.rssl.com", "SubProductCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("currency");
        elemField.setXmlName(new javax.xml.namespace.QName("http://generated.listener.etsm.phizic.rssl.com", "Currency"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://generated.listener.etsm.phizic.rssl.com", ">OfferType>Currency"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("department");
        elemField.setXmlName(new javax.xml.namespace.QName("http://generated.listener.etsm.phizic.rssl.com", "department"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("visibilityCounter");
        elemField.setXmlName(new javax.xml.namespace.QName("http://generated.listener.etsm.phizic.rssl.com", "visibilityCounter"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setNillable(true);
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
