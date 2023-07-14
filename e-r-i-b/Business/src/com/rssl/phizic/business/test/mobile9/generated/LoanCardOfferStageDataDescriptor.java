//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2015.07.17 at 02:39:21 PM MSD 
//


package com.rssl.phizic.business.test.mobile9.generated;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for LoanCardOfferStageData complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="LoanCardOfferStageData">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="option" maxOccurs="unbounded">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="loanId" type="{}string"/>
 *                   &lt;element name="offerId" type="{}string"/>
 *                   &lt;element name="name" type="{}string"/>
 *                   &lt;element name="gracePeriodDuration" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/>
 *                   &lt;element name="gracePeriodInterestRate" type="{}Amount" minOccurs="0"/>
 *                   &lt;element name="maxLimit" type="{}Money"/>
 *                   &lt;element name="offerInterestRate" type="{}Amount"/>
 *                   &lt;element name="firstYearPayment" type="{}Money"/>
 *                   &lt;element name="nextYearPayment" type="{}Money"/>
 *                   &lt;element name="terms" type="{}string" minOccurs="0"/>
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
@XmlType(name = "LoanCardOfferStageData", propOrder = {
    "option"
})
public class LoanCardOfferStageDataDescriptor {

    @XmlElement(required = true)
    protected List<LoanCardOfferStageDataDescriptor.Option> option;

    /**
     * Gets the value of the option property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the option property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getOption().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link LoanCardOfferStageDataDescriptor.Option }
     * 
     * 
     */
    public List<LoanCardOfferStageDataDescriptor.Option> getOption() {
        if (option == null) {
            option = new ArrayList<LoanCardOfferStageDataDescriptor.Option>();
        }
        return this.option;
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
     *         &lt;element name="loanId" type="{}string"/>
     *         &lt;element name="offerId" type="{}string"/>
     *         &lt;element name="name" type="{}string"/>
     *         &lt;element name="gracePeriodDuration" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/>
     *         &lt;element name="gracePeriodInterestRate" type="{}Amount" minOccurs="0"/>
     *         &lt;element name="maxLimit" type="{}Money"/>
     *         &lt;element name="offerInterestRate" type="{}Amount"/>
     *         &lt;element name="firstYearPayment" type="{}Money"/>
     *         &lt;element name="nextYearPayment" type="{}Money"/>
     *         &lt;element name="terms" type="{}string" minOccurs="0"/>
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
        "loanId",
        "offerId",
        "name",
        "gracePeriodDuration",
        "gracePeriodInterestRate",
        "maxLimit",
        "offerInterestRate",
        "firstYearPayment",
        "nextYearPayment",
        "terms"
    })
    public static class Option {

        @XmlElement(required = true)
        protected String loanId;
        @XmlElement(required = true)
        protected String offerId;
        @XmlElement(required = true)
        protected String name;
        protected BigInteger gracePeriodDuration;
        protected String gracePeriodInterestRate;
        @XmlElement(required = true)
        protected MoneyDescriptor maxLimit;
        @XmlElement(required = true)
        protected String offerInterestRate;
        @XmlElement(required = true)
        protected MoneyDescriptor firstYearPayment;
        @XmlElement(required = true)
        protected MoneyDescriptor nextYearPayment;
        protected String terms;

        /**
         * Gets the value of the loanId property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getLoanId() {
            return loanId;
        }

        /**
         * Sets the value of the loanId property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setLoanId(String value) {
            this.loanId = value;
        }

        /**
         * Gets the value of the offerId property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getOfferId() {
            return offerId;
        }

        /**
         * Sets the value of the offerId property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setOfferId(String value) {
            this.offerId = value;
        }

        /**
         * Gets the value of the name property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getName() {
            return name;
        }

        /**
         * Sets the value of the name property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setName(String value) {
            this.name = value;
        }

        /**
         * Gets the value of the gracePeriodDuration property.
         * 
         * @return
         *     possible object is
         *     {@link BigInteger }
         *     
         */
        public BigInteger getGracePeriodDuration() {
            return gracePeriodDuration;
        }

        /**
         * Sets the value of the gracePeriodDuration property.
         * 
         * @param value
         *     allowed object is
         *     {@link BigInteger }
         *     
         */
        public void setGracePeriodDuration(BigInteger value) {
            this.gracePeriodDuration = value;
        }

        /**
         * Gets the value of the gracePeriodInterestRate property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getGracePeriodInterestRate() {
            return gracePeriodInterestRate;
        }

        /**
         * Sets the value of the gracePeriodInterestRate property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setGracePeriodInterestRate(String value) {
            this.gracePeriodInterestRate = value;
        }

        /**
         * Gets the value of the maxLimit property.
         * 
         * @return
         *     possible object is
         *     {@link MoneyDescriptor }
         *     
         */
        public MoneyDescriptor getMaxLimit() {
            return maxLimit;
        }

        /**
         * Sets the value of the maxLimit property.
         * 
         * @param value
         *     allowed object is
         *     {@link MoneyDescriptor }
         *     
         */
        public void setMaxLimit(MoneyDescriptor value) {
            this.maxLimit = value;
        }

        /**
         * Gets the value of the offerInterestRate property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getOfferInterestRate() {
            return offerInterestRate;
        }

        /**
         * Sets the value of the offerInterestRate property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setOfferInterestRate(String value) {
            this.offerInterestRate = value;
        }

        /**
         * Gets the value of the firstYearPayment property.
         * 
         * @return
         *     possible object is
         *     {@link MoneyDescriptor }
         *     
         */
        public MoneyDescriptor getFirstYearPayment() {
            return firstYearPayment;
        }

        /**
         * Sets the value of the firstYearPayment property.
         * 
         * @param value
         *     allowed object is
         *     {@link MoneyDescriptor }
         *     
         */
        public void setFirstYearPayment(MoneyDescriptor value) {
            this.firstYearPayment = value;
        }

        /**
         * Gets the value of the nextYearPayment property.
         * 
         * @return
         *     possible object is
         *     {@link MoneyDescriptor }
         *     
         */
        public MoneyDescriptor getNextYearPayment() {
            return nextYearPayment;
        }

        /**
         * Sets the value of the nextYearPayment property.
         * 
         * @param value
         *     allowed object is
         *     {@link MoneyDescriptor }
         *     
         */
        public void setNextYearPayment(MoneyDescriptor value) {
            this.nextYearPayment = value;
        }

        /**
         * Gets the value of the terms property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getTerms() {
            return terms;
        }

        /**
         * Sets the value of the terms property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setTerms(String value) {
            this.terms = value;
        }

    }

}
