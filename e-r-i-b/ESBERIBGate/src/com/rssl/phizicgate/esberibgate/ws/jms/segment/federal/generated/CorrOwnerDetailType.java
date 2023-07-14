
package com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Детальная информации о владельце счета
 * 
 * <p>Java class for CorrOwnerDetail_Type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CorrOwnerDetail_Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}PersonName"/>
 *         &lt;element ref="{}IdentityCard"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CorrOwnerDetail_Type", propOrder = {
    "personName",
    "identityCard"
})
public class CorrOwnerDetailType {

    @XmlElement(name = "PersonName", required = true)
    protected PersonName personName;
    @XmlElement(name = "IdentityCard", required = true)
    protected IdentityCardType identityCard;

    /**
     * Gets the value of the personName property.
     * 
     * @return
     *     possible object is
     *     {@link PersonName }
     *     
     */
    public PersonName getPersonName() {
        return personName;
    }

    /**
     * Sets the value of the personName property.
     * 
     * @param value
     *     allowed object is
     *     {@link PersonName }
     *     
     */
    public void setPersonName(PersonName value) {
        this.personName = value;
    }

    /**
     * Gets the value of the identityCard property.
     * 
     * @return
     *     possible object is
     *     {@link IdentityCardType }
     *     
     */
    public IdentityCardType getIdentityCard() {
        return identityCard;
    }

    /**
     * Sets the value of the identityCard property.
     * 
     * @param value
     *     allowed object is
     *     {@link IdentityCardType }
     *     
     */
    public void setIdentityCard(IdentityCardType value) {
        this.identityCard = value;
    }

}
