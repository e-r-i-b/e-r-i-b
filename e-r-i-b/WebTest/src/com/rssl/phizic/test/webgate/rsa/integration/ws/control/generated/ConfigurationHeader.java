/**
 * ConfigurationHeader.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated;


/**
 * This is used in the ASP model to load caller-specfic configurations
 */
public class ConfigurationHeader  implements java.io.Serializable {
    private java.lang.String application;

    private java.lang.String instanceId;

    private java.lang.String ruleSet;

    public ConfigurationHeader() {
    }

    public ConfigurationHeader(
           java.lang.String application,
           java.lang.String instanceId,
           java.lang.String ruleSet) {
           this.application = application;
           this.instanceId = instanceId;
           this.ruleSet = ruleSet;
    }


    /**
     * Gets the application value for this ConfigurationHeader.
     * 
     * @return application
     */
    public java.lang.String getApplication() {
        return application;
    }


    /**
     * Sets the application value for this ConfigurationHeader.
     * 
     * @param application
     */
    public void setApplication(java.lang.String application) {
        this.application = application;
    }


    /**
     * Gets the instanceId value for this ConfigurationHeader.
     * 
     * @return instanceId
     */
    public java.lang.String getInstanceId() {
        return instanceId;
    }


    /**
     * Sets the instanceId value for this ConfigurationHeader.
     * 
     * @param instanceId
     */
    public void setInstanceId(java.lang.String instanceId) {
        this.instanceId = instanceId;
    }


    /**
     * Gets the ruleSet value for this ConfigurationHeader.
     * 
     * @return ruleSet
     */
    public java.lang.String getRuleSet() {
        return ruleSet;
    }


    /**
     * Sets the ruleSet value for this ConfigurationHeader.
     * 
     * @param ruleSet
     */
    public void setRuleSet(java.lang.String ruleSet) {
        this.ruleSet = ruleSet;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ConfigurationHeader)) return false;
        ConfigurationHeader other = (ConfigurationHeader) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.application==null && other.getApplication()==null) || 
             (this.application!=null &&
              this.application.equals(other.getApplication()))) &&
            ((this.instanceId==null && other.getInstanceId()==null) || 
             (this.instanceId!=null &&
              this.instanceId.equals(other.getInstanceId()))) &&
            ((this.ruleSet==null && other.getRuleSet()==null) || 
             (this.ruleSet!=null &&
              this.ruleSet.equals(other.getRuleSet())));
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
        if (getApplication() != null) {
            _hashCode += getApplication().hashCode();
        }
        if (getInstanceId() != null) {
            _hashCode += getInstanceId().hashCode();
        }
        if (getRuleSet() != null) {
            _hashCode += getRuleSet().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ConfigurationHeader.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "ConfigurationHeader"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("application");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "application"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("instanceId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "instanceId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ruleSet");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "ruleSet"));
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
