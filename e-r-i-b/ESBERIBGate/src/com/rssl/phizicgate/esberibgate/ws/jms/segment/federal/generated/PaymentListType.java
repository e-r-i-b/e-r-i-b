
package com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Список задолженностей по АП
 * 
 * <p>Java class for PaymentList_Type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PaymentList_Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="AutoPaymentId" type="{}AutoPaymentId_Type"/>
 *         &lt;element name="PaymentInfo" type="{}PaymentInfo_Type"/>
 *         &lt;element name="RecipientRec" type="{}RecipientRec_Type"/>
 *         &lt;element name="BankAcctRec">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{}BankAcctRec_Type">
 *                 &lt;sequence>
 *                   &lt;element name="CardAcctId">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{}CardAcctId_Type">
 *                           &lt;sequence>
 *                             &lt;element name="SystemId" type="{}SystemId_Type"/>
 *                             &lt;element name="CardNum" type="{}CardNumType"/>
 *                             &lt;element name="AcctId" type="{}AcctIdType" minOccurs="0"/>
 *                             &lt;element name="CardType" type="{}C"/>
 *                             &lt;element name="CardLevel" type="{}CardLevel_Type"/>
 *                             &lt;element name="CardBonusSign" type="{}CardBonusSign_Type" minOccurs="0"/>
 *                             &lt;element name="AcctCur" type="{}AcctCur_Type" minOccurs="0"/>
 *                             &lt;element name="EndDt" type="{}EndDt_Type"/>
 *                             &lt;element name="CustInfo">
 *                               &lt;complexType>
 *                                 &lt;complexContent>
 *                                   &lt;restriction base="{}CustInfo_Type">
 *                                     &lt;sequence>
 *                                       &lt;element name="PersonInfo">
 *                                         &lt;complexType>
 *                                           &lt;complexContent>
 *                                             &lt;restriction base="{}PersonInfo_Type">
 *                                               &lt;sequence>
 *                                                 &lt;element name="Birthday" type="{}Date" minOccurs="0"/>
 *                                                 &lt;element name="PersonName" type="{}PersonName_Type" minOccurs="0"/>
 *                                                 &lt;element name="IdentityCard" minOccurs="0">
 *                                                   &lt;complexType>
 *                                                     &lt;complexContent>
 *                                                       &lt;restriction base="{}IdentityCard_Type">
 *                                                         &lt;sequence>
 *                                                           &lt;element name="IdType" type="{}IdType_Type" minOccurs="0"/>
 *                                                           &lt;element name="IdSeries" type="{}IdSeries_Type" minOccurs="0"/>
 *                                                           &lt;element name="IdNum" type="{}IdNum_Type" minOccurs="0"/>
 *                                                         &lt;/sequence>
 *                                                       &lt;/restriction>
 *                                                     &lt;/complexContent>
 *                                                   &lt;/complexType>
 *                                                 &lt;/element>
 *                                                 &lt;element name="ContactInfo">
 *                                                   &lt;complexType>
 *                                                     &lt;complexContent>
 *                                                       &lt;restriction base="{}ContactInfo_Type">
 *                                                         &lt;sequence>
 *                                                           &lt;element name="PostAddr">
 *                                                             &lt;complexType>
 *                                                               &lt;complexContent>
 *                                                                 &lt;restriction base="{}FullAddress_Type">
 *                                                                   &lt;sequence>
 *                                                                     &lt;element name="AddrType" type="{}AddressType_Type"/>
 *                                                                     &lt;element name="Addr3" type="{}C"/>
 *                                                                   &lt;/sequence>
 *                                                                 &lt;/restriction>
 *                                                               &lt;/complexContent>
 *                                                             &lt;/complexType>
 *                                                           &lt;/element>
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
 *                             &lt;element name="BankInfo">
 *                               &lt;complexType>
 *                                 &lt;complexContent>
 *                                   &lt;restriction base="{}BankInfo_Type">
 *                                     &lt;sequence>
 *                                       &lt;element name="RbBrchId" type="{}RbBrchType"/>
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
 *         &lt;element name="CardAuthorization" type="{}CardAuthorization_Type" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PaymentList_Type", propOrder = {
    "autoPaymentId",
    "paymentInfo",
    "recipientRec",
    "bankAcctRec",
    "cardAuthorization"
})
public class PaymentListType {

    @XmlElement(name = "AutoPaymentId", required = true)
    protected AutoPaymentIdType autoPaymentId;
    @XmlElement(name = "PaymentInfo", required = true)
    protected PaymentInfoType paymentInfo;
    @XmlElement(name = "RecipientRec", required = true)
    protected RecipientRec recipientRec;
    @XmlElement(name = "BankAcctRec", required = true)
    protected PaymentListType.BankAcctRec bankAcctRec;
    @XmlElement(name = "CardAuthorization")
    protected CardAuthorization cardAuthorization;

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
     * Gets the value of the paymentInfo property.
     * 
     * @return
     *     possible object is
     *     {@link PaymentInfoType }
     *     
     */
    public PaymentInfoType getPaymentInfo() {
        return paymentInfo;
    }

    /**
     * Sets the value of the paymentInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link PaymentInfoType }
     *     
     */
    public void setPaymentInfo(PaymentInfoType value) {
        this.paymentInfo = value;
    }

    /**
     * Gets the value of the recipientRec property.
     * 
     * @return
     *     possible object is
     *     {@link RecipientRec }
     *     
     */
    public RecipientRec getRecipientRec() {
        return recipientRec;
    }

    /**
     * Sets the value of the recipientRec property.
     * 
     * @param value
     *     allowed object is
     *     {@link RecipientRec }
     *     
     */
    public void setRecipientRec(RecipientRec value) {
        this.recipientRec = value;
    }

    /**
     * Gets the value of the bankAcctRec property.
     * 
     * @return
     *     possible object is
     *     {@link PaymentListType.BankAcctRec }
     *     
     */
    public PaymentListType.BankAcctRec getBankAcctRec() {
        return bankAcctRec;
    }

    /**
     * Sets the value of the bankAcctRec property.
     * 
     * @param value
     *     allowed object is
     *     {@link PaymentListType.BankAcctRec }
     *     
     */
    public void setBankAcctRec(PaymentListType.BankAcctRec value) {
        this.bankAcctRec = value;
    }

    /**
     * Gets the value of the cardAuthorization property.
     * 
     * @return
     *     possible object is
     *     {@link CardAuthorization }
     *     
     */
    public CardAuthorization getCardAuthorization() {
        return cardAuthorization;
    }

    /**
     * Sets the value of the cardAuthorization property.
     * 
     * @param value
     *     allowed object is
     *     {@link CardAuthorization }
     *     
     */
    public void setCardAuthorization(CardAuthorization value) {
        this.cardAuthorization = value;
    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{}BankAcctRec_Type">
     *       &lt;sequence>
     *         &lt;element name="CardAcctId">
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
     *                   &lt;element name="AcctCur" type="{}AcctCur_Type" minOccurs="0"/>
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
     *                                       &lt;element name="Birthday" type="{}Date" minOccurs="0"/>
     *                                       &lt;element name="PersonName" type="{}PersonName_Type" minOccurs="0"/>
     *                                       &lt;element name="IdentityCard" minOccurs="0">
     *                                         &lt;complexType>
     *                                           &lt;complexContent>
     *                                             &lt;restriction base="{}IdentityCard_Type">
     *                                               &lt;sequence>
     *                                                 &lt;element name="IdType" type="{}IdType_Type" minOccurs="0"/>
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
    @XmlType(name = "")
    public static class BankAcctRec
        extends BankAcctRecType
    {


    }

}
