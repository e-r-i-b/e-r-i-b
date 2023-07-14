/**
 * PersonInfo.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.kba;


/**
 * This object defines the contact information for a user
 */
public class PersonInfo  implements java.io.Serializable {
    private com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.kba.SSNInfo ssnInfo;

    private com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.kba.NameInfo nameInfo;

    private com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.kba.AddressInfo addressInfo;

    private com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.kba.BirthdayInfo birthdayInfo;

    private java.lang.String languageInfo;

    public PersonInfo() {
    }

    public PersonInfo(
           com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.kba.SSNInfo ssnInfo,
           com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.kba.NameInfo nameInfo,
           com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.kba.AddressInfo addressInfo,
           com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.kba.BirthdayInfo birthdayInfo,
           java.lang.String languageInfo) {
           this.ssnInfo = ssnInfo;
           this.nameInfo = nameInfo;
           this.addressInfo = addressInfo;
           this.birthdayInfo = birthdayInfo;
           this.languageInfo = languageInfo;
    }


    /**
     * Gets the ssnInfo value for this PersonInfo.
     * 
     * @return ssnInfo
     */
    public com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.kba.SSNInfo getSsnInfo() {
        return ssnInfo;
    }


    /**
     * Sets the ssnInfo value for this PersonInfo.
     * 
     * @param ssnInfo
     */
    public void setSsnInfo(com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.kba.SSNInfo ssnInfo) {
        this.ssnInfo = ssnInfo;
    }


    /**
     * Gets the nameInfo value for this PersonInfo.
     * 
     * @return nameInfo
     */
    public com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.kba.NameInfo getNameInfo() {
        return nameInfo;
    }


    /**
     * Sets the nameInfo value for this PersonInfo.
     * 
     * @param nameInfo
     */
    public void setNameInfo(com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.kba.NameInfo nameInfo) {
        this.nameInfo = nameInfo;
    }


    /**
     * Gets the addressInfo value for this PersonInfo.
     * 
     * @return addressInfo
     */
    public com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.kba.AddressInfo getAddressInfo() {
        return addressInfo;
    }


    /**
     * Sets the addressInfo value for this PersonInfo.
     * 
     * @param addressInfo
     */
    public void setAddressInfo(com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.kba.AddressInfo addressInfo) {
        this.addressInfo = addressInfo;
    }


    /**
     * Gets the birthdayInfo value for this PersonInfo.
     * 
     * @return birthdayInfo
     */
    public com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.kba.BirthdayInfo getBirthdayInfo() {
        return birthdayInfo;
    }


    /**
     * Sets the birthdayInfo value for this PersonInfo.
     * 
     * @param birthdayInfo
     */
    public void setBirthdayInfo(com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.kba.BirthdayInfo birthdayInfo) {
        this.birthdayInfo = birthdayInfo;
    }


    /**
     * Gets the languageInfo value for this PersonInfo.
     * 
     * @return languageInfo
     */
    public java.lang.String getLanguageInfo() {
        return languageInfo;
    }


    /**
     * Sets the languageInfo value for this PersonInfo.
     * 
     * @param languageInfo
     */
    public void setLanguageInfo(java.lang.String languageInfo) {
        this.languageInfo = languageInfo;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof PersonInfo)) return false;
        PersonInfo other = (PersonInfo) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.ssnInfo==null && other.getSsnInfo()==null) || 
             (this.ssnInfo!=null &&
              this.ssnInfo.equals(other.getSsnInfo()))) &&
            ((this.nameInfo==null && other.getNameInfo()==null) || 
             (this.nameInfo!=null &&
              this.nameInfo.equals(other.getNameInfo()))) &&
            ((this.addressInfo==null && other.getAddressInfo()==null) || 
             (this.addressInfo!=null &&
              this.addressInfo.equals(other.getAddressInfo()))) &&
            ((this.birthdayInfo==null && other.getBirthdayInfo()==null) || 
             (this.birthdayInfo!=null &&
              this.birthdayInfo.equals(other.getBirthdayInfo()))) &&
            ((this.languageInfo==null && other.getLanguageInfo()==null) || 
             (this.languageInfo!=null &&
              this.languageInfo.equals(other.getLanguageInfo())));
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
        if (getSsnInfo() != null) {
            _hashCode += getSsnInfo().hashCode();
        }
        if (getNameInfo() != null) {
            _hashCode += getNameInfo().hashCode();
        }
        if (getAddressInfo() != null) {
            _hashCode += getAddressInfo().hashCode();
        }
        if (getBirthdayInfo() != null) {
            _hashCode += getBirthdayInfo().hashCode();
        }
        if (getLanguageInfo() != null) {
            _hashCode += getLanguageInfo().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(PersonInfo.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.kba.csd.rsa.com", "PersonInfo"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ssnInfo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.kba.csd.rsa.com", "ssnInfo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.kba.csd.rsa.com", "SSNInfo"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("nameInfo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.kba.csd.rsa.com", "nameInfo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.kba.csd.rsa.com", "NameInfo"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("addressInfo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.kba.csd.rsa.com", "addressInfo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.kba.csd.rsa.com", "AddressInfo"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("birthdayInfo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.kba.csd.rsa.com", "birthdayInfo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.kba.csd.rsa.com", "BirthdayInfo"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("languageInfo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.kba.csd.rsa.com", "languageInfo"));
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
