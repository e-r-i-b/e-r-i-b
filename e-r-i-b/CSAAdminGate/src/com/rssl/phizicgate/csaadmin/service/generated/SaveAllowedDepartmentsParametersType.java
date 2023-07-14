/**
 * SaveAllowedDepartmentsParametersType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizicgate.csaadmin.service.generated;

public class SaveAllowedDepartmentsParametersType  implements java.io.Serializable {
    private com.rssl.phizicgate.csaadmin.service.generated.DepartmentType[] addDepartments;

    private com.rssl.phizicgate.csaadmin.service.generated.DepartmentType[] deleteDepartments;

    private com.rssl.phizicgate.csaadmin.service.generated.EmployeeType employee;

    public SaveAllowedDepartmentsParametersType() {
    }

    public SaveAllowedDepartmentsParametersType(
           com.rssl.phizicgate.csaadmin.service.generated.DepartmentType[] addDepartments,
           com.rssl.phizicgate.csaadmin.service.generated.DepartmentType[] deleteDepartments,
           com.rssl.phizicgate.csaadmin.service.generated.EmployeeType employee) {
           this.addDepartments = addDepartments;
           this.deleteDepartments = deleteDepartments;
           this.employee = employee;
    }


    /**
     * Gets the addDepartments value for this SaveAllowedDepartmentsParametersType.
     * 
     * @return addDepartments
     */
    public com.rssl.phizicgate.csaadmin.service.generated.DepartmentType[] getAddDepartments() {
        return addDepartments;
    }


    /**
     * Sets the addDepartments value for this SaveAllowedDepartmentsParametersType.
     * 
     * @param addDepartments
     */
    public void setAddDepartments(com.rssl.phizicgate.csaadmin.service.generated.DepartmentType[] addDepartments) {
        this.addDepartments = addDepartments;
    }


    /**
     * Gets the deleteDepartments value for this SaveAllowedDepartmentsParametersType.
     * 
     * @return deleteDepartments
     */
    public com.rssl.phizicgate.csaadmin.service.generated.DepartmentType[] getDeleteDepartments() {
        return deleteDepartments;
    }


    /**
     * Sets the deleteDepartments value for this SaveAllowedDepartmentsParametersType.
     * 
     * @param deleteDepartments
     */
    public void setDeleteDepartments(com.rssl.phizicgate.csaadmin.service.generated.DepartmentType[] deleteDepartments) {
        this.deleteDepartments = deleteDepartments;
    }


    /**
     * Gets the employee value for this SaveAllowedDepartmentsParametersType.
     * 
     * @return employee
     */
    public com.rssl.phizicgate.csaadmin.service.generated.EmployeeType getEmployee() {
        return employee;
    }


    /**
     * Sets the employee value for this SaveAllowedDepartmentsParametersType.
     * 
     * @param employee
     */
    public void setEmployee(com.rssl.phizicgate.csaadmin.service.generated.EmployeeType employee) {
        this.employee = employee;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof SaveAllowedDepartmentsParametersType)) return false;
        SaveAllowedDepartmentsParametersType other = (SaveAllowedDepartmentsParametersType) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.addDepartments==null && other.getAddDepartments()==null) || 
             (this.addDepartments!=null &&
              java.util.Arrays.equals(this.addDepartments, other.getAddDepartments()))) &&
            ((this.deleteDepartments==null && other.getDeleteDepartments()==null) || 
             (this.deleteDepartments!=null &&
              java.util.Arrays.equals(this.deleteDepartments, other.getDeleteDepartments()))) &&
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
        if (getAddDepartments() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getAddDepartments());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getAddDepartments(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getDeleteDepartments() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getDeleteDepartments());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getDeleteDepartments(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getEmployee() != null) {
            _hashCode += getEmployee().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(SaveAllowedDepartmentsParametersType.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "SaveAllowedDepartmentsParametersType"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("addDepartments");
        elemField.setXmlName(new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "AddDepartments"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "DepartmentType"));
        elemField.setNillable(false);
        elemField.setItemQName(new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "item"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("deleteDepartments");
        elemField.setXmlName(new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "DeleteDepartments"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "DepartmentType"));
        elemField.setNillable(false);
        elemField.setItemQName(new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "item"));
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
