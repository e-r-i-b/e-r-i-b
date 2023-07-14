
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
 * Тип информация о депозитном договоре
 * 
 * <p>Java class for DepInfo_Type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DepInfo_Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;choice minOccurs="0">
 *           &lt;element name="MatDt" type="{}Date"/>
 *           &lt;element name="Term" type="{}Long"/>
 *         &lt;/choice>
 *         &lt;element ref="{}IrreducibleAmt" minOccurs="0"/>
 *         &lt;element ref="{}CustRec" minOccurs="0"/>
 *         &lt;element ref="{}AcctId" minOccurs="0"/>
 *         &lt;element ref="{}AcctCur" minOccurs="0"/>
 *         &lt;element name="AcctCode" type="{}Long" minOccurs="0"/>
 *         &lt;element name="AcctSubCode" type="{}Long" minOccurs="0"/>
 *         &lt;element name="DaysToEndOfSaving" type="{}Long" minOccurs="0"/>
 *         &lt;element name="DateToEndOfSaving" type="{}Date" minOccurs="0"/>
 *         &lt;element name="EarlyTermRate" type="{}IntRate_Type" minOccurs="0"/>
 *         &lt;element name="HaveForm190" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DepInfo_Type", propOrder = {
    "term",
    "matDt",
    "irreducibleAmt",
    "custRec",
    "acctId",
    "acctCur",
    "acctCode",
    "acctSubCode",
    "daysToEndOfSaving",
    "dateToEndOfSaving",
    "earlyTermRate",
    "haveForm190"
})
@XmlRootElement(name = "DepInfo")
public class DepInfo {

    @XmlElement(name = "Term")
    protected Long term;
    @XmlElement(name = "MatDt", type = String.class)
    @XmlJavaTypeAdapter(CalendarDateAdapter.class)
    protected Calendar matDt;
    @XmlElement(name = "IrreducibleAmt")
    protected BigDecimal irreducibleAmt;
    @XmlElement(name = "CustRec")
    protected CustRec custRec;
    @XmlElement(name = "AcctId")
    protected String acctId;
    @XmlElement(name = "AcctCur")
    protected String acctCur;
    @XmlElement(name = "AcctCode")
    protected Long acctCode;
    @XmlElement(name = "AcctSubCode")
    protected Long acctSubCode;
    @XmlElement(name = "DaysToEndOfSaving")
    protected Long daysToEndOfSaving;
    @XmlElement(name = "DateToEndOfSaving", type = String.class)
    @XmlJavaTypeAdapter(CalendarDateAdapter.class)
    protected Calendar dateToEndOfSaving;
    @XmlElement(name = "EarlyTermRate")
    protected IntRateType earlyTermRate;
    @XmlElement(name = "HaveForm190")
    protected Boolean haveForm190;

    /**
     * Gets the value of the term property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getTerm() {
        return term;
    }

    /**
     * Sets the value of the term property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setTerm(Long value) {
        this.term = value;
    }

    /**
     * Gets the value of the matDt property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public Calendar getMatDt() {
        return matDt;
    }

    /**
     * Sets the value of the matDt property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMatDt(Calendar value) {
        this.matDt = value;
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
     * Номер вкладного счета
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAcctId() {
        return acctId;
    }

    /**
     * Sets the value of the acctId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAcctId(String value) {
        this.acctId = value;
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
     * Gets the value of the daysToEndOfSaving property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getDaysToEndOfSaving() {
        return daysToEndOfSaving;
    }

    /**
     * Sets the value of the daysToEndOfSaving property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setDaysToEndOfSaving(Long value) {
        this.daysToEndOfSaving = value;
    }

    /**
     * Gets the value of the dateToEndOfSaving property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public Calendar getDateToEndOfSaving() {
        return dateToEndOfSaving;
    }

    /**
     * Sets the value of the dateToEndOfSaving property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDateToEndOfSaving(Calendar value) {
        this.dateToEndOfSaving = value;
    }

    /**
     * Gets the value of the earlyTermRate property.
     * 
     * @return
     *     possible object is
     *     {@link IntRateType }
     *     
     */
    public IntRateType getEarlyTermRate() {
        return earlyTermRate;
    }

    /**
     * Sets the value of the earlyTermRate property.
     * 
     * @param value
     *     allowed object is
     *     {@link IntRateType }
     *     
     */
    public void setEarlyTermRate(IntRateType value) {
        this.earlyTermRate = value;
    }

    /**
     * Gets the value of the haveForm190 property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean getHaveForm190() {
        return haveForm190;
    }

    /**
     * Sets the value of the haveForm190 property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setHaveForm190(Boolean value) {
        this.haveForm190 = value;
    }

}
