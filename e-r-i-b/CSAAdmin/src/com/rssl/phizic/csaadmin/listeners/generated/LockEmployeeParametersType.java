/**
 * LockEmployeeParametersType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.csaadmin.listeners.generated;

public class LockEmployeeParametersType  implements java.io.Serializable {
    private com.rssl.phizic.csaadmin.listeners.generated.LoginType lockLoginParameter;

    private com.rssl.phizic.csaadmin.listeners.generated.LoginBlockType lockBlockParameter;

    public LockEmployeeParametersType() {
    }

    public LockEmployeeParametersType(
           com.rssl.phizic.csaadmin.listeners.generated.LoginType lockLoginParameter,
           com.rssl.phizic.csaadmin.listeners.generated.LoginBlockType lockBlockParameter) {
           this.lockLoginParameter = lockLoginParameter;
           this.lockBlockParameter = lockBlockParameter;
    }


    /**
     * Gets the lockLoginParameter value for this LockEmployeeParametersType.
     * 
     * @return lockLoginParameter
     */
    public com.rssl.phizic.csaadmin.listeners.generated.LoginType getLockLoginParameter() {
        return lockLoginParameter;
    }


    /**
     * Sets the lockLoginParameter value for this LockEmployeeParametersType.
     * 
     * @param lockLoginParameter
     */
    public void setLockLoginParameter(com.rssl.phizic.csaadmin.listeners.generated.LoginType lockLoginParameter) {
        this.lockLoginParameter = lockLoginParameter;
    }


    /**
     * Gets the lockBlockParameter value for this LockEmployeeParametersType.
     * 
     * @return lockBlockParameter
     */
    public com.rssl.phizic.csaadmin.listeners.generated.LoginBlockType getLockBlockParameter() {
        return lockBlockParameter;
    }


    /**
     * Sets the lockBlockParameter value for this LockEmployeeParametersType.
     * 
     * @param lockBlockParameter
     */
    public void setLockBlockParameter(com.rssl.phizic.csaadmin.listeners.generated.LoginBlockType lockBlockParameter) {
        this.lockBlockParameter = lockBlockParameter;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof LockEmployeeParametersType)) return false;
        LockEmployeeParametersType other = (LockEmployeeParametersType) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.lockLoginParameter==null && other.getLockLoginParameter()==null) || 
             (this.lockLoginParameter!=null &&
              this.lockLoginParameter.equals(other.getLockLoginParameter()))) &&
            ((this.lockBlockParameter==null && other.getLockBlockParameter()==null) || 
             (this.lockBlockParameter!=null &&
              this.lockBlockParameter.equals(other.getLockBlockParameter())));
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
        if (getLockLoginParameter() != null) {
            _hashCode += getLockLoginParameter().hashCode();
        }
        if (getLockBlockParameter() != null) {
            _hashCode += getLockBlockParameter().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(LockEmployeeParametersType.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "LockEmployeeParametersType"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("lockLoginParameter");
        elemField.setXmlName(new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "LockLoginParameter"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "LoginType"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("lockBlockParameter");
        elemField.setXmlName(new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "LockBlockParameter"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "LoginBlockType"));
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
