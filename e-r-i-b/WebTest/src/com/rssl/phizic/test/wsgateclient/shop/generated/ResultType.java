/**
 * ResultType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.test.wsgateclient.shop.generated;

public class ResultType  implements java.io.Serializable {
    private java.lang.String ERIBUID;

    private com.rssl.phizic.test.wsgateclient.shop.generated.StatusType status;

    public ResultType() {
    }

    public ResultType(
           java.lang.String ERIBUID,
           com.rssl.phizic.test.wsgateclient.shop.generated.StatusType status) {
           this.ERIBUID = ERIBUID;
           this.status = status;
    }


    /**
     * Gets the ERIBUID value for this ResultType.
     * 
     * @return ERIBUID
     */
    public java.lang.String getERIBUID() {
        return ERIBUID;
    }


    /**
     * Sets the ERIBUID value for this ResultType.
     * 
     * @param ERIBUID
     */
    public void setERIBUID(java.lang.String ERIBUID) {
        this.ERIBUID = ERIBUID;
    }


    /**
     * Gets the status value for this ResultType.
     * 
     * @return status
     */
    public com.rssl.phizic.test.wsgateclient.shop.generated.StatusType getStatus() {
        return status;
    }


    /**
     * Sets the status value for this ResultType.
     * 
     * @param status
     */
    public void setStatus(com.rssl.phizic.test.wsgateclient.shop.generated.StatusType status) {
        this.status = status;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ResultType)) return false;
        ResultType other = (ResultType) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.ERIBUID==null && other.getERIBUID()==null) || 
             (this.ERIBUID!=null &&
              this.ERIBUID.equals(other.getERIBUID()))) &&
            ((this.status==null && other.getStatus()==null) || 
             (this.status!=null &&
              this.status.equals(other.getStatus())));
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
        if (getERIBUID() != null) {
            _hashCode += getERIBUID().hashCode();
        }
        if (getStatus() != null) {
            _hashCode += getStatus().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ResultType.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://generated.shoplistener.phizic.rssl.com", "ResultType"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ERIBUID");
        elemField.setXmlName(new javax.xml.namespace.QName("http://generated.shoplistener.phizic.rssl.com", "ERIBUID"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://generated.shoplistener.phizic.rssl.com", "UUIDType"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("status");
        elemField.setXmlName(new javax.xml.namespace.QName("http://generated.shoplistener.phizic.rssl.com", "Status"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://generated.shoplistener.phizic.rssl.com", "StatusType"));
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
