package com.rssl.phizic.business.payments.forms.meta;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.doc.BusinessDocumentHandlerBase;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.common.forms.state.StateObject;
import com.rssl.phizic.business.documents.payments.ExtendedLoanClaim;
import com.rssl.phizic.business.persons.PersonHelper;
import com.rssl.phizic.common.types.DateSpan;
import com.rssl.phizic.common.types.client.CreationType;
import com.rssl.phizic.context.PersonContext;

import java.util.Calendar;

/**
 * хендлер проверяет, что срок проживания в населенном пункте не превышает возраст
 * @author Nady
 * @ created 03.02.15
 * @ $Author$
 * @ $Revision$
 */
public class ExtendedLoanClaimPersonResidenceDurationHandler extends BusinessDocumentHandlerBase
{
	public void process(StateObject document, StateMachineEvent stateMachineEvent) throws DocumentException, DocumentLogicException
	{
		Integer personAge;
		Integer residenceDuration;
		if (!(document instanceof ExtendedLoanClaim))
			throw new DocumentException("Неверный тип заявки id=" + document.getId() + " (Ожидается ExtendedLoanClaim)");
		ExtendedLoanClaim loanClaim = (ExtendedLoanClaim) document;
		residenceDuration = loanClaim.getApplicantCityResidencePeriod();

		if (PersonContext.getPersonDataProvider().getPersonData().getPerson().getCreationType() == CreationType.ANONYMOUS
			|| PersonContext.getPersonDataProvider().getPersonData().isGuest())
		{
			DateSpan age = new DateSpan(loanClaim.getApplicantBirthDay(), Calendar.getInstance());
			personAge = age.getYears();
		}
		else
			personAge = PersonHelper.getPersonAgeFromContext();
		if (residenceDuration!=null && personAge < residenceDuration)
			throw new DocumentLogicException("Срок проживания в населенном пункте превышает Ваш возраст!");
	}
}
