package com.rssl.phizic.business.payments.forms.validators;

import com.rssl.common.forms.TemporalDocumentException;
import com.rssl.common.forms.validators.FieldValidatorBase;
import com.rssl.phizic.business.forms.types.UserResourceParser;
import com.rssl.phizic.business.resources.ReissuedCardFilter;
import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.business.resources.external.ExternalResourceLink;
import java.text.ParseException;


/**
 * Валидатор для проверки при перевыпуске карт.
 * не пропускаеет карты со следующими свойствами:
 * 1)	Перевыпуск кредитных карт (CardType= CC)
 * 2)	Перевыпуск Instant Issue карт(Momentum). (UNICardType=7 или  UNICardType=8 и UNIAcctType= F )
 * 3)	Перевыпуск карт УЭК. (UNICardType=27)
 * 4)	Перевыпуск карт ПРО100 (UNICardType=14 или UNICardType=15 или UNICardType=17 или UNICardType=18)
 *
 * @author basharin
 * @ created 27.06.2013
 * @ $Author$
 * @ $Revision$
 */

public class ReissuedUniCardTypeValidator extends FieldValidatorBase
{
	private static final UserResourceParser resourceParser = new UserResourceParser();

	public ReissuedUniCardTypeValidator()
	{
		setMessage("Перевыпуск данного типа карт через Сбербанк Онлайн невозможен.");
	}

	public boolean validate(String value) throws TemporalDocumentException
	{
		CardLink link = parseValue(value);
		return new ReissuedCardFilter().evaluate(link);
	}

	private CardLink parseValue(String value) throws TemporalDocumentException
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
		if (link instanceof CardLink)
			return (CardLink) link;
		throw new TemporalDocumentException("Данное значение счетом не является");
	}
}
