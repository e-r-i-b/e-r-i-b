//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2013.09.13 at 03:37:37 PM MSD 
//


package com.rssl.phizic.business.test.mobile5.generated;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for LoanOfferStageData complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="LoanOfferStageData">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="option" maxOccurs="unbounded">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="loanId" type="{http://www.w3.org/2001/XMLSchema}integer"/>
 *                   &lt;element name="name" type="{}string"/>
 *                   &lt;element name="offerSum" type="{}Money"/>
 *                   &lt;element name="rate" type="{}Amount"/>
 *                   &lt;element name="duration" type="{http://www.w3.org/2001/XMLSchema}integer"/>
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
@XmlType(name = "LoanOfferStageData", propOrder = {
    "option"
})
public class LoanOfferStageDataDescriptor {

    @XmlElement(required = true)
    protected List<LoanOfferStageDataDescriptor.Option> option;

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
     * {@link LoanOfferStageDataDescriptor.Option }
     * 
     * 
     */
    public List<LoanOfferStageDataDescriptor.Option> getOption() {
        if (option == null) {
            option = new ArrayList<LoanOfferStageDataDescriptor.Option>();
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
     *         &lt;element name="name" type="{}string"/>
     *         &lt;element name="offerSum" type="{}Money"/>
     *         &lt;element name="rate" type="{}Amount"/>
     *         &lt;element name="duration" type="{http://www.w3.org/2001/XMLSchema}integer"/>
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
        "name",
        "offerSum",
        "rate",
        "duration"
    })
    public static class Option {

        @XmlElement(required = true)
        protected BigInteger loanId;
        @XmlElement(required = true)
        protected String name;
        @XmlElement(required = true)
        protected MoneyDescriptor offerSum;
        @XmlElement(required = true)
        protected String rate;
        @XmlElement(required = true)
        protected BigInteger duration;

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
         * Gets the value of the offerSum property.
         * 
         * @return
         *     possible object is
         *     {@link MoneyDescriptor }
         *     
         */
        public MoneyDescriptor getOfferSum() {
            return offerSum;
        }

        /**
         * Sets the value of the offerSum property.
         * 
         * @param value
         *     allowed object is
         *     {@link MoneyDescriptor }
         *     
         */
        public void setOfferSum(MoneyDescriptor value) {
            this.offerSum = value;
        }

        /**
         * Gets the value of the rate property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getRate() {
            return rate;
        }

        /**
         * Sets the value of the rate property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setRate(String value) {
            this.rate = value;
        }

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

    }

}
