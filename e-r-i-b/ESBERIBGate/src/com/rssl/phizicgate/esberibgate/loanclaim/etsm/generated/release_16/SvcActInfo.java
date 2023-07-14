
package com.rssl.phizicgate.esberibgate.loanclaim.etsm.generated.release_16;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for SvcActInfo_Type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SvcActInfo_Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}Status"/>
 *         &lt;element name="SvcAct" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element ref="{}SvcAcctId"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element ref="{}DepAcctIdFrom" minOccurs="0"/>
 *         &lt;element ref="{}CardAcctIdFrom" minOccurs="0"/>
 *         &lt;element name="RecBIC" type="{}String" minOccurs="0"/>
 *         &lt;element name="RecCorrAccount" type="{}String" minOccurs="0"/>
 *         &lt;element name="RecCalcAccount" type="{}String" minOccurs="0"/>
 *         &lt;element name="RecAcctCur" type="{}AcctCur_Type" minOccurs="0"/>
 *         &lt;element name="RecINN" type="{}String" minOccurs="0"/>
 *         &lt;element name="KPPTo" type="{}String" minOccurs="0"/>
 *         &lt;element name="Purpose" type="{}String" minOccurs="0"/>
 *         &lt;element name="RecipientName" type="{}String" minOccurs="0"/>
 *         &lt;element name="PmtKind" type="{}String" minOccurs="0"/>
 *         &lt;element ref="{}Regular" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SvcActInfo_Type", propOrder = {
    "status",
    "svcAct",
    "depAcctIdFrom",
    "cardAcctIdFrom",
    "recBIC",
    "recCorrAccount",
    "recCalcAccount",
    "recAcctCur",
    "recINN",
    "kppTo",
    "purpose",
    "recipientName",
    "pmtKind",
    "regular"
})
@XmlRootElement(name = "SvcActInfo")
public class SvcActInfo {

    @XmlElement(name = "Status", required = true)
    protected Status status;
    @XmlElement(name = "SvcAct")
    protected SvcActInfo.SvcAct svcAct;
    @XmlElement(name = "DepAcctIdFrom")
    protected DepAcctIdType depAcctIdFrom;
    @XmlElement(name = "CardAcctIdFrom")
    protected CardAcctIdType cardAcctIdFrom;
    @XmlElement(name = "RecBIC")
    protected String recBIC;
    @XmlElement(name = "RecCorrAccount")
    protected String recCorrAccount;
    @XmlElement(name = "RecCalcAccount")
    protected String recCalcAccount;
    @XmlElement(name = "RecAcctCur")
    protected String recAcctCur;
    @XmlElement(name = "RecINN")
    protected String recINN;
    @XmlElement(name = "KPPTo")
    protected String kppTo;
    @XmlElement(name = "Purpose")
    protected String purpose;
    @XmlElement(name = "RecipientName")
    protected String recipientName;
    @XmlElement(name = "PmtKind")
    protected String pmtKind;
    @XmlElement(name = "Regular")
    protected Regular regular;

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
     * Gets the value of the svcAct property.
     * 
     * @return
     *     possible object is
     *     {@link SvcActInfo.SvcAct }
     *     
     */
    public SvcActInfo.SvcAct getSvcAct() {
        return svcAct;
    }

    /**
     * Sets the value of the svcAct property.
     * 
     * @param value
     *     allowed object is
     *     {@link SvcActInfo.SvcAct }
     *     
     */
    public void setSvcAct(SvcActInfo.SvcAct value) {
        this.svcAct = value;
    }

    /**
     * Gets the value of the depAcctIdFrom property.
     * 
     * @return
     *     possible object is
     *     {@link DepAcctIdType }
     *     
     */
    public DepAcctIdType getDepAcctIdFrom() {
        return depAcctIdFrom;
    }

    /**
     * Sets the value of the depAcctIdFrom property.
     * 
     * @param value
     *     allowed object is
     *     {@link DepAcctIdType }
     *     
     */
    public void setDepAcctIdFrom(DepAcctIdType value) {
        this.depAcctIdFrom = value;
    }

    /**
     * Gets the value of the cardAcctIdFrom property.
     * 
     * @return
     *     possible object is
     *     {@link CardAcctIdType }
     *     
     */
    public CardAcctIdType getCardAcctIdFrom() {
        return cardAcctIdFrom;
    }

    /**
     * Sets the value of the cardAcctIdFrom property.
     * 
     * @param value
     *     allowed object is
     *     {@link CardAcctIdType }
     *     
     */
    public void setCardAcctIdFrom(CardAcctIdType value) {
        this.cardAcctIdFrom = value;
    }

    /**
     * Gets the value of the recBIC property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRecBIC() {
        return recBIC;
    }

    /**
     * Sets the value of the recBIC property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRecBIC(String value) {
        this.recBIC = value;
    }

    /**
     * Gets the value of the recCorrAccount property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRecCorrAccount() {
        return recCorrAccount;
    }

    /**
     * Sets the value of the recCorrAccount property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRecCorrAccount(String value) {
        this.recCorrAccount = value;
    }

    /**
     * Gets the value of the recCalcAccount property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRecCalcAccount() {
        return recCalcAccount;
    }

    /**
     * Sets the value of the recCalcAccount property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRecCalcAccount(String value) {
        this.recCalcAccount = value;
    }

    /**
     * Gets the value of the recAcctCur property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRecAcctCur() {
        return recAcctCur;
    }

    /**
     * Sets the value of the recAcctCur property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRecAcctCur(String value) {
        this.recAcctCur = value;
    }

    /**
     * Gets the value of the recINN property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRecINN() {
        return recINN;
    }

    /**
     * Sets the value of the recINN property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRecINN(String value) {
        this.recINN = value;
    }

    /**
     * Gets the value of the kppTo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKPPTo() {
        return kppTo;
    }

    /**
     * Sets the value of the kppTo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKPPTo(String value) {
        this.kppTo = value;
    }

    /**
     * Gets the value of the purpose property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPurpose() {
        return purpose;
    }

    /**
     * Sets the value of the purpose property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPurpose(String value) {
        this.purpose = value;
    }

    /**
     * Gets the value of the recipientName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRecipientName() {
        return recipientName;
    }

    /**
     * Sets the value of the recipientName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRecipientName(String value) {
        this.recipientName = value;
    }

    /**
     * Gets the value of the pmtKind property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPmtKind() {
        return pmtKind;
    }

    /**
     * Sets the value of the pmtKind property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPmtKind(String value) {
        this.pmtKind = value;
    }

    /**
     * Gets the value of the regular property.
     * 
     * @return
     *     possible object is
     *     {@link Regular }
     *     
     */
    public Regular getRegular() {
        return regular;
    }

    /**
     * Sets the value of the regular property.
     * 
     * @param value
     *     allowed object is
     *     {@link Regular }
     *     
     */
    public void setRegular(Regular value) {
        this.regular = value;
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
     *         &lt;element ref="{}SvcAcctId"/>
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
        "svcAcctId"
    })
    public static class SvcAct {

        @XmlElement(name = "SvcAcctId", required = true)
        protected SvcAcctId svcAcctId;

        /**
         * Gets the value of the svcAcctId property.
         * 
         * @return
         *     possible object is
         *     {@link SvcAcctId }
         *     
         */
        public SvcAcctId getSvcAcctId() {
            return svcAcctId;
        }

        /**
         * Sets the value of the svcAcctId property.
         * 
         * @param value
         *     allowed object is
         *     {@link SvcAcctId }
         *     
         */
        public void setSvcAcctId(SvcAcctId value) {
            this.svcAcctId = value;
        }

    }

}
