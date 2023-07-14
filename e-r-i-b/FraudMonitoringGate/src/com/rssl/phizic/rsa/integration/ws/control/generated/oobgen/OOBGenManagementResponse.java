/**
 * OOBGenManagementResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.rsa.integration.ws.control.generated.oobgen;

public abstract class OOBGenManagementResponse  extends com.rssl.phizic.rsa.integration.ws.control.generated.AcspManagementResponse  implements java.io.Serializable {
    private com.rssl.phizic.rsa.integration.ws.control.generated.kba.Action action;

    private com.rssl.phizic.rsa.integration.ws.control.generated.oobgen.OOBPhoneInfo[] contactList;

    public OOBGenManagementResponse() {
    }

    public OOBGenManagementResponse(
           com.rssl.phizic.rsa.integration.ws.control.generated.kba.Action action,
           com.rssl.phizic.rsa.integration.ws.control.generated.oobgen.OOBPhoneInfo[] contactList) {
        this.action = action;
        this.contactList = contactList;
    }


    /**
     * Gets the action value for this OOBGenManagementResponse.
     * 
     * @return action
     */
    public com.rssl.phizic.rsa.integration.ws.control.generated.kba.Action getAction() {
        return action;
    }


    /**
     * Sets the action value for this OOBGenManagementResponse.
     * 
     * @param action
     */
    public void setAction(com.rssl.phizic.rsa.integration.ws.control.generated.kba.Action action) {
        this.action = action;
    }


    /**
     * Gets the contactList value for this OOBGenManagementResponse.
     * 
     * @return contactList
     */
    public com.rssl.phizic.rsa.integration.ws.control.generated.oobgen.OOBPhoneInfo[] getContactList() {
        return contactList;
    }


    /**
     * Sets the contactList value for this OOBGenManagementResponse.
     * 
     * @param contactList
     */
    public void setContactList(com.rssl.phizic.rsa.integration.ws.control.generated.oobgen.OOBPhoneInfo[] contactList) {
        this.contactList = contactList;
    }

    public com.rssl.phizic.rsa.integration.ws.control.generated.oobgen.OOBPhoneInfo getContactList(int i) {
        return this.contactList[i];
    }

    public void setContactList(int i, com.rssl.phizic.rsa.integration.ws.control.generated.oobgen.OOBPhoneInfo _value) {
        this.contactList[i] = _value;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof OOBGenManagementResponse)) return false;
        OOBGenManagementResponse other = (OOBGenManagementResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.action==null && other.getAction()==null) || 
             (this.action!=null &&
              this.action.equals(other.getAction()))) &&
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
        if (getAction() != null) {
            _hashCode += getAction().hashCode();
        }
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
        new org.apache.axis.description.TypeDesc(OOBGenManagementResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.oobgen.csd.rsa.com", "OOBGenManagementResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("action");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.oobgen.csd.rsa.com", "action"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.kba.csd.rsa.com", "Action"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("contactList");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.oobgen.csd.rsa.com", "contactList"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.oobgen.csd.rsa.com", "OOBPhoneInfo"));
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
