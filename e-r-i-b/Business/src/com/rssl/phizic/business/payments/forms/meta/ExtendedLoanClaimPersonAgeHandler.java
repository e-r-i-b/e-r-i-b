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
 * ������� ���������, ��� ������� ������� ������������� �������� ������� (������ ��� ����� 21)
 * @author Rtischeva
 * @ created 24.01.14
 * @ $Author$
 * @ $Revision$
 */
public class ExtendedLoanClaimPersonAgeHandler extends BusinessDocumentHandlerBase
{
	public void process(StateObject document, StateMachineEvent stateMachineEvent) throws DocumentException, DocumentLogicException
	{
		Integer personAge;
		if (PersonHelper.isGuest())
		{
			if (!(document instanceof ExtendedLoanClaim))
				throw new DocumentException("�������� ��� ������ id=" + document.getId() + " (��������� ExtendedLoanClaim)");
			ExtendedLoanClaim loanClaim = (ExtendedLoanClaim) document;
			DateSpan age = new DateSpan(loanClaim.getApplicantBirthDay(), Calendar.getInstance());
			personAge = age.getYears();
		}
		else
			personAge = PersonHelper.getPersonAgeFromContext();
		if (personAge < 21)
			throw new DocumentLogicException("��� ������� �� ������������� �������� �������");
	}
}
