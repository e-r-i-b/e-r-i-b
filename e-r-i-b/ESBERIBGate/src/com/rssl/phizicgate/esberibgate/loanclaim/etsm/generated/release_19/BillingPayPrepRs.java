
package com.rssl.phizicgate.esberibgate.loanclaim.etsm.generated.release_19;

import java.math.BigDecimal;
import java.util.Calendar;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import com.rssl.phizic.utils.jaxb.CalendarDateTimeNoMillisecondsAdapter;


/**
 * Ответ интерфейса TBP_PR подготовки билингового платежа к отправке
 * 
 * <p>Java class for BillingPayPrepRs_Type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="BillingPayPrepRs_Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}RqUID"/>
 *         &lt;element ref="{}RqTm"/>
 *         &lt;element ref="{}OperUID" minOccurs="0"/>
 *         &lt;element ref="{}Status"/>
 *         &lt;element ref="{}SystemId" minOccurs="0"/>
 *         &lt;element ref="{}RecipientRec" minOccurs="0"/>
 *         &lt;element name="WithCommision" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="Commission" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="CommissionCur" type="{}AcctCur_Type" minOccurs="0"/>
 *         &lt;element name="MadeOperationId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BillingPayPrepRs_Type", propOrder = {
    "rqUID",
    "rqTm",
    "operUID",
    "status",
    "systemId",
    "recipientRec",
    "withCommision",
    "commission",
    "commissionCur",
    "madeOperationId"
})
@XmlRootElement(name = "BillingPayPrepRs")
public class BillingPayPrepRs {

    @XmlElement(name = "RqUID", required = true)
    protected String rqUID;
    @XmlElement(name = "RqTm", required = true, type = String.class)
    @XmlJavaTypeAdapter(CalendarDateTimeNoMillisecondsAdapter.class)
    protected Calendar rqTm;
    @XmlElement(name = "OperUID")
    protected String operUID;
    @XmlElement(name = "Status", required = true)
    protected Status status;
    @XmlElement(name = "SystemId")
    protected String systemId;
    @XmlElement(name = "RecipientRec")
    protected RecipientRec recipientRec;
    @XmlElement(name = "WithCommision")
    protected Boolean withCommision;
    @XmlElement(name = "Commission")
    protected BigDecimal commission;
    @XmlElement(name = "CommissionCur")
    protected String commissionCur;
    @XmlElement(name = "MadeOperationId")
    protected String madeOperationId;

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
     * Gets the value of the recipientRec property.
     * 
     * @return
     *     possible object is
     *     {@link RecipientRec }
     *     
     */
    public RecipientRec getRecipientRec() {
        return recipientRec;
    }

    /**
     * Sets the value of the recipientRec property.
     * 
     * @param value
     *     allowed object is
     *     {@link RecipientRec }
     *     
     */
    public void setRecipientRec(RecipientRec value) {
        this.recipientRec = value;
    }

    /**
     * Gets the value of the withCommision property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean getWithCommision() {
        return withCommision;
    }

    /**
     * Sets the value of the withCommision property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setWithCommision(Boolean value) {
        this.withCommision = value;
    }

    /**
     * Gets the value of the commission property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getCommission() {
        return commission;
    }

    /**
     * Sets the value of the commission property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setCommission(BigDecimal value) {
        this.commission = value;
    }

    /**
     * Gets the value of the commissionCur property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCommissionCur() {
        return commissionCur;
    }

    /**
     * Sets the value of the commissionCur property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCommissionCur(String value) {
        this.commissionCur = value;
    }

    /**
     * Gets the value of the madeOperationId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMadeOperationId() {
        return madeOperationId;
    }

    /**
     * Sets the value of the madeOperationId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMadeOperationId(String value) {
        this.madeOperationId = value;
    }

}
