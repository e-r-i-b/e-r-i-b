/**
 * LoanRec_Type.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.test.webgate.esberib.generated;

public class LoanRec_Type  implements java.io.Serializable {
    private com.rssl.phizic.test.webgate.esberib.generated.LoanInfo_Type loanInfo;

    private com.rssl.phizic.test.webgate.esberib.generated.BankInfo_Type bankInfo;

    private com.rssl.phizic.test.webgate.esberib.generated.BankAcctInfo_Type bankAccInfo;

    private com.rssl.phizic.test.webgate.esberib.generated.Status_Type status;

    public LoanRec_Type() {
    }

    public LoanRec_Type(
           com.rssl.phizic.test.webgate.esberib.generated.LoanInfo_Type loanInfo,
           com.rssl.phizic.test.webgate.esberib.generated.BankInfo_Type bankInfo,
           com.rssl.phizic.test.webgate.esberib.generated.BankAcctInfo_Type bankAccInfo,
           com.rssl.phizic.test.webgate.esberib.generated.Status_Type status) {
           this.loanInfo = loanInfo;
           this.bankInfo = bankInfo;
           this.bankAccInfo = bankAccInfo;
           this.status = status;
    }


    /**
     * Gets the loanInfo value for this LoanRec_Type.
     * 
     * @return loanInfo
     */
    public com.rssl.phizic.test.webgate.esberib.generated.LoanInfo_Type getLoanInfo() {
        return loanInfo;
    }


    /**
     * Sets the loanInfo value for this LoanRec_Type.
     * 
     * @param loanInfo
     */
    public void setLoanInfo(com.rssl.phizic.test.webgate.esberib.generated.LoanInfo_Type loanInfo) {
        this.loanInfo = loanInfo;
    }


    /**
     * Gets the bankInfo value for this LoanRec_Type.
     * 
     * @return bankInfo
     */
    public com.rssl.phizic.test.webgate.esberib.generated.BankInfo_Type getBankInfo() {
        return bankInfo;
    }


    /**
     * Sets the bankInfo value for this LoanRec_Type.
     * 
     * @param bankInfo
     */
    public void setBankInfo(com.rssl.phizic.test.webgate.esberib.generated.BankInfo_Type bankInfo) {
        this.bankInfo = bankInfo;
    }


    /**
     * Gets the bankAccInfo value for this LoanRec_Type.
     * 
     * @return bankAccInfo
     */
    public com.rssl.phizic.test.webgate.esberib.generated.BankAcctInfo_Type getBankAccInfo() {
        return bankAccInfo;
    }


    /**
     * Sets the bankAccInfo value for this LoanRec_Type.
     * 
     * @param bankAccInfo
     */
    public void setBankAccInfo(com.rssl.phizic.test.webgate.esberib.generated.BankAcctInfo_Type bankAccInfo) {
        this.bankAccInfo = bankAccInfo;
    }


    /**
     * Gets the status value for this LoanRec_Type.
     * 
     * @return status
     */
    public com.rssl.phizic.test.webgate.esberib.generated.Status_Type getStatus() {
        return status;
    }


    /**
     * Sets the status value for this LoanRec_Type.
     * 
     * @param status
     */
    public void setStatus(com.rssl.phizic.test.webgate.esberib.generated.Status_Type status) {
        this.status = status;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof LoanRec_Type)) return false;
        LoanRec_Type other = (LoanRec_Type) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.loanInfo==null && other.getLoanInfo()==null) || 
             (this.loanInfo!=null &&
              this.loanInfo.equals(other.getLoanInfo()))) &&
            ((this.bankInfo==null && other.getBankInfo()==null) || 
             (this.bankInfo!=null &&
              this.bankInfo.equals(other.getBankInfo()))) &&
            ((this.bankAccInfo==null && other.getBankAccInfo()==null) || 
             (this.bankAccInfo!=null &&
              this.bankAccInfo.equals(other.getBankAccInfo()))) &&
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
        if (getLoanInfo() != null) {
            _hashCode += getLoanInfo().hashCode();
        }
        if (getBankInfo() != null) {
            _hashCode += getBankInfo().hashCode();
        }
        if (getBankAccInfo() != null) {
            _hashCode += getBankAccInfo().hashCode();
        }
        if (getStatus() != null) {
            _hashCode += getStatus().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(LoanRec_Type.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "LoanRec_Type"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("loanInfo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "LoanInfo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "LoanInfo_Type"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("bankInfo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "BankInfo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "BankInfo_Type"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("bankAccInfo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "BankAccInfo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "BankAcctInfo_Type"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("status");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Status"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Status_Type"));
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
