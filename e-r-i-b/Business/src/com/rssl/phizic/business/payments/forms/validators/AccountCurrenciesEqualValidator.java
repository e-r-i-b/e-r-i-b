package com.rssl.phizic.business.payments.forms.validators;

import com.rssl.phizic.gate.bankroll.Account;
import com.rssl.common.forms.TemporalDocumentException;

import java.util.Map;

/**
 * �������� �� ��, ��� ������ ����� �������� � ������ ����� ���������� �������� �����������.
 * @author Kosyakova
 * @ created 20.10.2006
 * @ $Author$
 * @ $Revision$
 */
public class AccountCurrenciesEqualValidator extends AccountAndCardValidatorBase
{
    public static final String FIELD_FROM_ACCOUNT = "fromAccount";
    public static final String FIELD_TO_ACCOUNT   = "toAccount";

    public boolean validate(Map values) throws TemporalDocumentException
    {
		String fromAccountCurrency = getAccountCurrency(retrieveFieldValue(FIELD_FROM_ACCOUNT, values));
		String toAccountCurrency = getAccountCurrency(retrieveFieldValue(FIELD_TO_ACCOUNT, values));

	    if (fromAccountCurrency == null || toAccountCurrency == null)
			return true;

	    return fromAccountCurrency.equals(toAccountCurrency);
    }

	/**
	 * �������� ��� ������.
	 * ��� toAccount ����� ���� ��� String ��� � Account
	 * ������� obj ����� ���� ��� Account'�� ��� � String'��
	 */
	private String getAccountCurrency(Object obj)
		{
			if (obj == null)
			{
				return null;
			}
			if (obj instanceof Account)
			{
				return ((Account) obj).getCurrency().getNumber();
			}
			if (obj instanceof String)
			{
				if (((String) obj).length() < 8)
				{
					return null;
				}
				return (((String) obj).substring(5,8).equals("810") ? "643" : ((String) obj).substring(5,8));
			}
			throw new RuntimeException("����������� ��� ����");
		}
}

