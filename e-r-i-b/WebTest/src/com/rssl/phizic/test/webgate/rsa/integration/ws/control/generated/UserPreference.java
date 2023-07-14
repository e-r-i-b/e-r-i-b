/**
 * UserPreference.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated;


/**
 * This type defines user preference regarding the RSA Milter
 */
public class UserPreference  implements java.io.Serializable {
    private com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.MilterOption milterOption;

    public UserPreference() {
    }

    public UserPreference(
           com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.MilterOption milterOption) {
           this.milterOption = milterOption;
    }


    /**
     * Gets the milterOption value for this UserPreference.
     * 
     * @return milterOption
     */
    public com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.MilterOption getMilterOption() {
        return milterOption;
    }


    /**
     * Sets the milterOption value for this UserPreference.
     * 
     * @param milterOption
     */
    public void setMilterOption(com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.MilterOption milterOption) {
        this.milterOption = milterOption;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof UserPreference)) return false;
        UserPreference other = (UserPreference) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.milterOption==null && other.getMilterOption()==null) || 
             (this.milterOption!=null &&
              this.milterOption.equals(other.getMilterOption())));
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
        if (getMilterOption() != null) {
            _hashCode += getMilterOption().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(UserPreference.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "UserPreference"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("milterOption");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "milterOption"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "MilterOption"));
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
