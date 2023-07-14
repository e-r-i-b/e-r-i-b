package com.rssl.phizic.business.payments.forms.validators;

import com.rssl.common.forms.TemporalDocumentException;
import com.rssl.common.forms.validators.FieldValidatorBase;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.common.types.transmiters.Pair;
import com.rssl.phizic.common.types.exceptions.SystemException;
import com.rssl.phizic.common.types.exceptions.LogicException;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.bankroll.BankrollService;
import com.rssl.phizic.gate.bankroll.Card;
import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizic.utils.GroupResultHelper;

/**
 * @author osminin
 * @ created 27.01.2011
 * @ $Author$
 * @ $Revision$
 *
 * Проверяем, существует ли карта с таким номером.
 */
public class FindExternalCardByNumberFieldValidator extends FieldValidatorBase
{
	private static BankrollService bankrollService = GateSingleton.getFactory().service(BankrollService.class);
	private static final String CARD_OPERATION_ERROR_MSG = "Операции по данной карте временно не доступны в системе. Пожалуйста, повторите попытку позже.";

	public boolean validate(String value) throws TemporalDocumentException
	{
		try
		{
			Pair<String, Office> pair = new Pair<String, Office>(value, null);
			ActivePerson person = PersonContext.getPersonDataProvider().getPersonData().getPerson();
			Card card = GroupResultHelper.getOneResult(bankrollService.getCardByNumber(person.asClient(), pair));
			return card != null;
		}
		catch (BusinessException e)
		{
			throw new TemporalDocumentException(CARD_OPERATION_ERROR_MSG, e);
		}
		catch (SystemException e)
		{
			throw new TemporalDocumentException(CARD_OPERATION_ERROR_MSG, e);
		}
		catch (LogicException e)
		{
			throw new TemporalDocumentException(CARD_OPERATION_ERROR_MSG, e);
		}
	}
}
