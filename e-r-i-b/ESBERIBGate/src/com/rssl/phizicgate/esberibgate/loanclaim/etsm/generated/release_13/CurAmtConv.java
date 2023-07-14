
package com.rssl.phizicgate.esberibgate.loanclaim.etsm.generated.release_13;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CurAmtConv_Type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CurAmtConv_Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="debet_sale" type="{}Decimal" minOccurs="0"/>
 *         &lt;element name="debet_buy" type="{}Decimal" minOccurs="0"/>
 *         &lt;element name="debet_cb" type="{}Decimal" minOccurs="0"/>
 *         &lt;element name="credit_sale" type="{}Decimal" minOccurs="0"/>
 *         &lt;element name="credit_buy" type="{}Decimal" minOccurs="0"/>
 *         &lt;element name="credit_cb" type="{}Decimal" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CurAmtConv_Type", propOrder = {
    "debetSale",
    "debetBuy",
    "debetCb",
    "creditSale",
    "creditBuy",
    "creditCb"
})
@XmlRootElement(name = "CurAmtConv")
public class CurAmtConv {

    @XmlElement(name = "debet_sale")
    protected BigDecimal debetSale;
    @XmlElement(name = "debet_buy")
    protected BigDecimal debetBuy;
    @XmlElement(name = "debet_cb")
    protected BigDecimal debetCb;
    @XmlElement(name = "credit_sale")
    protected BigDecimal creditSale;
    @XmlElement(name = "credit_buy")
    protected BigDecimal creditBuy;
    @XmlElement(name = "credit_cb")
    protected BigDecimal creditCb;

    /**
     * Gets the value of the debetSale property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getDebetSale() {
        return debetSale;
    }

    /**
     * Sets the value of the debetSale property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setDebetSale(BigDecimal value) {
        this.debetSale = value;
    }

    /**
     * Gets the value of the debetBuy property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getDebetBuy() {
        return debetBuy;
    }

    /**
     * Sets the value of the debetBuy property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setDebetBuy(BigDecimal value) {
        this.debetBuy = value;
    }

    /**
     * Gets the value of the debetCb property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getDebetCb() {
        return debetCb;
    }

    /**
     * Sets the value of the debetCb property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setDebetCb(BigDecimal value) {
        this.debetCb = value;
    }

    /**
     * Gets the value of the creditSale property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getCreditSale() {
        return creditSale;
    }

    /**
     * Sets the value of the creditSale property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setCreditSale(BigDecimal value) {
        this.creditSale = value;
    }

    /**
     * Gets the value of the creditBuy property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getCreditBuy() {
        return creditBuy;
    }

    /**
     * Sets the value of the creditBuy property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setCreditBuy(BigDecimal value) {
        this.creditBuy = value;
    }

    /**
     * Gets the value of the creditCb property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getCreditCb() {
        return creditCb;
    }

    /**
     * Sets the value of the creditCb property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setCreditCb(BigDecimal value) {
        this.creditCb = value;
    }

}
