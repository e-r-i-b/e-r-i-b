/**
 * AssignAccessSchemeEmployeeParametersType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizicgate.csaadmin.service.generated;

public class AssignAccessSchemeEmployeeParametersType  implements java.io.Serializable {
    private java.lang.Long schemeId;

    private com.rssl.phizicgate.csaadmin.service.generated.EmployeeType employee;

    public AssignAccessSchemeEmployeeParametersType() {
    }

    public AssignAccessSchemeEmployeeParametersType(
           java.lang.Long schemeId,
           com.rssl.phizicgate.csaadmin.service.generated.EmployeeType employee) {
           this.schemeId = schemeId;
           this.employee = employee;
    }


    /**
     * Gets the schemeId value for this AssignAccessSchemeEmployeeParametersType.
     * 
     * @return schemeId
     */
    public java.lang.Long getSchemeId() {
        return schemeId;
    }


    /**
     * Sets the schemeId value for this AssignAccessSchemeEmployeeParametersType.
     * 
     * @param schemeId
     */
    public void setSchemeId(java.lang.Long schemeId) {
        this.schemeId = schemeId;
    }


    /**
     * Gets the employee value for this AssignAccessSchemeEmployeeParametersType.
     * 
     * @return employee
     */
    public com.rssl.phizicgate.csaadmin.service.generated.EmployeeType getEmployee() {
        return employee;
    }


    /**
     * Sets the employee value for this AssignAccessSchemeEmployeeParametersType.
     * 
     * @param employee
     */
    public void setEmployee(com.rssl.phizicgate.csaadmin.service.generated.EmployeeType employee) {
        this.employee = employee;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof AssignAccessSchemeEmployeeParametersType)) return false;
        AssignAccessSchemeEmployeeParametersType other = (AssignAccessSchemeEmployeeParametersType) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.schemeId==null && other.getSchemeId()==null) || 
             (this.schemeId!=null &&
              this.schemeId.equals(other.getSchemeId()))) &&
            ((this.employee==null && other.getEmployee()==null) || 
             (this.employee!=null &&
              this.employee.equals(other.getEmployee())));
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
        if (getSchemeId() != null) {
            _hashCode += getSchemeId().hashCode();
        }
        if (getEmployee() != null) {
            _hashCode += getEmployee().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(AssignAccessSchemeEmployeeParametersType.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "AssignAccessSchemeEmployeeParametersType"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("schemeId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "SchemeId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("employee");
        elemField.setXmlName(new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "Employee"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "EmployeeType"));
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
