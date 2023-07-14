
package com.rssl.phizicgate.esberibgate.loanclaim.etsm.generated.release_19;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import com.rssl.phizic.utils.jaxb.CalendarDateAdapter;
import com.rssl.phizic.utils.jaxb.CalendarDateTimeNoMillisecondsAdapter;


/**
 * Тип данных ответа на поиск существующих кредитных заявок в ETSM
 * 
 * <p>Java class for SearchApplicationRs_Type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SearchApplicationRs_Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}RqUID"/>
 *         &lt;element ref="{}RqTm"/>
 *         &lt;element ref="{}SPName"/>
 *         &lt;element name="SrcRq">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element ref="{}RqUID"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="Status">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="StatusCode">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="Error" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="ErrorCode">
 *                               &lt;simpleType>
 *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
 *                                 &lt;/restriction>
 *                               &lt;/simpleType>
 *                             &lt;/element>
 *                             &lt;element name="Message">
 *                               &lt;simpleType>
 *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                                   &lt;maxLength value="255"/>
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
 *         &lt;element name="Application" maxOccurs="10" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="StatusCode">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
 *                         &lt;enumeration value="-2"/>
 *                         &lt;enumeration value="-1"/>
 *                         &lt;enumeration value="0"/>
 *                         &lt;enumeration value="1"/>
 *                         &lt;enumeration value="2"/>
 *                         &lt;enumeration value="3"/>
 *                         &lt;enumeration value="4"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="ApplicationNumber">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                         &lt;maxLength value="20"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="Type" type="{}NPPartyType_Type"/>
 *                   &lt;element name="FullnameKI" type="{}Fullname_Type" minOccurs="0"/>
 *                   &lt;element name="LoginKI" type="{}Login_Type" minOccurs="0"/>
 *                   &lt;element name="FullnameTM" type="{}Fullname_Type" minOccurs="0"/>
 *                   &lt;element name="LoginTM" type="{}Login_Type" minOccurs="0"/>
 *                   &lt;element name="Unit">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{}String">
 *                         &lt;length value="11"/>
 *                         &lt;pattern value="\d{11}"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="Channel" type="{}Chanel_Type"/>
 *                   &lt;element name="appSigningDate" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *                   &lt;element name="Product" type="{}ProductData_Type"/>
 *                   &lt;element name="Approval" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="PeriodM" minOccurs="0">
 *                               &lt;simpleType>
 *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}unsignedInt">
 *                                   &lt;totalDigits value="4"/>
 *                                 &lt;/restriction>
 *                               &lt;/simpleType>
 *                             &lt;/element>
 *                             &lt;element name="Amount" minOccurs="0">
 *                               &lt;simpleType>
 *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
 *                                   &lt;totalDigits value="17"/>
 *                                   &lt;fractionDigits value="2"/>
 *                                 &lt;/restriction>
 *                               &lt;/simpleType>
 *                             &lt;/element>
 *                             &lt;element name="InterestRate" minOccurs="0">
 *                               &lt;simpleType>
 *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
 *                                   &lt;totalDigits value="6"/>
 *                                   &lt;fractionDigits value="2"/>
 *                                 &lt;/restriction>
 *                               &lt;/simpleType>
 *                             &lt;/element>
 *                             &lt;element name="CreditCardLimit" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *                             &lt;element name="AutoGrantion" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                   &lt;element name="Offer" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="Alternative" maxOccurs="16">
 *                               &lt;complexType>
 *                                 &lt;complexContent>
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                     &lt;sequence>
 *                                       &lt;element name="AltPeriodM">
 *                                         &lt;simpleType>
 *                                           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}unsignedInt">
 *                                             &lt;totalDigits value="4"/>
 *                                           &lt;/restriction>
 *                                         &lt;/simpleType>
 *                                       &lt;/element>
 *                                       &lt;element name="AltAmount">
 *                                         &lt;simpleType>
 *                                           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
 *                                             &lt;totalDigits value="17"/>
 *                                             &lt;fractionDigits value="2"/>
 *                                           &lt;/restriction>
 *                                         &lt;/simpleType>
 *                                       &lt;/element>
 *                                       &lt;element name="AltInterestRate">
 *                                         &lt;simpleType>
 *                                           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
 *                                             &lt;totalDigits value="6"/>
 *                                             &lt;fractionDigits value="2"/>
 *                                           &lt;/restriction>
 *                                         &lt;/simpleType>
 *                                       &lt;/element>
 *                                       &lt;element name="AltFullLoanCost">
 *                                         &lt;simpleType>
 *                                           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
 *                                             &lt;totalDigits value="12"/>
 *                                             &lt;fractionDigits value="2"/>
 *                                           &lt;/restriction>
 *                                         &lt;/simpleType>
 *                                       &lt;/element>
 *                                       &lt;element name="AltAnnuitentyPayment">
 *                                         &lt;simpleType>
 *                                           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
 *                                             &lt;totalDigits value="3"/>
 *                                             &lt;fractionDigits value="3"/>
 *                                           &lt;/restriction>
 *                                         &lt;/simpleType>
 *                                       &lt;/element>
 *                                       &lt;element name="AltCreditCardLimit" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *                                     &lt;/sequence>
 *                                   &lt;/restriction>
 *                                 &lt;/complexContent>
 *                               &lt;/complexType>
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
@XmlType(name = "SearchApplicationRs_Type", propOrder = {
    "rqUID",
    "rqTm",
    "spName",
    "srcRq",
    "status",
    "applications"
})
@XmlRootElement(name = "SearchApplicationRs")
public class SearchApplicationRs {

    @XmlElement(name = "RqUID", required = true)
    protected String rqUID;
    @XmlElement(name = "RqTm", required = true, type = String.class)
    @XmlJavaTypeAdapter(CalendarDateTimeNoMillisecondsAdapter.class)
    protected Calendar rqTm;
    @XmlElement(name = "SPName", required = true)
    protected SPNameType spName;
    @XmlElement(name = "SrcRq", required = true)
    protected SearchApplicationRs.SrcRq srcRq;
    @XmlElement(name = "Status", required = true)
    protected SearchApplicationRs.Status status;
    @XmlElement(name = "Application")
    protected List<SearchApplicationRs.Application> applications;

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
     * Gets the value of the srcRq property.
     * 
     * @return
     *     possible object is
     *     {@link SearchApplicationRs.SrcRq }
     *     
     */
    public SearchApplicationRs.SrcRq getSrcRq() {
        return srcRq;
    }

    /**
     * Sets the value of the srcRq property.
     * 
     * @param value
     *     allowed object is
     *     {@link SearchApplicationRs.SrcRq }
     *     
     */
    public void setSrcRq(SearchApplicationRs.SrcRq value) {
        this.srcRq = value;
    }

    /**
     * Gets the value of the status property.
     * 
     * @return
     *     possible object is
     *     {@link SearchApplicationRs.Status }
     *     
     */
    public SearchApplicationRs.Status getStatus() {
        return status;
    }

    /**
     * Sets the value of the status property.
     * 
     * @param value
     *     allowed object is
     *     {@link SearchApplicationRs.Status }
     *     
     */
    public void setStatus(SearchApplicationRs.Status value) {
        this.status = value;
    }

    /**
     * Gets the value of the applications property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the applications property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getApplications().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link SearchApplicationRs.Application }
     * 
     * 
     */
    public List<SearchApplicationRs.Application> getApplications() {
        if (applications == null) {
            applications = new ArrayList<SearchApplicationRs.Application>();
        }
        return this.applications;
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
     *         &lt;element name="StatusCode">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
     *               &lt;enumeration value="-2"/>
     *               &lt;enumeration value="-1"/>
     *               &lt;enumeration value="0"/>
     *               &lt;enumeration value="1"/>
     *               &lt;enumeration value="2"/>
     *               &lt;enumeration value="3"/>
     *               &lt;enumeration value="4"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="ApplicationNumber">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *               &lt;maxLength value="20"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="Type" type="{}NPPartyType_Type"/>
     *         &lt;element name="FullnameKI" type="{}Fullname_Type" minOccurs="0"/>
     *         &lt;element name="LoginKI" type="{}Login_Type" minOccurs="0"/>
     *         &lt;element name="FullnameTM" type="{}Fullname_Type" minOccurs="0"/>
     *         &lt;element name="LoginTM" type="{}Login_Type" minOccurs="0"/>
     *         &lt;element name="Unit">
     *           &lt;simpleType>
     *             &lt;restriction base="{}String">
     *               &lt;length value="11"/>
     *               &lt;pattern value="\d{11}"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="Channel" type="{}Chanel_Type"/>
     *         &lt;element name="appSigningDate" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
     *         &lt;element name="Product" type="{}ProductData_Type"/>
     *         &lt;element name="Approval" minOccurs="0">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="PeriodM" minOccurs="0">
     *                     &lt;simpleType>
     *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}unsignedInt">
     *                         &lt;totalDigits value="4"/>
     *                       &lt;/restriction>
     *                     &lt;/simpleType>
     *                   &lt;/element>
     *                   &lt;element name="Amount" minOccurs="0">
     *                     &lt;simpleType>
     *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
     *                         &lt;totalDigits value="17"/>
     *                         &lt;fractionDigits value="2"/>
     *                       &lt;/restriction>
     *                     &lt;/simpleType>
     *                   &lt;/element>
     *                   &lt;element name="InterestRate" minOccurs="0">
     *                     &lt;simpleType>
     *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
     *                         &lt;totalDigits value="6"/>
     *                         &lt;fractionDigits value="2"/>
     *                       &lt;/restriction>
     *                     &lt;/simpleType>
     *                   &lt;/element>
     *                   &lt;element name="CreditCardLimit" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
     *                   &lt;element name="AutoGrantion" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
     *                 &lt;/sequence>
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *         &lt;element name="Offer" minOccurs="0">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="Alternative" maxOccurs="16">
     *                     &lt;complexType>
     *                       &lt;complexContent>
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                           &lt;sequence>
     *                             &lt;element name="AltPeriodM">
     *                               &lt;simpleType>
     *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}unsignedInt">
     *                                   &lt;totalDigits value="4"/>
     *                                 &lt;/restriction>
     *                               &lt;/simpleType>
     *                             &lt;/element>
     *                             &lt;element name="AltAmount">
     *                               &lt;simpleType>
     *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
     *                                   &lt;totalDigits value="17"/>
     *                                   &lt;fractionDigits value="2"/>
     *                                 &lt;/restriction>
     *                               &lt;/simpleType>
     *                             &lt;/element>
     *                             &lt;element name="AltInterestRate">
     *                               &lt;simpleType>
     *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
     *                                   &lt;totalDigits value="6"/>
     *                                   &lt;fractionDigits value="2"/>
     *                                 &lt;/restriction>
     *                               &lt;/simpleType>
     *                             &lt;/element>
     *                             &lt;element name="AltFullLoanCost">
     *                               &lt;simpleType>
     *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
     *                                   &lt;totalDigits value="12"/>
     *                                   &lt;fractionDigits value="2"/>
     *                                 &lt;/restriction>
     *                               &lt;/simpleType>
     *                             &lt;/element>
     *                             &lt;element name="AltAnnuitentyPayment">
     *                               &lt;simpleType>
     *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
     *                                   &lt;totalDigits value="3"/>
     *                                   &lt;fractionDigits value="3"/>
     *                                 &lt;/restriction>
     *                               &lt;/simpleType>
     *                             &lt;/element>
     *                             &lt;element name="AltCreditCardLimit" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
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
    @XmlType(name = "", propOrder = {
        "statusCode",
        "applicationNumber",
        "type",
        "fullnameKI",
        "loginKI",
        "fullnameTM",
        "loginTM",
        "unit",
        "channel",
        "appSigningDate",
        "product",
        "approval",
        "offer"
    })
    public static class Application {

        @XmlElement(name = "StatusCode")
        protected int statusCode;
        @XmlElement(name = "ApplicationNumber", required = true)
        protected String applicationNumber;
        @XmlElement(name = "Type", required = true)
        protected String type;
        @XmlElement(name = "FullnameKI")
        protected String fullnameKI;
        @XmlElement(name = "LoginKI")
        protected String loginKI;
        @XmlElement(name = "FullnameTM")
        protected String fullnameTM;
        @XmlElement(name = "LoginTM")
        protected String loginTM;
        @XmlElement(name = "Unit", required = true)
        protected String unit;
        @XmlElement(name = "Channel", required = true)
        protected String channel;
        @XmlElement(type = String.class)
        @XmlJavaTypeAdapter(CalendarDateAdapter.class)
        @XmlSchemaType(name = "date")
        protected Calendar appSigningDate;
        @XmlElement(name = "Product", required = true)
        protected ProductDataType product;
        @XmlElement(name = "Approval")
        protected SearchApplicationRs.Application.Approval approval;
        @XmlElement(name = "Offer")
        protected SearchApplicationRs.Application.Offer offer;

        /**
         * Gets the value of the statusCode property.
         * 
         */
        public int getStatusCode() {
            return statusCode;
        }

        /**
         * Sets the value of the statusCode property.
         * 
         */
        public void setStatusCode(int value) {
            this.statusCode = value;
        }

        /**
         * Gets the value of the applicationNumber property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getApplicationNumber() {
            return applicationNumber;
        }

        /**
         * Sets the value of the applicationNumber property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setApplicationNumber(String value) {
            this.applicationNumber = value;
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
         * Gets the value of the fullnameKI property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getFullnameKI() {
            return fullnameKI;
        }

        /**
         * Sets the value of the fullnameKI property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setFullnameKI(String value) {
            this.fullnameKI = value;
        }

        /**
         * Gets the value of the loginKI property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getLoginKI() {
            return loginKI;
        }

        /**
         * Sets the value of the loginKI property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setLoginKI(String value) {
            this.loginKI = value;
        }

        /**
         * Gets the value of the fullnameTM property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getFullnameTM() {
            return fullnameTM;
        }

        /**
         * Sets the value of the fullnameTM property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setFullnameTM(String value) {
            this.fullnameTM = value;
        }

        /**
         * Gets the value of the loginTM property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getLoginTM() {
            return loginTM;
        }

        /**
         * Sets the value of the loginTM property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setLoginTM(String value) {
            this.loginTM = value;
        }

        /**
         * Gets the value of the unit property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getUnit() {
            return unit;
        }

        /**
         * Sets the value of the unit property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setUnit(String value) {
            this.unit = value;
        }

        /**
         * Gets the value of the channel property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getChannel() {
            return channel;
        }

        /**
         * Sets the value of the channel property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setChannel(String value) {
            this.channel = value;
        }

        /**
         * Gets the value of the appSigningDate property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public Calendar getAppSigningDate() {
            return appSigningDate;
        }

        /**
         * Sets the value of the appSigningDate property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setAppSigningDate(Calendar value) {
            this.appSigningDate = value;
        }

        /**
         * Gets the value of the product property.
         * 
         * @return
         *     possible object is
         *     {@link ProductDataType }
         *     
         */
        public ProductDataType getProduct() {
            return product;
        }

        /**
         * Sets the value of the product property.
         * 
         * @param value
         *     allowed object is
         *     {@link ProductDataType }
         *     
         */
        public void setProduct(ProductDataType value) {
            this.product = value;
        }

        /**
         * Gets the value of the approval property.
         * 
         * @return
         *     possible object is
         *     {@link SearchApplicationRs.Application.Approval }
         *     
         */
        public SearchApplicationRs.Application.Approval getApproval() {
            return approval;
        }

        /**
         * Sets the value of the approval property.
         * 
         * @param value
         *     allowed object is
         *     {@link SearchApplicationRs.Application.Approval }
         *     
         */
        public void setApproval(SearchApplicationRs.Application.Approval value) {
            this.approval = value;
        }

        /**
         * Gets the value of the offer property.
         * 
         * @return
         *     possible object is
         *     {@link SearchApplicationRs.Application.Offer }
         *     
         */
        public SearchApplicationRs.Application.Offer getOffer() {
            return offer;
        }

        /**
         * Sets the value of the offer property.
         * 
         * @param value
         *     allowed object is
         *     {@link SearchApplicationRs.Application.Offer }
         *     
         */
        public void setOffer(SearchApplicationRs.Application.Offer value) {
            this.offer = value;
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
         *         &lt;element name="PeriodM" minOccurs="0">
         *           &lt;simpleType>
         *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}unsignedInt">
         *               &lt;totalDigits value="4"/>
         *             &lt;/restriction>
         *           &lt;/simpleType>
         *         &lt;/element>
         *         &lt;element name="Amount" minOccurs="0">
         *           &lt;simpleType>
         *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
         *               &lt;totalDigits value="17"/>
         *               &lt;fractionDigits value="2"/>
         *             &lt;/restriction>
         *           &lt;/simpleType>
         *         &lt;/element>
         *         &lt;element name="InterestRate" minOccurs="0">
         *           &lt;simpleType>
         *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
         *               &lt;totalDigits value="6"/>
         *               &lt;fractionDigits value="2"/>
         *             &lt;/restriction>
         *           &lt;/simpleType>
         *         &lt;/element>
         *         &lt;element name="CreditCardLimit" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
         *         &lt;element name="AutoGrantion" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
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
            "periodM",
            "amount",
            "interestRate",
            "creditCardLimit",
            "autoGrantion"
        })
        public static class Approval {

            @XmlElement(name = "PeriodM")
            protected Long periodM;
            @XmlElement(name = "Amount")
            protected BigDecimal amount;
            @XmlElement(name = "InterestRate")
            protected BigDecimal interestRate;
            @XmlElement(name = "CreditCardLimit")
            protected BigDecimal creditCardLimit;
            @XmlElement(name = "AutoGrantion")
            protected Boolean autoGrantion;

            /**
             * Gets the value of the periodM property.
             * 
             * @return
             *     possible object is
             *     {@link Long }
             *     
             */
            public Long getPeriodM() {
                return periodM;
            }

            /**
             * Sets the value of the periodM property.
             * 
             * @param value
             *     allowed object is
             *     {@link Long }
             *     
             */
            public void setPeriodM(Long value) {
                this.periodM = value;
            }

            /**
             * Gets the value of the amount property.
             * 
             * @return
             *     possible object is
             *     {@link BigDecimal }
             *     
             */
            public BigDecimal getAmount() {
                return amount;
            }

            /**
             * Sets the value of the amount property.
             * 
             * @param value
             *     allowed object is
             *     {@link BigDecimal }
             *     
             */
            public void setAmount(BigDecimal value) {
                this.amount = value;
            }

            /**
             * Gets the value of the interestRate property.
             * 
             * @return
             *     possible object is
             *     {@link BigDecimal }
             *     
             */
            public BigDecimal getInterestRate() {
                return interestRate;
            }

            /**
             * Sets the value of the interestRate property.
             * 
             * @param value
             *     allowed object is
             *     {@link BigDecimal }
             *     
             */
            public void setInterestRate(BigDecimal value) {
                this.interestRate = value;
            }

            /**
             * Gets the value of the creditCardLimit property.
             * 
             * @return
             *     possible object is
             *     {@link BigDecimal }
             *     
             */
            public BigDecimal getCreditCardLimit() {
                return creditCardLimit;
            }

            /**
             * Sets the value of the creditCardLimit property.
             * 
             * @param value
             *     allowed object is
             *     {@link BigDecimal }
             *     
             */
            public void setCreditCardLimit(BigDecimal value) {
                this.creditCardLimit = value;
            }

            /**
             * Gets the value of the autoGrantion property.
             * 
             * @return
             *     possible object is
             *     {@link Boolean }
             *     
             */
            public Boolean getAutoGrantion() {
                return autoGrantion;
            }

            /**
             * Sets the value of the autoGrantion property.
             * 
             * @param value
             *     allowed object is
             *     {@link Boolean }
             *     
             */
            public void setAutoGrantion(Boolean value) {
                this.autoGrantion = value;
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
         *         &lt;element name="Alternative" maxOccurs="16">
         *           &lt;complexType>
         *             &lt;complexContent>
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                 &lt;sequence>
         *                   &lt;element name="AltPeriodM">
         *                     &lt;simpleType>
         *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}unsignedInt">
         *                         &lt;totalDigits value="4"/>
         *                       &lt;/restriction>
         *                     &lt;/simpleType>
         *                   &lt;/element>
         *                   &lt;element name="AltAmount">
         *                     &lt;simpleType>
         *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
         *                         &lt;totalDigits value="17"/>
         *                         &lt;fractionDigits value="2"/>
         *                       &lt;/restriction>
         *                     &lt;/simpleType>
         *                   &lt;/element>
         *                   &lt;element name="AltInterestRate">
         *                     &lt;simpleType>
         *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
         *                         &lt;totalDigits value="6"/>
         *                         &lt;fractionDigits value="2"/>
         *                       &lt;/restriction>
         *                     &lt;/simpleType>
         *                   &lt;/element>
         *                   &lt;element name="AltFullLoanCost">
         *                     &lt;simpleType>
         *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
         *                         &lt;totalDigits value="12"/>
         *                         &lt;fractionDigits value="2"/>
         *                       &lt;/restriction>
         *                     &lt;/simpleType>
         *                   &lt;/element>
         *                   &lt;element name="AltAnnuitentyPayment">
         *                     &lt;simpleType>
         *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
         *                         &lt;totalDigits value="3"/>
         *                         &lt;fractionDigits value="3"/>
         *                       &lt;/restriction>
         *                     &lt;/simpleType>
         *                   &lt;/element>
         *                   &lt;element name="AltCreditCardLimit" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
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
            "alternatives"
        })
        public static class Offer {

            @XmlElement(name = "Alternative", required = true)
            protected List<SearchApplicationRs.Application.Offer.Alternative> alternatives;

            /**
             * Gets the value of the alternatives property.
             * 
             * <p>
             * This accessor method returns a reference to the live list,
             * not a snapshot. Therefore any modification you make to the
             * returned list will be present inside the JAXB object.
             * This is why there is not a <CODE>set</CODE> method for the alternatives property.
             * 
             * <p>
             * For example, to add a new item, do as follows:
             * <pre>
             *    getAlternatives().add(newItem);
             * </pre>
             * 
             * 
             * <p>
             * Objects of the following type(s) are allowed in the list
             * {@link SearchApplicationRs.Application.Offer.Alternative }
             * 
             * 
             */
            public List<SearchApplicationRs.Application.Offer.Alternative> getAlternatives() {
                if (alternatives == null) {
                    alternatives = new ArrayList<SearchApplicationRs.Application.Offer.Alternative>();
                }
                return this.alternatives;
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
             *         &lt;element name="AltPeriodM">
             *           &lt;simpleType>
             *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}unsignedInt">
             *               &lt;totalDigits value="4"/>
             *             &lt;/restriction>
             *           &lt;/simpleType>
             *         &lt;/element>
             *         &lt;element name="AltAmount">
             *           &lt;simpleType>
             *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
             *               &lt;totalDigits value="17"/>
             *               &lt;fractionDigits value="2"/>
             *             &lt;/restriction>
             *           &lt;/simpleType>
             *         &lt;/element>
             *         &lt;element name="AltInterestRate">
             *           &lt;simpleType>
             *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
             *               &lt;totalDigits value="6"/>
             *               &lt;fractionDigits value="2"/>
             *             &lt;/restriction>
             *           &lt;/simpleType>
             *         &lt;/element>
             *         &lt;element name="AltFullLoanCost">
             *           &lt;simpleType>
             *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
             *               &lt;totalDigits value="12"/>
             *               &lt;fractionDigits value="2"/>
             *             &lt;/restriction>
             *           &lt;/simpleType>
             *         &lt;/element>
             *         &lt;element name="AltAnnuitentyPayment">
             *           &lt;simpleType>
             *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
             *               &lt;totalDigits value="3"/>
             *               &lt;fractionDigits value="3"/>
             *             &lt;/restriction>
             *           &lt;/simpleType>
             *         &lt;/element>
             *         &lt;element name="AltCreditCardLimit" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
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
                "altPeriodM",
                "altAmount",
                "altInterestRate",
                "altFullLoanCost",
                "altAnnuitentyPayment",
                "altCreditCardLimit"
            })
            public static class Alternative {

                @XmlElement(name = "AltPeriodM")
                protected long altPeriodM;
                @XmlElement(name = "AltAmount", required = true)
                protected BigDecimal altAmount;
                @XmlElement(name = "AltInterestRate", required = true)
                protected BigDecimal altInterestRate;
                @XmlElement(name = "AltFullLoanCost", required = true)
                protected BigDecimal altFullLoanCost;
                @XmlElement(name = "AltAnnuitentyPayment", required = true)
                protected BigDecimal altAnnuitentyPayment;
                @XmlElement(name = "AltCreditCardLimit")
                protected BigDecimal altCreditCardLimit;

                /**
                 * Gets the value of the altPeriodM property.
                 * 
                 */
                public long getAltPeriodM() {
                    return altPeriodM;
                }

                /**
                 * Sets the value of the altPeriodM property.
                 * 
                 */
                public void setAltPeriodM(long value) {
                    this.altPeriodM = value;
                }

                /**
                 * Gets the value of the altAmount property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link BigDecimal }
                 *     
                 */
                public BigDecimal getAltAmount() {
                    return altAmount;
                }

                /**
                 * Sets the value of the altAmount property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link BigDecimal }
                 *     
                 */
                public void setAltAmount(BigDecimal value) {
                    this.altAmount = value;
                }

                /**
                 * Gets the value of the altInterestRate property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link BigDecimal }
                 *     
                 */
                public BigDecimal getAltInterestRate() {
                    return altInterestRate;
                }

                /**
                 * Sets the value of the altInterestRate property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link BigDecimal }
                 *     
                 */
                public void setAltInterestRate(BigDecimal value) {
                    this.altInterestRate = value;
                }

                /**
                 * Gets the value of the altFullLoanCost property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link BigDecimal }
                 *     
                 */
                public BigDecimal getAltFullLoanCost() {
                    return altFullLoanCost;
                }

                /**
                 * Sets the value of the altFullLoanCost property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link BigDecimal }
                 *     
                 */
                public void setAltFullLoanCost(BigDecimal value) {
                    this.altFullLoanCost = value;
                }

                /**
                 * Gets the value of the altAnnuitentyPayment property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link BigDecimal }
                 *     
                 */
                public BigDecimal getAltAnnuitentyPayment() {
                    return altAnnuitentyPayment;
                }

                /**
                 * Sets the value of the altAnnuitentyPayment property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link BigDecimal }
                 *     
                 */
                public void setAltAnnuitentyPayment(BigDecimal value) {
                    this.altAnnuitentyPayment = value;
                }

                /**
                 * Gets the value of the altCreditCardLimit property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link BigDecimal }
                 *     
                 */
                public BigDecimal getAltCreditCardLimit() {
                    return altCreditCardLimit;
                }

                /**
                 * Sets the value of the altCreditCardLimit property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link BigDecimal }
                 *     
                 */
                public void setAltCreditCardLimit(BigDecimal value) {
                    this.altCreditCardLimit = value;
                }

            }

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
     *         &lt;element ref="{}RqUID"/>
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
        "rqUID"
    })
    public static class SrcRq {

        @XmlElement(name = "RqUID", required = true)
        protected String rqUID;

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
     *         &lt;element name="StatusCode">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="Error" minOccurs="0">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="ErrorCode">
     *                     &lt;simpleType>
     *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
     *                       &lt;/restriction>
     *                     &lt;/simpleType>
     *                   &lt;/element>
     *                   &lt;element name="Message">
     *                     &lt;simpleType>
     *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *                         &lt;maxLength value="255"/>
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
        "statusCode",
        "error"
    })
    public static class Status {

        @XmlElement(name = "StatusCode")
        protected int statusCode;
        @XmlElement(name = "Error")
        protected SearchApplicationRs.Status.Error error;

        /**
         * Gets the value of the statusCode property.
         * 
         */
        public int getStatusCode() {
            return statusCode;
        }

        /**
         * Sets the value of the statusCode property.
         * 
         */
        public void setStatusCode(int value) {
            this.statusCode = value;
        }

        /**
         * Gets the value of the error property.
         * 
         * @return
         *     possible object is
         *     {@link SearchApplicationRs.Status.Error }
         *     
         */
        public SearchApplicationRs.Status.Error getError() {
            return error;
        }

        /**
         * Sets the value of the error property.
         * 
         * @param value
         *     allowed object is
         *     {@link SearchApplicationRs.Status.Error }
         *     
         */
        public void setError(SearchApplicationRs.Status.Error value) {
            this.error = value;
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
         *         &lt;element name="ErrorCode">
         *           &lt;simpleType>
         *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
         *             &lt;/restriction>
         *           &lt;/simpleType>
         *         &lt;/element>
         *         &lt;element name="Message">
         *           &lt;simpleType>
         *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
         *               &lt;maxLength value="255"/>
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
            "errorCode",
            "message"
        })
        public static class Error {

            @XmlElement(name = "ErrorCode")
            protected int errorCode;
            @XmlElement(name = "Message", required = true)
            protected String message;

            /**
             * Gets the value of the errorCode property.
             * 
             */
            public int getErrorCode() {
                return errorCode;
            }

            /**
             * Sets the value of the errorCode property.
             * 
             */
            public void setErrorCode(int value) {
                this.errorCode = value;
            }

            /**
             * Gets the value of the message property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getMessage() {
                return message;
            }

            /**
             * Sets the value of the message property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setMessage(String value) {
                this.message = value;
            }

        }

    }

}
