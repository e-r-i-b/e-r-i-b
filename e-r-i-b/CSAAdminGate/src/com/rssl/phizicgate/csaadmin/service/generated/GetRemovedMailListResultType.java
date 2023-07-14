/**
 * GetRemovedMailListResultType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizicgate.csaadmin.service.generated;

public class GetRemovedMailListResultType  implements java.io.Serializable {
    private com.rssl.phizicgate.csaadmin.service.generated.RemovedMailListDataType[] mailList;

    public GetRemovedMailListResultType() {
    }

    public GetRemovedMailListResultType(
           com.rssl.phizicgate.csaadmin.service.generated.RemovedMailListDataType[] mailList) {
           this.mailList = mailList;
    }


    /**
     * Gets the mailList value for this GetRemovedMailListResultType.
     * 
     * @return mailList
     */
    public com.rssl.phizicgate.csaadmin.service.generated.RemovedMailListDataType[] getMailList() {
        return mailList;
    }


    /**
     * Sets the mailList value for this GetRemovedMailListResultType.
     * 
     * @param mailList
     */
    public void setMailList(com.rssl.phizicgate.csaadmin.service.generated.RemovedMailListDataType[] mailList) {
        this.mailList = mailList;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof GetRemovedMailListResultType)) return false;
        GetRemovedMailListResultType other = (GetRemovedMailListResultType) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.mailList==null && other.getMailList()==null) || 
             (this.mailList!=null &&
              java.util.Arrays.equals(this.mailList, other.getMailList())));
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
        if (getMailList() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getMailList());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getMailList(), i);
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
        new org.apache.axis.description.TypeDesc(GetRemovedMailListResultType.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "GetRemovedMailListResultType"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("mailList");
        elemField.setXmlName(new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "mailList"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "RemovedMailListDataType"));
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
