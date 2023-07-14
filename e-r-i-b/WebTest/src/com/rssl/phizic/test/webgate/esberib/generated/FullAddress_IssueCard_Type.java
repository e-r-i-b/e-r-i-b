/**
 * FullAddress_IssueCard_Type.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.test.webgate.esberib.generated;


/**
 * Информация о полном адресе
 */
public class FullAddress_IssueCard_Type  implements java.io.Serializable {
    /* Тип адреса */
    private java.lang.String addressType;

    /* Почтовый индекс */
    private java.lang.String postalCode;

    /* Страна */
    private java.lang.String country;

    /* Регион(республика,область,край) */
    private java.lang.String region;

    /* Город */
    private java.lang.String city;

    /* Остальной адрес */
    private java.lang.String afterCityAdress;

    public FullAddress_IssueCard_Type() {
    }

    public FullAddress_IssueCard_Type(
           java.lang.String addressType,
           java.lang.String postalCode,
           java.lang.String country,
           java.lang.String region,
           java.lang.String city,
           java.lang.String afterCityAdress) {
           this.addressType = addressType;
           this.postalCode = postalCode;
           this.country = country;
           this.region = region;
           this.city = city;
           this.afterCityAdress = afterCityAdress;
    }


    /**
     * Gets the addressType value for this FullAddress_IssueCard_Type.
     * 
     * @return addressType   * Тип адреса
     */
    public java.lang.String getAddressType() {
        return addressType;
    }


    /**
     * Sets the addressType value for this FullAddress_IssueCard_Type.
     * 
     * @param addressType   * Тип адреса
     */
    public void setAddressType(java.lang.String addressType) {
        this.addressType = addressType;
    }


    /**
     * Gets the postalCode value for this FullAddress_IssueCard_Type.
     * 
     * @return postalCode   * Почтовый индекс
     */
    public java.lang.String getPostalCode() {
        return postalCode;
    }


    /**
     * Sets the postalCode value for this FullAddress_IssueCard_Type.
     * 
     * @param postalCode   * Почтовый индекс
     */
    public void setPostalCode(java.lang.String postalCode) {
        this.postalCode = postalCode;
    }


    /**
     * Gets the country value for this FullAddress_IssueCard_Type.
     * 
     * @return country   * Страна
     */
    public java.lang.String getCountry() {
        return country;
    }


    /**
     * Sets the country value for this FullAddress_IssueCard_Type.
     * 
     * @param country   * Страна
     */
    public void setCountry(java.lang.String country) {
        this.country = country;
    }


    /**
     * Gets the region value for this FullAddress_IssueCard_Type.
     * 
     * @return region   * Регион(республика,область,край)
     */
    public java.lang.String getRegion() {
        return region;
    }


    /**
     * Sets the region value for this FullAddress_IssueCard_Type.
     * 
     * @param region   * Регион(республика,область,край)
     */
    public void setRegion(java.lang.String region) {
        this.region = region;
    }


    /**
     * Gets the city value for this FullAddress_IssueCard_Type.
     * 
     * @return city   * Город
     */
    public java.lang.String getCity() {
        return city;
    }


    /**
     * Sets the city value for this FullAddress_IssueCard_Type.
     * 
     * @param city   * Город
     */
    public void setCity(java.lang.String city) {
        this.city = city;
    }


    /**
     * Gets the afterCityAdress value for this FullAddress_IssueCard_Type.
     * 
     * @return afterCityAdress   * Остальной адрес
     */
    public java.lang.String getAfterCityAdress() {
        return afterCityAdress;
    }


    /**
     * Sets the afterCityAdress value for this FullAddress_IssueCard_Type.
     * 
     * @param afterCityAdress   * Остальной адрес
     */
    public void setAfterCityAdress(java.lang.String afterCityAdress) {
        this.afterCityAdress = afterCityAdress;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof FullAddress_IssueCard_Type)) return false;
        FullAddress_IssueCard_Type other = (FullAddress_IssueCard_Type) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.addressType==null && other.getAddressType()==null) || 
             (this.addressType!=null &&
              this.addressType.equals(other.getAddressType()))) &&
            ((this.postalCode==null && other.getPostalCode()==null) || 
             (this.postalCode!=null &&
              this.postalCode.equals(other.getPostalCode()))) &&
            ((this.country==null && other.getCountry()==null) || 
             (this.country!=null &&
              this.country.equals(other.getCountry()))) &&
            ((this.region==null && other.getRegion()==null) || 
             (this.region!=null &&
              this.region.equals(other.getRegion()))) &&
            ((this.city==null && other.getCity()==null) || 
             (this.city!=null &&
              this.city.equals(other.getCity()))) &&
            ((this.afterCityAdress==null && other.getAfterCityAdress()==null) || 
             (this.afterCityAdress!=null &&
              this.afterCityAdress.equals(other.getAfterCityAdress())));
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
        if (getAddressType() != null) {
            _hashCode += getAddressType().hashCode();
        }
        if (getPostalCode() != null) {
            _hashCode += getPostalCode().hashCode();
        }
        if (getCountry() != null) {
            _hashCode += getCountry().hashCode();
        }
        if (getRegion() != null) {
            _hashCode += getRegion().hashCode();
        }
        if (getCity() != null) {
            _hashCode += getCity().hashCode();
        }
        if (getAfterCityAdress() != null) {
            _hashCode += getAfterCityAdress().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(FullAddress_IssueCard_Type.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "FullAddress_IssueCard_Type"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("addressType");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "AddressType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "String"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("postalCode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "PostalCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("country");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Country"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("region");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Region"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("city");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "City"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("afterCityAdress");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "AfterCityAdress"));
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
