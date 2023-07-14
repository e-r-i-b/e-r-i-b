//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2015.07.17 at 02:39:21 PM MSD 
//


package com.rssl.phizic.business.test.mobile9.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * ������� ����� ������ �������
 * 
 * <p>Java class for InitInternalPayment complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="InitInternalPayment">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="fromResource" type="{}Field"/>
 *         &lt;element name="toResource" type="{}Field"/>
 *         &lt;element name="buyAmount" type="{}Field"/>
 *         &lt;element name="sellAmount" type="{}Field"/>
 *         &lt;element name="exactAmount" type="{}Field"/>
 *         &lt;element name="operationCode" type="{}Field" minOccurs="0"/>
 *         &lt;element name="longOfferAllowed" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "InitInternalPayment", propOrder = {
    "fromResource",
    "toResource",
    "buyAmount",
    "sellAmount",
    "exactAmount",
    "operationCode",
    "longOfferAllowed"
})
public class InitInternalPaymentDescriptor {

    @XmlElement(required = true)
    protected FieldDescriptor fromResource;
    @XmlElement(required = true)
    protected FieldDescriptor toResource;
    @XmlElement(required = true)
    protected FieldDescriptor buyAmount;
    @XmlElement(required = true)
    protected FieldDescriptor sellAmount;
    @XmlElement(required = true)
    protected FieldDescriptor exactAmount;
    protected FieldDescriptor operationCode;
    protected boolean longOfferAllowed;

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
     * Gets the value of the toResource property.
     * 
     * @return
     *     possible object is
     *     {@link FieldDescriptor }
     *     
     */
    public FieldDescriptor getToResource() {
        return toResource;
    }

    /**
     * Sets the value of the toResource property.
     * 
     * @param value
     *     allowed object is
     *     {@link FieldDescriptor }
     *     
     */
    public void setToResource(FieldDescriptor value) {
        this.toResource = value;
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
     * Gets the value of the exactAmount property.
     * 
     * @return
     *     possible object is
     *     {@link FieldDescriptor }
     *     
     */
    public FieldDescriptor getExactAmount() {
        return exactAmount;
    }

    /**
     * Sets the value of the exactAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link FieldDescriptor }
     *     
     */
    public void setExactAmount(FieldDescriptor value) {
        this.exactAmount = value;
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
     * Gets the value of the longOfferAllowed property.
     * 
     */
    public boolean isLongOfferAllowed() {
        return longOfferAllowed;
    }

    /**
     * Sets the value of the longOfferAllowed property.
     * 
     */
    public void setLongOfferAllowed(boolean value) {
        this.longOfferAllowed = value;
    }

}
