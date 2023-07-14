/**
 * ClientGenCookie.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated;

public class ClientGenCookie  extends com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.DeviceIdentifier  implements java.io.Serializable {
    private java.lang.String clientGenCookie;

    public ClientGenCookie() {
    }

    public ClientGenCookie(
           java.lang.String clientGenCookie) {
        this.clientGenCookie = clientGenCookie;
    }


    /**
     * Gets the clientGenCookie value for this ClientGenCookie.
     * 
     * @return clientGenCookie
     */
    public java.lang.String getClientGenCookie() {
        return clientGenCookie;
    }


    /**
     * Sets the clientGenCookie value for this ClientGenCookie.
     * 
     * @param clientGenCookie
     */
    public void setClientGenCookie(java.lang.String clientGenCookie) {
        this.clientGenCookie = clientGenCookie;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ClientGenCookie)) return false;
        ClientGenCookie other = (ClientGenCookie) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.clientGenCookie==null && other.getClientGenCookie()==null) || 
             (this.clientGenCookie!=null &&
              this.clientGenCookie.equals(other.getClientGenCookie())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = super.hashCode();
        if (getClientGenCookie() != null) {
            _hashCode += getClientGenCookie().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ClientGenCookie.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "ClientGenCookie"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("clientGenCookie");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "clientGenCookie"));
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
