
package com.rssl.phizicgate.esberibgate.bki.generated;

import com.rssl.phizic.utils.jaxb.CalendarDateTimeAdapter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Java class for EnquiryRequestERIBType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="EnquiryRequestERIBType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}RqUID"/>
 *         &lt;element ref="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}RqTm"/>
 *         &lt;element ref="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}OperUID"/>
 *         &lt;element ref="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}SPName"/>
 *         &lt;element ref="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}RequestType"/>
 *         &lt;element name="Consumer" maxOccurs="unbounded">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="name1" minOccurs="0">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                         &lt;maxLength value="100"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="name2" minOccurs="0">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                         &lt;maxLength value="100"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="surname">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                         &lt;maxLength value="150"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="sex">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                         &lt;length value="1"/>
 *                         &lt;pattern value="\d"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="dateOfBirth" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}bureauDate"/>
 *                   &lt;element name="placeOfBirth">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                         &lt;maxLength value="100"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="nationality">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                         &lt;length value="2"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="dateConsentGiven" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}bureauDate"/>
 *                   &lt;element name="primaryIDType">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                         &lt;length value="2"/>
 *                         &lt;pattern value="\d{2}"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="primaryID">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                         &lt;maxLength value="20"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="primaryIDIssueDate" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}bureauDate" minOccurs="0"/>
 *                   &lt;element name="primaryIDAuthority" minOccurs="0">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                         &lt;maxLength value="100"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="Address" maxOccurs="unbounded">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="addressType">
 *                               &lt;simpleType>
 *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                                   &lt;length value="1"/>
 *                                   &lt;pattern value="\d"/>
 *                                 &lt;/restriction>
 *                               &lt;/simpleType>
 *                             &lt;/element>
 *                             &lt;element name="line1">
 *                               &lt;simpleType>
 *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                                   &lt;maxLength value="20"/>
 *                                 &lt;/restriction>
 *                               &lt;/simpleType>
 *                             &lt;/element>
 *                             &lt;element name="line2">
 *                               &lt;simpleType>
 *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                                   &lt;maxLength value="50"/>
 *                                 &lt;/restriction>
 *                               &lt;/simpleType>
 *                             &lt;/element>
 *                             &lt;element name="line3">
 *                               &lt;simpleType>
 *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                                   &lt;maxLength value="50"/>
 *                                 &lt;/restriction>
 *                               &lt;/simpleType>
 *                             &lt;/element>
 *                             &lt;element name="line4" minOccurs="0">
 *                               &lt;simpleType>
 *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                                   &lt;maxLength value="100"/>
 *                                 &lt;/restriction>
 *                               &lt;/simpleType>
 *                             &lt;/element>
 *                             &lt;element name="postcode" minOccurs="0">
 *                               &lt;simpleType>
 *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                                   &lt;maxLength value="10"/>
 *                                 &lt;/restriction>
 *                               &lt;/simpleType>
 *                             &lt;/element>
 *                             &lt;element name="country" minOccurs="0">
 *                               &lt;simpleType>
 *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                                   &lt;length value="2"/>
 *                                 &lt;/restriction>
 *                               &lt;/simpleType>
 *                             &lt;/element>
 *                             &lt;element name="homeTelNbr" minOccurs="0">
 *                               &lt;simpleType>
 *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                                   &lt;maxLength value="16"/>
 *                                 &lt;/restriction>
 *                               &lt;/simpleType>
 *                             &lt;/element>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "EnquiryRequestERIBType", propOrder = {
    "rqUID",
    "rqTm",
    "operUID",
    "spName",
    "requestType",
    "consumers"
})
public class EnquiryRequestERIBType {

    @XmlElement(name = "RqUID", required = true)
    protected String rqUID;
    @XmlElement(name = "RqTm", required = true, type = String.class)
    @XmlJavaTypeAdapter(CalendarDateTimeAdapter.class)
    protected Calendar rqTm;
    @XmlElement(name = "OperUID", required = true)
    protected String operUID;
    @XmlElement(name = "SPName", required = true)
    protected SPNameType spName;
    @XmlElement(name = "RequestType", required = true)
    protected String requestType;
    @XmlElement(name = "Consumer", required = true)
    protected List<EnquiryRequestERIBType.Consumer> consumers;

    /**
     * Gets the value of the rqUID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRqUID() {
        return rqUID;
    }

    /**
     * Sets the value of the rqUID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRqUID(String value) {
        this.rqUID = value;
    }

    /**
     * Gets the value of the rqTm property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public Calendar getRqTm() {
        return rqTm;
    }

    /**
     * Sets the value of the rqTm property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRqTm(Calendar value) {
        this.rqTm = value;
    }

    /**
     * Gets the value of the operUID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOperUID() {
        return operUID;
    }

    /**
     * Sets the value of the operUID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOperUID(String value) {
        this.operUID = value;
    }

    /**
     * Gets the value of the spName property.
     * 
     * @return
     *     possible object is
     *     {@link SPNameType }
     *     
     */
    public SPNameType getSPName() {
        return spName;
    }

    /**
     * Sets the value of the spName property.
     * 
     * @param value
     *     allowed object is
     *     {@link SPNameType }
     *     
     */
    public void setSPName(SPNameType value) {
        this.spName = value;
    }

    /**
     * Gets the value of the requestType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRequestType() {
        return requestType;
    }

    /**
     * Sets the value of the requestType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRequestType(String value) {
        this.requestType = value;
    }

    /**
     * Gets the value of the consumers property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the consumers property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getConsumers().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link EnquiryRequestERIBType.Consumer }
     * 
     * 
     */
    public List<EnquiryRequestERIBType.Consumer> getConsumers() {
        if (consumers == null) {
            consumers = new ArrayList<EnquiryRequestERIBType.Consumer>();
        }
        return this.consumers;
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
     *         &lt;element name="name1" minOccurs="0">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *               &lt;maxLength value="100"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="name2" minOccurs="0">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *               &lt;maxLength value="100"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="surname">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *               &lt;maxLength value="150"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="sex">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *               &lt;length value="1"/>
     *               &lt;pattern value="\d"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="dateOfBirth" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}bureauDate"/>
     *         &lt;element name="placeOfBirth">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *               &lt;maxLength value="100"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="nationality">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *               &lt;length value="2"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="dateConsentGiven" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}bureauDate"/>
     *         &lt;element name="primaryIDType">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *               &lt;length value="2"/>
     *               &lt;pattern value="\d{2}"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="primaryID">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *               &lt;maxLength value="20"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="primaryIDIssueDate" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}bureauDate" minOccurs="0"/>
     *         &lt;element name="primaryIDAuthority" minOccurs="0">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *               &lt;maxLength value="100"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="Address" maxOccurs="unbounded">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="addressType">
     *                     &lt;simpleType>
     *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *                         &lt;length value="1"/>
     *                         &lt;pattern value="\d"/>
     *                       &lt;/restriction>
     *                     &lt;/simpleType>
     *                   &lt;/element>
     *                   &lt;element name="line1">
     *                     &lt;simpleType>
     *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *                         &lt;maxLength value="20"/>
     *                       &lt;/restriction>
     *                     &lt;/simpleType>
     *                   &lt;/element>
     *                   &lt;element name="line2">
     *                     &lt;simpleType>
     *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *                         &lt;maxLength value="50"/>
     *                       &lt;/restriction>
     *                     &lt;/simpleType>
     *                   &lt;/element>
     *                   &lt;element name="line3">
     *                     &lt;simpleType>
     *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *                         &lt;maxLength value="50"/>
     *                       &lt;/restriction>
     *                     &lt;/simpleType>
     *                   &lt;/element>
     *                   &lt;element name="line4" minOccurs="0">
     *                     &lt;simpleType>
     *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *                         &lt;maxLength value="100"/>
     *                       &lt;/restriction>
     *                     &lt;/simpleType>
     *                   &lt;/element>
     *                   &lt;element name="postcode" minOccurs="0">
     *                     &lt;simpleType>
     *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *                         &lt;maxLength value="10"/>
     *                       &lt;/restriction>
     *                     &lt;/simpleType>
     *                   &lt;/element>
     *                   &lt;element name="country" minOccurs="0">
     *                     &lt;simpleType>
     *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *                         &lt;length value="2"/>
     *                       &lt;/restriction>
     *                     &lt;/simpleType>
     *                   &lt;/element>
     *                   &lt;element name="homeTelNbr" minOccurs="0">
     *                     &lt;simpleType>
     *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *                         &lt;maxLength value="16"/>
     *                       &lt;/restriction>
     *                     &lt;/simpleType>
     *                   &lt;/element>
     *                 &lt;/sequence>
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
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
        "name1",
        "name2",
        "surname",
        "sex",
        "dateOfBirth",
        "placeOfBirth",
        "nationality",
        "dateConsentGiven",
        "primaryIDType",
        "primaryID",
        "primaryIDIssueDate",
        "primaryIDAuthority",
        "addresses"
    })
    public static class Consumer {

        protected String name1;
        protected String name2;
        @XmlElement(required = true)
        protected String surname;
        @XmlElement(required = true)
        protected String sex;
        @XmlElement(required = true)
        protected String dateOfBirth;
        @XmlElement(required = true)
        protected String placeOfBirth;
        @XmlElement(required = true)
        protected String nationality;
        @XmlElement(required = true)
        protected String dateConsentGiven;
        @XmlElement(required = true)
        protected String primaryIDType;
        @XmlElement(required = true)
        protected String primaryID;
        protected String primaryIDIssueDate;
        protected String primaryIDAuthority;
        @XmlElement(name = "Address", required = true)
        protected List<EnquiryRequestERIBType.Consumer.Address> addresses;

        /**
         * Gets the value of the name1 property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getName1() {
            return name1;
        }

        /**
         * Sets the value of the name1 property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setName1(String value) {
            this.name1 = value;
        }

        /**
         * Gets the value of the name2 property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getName2() {
            return name2;
        }

        /**
         * Sets the value of the name2 property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setName2(String value) {
            this.name2 = value;
        }

        /**
         * Gets the value of the surname property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getSurname() {
            return surname;
        }

        /**
         * Sets the value of the surname property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setSurname(String value) {
            this.surname = value;
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
         * Gets the value of the dateOfBirth property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getDateOfBirth() {
            return dateOfBirth;
        }

        /**
         * Sets the value of the dateOfBirth property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setDateOfBirth(String value) {
            this.dateOfBirth = value;
        }

        /**
         * Gets the value of the placeOfBirth property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getPlaceOfBirth() {
            return placeOfBirth;
        }

        /**
         * Sets the value of the placeOfBirth property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setPlaceOfBirth(String value) {
            this.placeOfBirth = value;
        }

        /**
         * Gets the value of the nationality property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getNationality() {
            return nationality;
        }

        /**
         * Sets the value of the nationality property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setNationality(String value) {
            this.nationality = value;
        }

        /**
         * Gets the value of the dateConsentGiven property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getDateConsentGiven() {
            return dateConsentGiven;
        }

        /**
         * Sets the value of the dateConsentGiven property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setDateConsentGiven(String value) {
            this.dateConsentGiven = value;
        }

        /**
         * Gets the value of the primaryIDType property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getPrimaryIDType() {
            return primaryIDType;
        }

        /**
         * Sets the value of the primaryIDType property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setPrimaryIDType(String value) {
            this.primaryIDType = value;
        }

        /**
         * Gets the value of the primaryID property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getPrimaryID() {
            return primaryID;
        }

        /**
         * Sets the value of the primaryID property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setPrimaryID(String value) {
            this.primaryID = value;
        }

        /**
         * Gets the value of the primaryIDIssueDate property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getPrimaryIDIssueDate() {
            return primaryIDIssueDate;
        }

        /**
         * Sets the value of the primaryIDIssueDate property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setPrimaryIDIssueDate(String value) {
            this.primaryIDIssueDate = value;
        }

        /**
         * Gets the value of the primaryIDAuthority property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getPrimaryIDAuthority() {
            return primaryIDAuthority;
        }

        /**
         * Sets the value of the primaryIDAuthority property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setPrimaryIDAuthority(String value) {
            this.primaryIDAuthority = value;
        }

        /**
         * Gets the value of the addresses property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the addresses property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getAddresses().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link EnquiryRequestERIBType.Consumer.Address }
         * 
         * 
         */
        public List<EnquiryRequestERIBType.Consumer.Address> getAddresses() {
            if (addresses == null) {
                addresses = new ArrayList<EnquiryRequestERIBType.Consumer.Address>();
            }
            return this.addresses;
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
         *         &lt;element name="addressType">
         *           &lt;simpleType>
         *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
         *               &lt;length value="1"/>
         *               &lt;pattern value="\d"/>
         *             &lt;/restriction>
         *           &lt;/simpleType>
         *         &lt;/element>
         *         &lt;element name="line1">
         *           &lt;simpleType>
         *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
         *               &lt;maxLength value="20"/>
         *             &lt;/restriction>
         *           &lt;/simpleType>
         *         &lt;/element>
         *         &lt;element name="line2">
         *           &lt;simpleType>
         *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
         *               &lt;maxLength value="50"/>
         *             &lt;/restriction>
         *           &lt;/simpleType>
         *         &lt;/element>
         *         &lt;element name="line3">
         *           &lt;simpleType>
         *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
         *               &lt;maxLength value="50"/>
         *             &lt;/restriction>
         *           &lt;/simpleType>
         *         &lt;/element>
         *         &lt;element name="line4" minOccurs="0">
         *           &lt;simpleType>
         *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
         *               &lt;maxLength value="100"/>
         *             &lt;/restriction>
         *           &lt;/simpleType>
         *         &lt;/element>
         *         &lt;element name="postcode" minOccurs="0">
         *           &lt;simpleType>
         *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
         *               &lt;maxLength value="10"/>
         *             &lt;/restriction>
         *           &lt;/simpleType>
         *         &lt;/element>
         *         &lt;element name="country" minOccurs="0">
         *           &lt;simpleType>
         *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
         *               &lt;length value="2"/>
         *             &lt;/restriction>
         *           &lt;/simpleType>
         *         &lt;/element>
         *         &lt;element name="homeTelNbr" minOccurs="0">
         *           &lt;simpleType>
         *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
         *               &lt;maxLength value="16"/>
         *             &lt;/restriction>
         *           &lt;/simpleType>
         *         &lt;/element>
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
            "addressType",
            "line1",
            "line2",
            "line3",
            "line4",
            "postcode",
            "country",
            "homeTelNbr"
        })
        public static class Address {

            @XmlElement(required = true)
            protected String addressType;
            @XmlElement(required = true)
            protected String line1;
            @XmlElement(required = true)
            protected String line2;
            @XmlElement(required = true)
            protected String line3;
            protected String line4;
            protected String postcode;
            protected String country;
            protected String homeTelNbr;

            /**
             * Gets the value of the addressType property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getAddressType() {
                return addressType;
            }

            /**
             * Sets the value of the addressType property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setAddressType(String value) {
                this.addressType = value;
            }

            /**
             * Gets the value of the line1 property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getLine1() {
                return line1;
            }

            /**
             * Sets the value of the line1 property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setLine1(String value) {
                this.line1 = value;
            }

            /**
             * Gets the value of the line2 property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getLine2() {
                return line2;
            }

            /**
             * Sets the value of the line2 property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setLine2(String value) {
                this.line2 = value;
            }

            /**
             * Gets the value of the line3 property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getLine3() {
                return line3;
            }

            /**
             * Sets the value of the line3 property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setLine3(String value) {
                this.line3 = value;
            }

            /**
             * Gets the value of the line4 property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getLine4() {
                return line4;
            }

            /**
             * Sets the value of the line4 property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setLine4(String value) {
                this.line4 = value;
            }

            /**
             * Gets the value of the postcode property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getPostcode() {
                return postcode;
            }

            /**
             * Sets the value of the postcode property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setPostcode(String value) {
                this.postcode = value;
            }

            /**
             * Gets the value of the country property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getCountry() {
                return country;
            }

            /**
             * Sets the value of the country property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setCountry(String value) {
                this.country = value;
            }

            /**
             * Gets the value of the homeTelNbr property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getHomeTelNbr() {
                return homeTelNbr;
            }

            /**
             * Sets the value of the homeTelNbr property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setHomeTelNbr(String value) {
                this.homeTelNbr = value;
            }

        }

    }

}
