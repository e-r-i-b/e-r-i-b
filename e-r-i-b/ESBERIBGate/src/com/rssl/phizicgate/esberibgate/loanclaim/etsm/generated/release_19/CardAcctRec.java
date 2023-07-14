
package com.rssl.phizicgate.esberibgate.loanclaim.etsm.generated.release_19;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Информация по счету
 * 
 * <p>Java class for CardAcctRec_Type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CardAcctRec_Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}BankInfo" minOccurs="0"/>
 *         &lt;element ref="{}BankAcctStatus" minOccurs="0"/>
 *         &lt;element ref="{}CardAcctId" minOccurs="0"/>
 *         &lt;element ref="{}CardAcctInfo" minOccurs="0"/>
 *         &lt;element ref="{}AcctBal" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element ref="{}Status" minOccurs="0"/>
 *         &lt;element ref="{}OTPRestriction" minOccurs="0"/>
 *         &lt;element name="UsageInfo" type="{}UsageInfo_Type" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CardAcctRec_Type", propOrder = {
    "bankInfo",
    "bankAcctStatus",
    "cardAcctId",
    "cardAcctInfo",
    "acctBals",
    "status",
    "otpRestriction",
    "usageInfos"
})
@XmlRootElement(name = "CardAcctRec")
public class CardAcctRec {

    @XmlElement(name = "BankInfo")
    protected BankInfo bankInfo;
    @XmlElement(name = "BankAcctStatus")
    protected BankAcctStatus bankAcctStatus;
    @XmlElement(name = "CardAcctId")
    protected CardAcctIdType cardAcctId;
    @XmlElement(name = "CardAcctInfo")
    protected CardAcctInfo cardAcctInfo;
    @XmlElement(name = "AcctBal")
    protected List<AcctBal> acctBals;
    @XmlElement(name = "Status")
    protected Status status;
    @XmlElement(name = "OTPRestriction")
    protected OTPRestriction otpRestriction;
    @XmlElement(name = "UsageInfo")
    protected List<UsageInfoType> usageInfos;

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
     * Gets the value of the bankAcctStatus property.
     * 
     * @return
     *     possible object is
     *     {@link BankAcctStatus }
     *     
     */
    public BankAcctStatus getBankAcctStatus() {
        return bankAcctStatus;
    }

    /**
     * Sets the value of the bankAcctStatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link BankAcctStatus }
     *     
     */
    public void setBankAcctStatus(BankAcctStatus value) {
        this.bankAcctStatus = value;
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
     * Gets the value of the cardAcctInfo property.
     * 
     * @return
     *     possible object is
     *     {@link CardAcctInfo }
     *     
     */
    public CardAcctInfo getCardAcctInfo() {
        return cardAcctInfo;
    }

    /**
     * Sets the value of the cardAcctInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link CardAcctInfo }
     *     
     */
    public void setCardAcctInfo(CardAcctInfo value) {
        this.cardAcctInfo = value;
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
     * Статус обработки запроса для данной карты (возвращается при групповых операциях по картам, которые не поддерживаются бек-офисными системами)
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
     * Gets the value of the otpRestriction property.
     * 
     * @return
     *     possible object is
     *     {@link OTPRestriction }
     *     
     */
    public OTPRestriction getOTPRestriction() {
        return otpRestriction;
    }

    /**
     * Sets the value of the otpRestriction property.
     * 
     * @param value
     *     allowed object is
     *     {@link OTPRestriction }
     *     
     */
    public void setOTPRestriction(OTPRestriction value) {
        this.otpRestriction = value;
    }

    /**
     * Gets the value of the usageInfos property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the usageInfos property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getUsageInfos().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link UsageInfoType }
     * 
     * 
     */
    public List<UsageInfoType> getUsageInfos() {
        if (usageInfos == null) {
            usageInfos = new ArrayList<UsageInfoType>();
        }
        return this.usageInfos;
    }

}
