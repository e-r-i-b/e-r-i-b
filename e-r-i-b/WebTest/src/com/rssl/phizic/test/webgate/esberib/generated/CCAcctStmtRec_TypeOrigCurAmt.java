/**
 * CCAcctStmtRec_TypeOrigCurAmt.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.test.webgate.esberib.generated;

public class CCAcctStmtRec_TypeOrigCurAmt  implements java.io.Serializable {
    private java.math.BigDecimal curAmt;

    private java.lang.String acctCur;

    public CCAcctStmtRec_TypeOrigCurAmt() {
    }

    public CCAcctStmtRec_TypeOrigCurAmt(
           java.math.BigDecimal curAmt,
           java.lang.String acctCur) {
           this.curAmt = curAmt;
           this.acctCur = acctCur;
    }


    /**
     * Gets the curAmt value for this CCAcctStmtRec_TypeOrigCurAmt.
     * 
     * @return curAmt
     */
    public java.math.BigDecimal getCurAmt() {
        return curAmt;
    }


    /**
     * Sets the curAmt value for this CCAcctStmtRec_TypeOrigCurAmt.
     * 
     * @param curAmt
     */
    public void setCurAmt(java.math.BigDecimal curAmt) {
        this.curAmt = curAmt;
    }


    /**
     * Gets the acctCur value for this CCAcctStmtRec_TypeOrigCurAmt.
     * 
     * @return acctCur
     */
    public java.lang.String getAcctCur() {
        return acctCur;
    }


    /**
     * Sets the acctCur value for this CCAcctStmtRec_TypeOrigCurAmt.
     * 
     * @param acctCur
     */
    public void setAcctCur(java.lang.String acctCur) {
        this.acctCur = acctCur;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof CCAcctStmtRec_TypeOrigCurAmt)) return false;
        CCAcctStmtRec_TypeOrigCurAmt other = (CCAcctStmtRec_TypeOrigCurAmt) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.curAmt==null && other.getCurAmt()==null) || 
             (this.curAmt!=null &&
              this.curAmt.equals(other.getCurAmt()))) &&
            ((this.acctCur==null && other.getAcctCur()==null) || 
             (this.acctCur!=null &&
              this.acctCur.equals(other.getAcctCur())));
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
        if (getCurAmt() != null) {
            _hashCode += getCurAmt().hashCode();
        }
        if (getAcctCur() != null) {
            _hashCode += getAcctCur().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(CCAcctStmtRec_TypeOrigCurAmt.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", ">CCAcctStmtRec_Type>OrigCurAmt"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("curAmt");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "CurAmt"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Decimal"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("acctCur");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "AcctCur"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "AcctCur_Type"));
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
