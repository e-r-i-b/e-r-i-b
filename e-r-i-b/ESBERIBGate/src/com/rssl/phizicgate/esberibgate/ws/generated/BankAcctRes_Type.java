/**
 * BankAcctRes_Type.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizicgate.esberibgate.ws.generated;

public class BankAcctRes_Type  implements java.io.Serializable {
    private com.rssl.phizicgate.esberibgate.ws.generated.BankAcctId_Type bankAcctId;

    private com.rssl.phizicgate.esberibgate.ws.generated.AcctBal_Type acctBal;

    public BankAcctRes_Type() {
    }

    public BankAcctRes_Type(
           com.rssl.phizicgate.esberibgate.ws.generated.BankAcctId_Type bankAcctId,
           com.rssl.phizicgate.esberibgate.ws.generated.AcctBal_Type acctBal) {
           this.bankAcctId = bankAcctId;
           this.acctBal = acctBal;
    }


    /**
     * Gets the bankAcctId value for this BankAcctRes_Type.
     * 
     * @return bankAcctId
     */
    public com.rssl.phizicgate.esberibgate.ws.generated.BankAcctId_Type getBankAcctId() {
        return bankAcctId;
    }


    /**
     * Sets the bankAcctId value for this BankAcctRes_Type.
     * 
     * @param bankAcctId
     */
    public void setBankAcctId(com.rssl.phizicgate.esberibgate.ws.generated.BankAcctId_Type bankAcctId) {
        this.bankAcctId = bankAcctId;
    }


    /**
     * Gets the acctBal value for this BankAcctRes_Type.
     * 
     * @return acctBal
     */
    public com.rssl.phizicgate.esberibgate.ws.generated.AcctBal_Type getAcctBal() {
        return acctBal;
    }


    /**
     * Sets the acctBal value for this BankAcctRes_Type.
     * 
     * @param acctBal
     */
    public void setAcctBal(com.rssl.phizicgate.esberibgate.ws.generated.AcctBal_Type acctBal) {
        this.acctBal = acctBal;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof BankAcctRes_Type)) return false;
        BankAcctRes_Type other = (BankAcctRes_Type) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.bankAcctId==null && other.getBankAcctId()==null) || 
             (this.bankAcctId!=null &&
              this.bankAcctId.equals(other.getBankAcctId()))) &&
            ((this.acctBal==null && other.getAcctBal()==null) || 
             (this.acctBal!=null &&
              this.acctBal.equals(other.getAcctBal())));
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
        if (getBankAcctId() != null) {
            _hashCode += getBankAcctId().hashCode();
        }
        if (getAcctBal() != null) {
            _hashCode += getAcctBal().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(BankAcctRes_Type.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "BankAcctRes_Type"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("bankAcctId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "BankAcctId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "BankAcctId_Type"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("acctBal");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "AcctBal"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "AcctBal_Type"));
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
