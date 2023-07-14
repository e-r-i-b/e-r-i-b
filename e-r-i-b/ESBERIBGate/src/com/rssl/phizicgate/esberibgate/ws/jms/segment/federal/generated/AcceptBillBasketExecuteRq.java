
package com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Тип сообщения- запроса на оповещение об изменении статуса подписки
 * 
 * <p>Java class for AcceptBillBasketExecuteRq_Type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AcceptBillBasketExecuteRq_Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="RqUID" type="{}RqUID_Type"/>
 *         &lt;element name="RqTm" type="{}DateTime"/>
 *         &lt;element name="OperUID" type="{}OperUID_Type"/>
 *         &lt;element name="SPName" type="{}SPName_Type"/>
 *         &lt;element name="SystemId" type="{}SystemId_Type" minOccurs="0"/>
 *         &lt;element name="BankInfo">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{}BankInfo_Type">
 *                 &lt;sequence>
 *                   &lt;element name="RbTbBrchId" type="{}RbTbBrchType"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="AutoSubscriptionID" type="{}AutoSubscriptionId_Type"/>
 *         &lt;element name="AutoPaymentId" type="{}AutoPaymentId_Type"/>
 *         &lt;element name="CardAcctId" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{}CardAcctId_Type">
 *                 &lt;sequence>
 *                   &lt;element name="SystemId" type="{}SystemId_Type"/>
 *                   &lt;element name="CardNum" type="{}CardNumType"/>
 *                   &lt;element name="AcctId" type="{}AcctIdType" minOccurs="0"/>
 *                   &lt;element name="CardType" type="{}C"/>
 *                   &lt;element name="CardLevel" type="{}CardLevel_Type"/>
 *                   &lt;element name="CardBonusSign" type="{}CardBonusSign_Type" minOccurs="0"/>
 *                   &lt;element name="AcctCur" type="{}AcctCur_Type"/>
 *                   &lt;element name="EndDt" type="{}EndDt_Type"/>
 *                   &lt;element name="CustInfo">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{}CustInfo_Type">
 *                           &lt;sequence>
 *                             &lt;element name="PersonInfo">
 *                               &lt;complexType>
 *                                 &lt;complexContent>
 *                                   &lt;restriction base="{}PersonInfo_Type">
 *                                     &lt;sequence>
 *                                       &lt;element name="Birthday" type="{}Date"/>
 *                                       &lt;element name="PersonName" type="{}PersonName_Type"/>
 *                                       &lt;element name="IdentityCard">
 *                                         &lt;complexType>
 *                                           &lt;complexContent>
 *                                             &lt;restriction base="{}IdentityCard_Type">
 *                                               &lt;sequence>
 *                                                 &lt;element name="IdType" type="{}IdType_Type"/>
 *                                                 &lt;element name="IdSeries" type="{}IdSeries_Type" minOccurs="0"/>
 *                                                 &lt;element name="IdNum" type="{}IdNum_Type" minOccurs="0"/>
 *                                               &lt;/sequence>
 *                                             &lt;/restriction>
 *                                           &lt;/complexContent>
 *                                         &lt;/complexType>
 *                                       &lt;/element>
 *                                       &lt;element name="ContactInfo">
 *                                         &lt;complexType>
 *                                           &lt;complexContent>
 *                                             &lt;restriction base="{}ContactInfo_Type">
 *                                               &lt;sequence>
 *                                                 &lt;element name="PostAddr">
 *                                                   &lt;complexType>
 *                                                     &lt;complexContent>
 *                                                       &lt;restriction base="{}FullAddress_Type">
 *                                                         &lt;sequence>
 *                                                           &lt;element name="AddrType" type="{}AddressType_Type"/>
 *                                                           &lt;element name="Addr3" type="{}C"/>
 *                                                         &lt;/sequence>
 *                                                       &lt;/restriction>
 *                                                     &lt;/complexContent>
 *                                                   &lt;/complexType>
 *                                                 &lt;/element>
 *                                               &lt;/sequence>
 *                                             &lt;/restriction>
 *                                           &lt;/complexContent>
 *                                         &lt;/complexType>
 *                                       &lt;/element>
 *                                     &lt;/sequence>
 *                                   &lt;/restriction>
 *                                 &lt;/complexContent>
 *                               &lt;/complexType>
 *                             &lt;/element>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                   &lt;element name="BankInfo">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{}BankInfo_Type">
 *                           &lt;sequence>
 *                             &lt;element name="RbBrchId" type="{}RbBrchType"/>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
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
@XmlType(name = "AcceptBillBasketExecuteRq_Type", propOrder = {
    "rqUID",
    "rqTm",
    "operUID",
    "spName",
    "systemId",
    "bankInfo",
    "autoSubscriptionID",
    "autoPaymentId",
    "cardAcctId"
})
@XmlRootElement(name = "AcceptBillBasketExecuteRq")
public class AcceptBillBasketExecuteRq {

    @XmlElement(name = "RqUID", required = true)
    protected String rqUID;
    @XmlElement(name = "RqTm", required = true)
    protected String rqTm;
    @XmlElement(name = "OperUID", required = true)
    protected String operUID;
    @XmlElement(name = "SPName", required = true)
    protected SPNameType spName;
    @XmlElement(name = "SystemId")
    protected String systemId;
    @XmlElement(name = "BankInfo", required = true)
    protected AcceptBillBasketExecuteRq.BankInfo bankInfo;
    @XmlElement(name = "AutoSubscriptionID", required = true)
    protected AutoSubscriptionIdType autoSubscriptionID;
    @XmlElement(name = "AutoPaymentId", required = true)
    protected AutoPaymentIdType autoPaymentId;
    @XmlElement(name = "CardAcctId")
    protected AcceptBillBasketExecuteRq.CardAcctId cardAcctId;

    /**
     * Gets the value of the rqUID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRqUID() {
        return rqUID;
    }

    /**
     * Sets the value of the rqUID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRqUID(String value) {
        this.rqUID = value;
    }

    /**
     * Gets the value of the rqTm property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRqTm() {
        return rqTm;
    }

    /**
     * Sets the value of the rqTm property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRqTm(String value) {
        this.rqTm = value;
    }

    /**
     * Gets the value of the operUID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOperUID() {
        return operUID;
    }

    /**
     * Sets the value of the operUID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOperUID(String value) {
        this.operUID = value;
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
     * Gets the value of the bankInfo property.
     * 
     * @return
     *     possible object is
     *     {@link AcceptBillBasketExecuteRq.BankInfo }
     *     
     */
    public AcceptBillBasketExecuteRq.BankInfo getBankInfo() {
        return bankInfo;
    }

    /**
     * Sets the value of the bankInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link AcceptBillBasketExecuteRq.BankInfo }
     *     
     */
    public void setBankInfo(AcceptBillBasketExecuteRq.BankInfo value) {
        this.bankInfo = value;
    }

    /**
     * Gets the value of the autoSubscriptionID property.
     * 
     * @return
     *     possible object is
     *     {@link AutoSubscriptionIdType }
     *     
     */
    public AutoSubscriptionIdType getAutoSubscriptionID() {
        return autoSubscriptionID;
    }

    /**
     * Sets the value of the autoSubscriptionID property.
     * 
     * @param value
     *     allowed object is
     *     {@link AutoSubscriptionIdType }
     *     
     */
    public void setAutoSubscriptionID(AutoSubscriptionIdType value) {
        this.autoSubscriptionID = value;
    }

    /**
     * Gets the value of the autoPaymentId property.
     * 
     * @return
     *     possible object is
     *     {@link AutoPaymentIdType }
     *     
     */
    public AutoPaymentIdType getAutoPaymentId() {
        return autoPaymentId;
    }

    /**
     * Sets the value of the autoPaymentId property.
     * 
     * @param value
     *     allowed object is
     *     {@link AutoPaymentIdType }
     *     
     */
    public void setAutoPaymentId(AutoPaymentIdType value) {
        this.autoPaymentId = value;
    }

    /**
     * Gets the value of the cardAcctId property.
     * 
     * @return
     *     possible object is
     *     {@link AcceptBillBasketExecuteRq.CardAcctId }
     *     
     */
    public AcceptBillBasketExecuteRq.CardAcctId getCardAcctId() {
        return cardAcctId;
    }

    /**
     * Sets the value of the cardAcctId property.
     * 
     * @param value
     *     allowed object is
     *     {@link AcceptBillBasketExecuteRq.CardAcctId }
     *     
     */
    public void setCardAcctId(AcceptBillBasketExecuteRq.CardAcctId value) {
        this.cardAcctId = value;
    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{}BankInfo_Type">
     *       &lt;sequence>
     *         &lt;element name="RbTbBrchId" type="{}RbTbBrchType"/>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "")
    public static class BankInfo
        extends BankInfoType
    {


    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{}CardAcctId_Type">
     *       &lt;sequence>
     *         &lt;element name="SystemId" type="{}SystemId_Type"/>
     *         &lt;element name="CardNum" type="{}CardNumType"/>
     *         &lt;element name="AcctId" type="{}AcctIdType" minOccurs="0"/>
     *         &lt;element name="CardType" type="{}C"/>
     *         &lt;element name="CardLevel" type="{}CardLevel_Type"/>
     *         &lt;element name="CardBonusSign" type="{}CardBonusSign_Type" minOccurs="0"/>
     *         &lt;element name="AcctCur" type="{}AcctCur_Type"/>
     *         &lt;element name="EndDt" type="{}EndDt_Type"/>
     *         &lt;element name="CustInfo">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{}CustInfo_Type">
     *                 &lt;sequence>
     *                   &lt;element name="PersonInfo">
     *                     &lt;complexType>
     *                       &lt;complexContent>
     *                         &lt;restriction base="{}PersonInfo_Type">
     *                           &lt;sequence>
     *                             &lt;element name="Birthday" type="{}Date"/>
     *                             &lt;element name="PersonName" type="{}PersonName_Type"/>
     *                             &lt;element name="IdentityCard">
     *                               &lt;complexType>
     *                                 &lt;complexContent>
     *                                   &lt;restriction base="{}IdentityCard_Type">
     *                                     &lt;sequence>
     *                                       &lt;element name="IdType" type="{}IdType_Type"/>
     *                                       &lt;element name="IdSeries" type="{}IdSeries_Type" minOccurs="0"/>
     *                                       &lt;element name="IdNum" type="{}IdNum_Type" minOccurs="0"/>
     *                                     &lt;/sequence>
     *                                   &lt;/restriction>
     *                                 &lt;/complexContent>
     *                               &lt;/complexType>
     *                             &lt;/element>
     *                             &lt;element name="ContactInfo">
     *                               &lt;complexType>
     *                                 &lt;complexContent>
     *                                   &lt;restriction base="{}ContactInfo_Type">
     *                                     &lt;sequence>
     *                                       &lt;element name="PostAddr">
     *                                         &lt;complexType>
     *                                           &lt;complexContent>
     *                                             &lt;restriction base="{}FullAddress_Type">
     *                                               &lt;sequence>
     *                                                 &lt;element name="AddrType" type="{}AddressType_Type"/>
     *                                                 &lt;element name="Addr3" type="{}C"/>
     *                                               &lt;/sequence>
     *                                             &lt;/restriction>
     *                                           &lt;/complexContent>
     *                                         &lt;/complexType>
     *                                       &lt;/element>
     *                                     &lt;/sequence>
     *                                   &lt;/restriction>
     *                                 &lt;/complexContent>
     *                               &lt;/complexType>
     *                             &lt;/element>
     *                           &lt;/sequence>
     *                         &lt;/restriction>
     *                       &lt;/complexContent>
     *                     &lt;/complexType>
     *                   &lt;/element>
     *                 &lt;/sequence>
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *         &lt;element name="BankInfo">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{}BankInfo_Type">
     *                 &lt;sequence>
     *                   &lt;element name="RbBrchId" type="{}RbBrchType"/>
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
    @XmlType(name = "")
    public static class CardAcctId
        extends CardAcctIdType
    {


    }

}
