/**
 * ResponseType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.asfilial.listener.generated;


/**
 * Данные ответа.QueryProfileRs
 */
public class ResponseType  implements java.io.Serializable {
    private boolean newProfile;

    private com.rssl.phizic.asfilial.listener.generated.IdentityType clientIdentity;

    private com.rssl.phizic.asfilial.listener.generated.IdentityType clientOldIdentity;

    private com.rssl.phizic.asfilial.listener.generated.PhoneNumberType[] clientPhones;

    private com.rssl.phizic.asfilial.listener.generated.ResourcesType[] clientResources;

    private com.rssl.phizic.asfilial.listener.generated.PayResourceType[] payResources;

    private com.rssl.phizic.asfilial.listener.generated.ResourcesType[] internetClientService;

    private com.rssl.phizic.asfilial.listener.generated.ResourcesType[] mobileClientService;

    private com.rssl.phizic.asfilial.listener.generated.ResourcesType[] ATMClientService;

    private com.rssl.phizic.asfilial.listener.generated.MobileBankServiceQueryProfileRsType mobileBankService;

    public ResponseType() {
    }

    public ResponseType(
           boolean newProfile,
           com.rssl.phizic.asfilial.listener.generated.IdentityType clientIdentity,
           com.rssl.phizic.asfilial.listener.generated.IdentityType clientOldIdentity,
           com.rssl.phizic.asfilial.listener.generated.PhoneNumberType[] clientPhones,
           com.rssl.phizic.asfilial.listener.generated.ResourcesType[] clientResources,
           com.rssl.phizic.asfilial.listener.generated.PayResourceType[] payResources,
           com.rssl.phizic.asfilial.listener.generated.ResourcesType[] internetClientService,
           com.rssl.phizic.asfilial.listener.generated.ResourcesType[] mobileClientService,
           com.rssl.phizic.asfilial.listener.generated.ResourcesType[] ATMClientService,
           com.rssl.phizic.asfilial.listener.generated.MobileBankServiceQueryProfileRsType mobileBankService) {
           this.newProfile = newProfile;
           this.clientIdentity = clientIdentity;
           this.clientOldIdentity = clientOldIdentity;
           this.clientPhones = clientPhones;
           this.clientResources = clientResources;
           this.payResources = payResources;
           this.internetClientService = internetClientService;
           this.mobileClientService = mobileClientService;
           this.ATMClientService = ATMClientService;
           this.mobileBankService = mobileBankService;
    }


    /**
     * Gets the newProfile value for this ResponseType.
     * 
     * @return newProfile
     */
    public boolean isNewProfile() {
        return newProfile;
    }


    /**
     * Sets the newProfile value for this ResponseType.
     * 
     * @param newProfile
     */
    public void setNewProfile(boolean newProfile) {
        this.newProfile = newProfile;
    }


    /**
     * Gets the clientIdentity value for this ResponseType.
     * 
     * @return clientIdentity
     */
    public com.rssl.phizic.asfilial.listener.generated.IdentityType getClientIdentity() {
        return clientIdentity;
    }


    /**
     * Sets the clientIdentity value for this ResponseType.
     * 
     * @param clientIdentity
     */
    public void setClientIdentity(com.rssl.phizic.asfilial.listener.generated.IdentityType clientIdentity) {
        this.clientIdentity = clientIdentity;
    }


    /**
     * Gets the clientOldIdentity value for this ResponseType.
     * 
     * @return clientOldIdentity
     */
    public com.rssl.phizic.asfilial.listener.generated.IdentityType getClientOldIdentity() {
        return clientOldIdentity;
    }


    /**
     * Sets the clientOldIdentity value for this ResponseType.
     * 
     * @param clientOldIdentity
     */
    public void setClientOldIdentity(com.rssl.phizic.asfilial.listener.generated.IdentityType clientOldIdentity) {
        this.clientOldIdentity = clientOldIdentity;
    }


    /**
     * Gets the clientPhones value for this ResponseType.
     * 
     * @return clientPhones
     */
    public com.rssl.phizic.asfilial.listener.generated.PhoneNumberType[] getClientPhones() {
        return clientPhones;
    }


    /**
     * Sets the clientPhones value for this ResponseType.
     * 
     * @param clientPhones
     */
    public void setClientPhones(com.rssl.phizic.asfilial.listener.generated.PhoneNumberType[] clientPhones) {
        this.clientPhones = clientPhones;
    }

    public com.rssl.phizic.asfilial.listener.generated.PhoneNumberType getClientPhones(int i) {
        return this.clientPhones[i];
    }

    public void setClientPhones(int i, com.rssl.phizic.asfilial.listener.generated.PhoneNumberType _value) {
        this.clientPhones[i] = _value;
    }


    /**
     * Gets the clientResources value for this ResponseType.
     * 
     * @return clientResources
     */
    public com.rssl.phizic.asfilial.listener.generated.ResourcesType[] getClientResources() {
        return clientResources;
    }


    /**
     * Sets the clientResources value for this ResponseType.
     * 
     * @param clientResources
     */
    public void setClientResources(com.rssl.phizic.asfilial.listener.generated.ResourcesType[] clientResources) {
        this.clientResources = clientResources;
    }

    public com.rssl.phizic.asfilial.listener.generated.ResourcesType getClientResources(int i) {
        return this.clientResources[i];
    }

    public void setClientResources(int i, com.rssl.phizic.asfilial.listener.generated.ResourcesType _value) {
        this.clientResources[i] = _value;
    }


    /**
     * Gets the payResources value for this ResponseType.
     * 
     * @return payResources
     */
    public com.rssl.phizic.asfilial.listener.generated.PayResourceType[] getPayResources() {
        return payResources;
    }


    /**
     * Sets the payResources value for this ResponseType.
     * 
     * @param payResources
     */
    public void setPayResources(com.rssl.phizic.asfilial.listener.generated.PayResourceType[] payResources) {
        this.payResources = payResources;
    }

    public com.rssl.phizic.asfilial.listener.generated.PayResourceType getPayResources(int i) {
        return this.payResources[i];
    }

    public void setPayResources(int i, com.rssl.phizic.asfilial.listener.generated.PayResourceType _value) {
        this.payResources[i] = _value;
    }


    /**
     * Gets the internetClientService value for this ResponseType.
     * 
     * @return internetClientService
     */
    public com.rssl.phizic.asfilial.listener.generated.ResourcesType[] getInternetClientService() {
        return internetClientService;
    }


    /**
     * Sets the internetClientService value for this ResponseType.
     * 
     * @param internetClientService
     */
    public void setInternetClientService(com.rssl.phizic.asfilial.listener.generated.ResourcesType[] internetClientService) {
        this.internetClientService = internetClientService;
    }


    /**
     * Gets the mobileClientService value for this ResponseType.
     * 
     * @return mobileClientService
     */
    public com.rssl.phizic.asfilial.listener.generated.ResourcesType[] getMobileClientService() {
        return mobileClientService;
    }


    /**
     * Sets the mobileClientService value for this ResponseType.
     * 
     * @param mobileClientService
     */
    public void setMobileClientService(com.rssl.phizic.asfilial.listener.generated.ResourcesType[] mobileClientService) {
        this.mobileClientService = mobileClientService;
    }


    /**
     * Gets the ATMClientService value for this ResponseType.
     * 
     * @return ATMClientService
     */
    public com.rssl.phizic.asfilial.listener.generated.ResourcesType[] getATMClientService() {
        return ATMClientService;
    }


    /**
     * Sets the ATMClientService value for this ResponseType.
     * 
     * @param ATMClientService
     */
    public void setATMClientService(com.rssl.phizic.asfilial.listener.generated.ResourcesType[] ATMClientService) {
        this.ATMClientService = ATMClientService;
    }


    /**
     * Gets the mobileBankService value for this ResponseType.
     * 
     * @return mobileBankService
     */
    public com.rssl.phizic.asfilial.listener.generated.MobileBankServiceQueryProfileRsType getMobileBankService() {
        return mobileBankService;
    }


    /**
     * Sets the mobileBankService value for this ResponseType.
     * 
     * @param mobileBankService
     */
    public void setMobileBankService(com.rssl.phizic.asfilial.listener.generated.MobileBankServiceQueryProfileRsType mobileBankService) {
        this.mobileBankService = mobileBankService;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ResponseType)) return false;
        ResponseType other = (ResponseType) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            this.newProfile == other.isNewProfile() &&
            ((this.clientIdentity==null && other.getClientIdentity()==null) || 
             (this.clientIdentity!=null &&
              this.clientIdentity.equals(other.getClientIdentity()))) &&
            ((this.clientOldIdentity==null && other.getClientOldIdentity()==null) || 
             (this.clientOldIdentity!=null &&
              this.clientOldIdentity.equals(other.getClientOldIdentity()))) &&
            ((this.clientPhones==null && other.getClientPhones()==null) || 
             (this.clientPhones!=null &&
              java.util.Arrays.equals(this.clientPhones, other.getClientPhones()))) &&
            ((this.clientResources==null && other.getClientResources()==null) || 
             (this.clientResources!=null &&
              java.util.Arrays.equals(this.clientResources, other.getClientResources()))) &&
            ((this.payResources==null && other.getPayResources()==null) || 
             (this.payResources!=null &&
              java.util.Arrays.equals(this.payResources, other.getPayResources()))) &&
            ((this.internetClientService==null && other.getInternetClientService()==null) || 
             (this.internetClientService!=null &&
              java.util.Arrays.equals(this.internetClientService, other.getInternetClientService()))) &&
            ((this.mobileClientService==null && other.getMobileClientService()==null) || 
             (this.mobileClientService!=null &&
              java.util.Arrays.equals(this.mobileClientService, other.getMobileClientService()))) &&
            ((this.ATMClientService==null && other.getATMClientService()==null) || 
             (this.ATMClientService!=null &&
              java.util.Arrays.equals(this.ATMClientService, other.getATMClientService()))) &&
            ((this.mobileBankService==null && other.getMobileBankService()==null) || 
             (this.mobileBankService!=null &&
              this.mobileBankService.equals(other.getMobileBankService())));
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
        _hashCode += (isNewProfile() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        if (getClientIdentity() != null) {
            _hashCode += getClientIdentity().hashCode();
        }
        if (getClientOldIdentity() != null) {
            _hashCode += getClientOldIdentity().hashCode();
        }
        if (getClientPhones() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getClientPhones());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getClientPhones(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getClientResources() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getClientResources());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getClientResources(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getPayResources() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getPayResources());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getPayResources(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getInternetClientService() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getInternetClientService());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getInternetClientService(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getMobileClientService() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getMobileClientService());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getMobileClientService(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getATMClientService() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getATMClientService());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getATMClientService(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getMobileBankService() != null) {
            _hashCode += getMobileBankService().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ResponseType.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://generated.listener.asfilial.phizic.rssl.com", "ResponseType"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("newProfile");
        elemField.setXmlName(new javax.xml.namespace.QName("http://generated.listener.asfilial.phizic.rssl.com", "NewProfile"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("clientIdentity");
        elemField.setXmlName(new javax.xml.namespace.QName("http://generated.listener.asfilial.phizic.rssl.com", "ClientIdentity"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://generated.listener.asfilial.phizic.rssl.com", "IdentityType"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("clientOldIdentity");
        elemField.setXmlName(new javax.xml.namespace.QName("http://generated.listener.asfilial.phizic.rssl.com", "ClientOldIdentity"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://generated.listener.asfilial.phizic.rssl.com", "IdentityType"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("clientPhones");
        elemField.setXmlName(new javax.xml.namespace.QName("http://generated.listener.asfilial.phizic.rssl.com", "ClientPhones"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://generated.listener.asfilial.phizic.rssl.com", "PhoneNumberType"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("clientResources");
        elemField.setXmlName(new javax.xml.namespace.QName("http://generated.listener.asfilial.phizic.rssl.com", "ClientResources"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://generated.listener.asfilial.phizic.rssl.com", "ResourcesType"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("payResources");
        elemField.setXmlName(new javax.xml.namespace.QName("http://generated.listener.asfilial.phizic.rssl.com", "PayResources"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://generated.listener.asfilial.phizic.rssl.com", "PayResourceType"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("internetClientService");
        elemField.setXmlName(new javax.xml.namespace.QName("http://generated.listener.asfilial.phizic.rssl.com", "InternetClientService"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://generated.listener.asfilial.phizic.rssl.com", "ResourcesType"));
        elemField.setNillable(false);
        elemField.setItemQName(new javax.xml.namespace.QName("http://generated.listener.asfilial.phizic.rssl.com", "VisibleResources"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("mobileClientService");
        elemField.setXmlName(new javax.xml.namespace.QName("http://generated.listener.asfilial.phizic.rssl.com", "MobileClientService"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://generated.listener.asfilial.phizic.rssl.com", "ResourcesType"));
        elemField.setNillable(false);
        elemField.setItemQName(new javax.xml.namespace.QName("http://generated.listener.asfilial.phizic.rssl.com", "VisibleResources"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ATMClientService");
        elemField.setXmlName(new javax.xml.namespace.QName("http://generated.listener.asfilial.phizic.rssl.com", "ATMClientService"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://generated.listener.asfilial.phizic.rssl.com", "ResourcesType"));
        elemField.setNillable(false);
        elemField.setItemQName(new javax.xml.namespace.QName("http://generated.listener.asfilial.phizic.rssl.com", "VisibleResources"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("mobileBankService");
        elemField.setXmlName(new javax.xml.namespace.QName("http://generated.listener.asfilial.phizic.rssl.com", "MobileBankService"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://generated.listener.asfilial.phizic.rssl.com", "MobileBankServiceQueryProfileRsType"));
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
