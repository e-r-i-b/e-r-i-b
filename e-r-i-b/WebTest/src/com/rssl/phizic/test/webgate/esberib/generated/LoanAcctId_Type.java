/**
 * LoanAcctId_Type.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.test.webgate.esberib.generated;


/**
 * Информация о кредите
 */
public class LoanAcctId_Type  implements java.io.Serializable {
    private java.lang.String systemId;

    private java.lang.String acctId;

    private java.lang.String agreemtNum;

    private java.lang.String prodType;

    private java.lang.String loanType;

    /* Дата, на которую нужно рассчитать задолженность */
    private java.lang.String dateCalc;

    private java.math.BigDecimal curAmt;

    private java.lang.String acctCur;

    private java.math.BigDecimal origAmt;

    private com.rssl.phizic.test.webgate.esberib.generated.BankInfo_Type bankInfo;

    /* Идентификатор кредитного договора в ЕКП */
    private java.lang.String prodId;

    public LoanAcctId_Type() {
    }

    public LoanAcctId_Type(
           java.lang.String systemId,
           java.lang.String acctId,
           java.lang.String agreemtNum,
           java.lang.String prodType,
           java.lang.String loanType,
           java.lang.String dateCalc,
           java.math.BigDecimal curAmt,
           java.lang.String acctCur,
           java.math.BigDecimal origAmt,
           com.rssl.phizic.test.webgate.esberib.generated.BankInfo_Type bankInfo,
           java.lang.String prodId) {
           this.systemId = systemId;
           this.acctId = acctId;
           this.agreemtNum = agreemtNum;
           this.prodType = prodType;
           this.loanType = loanType;
           this.dateCalc = dateCalc;
           this.curAmt = curAmt;
           this.acctCur = acctCur;
           this.origAmt = origAmt;
           this.bankInfo = bankInfo;
           this.prodId = prodId;
    }


    /**
     * Gets the systemId value for this LoanAcctId_Type.
     * 
     * @return systemId
     */
    public java.lang.String getSystemId() {
        return systemId;
    }


    /**
     * Sets the systemId value for this LoanAcctId_Type.
     * 
     * @param systemId
     */
    public void setSystemId(java.lang.String systemId) {
        this.systemId = systemId;
    }


    /**
     * Gets the acctId value for this LoanAcctId_Type.
     * 
     * @return acctId
     */
    public java.lang.String getAcctId() {
        return acctId;
    }


    /**
     * Sets the acctId value for this LoanAcctId_Type.
     * 
     * @param acctId
     */
    public void setAcctId(java.lang.String acctId) {
        this.acctId = acctId;
    }


    /**
     * Gets the agreemtNum value for this LoanAcctId_Type.
     * 
     * @return agreemtNum
     */
    public java.lang.String getAgreemtNum() {
        return agreemtNum;
    }


    /**
     * Sets the agreemtNum value for this LoanAcctId_Type.
     * 
     * @param agreemtNum
     */
    public void setAgreemtNum(java.lang.String agreemtNum) {
        this.agreemtNum = agreemtNum;
    }


    /**
     * Gets the prodType value for this LoanAcctId_Type.
     * 
     * @return prodType
     */
    public java.lang.String getProdType() {
        return prodType;
    }


    /**
     * Sets the prodType value for this LoanAcctId_Type.
     * 
     * @param prodType
     */
    public void setProdType(java.lang.String prodType) {
        this.prodType = prodType;
    }


    /**
     * Gets the loanType value for this LoanAcctId_Type.
     * 
     * @return loanType
     */
    public java.lang.String getLoanType() {
        return loanType;
    }


    /**
     * Sets the loanType value for this LoanAcctId_Type.
     * 
     * @param loanType
     */
    public void setLoanType(java.lang.String loanType) {
        this.loanType = loanType;
    }


    /**
     * Gets the dateCalc value for this LoanAcctId_Type.
     * 
     * @return dateCalc   * Дата, на которую нужно рассчитать задолженность
     */
    public java.lang.String getDateCalc() {
        return dateCalc;
    }


    /**
     * Sets the dateCalc value for this LoanAcctId_Type.
     * 
     * @param dateCalc   * Дата, на которую нужно рассчитать задолженность
     */
    public void setDateCalc(java.lang.String dateCalc) {
        this.dateCalc = dateCalc;
    }


    /**
     * Gets the curAmt value for this LoanAcctId_Type.
     * 
     * @return curAmt
     */
    public java.math.BigDecimal getCurAmt() {
        return curAmt;
    }


    /**
     * Sets the curAmt value for this LoanAcctId_Type.
     * 
     * @param curAmt
     */
    public void setCurAmt(java.math.BigDecimal curAmt) {
        this.curAmt = curAmt;
    }


    /**
     * Gets the acctCur value for this LoanAcctId_Type.
     * 
     * @return acctCur
     */
    public java.lang.String getAcctCur() {
        return acctCur;
    }


    /**
     * Sets the acctCur value for this LoanAcctId_Type.
     * 
     * @param acctCur
     */
    public void setAcctCur(java.lang.String acctCur) {
        this.acctCur = acctCur;
    }


    /**
     * Gets the origAmt value for this LoanAcctId_Type.
     * 
     * @return origAmt
     */
    public java.math.BigDecimal getOrigAmt() {
        return origAmt;
    }


    /**
     * Sets the origAmt value for this LoanAcctId_Type.
     * 
     * @param origAmt
     */
    public void setOrigAmt(java.math.BigDecimal origAmt) {
        this.origAmt = origAmt;
    }


    /**
     * Gets the bankInfo value for this LoanAcctId_Type.
     * 
     * @return bankInfo
     */
    public com.rssl.phizic.test.webgate.esberib.generated.BankInfo_Type getBankInfo() {
        return bankInfo;
    }


    /**
     * Sets the bankInfo value for this LoanAcctId_Type.
     * 
     * @param bankInfo
     */
    public void setBankInfo(com.rssl.phizic.test.webgate.esberib.generated.BankInfo_Type bankInfo) {
        this.bankInfo = bankInfo;
    }


    /**
     * Gets the prodId value for this LoanAcctId_Type.
     * 
     * @return prodId   * Идентификатор кредитного договора в ЕКП
     */
    public java.lang.String getProdId() {
        return prodId;
    }


    /**
     * Sets the prodId value for this LoanAcctId_Type.
     * 
     * @param prodId   * Идентификатор кредитного договора в ЕКП
     */
    public void setProdId(java.lang.String prodId) {
        this.prodId = prodId;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof LoanAcctId_Type)) return false;
        LoanAcctId_Type other = (LoanAcctId_Type) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.systemId==null && other.getSystemId()==null) || 
             (this.systemId!=null &&
              this.systemId.equals(other.getSystemId()))) &&
            ((this.acctId==null && other.getAcctId()==null) || 
             (this.acctId!=null &&
              this.acctId.equals(other.getAcctId()))) &&
            ((this.agreemtNum==null && other.getAgreemtNum()==null) || 
             (this.agreemtNum!=null &&
              this.agreemtNum.equals(other.getAgreemtNum()))) &&
            ((this.prodType==null && other.getProdType()==null) || 
             (this.prodType!=null &&
              this.prodType.equals(other.getProdType()))) &&
            ((this.loanType==null && other.getLoanType()==null) || 
             (this.loanType!=null &&
              this.loanType.equals(other.getLoanType()))) &&
            ((this.dateCalc==null && other.getDateCalc()==null) || 
             (this.dateCalc!=null &&
              this.dateCalc.equals(other.getDateCalc()))) &&
            ((this.curAmt==null && other.getCurAmt()==null) || 
             (this.curAmt!=null &&
              this.curAmt.equals(other.getCurAmt()))) &&
            ((this.acctCur==null && other.getAcctCur()==null) || 
             (this.acctCur!=null &&
              this.acctCur.equals(other.getAcctCur()))) &&
            ((this.origAmt==null && other.getOrigAmt()==null) || 
             (this.origAmt!=null &&
              this.origAmt.equals(other.getOrigAmt()))) &&
            ((this.bankInfo==null && other.getBankInfo()==null) || 
             (this.bankInfo!=null &&
              this.bankInfo.equals(other.getBankInfo()))) &&
            ((this.prodId==null && other.getProdId()==null) || 
             (this.prodId!=null &&
              this.prodId.equals(other.getProdId())));
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
        if (getSystemId() != null) {
            _hashCode += getSystemId().hashCode();
        }
        if (getAcctId() != null) {
            _hashCode += getAcctId().hashCode();
        }
        if (getAgreemtNum() != null) {
            _hashCode += getAgreemtNum().hashCode();
        }
        if (getProdType() != null) {
            _hashCode += getProdType().hashCode();
        }
        if (getLoanType() != null) {
            _hashCode += getLoanType().hashCode();
        }
        if (getDateCalc() != null) {
            _hashCode += getDateCalc().hashCode();
        }
        if (getCurAmt() != null) {
            _hashCode += getCurAmt().hashCode();
        }
        if (getAcctCur() != null) {
            _hashCode += getAcctCur().hashCode();
        }
        if (getOrigAmt() != null) {
            _hashCode += getOrigAmt().hashCode();
        }
        if (getBankInfo() != null) {
            _hashCode += getBankInfo().hashCode();
        }
        if (getProdId() != null) {
            _hashCode += getProdId().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(LoanAcctId_Type.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "LoanAcctId_Type"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("systemId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "SystemId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "SystemId_Type"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("acctId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "AcctId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "AcctIdType"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("agreemtNum");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "AgreemtNum"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "AgreemtNum_Type"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("prodType");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "ProdType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "ProdType_Type"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("loanType");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "LoanType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "LoanType_Type"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dateCalc");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "DateCalc"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("curAmt");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "CurAmt"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Decimal"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("acctCur");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "AcctCur"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "AcctCur_Type"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("origAmt");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "OrigAmt"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Decimal"));
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
        elemField.setFieldName("prodId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "ProdId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "String"));
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
