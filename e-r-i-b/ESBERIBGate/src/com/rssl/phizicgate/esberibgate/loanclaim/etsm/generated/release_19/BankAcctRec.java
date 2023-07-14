
package com.rssl.phizicgate.esberibgate.loanclaim.etsm.generated.release_19;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Список депозитов,ОМС, кредитов, банковских карт, длительных поручений, услуг мобильный банк
 * 
 * <p>Java class for BankAcctRec_Type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="BankAcctRec_Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}BankInfo" minOccurs="0"/>
 *         &lt;element ref="{}BankAcctId" minOccurs="0"/>
 *         &lt;element ref="{}ImsAcctInfo" minOccurs="0"/>
 *         &lt;element ref="{}CardAcctId" minOccurs="0"/>
 *         &lt;element ref="{}AcctBal" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BankAcctRec_Type", propOrder = {
    "bankInfo",
    "bankAcctId",
    "imsAcctInfo",
    "cardAcctId",
    "acctBals"
})
@XmlRootElement(name = "BankAcctRec")
public class BankAcctRec {

    @XmlElement(name = "BankInfo")
    protected BankInfo bankInfo;
    @XmlElement(name = "BankAcctId")
    protected BankAcctId bankAcctId;
    @XmlElement(name = "ImsAcctInfo")
    protected ImsAcctInfo imsAcctInfo;
    @XmlElement(name = "CardAcctId")
    protected CardAcctIdType cardAcctId;
    @XmlElement(name = "AcctBal")
    protected List<AcctBal> acctBals;

    /**
     * Доп.информация о банке используется для DepAcctId и BankAcctId и CardAcctId
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
     * Gets the value of the imsAcctInfo property.
     * 
     * @return
     *     possible object is
     *     {@link ImsAcctInfo }
     *     
     */
    public ImsAcctInfo getImsAcctInfo() {
        return imsAcctInfo;
    }

    /**
     * Sets the value of the imsAcctInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link ImsAcctInfo }
     *     
     */
    public void setImsAcctInfo(ImsAcctInfo value) {
        this.imsAcctInfo = value;
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

}
