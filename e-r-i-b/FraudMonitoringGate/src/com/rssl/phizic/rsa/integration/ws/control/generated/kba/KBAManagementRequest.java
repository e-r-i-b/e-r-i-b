/**
 * KBAManagementRequest.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.rsa.integration.ws.control.generated.kba;

public class KBAManagementRequest  extends com.rssl.phizic.rsa.integration.ws.control.generated.AcspManagementRequest  implements java.io.Serializable {
    private com.rssl.phizic.rsa.integration.ws.control.generated.kba.Action action;

    private com.rssl.phizic.rsa.integration.ws.control.generated.kba.PersonInfo personInfo;

    public KBAManagementRequest() {
    }

    public KBAManagementRequest(
           java.lang.String opcode,
           com.rssl.phizic.rsa.integration.ws.control.generated.kba.Action action,
           com.rssl.phizic.rsa.integration.ws.control.generated.kba.PersonInfo personInfo) {
        super(
            opcode);
        this.action = action;
        this.personInfo = personInfo;
    }


    /**
     * Gets the action value for this KBAManagementRequest.
     * 
     * @return action
     */
    public com.rssl.phizic.rsa.integration.ws.control.generated.kba.Action getAction() {
        return action;
    }


    /**
     * Sets the action value for this KBAManagementRequest.
     * 
     * @param action
     */
    public void setAction(com.rssl.phizic.rsa.integration.ws.control.generated.kba.Action action) {
        this.action = action;
    }


    /**
     * Gets the personInfo value for this KBAManagementRequest.
     * 
     * @return personInfo
     */
    public com.rssl.phizic.rsa.integration.ws.control.generated.kba.PersonInfo getPersonInfo() {
        return personInfo;
    }


    /**
     * Sets the personInfo value for this KBAManagementRequest.
     * 
     * @param personInfo
     */
    public void setPersonInfo(com.rssl.phizic.rsa.integration.ws.control.generated.kba.PersonInfo personInfo) {
        this.personInfo = personInfo;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof KBAManagementRequest)) return false;
        KBAManagementRequest other = (KBAManagementRequest) obj;
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
            ((this.personInfo==null && other.getPersonInfo()==null) || 
             (this.personInfo!=null &&
              this.personInfo.equals(other.getPersonInfo())));
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
        if (getPersonInfo() != null) {
            _hashCode += getPersonInfo().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(KBAManagementRequest.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.kba.csd.rsa.com", "KBAManagementRequest"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("action");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.kba.csd.rsa.com", "action"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.kba.csd.rsa.com", "Action"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("personInfo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.kba.csd.rsa.com", "personInfo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.kba.csd.rsa.com", "PersonInfo"));
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
