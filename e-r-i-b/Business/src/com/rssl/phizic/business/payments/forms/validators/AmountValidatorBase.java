package com.rssl.phizic.business.payments.forms.validators;

import com.rssl.common.forms.TemporalDocumentException;
import com.rssl.common.forms.validators.MultiFieldsValidatorBase;
import com.rssl.phizic.business.resources.external.AccountLink;
import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.business.resources.external.ExternalResourceLink;
import com.rssl.phizic.business.resources.external.AbstractStoredResource;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.common.types.exceptions.IKFLException;
import com.rssl.phizic.gate.bankroll.Account;
import com.rssl.phizic.gate.bankroll.Card;
import com.rssl.phizic.utils.MaskUtil;
import com.rssl.phizic.utils.MockHelper;

import java.math.BigDecimal;
import java.util.Map;

/**
 * Базовый класс валидаторов остатков.
 * Происходит проверка, что проверяемая сумма(getCheckedAmount) не меньше, чем допустимая(getExpectedAmount)
 * @author krenev
 * @ created 21.04.2010
 * @ $Author$
 * @ $Revision$
 * проверяемт
 */
public abstract class AmountValidatorBase extends MultiFieldsValidatorBase
{
	public static final String FIELD_AMOUNT = "amount";
	public static final String AMOUNT_MAY_BE_NULL = "amountMayBeNull";

	/**
	 * @param values значения полей формы.
	 * @return Проверяемая сумма. Например остаток на счете, карте, вкладе.
	 */
	protected abstract BigDecimal getCheckedAmount(Map values) throws TemporalDocumentException;

	public boolean validate(Map values) throws TemporalDocumentException
	{
		try
		{
			BigDecimal accountAmount = getCheckedAmount(values);
			if (accountAmount == null)
				return false;
			BigDecimal amount = getExpectedAmount(values);
			boolean amountMayBeNull = getAmountMayBeNullValue();

			if (amount == null && amountMayBeNull)
				return true;
			return amount.compareTo(accountAmount) <= 0;
		}
		catch (NullPointerException e)
		{
			throw new RuntimeException("Не задана сумма (amount=null)", e);
		}
	}

	protected boolean getAmountMayBeNullValue()
	{
		String value = getParameter(AMOUNT_MAY_BE_NULL);
		return !(value == null || value.equals("")) && Boolean.parseBoolean(value);
	}

	/**
	 * @param values значения полей формы.
	 * @return Допустимая сумма. Например введенная пользователем сумма.
	 */
	protected BigDecimal getExpectedAmount(Map values)
	{
		Object amount = retrieveFieldValue(FIELD_AMOUNT, values);
		try
		{
			return (BigDecimal) retrieveFieldValue(FIELD_AMOUNT, values);
		}
		catch(ClassCastException e)
		{
			// предполагаем, что пришла строка (возможно в случае ОЗОН/ФНС), если нет, то все равно упадем
			return new BigDecimal((String) amount);
		}
	}

	protected BigDecimal getCardAmount(CardLink cardLink) throws TemporalDocumentException
	{
		Card card = getCard(cardLink);
		Money limit = card.getAvailableLimit();
		if(limit!=null)
			return limit.getDecimal();
		else
			return null;
	}

	protected BigDecimal getAccountAmount(AccountLink accountLink) throws TemporalDocumentException
	{
		if (accountLink == null)
		{
			return null;
		}

		Account account = accountLink.getAccount();
		if(MockHelper.isMockObject(account))
			throw new TemporalDocumentException("Ошибка при получении информации по счету №"+accountLink.getNumber());

		return account.getBalance().getDecimal();
	}

	protected Card getCard(CardLink cardLink) throws TemporalDocumentException
	{
		if (cardLink == null)
		{
			return null;
		}
		Card card = cardLink.getCard();

		if(MockHelper.isMockObject(card))
			throw new TemporalDocumentException("Ошибка при получении информации по карте №"+ MaskUtil.getCutCardNumber(cardLink.getNumber()));

		return card;
	}

	protected boolean isStoredResource(ExternalResourceLink resourceLink) throws TemporalDocumentException
	{
		if (resourceLink == null)
			return false;

		try
		{
			if (resourceLink.getValue() instanceof AbstractStoredResource)
				return true;
		}
		catch (IKFLException e)
		{
			throw new TemporalDocumentException(e);
		}

		return false;
	}
}
