
package com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Информация о физическом лице <PersonInfo>
 * 
 * <p>Java class for PersonInfo_Type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PersonInfo_Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="PFRAccount" type="{}PfrAccount_Type" minOccurs="0"/>
 *         &lt;element ref="{}Birthday" minOccurs="0"/>
 *         &lt;element name="BirthPlace" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="TaxId" type="{}TaxId_Type" minOccurs="0"/>
 *         &lt;element name="Citizenship" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="AdditionalInfo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="PersonRole" type="{}CreditRole_Type" minOccurs="0"/>
 *         &lt;element ref="{}Gender" minOccurs="0"/>
 *         &lt;element name="Resident" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element ref="{}PersonName" minOccurs="0"/>
 *         &lt;element ref="{}IdentityCard" minOccurs="0"/>
 *         &lt;element ref="{}ContactInfo" minOccurs="0"/>
 *         &lt;element name="Insider" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="EmploymentHistory" type="{}EmploymentHistoryType" minOccurs="0"/>
 *         &lt;element name="ControlWord" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PersonInfo_Type", propOrder = {
    "pfrAccount",
    "birthday",
    "birthPlace",
    "taxId",
    "citizenship",
    "additionalInfo",
    "personRole",
    "gender",
    "resident",
    "personName",
    "identityCard",
    "contactInfo",
    "insider",
    "employmentHistory",
    "controlWord"
})
public class PersonInfoType {

    @XmlElement(name = "PFRAccount")
    protected String pfrAccount;
    @XmlElement(name = "Birthday")
    protected String birthday;
    @XmlElement(name = "BirthPlace")
    protected String birthPlace;
    @XmlElement(name = "TaxId")
    protected String taxId;
    @XmlElement(name = "Citizenship")
    protected String citizenship;
    @XmlElement(name = "AdditionalInfo")
    protected String additionalInfo;
    @XmlElement(name = "PersonRole")
    protected Long personRole;
    @XmlElement(name = "Gender")
    protected String gender;
    @XmlElement(name = "Resident")
    protected Boolean resident;
    @XmlElement(name = "PersonName")
    protected PersonName personName;
    @XmlElement(name = "IdentityCard")
    protected IdentityCardType identityCard;
    @XmlElement(name = "ContactInfo")
    protected ContactInfoType contactInfo;
    @XmlElement(name = "Insider")
    protected Boolean insider;
    @XmlElement(name = "EmploymentHistory")
    protected EmploymentHistoryType employmentHistory;
    @XmlElement(name = "ControlWord")
    protected String controlWord;

    /**
     * Gets the value of the pfrAccount property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPFRAccount() {
        return pfrAccount;
    }

    /**
     * Sets the value of the pfrAccount property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPFRAccount(String value) {
        this.pfrAccount = value;
    }

    /**
     * Дата рождения
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBirthday() {
        return birthday;
    }

    /**
     * Sets the value of the birthday property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBirthday(String value) {
        this.birthday = value;
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
     * Gets the value of the taxId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTaxId() {
        return taxId;
    }

    /**
     * Sets the value of the taxId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTaxId(String value) {
        this.taxId = value;
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
     * Gets the value of the additionalInfo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAdditionalInfo() {
        return additionalInfo;
    }

    /**
     * Sets the value of the additionalInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAdditionalInfo(String value) {
        this.additionalInfo = value;
    }

    /**
     * Gets the value of the personRole property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getPersonRole() {
        return personRole;
    }

    /**
     * Sets the value of the personRole property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setPersonRole(Long value) {
        this.personRole = value;
    }

    /**
     * Пол. Значения:Male – мужской Female – женский Unknown – неизвестен
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
     * ФИО клиента
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
     * Удостоверение личности.
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

    /**
     * Контактная информация
     * 
     * @return
     *     possible object is
     *     {@link ContactInfoType }
     *     
     */
    public ContactInfoType getContactInfo() {
        return contactInfo;
    }

    /**
     * Sets the value of the contactInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link ContactInfoType }
     *     
     */
    public void setContactInfo(ContactInfoType value) {
        this.contactInfo = value;
    }

    /**
     * Gets the value of the insider property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean getInsider() {
        return insider;
    }

    /**
     * Sets the value of the insider property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setInsider(Boolean value) {
        this.insider = value;
    }

    /**
     * Gets the value of the employmentHistory property.
     * 
     * @return
     *     possible object is
     *     {@link EmploymentHistoryType }
     *     
     */
    public EmploymentHistoryType getEmploymentHistory() {
        return employmentHistory;
    }

    /**
     * Sets the value of the employmentHistory property.
     * 
     * @param value
     *     allowed object is
     *     {@link EmploymentHistoryType }
     *     
     */
    public void setEmploymentHistory(EmploymentHistoryType value) {
        this.employmentHistory = value;
    }

    /**
     * Gets the value of the controlWord property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getControlWord() {
        return controlWord;
    }

    /**
     * Sets the value of the controlWord property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setControlWord(String value) {
        this.controlWord = value;
    }

}
