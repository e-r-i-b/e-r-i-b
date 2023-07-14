
package com.rssl.phizicgate.esberibgate.loanclaim.etsm.generated.release_19;

import java.util.Calendar;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import com.rssl.phizic.utils.jaxb.CalendarDateTimeNoMillisecondsAdapter;


/**
 * Тип запроса для перевыпуска карт (в т.ч. не по месту ведения счета)
 * 
 * <p>Java class for CardReissuePlaceRq_Type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CardReissuePlaceRq_Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="RqUID">
 *           &lt;simpleType>
 *             &lt;restriction base="{}RqUID_Type">
 *               &lt;maxLength value="36"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="RqTm" type="{}DateTime"/>
 *         &lt;element name="SPName">
 *           &lt;simpleType>
 *             &lt;restriction base="{}SPName_Type">
 *               &lt;maxLength value="50"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="SystemId" type="{}SystemId_Type" minOccurs="0"/>
 *         &lt;element name="BankInfo" type="{}BankInfoMod1_Type"/>
 *         &lt;element name="CardNum" type="{}CardNumType"/>
 *         &lt;element name="CardType" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;maxLength value="16"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="Destination" type="{}BankInfoLeadZero_Type"/>
 *         &lt;element name="Source" type="{}SourceId_Type"/>
 *         &lt;element name="ReasonCode" type="{}ReasonCode_Type"/>
 *         &lt;element name="Commission" type="{}Commission_Type"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CardReissuePlaceRq_Type", propOrder = {
    "rqUID",
    "rqTm",
    "spName",
    "systemId",
    "bankInfo",
    "cardNum",
    "cardType",
    "destination",
    "source",
    "reasonCode",
    "commission"
})
@XmlRootElement(name = "CardReissuePlaceRq")
public class CardReissuePlaceRq {

    @XmlElement(name = "RqUID", required = true)
    protected String rqUID;
    @XmlElement(name = "RqTm", required = true, type = String.class)
    @XmlJavaTypeAdapter(CalendarDateTimeNoMillisecondsAdapter.class)
    protected Calendar rqTm;
    @XmlElement(name = "SPName", required = true)
    protected SPNameType spName;
    @XmlElement(name = "SystemId")
    protected String systemId;
    @XmlElement(name = "BankInfo", required = true)
    protected BankInfoMod1Type bankInfo;
    @XmlElement(name = "CardNum", required = true)
    protected String cardNum;
    @XmlElement(name = "CardType")
    protected String cardType;
    @XmlElement(name = "Destination", required = true)
    protected BankInfoLeadZeroType destination;
    @XmlElement(name = "Source", required = true)
    protected SourceIdType source;
    @XmlElement(name = "ReasonCode", required = true)
    protected String reasonCode;
    @XmlElement(name = "Commission")
    protected boolean commission;

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
     * Gets the value of the bankInfo property.
     * 
     * @return
     *     possible object is
     *     {@link BankInfoMod1Type }
     *     
     */
    public BankInfoMod1Type getBankInfo() {
        return bankInfo;
    }

    /**
     * Sets the value of the bankInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link BankInfoMod1Type }
     *     
     */
    public void setBankInfo(BankInfoMod1Type value) {
        this.bankInfo = value;
    }

    /**
     * Gets the value of the cardNum property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCardNum() {
        return cardNum;
    }

    /**
     * Sets the value of the cardNum property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCardNum(String value) {
        this.cardNum = value;
    }

    /**
     * Gets the value of the cardType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCardType() {
        return cardType;
    }

    /**
     * Sets the value of the cardType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCardType(String value) {
        this.cardType = value;
    }

    /**
     * Gets the value of the destination property.
     * 
     * @return
     *     possible object is
     *     {@link BankInfoLeadZeroType }
     *     
     */
    public BankInfoLeadZeroType getDestination() {
        return destination;
    }

    /**
     * Sets the value of the destination property.
     * 
     * @param value
     *     allowed object is
     *     {@link BankInfoLeadZeroType }
     *     
     */
    public void setDestination(BankInfoLeadZeroType value) {
        this.destination = value;
    }

    /**
     * Gets the value of the source property.
     * 
     * @return
     *     possible object is
     *     {@link SourceIdType }
     *     
     */
    public SourceIdType getSource() {
        return source;
    }

    /**
     * Sets the value of the source property.
     * 
     * @param value
     *     allowed object is
     *     {@link SourceIdType }
     *     
     */
    public void setSource(SourceIdType value) {
        this.source = value;
    }

    /**
     * Gets the value of the reasonCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReasonCode() {
        return reasonCode;
    }

    /**
     * Sets the value of the reasonCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReasonCode(String value) {
        this.reasonCode = value;
    }

    /**
     * Gets the value of the commission property.
     * 
     */
    public boolean isCommission() {
        return commission;
    }

    /**
     * Sets the value of the commission property.
     * 
     */
    public void setCommission(boolean value) {
        this.commission = value;
    }

}
