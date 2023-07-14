/**
 * StmtSummAmt_Type.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.test.webgate.esberib.generated;

public class StmtSummAmt_Type  implements java.io.Serializable {
    private java.lang.String stmtSummType;

    private java.math.BigDecimal curAmt;

    /* Валюта операции */
    private java.lang.String curAmtCur;

    /* Валюта операции */
    private java.lang.String acctCur;

    /* Номер счета контрагента */
    private java.lang.String contrAccount;

    public StmtSummAmt_Type() {
    }

    public StmtSummAmt_Type(
           java.lang.String stmtSummType,
           java.math.BigDecimal curAmt,
           java.lang.String curAmtCur,
           java.lang.String acctCur,
           java.lang.String contrAccount) {
           this.stmtSummType = stmtSummType;
           this.curAmt = curAmt;
           this.curAmtCur = curAmtCur;
           this.acctCur = acctCur;
           this.contrAccount = contrAccount;
    }


    /**
     * Gets the stmtSummType value for this StmtSummAmt_Type.
     * 
     * @return stmtSummType
     */
    public java.lang.String getStmtSummType() {
        return stmtSummType;
    }


    /**
     * Sets the stmtSummType value for this StmtSummAmt_Type.
     * 
     * @param stmtSummType
     */
    public void setStmtSummType(java.lang.String stmtSummType) {
        this.stmtSummType = stmtSummType;
    }


    /**
     * Gets the curAmt value for this StmtSummAmt_Type.
     * 
     * @return curAmt
     */
    public java.math.BigDecimal getCurAmt() {
        return curAmt;
    }


    /**
     * Sets the curAmt value for this StmtSummAmt_Type.
     * 
     * @param curAmt
     */
    public void setCurAmt(java.math.BigDecimal curAmt) {
        this.curAmt = curAmt;
    }


    /**
     * Gets the curAmtCur value for this StmtSummAmt_Type.
     * 
     * @return curAmtCur   * Валюта операции
     */
    public java.lang.String getCurAmtCur() {
        return curAmtCur;
    }


    /**
     * Sets the curAmtCur value for this StmtSummAmt_Type.
     * 
     * @param curAmtCur   * Валюта операции
     */
    public void setCurAmtCur(java.lang.String curAmtCur) {
        this.curAmtCur = curAmtCur;
    }


    /**
     * Gets the acctCur value for this StmtSummAmt_Type.
     * 
     * @return acctCur   * Валюта операции
     */
    public java.lang.String getAcctCur() {
        return acctCur;
    }


    /**
     * Sets the acctCur value for this StmtSummAmt_Type.
     * 
     * @param acctCur   * Валюта операции
     */
    public void setAcctCur(java.lang.String acctCur) {
        this.acctCur = acctCur;
    }


    /**
     * Gets the contrAccount value for this StmtSummAmt_Type.
     * 
     * @return contrAccount   * Номер счета контрагента
     */
    public java.lang.String getContrAccount() {
        return contrAccount;
    }


    /**
     * Sets the contrAccount value for this StmtSummAmt_Type.
     * 
     * @param contrAccount   * Номер счета контрагента
     */
    public void setContrAccount(java.lang.String contrAccount) {
        this.contrAccount = contrAccount;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof StmtSummAmt_Type)) return false;
        StmtSummAmt_Type other = (StmtSummAmt_Type) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.stmtSummType==null && other.getStmtSummType()==null) || 
             (this.stmtSummType!=null &&
              this.stmtSummType.equals(other.getStmtSummType()))) &&
            ((this.curAmt==null && other.getCurAmt()==null) || 
             (this.curAmt!=null &&
              this.curAmt.equals(other.getCurAmt()))) &&
            ((this.curAmtCur==null && other.getCurAmtCur()==null) || 
             (this.curAmtCur!=null &&
              this.curAmtCur.equals(other.getCurAmtCur()))) &&
            ((this.acctCur==null && other.getAcctCur()==null) || 
             (this.acctCur!=null &&
              this.acctCur.equals(other.getAcctCur()))) &&
            ((this.contrAccount==null && other.getContrAccount()==null) || 
             (this.contrAccount!=null &&
              this.contrAccount.equals(other.getContrAccount())));
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
        if (getStmtSummType() != null) {
            _hashCode += getStmtSummType().hashCode();
        }
        if (getCurAmt() != null) {
            _hashCode += getCurAmt().hashCode();
        }
        if (getCurAmtCur() != null) {
            _hashCode += getCurAmtCur().hashCode();
        }
        if (getAcctCur() != null) {
            _hashCode += getAcctCur().hashCode();
        }
        if (getContrAccount() != null) {
            _hashCode += getContrAccount().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(StmtSummAmt_Type.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "StmtSummAmt_Type"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("stmtSummType");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "StmtSummType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "StmtSummType_Type"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("curAmt");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "CurAmt"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Decimal"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("curAmtCur");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "CurAmtCur"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "String"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("acctCur");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "AcctCur"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "String"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("contrAccount");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "ContrAccount"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
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
