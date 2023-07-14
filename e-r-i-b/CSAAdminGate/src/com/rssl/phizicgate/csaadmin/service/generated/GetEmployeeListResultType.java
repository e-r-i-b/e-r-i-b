/**
 * GetEmployeeListResultType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizicgate.csaadmin.service.generated;

public class GetEmployeeListResultType  implements java.io.Serializable {
    private com.rssl.phizicgate.csaadmin.service.generated.EmployeeType[] employees;

    public GetEmployeeListResultType() {
    }

    public GetEmployeeListResultType(
           com.rssl.phizicgate.csaadmin.service.generated.EmployeeType[] employees) {
           this.employees = employees;
    }


    /**
     * Gets the employees value for this GetEmployeeListResultType.
     * 
     * @return employees
     */
    public com.rssl.phizicgate.csaadmin.service.generated.EmployeeType[] getEmployees() {
        return employees;
    }


    /**
     * Sets the employees value for this GetEmployeeListResultType.
     * 
     * @param employees
     */
    public void setEmployees(com.rssl.phizicgate.csaadmin.service.generated.EmployeeType[] employees) {
        this.employees = employees;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof GetEmployeeListResultType)) return false;
        GetEmployeeListResultType other = (GetEmployeeListResultType) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.employees==null && other.getEmployees()==null) || 
             (this.employees!=null &&
              java.util.Arrays.equals(this.employees, other.getEmployees())));
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
        if (getEmployees() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getEmployees());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getEmployees(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(GetEmployeeListResultType.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "GetEmployeeListResultType"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("employees");
        elemField.setXmlName(new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "Employees"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "EmployeeType"));
        elemField.setNillable(true);
        elemField.setItemQName(new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "item"));
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
