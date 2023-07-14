//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2015.02.20 at 04:13:07 PM MSK 
//


package com.rssl.phizic.business.test.mobile7.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * �������/������� OMC
 * 
 * <p>Java class for InitIMAPayment complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="InitIMAPayment">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="toResource" type="{}Field"/>
 *         &lt;element name="fromResource" type="{}Field"/>
 *         &lt;element name="documentDate" type="{}Field"/>
 *         &lt;element name="documentNumber" type="{}Field"/>
 *         &lt;element name="buyAmount" type="{}Field"/>
 *         &lt;element name="sellAmount" type="{}Field"/>
 *         &lt;element name="exactAmount" type="{}Field"/>
 *         &lt;element name="operationCode" type="{}Field" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "InitIMAPayment", propOrder = {
    "toResource",
    "fromResource",
    "documentDate",
    "documentNumber",
    "buyAmount",
    "sellAmount",
    "exactAmount",
    "operationCode"
})
public class InitIMAPaymentDescriptor {

    @XmlElement(required = true)
    protected FieldDescriptor toResource;
    @XmlElement(required = true)
    protected FieldDescriptor fromResource;
    @XmlElement(required = true)
    protected FieldDescriptor documentDate;
    @XmlElement(required = true)
    protected FieldDescriptor documentNumber;
    @XmlElement(required = true)
    protected FieldDescriptor buyAmount;
    @XmlElement(required = true)
    protected FieldDescriptor sellAmount;
    @XmlElement(required = true)
    protected FieldDescriptor exactAmount;
    protected FieldDescriptor operationCode;

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

}
