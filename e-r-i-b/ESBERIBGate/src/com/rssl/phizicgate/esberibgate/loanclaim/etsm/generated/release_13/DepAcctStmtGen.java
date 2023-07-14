
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
 * <p>Java class for DepAcctStmtGen_Type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DepAcctStmtGen_Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="DateFrom" type="{}Date" minOccurs="0"/>
 *         &lt;element name="DateTo" type="{}Date" minOccurs="0"/>
 *         &lt;element name="BalanceFrom" type="{}Decimal" minOccurs="0"/>
 *         &lt;element name="BalanceFromCur" type="{}AcctCur_Type" minOccurs="0"/>
 *         &lt;element name="BalanceTo" type="{}Decimal" minOccurs="0"/>
 *         &lt;element name="BalanceToCur" type="{}AcctCur_Type" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DepAcctStmtGen_Type", propOrder = {
    "dateFrom",
    "dateTo",
    "balanceFrom",
    "balanceFromCur",
    "balanceTo",
    "balanceToCur"
})
@XmlRootElement(name = "DepAcctStmtGen")
public class DepAcctStmtGen {

    @XmlElement(name = "DateFrom", type = String.class)
    @XmlJavaTypeAdapter(CalendarDateAdapter.class)
    protected Calendar dateFrom;
    @XmlElement(name = "DateTo", type = String.class)
    @XmlJavaTypeAdapter(CalendarDateAdapter.class)
    protected Calendar dateTo;
    @XmlElement(name = "BalanceFrom")
    protected BigDecimal balanceFrom;
    @XmlElement(name = "BalanceFromCur")
    protected String balanceFromCur;
    @XmlElement(name = "BalanceTo")
    protected BigDecimal balanceTo;
    @XmlElement(name = "BalanceToCur")
    protected String balanceToCur;

    /**
     * Gets the value of the dateFrom property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public Calendar getDateFrom() {
        return dateFrom;
    }

    /**
     * Sets the value of the dateFrom property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDateFrom(Calendar value) {
        this.dateFrom = value;
    }

    /**
     * Gets the value of the dateTo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public Calendar getDateTo() {
        return dateTo;
    }

    /**
     * Sets the value of the dateTo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDateTo(Calendar value) {
        this.dateTo = value;
    }

    /**
     * Gets the value of the balanceFrom property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getBalanceFrom() {
        return balanceFrom;
    }

    /**
     * Sets the value of the balanceFrom property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setBalanceFrom(BigDecimal value) {
        this.balanceFrom = value;
    }

    /**
     * Gets the value of the balanceFromCur property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBalanceFromCur() {
        return balanceFromCur;
    }

    /**
     * Sets the value of the balanceFromCur property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBalanceFromCur(String value) {
        this.balanceFromCur = value;
    }

    /**
     * Gets the value of the balanceTo property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getBalanceTo() {
        return balanceTo;
    }

    /**
     * Sets the value of the balanceTo property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setBalanceTo(BigDecimal value) {
        this.balanceTo = value;
    }

    /**
     * Gets the value of the balanceToCur property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBalanceToCur() {
        return balanceToCur;
    }

    /**
     * Sets the value of the balanceToCur property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBalanceToCur(String value) {
        this.balanceToCur = value;
    }

}
