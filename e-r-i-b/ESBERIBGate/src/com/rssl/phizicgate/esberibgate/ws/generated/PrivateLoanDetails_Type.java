/**
 * PrivateLoanDetails_Type.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizicgate.esberibgate.ws.generated;

public class PrivateLoanDetails_Type  implements java.io.Serializable {
    /* идентификатор кредитного договора в ЕКП */
    private java.lang.String prodId;

    private java.lang.String loanType;

    /* Срок договора в месяцах */
    private long period;

    /* Процентная ставка по кредиту */
    private java.math.BigDecimal creditingRate;

    /* Статус кредита,
     * 1 – открыт
     * 2 – закрыт */
    private long loanStatus;

    private java.math.BigDecimal principalBalance;

    private java.math.BigDecimal fullRepaymentAmount;

    /* Признак просрочки ближайшего платежа:
     * 1 – просрочен
     * 0 или отсутствие поля – не просрочен */
    private java.lang.Long overdue;

    private com.rssl.phizicgate.esberibgate.ws.generated.PrivateLoanDetails_TypeAccounts accounts;

    private java.math.BigInteger BAKS;

    /* Признак автоматической выдачи
     * 0 – ручная выдача
     * 2 – автоматическая выдача */
    private java.math.BigInteger autoGrantion;

    /* Номер заявки на получение КД */
    private java.lang.String applicationNumberCA;

    private com.rssl.phizicgate.esberibgate.ws.generated.CustRec_Type[] custRec;

    private java.lang.String agencyAddress;

    private com.rssl.phizicgate.esberibgate.ws.generated.EarlyRepayment_Type[] earlyRepayment;

    private com.rssl.phizicgate.esberibgate.ws.generated.AcctBal_Type[] acctBalOnDate;

    public PrivateLoanDetails_Type() {
    }

    public PrivateLoanDetails_Type(
           java.lang.String prodId,
           java.lang.String loanType,
           long period,
           java.math.BigDecimal creditingRate,
           long loanStatus,
           java.math.BigDecimal principalBalance,
           java.math.BigDecimal fullRepaymentAmount,
           java.lang.Long overdue,
           com.rssl.phizicgate.esberibgate.ws.generated.PrivateLoanDetails_TypeAccounts accounts,
           java.math.BigInteger BAKS,
           java.math.BigInteger autoGrantion,
           java.lang.String applicationNumberCA,
           com.rssl.phizicgate.esberibgate.ws.generated.CustRec_Type[] custRec,
           java.lang.String agencyAddress,
           com.rssl.phizicgate.esberibgate.ws.generated.EarlyRepayment_Type[] earlyRepayment,
           com.rssl.phizicgate.esberibgate.ws.generated.AcctBal_Type[] acctBalOnDate) {
           this.prodId = prodId;
           this.loanType = loanType;
           this.period = period;
           this.creditingRate = creditingRate;
           this.loanStatus = loanStatus;
           this.principalBalance = principalBalance;
           this.fullRepaymentAmount = fullRepaymentAmount;
           this.overdue = overdue;
           this.accounts = accounts;
           this.BAKS = BAKS;
           this.autoGrantion = autoGrantion;
           this.applicationNumberCA = applicationNumberCA;
           this.custRec = custRec;
           this.agencyAddress = agencyAddress;
           this.earlyRepayment = earlyRepayment;
           this.acctBalOnDate = acctBalOnDate;
    }


    /**
     * Gets the prodId value for this PrivateLoanDetails_Type.
     * 
     * @return prodId   * идентификатор кредитного договора в ЕКП
     */
    public java.lang.String getProdId() {
        return prodId;
    }


    /**
     * Sets the prodId value for this PrivateLoanDetails_Type.
     * 
     * @param prodId   * идентификатор кредитного договора в ЕКП
     */
    public void setProdId(java.lang.String prodId) {
        this.prodId = prodId;
    }


    /**
     * Gets the loanType value for this PrivateLoanDetails_Type.
     * 
     * @return loanType
     */
    public java.lang.String getLoanType() {
        return loanType;
    }


    /**
     * Sets the loanType value for this PrivateLoanDetails_Type.
     * 
     * @param loanType
     */
    public void setLoanType(java.lang.String loanType) {
        this.loanType = loanType;
    }


    /**
     * Gets the period value for this PrivateLoanDetails_Type.
     * 
     * @return period   * Срок договора в месяцах
     */
    public long getPeriod() {
        return period;
    }


    /**
     * Sets the period value for this PrivateLoanDetails_Type.
     * 
     * @param period   * Срок договора в месяцах
     */
    public void setPeriod(long period) {
        this.period = period;
    }


    /**
     * Gets the creditingRate value for this PrivateLoanDetails_Type.
     * 
     * @return creditingRate   * Процентная ставка по кредиту
     */
    public java.math.BigDecimal getCreditingRate() {
        return creditingRate;
    }


    /**
     * Sets the creditingRate value for this PrivateLoanDetails_Type.
     * 
     * @param creditingRate   * Процентная ставка по кредиту
     */
    public void setCreditingRate(java.math.BigDecimal creditingRate) {
        this.creditingRate = creditingRate;
    }


    /**
     * Gets the loanStatus value for this PrivateLoanDetails_Type.
     * 
     * @return loanStatus   * Статус кредита,
     * 1 – открыт
     * 2 – закрыт
     */
    public long getLoanStatus() {
        return loanStatus;
    }


    /**
     * Sets the loanStatus value for this PrivateLoanDetails_Type.
     * 
     * @param loanStatus   * Статус кредита,
     * 1 – открыт
     * 2 – закрыт
     */
    public void setLoanStatus(long loanStatus) {
        this.loanStatus = loanStatus;
    }


    /**
     * Gets the principalBalance value for this PrivateLoanDetails_Type.
     * 
     * @return principalBalance
     */
    public java.math.BigDecimal getPrincipalBalance() {
        return principalBalance;
    }


    /**
     * Sets the principalBalance value for this PrivateLoanDetails_Type.
     * 
     * @param principalBalance
     */
    public void setPrincipalBalance(java.math.BigDecimal principalBalance) {
        this.principalBalance = principalBalance;
    }


    /**
     * Gets the fullRepaymentAmount value for this PrivateLoanDetails_Type.
     * 
     * @return fullRepaymentAmount
     */
    public java.math.BigDecimal getFullRepaymentAmount() {
        return fullRepaymentAmount;
    }


    /**
     * Sets the fullRepaymentAmount value for this PrivateLoanDetails_Type.
     * 
     * @param fullRepaymentAmount
     */
    public void setFullRepaymentAmount(java.math.BigDecimal fullRepaymentAmount) {
        this.fullRepaymentAmount = fullRepaymentAmount;
    }


    /**
     * Gets the overdue value for this PrivateLoanDetails_Type.
     * 
     * @return overdue   * Признак просрочки ближайшего платежа:
     * 1 – просрочен
     * 0 или отсутствие поля – не просрочен
     */
    public java.lang.Long getOverdue() {
        return overdue;
    }


    /**
     * Sets the overdue value for this PrivateLoanDetails_Type.
     * 
     * @param overdue   * Признак просрочки ближайшего платежа:
     * 1 – просрочен
     * 0 или отсутствие поля – не просрочен
     */
    public void setOverdue(java.lang.Long overdue) {
        this.overdue = overdue;
    }


    /**
     * Gets the accounts value for this PrivateLoanDetails_Type.
     * 
     * @return accounts
     */
    public com.rssl.phizicgate.esberibgate.ws.generated.PrivateLoanDetails_TypeAccounts getAccounts() {
        return accounts;
    }


    /**
     * Sets the accounts value for this PrivateLoanDetails_Type.
     * 
     * @param accounts
     */
    public void setAccounts(com.rssl.phizicgate.esberibgate.ws.generated.PrivateLoanDetails_TypeAccounts accounts) {
        this.accounts = accounts;
    }


    /**
     * Gets the BAKS value for this PrivateLoanDetails_Type.
     * 
     * @return BAKS
     */
    public java.math.BigInteger getBAKS() {
        return BAKS;
    }


    /**
     * Sets the BAKS value for this PrivateLoanDetails_Type.
     * 
     * @param BAKS
     */
    public void setBAKS(java.math.BigInteger BAKS) {
        this.BAKS = BAKS;
    }


    /**
     * Gets the autoGrantion value for this PrivateLoanDetails_Type.
     * 
     * @return autoGrantion   * Признак автоматической выдачи
     * 0 – ручная выдача
     * 2 – автоматическая выдача
     */
    public java.math.BigInteger getAutoGrantion() {
        return autoGrantion;
    }


    /**
     * Sets the autoGrantion value for this PrivateLoanDetails_Type.
     * 
     * @param autoGrantion   * Признак автоматической выдачи
     * 0 – ручная выдача
     * 2 – автоматическая выдача
     */
    public void setAutoGrantion(java.math.BigInteger autoGrantion) {
        this.autoGrantion = autoGrantion;
    }


    /**
     * Gets the applicationNumberCA value for this PrivateLoanDetails_Type.
     * 
     * @return applicationNumberCA   * Номер заявки на получение КД
     */
    public java.lang.String getApplicationNumberCA() {
        return applicationNumberCA;
    }


    /**
     * Sets the applicationNumberCA value for this PrivateLoanDetails_Type.
     * 
     * @param applicationNumberCA   * Номер заявки на получение КД
     */
    public void setApplicationNumberCA(java.lang.String applicationNumberCA) {
        this.applicationNumberCA = applicationNumberCA;
    }


    /**
     * Gets the custRec value for this PrivateLoanDetails_Type.
     * 
     * @return custRec
     */
    public com.rssl.phizicgate.esberibgate.ws.generated.CustRec_Type[] getCustRec() {
        return custRec;
    }


    /**
     * Sets the custRec value for this PrivateLoanDetails_Type.
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


    /**
     * Gets the agencyAddress value for this PrivateLoanDetails_Type.
     * 
     * @return agencyAddress
     */
    public java.lang.String getAgencyAddress() {
        return agencyAddress;
    }


    /**
     * Sets the agencyAddress value for this PrivateLoanDetails_Type.
     * 
     * @param agencyAddress
     */
    public void setAgencyAddress(java.lang.String agencyAddress) {
        this.agencyAddress = agencyAddress;
    }


    /**
     * Gets the earlyRepayment value for this PrivateLoanDetails_Type.
     * 
     * @return earlyRepayment
     */
    public com.rssl.phizicgate.esberibgate.ws.generated.EarlyRepayment_Type[] getEarlyRepayment() {
        return earlyRepayment;
    }


    /**
     * Sets the earlyRepayment value for this PrivateLoanDetails_Type.
     * 
     * @param earlyRepayment
     */
    public void setEarlyRepayment(com.rssl.phizicgate.esberibgate.ws.generated.EarlyRepayment_Type[] earlyRepayment) {
        this.earlyRepayment = earlyRepayment;
    }

    public com.rssl.phizicgate.esberibgate.ws.generated.EarlyRepayment_Type getEarlyRepayment(int i) {
        return this.earlyRepayment[i];
    }

    public void setEarlyRepayment(int i, com.rssl.phizicgate.esberibgate.ws.generated.EarlyRepayment_Type _value) {
        this.earlyRepayment[i] = _value;
    }


    /**
     * Gets the acctBalOnDate value for this PrivateLoanDetails_Type.
     * 
     * @return acctBalOnDate
     */
    public com.rssl.phizicgate.esberibgate.ws.generated.AcctBal_Type[] getAcctBalOnDate() {
        return acctBalOnDate;
    }


    /**
     * Sets the acctBalOnDate value for this PrivateLoanDetails_Type.
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

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof PrivateLoanDetails_Type)) return false;
        PrivateLoanDetails_Type other = (PrivateLoanDetails_Type) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.prodId==null && other.getProdId()==null) || 
             (this.prodId!=null &&
              this.prodId.equals(other.getProdId()))) &&
            ((this.loanType==null && other.getLoanType()==null) || 
             (this.loanType!=null &&
              this.loanType.equals(other.getLoanType()))) &&
            this.period == other.getPeriod() &&
            ((this.creditingRate==null && other.getCreditingRate()==null) || 
             (this.creditingRate!=null &&
              this.creditingRate.equals(other.getCreditingRate()))) &&
            this.loanStatus == other.getLoanStatus() &&
            ((this.principalBalance==null && other.getPrincipalBalance()==null) || 
             (this.principalBalance!=null &&
              this.principalBalance.equals(other.getPrincipalBalance()))) &&
            ((this.fullRepaymentAmount==null && other.getFullRepaymentAmount()==null) || 
             (this.fullRepaymentAmount!=null &&
              this.fullRepaymentAmount.equals(other.getFullRepaymentAmount()))) &&
            ((this.overdue==null && other.getOverdue()==null) || 
             (this.overdue!=null &&
              this.overdue.equals(other.getOverdue()))) &&
            ((this.accounts==null && other.getAccounts()==null) || 
             (this.accounts!=null &&
              this.accounts.equals(other.getAccounts()))) &&
            ((this.BAKS==null && other.getBAKS()==null) || 
             (this.BAKS!=null &&
              this.BAKS.equals(other.getBAKS()))) &&
            ((this.autoGrantion==null && other.getAutoGrantion()==null) || 
             (this.autoGrantion!=null &&
              this.autoGrantion.equals(other.getAutoGrantion()))) &&
            ((this.applicationNumberCA==null && other.getApplicationNumberCA()==null) || 
             (this.applicationNumberCA!=null &&
              this.applicationNumberCA.equals(other.getApplicationNumberCA()))) &&
            ((this.custRec==null && other.getCustRec()==null) || 
             (this.custRec!=null &&
              java.util.Arrays.equals(this.custRec, other.getCustRec()))) &&
            ((this.agencyAddress==null && other.getAgencyAddress()==null) || 
             (this.agencyAddress!=null &&
              this.agencyAddress.equals(other.getAgencyAddress()))) &&
            ((this.earlyRepayment==null && other.getEarlyRepayment()==null) || 
             (this.earlyRepayment!=null &&
              java.util.Arrays.equals(this.earlyRepayment, other.getEarlyRepayment()))) &&
            ((this.acctBalOnDate==null && other.getAcctBalOnDate()==null) || 
             (this.acctBalOnDate!=null &&
              java.util.Arrays.equals(this.acctBalOnDate, other.getAcctBalOnDate())));
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
        if (getProdId() != null) {
            _hashCode += getProdId().hashCode();
        }
        if (getLoanType() != null) {
            _hashCode += getLoanType().hashCode();
        }
        _hashCode += new Long(getPeriod()).hashCode();
        if (getCreditingRate() != null) {
            _hashCode += getCreditingRate().hashCode();
        }
        _hashCode += new Long(getLoanStatus()).hashCode();
        if (getPrincipalBalance() != null) {
            _hashCode += getPrincipalBalance().hashCode();
        }
        if (getFullRepaymentAmount() != null) {
            _hashCode += getFullRepaymentAmount().hashCode();
        }
        if (getOverdue() != null) {
            _hashCode += getOverdue().hashCode();
        }
        if (getAccounts() != null) {
            _hashCode += getAccounts().hashCode();
        }
        if (getBAKS() != null) {
            _hashCode += getBAKS().hashCode();
        }
        if (getAutoGrantion() != null) {
            _hashCode += getAutoGrantion().hashCode();
        }
        if (getApplicationNumberCA() != null) {
            _hashCode += getApplicationNumberCA().hashCode();
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
        if (getAgencyAddress() != null) {
            _hashCode += getAgencyAddress().hashCode();
        }
        if (getEarlyRepayment() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getEarlyRepayment());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getEarlyRepayment(), i);
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
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(PrivateLoanDetails_Type.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "PrivateLoanDetails_Type"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("prodId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "ProdId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "String"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("loanType");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "LoanType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "LoanType_Type"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("period");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Period"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("creditingRate");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "CreditingRate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("loanStatus");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "LoanStatus"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("principalBalance");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "PrincipalBalance"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("fullRepaymentAmount");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "FullRepaymentAmount"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("overdue");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Overdue"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("accounts");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Accounts"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", ">PrivateLoanDetails_Type>Accounts"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("BAKS");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "BAKS"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("autoGrantion");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "AutoGrantion"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("applicationNumberCA");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "ApplicationNumberCA"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
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
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("agencyAddress");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "AgencyAddress"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("earlyRepayment");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "EarlyRepayment"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "EarlyRepayment_Type"));
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
