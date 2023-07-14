
package com.rssl.phizicgate.esberibgate.loanclaim.etsm.generated.release_16;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Список типов данных сообщений для выполнения запросов
 * 
 * <p>Java class for IFXRq_Type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="IFXRq_Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;choice>
 *           &lt;element ref="{}CustInqRq"/>
 *           &lt;element ref="{}BankAcctInqRq"/>
 *           &lt;element ref="{}AcctInqRq"/>
 *           &lt;element ref="{}AcctInfoRq"/>
 *           &lt;element ref="{}DepAcctStmtInqRq"/>
 *           &lt;element ref="{}ImaAcctInRq"/>
 *           &lt;element ref="{}BankAcctStmtInqRq"/>
 *           &lt;element ref="{}BankAcctFullStmtInqRq"/>
 *           &lt;element ref="{}CardAcctDInqRq"/>
 *           &lt;element ref="{}BankAcctStmtImgInqRq"/>
 *           &lt;element ref="{}CardAdditionalInfoRq"/>
 *           &lt;element ref="{}CardReissuePlaceRq"/>
 *           &lt;element ref="{}CCAcctExtStmtInqRq"/>
 *           &lt;element ref="{}CCAcctFullStmtInqRq"/>
 *           &lt;element ref="{}LoanInqRq"/>
 *           &lt;element ref="{}LoanPaymentRq"/>
 *           &lt;element ref="{}DepoClientRegRq"/>
 *           &lt;element ref="{}DepoAccInfoRq"/>
 *           &lt;element ref="{}DepoDeptsInfoRq"/>
 *           &lt;element ref="{}DepoDeptDetInfoRq"/>
 *           &lt;element ref="{}DepoDeptCardPayRq"/>
 *           &lt;element ref="{}DepoAccSecInfoRq"/>
 *           &lt;element ref="{}DepoAccTranRq"/>
 *           &lt;element ref="{}MessageRecvRq"/>
 *           &lt;element ref="{}DepoAccSecRegRq"/>
 *           &lt;element ref="{}DepoArRq"/>
 *           &lt;element ref="{}DepoRevokeDocRq"/>
 *           &lt;element ref="{}XferAddRq"/>
 *           &lt;element ref="{}BillingPayInqRq"/>
 *           &lt;element ref="{}BillingPayPrepRq"/>
 *           &lt;element ref="{}BillingPayExecRq"/>
 *           &lt;element ref="{}ServiceStmtRq"/>
 *           &lt;element ref="{}SvcAcctAudRq"/>
 *           &lt;element ref="{}SvcAcctDelRq"/>
 *           &lt;element ref="{}AccStopDocRq"/>
 *           &lt;element ref="{}CardBlockRq"/>
 *           &lt;element ref="{}MDMClientInfoUpdateRq"/>
 *           &lt;element ref="{}LoanInfoRq"/>
 *           &lt;element ref="{}SvcAddRq"/>
 *           &lt;element ref="{}BankAcctPermissModRq"/>
 *           &lt;element ref="{}DepToNewDepAddRq"/>
 *           &lt;element ref="{}CardToNewDepAddRq"/>
 *           &lt;element ref="{}PfrHasInfoInqRq"/>
 *           &lt;element ref="{}PfrGetInfoInqRq"/>
 *           &lt;element ref="{}NewDepAddRq"/>
 *           &lt;element ref="{}SetAccountStateRq"/>
 *           &lt;element ref="{}OTPRestrictionModRq"/>
 *           &lt;element ref="{}AutoSubscriptionModRq"/>
 *           &lt;element ref="{}AutoSubscriptionStatusModRq"/>
 *           &lt;element ref="{}GetAutoSubscriptionListRq"/>
 *           &lt;element ref="{}GetAutoSubscriptionDetailInfoRq"/>
 *           &lt;element ref="{}GetAutoPaymentListRq"/>
 *           &lt;element ref="{}GetAutoPaymentDetailInfoRq"/>
 *           &lt;element ref="{}DepToNewIMAAddRq"/>
 *           &lt;element ref="{}CardToNewIMAAddRq"/>
 *           &lt;element ref="{}CardToIMAAddRq"/>
 *           &lt;element ref="{}IMAToCardAddRq"/>
 *           &lt;element ref="{}XferOperStatusInfoRq"/>
 *           &lt;element ref="{}DocStateUpdateRq"/>
 *           &lt;element ref="{}GetInsuranceListRq"/>
 *           &lt;element ref="{}GetInsuranceAppRq"/>
 *         &lt;/choice>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "IFXRq_Type", propOrder = {
    "getInsuranceAppRq",
    "getInsuranceListRq",
    "docStateUpdateRq",
    "xferOperStatusInfoRq",
    "imaToCardAddRq",
    "cardToIMAAddRq",
    "cardToNewIMAAddRq",
    "depToNewIMAAddRq",
    "getAutoPaymentDetailInfoRq",
    "getAutoPaymentListRq",
    "getAutoSubscriptionDetailInfoRq",
    "getAutoSubscriptionListRq",
    "autoSubscriptionStatusModRq",
    "autoSubscriptionModRq",
    "otpRestrictionModRq",
    "setAccountStateRq",
    "newDepAddRq",
    "pfrGetInfoInqRq",
    "pfrHasInfoInqRq",
    "cardToNewDepAddRq",
    "depToNewDepAddRq",
    "bankAcctPermissModRq",
    "svcAddRq",
    "loanInfoRq",
    "mdmClientInfoUpdateRq",
    "cardBlockRq",
    "accStopDocRq",
    "svcAcctDelRq",
    "svcAcctAudRq",
    "serviceStmtRq",
    "billingPayExecRq",
    "billingPayPrepRq",
    "billingPayInqRq",
    "xferAddRq",
    "depoRevokeDocRq",
    "depoArRq",
    "depoAccSecRegRq",
    "messageRecvRq",
    "depoAccTranRq",
    "depoAccSecInfoRq",
    "depoDeptCardPayRq",
    "depoDeptDetInfoRq",
    "depoDeptsInfoRq",
    "depoAccInfoRq",
    "depoClientRegRq",
    "loanPaymentRq",
    "loanInqRq",
    "ccAcctFullStmtInqRq",
    "ccAcctExtStmtInqRq",
    "cardReissuePlaceRq",
    "cardAdditionalInfoRq",
    "bankAcctStmtImgInqRq",
    "cardAcctDInqRq",
    "bankAcctFullStmtInqRq",
    "bankAcctStmtInqRq",
    "imaAcctInRq",
    "depAcctStmtInqRq",
    "acctInfoRq",
    "acctInqRq",
    "bankAcctInqRq",
    "custInqRq"
})
@XmlRootElement(name = "DoIFXRq")
public class DoIFXRq {

    @XmlElement(name = "GetInsuranceAppRq")
    protected GetInsuranceAppRq getInsuranceAppRq;
    @XmlElement(name = "GetInsuranceListRq")
    protected GetInsuranceListRq getInsuranceListRq;
    @XmlElement(name = "DocStateUpdateRq")
    protected DocStateUpdateRq docStateUpdateRq;
    @XmlElement(name = "XferOperStatusInfoRq")
    protected XferOperStatusInfoRq xferOperStatusInfoRq;
    @XmlElement(name = "IMAToCardAddRq")
    protected IMAToCardAddRq imaToCardAddRq;
    @XmlElement(name = "CardToIMAAddRq")
    protected CardToIMAAddRq cardToIMAAddRq;
    @XmlElement(name = "CardToNewIMAAddRq")
    protected CardToNewIMAAddRq cardToNewIMAAddRq;
    @XmlElement(name = "DepToNewIMAAddRq")
    protected DepToNewIMAAddRq depToNewIMAAddRq;
    @XmlElement(name = "GetAutoPaymentDetailInfoRq")
    protected GetAutoPaymentDetailInfoRq getAutoPaymentDetailInfoRq;
    @XmlElement(name = "GetAutoPaymentListRq")
    protected GetAutoPaymentListRq getAutoPaymentListRq;
    @XmlElement(name = "GetAutoSubscriptionDetailInfoRq")
    protected GetAutoSubscriptionDetailInfoRq getAutoSubscriptionDetailInfoRq;
    @XmlElement(name = "GetAutoSubscriptionListRq")
    protected GetAutoSubscriptionListRq getAutoSubscriptionListRq;
    @XmlElement(name = "AutoSubscriptionStatusModRq")
    protected AutoSubscriptionStatusModRq autoSubscriptionStatusModRq;
    @XmlElement(name = "AutoSubscriptionModRq")
    protected AutoSubscriptionModRq autoSubscriptionModRq;
    @XmlElement(name = "OTPRestrictionModRq")
    protected OTPRestrictionModRq otpRestrictionModRq;
    @XmlElement(name = "SetAccountStateRq")
    protected SetAccountStateRq setAccountStateRq;
    @XmlElement(name = "NewDepAddRq")
    protected DepToNewDepAddRqType newDepAddRq;
    @XmlElement(name = "PfrGetInfoInqRq")
    protected PfrGetInfoInqRq pfrGetInfoInqRq;
    @XmlElement(name = "PfrHasInfoInqRq")
    protected PfrHasInfoInqRq pfrHasInfoInqRq;
    @XmlElement(name = "CardToNewDepAddRq")
    protected CardToNewDepAddRq cardToNewDepAddRq;
    @XmlElement(name = "DepToNewDepAddRq")
    protected DepToNewDepAddRqType depToNewDepAddRq;
    @XmlElement(name = "BankAcctPermissModRq")
    protected BankAcctPermissModRq bankAcctPermissModRq;
    @XmlElement(name = "SvcAddRq")
    protected SvcAddRq svcAddRq;
    @XmlElement(name = "LoanInfoRq")
    protected LoanInfoRq loanInfoRq;
    @XmlElement(name = "MDMClientInfoUpdateRq")
    protected MDMClientInfoUpdateRq mdmClientInfoUpdateRq;
    @XmlElement(name = "CardBlockRq")
    protected CardBlockRq cardBlockRq;
    @XmlElement(name = "AccStopDocRq")
    protected AccStopDocRq accStopDocRq;
    @XmlElement(name = "SvcAcctDelRq")
    protected SvcAcctDelRq svcAcctDelRq;
    @XmlElement(name = "SvcAcctAudRq")
    protected SvcAcctAudRq svcAcctAudRq;
    @XmlElement(name = "ServiceStmtRq")
    protected ServiceStmtRq serviceStmtRq;
    @XmlElement(name = "BillingPayExecRq")
    protected BillingPayExecRq billingPayExecRq;
    @XmlElement(name = "BillingPayPrepRq")
    protected BillingPayPrepRq billingPayPrepRq;
    @XmlElement(name = "BillingPayInqRq")
    protected BillingPayInqRq billingPayInqRq;
    @XmlElement(name = "XferAddRq")
    protected XferAddRq xferAddRq;
    @XmlElement(name = "DepoRevokeDocRq")
    protected DepoRevokeDocRq depoRevokeDocRq;
    @XmlElement(name = "DepoArRq")
    protected DepoAccInfoRqType depoArRq;
    @XmlElement(name = "DepoAccSecRegRq")
    protected DepoAccSecRegRq depoAccSecRegRq;
    @XmlElement(name = "MessageRecvRq")
    protected MessageRecvRq messageRecvRq;
    @XmlElement(name = "DepoAccTranRq")
    protected DepoAccTranRq depoAccTranRq;
    @XmlElement(name = "DepoAccSecInfoRq")
    protected DepoAccInfoRqType depoAccSecInfoRq;
    @XmlElement(name = "DepoDeptCardPayRq")
    protected DepoDeptCardPayRq depoDeptCardPayRq;
    @XmlElement(name = "DepoDeptDetInfoRq")
    protected DepoDeptDetInfoRq depoDeptDetInfoRq;
    @XmlElement(name = "DepoDeptsInfoRq")
    protected DepoAccInfoRqType depoDeptsInfoRq;
    @XmlElement(name = "DepoAccInfoRq")
    protected DepoAccInfoRqType depoAccInfoRq;
    @XmlElement(name = "DepoClientRegRq")
    protected DepoClientRegRq depoClientRegRq;
    @XmlElement(name = "LoanPaymentRq")
    protected LoanPaymentRq loanPaymentRq;
    @XmlElement(name = "LoanInqRq")
    protected LoanInqRq loanInqRq;
    @XmlElement(name = "CCAcctFullStmtInqRq")
    protected CCAcctFullStmtInqRq ccAcctFullStmtInqRq;
    @XmlElement(name = "CCAcctExtStmtInqRq")
    protected CCAcctExtStmtInqRq ccAcctExtStmtInqRq;
    @XmlElement(name = "CardReissuePlaceRq")
    protected CardReissuePlaceRq cardReissuePlaceRq;
    @XmlElement(name = "CardAdditionalInfoRq")
    protected CardAdditionalInfoRq cardAdditionalInfoRq;
    @XmlElement(name = "BankAcctStmtImgInqRq")
    protected BankAcctStmtImgInqRq bankAcctStmtImgInqRq;
    @XmlElement(name = "CardAcctDInqRq")
    protected CardAcctDInqRq cardAcctDInqRq;
    @XmlElement(name = "BankAcctFullStmtInqRq")
    protected BankAcctFullStmtInqRq bankAcctFullStmtInqRq;
    @XmlElement(name = "BankAcctStmtInqRq")
    protected BankAcctStmtInqRq bankAcctStmtInqRq;
    @XmlElement(name = "ImaAcctInRq")
    protected ImaAcctInRq imaAcctInRq;
    @XmlElement(name = "DepAcctStmtInqRq")
    protected DepAcctStmtInqRq depAcctStmtInqRq;
    @XmlElement(name = "AcctInfoRq")
    protected AcctInfoRq acctInfoRq;
    @XmlElement(name = "AcctInqRq")
    protected AcctInqRq acctInqRq;
    @XmlElement(name = "BankAcctInqRq")
    protected BankAcctInqRq bankAcctInqRq;
    @XmlElement(name = "CustInqRq")
    protected CustInqRq custInqRq;

    /**
     * Gets the value of the getInsuranceAppRq property.
     * 
     * @return
     *     possible object is
     *     {@link GetInsuranceAppRq }
     *     
     */
    public GetInsuranceAppRq getGetInsuranceAppRq() {
        return getInsuranceAppRq;
    }

    /**
     * Sets the value of the getInsuranceAppRq property.
     * 
     * @param value
     *     allowed object is
     *     {@link GetInsuranceAppRq }
     *     
     */
    public void setGetInsuranceAppRq(GetInsuranceAppRq value) {
        this.getInsuranceAppRq = value;
    }

    /**
     * Gets the value of the getInsuranceListRq property.
     * 
     * @return
     *     possible object is
     *     {@link GetInsuranceListRq }
     *     
     */
    public GetInsuranceListRq getGetInsuranceListRq() {
        return getInsuranceListRq;
    }

    /**
     * Sets the value of the getInsuranceListRq property.
     * 
     * @param value
     *     allowed object is
     *     {@link GetInsuranceListRq }
     *     
     */
    public void setGetInsuranceListRq(GetInsuranceListRq value) {
        this.getInsuranceListRq = value;
    }

    /**
     * Gets the value of the docStateUpdateRq property.
     * 
     * @return
     *     possible object is
     *     {@link DocStateUpdateRq }
     *     
     */
    public DocStateUpdateRq getDocStateUpdateRq() {
        return docStateUpdateRq;
    }

    /**
     * Sets the value of the docStateUpdateRq property.
     * 
     * @param value
     *     allowed object is
     *     {@link DocStateUpdateRq }
     *     
     */
    public void setDocStateUpdateRq(DocStateUpdateRq value) {
        this.docStateUpdateRq = value;
    }

    /**
     * Gets the value of the xferOperStatusInfoRq property.
     * 
     * @return
     *     possible object is
     *     {@link XferOperStatusInfoRq }
     *     
     */
    public XferOperStatusInfoRq getXferOperStatusInfoRq() {
        return xferOperStatusInfoRq;
    }

    /**
     * Sets the value of the xferOperStatusInfoRq property.
     * 
     * @param value
     *     allowed object is
     *     {@link XferOperStatusInfoRq }
     *     
     */
    public void setXferOperStatusInfoRq(XferOperStatusInfoRq value) {
        this.xferOperStatusInfoRq = value;
    }

    /**
     * Gets the value of the imaToCardAddRq property.
     * 
     * @return
     *     possible object is
     *     {@link IMAToCardAddRq }
     *     
     */
    public IMAToCardAddRq getIMAToCardAddRq() {
        return imaToCardAddRq;
    }

    /**
     * Sets the value of the imaToCardAddRq property.
     * 
     * @param value
     *     allowed object is
     *     {@link IMAToCardAddRq }
     *     
     */
    public void setIMAToCardAddRq(IMAToCardAddRq value) {
        this.imaToCardAddRq = value;
    }

    /**
     * Gets the value of the cardToIMAAddRq property.
     * 
     * @return
     *     possible object is
     *     {@link CardToIMAAddRq }
     *     
     */
    public CardToIMAAddRq getCardToIMAAddRq() {
        return cardToIMAAddRq;
    }

    /**
     * Sets the value of the cardToIMAAddRq property.
     * 
     * @param value
     *     allowed object is
     *     {@link CardToIMAAddRq }
     *     
     */
    public void setCardToIMAAddRq(CardToIMAAddRq value) {
        this.cardToIMAAddRq = value;
    }

    /**
     * Gets the value of the cardToNewIMAAddRq property.
     * 
     * @return
     *     possible object is
     *     {@link CardToNewIMAAddRq }
     *     
     */
    public CardToNewIMAAddRq getCardToNewIMAAddRq() {
        return cardToNewIMAAddRq;
    }

    /**
     * Sets the value of the cardToNewIMAAddRq property.
     * 
     * @param value
     *     allowed object is
     *     {@link CardToNewIMAAddRq }
     *     
     */
    public void setCardToNewIMAAddRq(CardToNewIMAAddRq value) {
        this.cardToNewIMAAddRq = value;
    }

    /**
     * Gets the value of the depToNewIMAAddRq property.
     * 
     * @return
     *     possible object is
     *     {@link DepToNewIMAAddRq }
     *     
     */
    public DepToNewIMAAddRq getDepToNewIMAAddRq() {
        return depToNewIMAAddRq;
    }

    /**
     * Sets the value of the depToNewIMAAddRq property.
     * 
     * @param value
     *     allowed object is
     *     {@link DepToNewIMAAddRq }
     *     
     */
    public void setDepToNewIMAAddRq(DepToNewIMAAddRq value) {
        this.depToNewIMAAddRq = value;
    }

    /**
     * Gets the value of the getAutoPaymentDetailInfoRq property.
     * 
     * @return
     *     possible object is
     *     {@link GetAutoPaymentDetailInfoRq }
     *     
     */
    public GetAutoPaymentDetailInfoRq getGetAutoPaymentDetailInfoRq() {
        return getAutoPaymentDetailInfoRq;
    }

    /**
     * Sets the value of the getAutoPaymentDetailInfoRq property.
     * 
     * @param value
     *     allowed object is
     *     {@link GetAutoPaymentDetailInfoRq }
     *     
     */
    public void setGetAutoPaymentDetailInfoRq(GetAutoPaymentDetailInfoRq value) {
        this.getAutoPaymentDetailInfoRq = value;
    }

    /**
     * Gets the value of the getAutoPaymentListRq property.
     * 
     * @return
     *     possible object is
     *     {@link GetAutoPaymentListRq }
     *     
     */
    public GetAutoPaymentListRq getGetAutoPaymentListRq() {
        return getAutoPaymentListRq;
    }

    /**
     * Sets the value of the getAutoPaymentListRq property.
     * 
     * @param value
     *     allowed object is
     *     {@link GetAutoPaymentListRq }
     *     
     */
    public void setGetAutoPaymentListRq(GetAutoPaymentListRq value) {
        this.getAutoPaymentListRq = value;
    }

    /**
     * Gets the value of the getAutoSubscriptionDetailInfoRq property.
     * 
     * @return
     *     possible object is
     *     {@link GetAutoSubscriptionDetailInfoRq }
     *     
     */
    public GetAutoSubscriptionDetailInfoRq getGetAutoSubscriptionDetailInfoRq() {
        return getAutoSubscriptionDetailInfoRq;
    }

    /**
     * Sets the value of the getAutoSubscriptionDetailInfoRq property.
     * 
     * @param value
     *     allowed object is
     *     {@link GetAutoSubscriptionDetailInfoRq }
     *     
     */
    public void setGetAutoSubscriptionDetailInfoRq(GetAutoSubscriptionDetailInfoRq value) {
        this.getAutoSubscriptionDetailInfoRq = value;
    }

    /**
     * Gets the value of the getAutoSubscriptionListRq property.
     * 
     * @return
     *     possible object is
     *     {@link GetAutoSubscriptionListRq }
     *     
     */
    public GetAutoSubscriptionListRq getGetAutoSubscriptionListRq() {
        return getAutoSubscriptionListRq;
    }

    /**
     * Sets the value of the getAutoSubscriptionListRq property.
     * 
     * @param value
     *     allowed object is
     *     {@link GetAutoSubscriptionListRq }
     *     
     */
    public void setGetAutoSubscriptionListRq(GetAutoSubscriptionListRq value) {
        this.getAutoSubscriptionListRq = value;
    }

    /**
     * Gets the value of the autoSubscriptionStatusModRq property.
     * 
     * @return
     *     possible object is
     *     {@link AutoSubscriptionStatusModRq }
     *     
     */
    public AutoSubscriptionStatusModRq getAutoSubscriptionStatusModRq() {
        return autoSubscriptionStatusModRq;
    }

    /**
     * Sets the value of the autoSubscriptionStatusModRq property.
     * 
     * @param value
     *     allowed object is
     *     {@link AutoSubscriptionStatusModRq }
     *     
     */
    public void setAutoSubscriptionStatusModRq(AutoSubscriptionStatusModRq value) {
        this.autoSubscriptionStatusModRq = value;
    }

    /**
     * Gets the value of the autoSubscriptionModRq property.
     * 
     * @return
     *     possible object is
     *     {@link AutoSubscriptionModRq }
     *     
     */
    public AutoSubscriptionModRq getAutoSubscriptionModRq() {
        return autoSubscriptionModRq;
    }

    /**
     * Sets the value of the autoSubscriptionModRq property.
     * 
     * @param value
     *     allowed object is
     *     {@link AutoSubscriptionModRq }
     *     
     */
    public void setAutoSubscriptionModRq(AutoSubscriptionModRq value) {
        this.autoSubscriptionModRq = value;
    }

    /**
     * Gets the value of the otpRestrictionModRq property.
     * 
     * @return
     *     possible object is
     *     {@link OTPRestrictionModRq }
     *     
     */
    public OTPRestrictionModRq getOTPRestrictionModRq() {
        return otpRestrictionModRq;
    }

    /**
     * Sets the value of the otpRestrictionModRq property.
     * 
     * @param value
     *     allowed object is
     *     {@link OTPRestrictionModRq }
     *     
     */
    public void setOTPRestrictionModRq(OTPRestrictionModRq value) {
        this.otpRestrictionModRq = value;
    }

    /**
     * Gets the value of the setAccountStateRq property.
     * 
     * @return
     *     possible object is
     *     {@link SetAccountStateRq }
     *     
     */
    public SetAccountStateRq getSetAccountStateRq() {
        return setAccountStateRq;
    }

    /**
     * Sets the value of the setAccountStateRq property.
     * 
     * @param value
     *     allowed object is
     *     {@link SetAccountStateRq }
     *     
     */
    public void setSetAccountStateRq(SetAccountStateRq value) {
        this.setAccountStateRq = value;
    }

    /**
     * Gets the value of the newDepAddRq property.
     * 
     * @return
     *     possible object is
     *     {@link DepToNewDepAddRqType }
     *     
     */
    public DepToNewDepAddRqType getNewDepAddRq() {
        return newDepAddRq;
    }

    /**
     * Sets the value of the newDepAddRq property.
     * 
     * @param value
     *     allowed object is
     *     {@link DepToNewDepAddRqType }
     *     
     */
    public void setNewDepAddRq(DepToNewDepAddRqType value) {
        this.newDepAddRq = value;
    }

    /**
     * Gets the value of the pfrGetInfoInqRq property.
     * 
     * @return
     *     possible object is
     *     {@link PfrGetInfoInqRq }
     *     
     */
    public PfrGetInfoInqRq getPfrGetInfoInqRq() {
        return pfrGetInfoInqRq;
    }

    /**
     * Sets the value of the pfrGetInfoInqRq property.
     * 
     * @param value
     *     allowed object is
     *     {@link PfrGetInfoInqRq }
     *     
     */
    public void setPfrGetInfoInqRq(PfrGetInfoInqRq value) {
        this.pfrGetInfoInqRq = value;
    }

    /**
     * Gets the value of the pfrHasInfoInqRq property.
     * 
     * @return
     *     possible object is
     *     {@link PfrHasInfoInqRq }
     *     
     */
    public PfrHasInfoInqRq getPfrHasInfoInqRq() {
        return pfrHasInfoInqRq;
    }

    /**
     * Sets the value of the pfrHasInfoInqRq property.
     * 
     * @param value
     *     allowed object is
     *     {@link PfrHasInfoInqRq }
     *     
     */
    public void setPfrHasInfoInqRq(PfrHasInfoInqRq value) {
        this.pfrHasInfoInqRq = value;
    }

    /**
     * Gets the value of the cardToNewDepAddRq property.
     * 
     * @return
     *     possible object is
     *     {@link CardToNewDepAddRq }
     *     
     */
    public CardToNewDepAddRq getCardToNewDepAddRq() {
        return cardToNewDepAddRq;
    }

    /**
     * Sets the value of the cardToNewDepAddRq property.
     * 
     * @param value
     *     allowed object is
     *     {@link CardToNewDepAddRq }
     *     
     */
    public void setCardToNewDepAddRq(CardToNewDepAddRq value) {
        this.cardToNewDepAddRq = value;
    }

    /**
     * Gets the value of the depToNewDepAddRq property.
     * 
     * @return
     *     possible object is
     *     {@link DepToNewDepAddRqType }
     *     
     */
    public DepToNewDepAddRqType getDepToNewDepAddRq() {
        return depToNewDepAddRq;
    }

    /**
     * Sets the value of the depToNewDepAddRq property.
     * 
     * @param value
     *     allowed object is
     *     {@link DepToNewDepAddRqType }
     *     
     */
    public void setDepToNewDepAddRq(DepToNewDepAddRqType value) {
        this.depToNewDepAddRq = value;
    }

    /**
     * Gets the value of the bankAcctPermissModRq property.
     * 
     * @return
     *     possible object is
     *     {@link BankAcctPermissModRq }
     *     
     */
    public BankAcctPermissModRq getBankAcctPermissModRq() {
        return bankAcctPermissModRq;
    }

    /**
     * Sets the value of the bankAcctPermissModRq property.
     * 
     * @param value
     *     allowed object is
     *     {@link BankAcctPermissModRq }
     *     
     */
    public void setBankAcctPermissModRq(BankAcctPermissModRq value) {
        this.bankAcctPermissModRq = value;
    }

    /**
     * Gets the value of the svcAddRq property.
     * 
     * @return
     *     possible object is
     *     {@link SvcAddRq }
     *     
     */
    public SvcAddRq getSvcAddRq() {
        return svcAddRq;
    }

    /**
     * Sets the value of the svcAddRq property.
     * 
     * @param value
     *     allowed object is
     *     {@link SvcAddRq }
     *     
     */
    public void setSvcAddRq(SvcAddRq value) {
        this.svcAddRq = value;
    }

    /**
     * Gets the value of the loanInfoRq property.
     * 
     * @return
     *     possible object is
     *     {@link LoanInfoRq }
     *     
     */
    public LoanInfoRq getLoanInfoRq() {
        return loanInfoRq;
    }

    /**
     * Sets the value of the loanInfoRq property.
     * 
     * @param value
     *     allowed object is
     *     {@link LoanInfoRq }
     *     
     */
    public void setLoanInfoRq(LoanInfoRq value) {
        this.loanInfoRq = value;
    }

    /**
     * Gets the value of the mdmClientInfoUpdateRq property.
     * 
     * @return
     *     possible object is
     *     {@link MDMClientInfoUpdateRq }
     *     
     */
    public MDMClientInfoUpdateRq getMDMClientInfoUpdateRq() {
        return mdmClientInfoUpdateRq;
    }

    /**
     * Sets the value of the mdmClientInfoUpdateRq property.
     * 
     * @param value
     *     allowed object is
     *     {@link MDMClientInfoUpdateRq }
     *     
     */
    public void setMDMClientInfoUpdateRq(MDMClientInfoUpdateRq value) {
        this.mdmClientInfoUpdateRq = value;
    }

    /**
     * Gets the value of the cardBlockRq property.
     * 
     * @return
     *     possible object is
     *     {@link CardBlockRq }
     *     
     */
    public CardBlockRq getCardBlockRq() {
        return cardBlockRq;
    }

    /**
     * Sets the value of the cardBlockRq property.
     * 
     * @param value
     *     allowed object is
     *     {@link CardBlockRq }
     *     
     */
    public void setCardBlockRq(CardBlockRq value) {
        this.cardBlockRq = value;
    }

    /**
     * Gets the value of the accStopDocRq property.
     * 
     * @return
     *     possible object is
     *     {@link AccStopDocRq }
     *     
     */
    public AccStopDocRq getAccStopDocRq() {
        return accStopDocRq;
    }

    /**
     * Sets the value of the accStopDocRq property.
     * 
     * @param value
     *     allowed object is
     *     {@link AccStopDocRq }
     *     
     */
    public void setAccStopDocRq(AccStopDocRq value) {
        this.accStopDocRq = value;
    }

    /**
     * Gets the value of the svcAcctDelRq property.
     * 
     * @return
     *     possible object is
     *     {@link SvcAcctDelRq }
     *     
     */
    public SvcAcctDelRq getSvcAcctDelRq() {
        return svcAcctDelRq;
    }

    /**
     * Sets the value of the svcAcctDelRq property.
     * 
     * @param value
     *     allowed object is
     *     {@link SvcAcctDelRq }
     *     
     */
    public void setSvcAcctDelRq(SvcAcctDelRq value) {
        this.svcAcctDelRq = value;
    }

    /**
     * Gets the value of the svcAcctAudRq property.
     * 
     * @return
     *     possible object is
     *     {@link SvcAcctAudRq }
     *     
     */
    public SvcAcctAudRq getSvcAcctAudRq() {
        return svcAcctAudRq;
    }

    /**
     * Sets the value of the svcAcctAudRq property.
     * 
     * @param value
     *     allowed object is
     *     {@link SvcAcctAudRq }
     *     
     */
    public void setSvcAcctAudRq(SvcAcctAudRq value) {
        this.svcAcctAudRq = value;
    }

    /**
     * Gets the value of the serviceStmtRq property.
     * 
     * @return
     *     possible object is
     *     {@link ServiceStmtRq }
     *     
     */
    public ServiceStmtRq getServiceStmtRq() {
        return serviceStmtRq;
    }

    /**
     * Sets the value of the serviceStmtRq property.
     * 
     * @param value
     *     allowed object is
     *     {@link ServiceStmtRq }
     *     
     */
    public void setServiceStmtRq(ServiceStmtRq value) {
        this.serviceStmtRq = value;
    }

    /**
     * Gets the value of the billingPayExecRq property.
     * 
     * @return
     *     possible object is
     *     {@link BillingPayExecRq }
     *     
     */
    public BillingPayExecRq getBillingPayExecRq() {
        return billingPayExecRq;
    }

    /**
     * Sets the value of the billingPayExecRq property.
     * 
     * @param value
     *     allowed object is
     *     {@link BillingPayExecRq }
     *     
     */
    public void setBillingPayExecRq(BillingPayExecRq value) {
        this.billingPayExecRq = value;
    }

    /**
     * Gets the value of the billingPayPrepRq property.
     * 
     * @return
     *     possible object is
     *     {@link BillingPayPrepRq }
     *     
     */
    public BillingPayPrepRq getBillingPayPrepRq() {
        return billingPayPrepRq;
    }

    /**
     * Sets the value of the billingPayPrepRq property.
     * 
     * @param value
     *     allowed object is
     *     {@link BillingPayPrepRq }
     *     
     */
    public void setBillingPayPrepRq(BillingPayPrepRq value) {
        this.billingPayPrepRq = value;
    }

    /**
     * Gets the value of the billingPayInqRq property.
     * 
     * @return
     *     possible object is
     *     {@link BillingPayInqRq }
     *     
     */
    public BillingPayInqRq getBillingPayInqRq() {
        return billingPayInqRq;
    }

    /**
     * Sets the value of the billingPayInqRq property.
     * 
     * @param value
     *     allowed object is
     *     {@link BillingPayInqRq }
     *     
     */
    public void setBillingPayInqRq(BillingPayInqRq value) {
        this.billingPayInqRq = value;
    }

    /**
     * Gets the value of the xferAddRq property.
     * 
     * @return
     *     possible object is
     *     {@link XferAddRq }
     *     
     */
    public XferAddRq getXferAddRq() {
        return xferAddRq;
    }

    /**
     * Sets the value of the xferAddRq property.
     * 
     * @param value
     *     allowed object is
     *     {@link XferAddRq }
     *     
     */
    public void setXferAddRq(XferAddRq value) {
        this.xferAddRq = value;
    }

    /**
     * Gets the value of the depoRevokeDocRq property.
     * 
     * @return
     *     possible object is
     *     {@link DepoRevokeDocRq }
     *     
     */
    public DepoRevokeDocRq getDepoRevokeDocRq() {
        return depoRevokeDocRq;
    }

    /**
     * Sets the value of the depoRevokeDocRq property.
     * 
     * @param value
     *     allowed object is
     *     {@link DepoRevokeDocRq }
     *     
     */
    public void setDepoRevokeDocRq(DepoRevokeDocRq value) {
        this.depoRevokeDocRq = value;
    }

    /**
     * Gets the value of the depoArRq property.
     * 
     * @return
     *     possible object is
     *     {@link DepoAccInfoRqType }
     *     
     */
    public DepoAccInfoRqType getDepoArRq() {
        return depoArRq;
    }

    /**
     * Sets the value of the depoArRq property.
     * 
     * @param value
     *     allowed object is
     *     {@link DepoAccInfoRqType }
     *     
     */
    public void setDepoArRq(DepoAccInfoRqType value) {
        this.depoArRq = value;
    }

    /**
     * Gets the value of the depoAccSecRegRq property.
     * 
     * @return
     *     possible object is
     *     {@link DepoAccSecRegRq }
     *     
     */
    public DepoAccSecRegRq getDepoAccSecRegRq() {
        return depoAccSecRegRq;
    }

    /**
     * Sets the value of the depoAccSecRegRq property.
     * 
     * @param value
     *     allowed object is
     *     {@link DepoAccSecRegRq }
     *     
     */
    public void setDepoAccSecRegRq(DepoAccSecRegRq value) {
        this.depoAccSecRegRq = value;
    }

    /**
     * Gets the value of the messageRecvRq property.
     * 
     * @return
     *     possible object is
     *     {@link MessageRecvRq }
     *     
     */
    public MessageRecvRq getMessageRecvRq() {
        return messageRecvRq;
    }

    /**
     * Sets the value of the messageRecvRq property.
     * 
     * @param value
     *     allowed object is
     *     {@link MessageRecvRq }
     *     
     */
    public void setMessageRecvRq(MessageRecvRq value) {
        this.messageRecvRq = value;
    }

    /**
     * Gets the value of the depoAccTranRq property.
     * 
     * @return
     *     possible object is
     *     {@link DepoAccTranRq }
     *     
     */
    public DepoAccTranRq getDepoAccTranRq() {
        return depoAccTranRq;
    }

    /**
     * Sets the value of the depoAccTranRq property.
     * 
     * @param value
     *     allowed object is
     *     {@link DepoAccTranRq }
     *     
     */
    public void setDepoAccTranRq(DepoAccTranRq value) {
        this.depoAccTranRq = value;
    }

    /**
     * Gets the value of the depoAccSecInfoRq property.
     * 
     * @return
     *     possible object is
     *     {@link DepoAccInfoRqType }
     *     
     */
    public DepoAccInfoRqType getDepoAccSecInfoRq() {
        return depoAccSecInfoRq;
    }

    /**
     * Sets the value of the depoAccSecInfoRq property.
     * 
     * @param value
     *     allowed object is
     *     {@link DepoAccInfoRqType }
     *     
     */
    public void setDepoAccSecInfoRq(DepoAccInfoRqType value) {
        this.depoAccSecInfoRq = value;
    }

    /**
     * Gets the value of the depoDeptCardPayRq property.
     * 
     * @return
     *     possible object is
     *     {@link DepoDeptCardPayRq }
     *     
     */
    public DepoDeptCardPayRq getDepoDeptCardPayRq() {
        return depoDeptCardPayRq;
    }

    /**
     * Sets the value of the depoDeptCardPayRq property.
     * 
     * @param value
     *     allowed object is
     *     {@link DepoDeptCardPayRq }
     *     
     */
    public void setDepoDeptCardPayRq(DepoDeptCardPayRq value) {
        this.depoDeptCardPayRq = value;
    }

    /**
     * Gets the value of the depoDeptDetInfoRq property.
     * 
     * @return
     *     possible object is
     *     {@link DepoDeptDetInfoRq }
     *     
     */
    public DepoDeptDetInfoRq getDepoDeptDetInfoRq() {
        return depoDeptDetInfoRq;
    }

    /**
     * Sets the value of the depoDeptDetInfoRq property.
     * 
     * @param value
     *     allowed object is
     *     {@link DepoDeptDetInfoRq }
     *     
     */
    public void setDepoDeptDetInfoRq(DepoDeptDetInfoRq value) {
        this.depoDeptDetInfoRq = value;
    }

    /**
     * Gets the value of the depoDeptsInfoRq property.
     * 
     * @return
     *     possible object is
     *     {@link DepoAccInfoRqType }
     *     
     */
    public DepoAccInfoRqType getDepoDeptsInfoRq() {
        return depoDeptsInfoRq;
    }

    /**
     * Sets the value of the depoDeptsInfoRq property.
     * 
     * @param value
     *     allowed object is
     *     {@link DepoAccInfoRqType }
     *     
     */
    public void setDepoDeptsInfoRq(DepoAccInfoRqType value) {
        this.depoDeptsInfoRq = value;
    }

    /**
     * Gets the value of the depoAccInfoRq property.
     * 
     * @return
     *     possible object is
     *     {@link DepoAccInfoRqType }
     *     
     */
    public DepoAccInfoRqType getDepoAccInfoRq() {
        return depoAccInfoRq;
    }

    /**
     * Sets the value of the depoAccInfoRq property.
     * 
     * @param value
     *     allowed object is
     *     {@link DepoAccInfoRqType }
     *     
     */
    public void setDepoAccInfoRq(DepoAccInfoRqType value) {
        this.depoAccInfoRq = value;
    }

    /**
     * Gets the value of the depoClientRegRq property.
     * 
     * @return
     *     possible object is
     *     {@link DepoClientRegRq }
     *     
     */
    public DepoClientRegRq getDepoClientRegRq() {
        return depoClientRegRq;
    }

    /**
     * Sets the value of the depoClientRegRq property.
     * 
     * @param value
     *     allowed object is
     *     {@link DepoClientRegRq }
     *     
     */
    public void setDepoClientRegRq(DepoClientRegRq value) {
        this.depoClientRegRq = value;
    }

    /**
     * Gets the value of the loanPaymentRq property.
     * 
     * @return
     *     possible object is
     *     {@link LoanPaymentRq }
     *     
     */
    public LoanPaymentRq getLoanPaymentRq() {
        return loanPaymentRq;
    }

    /**
     * Sets the value of the loanPaymentRq property.
     * 
     * @param value
     *     allowed object is
     *     {@link LoanPaymentRq }
     *     
     */
    public void setLoanPaymentRq(LoanPaymentRq value) {
        this.loanPaymentRq = value;
    }

    /**
     * Gets the value of the loanInqRq property.
     * 
     * @return
     *     possible object is
     *     {@link LoanInqRq }
     *     
     */
    public LoanInqRq getLoanInqRq() {
        return loanInqRq;
    }

    /**
     * Sets the value of the loanInqRq property.
     * 
     * @param value
     *     allowed object is
     *     {@link LoanInqRq }
     *     
     */
    public void setLoanInqRq(LoanInqRq value) {
        this.loanInqRq = value;
    }

    /**
     * Gets the value of the ccAcctFullStmtInqRq property.
     * 
     * @return
     *     possible object is
     *     {@link CCAcctFullStmtInqRq }
     *     
     */
    public CCAcctFullStmtInqRq getCCAcctFullStmtInqRq() {
        return ccAcctFullStmtInqRq;
    }

    /**
     * Sets the value of the ccAcctFullStmtInqRq property.
     * 
     * @param value
     *     allowed object is
     *     {@link CCAcctFullStmtInqRq }
     *     
     */
    public void setCCAcctFullStmtInqRq(CCAcctFullStmtInqRq value) {
        this.ccAcctFullStmtInqRq = value;
    }

    /**
     * Gets the value of the ccAcctExtStmtInqRq property.
     * 
     * @return
     *     possible object is
     *     {@link CCAcctExtStmtInqRq }
     *     
     */
    public CCAcctExtStmtInqRq getCCAcctExtStmtInqRq() {
        return ccAcctExtStmtInqRq;
    }

    /**
     * Sets the value of the ccAcctExtStmtInqRq property.
     * 
     * @param value
     *     allowed object is
     *     {@link CCAcctExtStmtInqRq }
     *     
     */
    public void setCCAcctExtStmtInqRq(CCAcctExtStmtInqRq value) {
        this.ccAcctExtStmtInqRq = value;
    }

    /**
     * Gets the value of the cardReissuePlaceRq property.
     * 
     * @return
     *     possible object is
     *     {@link CardReissuePlaceRq }
     *     
     */
    public CardReissuePlaceRq getCardReissuePlaceRq() {
        return cardReissuePlaceRq;
    }

    /**
     * Sets the value of the cardReissuePlaceRq property.
     * 
     * @param value
     *     allowed object is
     *     {@link CardReissuePlaceRq }
     *     
     */
    public void setCardReissuePlaceRq(CardReissuePlaceRq value) {
        this.cardReissuePlaceRq = value;
    }

    /**
     * Gets the value of the cardAdditionalInfoRq property.
     * 
     * @return
     *     possible object is
     *     {@link CardAdditionalInfoRq }
     *     
     */
    public CardAdditionalInfoRq getCardAdditionalInfoRq() {
        return cardAdditionalInfoRq;
    }

    /**
     * Sets the value of the cardAdditionalInfoRq property.
     * 
     * @param value
     *     allowed object is
     *     {@link CardAdditionalInfoRq }
     *     
     */
    public void setCardAdditionalInfoRq(CardAdditionalInfoRq value) {
        this.cardAdditionalInfoRq = value;
    }

    /**
     * Gets the value of the bankAcctStmtImgInqRq property.
     * 
     * @return
     *     possible object is
     *     {@link BankAcctStmtImgInqRq }
     *     
     */
    public BankAcctStmtImgInqRq getBankAcctStmtImgInqRq() {
        return bankAcctStmtImgInqRq;
    }

    /**
     * Sets the value of the bankAcctStmtImgInqRq property.
     * 
     * @param value
     *     allowed object is
     *     {@link BankAcctStmtImgInqRq }
     *     
     */
    public void setBankAcctStmtImgInqRq(BankAcctStmtImgInqRq value) {
        this.bankAcctStmtImgInqRq = value;
    }

    /**
     * Gets the value of the cardAcctDInqRq property.
     * 
     * @return
     *     possible object is
     *     {@link CardAcctDInqRq }
     *     
     */
    public CardAcctDInqRq getCardAcctDInqRq() {
        return cardAcctDInqRq;
    }

    /**
     * Sets the value of the cardAcctDInqRq property.
     * 
     * @param value
     *     allowed object is
     *     {@link CardAcctDInqRq }
     *     
     */
    public void setCardAcctDInqRq(CardAcctDInqRq value) {
        this.cardAcctDInqRq = value;
    }

    /**
     * Gets the value of the bankAcctFullStmtInqRq property.
     * 
     * @return
     *     possible object is
     *     {@link BankAcctFullStmtInqRq }
     *     
     */
    public BankAcctFullStmtInqRq getBankAcctFullStmtInqRq() {
        return bankAcctFullStmtInqRq;
    }

    /**
     * Sets the value of the bankAcctFullStmtInqRq property.
     * 
     * @param value
     *     allowed object is
     *     {@link BankAcctFullStmtInqRq }
     *     
     */
    public void setBankAcctFullStmtInqRq(BankAcctFullStmtInqRq value) {
        this.bankAcctFullStmtInqRq = value;
    }

    /**
     * Gets the value of the bankAcctStmtInqRq property.
     * 
     * @return
     *     possible object is
     *     {@link BankAcctStmtInqRq }
     *     
     */
    public BankAcctStmtInqRq getBankAcctStmtInqRq() {
        return bankAcctStmtInqRq;
    }

    /**
     * Sets the value of the bankAcctStmtInqRq property.
     * 
     * @param value
     *     allowed object is
     *     {@link BankAcctStmtInqRq }
     *     
     */
    public void setBankAcctStmtInqRq(BankAcctStmtInqRq value) {
        this.bankAcctStmtInqRq = value;
    }

    /**
     * Gets the value of the imaAcctInRq property.
     * 
     * @return
     *     possible object is
     *     {@link ImaAcctInRq }
     *     
     */
    public ImaAcctInRq getImaAcctInRq() {
        return imaAcctInRq;
    }

    /**
     * Sets the value of the imaAcctInRq property.
     * 
     * @param value
     *     allowed object is
     *     {@link ImaAcctInRq }
     *     
     */
    public void setImaAcctInRq(ImaAcctInRq value) {
        this.imaAcctInRq = value;
    }

    /**
     * Gets the value of the depAcctStmtInqRq property.
     * 
     * @return
     *     possible object is
     *     {@link DepAcctStmtInqRq }
     *     
     */
    public DepAcctStmtInqRq getDepAcctStmtInqRq() {
        return depAcctStmtInqRq;
    }

    /**
     * Sets the value of the depAcctStmtInqRq property.
     * 
     * @param value
     *     allowed object is
     *     {@link DepAcctStmtInqRq }
     *     
     */
    public void setDepAcctStmtInqRq(DepAcctStmtInqRq value) {
        this.depAcctStmtInqRq = value;
    }

    /**
     * Gets the value of the acctInfoRq property.
     * 
     * @return
     *     possible object is
     *     {@link AcctInfoRq }
     *     
     */
    public AcctInfoRq getAcctInfoRq() {
        return acctInfoRq;
    }

    /**
     * Sets the value of the acctInfoRq property.
     * 
     * @param value
     *     allowed object is
     *     {@link AcctInfoRq }
     *     
     */
    public void setAcctInfoRq(AcctInfoRq value) {
        this.acctInfoRq = value;
    }

    /**
     * Gets the value of the acctInqRq property.
     * 
     * @return
     *     possible object is
     *     {@link AcctInqRq }
     *     
     */
    public AcctInqRq getAcctInqRq() {
        return acctInqRq;
    }

    /**
     * Sets the value of the acctInqRq property.
     * 
     * @param value
     *     allowed object is
     *     {@link AcctInqRq }
     *     
     */
    public void setAcctInqRq(AcctInqRq value) {
        this.acctInqRq = value;
    }

    /**
     * Gets the value of the bankAcctInqRq property.
     * 
     * @return
     *     possible object is
     *     {@link BankAcctInqRq }
     *     
     */
    public BankAcctInqRq getBankAcctInqRq() {
        return bankAcctInqRq;
    }

    /**
     * Sets the value of the bankAcctInqRq property.
     * 
     * @param value
     *     allowed object is
     *     {@link BankAcctInqRq }
     *     
     */
    public void setBankAcctInqRq(BankAcctInqRq value) {
        this.bankAcctInqRq = value;
    }

    /**
     * Gets the value of the custInqRq property.
     * 
     * @return
     *     possible object is
     *     {@link CustInqRq }
     *     
     */
    public CustInqRq getCustInqRq() {
        return custInqRq;
    }

    /**
     * Sets the value of the custInqRq property.
     * 
     * @param value
     *     allowed object is
     *     {@link CustInqRq }
     *     
     */
    public void setCustInqRq(CustInqRq value) {
        this.custInqRq = value;
    }

}
