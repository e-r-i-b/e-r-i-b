package com.rssl.phizic.business.payments.forms.validators;

import com.rssl.common.forms.TemporalDocumentException;
import com.rssl.common.forms.validators.MultiFieldsValidatorBase;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.common.types.exceptions.LogicException;
import com.rssl.phizic.common.types.exceptions.SystemException;
import com.rssl.phizic.common.types.transmiters.GroupResult;
import com.rssl.phizic.common.types.transmiters.Pair;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.context.PersonData;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.bankroll.Account;
import com.rssl.phizic.gate.bankroll.BankrollService;
import com.rssl.phizic.gate.bankroll.Card;
import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizic.utils.GroupResultHelper;
import com.rssl.phizic.utils.MockHelper;

import java.util.Map;

/**
 * Базовый класс для работы с аккаунтами и картами (Account и Card)
 * @author eMakarov
 * @ created 20.12.2007
 * @ $Author$
 * @ $Revision$
 */
public abstract class AccountAndCardValidatorBase extends MultiFieldsValidatorBase
{
	protected Account getAccount(String validatorField, Map values) throws TemporalDocumentException
	{
		Account account = (Account)retrieveFieldValue(validatorField, values);
		if(MockHelper.isMockObject(account))
			throw new TemporalDocumentException("Ошибка при получении информации по счету");
		return account;
	}

	protected Card getCard(String validatorField, Map values) throws TemporalDocumentException
	{
		Card card = (Card) retrieveFieldValue(validatorField, values);
		if(MockHelper.isMockObject(card))
			throw new TemporalDocumentException("Ошибка при получении информации по карте");
		return card;
	}

	protected Card getExternalCard(String validatorField, Map values) throws TemporalDocumentException
	{
		try
		{
			Pair<String, Office> request = new Pair<String, Office>((String) retrieveFieldValue(validatorField, values), null);
			ActivePerson person = PersonContext.getPersonDataProvider().getPersonData().getPerson();

			BankrollService bankrollService = GateSingleton.getFactory().service(BankrollService.class);
			GroupResult<Pair<String, Office>, Card> result = bankrollService.getCardByNumber(person.asClient(), request);

			Card externalCard = GroupResultHelper.getOneResult(result);
			if(MockHelper.isMockObject(externalCard))
				throw new TemporalDocumentException("Ошибка при получении информации по карте");

			return externalCard;
		}
		catch (BusinessException e)
		{
			throw new TemporalDocumentException(e);
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

	protected CardLink getCardLink(String validatorField, Map values) throws TemporalDocumentException
	{
		CardLink cardLink = (CardLink) retrieveFieldValue(validatorField, values);
		if(MockHelper.isMockObject(cardLink.getCard()))
			throw new TemporalDocumentException("Ошибка при получении информации по карте");
		return cardLink;
	}

	protected CardLink getCardLink(String cardNumber) throws BusinessException
	{
		PersonData data = PersonContext.getPersonDataProvider().getPersonData();
		CardLink linkById = data.findCard(cardNumber);
		return linkById;
	}

	protected Currency getAccountCurrency(String validatorField, Map values) throws TemporalDocumentException
	{
		Account account = getAccount(validatorField, values);

	    return account.getCurrency();
	}

}
