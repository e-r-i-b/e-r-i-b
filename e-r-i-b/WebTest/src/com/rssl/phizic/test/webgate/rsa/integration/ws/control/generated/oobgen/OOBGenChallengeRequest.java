/**
 * OOBGenChallengeRequest.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.oobgen;

public abstract class OOBGenChallengeRequest  extends com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.AcspChallengeRequest  implements java.io.Serializable {
    private com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.oobgen.OOBPhoneInfo contactList;

    public OOBGenChallengeRequest() {
    }

    public OOBGenChallengeRequest(
           com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.oobgen.OOBPhoneInfo contactList) {
        this.contactList = contactList;
    }


    /**
     * Gets the contactList value for this OOBGenChallengeRequest.
     * 
     * @return contactList
     */
    public com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.oobgen.OOBPhoneInfo getContactList() {
        return contactList;
    }


    /**
     * Sets the contactList value for this OOBGenChallengeRequest.
     * 
     * @param contactList
     */
    public void setContactList(com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.oobgen.OOBPhoneInfo contactList) {
        this.contactList = contactList;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof OOBGenChallengeRequest)) return false;
        OOBGenChallengeRequest other = (OOBGenChallengeRequest) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.contactList==null && other.getContactList()==null) || 
             (this.contactList!=null &&
              this.contactList.equals(other.getContactList())));
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
        if (getContactList() != null) {
            _hashCode += getContactList().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(OOBGenChallengeRequest.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.oobgen.csd.rsa.com", "OOBGenChallengeRequest"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("contactList");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.oobgen.csd.rsa.com", "contactList"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.oobgen.csd.rsa.com", "OOBPhoneInfo"));
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
