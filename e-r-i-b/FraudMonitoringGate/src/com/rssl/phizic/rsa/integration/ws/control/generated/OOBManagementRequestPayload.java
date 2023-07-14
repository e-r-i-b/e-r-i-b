/**
 * OOBManagementRequestPayload.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.rsa.integration.ws.control.generated;

public abstract class OOBManagementRequestPayload  implements java.io.Serializable {
    private com.rssl.phizic.rsa.integration.ws.control.generated.OOBActionTypeList oobActionTypeList;

    public OOBManagementRequestPayload() {
    }

    public OOBManagementRequestPayload(
           com.rssl.phizic.rsa.integration.ws.control.generated.OOBActionTypeList oobActionTypeList) {
           this.oobActionTypeList = oobActionTypeList;
    }


    /**
     * Gets the oobActionTypeList value for this OOBManagementRequestPayload.
     * 
     * @return oobActionTypeList
     */
    public com.rssl.phizic.rsa.integration.ws.control.generated.OOBActionTypeList getOobActionTypeList() {
        return oobActionTypeList;
    }


    /**
     * Sets the oobActionTypeList value for this OOBManagementRequestPayload.
     * 
     * @param oobActionTypeList
     */
    public void setOobActionTypeList(com.rssl.phizic.rsa.integration.ws.control.generated.OOBActionTypeList oobActionTypeList) {
        this.oobActionTypeList = oobActionTypeList;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof OOBManagementRequestPayload)) return false;
        OOBManagementRequestPayload other = (OOBManagementRequestPayload) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.oobActionTypeList==null && other.getOobActionTypeList()==null) || 
             (this.oobActionTypeList!=null &&
              this.oobActionTypeList.equals(other.getOobActionTypeList())));
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
        if (getOobActionTypeList() != null) {
            _hashCode += getOobActionTypeList().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(OOBManagementRequestPayload.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "OOBManagementRequestPayload"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("oobActionTypeList");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "oobActionTypeList"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "OOBActionTypeList"));
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
