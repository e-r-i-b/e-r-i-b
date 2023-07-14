/**
 * GetOutcomeMailListResultType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.csaadmin.listeners.generated;

public class GetOutcomeMailListResultType  implements java.io.Serializable {
    private com.rssl.phizic.csaadmin.listeners.generated.OutcomeMailListDataType[] mailList;

    public GetOutcomeMailListResultType() {
    }

    public GetOutcomeMailListResultType(
           com.rssl.phizic.csaadmin.listeners.generated.OutcomeMailListDataType[] mailList) {
           this.mailList = mailList;
    }


    /**
     * Gets the mailList value for this GetOutcomeMailListResultType.
     * 
     * @return mailList
     */
    public com.rssl.phizic.csaadmin.listeners.generated.OutcomeMailListDataType[] getMailList() {
        return mailList;
    }


    /**
     * Sets the mailList value for this GetOutcomeMailListResultType.
     * 
     * @param mailList
     */
    public void setMailList(com.rssl.phizic.csaadmin.listeners.generated.OutcomeMailListDataType[] mailList) {
        this.mailList = mailList;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof GetOutcomeMailListResultType)) return false;
        GetOutcomeMailListResultType other = (GetOutcomeMailListResultType) obj;
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
        new org.apache.axis.description.TypeDesc(GetOutcomeMailListResultType.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "GetOutcomeMailListResultType"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("mailList");
        elemField.setXmlName(new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "mailList"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "OutcomeMailListDataType"));
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
