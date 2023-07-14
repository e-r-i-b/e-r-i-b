
package com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.generated;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * “ип сообщени€-ответа дл€ интерфейса XXX___123 Ц получение суммы комиссии, взимаемой банком за осуществление перевода денежных средств с карты на карту
 * 
 * <p>Java class for CalcCardToCardTransferCommissionRs_Type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CalcCardToCardTransferCommissionRs_Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="RqUID" type="{}RqUID_Type"/>
 *         &lt;element name="RqTm" type="{}DateTime"/>
 *         &lt;element name="OperUID" type="{}OperUID_Type"/>
 *         &lt;element name="SPName" type="{}SPName_Type"/>
 *         &lt;element name="Status" type="{}Status_Type"/>
 *         &lt;element name="Commission" type="{}Decimal" minOccurs="0"/>
 *         &lt;element name="CommissionCur" type="{}AcctCur_Type" minOccurs="0"/>
 *         &lt;element name="CardAuthorization" type="{}CardAuthorization_Type" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CalcCardToCardTransferCommissionRs_Type", propOrder = {
    "rqUID",
    "rqTm",
    "operUID",
    "spName",
    "status",
    "commission",
    "commissionCur",
    "cardAuthorization"
})
@XmlRootElement(name = "CalcCardToCardTransferCommissionRs")
public class CalcCardToCardTransferCommissionRs {

    @XmlElement(name = "RqUID", required = true)
    protected String rqUID;
    @XmlElement(name = "RqTm", required = true)
    protected String rqTm;
    @XmlElement(name = "OperUID", required = true)
    protected String operUID;
    @XmlElement(name = "SPName", required = true)
    protected SPNameType spName;
    @XmlElement(name = "Status", required = true)
    protected StatusType status;
    @XmlElement(name = "Commission")
    protected BigDecimal commission;
    @XmlElement(name = "CommissionCur")
    protected String commissionCur;
    @XmlElement(name = "CardAuthorization")
    protected CardAuthorization cardAuthorization;

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
    public String getRqTm() {
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
    public void setRqTm(String value) {
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
     * Gets the value of the spName property.
     * 
     * @return
     *     possible object is
     *     {@link SPNameType }
     *     
     */
    public SPNameType getSPName() {
        return spName;
    }

    /**
     * Sets the value of the spName property.
     * 
     * @param value
     *     allowed object is
     *     {@link SPNameType }
     *     
     */
    public void setSPName(SPNameType value) {
        this.spName = value;
    }

    /**
     * Gets the value of the status property.
     * 
     * @return
     *     possible object is
     *     {@link StatusType }
     *     
     */
    public StatusType getStatus() {
        return status;
    }

    /**
     * Sets the value of the status property.
     * 
     * @param value
     *     allowed object is
     *     {@link StatusType }
     *     
     */
    public void setStatus(StatusType value) {
        this.status = value;
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
     * Gets the value of the cardAuthorization property.
     * 
     * @return
     *     possible object is
     *     {@link CardAuthorization }
     *     
     */
    public CardAuthorization getCardAuthorization() {
        return cardAuthorization;
    }

    /**
     * Sets the value of the cardAuthorization property.
     * 
     * @param value
     *     allowed object is
     *     {@link CardAuthorization }
     *     
     */
    public void setCardAuthorization(CardAuthorization value) {
        this.cardAuthorization = value;
    }

}
