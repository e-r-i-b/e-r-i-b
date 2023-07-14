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
 * Документ перевода организации
 * 
 * <p>Java class for DocumentCheckJurPayment complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DocumentCheckJurPayment">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="operationDate" type="{}Field"/>
 *         &lt;element name="operationTime" type="{}Field"/>
 *         &lt;element name="documentNumber" type="{}Field"/>
 *         &lt;element name="fromResource" type="{}Field" minOccurs="0"/>
 *         &lt;element name="amount" type="{}Field"/>
 *         &lt;element name="comission" type="{}Field" minOccurs="0"/>
 *         &lt;element name="authCode" type="{}Field" minOccurs="0"/>
 *         &lt;element name="personName" type="{}Field"/>
 *         &lt;element name="ground" type="{}Field"/>
 *         &lt;element name="taxStatus" type="{}Field" minOccurs="0"/>
 *         &lt;element name="taxKBK" type="{}Field" minOccurs="0"/>
 *         &lt;element name="taxOKATO" type="{}Field" minOccurs="0"/>
 *         &lt;element name="taxGround" type="{}Field" minOccurs="0"/>
 *         &lt;element name="taxPeriod" type="{}Field" minOccurs="0"/>
 *         &lt;element name="taxDocumentDate" type="{}Field" minOccurs="0"/>
 *         &lt;element name="taxDocumentNumber" type="{}Field" minOccurs="0"/>
 *         &lt;element name="taxType" type="{}Field" minOccurs="0"/>
 *         &lt;element name="receiverName" type="{}Field"/>
 *         &lt;element name="receiverBIC" type="{}Field"/>
 *         &lt;element name="receiverINN" type="{}Field"/>
 *         &lt;element name="receiverAccount" type="{}Field"/>
 *         &lt;element name="receiverCorAccount" type="{}Field"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DocumentCheckJurPayment", propOrder = {
    "operationDate",
    "operationTime",
    "documentNumber",
    "fromResource",
    "amount",
    "comission",
    "authCode",
    "personName",
    "ground",
    "taxStatus",
    "taxKBK",
    "taxOKATO",
    "taxGround",
    "taxPeriod",
    "taxDocumentDate",
    "taxDocumentNumber",
    "taxType",
    "receiverName",
    "receiverBIC",
    "receiverINN",
    "receiverAccount",
    "receiverCorAccount"
})
public class DocumentCheckJurPaymentDescriptor {

    @XmlElement(required = true)
    protected FieldDescriptor operationDate;
    @XmlElement(required = true)
    protected FieldDescriptor operationTime;
    @XmlElement(required = true)
    protected FieldDescriptor documentNumber;
    protected FieldDescriptor fromResource;
    @XmlElement(required = true)
    protected FieldDescriptor amount;
    protected FieldDescriptor comission;
    protected FieldDescriptor authCode;
    @XmlElement(required = true)
    protected FieldDescriptor personName;
    @XmlElement(required = true)
    protected FieldDescriptor ground;
    protected FieldDescriptor taxStatus;
    protected FieldDescriptor taxKBK;
    protected FieldDescriptor taxOKATO;
    protected FieldDescriptor taxGround;
    protected FieldDescriptor taxPeriod;
    protected FieldDescriptor taxDocumentDate;
    protected FieldDescriptor taxDocumentNumber;
    protected FieldDescriptor taxType;
    @XmlElement(required = true)
    protected FieldDescriptor receiverName;
    @XmlElement(required = true)
    protected FieldDescriptor receiverBIC;
    @XmlElement(required = true)
    protected FieldDescriptor receiverINN;
    @XmlElement(required = true)
    protected FieldDescriptor receiverAccount;
    @XmlElement(required = true)
    protected FieldDescriptor receiverCorAccount;

    /**
     * Gets the value of the operationDate property.
     * 
     * @return
     *     possible object is
     *     {@link FieldDescriptor }
     *     
     */
    public FieldDescriptor getOperationDate() {
        return operationDate;
    }

    /**
     * Sets the value of the operationDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link FieldDescriptor }
     *     
     */
    public void setOperationDate(FieldDescriptor value) {
        this.operationDate = value;
    }

    /**
     * Gets the value of the operationTime property.
     * 
     * @return
     *     possible object is
     *     {@link FieldDescriptor }
     *     
     */
    public FieldDescriptor getOperationTime() {
        return operationTime;
    }

    /**
     * Sets the value of the operationTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link FieldDescriptor }
     *     
     */
    public void setOperationTime(FieldDescriptor value) {
        this.operationTime = value;
    }

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
     * Gets the value of the fromResource property.
     * 
     * @return
     *     possible object is
     *     {@link FieldDescriptor }
     *     
     */
    public FieldDescriptor getFromResource() {
        return fromResource;
    }

    /**
     * Sets the value of the fromResource property.
     * 
     * @param value
     *     allowed object is
     *     {@link FieldDescriptor }
     *     
     */
    public void setFromResource(FieldDescriptor value) {
        this.fromResource = value;
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
     * Gets the value of the comission property.
     * 
     * @return
     *     possible object is
     *     {@link FieldDescriptor }
     *     
     */
    public FieldDescriptor getComission() {
        return comission;
    }

    /**
     * Sets the value of the comission property.
     * 
     * @param value
     *     allowed object is
     *     {@link FieldDescriptor }
     *     
     */
    public void setComission(FieldDescriptor value) {
        this.comission = value;
    }

    /**
     * Gets the value of the authCode property.
     * 
     * @return
     *     possible object is
     *     {@link FieldDescriptor }
     *     
     */
    public FieldDescriptor getAuthCode() {
        return authCode;
    }

    /**
     * Sets the value of the authCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link FieldDescriptor }
     *     
     */
    public void setAuthCode(FieldDescriptor value) {
        this.authCode = value;
    }

    /**
     * Gets the value of the personName property.
     * 
     * @return
     *     possible object is
     *     {@link FieldDescriptor }
     *     
     */
    public FieldDescriptor getPersonName() {
        return personName;
    }

    /**
     * Sets the value of the personName property.
     * 
     * @param value
     *     allowed object is
     *     {@link FieldDescriptor }
     *     
     */
    public void setPersonName(FieldDescriptor value) {
        this.personName = value;
    }

    /**
     * Gets the value of the ground property.
     * 
     * @return
     *     possible object is
     *     {@link FieldDescriptor }
     *     
     */
    public FieldDescriptor getGround() {
        return ground;
    }

    /**
     * Sets the value of the ground property.
     * 
     * @param value
     *     allowed object is
     *     {@link FieldDescriptor }
     *     
     */
    public void setGround(FieldDescriptor value) {
        this.ground = value;
    }

    /**
     * Gets the value of the taxStatus property.
     * 
     * @return
     *     possible object is
     *     {@link FieldDescriptor }
     *     
     */
    public FieldDescriptor getTaxStatus() {
        return taxStatus;
    }

    /**
     * Sets the value of the taxStatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link FieldDescriptor }
     *     
     */
    public void setTaxStatus(FieldDescriptor value) {
        this.taxStatus = value;
    }

    /**
     * Gets the value of the taxKBK property.
     * 
     * @return
     *     possible object is
     *     {@link FieldDescriptor }
     *     
     */
    public FieldDescriptor getTaxKBK() {
        return taxKBK;
    }

    /**
     * Sets the value of the taxKBK property.
     * 
     * @param value
     *     allowed object is
     *     {@link FieldDescriptor }
     *     
     */
    public void setTaxKBK(FieldDescriptor value) {
        this.taxKBK = value;
    }

    /**
     * Gets the value of the taxOKATO property.
     * 
     * @return
     *     possible object is
     *     {@link FieldDescriptor }
     *     
     */
    public FieldDescriptor getTaxOKATO() {
        return taxOKATO;
    }

    /**
     * Sets the value of the taxOKATO property.
     * 
     * @param value
     *     allowed object is
     *     {@link FieldDescriptor }
     *     
     */
    public void setTaxOKATO(FieldDescriptor value) {
        this.taxOKATO = value;
    }

    /**
     * Gets the value of the taxGround property.
     * 
     * @return
     *     possible object is
     *     {@link FieldDescriptor }
     *     
     */
    public FieldDescriptor getTaxGround() {
        return taxGround;
    }

    /**
     * Sets the value of the taxGround property.
     * 
     * @param value
     *     allowed object is
     *     {@link FieldDescriptor }
     *     
     */
    public void setTaxGround(FieldDescriptor value) {
        this.taxGround = value;
    }

    /**
     * Gets the value of the taxPeriod property.
     * 
     * @return
     *     possible object is
     *     {@link FieldDescriptor }
     *     
     */
    public FieldDescriptor getTaxPeriod() {
        return taxPeriod;
    }

    /**
     * Sets the value of the taxPeriod property.
     * 
     * @param value
     *     allowed object is
     *     {@link FieldDescriptor }
     *     
     */
    public void setTaxPeriod(FieldDescriptor value) {
        this.taxPeriod = value;
    }

    /**
     * Gets the value of the taxDocumentDate property.
     * 
     * @return
     *     possible object is
     *     {@link FieldDescriptor }
     *     
     */
    public FieldDescriptor getTaxDocumentDate() {
        return taxDocumentDate;
    }

    /**
     * Sets the value of the taxDocumentDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link FieldDescriptor }
     *     
     */
    public void setTaxDocumentDate(FieldDescriptor value) {
        this.taxDocumentDate = value;
    }

    /**
     * Gets the value of the taxDocumentNumber property.
     * 
     * @return
     *     possible object is
     *     {@link FieldDescriptor }
     *     
     */
    public FieldDescriptor getTaxDocumentNumber() {
        return taxDocumentNumber;
    }

    /**
     * Sets the value of the taxDocumentNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link FieldDescriptor }
     *     
     */
    public void setTaxDocumentNumber(FieldDescriptor value) {
        this.taxDocumentNumber = value;
    }

    /**
     * Gets the value of the taxType property.
     * 
     * @return
     *     possible object is
     *     {@link FieldDescriptor }
     *     
     */
    public FieldDescriptor getTaxType() {
        return taxType;
    }

    /**
     * Sets the value of the taxType property.
     * 
     * @param value
     *     allowed object is
     *     {@link FieldDescriptor }
     *     
     */
    public void setTaxType(FieldDescriptor value) {
        this.taxType = value;
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
     * Gets the value of the receiverBIC property.
     * 
     * @return
     *     possible object is
     *     {@link FieldDescriptor }
     *     
     */
    public FieldDescriptor getReceiverBIC() {
        return receiverBIC;
    }

    /**
     * Sets the value of the receiverBIC property.
     * 
     * @param value
     *     allowed object is
     *     {@link FieldDescriptor }
     *     
     */
    public void setReceiverBIC(FieldDescriptor value) {
        this.receiverBIC = value;
    }

    /**
     * Gets the value of the receiverINN property.
     * 
     * @return
     *     possible object is
     *     {@link FieldDescriptor }
     *     
     */
    public FieldDescriptor getReceiverINN() {
        return receiverINN;
    }

    /**
     * Sets the value of the receiverINN property.
     * 
     * @param value
     *     allowed object is
     *     {@link FieldDescriptor }
     *     
     */
    public void setReceiverINN(FieldDescriptor value) {
        this.receiverINN = value;
    }

    /**
     * Gets the value of the receiverAccount property.
     * 
     * @return
     *     possible object is
     *     {@link FieldDescriptor }
     *     
     */
    public FieldDescriptor getReceiverAccount() {
        return receiverAccount;
    }

    /**
     * Sets the value of the receiverAccount property.
     * 
     * @param value
     *     allowed object is
     *     {@link FieldDescriptor }
     *     
     */
    public void setReceiverAccount(FieldDescriptor value) {
        this.receiverAccount = value;
    }

    /**
     * Gets the value of the receiverCorAccount property.
     * 
     * @return
     *     possible object is
     *     {@link FieldDescriptor }
     *     
     */
    public FieldDescriptor getReceiverCorAccount() {
        return receiverCorAccount;
    }

    /**
     * Sets the value of the receiverCorAccount property.
     * 
     * @param value
     *     allowed object is
     *     {@link FieldDescriptor }
     *     
     */
    public void setReceiverCorAccount(FieldDescriptor value) {
        this.receiverCorAccount = value;
    }

}
