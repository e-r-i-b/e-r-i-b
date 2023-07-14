
package com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Информация о карте
 * 
 * <p>Java class for CardContract_Type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CardContract_Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="CardType" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="ClientCategory" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="Currency" type="{}AcctCur_Type"/>
 *         &lt;element name="BankInfo" type="{}BankInfo_Type"/>
 *         &lt;element name="EmbossedText" type="{}EmbossedText_Type"/>
 *         &lt;element name="BonusProgram" type="{}BonusProgram_Type" minOccurs="0"/>
 *         &lt;element name="ParticipantNumber" type="{}ParticipantNumber_Type" minOccurs="0"/>
 *         &lt;element name="ServiceTarif" type="{}ServiceTarif_Type"/>
 *         &lt;element name="TarifFirst" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="TarifNext" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="IsInsider" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="HasBIOData" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="IsPINEnvelope" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="IsOwner" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CardContract_Type", propOrder = {
    "cardType",
    "clientCategory",
    "currency",
    "bankInfo",
    "embossedText",
    "bonusProgram",
    "participantNumber",
    "serviceTarif",
    "tarifFirst",
    "tarifNext",
    "isInsider",
    "hasBIOData",
    "isPINEnvelope",
    "isOwner"
})
public class CardContractType {

    @XmlElement(name = "CardType")
    protected long cardType;
    @XmlElement(name = "ClientCategory")
    protected long clientCategory;
    @XmlElement(name = "Currency", required = true)
    protected String currency;
    @XmlElement(name = "BankInfo", required = true)
    protected BankInfoType bankInfo;
    @XmlElement(name = "EmbossedText", required = true)
    protected String embossedText;
    @XmlElement(name = "BonusProgram")
    protected BonusProgramType bonusProgram;
    @XmlElement(name = "ParticipantNumber")
    protected String participantNumber;
    @XmlElement(name = "ServiceTarif", required = true)
    protected ServiceTarifType serviceTarif;
    @XmlElement(name = "TarifFirst")
    protected Long tarifFirst;
    @XmlElement(name = "TarifNext")
    protected Long tarifNext;
    @XmlElement(name = "IsInsider")
    protected boolean isInsider;
    @XmlElement(name = "HasBIOData")
    protected boolean hasBIOData;
    @XmlElement(name = "IsPINEnvelope")
    protected boolean isPINEnvelope;
    @XmlElement(name = "IsOwner")
    protected boolean isOwner;

    /**
     * Gets the value of the cardType property.
     * 
     */
    public long getCardType() {
        return cardType;
    }

    /**
     * Sets the value of the cardType property.
     * 
     */
    public void setCardType(long value) {
        this.cardType = value;
    }

    /**
     * Gets the value of the clientCategory property.
     * 
     */
    public long getClientCategory() {
        return clientCategory;
    }

    /**
     * Sets the value of the clientCategory property.
     * 
     */
    public void setClientCategory(long value) {
        this.clientCategory = value;
    }

    /**
     * Gets the value of the currency property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCurrency() {
        return currency;
    }

    /**
     * Sets the value of the currency property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCurrency(String value) {
        this.currency = value;
    }

    /**
     * Gets the value of the bankInfo property.
     * 
     * @return
     *     possible object is
     *     {@link BankInfoType }
     *     
     */
    public BankInfoType getBankInfo() {
        return bankInfo;
    }

    /**
     * Sets the value of the bankInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link BankInfoType }
     *     
     */
    public void setBankInfo(BankInfoType value) {
        this.bankInfo = value;
    }

    /**
     * Gets the value of the embossedText property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEmbossedText() {
        return embossedText;
    }

    /**
     * Sets the value of the embossedText property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEmbossedText(String value) {
        this.embossedText = value;
    }

    /**
     * Gets the value of the bonusProgram property.
     * 
     * @return
     *     possible object is
     *     {@link BonusProgramType }
     *     
     */
    public BonusProgramType getBonusProgram() {
        return bonusProgram;
    }

    /**
     * Sets the value of the bonusProgram property.
     * 
     * @param value
     *     allowed object is
     *     {@link BonusProgramType }
     *     
     */
    public void setBonusProgram(BonusProgramType value) {
        this.bonusProgram = value;
    }

    /**
     * Gets the value of the participantNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getParticipantNumber() {
        return participantNumber;
    }

    /**
     * Sets the value of the participantNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setParticipantNumber(String value) {
        this.participantNumber = value;
    }

    /**
     * Gets the value of the serviceTarif property.
     * 
     * @return
     *     possible object is
     *     {@link ServiceTarifType }
     *     
     */
    public ServiceTarifType getServiceTarif() {
        return serviceTarif;
    }

    /**
     * Sets the value of the serviceTarif property.
     * 
     * @param value
     *     allowed object is
     *     {@link ServiceTarifType }
     *     
     */
    public void setServiceTarif(ServiceTarifType value) {
        this.serviceTarif = value;
    }

    /**
     * Gets the value of the tarifFirst property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getTarifFirst() {
        return tarifFirst;
    }

    /**
     * Sets the value of the tarifFirst property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setTarifFirst(Long value) {
        this.tarifFirst = value;
    }

    /**
     * Gets the value of the tarifNext property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getTarifNext() {
        return tarifNext;
    }

    /**
     * Sets the value of the tarifNext property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setTarifNext(Long value) {
        this.tarifNext = value;
    }

    /**
     * Gets the value of the isInsider property.
     * 
     */
    public boolean isIsInsider() {
        return isInsider;
    }

    /**
     * Sets the value of the isInsider property.
     * 
     */
    public void setIsInsider(boolean value) {
        this.isInsider = value;
    }

    /**
     * Gets the value of the hasBIOData property.
     * 
     */
    public boolean isHasBIOData() {
        return hasBIOData;
    }

    /**
     * Sets the value of the hasBIOData property.
     * 
     */
    public void setHasBIOData(boolean value) {
        this.hasBIOData = value;
    }

    /**
     * Gets the value of the isPINEnvelope property.
     * 
     */
    public boolean isIsPINEnvelope() {
        return isPINEnvelope;
    }

    /**
     * Sets the value of the isPINEnvelope property.
     * 
     */
    public void setIsPINEnvelope(boolean value) {
        this.isPINEnvelope = value;
    }

    /**
     * Gets the value of the isOwner property.
     * 
     */
    public boolean isIsOwner() {
        return isOwner;
    }

    /**
     * Sets the value of the isOwner property.
     * 
     */
    public void setIsOwner(boolean value) {
        this.isOwner = value;
    }

}
