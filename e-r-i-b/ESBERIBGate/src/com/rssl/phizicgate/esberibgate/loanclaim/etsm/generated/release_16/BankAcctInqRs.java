
package com.rssl.phizicgate.esberibgate.loanclaim.etsm.generated.release_16;

import com.rssl.phizic.utils.jaxb.CalendarDateTimeNoMillisecondsAdapter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * Получение списка продуктов
 * 
 * <p>Java class for BankAcctInqRs_Type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="BankAcctInqRs_Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}RqUID"/>
 *         &lt;element ref="{}RqTm"/>
 *         &lt;element ref="{}OperUID"/>
 *         &lt;element ref="{}Status"/>
 *         &lt;element ref="{}DepAcctRec" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element ref="{}BankAcctRec" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element ref="{}CardAcctRec" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="DepoAccounts" type="{}DepoAccounts_Type" minOccurs="0"/>
 *         &lt;element ref="{}SvcsAcct" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element ref="{}LoanAcctRec" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BankAcctInqRs_Type", propOrder = {
    "rqUID",
    "rqTm",
    "operUID",
    "status",
    "depAcctRecs",
    "bankAcctRecs",
    "cardAcctRecs",
    "depoAccounts",
    "svcsAccts",
    "loanAcctRecs"
})
@XmlRootElement(name = "BankAcctInqRs")
public class BankAcctInqRs {

    @XmlElement(name = "RqUID", required = true)
    protected String rqUID;
    @XmlElement(name = "RqTm", required = true, type = String.class)
    @XmlJavaTypeAdapter(CalendarDateTimeNoMillisecondsAdapter.class)
    protected Calendar rqTm;
    @XmlElement(name = "OperUID", required = true)
    protected String operUID;
    @XmlElement(name = "Status", required = true)
    protected Status status;
    @XmlElement(name = "DepAcctRec")
    protected List<DepAcctRec> depAcctRecs;
    @XmlElement(name = "BankAcctRec")
    protected List<BankAcctRec> bankAcctRecs;
    @XmlElement(name = "CardAcctRec")
    protected List<CardAcctRec> cardAcctRecs;
    @XmlElement(name = "DepoAccounts")
    protected DepoAccountsType depoAccounts;
    @XmlElement(name = "SvcsAcct")
    protected List<SvcsAcct> svcsAccts;
    @XmlElement(name = "LoanAcctRec")
    protected List<LoanAcctRec> loanAcctRecs;

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
     * Gets the value of the depAcctRecs property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the depAcctRecs property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDepAcctRecs().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DepAcctRec }
     * 
     * 
     */
    public List<DepAcctRec> getDepAcctRecs() {
        if (depAcctRecs == null) {
            depAcctRecs = new ArrayList<DepAcctRec>();
        }
        return this.depAcctRecs;
    }

    /**
     * Gets the value of the bankAcctRecs property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the bankAcctRecs property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getBankAcctRecs().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link BankAcctRec }
     * 
     * 
     */
    public List<BankAcctRec> getBankAcctRecs() {
        if (bankAcctRecs == null) {
            bankAcctRecs = new ArrayList<BankAcctRec>();
        }
        return this.bankAcctRecs;
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
     * {@link CardAcctRec }
     * 
     * 
     */
    public List<CardAcctRec> getCardAcctRecs() {
        if (cardAcctRecs == null) {
            cardAcctRecs = new ArrayList<CardAcctRec>();
        }
        return this.cardAcctRecs;
    }

    /**
     * Gets the value of the depoAccounts property.
     * 
     * @return
     *     possible object is
     *     {@link DepoAccountsType }
     *     
     */
    public DepoAccountsType getDepoAccounts() {
        return depoAccounts;
    }

    /**
     * Sets the value of the depoAccounts property.
     * 
     * @param value
     *     allowed object is
     *     {@link DepoAccountsType }
     *     
     */
    public void setDepoAccounts(DepoAccountsType value) {
        this.depoAccounts = value;
    }

    /**
     * Gets the value of the svcsAccts property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the svcsAccts property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSvcsAccts().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link SvcsAcct }
     * 
     * 
     */
    public List<SvcsAcct> getSvcsAccts() {
        if (svcsAccts == null) {
            svcsAccts = new ArrayList<SvcsAcct>();
        }
        return this.svcsAccts;
    }

    /**
     * Gets the value of the loanAcctRecs property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the loanAcctRecs property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getLoanAcctRecs().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link LoanAcctRec }
     * 
     * 
     */
    public List<LoanAcctRec> getLoanAcctRecs() {
        if (loanAcctRecs == null) {
            loanAcctRecs = new ArrayList<LoanAcctRec>();
        }
        return this.loanAcctRecs;
    }

}
