
package com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.generated;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for PrivateLoanDetails_Type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PrivateLoanDetails_Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ProdId" type="{}AgreemtNum_Type"/>
 *         &lt;element ref="{}LoanType"/>
 *         &lt;element name="Period" type="{}Long"/>
 *         &lt;element name="CreditingRate" type="{}Decimal"/>
 *         &lt;element name="LoanStatus" type="{}Long"/>
 *         &lt;element name="PrincipalBalance" type="{}Decimal"/>
 *         &lt;element name="FullRepaymentAmount" type="{}Decimal"/>
 *         &lt;element name="Overdue" type="{}Long" minOccurs="0"/>
 *         &lt;element name="Accounts" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="Card" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="Element" maxOccurs="unbounded">
 *                               &lt;complexType>
 *                                 &lt;complexContent>
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                     &lt;sequence>
 *                                       &lt;element name="Id" type="{}AcctIdType"/>
 *                                       &lt;element name="Priority" type="{}Integer"/>
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
 *                   &lt;element name="Account" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="Element" maxOccurs="unbounded">
 *                               &lt;complexType>
 *                                 &lt;complexContent>
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                     &lt;sequence>
 *                                       &lt;element name="Id" type="{}AcctIdType"/>
 *                                       &lt;element name="Priority" type="{}Integer"/>
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
 *         &lt;element name="BAKS" type="{}Integer"/>
 *         &lt;element name="AutoGrantion" type="{}Integer"/>
 *         &lt;element name="ApplicationNumberCA" type="{}String"/>
 *         &lt;element ref="{}CustRec" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="AgencyAddress" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="EarlyRepayment" type="{}EarlyRepayment_Type" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="AcctBalOnDate" type="{}AcctBal_Type" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PrivateLoanDetails_Type", propOrder = {
    "prodId",
    "loanType",
    "period",
    "creditingRate",
    "loanStatus",
    "principalBalance",
    "fullRepaymentAmount",
    "overdue",
    "accounts",
    "baks",
    "autoGrantion",
    "applicationNumberCA",
    "custRecs",
    "agencyAddress",
    "earlyRepayments",
    "acctBalOnDates"
})
public class PrivateLoanDetailsType {

    @XmlElement(name = "ProdId", required = true)
    protected String prodId;
    @XmlElement(name = "LoanType", required = true)
    protected String loanType;
    @XmlElement(name = "Period")
    protected long period;
    @XmlElement(name = "CreditingRate", required = true)
    protected BigDecimal creditingRate;
    @XmlElement(name = "LoanStatus")
    protected long loanStatus;
    @XmlElement(name = "PrincipalBalance", required = true)
    protected BigDecimal principalBalance;
    @XmlElement(name = "FullRepaymentAmount", required = true)
    protected BigDecimal fullRepaymentAmount;
    @XmlElement(name = "Overdue")
    protected Long overdue;
    @XmlElement(name = "Accounts")
    protected PrivateLoanDetailsType.Accounts accounts;
    @XmlElement(name = "BAKS", required = true)
    protected BigInteger baks;
    @XmlElement(name = "AutoGrantion", required = true)
    protected BigInteger autoGrantion;
    @XmlElement(name = "ApplicationNumberCA", required = true)
    protected String applicationNumberCA;
    @XmlElement(name = "CustRec")
    protected List<CustRec> custRecs;
    @XmlElement(name = "AgencyAddress", required = true)
    protected String agencyAddress;
    @XmlElement(name = "EarlyRepayment")
    protected List<EarlyRepaymentType> earlyRepayments;
    @XmlElement(name = "AcctBalOnDate")
    protected List<AcctBal> acctBalOnDates;

    /**
     * Gets the value of the prodId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProdId() {
        return prodId;
    }

    /**
     * Sets the value of the prodId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProdId(String value) {
        this.prodId = value;
    }

    /**
     * Gets the value of the loanType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLoanType() {
        return loanType;
    }

    /**
     * Sets the value of the loanType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLoanType(String value) {
        this.loanType = value;
    }

    /**
     * Gets the value of the period property.
     * 
     */
    public long getPeriod() {
        return period;
    }

    /**
     * Sets the value of the period property.
     * 
     */
    public void setPeriod(long value) {
        this.period = value;
    }

    /**
     * Gets the value of the creditingRate property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getCreditingRate() {
        return creditingRate;
    }

    /**
     * Sets the value of the creditingRate property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setCreditingRate(BigDecimal value) {
        this.creditingRate = value;
    }

    /**
     * Gets the value of the loanStatus property.
     * 
     */
    public long getLoanStatus() {
        return loanStatus;
    }

    /**
     * Sets the value of the loanStatus property.
     * 
     */
    public void setLoanStatus(long value) {
        this.loanStatus = value;
    }

    /**
     * Gets the value of the principalBalance property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getPrincipalBalance() {
        return principalBalance;
    }

    /**
     * Sets the value of the principalBalance property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setPrincipalBalance(BigDecimal value) {
        this.principalBalance = value;
    }

    /**
     * Gets the value of the fullRepaymentAmount property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getFullRepaymentAmount() {
        return fullRepaymentAmount;
    }

    /**
     * Sets the value of the fullRepaymentAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setFullRepaymentAmount(BigDecimal value) {
        this.fullRepaymentAmount = value;
    }

    /**
     * Gets the value of the overdue property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getOverdue() {
        return overdue;
    }

    /**
     * Sets the value of the overdue property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setOverdue(Long value) {
        this.overdue = value;
    }

    /**
     * Gets the value of the accounts property.
     * 
     * @return
     *     possible object is
     *     {@link PrivateLoanDetailsType.Accounts }
     *     
     */
    public PrivateLoanDetailsType.Accounts getAccounts() {
        return accounts;
    }

    /**
     * Sets the value of the accounts property.
     * 
     * @param value
     *     allowed object is
     *     {@link PrivateLoanDetailsType.Accounts }
     *     
     */
    public void setAccounts(PrivateLoanDetailsType.Accounts value) {
        this.accounts = value;
    }

    /**
     * Gets the value of the baks property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getBAKS() {
        return baks;
    }

    /**
     * Sets the value of the baks property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setBAKS(BigInteger value) {
        this.baks = value;
    }

    /**
     * Gets the value of the autoGrantion property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getAutoGrantion() {
        return autoGrantion;
    }

    /**
     * Sets the value of the autoGrantion property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setAutoGrantion(BigInteger value) {
        this.autoGrantion = value;
    }

    /**
     * Gets the value of the applicationNumberCA property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getApplicationNumberCA() {
        return applicationNumberCA;
    }

    /**
     * Sets the value of the applicationNumberCA property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setApplicationNumberCA(String value) {
        this.applicationNumberCA = value;
    }

    /**
     * Gets the value of the custRecs property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the custRecs property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCustRecs().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link CustRec }
     * 
     * 
     */
    public List<CustRec> getCustRecs() {
        if (custRecs == null) {
            custRecs = new ArrayList<CustRec>();
        }
        return this.custRecs;
    }

    /**
     * Gets the value of the agencyAddress property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAgencyAddress() {
        return agencyAddress;
    }

    /**
     * Sets the value of the agencyAddress property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAgencyAddress(String value) {
        this.agencyAddress = value;
    }

    /**
     * Gets the value of the earlyRepayments property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the earlyRepayments property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getEarlyRepayments().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link EarlyRepaymentType }
     * 
     * 
     */
    public List<EarlyRepaymentType> getEarlyRepayments() {
        if (earlyRepayments == null) {
            earlyRepayments = new ArrayList<EarlyRepaymentType>();
        }
        return this.earlyRepayments;
    }

    /**
     * Gets the value of the acctBalOnDates property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the acctBalOnDates property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAcctBalOnDates().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link AcctBal }
     * 
     * 
     */
    public List<AcctBal> getAcctBalOnDates() {
        if (acctBalOnDates == null) {
            acctBalOnDates = new ArrayList<AcctBal>();
        }
        return this.acctBalOnDates;
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
     *         &lt;element name="Card" minOccurs="0">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="Element" maxOccurs="unbounded">
     *                     &lt;complexType>
     *                       &lt;complexContent>
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                           &lt;sequence>
     *                             &lt;element name="Id" type="{}AcctIdType"/>
     *                             &lt;element name="Priority" type="{}Integer"/>
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
     *         &lt;element name="Account" minOccurs="0">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="Element" maxOccurs="unbounded">
     *                     &lt;complexType>
     *                       &lt;complexContent>
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                           &lt;sequence>
     *                             &lt;element name="Id" type="{}AcctIdType"/>
     *                             &lt;element name="Priority" type="{}Integer"/>
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
        "card",
        "account"
    })
    public static class Accounts {

        @XmlElement(name = "Card")
        protected PrivateLoanDetailsType.Accounts.Card card;
        @XmlElement(name = "Account")
        protected PrivateLoanDetailsType.Accounts.Account account;

        /**
         * Gets the value of the card property.
         * 
         * @return
         *     possible object is
         *     {@link PrivateLoanDetailsType.Accounts.Card }
         *     
         */
        public PrivateLoanDetailsType.Accounts.Card getCard() {
            return card;
        }

        /**
         * Sets the value of the card property.
         * 
         * @param value
         *     allowed object is
         *     {@link PrivateLoanDetailsType.Accounts.Card }
         *     
         */
        public void setCard(PrivateLoanDetailsType.Accounts.Card value) {
            this.card = value;
        }

        /**
         * Gets the value of the account property.
         * 
         * @return
         *     possible object is
         *     {@link PrivateLoanDetailsType.Accounts.Account }
         *     
         */
        public PrivateLoanDetailsType.Accounts.Account getAccount() {
            return account;
        }

        /**
         * Sets the value of the account property.
         * 
         * @param value
         *     allowed object is
         *     {@link PrivateLoanDetailsType.Accounts.Account }
         *     
         */
        public void setAccount(PrivateLoanDetailsType.Accounts.Account value) {
            this.account = value;
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
         *         &lt;element name="Element" maxOccurs="unbounded">
         *           &lt;complexType>
         *             &lt;complexContent>
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                 &lt;sequence>
         *                   &lt;element name="Id" type="{}AcctIdType"/>
         *                   &lt;element name="Priority" type="{}Integer"/>
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
            "elements"
        })
        public static class Account {

            @XmlElement(name = "Element", required = true)
            protected List<PrivateLoanDetailsType.Accounts.Account.Element> elements;

            /**
             * Gets the value of the elements property.
             * 
             * <p>
             * This accessor method returns a reference to the live list,
             * not a snapshot. Therefore any modification you make to the
             * returned list will be present inside the JAXB object.
             * This is why there is not a <CODE>set</CODE> method for the elements property.
             * 
             * <p>
             * For example, to add a new item, do as follows:
             * <pre>
             *    getElements().add(newItem);
             * </pre>
             * 
             * 
             * <p>
             * Objects of the following type(s) are allowed in the list
             * {@link PrivateLoanDetailsType.Accounts.Account.Element }
             * 
             * 
             */
            public List<PrivateLoanDetailsType.Accounts.Account.Element> getElements() {
                if (elements == null) {
                    elements = new ArrayList<PrivateLoanDetailsType.Accounts.Account.Element>();
                }
                return this.elements;
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
             *         &lt;element name="Id" type="{}AcctIdType"/>
             *         &lt;element name="Priority" type="{}Integer"/>
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
                "id",
                "priority"
            })
            public static class Element {

                @XmlElement(name = "Id", required = true)
                protected String id;
                @XmlElement(name = "Priority", required = true)
                protected BigInteger priority;

                /**
                 * Gets the value of the id property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getId() {
                    return id;
                }

                /**
                 * Sets the value of the id property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setId(String value) {
                    this.id = value;
                }

                /**
                 * Gets the value of the priority property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link BigInteger }
                 *     
                 */
                public BigInteger getPriority() {
                    return priority;
                }

                /**
                 * Sets the value of the priority property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link BigInteger }
                 *     
                 */
                public void setPriority(BigInteger value) {
                    this.priority = value;
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
         *         &lt;element name="Element" maxOccurs="unbounded">
         *           &lt;complexType>
         *             &lt;complexContent>
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                 &lt;sequence>
         *                   &lt;element name="Id" type="{}AcctIdType"/>
         *                   &lt;element name="Priority" type="{}Integer"/>
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
            "elements"
        })
        public static class Card {

            @XmlElement(name = "Element", required = true)
            protected List<PrivateLoanDetailsType.Accounts.Card.Element> elements;

            /**
             * Gets the value of the elements property.
             * 
             * <p>
             * This accessor method returns a reference to the live list,
             * not a snapshot. Therefore any modification you make to the
             * returned list will be present inside the JAXB object.
             * This is why there is not a <CODE>set</CODE> method for the elements property.
             * 
             * <p>
             * For example, to add a new item, do as follows:
             * <pre>
             *    getElements().add(newItem);
             * </pre>
             * 
             * 
             * <p>
             * Objects of the following type(s) are allowed in the list
             * {@link PrivateLoanDetailsType.Accounts.Card.Element }
             * 
             * 
             */
            public List<PrivateLoanDetailsType.Accounts.Card.Element> getElements() {
                if (elements == null) {
                    elements = new ArrayList<PrivateLoanDetailsType.Accounts.Card.Element>();
                }
                return this.elements;
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
             *         &lt;element name="Id" type="{}AcctIdType"/>
             *         &lt;element name="Priority" type="{}Integer"/>
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
                "id",
                "priority"
            })
            public static class Element {

                @XmlElement(name = "Id", required = true)
                protected String id;
                @XmlElement(name = "Priority", required = true)
                protected BigInteger priority;

                /**
                 * Gets the value of the id property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getId() {
                    return id;
                }

                /**
                 * Sets the value of the id property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setId(String value) {
                    this.id = value;
                }

                /**
                 * Gets the value of the priority property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link BigInteger }
                 *     
                 */
                public BigInteger getPriority() {
                    return priority;
                }

                /**
                 * Sets the value of the priority property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link BigInteger }
                 *     
                 */
                public void setPriority(BigInteger value) {
                    this.priority = value;
                }

            }

        }

    }

}
