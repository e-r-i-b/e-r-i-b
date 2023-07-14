package com.rssl.phizic.business.payments.forms.validators;

import com.rssl.common.forms.TemporalDocumentException;
import com.rssl.common.forms.validators.MultiFieldsValidatorBase;
import com.rssl.phizic.business.resources.external.AccountLink;
import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.business.resources.external.IMAccountLink;
import com.rssl.phizic.gate.bankroll.Account;
import com.rssl.phizic.gate.bankroll.AccountState;
import com.rssl.phizic.gate.bankroll.Card;
import com.rssl.phizic.gate.bankroll.CardState;
import com.rssl.phizic.gate.ima.IMAccount;
import com.rssl.phizic.gate.ima.IMAccountState;

import java.util.Map;

/**
 * ѕровер€ем не арестован ли продукт списани€
 * @author basharin
 * @ created 03.09.14
 * @ $Author$
 * @ $Revision$
 */

public class NotArrestedProductValidator extends MultiFieldsValidatorBase
{
	private static String CARD_ERROR_MESSAGE = "¬ы не можете перевести средства с карты, котора€ арестована. ѕожалуйста, выберите другую карту списани€.";
	private static String ACCOUNT_ERROR_MESSAGE = "¬ы не можете перевести средства с вклада, который арестован. ѕожалуйста, выберите другой вклад списани€.";
	private static String IM_ACCOUNT_ERROR_MESSAGE = "¬ы не можете перевести средства с металлического счета, который арестован. ѕожалуйста, выберите другой счет списани€";
	private static String FIELD_RESOURCE = "fromResource";

	public boolean validate(Map values) throws TemporalDocumentException
	{
		Object fromResource = retrieveFieldValue(FIELD_RESOURCE, values);

		if (fromResource instanceof CardLink)
		{
			CardLink  cardLink = (CardLink) fromResource;

			Card card = cardLink.getCard();
			if (card.getCardAccountState() == AccountState.ARRESTED)
			{
				setMessage(CARD_ERROR_MESSAGE);
				return false;
			}
		}
		else if (fromResource instanceof IMAccountLink)
		{
			IMAccountLink imAccountLink = (IMAccountLink) fromResource;

			IMAccount imAccount = imAccountLink.getImAccount();
			if (imAccount.getState() == IMAccountState.arrested)
			{
				setMessage(IM_ACCOUNT_ERROR_MESSAGE);
				return false;
			}
		}
		else if (fromResource instanceof AccountLink)
		{
			AccountLink accountLink = (AccountLink) fromResource;

			Account account = accountLink.getAccount();
			if (account.getAccountState() == AccountState.ARRESTED)
			{
				setMessage(ACCOUNT_ERROR_MESSAGE);
				return false;
			}
		}

		return true;
	}
}
