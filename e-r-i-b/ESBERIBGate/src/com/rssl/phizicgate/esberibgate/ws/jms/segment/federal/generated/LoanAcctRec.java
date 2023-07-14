
package com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.generated;

import java.math.BigDecimal;
import java.util.Calendar;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import com.rssl.phizic.utils.jaxb.CalendarDateAdapter;


/**
 * Список кредитов
 * 
 * <p>Java class for LoanAcctRec_Type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="LoanAcctRec_Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}LoanAcctId"/>
 *         &lt;element ref="{}BankInfo"/>
 *         &lt;element ref="{}BankAcctInfo"/>
 *         &lt;element ref="{}CustRec"/>
 *         &lt;element name="Period" type="{}Period_type" minOccurs="0"/>
 *         &lt;element name="CreditigRate" type="{}CreditingRate_Type" minOccurs="0"/>
 *         &lt;element name="RegNextPayDate" type="{}RegNextPayDate_Type" minOccurs="0"/>
 *         &lt;element name="NextPayDate" type="{}NextPayDate_Type" minOccurs="0"/>
 *         &lt;element name="NextPaySum" type="{}NextPaySum_Type" minOccurs="0"/>
 *         &lt;element name="RegPayAmount" type="{}RegPayAmount_Type" minOccurs="0"/>
 *         &lt;element name="PaymentStatus" type="{}PaymentStatus_Type" minOccurs="0"/>
 *         &lt;element name="LoanStatus" type="{}LoanStatus_Type" minOccurs="0"/>
 *         &lt;element name="PrevPayDate" type="{}PrevPayDate_Type" minOccurs="0"/>
 *         &lt;element name="PrevPaySum" type="{}PrevPaySum_Type" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "LoanAcctRec_Type", propOrder = {
    "loanAcctId",
    "bankInfo",
    "bankAcctInfo",
    "custRec",
    "period",
    "creditigRate",
    "regNextPayDate",
    "nextPayDate",
    "nextPaySum",
    "regPayAmount",
    "paymentStatus",
    "loanStatus",
    "prevPayDate",
    "prevPaySum"
})
@XmlRootElement(name = "LoanAcctRec")
public class LoanAcctRec {

    @XmlElement(name = "LoanAcctId", required = true)
    protected LoanAcctIdType loanAcctId;
    @XmlElement(name = "BankInfo", required = true)
    protected BankInfoType bankInfo;
    @XmlElement(name = "BankAcctInfo", required = true)
    protected BankAcctInfo bankAcctInfo;
    @XmlElement(name = "CustRec", required = true)
    protected CustRec custRec;
    @XmlElement(name = "Period")
    protected Long period;
    @XmlElement(name = "CreditigRate")
    protected BigDecimal creditigRate;
    @XmlElement(name = "RegNextPayDate", type = String.class)
    @XmlJavaTypeAdapter(CalendarDateAdapter.class)
    protected Calendar regNextPayDate;
    @XmlElement(name = "NextPayDate", type = String.class)
    @XmlJavaTypeAdapter(CalendarDateAdapter.class)
    protected Calendar nextPayDate;
    @XmlElement(name = "NextPaySum")
    protected BigDecimal nextPaySum;
    @XmlElement(name = "RegPayAmount")
    protected BigDecimal regPayAmount;
    @XmlElement(name = "PaymentStatus")
    protected String paymentStatus;
    @XmlElement(name = "LoanStatus")
    protected String loanStatus;
    @XmlElement(name = "PrevPayDate", type = String.class)
    @XmlJavaTypeAdapter(CalendarDateAdapter.class)
    protected Calendar prevPayDate;
    @XmlElement(name = "PrevPaySum")
    protected BigDecimal prevPaySum;

    /**
     * Gets the value of the loanAcctId property.
     * 
     * @return
     *     possible object is
     *     {@link LoanAcctIdType }
     *     
     */
    public LoanAcctIdType getLoanAcctId() {
        return loanAcctId;
    }

    /**
     * Sets the value of the loanAcctId property.
     * 
     * @param value
     *     allowed object is
     *     {@link LoanAcctIdType }
     *     
     */
    public void setLoanAcctId(LoanAcctIdType value) {
        this.loanAcctId = value;
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
     * Gets the value of the bankAcctInfo property.
     * 
     * @return
     *     possible object is
     *     {@link BankAcctInfo }
     *     
     */
    public BankAcctInfo getBankAcctInfo() {
        return bankAcctInfo;
    }

    /**
     * Sets the value of the bankAcctInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link BankAcctInfo }
     *     
     */
    public void setBankAcctInfo(BankAcctInfo value) {
        this.bankAcctInfo = value;
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
     * Gets the value of the creditigRate property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getCreditigRate() {
        return creditigRate;
    }

    /**
     * Sets the value of the creditigRate property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setCreditigRate(BigDecimal value) {
        this.creditigRate = value;
    }

    /**
     * Gets the value of the regNextPayDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public Calendar getRegNextPayDate() {
        return regNextPayDate;
    }

    /**
     * Sets the value of the regNextPayDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRegNextPayDate(Calendar value) {
        this.regNextPayDate = value;
    }

    /**
     * Gets the value of the nextPayDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public Calendar getNextPayDate() {
        return nextPayDate;
    }

    /**
     * Sets the value of the nextPayDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNextPayDate(Calendar value) {
        this.nextPayDate = value;
    }

    /**
     * Gets the value of the nextPaySum property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getNextPaySum() {
        return nextPaySum;
    }

    /**
     * Sets the value of the nextPaySum property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setNextPaySum(BigDecimal value) {
        this.nextPaySum = value;
    }

    /**
     * Gets the value of the regPayAmount property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getRegPayAmount() {
        return regPayAmount;
    }

    /**
     * Sets the value of the regPayAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setRegPayAmount(BigDecimal value) {
        this.regPayAmount = value;
    }

    /**
     * Gets the value of the paymentStatus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPaymentStatus() {
        return paymentStatus;
    }

    /**
     * Sets the value of the paymentStatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPaymentStatus(String value) {
        this.paymentStatus = value;
    }

    /**
     * Gets the value of the loanStatus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLoanStatus() {
        return loanStatus;
    }

    /**
     * Sets the value of the loanStatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLoanStatus(String value) {
        this.loanStatus = value;
    }

    /**
     * Gets the value of the prevPayDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public Calendar getPrevPayDate() {
        return prevPayDate;
    }

    /**
     * Sets the value of the prevPayDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPrevPayDate(Calendar value) {
        this.prevPayDate = value;
    }

    /**
     * Gets the value of the prevPaySum property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getPrevPaySum() {
        return prevPaySum;
    }

    /**
     * Sets the value of the prevPaySum property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setPrevPaySum(BigDecimal value) {
        this.prevPaySum = value;
    }

}
