
package com.rssl.phizicgate.esberibgate.loanclaim.etsm.generated.release_16;

import java.util.Calendar;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import com.rssl.phizic.utils.jaxb.CalendarDateAdapter;


/**
 * Тип - Персональная информация о заемщике
 * 
 * <p>Java class for ApplicantPersonInfo_Type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ApplicantPersonInfo_Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}PersonName"/>
 *         &lt;element name="NameChangedFlag" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="PreviousName" type="{}PreviousNameData_Type" minOccurs="0"/>
 *         &lt;element name="INN" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;maxLength value="12"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="Sex">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;length value="1"/>
 *               &lt;enumeration value="0"/>
 *               &lt;enumeration value="1"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element ref="{}Birthday"/>
 *         &lt;element name="BirthPlace">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;maxLength value="100"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="Education" type="{}EducationData_Type"/>
 *         &lt;element name="Contact" type="{}Contact_Type"/>
 *         &lt;element name="Citizenship" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Passport" type="{}Passport_Type"/>
 *         &lt;element name="ExtPassportExFlag" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ApplicantPersonInfo_Type", propOrder = {
    "personName",
    "nameChangedFlag",
    "previousName",
    "inn",
    "sex",
    "birthday",
    "birthPlace",
    "education",
    "contact",
    "citizenship",
    "passport",
    "extPassportExFlag"
})
public class ApplicantPersonInfoType {

    @XmlElement(name = "PersonName", required = true)
    protected PersonNameType personName;
    @XmlElement(name = "NameChangedFlag")
    protected boolean nameChangedFlag;
    @XmlElement(name = "PreviousName")
    protected PreviousNameDataType previousName;
    @XmlElement(name = "INN")
    protected String inn;
    @XmlElement(name = "Sex", required = true)
    protected String sex;
    @XmlElement(name = "Birthday", required = true, type = String.class)
    @XmlJavaTypeAdapter(CalendarDateAdapter.class)
    protected Calendar birthday;
    @XmlElement(name = "BirthPlace", required = true)
    protected String birthPlace;
    @XmlElement(name = "Education", required = true)
    protected EducationDataType education;
    @XmlElement(name = "Contact", required = true)
    protected ContactType contact;
    @XmlElement(name = "Citizenship", required = true)
    protected String citizenship;
    @XmlElement(name = "Passport", required = true)
    protected PassportType passport;
    @XmlElement(name = "ExtPassportExFlag", defaultValue = "false")
    protected boolean extPassportExFlag;

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
     * Gets the value of the nameChangedFlag property.
     * 
     */
    public boolean isNameChangedFlag() {
        return nameChangedFlag;
    }

    /**
     * Sets the value of the nameChangedFlag property.
     * 
     */
    public void setNameChangedFlag(boolean value) {
        this.nameChangedFlag = value;
    }

    /**
     * Gets the value of the previousName property.
     * 
     * @return
     *     possible object is
     *     {@link PreviousNameDataType }
     *     
     */
    public PreviousNameDataType getPreviousName() {
        return previousName;
    }

    /**
     * Sets the value of the previousName property.
     * 
     * @param value
     *     allowed object is
     *     {@link PreviousNameDataType }
     *     
     */
    public void setPreviousName(PreviousNameDataType value) {
        this.previousName = value;
    }

    /**
     * Gets the value of the inn property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getINN() {
        return inn;
    }

    /**
     * Sets the value of the inn property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setINN(String value) {
        this.inn = value;
    }

    /**
     * Gets the value of the sex property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSex() {
        return sex;
    }

    /**
     * Sets the value of the sex property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSex(String value) {
        this.sex = value;
    }

    /**
     * Gets the value of the birthday property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public Calendar getBirthday() {
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
    public void setBirthday(Calendar value) {
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
     * Gets the value of the education property.
     * 
     * @return
     *     possible object is
     *     {@link EducationDataType }
     *     
     */
    public EducationDataType getEducation() {
        return education;
    }

    /**
     * Sets the value of the education property.
     * 
     * @param value
     *     allowed object is
     *     {@link EducationDataType }
     *     
     */
    public void setEducation(EducationDataType value) {
        this.education = value;
    }

    /**
     * Gets the value of the contact property.
     * 
     * @return
     *     possible object is
     *     {@link ContactType }
     *     
     */
    public ContactType getContact() {
        return contact;
    }

    /**
     * Sets the value of the contact property.
     * 
     * @param value
     *     allowed object is
     *     {@link ContactType }
     *     
     */
    public void setContact(ContactType value) {
        this.contact = value;
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
     * Gets the value of the passport property.
     * 
     * @return
     *     possible object is
     *     {@link PassportType }
     *     
     */
    public PassportType getPassport() {
        return passport;
    }

    /**
     * Sets the value of the passport property.
     * 
     * @param value
     *     allowed object is
     *     {@link PassportType }
     *     
     */
    public void setPassport(PassportType value) {
        this.passport = value;
    }

    /**
     * Gets the value of the extPassportExFlag property.
     * 
     */
    public boolean isExtPassportExFlag() {
        return extPassportExFlag;
    }

    /**
     * Sets the value of the extPassportExFlag property.
     * 
     */
    public void setExtPassportExFlag(boolean value) {
        this.extPassportExFlag = value;
    }

}
