package com.rssl.phizic.business.payments.forms.validators;

import com.rssl.common.forms.TemporalDocumentException;
import com.rssl.common.forms.validators.MultiFieldsValidatorBase;
import com.rssl.phizic.business.resources.external.AccountLink;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.gate.bankroll.Account;

import java.math.BigDecimal;
import java.util.Map;

/**
 * @author osminin
 * @ created 29.11.2010
 * @ $Author$
 * @ $Revision$
 *
 * ��������� ��� ��������� ���������� ������� �� ����� �������� � ����������� �������� �� �����
 */
public class MininmumBalanceValidator extends MultiFieldsValidatorBase
{
	private static final String FIELD_FROM_RESOURCE = "fromResource";
	private static final String FIELD_SELL_AMOUNT = "sellAmount";

	public boolean validate(Map values) throws TemporalDocumentException
	{
		Object fromResource = retrieveFieldValue(FIELD_FROM_RESOURCE, values);
		if (fromResource instanceof AccountLink)
		{
			Account account = ((AccountLink) fromResource).getAccount();
			Money balance = account.getBalance();
			Money maxSumWrite = account.getMaxSumWrite();
			BigDecimal sellAmount = (BigDecimal) retrieveFieldValue(FIELD_SELL_AMOUNT, values);
			//���� ������������ ����� �������� �� ������, �������, ��� ����������� ������� ����� ����
			return maxSumWrite == null || sellAmount.compareTo(balance.sub(maxSumWrite).getDecimal()) >= 0;
		}
		//� ���� ��� ������������ �������
		return true;
	}
}
