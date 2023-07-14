
package com.rssl.phizicgate.esberibgate.loanclaim.etsm.generated.release_19;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for BankAcctRes_Type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="BankAcctRes_Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}BankAcctId"/>
 *         &lt;element ref="{}AcctBal"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BankAcctRes_Type", propOrder = {
    "bankAcctId",
    "acctBal"
})
@XmlRootElement(name = "BankAcctRes")
public class BankAcctRes {

    @XmlElement(name = "BankAcctId", required = true)
    protected BankAcctId bankAcctId;
    @XmlElement(name = "AcctBal", required = true)
    protected AcctBal acctBal;

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
     * Gets the value of the acctBal property.
     * 
     * @return
     *     possible object is
     *     {@link AcctBal }
     *     
     */
    public AcctBal getAcctBal() {
        return acctBal;
    }

    /**
     * Sets the value of the acctBal property.
     * 
     * @param value
     *     allowed object is
     *     {@link AcctBal }
     *     
     */
    public void setAcctBal(AcctBal value) {
        this.acctBal = value;
    }

}
