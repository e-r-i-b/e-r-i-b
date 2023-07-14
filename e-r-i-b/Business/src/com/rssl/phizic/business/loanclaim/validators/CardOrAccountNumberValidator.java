package com.rssl.phizic.business.loanclaim.validators;

import com.rssl.common.forms.TemporalDocumentException;
import com.rssl.common.forms.validators.FieldValidatorBase;
import com.rssl.phizic.business.persons.PersonHelper;
import org.apache.commons.lang.StringUtils;

import java.util.regex.Pattern;

/**
 * Валидация поля ввода номера карты/счета
 * @author Puzikov
 * @ created 14.08.15
 * @ $Author$
 * @ $Revision$
 */

public class CardOrAccountNumberValidator extends FieldValidatorBase
{
	private static final Pattern ACCOUNT_REGEX          = Pattern.compile("\\d{20}");
	private static final Pattern ACCOUNT_OR_CARD_REGEX  = Pattern.compile("\\d{13,20}"); //по спекам etsm/crm поле номер карты от 13 до 19

	private static final String ACCOUNT_MESSAGE         = "Поле номер счета должно содержать ровно 20 цифр.";
	private static final String ACCOUNT_OR_CARD_MESSAGE = "Поле номер счета/карты должно содержать от 13 до 20 цифр.";

	public boolean validate(String value) throws TemporalDocumentException
	{
		if (StringUtils.isEmpty(value))
		{
			return true;
		}

		//для гостевой заявки разрешается вводить номер карты в поле "Номер счета"
		if (PersonHelper.isGuest())
		{
			setMessage(ACCOUNT_OR_CARD_MESSAGE);
			return ACCOUNT_OR_CARD_REGEX.matcher(value).matches();
		}
		else
		{
			setMessage(ACCOUNT_MESSAGE);
			return ACCOUNT_REGEX.matcher(value).matches();
		}
	}
}
