package com.rssl.phizicgate.esberibgate.types;

import com.rssl.phizic.gate.dictionaries.ResidentBank;
import com.rssl.phizic.gate.dictionaries.officies.Code;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.longoffer.LongOffer;
import com.rssl.phizic.gate.payments.systems.AccountPaymentSystemPayment;
import com.rssl.phizic.gate.payments.systems.CardPaymentSystemPayment;
import com.rssl.phizic.gate.payments.systems.recipients.Field;
import com.rssl.phizic.gate.payments.systems.recipients.Service;
import com.rssl.phizic.gate.payments.owner.EmployeeInfo;

import java.util.Calendar;
import java.util.List;

/**
 * @author osminin
 * @ created 23.09.2010
 * @ $Author$
 * @ $Revision$
 */
public class PaymentSystemPaymentImpl extends AbstractJurTransferImpl implements CardPaymentSystemPayment, AccountPaymentSystemPayment
{
	private String receiverPointCode;
	private String multiBlockReceiverPointCode;
	private List<Field> extendedFields;
	private String receiverTransitAccount;
	private String idFromPaymentSystem;
	private Service service;
	private String salesCheck;
	private String chargeOffAccount;
	private String chargeOffCard;
	private Calendar chargeOffCardExpireDate;
	private String authorizeCode;
	private Calendar authorizeDate;
	private String billingClientId;
	private String billingCode;
	private ResidentBank receiverTransitBank;
	private String receiverPhone;
	private String receiverNameForBill;
	private String chargeOffCardDescription;
	private boolean notVisibleBankDetails;
	private Code receiverOfficeCode;
	private String operationUID;
	private boolean isConnectChargeOffResourceToMobileBank;
	private EmployeeInfo createdEmployeeInfo;
	private EmployeeInfo confirmedEmployeeInfo;

	public PaymentSystemPaymentImpl()
	{
	}

	public PaymentSystemPaymentImpl(LongOffer longOffer) throws GateException
	{
		super(longOffer);
	}

	public String getReceiverPointCode()
	{
		return receiverPointCode;
	}

	public void setReceiverPointCode(String receiverPointCode)
	{
		this.receiverPointCode = receiverPointCode;
	}

	public String getMultiBlockReceiverPointCode()
	{
		return multiBlockReceiverPointCode;
	}

	public void setMultiBlockReceiverPointCode(String multiBlockReceiverPointCode)
	{
		this.multiBlockReceiverPointCode = multiBlockReceiverPointCode;
	}

	public List<Field>  getExtendedFields()
	{
		return extendedFields;
	}

	public void setExtendedFields(List<Field>  extendedFields)
	{
		this.extendedFields = extendedFields;
	}

	public String getReceiverTransitAccount()
	{
		return receiverTransitAccount;
	}

	public void setReceiverTransitAccount(String receiverTransitAccount)
	{
		this.receiverTransitAccount = receiverTransitAccount;
	}

	public String getIdFromPaymentSystem()
	{
		return idFromPaymentSystem;
	}

	public void setIdFromPaymentSystem(String idFromPaymentSystem)
	{
		this.idFromPaymentSystem = idFromPaymentSystem;
	}

	public Service getService()
	{
		return service;
	}

	public void setService(Service service)
	{
		this.service = service;
	}

	public String getSalesCheck()
	{
		return salesCheck;
	}

	public void setSalesCheck(String salesCheck)
	{
		this.salesCheck = salesCheck;
	}

	public String getChargeOffAccount()
	{
		return chargeOffAccount;
	}

	public void setChargeOffAccount(String chargeOffAccount)
	{
		this.chargeOffAccount = chargeOffAccount;
	}

	public String getChargeOffCard()
	{
		return chargeOffCard;
	}

	public void setChargeOffCard(String chargeOffCard)
	{
		this.chargeOffCard = chargeOffCard;
	}

	public Calendar getChargeOffCardExpireDate()
	{
		return chargeOffCardExpireDate;
	}

	public void setChargeOffCardExpireDate(Calendar chargeOffCardExpireDate)
	{
		this.chargeOffCardExpireDate = chargeOffCardExpireDate;
	}

	public String getChargeOffCardDescription()
	{
		return chargeOffCardDescription;
	}

	public void setChargeOffCardDescription(String chargeOffCardDescription)
	{
		this.chargeOffCardDescription = chargeOffCardDescription;
	}

	public String getAuthorizeCode()
	{
		return authorizeCode;
	}

	public Calendar getAuthorizeDate()
	{
		return authorizeDate;
	}

	public void setAuthorizeCode(String authorizeCode)
	{
		this.authorizeCode = authorizeCode;
	}

	public void setAuthorizeDate(Calendar authorizeDate)
	{
		this.authorizeDate = authorizeDate;
	}

	public String getBillingClientId()
	{
		return billingClientId;
	}

	public void setBillingClientId(String billingClientId)
	{
		this.billingClientId = billingClientId;
	}

	public String getBillingCode()
	{
		return billingCode;
	}

	public void setBillingCode(String billingCode)
	{
		this.billingCode = billingCode;
	}

	public ResidentBank getReceiverTransitBank()
	{
		return receiverTransitBank;
	}

	public void setReceiverTransitBank(ResidentBank receiverTransitBank)
	{
		this.receiverTransitBank = receiverTransitBank;
	}

	public String getReceiverPhone()
	{
		return receiverPhone;
	}

	public void setReceiverPhone(String receiverPhone)
	{
		this.receiverPhone = receiverPhone;
	}

	public String getReceiverNameForBill()
	{
		return receiverNameForBill;
	}

	public void setReceiverNameForBill(String receiverNameForBill)
	{
		this.receiverNameForBill = receiverNameForBill;
	}

	public boolean isNotVisibleBankDetails()
	{
		return notVisibleBankDetails;
	}

	public void setNotVisibleBankDetails(boolean notVisibleBankDetails)
	{
		this.notVisibleBankDetails = notVisibleBankDetails;
	}

	public Code getReceiverOfficeCode()
	{
		return receiverOfficeCode;
	}

	public void setReceiverOfficeCode(Code receiverOfficeCode)
	{
		this.receiverOfficeCode = receiverOfficeCode;
	}

	public String getOperationUID()
	{
		return operationUID;
	}

	public void setOperationUID(String operationUID)
	{
		this.operationUID = operationUID;
	}

	public boolean isConnectChargeOffResourceToMobileBank()
	{
		return isConnectChargeOffResourceToMobileBank;
	}

	public void setConnectChargeOffResourceToMobileBank(boolean connectChargeOffResourceToMobileBank)
	{
		isConnectChargeOffResourceToMobileBank = connectChargeOffResourceToMobileBank;
	}

	public boolean isEinvoicing()
	{
		return false;
	}
}
