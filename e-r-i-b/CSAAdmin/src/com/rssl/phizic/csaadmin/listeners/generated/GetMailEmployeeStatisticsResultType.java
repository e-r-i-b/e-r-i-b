/**
 * GetMailEmployeeStatisticsResultType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.csaadmin.listeners.generated;

public class GetMailEmployeeStatisticsResultType  implements java.io.Serializable {
    private com.rssl.phizic.csaadmin.listeners.generated.MailEmployeeStatisticsDataType[] statisticsData;

    public GetMailEmployeeStatisticsResultType() {
    }

    public GetMailEmployeeStatisticsResultType(
           com.rssl.phizic.csaadmin.listeners.generated.MailEmployeeStatisticsDataType[] statisticsData) {
           this.statisticsData = statisticsData;
    }


    /**
     * Gets the statisticsData value for this GetMailEmployeeStatisticsResultType.
     * 
     * @return statisticsData
     */
    public com.rssl.phizic.csaadmin.listeners.generated.MailEmployeeStatisticsDataType[] getStatisticsData() {
        return statisticsData;
    }


    /**
     * Sets the statisticsData value for this GetMailEmployeeStatisticsResultType.
     * 
     * @param statisticsData
     */
    public void setStatisticsData(com.rssl.phizic.csaadmin.listeners.generated.MailEmployeeStatisticsDataType[] statisticsData) {
        this.statisticsData = statisticsData;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof GetMailEmployeeStatisticsResultType)) return false;
        GetMailEmployeeStatisticsResultType other = (GetMailEmployeeStatisticsResultType) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.statisticsData==null && other.getStatisticsData()==null) || 
             (this.statisticsData!=null &&
              java.util.Arrays.equals(this.statisticsData, other.getStatisticsData())));
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
        if (getStatisticsData() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getStatisticsData());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getStatisticsData(), i);
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
        new org.apache.axis.description.TypeDesc(GetMailEmployeeStatisticsResultType.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "GetMailEmployeeStatisticsResultType"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("statisticsData");
        elemField.setXmlName(new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "statisticsData"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "MailEmployeeStatisticsDataType"));
        elemField.setNillable(true);
        elemField.setItemQName(new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "item"));
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