package com.rssl.phizic.business.payments.forms.validators;

import com.rssl.common.forms.TemporalDocumentException;
import com.rssl.common.forms.validators.MultiFieldsValidatorBase;
import com.rssl.phizic.person.Person;
import com.rssl.phizic.context.PersonContext;

import java.util.Map;

/**
 * @author osminin
 * @ created 07.10.2009
 * @ $Author$
 * @ $Revision$
 */

//Валидатор сравнивает ФИО отправителя и получателя без учета регистра и пробелов.
public class PayerReceiverFIOValidator extends MultiFieldsValidatorBase
{
	public static final String FIELD_RECEIVER_SURNAME = "receiverSurname";
	public static final String FIELD_RECEIVER_FIRSTNAME = "receiverFirstName";
	public static final String FIELD_RECEIVER_PATRNAME = "receiverPatrName";

	public boolean validate(Map values) throws TemporalDocumentException
	{
		Person person = PersonContext.getPersonDataProvider().getPersonData().getPerson();

		String surName = (String) retrieveFieldValue(FIELD_RECEIVER_SURNAME, values);
		String firstName = (String) retrieveFieldValue(FIELD_RECEIVER_FIRSTNAME, values);
		String patrName = (String) retrieveFieldValue(FIELD_RECEIVER_PATRNAME, values);

		if (!person.getSurName().equals(surName))
			return false;
		if (!person.getFirstName().equals(firstName))
			return false;
		if (person.getPatrName() != null && patrName != null && !person.getPatrName().equals(patrName))
			return false;
		return true;
	}
}
