/**
 * ConsumeOutMessageRequest.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizicgate.mfm.ws.outmessageconsumer.generated;

public class ConsumeOutMessageRequest  implements java.io.Serializable {
    private com.rssl.phizicgate.mfm.ws.outmessageconsumer.generated.ConsumeOutMessageArg consumeOutMessageArg;

    public ConsumeOutMessageRequest() {
    }

    public ConsumeOutMessageRequest(
           com.rssl.phizicgate.mfm.ws.outmessageconsumer.generated.ConsumeOutMessageArg consumeOutMessageArg) {
           this.consumeOutMessageArg = consumeOutMessageArg;
    }


    /**
     * Gets the consumeOutMessageArg value for this ConsumeOutMessageRequest.
     * 
     * @return consumeOutMessageArg
     */
    public com.rssl.phizicgate.mfm.ws.outmessageconsumer.generated.ConsumeOutMessageArg getConsumeOutMessageArg() {
        return consumeOutMessageArg;
    }


    /**
     * Sets the consumeOutMessageArg value for this ConsumeOutMessageRequest.
     * 
     * @param consumeOutMessageArg
     */
    public void setConsumeOutMessageArg(com.rssl.phizicgate.mfm.ws.outmessageconsumer.generated.ConsumeOutMessageArg consumeOutMessageArg) {
        this.consumeOutMessageArg = consumeOutMessageArg;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ConsumeOutMessageRequest)) return false;
        ConsumeOutMessageRequest other = (ConsumeOutMessageRequest) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.consumeOutMessageArg==null && other.getConsumeOutMessageArg()==null) || 
             (this.consumeOutMessageArg!=null &&
              this.consumeOutMessageArg.equals(other.getConsumeOutMessageArg())));
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
        if (getConsumeOutMessageArg() != null) {
            _hashCode += getConsumeOutMessageArg().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ConsumeOutMessageRequest.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.mfms.ru/sb/ifm/sms/sb0/out-message-consumer", ">ConsumeOutMessageRequest"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("consumeOutMessageArg");
        elemField.setXmlName(new javax.xml.namespace.QName("", "consumeOutMessageArg"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.mfms.ru/sb/ifm/sms/sb0/out-message-consumer", "ConsumeOutMessageArg"));
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
