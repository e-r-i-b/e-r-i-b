
package com.rssl.phizicgate.esberibgate.loanclaim.etsm.generated.release_13;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Тип Информация о транзакционных ограничителях
 * 
 * <p>Java class for UsageInfo_Type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="UsageInfo_Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="UsageCode" type="{}C"/>
 *         &lt;element name="MaxCurAmt" type="{}Decimal"/>
 *         &lt;element ref="{}AcctCur"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "UsageInfo_Type", propOrder = {
    "usageCode",
    "maxCurAmt",
    "acctCur"
})
public class UsageInfoType {

    @XmlElement(name = "UsageCode", required = true)
    protected String usageCode;
    @XmlElement(name = "MaxCurAmt", required = true)
    protected BigDecimal maxCurAmt;
    @XmlElement(name = "AcctCur", required = true)
    protected String acctCur;

    /**
     * Gets the value of the usageCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUsageCode() {
        return usageCode;
    }

    /**
     * Sets the value of the usageCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUsageCode(String value) {
        this.usageCode = value;
    }

    /**
     * Gets the value of the maxCurAmt property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getMaxCurAmt() {
        return maxCurAmt;
    }

    /**
     * Sets the value of the maxCurAmt property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setMaxCurAmt(BigDecimal value) {
        this.maxCurAmt = value;
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

}
