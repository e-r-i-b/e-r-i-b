/**
 * GetEmployeeSynchronizationDataResultType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizicgate.csaadmin.service.generated;


/**
 * Данные синхронизации сотрудника
 */
public class GetEmployeeSynchronizationDataResultType  implements java.io.Serializable {
    private com.rssl.phizicgate.csaadmin.service.generated.EmployeeType employee;

    private com.rssl.phizicgate.csaadmin.service.generated.LoginType login;

    private com.rssl.phizicgate.csaadmin.service.generated.DepartmentType[] allowedDepartments;

    public GetEmployeeSynchronizationDataResultType() {
    }

    public GetEmployeeSynchronizationDataResultType(
           com.rssl.phizicgate.csaadmin.service.generated.EmployeeType employee,
           com.rssl.phizicgate.csaadmin.service.generated.LoginType login,
           com.rssl.phizicgate.csaadmin.service.generated.DepartmentType[] allowedDepartments) {
           this.employee = employee;
           this.login = login;
           this.allowedDepartments = allowedDepartments;
    }


    /**
     * Gets the employee value for this GetEmployeeSynchronizationDataResultType.
     * 
     * @return employee
     */
    public com.rssl.phizicgate.csaadmin.service.generated.EmployeeType getEmployee() {
        return employee;
    }


    /**
     * Sets the employee value for this GetEmployeeSynchronizationDataResultType.
     * 
     * @param employee
     */
    public void setEmployee(com.rssl.phizicgate.csaadmin.service.generated.EmployeeType employee) {
        this.employee = employee;
    }


    /**
     * Gets the login value for this GetEmployeeSynchronizationDataResultType.
     * 
     * @return login
     */
    public com.rssl.phizicgate.csaadmin.service.generated.LoginType getLogin() {
        return login;
    }


    /**
     * Sets the login value for this GetEmployeeSynchronizationDataResultType.
     * 
     * @param login
     */
    public void setLogin(com.rssl.phizicgate.csaadmin.service.generated.LoginType login) {
        this.login = login;
    }


    /**
     * Gets the allowedDepartments value for this GetEmployeeSynchronizationDataResultType.
     * 
     * @return allowedDepartments
     */
    public com.rssl.phizicgate.csaadmin.service.generated.DepartmentType[] getAllowedDepartments() {
        return allowedDepartments;
    }


    /**
     * Sets the allowedDepartments value for this GetEmployeeSynchronizationDataResultType.
     * 
     * @param allowedDepartments
     */
    public void setAllowedDepartments(com.rssl.phizicgate.csaadmin.service.generated.DepartmentType[] allowedDepartments) {
        this.allowedDepartments = allowedDepartments;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof GetEmployeeSynchronizationDataResultType)) return false;
        GetEmployeeSynchronizationDataResultType other = (GetEmployeeSynchronizationDataResultType) obj;
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
              this.employee.equals(other.getEmployee()))) &&
            ((this.login==null && other.getLogin()==null) || 
             (this.login!=null &&
              this.login.equals(other.getLogin()))) &&
            ((this.allowedDepartments==null && other.getAllowedDepartments()==null) || 
             (this.allowedDepartments!=null &&
              java.util.Arrays.equals(this.allowedDepartments, other.getAllowedDepartments())));
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
        if (getLogin() != null) {
            _hashCode += getLogin().hashCode();
        }
        if (getAllowedDepartments() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getAllowedDepartments());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getAllowedDepartments(), i);
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
        new org.apache.axis.description.TypeDesc(GetEmployeeSynchronizationDataResultType.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "GetEmployeeSynchronizationDataResultType"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("employee");
        elemField.setXmlName(new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "Employee"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "EmployeeType"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("login");
        elemField.setXmlName(new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "Login"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "LoginType"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("allowedDepartments");
        elemField.setXmlName(new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "AllowedDepartments"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "DepartmentType"));
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
