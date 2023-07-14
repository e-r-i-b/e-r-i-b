package com.rssl.phizic.business.payments.forms.validators;

import com.rssl.common.forms.TemporalDocumentException;
import com.rssl.common.forms.validators.MultiFieldsValidatorBase;
import com.rssl.phizic.business.resources.external.LoanLink;
import com.rssl.phizic.context.PersonContext;

import java.util.Map;

/**
 * @author osminin
 * @ created 21.10.2010
 * @ $Author$
 * @ $Revision$
 * Проверка, что ФИО клиента и заемщика/созаемщика не отличаются
 */
public class UserAndBorrowerFIOValidator extends MultiFieldsValidatorBase
{
	private static String FIELD_LOAN = "loan";

	public boolean validate(Map values) throws TemporalDocumentException
	{
		LoanLink loanLink = (LoanLink) retrieveFieldValue(FIELD_LOAN, values);
		String personFIO = PersonContext.getPersonDataProvider().getPersonData().getPerson().getFullName();
		String borrowedFIO = loanLink.getLoan().getBorrower().getFullName();
		return personFIO.equalsIgnoreCase(borrowedFIO);
	}
}
