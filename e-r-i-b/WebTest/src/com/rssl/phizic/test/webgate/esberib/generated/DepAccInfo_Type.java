/**
 * DepAccInfo_Type.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.test.webgate.esberib.generated;

public class DepAccInfo_Type  implements java.io.Serializable {
    private java.lang.String acctCur;

    private java.lang.String acctName;

    /* Тип счета */
    private java.lang.Long acctCode;

    /* Подтип счета */
    private java.lang.Long acctSubCode;

    private java.math.BigDecimal curAmt;

    /* Максимальная сумма списания */
    private java.math.BigDecimal maxSumWrite;

    private java.math.BigDecimal irreducibleAmt;

    /* Сумма остатка по вкладу без капитализации */
    private java.math.BigDecimal clearBalance;

    /* Максимальная сумма вклада */
    private java.math.BigDecimal maxBalance;

    /* Информация, предназначенная для перечисления процентов */
    private com.rssl.phizic.test.webgate.esberib.generated.InterestOnDeposit_Type interestOnDeposit;

    /* Срок вклада в днях */
    private java.lang.Long period;

    /* Процентная ставка по вкладу, для текущих счетов не возвращается. */
    private java.math.BigDecimal rate;

    /* Тип клиента */
    private java.lang.String clientKind;

    /* Дата открытия счета */
    private java.lang.String openDate;

    /* Дата закрытия счета */
    private java.lang.String endDate;

    /* Статус счета */
    private com.rssl.phizic.test.webgate.esberib.generated.AccountStatusEnum_Type status;

    /* Разрешено ли списание со счета */
    private java.lang.Boolean isCreditAllowed;

    /* Разрешено ли зачисление на счет */
    private java.lang.Boolean isDebitAllowed;

    /* Разрешена ли пролонгация на следующий срок */
    private java.lang.Boolean isProlongationAllowed;

    /* Разрешено ли списание со счета в других ОСБ ( признак «Зеленая
     * улица») */
    private java.lang.Boolean isCreditCrossAgencyAllowed;

    /* Разрешено ли зачисление на счет в других ОСБ (признак «Зеленая
     * улица») */
    private java.lang.Boolean isDebitCrossAgencyAllowed;

    /* Признак наличия сберкнижки. True – есть, False – нет. */
    private java.lang.Boolean isPassBook;

    /* Дата пролонгации вклада */
    private java.lang.String prolongationDate;

    private com.rssl.phizic.test.webgate.esberib.generated.BankInfo_Type bankInfo;

    private com.rssl.phizic.test.webgate.esberib.generated.CustRec_Type custRec;

    public DepAccInfo_Type() {
    }

    public DepAccInfo_Type(
           java.lang.String acctCur,
           java.lang.String acctName,
           java.lang.Long acctCode,
           java.lang.Long acctSubCode,
           java.math.BigDecimal curAmt,
           java.math.BigDecimal maxSumWrite,
           java.math.BigDecimal irreducibleAmt,
           java.math.BigDecimal clearBalance,
           java.math.BigDecimal maxBalance,
           com.rssl.phizic.test.webgate.esberib.generated.InterestOnDeposit_Type interestOnDeposit,
           java.lang.Long period,
           java.math.BigDecimal rate,
           java.lang.String clientKind,
           java.lang.String openDate,
           java.lang.String endDate,
           com.rssl.phizic.test.webgate.esberib.generated.AccountStatusEnum_Type status,
           java.lang.Boolean isCreditAllowed,
           java.lang.Boolean isDebitAllowed,
           java.lang.Boolean isProlongationAllowed,
           java.lang.Boolean isCreditCrossAgencyAllowed,
           java.lang.Boolean isDebitCrossAgencyAllowed,
           java.lang.Boolean isPassBook,
           java.lang.String prolongationDate,
           com.rssl.phizic.test.webgate.esberib.generated.BankInfo_Type bankInfo,
           com.rssl.phizic.test.webgate.esberib.generated.CustRec_Type custRec) {
           this.acctCur = acctCur;
           this.acctName = acctName;
           this.acctCode = acctCode;
           this.acctSubCode = acctSubCode;
           this.curAmt = curAmt;
           this.maxSumWrite = maxSumWrite;
           this.irreducibleAmt = irreducibleAmt;
           this.clearBalance = clearBalance;
           this.maxBalance = maxBalance;
           this.interestOnDeposit = interestOnDeposit;
           this.period = period;
           this.rate = rate;
           this.clientKind = clientKind;
           this.openDate = openDate;
           this.endDate = endDate;
           this.status = status;
           this.isCreditAllowed = isCreditAllowed;
           this.isDebitAllowed = isDebitAllowed;
           this.isProlongationAllowed = isProlongationAllowed;
           this.isCreditCrossAgencyAllowed = isCreditCrossAgencyAllowed;
           this.isDebitCrossAgencyAllowed = isDebitCrossAgencyAllowed;
           this.isPassBook = isPassBook;
           this.prolongationDate = prolongationDate;
           this.bankInfo = bankInfo;
           this.custRec = custRec;
    }


    /**
     * Gets the acctCur value for this DepAccInfo_Type.
     * 
     * @return acctCur
     */
    public java.lang.String getAcctCur() {
        return acctCur;
    }


    /**
     * Sets the acctCur value for this DepAccInfo_Type.
     * 
     * @param acctCur
     */
    public void setAcctCur(java.lang.String acctCur) {
        this.acctCur = acctCur;
    }


    /**
     * Gets the acctName value for this DepAccInfo_Type.
     * 
     * @return acctName
     */
    public java.lang.String getAcctName() {
        return acctName;
    }


    /**
     * Sets the acctName value for this DepAccInfo_Type.
     * 
     * @param acctName
     */
    public void setAcctName(java.lang.String acctName) {
        this.acctName = acctName;
    }


    /**
     * Gets the acctCode value for this DepAccInfo_Type.
     * 
     * @return acctCode   * Тип счета
     */
    public java.lang.Long getAcctCode() {
        return acctCode;
    }


    /**
     * Sets the acctCode value for this DepAccInfo_Type.
     * 
     * @param acctCode   * Тип счета
     */
    public void setAcctCode(java.lang.Long acctCode) {
        this.acctCode = acctCode;
    }


    /**
     * Gets the acctSubCode value for this DepAccInfo_Type.
     * 
     * @return acctSubCode   * Подтип счета
     */
    public java.lang.Long getAcctSubCode() {
        return acctSubCode;
    }


    /**
     * Sets the acctSubCode value for this DepAccInfo_Type.
     * 
     * @param acctSubCode   * Подтип счета
     */
    public void setAcctSubCode(java.lang.Long acctSubCode) {
        this.acctSubCode = acctSubCode;
    }


    /**
     * Gets the curAmt value for this DepAccInfo_Type.
     * 
     * @return curAmt
     */
    public java.math.BigDecimal getCurAmt() {
        return curAmt;
    }


    /**
     * Sets the curAmt value for this DepAccInfo_Type.
     * 
     * @param curAmt
     */
    public void setCurAmt(java.math.BigDecimal curAmt) {
        this.curAmt = curAmt;
    }


    /**
     * Gets the maxSumWrite value for this DepAccInfo_Type.
     * 
     * @return maxSumWrite   * Максимальная сумма списания
     */
    public java.math.BigDecimal getMaxSumWrite() {
        return maxSumWrite;
    }


    /**
     * Sets the maxSumWrite value for this DepAccInfo_Type.
     * 
     * @param maxSumWrite   * Максимальная сумма списания
     */
    public void setMaxSumWrite(java.math.BigDecimal maxSumWrite) {
        this.maxSumWrite = maxSumWrite;
    }


    /**
     * Gets the irreducibleAmt value for this DepAccInfo_Type.
     * 
     * @return irreducibleAmt
     */
    public java.math.BigDecimal getIrreducibleAmt() {
        return irreducibleAmt;
    }


    /**
     * Sets the irreducibleAmt value for this DepAccInfo_Type.
     * 
     * @param irreducibleAmt
     */
    public void setIrreducibleAmt(java.math.BigDecimal irreducibleAmt) {
        this.irreducibleAmt = irreducibleAmt;
    }


    /**
     * Gets the clearBalance value for this DepAccInfo_Type.
     * 
     * @return clearBalance   * Сумма остатка по вкладу без капитализации
     */
    public java.math.BigDecimal getClearBalance() {
        return clearBalance;
    }


    /**
     * Sets the clearBalance value for this DepAccInfo_Type.
     * 
     * @param clearBalance   * Сумма остатка по вкладу без капитализации
     */
    public void setClearBalance(java.math.BigDecimal clearBalance) {
        this.clearBalance = clearBalance;
    }


    /**
     * Gets the maxBalance value for this DepAccInfo_Type.
     * 
     * @return maxBalance   * Максимальная сумма вклада
     */
    public java.math.BigDecimal getMaxBalance() {
        return maxBalance;
    }


    /**
     * Sets the maxBalance value for this DepAccInfo_Type.
     * 
     * @param maxBalance   * Максимальная сумма вклада
     */
    public void setMaxBalance(java.math.BigDecimal maxBalance) {
        this.maxBalance = maxBalance;
    }


    /**
     * Gets the interestOnDeposit value for this DepAccInfo_Type.
     * 
     * @return interestOnDeposit   * Информация, предназначенная для перечисления процентов
     */
    public com.rssl.phizic.test.webgate.esberib.generated.InterestOnDeposit_Type getInterestOnDeposit() {
        return interestOnDeposit;
    }


    /**
     * Sets the interestOnDeposit value for this DepAccInfo_Type.
     * 
     * @param interestOnDeposit   * Информация, предназначенная для перечисления процентов
     */
    public void setInterestOnDeposit(com.rssl.phizic.test.webgate.esberib.generated.InterestOnDeposit_Type interestOnDeposit) {
        this.interestOnDeposit = interestOnDeposit;
    }


    /**
     * Gets the period value for this DepAccInfo_Type.
     * 
     * @return period   * Срок вклада в днях
     */
    public java.lang.Long getPeriod() {
        return period;
    }


    /**
     * Sets the period value for this DepAccInfo_Type.
     * 
     * @param period   * Срок вклада в днях
     */
    public void setPeriod(java.lang.Long period) {
        this.period = period;
    }


    /**
     * Gets the rate value for this DepAccInfo_Type.
     * 
     * @return rate   * Процентная ставка по вкладу, для текущих счетов не возвращается.
     */
    public java.math.BigDecimal getRate() {
        return rate;
    }


    /**
     * Sets the rate value for this DepAccInfo_Type.
     * 
     * @param rate   * Процентная ставка по вкладу, для текущих счетов не возвращается.
     */
    public void setRate(java.math.BigDecimal rate) {
        this.rate = rate;
    }


    /**
     * Gets the clientKind value for this DepAccInfo_Type.
     * 
     * @return clientKind   * Тип клиента
     */
    public java.lang.String getClientKind() {
        return clientKind;
    }


    /**
     * Sets the clientKind value for this DepAccInfo_Type.
     * 
     * @param clientKind   * Тип клиента
     */
    public void setClientKind(java.lang.String clientKind) {
        this.clientKind = clientKind;
    }


    /**
     * Gets the openDate value for this DepAccInfo_Type.
     * 
     * @return openDate   * Дата открытия счета
     */
    public java.lang.String getOpenDate() {
        return openDate;
    }


    /**
     * Sets the openDate value for this DepAccInfo_Type.
     * 
     * @param openDate   * Дата открытия счета
     */
    public void setOpenDate(java.lang.String openDate) {
        this.openDate = openDate;
    }


    /**
     * Gets the endDate value for this DepAccInfo_Type.
     * 
     * @return endDate   * Дата закрытия счета
     */
    public java.lang.String getEndDate() {
        return endDate;
    }


    /**
     * Sets the endDate value for this DepAccInfo_Type.
     * 
     * @param endDate   * Дата закрытия счета
     */
    public void setEndDate(java.lang.String endDate) {
        this.endDate = endDate;
    }


    /**
     * Gets the status value for this DepAccInfo_Type.
     * 
     * @return status   * Статус счета
     */
    public com.rssl.phizic.test.webgate.esberib.generated.AccountStatusEnum_Type getStatus() {
        return status;
    }


    /**
     * Sets the status value for this DepAccInfo_Type.
     * 
     * @param status   * Статус счета
     */
    public void setStatus(com.rssl.phizic.test.webgate.esberib.generated.AccountStatusEnum_Type status) {
        this.status = status;
    }


    /**
     * Gets the isCreditAllowed value for this DepAccInfo_Type.
     * 
     * @return isCreditAllowed   * Разрешено ли списание со счета
     */
    public java.lang.Boolean getIsCreditAllowed() {
        return isCreditAllowed;
    }


    /**
     * Sets the isCreditAllowed value for this DepAccInfo_Type.
     * 
     * @param isCreditAllowed   * Разрешено ли списание со счета
     */
    public void setIsCreditAllowed(java.lang.Boolean isCreditAllowed) {
        this.isCreditAllowed = isCreditAllowed;
    }


    /**
     * Gets the isDebitAllowed value for this DepAccInfo_Type.
     * 
     * @return isDebitAllowed   * Разрешено ли зачисление на счет
     */
    public java.lang.Boolean getIsDebitAllowed() {
        return isDebitAllowed;
    }


    /**
     * Sets the isDebitAllowed value for this DepAccInfo_Type.
     * 
     * @param isDebitAllowed   * Разрешено ли зачисление на счет
     */
    public void setIsDebitAllowed(java.lang.Boolean isDebitAllowed) {
        this.isDebitAllowed = isDebitAllowed;
    }


    /**
     * Gets the isProlongationAllowed value for this DepAccInfo_Type.
     * 
     * @return isProlongationAllowed   * Разрешена ли пролонгация на следующий срок
     */
    public java.lang.Boolean getIsProlongationAllowed() {
        return isProlongationAllowed;
    }


    /**
     * Sets the isProlongationAllowed value for this DepAccInfo_Type.
     * 
     * @param isProlongationAllowed   * Разрешена ли пролонгация на следующий срок
     */
    public void setIsProlongationAllowed(java.lang.Boolean isProlongationAllowed) {
        this.isProlongationAllowed = isProlongationAllowed;
    }


    /**
     * Gets the isCreditCrossAgencyAllowed value for this DepAccInfo_Type.
     * 
     * @return isCreditCrossAgencyAllowed   * Разрешено ли списание со счета в других ОСБ ( признак «Зеленая
     * улица»)
     */
    public java.lang.Boolean getIsCreditCrossAgencyAllowed() {
        return isCreditCrossAgencyAllowed;
    }


    /**
     * Sets the isCreditCrossAgencyAllowed value for this DepAccInfo_Type.
     * 
     * @param isCreditCrossAgencyAllowed   * Разрешено ли списание со счета в других ОСБ ( признак «Зеленая
     * улица»)
     */
    public void setIsCreditCrossAgencyAllowed(java.lang.Boolean isCreditCrossAgencyAllowed) {
        this.isCreditCrossAgencyAllowed = isCreditCrossAgencyAllowed;
    }


    /**
     * Gets the isDebitCrossAgencyAllowed value for this DepAccInfo_Type.
     * 
     * @return isDebitCrossAgencyAllowed   * Разрешено ли зачисление на счет в других ОСБ (признак «Зеленая
     * улица»)
     */
    public java.lang.Boolean getIsDebitCrossAgencyAllowed() {
        return isDebitCrossAgencyAllowed;
    }


    /**
     * Sets the isDebitCrossAgencyAllowed value for this DepAccInfo_Type.
     * 
     * @param isDebitCrossAgencyAllowed   * Разрешено ли зачисление на счет в других ОСБ (признак «Зеленая
     * улица»)
     */
    public void setIsDebitCrossAgencyAllowed(java.lang.Boolean isDebitCrossAgencyAllowed) {
        this.isDebitCrossAgencyAllowed = isDebitCrossAgencyAllowed;
    }


    /**
     * Gets the isPassBook value for this DepAccInfo_Type.
     * 
     * @return isPassBook   * Признак наличия сберкнижки. True – есть, False – нет.
     */
    public java.lang.Boolean getIsPassBook() {
        return isPassBook;
    }


    /**
     * Sets the isPassBook value for this DepAccInfo_Type.
     * 
     * @param isPassBook   * Признак наличия сберкнижки. True – есть, False – нет.
     */
    public void setIsPassBook(java.lang.Boolean isPassBook) {
        this.isPassBook = isPassBook;
    }


    /**
     * Gets the prolongationDate value for this DepAccInfo_Type.
     * 
     * @return prolongationDate   * Дата пролонгации вклада
     */
    public java.lang.String getProlongationDate() {
        return prolongationDate;
    }


    /**
     * Sets the prolongationDate value for this DepAccInfo_Type.
     * 
     * @param prolongationDate   * Дата пролонгации вклада
     */
    public void setProlongationDate(java.lang.String prolongationDate) {
        this.prolongationDate = prolongationDate;
    }


    /**
     * Gets the bankInfo value for this DepAccInfo_Type.
     * 
     * @return bankInfo
     */
    public com.rssl.phizic.test.webgate.esberib.generated.BankInfo_Type getBankInfo() {
        return bankInfo;
    }


    /**
     * Sets the bankInfo value for this DepAccInfo_Type.
     * 
     * @param bankInfo
     */
    public void setBankInfo(com.rssl.phizic.test.webgate.esberib.generated.BankInfo_Type bankInfo) {
        this.bankInfo = bankInfo;
    }


    /**
     * Gets the custRec value for this DepAccInfo_Type.
     * 
     * @return custRec
     */
    public com.rssl.phizic.test.webgate.esberib.generated.CustRec_Type getCustRec() {
        return custRec;
    }


    /**
     * Sets the custRec value for this DepAccInfo_Type.
     * 
     * @param custRec
     */
    public void setCustRec(com.rssl.phizic.test.webgate.esberib.generated.CustRec_Type custRec) {
        this.custRec = custRec;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof DepAccInfo_Type)) return false;
        DepAccInfo_Type other = (DepAccInfo_Type) obj;
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
            ((this.acctName==null && other.getAcctName()==null) || 
             (this.acctName!=null &&
              this.acctName.equals(other.getAcctName()))) &&
            ((this.acctCode==null && other.getAcctCode()==null) || 
             (this.acctCode!=null &&
              this.acctCode.equals(other.getAcctCode()))) &&
            ((this.acctSubCode==null && other.getAcctSubCode()==null) || 
             (this.acctSubCode!=null &&
              this.acctSubCode.equals(other.getAcctSubCode()))) &&
            ((this.curAmt==null && other.getCurAmt()==null) || 
             (this.curAmt!=null &&
              this.curAmt.equals(other.getCurAmt()))) &&
            ((this.maxSumWrite==null && other.getMaxSumWrite()==null) || 
             (this.maxSumWrite!=null &&
              this.maxSumWrite.equals(other.getMaxSumWrite()))) &&
            ((this.irreducibleAmt==null && other.getIrreducibleAmt()==null) || 
             (this.irreducibleAmt!=null &&
              this.irreducibleAmt.equals(other.getIrreducibleAmt()))) &&
            ((this.clearBalance==null && other.getClearBalance()==null) || 
             (this.clearBalance!=null &&
              this.clearBalance.equals(other.getClearBalance()))) &&
            ((this.maxBalance==null && other.getMaxBalance()==null) || 
             (this.maxBalance!=null &&
              this.maxBalance.equals(other.getMaxBalance()))) &&
            ((this.interestOnDeposit==null && other.getInterestOnDeposit()==null) || 
             (this.interestOnDeposit!=null &&
              this.interestOnDeposit.equals(other.getInterestOnDeposit()))) &&
            ((this.period==null && other.getPeriod()==null) || 
             (this.period!=null &&
              this.period.equals(other.getPeriod()))) &&
            ((this.rate==null && other.getRate()==null) || 
             (this.rate!=null &&
              this.rate.equals(other.getRate()))) &&
            ((this.clientKind==null && other.getClientKind()==null) || 
             (this.clientKind!=null &&
              this.clientKind.equals(other.getClientKind()))) &&
            ((this.openDate==null && other.getOpenDate()==null) || 
             (this.openDate!=null &&
              this.openDate.equals(other.getOpenDate()))) &&
            ((this.endDate==null && other.getEndDate()==null) || 
             (this.endDate!=null &&
              this.endDate.equals(other.getEndDate()))) &&
            ((this.status==null && other.getStatus()==null) || 
             (this.status!=null &&
              this.status.equals(other.getStatus()))) &&
            ((this.isCreditAllowed==null && other.getIsCreditAllowed()==null) || 
             (this.isCreditAllowed!=null &&
              this.isCreditAllowed.equals(other.getIsCreditAllowed()))) &&
            ((this.isDebitAllowed==null && other.getIsDebitAllowed()==null) || 
             (this.isDebitAllowed!=null &&
              this.isDebitAllowed.equals(other.getIsDebitAllowed()))) &&
            ((this.isProlongationAllowed==null && other.getIsProlongationAllowed()==null) || 
             (this.isProlongationAllowed!=null &&
              this.isProlongationAllowed.equals(other.getIsProlongationAllowed()))) &&
            ((this.isCreditCrossAgencyAllowed==null && other.getIsCreditCrossAgencyAllowed()==null) || 
             (this.isCreditCrossAgencyAllowed!=null &&
              this.isCreditCrossAgencyAllowed.equals(other.getIsCreditCrossAgencyAllowed()))) &&
            ((this.isDebitCrossAgencyAllowed==null && other.getIsDebitCrossAgencyAllowed()==null) || 
             (this.isDebitCrossAgencyAllowed!=null &&
              this.isDebitCrossAgencyAllowed.equals(other.getIsDebitCrossAgencyAllowed()))) &&
            ((this.isPassBook==null && other.getIsPassBook()==null) || 
             (this.isPassBook!=null &&
              this.isPassBook.equals(other.getIsPassBook()))) &&
            ((this.prolongationDate==null && other.getProlongationDate()==null) || 
             (this.prolongationDate!=null &&
              this.prolongationDate.equals(other.getProlongationDate()))) &&
            ((this.bankInfo==null && other.getBankInfo()==null) || 
             (this.bankInfo!=null &&
              this.bankInfo.equals(other.getBankInfo()))) &&
            ((this.custRec==null && other.getCustRec()==null) || 
             (this.custRec!=null &&
              this.custRec.equals(other.getCustRec())));
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
        if (getAcctName() != null) {
            _hashCode += getAcctName().hashCode();
        }
        if (getAcctCode() != null) {
            _hashCode += getAcctCode().hashCode();
        }
        if (getAcctSubCode() != null) {
            _hashCode += getAcctSubCode().hashCode();
        }
        if (getCurAmt() != null) {
            _hashCode += getCurAmt().hashCode();
        }
        if (getMaxSumWrite() != null) {
            _hashCode += getMaxSumWrite().hashCode();
        }
        if (getIrreducibleAmt() != null) {
            _hashCode += getIrreducibleAmt().hashCode();
        }
        if (getClearBalance() != null) {
            _hashCode += getClearBalance().hashCode();
        }
        if (getMaxBalance() != null) {
            _hashCode += getMaxBalance().hashCode();
        }
        if (getInterestOnDeposit() != null) {
            _hashCode += getInterestOnDeposit().hashCode();
        }
        if (getPeriod() != null) {
            _hashCode += getPeriod().hashCode();
        }
        if (getRate() != null) {
            _hashCode += getRate().hashCode();
        }
        if (getClientKind() != null) {
            _hashCode += getClientKind().hashCode();
        }
        if (getOpenDate() != null) {
            _hashCode += getOpenDate().hashCode();
        }
        if (getEndDate() != null) {
            _hashCode += getEndDate().hashCode();
        }
        if (getStatus() != null) {
            _hashCode += getStatus().hashCode();
        }
        if (getIsCreditAllowed() != null) {
            _hashCode += getIsCreditAllowed().hashCode();
        }
        if (getIsDebitAllowed() != null) {
            _hashCode += getIsDebitAllowed().hashCode();
        }
        if (getIsProlongationAllowed() != null) {
            _hashCode += getIsProlongationAllowed().hashCode();
        }
        if (getIsCreditCrossAgencyAllowed() != null) {
            _hashCode += getIsCreditCrossAgencyAllowed().hashCode();
        }
        if (getIsDebitCrossAgencyAllowed() != null) {
            _hashCode += getIsDebitCrossAgencyAllowed().hashCode();
        }
        if (getIsPassBook() != null) {
            _hashCode += getIsPassBook().hashCode();
        }
        if (getProlongationDate() != null) {
            _hashCode += getProlongationDate().hashCode();
        }
        if (getBankInfo() != null) {
            _hashCode += getBankInfo().hashCode();
        }
        if (getCustRec() != null) {
            _hashCode += getCustRec().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(DepAccInfo_Type.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "DepAccInfo_Type"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("acctCur");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "AcctCur"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "AcctCur_Type"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("acctName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "AcctName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("acctCode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "AcctCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("acctSubCode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "AcctSubCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
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
        elemField.setFieldName("maxSumWrite");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "MaxSumWrite"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("irreducibleAmt");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "IrreducibleAmt"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Decimal"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("clearBalance");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "ClearBalance"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("maxBalance");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "MaxBalance"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("interestOnDeposit");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "InterestOnDeposit"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "InterestOnDeposit_Type"));
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
        elemField.setFieldName("rate");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Rate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("clientKind");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "ClientKind"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("openDate");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "OpenDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("endDate");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "EndDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("status");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Status"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "AccountStatusEnum_Type"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("isCreditAllowed");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "IsCreditAllowed"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("isDebitAllowed");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "IsDebitAllowed"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("isProlongationAllowed");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "IsProlongationAllowed"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("isCreditCrossAgencyAllowed");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "IsCreditCrossAgencyAllowed"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("isDebitCrossAgencyAllowed");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "IsDebitCrossAgencyAllowed"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("isPassBook");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "IsPassBook"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("prolongationDate");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "ProlongationDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
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
        elemField.setFieldName("custRec");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "CustRec"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "CustRec_Type"));
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
