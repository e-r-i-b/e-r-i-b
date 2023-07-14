
package com.rssl.phizicgate.esberibgate.loanclaim.etsm.generated.release_13;

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
 * Информация о запросе полной выписки по ОМС
 * 
 * <p>Java class for BankAcctFullStmtInfo_Type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="BankAcctFullStmtInfo_Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="FromDate" type="{}Date"/>
 *         &lt;element name="ToDate" type="{}Date"/>
 *         &lt;element name="LastDate" type="{}Date"/>
 *         &lt;element name="CurAmtCur" type="{}AcctCur_Type"/>
 *         &lt;element name="StartBalance" type="{}IMSBalance_Type"/>
 *         &lt;element name="EndBalance" type="{}IMSBalance_Type"/>
 *         &lt;element name="Rate" type="{}Decimal"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BankAcctFullStmtInfo_Type", propOrder = {
    "fromDate",
    "toDate",
    "lastDate",
    "curAmtCur",
    "startBalance",
    "endBalance",
    "rate"
})
@XmlRootElement(name = "BankAcctFullStmtInfo")
public class BankAcctFullStmtInfo {

    @XmlElement(name = "FromDate", required = true, type = String.class)
    @XmlJavaTypeAdapter(CalendarDateAdapter.class)
    protected Calendar fromDate;
    @XmlElement(name = "ToDate", required = true, type = String.class)
    @XmlJavaTypeAdapter(CalendarDateAdapter.class)
    protected Calendar toDate;
    @XmlElement(name = "LastDate", required = true, type = String.class)
    @XmlJavaTypeAdapter(CalendarDateAdapter.class)
    protected Calendar lastDate;
    @XmlElement(name = "CurAmtCur", required = true)
    protected String curAmtCur;
    @XmlElement(name = "StartBalance", required = true)
    protected IMSBalanceType startBalance;
    @XmlElement(name = "EndBalance", required = true)
    protected IMSBalanceType endBalance;
    @XmlElement(name = "Rate", required = true)
    protected BigDecimal rate;

    /**
     * Gets the value of the fromDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public Calendar getFromDate() {
        return fromDate;
    }

    /**
     * Sets the value of the fromDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFromDate(Calendar value) {
        this.fromDate = value;
    }

    /**
     * Gets the value of the toDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public Calendar getToDate() {
        return toDate;
    }

    /**
     * Sets the value of the toDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setToDate(Calendar value) {
        this.toDate = value;
    }

    /**
     * Gets the value of the lastDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public Calendar getLastDate() {
        return lastDate;
    }

    /**
     * Sets the value of the lastDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLastDate(Calendar value) {
        this.lastDate = value;
    }

    /**
     * Gets the value of the curAmtCur property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCurAmtCur() {
        return curAmtCur;
    }

    /**
     * Sets the value of the curAmtCur property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCurAmtCur(String value) {
        this.curAmtCur = value;
    }

    /**
     * Gets the value of the startBalance property.
     * 
     * @return
     *     possible object is
     *     {@link IMSBalanceType }
     *     
     */
    public IMSBalanceType getStartBalance() {
        return startBalance;
    }

    /**
     * Sets the value of the startBalance property.
     * 
     * @param value
     *     allowed object is
     *     {@link IMSBalanceType }
     *     
     */
    public void setStartBalance(IMSBalanceType value) {
        this.startBalance = value;
    }

    /**
     * Gets the value of the endBalance property.
     * 
     * @return
     *     possible object is
     *     {@link IMSBalanceType }
     *     
     */
    public IMSBalanceType getEndBalance() {
        return endBalance;
    }

    /**
     * Sets the value of the endBalance property.
     * 
     * @param value
     *     allowed object is
     *     {@link IMSBalanceType }
     *     
     */
    public void setEndBalance(IMSBalanceType value) {
        this.endBalance = value;
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

}
