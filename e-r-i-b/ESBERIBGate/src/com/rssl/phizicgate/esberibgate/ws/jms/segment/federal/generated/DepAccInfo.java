
package com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.generated;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DepAccInfo_Type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DepAccInfo_Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}AcctCur" minOccurs="0"/>
 *         &lt;element name="AcctName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="AcctCode" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="AcctSubCode" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element ref="{}CurAmt" minOccurs="0"/>
 *         &lt;element name="MaxSumWrite" type="{}Decimal" minOccurs="0"/>
 *         &lt;element ref="{}IrreducibleAmt" minOccurs="0"/>
 *         &lt;element name="ClearBalance" type="{}Decimal" minOccurs="0"/>
 *         &lt;element name="MaxBalance" type="{}Decimal" minOccurs="0"/>
 *         &lt;element name="InterestOnDeposit" type="{}InterestOnDeposit_Type" minOccurs="0"/>
 *         &lt;element name="Period" type="{}Long" minOccurs="0"/>
 *         &lt;element name="Rate" type="{}Decimal" minOccurs="0"/>
 *         &lt;element name="ClientKind" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="OpenDate" type="{}Date" minOccurs="0"/>
 *         &lt;element name="EndDate" type="{}Date" minOccurs="0"/>
 *         &lt;element name="Status" type="{}AccountStatusEnum_Type" minOccurs="0"/>
 *         &lt;element name="IsCreditAllowed" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="IsDebitAllowed" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="IsProlongationAllowed" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="IsCreditCrossAgencyAllowed" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="IsDebitCrossAgencyAllowed" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="IsPassBook" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="ProlongationDate" type="{}Date" minOccurs="0"/>
 *         &lt;element ref="{}BankInfo" minOccurs="0"/>
 *         &lt;element ref="{}CustRec" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DepAccInfo_Type", propOrder = {
    "acctCur",
    "acctName",
    "acctCode",
    "acctSubCode",
    "curAmt",
    "maxSumWrite",
    "irreducibleAmt",
    "clearBalance",
    "maxBalance",
    "interestOnDeposit",
    "period",
    "rate",
    "clientKind",
    "openDate",
    "endDate",
    "status",
    "isCreditAllowed",
    "isDebitAllowed",
    "isProlongationAllowed",
    "isCreditCrossAgencyAllowed",
    "isDebitCrossAgencyAllowed",
    "isPassBook",
    "prolongationDate",
    "bankInfo",
    "custRec"
})
@XmlRootElement(name = "DepAccInfo")
public class DepAccInfo {

    @XmlElement(name = "AcctCur")
    protected String acctCur;
    @XmlElement(name = "AcctName")
    protected String acctName;
    @XmlElement(name = "AcctCode")
    protected Long acctCode;
    @XmlElement(name = "AcctSubCode")
    protected Long acctSubCode;
    @XmlElement(name = "CurAmt")
    protected BigDecimal curAmt;
    @XmlElement(name = "MaxSumWrite")
    protected BigDecimal maxSumWrite;
    @XmlElement(name = "IrreducibleAmt")
    protected BigDecimal irreducibleAmt;
    @XmlElement(name = "ClearBalance")
    protected BigDecimal clearBalance;
    @XmlElement(name = "MaxBalance")
    protected BigDecimal maxBalance;
    @XmlElement(name = "InterestOnDeposit")
    protected InterestOnDepositType interestOnDeposit;
    @XmlElement(name = "Period")
    protected Long period;
    @XmlElement(name = "Rate")
    protected BigDecimal rate;
    @XmlElement(name = "ClientKind")
    protected String clientKind;
    @XmlElement(name = "OpenDate")
    protected String openDate;
    @XmlElement(name = "EndDate")
    protected String endDate;
    @XmlElement(name = "Status")
    protected AccountStatusEnumType status;
    @XmlElement(name = "IsCreditAllowed")
    protected Boolean isCreditAllowed;
    @XmlElement(name = "IsDebitAllowed")
    protected Boolean isDebitAllowed;
    @XmlElement(name = "IsProlongationAllowed")
    protected Boolean isProlongationAllowed;
    @XmlElement(name = "IsCreditCrossAgencyAllowed")
    protected Boolean isCreditCrossAgencyAllowed;
    @XmlElement(name = "IsDebitCrossAgencyAllowed")
    protected Boolean isDebitCrossAgencyAllowed;
    @XmlElement(name = "IsPassBook")
    protected Boolean isPassBook;
    @XmlElement(name = "ProlongationDate")
    protected String prolongationDate;
    @XmlElement(name = "BankInfo")
    protected BankInfoType bankInfo;
    @XmlElement(name = "CustRec")
    protected CustRec custRec;

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
     * Gets the value of the curAmt property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getCurAmt() {
        return curAmt;
    }

    /**
     * Sets the value of the curAmt property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setCurAmt(BigDecimal value) {
        this.curAmt = value;
    }

    /**
     * Gets the value of the maxSumWrite property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getMaxSumWrite() {
        return maxSumWrite;
    }

    /**
     * Sets the value of the maxSumWrite property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setMaxSumWrite(BigDecimal value) {
        this.maxSumWrite = value;
    }

    /**
     * Gets the value of the irreducibleAmt property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getIrreducibleAmt() {
        return irreducibleAmt;
    }

    /**
     * Sets the value of the irreducibleAmt property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setIrreducibleAmt(BigDecimal value) {
        this.irreducibleAmt = value;
    }

    /**
     * Gets the value of the clearBalance property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getClearBalance() {
        return clearBalance;
    }

    /**
     * Sets the value of the clearBalance property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setClearBalance(BigDecimal value) {
        this.clearBalance = value;
    }

    /**
     * Gets the value of the maxBalance property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getMaxBalance() {
        return maxBalance;
    }

    /**
     * Sets the value of the maxBalance property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setMaxBalance(BigDecimal value) {
        this.maxBalance = value;
    }

    /**
     * Gets the value of the interestOnDeposit property.
     * 
     * @return
     *     possible object is
     *     {@link InterestOnDepositType }
     *     
     */
    public InterestOnDepositType getInterestOnDeposit() {
        return interestOnDeposit;
    }

    /**
     * Sets the value of the interestOnDeposit property.
     * 
     * @param value
     *     allowed object is
     *     {@link InterestOnDepositType }
     *     
     */
    public void setInterestOnDeposit(InterestOnDepositType value) {
        this.interestOnDeposit = value;
    }

    /**
     * Gets the value of the period property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getPeriod() {
        return period;
    }

    /**
     * Sets the value of the period property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setPeriod(Long value) {
        this.period = value;
    }

    /**
     * Gets the value of the rate property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getRate() {
        return rate;
    }

    /**
     * Sets the value of the rate property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setRate(BigDecimal value) {
        this.rate = value;
    }

    /**
     * Gets the value of the clientKind property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getClientKind() {
        return clientKind;
    }

    /**
     * Sets the value of the clientKind property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setClientKind(String value) {
        this.clientKind = value;
    }

    /**
     * Gets the value of the openDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOpenDate() {
        return openDate;
    }

    /**
     * Sets the value of the openDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOpenDate(String value) {
        this.openDate = value;
    }

    /**
     * Gets the value of the endDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEndDate() {
        return endDate;
    }

    /**
     * Sets the value of the endDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEndDate(String value) {
        this.endDate = value;
    }

    /**
     * Gets the value of the status property.
     * 
     * @return
     *     possible object is
     *     {@link AccountStatusEnumType }
     *     
     */
    public AccountStatusEnumType getStatus() {
        return status;
    }

    /**
     * Sets the value of the status property.
     * 
     * @param value
     *     allowed object is
     *     {@link AccountStatusEnumType }
     *     
     */
    public void setStatus(AccountStatusEnumType value) {
        this.status = value;
    }

    /**
     * Gets the value of the isCreditAllowed property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean getIsCreditAllowed() {
        return isCreditAllowed;
    }

    /**
     * Sets the value of the isCreditAllowed property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsCreditAllowed(Boolean value) {
        this.isCreditAllowed = value;
    }

    /**
     * Gets the value of the isDebitAllowed property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean getIsDebitAllowed() {
        return isDebitAllowed;
    }

    /**
     * Sets the value of the isDebitAllowed property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsDebitAllowed(Boolean value) {
        this.isDebitAllowed = value;
    }

    /**
     * Gets the value of the isProlongationAllowed property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean getIsProlongationAllowed() {
        return isProlongationAllowed;
    }

    /**
     * Sets the value of the isProlongationAllowed property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsProlongationAllowed(Boolean value) {
        this.isProlongationAllowed = value;
    }

    /**
     * Gets the value of the isCreditCrossAgencyAllowed property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean getIsCreditCrossAgencyAllowed() {
        return isCreditCrossAgencyAllowed;
    }

    /**
     * Sets the value of the isCreditCrossAgencyAllowed property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsCreditCrossAgencyAllowed(Boolean value) {
        this.isCreditCrossAgencyAllowed = value;
    }

    /**
     * Gets the value of the isDebitCrossAgencyAllowed property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean getIsDebitCrossAgencyAllowed() {
        return isDebitCrossAgencyAllowed;
    }

    /**
     * Sets the value of the isDebitCrossAgencyAllowed property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsDebitCrossAgencyAllowed(Boolean value) {
        this.isDebitCrossAgencyAllowed = value;
    }

    /**
     * Gets the value of the isPassBook property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean getIsPassBook() {
        return isPassBook;
    }

    /**
     * Sets the value of the isPassBook property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsPassBook(Boolean value) {
        this.isPassBook = value;
    }

    /**
     * Gets the value of the prolongationDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProlongationDate() {
        return prolongationDate;
    }

    /**
     * Sets the value of the prolongationDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProlongationDate(String value) {
        this.prolongationDate = value;
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
     * Gets the value of the custRec property.
     * 
     * @return
     *     possible object is
     *     {@link CustRec }
     *     
     */
    public CustRec getCustRec() {
        return custRec;
    }

    /**
     * Sets the value of the custRec property.
     * 
     * @param value
     *     allowed object is
     *     {@link CustRec }
     *     
     */
    public void setCustRec(CustRec value) {
        this.custRec = value;
    }

}
