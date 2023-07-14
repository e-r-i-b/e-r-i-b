/**
 * PaymentType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.mbproviders.listener.generated;


/**
 * Информация о поставщике
 */
public class PaymentType  implements java.io.Serializable {
    private java.lang.String billingId;

    private java.lang.String serviceId;

    private java.lang.String providerId;

    private java.lang.String serviceName;

    private java.lang.String providerName;

    private java.lang.String providerSmsAlias;

    private com.rssl.phizic.mbproviders.listener.generated.EntryType[] paymentEntries;

    public PaymentType() {
    }

    public PaymentType(
           java.lang.String billingId,
           java.lang.String serviceId,
           java.lang.String providerId,
           java.lang.String serviceName,
           java.lang.String providerName,
           java.lang.String providerSmsAlias,
           com.rssl.phizic.mbproviders.listener.generated.EntryType[] paymentEntries) {
           this.billingId = billingId;
           this.serviceId = serviceId;
           this.providerId = providerId;
           this.serviceName = serviceName;
           this.providerName = providerName;
           this.providerSmsAlias = providerSmsAlias;
           this.paymentEntries = paymentEntries;
    }


    /**
     * Gets the billingId value for this PaymentType.
     * 
     * @return billingId
     */
    public java.lang.String getBillingId() {
        return billingId;
    }


    /**
     * Sets the billingId value for this PaymentType.
     * 
     * @param billingId
     */
    public void setBillingId(java.lang.String billingId) {
        this.billingId = billingId;
    }


    /**
     * Gets the serviceId value for this PaymentType.
     * 
     * @return serviceId
     */
    public java.lang.String getServiceId() {
        return serviceId;
    }


    /**
     * Sets the serviceId value for this PaymentType.
     * 
     * @param serviceId
     */
    public void setServiceId(java.lang.String serviceId) {
        this.serviceId = serviceId;
    }


    /**
     * Gets the providerId value for this PaymentType.
     * 
     * @return providerId
     */
    public java.lang.String getProviderId() {
        return providerId;
    }


    /**
     * Sets the providerId value for this PaymentType.
     * 
     * @param providerId
     */
    public void setProviderId(java.lang.String providerId) {
        this.providerId = providerId;
    }


    /**
     * Gets the serviceName value for this PaymentType.
     * 
     * @return serviceName
     */
    public java.lang.String getServiceName() {
        return serviceName;
    }


    /**
     * Sets the serviceName value for this PaymentType.
     * 
     * @param serviceName
     */
    public void setServiceName(java.lang.String serviceName) {
        this.serviceName = serviceName;
    }


    /**
     * Gets the providerName value for this PaymentType.
     * 
     * @return providerName
     */
    public java.lang.String getProviderName() {
        return providerName;
    }


    /**
     * Sets the providerName value for this PaymentType.
     * 
     * @param providerName
     */
    public void setProviderName(java.lang.String providerName) {
        this.providerName = providerName;
    }


    /**
     * Gets the providerSmsAlias value for this PaymentType.
     * 
     * @return providerSmsAlias
     */
    public java.lang.String getProviderSmsAlias() {
        return providerSmsAlias;
    }


    /**
     * Sets the providerSmsAlias value for this PaymentType.
     * 
     * @param providerSmsAlias
     */
    public void setProviderSmsAlias(java.lang.String providerSmsAlias) {
        this.providerSmsAlias = providerSmsAlias;
    }


    /**
     * Gets the paymentEntries value for this PaymentType.
     * 
     * @return paymentEntries
     */
    public com.rssl.phizic.mbproviders.listener.generated.EntryType[] getPaymentEntries() {
        return paymentEntries;
    }


    /**
     * Sets the paymentEntries value for this PaymentType.
     * 
     * @param paymentEntries
     */
    public void setPaymentEntries(com.rssl.phizic.mbproviders.listener.generated.EntryType[] paymentEntries) {
        this.paymentEntries = paymentEntries;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof PaymentType)) return false;
        PaymentType other = (PaymentType) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.billingId==null && other.getBillingId()==null) || 
             (this.billingId!=null &&
              this.billingId.equals(other.getBillingId()))) &&
            ((this.serviceId==null && other.getServiceId()==null) || 
             (this.serviceId!=null &&
              this.serviceId.equals(other.getServiceId()))) &&
            ((this.providerId==null && other.getProviderId()==null) || 
             (this.providerId!=null &&
              this.providerId.equals(other.getProviderId()))) &&
            ((this.serviceName==null && other.getServiceName()==null) || 
             (this.serviceName!=null &&
              this.serviceName.equals(other.getServiceName()))) &&
            ((this.providerName==null && other.getProviderName()==null) || 
             (this.providerName!=null &&
              this.providerName.equals(other.getProviderName()))) &&
            ((this.providerSmsAlias==null && other.getProviderSmsAlias()==null) || 
             (this.providerSmsAlias!=null &&
              this.providerSmsAlias.equals(other.getProviderSmsAlias()))) &&
            ((this.paymentEntries==null && other.getPaymentEntries()==null) || 
             (this.paymentEntries!=null &&
              java.util.Arrays.equals(this.paymentEntries, other.getPaymentEntries())));
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
        if (getBillingId() != null) {
            _hashCode += getBillingId().hashCode();
        }
        if (getServiceId() != null) {
            _hashCode += getServiceId().hashCode();
        }
        if (getProviderId() != null) {
            _hashCode += getProviderId().hashCode();
        }
        if (getServiceName() != null) {
            _hashCode += getServiceName().hashCode();
        }
        if (getProviderName() != null) {
            _hashCode += getProviderName().hashCode();
        }
        if (getProviderSmsAlias() != null) {
            _hashCode += getProviderSmsAlias().hashCode();
        }
        if (getPaymentEntries() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getPaymentEntries());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getPaymentEntries(), i);
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
        new org.apache.axis.description.TypeDesc(PaymentType.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/ermb/auxiliary/providers-dictionary/", "PaymentType"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("billingId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/ermb/auxiliary/providers-dictionary/", "billingId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("serviceId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/ermb/auxiliary/providers-dictionary/", "serviceId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("providerId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/ermb/auxiliary/providers-dictionary/", "providerId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("serviceName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/ermb/auxiliary/providers-dictionary/", "serviceName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("providerName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/ermb/auxiliary/providers-dictionary/", "providerName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("providerSmsAlias");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/ermb/auxiliary/providers-dictionary/", "providerSmsAlias"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("paymentEntries");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/ermb/auxiliary/providers-dictionary/", "paymentEntries"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/ermb/auxiliary/providers-dictionary/", "EntryType"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        elemField.setItemQName(new javax.xml.namespace.QName("http://sbrf.ru/ermb/auxiliary/providers-dictionary/", "entry"));
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
