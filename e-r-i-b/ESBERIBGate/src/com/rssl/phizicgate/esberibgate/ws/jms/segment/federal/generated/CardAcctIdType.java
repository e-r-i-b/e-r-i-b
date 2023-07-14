
package com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.generated;

import java.math.BigDecimal;
import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * Идентификатор карточного счета <CardAcctId> <CardAcctIdFrom> <CardAcctIdTo>
 * 
 * <p>Java class for CardAcctId_Type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CardAcctId_Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}SystemId" minOccurs="0"/>
 *         &lt;element ref="{}SPName" minOccurs="0"/>
 *         &lt;element ref="{}CardNum" minOccurs="0"/>
 *         &lt;element name="ContractNumber" type="{}ContractNumber_Type" minOccurs="0"/>
 *         &lt;element ref="{}AcctId" minOccurs="0"/>
 *         &lt;element name="AcctName" type="{}C" minOccurs="0"/>
 *         &lt;element name="AcctCode" type="{}Long" minOccurs="0"/>
 *         &lt;element name="AcctSubCode" type="{}Long" minOccurs="0"/>
 *         &lt;element name="CardType" type="{}C" minOccurs="0"/>
 *         &lt;element name="CardLevel" type="{}CardLevel_Type" minOccurs="0"/>
 *         &lt;element name="CardBonusSign" type="{}CardBonusSign_Type" minOccurs="0"/>
 *         &lt;element ref="{}CardName" minOccurs="0"/>
 *         &lt;element ref="{}AcctCur" minOccurs="0"/>
 *         &lt;element ref="{}EndDt" minOccurs="0"/>
 *         &lt;element name="PmtDt" type="{}Date" minOccurs="0"/>
 *         &lt;element name="IssDt" type="{}Date" minOccurs="0"/>
 *         &lt;element name="CardHolder" type="{}String" minOccurs="0"/>
 *         &lt;element name="UNICardType" type="{}String" minOccurs="0"/>
 *         &lt;element name="UNIAcctType" type="{}String" minOccurs="0"/>
 *         &lt;element ref="{}CardAccount" minOccurs="0"/>
 *         &lt;element name="Status" type="{}CardStatusEnum_Type" minOccurs="0"/>
 *         &lt;element ref="{}CustInfo" minOccurs="0"/>
 *         &lt;element ref="{}BankInfo" minOccurs="0"/>
 *         &lt;element name="CardNumberHash" type="{}CardNumberHash_Type" minOccurs="0"/>
 *         &lt;element name="CardStat" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/>
 *         &lt;element name="LevelSale" type="{}LevelSale_Type" minOccurs="0"/>
 *         &lt;element name="PlasticInfo" type="{}PlasticInfoType" minOccurs="0"/>
 *         &lt;element name="Contract" type="{}Contract_Type" minOccurs="0"/>
 *         &lt;element name="ProductCode" type="{}ProductCode_Type" minOccurs="0"/>
 *         &lt;element name="ForeignCard" type="{}Boolean" minOccurs="0"/>
 *         &lt;element name="CreditLimit" type="{}Decimal" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CardAcctId_Type", propOrder = {
    "systemId",
    "spName",
    "cardNum",
    "contractNumber",
    "acctId",
    "acctName",
    "acctCode",
    "acctSubCode",
    "cardType",
    "cardLevel",
    "cardBonusSign",
    "cardName",
    "acctCur",
    "endDt",
    "pmtDt",
    "issDt",
    "cardHolder",
    "uniCardType",
    "uniAcctType",
    "cardAccount",
    "status",
    "custInfo",
    "bankInfo",
    "cardNumberHash",
    "cardStat",
    "levelSale",
    "plasticInfo",
    "contract",
    "productCode",
    "foreignCard",
    "creditLimit"
})
@XmlSeeAlso({
    com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.generated.AcceptBillBasketExecuteRq.CardAcctId.class
})
public class CardAcctIdType {

    @XmlElement(name = "SystemId")
    protected String systemId;
    @XmlElement(name = "SPName")
    protected SPNameType spName;
    @XmlElement(name = "CardNum")
    protected String cardNum;
    @XmlElement(name = "ContractNumber")
    protected String contractNumber;
    @XmlElement(name = "AcctId")
    protected String acctId;
    @XmlElement(name = "AcctName")
    protected String acctName;
    @XmlElement(name = "AcctCode")
    protected Long acctCode;
    @XmlElement(name = "AcctSubCode")
    protected Long acctSubCode;
    @XmlElement(name = "CardType")
    protected String cardType;
    @XmlElement(name = "CardLevel")
    protected String cardLevel;
    @XmlElement(name = "CardBonusSign")
    protected String cardBonusSign;
    @XmlElement(name = "CardName")
    protected String cardName;
    @XmlElement(name = "AcctCur")
    protected String acctCur;
    @XmlElement(name = "EndDt")
    protected String endDt;
    @XmlElement(name = "PmtDt")
    protected String pmtDt;
    @XmlElement(name = "IssDt")
    protected String issDt;
    @XmlElement(name = "CardHolder")
    protected String cardHolder;
    @XmlElement(name = "UNICardType")
    protected String uniCardType;
    @XmlElement(name = "UNIAcctType")
    protected String uniAcctType;
    @XmlElement(name = "CardAccount")
    protected CardAccount cardAccount;
    @XmlElement(name = "Status")
    protected CardStatusEnumType status;
    @XmlElement(name = "CustInfo")
    protected CustInfoType custInfo;
    @XmlElement(name = "BankInfo")
    protected BankInfoType bankInfo;
    @XmlElement(name = "CardNumberHash")
    protected String cardNumberHash;
    @XmlElement(name = "CardStat")
    protected BigInteger cardStat;
    @XmlElement(name = "LevelSale")
    protected LevelSaleType levelSale;
    @XmlElement(name = "PlasticInfo")
    protected PlasticInfoType plasticInfo;
    @XmlElement(name = "Contract")
    protected ContractType contract;
    @XmlElement(name = "ProductCode")
    protected String productCode;
    @XmlElement(name = "ForeignCard")
    protected Boolean foreignCard;
    @XmlElement(name = "CreditLimit")
    protected BigDecimal creditLimit;

    /**
     * Gets the value of the systemId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSystemId() {
        return systemId;
    }

    /**
     * Sets the value of the systemId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSystemId(String value) {
        this.systemId = value;
    }

    /**
     * Gets the value of the spName property.
     * 
     * @return
     *     possible object is
     *     {@link SPNameType }
     *     
     */
    public SPNameType getSPName() {
        return spName;
    }

    /**
     * Sets the value of the spName property.
     * 
     * @param value
     *     allowed object is
     *     {@link SPNameType }
     *     
     */
    public void setSPName(SPNameType value) {
        this.spName = value;
    }

    /**
     * Gets the value of the cardNum property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCardNum() {
        return cardNum;
    }

    /**
     * Sets the value of the cardNum property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCardNum(String value) {
        this.cardNum = value;
    }

    /**
     * Gets the value of the contractNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getContractNumber() {
        return contractNumber;
    }

    /**
     * Sets the value of the contractNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setContractNumber(String value) {
        this.contractNumber = value;
    }

    /**
     * Номер карточного счета для карт (из ЦОД)
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAcctId() {
        return acctId;
    }

    /**
     * Sets the value of the acctId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAcctId(String value) {
        this.acctId = value;
    }

    /**
     * Gets the value of the acctName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAcctName() {
        return acctName;
    }

    /**
     * Sets the value of the acctName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAcctName(String value) {
        this.acctName = value;
    }

    /**
     * Gets the value of the acctCode property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getAcctCode() {
        return acctCode;
    }

    /**
     * Sets the value of the acctCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setAcctCode(Long value) {
        this.acctCode = value;
    }

    /**
     * Gets the value of the acctSubCode property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getAcctSubCode() {
        return acctSubCode;
    }

    /**
     * Sets the value of the acctSubCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setAcctSubCode(Long value) {
        this.acctSubCode = value;
    }

    /**
     * Gets the value of the cardType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCardType() {
        return cardType;
    }

    /**
     * Sets the value of the cardType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCardType(String value) {
        this.cardType = value;
    }

    /**
     * Gets the value of the cardLevel property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCardLevel() {
        return cardLevel;
    }

    /**
     * Sets the value of the cardLevel property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCardLevel(String value) {
        this.cardLevel = value;
    }

    /**
     * Gets the value of the cardBonusSign property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCardBonusSign() {
        return cardBonusSign;
    }

    /**
     * Sets the value of the cardBonusSign property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCardBonusSign(String value) {
        this.cardBonusSign = value;
    }

    /**
     * Gets the value of the cardName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCardName() {
        return cardName;
    }

    /**
     * Sets the value of the cardName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCardName(String value) {
        this.cardName = value;
    }

    /**
     * Gets the value of the acctCur property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAcctCur() {
        return acctCur;
    }

    /**
     * Sets the value of the acctCur property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAcctCur(String value) {
        this.acctCur = value;
    }

    /**
     * Gets the value of the endDt property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEndDt() {
        return endDt;
    }

    /**
     * Sets the value of the endDt property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEndDt(String value) {
        this.endDt = value;
    }

    /**
     * Gets the value of the pmtDt property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPmtDt() {
        return pmtDt;
    }

    /**
     * Sets the value of the pmtDt property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPmtDt(String value) {
        this.pmtDt = value;
    }

    /**
     * Gets the value of the issDt property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIssDt() {
        return issDt;
    }

    /**
     * Sets the value of the issDt property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIssDt(String value) {
        this.issDt = value;
    }

    /**
     * Gets the value of the cardHolder property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCardHolder() {
        return cardHolder;
    }

    /**
     * Sets the value of the cardHolder property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCardHolder(String value) {
        this.cardHolder = value;
    }

    /**
     * Gets the value of the uniCardType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUNICardType() {
        return uniCardType;
    }

    /**
     * Sets the value of the uniCardType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUNICardType(String value) {
        this.uniCardType = value;
    }

    /**
     * Gets the value of the uniAcctType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUNIAcctType() {
        return uniAcctType;
    }

    /**
     * Sets the value of the uniAcctType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUNIAcctType(String value) {
        this.uniAcctType = value;
    }

    /**
     * Номер картсчета
     * 
     * @return
     *     possible object is
     *     {@link CardAccount }
     *     
     */
    public CardAccount getCardAccount() {
        return cardAccount;
    }

    /**
     * Sets the value of the cardAccount property.
     * 
     * @param value
     *     allowed object is
     *     {@link CardAccount }
     *     
     */
    public void setCardAccount(CardAccount value) {
        this.cardAccount = value;
    }

    /**
     * Gets the value of the status property.
     * 
     * @return
     *     possible object is
     *     {@link CardStatusEnumType }
     *     
     */
    public CardStatusEnumType getStatus() {
        return status;
    }

    /**
     * Sets the value of the status property.
     * 
     * @param value
     *     allowed object is
     *     {@link CardStatusEnumType }
     *     
     */
    public void setStatus(CardStatusEnumType value) {
        this.status = value;
    }

    /**
     * Gets the value of the custInfo property.
     * 
     * @return
     *     possible object is
     *     {@link CustInfoType }
     *     
     */
    public CustInfoType getCustInfo() {
        return custInfo;
    }

    /**
     * Sets the value of the custInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link CustInfoType }
     *     
     */
    public void setCustInfo(CustInfoType value) {
        this.custInfo = value;
    }

    /**
     * Gets the value of the bankInfo property.
     * 
     * @return
     *     possible object is
     *     {@link BankInfoType }
     *     
     */
    public BankInfoType getBankInfo() {
        return bankInfo;
    }

    /**
     * Sets the value of the bankInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link BankInfoType }
     *     
     */
    public void setBankInfo(BankInfoType value) {
        this.bankInfo = value;
    }

    /**
     * Gets the value of the cardNumberHash property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCardNumberHash() {
        return cardNumberHash;
    }

    /**
     * Sets the value of the cardNumberHash property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCardNumberHash(String value) {
        this.cardNumberHash = value;
    }

    /**
     * Gets the value of the cardStat property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getCardStat() {
        return cardStat;
    }

    /**
     * Sets the value of the cardStat property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setCardStat(BigInteger value) {
        this.cardStat = value;
    }

    /**
     * Gets the value of the levelSale property.
     * 
     * @return
     *     possible object is
     *     {@link LevelSaleType }
     *     
     */
    public LevelSaleType getLevelSale() {
        return levelSale;
    }

    /**
     * Sets the value of the levelSale property.
     * 
     * @param value
     *     allowed object is
     *     {@link LevelSaleType }
     *     
     */
    public void setLevelSale(LevelSaleType value) {
        this.levelSale = value;
    }

    /**
     * Gets the value of the plasticInfo property.
     * 
     * @return
     *     possible object is
     *     {@link PlasticInfoType }
     *     
     */
    public PlasticInfoType getPlasticInfo() {
        return plasticInfo;
    }

    /**
     * Sets the value of the plasticInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link PlasticInfoType }
     *     
     */
    public void setPlasticInfo(PlasticInfoType value) {
        this.plasticInfo = value;
    }

    /**
     * Gets the value of the contract property.
     * 
     * @return
     *     possible object is
     *     {@link ContractType }
     *     
     */
    public ContractType getContract() {
        return contract;
    }

    /**
     * Sets the value of the contract property.
     * 
     * @param value
     *     allowed object is
     *     {@link ContractType }
     *     
     */
    public void setContract(ContractType value) {
        this.contract = value;
    }

    /**
     * Gets the value of the productCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProductCode() {
        return productCode;
    }

    /**
     * Sets the value of the productCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProductCode(String value) {
        this.productCode = value;
    }

    /**
     * Gets the value of the foreignCard property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean getForeignCard() {
        return foreignCard;
    }

    /**
     * Sets the value of the foreignCard property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setForeignCard(Boolean value) {
        this.foreignCard = value;
    }

    /**
     * Gets the value of the creditLimit property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getCreditLimit() {
        return creditLimit;
    }

    /**
     * Sets the value of the creditLimit property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setCreditLimit(BigDecimal value) {
        this.creditLimit = value;
    }

}
