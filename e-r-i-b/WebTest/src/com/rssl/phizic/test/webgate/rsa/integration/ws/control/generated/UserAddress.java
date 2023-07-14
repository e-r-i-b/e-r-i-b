/**
 * UserAddress.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated;


/**
 * This type defines the elements that make up a user's home location
 */
public class UserAddress  implements java.io.Serializable {
    private java.lang.String addressLastUpdateDate;

    private java.lang.String addressSetDate;

    private java.lang.String country;

    private java.lang.String postalCode;

    private java.lang.String region;

    public UserAddress() {
    }

    public UserAddress(
           java.lang.String addressLastUpdateDate,
           java.lang.String addressSetDate,
           java.lang.String country,
           java.lang.String postalCode,
           java.lang.String region) {
           this.addressLastUpdateDate = addressLastUpdateDate;
           this.addressSetDate = addressSetDate;
           this.country = country;
           this.postalCode = postalCode;
           this.region = region;
    }


    /**
     * Gets the addressLastUpdateDate value for this UserAddress.
     * 
     * @return addressLastUpdateDate
     */
    public java.lang.String getAddressLastUpdateDate() {
        return addressLastUpdateDate;
    }


    /**
     * Sets the addressLastUpdateDate value for this UserAddress.
     * 
     * @param addressLastUpdateDate
     */
    public void setAddressLastUpdateDate(java.lang.String addressLastUpdateDate) {
        this.addressLastUpdateDate = addressLastUpdateDate;
    }


    /**
     * Gets the addressSetDate value for this UserAddress.
     * 
     * @return addressSetDate
     */
    public java.lang.String getAddressSetDate() {
        return addressSetDate;
    }


    /**
     * Sets the addressSetDate value for this UserAddress.
     * 
     * @param addressSetDate
     */
    public void setAddressSetDate(java.lang.String addressSetDate) {
        this.addressSetDate = addressSetDate;
    }


    /**
     * Gets the country value for this UserAddress.
     * 
     * @return country
     */
    public java.lang.String getCountry() {
        return country;
    }


    /**
     * Sets the country value for this UserAddress.
     * 
     * @param country
     */
    public void setCountry(java.lang.String country) {
        this.country = country;
    }


    /**
     * Gets the postalCode value for this UserAddress.
     * 
     * @return postalCode
     */
    public java.lang.String getPostalCode() {
        return postalCode;
    }


    /**
     * Sets the postalCode value for this UserAddress.
     * 
     * @param postalCode
     */
    public void setPostalCode(java.lang.String postalCode) {
        this.postalCode = postalCode;
    }


    /**
     * Gets the region value for this UserAddress.
     * 
     * @return region
     */
    public java.lang.String getRegion() {
        return region;
    }


    /**
     * Sets the region value for this UserAddress.
     * 
     * @param region
     */
    public void setRegion(java.lang.String region) {
        this.region = region;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof UserAddress)) return false;
        UserAddress other = (UserAddress) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.addressLastUpdateDate==null && other.getAddressLastUpdateDate()==null) || 
             (this.addressLastUpdateDate!=null &&
              this.addressLastUpdateDate.equals(other.getAddressLastUpdateDate()))) &&
            ((this.addressSetDate==null && other.getAddressSetDate()==null) || 
             (this.addressSetDate!=null &&
              this.addressSetDate.equals(other.getAddressSetDate()))) &&
            ((this.country==null && other.getCountry()==null) || 
             (this.country!=null &&
              this.country.equals(other.getCountry()))) &&
            ((this.postalCode==null && other.getPostalCode()==null) || 
             (this.postalCode!=null &&
              this.postalCode.equals(other.getPostalCode()))) &&
            ((this.region==null && other.getRegion()==null) || 
             (this.region!=null &&
              this.region.equals(other.getRegion())));
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
        if (getAddressLastUpdateDate() != null) {
            _hashCode += getAddressLastUpdateDate().hashCode();
        }
        if (getAddressSetDate() != null) {
            _hashCode += getAddressSetDate().hashCode();
        }
        if (getCountry() != null) {
            _hashCode += getCountry().hashCode();
        }
        if (getPostalCode() != null) {
            _hashCode += getPostalCode().hashCode();
        }
        if (getRegion() != null) {
            _hashCode += getRegion().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(UserAddress.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "UserAddress"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("addressLastUpdateDate");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "addressLastUpdateDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("addressSetDate");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "addressSetDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("country");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "country"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("postalCode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "postalCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("region");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "region"));
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
