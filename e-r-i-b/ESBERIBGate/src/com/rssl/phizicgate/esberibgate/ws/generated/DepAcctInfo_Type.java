/**
 * DepAcctInfo_Type.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizicgate.esberibgate.ws.generated;


/**
 * Информация по счету
 */
public class DepAcctInfo_Type  implements java.io.Serializable {
    private java.lang.String acctCur;

    private com.rssl.phizicgate.esberibgate.ws.generated.LoanStatus_Type status;

    public DepAcctInfo_Type() {
    }

    public DepAcctInfo_Type(
           java.lang.String acctCur,
           com.rssl.phizicgate.esberibgate.ws.generated.LoanStatus_Type status) {
           this.acctCur = acctCur;
           this.status = status;
    }


    /**
     * Gets the acctCur value for this DepAcctInfo_Type.
     * 
     * @return acctCur
     */
    public java.lang.String getAcctCur() {
        return acctCur;
    }


    /**
     * Sets the acctCur value for this DepAcctInfo_Type.
     * 
     * @param acctCur
     */
    public void setAcctCur(java.lang.String acctCur) {
        this.acctCur = acctCur;
    }


    /**
     * Gets the status value for this DepAcctInfo_Type.
     * 
     * @return status
     */
    public com.rssl.phizicgate.esberibgate.ws.generated.LoanStatus_Type getStatus() {
        return status;
    }


    /**
     * Sets the status value for this DepAcctInfo_Type.
     * 
     * @param status
     */
    public void setStatus(com.rssl.phizicgate.esberibgate.ws.generated.LoanStatus_Type status) {
        this.status = status;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof DepAcctInfo_Type)) return false;
        DepAcctInfo_Type other = (DepAcctInfo_Type) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.acctCur==null && other.getAcctCur()==null) || 
             (this.acctCur!=null &&
              this.acctCur.equals(other.getAcctCur()))) &&
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
        if (getAcctCur() != null) {
            _hashCode += getAcctCur().hashCode();
        }
        if (getStatus() != null) {
            _hashCode += getStatus().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(DepAcctInfo_Type.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "DepAcctInfo_Type"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("acctCur");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "AcctCur"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "AcctCur_Type"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("status");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Status"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "LoanStatus_Type"));
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
