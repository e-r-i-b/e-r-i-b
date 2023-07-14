//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2013.09.13 at 03:37:43 PM MSD 
//


package com.rssl.phizic.business.test.mobile6.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Перевод организации
 * 
 * <p>Java class for InitJurPayment complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="InitJurPayment">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="operationUID" type="{}string"/>
 *         &lt;element name="receiverAccount" type="{}Field"/>
 *         &lt;element name="receiverINN" type="{}Field"/>
 *         &lt;element name="receiverBIC" type="{}Field"/>
 *         &lt;element name="externalReceiver" type="{}Field" minOccurs="0"/>
 *         &lt;element name="availableFromResources" type="{}Field"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "InitJurPayment", propOrder = {
    "operationUID",
    "receiverAccount",
    "receiverINN",
    "receiverBIC",
    "externalReceiver",
    "availableFromResources"
})
public class InitJurPaymentDescriptor {

    @XmlElement(required = true)
    protected String operationUID;
    @XmlElement(required = true)
    protected FieldDescriptor receiverAccount;
    @XmlElement(required = true)
    protected FieldDescriptor receiverINN;
    @XmlElement(required = true)
    protected FieldDescriptor receiverBIC;
    protected FieldDescriptor externalReceiver;
    @XmlElement(required = true)
    protected FieldDescriptor availableFromResources;

    /**
     * Gets the value of the operationUID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOperationUID() {
        return operationUID;
    }

    /**
     * Sets the value of the operationUID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOperationUID(String value) {
        this.operationUID = value;
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
     * Gets the value of the externalReceiver property.
     * 
     * @return
     *     possible object is
     *     {@link FieldDescriptor }
     *     
     */
    public FieldDescriptor getExternalReceiver() {
        return externalReceiver;
    }

    /**
     * Sets the value of the externalReceiver property.
     * 
     * @param value
     *     allowed object is
     *     {@link FieldDescriptor }
     *     
     */
    public void setExternalReceiver(FieldDescriptor value) {
        this.externalReceiver = value;
    }

    /**
     * Gets the value of the availableFromResources property.
     * 
     * @return
     *     possible object is
     *     {@link FieldDescriptor }
     *     
     */
    public FieldDescriptor getAvailableFromResources() {
        return availableFromResources;
    }

    /**
     * Sets the value of the availableFromResources property.
     * 
     * @param value
     *     allowed object is
     *     {@link FieldDescriptor }
     *     
     */
    public void setAvailableFromResources(FieldDescriptor value) {
        this.availableFromResources = value;
    }

}
