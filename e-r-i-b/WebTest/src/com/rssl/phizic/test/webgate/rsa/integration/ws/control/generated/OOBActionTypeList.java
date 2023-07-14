/**
 * OOBActionTypeList.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated;

public class OOBActionTypeList  extends com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.ActionTypeList  implements java.io.Serializable {
    private com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.OOBActionType[] oobActionType;

    public OOBActionTypeList() {
    }

    public OOBActionTypeList(
           com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.OOBActionType[] oobActionType) {
        this.oobActionType = oobActionType;
    }


    /**
     * Gets the oobActionType value for this OOBActionTypeList.
     * 
     * @return oobActionType
     */
    public com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.OOBActionType[] getOobActionType() {
        return oobActionType;
    }


    /**
     * Sets the oobActionType value for this OOBActionTypeList.
     * 
     * @param oobActionType
     */
    public void setOobActionType(com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.OOBActionType[] oobActionType) {
        this.oobActionType = oobActionType;
    }

    public com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.OOBActionType getOobActionType(int i) {
        return this.oobActionType[i];
    }

    public void setOobActionType(int i, com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.OOBActionType _value) {
        this.oobActionType[i] = _value;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof OOBActionTypeList)) return false;
        OOBActionTypeList other = (OOBActionTypeList) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.oobActionType==null && other.getOobActionType()==null) || 
             (this.oobActionType!=null &&
              java.util.Arrays.equals(this.oobActionType, other.getOobActionType())));
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
        if (getOobActionType() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getOobActionType());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getOobActionType(), i);
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
        new org.apache.axis.description.TypeDesc(OOBActionTypeList.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "OOBActionTypeList"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("oobActionType");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "oobActionType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "OOBActionType"));
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
