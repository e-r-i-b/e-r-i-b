//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2015.06.26 at 01:33:00 PM MSD 
//


package com.rssl.phizic.business.test.atm.generated;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ProductsType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ProductsType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="cards" maxOccurs="unbounded" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="status" type="{}string" minOccurs="0"/>
 *                   &lt;element name="card" type="{}CardProductType"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="accounts" maxOccurs="unbounded" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="status" type="{}string" minOccurs="0"/>
 *                   &lt;element name="account" type="{}AccountType"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="loans" maxOccurs="unbounded" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="status" type="{}string" minOccurs="0"/>
 *                   &lt;element name="loan" type="{}LoanType"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="imaccounts" maxOccurs="unbounded" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="status" type="{}string" minOccurs="0"/>
 *                   &lt;element name="imaccount" type="{}IMAccountType"/>
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
@XmlType(name = "ProductsType", propOrder = {
    "cards",
    "accounts",
    "loans",
    "imaccounts"
})
public class ProductsTypeDescriptor {

    protected List<ProductsTypeDescriptor.Cards> cards;
    protected List<ProductsTypeDescriptor.Accounts> accounts;
    protected List<ProductsTypeDescriptor.Loans> loans;
    protected List<ProductsTypeDescriptor.Imaccounts> imaccounts;

    /**
     * Gets the value of the cards property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the cards property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCards().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ProductsTypeDescriptor.Cards }
     * 
     * 
     */
    public List<ProductsTypeDescriptor.Cards> getCards() {
        if (cards == null) {
            cards = new ArrayList<ProductsTypeDescriptor.Cards>();
        }
        return this.cards;
    }

    /**
     * Gets the value of the accounts property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the accounts property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAccounts().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ProductsTypeDescriptor.Accounts }
     * 
     * 
     */
    public List<ProductsTypeDescriptor.Accounts> getAccounts() {
        if (accounts == null) {
            accounts = new ArrayList<ProductsTypeDescriptor.Accounts>();
        }
        return this.accounts;
    }

    /**
     * Gets the value of the loans property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the loans property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getLoans().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ProductsTypeDescriptor.Loans }
     * 
     * 
     */
    public List<ProductsTypeDescriptor.Loans> getLoans() {
        if (loans == null) {
            loans = new ArrayList<ProductsTypeDescriptor.Loans>();
        }
        return this.loans;
    }

    /**
     * Gets the value of the imaccounts property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the imaccounts property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getImaccounts().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ProductsTypeDescriptor.Imaccounts }
     * 
     * 
     */
    public List<ProductsTypeDescriptor.Imaccounts> getImaccounts() {
        if (imaccounts == null) {
            imaccounts = new ArrayList<ProductsTypeDescriptor.Imaccounts>();
        }
        return this.imaccounts;
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
     *         &lt;element name="status" type="{}string" minOccurs="0"/>
     *         &lt;element name="account" type="{}AccountType"/>
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
        "status",
        "account"
    })
    public static class Accounts {

        protected String status;
        @XmlElement(required = true)
        protected AccountTypeDescriptor account;

        /**
         * Gets the value of the status property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getStatus() {
            return status;
        }

        /**
         * Sets the value of the status property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setStatus(String value) {
            this.status = value;
        }

        /**
         * Gets the value of the account property.
         * 
         * @return
         *     possible object is
         *     {@link AccountTypeDescriptor }
         *     
         */
        public AccountTypeDescriptor getAccount() {
            return account;
        }

        /**
         * Sets the value of the account property.
         * 
         * @param value
         *     allowed object is
         *     {@link AccountTypeDescriptor }
         *     
         */
        public void setAccount(AccountTypeDescriptor value) {
            this.account = value;
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
     *         &lt;element name="status" type="{}string" minOccurs="0"/>
     *         &lt;element name="card" type="{}CardProductType"/>
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
        "status",
        "card"
    })
    public static class Cards {

        protected String status;
        @XmlElement(required = true)
        protected CardProductTypeDescriptor card;

        /**
         * Gets the value of the status property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getStatus() {
            return status;
        }

        /**
         * Sets the value of the status property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setStatus(String value) {
            this.status = value;
        }

        /**
         * Gets the value of the card property.
         * 
         * @return
         *     possible object is
         *     {@link CardProductTypeDescriptor }
         *     
         */
        public CardProductTypeDescriptor getCard() {
            return card;
        }

        /**
         * Sets the value of the card property.
         * 
         * @param value
         *     allowed object is
         *     {@link CardProductTypeDescriptor }
         *     
         */
        public void setCard(CardProductTypeDescriptor value) {
            this.card = value;
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
     *         &lt;element name="status" type="{}string" minOccurs="0"/>
     *         &lt;element name="imaccount" type="{}IMAccountType"/>
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
        "status",
        "imaccount"
    })
    public static class Imaccounts {

        protected String status;
        @XmlElement(required = true)
        protected IMAccountTypeDescriptor imaccount;

        /**
         * Gets the value of the status property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getStatus() {
            return status;
        }

        /**
         * Sets the value of the status property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setStatus(String value) {
            this.status = value;
        }

        /**
         * Gets the value of the imaccount property.
         * 
         * @return
         *     possible object is
         *     {@link IMAccountTypeDescriptor }
         *     
         */
        public IMAccountTypeDescriptor getImaccount() {
            return imaccount;
        }

        /**
         * Sets the value of the imaccount property.
         * 
         * @param value
         *     allowed object is
         *     {@link IMAccountTypeDescriptor }
         *     
         */
        public void setImaccount(IMAccountTypeDescriptor value) {
            this.imaccount = value;
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
     *         &lt;element name="status" type="{}string" minOccurs="0"/>
     *         &lt;element name="loan" type="{}LoanType"/>
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
        "status",
        "loan"
    })
    public static class Loans {

        protected String status;
        @XmlElement(required = true)
        protected LoanTypeDescriptor loan;

        /**
         * Gets the value of the status property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getStatus() {
            return status;
        }

        /**
         * Sets the value of the status property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setStatus(String value) {
            this.status = value;
        }

        /**
         * Gets the value of the loan property.
         * 
         * @return
         *     possible object is
         *     {@link LoanTypeDescriptor }
         *     
         */
        public LoanTypeDescriptor getLoan() {
            return loan;
        }

        /**
         * Sets the value of the loan property.
         * 
         * @param value
         *     allowed object is
         *     {@link LoanTypeDescriptor }
         *     
         */
        public void setLoan(LoanTypeDescriptor value) {
            this.loan = value;
        }

    }

}
