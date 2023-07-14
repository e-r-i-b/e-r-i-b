//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2014.11.06 at 04:05:33 PM MSK 
//


package com.rssl.phizic.business.test.mobile8.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * ������ �� �������������� ������
 * 
 * <p>Java class for InitLoanOffer complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="InitLoanOffer">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="loan" type="{}Field"/>
 *         &lt;element name="passportNumber" type="{}Field"/>
 *         &lt;element name="passportSeries" type="{}Field"/>
 *         &lt;element name="tb" type="{}Field"/>
 *         &lt;element name="documentNumber" type="{}Field"/>
 *         &lt;element name="documentDate" type="{}Field"/>
 *         &lt;element name="creditType" type="{}Field"/>
 *         &lt;element name="duration" type="{}Field"/>
 *         &lt;element name="sum">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="amount" type="{}Field"/>
 *                   &lt;element name="currency" type="{}Field"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="surName" type="{}Field"/>
 *         &lt;element name="firstName" type="{}Field"/>
 *         &lt;element name="patrName" type="{}Field"/>
 *         &lt;element name="mobilePhone" type="{}Field"/>
 *         &lt;element name="hirer" type="{}Field"/>
 *         &lt;element name="averageIncomePerMonth" type="{}Field"/>
 *         &lt;element name="getPaidOnAccount" type="{}Field"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "InitLoanOffer", propOrder = {
    "loan",
    "passportNumber",
    "passportSeries",
    "tb",
    "documentNumber",
    "documentDate",
    "creditType",
    "duration",
    "sum",
    "surName",
    "firstName",
    "patrName",
    "mobilePhone",
    "hirer",
    "averageIncomePerMonth",
    "getPaidOnAccount"
})
public class InitLoanOfferDescriptor {

    @XmlElement(required = true)
    protected FieldDescriptor loan;
    @XmlElement(required = true)
    protected FieldDescriptor passportNumber;
    @XmlElement(required = true)
    protected FieldDescriptor passportSeries;
    @XmlElement(required = true)
    protected FieldDescriptor tb;
    @XmlElement(required = true)
    protected FieldDescriptor documentNumber;
    @XmlElement(required = true)
    protected FieldDescriptor documentDate;
    @XmlElement(required = true)
    protected FieldDescriptor creditType;
    @XmlElement(required = true)
    protected FieldDescriptor duration;
    @XmlElement(required = true)
    protected InitLoanOfferDescriptor.Sum sum;
    @XmlElement(required = true)
    protected FieldDescriptor surName;
    @XmlElement(required = true)
    protected FieldDescriptor firstName;
    @XmlElement(required = true)
    protected FieldDescriptor patrName;
    @XmlElement(required = true)
    protected FieldDescriptor mobilePhone;
    @XmlElement(required = true)
    protected FieldDescriptor hirer;
    @XmlElement(required = true)
    protected FieldDescriptor averageIncomePerMonth;
    @XmlElement(required = true)
    protected FieldDescriptor getPaidOnAccount;

    /**
     * Gets the value of the loan property.
     * 
     * @return
     *     possible object is
     *     {@link FieldDescriptor }
     *     
     */
    public FieldDescriptor getLoan() {
        return loan;
    }

    /**
     * Sets the value of the loan property.
     * 
     * @param value
     *     allowed object is
     *     {@link FieldDescriptor }
     *     
     */
    public void setLoan(FieldDescriptor value) {
        this.loan = value;
    }

    /**
     * Gets the value of the passportNumber property.
     * 
     * @return
     *     possible object is
     *     {@link FieldDescriptor }
     *     
     */
    public FieldDescriptor getPassportNumber() {
        return passportNumber;
    }

    /**
     * Sets the value of the passportNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link FieldDescriptor }
     *     
     */
    public void setPassportNumber(FieldDescriptor value) {
        this.passportNumber = value;
    }

    /**
     * Gets the value of the passportSeries property.
     * 
     * @return
     *     possible object is
     *     {@link FieldDescriptor }
     *     
     */
    public FieldDescriptor getPassportSeries() {
        return passportSeries;
    }

    /**
     * Sets the value of the passportSeries property.
     * 
     * @param value
     *     allowed object is
     *     {@link FieldDescriptor }
     *     
     */
    public void setPassportSeries(FieldDescriptor value) {
        this.passportSeries = value;
    }

    /**
     * Gets the value of the tb property.
     * 
     * @return
     *     possible object is
     *     {@link FieldDescriptor }
     *     
     */
    public FieldDescriptor getTb() {
        return tb;
    }

    /**
     * Sets the value of the tb property.
     * 
     * @param value
     *     allowed object is
     *     {@link FieldDescriptor }
     *     
     */
    public void setTb(FieldDescriptor value) {
        this.tb = value;
    }

    /**
     * Gets the value of the documentNumber property.
     * 
     * @return
     *     possible object is
     *     {@link FieldDescriptor }
     *     
     */
    public FieldDescriptor getDocumentNumber() {
        return documentNumber;
    }

    /**
     * Sets the value of the documentNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link FieldDescriptor }
     *     
     */
    public void setDocumentNumber(FieldDescriptor value) {
        this.documentNumber = value;
    }

    /**
     * Gets the value of the documentDate property.
     * 
     * @return
     *     possible object is
     *     {@link FieldDescriptor }
     *     
     */
    public FieldDescriptor getDocumentDate() {
        return documentDate;
    }

    /**
     * Sets the value of the documentDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link FieldDescriptor }
     *     
     */
    public void setDocumentDate(FieldDescriptor value) {
        this.documentDate = value;
    }

    /**
     * Gets the value of the creditType property.
     * 
     * @return
     *     possible object is
     *     {@link FieldDescriptor }
     *     
     */
    public FieldDescriptor getCreditType() {
        return creditType;
    }

    /**
     * Sets the value of the creditType property.
     * 
     * @param value
     *     allowed object is
     *     {@link FieldDescriptor }
     *     
     */
    public void setCreditType(FieldDescriptor value) {
        this.creditType = value;
    }

    /**
     * Gets the value of the duration property.
     * 
     * @return
     *     possible object is
     *     {@link FieldDescriptor }
     *     
     */
    public FieldDescriptor getDuration() {
        return duration;
    }

    /**
     * Sets the value of the duration property.
     * 
     * @param value
     *     allowed object is
     *     {@link FieldDescriptor }
     *     
     */
    public void setDuration(FieldDescriptor value) {
        this.duration = value;
    }

    /**
     * Gets the value of the sum property.
     * 
     * @return
     *     possible object is
     *     {@link InitLoanOfferDescriptor.Sum }
     *     
     */
    public InitLoanOfferDescriptor.Sum getSum() {
        return sum;
    }

    /**
     * Sets the value of the sum property.
     * 
     * @param value
     *     allowed object is
     *     {@link InitLoanOfferDescriptor.Sum }
     *     
     */
    public void setSum(InitLoanOfferDescriptor.Sum value) {
        this.sum = value;
    }

    /**
     * Gets the value of the surName property.
     * 
     * @return
     *     possible object is
     *     {@link FieldDescriptor }
     *     
     */
    public FieldDescriptor getSurName() {
        return surName;
    }

    /**
     * Sets the value of the surName property.
     * 
     * @param value
     *     allowed object is
     *     {@link FieldDescriptor }
     *     
     */
    public void setSurName(FieldDescriptor value) {
        this.surName = value;
    }

    /**
     * Gets the value of the firstName property.
     * 
     * @return
     *     possible object is
     *     {@link FieldDescriptor }
     *     
     */
    public FieldDescriptor getFirstName() {
        return firstName;
    }

    /**
     * Sets the value of the firstName property.
     * 
     * @param value
     *     allowed object is
     *     {@link FieldDescriptor }
     *     
     */
    public void setFirstName(FieldDescriptor value) {
        this.firstName = value;
    }

    /**
     * Gets the value of the patrName property.
     * 
     * @return
     *     possible object is
     *     {@link FieldDescriptor }
     *     
     */
    public FieldDescriptor getPatrName() {
        return patrName;
    }

    /**
     * Sets the value of the patrName property.
     * 
     * @param value
     *     allowed object is
     *     {@link FieldDescriptor }
     *     
     */
    public void setPatrName(FieldDescriptor value) {
        this.patrName = value;
    }

    /**
     * Gets the value of the mobilePhone property.
     * 
     * @return
     *     possible object is
     *     {@link FieldDescriptor }
     *     
     */
    public FieldDescriptor getMobilePhone() {
        return mobilePhone;
    }

    /**
     * Sets the value of the mobilePhone property.
     * 
     * @param value
     *     allowed object is
     *     {@link FieldDescriptor }
     *     
     */
    public void setMobilePhone(FieldDescriptor value) {
        this.mobilePhone = value;
    }

    /**
     * Gets the value of the hirer property.
     * 
     * @return
     *     possible object is
     *     {@link FieldDescriptor }
     *     
     */
    public FieldDescriptor getHirer() {
        return hirer;
    }

    /**
     * Sets the value of the hirer property.
     * 
     * @param value
     *     allowed object is
     *     {@link FieldDescriptor }
     *     
     */
    public void setHirer(FieldDescriptor value) {
        this.hirer = value;
    }

    /**
     * Gets the value of the averageIncomePerMonth property.
     * 
     * @return
     *     possible object is
     *     {@link FieldDescriptor }
     *     
     */
    public FieldDescriptor getAverageIncomePerMonth() {
        return averageIncomePerMonth;
    }

    /**
     * Sets the value of the averageIncomePerMonth property.
     * 
     * @param value
     *     allowed object is
     *     {@link FieldDescriptor }
     *     
     */
    public void setAverageIncomePerMonth(FieldDescriptor value) {
        this.averageIncomePerMonth = value;
    }

    /**
     * Gets the value of the getPaidOnAccount property.
     * 
     * @return
     *     possible object is
     *     {@link FieldDescriptor }
     *     
     */
    public FieldDescriptor getGetPaidOnAccount() {
        return getPaidOnAccount;
    }

    /**
     * Sets the value of the getPaidOnAccount property.
     * 
     * @param value
     *     allowed object is
     *     {@link FieldDescriptor }
     *     
     */
    public void setGetPaidOnAccount(FieldDescriptor value) {
        this.getPaidOnAccount = value;
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
     *         &lt;element name="amount" type="{}Field"/>
     *         &lt;element name="currency" type="{}Field"/>
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
        "amount",
        "currency"
    })
    public static class Sum {

        @XmlElement(required = true)
        protected FieldDescriptor amount;
        @XmlElement(required = true)
        protected FieldDescriptor currency;

        /**
         * Gets the value of the amount property.
         * 
         * @return
         *     possible object is
         *     {@link FieldDescriptor }
         *     
         */
        public FieldDescriptor getAmount() {
            return amount;
        }

        /**
         * Sets the value of the amount property.
         * 
         * @param value
         *     allowed object is
         *     {@link FieldDescriptor }
         *     
         */
        public void setAmount(FieldDescriptor value) {
            this.amount = value;
        }

        /**
         * Gets the value of the currency property.
         * 
         * @return
         *     possible object is
         *     {@link FieldDescriptor }
         *     
         */
        public FieldDescriptor getCurrency() {
            return currency;
        }

        /**
         * Sets the value of the currency property.
         * 
         * @param value
         *     allowed object is
         *     {@link FieldDescriptor }
         *     
         */
        public void setCurrency(FieldDescriptor value) {
            this.currency = value;
        }

    }

}
