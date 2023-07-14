package com.rssl.phizic.business.payments.forms.validators;

import com.rssl.common.forms.TemporalDocumentException;
import com.rssl.common.forms.validators.MultiFieldsValidatorBase;
import com.rssl.phizic.business.resources.external.LoanLink;
import com.rssl.phizic.gate.loans.Loan;
import com.rssl.phizic.utils.MockHelper;

import java.util.Date;
import java.util.Map;

/**
 * @author niculichev
 * @ created 01.11.2010
 * @ $Author$
 * @ $Revision$
 */
public class CloseLoanDateValidator extends MultiFieldsValidatorBase
{
	private static final String FROM_RESOURCE = "resource";
	private static final String DOCUMENT_DATE = "longOfferEndDate";

	public boolean validate(Map values) throws TemporalDocumentException
	{
		Object resource = retrieveFieldValue(FROM_RESOURCE, values);
		Date endDate = (Date) retrieveFieldValue(DOCUMENT_DATE, values);

		if(resource instanceof LoanLink)
		{
			Loan loan = ((LoanLink)resource).getLoan();
			if(MockHelper.isMockObject(loan))
				throw new TemporalDocumentException("Ошибка при получении информации по кредиту с номером ссудного счета"+ loan.getAccountNumber());

			return endDate.compareTo(loan.getTermEnd().getTime())<=0;
		}

		return false;
	}
}
