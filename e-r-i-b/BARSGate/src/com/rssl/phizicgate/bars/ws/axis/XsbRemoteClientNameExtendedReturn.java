/**
 * XsbRemoteClientNameExtendedReturn.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizicgate.bars.ws.axis;

public class XsbRemoteClientNameExtendedReturn  extends com.rssl.phizicgate.bars.ws.axis.XsbReturn  implements java.io.Serializable {
    private com.rssl.phizicgate.bars.ws.axis.XsbRemoteClientNameExtendedResult[] documents;

    public XsbRemoteClientNameExtendedReturn() {
    }

    public XsbRemoteClientNameExtendedReturn(
           com.rssl.phizicgate.bars.ws.axis.XsbExceptionItem[] exceptionItems,
           com.rssl.phizicgate.bars.ws.axis.XsbRemoteClientNameExtendedResult[] documents) {
        super(
            exceptionItems);
        this.documents = documents;
    }


    /**
     * Gets the documents value for this XsbRemoteClientNameExtendedReturn.
     * 
     * @return documents
     */
    public com.rssl.phizicgate.bars.ws.axis.XsbRemoteClientNameExtendedResult[] getDocuments() {
        return documents;
    }


    /**
     * Sets the documents value for this XsbRemoteClientNameExtendedReturn.
     * 
     * @param documents
     */
    public void setDocuments(com.rssl.phizicgate.bars.ws.axis.XsbRemoteClientNameExtendedResult[] documents) {
        this.documents = documents;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof XsbRemoteClientNameExtendedReturn)) return false;
        XsbRemoteClientNameExtendedReturn other = (XsbRemoteClientNameExtendedReturn) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.documents==null && other.getDocuments()==null) || 
             (this.documents!=null &&
              java.util.Arrays.equals(this.documents, other.getDocuments())));
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
        if (getDocuments() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getDocuments());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getDocuments(), i);
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
        new org.apache.axis.description.TypeDesc(XsbRemoteClientNameExtendedReturn.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://checkNames.xsb.webservices.bars.sbrf", "XsbRemoteClientNameExtendedReturn"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("documents");
        elemField.setXmlName(new javax.xml.namespace.QName("", "documents"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://checkNames.xsb.webservices.bars.sbrf", "XsbRemoteClientNameExtendedResult"));
        elemField.setNillable(true);
        elemField.setItemQName(new javax.xml.namespace.QName("", "XsbRemoteClientNameExtendedResult"));
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
