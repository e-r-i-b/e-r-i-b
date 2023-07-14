/**
 * ReadRemoteClientName.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizicgate.bars.ws.axis;

public class ReadRemoteClientName  implements java.io.Serializable {
    private java.lang.String xsbDocument;

    private com.rssl.phizicgate.bars.ws.axis.XsbParameter[] parameters;

    public ReadRemoteClientName() {
    }

    public ReadRemoteClientName(
           java.lang.String xsbDocument,
           com.rssl.phizicgate.bars.ws.axis.XsbParameter[] parameters) {
           this.xsbDocument = xsbDocument;
           this.parameters = parameters;
    }


    /**
     * Gets the xsbDocument value for this ReadRemoteClientName.
     * 
     * @return xsbDocument
     */
    public java.lang.String getXsbDocument() {
        return xsbDocument;
    }


    /**
     * Sets the xsbDocument value for this ReadRemoteClientName.
     * 
     * @param xsbDocument
     */
    public void setXsbDocument(java.lang.String xsbDocument) {
        this.xsbDocument = xsbDocument;
    }


    /**
     * Gets the parameters value for this ReadRemoteClientName.
     * 
     * @return parameters
     */
    public com.rssl.phizicgate.bars.ws.axis.XsbParameter[] getParameters() {
        return parameters;
    }


    /**
     * Sets the parameters value for this ReadRemoteClientName.
     * 
     * @param parameters
     */
    public void setParameters(com.rssl.phizicgate.bars.ws.axis.XsbParameter[] parameters) {
        this.parameters = parameters;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ReadRemoteClientName)) return false;
        ReadRemoteClientName other = (ReadRemoteClientName) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.xsbDocument==null && other.getXsbDocument()==null) || 
             (this.xsbDocument!=null &&
              this.xsbDocument.equals(other.getXsbDocument()))) &&
            ((this.parameters==null && other.getParameters()==null) || 
             (this.parameters!=null &&
              java.util.Arrays.equals(this.parameters, other.getParameters())));
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
        if (getXsbDocument() != null) {
            _hashCode += getXsbDocument().hashCode();
        }
        if (getParameters() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getParameters());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getParameters(), i);
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
        new org.apache.axis.description.TypeDesc(ReadRemoteClientName.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://checkNames.xsb.webservices.bars.sbrf", ">readRemoteClientName"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("xsbDocument");
        elemField.setXmlName(new javax.xml.namespace.QName("", "xsbDocument"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("parameters");
        elemField.setXmlName(new javax.xml.namespace.QName("", "parameters"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://common.xsb.webservices.bars.sbrf", "XsbParameter"));
        elemField.setNillable(true);
        elemField.setItemQName(new javax.xml.namespace.QName("", "XsbParameter"));
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
