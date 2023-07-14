
package com.rssl.phizicgate.esberibgate.loanclaim.etsm.generated.release_13;

import java.math.BigDecimal;
import java.util.Calendar;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import com.rssl.phizic.utils.jaxb.CalendarDateAdapter;
import com.rssl.phizic.utils.jaxb.CalendarDateTimeAdapter;


/**
 * <p>Java class for XferInfo_Type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="XferInfo_Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="XferMethod" type="{}String" minOccurs="0"/>
 *         &lt;element name="ClientDocumentNumber" type="{}Long" minOccurs="0"/>
 *         &lt;element name="ClientDocumentDate" type="{}Date" minOccurs="0"/>
 *         &lt;element name="PayerInfo" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="PersonInfo" type="{}PersonInfo_Type"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="TaxIdTo" type="{}TaxId_Type" minOccurs="0"/>
 *         &lt;element name="KPPTo" type="{}String" minOccurs="0"/>
 *         &lt;element name="TaxIdFrom" type="{}TaxId_Type" minOccurs="0"/>
 *         &lt;element name="RecipientName" type="{}String" minOccurs="0"/>
 *         &lt;element ref="{}CustInfo" minOccurs="0"/>
 *         &lt;element ref="{}TaxColl" minOccurs="0"/>
 *         &lt;sequence minOccurs="0">
 *           &lt;element ref="{}CardAcctIdFrom" minOccurs="0"/>
 *           &lt;element ref="{}DepAcctIdFrom" minOccurs="0"/>
 *           &lt;element ref="{}DepAcctIdTo" minOccurs="0"/>
 *           &lt;element ref="{}CardAcctIdTo" minOccurs="0"/>
 *           &lt;element ref="{}LoanAcctIdTo" minOccurs="0"/>
 *           &lt;element ref="{}AgreemtInfo" minOccurs="0"/>
 *         &lt;/sequence>
 *         &lt;element name="IdSpacing" type="{}String" minOccurs="0"/>
 *         &lt;element name="Purpose" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="NonResCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element ref="{}CurAmt" minOccurs="0"/>
 *         &lt;element ref="{}AcctCur" minOccurs="0"/>
 *         &lt;element ref="{}CardAcctId" minOccurs="0"/>
 *         &lt;element ref="{}DepAcctId" minOccurs="0"/>
 *         &lt;element name="CurAmt1" type="{}Decimal" minOccurs="0"/>
 *         &lt;element name="AcctCur1" type="{}AcctCur_Type" minOccurs="0"/>
 *         &lt;element name="CurAmt2" type="{}Decimal" minOccurs="0"/>
 *         &lt;element name="AcctCur2" type="{}AcctCur_Type" minOccurs="0"/>
 *         &lt;element name="DateCalc" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *         &lt;element ref="{}CurAmtConv" minOccurs="0"/>
 *         &lt;element ref="{}BankInfo" minOccurs="0"/>
 *         &lt;element name="ParentAuthorizationCode" type="{}Long" minOccurs="0"/>
 *         &lt;element name="ParentTransDtTm" type="{}DateTime" minOccurs="0"/>
 *         &lt;element name="WithClose" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="Execute" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="Comission" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="Requisites" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="DataSpec" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="UniqueNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="SpecClientCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CurMonth" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="CommissionLabel" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ServiceCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ServiceKind" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Tarif" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CanChangeTarif" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "XferInfo_Type", propOrder = {
    "xferMethod",
    "clientDocumentNumber",
    "clientDocumentDate",
    "payerInfo",
    "taxIdTo",
    "kppTo",
    "taxIdFrom",
    "recipientName",
    "custInfo",
    "taxColl",
    "cardAcctIdFrom",
    "depAcctIdFrom",
    "depAcctIdTo",
    "cardAcctIdTo",
    "loanAcctIdTo",
    "agreemtInfo",
    "idSpacing",
    "purpose",
    "nonResCode",
    "curAmt",
    "acctCur",
    "cardAcctId",
    "depAcctId",
    "curAmt1",
    "acctCur1",
    "curAmt2",
    "acctCur2",
    "dateCalc",
    "curAmtConv",
    "bankInfo",
    "parentAuthorizationCode",
    "parentTransDtTm",
    "withClose",
    "execute",
    "comission",
    "requisites",
    "dataSpec",
    "uniqueNumber",
    "specClientCode",
    "curMonth",
    "commissionLabel",
    "serviceCode",
    "serviceKind",
    "tarif",
    "canChangeTarif"
})
@XmlRootElement(name = "XferInfo")
public class XferInfo {

    @XmlElement(name = "XferMethod")
    protected String xferMethod;
    @XmlElement(name = "ClientDocumentNumber")
    protected Long clientDocumentNumber;
    @XmlElement(name = "ClientDocumentDate", type = String.class)
    @XmlJavaTypeAdapter(CalendarDateAdapter.class)
    protected Calendar clientDocumentDate;
    @XmlElement(name = "PayerInfo")
    protected XferInfo.PayerInfo payerInfo;
    @XmlElement(name = "TaxIdTo")
    protected String taxIdTo;
    @XmlElement(name = "KPPTo")
    protected String kppTo;
    @XmlElement(name = "TaxIdFrom")
    protected String taxIdFrom;
    @XmlElement(name = "RecipientName")
    protected String recipientName;
    @XmlElement(name = "CustInfo")
    protected CustInfo custInfo;
    @XmlElement(name = "TaxColl")
    protected TaxColl taxColl;
    @XmlElement(name = "CardAcctIdFrom")
    protected CardAcctIdType cardAcctIdFrom;
    @XmlElement(name = "DepAcctIdFrom")
    protected DepAcctIdType depAcctIdFrom;
    @XmlElement(name = "DepAcctIdTo")
    protected DepAcctIdType depAcctIdTo;
    @XmlElement(name = "CardAcctIdTo")
    protected CardAcctIdType cardAcctIdTo;
    @XmlElement(name = "LoanAcctIdTo")
    protected LoanAcctIdType loanAcctIdTo;
    @XmlElement(name = "AgreemtInfo")
    protected AgreemtInfoType agreemtInfo;
    @XmlElement(name = "IdSpacing")
    protected String idSpacing;
    @XmlElement(name = "Purpose")
    protected String purpose;
    @XmlElement(name = "NonResCode")
    protected String nonResCode;
    @XmlElement(name = "CurAmt")
    protected BigDecimal curAmt;
    @XmlElement(name = "AcctCur")
    protected String acctCur;
    @XmlElement(name = "CardAcctId")
    protected CardAcctIdType cardAcctId;
    @XmlElement(name = "DepAcctId")
    protected DepAcctIdType depAcctId;
    @XmlElement(name = "CurAmt1")
    protected BigDecimal curAmt1;
    @XmlElement(name = "AcctCur1")
    protected String acctCur1;
    @XmlElement(name = "CurAmt2")
    protected BigDecimal curAmt2;
    @XmlElement(name = "AcctCur2")
    protected String acctCur2;
    @XmlElement(name = "DateCalc", type = String.class)
    @XmlJavaTypeAdapter(CalendarDateAdapter.class)
    @XmlSchemaType(name = "date")
    protected Calendar dateCalc;
    @XmlElement(name = "CurAmtConv")
    protected CurAmtConv curAmtConv;
    @XmlElement(name = "BankInfo")
    protected BankInfo bankInfo;
    @XmlElement(name = "ParentAuthorizationCode")
    protected Long parentAuthorizationCode;
    @XmlElement(name = "ParentTransDtTm", type = String.class)
    @XmlJavaTypeAdapter(CalendarDateTimeAdapter.class)
    protected Calendar parentTransDtTm;
    @XmlElement(name = "WithClose")
    protected Boolean withClose;
    @XmlElement(name = "Execute")
    protected Boolean execute;
    @XmlElement(name = "Comission")
    protected BigDecimal comission;
    @XmlElement(name = "Requisites")
    protected String requisites;
    @XmlElement(name = "DataSpec")
    protected String dataSpec;
    @XmlElement(name = "UniqueNumber")
    protected String uniqueNumber;
    @XmlElement(name = "SpecClientCode")
    protected String specClientCode;
    @XmlElement(name = "CurMonth")
    protected Boolean curMonth;
    @XmlElement(name = "CommissionLabel")
    protected String commissionLabel;
    @XmlElement(name = "ServiceCode")
    protected String serviceCode;
    @XmlElement(name = "ServiceKind")
    protected String serviceKind;
    @XmlElement(name = "Tarif")
    protected String tarif;
    @XmlElement(name = "CanChangeTarif")
    protected String canChangeTarif;

    /**
     * Gets the value of the xferMethod property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getXferMethod() {
        return xferMethod;
    }

    /**
     * Sets the value of the xferMethod property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setXferMethod(String value) {
        this.xferMethod = value;
    }

    /**
     * Gets the value of the clientDocumentNumber property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getClientDocumentNumber() {
        return clientDocumentNumber;
    }

    /**
     * Sets the value of the clientDocumentNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setClientDocumentNumber(Long value) {
        this.clientDocumentNumber = value;
    }

    /**
     * Gets the value of the clientDocumentDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public Calendar getClientDocumentDate() {
        return clientDocumentDate;
    }

    /**
     * Sets the value of the clientDocumentDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setClientDocumentDate(Calendar value) {
        this.clientDocumentDate = value;
    }

    /**
     * Gets the value of the payerInfo property.
     * 
     * @return
     *     possible object is
     *     {@link XferInfo.PayerInfo }
     *     
     */
    public XferInfo.PayerInfo getPayerInfo() {
        return payerInfo;
    }

    /**
     * Sets the value of the payerInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link XferInfo.PayerInfo }
     *     
     */
    public void setPayerInfo(XferInfo.PayerInfo value) {
        this.payerInfo = value;
    }

    /**
     * Gets the value of the taxIdTo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTaxIdTo() {
        return taxIdTo;
    }

    /**
     * Sets the value of the taxIdTo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTaxIdTo(String value) {
        this.taxIdTo = value;
    }

    /**
     * Gets the value of the kppTo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKPPTo() {
        return kppTo;
    }

    /**
     * Sets the value of the kppTo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKPPTo(String value) {
        this.kppTo = value;
    }

    /**
     * Gets the value of the taxIdFrom property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTaxIdFrom() {
        return taxIdFrom;
    }

    /**
     * Sets the value of the taxIdFrom property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTaxIdFrom(String value) {
        this.taxIdFrom = value;
    }

    /**
     * Gets the value of the recipientName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRecipientName() {
        return recipientName;
    }

    /**
     * Sets the value of the recipientName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRecipientName(String value) {
        this.recipientName = value;
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
     * Gets the value of the taxColl property.
     * 
     * @return
     *     possible object is
     *     {@link TaxColl }
     *     
     */
    public TaxColl getTaxColl() {
        return taxColl;
    }

    /**
     * Sets the value of the taxColl property.
     * 
     * @param value
     *     allowed object is
     *     {@link TaxColl }
     *     
     */
    public void setTaxColl(TaxColl value) {
        this.taxColl = value;
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
     * Gets the value of the depAcctIdTo property.
     * 
     * @return
     *     possible object is
     *     {@link DepAcctIdType }
     *     
     */
    public DepAcctIdType getDepAcctIdTo() {
        return depAcctIdTo;
    }

    /**
     * Sets the value of the depAcctIdTo property.
     * 
     * @param value
     *     allowed object is
     *     {@link DepAcctIdType }
     *     
     */
    public void setDepAcctIdTo(DepAcctIdType value) {
        this.depAcctIdTo = value;
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
     * Gets the value of the loanAcctIdTo property.
     * 
     * @return
     *     possible object is
     *     {@link LoanAcctIdType }
     *     
     */
    public LoanAcctIdType getLoanAcctIdTo() {
        return loanAcctIdTo;
    }

    /**
     * Sets the value of the loanAcctIdTo property.
     * 
     * @param value
     *     allowed object is
     *     {@link LoanAcctIdType }
     *     
     */
    public void setLoanAcctIdTo(LoanAcctIdType value) {
        this.loanAcctIdTo = value;
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
     * Gets the value of the idSpacing property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdSpacing() {
        return idSpacing;
    }

    /**
     * Sets the value of the idSpacing property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdSpacing(String value) {
        this.idSpacing = value;
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
     * Gets the value of the nonResCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNonResCode() {
        return nonResCode;
    }

    /**
     * Sets the value of the nonResCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNonResCode(String value) {
        this.nonResCode = value;
    }

    /**
     * Gets the value of the curAmt property.
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
     * Gets the value of the depAcctId property.
     * 
     * @return
     *     possible object is
     *     {@link DepAcctIdType }
     *     
     */
    public DepAcctIdType getDepAcctId() {
        return depAcctId;
    }

    /**
     * Sets the value of the depAcctId property.
     * 
     * @param value
     *     allowed object is
     *     {@link DepAcctIdType }
     *     
     */
    public void setDepAcctId(DepAcctIdType value) {
        this.depAcctId = value;
    }

    /**
     * Gets the value of the curAmt1 property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getCurAmt1() {
        return curAmt1;
    }

    /**
     * Sets the value of the curAmt1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setCurAmt1(BigDecimal value) {
        this.curAmt1 = value;
    }

    /**
     * Gets the value of the acctCur1 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAcctCur1() {
        return acctCur1;
    }

    /**
     * Sets the value of the acctCur1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAcctCur1(String value) {
        this.acctCur1 = value;
    }

    /**
     * Gets the value of the curAmt2 property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getCurAmt2() {
        return curAmt2;
    }

    /**
     * Sets the value of the curAmt2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setCurAmt2(BigDecimal value) {
        this.curAmt2 = value;
    }

    /**
     * Gets the value of the acctCur2 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAcctCur2() {
        return acctCur2;
    }

    /**
     * Sets the value of the acctCur2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAcctCur2(String value) {
        this.acctCur2 = value;
    }

    /**
     * Gets the value of the dateCalc property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public Calendar getDateCalc() {
        return dateCalc;
    }

    /**
     * Sets the value of the dateCalc property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDateCalc(Calendar value) {
        this.dateCalc = value;
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
     * Gets the value of the parentAuthorizationCode property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getParentAuthorizationCode() {
        return parentAuthorizationCode;
    }

    /**
     * Sets the value of the parentAuthorizationCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setParentAuthorizationCode(Long value) {
        this.parentAuthorizationCode = value;
    }

    /**
     * Gets the value of the parentTransDtTm property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public Calendar getParentTransDtTm() {
        return parentTransDtTm;
    }

    /**
     * Sets the value of the parentTransDtTm property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setParentTransDtTm(Calendar value) {
        this.parentTransDtTm = value;
    }

    /**
     * Gets the value of the withClose property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean getWithClose() {
        return withClose;
    }

    /**
     * Sets the value of the withClose property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setWithClose(Boolean value) {
        this.withClose = value;
    }

    /**
     * Gets the value of the execute property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean getExecute() {
        return execute;
    }

    /**
     * Sets the value of the execute property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setExecute(Boolean value) {
        this.execute = value;
    }

    /**
     * Gets the value of the comission property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getComission() {
        return comission;
    }

    /**
     * Sets the value of the comission property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setComission(BigDecimal value) {
        this.comission = value;
    }

    /**
     * Gets the value of the requisites property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRequisites() {
        return requisites;
    }

    /**
     * Sets the value of the requisites property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRequisites(String value) {
        this.requisites = value;
    }

    /**
     * Gets the value of the dataSpec property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDataSpec() {
        return dataSpec;
    }

    /**
     * Sets the value of the dataSpec property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDataSpec(String value) {
        this.dataSpec = value;
    }

    /**
     * Gets the value of the uniqueNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUniqueNumber() {
        return uniqueNumber;
    }

    /**
     * Sets the value of the uniqueNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUniqueNumber(String value) {
        this.uniqueNumber = value;
    }

    /**
     * Gets the value of the specClientCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSpecClientCode() {
        return specClientCode;
    }

    /**
     * Sets the value of the specClientCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSpecClientCode(String value) {
        this.specClientCode = value;
    }

    /**
     * Gets the value of the curMonth property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean getCurMonth() {
        return curMonth;
    }

    /**
     * Sets the value of the curMonth property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setCurMonth(Boolean value) {
        this.curMonth = value;
    }

    /**
     * Gets the value of the commissionLabel property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCommissionLabel() {
        return commissionLabel;
    }

    /**
     * Sets the value of the commissionLabel property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCommissionLabel(String value) {
        this.commissionLabel = value;
    }

    /**
     * Gets the value of the serviceCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getServiceCode() {
        return serviceCode;
    }

    /**
     * Sets the value of the serviceCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setServiceCode(String value) {
        this.serviceCode = value;
    }

    /**
     * Gets the value of the serviceKind property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getServiceKind() {
        return serviceKind;
    }

    /**
     * Sets the value of the serviceKind property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setServiceKind(String value) {
        this.serviceKind = value;
    }

    /**
     * Gets the value of the tarif property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTarif() {
        return tarif;
    }

    /**
     * Sets the value of the tarif property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTarif(String value) {
        this.tarif = value;
    }

    /**
     * Gets the value of the canChangeTarif property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCanChangeTarif() {
        return canChangeTarif;
    }

    /**
     * Sets the value of the canChangeTarif property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCanChangeTarif(String value) {
        this.canChangeTarif = value;
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
     *         &lt;element name="PersonInfo" type="{}PersonInfo_Type"/>
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
        "personInfo"
    })
    public static class PayerInfo {

        @XmlElement(name = "PersonInfo", required = true)
        protected PersonInfo personInfo;

        /**
         * Gets the value of the personInfo property.
         * 
         * @return
         *     possible object is
         *     {@link PersonInfo }
         *     
         */
        public PersonInfo getPersonInfo() {
            return personInfo;
        }

        /**
         * Sets the value of the personInfo property.
         * 
         * @param value
         *     allowed object is
         *     {@link PersonInfo }
         *     
         */
        public void setPersonInfo(PersonInfo value) {
            this.personInfo = value;
        }

    }

}
