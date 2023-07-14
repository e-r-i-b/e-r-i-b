
package com.rssl.phizicgate.esberibgate.loanclaim.etsm.generated.release_19;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Выписка по ОМС
 * 
 * <p>Java class for IMSAcctRec_Type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="IMSAcctRec_Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}BankAcctId"/>
 *         &lt;element ref="{}BankAcctFullStmtInfo" minOccurs="0"/>
 *         &lt;element ref="{}BankAcctFullStmtRec" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element ref="{}BankAcctStmtRec" maxOccurs="10" minOccurs="0"/>
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
@XmlType(name = "IMSAcctRec_Type", propOrder = {
    "bankAcctId",
    "bankAcctFullStmtInfo",
    "bankAcctFullStmtRecs",
    "bankAcctStmtRecs",
    "status"
})
@XmlRootElement(name = "IMSAcctRec")
public class IMSAcctRec {

    @XmlElement(name = "BankAcctId", required = true)
    protected BankAcctId bankAcctId;
    @XmlElement(name = "BankAcctFullStmtInfo")
    protected BankAcctFullStmtInfo bankAcctFullStmtInfo;
    @XmlElement(name = "BankAcctFullStmtRec")
    protected List<BankAcctFullStmtRec> bankAcctFullStmtRecs;
    @XmlElement(name = "BankAcctStmtRec")
    protected List<BankAcctStmtRec> bankAcctStmtRecs;
    @XmlElement(name = "Status", required = true)
    protected Status status;

    /**
     * Gets the value of the bankAcctId property.
     * 
     * @return
     *     possible object is
     *     {@link BankAcctId }
     *     
     */
    public BankAcctId getBankAcctId() {
        return bankAcctId;
    }

    /**
     * Sets the value of the bankAcctId property.
     * 
     * @param value
     *     allowed object is
     *     {@link BankAcctId }
     *     
     */
    public void setBankAcctId(BankAcctId value) {
        this.bankAcctId = value;
    }

    /**
     * Gets the value of the bankAcctFullStmtInfo property.
     * 
     * @return
     *     possible object is
     *     {@link BankAcctFullStmtInfo }
     *     
     */
    public BankAcctFullStmtInfo getBankAcctFullStmtInfo() {
        return bankAcctFullStmtInfo;
    }

    /**
     * Sets the value of the bankAcctFullStmtInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link BankAcctFullStmtInfo }
     *     
     */
    public void setBankAcctFullStmtInfo(BankAcctFullStmtInfo value) {
        this.bankAcctFullStmtInfo = value;
    }

    /**
     * Gets the value of the bankAcctFullStmtRecs property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the bankAcctFullStmtRecs property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getBankAcctFullStmtRecs().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link BankAcctFullStmtRec }
     * 
     * 
     */
    public List<BankAcctFullStmtRec> getBankAcctFullStmtRecs() {
        if (bankAcctFullStmtRecs == null) {
            bankAcctFullStmtRecs = new ArrayList<BankAcctFullStmtRec>();
        }
        return this.bankAcctFullStmtRecs;
    }

    /**
     * Gets the value of the bankAcctStmtRecs property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the bankAcctStmtRecs property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getBankAcctStmtRecs().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link BankAcctStmtRec }
     * 
     * 
     */
    public List<BankAcctStmtRec> getBankAcctStmtRecs() {
        if (bankAcctStmtRecs == null) {
            bankAcctStmtRecs = new ArrayList<BankAcctStmtRec>();
        }
        return this.bankAcctStmtRecs;
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
