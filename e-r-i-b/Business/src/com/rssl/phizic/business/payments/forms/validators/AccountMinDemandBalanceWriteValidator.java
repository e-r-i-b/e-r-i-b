package com.rssl.phizic.business.payments.forms.validators;

import com.rssl.common.forms.TemporalDocumentException;
import com.rssl.phizic.business.resources.external.AccountLink;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.gate.bankroll.Account;
import com.rssl.phizic.utils.MockHelper;

import java.math.BigDecimal;
import java.util.Map;

/**
 * @author Gololobov
 * @ created 18.07.2011
 * @ $Author$
 * @ $Revision$
 */

public class AccountMinDemandBalanceWriteValidator extends AccountMaxSumWriteValidator
{
	/*
	 * ������������� ��� ����������� ������� ������������ �������
	 */
	@Override
	protected BigDecimal getCheckedAmount(Map values) throws TemporalDocumentException
	{
		AccountLink accountLink = getAccountLink(values);
		if (accountLink == null)
			return null;

		Account account = accountLink.getAccount();
		if(MockHelper.isMockObject(account))
		        return null;
		Money maxSum = account.getMaxSumWrite();
		Money balance = account.getBalance();
		BigDecimal checkAmount = null;
		if (balance != null && maxSum != null && balance.getDecimal().compareTo(maxSum.getDecimal()) >= 0)
		{
		 	//������ ������������ �������
			checkAmount = balance.getDecimal().subtract(maxSum.getDecimal());
		}
		return checkAmount;
	}

	@Override
	public boolean validate(Map values) throws TemporalDocumentException
	{
		AccountLink accountLink = getAccountLink(values);
		if (accountLink == null)
			return false;

		if (isStoredResource(accountLink))
			return true;

		Account account = accountLink.getAccount();
		if (MockHelper.isMockObject(account))
			return false;

		BigDecimal sum = getExpectedAmount(values);
		if (sum == null && getAmountMayBeNullValue())
			return true;

		Money balance = account.getBalance();
		BigDecimal minDemandBalance = getCheckedAmount(values);

		// ���� ����������� ������� �������, �� �� ������� ��������� � ����������� �������
		boolean valid = minDemandBalance == null || minDemandBalance.compareTo(BigDecimal.ZERO) == 0
							|| (balance.getDecimal().subtract(sum)).compareTo(minDemandBalance) >=0;
		if (!valid)
		{
			String binding = getBinding(FIELD_RESOURCE_CURRENCY);
			if(binding != null)
			{
				StringBuffer message = new StringBuffer(getMessage());
				message.append(" (");
				message.append(minDemandBalance);
				message.append(" ");
				message.append(getCurrencyISOCode(binding, values));
				message.append(")");

				setMessage( message.toString() );
			}
		}
		return valid;
	}
}
