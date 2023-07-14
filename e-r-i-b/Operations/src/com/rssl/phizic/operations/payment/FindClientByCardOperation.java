package com.rssl.phizic.operations.payment;

import com.rssl.common.forms.TemporalDocumentException;
import com.rssl.common.forms.validators.RegexpFieldValidator;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.common.types.exceptions.LogicException;
import com.rssl.phizic.common.types.exceptions.SystemException;
import com.rssl.phizic.common.types.transmiters.GroupResult;
import com.rssl.phizic.common.types.transmiters.Pair;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.bankroll.BankrollService;
import com.rssl.phizic.gate.bankroll.Card;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.operations.ViewEntityOperation;
import com.rssl.phizic.operations.card.GetExternalCardCurrencyOperation;
import com.rssl.phizic.utils.GroupResultHelper;
import com.rssl.phizic.utils.StringHelper;

/**
 * Получение информации о клиенте по номеру карты
 * @author gladishev
 * @ created 16.10.2014
 * @ $Author$
 * @ $Revision$
 */

public class FindClientByCardOperation extends GetExternalCardCurrencyOperation
{
	private static final RegexpFieldValidator REGEXP_FIELD_VALIDATOR = new RegexpFieldValidator("(\\d{15})|(\\d{16})|(\\d{18})|(\\d{19})");
	private Pair<Client, Card> result;

	/**
	 * Инициализация операции
	 * @param cardNumber - номер карты
	 * @throws BusinessException
	 */
	public void initialize(String cardNumber) throws BusinessException, BusinessLogicException
	{
		try
		{
			if (!REGEXP_FIELD_VALIDATOR.validate(cardNumber))
				throw new BusinessException("Некорректный номер карты");
		}
		catch (TemporalDocumentException e)
		{
			throw new BusinessException(e);
		}

		super.initialize(cardNumber);
		try
		{
			Card card = getEntity();
			if (card == null)
				return;

			result = new Pair<Client, Card>(card.getCardClient(), card);
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	public Pair<Client, Card> getResult() throws BusinessException, BusinessLogicException
	{
		return result;
	}
}
