package com.rssl.phizic.business.payments.forms.validators;

import com.rssl.common.forms.validators.MultiFieldsValidatorBase;
import com.rssl.common.forms.TemporalDocumentException;
import com.rssl.phizic.gate.bankroll.Account;

import java.util.Map;

/**
 * �������� �� ��, ��� ���� �������� � ���� ���������� �������� �������.
 * ���� ���� �������� ��� ���� ���������� �������� ��� ������ ������ (""),
 * �� ��������� ������� �������
 * @author Kidyaev
 * @ created 05.12.2005
 * @ $Author$
 * @ $Revision$
 */
public class AccountsNotEqualValidator extends MultiFieldsValidatorBase
{
	public static final String FIELD_FROM_ACCOUNT = "fromAccount";
	public static final String FIELD_TO_ACCOUNT = "toAccount";

	public boolean validate(Map values) throws TemporalDocumentException
	{
		String fromAccountNumber = getAccountNumber(retrieveFieldValue(FIELD_FROM_ACCOUNT, values));
		if (fromAccountNumber == null || fromAccountNumber.length() == 0)
			return true;
		String toAccountNumber = getAccountNumber(retrieveFieldValue(FIELD_TO_ACCOUNT, values));
		if (toAccountNumber == null || toAccountNumber.length() == 0)
			return true;
		return !fromAccountNumber.equals(toAccountNumber);
	}

//TODO ��� ���� toAccount ����� ���� �� ������ account, �� � string,
//TODO ������� obj ����� ���� ��� Account'�� ��� � Strin'��.
//TODO ����� ������� "�������" ������ �������...
//TODO ��������, ������� ��� ����������.
	private String getAccountNumber(Object obj)
	{
		if (obj == null)
		{
			return null;
		}
		if (obj instanceof Account)
		{
			return ((Account) obj).getNumber();
		}
		if (obj instanceof String)
		{
			return (String) obj;
		}
		throw new RuntimeException("����������� ��� ����");
	}
}
