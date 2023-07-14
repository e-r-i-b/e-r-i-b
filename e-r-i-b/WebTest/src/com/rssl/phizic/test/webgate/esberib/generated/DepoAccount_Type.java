/**
 * DepoAccount_Type.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.test.webgate.esberib.generated;


/**
 * Информация по счету
 */
public class DepoAccount_Type  implements java.io.Serializable {
    private com.rssl.phizic.test.webgate.esberib.generated.BankInfo_Type bankInfo;

    private com.rssl.phizic.test.webgate.esberib.generated.DepoAcctId_Type depoAcctId;

    private com.rssl.phizic.test.webgate.esberib.generated.DepAcctInfo_Type depoAccInfo;

    private com.rssl.phizic.test.webgate.esberib.generated.Status_Type status;

    public DepoAccount_Type() {
    }

    public DepoAccount_Type(
           com.rssl.phizic.test.webgate.esberib.generated.BankInfo_Type bankInfo,
           com.rssl.phizic.test.webgate.esberib.generated.DepoAcctId_Type depoAcctId,
           com.rssl.phizic.test.webgate.esberib.generated.DepAcctInfo_Type depoAccInfo,
           com.rssl.phizic.test.webgate.esberib.generated.Status_Type status) {
           this.bankInfo = bankInfo;
           this.depoAcctId = depoAcctId;
           this.depoAccInfo = depoAccInfo;
           this.status = status;
    }


    /**
     * Gets the bankInfo value for this DepoAccount_Type.
     * 
     * @return bankInfo
     */
    public com.rssl.phizic.test.webgate.esberib.generated.BankInfo_Type getBankInfo() {
        return bankInfo;
    }


    /**
     * Sets the bankInfo value for this DepoAccount_Type.
     * 
     * @param bankInfo
     */
    public void setBankInfo(com.rssl.phizic.test.webgate.esberib.generated.BankInfo_Type bankInfo) {
        this.bankInfo = bankInfo;
    }


    /**
     * Gets the depoAcctId value for this DepoAccount_Type.
     * 
     * @return depoAcctId
     */
    public com.rssl.phizic.test.webgate.esberib.generated.DepoAcctId_Type getDepoAcctId() {
        return depoAcctId;
    }


    /**
     * Sets the depoAcctId value for this DepoAccount_Type.
     * 
     * @param depoAcctId
     */
    public void setDepoAcctId(com.rssl.phizic.test.webgate.esberib.generated.DepoAcctId_Type depoAcctId) {
        this.depoAcctId = depoAcctId;
    }


    /**
     * Gets the depoAccInfo value for this DepoAccount_Type.
     * 
     * @return depoAccInfo
     */
    public com.rssl.phizic.test.webgate.esberib.generated.DepAcctInfo_Type getDepoAccInfo() {
        return depoAccInfo;
    }


    /**
     * Sets the depoAccInfo value for this DepoAccount_Type.
     * 
     * @param depoAccInfo
     */
    public void setDepoAccInfo(com.rssl.phizic.test.webgate.esberib.generated.DepAcctInfo_Type depoAccInfo) {
        this.depoAccInfo = depoAccInfo;
    }


    /**
     * Gets the status value for this DepoAccount_Type.
     * 
     * @return status
     */
    public com.rssl.phizic.test.webgate.esberib.generated.Status_Type getStatus() {
        return status;
    }


    /**
     * Sets the status value for this DepoAccount_Type.
     * 
     * @param status
     */
    public void setStatus(com.rssl.phizic.test.webgate.esberib.generated.Status_Type status) {
        this.status = status;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof DepoAccount_Type)) return false;
        DepoAccount_Type other = (DepoAccount_Type) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.bankInfo==null && other.getBankInfo()==null) || 
             (this.bankInfo!=null &&
              this.bankInfo.equals(other.getBankInfo()))) &&
            ((this.depoAcctId==null && other.getDepoAcctId()==null) || 
             (this.depoAcctId!=null &&
              this.depoAcctId.equals(other.getDepoAcctId()))) &&
            ((this.depoAccInfo==null && other.getDepoAccInfo()==null) || 
             (this.depoAccInfo!=null &&
              this.depoAccInfo.equals(other.getDepoAccInfo()))) &&
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
        if (getBankInfo() != null) {
            _hashCode += getBankInfo().hashCode();
        }
        if (getDepoAcctId() != null) {
            _hashCode += getDepoAcctId().hashCode();
        }
        if (getDepoAccInfo() != null) {
            _hashCode += getDepoAccInfo().hashCode();
        }
        if (getStatus() != null) {
            _hashCode += getStatus().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(DepoAccount_Type.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "DepoAccount_Type"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("bankInfo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "BankInfo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "BankInfo_Type"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("depoAcctId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "DepoAcctId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "DepoAcctId_Type"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("depoAccInfo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "DepoAccInfo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "DepAcctInfo_Type"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("status");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Status"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Status_Type"));
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
