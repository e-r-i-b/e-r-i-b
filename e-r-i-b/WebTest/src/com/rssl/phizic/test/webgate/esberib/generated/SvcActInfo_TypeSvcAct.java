/**
 * SvcActInfo_TypeSvcAct.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.test.webgate.esberib.generated;

public class SvcActInfo_TypeSvcAct  implements java.io.Serializable {
    private com.rssl.phizic.test.webgate.esberib.generated.SvcAcctId_Type svcAcctId;

    public SvcActInfo_TypeSvcAct() {
    }

    public SvcActInfo_TypeSvcAct(
           com.rssl.phizic.test.webgate.esberib.generated.SvcAcctId_Type svcAcctId) {
           this.svcAcctId = svcAcctId;
    }


    /**
     * Gets the svcAcctId value for this SvcActInfo_TypeSvcAct.
     * 
     * @return svcAcctId
     */
    public com.rssl.phizic.test.webgate.esberib.generated.SvcAcctId_Type getSvcAcctId() {
        return svcAcctId;
    }


    /**
     * Sets the svcAcctId value for this SvcActInfo_TypeSvcAct.
     * 
     * @param svcAcctId
     */
    public void setSvcAcctId(com.rssl.phizic.test.webgate.esberib.generated.SvcAcctId_Type svcAcctId) {
        this.svcAcctId = svcAcctId;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof SvcActInfo_TypeSvcAct)) return false;
        SvcActInfo_TypeSvcAct other = (SvcActInfo_TypeSvcAct) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.svcAcctId==null && other.getSvcAcctId()==null) || 
             (this.svcAcctId!=null &&
              this.svcAcctId.equals(other.getSvcAcctId())));
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
        if (getSvcAcctId() != null) {
            _hashCode += getSvcAcctId().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(SvcActInfo_TypeSvcAct.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", ">SvcActInfo_Type>SvcAct"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("svcAcctId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "SvcAcctId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "SvcAcctId_Type"));
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
