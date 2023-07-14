package com.rssl.phizic.operations.card;

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
import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.utils.GroupResultHelper;
import com.rssl.phizic.utils.StringHelper;

/**
 * ќпераци€ получени€ информации о банке-эмитенте карты
 * @ author: Gololobov
 * @ created: 25.06.14
 * @ $Author$
 * @ $Revision$
 */
public class GetCardIssuerInfoOperation extends OperationBase
{
	/*
	 * явл€етс€ ли —бербанк банком-эмитентом карты
	 * @param cardNumber - номер карты
	 * @return
	 * @throws BusinessException
	 */
	public boolean isSBRFCardIssuerBank(String cardNumber) throws BusinessException, BusinessLogicException
	{
		try
		{
			if (StringHelper.isEmpty(cardNumber))
				return false;
			Pair<String, Office> request = new Pair<String, Office>(cardNumber, null);
			ActivePerson person = PersonContext.getPersonDataProvider().getPersonData().getPerson();

			BankrollService bankrollService = GateSingleton.getFactory().service(BankrollService.class);
			GroupResult<Pair<String, Office>, Card> result = bankrollService.getCardByNumber(person.asClient(), request);
			Card card = GroupResultHelper.getOneResult(result);
			return (card != null);
		}
		catch (SystemException e)
		{
			throw new BusinessException(e);
		}
		catch (LogicException e)
		{
			throw new BusinessLogicException(e);
		}
	}
}
