package com.rssl.phizic.business.payments.forms.validators;

import com.rssl.common.forms.TemporalDocumentException;
import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.common.types.exceptions.LogicException;
import com.rssl.phizic.common.types.exceptions.SystemException;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.bankroll.Account;
import com.rssl.phizic.gate.bankroll.BankrollService;
import com.rssl.phizic.gate.bankroll.Card;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.utils.GroupResultHelper;

import java.util.Map;

/**
 * �������� �� ��, ��� ������ ����� �������� � ������ ���������� ����� ���������� �������� �����������
 * @author eMakarov
 * @ created 20.12.2007
 * @ $Author$
 * @ $Revision$
 */
public class AccountAndCardAccountCurrenciesEqualValidator extends AccountAndCardValidatorBase
{
	public static final String FIELD_CARD = "card";
	public static final String FIELD_ACCOUNT = "account";

	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);

	/**
	 * ��������� �������� ����� �� ���������� ���� ����� �� ���� ����������� ������ ���� THREAD SAFE!!!!!!!!!
	 *
	 * @param values �������� ��� ��������. Key - ��� ���� (� �����), Value - �������� ����.
	 */
	public boolean validate(Map values) throws TemporalDocumentException
	{
		Currency accountCurrency = null;
		Currency cardAccountCurrency = null;

		cardAccountCurrency = getCardAccountCurrency(FIELD_CARD, values);
		accountCurrency = getAccountCurrency(FIELD_ACCOUNT, values);
		if (cardAccountCurrency == null || accountCurrency == null)
			throw new TemporalDocumentException("������ ��� ��������� ����� ��� ���������");

		return accountCurrency.getCode().equals(cardAccountCurrency.getCode());
	}

	private Currency getCardAccountCurrency(String validatorField, Map values) throws TemporalDocumentException
	{
		//TODO   ������� ���� ���������� ��-�� ����, ��� ��� �������� ����� ant gatefactory �� ����������
		try
		{
			Card card = getCard(validatorField, values);
			BankrollService bankrollService = GateSingleton.getFactory().service(BankrollService.class);
			Account cardPrimaryAccount = GroupResultHelper.getOneResult(bankrollService.getCardPrimaryAccount(card));

			return cardPrimaryAccount.getCurrency();
		}
		catch (SystemException e)
		{
			throw new TemporalDocumentException(e);
		}
		catch (LogicException e)
		{
			throw new TemporalDocumentException(e);
		}
	}
}
