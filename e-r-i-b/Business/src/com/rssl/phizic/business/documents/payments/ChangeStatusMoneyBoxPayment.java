package com.rssl.phizic.business.documents.payments;

import com.rssl.phizic.auth.modes.UserVisitingMode;
import com.rssl.phizic.business.documents.InternalTransfer;
import com.rssl.phizic.common.types.Application;
import com.rssl.phizic.common.types.ApplicationInfo;
import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.longoffer.SumType;
import com.rssl.phizic.gate.payments.autosubscriptions.AutoSubscriptionClaim;

import java.math.BigDecimal;
import java.util.Calendar;

/**
 * @author tisov
 * @ created 03.10.14
 * @ $Author$
 * @ $Revision$
 * «а€вка на смену статуса копилки
 */

public abstract class ChangeStatusMoneyBoxPayment extends InternalTransfer implements AutoSubscriptionClaim
{

	private static final String MONEY_BOX_NAME_ATTRIBUTE = "money-box-name";       //им€ копилки
	private static final String MONEY_BOX_TYPE_ATTRIBUTE = "money-box-type";       //тип копилки
	private static final String PAYER_RESOURCE_ATTRIBUTE = "payer-resource";       //источник списани€
	private static final String RECEIVER_RESOURCE_ATTRIBUTE = "receiver-resource"; //цель зачислени€
	private static final String CURRENCY_ATTRIBUTE_POSTFIX = "-currency";          //суффикс дл€ атрибутов валюты

	public Calendar getUpdateDate()
	{
		return null;
	}

	public String getMessageToRecipient()
	{
		return null;
	}

	public boolean isSameTB() throws GateException, GateLogicException
	{
		return true;
	}

	public void setMoneyBoxName(String name)
	{
		setNullSaveAttributeStringValue(MONEY_BOX_NAME_ATTRIBUTE, name);
	}

	public void setMoneyBoxType(String type)
	{
		setNullSaveAttributeStringValue(MONEY_BOX_TYPE_ATTRIBUTE, type);
	}

	public SumType getSumType()
	{
		return getNullSaveAttributeEnumValue(SumType.class, MONEY_BOX_TYPE_ATTRIBUTE);
	}

	public void setPercent(BigDecimal percent)
	{
		setNullSaveAttributeDecimalValue(LONG_OFFER_PERCENT_ATTRIBUTE_NAME, percent);
	}


	public boolean isLongOffer()
	{
		return true;
	}

	public void setFromAccountName(String name)
	{
		setNullSaveAttributeStringValue(CHARGE_OFF_RESOURCE_NAME_ATTRIBUTE_NAME, name);
	}

	public void setFromAccountCurrency(Currency cur)
	{
		setNullSaveAttributeStringValue(PAYER_RESOURCE_ATTRIBUTE + CURRENCY_ATTRIBUTE_POSTFIX, cur.getCode());
	}

	public void setToAccountCurrency(Currency cur)
	{
		setNullSaveAttributeStringValue(RECEIVER_RESOURCE_ATTRIBUTE + CURRENCY_ATTRIBUTE_POSTFIX, cur.getCode());
	}

	public boolean isNeedConfirmation()
	{
		//если за€вку создаем из админки-клиент должен подтвердить отдельно.
		return ApplicationInfo.getCurrentApplication().equals(Application.PhizIA) && PersonContext.isAvailable() && PersonContext.getPersonDataProvider().getPersonData().getPerson().getLogin().getLastUserVisitingMode() == UserVisitingMode.EMPLOYEE_INPUT_BY_IDENTITY_DOCUMENT;
	}

	public String getAutopayNumber()
	{
		return null;
	}

	public void setAutopayNumber(String number) {}
}
