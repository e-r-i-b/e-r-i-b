//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2013.09.13 at 03:37:37 PM MSD 
//


package com.rssl.phizic.business.test.mobile5.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * ������ �� ������
 * 
 * <p>Java class for DocLoanProduct complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DocLoanProduct">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="documentNumber" type="{}Field"/>
 *         &lt;element name="documentDate" type="{}Field"/>
 *         &lt;element name="creditType" type="{}Field"/>
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
 *         &lt;element name="duration" type="{}Field"/>
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
@XmlType(name = "DocLoanProduct", propOrder = {
    "documentNumber",
    "documentDate",
    "creditType",
    "sum",
    "duration",
    "surName",
    "firstName",
    "patrName",
    "mobilePhone",
    "hirer",
    "averageIncomePerMonth",
    "getPaidOnAccount"
})
public class DocLoanProductDescriptor {

    @XmlElement(required = true)
    protected FieldDescriptor documentNumber;
    @XmlElement(required = true)
    protected FieldDescriptor documentDate;
    @XmlElement(required = true)
    protected FieldDescriptor creditType;
    @XmlElement(required = true)
    protected DocLoanProductDescriptor.Sum sum;
    @XmlElement(required = true)
    protected FieldDescriptor duration;
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
     * Gets the value of the sum property.
     * 
     * @return
     *     possible object is
     *     {@link DocLoanProductDescriptor.Sum }
     *     
     */
    public DocLoanProductDescriptor.Sum getSum() {
        return sum;
    }

    /**
     * Sets the value of the sum property.
     * 
     * @param value
     *     allowed object is
     *     {@link DocLoanProductDescriptor.Sum }
     *     
     */
    public void setSum(DocLoanProductDescriptor.Sum value) {
        this.sum = value;
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
