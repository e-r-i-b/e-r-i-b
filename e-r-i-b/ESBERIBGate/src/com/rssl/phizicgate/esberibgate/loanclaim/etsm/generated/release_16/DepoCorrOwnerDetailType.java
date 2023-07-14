
package com.rssl.phizicgate.esberibgate.loanclaim.etsm.generated.release_16;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Детальная информации о владельце счета (ДЕПО).
 * 
 * <p>Java class for DepoCorrOwnerDetail_Type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DepoCorrOwnerDetail_Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="PersonName" type="{}PersonName_Type"/>
 *         &lt;element name="IdentityCard" type="{}IdentityCard_Type"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DepoCorrOwnerDetail_Type", propOrder = {
    "personName",
    "identityCard"
})
public class DepoCorrOwnerDetailType {

    @XmlElement(name = "PersonName", required = true)
    protected PersonNameType personName;
    @XmlElement(name = "IdentityCard", required = true)
    protected IdentityCard identityCard;

    /**
     * Gets the value of the personName property.
     * 
     * @return
     *     possible object is
     *     {@link PersonNameType }
     *     
     */
    public PersonNameType getPersonName() {
        return personName;
    }

    /**
     * Sets the value of the personName property.
     * 
     * @param value
     *     allowed object is
     *     {@link PersonNameType }
     *     
     */
    public void setPersonName(PersonNameType value) {
        this.personName = value;
    }

    /**
     * Gets the value of the identityCard property.
     * 
     * @return
     *     possible object is
     *     {@link IdentityCard }
     *     
     */
    public IdentityCard getIdentityCard() {
        return identityCard;
    }

    /**
     * Sets the value of the identityCard property.
     * 
     * @param value
     *     allowed object is
     *     {@link IdentityCard }
     *     
     */
    public void setIdentityCard(IdentityCard value) {
        this.identityCard = value;
    }

}
