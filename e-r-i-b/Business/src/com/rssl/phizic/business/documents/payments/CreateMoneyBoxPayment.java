package com.rssl.phizic.business.documents.payments;

import com.rssl.phizic.auth.modes.UserVisitingMode;
import com.rssl.phizic.business.documents.InternalTransfer;
import com.rssl.phizic.business.documents.ResourceType;
import com.rssl.phizic.common.types.Application;
import com.rssl.phizic.common.types.ApplicationInfo;
import com.rssl.phizic.common.types.LongOfferPayDay;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.common.types.documents.FormType;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.longoffer.SumType;
import com.rssl.phizic.gate.payments.autosubscriptions.AutoSubscriptionClaim;
import com.rssl.phizic.gate.payments.longoffer.CreateCardToAccountLongOffer;
import com.rssl.phizic.utils.StringHelper;

import java.math.BigDecimal;
import java.util.Calendar;

/**
 * @author vagin
 * @ created 02.09.14
 * @ $Author$
 * @ $Revision$
 * Создание заявки на копилку.
 */
public class CreateMoneyBoxPayment extends InternalTransfer implements AutoSubscriptionClaim
{
	protected static final String SUM_TYPE_FIELD_NAME = "money-box-sum-type";
	protected static final String MONEY_BOX_NAME_FIELD_NAME = "money-box-name";
	protected static final String AUTOPAY_NUMBER_ATTRIBUTE_NAME = "autoPay-number";

	public Class<? extends GateDocument> getType()
	{
		return CreateCardToAccountLongOffer.class;
	}

	public SumType getSumType()
	{
		String fieldValue = getNullSaveAttributeStringValue(SUM_TYPE_FIELD_NAME);
		if (StringHelper.isNotEmpty(fieldValue))
			return SumType.valueOf(fieldValue);
		return null;
	}

	public Calendar getNextPayDate()
	{
		return getNullSaveAttributeDateTimeValue(LONG_OFFER_START_DATE_ATTRIBUTE_NAME);
	}

	public BigDecimal getPercent()
	{
		Object percentValue = getNullSaveAttributeValue(LONG_OFFER_PERCENT_ATTRIBUTE_NAME);
		if (percentValue != null)
			return BigDecimal.valueOf((Integer) percentValue);
		return null;
	}

	public Calendar getUpdateDate()
	{
		return null;
	}

	public String getMessageToRecipient()
	{
		return null;
	}

	public boolean isSameTB()
	{
		return true;
	}

	public boolean isLongOffer()
	{
		return true;
	}

	public boolean isNeedConfirmation()
	{
		//если заявку создаем из админки-клиент должен подтвердить отдельно.
		return ApplicationInfo.getCurrentApplication().equals(Application.PhizIA) && PersonContext.isAvailable() && PersonContext.getPersonDataProvider().getPersonData().getPerson().getLogin().getLastUserVisitingMode() == UserVisitingMode.EMPLOYEE_INPUT_BY_IDENTITY_DOCUMENT;
	}

	public String getFriendlyName()
	{
		return getNullSaveAttributeStringValue(MONEY_BOX_NAME_FIELD_NAME);
	}

	public String getAccountNumber()
	{
		return getReceiverAccount();
	}

	public LongOfferPayDay getLongOfferPayDay()
	{
		LongOfferPayDay payDay;
		Calendar startDate = getStartDate();
		switch (getExecutionEventType())
		{
			case ONCE_IN_WEEK:
				payDay = new LongOfferPayDay(0, 0, 0, startDate.get(Calendar.DAY_OF_WEEK));
				break;
			case ONCE_IN_YEAR:
				payDay = new LongOfferPayDay(startDate.get(Calendar.DAY_OF_MONTH), 0, startDate.get(Calendar.MONTH) + 1, 0);
				break;
			case ONCE_IN_QUARTER:
				int monthInQuarter = (startDate.get(Calendar.MONTH) + 1) % 3;
				payDay = new LongOfferPayDay(startDate.get(Calendar.DAY_OF_MONTH), monthInQuarter == 0 ? 3 : monthInQuarter, 0, 0);
				break;
			case ONCE_IN_MONTH:
				payDay = new LongOfferPayDay(startDate.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
				break;
			default:
				throw new IllegalArgumentException("Не поддерживаемый пораметр исполнения копилки: " + getExecutionEventType());
		}
		return payDay;
	}

	public Money getAmount()
	{
		return getChargeOffAmount();
	}

	public FormType getFormType()
	{
		return FormType.CARD_TO_ACCOUNT_AUTO_SUBSCRIPTION_CLAIM;
	}

	public ResourceType getDestinationResourceType()
	{
		return ResourceType.ACCOUNT;
	}

	public String getAutopayNumber()
	{
		return getNullSaveAttributeStringValue(AUTOPAY_NUMBER_ATTRIBUTE_NAME);
	}

	public void setAutopayNumber(String number)
	{
		setNullSaveAttributeStringValue(AUTOPAY_NUMBER_ATTRIBUTE_NAME, number);
	}
}
