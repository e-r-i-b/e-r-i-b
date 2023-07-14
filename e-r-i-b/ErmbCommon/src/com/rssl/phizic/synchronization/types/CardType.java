
package com.rssl.phizic.synchronization.types;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CardType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CardType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="number" type="{}StringType"/>
 *         &lt;element name="isMain" type="{}BooleanType"/>
 *         &lt;element name="type" type="{}StringType"/>
 *         &lt;element name="tb" type="{}String3Type"/>
 *         &lt;element name="isOwnerProfile" type="{}BooleanType" minOccurs="0"/>
 *         &lt;element name="name" type="{}StringType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CardType", propOrder = {
    "number",
    "isMain",
    "type",
    "tb",
    "isOwnerProfile",
    "name"
})
public class CardType {

    @XmlElement(required = true)
    protected String number;
    protected boolean isMain;
    @XmlElement(required = true)
    protected String type;
    @XmlElement(required = true)
    protected String tb;
    protected Boolean isOwnerProfile;
    @XmlElement(required = true)
    protected String name;

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
     */
    public boolean isIsMain() {
        return isMain;
    }

    /**
     * Sets the value of the isMain property.
     * 
     */
    public void setIsMain(boolean value) {
        this.isMain = value;
    }

    /**
     * Gets the value of the type property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the value of the type property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setType(String value) {
        this.type = value;
    }

    /**
     * Gets the value of the tb property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTb() {
        return tb;
    }

    /**
     * Sets the value of the tb property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTb(String value) {
        this.tb = value;
    }

    /**
     * Gets the value of the isOwnerProfile property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean getIsOwnerProfile() {
        return isOwnerProfile;
    }

    /**
     * Sets the value of the isOwnerProfile property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsOwnerProfile(Boolean value) {
        this.isOwnerProfile = value;
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

}
