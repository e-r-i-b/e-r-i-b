
package com.rssl.phizicgate.esberibgate.loanclaim.etsm.generated.release_19;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DepAcctRec_Type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DepAcctRec_Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}BankInfo" minOccurs="0"/>
 *         &lt;element ref="{}DepAcctId"/>
 *         &lt;element ref="{}DepAccInfo" minOccurs="0"/>
 *         &lt;element ref="{}BankAcctPermiss" maxOccurs="2" minOccurs="0"/>
 *         &lt;element ref="{}AcctBal" maxOccurs="3" minOccurs="0"/>
 *         &lt;element ref="{}Status" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DepAcctRec_Type", propOrder = {
    "bankInfo",
    "depAcctId",
    "depAccInfo",
    "bankAcctPermisses",
    "acctBals",
    "status"
})
@XmlRootElement(name = "DepAcctRec")
public class DepAcctRec {

    @XmlElement(name = "BankInfo")
    protected BankInfo bankInfo;
    @XmlElement(name = "DepAcctId", required = true)
    protected DepAcctIdType depAcctId;
    @XmlElement(name = "DepAccInfo")
    protected DepAccInfo depAccInfo;
    @XmlElement(name = "BankAcctPermiss")
    protected List<BankAcctPermiss> bankAcctPermisses;
    @XmlElement(name = "AcctBal")
    protected List<AcctBal> acctBals;
    @XmlElement(name = "Status")
    protected Status status;

    /**
     * Gets the value of the bankInfo property.
     * 
     * @return
     *     possible object is
     *     {@link BankInfo }
     *     
     */
    public BankInfo getBankInfo() {
        return bankInfo;
    }

    /**
     * Sets the value of the bankInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link BankInfo }
     *     
     */
    public void setBankInfo(BankInfo value) {
        this.bankInfo = value;
    }

    /**
     * Gets the value of the depAcctId property.
     * 
     * @return
     *     possible object is
     *     {@link DepAcctIdType }
     *     
     */
    public DepAcctIdType getDepAcctId() {
        return depAcctId;
    }

    /**
     * Sets the value of the depAcctId property.
     * 
     * @param value
     *     allowed object is
     *     {@link DepAcctIdType }
     *     
     */
    public void setDepAcctId(DepAcctIdType value) {
        this.depAcctId = value;
    }

    /**
     * Gets the value of the depAccInfo property.
     * 
     * @return
     *     possible object is
     *     {@link DepAccInfo }
     *     
     */
    public DepAccInfo getDepAccInfo() {
        return depAccInfo;
    }

    /**
     * Sets the value of the depAccInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link DepAccInfo }
     *     
     */
    public void setDepAccInfo(DepAccInfo value) {
        this.depAccInfo = value;
    }

    /**
     * Gets the value of the bankAcctPermisses property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the bankAcctPermisses property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getBankAcctPermisses().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link BankAcctPermiss }
     * 
     * 
     */
    public List<BankAcctPermiss> getBankAcctPermisses() {
        if (bankAcctPermisses == null) {
            bankAcctPermisses = new ArrayList<BankAcctPermiss>();
        }
        return this.bankAcctPermisses;
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
