
package com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.generated;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Остаток.Предопределенные значения для BalType: Avail -остаток по счету(доступный расходный лимит по карте), AvailCash - доступный лимит для получения наличных, AvailPmt - доступный лимит для оплаты товаров/услуг, CashAvail - макс.сумма списания без нарушения условий договора, Outstanding - сумма задолженности, Pmt - величина ближайшего платежа,MinAvail - величина неснижаемого остатка, ClearBalance - сумма остатка по счету без капитализации.Если BalType пустой- остаток после операции
 * 
 * <p>Java class for AcctBal_Type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AcctBal_Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}BalType" minOccurs="0"/>
 *         &lt;element name="BalName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element ref="{}CurAmt" minOccurs="0"/>
 *         &lt;element name="NextAmt" type="{}Decimal" minOccurs="0"/>
 *         &lt;element ref="{}AcctCur" minOccurs="0"/>
 *         &lt;element name="Priority" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="AcctCount" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="EffDt" type="{}Date" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AcctBal_Type", propOrder = {
    "balType",
    "balName",
    "curAmt",
    "nextAmt",
    "acctCur",
    "priority",
    "acctCount",
    "effDt"
})
@XmlRootElement(name = "AcctBal")
public class AcctBal {

    @XmlElement(name = "BalType")
    protected String balType;
    @XmlElement(name = "BalName")
    protected String balName;
    @XmlElement(name = "CurAmt")
    protected BigDecimal curAmt;
    @XmlElement(name = "NextAmt")
    protected BigDecimal nextAmt;
    @XmlElement(name = "AcctCur")
    protected String acctCur;
    @XmlElement(name = "Priority")
    protected Long priority;
    @XmlElement(name = "AcctCount")
    protected String acctCount;
    @XmlElement(name = "EffDt")
    protected String effDt;

    public AcctBal() {}

    public AcctBal(String balType, String balName, BigDecimal curAmt, BigDecimal nextAmt, String acctCur, Long priority, String acctCount)
    {
        this.balType = balType;
        this.balName = balName;
        this.curAmt = curAmt;
        this.nextAmt = nextAmt;
        this.acctCur = acctCur;
        this.priority = priority;
        this.acctCount = acctCount;
    }

    /**
     * Gets the value of the balType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBalType() {
        return balType;
    }

    /**
     * Sets the value of the balType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBalType(String value) {
        this.balType = value;
    }

    /**
     * Gets the value of the balName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBalName() {
        return balName;
    }

    /**
     * Sets the value of the balName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBalName(String value) {
        this.balName = value;
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
     * Gets the value of the nextAmt property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getNextAmt() {
        return nextAmt;
    }

    /**
     * Sets the value of the nextAmt property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setNextAmt(BigDecimal value) {
        this.nextAmt = value;
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
     * Gets the value of the priority property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getPriority() {
        return priority;
    }

    /**
     * Sets the value of the priority property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setPriority(Long value) {
        this.priority = value;
    }

    /**
     * Gets the value of the acctCount property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAcctCount() {
        return acctCount;
    }

    /**
     * Sets the value of the acctCount property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAcctCount(String value) {
        this.acctCount = value;
    }

    /**
     * Gets the value of the effDt property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEffDt() {
        return effDt;
    }

    /**
     * Sets the value of the effDt property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEffDt(String value) {
        this.effDt = value;
    }

}
