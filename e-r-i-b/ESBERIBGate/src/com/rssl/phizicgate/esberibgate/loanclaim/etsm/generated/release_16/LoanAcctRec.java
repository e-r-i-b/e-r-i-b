
package com.rssl.phizicgate.esberibgate.loanclaim.etsm.generated.release_16;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Список кредитов
 * 
 * <p>Java class for LoanAcctRec_Type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="LoanAcctRec_Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}LoanAcctId"/>
 *         &lt;element ref="{}BankInfo"/>
 *         &lt;element ref="{}BankAcctInfo"/>
 *         &lt;element ref="{}CustRec"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "LoanAcctRec_Type", propOrder = {
    "loanAcctId",
    "bankInfo",
    "bankAcctInfo",
    "custRec"
})
@XmlRootElement(name = "LoanAcctRec")
public class LoanAcctRec {

    @XmlElement(name = "LoanAcctId", required = true)
    protected LoanAcctIdType loanAcctId;
    @XmlElement(name = "BankInfo", required = true)
    protected BankInfo bankInfo;
    @XmlElement(name = "BankAcctInfo", required = true)
    protected BankAcctInfo bankAcctInfo;
    @XmlElement(name = "CustRec", required = true)
    protected CustRec custRec;

    /**
     * Gets the value of the loanAcctId property.
     * 
     * @return
     *     possible object is
     *     {@link LoanAcctIdType }
     *     
     */
    public LoanAcctIdType getLoanAcctId() {
        return loanAcctId;
    }

    /**
     * Sets the value of the loanAcctId property.
     * 
     * @param value
     *     allowed object is
     *     {@link LoanAcctIdType }
     *     
     */
    public void setLoanAcctId(LoanAcctIdType value) {
        this.loanAcctId = value;
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
     * Gets the value of the bankAcctInfo property.
     * 
     * @return
     *     possible object is
     *     {@link BankAcctInfo }
     *     
     */
    public BankAcctInfo getBankAcctInfo() {
        return bankAcctInfo;
    }

    /**
     * Sets the value of the bankAcctInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link BankAcctInfo }
     *     
     */
    public void setBankAcctInfo(BankAcctInfo value) {
        this.bankAcctInfo = value;
    }

    /**
     * Gets the value of the custRec property.
     * 
     * @return
     *     possible object is
     *     {@link CustRec }
     *     
     */
    public CustRec getCustRec() {
        return custRec;
    }

    /**
     * Sets the value of the custRec property.
     * 
     * @param value
     *     allowed object is
     *     {@link CustRec }
     *     
     */
    public void setCustRec(CustRec value) {
        this.custRec = value;
    }

}
