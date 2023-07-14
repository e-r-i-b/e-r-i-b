
package com.rssl.phizicgate.mdm.integration.mdm.generated;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import com.rssl.phizic.utils.jaxb.CalendarDateAdapter;


/**
 * <p>Java class for PersonInfo_Type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PersonInfo_Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}PersonName" minOccurs="0"/>
 *         &lt;element ref="{}ContactInfo" minOccurs="0"/>
 *         &lt;element ref="{}Gender" minOccurs="0"/>
 *         &lt;element ref="{}Birthday" minOccurs="0"/>
 *         &lt;element ref="{}BirthPlace" minOccurs="0"/>
 *         &lt;element ref="{}MaritalStatus" minOccurs="0"/>
 *         &lt;element ref="{}TaxId" minOccurs="0"/>
 *         &lt;element ref="{}Resident" minOccurs="0"/>
 *         &lt;element ref="{}Employee" minOccurs="0"/>
 *         &lt;element ref="{}Shareholder" minOccurs="0"/>
 *         &lt;element ref="{}Insider" minOccurs="0"/>
 *         &lt;element ref="{}IdentityCard" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element ref="{}CitizenShip" minOccurs="0"/>
 *         &lt;element ref="{}Literacy" minOccurs="0"/>
 *         &lt;element ref="{}Education" minOccurs="0"/>
 *         &lt;element ref="{}SocialCategory" minOccurs="0"/>
 *         &lt;element ref="{}DateOfDeath" minOccurs="0"/>
 *         &lt;element ref="{}VIPStatus" minOccurs="0"/>
 *         &lt;element ref="{}ForeignOfficial" minOccurs="0"/>
 *         &lt;element ref="{}BankRel" minOccurs="0"/>
 *         &lt;element ref="{}InStopList" minOccurs="0"/>
 *         &lt;element ref="{}StopListType" minOccurs="0"/>
 *         &lt;element ref="{}HighRiskInfo" minOccurs="0"/>
 *         &lt;element ref="{}SegmentCMRec" minOccurs="0"/>
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
    "personName",
    "contactInfo",
    "gender",
    "birthday",
    "birthPlace",
    "maritalStatus",
    "taxId",
    "resident",
    "employee",
    "shareholder",
    "insider",
    "identityCards",
    "citizenShip",
    "literacy",
    "education",
    "socialCategory",
    "dateOfDeath",
    "vipStatus",
    "foreignOfficial",
    "bankRel",
    "inStopList",
    "stopListType",
    "highRiskInfo",
    "segmentCMRec"
})
@XmlRootElement(name = "PersonInfo")
public class PersonInfo {

    @XmlElement(name = "PersonName")
    protected PersonName personName;
    @XmlElement(name = "ContactInfo")
    protected ContactInfo contactInfo;
    @XmlElement(name = "Gender")
    protected String gender;
    @XmlElement(name = "Birthday", type = String.class)
    @XmlJavaTypeAdapter(CalendarDateAdapter.class)
    protected Calendar birthday;
    @XmlElement(name = "BirthPlace")
    protected String birthPlace;
    @XmlElement(name = "MaritalStatus")
    protected String maritalStatus;
    @XmlElement(name = "TaxId")
    protected String taxId;
    @XmlElement(name = "Resident")
    protected String resident;
    @XmlElement(name = "Employee")
    protected String employee;
    @XmlElement(name = "Shareholder")
    protected String shareholder;
    @XmlElement(name = "Insider")
    protected String insider;
    @XmlElement(name = "IdentityCard")
    protected List<IdentityCard> identityCards;
    @XmlElement(name = "CitizenShip")
    protected String citizenShip;
    @XmlElement(name = "Literacy")
    protected String literacy;
    @XmlElement(name = "Education")
    protected String education;
    @XmlElement(name = "SocialCategory")
    protected String socialCategory;
    @XmlElement(name = "DateOfDeath", type = String.class)
    @XmlJavaTypeAdapter(CalendarDateAdapter.class)
    protected Calendar dateOfDeath;
    @XmlElement(name = "VIPStatus")
    protected String vipStatus;
    @XmlElement(name = "ForeignOfficial")
    protected String foreignOfficial;
    @XmlElement(name = "BankRel")
    protected String bankRel;
    @XmlElement(name = "InStopList")
    protected String inStopList;
    @XmlElement(name = "StopListType")
    protected String stopListType;
    @XmlElement(name = "HighRiskInfo")
    protected HighRiskInfo highRiskInfo;
    @XmlElement(name = "SegmentCMRec")
    protected SegmentCMRec segmentCMRec;

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
     * Контактная информация
     * 
     * @return
     *     possible object is
     *     {@link ContactInfo }
     *     
     */
    public ContactInfo getContactInfo() {
        return contactInfo;
    }

    /**
     * Sets the value of the contactInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link ContactInfo }
     *     
     */
    public void setContactInfo(ContactInfo value) {
        this.contactInfo = value;
    }

    /**
     * Gets the value of the gender property.
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
     * Gets the value of the maritalStatus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMaritalStatus() {
        return maritalStatus;
    }

    /**
     * Sets the value of the maritalStatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMaritalStatus(String value) {
        this.maritalStatus = value;
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
     * Gets the value of the resident property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getResident() {
        return resident;
    }

    /**
     * Sets the value of the resident property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setResident(String value) {
        this.resident = value;
    }

    /**
     * Gets the value of the employee property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEmployee() {
        return employee;
    }

    /**
     * Sets the value of the employee property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEmployee(String value) {
        this.employee = value;
    }

    /**
     * Gets the value of the shareholder property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getShareholder() {
        return shareholder;
    }

    /**
     * Sets the value of the shareholder property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setShareholder(String value) {
        this.shareholder = value;
    }

    /**
     * Gets the value of the insider property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInsider() {
        return insider;
    }

    /**
     * Sets the value of the insider property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInsider(String value) {
        this.insider = value;
    }

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
     * {@link IdentityCard }
     * 
     * 
     */
    public List<IdentityCard> getIdentityCards() {
        if (identityCards == null) {
            identityCards = new ArrayList<IdentityCard>();
        }
        return this.identityCards;
    }

    /**
     * Gets the value of the citizenShip property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCitizenShip() {
        return citizenShip;
    }

    /**
     * Sets the value of the citizenShip property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCitizenShip(String value) {
        this.citizenShip = value;
    }

    /**
     * Gets the value of the literacy property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLiteracy() {
        return literacy;
    }

    /**
     * Sets the value of the literacy property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLiteracy(String value) {
        this.literacy = value;
    }

    /**
     * Gets the value of the education property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEducation() {
        return education;
    }

    /**
     * Sets the value of the education property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEducation(String value) {
        this.education = value;
    }

    /**
     * Gets the value of the socialCategory property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSocialCategory() {
        return socialCategory;
    }

    /**
     * Sets the value of the socialCategory property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSocialCategory(String value) {
        this.socialCategory = value;
    }

    /**
     * Gets the value of the dateOfDeath property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public Calendar getDateOfDeath() {
        return dateOfDeath;
    }

    /**
     * Sets the value of the dateOfDeath property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDateOfDeath(Calendar value) {
        this.dateOfDeath = value;
    }

    /**
     * Gets the value of the vipStatus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVIPStatus() {
        return vipStatus;
    }

    /**
     * Sets the value of the vipStatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVIPStatus(String value) {
        this.vipStatus = value;
    }

    /**
     * Gets the value of the foreignOfficial property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getForeignOfficial() {
        return foreignOfficial;
    }

    /**
     * Sets the value of the foreignOfficial property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setForeignOfficial(String value) {
        this.foreignOfficial = value;
    }

    /**
     * Gets the value of the bankRel property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBankRel() {
        return bankRel;
    }

    /**
     * Sets the value of the bankRel property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBankRel(String value) {
        this.bankRel = value;
    }

    /**
     * Gets the value of the inStopList property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInStopList() {
        return inStopList;
    }

    /**
     * Sets the value of the inStopList property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInStopList(String value) {
        this.inStopList = value;
    }

    /**
     * Gets the value of the stopListType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStopListType() {
        return stopListType;
    }

    /**
     * Sets the value of the stopListType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStopListType(String value) {
        this.stopListType = value;
    }

    /**
     * Gets the value of the highRiskInfo property.
     * 
     * @return
     *     possible object is
     *     {@link HighRiskInfo }
     *     
     */
    public HighRiskInfo getHighRiskInfo() {
        return highRiskInfo;
    }

    /**
     * Sets the value of the highRiskInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link HighRiskInfo }
     *     
     */
    public void setHighRiskInfo(HighRiskInfo value) {
        this.highRiskInfo = value;
    }

    /**
     * Gets the value of the segmentCMRec property.
     * 
     * @return
     *     possible object is
     *     {@link SegmentCMRec }
     *     
     */
    public SegmentCMRec getSegmentCMRec() {
        return segmentCMRec;
    }

    /**
     * Sets the value of the segmentCMRec property.
     * 
     * @param value
     *     allowed object is
     *     {@link SegmentCMRec }
     *     
     */
    public void setSegmentCMRec(SegmentCMRec value) {
        this.segmentCMRec = value;
    }

}
