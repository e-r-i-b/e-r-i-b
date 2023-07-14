
package com.rssl.phizgate.basket.generated;

import java.util.Calendar;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import com.rssl.phizic.utils.jaxb.CalendarDateAdapter;


/**
 * Информация по пластиковой карте
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
 *         &lt;element name="SystemId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="CardNum" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="AcctId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CardType" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="CardLevel" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="CardBonusSign" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="EndDt" type="{http://www.w3.org/2001/XMLSchema}date"/>
 *         &lt;element name="CustInfo" type="{}CustInfo_Type"/>
 *         &lt;element name="BankInfo">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="RbBrchId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
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
    "cardNum",
    "acctId",
    "cardType",
    "cardLevel",
    "cardBonusSign",
    "endDt",
    "custInfo",
    "bankInfo"
})
public class CardAcctIdType {

    @XmlElement(name = "SystemId", required = true)
    protected String systemId;
    @XmlElement(name = "CardNum", required = true)
    protected String cardNum;
    @XmlElement(name = "AcctId")
    protected String acctId;
    @XmlElement(name = "CardType", required = true)
    protected String cardType;
    @XmlElement(name = "CardLevel", required = true)
    protected String cardLevel;
    @XmlElement(name = "CardBonusSign")
    protected String cardBonusSign;
    @XmlElement(name = "EndDt", required = true, type = String.class)
    @XmlJavaTypeAdapter(CalendarDateAdapter.class)
    @XmlSchemaType(name = "date")
    protected Calendar endDt;
    @XmlElement(name = "CustInfo", required = true)
    protected CustInfoType custInfo;
    @XmlElement(name = "BankInfo", required = true)
    protected CardAcctIdType.BankInfo bankInfo;

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
     * Gets the value of the acctId property.
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
     * Gets the value of the cardLevel property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCardLevel() {
        return cardLevel;
    }

    /**
     * Sets the value of the cardLevel property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCardLevel(String value) {
        this.cardLevel = value;
    }

    /**
     * Gets the value of the cardBonusSign property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCardBonusSign() {
        return cardBonusSign;
    }

    /**
     * Sets the value of the cardBonusSign property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCardBonusSign(String value) {
        this.cardBonusSign = value;
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
     * Gets the value of the custInfo property.
     * 
     * @return
     *     possible object is
     *     {@link CustInfoType }
     *     
     */
    public CustInfoType getCustInfo() {
        return custInfo;
    }

    /**
     * Sets the value of the custInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link CustInfoType }
     *     
     */
    public void setCustInfo(CustInfoType value) {
        this.custInfo = value;
    }

    /**
     * Gets the value of the bankInfo property.
     * 
     * @return
     *     possible object is
     *     {@link CardAcctIdType.BankInfo }
     *     
     */
    public CardAcctIdType.BankInfo getBankInfo() {
        return bankInfo;
    }

    /**
     * Sets the value of the bankInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link CardAcctIdType.BankInfo }
     *     
     */
    public void setBankInfo(CardAcctIdType.BankInfo value) {
        this.bankInfo = value;
    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="RbBrchId" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "rbBrchId"
    })
    public static class BankInfo {

        @XmlElement(name = "RbBrchId", required = true)
        protected String rbBrchId;

        /**
         * Gets the value of the rbBrchId property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getRbBrchId() {
            return rbBrchId;
        }

        /**
         * Sets the value of the rbBrchId property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setRbBrchId(String value) {
            this.rbBrchId = value;
        }

    }

}
