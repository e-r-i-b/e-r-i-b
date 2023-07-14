/**
 * GetMailAverageTimeResultType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizicgate.csaadmin.service.generated;

public class GetMailAverageTimeResultType  implements java.io.Serializable {
    private com.rssl.phizicgate.csaadmin.service.generated.MailAverageTimeType mailAverageTime;

    public GetMailAverageTimeResultType() {
    }

    public GetMailAverageTimeResultType(
           com.rssl.phizicgate.csaadmin.service.generated.MailAverageTimeType mailAverageTime) {
           this.mailAverageTime = mailAverageTime;
    }


    /**
     * Gets the mailAverageTime value for this GetMailAverageTimeResultType.
     * 
     * @return mailAverageTime
     */
    public com.rssl.phizicgate.csaadmin.service.generated.MailAverageTimeType getMailAverageTime() {
        return mailAverageTime;
    }


    /**
     * Sets the mailAverageTime value for this GetMailAverageTimeResultType.
     * 
     * @param mailAverageTime
     */
    public void setMailAverageTime(com.rssl.phizicgate.csaadmin.service.generated.MailAverageTimeType mailAverageTime) {
        this.mailAverageTime = mailAverageTime;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof GetMailAverageTimeResultType)) return false;
        GetMailAverageTimeResultType other = (GetMailAverageTimeResultType) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.mailAverageTime==null && other.getMailAverageTime()==null) || 
             (this.mailAverageTime!=null &&
              this.mailAverageTime.equals(other.getMailAverageTime())));
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
        if (getMailAverageTime() != null) {
            _hashCode += getMailAverageTime().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(GetMailAverageTimeResultType.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "GetMailAverageTimeResultType"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("mailAverageTime");
        elemField.setXmlName(new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "mailAverageTime"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "MailAverageTimeType"));
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
