/**
 * ReadRemoteClientNameResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizicgate.bars.ws.axis;

public class ReadRemoteClientNameResponse  implements java.io.Serializable {
    private com.rssl.phizicgate.bars.ws.axis.XsbRemoteClientNameReturn readRemoteClientNameReturn;

    public ReadRemoteClientNameResponse() {
    }

    public ReadRemoteClientNameResponse(
           com.rssl.phizicgate.bars.ws.axis.XsbRemoteClientNameReturn readRemoteClientNameReturn) {
           this.readRemoteClientNameReturn = readRemoteClientNameReturn;
    }


    /**
     * Gets the readRemoteClientNameReturn value for this ReadRemoteClientNameResponse.
     * 
     * @return readRemoteClientNameReturn
     */
    public com.rssl.phizicgate.bars.ws.axis.XsbRemoteClientNameReturn getReadRemoteClientNameReturn() {
        return readRemoteClientNameReturn;
    }


    /**
     * Sets the readRemoteClientNameReturn value for this ReadRemoteClientNameResponse.
     * 
     * @param readRemoteClientNameReturn
     */
    public void setReadRemoteClientNameReturn(com.rssl.phizicgate.bars.ws.axis.XsbRemoteClientNameReturn readRemoteClientNameReturn) {
        this.readRemoteClientNameReturn = readRemoteClientNameReturn;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ReadRemoteClientNameResponse)) return false;
        ReadRemoteClientNameResponse other = (ReadRemoteClientNameResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.readRemoteClientNameReturn==null && other.getReadRemoteClientNameReturn()==null) || 
             (this.readRemoteClientNameReturn!=null &&
              this.readRemoteClientNameReturn.equals(other.getReadRemoteClientNameReturn())));
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
        if (getReadRemoteClientNameReturn() != null) {
            _hashCode += getReadRemoteClientNameReturn().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ReadRemoteClientNameResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://checkNames.xsb.webservices.bars.sbrf", ">readRemoteClientNameResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("readRemoteClientNameReturn");
        elemField.setXmlName(new javax.xml.namespace.QName("", "readRemoteClientNameReturn"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://checkNames.xsb.webservices.bars.sbrf", "XsbRemoteClientNameReturn"));
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
