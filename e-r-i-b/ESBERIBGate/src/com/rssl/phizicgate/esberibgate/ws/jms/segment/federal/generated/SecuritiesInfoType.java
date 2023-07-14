
package com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.generated;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Базовая информация ценной бумаги
 * 
 * <p>Java class for SecuritiesInfo_Type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SecuritiesInfo_Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="NominalAmt" type="{}Amt_Type"/>
 *         &lt;element name="NominalCurCode" type="{}CurCode_Type"/>
 *         &lt;element name="IncomeRate" type="{}Rate_Type"/>
 *         &lt;element name="IncomeAmt" type="{}Amt_Type"/>
 *         &lt;element name="ComposeDt" type="{}Date"/>
 *         &lt;element name="TermType" type="{}Identifier" minOccurs="0"/>
 *         &lt;element name="TermDays" type="{}Long" minOccurs="0"/>
 *         &lt;element name="TermStartDt" type="{}Date"/>
 *         &lt;element name="TermFinishDt" type="{}Date" minOccurs="0"/>
 *         &lt;element name="TermLimitDt" type="{}Date" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SecuritiesInfo_Type", propOrder = {
    "nominalAmt",
    "nominalCurCode",
    "incomeRate",
    "incomeAmt",
    "composeDt",
    "termType",
    "termDays",
    "termStartDt",
    "termFinishDt",
    "termLimitDt"
})
public class SecuritiesInfoType {

    @XmlElement(name = "NominalAmt", required = true)
    protected BigDecimal nominalAmt;
    @XmlElement(name = "NominalCurCode", required = true)
    protected String nominalCurCode;
    @XmlElement(name = "IncomeRate", required = true)
    protected BigDecimal incomeRate;
    @XmlElement(name = "IncomeAmt", required = true)
    protected BigDecimal incomeAmt;
    @XmlElement(name = "ComposeDt", required = true)
    protected String composeDt;
    @XmlElement(name = "TermType")
    protected String termType;
    @XmlElement(name = "TermDays")
    protected Long termDays;
    @XmlElement(name = "TermStartDt", required = true)
    protected String termStartDt;
    @XmlElement(name = "TermFinishDt")
    protected String termFinishDt;
    @XmlElement(name = "TermLimitDt")
    protected String termLimitDt;

    /**
     * Gets the value of the nominalAmt property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getNominalAmt() {
        return nominalAmt;
    }

    /**
     * Sets the value of the nominalAmt property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setNominalAmt(BigDecimal value) {
        this.nominalAmt = value;
    }

    /**
     * Gets the value of the nominalCurCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNominalCurCode() {
        return nominalCurCode;
    }

    /**
     * Sets the value of the nominalCurCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNominalCurCode(String value) {
        this.nominalCurCode = value;
    }

    /**
     * Gets the value of the incomeRate property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getIncomeRate() {
        return incomeRate;
    }

    /**
     * Sets the value of the incomeRate property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setIncomeRate(BigDecimal value) {
        this.incomeRate = value;
    }

    /**
     * Gets the value of the incomeAmt property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getIncomeAmt() {
        return incomeAmt;
    }

    /**
     * Sets the value of the incomeAmt property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setIncomeAmt(BigDecimal value) {
        this.incomeAmt = value;
    }

    /**
     * Gets the value of the composeDt property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getComposeDt() {
        return composeDt;
    }

    /**
     * Sets the value of the composeDt property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setComposeDt(String value) {
        this.composeDt = value;
    }

    /**
     * Gets the value of the termType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTermType() {
        return termType;
    }

    /**
     * Sets the value of the termType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTermType(String value) {
        this.termType = value;
    }

    /**
     * Gets the value of the termDays property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getTermDays() {
        return termDays;
    }

    /**
     * Sets the value of the termDays property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setTermDays(Long value) {
        this.termDays = value;
    }

    /**
     * Gets the value of the termStartDt property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTermStartDt() {
        return termStartDt;
    }

    /**
     * Sets the value of the termStartDt property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTermStartDt(String value) {
        this.termStartDt = value;
    }

    /**
     * Gets the value of the termFinishDt property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTermFinishDt() {
        return termFinishDt;
    }

    /**
     * Sets the value of the termFinishDt property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTermFinishDt(String value) {
        this.termFinishDt = value;
    }

    /**
     * Gets the value of the termLimitDt property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTermLimitDt() {
        return termLimitDt;
    }

    /**
     * Sets the value of the termLimitDt property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTermLimitDt(String value) {
        this.termLimitDt = value;
    }

}
