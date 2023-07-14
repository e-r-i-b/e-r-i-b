/**
 * EmploymentHistoryType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.test.webgate.esberib.generated;


/**
 * Тип <Информация о месте работы>
 */
public class EmploymentHistoryType  implements java.io.Serializable {
    /* Информация о работодателе */
    private com.rssl.phizic.test.webgate.esberib.generated.OrgInfo_Type orgInfo;

    /* Должность */
    private java.lang.String jobTitle;

    public EmploymentHistoryType() {
    }

    public EmploymentHistoryType(
           com.rssl.phizic.test.webgate.esberib.generated.OrgInfo_Type orgInfo,
           java.lang.String jobTitle) {
           this.orgInfo = orgInfo;
           this.jobTitle = jobTitle;
    }


    /**
     * Gets the orgInfo value for this EmploymentHistoryType.
     * 
     * @return orgInfo   * Информация о работодателе
     */
    public com.rssl.phizic.test.webgate.esberib.generated.OrgInfo_Type getOrgInfo() {
        return orgInfo;
    }


    /**
     * Sets the orgInfo value for this EmploymentHistoryType.
     * 
     * @param orgInfo   * Информация о работодателе
     */
    public void setOrgInfo(com.rssl.phizic.test.webgate.esberib.generated.OrgInfo_Type orgInfo) {
        this.orgInfo = orgInfo;
    }


    /**
     * Gets the jobTitle value for this EmploymentHistoryType.
     * 
     * @return jobTitle   * Должность
     */
    public java.lang.String getJobTitle() {
        return jobTitle;
    }


    /**
     * Sets the jobTitle value for this EmploymentHistoryType.
     * 
     * @param jobTitle   * Должность
     */
    public void setJobTitle(java.lang.String jobTitle) {
        this.jobTitle = jobTitle;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof EmploymentHistoryType)) return false;
        EmploymentHistoryType other = (EmploymentHistoryType) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.orgInfo==null && other.getOrgInfo()==null) || 
             (this.orgInfo!=null &&
              this.orgInfo.equals(other.getOrgInfo()))) &&
            ((this.jobTitle==null && other.getJobTitle()==null) || 
             (this.jobTitle!=null &&
              this.jobTitle.equals(other.getJobTitle())));
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
        if (getOrgInfo() != null) {
            _hashCode += getOrgInfo().hashCode();
        }
        if (getJobTitle() != null) {
            _hashCode += getJobTitle().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(EmploymentHistoryType.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "EmploymentHistoryType"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("orgInfo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "OrgInfo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "OrgInfo_Type"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("jobTitle");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "JobTitle"));
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
