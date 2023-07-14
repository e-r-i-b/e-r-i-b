/**
 * XsbChecksDocResults.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizicgate.bars.ws.axis;

public class XsbChecksDocResults  extends com.rssl.phizicgate.bars.ws.axis.XsbDocResults  implements java.io.Serializable {
    private com.rssl.phizicgate.bars.ws.axis.XsbResult[] checks;

    public XsbChecksDocResults() {
    }

    public XsbChecksDocResults(
           com.rssl.phizicgate.bars.ws.axis.XsbDocID docID,
           com.rssl.phizicgate.bars.ws.axis.XsbExceptionItem[] exceptionItems,
           com.rssl.phizicgate.bars.ws.axis.XsbResult[] checks) {
        super(
            docID,
            exceptionItems);
        this.checks = checks;
    }


    /**
     * Gets the checks value for this XsbChecksDocResults.
     * 
     * @return checks
     */
    public com.rssl.phizicgate.bars.ws.axis.XsbResult[] getChecks() {
        return checks;
    }


    /**
     * Sets the checks value for this XsbChecksDocResults.
     * 
     * @param checks
     */
    public void setChecks(com.rssl.phizicgate.bars.ws.axis.XsbResult[] checks) {
        this.checks = checks;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof XsbChecksDocResults)) return false;
        XsbChecksDocResults other = (XsbChecksDocResults) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.checks==null && other.getChecks()==null) || 
             (this.checks!=null &&
              java.util.Arrays.equals(this.checks, other.getChecks())));
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
        if (getChecks() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getChecks());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getChecks(), i);
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
        new org.apache.axis.description.TypeDesc(XsbChecksDocResults.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://checks.xsb.webservices.bars.sbrf", "XsbChecksDocResults"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("checks");
        elemField.setXmlName(new javax.xml.namespace.QName("", "checks"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://common.xsb.webservices.bars.sbrf", "XsbResult"));
        elemField.setNillable(true);
        elemField.setItemQName(new javax.xml.namespace.QName("", "XsbResult"));
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
