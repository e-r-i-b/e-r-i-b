/**
 * DirectSaler_Type.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.test.webgate.esberib.generated;


/**
 * ГСПП
 */
public class DirectSaler_Type  implements java.io.Serializable {
    /* Идентификатор ГСПП */
    private java.lang.String directSalerId;

    /* Наименование организации клиента */
    private java.lang.String orgName;

    /* Наименование региона организации клиента */
    private java.lang.String orgRegion;

    /* Наименование населенного пункта организации клиента */
    private java.lang.String orgPlace;

    /* Адрес организации клиента */
    private java.lang.String orgAddress;

    public DirectSaler_Type() {
    }

    public DirectSaler_Type(
           java.lang.String directSalerId,
           java.lang.String orgName,
           java.lang.String orgRegion,
           java.lang.String orgPlace,
           java.lang.String orgAddress) {
           this.directSalerId = directSalerId;
           this.orgName = orgName;
           this.orgRegion = orgRegion;
           this.orgPlace = orgPlace;
           this.orgAddress = orgAddress;
    }


    /**
     * Gets the directSalerId value for this DirectSaler_Type.
     * 
     * @return directSalerId   * Идентификатор ГСПП
     */
    public java.lang.String getDirectSalerId() {
        return directSalerId;
    }


    /**
     * Sets the directSalerId value for this DirectSaler_Type.
     * 
     * @param directSalerId   * Идентификатор ГСПП
     */
    public void setDirectSalerId(java.lang.String directSalerId) {
        this.directSalerId = directSalerId;
    }


    /**
     * Gets the orgName value for this DirectSaler_Type.
     * 
     * @return orgName   * Наименование организации клиента
     */
    public java.lang.String getOrgName() {
        return orgName;
    }


    /**
     * Sets the orgName value for this DirectSaler_Type.
     * 
     * @param orgName   * Наименование организации клиента
     */
    public void setOrgName(java.lang.String orgName) {
        this.orgName = orgName;
    }


    /**
     * Gets the orgRegion value for this DirectSaler_Type.
     * 
     * @return orgRegion   * Наименование региона организации клиента
     */
    public java.lang.String getOrgRegion() {
        return orgRegion;
    }


    /**
     * Sets the orgRegion value for this DirectSaler_Type.
     * 
     * @param orgRegion   * Наименование региона организации клиента
     */
    public void setOrgRegion(java.lang.String orgRegion) {
        this.orgRegion = orgRegion;
    }


    /**
     * Gets the orgPlace value for this DirectSaler_Type.
     * 
     * @return orgPlace   * Наименование населенного пункта организации клиента
     */
    public java.lang.String getOrgPlace() {
        return orgPlace;
    }


    /**
     * Sets the orgPlace value for this DirectSaler_Type.
     * 
     * @param orgPlace   * Наименование населенного пункта организации клиента
     */
    public void setOrgPlace(java.lang.String orgPlace) {
        this.orgPlace = orgPlace;
    }


    /**
     * Gets the orgAddress value for this DirectSaler_Type.
     * 
     * @return orgAddress   * Адрес организации клиента
     */
    public java.lang.String getOrgAddress() {
        return orgAddress;
    }


    /**
     * Sets the orgAddress value for this DirectSaler_Type.
     * 
     * @param orgAddress   * Адрес организации клиента
     */
    public void setOrgAddress(java.lang.String orgAddress) {
        this.orgAddress = orgAddress;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof DirectSaler_Type)) return false;
        DirectSaler_Type other = (DirectSaler_Type) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.directSalerId==null && other.getDirectSalerId()==null) || 
             (this.directSalerId!=null &&
              this.directSalerId.equals(other.getDirectSalerId()))) &&
            ((this.orgName==null && other.getOrgName()==null) || 
             (this.orgName!=null &&
              this.orgName.equals(other.getOrgName()))) &&
            ((this.orgRegion==null && other.getOrgRegion()==null) || 
             (this.orgRegion!=null &&
              this.orgRegion.equals(other.getOrgRegion()))) &&
            ((this.orgPlace==null && other.getOrgPlace()==null) || 
             (this.orgPlace!=null &&
              this.orgPlace.equals(other.getOrgPlace()))) &&
            ((this.orgAddress==null && other.getOrgAddress()==null) || 
             (this.orgAddress!=null &&
              this.orgAddress.equals(other.getOrgAddress())));
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
        if (getDirectSalerId() != null) {
            _hashCode += getDirectSalerId().hashCode();
        }
        if (getOrgName() != null) {
            _hashCode += getOrgName().hashCode();
        }
        if (getOrgRegion() != null) {
            _hashCode += getOrgRegion().hashCode();
        }
        if (getOrgPlace() != null) {
            _hashCode += getOrgPlace().hashCode();
        }
        if (getOrgAddress() != null) {
            _hashCode += getOrgAddress().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(DirectSaler_Type.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "DirectSaler_Type"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("directSalerId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "DirectSalerId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "C"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("orgName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "OrgName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "C"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("orgRegion");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "OrgRegion"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "C"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("orgPlace");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "OrgPlace"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "C"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("orgAddress");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "OrgAddress"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "C"));
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
