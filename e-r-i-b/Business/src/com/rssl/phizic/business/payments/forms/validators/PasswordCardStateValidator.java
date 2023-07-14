package com.rssl.phizic.business.payments.forms.validators;

import com.rssl.common.forms.validators.FieldValidatorBase;
import com.rssl.common.forms.TemporalDocumentException;
import com.rssl.phizic.auth.passwordcards.PasswordCard;
import com.rssl.phizic.auth.passwordcards.PasswordCardService;
import com.rssl.phizic.security.SecurityDbException;

/**
 * @author Krenev
 * @ created 01.11.2007
 * @ $Author$
 * @ $Revision$
 */
public class PasswordCardStateValidator extends FieldValidatorBase
{
	public static final String PARAMETER_STATE = "state";

	private static final PasswordCardService cardService = new PasswordCardService();

	private String state;

	public PasswordCardStateValidator()
	{
		this("Некорректный статус карты");
	}

	public PasswordCardStateValidator(String message)
	{
		setMessage(message);
	}

	public void setParameter(String name, String value)
	{
		if (PARAMETER_STATE.equals(name))
		{
			state = value;
		}
	}

	public String getParameter(String name)
	{
		if (PARAMETER_STATE.equals(name))
		{
			return state;
		}
		return null;
	}

	public boolean validate(String value) throws TemporalDocumentException
	{
		String key = getParameter(PARAMETER_STATE);
		if (key == null || key.length() == 0)
			throw new RuntimeException("Не определен параметр "+PARAMETER_STATE+" .");
		try
		{
			PasswordCard card = cardService.findByNumber(value);
			return key.equals(card.getState());
		}
		catch (SecurityDbException ex)
		{
			return false;
		}
	}
}
