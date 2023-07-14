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
 * Проверка на то, что валюта счета списания и валюта карточного счета зачисления являются одинаковыми
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
	 * Проверяет значения полей на валидность ЭТОТ МЕТОД ВО ВСЕХ РЕАЛИЗАЦИЯХ ДОЛЖЕН БЫТЬ THREAD SAFE!!!!!!!!!
	 *
	 * @param values значения для проверки. Key - имя поля (в форме), Value - значение поля.
	 */
	public boolean validate(Map values) throws TemporalDocumentException
	{
		Currency accountCurrency = null;
		Currency cardAccountCurrency = null;

		cardAccountCurrency = getCardAccountCurrency(FIELD_CARD, values);
		accountCurrency = getAccountCurrency(FIELD_ACCOUNT, values);
		if (cardAccountCurrency == null || accountCurrency == null)
			throw new TemporalDocumentException("Ошибка при получении валют для сравнения");

		return accountCurrency.getCode().equals(cardAccountCurrency.getCode());
	}

	private Currency getCardAccountCurrency(String validatorField, Map values) throws TemporalDocumentException
	{
		//TODO   перенес сюда объявление из-за того, что при загрузке через ant gatefactory не существует
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
