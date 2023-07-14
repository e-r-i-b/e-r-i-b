
package com.rssl.phizicgate.esberibgate.loanclaim.etsm.generated.release_13;

import com.rssl.phizic.utils.jaxb.CalendarDateAdapter;

import java.math.BigDecimal;
import java.util.Calendar;
import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Java class for ExctractLine_Type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ExctractLine_Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="PayDate" type="{}Date"/>
 *         &lt;element name="Summa" type="{}Decimal"/>
 *         &lt;element name="SummaCur" type="{}AcctCur_Type" minOccurs="0"/>
 *         &lt;element name="MonthOfPay" type="{}Date"/>
 *         &lt;element name="RecBIC" type="{}String"/>
 *         &lt;element name="RecCorrAccount" type="{}String" minOccurs="0"/>
 *         &lt;element name="RecCalcAccount" type="{}String"/>
 *         &lt;element name="Status" type="{}String"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ExctractLine_Type", propOrder = {
    "payDate",
    "summa",
    "summaCur",
    "monthOfPay",
    "recBIC",
    "recCorrAccount",
    "recCalcAccount",
    "status"
})
@XmlRootElement(name = "ExctractLine")
public class ExctractLine {

    @XmlElement(name = "PayDate", required = true, type = String.class)
    @XmlJavaTypeAdapter(CalendarDateAdapter.class)
    protected Calendar payDate;
    @XmlElement(name = "Summa", required = true)
    protected BigDecimal summa;
    @XmlElement(name = "SummaCur")
    protected String summaCur;
    @XmlElement(name = "MonthOfPay", required = true, type = String.class)
    @XmlJavaTypeAdapter(CalendarDateAdapter.class)
    protected Calendar monthOfPay;
    @XmlElement(name = "RecBIC", required = true)
    protected String recBIC;
    @XmlElement(name = "RecCorrAccount")
    protected String recCorrAccount;
    @XmlElement(name = "RecCalcAccount", required = true)
    protected String recCalcAccount;
    @XmlElement(name = "Status", required = true)
    protected String status;

    /**
     * Gets the value of the payDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public Calendar getPayDate() {
        return payDate;
    }

    /**
     * Sets the value of the payDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPayDate(Calendar value) {
        this.payDate = value;
    }

    /**
     * Gets the value of the summa property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getSumma() {
        return summa;
    }

    /**
     * Sets the value of the summa property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setSumma(BigDecimal value) {
        this.summa = value;
    }

    /**
     * Gets the value of the summaCur property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSummaCur() {
        return summaCur;
    }

    /**
     * Sets the value of the summaCur property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSummaCur(String value) {
        this.summaCur = value;
    }

    /**
     * Gets the value of the monthOfPay property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public Calendar getMonthOfPay() {
        return monthOfPay;
    }

    /**
     * Sets the value of the monthOfPay property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMonthOfPay(Calendar value) {
        this.monthOfPay = value;
    }

    /**
     * Gets the value of the recBIC property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRecBIC() {
        return recBIC;
    }

    /**
     * Sets the value of the recBIC property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRecBIC(String value) {
        this.recBIC = value;
    }

    /**
     * Gets the value of the recCorrAccount property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRecCorrAccount() {
        return recCorrAccount;
    }

    /**
     * Sets the value of the recCorrAccount property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRecCorrAccount(String value) {
        this.recCorrAccount = value;
    }

    /**
     * Gets the value of the recCalcAccount property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRecCalcAccount() {
        return recCalcAccount;
    }

    /**
     * Sets the value of the recCalcAccount property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRecCalcAccount(String value) {
        this.recCalcAccount = value;
    }

    /**
     * Gets the value of the status property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets the value of the status property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStatus(String value) {
        this.status = value;
    }

}
