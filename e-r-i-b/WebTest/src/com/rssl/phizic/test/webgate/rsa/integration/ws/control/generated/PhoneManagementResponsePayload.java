/**
 * PhoneManagementResponsePayload.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated;

public class PhoneManagementResponsePayload  extends com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.OOBManagementResponsePayload  implements java.io.Serializable {
    private com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.PhoneInfo[] contactList;

    public PhoneManagementResponsePayload() {
    }

    public PhoneManagementResponsePayload(
           java.lang.String acspAccountId,
           com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.CallStatus callStatus,
           com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.PhoneInfo[] contactList) {
        super(
            acspAccountId,
            callStatus);
        this.contactList = contactList;
    }


    /**
     * Gets the contactList value for this PhoneManagementResponsePayload.
     * 
     * @return contactList
     */
    public com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.PhoneInfo[] getContactList() {
        return contactList;
    }


    /**
     * Sets the contactList value for this PhoneManagementResponsePayload.
     * 
     * @param contactList
     */
    public void setContactList(com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.PhoneInfo[] contactList) {
        this.contactList = contactList;
    }

    public com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.PhoneInfo getContactList(int i) {
        return this.contactList[i];
    }

    public void setContactList(int i, com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.PhoneInfo _value) {
        this.contactList[i] = _value;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof PhoneManagementResponsePayload)) return false;
        PhoneManagementResponsePayload other = (PhoneManagementResponsePayload) obj;
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
              java.util.Arrays.equals(this.contactList, other.getContactList())));
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
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getContactList());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getContactList(), i);
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
        new org.apache.axis.description.TypeDesc(PhoneManagementResponsePayload.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "PhoneManagementResponsePayload"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("contactList");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "contactList"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "PhoneInfo"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        elemField.setMaxOccursUnbounded(true);
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
