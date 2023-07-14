/**
 * UpdateProfileRqType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.asfilial.listener.generated;

public class UpdateProfileRqType  implements java.io.Serializable {
    /* Идентификатор запроса REGEXP: [0-9a-fA-F]{32} */
    private java.lang.String rqUID;

    private java.util.Calendar rqTm;

    /* Идентификатор операции REGEXP: [0-9a-fA-F]{32} */
    private java.lang.String operUID;

    /* Фиксированное значение:
     *                         "АС_ФИЛИАЛ"
     *                         "СПООБК-2" */
    private java.lang.String SName;

    private com.rssl.phizic.asfilial.listener.generated.BankInfoType bankInfo;

    private com.rssl.phizic.asfilial.listener.generated.IdentityType clientIdentity;

    private com.rssl.phizic.asfilial.listener.generated.ClientPhonesType[] clientData;

    private com.rssl.phizic.asfilial.listener.generated.ResourcesType[] internetClientService;

    private com.rssl.phizic.asfilial.listener.generated.ResourcesType[] mobileClientService;

    private com.rssl.phizic.asfilial.listener.generated.ResourcesType[] ATMClientService;

    private com.rssl.phizic.asfilial.listener.generated.MobileBankServiceUpdateProfileRqType mobileBankService;

    public UpdateProfileRqType() {
    }

    public UpdateProfileRqType(
           java.lang.String rqUID,
           java.util.Calendar rqTm,
           java.lang.String operUID,
           java.lang.String SName,
           com.rssl.phizic.asfilial.listener.generated.BankInfoType bankInfo,
           com.rssl.phizic.asfilial.listener.generated.IdentityType clientIdentity,
           com.rssl.phizic.asfilial.listener.generated.ClientPhonesType[] clientData,
           com.rssl.phizic.asfilial.listener.generated.ResourcesType[] internetClientService,
           com.rssl.phizic.asfilial.listener.generated.ResourcesType[] mobileClientService,
           com.rssl.phizic.asfilial.listener.generated.ResourcesType[] ATMClientService,
           com.rssl.phizic.asfilial.listener.generated.MobileBankServiceUpdateProfileRqType mobileBankService) {
           this.rqUID = rqUID;
           this.rqTm = rqTm;
           this.operUID = operUID;
           this.SName = SName;
           this.bankInfo = bankInfo;
           this.clientIdentity = clientIdentity;
           this.clientData = clientData;
           this.internetClientService = internetClientService;
           this.mobileClientService = mobileClientService;
           this.ATMClientService = ATMClientService;
           this.mobileBankService = mobileBankService;
    }


    /**
     * Gets the rqUID value for this UpdateProfileRqType.
     * 
     * @return rqUID   * Идентификатор запроса REGEXP: [0-9a-fA-F]{32}
     */
    public java.lang.String getRqUID() {
        return rqUID;
    }


    /**
     * Sets the rqUID value for this UpdateProfileRqType.
     * 
     * @param rqUID   * Идентификатор запроса REGEXP: [0-9a-fA-F]{32}
     */
    public void setRqUID(java.lang.String rqUID) {
        this.rqUID = rqUID;
    }


    /**
     * Gets the rqTm value for this UpdateProfileRqType.
     * 
     * @return rqTm
     */
    public java.util.Calendar getRqTm() {
        return rqTm;
    }


    /**
     * Sets the rqTm value for this UpdateProfileRqType.
     * 
     * @param rqTm
     */
    public void setRqTm(java.util.Calendar rqTm) {
        this.rqTm = rqTm;
    }


    /**
     * Gets the operUID value for this UpdateProfileRqType.
     * 
     * @return operUID   * Идентификатор операции REGEXP: [0-9a-fA-F]{32}
     */
    public java.lang.String getOperUID() {
        return operUID;
    }


    /**
     * Sets the operUID value for this UpdateProfileRqType.
     * 
     * @param operUID   * Идентификатор операции REGEXP: [0-9a-fA-F]{32}
     */
    public void setOperUID(java.lang.String operUID) {
        this.operUID = operUID;
    }


    /**
     * Gets the SName value for this UpdateProfileRqType.
     * 
     * @return SName   * Фиксированное значение:
     *                         "АС_ФИЛИАЛ"
     *                         "СПООБК-2"
     */
    public java.lang.String getSName() {
        return SName;
    }


    /**
     * Sets the SName value for this UpdateProfileRqType.
     * 
     * @param SName   * Фиксированное значение:
     *                         "АС_ФИЛИАЛ"
     *                         "СПООБК-2"
     */
    public void setSName(java.lang.String SName) {
        this.SName = SName;
    }


    /**
     * Gets the bankInfo value for this UpdateProfileRqType.
     * 
     * @return bankInfo
     */
    public com.rssl.phizic.asfilial.listener.generated.BankInfoType getBankInfo() {
        return bankInfo;
    }


    /**
     * Sets the bankInfo value for this UpdateProfileRqType.
     * 
     * @param bankInfo
     */
    public void setBankInfo(com.rssl.phizic.asfilial.listener.generated.BankInfoType bankInfo) {
        this.bankInfo = bankInfo;
    }


    /**
     * Gets the clientIdentity value for this UpdateProfileRqType.
     * 
     * @return clientIdentity
     */
    public com.rssl.phizic.asfilial.listener.generated.IdentityType getClientIdentity() {
        return clientIdentity;
    }


    /**
     * Sets the clientIdentity value for this UpdateProfileRqType.
     * 
     * @param clientIdentity
     */
    public void setClientIdentity(com.rssl.phizic.asfilial.listener.generated.IdentityType clientIdentity) {
        this.clientIdentity = clientIdentity;
    }


    /**
     * Gets the clientData value for this UpdateProfileRqType.
     * 
     * @return clientData
     */
    public com.rssl.phizic.asfilial.listener.generated.ClientPhonesType[] getClientData() {
        return clientData;
    }


    /**
     * Sets the clientData value for this UpdateProfileRqType.
     * 
     * @param clientData
     */
    public void setClientData(com.rssl.phizic.asfilial.listener.generated.ClientPhonesType[] clientData) {
        this.clientData = clientData;
    }


    /**
     * Gets the internetClientService value for this UpdateProfileRqType.
     * 
     * @return internetClientService
     */
    public com.rssl.phizic.asfilial.listener.generated.ResourcesType[] getInternetClientService() {
        return internetClientService;
    }


    /**
     * Sets the internetClientService value for this UpdateProfileRqType.
     * 
     * @param internetClientService
     */
    public void setInternetClientService(com.rssl.phizic.asfilial.listener.generated.ResourcesType[] internetClientService) {
        this.internetClientService = internetClientService;
    }


    /**
     * Gets the mobileClientService value for this UpdateProfileRqType.
     * 
     * @return mobileClientService
     */
    public com.rssl.phizic.asfilial.listener.generated.ResourcesType[] getMobileClientService() {
        return mobileClientService;
    }


    /**
     * Sets the mobileClientService value for this UpdateProfileRqType.
     * 
     * @param mobileClientService
     */
    public void setMobileClientService(com.rssl.phizic.asfilial.listener.generated.ResourcesType[] mobileClientService) {
        this.mobileClientService = mobileClientService;
    }


    /**
     * Gets the ATMClientService value for this UpdateProfileRqType.
     * 
     * @return ATMClientService
     */
    public com.rssl.phizic.asfilial.listener.generated.ResourcesType[] getATMClientService() {
        return ATMClientService;
    }


    /**
     * Sets the ATMClientService value for this UpdateProfileRqType.
     * 
     * @param ATMClientService
     */
    public void setATMClientService(com.rssl.phizic.asfilial.listener.generated.ResourcesType[] ATMClientService) {
        this.ATMClientService = ATMClientService;
    }


    /**
     * Gets the mobileBankService value for this UpdateProfileRqType.
     * 
     * @return mobileBankService
     */
    public com.rssl.phizic.asfilial.listener.generated.MobileBankServiceUpdateProfileRqType getMobileBankService() {
        return mobileBankService;
    }


    /**
     * Sets the mobileBankService value for this UpdateProfileRqType.
     * 
     * @param mobileBankService
     */
    public void setMobileBankService(com.rssl.phizic.asfilial.listener.generated.MobileBankServiceUpdateProfileRqType mobileBankService) {
        this.mobileBankService = mobileBankService;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof UpdateProfileRqType)) return false;
        UpdateProfileRqType other = (UpdateProfileRqType) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.rqUID==null && other.getRqUID()==null) || 
             (this.rqUID!=null &&
              this.rqUID.equals(other.getRqUID()))) &&
            ((this.rqTm==null && other.getRqTm()==null) || 
             (this.rqTm!=null &&
              this.rqTm.equals(other.getRqTm()))) &&
            ((this.operUID==null && other.getOperUID()==null) || 
             (this.operUID!=null &&
              this.operUID.equals(other.getOperUID()))) &&
            ((this.SName==null && other.getSName()==null) || 
             (this.SName!=null &&
              this.SName.equals(other.getSName()))) &&
            ((this.bankInfo==null && other.getBankInfo()==null) || 
             (this.bankInfo!=null &&
              this.bankInfo.equals(other.getBankInfo()))) &&
            ((this.clientIdentity==null && other.getClientIdentity()==null) || 
             (this.clientIdentity!=null &&
              this.clientIdentity.equals(other.getClientIdentity()))) &&
            ((this.clientData==null && other.getClientData()==null) || 
             (this.clientData!=null &&
              java.util.Arrays.equals(this.clientData, other.getClientData()))) &&
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
        if (getRqUID() != null) {
            _hashCode += getRqUID().hashCode();
        }
        if (getRqTm() != null) {
            _hashCode += getRqTm().hashCode();
        }
        if (getOperUID() != null) {
            _hashCode += getOperUID().hashCode();
        }
        if (getSName() != null) {
            _hashCode += getSName().hashCode();
        }
        if (getBankInfo() != null) {
            _hashCode += getBankInfo().hashCode();
        }
        if (getClientIdentity() != null) {
            _hashCode += getClientIdentity().hashCode();
        }
        if (getClientData() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getClientData());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getClientData(), i);
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
        new org.apache.axis.description.TypeDesc(UpdateProfileRqType.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://generated.listener.asfilial.phizic.rssl.com", "UpdateProfileRqType"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("rqUID");
        elemField.setXmlName(new javax.xml.namespace.QName("http://generated.listener.asfilial.phizic.rssl.com", "RqUID"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("rqTm");
        elemField.setXmlName(new javax.xml.namespace.QName("http://generated.listener.asfilial.phizic.rssl.com", "RqTm"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("operUID");
        elemField.setXmlName(new javax.xml.namespace.QName("http://generated.listener.asfilial.phizic.rssl.com", "OperUID"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("SName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://generated.listener.asfilial.phizic.rssl.com", "SName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("bankInfo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://generated.listener.asfilial.phizic.rssl.com", "BankInfo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://generated.listener.asfilial.phizic.rssl.com", "BankInfoType"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("clientIdentity");
        elemField.setXmlName(new javax.xml.namespace.QName("http://generated.listener.asfilial.phizic.rssl.com", "ClientIdentity"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://generated.listener.asfilial.phizic.rssl.com", "IdentityType"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("clientData");
        elemField.setXmlName(new javax.xml.namespace.QName("http://generated.listener.asfilial.phizic.rssl.com", "ClientData"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://generated.listener.asfilial.phizic.rssl.com", "ClientPhonesType"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        elemField.setItemQName(new javax.xml.namespace.QName("http://generated.listener.asfilial.phizic.rssl.com", "ClientPhones"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("internetClientService");
        elemField.setXmlName(new javax.xml.namespace.QName("http://generated.listener.asfilial.phizic.rssl.com", "InternetClientService"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://generated.listener.asfilial.phizic.rssl.com", "ResourcesType"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        elemField.setItemQName(new javax.xml.namespace.QName("http://generated.listener.asfilial.phizic.rssl.com", "VisibleResources"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("mobileClientService");
        elemField.setXmlName(new javax.xml.namespace.QName("http://generated.listener.asfilial.phizic.rssl.com", "MobileClientService"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://generated.listener.asfilial.phizic.rssl.com", "ResourcesType"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        elemField.setItemQName(new javax.xml.namespace.QName("http://generated.listener.asfilial.phizic.rssl.com", "VisibleResources"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ATMClientService");
        elemField.setXmlName(new javax.xml.namespace.QName("http://generated.listener.asfilial.phizic.rssl.com", "ATMClientService"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://generated.listener.asfilial.phizic.rssl.com", "ResourcesType"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        elemField.setItemQName(new javax.xml.namespace.QName("http://generated.listener.asfilial.phizic.rssl.com", "VisibleResources"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("mobileBankService");
        elemField.setXmlName(new javax.xml.namespace.QName("http://generated.listener.asfilial.phizic.rssl.com", "MobileBankService"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://generated.listener.asfilial.phizic.rssl.com", "MobileBankServiceUpdateProfileRqType"));
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
