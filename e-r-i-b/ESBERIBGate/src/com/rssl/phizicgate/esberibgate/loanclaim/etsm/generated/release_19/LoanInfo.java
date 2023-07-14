
package com.rssl.phizicgate.esberibgate.loanclaim.etsm.generated.release_19;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import com.rssl.phizic.utils.jaxb.CalendarDateAdapter;


/**
 * <p>Java class for LoanInfo_Type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="LoanInfo_Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="AcctBalFull" type="{}AcctBal_Type" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="AcctBalOnDate" type="{}AcctBal_Type" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="IdSpacing" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element ref="{}LoanAcctId" minOccurs="0"/>
 *         &lt;element ref="{}AgreemtNum" minOccurs="0"/>
 *         &lt;element ref="{}ProdType" minOccurs="0"/>
 *         &lt;element ref="{}LoanType" minOccurs="0"/>
 *         &lt;element ref="{}AcctCur" minOccurs="0"/>
 *         &lt;element ref="{}CurAmt" minOccurs="0"/>
 *         &lt;element name="Period" type="{}Long" minOccurs="0"/>
 *         &lt;element name="CreditingRate" type="{}Decimal" minOccurs="0"/>
 *         &lt;element name="RegNextPayDate" type="{}Date" minOccurs="0"/>
 *         &lt;element name="NextPayDate" type="{}Date" minOccurs="0"/>
 *         &lt;element name="NextPaySum" type="{}Decimal" minOccurs="0"/>
 *         &lt;element name="RegPayAmount" type="{}Decimal" minOccurs="0"/>
 *         &lt;element name="PaymentStatus" type="{}PaymentStatus_Type" minOccurs="0"/>
 *         &lt;element name="LoanStatus" type="{}String" minOccurs="0"/>
 *         &lt;element name="PrevPayDate" type="{}Date" minOccurs="0"/>
 *         &lt;element name="PrevPaySum" type="{}Decimal" minOccurs="0"/>
 *         &lt;element name="PayCard" type="{}CardNumType" minOccurs="0"/>
 *         &lt;element ref="{}CustRec" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "LoanInfo_Type", propOrder = {
    "acctBalFulls",
    "acctBalOnDates",
    "idSpacing",
    "loanAcctId",
    "agreemtNum",
    "prodType",
    "loanType",
    "acctCur",
    "curAmt",
    "period",
    "creditingRate",
    "regNextPayDate",
    "nextPayDate",
    "nextPaySum",
    "regPayAmount",
    "paymentStatus",
    "loanStatus",
    "prevPayDate",
    "prevPaySum",
    "payCard",
    "custRecs"
})
@XmlRootElement(name = "LoanInfo")
public class LoanInfo {

    @XmlElement(name = "AcctBalFull")
    protected List<AcctBal> acctBalFulls;
    @XmlElement(name = "AcctBalOnDate")
    protected List<AcctBal> acctBalOnDates;
    @XmlElement(name = "IdSpacing")
    protected String idSpacing;
    @XmlElement(name = "LoanAcctId")
    protected LoanAcctIdType loanAcctId;
    @XmlElement(name = "AgreemtNum")
    protected String agreemtNum;
    @XmlElement(name = "ProdType")
    protected String prodType;
    @XmlElement(name = "LoanType")
    protected String loanType;
    @XmlElement(name = "AcctCur")
    protected String acctCur;
    @XmlElement(name = "CurAmt")
    protected BigDecimal curAmt;
    @XmlElement(name = "Period")
    protected Long period;
    @XmlElement(name = "CreditingRate")
    protected BigDecimal creditingRate;
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
    @XmlElement(name = "PayCard")
    protected String payCard;
    @XmlElement(name = "CustRec")
    protected List<CustRec> custRecs;

    /**
     * Gets the value of the acctBalFulls property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the acctBalFulls property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAcctBalFulls().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link AcctBal }
     * 
     * 
     */
    public List<AcctBal> getAcctBalFulls() {
        if (acctBalFulls == null) {
            acctBalFulls = new ArrayList<AcctBal>();
        }
        return this.acctBalFulls;
    }

    /**
     * Gets the value of the acctBalOnDates property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the acctBalOnDates property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAcctBalOnDates().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link AcctBal }
     * 
     * 
     */
    public List<AcctBal> getAcctBalOnDates() {
        if (acctBalOnDates == null) {
            acctBalOnDates = new ArrayList<AcctBal>();
        }
        return this.acctBalOnDates;
    }

    /**
     * Gets the value of the idSpacing property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdSpacing() {
        return idSpacing;
    }

    /**
     * Sets the value of the idSpacing property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdSpacing(String value) {
        this.idSpacing = value;
    }

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
     * Gets the value of the agreemtNum property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAgreemtNum() {
        return agreemtNum;
    }

    /**
     * Sets the value of the agreemtNum property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAgreemtNum(String value) {
        this.agreemtNum = value;
    }

    /**
     * Gets the value of the prodType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProdType() {
        return prodType;
    }

    /**
     * Sets the value of the prodType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProdType(String value) {
        this.prodType = value;
    }

    /**
     * Gets the value of the loanType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLoanType() {
        return loanType;
    }

    /**
     * Sets the value of the loanType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLoanType(String value) {
        this.loanType = value;
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
     * Gets the value of the creditingRate property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getCreditingRate() {
        return creditingRate;
    }

    /**
     * Sets the value of the creditingRate property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setCreditingRate(BigDecimal value) {
        this.creditingRate = value;
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

    /**
     * Gets the value of the payCard property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPayCard() {
        return payCard;
    }

    /**
     * Sets the value of the payCard property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPayCard(String value) {
        this.payCard = value;
    }

    /**
     * Gets the value of the custRecs property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the custRecs property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCustRecs().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link CustRec }
     * 
     * 
     */
    public List<CustRec> getCustRecs() {
        if (custRecs == null) {
            custRecs = new ArrayList<CustRec>();
        }
        return this.custRecs;
    }

}
