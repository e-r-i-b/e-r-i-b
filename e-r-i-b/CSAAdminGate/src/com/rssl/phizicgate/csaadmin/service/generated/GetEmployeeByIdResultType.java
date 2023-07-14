/**
 * GetEmployeeByIdResultType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizicgate.csaadmin.service.generated;

public class GetEmployeeByIdResultType  implements java.io.Serializable {
    private com.rssl.phizicgate.csaadmin.service.generated.EmployeeType employee;

    public GetEmployeeByIdResultType() {
    }

    public GetEmployeeByIdResultType(
           com.rssl.phizicgate.csaadmin.service.generated.EmployeeType employee) {
           this.employee = employee;
    }


    /**
     * Gets the employee value for this GetEmployeeByIdResultType.
     * 
     * @return employee
     */
    public com.rssl.phizicgate.csaadmin.service.generated.EmployeeType getEmployee() {
        return employee;
    }


    /**
     * Sets the employee value for this GetEmployeeByIdResultType.
     * 
     * @param employee
     */
    public void setEmployee(com.rssl.phizicgate.csaadmin.service.generated.EmployeeType employee) {
        this.employee = employee;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof GetEmployeeByIdResultType)) return false;
        GetEmployeeByIdResultType other = (GetEmployeeByIdResultType) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
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
        if (getEmployee() != null) {
            _hashCode += getEmployee().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(GetEmployeeByIdResultType.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "GetEmployeeByIdResultType"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("employee");
        elemField.setXmlName(new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "Employee"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "EmployeeType"));
        elemField.setNillable(true);
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
