
package com.rssl.phizicgate.mdm.integration.mdm.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for AgreemtAcct_Type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AgreemtAcct_Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}LoanAcctId"/>
 *         &lt;element ref="{}CardAcctId"/>
 *         &lt;element ref="{}DepAcctId"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AgreemtAcct_Type", propOrder = {
    "loanAcctId",
    "cardAcctId",
    "depAcctId"
})
@XmlRootElement(name = "AgreemtAcct")
public class AgreemtAcct {

    @XmlElement(name = "LoanAcctId", required = true)
    protected LoanAcctId loanAcctId;
    @XmlElement(name = "CardAcctId", required = true)
    protected CardAcctId cardAcctId;
    @XmlElement(name = "DepAcctId", required = true)
    protected DepAcctId depAcctId;

    /**
     * Gets the value of the loanAcctId property.
     * 
     * @return
     *     possible object is
     *     {@link LoanAcctId }
     *     
     */
    public LoanAcctId getLoanAcctId() {
        return loanAcctId;
    }

    /**
     * Sets the value of the loanAcctId property.
     * 
     * @param value
     *     allowed object is
     *     {@link LoanAcctId }
     *     
     */
    public void setLoanAcctId(LoanAcctId value) {
        this.loanAcctId = value;
    }

    /**
     * Gets the value of the cardAcctId property.
     * 
     * @return
     *     possible object is
     *     {@link CardAcctId }
     *     
     */
    public CardAcctId getCardAcctId() {
        return cardAcctId;
    }

    /**
     * Sets the value of the cardAcctId property.
     * 
     * @param value
     *     allowed object is
     *     {@link CardAcctId }
     *     
     */
    public void setCardAcctId(CardAcctId value) {
        this.cardAcctId = value;
    }

    /**
     * Gets the value of the depAcctId property.
     * 
     * @return
     *     possible object is
     *     {@link DepAcctId }
     *     
     */
    public DepAcctId getDepAcctId() {
        return depAcctId;
    }

    /**
     * Sets the value of the depAcctId property.
     * 
     * @param value
     *     allowed object is
     *     {@link DepAcctId }
     *     
     */
    public void setDepAcctId(DepAcctId value) {
        this.depAcctId = value;
    }

}
