/**
 * CardAcctId_Type.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizicgate.esberibgate.ws.generated;


/**
 * Идентификатор карточного счета <CardAcctId> <CardAcctIdFrom> <CardAcctIdTo>
 */
public class CardAcctId_Type  implements java.io.Serializable {
    private java.lang.String systemId;

    private com.rssl.phizicgate.esberibgate.ws.generated.SPName_Type SPName;

    private java.lang.String cardNum;

    private java.lang.String contractNumber;

    /* Номер карточного счета для карт (из ЦОД) */
    private java.lang.String acctId;

    /* Краткое наименование вклада из поля qsname справочника qvb
     * ЦАС (должен приходить текст на русском языке, полностью готовый для
     * отображения пользователю как есть) */
    private java.lang.String acctName;

    /* Тип счета */
    private java.lang.Long acctCode;

    /* Подтип счета */
    private java.lang.Long acctSubCode;

    /* Тип пластиковой карты. CC- кредитная, DC - дебетовая, OV -
     * с овердрафтом. Для АС Филиала используется для услуги МБК (0 - основная
     * или 1- активная) */
    private java.lang.String cardType;

    /* Вид карты в рамках МПС */
    private java.lang.String cardLevel;

    /* Признак принадлежности карты к бонусной программе */
    private java.lang.String cardBonusSign;

    private java.lang.String cardName;

    private java.lang.String acctCur;

    private java.lang.String endDt;

    /* Дата платежа ( для кредитных карт) */
    private java.lang.String pmtDt;

    /* Дата выпуска карты */
    private java.lang.String issDt;

    /* Имя на карте */
    private java.lang.String cardHolder;

    /* Тип карты */
    private java.lang.String UNICardType;

    /* Тип счета */
    private java.lang.String UNIAcctType;

    /* Номер картсчета */
    private com.rssl.phizicgate.esberibgate.ws.generated.CardAccount_Type cardAccount;

    /* Статус карточного счета */
    private com.rssl.phizicgate.esberibgate.ws.generated.CardStatusEnum_Type status;

    private com.rssl.phizicgate.esberibgate.ws.generated.CustInfo_Type custInfo;

    private com.rssl.phizicgate.esberibgate.ws.generated.BankInfo_Type bankInfo;

    /* Захешированный номера карт, на которые рассчитано предложение */
    private java.lang.String cardNumberHash;

    /* Флаг для предложений на кредитные карты ("1" для предложений
     * на кредитные карты) */
    private java.math.BigInteger cardStat;

    /* Тип карты на основе которой сделано предложение предодобренного
     * лимита */
    private java.lang.String levelSale;

    /* Информация о клиенте, предназначенная для занесения на чип
     * карты */
    private com.rssl.phizicgate.esberibgate.ws.generated.PlasticInfoType plasticInfo;

    /* Счетовой контракт */
    private com.rssl.phizicgate.esberibgate.ws.generated.Contract_Type contract;

    /* Код продукта счетового контракта 
     * CODEWAY4SHORT справочника CARD_MDM */
    private java.lang.String productCode;

    /* Признак принадлежности карты к стороннему банку */
    private java.lang.Boolean foreignCard;

    /* Размер кредитного лимита в валюте счета */
    private java.math.BigDecimal creditLimit;

    public CardAcctId_Type() {
    }

    public CardAcctId_Type(
           java.lang.String systemId,
           com.rssl.phizicgate.esberibgate.ws.generated.SPName_Type SPName,
           java.lang.String cardNum,
           java.lang.String contractNumber,
           java.lang.String acctId,
           java.lang.String acctName,
           java.lang.Long acctCode,
           java.lang.Long acctSubCode,
           java.lang.String cardType,
           java.lang.String cardLevel,
           java.lang.String cardBonusSign,
           java.lang.String cardName,
           java.lang.String acctCur,
           java.lang.String endDt,
           java.lang.String pmtDt,
           java.lang.String issDt,
           java.lang.String cardHolder,
           java.lang.String UNICardType,
           java.lang.String UNIAcctType,
           com.rssl.phizicgate.esberibgate.ws.generated.CardAccount_Type cardAccount,
           com.rssl.phizicgate.esberibgate.ws.generated.CardStatusEnum_Type status,
           com.rssl.phizicgate.esberibgate.ws.generated.CustInfo_Type custInfo,
           com.rssl.phizicgate.esberibgate.ws.generated.BankInfo_Type bankInfo,
           java.lang.String cardNumberHash,
           java.math.BigInteger cardStat,
           java.lang.String levelSale,
           com.rssl.phizicgate.esberibgate.ws.generated.PlasticInfoType plasticInfo,
           com.rssl.phizicgate.esberibgate.ws.generated.Contract_Type contract,
           java.lang.String productCode,
           java.lang.Boolean foreignCard,
           java.math.BigDecimal creditLimit) {
           this.systemId = systemId;
           this.SPName = SPName;
           this.cardNum = cardNum;
           this.contractNumber = contractNumber;
           this.acctId = acctId;
           this.acctName = acctName;
           this.acctCode = acctCode;
           this.acctSubCode = acctSubCode;
           this.cardType = cardType;
           this.cardLevel = cardLevel;
           this.cardBonusSign = cardBonusSign;
           this.cardName = cardName;
           this.acctCur = acctCur;
           this.endDt = endDt;
           this.pmtDt = pmtDt;
           this.issDt = issDt;
           this.cardHolder = cardHolder;
           this.UNICardType = UNICardType;
           this.UNIAcctType = UNIAcctType;
           this.cardAccount = cardAccount;
           this.status = status;
           this.custInfo = custInfo;
           this.bankInfo = bankInfo;
           this.cardNumberHash = cardNumberHash;
           this.cardStat = cardStat;
           this.levelSale = levelSale;
           this.plasticInfo = plasticInfo;
           this.contract = contract;
           this.productCode = productCode;
           this.foreignCard = foreignCard;
           this.creditLimit = creditLimit;
    }


    /**
     * Gets the systemId value for this CardAcctId_Type.
     * 
     * @return systemId
     */
    public java.lang.String getSystemId() {
        return systemId;
    }


    /**
     * Sets the systemId value for this CardAcctId_Type.
     * 
     * @param systemId
     */
    public void setSystemId(java.lang.String systemId) {
        this.systemId = systemId;
    }


    /**
     * Gets the SPName value for this CardAcctId_Type.
     * 
     * @return SPName
     */
    public com.rssl.phizicgate.esberibgate.ws.generated.SPName_Type getSPName() {
        return SPName;
    }


    /**
     * Sets the SPName value for this CardAcctId_Type.
     * 
     * @param SPName
     */
    public void setSPName(com.rssl.phizicgate.esberibgate.ws.generated.SPName_Type SPName) {
        this.SPName = SPName;
    }


    /**
     * Gets the cardNum value for this CardAcctId_Type.
     * 
     * @return cardNum
     */
    public java.lang.String getCardNum() {
        return cardNum;
    }


    /**
     * Sets the cardNum value for this CardAcctId_Type.
     * 
     * @param cardNum
     */
    public void setCardNum(java.lang.String cardNum) {
        this.cardNum = cardNum;
    }


    /**
     * Gets the contractNumber value for this CardAcctId_Type.
     * 
     * @return contractNumber
     */
    public java.lang.String getContractNumber() {
        return contractNumber;
    }


    /**
     * Sets the contractNumber value for this CardAcctId_Type.
     * 
     * @param contractNumber
     */
    public void setContractNumber(java.lang.String contractNumber) {
        this.contractNumber = contractNumber;
    }


    /**
     * Gets the acctId value for this CardAcctId_Type.
     * 
     * @return acctId   * Номер карточного счета для карт (из ЦОД)
     */
    public java.lang.String getAcctId() {
        return acctId;
    }


    /**
     * Sets the acctId value for this CardAcctId_Type.
     * 
     * @param acctId   * Номер карточного счета для карт (из ЦОД)
     */
    public void setAcctId(java.lang.String acctId) {
        this.acctId = acctId;
    }


    /**
     * Gets the acctName value for this CardAcctId_Type.
     * 
     * @return acctName   * Краткое наименование вклада из поля qsname справочника qvb
     * ЦАС (должен приходить текст на русском языке, полностью готовый для
     * отображения пользователю как есть)
     */
    public java.lang.String getAcctName() {
        return acctName;
    }


    /**
     * Sets the acctName value for this CardAcctId_Type.
     * 
     * @param acctName   * Краткое наименование вклада из поля qsname справочника qvb
     * ЦАС (должен приходить текст на русском языке, полностью готовый для
     * отображения пользователю как есть)
     */
    public void setAcctName(java.lang.String acctName) {
        this.acctName = acctName;
    }


    /**
     * Gets the acctCode value for this CardAcctId_Type.
     * 
     * @return acctCode   * Тип счета
     */
    public java.lang.Long getAcctCode() {
        return acctCode;
    }


    /**
     * Sets the acctCode value for this CardAcctId_Type.
     * 
     * @param acctCode   * Тип счета
     */
    public void setAcctCode(java.lang.Long acctCode) {
        this.acctCode = acctCode;
    }


    /**
     * Gets the acctSubCode value for this CardAcctId_Type.
     * 
     * @return acctSubCode   * Подтип счета
     */
    public java.lang.Long getAcctSubCode() {
        return acctSubCode;
    }


    /**
     * Sets the acctSubCode value for this CardAcctId_Type.
     * 
     * @param acctSubCode   * Подтип счета
     */
    public void setAcctSubCode(java.lang.Long acctSubCode) {
        this.acctSubCode = acctSubCode;
    }


    /**
     * Gets the cardType value for this CardAcctId_Type.
     * 
     * @return cardType   * Тип пластиковой карты. CC- кредитная, DC - дебетовая, OV -
     * с овердрафтом. Для АС Филиала используется для услуги МБК (0 - основная
     * или 1- активная)
     */
    public java.lang.String getCardType() {
        return cardType;
    }


    /**
     * Sets the cardType value for this CardAcctId_Type.
     * 
     * @param cardType   * Тип пластиковой карты. CC- кредитная, DC - дебетовая, OV -
     * с овердрафтом. Для АС Филиала используется для услуги МБК (0 - основная
     * или 1- активная)
     */
    public void setCardType(java.lang.String cardType) {
        this.cardType = cardType;
    }


    /**
     * Gets the cardLevel value for this CardAcctId_Type.
     * 
     * @return cardLevel   * Вид карты в рамках МПС
     */
    public java.lang.String getCardLevel() {
        return cardLevel;
    }


    /**
     * Sets the cardLevel value for this CardAcctId_Type.
     * 
     * @param cardLevel   * Вид карты в рамках МПС
     */
    public void setCardLevel(java.lang.String cardLevel) {
        this.cardLevel = cardLevel;
    }


    /**
     * Gets the cardBonusSign value for this CardAcctId_Type.
     * 
     * @return cardBonusSign   * Признак принадлежности карты к бонусной программе
     */
    public java.lang.String getCardBonusSign() {
        return cardBonusSign;
    }


    /**
     * Sets the cardBonusSign value for this CardAcctId_Type.
     * 
     * @param cardBonusSign   * Признак принадлежности карты к бонусной программе
     */
    public void setCardBonusSign(java.lang.String cardBonusSign) {
        this.cardBonusSign = cardBonusSign;
    }


    /**
     * Gets the cardName value for this CardAcctId_Type.
     * 
     * @return cardName
     */
    public java.lang.String getCardName() {
        return cardName;
    }


    /**
     * Sets the cardName value for this CardAcctId_Type.
     * 
     * @param cardName
     */
    public void setCardName(java.lang.String cardName) {
        this.cardName = cardName;
    }


    /**
     * Gets the acctCur value for this CardAcctId_Type.
     * 
     * @return acctCur
     */
    public java.lang.String getAcctCur() {
        return acctCur;
    }


    /**
     * Sets the acctCur value for this CardAcctId_Type.
     * 
     * @param acctCur
     */
    public void setAcctCur(java.lang.String acctCur) {
        this.acctCur = acctCur;
    }


    /**
     * Gets the endDt value for this CardAcctId_Type.
     * 
     * @return endDt
     */
    public java.lang.String getEndDt() {
        return endDt;
    }


    /**
     * Sets the endDt value for this CardAcctId_Type.
     * 
     * @param endDt
     */
    public void setEndDt(java.lang.String endDt) {
        this.endDt = endDt;
    }


    /**
     * Gets the pmtDt value for this CardAcctId_Type.
     * 
     * @return pmtDt   * Дата платежа ( для кредитных карт)
     */
    public java.lang.String getPmtDt() {
        return pmtDt;
    }


    /**
     * Sets the pmtDt value for this CardAcctId_Type.
     * 
     * @param pmtDt   * Дата платежа ( для кредитных карт)
     */
    public void setPmtDt(java.lang.String pmtDt) {
        this.pmtDt = pmtDt;
    }


    /**
     * Gets the issDt value for this CardAcctId_Type.
     * 
     * @return issDt   * Дата выпуска карты
     */
    public java.lang.String getIssDt() {
        return issDt;
    }


    /**
     * Sets the issDt value for this CardAcctId_Type.
     * 
     * @param issDt   * Дата выпуска карты
     */
    public void setIssDt(java.lang.String issDt) {
        this.issDt = issDt;
    }


    /**
     * Gets the cardHolder value for this CardAcctId_Type.
     * 
     * @return cardHolder   * Имя на карте
     */
    public java.lang.String getCardHolder() {
        return cardHolder;
    }


    /**
     * Sets the cardHolder value for this CardAcctId_Type.
     * 
     * @param cardHolder   * Имя на карте
     */
    public void setCardHolder(java.lang.String cardHolder) {
        this.cardHolder = cardHolder;
    }


    /**
     * Gets the UNICardType value for this CardAcctId_Type.
     * 
     * @return UNICardType   * Тип карты
     */
    public java.lang.String getUNICardType() {
        return UNICardType;
    }


    /**
     * Sets the UNICardType value for this CardAcctId_Type.
     * 
     * @param UNICardType   * Тип карты
     */
    public void setUNICardType(java.lang.String UNICardType) {
        this.UNICardType = UNICardType;
    }


    /**
     * Gets the UNIAcctType value for this CardAcctId_Type.
     * 
     * @return UNIAcctType   * Тип счета
     */
    public java.lang.String getUNIAcctType() {
        return UNIAcctType;
    }


    /**
     * Sets the UNIAcctType value for this CardAcctId_Type.
     * 
     * @param UNIAcctType   * Тип счета
     */
    public void setUNIAcctType(java.lang.String UNIAcctType) {
        this.UNIAcctType = UNIAcctType;
    }


    /**
     * Gets the cardAccount value for this CardAcctId_Type.
     * 
     * @return cardAccount   * Номер картсчета
     */
    public com.rssl.phizicgate.esberibgate.ws.generated.CardAccount_Type getCardAccount() {
        return cardAccount;
    }


    /**
     * Sets the cardAccount value for this CardAcctId_Type.
     * 
     * @param cardAccount   * Номер картсчета
     */
    public void setCardAccount(com.rssl.phizicgate.esberibgate.ws.generated.CardAccount_Type cardAccount) {
        this.cardAccount = cardAccount;
    }


    /**
     * Gets the status value for this CardAcctId_Type.
     * 
     * @return status   * Статус карточного счета
     */
    public com.rssl.phizicgate.esberibgate.ws.generated.CardStatusEnum_Type getStatus() {
        return status;
    }


    /**
     * Sets the status value for this CardAcctId_Type.
     * 
     * @param status   * Статус карточного счета
     */
    public void setStatus(com.rssl.phizicgate.esberibgate.ws.generated.CardStatusEnum_Type status) {
        this.status = status;
    }


    /**
     * Gets the custInfo value for this CardAcctId_Type.
     * 
     * @return custInfo
     */
    public com.rssl.phizicgate.esberibgate.ws.generated.CustInfo_Type getCustInfo() {
        return custInfo;
    }


    /**
     * Sets the custInfo value for this CardAcctId_Type.
     * 
     * @param custInfo
     */
    public void setCustInfo(com.rssl.phizicgate.esberibgate.ws.generated.CustInfo_Type custInfo) {
        this.custInfo = custInfo;
    }


    /**
     * Gets the bankInfo value for this CardAcctId_Type.
     * 
     * @return bankInfo
     */
    public com.rssl.phizicgate.esberibgate.ws.generated.BankInfo_Type getBankInfo() {
        return bankInfo;
    }


    /**
     * Sets the bankInfo value for this CardAcctId_Type.
     * 
     * @param bankInfo
     */
    public void setBankInfo(com.rssl.phizicgate.esberibgate.ws.generated.BankInfo_Type bankInfo) {
        this.bankInfo = bankInfo;
    }


    /**
     * Gets the cardNumberHash value for this CardAcctId_Type.
     * 
     * @return cardNumberHash   * Захешированный номера карт, на которые рассчитано предложение
     */
    public java.lang.String getCardNumberHash() {
        return cardNumberHash;
    }


    /**
     * Sets the cardNumberHash value for this CardAcctId_Type.
     * 
     * @param cardNumberHash   * Захешированный номера карт, на которые рассчитано предложение
     */
    public void setCardNumberHash(java.lang.String cardNumberHash) {
        this.cardNumberHash = cardNumberHash;
    }


    /**
     * Gets the cardStat value for this CardAcctId_Type.
     * 
     * @return cardStat   * Флаг для предложений на кредитные карты ("1" для предложений
     * на кредитные карты)
     */
    public java.math.BigInteger getCardStat() {
        return cardStat;
    }


    /**
     * Sets the cardStat value for this CardAcctId_Type.
     * 
     * @param cardStat   * Флаг для предложений на кредитные карты ("1" для предложений
     * на кредитные карты)
     */
    public void setCardStat(java.math.BigInteger cardStat) {
        this.cardStat = cardStat;
    }


    /**
     * Gets the levelSale value for this CardAcctId_Type.
     * 
     * @return levelSale   * Тип карты на основе которой сделано предложение предодобренного
     * лимита
     */
    public java.lang.String getLevelSale() {
        return levelSale;
    }


    /**
     * Sets the levelSale value for this CardAcctId_Type.
     * 
     * @param levelSale   * Тип карты на основе которой сделано предложение предодобренного
     * лимита
     */
    public void setLevelSale(java.lang.String levelSale) {
        this.levelSale = levelSale;
    }


    /**
     * Gets the plasticInfo value for this CardAcctId_Type.
     * 
     * @return plasticInfo   * Информация о клиенте, предназначенная для занесения на чип
     * карты
     */
    public com.rssl.phizicgate.esberibgate.ws.generated.PlasticInfoType getPlasticInfo() {
        return plasticInfo;
    }


    /**
     * Sets the plasticInfo value for this CardAcctId_Type.
     * 
     * @param plasticInfo   * Информация о клиенте, предназначенная для занесения на чип
     * карты
     */
    public void setPlasticInfo(com.rssl.phizicgate.esberibgate.ws.generated.PlasticInfoType plasticInfo) {
        this.plasticInfo = plasticInfo;
    }


    /**
     * Gets the contract value for this CardAcctId_Type.
     * 
     * @return contract   * Счетовой контракт
     */
    public com.rssl.phizicgate.esberibgate.ws.generated.Contract_Type getContract() {
        return contract;
    }


    /**
     * Sets the contract value for this CardAcctId_Type.
     * 
     * @param contract   * Счетовой контракт
     */
    public void setContract(com.rssl.phizicgate.esberibgate.ws.generated.Contract_Type contract) {
        this.contract = contract;
    }


    /**
     * Gets the productCode value for this CardAcctId_Type.
     * 
     * @return productCode   * Код продукта счетового контракта 
     * CODEWAY4SHORT справочника CARD_MDM
     */
    public java.lang.String getProductCode() {
        return productCode;
    }


    /**
     * Sets the productCode value for this CardAcctId_Type.
     * 
     * @param productCode   * Код продукта счетового контракта 
     * CODEWAY4SHORT справочника CARD_MDM
     */
    public void setProductCode(java.lang.String productCode) {
        this.productCode = productCode;
    }


    /**
     * Gets the foreignCard value for this CardAcctId_Type.
     * 
     * @return foreignCard   * Признак принадлежности карты к стороннему банку
     */
    public java.lang.Boolean getForeignCard() {
        return foreignCard;
    }


    /**
     * Sets the foreignCard value for this CardAcctId_Type.
     * 
     * @param foreignCard   * Признак принадлежности карты к стороннему банку
     */
    public void setForeignCard(java.lang.Boolean foreignCard) {
        this.foreignCard = foreignCard;
    }


    /**
     * Gets the creditLimit value for this CardAcctId_Type.
     * 
     * @return creditLimit   * Размер кредитного лимита в валюте счета
     */
    public java.math.BigDecimal getCreditLimit() {
        return creditLimit;
    }


    /**
     * Sets the creditLimit value for this CardAcctId_Type.
     * 
     * @param creditLimit   * Размер кредитного лимита в валюте счета
     */
    public void setCreditLimit(java.math.BigDecimal creditLimit) {
        this.creditLimit = creditLimit;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof CardAcctId_Type)) return false;
        CardAcctId_Type other = (CardAcctId_Type) obj;
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
            ((this.SPName==null && other.getSPName()==null) || 
             (this.SPName!=null &&
              this.SPName.equals(other.getSPName()))) &&
            ((this.cardNum==null && other.getCardNum()==null) || 
             (this.cardNum!=null &&
              this.cardNum.equals(other.getCardNum()))) &&
            ((this.contractNumber==null && other.getContractNumber()==null) || 
             (this.contractNumber!=null &&
              this.contractNumber.equals(other.getContractNumber()))) &&
            ((this.acctId==null && other.getAcctId()==null) || 
             (this.acctId!=null &&
              this.acctId.equals(other.getAcctId()))) &&
            ((this.acctName==null && other.getAcctName()==null) || 
             (this.acctName!=null &&
              this.acctName.equals(other.getAcctName()))) &&
            ((this.acctCode==null && other.getAcctCode()==null) || 
             (this.acctCode!=null &&
              this.acctCode.equals(other.getAcctCode()))) &&
            ((this.acctSubCode==null && other.getAcctSubCode()==null) || 
             (this.acctSubCode!=null &&
              this.acctSubCode.equals(other.getAcctSubCode()))) &&
            ((this.cardType==null && other.getCardType()==null) || 
             (this.cardType!=null &&
              this.cardType.equals(other.getCardType()))) &&
            ((this.cardLevel==null && other.getCardLevel()==null) || 
             (this.cardLevel!=null &&
              this.cardLevel.equals(other.getCardLevel()))) &&
            ((this.cardBonusSign==null && other.getCardBonusSign()==null) || 
             (this.cardBonusSign!=null &&
              this.cardBonusSign.equals(other.getCardBonusSign()))) &&
            ((this.cardName==null && other.getCardName()==null) || 
             (this.cardName!=null &&
              this.cardName.equals(other.getCardName()))) &&
            ((this.acctCur==null && other.getAcctCur()==null) || 
             (this.acctCur!=null &&
              this.acctCur.equals(other.getAcctCur()))) &&
            ((this.endDt==null && other.getEndDt()==null) || 
             (this.endDt!=null &&
              this.endDt.equals(other.getEndDt()))) &&
            ((this.pmtDt==null && other.getPmtDt()==null) || 
             (this.pmtDt!=null &&
              this.pmtDt.equals(other.getPmtDt()))) &&
            ((this.issDt==null && other.getIssDt()==null) || 
             (this.issDt!=null &&
              this.issDt.equals(other.getIssDt()))) &&
            ((this.cardHolder==null && other.getCardHolder()==null) || 
             (this.cardHolder!=null &&
              this.cardHolder.equals(other.getCardHolder()))) &&
            ((this.UNICardType==null && other.getUNICardType()==null) || 
             (this.UNICardType!=null &&
              this.UNICardType.equals(other.getUNICardType()))) &&
            ((this.UNIAcctType==null && other.getUNIAcctType()==null) || 
             (this.UNIAcctType!=null &&
              this.UNIAcctType.equals(other.getUNIAcctType()))) &&
            ((this.cardAccount==null && other.getCardAccount()==null) || 
             (this.cardAccount!=null &&
              this.cardAccount.equals(other.getCardAccount()))) &&
            ((this.status==null && other.getStatus()==null) || 
             (this.status!=null &&
              this.status.equals(other.getStatus()))) &&
            ((this.custInfo==null && other.getCustInfo()==null) || 
             (this.custInfo!=null &&
              this.custInfo.equals(other.getCustInfo()))) &&
            ((this.bankInfo==null && other.getBankInfo()==null) || 
             (this.bankInfo!=null &&
              this.bankInfo.equals(other.getBankInfo()))) &&
            ((this.cardNumberHash==null && other.getCardNumberHash()==null) || 
             (this.cardNumberHash!=null &&
              this.cardNumberHash.equals(other.getCardNumberHash()))) &&
            ((this.cardStat==null && other.getCardStat()==null) || 
             (this.cardStat!=null &&
              this.cardStat.equals(other.getCardStat()))) &&
            ((this.levelSale==null && other.getLevelSale()==null) || 
             (this.levelSale!=null &&
              this.levelSale.equals(other.getLevelSale()))) &&
            ((this.plasticInfo==null && other.getPlasticInfo()==null) || 
             (this.plasticInfo!=null &&
              this.plasticInfo.equals(other.getPlasticInfo()))) &&
            ((this.contract==null && other.getContract()==null) || 
             (this.contract!=null &&
              this.contract.equals(other.getContract()))) &&
            ((this.productCode==null && other.getProductCode()==null) || 
             (this.productCode!=null &&
              this.productCode.equals(other.getProductCode()))) &&
            ((this.foreignCard==null && other.getForeignCard()==null) || 
             (this.foreignCard!=null &&
              this.foreignCard.equals(other.getForeignCard()))) &&
            ((this.creditLimit==null && other.getCreditLimit()==null) || 
             (this.creditLimit!=null &&
              this.creditLimit.equals(other.getCreditLimit())));
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
        if (getSPName() != null) {
            _hashCode += getSPName().hashCode();
        }
        if (getCardNum() != null) {
            _hashCode += getCardNum().hashCode();
        }
        if (getContractNumber() != null) {
            _hashCode += getContractNumber().hashCode();
        }
        if (getAcctId() != null) {
            _hashCode += getAcctId().hashCode();
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
        if (getCardType() != null) {
            _hashCode += getCardType().hashCode();
        }
        if (getCardLevel() != null) {
            _hashCode += getCardLevel().hashCode();
        }
        if (getCardBonusSign() != null) {
            _hashCode += getCardBonusSign().hashCode();
        }
        if (getCardName() != null) {
            _hashCode += getCardName().hashCode();
        }
        if (getAcctCur() != null) {
            _hashCode += getAcctCur().hashCode();
        }
        if (getEndDt() != null) {
            _hashCode += getEndDt().hashCode();
        }
        if (getPmtDt() != null) {
            _hashCode += getPmtDt().hashCode();
        }
        if (getIssDt() != null) {
            _hashCode += getIssDt().hashCode();
        }
        if (getCardHolder() != null) {
            _hashCode += getCardHolder().hashCode();
        }
        if (getUNICardType() != null) {
            _hashCode += getUNICardType().hashCode();
        }
        if (getUNIAcctType() != null) {
            _hashCode += getUNIAcctType().hashCode();
        }
        if (getCardAccount() != null) {
            _hashCode += getCardAccount().hashCode();
        }
        if (getStatus() != null) {
            _hashCode += getStatus().hashCode();
        }
        if (getCustInfo() != null) {
            _hashCode += getCustInfo().hashCode();
        }
        if (getBankInfo() != null) {
            _hashCode += getBankInfo().hashCode();
        }
        if (getCardNumberHash() != null) {
            _hashCode += getCardNumberHash().hashCode();
        }
        if (getCardStat() != null) {
            _hashCode += getCardStat().hashCode();
        }
        if (getLevelSale() != null) {
            _hashCode += getLevelSale().hashCode();
        }
        if (getPlasticInfo() != null) {
            _hashCode += getPlasticInfo().hashCode();
        }
        if (getContract() != null) {
            _hashCode += getContract().hashCode();
        }
        if (getProductCode() != null) {
            _hashCode += getProductCode().hashCode();
        }
        if (getForeignCard() != null) {
            _hashCode += getForeignCard().hashCode();
        }
        if (getCreditLimit() != null) {
            _hashCode += getCreditLimit().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(CardAcctId_Type.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "CardAcctId_Type"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("systemId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "SystemId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "SystemId_Type"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("SPName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "SPName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "SPName_Type"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cardNum");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "CardNum"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "CardNumType"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("contractNumber");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "ContractNumber"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "String"));
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
        elemField.setFieldName("cardType");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "CardType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cardLevel");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "CardLevel"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "C"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cardBonusSign");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "CardBonusSign"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "C"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cardName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "CardName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "CardName_Type"));
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
        elemField.setFieldName("endDt");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "EndDt"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "EndDt_Type"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("pmtDt");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "PmtDt"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("issDt");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "IssDt"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cardHolder");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "CardHolder"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("UNICardType");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "UNICardType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("UNIAcctType");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "UNIAcctType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cardAccount");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "CardAccount"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "CardAccount_Type"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("status");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Status"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "CardStatusEnum_Type"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("custInfo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "CustInfo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "CustInfo_Type"));
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
        elemField.setFieldName("cardNumberHash");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "CardNumberHash"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "C"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cardStat");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "CardStat"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("levelSale");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "LevelSale"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "C"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("plasticInfo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "PlasticInfo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "PlasticInfoType"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("contract");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Contract"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Contract_Type"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("productCode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "ProductCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "String"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("foreignCard");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "ForeignCard"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("creditLimit");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "CreditLimit"));
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
