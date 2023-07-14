/**
 * AccountData.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated;


/**
 * This type defines account information for a user
 */
public class AccountData  implements java.io.Serializable {
    private com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.Amount accountBalance;

    private java.lang.String accountCategory;

    private java.lang.String accountCountry;

    private com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.Amount accountCreditLimit;

    private com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.Amount accountCreditsTurnover;

    private com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.Amount accountCreditsUsed;

    private com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.Amount accountDailyLimit;

    private java.lang.String accountLastCreditGrantDate;

    private java.lang.String accountName;

    private java.lang.String accountNickName;

    private java.lang.String accountNumber;

    private java.lang.String internationalAccountNumber;

    private java.lang.String accountOpenedDate;

    private com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.AccountOwnershipType accountOwnershipType;

    private com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.AccountRelationType accountRelationType;

    private com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.AccountType accountType;

    private java.lang.String clientDefinedAccountType;

    private java.lang.Integer externalRiskScore;

    private java.lang.Boolean liquid;

    private java.lang.String nextLiquidDate;

    private java.lang.String referenceCode;

    private java.lang.String routingCode;

    private java.lang.String swiftCode;

    public AccountData() {
    }

    public AccountData(
           com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.Amount accountBalance,
           java.lang.String accountCategory,
           java.lang.String accountCountry,
           com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.Amount accountCreditLimit,
           com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.Amount accountCreditsTurnover,
           com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.Amount accountCreditsUsed,
           com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.Amount accountDailyLimit,
           java.lang.String accountLastCreditGrantDate,
           java.lang.String accountName,
           java.lang.String accountNickName,
           java.lang.String accountNumber,
           java.lang.String internationalAccountNumber,
           java.lang.String accountOpenedDate,
           com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.AccountOwnershipType accountOwnershipType,
           com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.AccountRelationType accountRelationType,
           com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.AccountType accountType,
           java.lang.String clientDefinedAccountType,
           java.lang.Integer externalRiskScore,
           java.lang.Boolean liquid,
           java.lang.String nextLiquidDate,
           java.lang.String referenceCode,
           java.lang.String routingCode,
           java.lang.String swiftCode) {
           this.accountBalance = accountBalance;
           this.accountCategory = accountCategory;
           this.accountCountry = accountCountry;
           this.accountCreditLimit = accountCreditLimit;
           this.accountCreditsTurnover = accountCreditsTurnover;
           this.accountCreditsUsed = accountCreditsUsed;
           this.accountDailyLimit = accountDailyLimit;
           this.accountLastCreditGrantDate = accountLastCreditGrantDate;
           this.accountName = accountName;
           this.accountNickName = accountNickName;
           this.accountNumber = accountNumber;
           this.internationalAccountNumber = internationalAccountNumber;
           this.accountOpenedDate = accountOpenedDate;
           this.accountOwnershipType = accountOwnershipType;
           this.accountRelationType = accountRelationType;
           this.accountType = accountType;
           this.clientDefinedAccountType = clientDefinedAccountType;
           this.externalRiskScore = externalRiskScore;
           this.liquid = liquid;
           this.nextLiquidDate = nextLiquidDate;
           this.referenceCode = referenceCode;
           this.routingCode = routingCode;
           this.swiftCode = swiftCode;
    }


    /**
     * Gets the accountBalance value for this AccountData.
     * 
     * @return accountBalance
     */
    public com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.Amount getAccountBalance() {
        return accountBalance;
    }


    /**
     * Sets the accountBalance value for this AccountData.
     * 
     * @param accountBalance
     */
    public void setAccountBalance(com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.Amount accountBalance) {
        this.accountBalance = accountBalance;
    }


    /**
     * Gets the accountCategory value for this AccountData.
     * 
     * @return accountCategory
     */
    public java.lang.String getAccountCategory() {
        return accountCategory;
    }


    /**
     * Sets the accountCategory value for this AccountData.
     * 
     * @param accountCategory
     */
    public void setAccountCategory(java.lang.String accountCategory) {
        this.accountCategory = accountCategory;
    }


    /**
     * Gets the accountCountry value for this AccountData.
     * 
     * @return accountCountry
     */
    public java.lang.String getAccountCountry() {
        return accountCountry;
    }


    /**
     * Sets the accountCountry value for this AccountData.
     * 
     * @param accountCountry
     */
    public void setAccountCountry(java.lang.String accountCountry) {
        this.accountCountry = accountCountry;
    }


    /**
     * Gets the accountCreditLimit value for this AccountData.
     * 
     * @return accountCreditLimit
     */
    public com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.Amount getAccountCreditLimit() {
        return accountCreditLimit;
    }


    /**
     * Sets the accountCreditLimit value for this AccountData.
     * 
     * @param accountCreditLimit
     */
    public void setAccountCreditLimit(com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.Amount accountCreditLimit) {
        this.accountCreditLimit = accountCreditLimit;
    }


    /**
     * Gets the accountCreditsTurnover value for this AccountData.
     * 
     * @return accountCreditsTurnover
     */
    public com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.Amount getAccountCreditsTurnover() {
        return accountCreditsTurnover;
    }


    /**
     * Sets the accountCreditsTurnover value for this AccountData.
     * 
     * @param accountCreditsTurnover
     */
    public void setAccountCreditsTurnover(com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.Amount accountCreditsTurnover) {
        this.accountCreditsTurnover = accountCreditsTurnover;
    }


    /**
     * Gets the accountCreditsUsed value for this AccountData.
     * 
     * @return accountCreditsUsed
     */
    public com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.Amount getAccountCreditsUsed() {
        return accountCreditsUsed;
    }


    /**
     * Sets the accountCreditsUsed value for this AccountData.
     * 
     * @param accountCreditsUsed
     */
    public void setAccountCreditsUsed(com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.Amount accountCreditsUsed) {
        this.accountCreditsUsed = accountCreditsUsed;
    }


    /**
     * Gets the accountDailyLimit value for this AccountData.
     * 
     * @return accountDailyLimit
     */
    public com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.Amount getAccountDailyLimit() {
        return accountDailyLimit;
    }


    /**
     * Sets the accountDailyLimit value for this AccountData.
     * 
     * @param accountDailyLimit
     */
    public void setAccountDailyLimit(com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.Amount accountDailyLimit) {
        this.accountDailyLimit = accountDailyLimit;
    }


    /**
     * Gets the accountLastCreditGrantDate value for this AccountData.
     * 
     * @return accountLastCreditGrantDate
     */
    public java.lang.String getAccountLastCreditGrantDate() {
        return accountLastCreditGrantDate;
    }


    /**
     * Sets the accountLastCreditGrantDate value for this AccountData.
     * 
     * @param accountLastCreditGrantDate
     */
    public void setAccountLastCreditGrantDate(java.lang.String accountLastCreditGrantDate) {
        this.accountLastCreditGrantDate = accountLastCreditGrantDate;
    }


    /**
     * Gets the accountName value for this AccountData.
     * 
     * @return accountName
     */
    public java.lang.String getAccountName() {
        return accountName;
    }


    /**
     * Sets the accountName value for this AccountData.
     * 
     * @param accountName
     */
    public void setAccountName(java.lang.String accountName) {
        this.accountName = accountName;
    }


    /**
     * Gets the accountNickName value for this AccountData.
     * 
     * @return accountNickName
     */
    public java.lang.String getAccountNickName() {
        return accountNickName;
    }


    /**
     * Sets the accountNickName value for this AccountData.
     * 
     * @param accountNickName
     */
    public void setAccountNickName(java.lang.String accountNickName) {
        this.accountNickName = accountNickName;
    }


    /**
     * Gets the accountNumber value for this AccountData.
     * 
     * @return accountNumber
     */
    public java.lang.String getAccountNumber() {
        return accountNumber;
    }


    /**
     * Sets the accountNumber value for this AccountData.
     * 
     * @param accountNumber
     */
    public void setAccountNumber(java.lang.String accountNumber) {
        this.accountNumber = accountNumber;
    }


    /**
     * Gets the internationalAccountNumber value for this AccountData.
     * 
     * @return internationalAccountNumber
     */
    public java.lang.String getInternationalAccountNumber() {
        return internationalAccountNumber;
    }


    /**
     * Sets the internationalAccountNumber value for this AccountData.
     * 
     * @param internationalAccountNumber
     */
    public void setInternationalAccountNumber(java.lang.String internationalAccountNumber) {
        this.internationalAccountNumber = internationalAccountNumber;
    }


    /**
     * Gets the accountOpenedDate value for this AccountData.
     * 
     * @return accountOpenedDate
     */
    public java.lang.String getAccountOpenedDate() {
        return accountOpenedDate;
    }


    /**
     * Sets the accountOpenedDate value for this AccountData.
     * 
     * @param accountOpenedDate
     */
    public void setAccountOpenedDate(java.lang.String accountOpenedDate) {
        this.accountOpenedDate = accountOpenedDate;
    }


    /**
     * Gets the accountOwnershipType value for this AccountData.
     * 
     * @return accountOwnershipType
     */
    public com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.AccountOwnershipType getAccountOwnershipType() {
        return accountOwnershipType;
    }


    /**
     * Sets the accountOwnershipType value for this AccountData.
     * 
     * @param accountOwnershipType
     */
    public void setAccountOwnershipType(com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.AccountOwnershipType accountOwnershipType) {
        this.accountOwnershipType = accountOwnershipType;
    }


    /**
     * Gets the accountRelationType value for this AccountData.
     * 
     * @return accountRelationType
     */
    public com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.AccountRelationType getAccountRelationType() {
        return accountRelationType;
    }


    /**
     * Sets the accountRelationType value for this AccountData.
     * 
     * @param accountRelationType
     */
    public void setAccountRelationType(com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.AccountRelationType accountRelationType) {
        this.accountRelationType = accountRelationType;
    }


    /**
     * Gets the accountType value for this AccountData.
     * 
     * @return accountType
     */
    public com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.AccountType getAccountType() {
        return accountType;
    }


    /**
     * Sets the accountType value for this AccountData.
     * 
     * @param accountType
     */
    public void setAccountType(com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.AccountType accountType) {
        this.accountType = accountType;
    }


    /**
     * Gets the clientDefinedAccountType value for this AccountData.
     * 
     * @return clientDefinedAccountType
     */
    public java.lang.String getClientDefinedAccountType() {
        return clientDefinedAccountType;
    }


    /**
     * Sets the clientDefinedAccountType value for this AccountData.
     * 
     * @param clientDefinedAccountType
     */
    public void setClientDefinedAccountType(java.lang.String clientDefinedAccountType) {
        this.clientDefinedAccountType = clientDefinedAccountType;
    }


    /**
     * Gets the externalRiskScore value for this AccountData.
     * 
     * @return externalRiskScore
     */
    public java.lang.Integer getExternalRiskScore() {
        return externalRiskScore;
    }


    /**
     * Sets the externalRiskScore value for this AccountData.
     * 
     * @param externalRiskScore
     */
    public void setExternalRiskScore(java.lang.Integer externalRiskScore) {
        this.externalRiskScore = externalRiskScore;
    }


    /**
     * Gets the liquid value for this AccountData.
     * 
     * @return liquid
     */
    public java.lang.Boolean getLiquid() {
        return liquid;
    }


    /**
     * Sets the liquid value for this AccountData.
     * 
     * @param liquid
     */
    public void setLiquid(java.lang.Boolean liquid) {
        this.liquid = liquid;
    }


    /**
     * Gets the nextLiquidDate value for this AccountData.
     * 
     * @return nextLiquidDate
     */
    public java.lang.String getNextLiquidDate() {
        return nextLiquidDate;
    }


    /**
     * Sets the nextLiquidDate value for this AccountData.
     * 
     * @param nextLiquidDate
     */
    public void setNextLiquidDate(java.lang.String nextLiquidDate) {
        this.nextLiquidDate = nextLiquidDate;
    }


    /**
     * Gets the referenceCode value for this AccountData.
     * 
     * @return referenceCode
     */
    public java.lang.String getReferenceCode() {
        return referenceCode;
    }


    /**
     * Sets the referenceCode value for this AccountData.
     * 
     * @param referenceCode
     */
    public void setReferenceCode(java.lang.String referenceCode) {
        this.referenceCode = referenceCode;
    }


    /**
     * Gets the routingCode value for this AccountData.
     * 
     * @return routingCode
     */
    public java.lang.String getRoutingCode() {
        return routingCode;
    }


    /**
     * Sets the routingCode value for this AccountData.
     * 
     * @param routingCode
     */
    public void setRoutingCode(java.lang.String routingCode) {
        this.routingCode = routingCode;
    }


    /**
     * Gets the swiftCode value for this AccountData.
     * 
     * @return swiftCode
     */
    public java.lang.String getSwiftCode() {
        return swiftCode;
    }


    /**
     * Sets the swiftCode value for this AccountData.
     * 
     * @param swiftCode
     */
    public void setSwiftCode(java.lang.String swiftCode) {
        this.swiftCode = swiftCode;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof AccountData)) return false;
        AccountData other = (AccountData) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.accountBalance==null && other.getAccountBalance()==null) || 
             (this.accountBalance!=null &&
              this.accountBalance.equals(other.getAccountBalance()))) &&
            ((this.accountCategory==null && other.getAccountCategory()==null) || 
             (this.accountCategory!=null &&
              this.accountCategory.equals(other.getAccountCategory()))) &&
            ((this.accountCountry==null && other.getAccountCountry()==null) || 
             (this.accountCountry!=null &&
              this.accountCountry.equals(other.getAccountCountry()))) &&
            ((this.accountCreditLimit==null && other.getAccountCreditLimit()==null) || 
             (this.accountCreditLimit!=null &&
              this.accountCreditLimit.equals(other.getAccountCreditLimit()))) &&
            ((this.accountCreditsTurnover==null && other.getAccountCreditsTurnover()==null) || 
             (this.accountCreditsTurnover!=null &&
              this.accountCreditsTurnover.equals(other.getAccountCreditsTurnover()))) &&
            ((this.accountCreditsUsed==null && other.getAccountCreditsUsed()==null) || 
             (this.accountCreditsUsed!=null &&
              this.accountCreditsUsed.equals(other.getAccountCreditsUsed()))) &&
            ((this.accountDailyLimit==null && other.getAccountDailyLimit()==null) || 
             (this.accountDailyLimit!=null &&
              this.accountDailyLimit.equals(other.getAccountDailyLimit()))) &&
            ((this.accountLastCreditGrantDate==null && other.getAccountLastCreditGrantDate()==null) || 
             (this.accountLastCreditGrantDate!=null &&
              this.accountLastCreditGrantDate.equals(other.getAccountLastCreditGrantDate()))) &&
            ((this.accountName==null && other.getAccountName()==null) || 
             (this.accountName!=null &&
              this.accountName.equals(other.getAccountName()))) &&
            ((this.accountNickName==null && other.getAccountNickName()==null) || 
             (this.accountNickName!=null &&
              this.accountNickName.equals(other.getAccountNickName()))) &&
            ((this.accountNumber==null && other.getAccountNumber()==null) || 
             (this.accountNumber!=null &&
              this.accountNumber.equals(other.getAccountNumber()))) &&
            ((this.internationalAccountNumber==null && other.getInternationalAccountNumber()==null) || 
             (this.internationalAccountNumber!=null &&
              this.internationalAccountNumber.equals(other.getInternationalAccountNumber()))) &&
            ((this.accountOpenedDate==null && other.getAccountOpenedDate()==null) || 
             (this.accountOpenedDate!=null &&
              this.accountOpenedDate.equals(other.getAccountOpenedDate()))) &&
            ((this.accountOwnershipType==null && other.getAccountOwnershipType()==null) || 
             (this.accountOwnershipType!=null &&
              this.accountOwnershipType.equals(other.getAccountOwnershipType()))) &&
            ((this.accountRelationType==null && other.getAccountRelationType()==null) || 
             (this.accountRelationType!=null &&
              this.accountRelationType.equals(other.getAccountRelationType()))) &&
            ((this.accountType==null && other.getAccountType()==null) || 
             (this.accountType!=null &&
              this.accountType.equals(other.getAccountType()))) &&
            ((this.clientDefinedAccountType==null && other.getClientDefinedAccountType()==null) || 
             (this.clientDefinedAccountType!=null &&
              this.clientDefinedAccountType.equals(other.getClientDefinedAccountType()))) &&
            ((this.externalRiskScore==null && other.getExternalRiskScore()==null) || 
             (this.externalRiskScore!=null &&
              this.externalRiskScore.equals(other.getExternalRiskScore()))) &&
            ((this.liquid==null && other.getLiquid()==null) || 
             (this.liquid!=null &&
              this.liquid.equals(other.getLiquid()))) &&
            ((this.nextLiquidDate==null && other.getNextLiquidDate()==null) || 
             (this.nextLiquidDate!=null &&
              this.nextLiquidDate.equals(other.getNextLiquidDate()))) &&
            ((this.referenceCode==null && other.getReferenceCode()==null) || 
             (this.referenceCode!=null &&
              this.referenceCode.equals(other.getReferenceCode()))) &&
            ((this.routingCode==null && other.getRoutingCode()==null) || 
             (this.routingCode!=null &&
              this.routingCode.equals(other.getRoutingCode()))) &&
            ((this.swiftCode==null && other.getSwiftCode()==null) || 
             (this.swiftCode!=null &&
              this.swiftCode.equals(other.getSwiftCode())));
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
        if (getAccountBalance() != null) {
            _hashCode += getAccountBalance().hashCode();
        }
        if (getAccountCategory() != null) {
            _hashCode += getAccountCategory().hashCode();
        }
        if (getAccountCountry() != null) {
            _hashCode += getAccountCountry().hashCode();
        }
        if (getAccountCreditLimit() != null) {
            _hashCode += getAccountCreditLimit().hashCode();
        }
        if (getAccountCreditsTurnover() != null) {
            _hashCode += getAccountCreditsTurnover().hashCode();
        }
        if (getAccountCreditsUsed() != null) {
            _hashCode += getAccountCreditsUsed().hashCode();
        }
        if (getAccountDailyLimit() != null) {
            _hashCode += getAccountDailyLimit().hashCode();
        }
        if (getAccountLastCreditGrantDate() != null) {
            _hashCode += getAccountLastCreditGrantDate().hashCode();
        }
        if (getAccountName() != null) {
            _hashCode += getAccountName().hashCode();
        }
        if (getAccountNickName() != null) {
            _hashCode += getAccountNickName().hashCode();
        }
        if (getAccountNumber() != null) {
            _hashCode += getAccountNumber().hashCode();
        }
        if (getInternationalAccountNumber() != null) {
            _hashCode += getInternationalAccountNumber().hashCode();
        }
        if (getAccountOpenedDate() != null) {
            _hashCode += getAccountOpenedDate().hashCode();
        }
        if (getAccountOwnershipType() != null) {
            _hashCode += getAccountOwnershipType().hashCode();
        }
        if (getAccountRelationType() != null) {
            _hashCode += getAccountRelationType().hashCode();
        }
        if (getAccountType() != null) {
            _hashCode += getAccountType().hashCode();
        }
        if (getClientDefinedAccountType() != null) {
            _hashCode += getClientDefinedAccountType().hashCode();
        }
        if (getExternalRiskScore() != null) {
            _hashCode += getExternalRiskScore().hashCode();
        }
        if (getLiquid() != null) {
            _hashCode += getLiquid().hashCode();
        }
        if (getNextLiquidDate() != null) {
            _hashCode += getNextLiquidDate().hashCode();
        }
        if (getReferenceCode() != null) {
            _hashCode += getReferenceCode().hashCode();
        }
        if (getRoutingCode() != null) {
            _hashCode += getRoutingCode().hashCode();
        }
        if (getSwiftCode() != null) {
            _hashCode += getSwiftCode().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(AccountData.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "AccountData"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("accountBalance");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "accountBalance"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "Amount"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("accountCategory");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "accountCategory"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("accountCountry");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "accountCountry"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("accountCreditLimit");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "accountCreditLimit"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "Amount"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("accountCreditsTurnover");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "accountCreditsTurnover"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "Amount"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("accountCreditsUsed");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "accountCreditsUsed"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "Amount"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("accountDailyLimit");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "accountDailyLimit"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "Amount"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("accountLastCreditGrantDate");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "accountLastCreditGrantDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("accountName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "accountName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("accountNickName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "accountNickName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("accountNumber");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "accountNumber"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("internationalAccountNumber");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "internationalAccountNumber"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("accountOpenedDate");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "accountOpenedDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("accountOwnershipType");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "accountOwnershipType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "AccountOwnershipType"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("accountRelationType");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "accountRelationType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "AccountRelationType"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("accountType");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "accountType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "AccountType"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("clientDefinedAccountType");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "clientDefinedAccountType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("externalRiskScore");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "externalRiskScore"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("liquid");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "liquid"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("nextLiquidDate");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "nextLiquidDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("referenceCode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "referenceCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("routingCode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "routingCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("swiftCode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "swiftCode"));
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
