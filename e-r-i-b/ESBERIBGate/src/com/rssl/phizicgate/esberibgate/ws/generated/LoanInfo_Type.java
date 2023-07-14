/**
 * LoanInfo_Type.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizicgate.esberibgate.ws.generated;

public class LoanInfo_Type  implements java.io.Serializable {
    private com.rssl.phizicgate.esberibgate.ws.generated.AcctBal_Type[] acctBalFull;

    private com.rssl.phizicgate.esberibgate.ws.generated.AcctBal_Type[] acctBalOnDate;

    private java.lang.String idSpacing;

    private com.rssl.phizicgate.esberibgate.ws.generated.LoanAcctId_Type loanAcctId;

    private java.lang.String agreemtNum;

    private java.lang.String prodType;

    private java.lang.String loanType;

    private java.lang.String acctCur;

    private java.math.BigDecimal curAmt;

    /* Срок договора в днях */
    private java.lang.Long period;

    /* Процентная ставка по кредиту */
    private java.math.BigDecimal creditingRate;

    /* Предельная дата платежа */
    private java.lang.String regNextPayDate;

    /* Предельная дата текущего периода */
    private java.lang.String nextPayDate;

    /* Предполагаемая сумма платежа */
    private java.math.BigDecimal nextPaySum;

    /* Размер аннуитетного платежа, поле обязательно, если признак
     * аннуитетности равен true */
    private java.math.BigDecimal regPayAmount;

    /* Статус оплаты, 1 – не просрочен, 2 – просрочен */
    private com.rssl.phizicgate.esberibgate.ws.generated.PaymentStatus_Type paymentStatus;

    /* Статус кредита, 1 – открыт, 2 – закрыт */
    private java.lang.String loanStatus;

    /* Дата предыдущего платежа */
    private java.lang.String prevPayDate;

    /* Сумма предыдущего платежа */
    private java.math.BigDecimal prevPaySum;

    /* Карта оплаты */
    private java.lang.String payCard;

    private com.rssl.phizicgate.esberibgate.ws.generated.CustRec_Type[] custRec;

    public LoanInfo_Type() {
    }

    public LoanInfo_Type(
           com.rssl.phizicgate.esberibgate.ws.generated.AcctBal_Type[] acctBalFull,
           com.rssl.phizicgate.esberibgate.ws.generated.AcctBal_Type[] acctBalOnDate,
           java.lang.String idSpacing,
           com.rssl.phizicgate.esberibgate.ws.generated.LoanAcctId_Type loanAcctId,
           java.lang.String agreemtNum,
           java.lang.String prodType,
           java.lang.String loanType,
           java.lang.String acctCur,
           java.math.BigDecimal curAmt,
           java.lang.Long period,
           java.math.BigDecimal creditingRate,
           java.lang.String regNextPayDate,
           java.lang.String nextPayDate,
           java.math.BigDecimal nextPaySum,
           java.math.BigDecimal regPayAmount,
           com.rssl.phizicgate.esberibgate.ws.generated.PaymentStatus_Type paymentStatus,
           java.lang.String loanStatus,
           java.lang.String prevPayDate,
           java.math.BigDecimal prevPaySum,
           java.lang.String payCard,
           com.rssl.phizicgate.esberibgate.ws.generated.CustRec_Type[] custRec) {
           this.acctBalFull = acctBalFull;
           this.acctBalOnDate = acctBalOnDate;
           this.idSpacing = idSpacing;
           this.loanAcctId = loanAcctId;
           this.agreemtNum = agreemtNum;
           this.prodType = prodType;
           this.loanType = loanType;
           this.acctCur = acctCur;
           this.curAmt = curAmt;
           this.period = period;
           this.creditingRate = creditingRate;
           this.regNextPayDate = regNextPayDate;
           this.nextPayDate = nextPayDate;
           this.nextPaySum = nextPaySum;
           this.regPayAmount = regPayAmount;
           this.paymentStatus = paymentStatus;
           this.loanStatus = loanStatus;
           this.prevPayDate = prevPayDate;
           this.prevPaySum = prevPaySum;
           this.payCard = payCard;
           this.custRec = custRec;
    }


    /**
     * Gets the acctBalFull value for this LoanInfo_Type.
     * 
     * @return acctBalFull
     */
    public com.rssl.phizicgate.esberibgate.ws.generated.AcctBal_Type[] getAcctBalFull() {
        return acctBalFull;
    }


    /**
     * Sets the acctBalFull value for this LoanInfo_Type.
     * 
     * @param acctBalFull
     */
    public void setAcctBalFull(com.rssl.phizicgate.esberibgate.ws.generated.AcctBal_Type[] acctBalFull) {
        this.acctBalFull = acctBalFull;
    }

    public com.rssl.phizicgate.esberibgate.ws.generated.AcctBal_Type getAcctBalFull(int i) {
        return this.acctBalFull[i];
    }

    public void setAcctBalFull(int i, com.rssl.phizicgate.esberibgate.ws.generated.AcctBal_Type _value) {
        this.acctBalFull[i] = _value;
    }


    /**
     * Gets the acctBalOnDate value for this LoanInfo_Type.
     * 
     * @return acctBalOnDate
     */
    public com.rssl.phizicgate.esberibgate.ws.generated.AcctBal_Type[] getAcctBalOnDate() {
        return acctBalOnDate;
    }


    /**
     * Sets the acctBalOnDate value for this LoanInfo_Type.
     * 
     * @param acctBalOnDate
     */
    public void setAcctBalOnDate(com.rssl.phizicgate.esberibgate.ws.generated.AcctBal_Type[] acctBalOnDate) {
        this.acctBalOnDate = acctBalOnDate;
    }

    public com.rssl.phizicgate.esberibgate.ws.generated.AcctBal_Type getAcctBalOnDate(int i) {
        return this.acctBalOnDate[i];
    }

    public void setAcctBalOnDate(int i, com.rssl.phizicgate.esberibgate.ws.generated.AcctBal_Type _value) {
        this.acctBalOnDate[i] = _value;
    }


    /**
     * Gets the idSpacing value for this LoanInfo_Type.
     * 
     * @return idSpacing
     */
    public java.lang.String getIdSpacing() {
        return idSpacing;
    }


    /**
     * Sets the idSpacing value for this LoanInfo_Type.
     * 
     * @param idSpacing
     */
    public void setIdSpacing(java.lang.String idSpacing) {
        this.idSpacing = idSpacing;
    }


    /**
     * Gets the loanAcctId value for this LoanInfo_Type.
     * 
     * @return loanAcctId
     */
    public com.rssl.phizicgate.esberibgate.ws.generated.LoanAcctId_Type getLoanAcctId() {
        return loanAcctId;
    }


    /**
     * Sets the loanAcctId value for this LoanInfo_Type.
     * 
     * @param loanAcctId
     */
    public void setLoanAcctId(com.rssl.phizicgate.esberibgate.ws.generated.LoanAcctId_Type loanAcctId) {
        this.loanAcctId = loanAcctId;
    }


    /**
     * Gets the agreemtNum value for this LoanInfo_Type.
     * 
     * @return agreemtNum
     */
    public java.lang.String getAgreemtNum() {
        return agreemtNum;
    }


    /**
     * Sets the agreemtNum value for this LoanInfo_Type.
     * 
     * @param agreemtNum
     */
    public void setAgreemtNum(java.lang.String agreemtNum) {
        this.agreemtNum = agreemtNum;
    }


    /**
     * Gets the prodType value for this LoanInfo_Type.
     * 
     * @return prodType
     */
    public java.lang.String getProdType() {
        return prodType;
    }


    /**
     * Sets the prodType value for this LoanInfo_Type.
     * 
     * @param prodType
     */
    public void setProdType(java.lang.String prodType) {
        this.prodType = prodType;
    }


    /**
     * Gets the loanType value for this LoanInfo_Type.
     * 
     * @return loanType
     */
    public java.lang.String getLoanType() {
        return loanType;
    }


    /**
     * Sets the loanType value for this LoanInfo_Type.
     * 
     * @param loanType
     */
    public void setLoanType(java.lang.String loanType) {
        this.loanType = loanType;
    }


    /**
     * Gets the acctCur value for this LoanInfo_Type.
     * 
     * @return acctCur
     */
    public java.lang.String getAcctCur() {
        return acctCur;
    }


    /**
     * Sets the acctCur value for this LoanInfo_Type.
     * 
     * @param acctCur
     */
    public void setAcctCur(java.lang.String acctCur) {
        this.acctCur = acctCur;
    }


    /**
     * Gets the curAmt value for this LoanInfo_Type.
     * 
     * @return curAmt
     */
    public java.math.BigDecimal getCurAmt() {
        return curAmt;
    }


    /**
     * Sets the curAmt value for this LoanInfo_Type.
     * 
     * @param curAmt
     */
    public void setCurAmt(java.math.BigDecimal curAmt) {
        this.curAmt = curAmt;
    }


    /**
     * Gets the period value for this LoanInfo_Type.
     * 
     * @return period   * Срок договора в днях
     */
    public java.lang.Long getPeriod() {
        return period;
    }


    /**
     * Sets the period value for this LoanInfo_Type.
     * 
     * @param period   * Срок договора в днях
     */
    public void setPeriod(java.lang.Long period) {
        this.period = period;
    }


    /**
     * Gets the creditingRate value for this LoanInfo_Type.
     * 
     * @return creditingRate   * Процентная ставка по кредиту
     */
    public java.math.BigDecimal getCreditingRate() {
        return creditingRate;
    }


    /**
     * Sets the creditingRate value for this LoanInfo_Type.
     * 
     * @param creditingRate   * Процентная ставка по кредиту
     */
    public void setCreditingRate(java.math.BigDecimal creditingRate) {
        this.creditingRate = creditingRate;
    }


    /**
     * Gets the regNextPayDate value for this LoanInfo_Type.
     * 
     * @return regNextPayDate   * Предельная дата платежа
     */
    public java.lang.String getRegNextPayDate() {
        return regNextPayDate;
    }


    /**
     * Sets the regNextPayDate value for this LoanInfo_Type.
     * 
     * @param regNextPayDate   * Предельная дата платежа
     */
    public void setRegNextPayDate(java.lang.String regNextPayDate) {
        this.regNextPayDate = regNextPayDate;
    }


    /**
     * Gets the nextPayDate value for this LoanInfo_Type.
     * 
     * @return nextPayDate   * Предельная дата текущего периода
     */
    public java.lang.String getNextPayDate() {
        return nextPayDate;
    }


    /**
     * Sets the nextPayDate value for this LoanInfo_Type.
     * 
     * @param nextPayDate   * Предельная дата текущего периода
     */
    public void setNextPayDate(java.lang.String nextPayDate) {
        this.nextPayDate = nextPayDate;
    }


    /**
     * Gets the nextPaySum value for this LoanInfo_Type.
     * 
     * @return nextPaySum   * Предполагаемая сумма платежа
     */
    public java.math.BigDecimal getNextPaySum() {
        return nextPaySum;
    }


    /**
     * Sets the nextPaySum value for this LoanInfo_Type.
     * 
     * @param nextPaySum   * Предполагаемая сумма платежа
     */
    public void setNextPaySum(java.math.BigDecimal nextPaySum) {
        this.nextPaySum = nextPaySum;
    }


    /**
     * Gets the regPayAmount value for this LoanInfo_Type.
     * 
     * @return regPayAmount   * Размер аннуитетного платежа, поле обязательно, если признак
     * аннуитетности равен true
     */
    public java.math.BigDecimal getRegPayAmount() {
        return regPayAmount;
    }


    /**
     * Sets the regPayAmount value for this LoanInfo_Type.
     * 
     * @param regPayAmount   * Размер аннуитетного платежа, поле обязательно, если признак
     * аннуитетности равен true
     */
    public void setRegPayAmount(java.math.BigDecimal regPayAmount) {
        this.regPayAmount = regPayAmount;
    }


    /**
     * Gets the paymentStatus value for this LoanInfo_Type.
     * 
     * @return paymentStatus   * Статус оплаты, 1 – не просрочен, 2 – просрочен
     */
    public com.rssl.phizicgate.esberibgate.ws.generated.PaymentStatus_Type getPaymentStatus() {
        return paymentStatus;
    }


    /**
     * Sets the paymentStatus value for this LoanInfo_Type.
     * 
     * @param paymentStatus   * Статус оплаты, 1 – не просрочен, 2 – просрочен
     */
    public void setPaymentStatus(com.rssl.phizicgate.esberibgate.ws.generated.PaymentStatus_Type paymentStatus) {
        this.paymentStatus = paymentStatus;
    }


    /**
     * Gets the loanStatus value for this LoanInfo_Type.
     * 
     * @return loanStatus   * Статус кредита, 1 – открыт, 2 – закрыт
     */
    public java.lang.String getLoanStatus() {
        return loanStatus;
    }


    /**
     * Sets the loanStatus value for this LoanInfo_Type.
     * 
     * @param loanStatus   * Статус кредита, 1 – открыт, 2 – закрыт
     */
    public void setLoanStatus(java.lang.String loanStatus) {
        this.loanStatus = loanStatus;
    }


    /**
     * Gets the prevPayDate value for this LoanInfo_Type.
     * 
     * @return prevPayDate   * Дата предыдущего платежа
     */
    public java.lang.String getPrevPayDate() {
        return prevPayDate;
    }


    /**
     * Sets the prevPayDate value for this LoanInfo_Type.
     * 
     * @param prevPayDate   * Дата предыдущего платежа
     */
    public void setPrevPayDate(java.lang.String prevPayDate) {
        this.prevPayDate = prevPayDate;
    }


    /**
     * Gets the prevPaySum value for this LoanInfo_Type.
     * 
     * @return prevPaySum   * Сумма предыдущего платежа
     */
    public java.math.BigDecimal getPrevPaySum() {
        return prevPaySum;
    }


    /**
     * Sets the prevPaySum value for this LoanInfo_Type.
     * 
     * @param prevPaySum   * Сумма предыдущего платежа
     */
    public void setPrevPaySum(java.math.BigDecimal prevPaySum) {
        this.prevPaySum = prevPaySum;
    }


    /**
     * Gets the payCard value for this LoanInfo_Type.
     * 
     * @return payCard   * Карта оплаты
     */
    public java.lang.String getPayCard() {
        return payCard;
    }


    /**
     * Sets the payCard value for this LoanInfo_Type.
     * 
     * @param payCard   * Карта оплаты
     */
    public void setPayCard(java.lang.String payCard) {
        this.payCard = payCard;
    }


    /**
     * Gets the custRec value for this LoanInfo_Type.
     * 
     * @return custRec
     */
    public com.rssl.phizicgate.esberibgate.ws.generated.CustRec_Type[] getCustRec() {
        return custRec;
    }


    /**
     * Sets the custRec value for this LoanInfo_Type.
     * 
     * @param custRec
     */
    public void setCustRec(com.rssl.phizicgate.esberibgate.ws.generated.CustRec_Type[] custRec) {
        this.custRec = custRec;
    }

    public com.rssl.phizicgate.esberibgate.ws.generated.CustRec_Type getCustRec(int i) {
        return this.custRec[i];
    }

    public void setCustRec(int i, com.rssl.phizicgate.esberibgate.ws.generated.CustRec_Type _value) {
        this.custRec[i] = _value;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof LoanInfo_Type)) return false;
        LoanInfo_Type other = (LoanInfo_Type) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.acctBalFull==null && other.getAcctBalFull()==null) || 
             (this.acctBalFull!=null &&
              java.util.Arrays.equals(this.acctBalFull, other.getAcctBalFull()))) &&
            ((this.acctBalOnDate==null && other.getAcctBalOnDate()==null) || 
             (this.acctBalOnDate!=null &&
              java.util.Arrays.equals(this.acctBalOnDate, other.getAcctBalOnDate()))) &&
            ((this.idSpacing==null && other.getIdSpacing()==null) || 
             (this.idSpacing!=null &&
              this.idSpacing.equals(other.getIdSpacing()))) &&
            ((this.loanAcctId==null && other.getLoanAcctId()==null) || 
             (this.loanAcctId!=null &&
              this.loanAcctId.equals(other.getLoanAcctId()))) &&
            ((this.agreemtNum==null && other.getAgreemtNum()==null) || 
             (this.agreemtNum!=null &&
              this.agreemtNum.equals(other.getAgreemtNum()))) &&
            ((this.prodType==null && other.getProdType()==null) || 
             (this.prodType!=null &&
              this.prodType.equals(other.getProdType()))) &&
            ((this.loanType==null && other.getLoanType()==null) || 
             (this.loanType!=null &&
              this.loanType.equals(other.getLoanType()))) &&
            ((this.acctCur==null && other.getAcctCur()==null) || 
             (this.acctCur!=null &&
              this.acctCur.equals(other.getAcctCur()))) &&
            ((this.curAmt==null && other.getCurAmt()==null) || 
             (this.curAmt!=null &&
              this.curAmt.equals(other.getCurAmt()))) &&
            ((this.period==null && other.getPeriod()==null) || 
             (this.period!=null &&
              this.period.equals(other.getPeriod()))) &&
            ((this.creditingRate==null && other.getCreditingRate()==null) || 
             (this.creditingRate!=null &&
              this.creditingRate.equals(other.getCreditingRate()))) &&
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
              this.prevPaySum.equals(other.getPrevPaySum()))) &&
            ((this.payCard==null && other.getPayCard()==null) || 
             (this.payCard!=null &&
              this.payCard.equals(other.getPayCard()))) &&
            ((this.custRec==null && other.getCustRec()==null) || 
             (this.custRec!=null &&
              java.util.Arrays.equals(this.custRec, other.getCustRec())));
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
        if (getAcctBalFull() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getAcctBalFull());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getAcctBalFull(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getAcctBalOnDate() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getAcctBalOnDate());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getAcctBalOnDate(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getIdSpacing() != null) {
            _hashCode += getIdSpacing().hashCode();
        }
        if (getLoanAcctId() != null) {
            _hashCode += getLoanAcctId().hashCode();
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
        if (getAcctCur() != null) {
            _hashCode += getAcctCur().hashCode();
        }
        if (getCurAmt() != null) {
            _hashCode += getCurAmt().hashCode();
        }
        if (getPeriod() != null) {
            _hashCode += getPeriod().hashCode();
        }
        if (getCreditingRate() != null) {
            _hashCode += getCreditingRate().hashCode();
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
        if (getPayCard() != null) {
            _hashCode += getPayCard().hashCode();
        }
        if (getCustRec() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getCustRec());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getCustRec(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(LoanInfo_Type.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "LoanInfo_Type"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("acctBalFull");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "AcctBalFull"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "AcctBal_Type"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("acctBalOnDate");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "AcctBalOnDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "AcctBal_Type"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idSpacing");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "IdSpacing"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("loanAcctId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "LoanAcctId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "LoanAcctId_Type"));
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
        elemField.setFieldName("acctCur");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "AcctCur"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "AcctCur_Type"));
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
        elemField.setFieldName("period");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Period"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("creditingRate");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "CreditingRate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("regNextPayDate");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "RegNextPayDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("nextPayDate");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "NextPayDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
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
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("prevPayDate");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "PrevPayDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
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
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("payCard");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "PayCard"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "String"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("custRec");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "CustRec"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "CustRec"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        elemField.setMaxOccursUnbounded(true);
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
