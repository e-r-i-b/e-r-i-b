/**
 * FindOutMessageRequest.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizicgate.mfm.ws.outmessageconsumer.generated;

public class FindOutMessageRequest  implements java.io.Serializable {
    private com.rssl.phizicgate.mfm.ws.outmessageconsumer.generated.FindOutMessageArg findOutMessageArg;

    public FindOutMessageRequest() {
    }

    public FindOutMessageRequest(
           com.rssl.phizicgate.mfm.ws.outmessageconsumer.generated.FindOutMessageArg findOutMessageArg) {
           this.findOutMessageArg = findOutMessageArg;
    }


    /**
     * Gets the findOutMessageArg value for this FindOutMessageRequest.
     * 
     * @return findOutMessageArg
     */
    public com.rssl.phizicgate.mfm.ws.outmessageconsumer.generated.FindOutMessageArg getFindOutMessageArg() {
        return findOutMessageArg;
    }


    /**
     * Sets the findOutMessageArg value for this FindOutMessageRequest.
     * 
     * @param findOutMessageArg
     */
    public void setFindOutMessageArg(com.rssl.phizicgate.mfm.ws.outmessageconsumer.generated.FindOutMessageArg findOutMessageArg) {
        this.findOutMessageArg = findOutMessageArg;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof FindOutMessageRequest)) return false;
        FindOutMessageRequest other = (FindOutMessageRequest) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.findOutMessageArg==null && other.getFindOutMessageArg()==null) || 
             (this.findOutMessageArg!=null &&
              this.findOutMessageArg.equals(other.getFindOutMessageArg())));
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
        if (getFindOutMessageArg() != null) {
            _hashCode += getFindOutMessageArg().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(FindOutMessageRequest.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.mfms.ru/sb/ifm/sms/sb0/out-message-consumer", ">FindOutMessageRequest"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("findOutMessageArg");
        elemField.setXmlName(new javax.xml.namespace.QName("", "findOutMessageArg"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.mfms.ru/sb/ifm/sms/sb0/out-message-consumer", "FindOutMessageArg"));
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
