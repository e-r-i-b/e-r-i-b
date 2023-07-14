/**
 * Response_Type.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.test.webgate.rsa.integration.ws.back.generated;


/**
 * This defines the contents of a Response
 */
public class Response_Type  implements java.io.Serializable {
    private com.rssl.phizic.test.webgate.rsa.integration.ws.back.generated.StatusHeader_Type statusHeader;

    public Response_Type() {
    }

    public Response_Type(
           com.rssl.phizic.test.webgate.rsa.integration.ws.back.generated.StatusHeader_Type statusHeader) {
           this.statusHeader = statusHeader;
    }


    /**
     * Gets the statusHeader value for this Response_Type.
     * 
     * @return statusHeader
     */
    public com.rssl.phizic.test.webgate.rsa.integration.ws.back.generated.StatusHeader_Type getStatusHeader() {
        return statusHeader;
    }


    /**
     * Sets the statusHeader value for this Response_Type.
     * 
     * @param statusHeader
     */
    public void setStatusHeader(com.rssl.phizic.test.webgate.rsa.integration.ws.back.generated.StatusHeader_Type statusHeader) {
        this.statusHeader = statusHeader;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Response_Type)) return false;
        Response_Type other = (Response_Type) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.statusHeader==null && other.getStatusHeader()==null) || 
             (this.statusHeader!=null &&
              this.statusHeader.equals(other.getStatusHeader())));
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
        if (getStatusHeader() != null) {
            _hashCode += getStatusHeader().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Response_Type.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://async-interaction/fraud/monitoring/back/gate", "Response_Type"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("statusHeader");
        elemField.setXmlName(new javax.xml.namespace.QName("http://async-interaction/fraud/monitoring/back/gate", "statusHeader"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://async-interaction/fraud/monitoring/back/gate", "StatusHeader_Type"));
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
