package com.rssl.phizic.business.payments.forms.validators;

import com.rssl.common.forms.TemporalDocumentException;
import com.rssl.common.forms.validators.MultiFieldsValidator;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.resources.external.AccountLink;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.context.PersonData;
import com.rssl.phizic.gate.bankroll.Account;
import com.rssl.phizic.utils.MockHelper;

import java.math.BigDecimal;
import java.util.Map;

/**
 * Проверяем, что сумма не превышает максимальную сумму списания.
 * @author osminin
 * @ created 08.12.2008
 * @ $Author$
 * @ $Revision$
 */

public class AccountMaxSumWriteValidator extends AmountValidatorBase implements MultiFieldsValidator
{
	public static final String FIELD_ACCOUNT = "account";
	public static final String FIELD_RESOURCE_CURRENCY = "resourceCurrency";

	/*
	 * Переопределён поскольку сумма для снятия может быть равна максимальной сумме снятия со счёта.
	 */
	@Override
	public boolean validate(Map values) throws TemporalDocumentException
	{
		if (isStoredResource(getAccountLink(values)))
			return true;
		
		boolean isValid = super.validate(values);

		if ( !isValid )
		{
			AccountLink accountLink = getAccountLink(values);
			if (accountLink == null)
				return false;

			Account account = accountLink.getAccount();
			if (MockHelper.isMockObject(account))
				return false;

			BigDecimal maxSum = getCheckedAmount(values);
			BigDecimal amount = getExpectedAmount(values);

			boolean valid = maxSum == null || amount.compareTo(maxSum) <= 0;
			if (!valid)
			{
				String binding = getBinding(FIELD_RESOURCE_CURRENCY);
				if (binding != null)
				{
					StringBuffer message = new StringBuffer(getMessage());
					message.append(" (");
					message.append(maxSum);
					message.append(" ");
					message.append(getCurrencyISOCode(binding, values));
					message.append(")");

					setMessage( message.toString() );
				}
			}
			return valid;
		}
		return isValid;
	}

	public String getMessage()
	{
		String message = super.getMessage();

		int firstOccurrence = message.indexOf('(');
		int lastOccurrence  = message.lastIndexOf('(');

		if(firstOccurrence == lastOccurrence)
		{
			return message;
		}
		else
		{
			StringBuilder builder = new StringBuilder(message);
			builder.delete(firstOccurrence, lastOccurrence);
			return builder.toString();
		}
	}

	protected BigDecimal getCheckedAmount(Map values) throws TemporalDocumentException
	{
		AccountLink accountLink = getAccountLink(values);
		if (accountLink == null)
			return null;

		Account account = accountLink.getAccount();
		if(MockHelper.isMockObject(account))
		        return null;
		Money maxSum = account.getMaxSumWrite();
		return maxSum == null? null : maxSum.getDecimal();
	}

	protected AccountLink getAccountLink(Map values)
	{
		Object value = retrieveFieldValue(FIELD_ACCOUNT, values);
		if (value == null)
		{
			return null;
		}
		if (value instanceof AccountLink)
		{
			return (AccountLink) value;
		}
		if (value instanceof Account)
		{
			try
			{
				Account account = (Account) value;
				PersonData data = PersonContext.getPersonDataProvider().getPersonData();
				return data.findAccount(account.getNumber());
			}
			catch (BusinessException e)
			{
				throw new RuntimeException(e);
			}
			catch (BusinessLogicException e)
			{
				throw new RuntimeException(e);
			}
		}
		if (value instanceof String)
		{
			try
			{
				PersonData data = PersonContext.getPersonDataProvider().getPersonData();
				return data.findAccount((String) value);
			}
			catch (BusinessException e)
			{
				throw new RuntimeException(e);
			}
			catch (BusinessLogicException e)
			{
				throw new RuntimeException(e);
			}
		}
		return null;
	}

	protected String getCurrencyISOCode(String binding, Map values)
	{
		if(binding == null)
		{
			return null;
		}
		return (String) values.get(binding);
	}
}
