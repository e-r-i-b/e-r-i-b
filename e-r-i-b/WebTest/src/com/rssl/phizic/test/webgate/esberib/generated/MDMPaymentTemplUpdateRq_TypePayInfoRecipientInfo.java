/**
 * MDMPaymentTemplUpdateRq_TypePayInfoRecipientInfo.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.test.webgate.esberib.generated;

public class MDMPaymentTemplUpdateRq_TypePayInfoRecipientInfo  implements java.io.Serializable {
    /* ИНН получателя */
    private java.lang.String taxIdTo;

    /* КПП получателя */
    private java.lang.String KPPTo;

    /* Наименование получателя юр. Лица, либо ФИО физ.лица */
    private java.lang.String recipientName;

    public MDMPaymentTemplUpdateRq_TypePayInfoRecipientInfo() {
    }

    public MDMPaymentTemplUpdateRq_TypePayInfoRecipientInfo(
           java.lang.String taxIdTo,
           java.lang.String KPPTo,
           java.lang.String recipientName) {
           this.taxIdTo = taxIdTo;
           this.KPPTo = KPPTo;
           this.recipientName = recipientName;
    }


    /**
     * Gets the taxIdTo value for this MDMPaymentTemplUpdateRq_TypePayInfoRecipientInfo.
     * 
     * @return taxIdTo   * ИНН получателя
     */
    public java.lang.String getTaxIdTo() {
        return taxIdTo;
    }


    /**
     * Sets the taxIdTo value for this MDMPaymentTemplUpdateRq_TypePayInfoRecipientInfo.
     * 
     * @param taxIdTo   * ИНН получателя
     */
    public void setTaxIdTo(java.lang.String taxIdTo) {
        this.taxIdTo = taxIdTo;
    }


    /**
     * Gets the KPPTo value for this MDMPaymentTemplUpdateRq_TypePayInfoRecipientInfo.
     * 
     * @return KPPTo   * КПП получателя
     */
    public java.lang.String getKPPTo() {
        return KPPTo;
    }


    /**
     * Sets the KPPTo value for this MDMPaymentTemplUpdateRq_TypePayInfoRecipientInfo.
     * 
     * @param KPPTo   * КПП получателя
     */
    public void setKPPTo(java.lang.String KPPTo) {
        this.KPPTo = KPPTo;
    }


    /**
     * Gets the recipientName value for this MDMPaymentTemplUpdateRq_TypePayInfoRecipientInfo.
     * 
     * @return recipientName   * Наименование получателя юр. Лица, либо ФИО физ.лица
     */
    public java.lang.String getRecipientName() {
        return recipientName;
    }


    /**
     * Sets the recipientName value for this MDMPaymentTemplUpdateRq_TypePayInfoRecipientInfo.
     * 
     * @param recipientName   * Наименование получателя юр. Лица, либо ФИО физ.лица
     */
    public void setRecipientName(java.lang.String recipientName) {
        this.recipientName = recipientName;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof MDMPaymentTemplUpdateRq_TypePayInfoRecipientInfo)) return false;
        MDMPaymentTemplUpdateRq_TypePayInfoRecipientInfo other = (MDMPaymentTemplUpdateRq_TypePayInfoRecipientInfo) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.taxIdTo==null && other.getTaxIdTo()==null) || 
             (this.taxIdTo!=null &&
              this.taxIdTo.equals(other.getTaxIdTo()))) &&
            ((this.KPPTo==null && other.getKPPTo()==null) || 
             (this.KPPTo!=null &&
              this.KPPTo.equals(other.getKPPTo()))) &&
            ((this.recipientName==null && other.getRecipientName()==null) || 
             (this.recipientName!=null &&
              this.recipientName.equals(other.getRecipientName())));
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
        if (getTaxIdTo() != null) {
            _hashCode += getTaxIdTo().hashCode();
        }
        if (getKPPTo() != null) {
            _hashCode += getKPPTo().hashCode();
        }
        if (getRecipientName() != null) {
            _hashCode += getRecipientName().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(MDMPaymentTemplUpdateRq_TypePayInfoRecipientInfo.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", ">>MDMPaymentTemplUpdateRq_Type>PayInfo>RecipientInfo"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("taxIdTo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "TaxIdTo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "String"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("KPPTo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "KPPTo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("recipientName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "RecipientName"));
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
