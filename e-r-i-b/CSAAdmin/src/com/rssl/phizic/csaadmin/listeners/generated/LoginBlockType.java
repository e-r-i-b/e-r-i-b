/**
 * LoginBlockType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.csaadmin.listeners.generated;


/**
 * Блокировка
 */
public class LoginBlockType  implements java.io.Serializable {
    private java.lang.String reasonType;

    private java.lang.String reasonDescription;

    private java.lang.String blockedFrom;

    private java.lang.String blockedUntil;

    private com.rssl.phizic.csaadmin.listeners.generated.LoginType employee;

    public LoginBlockType() {
    }

    public LoginBlockType(
           java.lang.String reasonType,
           java.lang.String reasonDescription,
           java.lang.String blockedFrom,
           java.lang.String blockedUntil,
           com.rssl.phizic.csaadmin.listeners.generated.LoginType employee) {
           this.reasonType = reasonType;
           this.reasonDescription = reasonDescription;
           this.blockedFrom = blockedFrom;
           this.blockedUntil = blockedUntil;
           this.employee = employee;
    }


    /**
     * Gets the reasonType value for this LoginBlockType.
     * 
     * @return reasonType
     */
    public java.lang.String getReasonType() {
        return reasonType;
    }


    /**
     * Sets the reasonType value for this LoginBlockType.
     * 
     * @param reasonType
     */
    public void setReasonType(java.lang.String reasonType) {
        this.reasonType = reasonType;
    }


    /**
     * Gets the reasonDescription value for this LoginBlockType.
     * 
     * @return reasonDescription
     */
    public java.lang.String getReasonDescription() {
        return reasonDescription;
    }


    /**
     * Sets the reasonDescription value for this LoginBlockType.
     * 
     * @param reasonDescription
     */
    public void setReasonDescription(java.lang.String reasonDescription) {
        this.reasonDescription = reasonDescription;
    }


    /**
     * Gets the blockedFrom value for this LoginBlockType.
     * 
     * @return blockedFrom
     */
    public java.lang.String getBlockedFrom() {
        return blockedFrom;
    }


    /**
     * Sets the blockedFrom value for this LoginBlockType.
     * 
     * @param blockedFrom
     */
    public void setBlockedFrom(java.lang.String blockedFrom) {
        this.blockedFrom = blockedFrom;
    }


    /**
     * Gets the blockedUntil value for this LoginBlockType.
     * 
     * @return blockedUntil
     */
    public java.lang.String getBlockedUntil() {
        return blockedUntil;
    }


    /**
     * Sets the blockedUntil value for this LoginBlockType.
     * 
     * @param blockedUntil
     */
    public void setBlockedUntil(java.lang.String blockedUntil) {
        this.blockedUntil = blockedUntil;
    }


    /**
     * Gets the employee value for this LoginBlockType.
     * 
     * @return employee
     */
    public com.rssl.phizic.csaadmin.listeners.generated.LoginType getEmployee() {
        return employee;
    }


    /**
     * Sets the employee value for this LoginBlockType.
     * 
     * @param employee
     */
    public void setEmployee(com.rssl.phizic.csaadmin.listeners.generated.LoginType employee) {
        this.employee = employee;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof LoginBlockType)) return false;
        LoginBlockType other = (LoginBlockType) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.reasonType==null && other.getReasonType()==null) || 
             (this.reasonType!=null &&
              this.reasonType.equals(other.getReasonType()))) &&
            ((this.reasonDescription==null && other.getReasonDescription()==null) || 
             (this.reasonDescription!=null &&
              this.reasonDescription.equals(other.getReasonDescription()))) &&
            ((this.blockedFrom==null && other.getBlockedFrom()==null) || 
             (this.blockedFrom!=null &&
              this.blockedFrom.equals(other.getBlockedFrom()))) &&
            ((this.blockedUntil==null && other.getBlockedUntil()==null) || 
             (this.blockedUntil!=null &&
              this.blockedUntil.equals(other.getBlockedUntil()))) &&
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
        if (getReasonType() != null) {
            _hashCode += getReasonType().hashCode();
        }
        if (getReasonDescription() != null) {
            _hashCode += getReasonDescription().hashCode();
        }
        if (getBlockedFrom() != null) {
            _hashCode += getBlockedFrom().hashCode();
        }
        if (getBlockedUntil() != null) {
            _hashCode += getBlockedUntil().hashCode();
        }
        if (getEmployee() != null) {
            _hashCode += getEmployee().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(LoginBlockType.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "LoginBlockType"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("reasonType");
        elemField.setXmlName(new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "reasonType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("reasonDescription");
        elemField.setXmlName(new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "reasonDescription"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("blockedFrom");
        elemField.setXmlName(new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "blockedFrom"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("blockedUntil");
        elemField.setXmlName(new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "blockedUntil"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("employee");
        elemField.setXmlName(new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "employee"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "LoginType"));
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
