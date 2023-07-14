package com.rssl.phizic.business.payments.forms.validators;

import com.rssl.common.forms.TemporalDocumentException;
import com.rssl.common.forms.validators.FieldValidatorBase;
import com.rssl.phizic.business.forms.types.UserResourceParser;
import com.rssl.phizic.business.resources.external.AccountLink;
import com.rssl.phizic.business.resources.external.ExternalResourceLink;
import com.rssl.phizic.gate.bankroll.Account;
import com.rssl.phizic.gate.bankroll.AccountState;

import java.text.ParseException;

/**
 * @author Gulov
 * @ created 13.06.2013
 * @ $Author$
 * @ $Revision$
 */

/**
 * Проверка наличия сберкнижки на данном счет
 */
public class LossPassbookAccountValidator extends FieldValidatorBase
{
	private static final UserResourceParser resourceParser = new UserResourceParser();

	public boolean validate(String value) throws TemporalDocumentException
	{
		if(isValueEmpty(value))
			return true;
		AccountLink accountLink = parseValue(value);
		Account account = accountLink.getAccount();
		return account.getAccountState() != AccountState.LOST_PASSBOOK
			&& account.getPassbook() != null
			&& account.getPassbook();
	}

	private AccountLink parseValue(String value) throws TemporalDocumentException
	{
		ExternalResourceLink link = null;
		try
		{
			link = resourceParser.parse(value);
		}
		catch (ParseException e)
		{
			throw new TemporalDocumentException("Ошибка парсинга значения " + value, e);
		}
		if (link instanceof AccountLink)
			return (AccountLink) link;
		throw new TemporalDocumentException("Данное значение счетом не является");
	}
}
