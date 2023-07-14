
package com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.generated;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for AdditionalCardInfo_Type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AdditionalCardInfo_Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}SystemId"/>
 *         &lt;element ref="{}CardNum"/>
 *         &lt;element name="Cards" maxOccurs="unbounded" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element ref="{}CardNum"/>
 *                   &lt;element ref="{}SystemId"/>
 *                   &lt;element name="AdditionalCard" type="{}String"/>
 *                   &lt;element ref="{}BankInfo"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element ref="{}Status"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AdditionalCardInfo_Type", propOrder = {
    "systemId",
    "cardNum",
    "cards",
    "status"
})
@XmlRootElement(name = "AdditionalCardInfo")
public class AdditionalCardInfo {

    @XmlElement(name = "SystemId", required = true)
    protected String systemId;
    @XmlElement(name = "CardNum", required = true)
    protected String cardNum;
    @XmlElement(name = "Cards")
    protected List<AdditionalCardInfo.Cards> cards;
    @XmlElement(name = "Status", required = true)
    protected StatusType status;

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
     * Gets the value of the cards property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the cards property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCards().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link AdditionalCardInfo.Cards }
     * 
     * 
     */
    public List<AdditionalCardInfo.Cards> getCards() {
        if (cards == null) {
            cards = new ArrayList<AdditionalCardInfo.Cards>();
        }
        return this.cards;
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
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element ref="{}CardNum"/>
     *         &lt;element ref="{}SystemId"/>
     *         &lt;element name="AdditionalCard" type="{}String"/>
     *         &lt;element ref="{}BankInfo"/>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "cardNum",
        "systemId",
        "additionalCard",
        "bankInfo"
    })
    public static class Cards {

        @XmlElement(name = "CardNum", required = true)
        protected String cardNum;
        @XmlElement(name = "SystemId", required = true)
        protected String systemId;
        @XmlElement(name = "AdditionalCard", required = true)
        protected String additionalCard;
        @XmlElement(name = "BankInfo", required = true)
        protected BankInfoType bankInfo;

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
         * Gets the value of the additionalCard property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getAdditionalCard() {
            return additionalCard;
        }

        /**
         * Sets the value of the additionalCard property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setAdditionalCard(String value) {
            this.additionalCard = value;
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

    }

}
