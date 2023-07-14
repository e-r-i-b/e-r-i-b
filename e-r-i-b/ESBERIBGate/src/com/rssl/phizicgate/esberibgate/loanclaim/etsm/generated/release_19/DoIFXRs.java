
package com.rssl.phizicgate.esberibgate.loanclaim.etsm.generated.release_19;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Список типов данных ответных сообщений на выполненные запросы
 * 
 * <p>Java class for IFXRs_Type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="IFXRs_Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;choice>
 *           &lt;element ref="{}CustInqRs"/>
 *           &lt;element ref="{}BankAcctInqRs"/>
 *           &lt;element ref="{}AcctInqRs"/>
 *           &lt;element ref="{}AcctInfoRs"/>
 *           &lt;element ref="{}DepAcctExtRs"/>
 *           &lt;element ref="{}ImaAcctInRs"/>
 *           &lt;element ref="{}BankAcctStmtInqRs"/>
 *           &lt;element ref="{}BankAcctFullStmtInqRs"/>
 *           &lt;element ref="{}CardAcctDInqRs"/>
 *           &lt;element ref="{}CardReissuePlaceRs"/>
 *           &lt;element ref="{}BankAcctStmtImgInqRs"/>
 *           &lt;element ref="{}CardAdditionalInfoRs"/>
 *           &lt;element ref="{}CCAcctExtStmtInqRs"/>
 *           &lt;element ref="{}CCAcctFullStmtInqRs"/>
 *           &lt;element ref="{}LoanInqRs"/>
 *           &lt;element ref="{}LoanPaymentRs"/>
 *           &lt;element ref="{}DepoClientRegRs"/>
 *           &lt;element ref="{}DepoAccInfoRs"/>
 *           &lt;element ref="{}DepoDeptsInfoRs"/>
 *           &lt;element ref="{}DepoDeptDetInfoRs"/>
 *           &lt;element ref="{}DepoDeptCardPayRs"/>
 *           &lt;element ref="{}DepoAccSecInfoRs"/>
 *           &lt;element ref="{}DepoAccTranRs"/>
 *           &lt;element ref="{}MessageRecvRs"/>
 *           &lt;element ref="{}DepoAccSecRegRs"/>
 *           &lt;element ref="{}DepoArRs"/>
 *           &lt;element ref="{}DepoRevokeDocRs"/>
 *           &lt;element ref="{}XferAddRs"/>
 *           &lt;element ref="{}BillingPayInqRs"/>
 *           &lt;element ref="{}BillingPayPrepRs"/>
 *           &lt;element ref="{}BillingPayExecRs"/>
 *           &lt;element ref="{}ServiceStmtRs"/>
 *           &lt;element ref="{}SvcAcctAudRs"/>
 *           &lt;element ref="{}SvcAcctDelRs"/>
 *           &lt;element ref="{}AccStopDocRs"/>
 *           &lt;element ref="{}CardBlockRs"/>
 *           &lt;element ref="{}MDMClientInfoUpdateRs"/>
 *           &lt;element ref="{}LoanInfoRs"/>
 *           &lt;element ref="{}SvcAddRs"/>
 *           &lt;element ref="{}BankAcctPermissModRs"/>
 *           &lt;element ref="{}DepToNewDepAddRs"/>
 *           &lt;element ref="{}CardToNewDepAddRs"/>
 *           &lt;element ref="{}PfrHasInfoInqRs"/>
 *           &lt;element ref="{}PfrGetInfoInqRs"/>
 *           &lt;element ref="{}NewDepAddRs"/>
 *           &lt;element ref="{}SetAccountStateRs"/>
 *           &lt;element ref="{}OTPRestrictionModRs"/>
 *           &lt;element ref="{}AutoSubscriptionModRs"/>
 *           &lt;element ref="{}AutoSubscriptionStatusModRs"/>
 *           &lt;element ref="{}GetAutoSubscriptionListRs"/>
 *           &lt;element ref="{}GetAutoSubscriptionDetailInfoRs"/>
 *           &lt;element ref="{}GetAutoPaymentListRs"/>
 *           &lt;element ref="{}GetAutoPaymentDetailInfoRs"/>
 *           &lt;element ref="{}DepToNewIMAAddRs"/>
 *           &lt;element ref="{}CardToNewIMAAddRs"/>
 *           &lt;element ref="{}CardToIMAAddRs"/>
 *           &lt;element ref="{}IMAToCardAddRs"/>
 *           &lt;element ref="{}XferOperStatusInfoRs"/>
 *           &lt;element ref="{}DocStateUpdateRs"/>
 *           &lt;element ref="{}GetInsuranceListRs"/>
 *           &lt;element ref="{}GetInsuranceAppRs"/>
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
@XmlType(name = "IFXRs_Type", propOrder = {
    "getInsuranceAppRs",
    "getInsuranceListRs",
    "docStateUpdateRs",
    "xferOperStatusInfoRs",
    "imaToCardAddRs",
    "cardToIMAAddRs",
    "cardToNewIMAAddRs",
    "depToNewIMAAddRs",
    "getAutoPaymentDetailInfoRs",
    "getAutoPaymentListRs",
    "getAutoSubscriptionDetailInfoRs",
    "getAutoSubscriptionListRs",
    "autoSubscriptionStatusModRs",
    "autoSubscriptionModRs",
    "otpRestrictionModRs",
    "setAccountStateRs",
    "newDepAddRs",
    "pfrGetInfoInqRs",
    "pfrHasInfoInqRs",
    "cardToNewDepAddRs",
    "depToNewDepAddRs",
    "bankAcctPermissModRs",
    "svcAddRs",
    "loanInfoRs",
    "mdmClientInfoUpdateRs",
    "cardBlockRs",
    "accStopDocRs",
    "svcAcctDelRs",
    "svcAcctAudRs",
    "serviceStmtRs",
    "billingPayExecRs",
    "billingPayPrepRs",
    "billingPayInqRs",
    "xferAddRs",
    "depoRevokeDocRs",
    "depoArRs",
    "depoAccSecRegRs",
    "messageRecvRs",
    "depoAccTranRs",
    "depoAccSecInfoRs",
    "depoDeptCardPayRs",
    "depoDeptDetInfoRs",
    "depoDeptsInfoRs",
    "depoAccInfoRs",
    "depoClientRegRs",
    "loanPaymentRs",
    "loanInqRs",
    "ccAcctFullStmtInqRs",
    "ccAcctExtStmtInqRs",
    "cardAdditionalInfoRs",
    "bankAcctStmtImgInqRs",
    "cardReissuePlaceRs",
    "cardAcctDInqRs",
    "bankAcctFullStmtInqRs",
    "bankAcctStmtInqRs",
    "imaAcctInRs",
    "depAcctExtRs",
    "acctInfoRs",
    "acctInqRs",
    "bankAcctInqRs",
    "custInqRs"
})
@XmlRootElement(name = "DoIFXRs")
public class DoIFXRs {

    @XmlElement(name = "GetInsuranceAppRs")
    protected GetInsuranceAppRs getInsuranceAppRs;
    @XmlElement(name = "GetInsuranceListRs")
    protected GetInsuranceListRs getInsuranceListRs;
    @XmlElement(name = "DocStateUpdateRs")
    protected DocStateUpdateRs docStateUpdateRs;
    @XmlElement(name = "XferOperStatusInfoRs")
    protected XferOperStatusInfoRs xferOperStatusInfoRs;
    @XmlElement(name = "IMAToCardAddRs")
    protected IMAToCardAddRs imaToCardAddRs;
    @XmlElement(name = "CardToIMAAddRs")
    protected CardToIMAAddRs cardToIMAAddRs;
    @XmlElement(name = "CardToNewIMAAddRs")
    protected CardToNewIMAAddRs cardToNewIMAAddRs;
    @XmlElement(name = "DepToNewIMAAddRs")
    protected DepToNewIMAAddRs depToNewIMAAddRs;
    @XmlElement(name = "GetAutoPaymentDetailInfoRs")
    protected GetAutoPaymentDetailInfoRs getAutoPaymentDetailInfoRs;
    @XmlElement(name = "GetAutoPaymentListRs")
    protected GetAutoPaymentListRs getAutoPaymentListRs;
    @XmlElement(name = "GetAutoSubscriptionDetailInfoRs")
    protected GetAutoSubscriptionDetailInfoRs getAutoSubscriptionDetailInfoRs;
    @XmlElement(name = "GetAutoSubscriptionListRs")
    protected GetAutoSubscriptionListRs getAutoSubscriptionListRs;
    @XmlElement(name = "AutoSubscriptionStatusModRs")
    protected AutoSubscriptionStatusModRs autoSubscriptionStatusModRs;
    @XmlElement(name = "AutoSubscriptionModRs")
    protected AutoSubscriptionModRs autoSubscriptionModRs;
    @XmlElement(name = "OTPRestrictionModRs")
    protected OTPRestrictionModRs otpRestrictionModRs;
    @XmlElement(name = "SetAccountStateRs")
    protected SetAccountStateRs setAccountStateRs;
    @XmlElement(name = "NewDepAddRs")
    protected DepToNewDepAddRsType newDepAddRs;
    @XmlElement(name = "PfrGetInfoInqRs")
    protected PfrGetInfoInqRs pfrGetInfoInqRs;
    @XmlElement(name = "PfrHasInfoInqRs")
    protected PfrHasInfoInqRs pfrHasInfoInqRs;
    @XmlElement(name = "CardToNewDepAddRs")
    protected CardToNewDepAddRs cardToNewDepAddRs;
    @XmlElement(name = "DepToNewDepAddRs")
    protected DepToNewDepAddRsType depToNewDepAddRs;
    @XmlElement(name = "BankAcctPermissModRs")
    protected BankAcctPermissModRs bankAcctPermissModRs;
    @XmlElement(name = "SvcAddRs")
    protected SvcAddRs svcAddRs;
    @XmlElement(name = "LoanInfoRs")
    protected LoanInfoRs loanInfoRs;
    @XmlElement(name = "MDMClientInfoUpdateRs")
    protected MDMClientInfoUpdateRs mdmClientInfoUpdateRs;
    @XmlElement(name = "CardBlockRs")
    protected CardBlockRs cardBlockRs;
    @XmlElement(name = "AccStopDocRs")
    protected AccStopDocRs accStopDocRs;
    @XmlElement(name = "SvcAcctDelRs")
    protected SvcAcctDelRs svcAcctDelRs;
    @XmlElement(name = "SvcAcctAudRs")
    protected SvcAcctAudRs svcAcctAudRs;
    @XmlElement(name = "ServiceStmtRs")
    protected ServiceStmtRs serviceStmtRs;
    @XmlElement(name = "BillingPayExecRs")
    protected BillingPayExecRs billingPayExecRs;
    @XmlElement(name = "BillingPayPrepRs")
    protected BillingPayPrepRs billingPayPrepRs;
    @XmlElement(name = "BillingPayInqRs")
    protected BillingPayInqRs billingPayInqRs;
    @XmlElement(name = "XferAddRs")
    protected XferAddRs xferAddRs;
    @XmlElement(name = "DepoRevokeDocRs")
    protected DepoRevokeDocRs depoRevokeDocRs;
    @XmlElement(name = "DepoArRs")
    protected DepoArRs depoArRs;
    @XmlElement(name = "DepoAccSecRegRs")
    protected DepoAccSecRegRs depoAccSecRegRs;
    @XmlElement(name = "MessageRecvRs")
    protected MessageRecvRs messageRecvRs;
    @XmlElement(name = "DepoAccTranRs")
    protected DepoAccTranRs depoAccTranRs;
    @XmlElement(name = "DepoAccSecInfoRs")
    protected DepoAccSecInfoRs depoAccSecInfoRs;
    @XmlElement(name = "DepoDeptCardPayRs")
    protected DepoDeptCardPayRs depoDeptCardPayRs;
    @XmlElement(name = "DepoDeptDetInfoRs")
    protected DepoDeptDetInfoRs depoDeptDetInfoRs;
    @XmlElement(name = "DepoDeptsInfoRs")
    protected DepoDeptsInfoRs depoDeptsInfoRs;
    @XmlElement(name = "DepoAccInfoRs")
    protected DepoAccInfoRs depoAccInfoRs;
    @XmlElement(name = "DepoClientRegRs")
    protected DepoClientRegRs depoClientRegRs;
    @XmlElement(name = "LoanPaymentRs")
    protected LoanPaymentRs loanPaymentRs;
    @XmlElement(name = "LoanInqRs")
    protected LoanInqRs loanInqRs;
    @XmlElement(name = "CCAcctFullStmtInqRs")
    protected CCAcctFullStmtInqRs ccAcctFullStmtInqRs;
    @XmlElement(name = "CCAcctExtStmtInqRs")
    protected CCAcctExtStmtInqRs ccAcctExtStmtInqRs;
    @XmlElement(name = "CardAdditionalInfoRs")
    protected CardAdditionalInfoRs cardAdditionalInfoRs;
    @XmlElement(name = "BankAcctStmtImgInqRs")
    protected BankAcctStmtImgInqRs bankAcctStmtImgInqRs;
    @XmlElement(name = "CardReissuePlaceRs")
    protected CardReissuePlaceRs cardReissuePlaceRs;
    @XmlElement(name = "CardAcctDInqRs")
    protected CardAcctDInqRs cardAcctDInqRs;
    @XmlElement(name = "BankAcctFullStmtInqRs")
    protected BankAcctFullStmtInqRs bankAcctFullStmtInqRs;
    @XmlElement(name = "BankAcctStmtInqRs")
    protected BankAcctStmtInqRs bankAcctStmtInqRs;
    @XmlElement(name = "ImaAcctInRs")
    protected ImaAcctInRs imaAcctInRs;
    @XmlElement(name = "DepAcctExtRs")
    protected DepAcctExtRs depAcctExtRs;
    @XmlElement(name = "AcctInfoRs")
    protected AcctInfoRs acctInfoRs;
    @XmlElement(name = "AcctInqRs")
    protected AcctInqRs acctInqRs;
    @XmlElement(name = "BankAcctInqRs")
    protected BankAcctInqRs bankAcctInqRs;
    @XmlElement(name = "CustInqRs")
    protected CustInqRs custInqRs;

    /**
     * Gets the value of the getInsuranceAppRs property.
     * 
     * @return
     *     possible object is
     *     {@link GetInsuranceAppRs }
     *     
     */
    public GetInsuranceAppRs getGetInsuranceAppRs() {
        return getInsuranceAppRs;
    }

    /**
     * Sets the value of the getInsuranceAppRs property.
     * 
     * @param value
     *     allowed object is
     *     {@link GetInsuranceAppRs }
     *     
     */
    public void setGetInsuranceAppRs(GetInsuranceAppRs value) {
        this.getInsuranceAppRs = value;
    }

    /**
     * Gets the value of the getInsuranceListRs property.
     * 
     * @return
     *     possible object is
     *     {@link GetInsuranceListRs }
     *     
     */
    public GetInsuranceListRs getGetInsuranceListRs() {
        return getInsuranceListRs;
    }

    /**
     * Sets the value of the getInsuranceListRs property.
     * 
     * @param value
     *     allowed object is
     *     {@link GetInsuranceListRs }
     *     
     */
    public void setGetInsuranceListRs(GetInsuranceListRs value) {
        this.getInsuranceListRs = value;
    }

    /**
     * Gets the value of the docStateUpdateRs property.
     * 
     * @return
     *     possible object is
     *     {@link DocStateUpdateRs }
     *     
     */
    public DocStateUpdateRs getDocStateUpdateRs() {
        return docStateUpdateRs;
    }

    /**
     * Sets the value of the docStateUpdateRs property.
     * 
     * @param value
     *     allowed object is
     *     {@link DocStateUpdateRs }
     *     
     */
    public void setDocStateUpdateRs(DocStateUpdateRs value) {
        this.docStateUpdateRs = value;
    }

    /**
     * Gets the value of the xferOperStatusInfoRs property.
     * 
     * @return
     *     possible object is
     *     {@link XferOperStatusInfoRs }
     *     
     */
    public XferOperStatusInfoRs getXferOperStatusInfoRs() {
        return xferOperStatusInfoRs;
    }

    /**
     * Sets the value of the xferOperStatusInfoRs property.
     * 
     * @param value
     *     allowed object is
     *     {@link XferOperStatusInfoRs }
     *     
     */
    public void setXferOperStatusInfoRs(XferOperStatusInfoRs value) {
        this.xferOperStatusInfoRs = value;
    }

    /**
     * Gets the value of the imaToCardAddRs property.
     * 
     * @return
     *     possible object is
     *     {@link IMAToCardAddRs }
     *     
     */
    public IMAToCardAddRs getIMAToCardAddRs() {
        return imaToCardAddRs;
    }

    /**
     * Sets the value of the imaToCardAddRs property.
     * 
     * @param value
     *     allowed object is
     *     {@link IMAToCardAddRs }
     *     
     */
    public void setIMAToCardAddRs(IMAToCardAddRs value) {
        this.imaToCardAddRs = value;
    }

    /**
     * Gets the value of the cardToIMAAddRs property.
     * 
     * @return
     *     possible object is
     *     {@link CardToIMAAddRs }
     *     
     */
    public CardToIMAAddRs getCardToIMAAddRs() {
        return cardToIMAAddRs;
    }

    /**
     * Sets the value of the cardToIMAAddRs property.
     * 
     * @param value
     *     allowed object is
     *     {@link CardToIMAAddRs }
     *     
     */
    public void setCardToIMAAddRs(CardToIMAAddRs value) {
        this.cardToIMAAddRs = value;
    }

    /**
     * Gets the value of the cardToNewIMAAddRs property.
     * 
     * @return
     *     possible object is
     *     {@link CardToNewIMAAddRs }
     *     
     */
    public CardToNewIMAAddRs getCardToNewIMAAddRs() {
        return cardToNewIMAAddRs;
    }

    /**
     * Sets the value of the cardToNewIMAAddRs property.
     * 
     * @param value
     *     allowed object is
     *     {@link CardToNewIMAAddRs }
     *     
     */
    public void setCardToNewIMAAddRs(CardToNewIMAAddRs value) {
        this.cardToNewIMAAddRs = value;
    }

    /**
     * Gets the value of the depToNewIMAAddRs property.
     * 
     * @return
     *     possible object is
     *     {@link DepToNewIMAAddRs }
     *     
     */
    public DepToNewIMAAddRs getDepToNewIMAAddRs() {
        return depToNewIMAAddRs;
    }

    /**
     * Sets the value of the depToNewIMAAddRs property.
     * 
     * @param value
     *     allowed object is
     *     {@link DepToNewIMAAddRs }
     *     
     */
    public void setDepToNewIMAAddRs(DepToNewIMAAddRs value) {
        this.depToNewIMAAddRs = value;
    }

    /**
     * Gets the value of the getAutoPaymentDetailInfoRs property.
     * 
     * @return
     *     possible object is
     *     {@link GetAutoPaymentDetailInfoRs }
     *     
     */
    public GetAutoPaymentDetailInfoRs getGetAutoPaymentDetailInfoRs() {
        return getAutoPaymentDetailInfoRs;
    }

    /**
     * Sets the value of the getAutoPaymentDetailInfoRs property.
     * 
     * @param value
     *     allowed object is
     *     {@link GetAutoPaymentDetailInfoRs }
     *     
     */
    public void setGetAutoPaymentDetailInfoRs(GetAutoPaymentDetailInfoRs value) {
        this.getAutoPaymentDetailInfoRs = value;
    }

    /**
     * Gets the value of the getAutoPaymentListRs property.
     * 
     * @return
     *     possible object is
     *     {@link GetAutoPaymentListRs }
     *     
     */
    public GetAutoPaymentListRs getGetAutoPaymentListRs() {
        return getAutoPaymentListRs;
    }

    /**
     * Sets the value of the getAutoPaymentListRs property.
     * 
     * @param value
     *     allowed object is
     *     {@link GetAutoPaymentListRs }
     *     
     */
    public void setGetAutoPaymentListRs(GetAutoPaymentListRs value) {
        this.getAutoPaymentListRs = value;
    }

    /**
     * Gets the value of the getAutoSubscriptionDetailInfoRs property.
     * 
     * @return
     *     possible object is
     *     {@link GetAutoSubscriptionDetailInfoRs }
     *     
     */
    public GetAutoSubscriptionDetailInfoRs getGetAutoSubscriptionDetailInfoRs() {
        return getAutoSubscriptionDetailInfoRs;
    }

    /**
     * Sets the value of the getAutoSubscriptionDetailInfoRs property.
     * 
     * @param value
     *     allowed object is
     *     {@link GetAutoSubscriptionDetailInfoRs }
     *     
     */
    public void setGetAutoSubscriptionDetailInfoRs(GetAutoSubscriptionDetailInfoRs value) {
        this.getAutoSubscriptionDetailInfoRs = value;
    }

    /**
     * Gets the value of the getAutoSubscriptionListRs property.
     * 
     * @return
     *     possible object is
     *     {@link GetAutoSubscriptionListRs }
     *     
     */
    public GetAutoSubscriptionListRs getGetAutoSubscriptionListRs() {
        return getAutoSubscriptionListRs;
    }

    /**
     * Sets the value of the getAutoSubscriptionListRs property.
     * 
     * @param value
     *     allowed object is
     *     {@link GetAutoSubscriptionListRs }
     *     
     */
    public void setGetAutoSubscriptionListRs(GetAutoSubscriptionListRs value) {
        this.getAutoSubscriptionListRs = value;
    }

    /**
     * Gets the value of the autoSubscriptionStatusModRs property.
     * 
     * @return
     *     possible object is
     *     {@link AutoSubscriptionStatusModRs }
     *     
     */
    public AutoSubscriptionStatusModRs getAutoSubscriptionStatusModRs() {
        return autoSubscriptionStatusModRs;
    }

    /**
     * Sets the value of the autoSubscriptionStatusModRs property.
     * 
     * @param value
     *     allowed object is
     *     {@link AutoSubscriptionStatusModRs }
     *     
     */
    public void setAutoSubscriptionStatusModRs(AutoSubscriptionStatusModRs value) {
        this.autoSubscriptionStatusModRs = value;
    }

    /**
     * Gets the value of the autoSubscriptionModRs property.
     * 
     * @return
     *     possible object is
     *     {@link AutoSubscriptionModRs }
     *     
     */
    public AutoSubscriptionModRs getAutoSubscriptionModRs() {
        return autoSubscriptionModRs;
    }

    /**
     * Sets the value of the autoSubscriptionModRs property.
     * 
     * @param value
     *     allowed object is
     *     {@link AutoSubscriptionModRs }
     *     
     */
    public void setAutoSubscriptionModRs(AutoSubscriptionModRs value) {
        this.autoSubscriptionModRs = value;
    }

    /**
     * Gets the value of the otpRestrictionModRs property.
     * 
     * @return
     *     possible object is
     *     {@link OTPRestrictionModRs }
     *     
     */
    public OTPRestrictionModRs getOTPRestrictionModRs() {
        return otpRestrictionModRs;
    }

    /**
     * Sets the value of the otpRestrictionModRs property.
     * 
     * @param value
     *     allowed object is
     *     {@link OTPRestrictionModRs }
     *     
     */
    public void setOTPRestrictionModRs(OTPRestrictionModRs value) {
        this.otpRestrictionModRs = value;
    }

    /**
     * Gets the value of the setAccountStateRs property.
     * 
     * @return
     *     possible object is
     *     {@link SetAccountStateRs }
     *     
     */
    public SetAccountStateRs getSetAccountStateRs() {
        return setAccountStateRs;
    }

    /**
     * Sets the value of the setAccountStateRs property.
     * 
     * @param value
     *     allowed object is
     *     {@link SetAccountStateRs }
     *     
     */
    public void setSetAccountStateRs(SetAccountStateRs value) {
        this.setAccountStateRs = value;
    }

    /**
     * Gets the value of the newDepAddRs property.
     * 
     * @return
     *     possible object is
     *     {@link DepToNewDepAddRsType }
     *     
     */
    public DepToNewDepAddRsType getNewDepAddRs() {
        return newDepAddRs;
    }

    /**
     * Sets the value of the newDepAddRs property.
     * 
     * @param value
     *     allowed object is
     *     {@link DepToNewDepAddRsType }
     *     
     */
    public void setNewDepAddRs(DepToNewDepAddRsType value) {
        this.newDepAddRs = value;
    }

    /**
     * Gets the value of the pfrGetInfoInqRs property.
     * 
     * @return
     *     possible object is
     *     {@link PfrGetInfoInqRs }
     *     
     */
    public PfrGetInfoInqRs getPfrGetInfoInqRs() {
        return pfrGetInfoInqRs;
    }

    /**
     * Sets the value of the pfrGetInfoInqRs property.
     * 
     * @param value
     *     allowed object is
     *     {@link PfrGetInfoInqRs }
     *     
     */
    public void setPfrGetInfoInqRs(PfrGetInfoInqRs value) {
        this.pfrGetInfoInqRs = value;
    }

    /**
     * Gets the value of the pfrHasInfoInqRs property.
     * 
     * @return
     *     possible object is
     *     {@link PfrHasInfoInqRs }
     *     
     */
    public PfrHasInfoInqRs getPfrHasInfoInqRs() {
        return pfrHasInfoInqRs;
    }

    /**
     * Sets the value of the pfrHasInfoInqRs property.
     * 
     * @param value
     *     allowed object is
     *     {@link PfrHasInfoInqRs }
     *     
     */
    public void setPfrHasInfoInqRs(PfrHasInfoInqRs value) {
        this.pfrHasInfoInqRs = value;
    }

    /**
     * Gets the value of the cardToNewDepAddRs property.
     * 
     * @return
     *     possible object is
     *     {@link CardToNewDepAddRs }
     *     
     */
    public CardToNewDepAddRs getCardToNewDepAddRs() {
        return cardToNewDepAddRs;
    }

    /**
     * Sets the value of the cardToNewDepAddRs property.
     * 
     * @param value
     *     allowed object is
     *     {@link CardToNewDepAddRs }
     *     
     */
    public void setCardToNewDepAddRs(CardToNewDepAddRs value) {
        this.cardToNewDepAddRs = value;
    }

    /**
     * Gets the value of the depToNewDepAddRs property.
     * 
     * @return
     *     possible object is
     *     {@link DepToNewDepAddRsType }
     *     
     */
    public DepToNewDepAddRsType getDepToNewDepAddRs() {
        return depToNewDepAddRs;
    }

    /**
     * Sets the value of the depToNewDepAddRs property.
     * 
     * @param value
     *     allowed object is
     *     {@link DepToNewDepAddRsType }
     *     
     */
    public void setDepToNewDepAddRs(DepToNewDepAddRsType value) {
        this.depToNewDepAddRs = value;
    }

    /**
     * Gets the value of the bankAcctPermissModRs property.
     * 
     * @return
     *     possible object is
     *     {@link BankAcctPermissModRs }
     *     
     */
    public BankAcctPermissModRs getBankAcctPermissModRs() {
        return bankAcctPermissModRs;
    }

    /**
     * Sets the value of the bankAcctPermissModRs property.
     * 
     * @param value
     *     allowed object is
     *     {@link BankAcctPermissModRs }
     *     
     */
    public void setBankAcctPermissModRs(BankAcctPermissModRs value) {
        this.bankAcctPermissModRs = value;
    }

    /**
     * Gets the value of the svcAddRs property.
     * 
     * @return
     *     possible object is
     *     {@link SvcAddRs }
     *     
     */
    public SvcAddRs getSvcAddRs() {
        return svcAddRs;
    }

    /**
     * Sets the value of the svcAddRs property.
     * 
     * @param value
     *     allowed object is
     *     {@link SvcAddRs }
     *     
     */
    public void setSvcAddRs(SvcAddRs value) {
        this.svcAddRs = value;
    }

    /**
     * Gets the value of the loanInfoRs property.
     * 
     * @return
     *     possible object is
     *     {@link LoanInfoRs }
     *     
     */
    public LoanInfoRs getLoanInfoRs() {
        return loanInfoRs;
    }

    /**
     * Sets the value of the loanInfoRs property.
     * 
     * @param value
     *     allowed object is
     *     {@link LoanInfoRs }
     *     
     */
    public void setLoanInfoRs(LoanInfoRs value) {
        this.loanInfoRs = value;
    }

    /**
     * Gets the value of the mdmClientInfoUpdateRs property.
     * 
     * @return
     *     possible object is
     *     {@link MDMClientInfoUpdateRs }
     *     
     */
    public MDMClientInfoUpdateRs getMDMClientInfoUpdateRs() {
        return mdmClientInfoUpdateRs;
    }

    /**
     * Sets the value of the mdmClientInfoUpdateRs property.
     * 
     * @param value
     *     allowed object is
     *     {@link MDMClientInfoUpdateRs }
     *     
     */
    public void setMDMClientInfoUpdateRs(MDMClientInfoUpdateRs value) {
        this.mdmClientInfoUpdateRs = value;
    }

    /**
     * Gets the value of the cardBlockRs property.
     * 
     * @return
     *     possible object is
     *     {@link CardBlockRs }
     *     
     */
    public CardBlockRs getCardBlockRs() {
        return cardBlockRs;
    }

    /**
     * Sets the value of the cardBlockRs property.
     * 
     * @param value
     *     allowed object is
     *     {@link CardBlockRs }
     *     
     */
    public void setCardBlockRs(CardBlockRs value) {
        this.cardBlockRs = value;
    }

    /**
     * Gets the value of the accStopDocRs property.
     * 
     * @return
     *     possible object is
     *     {@link AccStopDocRs }
     *     
     */
    public AccStopDocRs getAccStopDocRs() {
        return accStopDocRs;
    }

    /**
     * Sets the value of the accStopDocRs property.
     * 
     * @param value
     *     allowed object is
     *     {@link AccStopDocRs }
     *     
     */
    public void setAccStopDocRs(AccStopDocRs value) {
        this.accStopDocRs = value;
    }

    /**
     * Gets the value of the svcAcctDelRs property.
     * 
     * @return
     *     possible object is
     *     {@link SvcAcctDelRs }
     *     
     */
    public SvcAcctDelRs getSvcAcctDelRs() {
        return svcAcctDelRs;
    }

    /**
     * Sets the value of the svcAcctDelRs property.
     * 
     * @param value
     *     allowed object is
     *     {@link SvcAcctDelRs }
     *     
     */
    public void setSvcAcctDelRs(SvcAcctDelRs value) {
        this.svcAcctDelRs = value;
    }

    /**
     * Gets the value of the svcAcctAudRs property.
     * 
     * @return
     *     possible object is
     *     {@link SvcAcctAudRs }
     *     
     */
    public SvcAcctAudRs getSvcAcctAudRs() {
        return svcAcctAudRs;
    }

    /**
     * Sets the value of the svcAcctAudRs property.
     * 
     * @param value
     *     allowed object is
     *     {@link SvcAcctAudRs }
     *     
     */
    public void setSvcAcctAudRs(SvcAcctAudRs value) {
        this.svcAcctAudRs = value;
    }

    /**
     * Gets the value of the serviceStmtRs property.
     * 
     * @return
     *     possible object is
     *     {@link ServiceStmtRs }
     *     
     */
    public ServiceStmtRs getServiceStmtRs() {
        return serviceStmtRs;
    }

    /**
     * Sets the value of the serviceStmtRs property.
     * 
     * @param value
     *     allowed object is
     *     {@link ServiceStmtRs }
     *     
     */
    public void setServiceStmtRs(ServiceStmtRs value) {
        this.serviceStmtRs = value;
    }

    /**
     * Gets the value of the billingPayExecRs property.
     * 
     * @return
     *     possible object is
     *     {@link BillingPayExecRs }
     *     
     */
    public BillingPayExecRs getBillingPayExecRs() {
        return billingPayExecRs;
    }

    /**
     * Sets the value of the billingPayExecRs property.
     * 
     * @param value
     *     allowed object is
     *     {@link BillingPayExecRs }
     *     
     */
    public void setBillingPayExecRs(BillingPayExecRs value) {
        this.billingPayExecRs = value;
    }

    /**
     * Gets the value of the billingPayPrepRs property.
     * 
     * @return
     *     possible object is
     *     {@link BillingPayPrepRs }
     *     
     */
    public BillingPayPrepRs getBillingPayPrepRs() {
        return billingPayPrepRs;
    }

    /**
     * Sets the value of the billingPayPrepRs property.
     * 
     * @param value
     *     allowed object is
     *     {@link BillingPayPrepRs }
     *     
     */
    public void setBillingPayPrepRs(BillingPayPrepRs value) {
        this.billingPayPrepRs = value;
    }

    /**
     * Gets the value of the billingPayInqRs property.
     * 
     * @return
     *     possible object is
     *     {@link BillingPayInqRs }
     *     
     */
    public BillingPayInqRs getBillingPayInqRs() {
        return billingPayInqRs;
    }

    /**
     * Sets the value of the billingPayInqRs property.
     * 
     * @param value
     *     allowed object is
     *     {@link BillingPayInqRs }
     *     
     */
    public void setBillingPayInqRs(BillingPayInqRs value) {
        this.billingPayInqRs = value;
    }

    /**
     * Gets the value of the xferAddRs property.
     * 
     * @return
     *     possible object is
     *     {@link XferAddRs }
     *     
     */
    public XferAddRs getXferAddRs() {
        return xferAddRs;
    }

    /**
     * Sets the value of the xferAddRs property.
     * 
     * @param value
     *     allowed object is
     *     {@link XferAddRs }
     *     
     */
    public void setXferAddRs(XferAddRs value) {
        this.xferAddRs = value;
    }

    /**
     * Gets the value of the depoRevokeDocRs property.
     * 
     * @return
     *     possible object is
     *     {@link DepoRevokeDocRs }
     *     
     */
    public DepoRevokeDocRs getDepoRevokeDocRs() {
        return depoRevokeDocRs;
    }

    /**
     * Sets the value of the depoRevokeDocRs property.
     * 
     * @param value
     *     allowed object is
     *     {@link DepoRevokeDocRs }
     *     
     */
    public void setDepoRevokeDocRs(DepoRevokeDocRs value) {
        this.depoRevokeDocRs = value;
    }

    /**
     * Gets the value of the depoArRs property.
     * 
     * @return
     *     possible object is
     *     {@link DepoArRs }
     *     
     */
    public DepoArRs getDepoArRs() {
        return depoArRs;
    }

    /**
     * Sets the value of the depoArRs property.
     * 
     * @param value
     *     allowed object is
     *     {@link DepoArRs }
     *     
     */
    public void setDepoArRs(DepoArRs value) {
        this.depoArRs = value;
    }

    /**
     * Gets the value of the depoAccSecRegRs property.
     * 
     * @return
     *     possible object is
     *     {@link DepoAccSecRegRs }
     *     
     */
    public DepoAccSecRegRs getDepoAccSecRegRs() {
        return depoAccSecRegRs;
    }

    /**
     * Sets the value of the depoAccSecRegRs property.
     * 
     * @param value
     *     allowed object is
     *     {@link DepoAccSecRegRs }
     *     
     */
    public void setDepoAccSecRegRs(DepoAccSecRegRs value) {
        this.depoAccSecRegRs = value;
    }

    /**
     * Gets the value of the messageRecvRs property.
     * 
     * @return
     *     possible object is
     *     {@link MessageRecvRs }
     *     
     */
    public MessageRecvRs getMessageRecvRs() {
        return messageRecvRs;
    }

    /**
     * Sets the value of the messageRecvRs property.
     * 
     * @param value
     *     allowed object is
     *     {@link MessageRecvRs }
     *     
     */
    public void setMessageRecvRs(MessageRecvRs value) {
        this.messageRecvRs = value;
    }

    /**
     * Gets the value of the depoAccTranRs property.
     * 
     * @return
     *     possible object is
     *     {@link DepoAccTranRs }
     *     
     */
    public DepoAccTranRs getDepoAccTranRs() {
        return depoAccTranRs;
    }

    /**
     * Sets the value of the depoAccTranRs property.
     * 
     * @param value
     *     allowed object is
     *     {@link DepoAccTranRs }
     *     
     */
    public void setDepoAccTranRs(DepoAccTranRs value) {
        this.depoAccTranRs = value;
    }

    /**
     * Gets the value of the depoAccSecInfoRs property.
     * 
     * @return
     *     possible object is
     *     {@link DepoAccSecInfoRs }
     *     
     */
    public DepoAccSecInfoRs getDepoAccSecInfoRs() {
        return depoAccSecInfoRs;
    }

    /**
     * Sets the value of the depoAccSecInfoRs property.
     * 
     * @param value
     *     allowed object is
     *     {@link DepoAccSecInfoRs }
     *     
     */
    public void setDepoAccSecInfoRs(DepoAccSecInfoRs value) {
        this.depoAccSecInfoRs = value;
    }

    /**
     * Gets the value of the depoDeptCardPayRs property.
     * 
     * @return
     *     possible object is
     *     {@link DepoDeptCardPayRs }
     *     
     */
    public DepoDeptCardPayRs getDepoDeptCardPayRs() {
        return depoDeptCardPayRs;
    }

    /**
     * Sets the value of the depoDeptCardPayRs property.
     * 
     * @param value
     *     allowed object is
     *     {@link DepoDeptCardPayRs }
     *     
     */
    public void setDepoDeptCardPayRs(DepoDeptCardPayRs value) {
        this.depoDeptCardPayRs = value;
    }

    /**
     * Gets the value of the depoDeptDetInfoRs property.
     * 
     * @return
     *     possible object is
     *     {@link DepoDeptDetInfoRs }
     *     
     */
    public DepoDeptDetInfoRs getDepoDeptDetInfoRs() {
        return depoDeptDetInfoRs;
    }

    /**
     * Sets the value of the depoDeptDetInfoRs property.
     * 
     * @param value
     *     allowed object is
     *     {@link DepoDeptDetInfoRs }
     *     
     */
    public void setDepoDeptDetInfoRs(DepoDeptDetInfoRs value) {
        this.depoDeptDetInfoRs = value;
    }

    /**
     * Gets the value of the depoDeptsInfoRs property.
     * 
     * @return
     *     possible object is
     *     {@link DepoDeptsInfoRs }
     *     
     */
    public DepoDeptsInfoRs getDepoDeptsInfoRs() {
        return depoDeptsInfoRs;
    }

    /**
     * Sets the value of the depoDeptsInfoRs property.
     * 
     * @param value
     *     allowed object is
     *     {@link DepoDeptsInfoRs }
     *     
     */
    public void setDepoDeptsInfoRs(DepoDeptsInfoRs value) {
        this.depoDeptsInfoRs = value;
    }

    /**
     * Gets the value of the depoAccInfoRs property.
     * 
     * @return
     *     possible object is
     *     {@link DepoAccInfoRs }
     *     
     */
    public DepoAccInfoRs getDepoAccInfoRs() {
        return depoAccInfoRs;
    }

    /**
     * Sets the value of the depoAccInfoRs property.
     * 
     * @param value
     *     allowed object is
     *     {@link DepoAccInfoRs }
     *     
     */
    public void setDepoAccInfoRs(DepoAccInfoRs value) {
        this.depoAccInfoRs = value;
    }

    /**
     * Gets the value of the depoClientRegRs property.
     * 
     * @return
     *     possible object is
     *     {@link DepoClientRegRs }
     *     
     */
    public DepoClientRegRs getDepoClientRegRs() {
        return depoClientRegRs;
    }

    /**
     * Sets the value of the depoClientRegRs property.
     * 
     * @param value
     *     allowed object is
     *     {@link DepoClientRegRs }
     *     
     */
    public void setDepoClientRegRs(DepoClientRegRs value) {
        this.depoClientRegRs = value;
    }

    /**
     * Gets the value of the loanPaymentRs property.
     * 
     * @return
     *     possible object is
     *     {@link LoanPaymentRs }
     *     
     */
    public LoanPaymentRs getLoanPaymentRs() {
        return loanPaymentRs;
    }

    /**
     * Sets the value of the loanPaymentRs property.
     * 
     * @param value
     *     allowed object is
     *     {@link LoanPaymentRs }
     *     
     */
    public void setLoanPaymentRs(LoanPaymentRs value) {
        this.loanPaymentRs = value;
    }

    /**
     * Gets the value of the loanInqRs property.
     * 
     * @return
     *     possible object is
     *     {@link LoanInqRs }
     *     
     */
    public LoanInqRs getLoanInqRs() {
        return loanInqRs;
    }

    /**
     * Sets the value of the loanInqRs property.
     * 
     * @param value
     *     allowed object is
     *     {@link LoanInqRs }
     *     
     */
    public void setLoanInqRs(LoanInqRs value) {
        this.loanInqRs = value;
    }

    /**
     * Gets the value of the ccAcctFullStmtInqRs property.
     * 
     * @return
     *     possible object is
     *     {@link CCAcctFullStmtInqRs }
     *     
     */
    public CCAcctFullStmtInqRs getCCAcctFullStmtInqRs() {
        return ccAcctFullStmtInqRs;
    }

    /**
     * Sets the value of the ccAcctFullStmtInqRs property.
     * 
     * @param value
     *     allowed object is
     *     {@link CCAcctFullStmtInqRs }
     *     
     */
    public void setCCAcctFullStmtInqRs(CCAcctFullStmtInqRs value) {
        this.ccAcctFullStmtInqRs = value;
    }

    /**
     * Gets the value of the ccAcctExtStmtInqRs property.
     * 
     * @return
     *     possible object is
     *     {@link CCAcctExtStmtInqRs }
     *     
     */
    public CCAcctExtStmtInqRs getCCAcctExtStmtInqRs() {
        return ccAcctExtStmtInqRs;
    }

    /**
     * Sets the value of the ccAcctExtStmtInqRs property.
     * 
     * @param value
     *     allowed object is
     *     {@link CCAcctExtStmtInqRs }
     *     
     */
    public void setCCAcctExtStmtInqRs(CCAcctExtStmtInqRs value) {
        this.ccAcctExtStmtInqRs = value;
    }

    /**
     * Gets the value of the cardAdditionalInfoRs property.
     * 
     * @return
     *     possible object is
     *     {@link CardAdditionalInfoRs }
     *     
     */
    public CardAdditionalInfoRs getCardAdditionalInfoRs() {
        return cardAdditionalInfoRs;
    }

    /**
     * Sets the value of the cardAdditionalInfoRs property.
     * 
     * @param value
     *     allowed object is
     *     {@link CardAdditionalInfoRs }
     *     
     */
    public void setCardAdditionalInfoRs(CardAdditionalInfoRs value) {
        this.cardAdditionalInfoRs = value;
    }

    /**
     * Gets the value of the bankAcctStmtImgInqRs property.
     * 
     * @return
     *     possible object is
     *     {@link BankAcctStmtImgInqRs }
     *     
     */
    public BankAcctStmtImgInqRs getBankAcctStmtImgInqRs() {
        return bankAcctStmtImgInqRs;
    }

    /**
     * Sets the value of the bankAcctStmtImgInqRs property.
     * 
     * @param value
     *     allowed object is
     *     {@link BankAcctStmtImgInqRs }
     *     
     */
    public void setBankAcctStmtImgInqRs(BankAcctStmtImgInqRs value) {
        this.bankAcctStmtImgInqRs = value;
    }

    /**
     * Gets the value of the cardReissuePlaceRs property.
     * 
     * @return
     *     possible object is
     *     {@link CardReissuePlaceRs }
     *     
     */
    public CardReissuePlaceRs getCardReissuePlaceRs() {
        return cardReissuePlaceRs;
    }

    /**
     * Sets the value of the cardReissuePlaceRs property.
     * 
     * @param value
     *     allowed object is
     *     {@link CardReissuePlaceRs }
     *     
     */
    public void setCardReissuePlaceRs(CardReissuePlaceRs value) {
        this.cardReissuePlaceRs = value;
    }

    /**
     * Gets the value of the cardAcctDInqRs property.
     * 
     * @return
     *     possible object is
     *     {@link CardAcctDInqRs }
     *     
     */
    public CardAcctDInqRs getCardAcctDInqRs() {
        return cardAcctDInqRs;
    }

    /**
     * Sets the value of the cardAcctDInqRs property.
     * 
     * @param value
     *     allowed object is
     *     {@link CardAcctDInqRs }
     *     
     */
    public void setCardAcctDInqRs(CardAcctDInqRs value) {
        this.cardAcctDInqRs = value;
    }

    /**
     * Gets the value of the bankAcctFullStmtInqRs property.
     * 
     * @return
     *     possible object is
     *     {@link BankAcctFullStmtInqRs }
     *     
     */
    public BankAcctFullStmtInqRs getBankAcctFullStmtInqRs() {
        return bankAcctFullStmtInqRs;
    }

    /**
     * Sets the value of the bankAcctFullStmtInqRs property.
     * 
     * @param value
     *     allowed object is
     *     {@link BankAcctFullStmtInqRs }
     *     
     */
    public void setBankAcctFullStmtInqRs(BankAcctFullStmtInqRs value) {
        this.bankAcctFullStmtInqRs = value;
    }

    /**
     * Gets the value of the bankAcctStmtInqRs property.
     * 
     * @return
     *     possible object is
     *     {@link BankAcctStmtInqRs }
     *     
     */
    public BankAcctStmtInqRs getBankAcctStmtInqRs() {
        return bankAcctStmtInqRs;
    }

    /**
     * Sets the value of the bankAcctStmtInqRs property.
     * 
     * @param value
     *     allowed object is
     *     {@link BankAcctStmtInqRs }
     *     
     */
    public void setBankAcctStmtInqRs(BankAcctStmtInqRs value) {
        this.bankAcctStmtInqRs = value;
    }

    /**
     * Gets the value of the imaAcctInRs property.
     * 
     * @return
     *     possible object is
     *     {@link ImaAcctInRs }
     *     
     */
    public ImaAcctInRs getImaAcctInRs() {
        return imaAcctInRs;
    }

    /**
     * Sets the value of the imaAcctInRs property.
     * 
     * @param value
     *     allowed object is
     *     {@link ImaAcctInRs }
     *     
     */
    public void setImaAcctInRs(ImaAcctInRs value) {
        this.imaAcctInRs = value;
    }

    /**
     * Gets the value of the depAcctExtRs property.
     * 
     * @return
     *     possible object is
     *     {@link DepAcctExtRs }
     *     
     */
    public DepAcctExtRs getDepAcctExtRs() {
        return depAcctExtRs;
    }

    /**
     * Sets the value of the depAcctExtRs property.
     * 
     * @param value
     *     allowed object is
     *     {@link DepAcctExtRs }
     *     
     */
    public void setDepAcctExtRs(DepAcctExtRs value) {
        this.depAcctExtRs = value;
    }

    /**
     * Gets the value of the acctInfoRs property.
     * 
     * @return
     *     possible object is
     *     {@link AcctInfoRs }
     *     
     */
    public AcctInfoRs getAcctInfoRs() {
        return acctInfoRs;
    }

    /**
     * Sets the value of the acctInfoRs property.
     * 
     * @param value
     *     allowed object is
     *     {@link AcctInfoRs }
     *     
     */
    public void setAcctInfoRs(AcctInfoRs value) {
        this.acctInfoRs = value;
    }

    /**
     * Gets the value of the acctInqRs property.
     * 
     * @return
     *     possible object is
     *     {@link AcctInqRs }
     *     
     */
    public AcctInqRs getAcctInqRs() {
        return acctInqRs;
    }

    /**
     * Sets the value of the acctInqRs property.
     * 
     * @param value
     *     allowed object is
     *     {@link AcctInqRs }
     *     
     */
    public void setAcctInqRs(AcctInqRs value) {
        this.acctInqRs = value;
    }

    /**
     * Gets the value of the bankAcctInqRs property.
     * 
     * @return
     *     possible object is
     *     {@link BankAcctInqRs }
     *     
     */
    public BankAcctInqRs getBankAcctInqRs() {
        return bankAcctInqRs;
    }

    /**
     * Sets the value of the bankAcctInqRs property.
     * 
     * @param value
     *     allowed object is
     *     {@link BankAcctInqRs }
     *     
     */
    public void setBankAcctInqRs(BankAcctInqRs value) {
        this.bankAcctInqRs = value;
    }

    /**
     * Gets the value of the custInqRs property.
     * 
     * @return
     *     possible object is
     *     {@link CustInqRs }
     *     
     */
    public CustInqRs getCustInqRs() {
        return custInqRs;
    }

    /**
     * Sets the value of the custInqRs property.
     * 
     * @param value
     *     allowed object is
     *     {@link CustInqRs }
     *     
     */
    public void setCustInqRs(CustInqRs value) {
        this.custInqRs = value;
    }

}
