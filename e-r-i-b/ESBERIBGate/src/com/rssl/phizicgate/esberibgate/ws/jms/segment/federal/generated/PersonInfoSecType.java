
package com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.generated;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Информация о физическом лице формат МДО
 * 
 * <p>Java class for PersonInfoSec_Type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PersonInfoSec_Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}PersonName"/>
 *         &lt;element name="ContactInfo" type="{}ContactInfoSec_Type" minOccurs="0"/>
 *         &lt;element name="TINInfo" type="{}TINInfo_Type" minOccurs="0"/>
 *         &lt;element name="BirthDt" type="{}Date" minOccurs="0"/>
 *         &lt;element ref="{}Gender" minOccurs="0"/>
 *         &lt;element name="IdentityCards" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element ref="{}IdentityCard" maxOccurs="unbounded"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="BirthPlace" type="{}BirthPlace_Type" minOccurs="0"/>
 *         &lt;element name="Resident" type="{}Resident_Type" minOccurs="0"/>
 *         &lt;element name="Citizenship" type="{}CitizenShip_Type" minOccurs="0"/>
 *         &lt;element name="ClientId" type="{}ClientId_Type" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PersonInfoSec_Type", propOrder = {
    "personName",
    "contactInfo",
    "tinInfo",
    "birthDt",
    "gender",
    "identityCards",
    "birthPlace",
    "resident",
    "citizenship",
    "clientId"
})
public class PersonInfoSecType {

    @XmlElement(name = "PersonName", required = true)
    protected PersonName personName;
    @XmlElement(name = "ContactInfo")
    protected ContactInfoSecType contactInfo;
    @XmlElement(name = "TINInfo")
    protected TINInfoType tinInfo;
    @XmlElement(name = "BirthDt")
    protected String birthDt;
    @XmlElement(name = "Gender")
    protected String gender;
    @XmlElement(name = "IdentityCards")
    protected PersonInfoSecType.IdentityCards identityCards;
    @XmlElement(name = "BirthPlace")
    protected String birthPlace;
    @XmlElement(name = "Resident")
    protected Boolean resident;
    @XmlElement(name = "Citizenship")
    protected String citizenship;
    @XmlElement(name = "ClientId")
    protected String clientId;

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
     * Gets the value of the contactInfo property.
     * 
     * @return
     *     possible object is
     *     {@link ContactInfoSecType }
     *     
     */
    public ContactInfoSecType getContactInfo() {
        return contactInfo;
    }

    /**
     * Sets the value of the contactInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link ContactInfoSecType }
     *     
     */
    public void setContactInfo(ContactInfoSecType value) {
        this.contactInfo = value;
    }

    /**
     * Gets the value of the tinInfo property.
     * 
     * @return
     *     possible object is
     *     {@link TINInfoType }
     *     
     */
    public TINInfoType getTINInfo() {
        return tinInfo;
    }

    /**
     * Sets the value of the tinInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link TINInfoType }
     *     
     */
    public void setTINInfo(TINInfoType value) {
        this.tinInfo = value;
    }

    /**
     * Gets the value of the birthDt property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBirthDt() {
        return birthDt;
    }

    /**
     * Sets the value of the birthDt property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBirthDt(String value) {
        this.birthDt = value;
    }

    /**
     * Пол
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGender() {
        return gender;
    }

    /**
     * Sets the value of the gender property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGender(String value) {
        this.gender = value;
    }

    /**
     * Gets the value of the identityCards property.
     * 
     * @return
     *     possible object is
     *     {@link PersonInfoSecType.IdentityCards }
     *     
     */
    public PersonInfoSecType.IdentityCards getIdentityCards() {
        return identityCards;
    }

    /**
     * Sets the value of the identityCards property.
     * 
     * @param value
     *     allowed object is
     *     {@link PersonInfoSecType.IdentityCards }
     *     
     */
    public void setIdentityCards(PersonInfoSecType.IdentityCards value) {
        this.identityCards = value;
    }

    /**
     * Gets the value of the birthPlace property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBirthPlace() {
        return birthPlace;
    }

    /**
     * Sets the value of the birthPlace property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBirthPlace(String value) {
        this.birthPlace = value;
    }

    /**
     * Gets the value of the resident property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean getResident() {
        return resident;
    }

    /**
     * Sets the value of the resident property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setResident(Boolean value) {
        this.resident = value;
    }

    /**
     * Gets the value of the citizenship property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCitizenship() {
        return citizenship;
    }

    /**
     * Sets the value of the citizenship property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCitizenship(String value) {
        this.citizenship = value;
    }

    /**
     * Gets the value of the clientId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getClientId() {
        return clientId;
    }

    /**
     * Sets the value of the clientId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setClientId(String value) {
        this.clientId = value;
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
     *         &lt;element ref="{}IdentityCard" maxOccurs="unbounded"/>
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
        "identityCards"
    })
    public static class IdentityCards {

        @XmlElement(name = "IdentityCard", required = true)
        protected List<IdentityCardType> identityCards;

        /**
         * Gets the value of the identityCards property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the identityCards property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getIdentityCards().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link IdentityCardType }
         * 
         * 
         */
        public List<IdentityCardType> getIdentityCards() {
            if (identityCards == null) {
                identityCards = new ArrayList<IdentityCardType>();
            }
            return this.identityCards;
        }

    }

}
