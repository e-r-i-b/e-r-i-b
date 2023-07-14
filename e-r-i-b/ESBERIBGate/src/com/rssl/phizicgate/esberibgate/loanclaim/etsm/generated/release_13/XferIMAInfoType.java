
package com.rssl.phizicgate.esberibgate.loanclaim.etsm.generated.release_13;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Информация об операции со счетом ОМС
 * 
 * <p>Java class for XferIMAInfo_Type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="XferIMAInfo_Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;choice>
 *           &lt;element ref="{}DepAcctIdFrom"/>
 *           &lt;element ref="{}CardAcctIdFrom"/>
 *           &lt;element name="IMAAcctIdFrom" type="{}IMAAcctId_Type"/>
 *         &lt;/choice>
 *         &lt;choice>
 *           &lt;element ref="{}AgreemtInfo"/>
 *           &lt;element name="IMAAcctIdTo" type="{}IMAAcctId_Type"/>
 *           &lt;element ref="{}CardAcctIdTo"/>
 *         &lt;/choice>
 *         &lt;element name="Purpose" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element ref="{}CurAmt"/>
 *         &lt;element ref="{}AcctCur"/>
 *         &lt;element ref="{}CurAmtConv"/>
 *         &lt;element name="Execute" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "XferIMAInfo_Type", propOrder = {
    "imaAcctIdFrom",
    "cardAcctIdFrom",
    "depAcctIdFrom",
    "cardAcctIdTo",
    "imaAcctIdTo",
    "agreemtInfo",
    "purpose",
    "curAmt",
    "acctCur",
    "curAmtConv",
    "execute"
})
public class XferIMAInfoType {

    @XmlElement(name = "IMAAcctIdFrom")
    protected IMAAcctIdType imaAcctIdFrom;
    @XmlElement(name = "CardAcctIdFrom")
    protected CardAcctIdType cardAcctIdFrom;
    @XmlElement(name = "DepAcctIdFrom")
    protected DepAcctIdType depAcctIdFrom;
    @XmlElement(name = "CardAcctIdTo")
    protected CardAcctIdType cardAcctIdTo;
    @XmlElement(name = "IMAAcctIdTo")
    protected IMAAcctIdType imaAcctIdTo;
    @XmlElement(name = "AgreemtInfo")
    protected AgreemtInfoType agreemtInfo;
    @XmlElement(name = "Purpose")
    protected String purpose;
    @XmlElement(name = "CurAmt", required = true)
    protected BigDecimal curAmt;
    @XmlElement(name = "AcctCur", required = true)
    protected String acctCur;
    @XmlElement(name = "CurAmtConv", required = true)
    protected CurAmtConv curAmtConv;
    @XmlElement(name = "Execute")
    protected boolean execute;

    /**
     * Gets the value of the imaAcctIdFrom property.
     * 
     * @return
     *     possible object is
     *     {@link IMAAcctIdType }
     *     
     */
    public IMAAcctIdType getIMAAcctIdFrom() {
        return imaAcctIdFrom;
    }

    /**
     * Sets the value of the imaAcctIdFrom property.
     * 
     * @param value
     *     allowed object is
     *     {@link IMAAcctIdType }
     *     
     */
    public void setIMAAcctIdFrom(IMAAcctIdType value) {
        this.imaAcctIdFrom = value;
    }

    /**
     * Gets the value of the cardAcctIdFrom property.
     * 
     * @return
     *     possible object is
     *     {@link CardAcctIdType }
     *     
     */
    public CardAcctIdType getCardAcctIdFrom() {
        return cardAcctIdFrom;
    }

    /**
     * Sets the value of the cardAcctIdFrom property.
     * 
     * @param value
     *     allowed object is
     *     {@link CardAcctIdType }
     *     
     */
    public void setCardAcctIdFrom(CardAcctIdType value) {
        this.cardAcctIdFrom = value;
    }

    /**
     * Gets the value of the depAcctIdFrom property.
     * 
     * @return
     *     possible object is
     *     {@link DepAcctIdType }
     *     
     */
    public DepAcctIdType getDepAcctIdFrom() {
        return depAcctIdFrom;
    }

    /**
     * Sets the value of the depAcctIdFrom property.
     * 
     * @param value
     *     allowed object is
     *     {@link DepAcctIdType }
     *     
     */
    public void setDepAcctIdFrom(DepAcctIdType value) {
        this.depAcctIdFrom = value;
    }

    /**
     * Gets the value of the cardAcctIdTo property.
     * 
     * @return
     *     possible object is
     *     {@link CardAcctIdType }
     *     
     */
    public CardAcctIdType getCardAcctIdTo() {
        return cardAcctIdTo;
    }

    /**
     * Sets the value of the cardAcctIdTo property.
     * 
     * @param value
     *     allowed object is
     *     {@link CardAcctIdType }
     *     
     */
    public void setCardAcctIdTo(CardAcctIdType value) {
        this.cardAcctIdTo = value;
    }

    /**
     * Gets the value of the imaAcctIdTo property.
     * 
     * @return
     *     possible object is
     *     {@link IMAAcctIdType }
     *     
     */
    public IMAAcctIdType getIMAAcctIdTo() {
        return imaAcctIdTo;
    }

    /**
     * Sets the value of the imaAcctIdTo property.
     * 
     * @param value
     *     allowed object is
     *     {@link IMAAcctIdType }
     *     
     */
    public void setIMAAcctIdTo(IMAAcctIdType value) {
        this.imaAcctIdTo = value;
    }

    /**
     * Gets the value of the agreemtInfo property.
     * 
     * @return
     *     possible object is
     *     {@link AgreemtInfoType }
     *     
     */
    public AgreemtInfoType getAgreemtInfo() {
        return agreemtInfo;
    }

    /**
     * Sets the value of the agreemtInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link AgreemtInfoType }
     *     
     */
    public void setAgreemtInfo(AgreemtInfoType value) {
        this.agreemtInfo = value;
    }

    /**
     * Gets the value of the purpose property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPurpose() {
        return purpose;
    }

    /**
     * Sets the value of the purpose property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPurpose(String value) {
        this.purpose = value;
    }

    /**
     * Сумма операции (в граммах)
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getCurAmt() {
        return curAmt;
    }

    /**
     * Sets the value of the curAmt property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setCurAmt(BigDecimal value) {
        this.curAmt = value;
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
     * Gets the value of the curAmtConv property.
     * 
     * @return
     *     possible object is
     *     {@link CurAmtConv }
     *     
     */
    public CurAmtConv getCurAmtConv() {
        return curAmtConv;
    }

    /**
     * Sets the value of the curAmtConv property.
     * 
     * @param value
     *     allowed object is
     *     {@link CurAmtConv }
     *     
     */
    public void setCurAmtConv(CurAmtConv value) {
        this.curAmtConv = value;
    }

    /**
     * Gets the value of the execute property.
     * 
     */
    public boolean isExecute() {
        return execute;
    }

    /**
     * Sets the value of the execute property.
     * 
     */
    public void setExecute(boolean value) {
        this.execute = value;
    }

}
