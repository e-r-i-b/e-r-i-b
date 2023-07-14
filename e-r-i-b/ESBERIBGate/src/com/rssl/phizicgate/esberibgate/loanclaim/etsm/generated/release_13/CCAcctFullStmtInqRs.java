
package com.rssl.phizicgate.esberibgate.loanclaim.etsm.generated.release_13;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import com.rssl.phizic.utils.jaxb.CalendarDateAdapter;
import com.rssl.phizic.utils.jaxb.CalendarDateTimeAdapter;


/**
 * Ответ получения полной выписки по карте
 * 
 * <p>Java class for CCAcctFullStmtInqRs_Type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CCAcctFullStmtInqRs_Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}RqUID"/>
 *         &lt;element ref="{}RqTm"/>
 *         &lt;element ref="{}OperUID"/>
 *         &lt;element ref="{}Status"/>
 *         &lt;element name="CardAcctRec" maxOccurs="unbounded">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="FromDate" type="{}Date"/>
 *                   &lt;element name="ToDate" type="{}Date"/>
 *                   &lt;element ref="{}CardAcctId"/>
 *                   &lt;element ref="{}AcctBal" maxOccurs="2" minOccurs="0"/>
 *                   &lt;element name="StartBalance" type="{}AcctBal_Type" minOccurs="0"/>
 *                   &lt;element name="EndBalance" type="{}AcctBal_Type" minOccurs="0"/>
 *                   &lt;element ref="{}CCAcctStmtRec" maxOccurs="10" minOccurs="0"/>
 *                   &lt;element ref="{}Status"/>
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
@XmlType(name = "CCAcctFullStmtInqRs_Type", propOrder = {
    "rqUID",
    "rqTm",
    "operUID",
    "status",
    "cardAcctRecs"
})
@XmlRootElement(name = "CCAcctFullStmtInqRs")
public class CCAcctFullStmtInqRs {

    @XmlElement(name = "RqUID", required = true)
    protected String rqUID;
    @XmlElement(name = "RqTm", required = true, type = String.class)
    @XmlJavaTypeAdapter(CalendarDateTimeAdapter.class)
    protected Calendar rqTm;
    @XmlElement(name = "OperUID", required = true)
    protected String operUID;
    @XmlElement(name = "Status", required = true)
    protected Status status;
    @XmlElement(name = "CardAcctRec", required = true)
    protected List<CCAcctFullStmtInqRs.CardAcctRec> cardAcctRecs;

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
     * Gets the value of the status property.
     * 
     * @return
     *     possible object is
     *     {@link Status }
     *     
     */
    public Status getStatus() {
        return status;
    }

    /**
     * Sets the value of the status property.
     * 
     * @param value
     *     allowed object is
     *     {@link Status }
     *     
     */
    public void setStatus(Status value) {
        this.status = value;
    }

    /**
     * Gets the value of the cardAcctRecs property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the cardAcctRecs property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCardAcctRecs().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link CCAcctFullStmtInqRs.CardAcctRec }
     * 
     * 
     */
    public List<CCAcctFullStmtInqRs.CardAcctRec> getCardAcctRecs() {
        if (cardAcctRecs == null) {
            cardAcctRecs = new ArrayList<CCAcctFullStmtInqRs.CardAcctRec>();
        }
        return this.cardAcctRecs;
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
     *         &lt;element name="FromDate" type="{}Date"/>
     *         &lt;element name="ToDate" type="{}Date"/>
     *         &lt;element ref="{}CardAcctId"/>
     *         &lt;element ref="{}AcctBal" maxOccurs="2" minOccurs="0"/>
     *         &lt;element name="StartBalance" type="{}AcctBal_Type" minOccurs="0"/>
     *         &lt;element name="EndBalance" type="{}AcctBal_Type" minOccurs="0"/>
     *         &lt;element ref="{}CCAcctStmtRec" maxOccurs="10" minOccurs="0"/>
     *         &lt;element ref="{}Status"/>
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
        "fromDate",
        "toDate",
        "cardAcctId",
        "acctBals",
        "startBalance",
        "endBalance",
        "ccAcctStmtRecs",
        "status"
    })
    public static class CardAcctRec {

        @XmlElement(name = "FromDate", required = true, type = String.class)
        @XmlJavaTypeAdapter(CalendarDateAdapter.class)
        protected Calendar fromDate;
        @XmlElement(name = "ToDate", required = true, type = String.class)
        @XmlJavaTypeAdapter(CalendarDateAdapter.class)
        protected Calendar toDate;
        @XmlElement(name = "CardAcctId", required = true)
        protected CardAcctIdType cardAcctId;
        @XmlElement(name = "AcctBal")
        protected List<AcctBal> acctBals;
        @XmlElement(name = "StartBalance")
        protected AcctBal startBalance;
        @XmlElement(name = "EndBalance")
        protected AcctBal endBalance;
        @XmlElement(name = "CCAcctStmtRec")
        protected List<CCAcctStmtRec> ccAcctStmtRecs;
        @XmlElement(name = "Status", required = true)
        protected Status status;

        /**
         * Gets the value of the fromDate property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public Calendar getFromDate() {
            return fromDate;
        }

        /**
         * Sets the value of the fromDate property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setFromDate(Calendar value) {
            this.fromDate = value;
        }

        /**
         * Gets the value of the toDate property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public Calendar getToDate() {
            return toDate;
        }

        /**
         * Sets the value of the toDate property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setToDate(Calendar value) {
            this.toDate = value;
        }

        /**
         * Gets the value of the cardAcctId property.
         * 
         * @return
         *     possible object is
         *     {@link CardAcctIdType }
         *     
         */
        public CardAcctIdType getCardAcctId() {
            return cardAcctId;
        }

        /**
         * Sets the value of the cardAcctId property.
         * 
         * @param value
         *     allowed object is
         *     {@link CardAcctIdType }
         *     
         */
        public void setCardAcctId(CardAcctIdType value) {
            this.cardAcctId = value;
        }

        /**
         * Gets the value of the acctBals property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the acctBals property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getAcctBals().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link AcctBal }
         * 
         * 
         */
        public List<AcctBal> getAcctBals() {
            if (acctBals == null) {
                acctBals = new ArrayList<AcctBal>();
            }
            return this.acctBals;
        }

        /**
         * Gets the value of the startBalance property.
         * 
         * @return
         *     possible object is
         *     {@link AcctBal }
         *     
         */
        public AcctBal getStartBalance() {
            return startBalance;
        }

        /**
         * Sets the value of the startBalance property.
         * 
         * @param value
         *     allowed object is
         *     {@link AcctBal }
         *     
         */
        public void setStartBalance(AcctBal value) {
            this.startBalance = value;
        }

        /**
         * Gets the value of the endBalance property.
         * 
         * @return
         *     possible object is
         *     {@link AcctBal }
         *     
         */
        public AcctBal getEndBalance() {
            return endBalance;
        }

        /**
         * Sets the value of the endBalance property.
         * 
         * @param value
         *     allowed object is
         *     {@link AcctBal }
         *     
         */
        public void setEndBalance(AcctBal value) {
            this.endBalance = value;
        }

        /**
         * Gets the value of the ccAcctStmtRecs property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the ccAcctStmtRecs property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getCCAcctStmtRecs().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link CCAcctStmtRec }
         * 
         * 
         */
        public List<CCAcctStmtRec> getCCAcctStmtRecs() {
            if (ccAcctStmtRecs == null) {
                ccAcctStmtRecs = new ArrayList<CCAcctStmtRec>();
            }
            return this.ccAcctStmtRecs;
        }

        /**
         * Gets the value of the status property.
         * 
         * @return
         *     possible object is
         *     {@link Status }
         *     
         */
        public Status getStatus() {
            return status;
        }

        /**
         * Sets the value of the status property.
         * 
         * @param value
         *     allowed object is
         *     {@link Status }
         *     
         */
        public void setStatus(Status value) {
            this.status = value;
        }

    }

}
