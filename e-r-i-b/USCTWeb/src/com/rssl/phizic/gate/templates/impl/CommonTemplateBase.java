package com.rssl.phizic.gate.templates.impl;

import com.rssl.common.forms.DocumentException;
import com.rssl.phizic.common.types.documents.State;
import com.rssl.phizic.gate.depo.DeliveryType;
import com.rssl.phizic.gate.depo.TransferOperation;
import com.rssl.phizic.gate.depo.TransferSubOperation;
import com.rssl.phizic.gate.dictionaries.ResidentBank;
import com.rssl.phizic.gate.dictionaries.officies.Code;
import com.rssl.phizic.gate.payments.ReceiverCardType;
import com.rssl.phizic.gate.payments.systems.recipients.Field;
import com.rssl.phizic.gate.payments.systems.recipients.Service;

import java.util.Calendar;
import java.util.List;

/**
 * Базовый класс шаблона документа.
 *
 * @author khudyakov
 * @ created 28.04.14
 * @ $Author$
 * @ $Revision$
 */
public abstract class CommonTemplateBase extends TransferTemplateBase
{
	public void setReceiverTransitAccount(String receiverTransitAccount)
	{

	}

	public void setReceiverINN(String receiverINN)
	{

	}

	public void setReceiverKPP(String receiverKPP)
	{

	}

	public void setReceiverBank(ResidentBank receiverBank)
	{

	}

	public ResidentBank getReceiverTransitBank()
	{
		return null;
	}

	public String getReceiverPhone()
	{
		return null;
	}

	public void setReceiverPhone(String receiverPhone)
	{

	}

	//////////////////////////////////////////////////////////
	//оплата с переменным количеством полей

	public String getBillingCode()
	{
		return null;
	}

	public void setBillingCode(String billingCode)
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

	public List<Field> getExtendedFields() throws DocumentException
	{
		return null;
	}

	public String getReceiverTransitAccount()
	{
		return null;
	}

	public String getReceiverNameForBill()
	{
		return null;
	}

	public void setReceiverNameForBill(String receiverNameForBill)
	{

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

	public void setExtendedFields(List<Field> extendedFields) throws DocumentException
	{

	}

	public String getIdFromPaymentSystem()
	{
		return null;
	}

	public void setIdFromPaymentSystem(String id)
	{

	}

	public void setReceiverPointCode(String receiverPointCode)
	{

	}

	public String getSalesCheck()
	{
		//не используется в ЕСУШ
		return null;
	}

	public void setSalesCheck(String salesCheck)
	{
		//не используется в ЕСУШ
	}

	public String getReceiverPointCode()
	{
		return null;
	}

	public Long getReceiverInternalId()
	{
		return null;
	}

	/**
	 * @return межблочный идентификатор поставщика услуг
	 */
	public String getMultiBlockReceiverPointCode()
	{
		return null;
	}

	public void setReceiverName(String receiverName)
	{

	}

	public String getReceiverKPP()
	{
		return null;
	}

	public void setRegisterNumber(String registerNumber)
	{
		//не используется в ЕСУШ
	}

	public void setRegisterString(String registerString)
	{
		//не используется в ЕСУШ
	}

	public String getReceiverSurName()
	{
		return null;
	}

	public String getReceiverFirstName()
	{
		return null;
	}

	public String getReceiverPatrName()
	{
		return null;
	}

	public String getReceiverName()
	{
		return null;
	}

	public String getReceiverINN()
	{
		return null;
	}

	public ResidentBank getReceiverBank()
	{
		return null;
	}

	//////////////////////////////////////////////////////////
	// кредиты

	public String getLoanExternalId()
	{
		return null;
	}

	public String getAccountNumber()
	{
		return null;
	}

	public String getAgreementNumber()
	{
		return null;
	}

	public String getIdSpacing()
	{
		return null;
	}

	public Calendar getSpacingDate()
	{
		return null;
	}

	//////////////////////////////////////////////////////////
	//ценные бумаги

	public TransferOperation getOperType()
	{
		return null;
	}

	public TransferSubOperation getOperationSubType()
	{
		return null;
	}

	public String getOperationDesc()
	{
		return null;
	}

	public String getDivisionType()
	{
		return null;
	}

	public String getDivisionNumber()
	{
		return null;
	}

	public String getInsideCode()
	{
		return null;
	}

	public Long getSecurityCount()
	{
		return null;
	}

	public String getOperationReason()
	{
		return null;
	}

	public String getCorrDepositary()
	{
		return null;
	}

	public String getCorrDepoAccount()
	{
		return null;
	}

	public String getCorrDepoAccountOwner()
	{
		return null;
	}

	public String getAdditionalInfo()
	{
		return null;
	}

	public DeliveryType getDeliveryType()
	{
		return null;
	}

	public String getRegistrationNumber()
	{
		return null;
	}

	public String getDepoExternalId()
	{
		return null;
	}

	public String getDepoAccountNumber()
	{
		return null;
	}

	public boolean isEinvoicing()
	{
		return false;
	}
}
