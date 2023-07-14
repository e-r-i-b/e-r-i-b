/**
 * SendMessageResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.test.webgate.depomobilebank.generated;

public class SendMessageResponse  implements java.io.Serializable {
    private java.lang.String sendMessageReturn;

    public SendMessageResponse() {
    }

    public SendMessageResponse(
           java.lang.String sendMessageReturn) {
           this.sendMessageReturn = sendMessageReturn;
    }


    /**
     * Gets the sendMessageReturn value for this SendMessageResponse.
     * 
     * @return sendMessageReturn
     */
    public java.lang.String getSendMessageReturn() {
        return sendMessageReturn;
    }


    /**
     * Sets the sendMessageReturn value for this SendMessageResponse.
     * 
     * @param sendMessageReturn
     */
    public void setSendMessageReturn(java.lang.String sendMessageReturn) {
        this.sendMessageReturn = sendMessageReturn;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof SendMessageResponse)) return false;
        SendMessageResponse other = (SendMessageResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.sendMessageReturn==null && other.getSendMessageReturn()==null) || 
             (this.sendMessageReturn!=null &&
              this.sendMessageReturn.equals(other.getSendMessageReturn())));
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
        if (getSendMessageReturn() != null) {
            _hashCode += getSendMessageReturn().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(SendMessageResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://service.mbv.sbrf.ru", ">sendMessageResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("sendMessageReturn");
        elemField.setXmlName(new javax.xml.namespace.QName("", "sendMessageReturn"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
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
