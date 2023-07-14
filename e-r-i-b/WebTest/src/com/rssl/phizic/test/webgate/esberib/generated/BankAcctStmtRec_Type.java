/**
 * BankAcctStmtRec_Type.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.test.webgate.esberib.generated;


/**
 * Информация по выписке по ОМС
 */
public class BankAcctStmtRec_Type  implements java.io.Serializable {
    private java.lang.String effDate;

    private com.rssl.phizic.test.webgate.esberib.generated.StmtSummAmt_Type stmtSummAmt;

    /* Тип остатков BalType для ОМС: BalanceAfterOperation */
    private com.rssl.phizic.test.webgate.esberib.generated.AcctBal_Type acctBal;

    /* Информация о конверсии при операциях с ОМС */
    private com.rssl.phizic.test.webgate.esberib.generated.IMAOperConvInfo_Type IMAOperConvInfo;

    public BankAcctStmtRec_Type() {
    }

    public BankAcctStmtRec_Type(
           java.lang.String effDate,
           com.rssl.phizic.test.webgate.esberib.generated.StmtSummAmt_Type stmtSummAmt,
           com.rssl.phizic.test.webgate.esberib.generated.AcctBal_Type acctBal,
           com.rssl.phizic.test.webgate.esberib.generated.IMAOperConvInfo_Type IMAOperConvInfo) {
           this.effDate = effDate;
           this.stmtSummAmt = stmtSummAmt;
           this.acctBal = acctBal;
           this.IMAOperConvInfo = IMAOperConvInfo;
    }


    /**
     * Gets the effDate value for this BankAcctStmtRec_Type.
     * 
     * @return effDate
     */
    public java.lang.String getEffDate() {
        return effDate;
    }


    /**
     * Sets the effDate value for this BankAcctStmtRec_Type.
     * 
     * @param effDate
     */
    public void setEffDate(java.lang.String effDate) {
        this.effDate = effDate;
    }


    /**
     * Gets the stmtSummAmt value for this BankAcctStmtRec_Type.
     * 
     * @return stmtSummAmt
     */
    public com.rssl.phizic.test.webgate.esberib.generated.StmtSummAmt_Type getStmtSummAmt() {
        return stmtSummAmt;
    }


    /**
     * Sets the stmtSummAmt value for this BankAcctStmtRec_Type.
     * 
     * @param stmtSummAmt
     */
    public void setStmtSummAmt(com.rssl.phizic.test.webgate.esberib.generated.StmtSummAmt_Type stmtSummAmt) {
        this.stmtSummAmt = stmtSummAmt;
    }


    /**
     * Gets the acctBal value for this BankAcctStmtRec_Type.
     * 
     * @return acctBal   * Тип остатков BalType для ОМС: BalanceAfterOperation
     */
    public com.rssl.phizic.test.webgate.esberib.generated.AcctBal_Type getAcctBal() {
        return acctBal;
    }


    /**
     * Sets the acctBal value for this BankAcctStmtRec_Type.
     * 
     * @param acctBal   * Тип остатков BalType для ОМС: BalanceAfterOperation
     */
    public void setAcctBal(com.rssl.phizic.test.webgate.esberib.generated.AcctBal_Type acctBal) {
        this.acctBal = acctBal;
    }


    /**
     * Gets the IMAOperConvInfo value for this BankAcctStmtRec_Type.
     * 
     * @return IMAOperConvInfo   * Информация о конверсии при операциях с ОМС
     */
    public com.rssl.phizic.test.webgate.esberib.generated.IMAOperConvInfo_Type getIMAOperConvInfo() {
        return IMAOperConvInfo;
    }


    /**
     * Sets the IMAOperConvInfo value for this BankAcctStmtRec_Type.
     * 
     * @param IMAOperConvInfo   * Информация о конверсии при операциях с ОМС
     */
    public void setIMAOperConvInfo(com.rssl.phizic.test.webgate.esberib.generated.IMAOperConvInfo_Type IMAOperConvInfo) {
        this.IMAOperConvInfo = IMAOperConvInfo;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof BankAcctStmtRec_Type)) return false;
        BankAcctStmtRec_Type other = (BankAcctStmtRec_Type) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.effDate==null && other.getEffDate()==null) || 
             (this.effDate!=null &&
              this.effDate.equals(other.getEffDate()))) &&
            ((this.stmtSummAmt==null && other.getStmtSummAmt()==null) || 
             (this.stmtSummAmt!=null &&
              this.stmtSummAmt.equals(other.getStmtSummAmt()))) &&
            ((this.acctBal==null && other.getAcctBal()==null) || 
             (this.acctBal!=null &&
              this.acctBal.equals(other.getAcctBal()))) &&
            ((this.IMAOperConvInfo==null && other.getIMAOperConvInfo()==null) || 
             (this.IMAOperConvInfo!=null &&
              this.IMAOperConvInfo.equals(other.getIMAOperConvInfo())));
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
        if (getEffDate() != null) {
            _hashCode += getEffDate().hashCode();
        }
        if (getStmtSummAmt() != null) {
            _hashCode += getStmtSummAmt().hashCode();
        }
        if (getAcctBal() != null) {
            _hashCode += getAcctBal().hashCode();
        }
        if (getIMAOperConvInfo() != null) {
            _hashCode += getIMAOperConvInfo().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(BankAcctStmtRec_Type.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "BankAcctStmtRec_Type"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("effDate");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "EffDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Date"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("stmtSummAmt");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "StmtSummAmt"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "StmtSummAmt_Type"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("acctBal");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "AcctBal"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "AcctBal_Type"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("IMAOperConvInfo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "IMAOperConvInfo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "IMAOperConvInfo_Type"));
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
