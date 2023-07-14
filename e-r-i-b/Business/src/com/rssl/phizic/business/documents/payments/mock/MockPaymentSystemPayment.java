package com.rssl.phizic.business.documents.payments.mock;

import com.rssl.common.forms.DocumentException;
import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.gate.dictionaries.ResidentBank;
import com.rssl.phizic.gate.dictionaries.officies.Code;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.payments.owner.EmployeeInfo;
import com.rssl.phizic.gate.payments.systems.AccountPaymentSystemPayment;
import com.rssl.phizic.gate.payments.systems.CardPaymentSystemPayment;
import com.rssl.phizic.gate.payments.systems.recipients.Field;
import com.rssl.phizic.gate.payments.systems.recipients.Service;

import java.util.Calendar;
import java.util.List;

/**
 * @author osminin
 * @ created 02.11.2010
 * @ $Author$
 * @ $Revision$
 */
public class MockPaymentSystemPayment extends MockAbstractJurTransfer implements CardPaymentSystemPayment, AccountPaymentSystemPayment
{
	private String chargeOffAccount = EMPTY_STRING;
	private String chargeOffCard = EMPTY_STRING;
	private String receiverTransitAccount = EMPTY_STRING;
	private String receiverPhone = EMPTY_STRING;
	private String receiverNameForBill = EMPTY_STRING;
	private String chargeOffCardDescription = EMPTY_STRING;

	/**
	 * @return код биллинга, в который отправляется платеж
	 */
	public String getBillingCode()
	{
		return null;
	}

	/**
	 * Установить код биллинга
	 * @param billingCode код биллинга
	 */
	public void setBillingCode(String billingCode)
	{
	}

	public String getReceiverPointCode()
	{
		return null;
	}

	public String getMultiBlockReceiverPointCode()
	{
		return null;
	}

	public List<Field> getExtendedFields() throws DocumentException
	{
		return null;
	}

	public String getReceiverTransitAccount()
	{
		return receiverTransitAccount;
	}

	public void setReceiverTransitAccount(String receiverTransitAccount)
	{
		this.receiverTransitAccount = receiverTransitAccount;
	}

	public ResidentBank getReceiverTransitBank()
	{
		return new ResidentBank();
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
		return false;
	}

	public void setNotVisibleBankDetails(boolean notVisibleBankDetails)
	{

	}

	public Code getReceiverOfficeCode()
	{
		return null;
	}

	public void setReceiverOfficeCode(Code code)
	{

	}

	public void setExtendedFields(List<Field> extendedFields)
	{
	}

	public void setRegisterString(String registerString)
	{
		//To change body of implemented methods use File | Settings | File Templates.
	}

	public String getIdFromPaymentSystem()
	{
		return null;
	}

	public void setIdFromPaymentSystem(String id)
	{

	}

	public String getBillingClientId()
	{
		return null;
	}

	public Service getService()
	{
		return null;
	}


	public void setService(Service service)
	{
		
	}

	public void setReceiverPointCode(String receiverPointCode)
	{

	}

	public String getSalesCheck()
	{
		return null;
	}

	public void setSalesCheck(String salesCheck)
	{

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

	public Currency getChargeOffCurrency() throws GateException
	{
		return null;
	}

	public void setChargeOffCard(String chargeOffCard)
	{
		this.chargeOffCard = chargeOffCard;
	}

	public Calendar getChargeOffCardExpireDate()
	{
		return null;
	}

	public void setAuthorizeCode(String authorizeCode)
	{

	}

	public String getAuthorizeCode()
	{
		return null;
	}

	public Calendar getAuthorizeDate()
	{
		return null;
	}

	public void setAuthorizeDate(Calendar authorizeDate)
	{
	}

	public String getChargeOffCardDescription()
	{
		return chargeOffCardDescription;
	}

	public String getOperationUID()
	{
		return null;
	}

	public void setOperationUID(String operationUID)
	{
	}

	public boolean isConnectChargeOffResourceToMobileBank()
	{
		return false;
	}

	public EmployeeInfo getCreatedEmployeeInfo()
	{
		return null;
	}

	public EmployeeInfo getConfirmedEmployeeInfo()
	{
		return null;
	}

	public boolean isEinvoicing()
	{
		return false;
	}
}
