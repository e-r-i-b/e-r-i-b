/**
 * MobileBankServiceUpdateProfileRqType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.test.wsgateclient.asfilial.generated;


/**
 * Данные по услуге «Мобильный банк».UpdateProfileRqType
 */
public class MobileBankServiceUpdateProfileRqType  implements java.io.Serializable {
    private boolean registrationStatus;

    private com.rssl.phizic.test.wsgateclient.asfilial.generated.ServiceParamsType serviceParams;

    public MobileBankServiceUpdateProfileRqType() {
    }

    public MobileBankServiceUpdateProfileRqType(
           boolean registrationStatus,
           com.rssl.phizic.test.wsgateclient.asfilial.generated.ServiceParamsType serviceParams) {
           this.registrationStatus = registrationStatus;
           this.serviceParams = serviceParams;
    }


    /**
     * Gets the registrationStatus value for this MobileBankServiceUpdateProfileRqType.
     * 
     * @return registrationStatus
     */
    public boolean isRegistrationStatus() {
        return registrationStatus;
    }


    /**
     * Sets the registrationStatus value for this MobileBankServiceUpdateProfileRqType.
     * 
     * @param registrationStatus
     */
    public void setRegistrationStatus(boolean registrationStatus) {
        this.registrationStatus = registrationStatus;
    }


    /**
     * Gets the serviceParams value for this MobileBankServiceUpdateProfileRqType.
     * 
     * @return serviceParams
     */
    public com.rssl.phizic.test.wsgateclient.asfilial.generated.ServiceParamsType getServiceParams() {
        return serviceParams;
    }


    /**
     * Sets the serviceParams value for this MobileBankServiceUpdateProfileRqType.
     * 
     * @param serviceParams
     */
    public void setServiceParams(com.rssl.phizic.test.wsgateclient.asfilial.generated.ServiceParamsType serviceParams) {
        this.serviceParams = serviceParams;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof MobileBankServiceUpdateProfileRqType)) return false;
        MobileBankServiceUpdateProfileRqType other = (MobileBankServiceUpdateProfileRqType) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            this.registrationStatus == other.isRegistrationStatus() &&
            ((this.serviceParams==null && other.getServiceParams()==null) || 
             (this.serviceParams!=null &&
              this.serviceParams.equals(other.getServiceParams())));
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
        _hashCode += (isRegistrationStatus() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        if (getServiceParams() != null) {
            _hashCode += getServiceParams().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(MobileBankServiceUpdateProfileRqType.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://generated.listener.asfilial.phizic.rssl.com", "MobileBankServiceUpdateProfileRqType"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("registrationStatus");
        elemField.setXmlName(new javax.xml.namespace.QName("http://generated.listener.asfilial.phizic.rssl.com", "RegistrationStatus"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("serviceParams");
        elemField.setXmlName(new javax.xml.namespace.QName("http://generated.listener.asfilial.phizic.rssl.com", "ServiceParams"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://generated.listener.asfilial.phizic.rssl.com", "ServiceParamsType"));
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
