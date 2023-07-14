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
 * ������� �������� ����
 * 
 * <p>Java class for DocRurPayment complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DocRurPayment">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="documentNumber" type="{}Field"/>
 *         &lt;element name="documentDate" type="{}Field"/>
 *         &lt;element name="receiverAccount" type="{}Field" minOccurs="0"/>
 *         &lt;element name="receiverPhone" type="{}Field" minOccurs="0"/>
 *         &lt;element name="receiverName" type="{}Field" minOccurs="0"/>
 *         &lt;element name="receiverINN" type="{}Field" minOccurs="0"/>
 *         &lt;element name="receiverAddress" type="{}Field" minOccurs="0"/>
 *         &lt;element name="bankInfo" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="bank" type="{}Field"/>
 *                   &lt;element name="receiverBIC" type="{}Field"/>
 *                   &lt;element name="receiverCorAccount" type="{}Field"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="ground" type="{}Field" minOccurs="0"/>
 *         &lt;element name="fromResource" type="{}Field"/>
 *         &lt;element name="sellAmount" type="{}Field" minOccurs="0"/>
 *         &lt;element name="sellCurrency" type="{}Field" minOccurs="0"/>
 *         &lt;element name="buyAmount" type="{}Field" minOccurs="0"/>
 *         &lt;element name="commission" type="{}Money" minOccurs="0"/>
 *         &lt;element name="operationCode" type="{}Field" minOccurs="0"/>
 *         &lt;element name="admissionDate" type="{}Field" minOccurs="0"/>
 *         &lt;element name="messageToReceiver" type="{}Field" minOccurs="0"/>
 *         &lt;element name="messageToReceiverStatus" type="{}Field" minOccurs="0"/>
 *         &lt;element name="receiverSubType" type="{}Field"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DocRurPayment", propOrder = {
    "documentNumber",
    "documentDate",
    "receiverAccount",
    "receiverPhone",
    "receiverName",
    "receiverINN",
    "receiverAddress",
    "bankInfo",
    "ground",
    "fromResource",
    "sellAmount",
    "sellCurrency",
    "buyAmount",
    "commission",
    "operationCode",
    "admissionDate",
    "messageToReceiver",
    "messageToReceiverStatus",
    "receiverSubType"
})
public class DocRurPaymentDescriptor {

    @XmlElement(required = true)
    protected FieldDescriptor documentNumber;
    @XmlElement(required = true)
    protected FieldDescriptor documentDate;
    protected FieldDescriptor receiverAccount;
    protected FieldDescriptor receiverPhone;
    protected FieldDescriptor receiverName;
    protected FieldDescriptor receiverINN;
    protected FieldDescriptor receiverAddress;
    protected DocRurPaymentDescriptor.BankInfo bankInfo;
    protected FieldDescriptor ground;
    @XmlElement(required = true)
    protected FieldDescriptor fromResource;
    protected FieldDescriptor sellAmount;
    protected FieldDescriptor sellCurrency;
    protected FieldDescriptor buyAmount;
    protected MoneyDescriptor commission;
    protected FieldDescriptor operationCode;
    protected FieldDescriptor admissionDate;
    protected FieldDescriptor messageToReceiver;
    protected FieldDescriptor messageToReceiverStatus;
    @XmlElement(required = true)
    protected FieldDescriptor receiverSubType;

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
     * Gets the value of the documentDate property.
     * 
     * @return
     *     possible object is
     *     {@link FieldDescriptor }
     *     
     */
    public FieldDescriptor getDocumentDate() {
        return documentDate;
    }

    /**
     * Sets the value of the documentDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link FieldDescriptor }
     *     
     */
    public void setDocumentDate(FieldDescriptor value) {
        this.documentDate = value;
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
     * Gets the value of the receiverPhone property.
     * 
     * @return
     *     possible object is
     *     {@link FieldDescriptor }
     *     
     */
    public FieldDescriptor getReceiverPhone() {
        return receiverPhone;
    }

    /**
     * Sets the value of the receiverPhone property.
     * 
     * @param value
     *     allowed object is
     *     {@link FieldDescriptor }
     *     
     */
    public void setReceiverPhone(FieldDescriptor value) {
        this.receiverPhone = value;
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
     * Gets the value of the receiverAddress property.
     * 
     * @return
     *     possible object is
     *     {@link FieldDescriptor }
     *     
     */
    public FieldDescriptor getReceiverAddress() {
        return receiverAddress;
    }

    /**
     * Sets the value of the receiverAddress property.
     * 
     * @param value
     *     allowed object is
     *     {@link FieldDescriptor }
     *     
     */
    public void setReceiverAddress(FieldDescriptor value) {
        this.receiverAddress = value;
    }

    /**
     * Gets the value of the bankInfo property.
     * 
     * @return
     *     possible object is
     *     {@link DocRurPaymentDescriptor.BankInfo }
     *     
     */
    public DocRurPaymentDescriptor.BankInfo getBankInfo() {
        return bankInfo;
    }

    /**
     * Sets the value of the bankInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link DocRurPaymentDescriptor.BankInfo }
     *     
     */
    public void setBankInfo(DocRurPaymentDescriptor.BankInfo value) {
        this.bankInfo = value;
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
     * Gets the value of the sellAmount property.
     * 
     * @return
     *     possible object is
     *     {@link FieldDescriptor }
     *     
     */
    public FieldDescriptor getSellAmount() {
        return sellAmount;
    }

    /**
     * Sets the value of the sellAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link FieldDescriptor }
     *     
     */
    public void setSellAmount(FieldDescriptor value) {
        this.sellAmount = value;
    }

    /**
     * Gets the value of the sellCurrency property.
     * 
     * @return
     *     possible object is
     *     {@link FieldDescriptor }
     *     
     */
    public FieldDescriptor getSellCurrency() {
        return sellCurrency;
    }

    /**
     * Sets the value of the sellCurrency property.
     * 
     * @param value
     *     allowed object is
     *     {@link FieldDescriptor }
     *     
     */
    public void setSellCurrency(FieldDescriptor value) {
        this.sellCurrency = value;
    }

    /**
     * Gets the value of the buyAmount property.
     * 
     * @return
     *     possible object is
     *     {@link FieldDescriptor }
     *     
     */
    public FieldDescriptor getBuyAmount() {
        return buyAmount;
    }

    /**
     * Sets the value of the buyAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link FieldDescriptor }
     *     
     */
    public void setBuyAmount(FieldDescriptor value) {
        this.buyAmount = value;
    }

    /**
     * Gets the value of the commission property.
     * 
     * @return
     *     possible object is
     *     {@link MoneyDescriptor }
     *     
     */
    public MoneyDescriptor getCommission() {
        return commission;
    }

    /**
     * Sets the value of the commission property.
     * 
     * @param value
     *     allowed object is
     *     {@link MoneyDescriptor }
     *     
     */
    public void setCommission(MoneyDescriptor value) {
        this.commission = value;
    }

    /**
     * Gets the value of the operationCode property.
     * 
     * @return
     *     possible object is
     *     {@link FieldDescriptor }
     *     
     */
    public FieldDescriptor getOperationCode() {
        return operationCode;
    }

    /**
     * Sets the value of the operationCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link FieldDescriptor }
     *     
     */
    public void setOperationCode(FieldDescriptor value) {
        this.operationCode = value;
    }

    /**
     * Gets the value of the admissionDate property.
     * 
     * @return
     *     possible object is
     *     {@link FieldDescriptor }
     *     
     */
    public FieldDescriptor getAdmissionDate() {
        return admissionDate;
    }

    /**
     * Sets the value of the admissionDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link FieldDescriptor }
     *     
     */
    public void setAdmissionDate(FieldDescriptor value) {
        this.admissionDate = value;
    }

    /**
     * Gets the value of the messageToReceiver property.
     * 
     * @return
     *     possible object is
     *     {@link FieldDescriptor }
     *     
     */
    public FieldDescriptor getMessageToReceiver() {
        return messageToReceiver;
    }

    /**
     * Sets the value of the messageToReceiver property.
     * 
     * @param value
     *     allowed object is
     *     {@link FieldDescriptor }
     *     
     */
    public void setMessageToReceiver(FieldDescriptor value) {
        this.messageToReceiver = value;
    }

    /**
     * Gets the value of the messageToReceiverStatus property.
     * 
     * @return
     *     possible object is
     *     {@link FieldDescriptor }
     *     
     */
    public FieldDescriptor getMessageToReceiverStatus() {
        return messageToReceiverStatus;
    }

    /**
     * Sets the value of the messageToReceiverStatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link FieldDescriptor }
     *     
     */
    public void setMessageToReceiverStatus(FieldDescriptor value) {
        this.messageToReceiverStatus = value;
    }

    /**
     * Gets the value of the receiverSubType property.
     * 
     * @return
     *     possible object is
     *     {@link FieldDescriptor }
     *     
     */
    public FieldDescriptor getReceiverSubType() {
        return receiverSubType;
    }

    /**
     * Sets the value of the receiverSubType property.
     * 
     * @param value
     *     allowed object is
     *     {@link FieldDescriptor }
     *     
     */
    public void setReceiverSubType(FieldDescriptor value) {
        this.receiverSubType = value;
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
     *         &lt;element name="bank" type="{}Field"/>
     *         &lt;element name="receiverBIC" type="{}Field"/>
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
    @XmlType(name = "", propOrder = {
        "bank",
        "receiverBIC",
        "receiverCorAccount"
    })
    public static class BankInfo {

        @XmlElement(required = true)
        protected FieldDescriptor bank;
        @XmlElement(required = true)
        protected FieldDescriptor receiverBIC;
        @XmlElement(required = true)
        protected FieldDescriptor receiverCorAccount;

        /**
         * Gets the value of the bank property.
         * 
         * @return
         *     possible object is
         *     {@link FieldDescriptor }
         *     
         */
        public FieldDescriptor getBank() {
            return bank;
        }

        /**
         * Sets the value of the bank property.
         * 
         * @param value
         *     allowed object is
         *     {@link FieldDescriptor }
         *     
         */
        public void setBank(FieldDescriptor value) {
            this.bank = value;
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

}
