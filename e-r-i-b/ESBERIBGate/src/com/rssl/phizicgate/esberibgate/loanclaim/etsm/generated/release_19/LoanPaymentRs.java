
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
import com.rssl.phizic.utils.jaxb.CalendarDateTimeNoMillisecondsAdapter;


/**
 * Ответ интерфейса LN_PSC получения графика платежей
 * 
 * <p>Java class for LoanPaymentRs_Type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="LoanPaymentRs_Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}RqUID"/>
 *         &lt;element ref="{}RqTm"/>
 *         &lt;element ref="{}OperUID"/>
 *         &lt;element ref="{}Status"/>
 *         &lt;element ref="{}SystemId"/>
 *         &lt;element ref="{}AcctId"/>
 *         &lt;element name="DoneAmount" type="{}Decimal" minOccurs="0"/>
 *         &lt;element name="RemainAmount" type="{}Decimal" minOccurs="0"/>
 *         &lt;element name="FineAmount" type="{}Decimal" minOccurs="0"/>
 *         &lt;element name="MaxSize" type="{}Long" minOccurs="0"/>
 *         &lt;element ref="{}LoanPaymentRec" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "LoanPaymentRs_Type", propOrder = {
    "rqUID",
    "rqTm",
    "operUID",
    "status",
    "systemId",
    "acctId",
    "doneAmount",
    "remainAmount",
    "fineAmount",
    "maxSize",
    "loanPaymentRecs"
})
@XmlRootElement(name = "LoanPaymentRs")
public class LoanPaymentRs {

    @XmlElement(name = "RqUID", required = true)
    protected String rqUID;
    @XmlElement(name = "RqTm", required = true, type = String.class)
    @XmlJavaTypeAdapter(CalendarDateTimeNoMillisecondsAdapter.class)
    protected Calendar rqTm;
    @XmlElement(name = "OperUID", required = true)
    protected String operUID;
    @XmlElement(name = "Status", required = true)
    protected Status status;
    @XmlElement(name = "SystemId", required = true)
    protected String systemId;
    @XmlElement(name = "AcctId", required = true)
    protected String acctId;
    @XmlElement(name = "DoneAmount")
    protected BigDecimal doneAmount;
    @XmlElement(name = "RemainAmount")
    protected BigDecimal remainAmount;
    @XmlElement(name = "FineAmount")
    protected BigDecimal fineAmount;
    @XmlElement(name = "MaxSize")
    protected Long maxSize;
    @XmlElement(name = "LoanPaymentRec")
    protected List<LoanPaymentRec> loanPaymentRecs;

    /**
     * Gets the value of the rqUID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRqUID() {
        return rqUID;
    }

    /**
     * Sets the value of the rqUID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRqUID(String value) {
        this.rqUID = value;
    }

    /**
     * Gets the value of the rqTm property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public Calendar getRqTm() {
        return rqTm;
    }

    /**
     * Sets the value of the rqTm property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRqTm(Calendar value) {
        this.rqTm = value;
    }

    /**
     * Gets the value of the operUID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOperUID() {
        return operUID;
    }

    /**
     * Sets the value of the operUID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOperUID(String value) {
        this.operUID = value;
    }

    /**
     * Gets the value of the status property.
     * 
     * @return
     *     possible object is
     *     {@link Status }
     *     
     */
    public Status getStatus() {
        return status;
    }

    /**
     * Sets the value of the status property.
     * 
     * @param value
     *     allowed object is
     *     {@link Status }
     *     
     */
    public void setStatus(Status value) {
        this.status = value;
    }

    /**
     * Gets the value of the systemId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSystemId() {
        return systemId;
    }

    /**
     * Sets the value of the systemId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSystemId(String value) {
        this.systemId = value;
    }

    /**
     * Gets the value of the acctId property.
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
     * Gets the value of the doneAmount property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getDoneAmount() {
        return doneAmount;
    }

    /**
     * Sets the value of the doneAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setDoneAmount(BigDecimal value) {
        this.doneAmount = value;
    }

    /**
     * Gets the value of the remainAmount property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getRemainAmount() {
        return remainAmount;
    }

    /**
     * Sets the value of the remainAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setRemainAmount(BigDecimal value) {
        this.remainAmount = value;
    }

    /**
     * Gets the value of the fineAmount property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getFineAmount() {
        return fineAmount;
    }

    /**
     * Sets the value of the fineAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setFineAmount(BigDecimal value) {
        this.fineAmount = value;
    }

    /**
     * Gets the value of the maxSize property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getMaxSize() {
        return maxSize;
    }

    /**
     * Sets the value of the maxSize property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setMaxSize(Long value) {
        this.maxSize = value;
    }

    /**
     * Gets the value of the loanPaymentRecs property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the loanPaymentRecs property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getLoanPaymentRecs().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link LoanPaymentRec }
     * 
     * 
     */
    public List<LoanPaymentRec> getLoanPaymentRecs() {
        if (loanPaymentRecs == null) {
            loanPaymentRecs = new ArrayList<LoanPaymentRec>();
        }
        return this.loanPaymentRecs;
    }

}
