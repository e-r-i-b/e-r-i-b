
package com.rssl.phizicgate.esberibgate.loanclaim.etsm.generated.release_19;

import java.math.BigDecimal;
import java.util.Calendar;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import com.rssl.phizic.utils.jaxb.CalendarDateTimeNoMillisecondsAdapter;


/**
 * Тип данных - ответ на оферту
 * 
 * <p>Java class for ConsumerProductOfferResult_Type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ConsumerProductOfferResult_Type">
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
 *                   &lt;element name="RqUID" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element ref="{}OperUID"/>
 *         &lt;element name="Offer">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="OfferAccept" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *                   &lt;element name="FinalAmount" minOccurs="0">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
 *                         &lt;totalDigits value="17"/>
 *                         &lt;fractionDigits value="2"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="FinalPeriodM" minOccurs="0">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}unsignedInt">
 *                         &lt;totalDigits value="4"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="FinalInterestRate" minOccurs="0">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
 *                         &lt;totalDigits value="6"/>
 *                         &lt;fractionDigits value="2"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="OfferDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="Insurance" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="businessProcess" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="insuranceProgram" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="includeInsuranceFlag" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *                   &lt;element name="insurancePremium" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
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
@XmlType(name = "ConsumerProductOfferResult_Type", propOrder = {
    "rqUID",
    "rqTm",
    "spName",
    "srcRq",
    "operUID",
    "offer",
    "insurance"
})
@XmlRootElement(name = "ConsumerProductOfferResultRq")
public class ConsumerProductOfferResultRq {

    @XmlElement(name = "RqUID", required = true)
    protected String rqUID;
    @XmlElement(name = "RqTm", required = true, type = String.class)
    @XmlJavaTypeAdapter(CalendarDateTimeNoMillisecondsAdapter.class)
    protected Calendar rqTm;
    @XmlElement(name = "SPName", required = true)
    protected SPNameType spName;
    @XmlElement(name = "SrcRq", required = true)
    protected ConsumerProductOfferResultRq.SrcRq srcRq;
    @XmlElement(name = "OperUID", required = true)
    protected String operUID;
    @XmlElement(name = "Offer", required = true)
    protected ConsumerProductOfferResultRq.Offer offer;
    @XmlElement(name = "Insurance")
    protected ConsumerProductOfferResultRq.Insurance insurance;

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
     *     {@link ConsumerProductOfferResultRq.SrcRq }
     *     
     */
    public ConsumerProductOfferResultRq.SrcRq getSrcRq() {
        return srcRq;
    }

    /**
     * Sets the value of the srcRq property.
     * 
     * @param value
     *     allowed object is
     *     {@link ConsumerProductOfferResultRq.SrcRq }
     *     
     */
    public void setSrcRq(ConsumerProductOfferResultRq.SrcRq value) {
        this.srcRq = value;
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
     * Gets the value of the offer property.
     * 
     * @return
     *     possible object is
     *     {@link ConsumerProductOfferResultRq.Offer }
     *     
     */
    public ConsumerProductOfferResultRq.Offer getOffer() {
        return offer;
    }

    /**
     * Sets the value of the offer property.
     * 
     * @param value
     *     allowed object is
     *     {@link ConsumerProductOfferResultRq.Offer }
     *     
     */
    public void setOffer(ConsumerProductOfferResultRq.Offer value) {
        this.offer = value;
    }

    /**
     * Gets the value of the insurance property.
     * 
     * @return
     *     possible object is
     *     {@link ConsumerProductOfferResultRq.Insurance }
     *     
     */
    public ConsumerProductOfferResultRq.Insurance getInsurance() {
        return insurance;
    }

    /**
     * Sets the value of the insurance property.
     * 
     * @param value
     *     allowed object is
     *     {@link ConsumerProductOfferResultRq.Insurance }
     *     
     */
    public void setInsurance(ConsumerProductOfferResultRq.Insurance value) {
        this.insurance = value;
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
     *         &lt;element name="businessProcess" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="insuranceProgram" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="includeInsuranceFlag" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
     *         &lt;element name="insurancePremium" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
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
        "businessProcess",
        "insuranceProgram",
        "includeInsuranceFlag",
        "insurancePremium"
    })
    public static class Insurance {

        @XmlElement(required = true)
        protected String businessProcess;
        @XmlElement(required = true)
        protected String insuranceProgram;
        protected boolean includeInsuranceFlag;
        @XmlElement(required = true)
        protected BigDecimal insurancePremium;

        /**
         * Gets the value of the businessProcess property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getBusinessProcess() {
            return businessProcess;
        }

        /**
         * Sets the value of the businessProcess property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setBusinessProcess(String value) {
            this.businessProcess = value;
        }

        /**
         * Gets the value of the insuranceProgram property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getInsuranceProgram() {
            return insuranceProgram;
        }

        /**
         * Sets the value of the insuranceProgram property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setInsuranceProgram(String value) {
            this.insuranceProgram = value;
        }

        /**
         * Gets the value of the includeInsuranceFlag property.
         * 
         */
        public boolean isIncludeInsuranceFlag() {
            return includeInsuranceFlag;
        }

        /**
         * Sets the value of the includeInsuranceFlag property.
         * 
         */
        public void setIncludeInsuranceFlag(boolean value) {
            this.includeInsuranceFlag = value;
        }

        /**
         * Gets the value of the insurancePremium property.
         * 
         * @return
         *     possible object is
         *     {@link BigDecimal }
         *     
         */
        public BigDecimal getInsurancePremium() {
            return insurancePremium;
        }

        /**
         * Sets the value of the insurancePremium property.
         * 
         * @param value
         *     allowed object is
         *     {@link BigDecimal }
         *     
         */
        public void setInsurancePremium(BigDecimal value) {
            this.insurancePremium = value;
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
     *         &lt;element name="OfferAccept" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
     *         &lt;element name="FinalAmount" minOccurs="0">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
     *               &lt;totalDigits value="17"/>
     *               &lt;fractionDigits value="2"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="FinalPeriodM" minOccurs="0">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}unsignedInt">
     *               &lt;totalDigits value="4"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="FinalInterestRate" minOccurs="0">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
     *               &lt;totalDigits value="6"/>
     *               &lt;fractionDigits value="2"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="OfferDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
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
        "offerAccept",
        "finalAmount",
        "finalPeriodM",
        "finalInterestRate",
        "offerDate"
    })
    public static class Offer {

        @XmlElement(name = "OfferAccept")
        protected boolean offerAccept;
        @XmlElement(name = "FinalAmount")
        protected BigDecimal finalAmount;
        @XmlElement(name = "FinalPeriodM")
        protected Long finalPeriodM;
        @XmlElement(name = "FinalInterestRate")
        protected BigDecimal finalInterestRate;
        @XmlElement(name = "OfferDate", type = String.class)
        @XmlJavaTypeAdapter(CalendarDateTimeNoMillisecondsAdapter.class)
        @XmlSchemaType(name = "dateTime")
        protected Calendar offerDate;

        /**
         * Gets the value of the offerAccept property.
         * 
         */
        public boolean isOfferAccept() {
            return offerAccept;
        }

        /**
         * Sets the value of the offerAccept property.
         * 
         */
        public void setOfferAccept(boolean value) {
            this.offerAccept = value;
        }

        /**
         * Gets the value of the finalAmount property.
         * 
         * @return
         *     possible object is
         *     {@link BigDecimal }
         *     
         */
        public BigDecimal getFinalAmount() {
            return finalAmount;
        }

        /**
         * Sets the value of the finalAmount property.
         * 
         * @param value
         *     allowed object is
         *     {@link BigDecimal }
         *     
         */
        public void setFinalAmount(BigDecimal value) {
            this.finalAmount = value;
        }

        /**
         * Gets the value of the finalPeriodM property.
         * 
         * @return
         *     possible object is
         *     {@link Long }
         *     
         */
        public Long getFinalPeriodM() {
            return finalPeriodM;
        }

        /**
         * Sets the value of the finalPeriodM property.
         * 
         * @param value
         *     allowed object is
         *     {@link Long }
         *     
         */
        public void setFinalPeriodM(Long value) {
            this.finalPeriodM = value;
        }

        /**
         * Gets the value of the finalInterestRate property.
         * 
         * @return
         *     possible object is
         *     {@link BigDecimal }
         *     
         */
        public BigDecimal getFinalInterestRate() {
            return finalInterestRate;
        }

        /**
         * Sets the value of the finalInterestRate property.
         * 
         * @param value
         *     allowed object is
         *     {@link BigDecimal }
         *     
         */
        public void setFinalInterestRate(BigDecimal value) {
            this.finalInterestRate = value;
        }

        /**
         * Gets the value of the offerDate property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public Calendar getOfferDate() {
            return offerDate;
        }

        /**
         * Sets the value of the offerDate property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setOfferDate(Calendar value) {
            this.offerDate = value;
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
     *         &lt;element name="RqUID" type="{http://www.w3.org/2001/XMLSchema}string"/>
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

}
