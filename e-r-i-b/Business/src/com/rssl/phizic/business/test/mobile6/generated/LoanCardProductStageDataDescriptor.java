//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2013.09.13 at 03:37:43 PM MSD 
//


package com.rssl.phizic.business.test.mobile6.generated;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for LoanCardProductStageData complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="LoanCardProductStageData">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="option" maxOccurs="unbounded">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="loanId" type="{http://www.w3.org/2001/XMLSchema}integer"/>
 *                   &lt;element name="changeDate" type="{http://www.w3.org/2001/XMLSchema}integer"/>
 *                   &lt;element name="name" type="{}string"/>
 *                   &lt;element name="gracePeriod" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="duration" type="{http://www.w3.org/2001/XMLSchema}integer"/>
 *                             &lt;element name="interestRate" type="{}Amount"/>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                   &lt;element name="creditLimit">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="min" type="{}Money"/>
 *                             &lt;element name="max" type="{}Money"/>
 *                             &lt;element name="includeMax" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
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
@XmlType(name = "LoanCardProductStageData", propOrder = {
    "option"
})
public class LoanCardProductStageDataDescriptor {

    @XmlElement(required = true)
    protected List<LoanCardProductStageDataDescriptor.Option> option;

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
     * {@link LoanCardProductStageDataDescriptor.Option }
     * 
     * 
     */
    public List<LoanCardProductStageDataDescriptor.Option> getOption() {
        if (option == null) {
            option = new ArrayList<LoanCardProductStageDataDescriptor.Option>();
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
     *         &lt;element name="loanId" type="{http://www.w3.org/2001/XMLSchema}integer"/>
     *         &lt;element name="changeDate" type="{http://www.w3.org/2001/XMLSchema}integer"/>
     *         &lt;element name="name" type="{}string"/>
     *         &lt;element name="gracePeriod" minOccurs="0">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="duration" type="{http://www.w3.org/2001/XMLSchema}integer"/>
     *                   &lt;element name="interestRate" type="{}Amount"/>
     *                 &lt;/sequence>
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *         &lt;element name="creditLimit">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="min" type="{}Money"/>
     *                   &lt;element name="max" type="{}Money"/>
     *                   &lt;element name="includeMax" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
     *                 &lt;/sequence>
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
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
        "changeDate",
        "name",
        "gracePeriod",
        "creditLimit",
        "offerInterestRate",
        "firstYearPayment",
        "nextYearPayment",
        "terms"
    })
    public static class Option {

        @XmlElement(required = true)
        protected BigInteger loanId;
        @XmlElement(required = true)
        protected BigInteger changeDate;
        @XmlElement(required = true)
        protected String name;
        protected LoanCardProductStageDataDescriptor.Option.GracePeriod gracePeriod;
        @XmlElement(required = true)
        protected LoanCardProductStageDataDescriptor.Option.CreditLimit creditLimit;
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
         *     {@link BigInteger }
         *     
         */
        public BigInteger getLoanId() {
            return loanId;
        }

        /**
         * Sets the value of the loanId property.
         * 
         * @param value
         *     allowed object is
         *     {@link BigInteger }
         *     
         */
        public void setLoanId(BigInteger value) {
            this.loanId = value;
        }

        /**
         * Gets the value of the changeDate property.
         * 
         * @return
         *     possible object is
         *     {@link BigInteger }
         *     
         */
        public BigInteger getChangeDate() {
            return changeDate;
        }

        /**
         * Sets the value of the changeDate property.
         * 
         * @param value
         *     allowed object is
         *     {@link BigInteger }
         *     
         */
        public void setChangeDate(BigInteger value) {
            this.changeDate = value;
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
         * Gets the value of the gracePeriod property.
         * 
         * @return
         *     possible object is
         *     {@link LoanCardProductStageDataDescriptor.Option.GracePeriod }
         *     
         */
        public LoanCardProductStageDataDescriptor.Option.GracePeriod getGracePeriod() {
            return gracePeriod;
        }

        /**
         * Sets the value of the gracePeriod property.
         * 
         * @param value
         *     allowed object is
         *     {@link LoanCardProductStageDataDescriptor.Option.GracePeriod }
         *     
         */
        public void setGracePeriod(LoanCardProductStageDataDescriptor.Option.GracePeriod value) {
            this.gracePeriod = value;
        }

        /**
         * Gets the value of the creditLimit property.
         * 
         * @return
         *     possible object is
         *     {@link LoanCardProductStageDataDescriptor.Option.CreditLimit }
         *     
         */
        public LoanCardProductStageDataDescriptor.Option.CreditLimit getCreditLimit() {
            return creditLimit;
        }

        /**
         * Sets the value of the creditLimit property.
         * 
         * @param value
         *     allowed object is
         *     {@link LoanCardProductStageDataDescriptor.Option.CreditLimit }
         *     
         */
        public void setCreditLimit(LoanCardProductStageDataDescriptor.Option.CreditLimit value) {
            this.creditLimit = value;
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
         *         &lt;element name="min" type="{}Money"/>
         *         &lt;element name="max" type="{}Money"/>
         *         &lt;element name="includeMax" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
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
            "min",
            "max",
            "includeMax"
        })
        public static class CreditLimit {

            @XmlElement(required = true)
            protected MoneyDescriptor min;
            @XmlElement(required = true)
            protected MoneyDescriptor max;
            protected boolean includeMax;

            /**
             * Gets the value of the min property.
             * 
             * @return
             *     possible object is
             *     {@link MoneyDescriptor }
             *     
             */
            public MoneyDescriptor getMin() {
                return min;
            }

            /**
             * Sets the value of the min property.
             * 
             * @param value
             *     allowed object is
             *     {@link MoneyDescriptor }
             *     
             */
            public void setMin(MoneyDescriptor value) {
                this.min = value;
            }

            /**
             * Gets the value of the max property.
             * 
             * @return
             *     possible object is
             *     {@link MoneyDescriptor }
             *     
             */
            public MoneyDescriptor getMax() {
                return max;
            }

            /**
             * Sets the value of the max property.
             * 
             * @param value
             *     allowed object is
             *     {@link MoneyDescriptor }
             *     
             */
            public void setMax(MoneyDescriptor value) {
                this.max = value;
            }

            /**
             * Gets the value of the includeMax property.
             * 
             */
            public boolean isIncludeMax() {
                return includeMax;
            }

            /**
             * Sets the value of the includeMax property.
             * 
             */
            public void setIncludeMax(boolean value) {
                this.includeMax = value;
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
         *         &lt;element name="duration" type="{http://www.w3.org/2001/XMLSchema}integer"/>
         *         &lt;element name="interestRate" type="{}Amount"/>
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
            "duration",
            "interestRate"
        })
        public static class GracePeriod {

            @XmlElement(required = true)
            protected BigInteger duration;
            @XmlElement(required = true)
            protected String interestRate;

            /**
             * Gets the value of the duration property.
             * 
             * @return
             *     possible object is
             *     {@link BigInteger }
             *     
             */
            public BigInteger getDuration() {
                return duration;
            }

            /**
             * Sets the value of the duration property.
             * 
             * @param value
             *     allowed object is
             *     {@link BigInteger }
             *     
             */
            public void setDuration(BigInteger value) {
                this.duration = value;
            }

            /**
             * Gets the value of the interestRate property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getInterestRate() {
                return interestRate;
            }

            /**
             * Sets the value of the interestRate property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setInterestRate(String value) {
                this.interestRate = value;
            }

        }

    }

}
