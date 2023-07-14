//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2015.06.26 at 01:33:00 PM MSD 
//


package com.rssl.phizic.business.test.atm.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * ������ ����������� iqwave
 * 
 * <p>Java class for InitRefuseAutoPaymentPayment complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="InitRefuseAutoPaymentPayment">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="documentNumber" type="{}Field" minOccurs="0"/>
 *         &lt;element name="cardId" type="{}Field"/>
 *         &lt;element name="card" type="{}Field"/>
 *         &lt;element name="receiverName" type="{}Field"/>
 *         &lt;element name="requisite" type="{}Field"/>
 *         &lt;element name="amount" type="{}Field" minOccurs="0"/>
 *         &lt;element name="executionEventType" type="{}Field"/>
 *         &lt;element name="autoPaymentFloorLimit" type="{}Field" minOccurs="0"/>
 *         &lt;element name="autoPaymentFloorCurrency" type="{}Field" minOccurs="0"/>
 *         &lt;element name="autoPaymentTotalAmountLimit" type="{}Field" minOccurs="0"/>
 *         &lt;element name="autoPaymentTotalAmountCurrency" type="{}Field" minOccurs="0"/>
 *         &lt;element name="autoPaymentStartDate" type="{}Field" minOccurs="0"/>
 *         &lt;element name="autoPaymentName" type="{}Field"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "InitRefuseAutoPaymentPayment", propOrder = {
    "documentNumber",
    "cardId",
    "card",
    "receiverName",
    "requisite",
    "amount",
    "executionEventType",
    "autoPaymentFloorLimit",
    "autoPaymentFloorCurrency",
    "autoPaymentTotalAmountLimit",
    "autoPaymentTotalAmountCurrency",
    "autoPaymentStartDate",
    "autoPaymentName"
})
public class InitRefuseAutoPaymentPaymentDescriptor {

    protected FieldDescriptor documentNumber;
    @XmlElement(required = true)
    protected FieldDescriptor cardId;
    @XmlElement(required = true)
    protected FieldDescriptor card;
    @XmlElement(required = true)
    protected FieldDescriptor receiverName;
    @XmlElement(required = true)
    protected FieldDescriptor requisite;
    protected FieldDescriptor amount;
    @XmlElement(required = true)
    protected FieldDescriptor executionEventType;
    protected FieldDescriptor autoPaymentFloorLimit;
    protected FieldDescriptor autoPaymentFloorCurrency;
    protected FieldDescriptor autoPaymentTotalAmountLimit;
    protected FieldDescriptor autoPaymentTotalAmountCurrency;
    protected FieldDescriptor autoPaymentStartDate;
    @XmlElement(required = true)
    protected FieldDescriptor autoPaymentName;

    /**
     * Gets the value of the documentNumber property.
     * 
     * @return
     *     possible object is
     *     {@link FieldDescriptor }
     *     
     */
    public FieldDescriptor getDocumentNumber() {
        return documentNumber;
    }

    /**
     * Sets the value of the documentNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link FieldDescriptor }
     *     
     */
    public void setDocumentNumber(FieldDescriptor value) {
        this.documentNumber = value;
    }

    /**
     * Gets the value of the cardId property.
     * 
     * @return
     *     possible object is
     *     {@link FieldDescriptor }
     *     
     */
    public FieldDescriptor getCardId() {
        return cardId;
    }

    /**
     * Sets the value of the cardId property.
     * 
     * @param value
     *     allowed object is
     *     {@link FieldDescriptor }
     *     
     */
    public void setCardId(FieldDescriptor value) {
        this.cardId = value;
    }

    /**
     * Gets the value of the card property.
     * 
     * @return
     *     possible object is
     *     {@link FieldDescriptor }
     *     
     */
    public FieldDescriptor getCard() {
        return card;
    }

    /**
     * Sets the value of the card property.
     * 
     * @param value
     *     allowed object is
     *     {@link FieldDescriptor }
     *     
     */
    public void setCard(FieldDescriptor value) {
        this.card = value;
    }

    /**
     * Gets the value of the receiverName property.
     * 
     * @return
     *     possible object is
     *     {@link FieldDescriptor }
     *     
     */
    public FieldDescriptor getReceiverName() {
        return receiverName;
    }

    /**
     * Sets the value of the receiverName property.
     * 
     * @param value
     *     allowed object is
     *     {@link FieldDescriptor }
     *     
     */
    public void setReceiverName(FieldDescriptor value) {
        this.receiverName = value;
    }

    /**
     * Gets the value of the requisite property.
     * 
     * @return
     *     possible object is
     *     {@link FieldDescriptor }
     *     
     */
    public FieldDescriptor getRequisite() {
        return requisite;
    }

    /**
     * Sets the value of the requisite property.
     * 
     * @param value
     *     allowed object is
     *     {@link FieldDescriptor }
     *     
     */
    public void setRequisite(FieldDescriptor value) {
        this.requisite = value;
    }

    /**
     * Gets the value of the amount property.
     * 
     * @return
     *     possible object is
     *     {@link FieldDescriptor }
     *     
     */
    public FieldDescriptor getAmount() {
        return amount;
    }

    /**
     * Sets the value of the amount property.
     * 
     * @param value
     *     allowed object is
     *     {@link FieldDescriptor }
     *     
     */
    public void setAmount(FieldDescriptor value) {
        this.amount = value;
    }

    /**
     * Gets the value of the executionEventType property.
     * 
     * @return
     *     possible object is
     *     {@link FieldDescriptor }
     *     
     */
    public FieldDescriptor getExecutionEventType() {
        return executionEventType;
    }

    /**
     * Sets the value of the executionEventType property.
     * 
     * @param value
     *     allowed object is
     *     {@link FieldDescriptor }
     *     
     */
    public void setExecutionEventType(FieldDescriptor value) {
        this.executionEventType = value;
    }

    /**
     * Gets the value of the autoPaymentFloorLimit property.
     * 
     * @return
     *     possible object is
     *     {@link FieldDescriptor }
     *     
     */
    public FieldDescriptor getAutoPaymentFloorLimit() {
        return autoPaymentFloorLimit;
    }

    /**
     * Sets the value of the autoPaymentFloorLimit property.
     * 
     * @param value
     *     allowed object is
     *     {@link FieldDescriptor }
     *     
     */
    public void setAutoPaymentFloorLimit(FieldDescriptor value) {
        this.autoPaymentFloorLimit = value;
    }

    /**
     * Gets the value of the autoPaymentFloorCurrency property.
     * 
     * @return
     *     possible object is
     *     {@link FieldDescriptor }
     *     
     */
    public FieldDescriptor getAutoPaymentFloorCurrency() {
        return autoPaymentFloorCurrency;
    }

    /**
     * Sets the value of the autoPaymentFloorCurrency property.
     * 
     * @param value
     *     allowed object is
     *     {@link FieldDescriptor }
     *     
     */
    public void setAutoPaymentFloorCurrency(FieldDescriptor value) {
        this.autoPaymentFloorCurrency = value;
    }

    /**
     * Gets the value of the autoPaymentTotalAmountLimit property.
     * 
     * @return
     *     possible object is
     *     {@link FieldDescriptor }
     *     
     */
    public FieldDescriptor getAutoPaymentTotalAmountLimit() {
        return autoPaymentTotalAmountLimit;
    }

    /**
     * Sets the value of the autoPaymentTotalAmountLimit property.
     * 
     * @param value
     *     allowed object is
     *     {@link FieldDescriptor }
     *     
     */
    public void setAutoPaymentTotalAmountLimit(FieldDescriptor value) {
        this.autoPaymentTotalAmountLimit = value;
    }

    /**
     * Gets the value of the autoPaymentTotalAmountCurrency property.
     * 
     * @return
     *     possible object is
     *     {@link FieldDescriptor }
     *     
     */
    public FieldDescriptor getAutoPaymentTotalAmountCurrency() {
        return autoPaymentTotalAmountCurrency;
    }

    /**
     * Sets the value of the autoPaymentTotalAmountCurrency property.
     * 
     * @param value
     *     allowed object is
     *     {@link FieldDescriptor }
     *     
     */
    public void setAutoPaymentTotalAmountCurrency(FieldDescriptor value) {
        this.autoPaymentTotalAmountCurrency = value;
    }

    /**
     * Gets the value of the autoPaymentStartDate property.
     * 
     * @return
     *     possible object is
     *     {@link FieldDescriptor }
     *     
     */
    public FieldDescriptor getAutoPaymentStartDate() {
        return autoPaymentStartDate;
    }

    /**
     * Sets the value of the autoPaymentStartDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link FieldDescriptor }
     *     
     */
    public void setAutoPaymentStartDate(FieldDescriptor value) {
        this.autoPaymentStartDate = value;
    }

    /**
     * Gets the value of the autoPaymentName property.
     * 
     * @return
     *     possible object is
     *     {@link FieldDescriptor }
     *     
     */
    public FieldDescriptor getAutoPaymentName() {
        return autoPaymentName;
    }

    /**
     * Sets the value of the autoPaymentName property.
     * 
     * @param value
     *     allowed object is
     *     {@link FieldDescriptor }
     *     
     */
    public void setAutoPaymentName(FieldDescriptor value) {
        this.autoPaymentName = value;
    }

}
