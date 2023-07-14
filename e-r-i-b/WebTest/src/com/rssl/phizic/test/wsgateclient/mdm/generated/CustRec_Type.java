/**
 * CustRec_Type.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.test.wsgateclient.mdm.generated;

public class CustRec_Type  implements java.io.Serializable {
    /* Идентификатор потребителя */
    private java.lang.String custId;

    private com.rssl.phizic.test.wsgateclient.mdm.generated.CustInfo_Type custInfo;

    private com.rssl.phizic.test.wsgateclient.mdm.generated.ServiceInfo_Type serviceInfo;

    public CustRec_Type() {
    }

    public CustRec_Type(
           java.lang.String custId,
           com.rssl.phizic.test.wsgateclient.mdm.generated.CustInfo_Type custInfo,
           com.rssl.phizic.test.wsgateclient.mdm.generated.ServiceInfo_Type serviceInfo) {
           this.custId = custId;
           this.custInfo = custInfo;
           this.serviceInfo = serviceInfo;
    }


    /**
     * Gets the custId value for this CustRec_Type.
     * 
     * @return custId   * Идентификатор потребителя
     */
    public java.lang.String getCustId() {
        return custId;
    }


    /**
     * Sets the custId value for this CustRec_Type.
     * 
     * @param custId   * Идентификатор потребителя
     */
    public void setCustId(java.lang.String custId) {
        this.custId = custId;
    }


    /**
     * Gets the custInfo value for this CustRec_Type.
     * 
     * @return custInfo
     */
    public com.rssl.phizic.test.wsgateclient.mdm.generated.CustInfo_Type getCustInfo() {
        return custInfo;
    }


    /**
     * Sets the custInfo value for this CustRec_Type.
     * 
     * @param custInfo
     */
    public void setCustInfo(com.rssl.phizic.test.wsgateclient.mdm.generated.CustInfo_Type custInfo) {
        this.custInfo = custInfo;
    }


    /**
     * Gets the serviceInfo value for this CustRec_Type.
     * 
     * @return serviceInfo
     */
    public com.rssl.phizic.test.wsgateclient.mdm.generated.ServiceInfo_Type getServiceInfo() {
        return serviceInfo;
    }


    /**
     * Sets the serviceInfo value for this CustRec_Type.
     * 
     * @param serviceInfo
     */
    public void setServiceInfo(com.rssl.phizic.test.wsgateclient.mdm.generated.ServiceInfo_Type serviceInfo) {
        this.serviceInfo = serviceInfo;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof CustRec_Type)) return false;
        CustRec_Type other = (CustRec_Type) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.custId==null && other.getCustId()==null) || 
             (this.custId!=null &&
              this.custId.equals(other.getCustId()))) &&
            ((this.custInfo==null && other.getCustInfo()==null) || 
             (this.custInfo!=null &&
              this.custInfo.equals(other.getCustInfo()))) &&
            ((this.serviceInfo==null && other.getServiceInfo()==null) || 
             (this.serviceInfo!=null &&
              this.serviceInfo.equals(other.getServiceInfo())));
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
        if (getCustId() != null) {
            _hashCode += getCustId().hashCode();
        }
        if (getCustInfo() != null) {
            _hashCode += getCustInfo().hashCode();
        }
        if (getServiceInfo() != null) {
            _hashCode += getServiceInfo().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(CustRec_Type.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://sbrf.srb.ru/erib/mdm", "CustRec_Type"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("custId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.srb.ru/erib/mdm", "CustId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.srb.ru/erib/mdm", "String"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("custInfo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.srb.ru/erib/mdm", "CustInfo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.srb.ru/erib/mdm", "CustInfo_Type"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("serviceInfo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.srb.ru/erib/mdm", "ServiceInfo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.srb.ru/erib/mdm", "ServiceInfo_Type"));
        elemField.setMinOccurs(0);
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
