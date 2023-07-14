/**
 * GetCurrentEmployeeContextResultType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizicgate.csaadmin.service.generated;

public class GetCurrentEmployeeContextResultType  implements java.io.Serializable {
    private long loginId;

    private boolean allTbAccess;

    public GetCurrentEmployeeContextResultType() {
    }

    public GetCurrentEmployeeContextResultType(
           long loginId,
           boolean allTbAccess) {
           this.loginId = loginId;
           this.allTbAccess = allTbAccess;
    }


    /**
     * Gets the loginId value for this GetCurrentEmployeeContextResultType.
     * 
     * @return loginId
     */
    public long getLoginId() {
        return loginId;
    }


    /**
     * Sets the loginId value for this GetCurrentEmployeeContextResultType.
     * 
     * @param loginId
     */
    public void setLoginId(long loginId) {
        this.loginId = loginId;
    }


    /**
     * Gets the allTbAccess value for this GetCurrentEmployeeContextResultType.
     * 
     * @return allTbAccess
     */
    public boolean isAllTbAccess() {
        return allTbAccess;
    }


    /**
     * Sets the allTbAccess value for this GetCurrentEmployeeContextResultType.
     * 
     * @param allTbAccess
     */
    public void setAllTbAccess(boolean allTbAccess) {
        this.allTbAccess = allTbAccess;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof GetCurrentEmployeeContextResultType)) return false;
        GetCurrentEmployeeContextResultType other = (GetCurrentEmployeeContextResultType) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            this.loginId == other.getLoginId() &&
            this.allTbAccess == other.isAllTbAccess();
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
        _hashCode += new Long(getLoginId()).hashCode();
        _hashCode += (isAllTbAccess() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(GetCurrentEmployeeContextResultType.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "GetCurrentEmployeeContextResultType"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("loginId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "loginId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("allTbAccess");
        elemField.setXmlName(new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "allTbAccess"));
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
