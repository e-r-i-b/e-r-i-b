/**
 * ServiceParamsType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.test.wsgateclient.asfilial.generated;


/**
 * Параметры услуги.UpdateProfileRqType
 */
public class ServiceParamsType  implements java.io.Serializable {
    /* Тарифный план услуги
     *                         Допустимые значения
     *                         "full" – полный
     *                         "saving" - экономный */
    private java.lang.String tariffId;

    private boolean quickServices;

    private com.rssl.phizic.test.wsgateclient.asfilial.generated.PhoneNumberType activePhone;

    private com.rssl.phizic.test.wsgateclient.asfilial.generated.ResourcesType[] visibleResources;

    private com.rssl.phizic.test.wsgateclient.asfilial.generated.ResourcesType[] informResources;

    /* Номер приоритетной карты для списания абонентской платы */
    private java.lang.String chargeOffCard;

    private boolean informNewResource;

    private com.rssl.phizic.test.wsgateclient.asfilial.generated.DaytimePeriodType informPeriod;

    private boolean suppressAdvertising;

    private boolean informDespositEnrollment;

    public ServiceParamsType() {
    }

    public ServiceParamsType(
           java.lang.String tariffId,
           boolean quickServices,
           com.rssl.phizic.test.wsgateclient.asfilial.generated.PhoneNumberType activePhone,
           com.rssl.phizic.test.wsgateclient.asfilial.generated.ResourcesType[] visibleResources,
           com.rssl.phizic.test.wsgateclient.asfilial.generated.ResourcesType[] informResources,
           java.lang.String chargeOffCard,
           boolean informNewResource,
           com.rssl.phizic.test.wsgateclient.asfilial.generated.DaytimePeriodType informPeriod,
           boolean suppressAdvertising,
           boolean informDespositEnrollment) {
           this.tariffId = tariffId;
           this.quickServices = quickServices;
           this.activePhone = activePhone;
           this.visibleResources = visibleResources;
           this.informResources = informResources;
           this.chargeOffCard = chargeOffCard;
           this.informNewResource = informNewResource;
           this.informPeriod = informPeriod;
           this.suppressAdvertising = suppressAdvertising;
           this.informDespositEnrollment = informDespositEnrollment;
    }


    /**
     * Gets the tariffId value for this ServiceParamsType.
     * 
     * @return tariffId   * Тарифный план услуги
     *                         Допустимые значения
     *                         "full" – полный
     *                         "saving" - экономный
     */
    public java.lang.String getTariffId() {
        return tariffId;
    }


    /**
     * Sets the tariffId value for this ServiceParamsType.
     * 
     * @param tariffId   * Тарифный план услуги
     *                         Допустимые значения
     *                         "full" – полный
     *                         "saving" - экономный
     */
    public void setTariffId(java.lang.String tariffId) {
        this.tariffId = tariffId;
    }


    /**
     * Gets the quickServices value for this ServiceParamsType.
     * 
     * @return quickServices
     */
    public boolean isQuickServices() {
        return quickServices;
    }


    /**
     * Sets the quickServices value for this ServiceParamsType.
     * 
     * @param quickServices
     */
    public void setQuickServices(boolean quickServices) {
        this.quickServices = quickServices;
    }


    /**
     * Gets the activePhone value for this ServiceParamsType.
     * 
     * @return activePhone
     */
    public com.rssl.phizic.test.wsgateclient.asfilial.generated.PhoneNumberType getActivePhone() {
        return activePhone;
    }


    /**
     * Sets the activePhone value for this ServiceParamsType.
     * 
     * @param activePhone
     */
    public void setActivePhone(com.rssl.phizic.test.wsgateclient.asfilial.generated.PhoneNumberType activePhone) {
        this.activePhone = activePhone;
    }


    /**
     * Gets the visibleResources value for this ServiceParamsType.
     * 
     * @return visibleResources
     */
    public com.rssl.phizic.test.wsgateclient.asfilial.generated.ResourcesType[] getVisibleResources() {
        return visibleResources;
    }


    /**
     * Sets the visibleResources value for this ServiceParamsType.
     * 
     * @param visibleResources
     */
    public void setVisibleResources(com.rssl.phizic.test.wsgateclient.asfilial.generated.ResourcesType[] visibleResources) {
        this.visibleResources = visibleResources;
    }

    public com.rssl.phizic.test.wsgateclient.asfilial.generated.ResourcesType getVisibleResources(int i) {
        return this.visibleResources[i];
    }

    public void setVisibleResources(int i, com.rssl.phizic.test.wsgateclient.asfilial.generated.ResourcesType _value) {
        this.visibleResources[i] = _value;
    }


    /**
     * Gets the informResources value for this ServiceParamsType.
     * 
     * @return informResources
     */
    public com.rssl.phizic.test.wsgateclient.asfilial.generated.ResourcesType[] getInformResources() {
        return informResources;
    }


    /**
     * Sets the informResources value for this ServiceParamsType.
     * 
     * @param informResources
     */
    public void setInformResources(com.rssl.phizic.test.wsgateclient.asfilial.generated.ResourcesType[] informResources) {
        this.informResources = informResources;
    }

    public com.rssl.phizic.test.wsgateclient.asfilial.generated.ResourcesType getInformResources(int i) {
        return this.informResources[i];
    }

    public void setInformResources(int i, com.rssl.phizic.test.wsgateclient.asfilial.generated.ResourcesType _value) {
        this.informResources[i] = _value;
    }


    /**
     * Gets the chargeOffCard value for this ServiceParamsType.
     * 
     * @return chargeOffCard   * Номер приоритетной карты для списания абонентской платы
     */
    public java.lang.String getChargeOffCard() {
        return chargeOffCard;
    }


    /**
     * Sets the chargeOffCard value for this ServiceParamsType.
     * 
     * @param chargeOffCard   * Номер приоритетной карты для списания абонентской платы
     */
    public void setChargeOffCard(java.lang.String chargeOffCard) {
        this.chargeOffCard = chargeOffCard;
    }


    /**
     * Gets the informNewResource value for this ServiceParamsType.
     * 
     * @return informNewResource
     */
    public boolean isInformNewResource() {
        return informNewResource;
    }


    /**
     * Sets the informNewResource value for this ServiceParamsType.
     * 
     * @param informNewResource
     */
    public void setInformNewResource(boolean informNewResource) {
        this.informNewResource = informNewResource;
    }


    /**
     * Gets the informPeriod value for this ServiceParamsType.
     * 
     * @return informPeriod
     */
    public com.rssl.phizic.test.wsgateclient.asfilial.generated.DaytimePeriodType getInformPeriod() {
        return informPeriod;
    }


    /**
     * Sets the informPeriod value for this ServiceParamsType.
     * 
     * @param informPeriod
     */
    public void setInformPeriod(com.rssl.phizic.test.wsgateclient.asfilial.generated.DaytimePeriodType informPeriod) {
        this.informPeriod = informPeriod;
    }


    /**
     * Gets the suppressAdvertising value for this ServiceParamsType.
     * 
     * @return suppressAdvertising
     */
    public boolean isSuppressAdvertising() {
        return suppressAdvertising;
    }


    /**
     * Sets the suppressAdvertising value for this ServiceParamsType.
     * 
     * @param suppressAdvertising
     */
    public void setSuppressAdvertising(boolean suppressAdvertising) {
        this.suppressAdvertising = suppressAdvertising;
    }


    /**
     * Gets the informDespositEnrollment value for this ServiceParamsType.
     * 
     * @return informDespositEnrollment
     */
    public boolean isInformDespositEnrollment() {
        return informDespositEnrollment;
    }


    /**
     * Sets the informDespositEnrollment value for this ServiceParamsType.
     * 
     * @param informDespositEnrollment
     */
    public void setInformDespositEnrollment(boolean informDespositEnrollment) {
        this.informDespositEnrollment = informDespositEnrollment;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ServiceParamsType)) return false;
        ServiceParamsType other = (ServiceParamsType) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.tariffId==null && other.getTariffId()==null) || 
             (this.tariffId!=null &&
              this.tariffId.equals(other.getTariffId()))) &&
            this.quickServices == other.isQuickServices() &&
            ((this.activePhone==null && other.getActivePhone()==null) || 
             (this.activePhone!=null &&
              this.activePhone.equals(other.getActivePhone()))) &&
            ((this.visibleResources==null && other.getVisibleResources()==null) || 
             (this.visibleResources!=null &&
              java.util.Arrays.equals(this.visibleResources, other.getVisibleResources()))) &&
            ((this.informResources==null && other.getInformResources()==null) || 
             (this.informResources!=null &&
              java.util.Arrays.equals(this.informResources, other.getInformResources()))) &&
            ((this.chargeOffCard==null && other.getChargeOffCard()==null) || 
             (this.chargeOffCard!=null &&
              this.chargeOffCard.equals(other.getChargeOffCard()))) &&
            this.informNewResource == other.isInformNewResource() &&
            ((this.informPeriod==null && other.getInformPeriod()==null) || 
             (this.informPeriod!=null &&
              this.informPeriod.equals(other.getInformPeriod()))) &&
            this.suppressAdvertising == other.isSuppressAdvertising() &&
            this.informDespositEnrollment == other.isInformDespositEnrollment();
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
        if (getTariffId() != null) {
            _hashCode += getTariffId().hashCode();
        }
        _hashCode += (isQuickServices() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        if (getActivePhone() != null) {
            _hashCode += getActivePhone().hashCode();
        }
        if (getVisibleResources() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getVisibleResources());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getVisibleResources(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getInformResources() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getInformResources());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getInformResources(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getChargeOffCard() != null) {
            _hashCode += getChargeOffCard().hashCode();
        }
        _hashCode += (isInformNewResource() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        if (getInformPeriod() != null) {
            _hashCode += getInformPeriod().hashCode();
        }
        _hashCode += (isSuppressAdvertising() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        _hashCode += (isInformDespositEnrollment() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ServiceParamsType.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://generated.listener.asfilial.phizic.rssl.com", "ServiceParamsType"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tariffId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://generated.listener.asfilial.phizic.rssl.com", "TariffId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("quickServices");
        elemField.setXmlName(new javax.xml.namespace.QName("http://generated.listener.asfilial.phizic.rssl.com", "QuickServices"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("activePhone");
        elemField.setXmlName(new javax.xml.namespace.QName("http://generated.listener.asfilial.phizic.rssl.com", "ActivePhone"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://generated.listener.asfilial.phizic.rssl.com", "PhoneNumberType"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("visibleResources");
        elemField.setXmlName(new javax.xml.namespace.QName("http://generated.listener.asfilial.phizic.rssl.com", "VisibleResources"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://generated.listener.asfilial.phizic.rssl.com", "ResourcesType"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("informResources");
        elemField.setXmlName(new javax.xml.namespace.QName("http://generated.listener.asfilial.phizic.rssl.com", "InformResources"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://generated.listener.asfilial.phizic.rssl.com", "ResourcesType"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("chargeOffCard");
        elemField.setXmlName(new javax.xml.namespace.QName("http://generated.listener.asfilial.phizic.rssl.com", "ChargeOffCard"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("informNewResource");
        elemField.setXmlName(new javax.xml.namespace.QName("http://generated.listener.asfilial.phizic.rssl.com", "InformNewResource"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("informPeriod");
        elemField.setXmlName(new javax.xml.namespace.QName("http://generated.listener.asfilial.phizic.rssl.com", "InformPeriod"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://generated.listener.asfilial.phizic.rssl.com", "DaytimePeriodType"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("suppressAdvertising");
        elemField.setXmlName(new javax.xml.namespace.QName("http://generated.listener.asfilial.phizic.rssl.com", "SuppressAdvertising"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("informDespositEnrollment");
        elemField.setXmlName(new javax.xml.namespace.QName("http://generated.listener.asfilial.phizic.rssl.com", "InformDespositEnrollment"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
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
