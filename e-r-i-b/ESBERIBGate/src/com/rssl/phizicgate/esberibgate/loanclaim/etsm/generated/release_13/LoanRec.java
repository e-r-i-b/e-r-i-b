
package com.rssl.phizicgate.esberibgate.loanclaim.etsm.generated.release_13;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for LoanRec_Type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="LoanRec_Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}LoanInfo"/>
 *         &lt;element ref="{}BankInfo" minOccurs="0"/>
 *         &lt;element name="BankAccInfo" type="{}BankAcctInfo_Type" minOccurs="0"/>
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
@XmlType(name = "LoanRec_Type", propOrder = {
    "loanInfo",
    "bankInfo",
    "bankAccInfo",
    "status"
})
@XmlRootElement(name = "LoanRec")
public class LoanRec {

    @XmlElement(name = "LoanInfo", required = true)
    protected LoanInfo loanInfo;
    @XmlElement(name = "BankInfo")
    protected BankInfo bankInfo;
    @XmlElement(name = "BankAccInfo")
    protected BankAcctInfo bankAccInfo;
    @XmlElement(name = "Status")
    protected Status status;

    /**
     * Gets the value of the loanInfo property.
     * 
     * @return
     *     possible object is
     *     {@link LoanInfo }
     *     
     */
    public LoanInfo getLoanInfo() {
        return loanInfo;
    }

    /**
     * Sets the value of the loanInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link LoanInfo }
     *     
     */
    public void setLoanInfo(LoanInfo value) {
        this.loanInfo = value;
    }

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
     * Gets the value of the bankAccInfo property.
     * 
     * @return
     *     possible object is
     *     {@link BankAcctInfo }
     *     
     */
    public BankAcctInfo getBankAccInfo() {
        return bankAccInfo;
    }

    /**
     * Sets the value of the bankAccInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link BankAcctInfo }
     *     
     */
    public void setBankAccInfo(BankAcctInfo value) {
        this.bankAccInfo = value;
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
