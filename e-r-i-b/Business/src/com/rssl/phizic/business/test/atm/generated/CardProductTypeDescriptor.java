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
 * <p>Java class for CardProductType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CardProductType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="code" type="{}Resource"/>
 *         &lt;element name="name" type="{}string"/>
 *         &lt;element name="description" type="{}string" minOccurs="0"/>
 *         &lt;element name="number" type="{}string" minOccurs="0"/>
 *         &lt;element name="isMain" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="type" type="{}CardType" minOccurs="0"/>
 *         &lt;element name="availableLimit" type="{}Money" minOccurs="0"/>
 *         &lt;element name="state" type="{}CardState"/>
 *         &lt;element name="mainCardNumber" type="{}string" minOccurs="0"/>
 *         &lt;element name="additionalCardType" type="{}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CardProductType", propOrder = {
    "id",
    "code",
    "name",
    "description",
    "number",
    "isMain",
    "type",
    "availableLimit",
    "state",
    "mainCardNumber",
    "additionalCardType"
})
public class CardProductTypeDescriptor {

    protected int id;
    @XmlElement(required = true)
    protected String code;
    @XmlElement(required = true)
    protected String name;
    protected String description;
    protected String number;
    protected Boolean isMain;
    protected CardTypeDescriptor type;
    protected MoneyDescriptor availableLimit;
    @XmlElement(required = true)
    protected CardStateDescriptor state;
    protected String mainCardNumber;
    protected String additionalCardType;

    /**
     * Gets the value of the id property.
     * 
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     */
    public void setId(int value) {
        this.id = value;
    }

    /**
     * Gets the value of the code property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCode() {
        return code;
    }

    /**
     * Sets the value of the code property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCode(String value) {
        this.code = value;
    }

    /**
     * Gets the value of the name property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * Gets the value of the description property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the value of the description property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescription(String value) {
        this.description = value;
    }

    /**
     * Gets the value of the number property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumber() {
        return number;
    }

    /**
     * Sets the value of the number property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumber(String value) {
        this.number = value;
    }

    /**
     * Gets the value of the isMain property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean getIsMain() {
        return isMain;
    }

    /**
     * Sets the value of the isMain property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsMain(Boolean value) {
        this.isMain = value;
    }

    /**
     * Gets the value of the type property.
     * 
     * @return
     *     possible object is
     *     {@link CardTypeDescriptor }
     *     
     */
    public CardTypeDescriptor getType() {
        return type;
    }

    /**
     * Sets the value of the type property.
     * 
     * @param value
     *     allowed object is
     *     {@link CardTypeDescriptor }
     *     
     */
    public void setType(CardTypeDescriptor value) {
        this.type = value;
    }

    /**
     * Gets the value of the availableLimit property.
     * 
     * @return
     *     possible object is
     *     {@link MoneyDescriptor }
     *     
     */
    public MoneyDescriptor getAvailableLimit() {
        return availableLimit;
    }

    /**
     * Sets the value of the availableLimit property.
     * 
     * @param value
     *     allowed object is
     *     {@link MoneyDescriptor }
     *     
     */
    public void setAvailableLimit(MoneyDescriptor value) {
        this.availableLimit = value;
    }

    /**
     * Gets the value of the state property.
     * 
     * @return
     *     possible object is
     *     {@link CardStateDescriptor }
     *     
     */
    public CardStateDescriptor getState() {
        return state;
    }

    /**
     * Sets the value of the state property.
     * 
     * @param value
     *     allowed object is
     *     {@link CardStateDescriptor }
     *     
     */
    public void setState(CardStateDescriptor value) {
        this.state = value;
    }

    /**
     * Gets the value of the mainCardNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMainCardNumber() {
        return mainCardNumber;
    }

    /**
     * Sets the value of the mainCardNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMainCardNumber(String value) {
        this.mainCardNumber = value;
    }

    /**
     * Gets the value of the additionalCardType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAdditionalCardType() {
        return additionalCardType;
    }

    /**
     * Sets the value of the additionalCardType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAdditionalCardType(String value) {
        this.additionalCardType = value;
    }

}