/**
 * LoanAcctRec_Type.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.test.webgate.esberib.generated;


/**
 * Список кредитов
 */
public class LoanAcctRec_Type  implements java.io.Serializable {
    private com.rssl.phizic.test.webgate.esberib.generated.LoanAcctId_Type loanAcctId;

    private com.rssl.phizic.test.webgate.esberib.generated.BankInfo_Type bankInfo;

    private com.rssl.phizic.test.webgate.esberib.generated.BankAcctInfo_Type bankAcctInfo;

    private com.rssl.phizic.test.webgate.esberib.generated.CustRec_Type custRec;

    /* Срок договора в днях */
    private java.lang.Long period;

    /* Процентная ставка по кредиту */
    private java.math.BigDecimal creditigRate;

    /* Предельная дата платежа */
    private java.util.Date regNextPayDate;

    /* Предельная дата текущего платежа */
    private java.util.Date nextPayDate;

    /* Предполагаемая сумма платежа */
    private java.math.BigDecimal nextPaySum;

    /* Размер аннуитетного платежа */
    private java.math.BigDecimal regPayAmount;

    /* Статус оплаты */
    private com.rssl.phizic.test.webgate.esberib.generated.PaymentStatus_Type paymentStatus;

    /* Статус кредита */
    private com.rssl.phizic.test.webgate.esberib.generated.LoanStatus_Type loanStatus;

    /* Дата предыдущего платежа */
    private java.util.Date prevPayDate;

    /* Сумма предыдущего платежа */
    private java.math.BigDecimal prevPaySum;

    public LoanAcctRec_Type() {
    }

    public LoanAcctRec_Type(
           com.rssl.phizic.test.webgate.esberib.generated.LoanAcctId_Type loanAcctId,
           com.rssl.phizic.test.webgate.esberib.generated.BankInfo_Type bankInfo,
           com.rssl.phizic.test.webgate.esberib.generated.BankAcctInfo_Type bankAcctInfo,
           com.rssl.phizic.test.webgate.esberib.generated.CustRec_Type custRec,
           java.lang.Long period,
           java.math.BigDecimal creditigRate,
           java.util.Date regNextPayDate,
           java.util.Date nextPayDate,
           java.math.BigDecimal nextPaySum,
           java.math.BigDecimal regPayAmount,
           com.rssl.phizic.test.webgate.esberib.generated.PaymentStatus_Type paymentStatus,
           com.rssl.phizic.test.webgate.esberib.generated.LoanStatus_Type loanStatus,
           java.util.Date prevPayDate,
           java.math.BigDecimal prevPaySum) {
           this.loanAcctId = loanAcctId;
           this.bankInfo = bankInfo;
           this.bankAcctInfo = bankAcctInfo;
           this.custRec = custRec;
           this.period = period;
           this.creditigRate = creditigRate;
           this.regNextPayDate = regNextPayDate;
           this.nextPayDate = nextPayDate;
           this.nextPaySum = nextPaySum;
           this.regPayAmount = regPayAmount;
           this.paymentStatus = paymentStatus;
           this.loanStatus = loanStatus;
           this.prevPayDate = prevPayDate;
           this.prevPaySum = prevPaySum;
    }


    /**
     * Gets the loanAcctId value for this LoanAcctRec_Type.
     * 
     * @return loanAcctId
     */
    public com.rssl.phizic.test.webgate.esberib.generated.LoanAcctId_Type getLoanAcctId() {
        return loanAcctId;
    }


    /**
     * Sets the loanAcctId value for this LoanAcctRec_Type.
     * 
     * @param loanAcctId
     */
    public void setLoanAcctId(com.rssl.phizic.test.webgate.esberib.generated.LoanAcctId_Type loanAcctId) {
        this.loanAcctId = loanAcctId;
    }


    /**
     * Gets the bankInfo value for this LoanAcctRec_Type.
     * 
     * @return bankInfo
     */
    public com.rssl.phizic.test.webgate.esberib.generated.BankInfo_Type getBankInfo() {
        return bankInfo;
    }


    /**
     * Sets the bankInfo value for this LoanAcctRec_Type.
     * 
     * @param bankInfo
     */
    public void setBankInfo(com.rssl.phizic.test.webgate.esberib.generated.BankInfo_Type bankInfo) {
        this.bankInfo = bankInfo;
    }


    /**
     * Gets the bankAcctInfo value for this LoanAcctRec_Type.
     * 
     * @return bankAcctInfo
     */
    public com.rssl.phizic.test.webgate.esberib.generated.BankAcctInfo_Type getBankAcctInfo() {
        return bankAcctInfo;
    }


    /**
     * Sets the bankAcctInfo value for this LoanAcctRec_Type.
     * 
     * @param bankAcctInfo
     */
    public void setBankAcctInfo(com.rssl.phizic.test.webgate.esberib.generated.BankAcctInfo_Type bankAcctInfo) {
        this.bankAcctInfo = bankAcctInfo;
    }


    /**
     * Gets the custRec value for this LoanAcctRec_Type.
     * 
     * @return custRec
     */
    public com.rssl.phizic.test.webgate.esberib.generated.CustRec_Type getCustRec() {
        return custRec;
    }


    /**
     * Sets the custRec value for this LoanAcctRec_Type.
     * 
     * @param custRec
     */
    public void setCustRec(com.rssl.phizic.test.webgate.esberib.generated.CustRec_Type custRec) {
        this.custRec = custRec;
    }


    /**
     * Gets the period value for this LoanAcctRec_Type.
     * 
     * @return period   * Срок договора в днях
     */
    public java.lang.Long getPeriod() {
        return period;
    }


    /**
     * Sets the period value for this LoanAcctRec_Type.
     * 
     * @param period   * Срок договора в днях
     */
    public void setPeriod(java.lang.Long period) {
        this.period = period;
    }


    /**
     * Gets the creditigRate value for this LoanAcctRec_Type.
     * 
     * @return creditigRate   * Процентная ставка по кредиту
     */
    public java.math.BigDecimal getCreditigRate() {
        return creditigRate;
    }


    /**
     * Sets the creditigRate value for this LoanAcctRec_Type.
     * 
     * @param creditigRate   * Процентная ставка по кредиту
     */
    public void setCreditigRate(java.math.BigDecimal creditigRate) {
        this.creditigRate = creditigRate;
    }


    /**
     * Gets the regNextPayDate value for this LoanAcctRec_Type.
     * 
     * @return regNextPayDate   * Предельная дата платежа
     */
    public java.util.Date getRegNextPayDate() {
        return regNextPayDate;
    }


    /**
     * Sets the regNextPayDate value for this LoanAcctRec_Type.
     * 
     * @param regNextPayDate   * Предельная дата платежа
     */
    public void setRegNextPayDate(java.util.Date regNextPayDate) {
        this.regNextPayDate = regNextPayDate;
    }


    /**
     * Gets the nextPayDate value for this LoanAcctRec_Type.
     * 
     * @return nextPayDate   * Предельная дата текущего платежа
     */
    public java.util.Date getNextPayDate() {
        return nextPayDate;
    }


    /**
     * Sets the nextPayDate value for this LoanAcctRec_Type.
     * 
     * @param nextPayDate   * Предельная дата текущего платежа
     */
    public void setNextPayDate(java.util.Date nextPayDate) {
        this.nextPayDate = nextPayDate;
    }


    /**
     * Gets the nextPaySum value for this LoanAcctRec_Type.
     * 
     * @return nextPaySum   * Предполагаемая сумма платежа
     */
    public java.math.BigDecimal getNextPaySum() {
        return nextPaySum;
    }


    /**
     * Sets the nextPaySum value for this LoanAcctRec_Type.
     * 
     * @param nextPaySum   * Предполагаемая сумма платежа
     */
    public void setNextPaySum(java.math.BigDecimal nextPaySum) {
        this.nextPaySum = nextPaySum;
    }


    /**
     * Gets the regPayAmount value for this LoanAcctRec_Type.
     * 
     * @return regPayAmount   * Размер аннуитетного платежа
     */
    public java.math.BigDecimal getRegPayAmount() {
        return regPayAmount;
    }


    /**
     * Sets the regPayAmount value for this LoanAcctRec_Type.
     * 
     * @param regPayAmount   * Размер аннуитетного платежа
     */
    public void setRegPayAmount(java.math.BigDecimal regPayAmount) {
        this.regPayAmount = regPayAmount;
    }


    /**
     * Gets the paymentStatus value for this LoanAcctRec_Type.
     * 
     * @return paymentStatus   * Статус оплаты
     */
    public com.rssl.phizic.test.webgate.esberib.generated.PaymentStatus_Type getPaymentStatus() {
        return paymentStatus;
    }


    /**
     * Sets the paymentStatus value for this LoanAcctRec_Type.
     * 
     * @param paymentStatus   * Статус оплаты
     */
    public void setPaymentStatus(com.rssl.phizic.test.webgate.esberib.generated.PaymentStatus_Type paymentStatus) {
        this.paymentStatus = paymentStatus;
    }


    /**
     * Gets the loanStatus value for this LoanAcctRec_Type.
     * 
     * @return loanStatus   * Статус кредита
     */
    public com.rssl.phizic.test.webgate.esberib.generated.LoanStatus_Type getLoanStatus() {
        return loanStatus;
    }


    /**
     * Sets the loanStatus value for this LoanAcctRec_Type.
     * 
     * @param loanStatus   * Статус кредита
     */
    public void setLoanStatus(com.rssl.phizic.test.webgate.esberib.generated.LoanStatus_Type loanStatus) {
        this.loanStatus = loanStatus;
    }


    /**
     * Gets the prevPayDate value for this LoanAcctRec_Type.
     * 
     * @return prevPayDate   * Дата предыдущего платежа
     */
    public java.util.Date getPrevPayDate() {
        return prevPayDate;
    }


    /**
     * Sets the prevPayDate value for this LoanAcctRec_Type.
     * 
     * @param prevPayDate   * Дата предыдущего платежа
     */
    public void setPrevPayDate(java.util.Date prevPayDate) {
        this.prevPayDate = prevPayDate;
    }


    /**
     * Gets the prevPaySum value for this LoanAcctRec_Type.
     * 
     * @return prevPaySum   * Сумма предыдущего платежа
     */
    public java.math.BigDecimal getPrevPaySum() {
        return prevPaySum;
    }


    /**
     * Sets the prevPaySum value for this LoanAcctRec_Type.
     * 
     * @param prevPaySum   * Сумма предыдущего платежа
     */
    public void setPrevPaySum(java.math.BigDecimal prevPaySum) {
        this.prevPaySum = prevPaySum;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof LoanAcctRec_Type)) return false;
        LoanAcctRec_Type other = (LoanAcctRec_Type) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.loanAcctId==null && other.getLoanAcctId()==null) || 
             (this.loanAcctId!=null &&
              this.loanAcctId.equals(other.getLoanAcctId()))) &&
            ((this.bankInfo==null && other.getBankInfo()==null) || 
             (this.bankInfo!=null &&
              this.bankInfo.equals(other.getBankInfo()))) &&
            ((this.bankAcctInfo==null && other.getBankAcctInfo()==null) || 
             (this.bankAcctInfo!=null &&
              this.bankAcctInfo.equals(other.getBankAcctInfo()))) &&
            ((this.custRec==null && other.getCustRec()==null) || 
             (this.custRec!=null &&
              this.custRec.equals(other.getCustRec()))) &&
            ((this.period==null && other.getPeriod()==null) || 
             (this.period!=null &&
              this.period.equals(other.getPeriod()))) &&
            ((this.creditigRate==null && other.getCreditigRate()==null) || 
             (this.creditigRate!=null &&
              this.creditigRate.equals(other.getCreditigRate()))) &&
            ((this.regNextPayDate==null && other.getRegNextPayDate()==null) || 
             (this.regNextPayDate!=null &&
              this.regNextPayDate.equals(other.getRegNextPayDate()))) &&
            ((this.nextPayDate==null && other.getNextPayDate()==null) || 
             (this.nextPayDate!=null &&
              this.nextPayDate.equals(other.getNextPayDate()))) &&
            ((this.nextPaySum==null && other.getNextPaySum()==null) || 
             (this.nextPaySum!=null &&
              this.nextPaySum.equals(other.getNextPaySum()))) &&
            ((this.regPayAmount==null && other.getRegPayAmount()==null) || 
             (this.regPayAmount!=null &&
              this.regPayAmount.equals(other.getRegPayAmount()))) &&
            ((this.paymentStatus==null && other.getPaymentStatus()==null) || 
             (this.paymentStatus!=null &&
              this.paymentStatus.equals(other.getPaymentStatus()))) &&
            ((this.loanStatus==null && other.getLoanStatus()==null) || 
             (this.loanStatus!=null &&
              this.loanStatus.equals(other.getLoanStatus()))) &&
            ((this.prevPayDate==null && other.getPrevPayDate()==null) || 
             (this.prevPayDate!=null &&
              this.prevPayDate.equals(other.getPrevPayDate()))) &&
            ((this.prevPaySum==null && other.getPrevPaySum()==null) || 
             (this.prevPaySum!=null &&
              this.prevPaySum.equals(other.getPrevPaySum())));
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
        if (getLoanAcctId() != null) {
            _hashCode += getLoanAcctId().hashCode();
        }
        if (getBankInfo() != null) {
            _hashCode += getBankInfo().hashCode();
        }
        if (getBankAcctInfo() != null) {
            _hashCode += getBankAcctInfo().hashCode();
        }
        if (getCustRec() != null) {
            _hashCode += getCustRec().hashCode();
        }
        if (getPeriod() != null) {
            _hashCode += getPeriod().hashCode();
        }
        if (getCreditigRate() != null) {
            _hashCode += getCreditigRate().hashCode();
        }
        if (getRegNextPayDate() != null) {
            _hashCode += getRegNextPayDate().hashCode();
        }
        if (getNextPayDate() != null) {
            _hashCode += getNextPayDate().hashCode();
        }
        if (getNextPaySum() != null) {
            _hashCode += getNextPaySum().hashCode();
        }
        if (getRegPayAmount() != null) {
            _hashCode += getRegPayAmount().hashCode();
        }
        if (getPaymentStatus() != null) {
            _hashCode += getPaymentStatus().hashCode();
        }
        if (getLoanStatus() != null) {
            _hashCode += getLoanStatus().hashCode();
        }
        if (getPrevPayDate() != null) {
            _hashCode += getPrevPayDate().hashCode();
        }
        if (getPrevPaySum() != null) {
            _hashCode += getPrevPaySum().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(LoanAcctRec_Type.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "LoanAcctRec_Type"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("loanAcctId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "LoanAcctId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "LoanAcctId_Type"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("bankInfo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "BankInfo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "BankInfo_Type"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("bankAcctInfo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "BankAcctInfo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "BankAcctInfo_Type"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("custRec");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "CustRec"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "CustRec_Type"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("period");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Period"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("creditigRate");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "CreditigRate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("regNextPayDate");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "RegNextPayDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "date"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("nextPayDate");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "NextPayDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "date"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("nextPaySum");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "NextPaySum"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("regPayAmount");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "RegPayAmount"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("paymentStatus");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "PaymentStatus"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "PaymentStatus_Type"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("loanStatus");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "LoanStatus"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "LoanStatus_Type"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("prevPayDate");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "PrevPayDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "date"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("prevPaySum");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "PrevPaySum"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
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
