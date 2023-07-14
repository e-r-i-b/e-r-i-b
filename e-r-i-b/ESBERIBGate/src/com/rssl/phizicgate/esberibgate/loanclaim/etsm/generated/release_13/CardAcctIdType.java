
package com.rssl.phizicgate.esberibgate.loanclaim.etsm.generated.release_13;

import java.util.Calendar;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import com.rssl.phizic.utils.jaxb.CalendarDateAdapter;


/**
 * Идентификатор карточного счета <CardAcctId> <CardAcctIdFrom> <CardAcctIdTo>
 * 
 * <p>Java class for CardAcctId_Type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CardAcctId_Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}SystemId" minOccurs="0"/>
 *         &lt;element ref="{}SPName" minOccurs="0"/>
 *         &lt;element ref="{}CardNum" minOccurs="0"/>
 *         &lt;element ref="{}AcctId" minOccurs="0"/>
 *         &lt;element name="AcctCode" type="{}Long" minOccurs="0"/>
 *         &lt;element name="AcctSubCode" type="{}Long" minOccurs="0"/>
 *         &lt;element name="CardType" type="{}String" minOccurs="0"/>
 *         &lt;element ref="{}CardName" minOccurs="0"/>
 *         &lt;element ref="{}AcctCur" minOccurs="0"/>
 *         &lt;element ref="{}EndDt" minOccurs="0"/>
 *         &lt;element name="PmtDt" type="{}Date" minOccurs="0"/>
 *         &lt;element name="IssDt" type="{}Date" minOccurs="0"/>
 *         &lt;element name="CardHolder" type="{}String" minOccurs="0"/>
 *         &lt;element name="UNICardType" type="{}String" minOccurs="0"/>
 *         &lt;element name="UNIAcctType" type="{}String" minOccurs="0"/>
 *         &lt;element ref="{}CardAccount" minOccurs="0"/>
 *         &lt;element ref="{}CustInfo" minOccurs="0"/>
 *         &lt;element ref="{}BankInfo" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CardAcctId_Type", propOrder = {
    "systemId",
    "spName",
    "cardNum",
    "acctId",
    "acctCode",
    "acctSubCode",
    "cardType",
    "cardName",
    "acctCur",
    "endDt",
    "pmtDt",
    "issDt",
    "cardHolder",
    "uniCardType",
    "uniAcctType",
    "cardAccount",
    "custInfo",
    "bankInfo"
})
public class CardAcctIdType {

    @XmlElement(name = "SystemId")
    protected String systemId;
    @XmlElement(name = "SPName")
    protected SPNameType spName;
    @XmlElement(name = "CardNum")
    protected String cardNum;
    @XmlElement(name = "AcctId")
    protected String acctId;
    @XmlElement(name = "AcctCode")
    protected Long acctCode;
    @XmlElement(name = "AcctSubCode")
    protected Long acctSubCode;
    @XmlElement(name = "CardType")
    protected String cardType;
    @XmlElement(name = "CardName")
    protected String cardName;
    @XmlElement(name = "AcctCur")
    protected String acctCur;
    @XmlElement(name = "EndDt", type = String.class)
    @XmlJavaTypeAdapter(CalendarDateAdapter.class)
    protected Calendar endDt;
    @XmlElement(name = "PmtDt", type = String.class)
    @XmlJavaTypeAdapter(CalendarDateAdapter.class)
    protected Calendar pmtDt;
    @XmlElement(name = "IssDt", type = String.class)
    @XmlJavaTypeAdapter(CalendarDateAdapter.class)
    protected Calendar issDt;
    @XmlElement(name = "CardHolder")
    protected String cardHolder;
    @XmlElement(name = "UNICardType")
    protected String uniCardType;
    @XmlElement(name = "UNIAcctType")
    protected String uniAcctType;
    @XmlElement(name = "CardAccount")
    protected CardAccount cardAccount;
    @XmlElement(name = "CustInfo")
    protected CustInfo custInfo;
    @XmlElement(name = "BankInfo")
    protected BankInfo bankInfo;

    /**
     * Gets the value of the systemId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSystemId() {
        return systemId;
    }

    /**
     * Sets the value of the systemId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSystemId(String value) {
        this.systemId = value;
    }

    /**
     * Gets the value of the spName property.
     * 
     * @return
     *     possible object is
     *     {@link SPNameType }
     *     
     */
    public SPNameType getSPName() {
        return spName;
    }

    /**
     * Sets the value of the spName property.
     * 
     * @param value
     *     allowed object is
     *     {@link SPNameType }
     *     
     */
    public void setSPName(SPNameType value) {
        this.spName = value;
    }

    /**
     * Gets the value of the cardNum property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCardNum() {
        return cardNum;
    }

    /**
     * Sets the value of the cardNum property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCardNum(String value) {
        this.cardNum = value;
    }

    /**
     * Номер карточного счета для карт (из ЦОД)
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAcctId() {
        return acctId;
    }

    /**
     * Sets the value of the acctId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAcctId(String value) {
        this.acctId = value;
    }

    /**
     * Gets the value of the acctCode property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getAcctCode() {
        return acctCode;
    }

    /**
     * Sets the value of the acctCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setAcctCode(Long value) {
        this.acctCode = value;
    }

    /**
     * Gets the value of the acctSubCode property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getAcctSubCode() {
        return acctSubCode;
    }

    /**
     * Sets the value of the acctSubCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setAcctSubCode(Long value) {
        this.acctSubCode = value;
    }

    /**
     * Gets the value of the cardType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCardType() {
        return cardType;
    }

    /**
     * Sets the value of the cardType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCardType(String value) {
        this.cardType = value;
    }

    /**
     * Gets the value of the cardName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCardName() {
        return cardName;
    }

    /**
     * Sets the value of the cardName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCardName(String value) {
        this.cardName = value;
    }

    /**
     * Gets the value of the acctCur property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAcctCur() {
        return acctCur;
    }

    /**
     * Sets the value of the acctCur property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAcctCur(String value) {
        this.acctCur = value;
    }

    /**
     * Gets the value of the endDt property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public Calendar getEndDt() {
        return endDt;
    }

    /**
     * Sets the value of the endDt property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEndDt(Calendar value) {
        this.endDt = value;
    }

    /**
     * Gets the value of the pmtDt property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public Calendar getPmtDt() {
        return pmtDt;
    }

    /**
     * Sets the value of the pmtDt property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPmtDt(Calendar value) {
        this.pmtDt = value;
    }

    /**
     * Gets the value of the issDt property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public Calendar getIssDt() {
        return issDt;
    }

    /**
     * Sets the value of the issDt property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIssDt(Calendar value) {
        this.issDt = value;
    }

    /**
     * Gets the value of the cardHolder property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCardHolder() {
        return cardHolder;
    }

    /**
     * Sets the value of the cardHolder property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCardHolder(String value) {
        this.cardHolder = value;
    }

    /**
     * Gets the value of the uniCardType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUNICardType() {
        return uniCardType;
    }

    /**
     * Sets the value of the uniCardType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUNICardType(String value) {
        this.uniCardType = value;
    }

    /**
     * Gets the value of the uniAcctType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUNIAcctType() {
        return uniAcctType;
    }

    /**
     * Sets the value of the uniAcctType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUNIAcctType(String value) {
        this.uniAcctType = value;
    }

    /**
     * Номер картсчета
     * 
     * @return
     *     possible object is
     *     {@link CardAccount }
     *     
     */
    public CardAccount getCardAccount() {
        return cardAccount;
    }

    /**
     * Sets the value of the cardAccount property.
     * 
     * @param value
     *     allowed object is
     *     {@link CardAccount }
     *     
     */
    public void setCardAccount(CardAccount value) {
        this.cardAccount = value;
    }

    /**
     * Gets the value of the custInfo property.
     * 
     * @return
     *     possible object is
     *     {@link CustInfo }
     *     
     */
    public CustInfo getCustInfo() {
        return custInfo;
    }

    /**
     * Sets the value of the custInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link CustInfo }
     *     
     */
    public void setCustInfo(CustInfo value) {
        this.custInfo = value;
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

}
