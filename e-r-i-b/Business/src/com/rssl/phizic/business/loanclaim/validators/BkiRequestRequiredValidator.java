package com.rssl.phizic.business.loanclaim.validators;

import com.rssl.common.forms.TemporalDocumentException;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.phizic.business.login.GuestHelper;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.context.PersonData;

/**
 * Валидатор, проверяющий необходимость заполнения чекбокса согласия на запрос в БКИ
 * @author Rtischeva
 * @ created 25.05.15
 * @ $Author$
 * @ $Revision$
 */
public class BkiRequestRequiredValidator extends RequiredFieldValidator
{
	@Override
	public boolean validate(String value) throws TemporalDocumentException
	{
		boolean isGuest = false;
		if (PersonContext.isAvailable())
		{
			PersonData personData = PersonContext.getPersonDataProvider().getPersonData();
			isGuest = personData.isGuest();
		}

		if (isGuest && !GuestHelper.hasPhoneInMB())
			return true;

		return super.validate(value);
	}
}
