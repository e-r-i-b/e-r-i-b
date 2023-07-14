
package com.rssl.phizicgate.esberibgate.loanclaim.etsm.generated.release_13;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import com.rssl.phizic.utils.jaxb.CalendarDateAdapter;


/**
 * Тип - способ связи
 * 
 * <p>Java class for Contact_Type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Contact_Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="EmailAddr" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;maxLength value="50"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="PhoneList">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="Phone" type="{}Phone_Type" maxOccurs="unbounded"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="AddressList">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="ResidenceEqualFlag" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *                   &lt;element name="CityResidencePeriod" type="{}ResidencePeriod_Type"/>
 *                   &lt;element name="ActResidencePeriod" type="{}ResidencePeriod_Type"/>
 *                   &lt;element name="ResidenceRight" type="{}ResidenceRight_Type"/>
 *                   &lt;element name="TempRegExpiryDt" type="{}Date" minOccurs="0"/>
 *                   &lt;element name="Address" type="{}Address_Type" maxOccurs="unbounded"/>
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
@XmlType(name = "Contact_Type", propOrder = {
    "emailAddr",
    "phoneList",
    "addressList"
})
public class ContactType {

    @XmlElement(name = "EmailAddr")
    protected String emailAddr;
    @XmlElement(name = "PhoneList", required = true)
    protected ContactType.PhoneList phoneList;
    @XmlElement(name = "AddressList", required = true)
    protected ContactType.AddressList addressList;

    /**
     * Gets the value of the emailAddr property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEmailAddr() {
        return emailAddr;
    }

    /**
     * Sets the value of the emailAddr property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEmailAddr(String value) {
        this.emailAddr = value;
    }

    /**
     * Gets the value of the phoneList property.
     * 
     * @return
     *     possible object is
     *     {@link ContactType.PhoneList }
     *     
     */
    public ContactType.PhoneList getPhoneList() {
        return phoneList;
    }

    /**
     * Sets the value of the phoneList property.
     * 
     * @param value
     *     allowed object is
     *     {@link ContactType.PhoneList }
     *     
     */
    public void setPhoneList(ContactType.PhoneList value) {
        this.phoneList = value;
    }

    /**
     * Gets the value of the addressList property.
     * 
     * @return
     *     possible object is
     *     {@link ContactType.AddressList }
     *     
     */
    public ContactType.AddressList getAddressList() {
        return addressList;
    }

    /**
     * Sets the value of the addressList property.
     * 
     * @param value
     *     allowed object is
     *     {@link ContactType.AddressList }
     *     
     */
    public void setAddressList(ContactType.AddressList value) {
        this.addressList = value;
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
     *         &lt;element name="ResidenceEqualFlag" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
     *         &lt;element name="CityResidencePeriod" type="{}ResidencePeriod_Type"/>
     *         &lt;element name="ActResidencePeriod" type="{}ResidencePeriod_Type"/>
     *         &lt;element name="ResidenceRight" type="{}ResidenceRight_Type"/>
     *         &lt;element name="TempRegExpiryDt" type="{}Date" minOccurs="0"/>
     *         &lt;element name="Address" type="{}Address_Type" maxOccurs="unbounded"/>
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
        "residenceEqualFlag",
        "cityResidencePeriod",
        "actResidencePeriod",
        "residenceRight",
        "tempRegExpiryDt",
        "addresses"
    })
    public static class AddressList {

        @XmlElement(name = "ResidenceEqualFlag")
        protected boolean residenceEqualFlag;
        @XmlElement(name = "CityResidencePeriod", required = true)
        protected BigInteger cityResidencePeriod;
        @XmlElement(name = "ActResidencePeriod", required = true)
        protected BigInteger actResidencePeriod;
        @XmlElement(name = "ResidenceRight", required = true)
        protected String residenceRight;
        @XmlElement(name = "TempRegExpiryDt", type = String.class)
        @XmlJavaTypeAdapter(CalendarDateAdapter.class)
        protected Calendar tempRegExpiryDt;
        @XmlElement(name = "Address", required = true)
        protected List<AddressType> addresses;

        /**
         * Gets the value of the residenceEqualFlag property.
         * 
         */
        public boolean isResidenceEqualFlag() {
            return residenceEqualFlag;
        }

        /**
         * Sets the value of the residenceEqualFlag property.
         * 
         */
        public void setResidenceEqualFlag(boolean value) {
            this.residenceEqualFlag = value;
        }

        /**
         * Gets the value of the cityResidencePeriod property.
         * 
         * @return
         *     possible object is
         *     {@link BigInteger }
         *     
         */
        public BigInteger getCityResidencePeriod() {
            return cityResidencePeriod;
        }

        /**
         * Sets the value of the cityResidencePeriod property.
         * 
         * @param value
         *     allowed object is
         *     {@link BigInteger }
         *     
         */
        public void setCityResidencePeriod(BigInteger value) {
            this.cityResidencePeriod = value;
        }

        /**
         * Gets the value of the actResidencePeriod property.
         * 
         * @return
         *     possible object is
         *     {@link BigInteger }
         *     
         */
        public BigInteger getActResidencePeriod() {
            return actResidencePeriod;
        }

        /**
         * Sets the value of the actResidencePeriod property.
         * 
         * @param value
         *     allowed object is
         *     {@link BigInteger }
         *     
         */
        public void setActResidencePeriod(BigInteger value) {
            this.actResidencePeriod = value;
        }

        /**
         * Gets the value of the residenceRight property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getResidenceRight() {
            return residenceRight;
        }

        /**
         * Sets the value of the residenceRight property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setResidenceRight(String value) {
            this.residenceRight = value;
        }

        /**
         * Gets the value of the tempRegExpiryDt property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public Calendar getTempRegExpiryDt() {
            return tempRegExpiryDt;
        }

        /**
         * Sets the value of the tempRegExpiryDt property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setTempRegExpiryDt(Calendar value) {
            this.tempRegExpiryDt = value;
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
         * {@link AddressType }
         * 
         * 
         */
        public List<AddressType> getAddresses() {
            if (addresses == null) {
                addresses = new ArrayList<AddressType>();
            }
            return this.addresses;
        }

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
     *         &lt;element name="Phone" type="{}Phone_Type" maxOccurs="unbounded"/>
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
        "phones"
    })
    public static class PhoneList {

        @XmlElement(name = "Phone", required = true)
        protected List<PhoneType> phones;

        /**
         * Gets the value of the phones property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the phones property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getPhones().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link PhoneType }
         * 
         * 
         */
        public List<PhoneType> getPhones() {
            if (phones == null) {
                phones = new ArrayList<PhoneType>();
            }
            return this.phones;
        }

    }

}
