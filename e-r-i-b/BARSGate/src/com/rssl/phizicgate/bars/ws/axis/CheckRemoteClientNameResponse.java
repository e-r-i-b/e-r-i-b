/**
 * CheckRemoteClientNameResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizicgate.bars.ws.axis;

public class CheckRemoteClientNameResponse  implements java.io.Serializable {
    private com.rssl.phizicgate.bars.ws.axis.XsbChecksReturn checkRemoteClientNameReturn;

    public CheckRemoteClientNameResponse() {
    }

    public CheckRemoteClientNameResponse(
           com.rssl.phizicgate.bars.ws.axis.XsbChecksReturn checkRemoteClientNameReturn) {
           this.checkRemoteClientNameReturn = checkRemoteClientNameReturn;
    }


    /**
     * Gets the checkRemoteClientNameReturn value for this CheckRemoteClientNameResponse.
     * 
     * @return checkRemoteClientNameReturn
     */
    public com.rssl.phizicgate.bars.ws.axis.XsbChecksReturn getCheckRemoteClientNameReturn() {
        return checkRemoteClientNameReturn;
    }


    /**
     * Sets the checkRemoteClientNameReturn value for this CheckRemoteClientNameResponse.
     * 
     * @param checkRemoteClientNameReturn
     */
    public void setCheckRemoteClientNameReturn(com.rssl.phizicgate.bars.ws.axis.XsbChecksReturn checkRemoteClientNameReturn) {
        this.checkRemoteClientNameReturn = checkRemoteClientNameReturn;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof CheckRemoteClientNameResponse)) return false;
        CheckRemoteClientNameResponse other = (CheckRemoteClientNameResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.checkRemoteClientNameReturn==null && other.getCheckRemoteClientNameReturn()==null) || 
             (this.checkRemoteClientNameReturn!=null &&
              this.checkRemoteClientNameReturn.equals(other.getCheckRemoteClientNameReturn())));
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
        if (getCheckRemoteClientNameReturn() != null) {
            _hashCode += getCheckRemoteClientNameReturn().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(CheckRemoteClientNameResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://checkNames.xsb.webservices.bars.sbrf", ">checkRemoteClientNameResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("checkRemoteClientNameReturn");
        elemField.setXmlName(new javax.xml.namespace.QName("", "checkRemoteClientNameReturn"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://checks.xsb.webservices.bars.sbrf", "XsbChecksReturn"));
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
