package com.rssl.phizic.business.payments.forms.meta;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.doc.BusinessDocumentHandlerBase;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.common.forms.state.StateObject;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.business.documents.payments.ExtendedLoanClaim;
import com.rssl.phizic.business.persons.PersonHelper;
import com.rssl.phizic.common.types.DateSpan;
import com.rssl.phizic.common.types.client.CreationType;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.gate.loanclaim.type.Employee;

import java.util.Calendar;

/**
 * Проверка максимального возраста для получения кредита
 * @author Puzikov
 * @ created 17.04.14
 * @ $Author$
 * @ $Revision$
 */

public class ExtendedLoanClaimMaxPersonAgeHandler extends BusinessDocumentHandlerBase
{
	private static final int NON_EMPLOYED_MAX_AGE = 65;

	public void process(StateObject document, StateMachineEvent stateMachineEvent) throws DocumentException, DocumentLogicException
	{
		if (!(document instanceof ExtendedLoanClaim))
		{
			throw new DocumentException("Неверный тип заявки id=" + ((BusinessDocument) document).getId() + " (Ожидается ExtendedLoanClaim)");
		}
		ExtendedLoanClaim loanClaim = (ExtendedLoanClaim) document;

		Employee applicantAsEmployee = loanClaim.getApplicantAsEmployee();

		//для частных практиков и пенсионеров дефолтный макс. возраст
		int maxAge = (applicantAsEmployee != null) ? applicantAsEmployee.getPositionCategory().getMaxAge() : NON_EMPLOYED_MAX_AGE;

		Integer personAge;
		if (PersonContext.getPersonDataProvider().getPersonData().getPerson().getCreationType() == CreationType.ANONYMOUS
			|| PersonContext.getPersonDataProvider().getPersonData().isGuest())
		{
			DateSpan age = new DateSpan(loanClaim.getApplicantBirthDay(), Calendar.getInstance());
			personAge = age.getYears();
		}
		else
			personAge = PersonHelper.getPersonAgeFromContext();

		if (personAge > maxAge)
			throw new DocumentLogicException("Ваш возраст не удовлетворяет условиям кредита");
	}
}
