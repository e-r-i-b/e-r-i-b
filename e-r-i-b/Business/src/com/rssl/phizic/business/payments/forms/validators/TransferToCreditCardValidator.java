package com.rssl.phizic.business.payments.forms.validators;

import com.rssl.common.forms.TemporalDocumentException;
import com.rssl.common.forms.validators.MultiFieldsValidatorBase;
import com.rssl.phizic.business.resources.external.AccountLink;
import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.common.types.transmiters.Pair;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.bankroll.BankrollService;
import com.rssl.phizic.gate.bankroll.Card;
import com.rssl.phizic.gate.bankroll.CardType;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizic.utils.GroupResultHelper;
import com.rssl.phizic.utils.StringHelper;

import java.util.Map;

/**
 * @author osminin
 * @ created 15.12.2010
 * @ $Author$
 * @ $Revision$
 *
 * Проверка на то, что если переводим на кредитную карту, то обязательно с карты
 */
public class TransferToCreditCardValidator extends MultiFieldsValidatorBase
{
	private static String FIELD_TO_RESOURCE = "toResource";
	private static String FIELD_FROM_RESOURCE = "fromResource";

	public boolean validate(Map values) throws TemporalDocumentException
	{
		try
		{
			Object fromResource = retrieveFieldValue(FIELD_FROM_RESOURCE, values);
			Object toResource = retrieveFieldValue(FIELD_TO_RESOURCE, values);
			// если не задан счет зачисления(в случае шаблона), проверять нечего
			if (toResource == null)
				return true;

			Card card = null;
			if (toResource instanceof CardLink)
			{
				card = ((CardLink) toResource).getCard();
			}
			else
			{
				String toResourceNumber = (String) toResource;
				if (StringHelper.isEmpty(toResourceNumber))
					return true;

				BankrollService bankrollService = GateSingleton.getFactory().service(BankrollService.class);
				Client client = PersonContext.getPersonDataProvider().getPersonData().getPerson().asClient();
				card = GroupResultHelper.getOneResult(bankrollService.getCardByNumber(client, new Pair<String, Office>(toResourceNumber, client.getOffice())));
			}

			return card == null || !(card.getCardType() == CardType.credit && fromResource instanceof AccountLink);
		}
		catch (Exception e)
		{
			throw new TemporalDocumentException(e);
		}
	}
}
